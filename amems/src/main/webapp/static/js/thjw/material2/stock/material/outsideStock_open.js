var certificateUtil = {};
$(function(){
	$('.date-picker').datepicker({
		autoclose : true,
		clearBtn : true
	});
	
	 //收缩效果
//	$('#borrow_info').on('show.bs.collapse', function () {
//		$('#borrow_info').siblings("div").find("input.selectCheckbox").prop("checked",true);
//	})
//	$('#borrow_info').on('hide.bs.collapse', function () {
//		$('#borrow_info').siblings("div").find("input.selectCheckbox").prop("checked",false);
//	})
	
	$('#certificate_info').on('shown.bs.collapse', function () {
		
		$('#certificate_info').siblings("div").find("input.selectCheckbox").prop("checked",true);
	});
	$('#certificate_info').on('hidden.bs.collapse', function () {
		$('#certificate_info').siblings("div").find("input.selectCheckbox").prop("checked",false);
	});
	
	
	$('#resume_info').on('shown.bs.collapse', function () {
		$('#resume_info').siblings("div").find("input.selectCheckbox").prop("checked",true);
	});
	$('#resume_info').on('hidden.bs.collapse', function () {
		$('#resume_info').siblings("div").find("input.selectCheckbox").prop("checked",false);
	});
	     
		//添加事件
		bindEvent();
	    AlertUtil.hideModalFooterMessage();
		//初始化管理级别,物料类别,来源基础数据
		initBasicDataOpen();
		//初始化当前用户有权限的仓库
		initStoreOpen();
		//初始化币种
		var hbHtml = initBizOpen();
		$("#biz_view").empty();
		$("#biz_view").append(hbHtml);
		$("#jzbz_view").empty();
		$("#jzbz_view").append(hbHtml);
		// 初始化证书
		initCertificate();
	
})

/**
 * 添加事件
 */
function bindEvent(){
	$(".fxyz").keyup(function(){
		validateTime(this);
	});
	$(".fxzsyz").keyup(function(){
		validateCycle (this);
	});
	$(".fxzsyz2").keyup(function(){
		validateDecimal(this);
	});
	
}

/**
 * 验证小数
 */
function validateDecimal(obj){
	var value = $(obj).val();
	while(!(/^(0|[1-9]\d*)(\.[0-9]?[0-9]?)?$/.test(value)) && value.length > 0){
		value = value.substr(0, value.length-1);
    }
	$(obj).val(value);
}

/**
 * 验证整数
 */
function validateCycle (obj){
	var value = $(obj).val();
	while(!(/^(0|[1-9]\d*)$/.test(value)) && value.length > 0){
		value = value.substr(0, value.length-1);
    }
	$(obj).val(value);
}

/**
 * 验证时间
 */
function validateTime (obj){
	var value = $(obj).val();
	while(!(/^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/.test(value)) && value.length > 0){
		value = value.substr(0, value.length-1);
    }
	$(obj).val(value);
}
function getCount(separtor,str){
	var count=0;
	for(var i=0;i<str.length;i++){
		if(separtor==str[i])count++;
		
	}
	return count;
}
function backspace(obj, reg, replace){
	var value = obj.val();
	var count = getCount(".", value);
	if (replace) {
		value = value.replace(/：/g, ":");
	}
	if (count != 1) {
		while (!(reg.test(value)) && value.length > 0) {
			value = value.substr(0, value.length - 1);
		}

	}

	
	obj.val(value);
}
function initBasicDataOpen(){
	var gllbOptions=getOptionsOpen(DicAndEnumUtil.getEnum("supervisoryLevelEnum"));
	var wllbOptions=getOptionsOpen(DicAndEnumUtil.getEnum("materialTypeEnum"));
//	var lyOptions=getOptionsOpen(DicAndEnumUtil.getEnum("materialSrouceEnum"));
	$("#gljb_view").empty();
	$("#wllb_view").empty();
//	$("#hcly_view").empty();
	$("#gljb_view").append(gllbOptions);
	$("#wllb_view").append(wllbOptions);
//	$("#hcly_view").append(lyOptions);
	inithclyone();
}
function inithclyone(){
	$("#hcly_view").empty();
	DicAndEnumUtil.registerDic('85',"hcly_view",$("#dprtcode_search").val());
}
function initStoreOpen(){
	var htmlOption="";
	for(var i=0;i<userStoreList.length;i++){
		var dprtcode=currentUser.jgdm;
		if(userStoreList[i].dprtcode==dprtcode){
			htmlOption+="<option value='"+userStoreList[i].id+"'>"+userStoreList[i].ckh+" "+userStoreList[i].ckmc+"</option>";	
		}
	}	
	$("#ck_view").empty();
	$("#ck_view").append(htmlOption);
}
function initBizOpen(){
	  var dicts=DicAndEnumUtil.getDict(3,$("#dprtcode_search").val());
		var htmlOption="";
		for(var i=0;i<dicts.length;i++){
		 htmlOption+="<option value='"+dicts[i].id+"'>"+dicts[i].name+"</option>";	
			
		}
		return htmlOption;
	
}

function getOptionsOpen(ars){
	var html="";
	if(ars&&ars instanceof Array){
		 for(var i=0;i<ars.length;i++){
			 html+="<option value='"+ars[i].id+"'>"+ars[i].name+"</option>";
		 }	
	}else{
		html="";
	}
	  return html
}
var inside_Modal={
		
		modalId:'inside_open_alert',
		
		param:{
			id:null,            //库存id
			dprtcode:userJgdm,  //默认登录人当前机构代码
			callback:null       //保存成功后的回调函数
		},
		
		data:{},
		
		show:function(param){
			var this_=this;
			if(param){
				$.extend(this.param,param);
			}
						
			 this.clearData(); //清空弹框数据
			 
			 this.initData();
			 
			
			 
			 $("#"+this_.modalId).modal("show");
			 $(".modal").find("table").parent().removeAttr("style");
		},
		clearData:function(){
			//隐藏出现异常的感叹号
			AlertUtil.hideModalFooterMessage();
			$("input[id$='view'],select[id$='view'],textarea[id$='view']").val("");
		},
		
		initData:function(){
			var this_=this;
			var kcid=this_.param['id'];
			startWait();
			AjaxUtil.ajax({
				url: basePath +"/aerialmaterial/outfieldstock/selectKcxqById",
				type:"post",
				async: false,
				data:{
					 'id' : kcid
				},
				dataType:"json",
				success:function(data){
					finishWait();
					this_.data=data;
					this_.setData();
					refreshPermission();
				}
			})
			
		},
		setData:function(){
			var this_=this;
			var obj=this_.data['outFieldStock']; //库存数据
			var componentCertificateList=this_.data['componentCertificateList']; //证书
			var stockHistoryList=this_.data['stockHistoryList']; //库存履历
			var toolBorrowRecordList=this_.data['toolBorrowRecordList']; //工具借用归还履历
			var certificate = {
					bjh:"-",
					xlh:"-",
					pch:"-",
					zt:1,
					dprtcode:userJgdm
			};
		    if(obj){
		    	$("#bjh_view").val(obj.bjh || '');
		    	$("#ckmc_view").val((obj.ywms || '') +" "+ (obj.zwms || ''));
		    	$("#pch_view").val(obj.pch || '');
		    	$("#sn_view").val(obj.sn || '');
		    	$("#ck_view").val('外场');
		    	$("#kw_view").val('外场');
		    	$("#kcsldw_view").val((obj.kcsl || '')+(obj.jldw||''));
		    	$("#djsl_view").val((obj.djsl || 0)+(obj.jldw||''));  
		    	$("#kccb_view").val(obj.kccb || '');		    	
		    	if(obj.biz){
		    		$("#biz_view").val(obj.biz);	
		    	}else{
		    		$("#biz_view option:eq(0)").attr("selected","selected");
		    	}		    	
		    	$("#jz_view").val(obj.jz || '');
		    	if(obj.jzbz){
		    		$("#jzbz_view").val(obj.jzbz);	
		    	}else{
		    		$("#jzbz_view option:eq(0)").attr("selected","selected");
		    	}		    	
		    	$("#cqbh_view").val(obj.cqbh || '');
		    	$("#cqbh_view").val(obj.paramsMap?obj.paramsMap.cqbh:'');//物料类别
				$("#cqid_view").val(obj.paramsMap?obj.paramsMap.cqid:"");//cqid
		    	$("#hjsm_view").val(obj.hjsm?obj.hjsm.substring(0,10):'');
		    	$("#hcly_view").val(obj.hcly);//来源
		    	
		    	$("#grn_view").val(obj.grn || '');
		    	$("#tsn_view").val(obj.tsn?TimeUtil.convertToHour(obj.tsn, TimeUtil.Separator.COLON):'');
		    	$("#tso_view").val(obj.tso?TimeUtil.convertToHour(obj.tso, TimeUtil.Separator.COLON):'');
		    	$("#csn_view").val(obj.csn || '');
		    	$("#cso_view").val(obj.cso || '');
		    	$("#scrq_view").val(obj.scrq?obj.scrq.substring(0,10):'');
		    	
		    	$("#zt_view").val(DicAndEnumUtil.getEnumName('stockStatusEnum',obj.zt));//状态
		    	$("#cfyq_view").val(obj.cfyq || '');
		    	$("#bz_view").val(obj.bz || '');
		    	$("#qczt_view").val(obj.qczt || '');
		    	if($("#isTool").val()=='false'){//航材物料类别
					$("#wllb_view").val(obj.paramsMap?DicAndEnumUtil.getEnumName("materialTypeEnum",obj.paramsMap.hclx):"");					
				}else{//工具物料类别
					$("#wllb_view").val(obj.paramsMap?DicAndEnumUtil.getEnumName("materialToolSecondTypeEnum",obj.paramsMap.hclx_ej):"");
				}	
		    	//$("#wllb_view").val(obj.paramsMap?DicAndEnumUtil.getEnumName('materialTypeEnum',obj.paramsMap.hclx):'');//物料类别
		    	
		    	$("#cjjh_view").val(obj.paramsMap?obj.paramsMap.cjjh : '');
		    	$("#zzcs_view").val(obj.zzcs || '');
		    	$("#gg_view").val(obj.gg || '');
		    	$("#xh_view").val(obj.xh || '');
		    	$("#gljb_view").val(DicAndEnumUtil.getEnumName('supervisoryLevelEnum',obj.paramsMap?obj.paramsMap.gljb:''));//管理级别
		    	$("#maxkcs_view").val(obj.paramsMap?obj.paramsMap.max_kcl:'');
		    	$("#minkcs_view").val(obj.paramsMap?obj.paramsMap.min_kcl:'');		    	
		    	$("#sjr_view").val(obj.sjr?obj.sjr.displayName:'');
		    	$("#sjsj_view").val(obj.rksj || '');
		    	$("#update_person_view").val(obj.xgr?obj.xgr.displayName:'');
		    	$("#update_view").val(obj.whsj || '');
		    	$("#dprtcode_view").val(obj.dprtcode || '');		    	
		    	certificate.bjh = obj.bjh || '-';
		    	certificate.xlh = obj.sn || '-';
		    	certificate.pch = obj.pch || '-';
		    	if(obj.sn){
		    		certificate.pch = '-';	
		    	}
		    	certificate.zt = 1;
		    	certificate.dprtcode = obj.dprtcode;
		    	
		    }
		    //拼接证书
		    loadCertificate(certificate);
		   /* if(componentCertificateList != null && componentCertificateList.length > 0){
		    	this_.appendZs(componentCertificateList);
		    }else{
		    	$("#store_inner_certificate_list").empty();
				$("#store_inner_certificate_list").append('<tr class="noData"><td class="text-center" colspan="6">暂无数据 No data.</td></tr>');
		    }*/
		    
			//拼接库存履历
		    if(stockHistoryList != null && stockHistoryList.length > 0){
		    	this_.appendKcll(stockHistoryList);
		    }else{
		    	$("#hisTbody").empty();
				$("#hisTbody").html("<tr><td class='text-center' colspan='3'>暂无数据 No data.</td></tr>");
		    }
		    //拼接借用归还
		   this_.appendJygh(toolBorrowRecordList,obj.paramsMap?obj.paramsMap.hclx:'');
		},
		 //拼接证书
		appendZs : function(list){
			var this_ = this;
			var htmlappend = "";
			$.each(list,function(index,row){
				htmlappend += "<tr>";
				htmlappend += "<td><style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.zjlx)+"</td>";
				htmlappend += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.zsbh)+"</td>";
				htmlappend += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.zscfwz)+"</td>";
				htmlappend += "<td style='text-align:center;vertical-align:middle;' >"+row.qsrq?row.qsrq.substr(0, 10):''+"</td>";
				//附件
				if(row.paramsMap != null && row.paramsMap.attachcount != null && row.paramsMap.attachcount > 0){
					htmlappend += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
					htmlappend += '<i fjid="'+row.id+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
					htmlappend += "</td>";
				}else{
					htmlappend += "<td style='text-align:center;vertical-align:middle;' ></td>";
				}
				htmlappend += "</td>";
				htmlappend += "</tr>";	
				this_.initWebuiPopover();
			});
			$("#store_inner_certificate_list").empty();
			$("#store_inner_certificate_list").append(htmlappend);
		},
		//附件列表
		 initWebuiPopover : function(){//初始化
			 var this_ = this;
			WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
				return this_.getHistoryAttachmentList(obj);
			});
			$("#certificate_info").scroll(function(){
				$('.attachment-view').webuiPopover('hide');
			});
		},
		 getHistoryAttachmentList : function(obj){//获取历史附件列表
			var jsonData = [
		         {mainid : $(obj).attr('fjid'), type : '附件'}
		    ];
			return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
		},
		//拼接库存履历
		appendKcll : function(list){
			var htmlappend="";
			$.each(list,function(index,row){
				htmlappend += "<tr>";
				htmlappend += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.czsj)+"</td>";
				htmlappend += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.zdr.username)+" "+StringUtil.escapeStr(row.zdr.realname)+"</td>";
				htmlappend += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.czsm)+"</td>";
				htmlappend += "</tr>";	
			});
			$("#hisTbody").empty();
			$("#hisTbody").html(htmlappend);
		},
		//加载借用归还
		appendJygh : function(toolBorrowRecordList,hclx){
			//如果物料类型是工具,展示借用/归还履历
			 if(hclx == 2){
				 $("#borrowInfo").show();
				 if(toolBorrowRecordList != null && toolBorrowRecordList.length > 0){
					 var htmlBorrowHistory="";
					$.each(list,function(index,row){
						htmlBorrowHistory += "<tr>";
						htmlBorrowHistory += "<td style='text-align:right;vertical-align:middle;'>"+row.jhlx=='10'?'借用':'归还'+"</td>";
						htmlBorrowHistory += "<td style='text-align:right;vertical-align:middle;'>"+row.jy_sj?row.jy_sj.substring(0,10):''+"</td>";
						htmlBorrowHistory += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.jy_zrrmc)+"</td>";
						htmlBorrowHistory += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.jy_sl)+"</td>";
						htmlBorrowHistory += "<td style='text-align:right;vertical-align:middle;'>"+row.cqjybs=='0'?'否':'是'+"</td>";
						htmlBorrowHistory += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.jy_bz)+"</td>";
						htmlBorrowHistory += "</tr>";
					});
					$("#borrowTbody").empty();
					$("#borrowTbody").html(htmlBorrowHistory);
				 }else{
					$("#borrowTbody").empty();
					$("#borrowTbody").html("<tr><td class='text-center' colspan='7'>暂无数据 No data.</td></tr>");
				 }
				
			 }else{
				 $("#borrowInfo").hide();		 
			 }
		},
		initWebuiPopover:function (){
			var this_=this;
			WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
				return this_.getHistoryAttachmentList(obj);
			});					
		},
		getHistoryAttachmentList:function(){
			var jsonData = [
				   	         {mainid : $(obj).attr('qtid'), type : '证书附件'}
				   	    ];
				return getHistoryAttachmentList(jsonData);			
		},
		removeCertificate:function(obj){
			$(obj).parent().parent().remove();
			if($("#store_inner_certificate_list").find("tr").size()==0){
				$("#store_inner_certificate_list").append('<tr class="noData"><td class="text-center" colspan="6">暂无数据 No data.</td></tr>');
			}
		},
		certificateOpen:function(obj){
			var this_=this;
			var insideCertificate=new Inside_Certficate({
				id:$(obj).attr("id"),  
				jh:$(obj).attr("jh"),
				xlh:$(obj).attr("xlh"),
				pch:$(obj).attr("pch"),
				zjlx:$(obj).attr("zjlx"),
				zsbh:$(obj).attr("zsbh"),
				zscfwz:$(obj).attr("zscfwz"),
				qsrq:$(obj).attr("qsrq"),
				dprtcode:$(obj).attr("dprtcode"),
				listid:'store_inner_certificate_list',
				callback:function(data){//点击保存执行的回调方法
					if(data.length!=0){
						this_.certificateAppend(data);						
					}
									
				}
			});
			
			$("#inside_certificate_modal").modal("show");			
		},
		certificateAppend:function(list){
			var this_ = this;
			var htmlappend = "";
			$.each(list,function(index,row){
				htmlappend += "<tr insert='1' zjlx='"+row.zjlx+"' zsbh='"+row.zsbh+"' zscfwz='"+row.zscfwz+"' qsrq='"+row.qsrq+"' >";
				htmlappend += "<td><style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.zjlx)+"</td>";
				htmlappend += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.zsbh)+"</td>";
				htmlappend += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.zscfwz)+"</td>";
				htmlappend += "<td style='text-align:center;vertical-align:middle;' >"+row.qsrq?row.qsrq.substr(0, 10):''+"</td>";
				//附件
				if(row.paramsMap != null && row.paramsMap.attachcount != null && row.paramsMap.attachcount > 0){
					htmlappend += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
					htmlappend += '<i fjid="'+row.id+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
					htmlappend += "</td>";
				}else{
					htmlappend += "<td style='text-align:center;vertical-align:middle;' ></td>";
				}
				htmlappend += "</td>";
				htmlappend += "</tr>";	
			});
			$("#store_inner_certificate_list").append(htmlappend);
		},
		getParamsData:function(){
			var this_ = this;
			var data=this.data;
			var objData = {
					message:'',
					fl:true
			};
			var id = data.outFieldStock?data.outFieldStock.id:'';
			if(!id){
				this_.closeModal();
				objData.message = "数据不存在，请刷新后再进行操作！";
				objData.fl = false;
			}
			var obj ={};
			if($("#cso_view").val() != "" && !(/^(0|[1-9]\d*)$/.test($("#cso_view").val()))){
				objData.message ="CSO只能输入整数";
				objData.fl = false;
			}
			
			if($("#csn_view").val() != "" && !(/^(0|[1-9]\d*)$/.test($("#csn_view").val()))){
				objData.message ="CSN只能输入整数";
				objData.fl = false;
			}
			
			if($("#tso_view").val() != "" && !(/^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/.test($("#tso_view").val()))){
				objData.message ="TSO只能输入整数和冒号";
				objData.fl = false;
			}
			
			if($("#tsn_view").val() != "" && !(/^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/.test($("#tsn_view").val()))){
				objData.message ="TSN只能输入整数和冒号";
				objData.fl = false;
			}
			
//			if($("#jz_view").val() != "" && !(/^(0|[1-9]\d*)(\.[0-9]?[0-9]?)?$/.test($("#jz_view").val()))){
//				objData.message ="库存价值只能输入整数和小数";
//				objData.fl = false;
//			}
			
			if($("#kccb_view").val() != "" && !(/^(0|[1-9]\d*)(\.[0-9]?[0-9]?)?$/.test($("#kccb_view").val()))){
				objData.message ="库存成本只能输入整数和小数";
				objData.fl = false;
			}
			
			obj.id = data.outFieldStock.id;
			obj.kccb = $("#kccb_view").val();
			obj.biz = $("#biz_view").val();
			obj.jz = $("#jz_view").val();
			obj.jzbz = $("#jzbz_view").val();
			obj.cqid = $("#cqid_view").val();
			obj.hjsm = $("#hjsm_view").val();
			obj.hcly = $("#hcly_view").val();
			obj.grn = $("#grn_view").val();
			obj.tsn = (TimeUtil.convertToMinute($("#tsn_view").val(), TimeUtil.Separator.COLON))==0?'':TimeUtil.convertToMinute($("#tsn_view").val(), TimeUtil.Separator.COLON);
			obj.tso = (TimeUtil.convertToMinute($("#tso_view").val(), TimeUtil.Separator.COLON))==0?'':TimeUtil.convertToMinute($("#tso_view").val(), TimeUtil.Separator.COLON);
			obj.csn = $("#csn_view").val();
			obj.cso = $("#cso_view").val();
			obj.scrq = $("#scrq_view").val();
			obj.cfyq = $("#cfyq_view").val();
			obj.bz = $("#bz_view").val();
			obj.dprtcode = $("#dprtcode_view").val();
			obj.bjh = $("#bjh_view").val();
			obj.sn = $("#sn_view").val();
			obj.pch = $("#pch_view").val();
			obj.certificates = certificateUtil.getData();
			
			objData.obj = obj;
			return objData;
			
		},
		saveData:function(){
			var this_=this;
			var objData=this_.getParamsData();
			if(!objData.fl){
				AlertUtil.showModalFooterMessage(objData.message);
				return false;
			}
			
			//遮罩
			startWait();
			AjaxUtil.ajax({
				url :basePath +"/material/stock/material/updateByWc",
				type:"post",
				contentType:"application/json;charset=utf-8",
				data:JSON.stringify(objData.obj),
				dataType:"json",
				success:function(data){
					 AlertUtil.showErrorMessage("操作成功！");
					 finishWait();
			 		$("#inside_open_alert").modal("hide");
			 		outfield_main.search();
				}
			});
		},
		
		closeModal:function(){
			var this_=this;
			$("#"+this_.modalId).modal("hide");			
		},
		
		loadCqView:function(){
			cqModal.show({
				selected:$("#cqid_view").val(),
				callback:function(data){
					$("#cqid_view").val(data.id);
					$("#cqbh_view").val(data.cqbh);
				}
				
			});	
		},	
		showFrozenHistory:function(){		
			//查看冻结履历
			frozenHistoryModal.show({
			        id:$("#kcid_view").val()       //库存id	
			})
		}
		
  }
//初始化证书
function initCertificate(){
	certificateUtil = new CertificateUtil({
		parentId : 'inside_open_alert',
		dprtcode : userJgdm,
		tbody : $("#store_inner_certificate_list"),
		container :"#inside_open_alert",
		ope_type : 2
	});
}

//加载证书
function loadCertificate(obj){
	certificateUtil.load({
		bjh : obj.bjh,
		xlh : obj.xlh,
		pch : obj.pch,
		dprtcode : obj.dprtcode,
	});
}