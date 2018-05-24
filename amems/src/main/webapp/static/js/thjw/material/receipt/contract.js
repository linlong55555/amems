contractModal = {
	param: {
		selected:null,
		clearUser: true,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		$("#contractModal").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		if(param.clearUser==false){
			$("#contractModal_btn_clear").hide();
		}
		this.clearData();
		this.goPage(1,"auto","desc");
	},
	data:[],
	clearData : function(){
		$("#contract_keyword_search").val('');
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
		   url:basePath+"/aerialmaterial/contract/queryPageInModal",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.data = data.rows;
				   contractModal.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : 'contract_pagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				signByKeyword($.trim($("#contract_keyword_search").val()),[2,3,5,6],"#contractlist tr td");
			   } else {
				  $("#contractlist").empty();
				  $("#contract_pagination").empty();
				  $("#contractlist").append("<tr><td colspan=\"10\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#contract_keyword_search").val());
		 if(this.param.type){
			 searchParam.htlx = this.param.type;
		 }
		 searchParam.dprtcode = this.param.dprtcode;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var contractJjcdMap = {
				1 : "普通",
				2 : "紧急",
				3 : "AOG"
		};
		var contractDhztMap = {
				1 : "未到货",
				2 : "部分到货",
				3 : "全部到货"
		};
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='contractModal.checkContract(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='contractModal.checkContract(this)'>";
		   }
		   if(contractModal.param.selected && contractModal.param.selected == row.id){
			   checked = "checked";
		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.htlsh)+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.hth)+"</td>";  
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(contractJjcdMap[row.jjcd])+"</td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gysbm)+"</td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gysmc)+"</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(row.htqdrq||"").substr(0, 10)+"</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(contractDhztMap[row.dhzt])+"</td>";
		   htmlContent += + "</tr>";  
		    
	   });
	   $("#contractlist", $("#contractModal")).empty();
	   $("#contractlist", $("#contractModal")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkContract: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	setContract: function(){
		var $checkedRadio = $("#contractlist", $("#contractModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0){
				this.param.callback(this.data[index]);
			}else{
				this.param.callback({});
			}
		}
		$("#contractModal").modal("hide");
	},
	clearUser:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
		$("#contractModal").modal("hide");
	}
}