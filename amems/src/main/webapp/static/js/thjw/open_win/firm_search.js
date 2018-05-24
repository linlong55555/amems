ManufacturerModal = {
	paginationId:'ManufacturerPagination',
	id:'ManufacturerModal',
	viewManufacturerId:'um_selectManufacturer',
	param: {
		selected:null,
		callback:function(){},
		dprtcode:userJgdm,//默认登录人当前机构代码
		id:null,
		parentid:null,//第一层modal
	},
	data:[],
	show : function(param){
		var this_ = this;
		
		$("#ManufacturerModal").modal("show");
		$("#keyword_Manufacturer_search").val("");
		if(param){
			$.extend(this.param, param);
		}
		this_.goPage(1,"auto","desc");
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
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
		   url:basePath+"/aerialmaterial/outsourcingfirm/queryList4Pop",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total != null &&  data.total > 0){
				   this_.data = data.rows;
				   this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : "ManufacturerPagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						extParams:{},
					    goPage: function(a,b,c){
					    	this_.AjaxGetDatasWithSearch(a,b,c);
						}
					}); 
				   // 标记关键字
				   signByKeyword($("#keyword_Manufacturer_search").val(),[2,3],"#ManufacturerList tr td");
			   } else {
				  $("#ManufacturerList").empty();
				  $("#ManufacturerPagination").empty();
				  $("#ManufacturerList").append("<tr><td colspan=\"3\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_Manufacturer_search").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 searchParam.gyslb = 2;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
			if(ManufacturerModal.param.selected && ManufacturerModal.param.selected == row.id){
				checked = "checked";
			}
			htmlContent += "<tr onclick='ManufacturerModal.checkRow(this)'>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gysbm)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.gysbm)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gysmc)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gysmc)+"</td>";  
			htmlContent += "</tr>";  
		    
		});
		$("#ManufacturerList").empty();
		$("#ManufacturerList").html(htmlContent);
	},
	search: function(){
		var this_ = this;
		this_.goPage(1,"auto","desc");
	},
	checkRow: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	setData: function(){
		var $checkedRadio = $("#ManufacturerList", $("#ManufacturerModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0 && index != undefined){			
				var manufacturer = this.data[index];
				this.param.callback(manufacturer);
			}else{
				
			}
		}
		$("#"+this.id).modal("hide");
	}
}
