annual_plan_alert_Update = {
	id : "annual_plan_alert_Modal",
	module : "annual_plan_module",
	init : "annual_plan",
	initData : "annual_plan_alert_Add",
	isValid : true,//验证是否通过,isValid:true表示验	证通过,isValid:false表示验证未通过
	open: function(param){
		var this_ = this;
		if(annual_plan.zt == 3 && annual_plan.bbh == annual_plan.maxbbh){
			var message = "因为该年度计划已经通过审批，所以您的此次操作系统会自动对该年度计划进行升级版本，升级版本后不会对您之前的数据造成影响，您是否继续？";
			AlertUtil.showConfirmMessage(message, {callback: function(){
				
				this_.initOpen(param);
				
			}});
		}else{
			this_.initOpen(param);
		}
	},
	initOpen : function(param){
		var this_ = this;
		AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		$("#modalName1").html("修改年度审核计划");
		$("#modalEname1").html("Update Annual Audit Plan");
		annual_plan_alert_Add.EmptiedData();   //清空数据
		this_.initInfo(param);					//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(param){
		var this_=this;
		$("#"+this_.id+"_nd").val($("#year_search").val());
		this.initDate(param);//加载缺陷保留数据
	},
	/**
	 * 加载缺陷保留数据
	 */
	initDate : function(param){
		var falg=true;
		var this_=this;
		var obj = {};
		obj.id = param;
		obj.dprtcode = $("#dprtcode").val();
		//根据单据id查询信息
		startWait($("#"+this_.id));
	   	AjaxUtil.ajax({
	 		url:basePath + "/quality/annualplan/getById",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#"+this_.id),
	 		success:function(data){
	 			finishWait($("#"+this_.id));
	 			if(data!=null){
	 				if($("#oldzt").val()==1){
	 					AlertUtil.showMessage("该数据已更新，请刷新后再进行操作!");
	 					falg=false;
	 					return false;
	 				}
	 			  	if(falg){
	 			   		$("#"+this_.id+"").modal("show");//显示窗口
	 			    	annual_plan_alert_Add.validation(); //初始化验证
	 			   	}
	 				this_.setDate(data);// 加载缺陷保留数据
				};
	 		}
		});
	},
	/**
	 * 加载缺陷保留数据
	 */
	setDate : function(data){
		var this_=this;

		$("#"+this_.id+"_yf").val(data.yf);			//月份
		$("input:radio[name='"+this_.id+"_lx'][value='"+data.lx+"']").attr("checked",true); //来源类型			
		annual_plan_module.changeType();
		$("#"+this_.module+"_shdxid").val(data.shdxid);	//审核对象id
		$("#"+this_.module+"_shdxbh").val(data.shdxbh);	//审核对象编号
		$("#"+this_.module+"_shdxmc").val(data.shdxmc);	//审核对象名称
		var shdx = StringUtil.escapeStr(data.shdxbh) + " " + StringUtil.escapeStr(data.shdxmc);
		$("#"+this_.module+"_shdx").val(shdx);	//审核对象
		if(data.lx == 2){
			$("#"+this_.module+"_shdx").val(data.shdxmc);	//审核对象
		}
		var dataList = StringUtil.escapeStr(data.paramsMap?data.paramsMap.shcy:'').split(",");// 在每个逗号(,)处进行分解。
		var zrr="";
		var zrrid="";
		for (var i = 0; i < dataList.length; i++) {
		var ry=dataList[i].split("#_#");
			zrr=ry[2]+" "+ry[3]+","+zrr;
			zrrid=ry[1]+","+zrrid;
		}
		zrr=zrr.substr(0,zrr.length-1);
		zrrid=zrrid.substr(0,zrrid.length-1);
		$("#"+this_.module+"_shzrrid").val(zrrid);		//责任审核人id
		$("#"+this_.module+"_shzrr").val(zrr);			//责任审核人
		$("#"+this_.id+"_bz").val(data.bz);			//备注
		$("#"+this_.id+"_id").val(data.id);				//id
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_plan_edit');
		attachmentsObj.show({
			djid:data.id,
			fileHead : true,
			colOptionhead : true,
			fileType:"plan"
		});//显示附件
		
		$("#left_column", $("#attachments_list_plan_edit")).removeClass("col-lg-1").addClass("col-lg-2");
		$("#right_column", $("#attachments_list_plan_edit")).removeClass("col-lg-11").addClass("col-lg-10");
	}
};


