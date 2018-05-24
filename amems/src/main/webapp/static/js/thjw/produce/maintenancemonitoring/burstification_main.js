
var burstification_main = {
	id:'burstification_main',
	tableDivId : 'burstification_main_top_div',
	tableId : 'burstification_main_table',
	tbodyId : 'burstification_main_tbody',
	paginationId:'burstification_main_Pagination',
	selectTR:null,
	pagination : {},
	param: {
		parentObj : {},
		fjzch : '',
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load(1,"auto","desc");
	},
	//加载数据
	load : function(pageNumber, sortColumn, orderType){
		var this_ = this;
		this_.mainData = [];
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		if(typeof(sortColumn) == "undefined"){
			sortColumn = "auto";
		} 
		if(typeof(orderType) == "undefined"){
			orderType = "desc";
		} 
		var searchParam ={};
		this_.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:10000};
		searchParam.pagination = this_.pagination;
		$.extend(searchParam, this_.getParams());
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/produce/workpackage/queryBurstificationWPList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				var count = 0;
				if(data.total >0){
					count = data.total;
					this_.mainData = data.rows;
					this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : this_.paginationId,
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					}); 
				} else {
					$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
					$("#"+this_.paginationId, $("#"+this_.id)).empty();
					$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"22\" class='text-center'>暂无数据 No data.</td></tr>");
				}
				this_.param.parentObj.setBurstificationCount(count);
	      }
	    }); 
	},	
	getParams : function(){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		var keyword = $.trim($("#keyword_search", $("#"+this.id)).val());
		searchParam.dprtcode = this.param.dprtcode;
		searchParam.dtbs = 1;
		paramsMap.keyword = keyword;
		searchParam.fjzch = this.param.fjzch;
		paramsMap.userId = userId;
		paramsMap.userType = userType;
		paramsMap.ztList = [1,2,7];
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
			
			htmlContent += "<tr>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;' >"
			htmlContent += "	<i class='spacing icon-edit color-blue cursor-pointer checkPermission' permissioncode='produce:maintenance:monitoring:main:04' title='修改 Edit' onClick="+this_.id+".openWinEdit('"+StringUtil.escapeStr(row.id)+"')></i>";
			if(row.zt == 1){
				htmlContent += "	<i class='spacing icon-trash color-blue cursor-pointer checkPermission' permissioncode='produce:maintenance:monitoring:main:05' title='删除 Delete' onClick="+this_.id+".deletePackage('"+StringUtil.escapeStr(row.id)+"')></i>";  
			}
			htmlContent += "	<i class='spacing icon-tumblr-sign color-blue cursor-pointer checkPermission' permissioncode='produce:maintenance:monitoring:main:06' title='提交 Submit'   onClick="+this_.id+".submitPackage('"+StringUtil.escapeStr(row.id)+"')></i>";
			htmlContent += "</td>"
			
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gbbh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkpackage('"+StringUtil.escapeStr(row.id)+"')>"+StringUtil.escapeStr(row.gbbh)+"</a>";
			htmlContent += "</td>";
			
			htmlContent += "<td class='text-left' title='"+formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.zt))+"' >"+ formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.zt))+ "</td>";
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.gbmc) + "'>"+ StringUtil.escapeStr(row.gbmc)+ "</td>";		
			htmlContent += "<td class='text-left'>"+ StringUtil.escapeStr(row.wxlx) + "</td>";
			htmlContent += "<td class='text-center' title='"+ StringUtil.escapeStr(row.jhKsrq==null?"":(row.jhKsrq).substring(0,10))+ "'>" + StringUtil.escapeStr(row.jhKsrq==null?"":(row.jhKsrq).substring(0,10))+ "</td>";
			htmlContent += "<td class='text-center' title='"+ StringUtil.escapeStr(row.jhJsrq==null?"":(row.jhJsrq).substring(0,10))+ "'>" + StringUtil.escapeStr(row.jhJsrq==null?"":(row.jhJsrq).substring(0,10))+ "</td>";
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.xfdwDprtname) + "' >"+StringUtil.escapeStr(row.xfdwDprtname)  + "</td>";
			htmlContent += "<td class='text-center' title='"+ StringUtil.escapeStr(row.zdrq==null?"":(row.zdrq).substring(0,10))+ "'>" + StringUtil.escapeStr(row.zdrq==null?"":(row.zdrq).substring(0,10))+ "</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.jhZxdw)+"' >"+ StringUtil.escapeStr(row.jhZxdw)+ "</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.gzyq)+"' >"+ StringUtil.escapeStr(row.gzyq)+ "</td>";
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+ "'>"+ StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+ "</td>";
			htmlContent += "<td class='text-center'>"+ formatUndefine(row.whsj) + "</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.dprtname)+"' >"+ StringUtil.escapeStr(row.dprtname)+ "</td>";
			htmlContent += "</tr>";
		    
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
		refreshPermission();
	},
	/**
	 * 维修历史
	 */
	ViewHistory : function(id){
		window.open(basePath+"/produce/maintenance/initialization/project/history/" + id);
	},
	/**
	 * 查看工包
	 */
	viewWorkpackage : function(id){
		window.open(basePath+"/produce/workpackage/view?id="+id);
	},
	/**
	 * 编辑工包
	 */
	openWinEdit : function(id){
		var this_ = this;
		this_.getDataById(id,function(obj){
			if(!(obj.zt == 1 || obj.zt == 2 || obj.zt == 7 )){
				AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
				return;
			}
			var view = true;
			var operation = false;
			if(obj.zt == 1){
				view = false;
				operation = true;
			}
			workpackage_modal.init({
				obj :obj,//工包对象
				fjzch :obj.fjzch,
				dprtcode:obj.dprtcode,//组织机构
				workpackageId:obj.id,//工包id
				operation:operation,//操作类型
				view : view,//查看
				modalName:'工包编辑',//模态框中文名称
				modalEname:'Work Package Edit',//模态框英文名称
				callback:function(data){
					if(obj.zt == 1){
						this_.doRequest(data,"修改成功!",basePath + "/produce/workpackage/update","workpackage_modal");
					}else{
						AlertUtil.showMessage("修改成功!");
						$("#workpackage_modal").modal("hide");
						this_.search();
					}
				}
			});
			
			work_package_edit_Modal.show({
				obj :obj,//工包对象
				fjzch :obj.fjzch,
				dprtcode:obj.dprtcode,//组织机构
				gbid:obj.id,//工包id
				parent_win_id : 'workpackage_modal',
				scroll_body : 'workpackage_modal_body',
				callback:function(data){
					
				}
			});
		})
		
	},
	doRequest : function(data,message,url,modal){
		var this_ = this;
		AjaxUtil.ajax({
			url : url,
			type : "post",
			data : JSON.stringify(data),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			async : false,
			success : function(data) {
				AlertUtil.showMessage(message);
				$("#"+modal).modal("hide");
				this_.search();
			}
		});
	},
	/**
	 * 删除预组包
	 */
	deletePackage : function(id){
		var this_ = this;
		AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
			
			 AjaxUtil.ajax({
				 type:"post",
				 url:basePath+"/produce/maintenance/monitoring/deletePackage",
				 dataType:"json",
				 data:{'id':id},
				 success:function(data) {
					AlertUtil.showMessage('删除成功!');
					this_.search();
				 }
			 });
			 
		}});
	},
	/**
	 * 提交预组包
	 */
	submitPackage : function(id){
		var this_ = this;
		AlertUtil.showConfirmMessage("确定要提交吗？", {callback: function(){
			startWait();
			 AjaxUtil.ajax({
				 type:"post",
				 url:basePath+"/produce/maintenance/monitoring/submitPackage",
				 dataType:"json",
				 data:{'id':id},
				 success:function(data) {
					 finishWait();
					 this_.param.parentObj.setCheckCount(data);
					 AlertUtil.showMessage('提交成功!');
					 this_.search();
				 }
			 });
			 
		}});
	},
	/**
	 * 排序
	 */
	orderBy : function(sortColumn, prefix){//字段排序
		var this_ = this;
		var $obj = $("th[name='column_"+sortColumn+"']", $("#"+this_.tableDivId));
		var orderStyle = $obj.attr("class");
		$(".sorting_desc", $("#"+this_.tableDivId)).removeClass("sorting_desc").addClass("sorting");
		$(".sorting_asc", $("#"+this_.tableDivId)).removeClass("sorting_asc").addClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf ("sorting_asc")>=0) {
			$obj.removeClass("sorting").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$obj.removeClass("sorting").addClass("sorting_asc");
			orderType = "asc";
		}
		var currentPage = $("#"+this_.paginationId+" li[class='active']", $("#"+this_.id)).text();
		if(currentPage == ""){currentPage = 1;}
		if(prefix != null && prefix != '' && typeof prefix != undefined){
			sortColumn = prefix+"."+sortColumn;
		}
		this_.load(currentPage, sortColumn, orderType);
	},
	/**
	 * 搜索
	 */
	search : function(){
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load(1,"auto","desc");
	},
	/**
	 * 根据工包id查询工包数据
	 */
	getDataById : function(id,obj){
		var this_ = this;
		var param={};
		param.id=id;
		AjaxUtil.ajax({
			url : basePath + "/produce/workpackage/getRecord",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			async : false,
			success : function(data) {
				if(data != null){
					if(typeof(obj)=="function"){
						obj(data); 
					}
				}			
			}
		});
	}
}	