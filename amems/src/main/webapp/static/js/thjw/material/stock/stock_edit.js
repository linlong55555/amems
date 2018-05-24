
var gljb=["","无","批次号管理","序列号管理"];
var hclb=["其他","航材","设备","工具","危险品","低值易耗品"];
$(function(){
	
	$('.date-picker').datepicker({
		format:'yyyy-mm-dd',
		autoclose : true
	}).next().on("click", function() {
		console.log(1);
		$(this).prev().focus();
	});
	
});


	 	stockEditComp = {
	 		param: {
	 		},
	 		show : function(param){
	 			if(param){
	 				$.extend(this.param, param);
	 			}
	 			
	 		     if($('#stockEditModal #form').data('bootstrapValidator')){
	 		    	 $('#stockEditModal #form').data('bootstrapValidator').destroy();
		 		     $('#stockEditModal #form').data('bootstrapValidator', null);
	 		     }
	 		     initValidator();
	 		     
	 			$('#stockEditModal #form').data('bootstrapValidator').resetForm(false);
	 			this.loadData();
	 			$("#stockEditModal").modal('show');
	 			
	 		},
	 		 
	 		loadData:function(){
	 			AjaxUtil.ajax({
	 			   url:basePath+"/aerialmaterial/stock/load",
	 			   type: "post",
	 			   contentType:"application/json;charset=utf-8",
	 			   dataType:"json",
	 			   data:JSON.stringify(this.param),
	 			   success:function(data){
	 				    finishWait();
	 				    if(data !=null && data !=""){
	 				    	
	 				    	$('#stockEditModalBody #kcly').val($.trim(formatUndefine(data.kcly)));
	 				    	$('#stockEditModalBody #id').val($.trim(formatUndefine(data.id)));
	 				    	
	 				    	
	 				    	$('#stockEditModalBody #bjh').val(formatUndefine(data.bjh));
	 				    	$('#stockEditModalBody #zwms').val(formatUndefine(data.zwms));
	 				    	$('#stockEditModalBody #ywms').val(formatUndefine(data.ywms));
	 				    	$('#stockEditModalBody #gljb').val(StringUtil.escapeStr(gljb[data.gljb]));
	 				    	 

	 				    	$('#stockEditModalBody #hclx').val(formatUndefine(hclb[data.hclx]));
	 				    	$('#stockEditModalBody #kcsl').val(formatUndefine(data.kcsl));
	 				    	$('#stockEditModalBody #ckmc').val(StringUtil.escapeStr(data.ckmc));
	 				    	
	 				    	$('#stockEditModalBody #kwh').val(StringUtil.escapeStr(data.kwh));
	 				    	
	 				    	$('#stockEditModalBody #xkzh').val(formatUndefine(data.xkzh));
	 				    	$('#stockEditModalBody #shzh').val(formatUndefine(data.shzh));  
	 				    	$('#stockEditModalBody #shzwz').val(formatUndefine(data.shzwz));  
	 				    	$('#stockEditModalBody #hjsm').val((formatUndefine(data.hjsm)||'').substring(0,10));   
	 				    	
	 				    	
	 				    	$('#stockEditModalBody #purchase_view_spqx').val((formatUndefine(data.spqx)||'').substring(0,10));
	 				    	$('#stockEditModalBody #scrq').val((formatUndefine(data.scrq)||'').substring(0,10));
	 				    	$('#stockEditModalBody #zzcs').val(formatUndefine(data.zzcs));  
	 				    	$('#stockEditModalBody #tsn').val(formatUndefine(data.tsn));  
	 				    	
		 				   	$('#stockEditModalBody #tso').val(formatUndefine(data.tso));
		 				    $("#stockEditModalBody #hcly").empty();
		 				    DicAndEnumUtil.registerDic('85',"hcly",data.dprtcode);
		 				   	
	 				    	$('#stockEditModalBody #hcly').val(formatUndefine(data.hcly));  
	 				    	$('#stockEditModalBody #pch').val(formatUndefine(data.pch));
	 				    	$('#stockEditModalBody #sn').val(formatUndefine(data.sn));  
	 				    	$('#stockEditModalBody #kccb').val(formatUndefine(data.kccb));
	 				    	$('#stockEditModalBody #cfyq').val(formatUndefine(data.cfyq));
	 				    	$('#stockEditModalBody #grn').val(formatUndefine(data.grn));
	 				    	$('#stockEditModalBody #csn').val(formatUndefine(data.csn));
	 				    	$('#stockEditModalBody #cso').val(formatUndefine(data.cso));
	 				    	
	 				    	$("#stockEditModalBody #bjid").val(data.bjid);
	 				    	
	 					} else {
	 						AlertUtil.showErrorMessage("库存信息修改失败");
 					   }
	 		      }
	 		    }); 
	 			
	 		}
	 		,save:function(){

	 			$('#stockEditModal #form').data('bootstrapValidator').validate();
	 			if (!$('#stockEditModal #form').data('bootstrapValidator').isValid()) {
	 				AlertUtil.showErrorMessage("请根据提示输入正确的信息");
	 				return false;
	 			}
	 			var data = {
	 					 xkzh:$.trim($('#stockEditModalBody #xkzh').val())
	 					,shzh:$.trim($('#stockEditModalBody #shzh').val())
	 					,shzwz:$.trim($('#stockEditModalBody #shzwz').val())
	 					,hjsm:$.trim($('#stockEditModalBody #hjsm').val())
	 					
	 					,spqx:$.trim($('#stockEditModalBody #spqx').val())
	 					,scrq:$.trim($('#stockEditModalBody #scrq').val())
	 					,zzcs:$.trim($('#stockEditModalBody #zzcs').val())
	 					,tsn:$.trim($('#stockEditModalBody #tsn').val())
	 					
	 					,tso:$.trim($('#stockEditModalBody #tso').val())
	 					,hcly:$.trim($('#stockEditModalBody #hcly').val())
	 					,pch:$.trim($('#stockEditModalBody #pch').val())
	 					,sn:$.trim($('#stockEditModalBody #sn').val())
	 					
	 					,kccb:$.trim($('#stockEditModalBody #kccb').val())
	 					,cfyq:$.trim($('#stockEditModalBody #cfyq').val()) 
	 					,kcly:$.trim($('#stockEditModalBody #kcly').val()) 
	 					,id:$.trim($('#stockEditModalBody #id').val()) 
	 					,grn:$.trim($('#stockEditModalBody #grn').val()) 
	 					,csn:$.trim($('#stockEditModalBody #csn').val()) 
	 					,cso:$.trim($('#stockEditModalBody #cso').val()) 
	 			};
	 			startWait();
	 			// 提交数据
	 			AjaxUtil.ajax({
	 				url : basePath + "/aerialmaterial/stock/edit",
	 				type : "post",
	 				data : JSON.stringify(data),
	 				dataType : "json",
	 				contentType : "application/json",
	 				success : function(data) {
	 					$("#stockEditModal").modal('hide');
 						AlertUtil.showMessageCallBack({
 							message : "修改成功",
 							callback : function() {
 								 $('#searchBtn').trigger('click');
 							}
 						});
	 				}
	 			});
	 			
	 		}
	
	 	}
 		//历史航材合同采购价格
	 	function showContractCost(){
 			open_win_contract_cost_his.show({
 				bjid: $('#stockEditModalBody #bjid').val(),
 				callback: function(data){//回调函数
 					if(data != null){
 						$('#stockEditModalBody #kccb').val(data.htClf);
 					}
 				}
 			},true)
 		
 		} 
	 	
	 	function initValidator(){
	 		if($('#stockEditModal #form').data('bootstrapValidator')){
	 			$('#stockEditModal #form').data('bootstrapValidator').destroy();
	 			$('#stockEditModal #form').data('bootstrapValidator', null);
			}
	 		
 			validatorForm =  $('#stockEditModal #form').bootstrapValidator({
 		        message: '数据不合法',
 		        feedbackIcons: {
 		            //valid: 'glyphicon glyphicon-ok',
 		            invalid: 'glyphicon glyphicon-remove',
 		            validating: 'glyphicon glyphicon-refresh'
 		        },
 		        fields: {
 		        	xkzh: {
 		                validators: {
// 		                	$('#stockEditModalBody #gljb_hide').val
 		                	regexp: {
 		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
 		                        message: '不能输入中文'
 		                    },
 		                }
 		            },
// 		            shzh: {
// 		            	validators: {
// 		            		callback: {
// 			                    message: '管理级别为序列号时,适航证号必填,且不能输入中文.',
// 			                    callback: function(value, validator) {
// 			                    	regexp = /^[^\u4e00-\u9fa5]{0,}$/;
// 			                        	
// 			                        if($.trim($('#stockEditModalBody #shzh').val())=='' && $.trim($('#stockEditModalBody #gljb').val()) ==gljb[3]){
// 			                        	return false;
// 			                        }
// 			                        else if(value.match(regexp)==null){
// 			                        	return false;
// 			                        }
// 			                        return true;
// 			                    }
// 			                } 
// 		                }
// 		            },
 		            shzwz: {
 		                validators: {
 		                }
 		            },
 		            tsn: {
 		                validators: {
 		                	regexp: {
 		                        regexp: /^[0-9]+((\:|\.)[0-5]?[0-9])?$/,
 		                        message: '输入的格式不正确'
 		                    },
 		                }
 		            }, 
 		            tso: {
 		                validators: {
 		                	regexp: {
 		                        regexp:/^[0-9]+((\:|\.)[0-5]?[0-9])?$/,
 		                        message: '输入的格式不正确'
 		                    },
 		                }
 		            },
 		            pch: {
 		            	validators: {
 		            		callback: {
 			                    message: '管理级别为批次号时,批次号必填,且不能输入中文.',
 			                    callback: function(value, validator) {
 			                    	regexp = /^[^\u4e00-\u9fa5]{0,}$/;
 			                        if($.trim($('#stockEditModalBody #pch').val())=='' && $.trim($('#stockEditModalBody #gljb').val()) ==gljb[2]){
 			                        	return false;
 			                        }
 			                        else if(value.match(regexp)==null){
 			                        	return false;
 			                        }
 			                        return true;
 			                    }
 			                } 
 		                }
 		            },
 		            sn: {
 		            	validators: {
 		            		callback: {
 			                    message: '管理级别为序列号时,序列号必填,且不能输入中文.',
 			                    callback: function(value, validator) {
 			                    	regexp = /^[^\u4e00-\u9fa5]{0,}$/;
 			                        if($.trim($('#stockEditModalBody #sn').val())=='' && $.trim($('#stockEditModalBody #gljb').val()) ==gljb[3]){
 			                        	return false;
 			                        }
 			                        else if(value.match(regexp)==null){
 			                        	return false;
 			                        }
 			                        return true;
 			                    }
 			                } 
 		                }
 		            },
 		            cfyq: {
 		                validators: {
 		                }
 		            } 
 		        }
 		    });
 		}
	 	
	 	//只能输入数字和小数,保留两位
	 	function onkeyup4Num(obj){
	 	     //先把非数字的都替换掉，除了数字和.
	 	     obj.value = obj.value.replace(/[^\d.]/g,"");
	 	     //必须保证第一个为数字而不是.
	 	     obj.value = obj.value.replace(/^\./g,"");
	 	     //保证只有出现一个.而没有多个.
	 	     obj.value = obj.value.replace(/\.{2,}/g,".");
	 	     //保证.只出现一次，而不能出现两次以上
	 	     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	 	     
	 	     var str = obj.value.split(".");
	 	     if(str.length > 1){
	 	    	 if(str[1].length > 2){
	 	    		 obj.value = str[0] +"." + str[1].substring(0,2);
	 	    	 }
	 	     }
	 	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	 	  		 if(obj.value.substring(1,2) != "."){
	 	  			obj.value = 0;
	 	  		 }
	 	  	 }
	 	     obj.value = validateMax(obj.value);
	 	     function validateMax(_value){
	 	    	 if(Number(_value) > 99999999.99){
	 	    		return validateMax(_value.substr(0, _value.length-1));
	 	    	 }
	 	    	 return _value;
	 	    }
	 	}
	 	

