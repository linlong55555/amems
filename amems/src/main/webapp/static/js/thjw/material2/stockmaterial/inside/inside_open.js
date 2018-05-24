$(function(){
	 //收缩效果
//	$('#borrow_info').on('show.bs.collapse', function () {
//		$('#borrow_info').siblings("div").find("input.selectCheckbox").prop("checked",true);
//	})
//	$('#borrow_info').on('hide.bs.collapse', function () {
//		$('#borrow_info').siblings("div").find("input.selectCheckbox").prop("checked",false);
//	})
	
	$('#certificate_info').on('shown.bs.collapse', function () {
		$('#certificate_info').siblings("div").find("input.selectCheckbox").prop("checked",true);
	})
	$('#certificate_info').on('hidden.bs.collapse', function () {
		$('#certificate_info').siblings("div").find("input.selectCheckbox").prop("checked",false);
	})
	
	
	$('#resume_info').on('shown.bs.collapse', function () {
		$('#resume_info').siblings("div").find("input.selectCheckbox").prop("checked",true);
	})
	$('#resume_info').on('hidden.bs.collapse', function () {
		$('#resume_info').siblings("div").find("input.selectCheckbox").prop("checked",false);
	})	
	
		//清除foot信息		
		$("#inside_open_alert").on('shown.bs.modal', function (e) {
			$('#form').data('bootstrapValidator').resetForm(false); 
			 AlertUtil.hideModalFooterMessage();
		});	
	    bindEvent();	     
		//初始化管理级别,物料类别,来源基础数据
		initBasicDataOpen();
		//初始化当前用户有权限的仓库
		initStoreOpen();
		//初始化币种
		initBizOpen();
		//校验
		validation();
			
})
function validation(){
 $('#form').bootstrapValidator({
	        message: '数据不合法',
	        excluded:[":disabled"],  
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	cb_view: {
	                validators: {
				       regexp: {	   
			    			regexp:   /^\d+(\.\d{1,2})?$/ ,
			    			message: '成本只能为两位小数以内的正数'
			    		}
	                }
	            },
	         	jz_view: {
	                validators: {
				       regexp: {	   
			    			regexp:   /^\d+(\.\d{1,2})?$/ ,
			    			message: '价值只能为两位小数以内的正数'
			    		}
	                }
	            },
	         	common_cso: {
	                validators: {
				       regexp: {	
			    			regexp:   /^[0-9]*[1-9][0-9]*$/ ,
			    			message: 'cso只能为正整数'
			    		}
	                }
	            },
	        	common_csn: {
	                validators: {
				       regexp: {	   
			    			regexp:   /^[0-9]*[1-9][0-9]*$/ ,
			    			message: 'csn只能为正整数'
			    		}
	                }
	            }
	        }
	    });
}

function bindEvent(){
	$("#inside_open_alert .time-masked").on("keyup", function(){
		// 回退循环
		validateDateTime($(this));
	});	
}

function validateDateTime($obj){
	this.backspace($obj, /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/, true);
}

// 回退
function backspace(obj, reg, replace){
	var value = obj.val();
	if(replace){
		value = value.replace(/：/g, ":");
	}
	while(!(reg.test(value)) && value.length > 0){
		value = value.substr(0, value.length-1);
    }
	obj.val(value);
	
}
function downloadfile(id) {
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}
function initBasicDataOpen(){
	var gllbOptions=getOptionsOpen(DicAndEnumUtil.getEnum("supervisoryLevelEnum"));
    var wllbOptions=getOptionsOpen(DicAndEnumUtil.getEnum("materialTypeEnum"));
    if($("#isTool").val()=='true'){//如果是工具
    	wllbOptions=getOptionsOpen(DicAndEnumUtil.getEnum("materialToolSecondTypeEnum"));	
    }
//	var lyOptions=getOptionsOpen(DicAndEnumUtil.getEnum("materialSrouceEnum"));
	$("#gljb_view").empty();
	$("#wllb_view").empty();
	$("#qczt_view").empty();
//	$("#hcly_view").empty();
	$("#gljb_view").append(gllbOptions);
	$("#wllb_view").append(wllbOptions);
	DicAndEnumUtil.registerDic("86", "qczt_view", $("#dprtcode_search").val());
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
		$("#cbbz_view").empty();
		$("#cbbz_view").append(htmlOption);
		$("#jzbz_view").empty();
		$("#jzbz_view").append(htmlOption);
	
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

var certificateUtil={};

var inside_Modal={
		
		modalId:'inside_open_alert',
		
		param:{
			id:null,            //库存id
			dprtcode:userJgdm,  //默认登录人当前机构代码
			callback:null,       //保存成功后的回调函数
			ztView:false,//航材库存、工具库存的库内查询状态不显示，器材状态显示，工具库存的库存查询状态显示，器材状态不显示
		},
		
		data:{},
		
		
		show:function(param){
			var this_=this;
			this_.param.ztView = false;
			if(param){
				$.extend(this.param,param);
			}
						
			 this.clearData(); //清空弹框数据
			 
			 this.ztView();//状态和器材状态显示控制
			 
			 this.initData();
			 
			 this.setData();
			 
			 $("#"+this_.modalId).modal("show");
		},
		clearData:function(){
			
			$("input[id$='view'],select[id$='view'],textarea[id$='view']").val("");
		},
		ztView:function(){
			if(this.param.ztView){
				$("#zt_view_div").show();
				$("#qczt_view_div").hide();
			}else{
				$("#zt_view_div").hide();
				$("#qczt_view_div").show();
			}
		},
		initData:function(){
			var this_=this;
			var kcid=this_.param['id'];
			startWait();
			AjaxUtil.ajax({
				url: basePath + "/material/stock/material/queryStockById",
				async:false,
				type:"post",
				dataType:"json",
				data:{'kcid':kcid},
				success:function(data){
					finishWait();
					this_.data=data;
					refreshPermission();
				}
			})
			
		},
		setData:function(){
			var this_=this;
			var data=this_.data['stock']; //库存数据
			var history=this_.data['stockHistory']; //库存履历
			var borrowRecords=this_.data['borrowRecords']; //工具借用归还履历
		    if(data){	
		    	if(data.materialBatchInfo){
		    		$("#cb_view").val((data.materialBatchInfo.cb||data.materialBatchInfo.cb==0)?data.materialBatchInfo.cb.toFixed(2):"");
		    		$("#jz_view").val((data.materialBatchInfo.jz||data.materialBatchInfo.jz==0)?data.materialBatchInfo.jz.toFixed(2):"");
		    		$("#cbbz_view").val(data.materialBatchInfo.cbbz);
		    		$("#jzbz_view").val(data.materialBatchInfo.jzbz);
		    	}else{
		    		$("#cbbz_view").find("option:eq(0)").attr("selected","selected");
		    		$("#jzbz_view").find("option:eq(0)").attr("selected","selected");
		    	}
			    $("#bz_view").val(data.bz);
				$("#kcid_view").val(data.id);
				$("#bjh_view").val(data.bjh||"");
				$("#ckmc_view").val((data.hCMainData?data.hCMainData.ywms:"")+" "+(data.hCMainData?(data.hCMainData.zwms?data.hCMainData.zwms:''):""));
				$("#pch_view").val(data.pch||"");
				$("#sn_view").val(data.sn||"");
				$("#ck_view").val(data.ckid);
				$("#cklb_view").val(data.cklb);
				$("#ckh_view").val(data.ckh);
				$("#kw_view").val(data.kwh||"");
				$("#kwid_view").val(data.kwid);
				$("#kcsl_view").val(data.kcsl||0);
				$("#dw_view").val(data.hCMainData?(data.hCMainData.jldw||''):'');
				$("#kcsldw_view").val((data.kcsl||0)+" "+(data.hCMainData?(data.hCMainData.jldw||''):''));
				$("#djsl_view").val(data.djsl||"");		
				if(data.djsl){
					$("#djsl_link").html(data.djsl+" "+(data.hCMainData?(data.hCMainData.jldw||''):''));
				}else{
					$("#djsl_link").remove();
					$("#djno").append("<input type='text' id='djsl_link' disabled='disabled' class='form-control' value='"+0+" "+(data.hCMainData?(data.hCMainData.jldw||''):'')+"'/>");
				}				
				$("#cqbh_view").val(data.propertyRight?data.propertyRight.cqbh:"");
				$("#cqid_view").val(data.cqid);
				$("#hjsm_view").val(data.hjsm?data.hjsm.substring(0, 10):"");
				$("#hjsm_view").datepicker("update");
				if(data.hcly){
					$("#hcly_view").val(data.hcly);
				}else{
		    		$("#hcly_view").find("option:eq(0)").attr("selected","selected");
				}	
				if(data.qczt){
					$("#qczt_view").val(data.qczt);
				}else{
		    		$("#qczt_view").find("option:eq(0)").attr("selected","selected");
				}
				$("#grn_view").val(data.grn||"");
				$("#tsn_view").val(TimeUtil.convertToHour(data.tsn, TimeUtil.Separator.COLON)||"");
				$("#tso_view").val(TimeUtil.convertToHour(data.tso, TimeUtil.Separator.COLON)||"");
				$("#csn_view").val(data.csn||"");
				$("#cso_view").val(data.cso||"");
				$("#scrq_view").val(data.scrq?data.scrq.substring(0, 10):"");
				$("#scrq_view").datepicker("update");
				$("#zt_view").val(DicAndEnumUtil.getEnumName("stockStatusEnum",StringUtil.escapeStr(data.zt)));
				$("#cfyq_view").val(data.cfyq||"");
				
				if($("#isTool").val()=='false'){//航材物料类别
					$("#wllb_view").val(data.hCMainData?data.hCMainData.hclx:"");					
				}else{//工具物料类别
					$("#wllb_view").val(data.hCMainData?data.hCMainData.hclxEj:"");
				}				
				$("#cjjh_view").val(data.hCMainData?data.hCMainData.cjjh:"");
				$("#gg_view").val(data.hCMainData?(data.hCMainData.gg||""):"");
				$("#xh_view").val(data.hCMainData?(data.hCMainData.xingh||""):"");
				$("#gljb_view").val(data.hCMainData?data.hCMainData.gljb:"");
				$("#zzcs_view").val(data.hCMainData?(data.hCMainData.zzcs||""):"");
				$("#maxkcs_view").val(data.hCMainData?data.hCMainData.maxKcl:"");
				$("#minkcs_view").val(data.hCMainData?data.hCMainData.minKcl:"");
				$("#sjsj_view").val(data.rksj||"");
				$("#sjr_view").val((data.rkr_user?data.rkr_user.username:"")+" "+(data.rkr_user?data.rkr_user.realname:""));
				$("#update_view").val(data.whsj||"");
				$("#update_person_view").val((data.zdr?data.zdr.username:"")+" "+(data.zdr?data.zdr.realname:""));
		    }
			
		    //加载库存履历
			var htmlHistory="";
			if(history.length==0){
				htmlHistory="<tr><td class='text-center' colspan='3'>暂无数据</td></tr>";
			}else{
				for(var i=0;i<history.length;i++){					 
					htmlHistory+="<tr><td class='text-left'>"+history[i].czsj+"</td><td class='text-left'>"+(history[i].zdr.username||"")+" "+(history[i].zdr.realname||"")+"</td><td>"+(history[i].czsm||"")+"</td></tr>"		
				}		
			}
			$("#hisTbody").empty();
			$("#hisTbody").append(htmlHistory);
			
			
			//加载证书列表
			this_.initCerrtficateList(data);
						 			
		},
		initCerrtficateList:function(data){
			
			 certificateUtil = new CertificateUtil({
				parentId : "inside_open_alert",
				dprtcode : data.dprtcode,
				tbody : $("#store_inner_certificate_list"),
				container : "#inside_open_alert",
				ope_type : 1
			});
			
			var obj={
				bjh:data.bjh,	
				pch:data.pch,	
				xlh:data.sn,
				dprtcode:data.dprtcode
			};

			certificateUtil.load(obj);
			

		},
		getParamsData:function(){
			var data=this.data;
			var paramsData=data['stock'];
		    //覆盖被修改的字段
			paramsData['kccb']=$.trim($("#cb_view").val());
			paramsData['biz']=$.trim($("#cbbz_view").val());
			paramsData['jz']=$.trim($("#jz_view").val());
			paramsData['jzbz']=$.trim($("#jzbz_view").val());
			paramsData['cqid']=$.trim($("#cqid_view").val());
			paramsData['hjsm']=$.trim($("#hjsm_view").val());
			paramsData['hcly']=$.trim($("#hcly_view").val());
			paramsData['qczt']=$.trim($("#qczt_view").val());
			paramsData['grn']=$.trim($("#grn_view").val()||'');
			paramsData['tsn']=$.trim(TimeUtil.convertToMinute($("#tsn_view").val(), TimeUtil.Separator.COLON));
			paramsData['tso']=$.trim(TimeUtil.convertToMinute($("#tso_view").val(), TimeUtil.Separator.COLON));
			paramsData['csn']=$.trim($("#csn_view").val());
			paramsData['cso']=$.trim($("#cso_view").val());
			paramsData['scrq']=$.trim($("#scrq_view").val());
			paramsData['cfyq']=$.trim($("#cfyq_view").val());
			paramsData['bz']=$.trim($("#bz_view").val());
			paramsData['qczt']=$.trim($("#qczt_view").val());
			paramsData['whsj']=paramsData['whsj']?paramsData['whsj'].substring(0,10):'';
			paramsData['rksj']=paramsData['rksj']?paramsData['rksj'].substring(0,10):'';
			paramsData['spqx']=paramsData['spqx']?paramsData['spqx'].substring(0,10):'';
			var certificateList=[];
			certificateList=certificateUtil.getData();		
			paramsData.componentCertificateList=certificateList;
			return paramsData;
			
		},
		saveData:function(){
			$('#form').data('bootstrapValidator').validate();
			 if(!$('#form').data('bootstrapValidator').isValid()){
				return false;
			  }
			 
			var tsn=$("#tsn_view").val();
			var tso=$("#tso_view").val();
			var csn=$("#csn_view").val();
			var cso=$("#cso_view").val();
			
			var reg=/^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/;;
			if(tsn&&!reg.test(tsn)){
				AlertUtil.showModalFooterMessage("TSN格式不对");
				return ;
			}
			if(tso&&!reg.test(tso)){
				AlertUtil.showModalFooterMessage("TSO格式不对");
				return ;
			}          
			 
			var this_=this;
			var obj=this_.getParamsData();
			startWait();
			AjaxUtil.ajax({
				url : basePath + "/material/stock/material/save",
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(obj),
				success : function(data){
					finishWait();
		 			$("#inside_open_alert").modal("hide");
	                if(this_.param.callback&&typeof(this_.param.callback)=='function'){	                	
	                	this_.param.callback("success");
	                }
				}
						
			})
			
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
		},
		printInstock:function(){
			window.open(basePath +"/material/stock/material/viewHtml?id="+$("#kcid_view").val());
		}
		
		
		
  }