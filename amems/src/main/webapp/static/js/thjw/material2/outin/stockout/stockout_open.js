var orderNumber = 1;
stockout_body = {
	id : "stockout_body",
	deleteUploadId : "",
	uploadObjSingle :[],
	uploadObjjl : {},
	planeDatas:[],
	/**
	 * 字段验证
	 */ 
	validation : function(){
		validatorForm = $('#from1').bootstrapValidator({
		       message: '数据不合法',
		       feedbackIcons: {
		          invalid:    'glyphicon glyphicon-remove',
		          validating: 'glyphicon glyphicon-refresh'
		       },
		       fields: {
		    	   shrq: {
			            validators: {
			            	notEmpty: {message: '出库日期不能为空!'}
			            }
			      }, 
			      ckid: {
			    	  validators: {
			    		  notEmpty: {message: '仓库不能为空!'}
			    	  }
			      }
		       }
		   });
	},
	/**
	 * 清空数据
	 */
	EmptiedData : function(){
		$(".required").show();   			    //隐藏必填标识
		$(".noteditable").attr("disabled",false);//标签不可编辑
		$(".colse").addClass("readonly-style");  //去掉文本框为白的的样式
		this.button(1);
		$("#lybh").attr("disabled",true);//标签可编辑
		$("#ckids").hide();		
		$("#ckid").show();		
		$("#id").val('');
		$("#shdhid").val('');
		$("#shdh").val('');
		$("#shlx").val("70");
		$("#shrq").val("");
		$("#lyid").val("");
		$("#lybh").val("");
		$("#lydw").val("");
		$("#fjzch").val("");
		stockout_open_alert.ids=[];
		stockout_open_alert.idList=[];
//		$("#ckid").val("");
		 var SelectArr1 = $("#ckDiv select"); //仓库重置为第一个
		 if(SelectArr1[0].options[0]!=undefined){
			 SelectArr1[0].options[0].selected = true;
		 }
		
		$("#bz").val("");
		$("#zt").val("");
		$("#ztName").val("");
		TimeUtil.getCurrentDate("#shrq"); //颁发日期,默认当前日期
		//清空出库明细
		$("#stockout_main_list").empty();
		$("#stockout_main_list").append("<tr><td colspan=\"15\"  class='text-center'>暂无数据 No data.</td></tr>");
		
		stockout_body.onchangeshlx();
		
		
		
	},
	//按钮权限
	button: function(param){
		$(".required").show();   			    //隐藏必填标识
		$(".noteditable").attr("disabled",false);//标签不可编辑
		$(".colse").addClass("readonly-style");  //去掉文本框为白的的样式
		
		$("#shdh").attr("ondblclick","");
		if(param==1){//新增
			$("#lybh").attr("disabled",true);//标签可编辑
			 $("#cexiao").hide();
			 $("#shanchu").hide();
			 $("#baocuns").show();
			 $("#tijiao").show();
		}else if(param==2){//保存
			$("#shdh").attr("disabled",true);//标签不可编辑
			$("#shdh").addClass("readonly-style");  //去掉文本框为白的的样式
			$("#shdh").attr("ondblclick","stockout_body.outstock()");
			
			 $("#tijiao").show();
			 $("#baocuns").show();
			 $("#shanchu").show();
			 $("#cexiao").hide();
		}else if(param==3){//提交
			$("#shdh").attr("disabled",true);//标签不可编辑
			$("#shdh").addClass("readonly-style");  //去掉文本框为白的的样式
			$("#shdh").attr("ondblclick","stockout_body.outstock()");
			
			 $("#baocuns").hide();
			 $("#tijiao").hide();
			 $("#shanchu").hide();
			 $("#cexiao").show();
			 
				$(".required").hide();   			    //隐藏必填标识
				$(".noteditable").attr("disabled",true);//标签不可编辑
 				$(".colse").removeClass("readonly-style");  //去掉文本框为白的的样式
 				
		}
	},
	//初始化飞机注册号
	initFjzchOption: function(jgdm){
		$("#fjzch").html("<option value=''>N/A</option>");
		$("#outStockModel_fjzch").html("<option value=''>显示全部</option><option value='N/A'>N/A</option>");
		var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE : jgdm});
		if(planeDatas.length > 0){
			for(var i = 0; i < planeDatas.length; i++){
				$("#fjzch").append("<option value='"+StringUtil.escapeStr(planeDatas[i].FJZCH)+"'>"+StringUtil.escapeStr(planeDatas[i].FJZCH)+ " " + StringUtil.escapeStr(planeDatas[i].XLH)+"</option>");
				$("#outStockModel_fjzch").append("<option value='"+StringUtil.escapeStr(planeDatas[i].FJZCH)+"'>"+StringUtil.escapeStr(planeDatas[i].FJZCH)+ " " + StringUtil.escapeStr(planeDatas[i].XLH)+"</option>");
			}
		}else{
			$("#fjzch").html("<option value=''>N/A</option>");
			$("#outStockModel_fjzch").html("<option value='N/A'>N/A</option>");
		}
	},
	//选择出库类型
	onchangeshlx: function(){
		var shlx=$("#shlx").val();
		
		if(shlx == 70){ //发料
			//选择需求单
			$("#lyidDiv").show(); //显示来源框
			

			 $("#lybutton").attr("onclick","stockout_body.initDemandData()");
			 $("#lybh").attr("ondblclick","stockout_body.initDemandData()");
			 
		}else if(shlx == 90){
			//隐藏选择
			$("#lyidDiv").hide();
			
		}else{
			$("#lyidDiv").show(); //显示来源框
			//与出库类型匹配的选择合同
			$("#lybutton").attr("onclick","stockout_body.initContractData()");
			$("#lybh").attr("ondblclick","stockout_body.initContractData()");
		}
		
		$("#lydw").val("");//清空 接收单位 
		$("#fjzch").val("");//清空 飞机注册号
		
		
		$("#lyid").val("");//清空 来源单据id
		$("#lybh").val("");//清空 来源编号
		
		stockout_open_alert.ids=[];
		stockout_open_alert.idList=[];
		//清空出库明细
		$("#stockout_main_list").empty();
		$("#stockout_main_list").append("<tr><td colspan=\"15\"  class='text-center'>暂无数据 No data.</td></tr>");
		
	},
	//需求单
	initDemandData : function(){
		demandModal.show({
			selected:$("#lyid").val(),//原值，在弹窗中默认勾选
			dprtcode:userJgdm,
			fjzch:$("#fjzch").val(),
			parentWinId : "demandModal",
			callback: function(data){//回调函数
				if(data != null){	
					$("#lyid").val(data.lyid);
					$("#lybh").val(data.lybh);
					$("#fjzch").val(data.fjzch);
				}else{
					$("#lyid").val('');
					$("#lybh").val('');
					$("#fjzch").val('');
				}
				
				stockout_open_alert.ids=[];
				stockout_open_alert.idList=[];
				//清空出库明细
				$("#stockout_main_list").empty();
				$("#stockout_main_list").append("<tr><td colspan=\"15\"  class='text-center'>暂无数据 No data.</td></tr>");
			}
		});
	},
	//合同
	initContractData : function(){
		SelectSourceorderModal.show({
			selected:$("#lyid").val(),//原值，在弹窗中默认勾选
			htlx:$("#shlx").val(),
			zt :[2,12] ,
			parentWinId : "SelectSourceorderModal",
			callback: function(data){//回调函数
				if(data != null){	
					$("#lyid").val(data.lyid);
					$("#lybh").val(data.lybh);
					$("#lydw").val(data.lydw);
				}else{
					$("#lyid").val('');
					$("#lybh").val('');
					$("#lydw").val('');
				}
				
				stockout_open_alert.ids=[];
				stockout_open_alert.idList=[];
				//清空出库明细
				$("#stockout_main_list").empty();
				$("#stockout_main_list").append("<tr><td colspan=\"15\"  class='text-center'>暂无数据 No data.</td></tr>");
			}
		});
	},
	//初始化仓库
	initStoreSelect : function(){
		var storeHtml = "";
		/*var cklbs=[0,4,3,5,6,7,8];*/
		var dprtcode =userJgdm;
		$.each(userStoreList, function(index, row){
			if( dprtcode == row.dprtcode){
				storeHtml += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>";
			}
		});

		$("#ckid").html(storeHtml);
	},
	//初始化弹出框的仓库
	initStoreSelectck : function(){
		var storeHtml = "<option value=\"\">显示全部</option>";
		/*var cklbs=[0,4,3,5,6,7,8];*/
		var dprtcode =userJgdm;
		$.each(userStoreList, function(index, row){
			if( dprtcode == row.dprtcode){
				storeHtml += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>";
			}
		});

		$("#stockout_open_alert_ckid").html(storeHtml);
	},
	//初始化弹出库位
	initStorek : function(){
		if($("#stockout_open_alert_ckid").val()!=""){
			var storeId = $("#stockout_open_alert_ckid").val();
			for (var i = 0; i < userStoreList.length; i++) {
				if(userStoreList[i].id == storeId){
					var kwOptionHtml = "<option value=\"\">显示全部</option>";
					var storageList = userStoreList[i].storageList;
					for (var j = 0; j < storageList.length; j++) {
						
						kwOptionHtml += "<option value='"+storageList[j].id+"'>"+StringUtil.escapeStr(storageList[j].kwh)+"</option>";
					}
					$("#stockout_open_alert_kwid").html(kwOptionHtml);
				}
			}
		}else{
			var htmlContent1 = '<option value=\"\">显示全部</option>';
			$("#stockout_open_alert_kwid").html(htmlContent1);
		}
		$("#stockout_open_alert_kwid").selectpicker("refresh");
		stockout_open_alert.searchkc();
	},
	//打开出库物料
	stockOutOpen: function(){
		var this_=this;
		if($("#ckid").val() == ''){
			AlertUtil.showModalFooterMessage("仓库不能为空!");
			return false;
		}
		
		var shlx=$("#shlx").val();
		
		if(shlx == 70){ //发料  -需求
			$("#modalPanelLeft").css("display","block");
			$("#modalPanelRight").removeAttr("class");
			$("#modalPanelRight").addClass("col-xs-9 padding-right-0");
			 
			stockout_open_alert.show({
				//selected:gdid,//原值，在弹窗中默认勾选
				bs : 1, 
				id:$("#lyid").val(),  //来源单据id
				parentWinId : "demandModal",
				callback: function(data){//回调函数
					stockout_main.appendContentHtmlStock(data);
					
				}
			});
			
//			$("#pply").show();
			
		}else if(shlx == 90){
			
			stockout_open_alert.show({
				//selected:gdid,//原值，在弹窗中默认勾选
				bs : 1, 
				id:$("#lyid").val(),  //来源单据id
				parentWinId : "demandModal",
				callback: function(data){//回调函数
					stockout_main.appendContentHtmlStock(data);
				}
			});
			
			
			$("#modalPanelLeft").css("display","none");
			$("#modalPanelRight").removeAttr("class");
			$("#modalPanelRight").addClass("col-xs-12 padding-left-0 padding-right-0");
			
//			$("#pply").hide();
			
		}else{
			$("#modalPanelLeft").css("display","block");
			$("#modalPanelRight").removeAttr("class");
			$("#modalPanelRight").addClass("col-xs-9 padding-right-0");
			
			stockout_open_alert.show({//合同
				//selected:gdid,//原值，在弹窗中默认勾选
				bs : 2, 
				id:$("#lyid").val(),  //来源单据id
				parentWinId : "demandModal",
				callback: function(data){//回调函数
					
					stockout_main.appendContentHtmlStock(data);
				}
			});
//			$("#pply").show();
		}
		
		$("#stockout_open_alert").modal("show"); 
		
	},
	//打开出库单号
	outstock : function(){
		outStockModel.show({
			selected:$("#shdhid").val(),//原值，在弹窗中默认勾选
			parentWinId : "demandModal",
			callback: function(data){//回调函数
				stockout_body.EmptiedData(); //清空数据
				if(data.id != undefined){	
					$("#shdhid").val(data.id);
					$("#shdh").val(data.shdh);
					stockout_update_modal.open();//加载数据
				}
				$('#from1').data('bootstrapValidator').resetForm(false);
//				$("#from1").data('bootstrapValidator').destroy();
//				$('#from1').data('bootstrapValidator', null);
				 stockout_body.validation(); 			//初始化验证
//				 stockout_body.onchangeshlx();
			}
		});
	},
	//附件下载
	downfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	},
	//移除一行
	removeCol : function(obj,id){
		$(obj).parent().parent().remove();
		var len = $("#stockout_main_list").find("tr").length;
		if(len < 1){
			$("#stockout_main_list").append("<tr><td  colspan='15'  class='text-center'>暂无数据 No data.</td></tr>");
		}
		
		var	idList=stockout_open_alert.idList;
		var idnewlist=[];
		for(var i=0;i<idList.length;i++){
			if(idList[i] != id){
				idnewlist.push(idList[i]);
			}
		}
		stockout_open_alert.idList=idnewlist;
	},
	//修改仓库
	onchangeck : function(){
		//清空出库明细
		stockout_open_alert.ids=[];
		stockout_open_alert.idList=[];
		$("#stockout_main_list").empty();
		$("#stockout_main_list").append("<tr><td colspan=\"15\"  class='text-center'>暂无数据 No data.</td></tr>");
	},
	//只能输入数字和小数
	clearNoNum : function(obj){
			//先把非数字的都替换掉，除了数字和.
				obj.value = obj.value.replace(/[^\d.]/g,"");
				//必须保证第一个为数字而不是.
				obj.value = obj.value.replace(/^\./g,"");
				//保证只有出现一个.而没有多个.
				obj.value = obj.value.replace(/\.{2,}/g,".");
				//保证.只出现一次，而不能出现两次以上
				obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		      
				strs=obj.value.split("."); //字符分割
				if(strs.length>1){
					if(strs[1]>99){
						obj.value=strs[0]+'.'+strs[1].substr(0, 2);
					}
				}
	}
};
$('.date-picker').datepicker({
	format:'yyyy-mm-dd',
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});


$('#shrq').datepicker({
	 autoclose: true,
	 clearBtn:true
}).on('hide', function(e) {
	  $('#from1').data('bootstrapValidator')  
.updateStatus('shrq', 'NOT_VALIDATED',null)  
.validateField('shrq');  
});
