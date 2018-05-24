
/**
 * 系统同步关系
 */
var synrel_main = {
	id:'synrel_main',
	data:[],
	dic:{
		gxlx:['','外委供应商','客户']
	},
	//初始化参数
	initParam: function(){
		$("#"+this.id+"_keyword").val('');
	},
	//加载数据
	load : 	function(pageNumber, sortColumn, orderType) {
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		if(typeof(sortColumn) == "undefined"){
			sortColumn = "auto";
		} 
		if(typeof(orderType) == "undefined"){
			orderType = "desc";
		} 
		
		var obj ={};
		obj.pagination = {page:pageNumber, sort:sortColumn, order:orderType, rows:10};
		$.extend(obj, this.getParams());
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url: basePath+"/sys/synrel/page",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success:function(data){
				
				if(data.total >0){
					this_.data = data.rows;
					this_.appendContentHtml(data.rows);
					new Pagination({
						renderTo : this_.id+"_pagination",
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					}); 
					// 标记关键字
					signByKeyword($("#"+this_.id+"_keyword").val(),[3], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
				}
				 
				finishWait();
		    }
		}); 
	},	
	getParams : function() {
		 var searchParam = {};
		 var paramsMap = {};
		 var keyword = $.trim($("#"+this.id+"_keyword").val());
		 var gxlx  = $.trim($("#"+this.id+"_gxlx").val());  
		 var dprtcode  = $.trim($("#"+this.id+"_dprtCode").val());  
		 
		 
		 if('' != keyword){
			 paramsMap.keyword = keyword;
		 }
		 
	     if('' != gxlx){
			 searchParam.gxlx = gxlx;
		 }
	      
	     if('' != dprtcode){
			 searchParam.dprtcode = dprtcode;
		 }
		 
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list) {
		var htmlContent = '';
		var this_ = this;
		var gxlx = this_.dic.gxlx;
		$.each(list,function(index,row){
			var gljg = row.gljg||{};
			var jg = row.jg||{};
			
			if (index % 2 == 0) {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='"+this_.id+".rowonclick(event);' >";
			} else {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='"+this_.id+".rowonclick(event);' >";
			}
		   
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"; 
			htmlContent += "<input type='checkbox' name='"+this_.id+"_list_radio' value='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','"+this_.id+"_list')\" >";
			
			htmlContent += "&nbsp;&nbsp;<i class=\"icon-edit color-blue cursor-pointer  checkPermission\" permissioncode=\"sys:synrel:edit\" onclick=\"javascript:synrel_main.popEdit('"+row.id+"');\" title=\"修改 Edit\"></i>";
			
			
			
			htmlContent += "</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(gxlx[row.gxlx])+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(gxlx[row.gxlx])+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.objText)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.objText)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(gljg.dprtname)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(gljg.dprtname)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(jg.dprtname)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(jg.dprtname)+"</td>";  
		   
		   	htmlContent += "</tr>";
	   });
	   $("#"+this_.id+"_list").html(htmlContent);
	   
	   refreshPermission();
	    
	},
	changeGxlx: function(obj) {
		var $this = $(obj);
		$("input[name^='gxlx']:checked").removeAttr("checked");
		$this.attr("checked","checked");
		this.search();
	},
	//搜索
	search: function() {
		this.load();
	},
	 
	//确认
	rowonclick: function(e){
		SelectUtil.checkRow($(e.target).parent().find("input[type='checkbox']"),'selectAllId',this.id+"_list");
	},
	popAdd: function(){ 
		synrel_form_Modal.popAdd({deptType:deptType});
	},
	 
	popEdit: function(id){ 
		synrel_form_Modal.popEdit({id:id,deptType:deptType});
	},
	 /**批量删除*/
	dels: function(){ 
		var _this = this;
		var ids = [];
		$checkboxs = $('#synrel_main_table tr :checkbox:checked ');
		if($checkboxs.length <= 0){
			AlertUtil.showErrorMessage('请选中需要删除的记录');
			return false;
		}
		
		$.each($checkboxs,function(idx,item){
			ids.push(_this.data[$(item).val()].id)
		})
		
		var obj = {paramsMap:{ids:ids.join()}};
		AjaxUtil.ajax({
			url: basePath+"/sys/synrel/dels",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success:function(data){
				AlertUtil.showAfterMessage('批量删除成功');
				synrel_main.search();
		    }
		}); 
	},
	orderBy: function (obj){
		// 字段排序
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc") >= 0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
			orderType = "asc";
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		this.load(currentPage,obj.replace('-','.'),orderStyle.split("_")[1]);
	},
};

function refreshPage()
{
	synrel_main.search();
}