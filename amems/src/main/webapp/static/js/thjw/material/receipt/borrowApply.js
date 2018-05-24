borrowApplyModal = {
	param: {
		selected:null,
		clearUser: true,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		$("#borrowApplyModal").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		if(param.clearUser==false){
			$("#borrowApplyModal_btn_clear").hide();
		}
		this.clearData();
		this.goPage(1,"auto","desc");
	},
	data:[],
	clearData : function(){
		$("#borrowApply_keyword_search").val('');
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
		   url:basePath+"/aerialmaterial/borrow/list/toInstock",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.data = data.rows;
				   borrowApplyModal.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : 'borrowApply_pagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				signByKeyword($.trim($("#borrowApply_keyword_search").val()),[2],"#borrowApplyList tr td");
			   } else {
				  $("#borrowApplyList").empty();
				  $("#borrowApply_pagination").empty();
				  $("#borrowApplyList").append("<tr><td colspan=\"10\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.paramsMap = {
				 keyword:$.trim($("#borrowApply_keyword_search").val()),
				 dprtcode:this.param.dprtcode,
				 jddxlx:$.trim($("#jddxlx").val()),
				 jddxid:$.trim($("#jddx").val())
				 
		 };
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
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='borrowApplyModal.checkContract(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='borrowApplyModal.checkContract(this)'>";
		   }
		   if(borrowApplyModal.param.selected && borrowApplyModal.param.selected == row.id){
			   checked = "checked";
		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.sqdh)+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.sqrUser.displayName)+"</td>";  
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(row.sqsj||"").substr(0, 10)+"</td>";
		   if(row.fjzch == "00000"){
			   htmlContent += "<td style='text-align:left;vertical-align:middle;'>通用Currency</td>";
		   }else{
			   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.fjzch)+"</td>";
		   }
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.secondment?row.secondment.jddxms:'')+"</td>";
		   htmlContent += + "</tr>";  
		    
	   });
	   $("#borrowApplyList", $("#borrowApplyModal")).empty();
	   $("#borrowApplyList", $("#borrowApplyModal")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkContract: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	setContract: function(){
		var $checkedRadio = $("#borrowApplyList", $("#borrowApplyModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0){
				this.param.callback(this.data[index]);
			}else{
				this.param.callback({});
			}
		}
		$("#borrowApplyModal").modal("hide");
	},
	clearUser:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
		$("#borrowApplyModal").modal("hide");
	}
}