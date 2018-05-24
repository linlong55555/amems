lendReturnModal = {
	param: {
		selected:null,
		clearUser: true,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		$("#lendReturnModal").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		if(param.clearUser==false){
			$("#lendReturnModal_btn_clear").hide();
		}
		this.clearData();
		this.goPage(1,"auto","desc");
	},
	data:[],
	clearData : function(){
		$("#lendReturn_keyword_search").val('');
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var searchParam = this.gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		var this_ = this;
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/aerialmaterial/instock/list/lend",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.data = data.rows;
				   lendReturnModal.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : 'lendReturn_pagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				signByKeyword($.trim($("#lendReturn_keyword_search").val()),[3,4,5],"#lendReturnList tr td");
			   } else {
				  $("#lendReturnList").empty();
				  $("#lendReturn_pagination").empty();
				  $("#lendReturnList").append("<tr><td colspan=\"10\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.paramsMap = {
				 keyword:$.trim($("#lendReturn_keyword_search").val())
		 };
		 var secondment = {};
		 secondment.jddxlx = $("#jddxlx").val();
		 secondment.id = $("#jddx").val();
		 searchParam.secondment = secondment;
		 searchParam.dprtcode = this.param.dprtcode;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var hclxMap = {
				0 : "其他",
				1 : "航材",
				2 : "设备",
				3 : "工具",
				4 : "危险品",
				5 : "低值易耗品"
		};
		var gljbMap = {
				1 : "无",
				2 : "批次号管理",
				3 : "序列号管理"
		};
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
			if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='lendReturnModal.checkContract(this)'>";
			} else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='lendReturnModal.checkContract(this)'>";
			}
			if(lendReturnModal.param.selected && lendReturnModal.param.selected == row.id){
			   checked = "checked";
			}
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='checkbox' name='name' index='"+index+"' "+checked+" /></td>";
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.secondment ? row.secondment.jddxms:'')+"' align='left'>"+StringUtil.escapeStr(row.secondment ? row.secondment.jddxms:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.bjh:'')+"' align='left'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.bjh:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.ywms:'')+"' align='left'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.ywms:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.zwms:'')+"' align='left'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.zwms:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.cjjh:'')+"' align='left'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.cjjh:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+hclxMap["hclx",formatUndefine(row.hcMainData?row.hcMainData.hclx:'')]+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='center'>"+gljbMap["gljb",formatUndefine(row.hcMainData?row.hcMainData.gljb:'')]+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='right'>"+formatUndefine(row.dghsl)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.jldw:'')+"</td>";  
			htmlContent += "<input type='hidden' name='sn' value='"+(row.sn||row.pch||"")+"'>";  
			htmlContent += "</tr>";  
		    
	   });
	   $("#lendReturnList", $("#lendReturnModal")).empty();
	   $("#lendReturnList", $("#lendReturnModal")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkContract: function(objradio){
		$(objradio).find("input[type='checkbox']").attr("checked",true);
	},
	setContract: function(){
		var $checkedRadio = $("#lendReturnList", $("#lendReturnModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			var datas = [];
			if(data.length > 0){
				$("#lendReturnList tr input:checked").each(function(){
					var index = $(this).attr("index");	
					datas.push(data[index]);
				});
				this.param.callback(datas);
			}else{
				this.param.callback({});
			}
		}
		$("#lendReturnModal").modal("hide");
	},
	clearUser:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
		$("#lendReturnModal").modal("hide");
	}
}