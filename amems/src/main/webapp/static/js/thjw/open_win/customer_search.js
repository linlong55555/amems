CustomerModal = {
	paginationId:'CustomerPagination',
	id:'CustomerModal',
	viewCustomerId:'um_selectCustomer',
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
		
		$("#CustomerModal").modal("show");
		$("#keyword_Customer_search").val("");
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
		   url:basePath+"/baseinfo/customer/selectByDprtcode",
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
						renderTo : "CustomerPagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						extParams:{},
					    goPage: function(a,b,c){
					    	this_.AjaxGetDatasWithSearch(a,b,c);
						}
					}); 
				   // 标记关键字
				   signByKeyword($("#keyword_Customer_search").val(),[2,3],"#CustomerList tr td");
			   } else {
				  $("#CustomerList").empty();
				  $("#CustomerPagination").empty();
				  $("#CustomerList").append("<tr><td colspan=\"9\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_Customer_search").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
			if(CustomerModal.param.selected && CustomerModal.param.selected == row.id){
				checked = "checked";
			}
			htmlContent += "<tr onclick='CustomerModal.checkRow(this)'>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.khbm)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.khbm)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.khmc)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.khmc)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.cs)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.cs)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.GJ)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gj)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.LXDH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.lxdh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.BZ)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.YB)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.yb)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.khjc)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.khjc)+"</td>";
			htmlContent += "</tr>";  
		    
		});
		$("#CustomerList").empty();
		$("#CustomerList").html(htmlContent);
	},
	search: function(){
		var this_ = this;
		this_.goPage(1,"auto","desc");
	},
	checkRow: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	setData: function(){
		var $checkedRadio = $("#CustomerList", $("#CustomerModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0 && index != undefined){			
				var customer = this.data[index];
				this.param.callback(customer);
			}else{
				
			}
		}
		$("#"+this.id).modal("hide");
	}
}
