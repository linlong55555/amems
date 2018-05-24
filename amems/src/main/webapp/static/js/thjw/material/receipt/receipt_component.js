componentModal = {
	param: {
		selected:null,
		clearUser: true,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		$("#componentModal").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		if(param.clearUser==false){
			$("#componentModal_btn_clear").hide();
		}
		this.clearData();
		this.goPage(1,"auto","desc");
	},
	data:[],
	clearData : function(){
		$("#component_keyword_search").val('');
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
		   url:basePath+"/aerialmaterial/receipt/queryComponentPage",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.data = data.rows;
				   componentModal.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : 'component_pagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				signByKeyword($.trim($("#component_keyword_search").val()),[2],"#componentList tr td");
			   } else {
				  $("#componentList").empty();
				  $("#component_pagination").empty();
				  $("#componentList").append("<tr><td colspan=\"10\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 var shlx = $("#shlx").val();
		 var id = "";
		 if(shlx == 1 || shlx == 2){
			 id = $("#htid").val();
		 }else if(shlx == 3){
			 id = $("#jrsqid").val();
		 }
		 searchParam.paramsMap = {
				 keyword:$.trim($("#component_keyword_search").val()),
				 dprtcode:this.param.dprtcode,
				 type:shlx,
				 id:id
		 };
		 var choose = [];
		 $("#list>tr:not(.non-choice)").each(function(){
			 var tr = $(this);
			 var isContain = false;
			 for(var i = 0; i < choose.length; i++){
				var bjid = choose[i].bjid;
				var sl = parseInt(choose[i].sl);
				if(bjid == tr.find("input[name='bjid']").val()){
					sl += parseInt(tr.find("input[name='shsl']").val());
					choose[i].sl = sl;
					isContain = true;
				}
			} 
			if(!isContain){
				choose.push({
				    bjid : tr.find("input[name='bjid']").val(),
				    sl : tr.find("input[name='shsl']").val()
				});
			}
		 });
		 searchParam.paramsMap.choose = choose;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var jddxlxMap = {
				1 : "其他",
				2 : "飞行队",
				3 : "航空公司"
		};
		var htmlContent = '';
		$.each(list,function(index,row){
		   var checked = "";
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
		   }
		   if(componentModal.param.selected && componentModal.param.selected == row.ID){
			   checked = "checked";
		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='checkbox' name='name' index='"+index+"' "+checked+" /></td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.BJH)+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.YWMS)+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.ZWMS)+"</td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.GLJB)+"</td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.HCLX)+"</td>";
		   htmlContent += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.ZDSL)+"</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.JLDW)+"</td>";
		   htmlContent += "<input type='hidden' name='sn' value='"+(row.SN||"")+"'>";
		   htmlContent += + "</tr>";  
		    
	   });
	   $("#componentList", $("#componentModal")).empty();
	   $("#componentList", $("#componentModal")).html(htmlContent);
	   componentModal.checkContract();
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkContract: function(){
		$("#componentList tr").click(function(event){
			// 避免复选框重复选择
			if($(event.target).attr("type") == "checkbox"){
				return;
			}
			var checked = $(this).find("input[type='checkbox']").is(":checked");
			$(this).find("input[type='checkbox']").attr("checked",!checked);
		});
		
	},
	setContract: function(){
		var $checkedRadio = $("#componentList", $("#componentModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			var datas = [];
			if(data.length > 0){
				$("#componentList tr input:checked").each(function(){
					var index = $(this).attr("index");	
					datas.push(data[index]);
				});
				this.param.callback(datas);
			}else{
				this.param.callback({});
			}
		}
		$("#componentModal").modal("hide");
	},
	clearUser:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
		$("#componentModal").modal("hide");
	}
}