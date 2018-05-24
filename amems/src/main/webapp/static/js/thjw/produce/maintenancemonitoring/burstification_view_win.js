
burstification_view_Modal = {
	id : 'burstification_view_Modal',
	pagination : {},
	param: {
		parentObj : {},
		fjzch : '',
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	getHistoryBbList : function(parentObj, fjzch, dprtcode){
		var this_ = this;
		this_.param.fjzch = fjzch;
		this_.param.dprtcode = dprtcode;
		this_.param.parentObj = parentObj;
		var searchParam ={};
		this_.pagination = {page:1,sort:"auto",order:"desc",rows:10000};
		searchParam.pagination = this_.pagination;
		$.extend(searchParam, this_.getParams());
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/produce/workpackage/getWorkpackageList",
			type: "post",
			async : false,
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				$("#existsPackage", $("#"+this_.id)).empty();
				$("#existsPackage_li", $("#"+this_.id)).hide();
				finishWait();
				if(data.total >0){
					$("#existsPackage_li", $("#"+this_.id)).show();
					this_.appendContentHtml(data.rows);
				}
	      }
	    }); 
		return $("#"+this_.id).html();
	},
	getParams : function(){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		searchParam.dprtcode = this.param.dprtcode;
//		searchParam.dtbs = 1;
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
			htmlContent += "<li><a href='javascript:void(0);' onclick="+this_.id+".openWinView('"+row.id+"','"+row.dprtcode+"') >"+StringUtil.escapeStr(row.gbbh)+"</a></li>";
		});
		$("#existsPackage", $("#"+this_.id)).html(htmlContent);
	},
	/**
	 * 创建新工包
	 */
	openWinAdd : function(){
		var this_ = this;
		AlertUtil.hideModalFooterMessage();
		console.info(22222);
		workpackage_modal.init({
			obj :{},//工包对象
			fjzch : this_.param.fjzch,
			dprtcode:this_.param.dprtcode,//组织机构
			workpackageId:'',//工包id
			operation:true,//操作类型
			mark : false,//飞机注册号不可编辑
			modalName:'新增工包',//模态框中文名称
			modalEname:'Add Workpackage',//模态框英文名称
			callback:function(data){
			
				this_.param.parentObj.addWorkPackage(data, "workpackage_modal");
			}
		});
		work_package_edit_Modal.hide();
	},
	/**
	 * 添加到现有工包
	 */
	openWinView : function(id, dprtcode){
		var this_ = this;
		this_.getDataById(id,function(obj){
			if(!(obj.zt == 1 || obj.zt == 2 || obj.zt == 7 )){
				AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
				return;
			}
			workpackage_modal.init({
				obj :obj,//工包对象
				fjzch :obj.fjzch,
				dprtcode:obj.dprtcode,//组织机构
				workpackageId:obj.id,//工包id
				operation:false,//操作类型
				view : true,//查看
				modalName:'在现有工包下添加工单',//模态框中文名称
				modalEname:'Add',//模态框英文名称
				callback:function(data){
					this_.param.parentObj.add2WorkPackage(data, "workpackage_modal");
				}
			});
		});
		work_package_edit_Modal.hide();
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