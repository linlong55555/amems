Fault_Handling_Record_Open_Modal = {
	id : "Fault_Handling_Record_Modal",
	deleteUploadId : "",
	uploadObjSingle :[],
	zsjxxjl : "",
	cxjxxjl : "",
	uploadObjjl : {},
	//显示弹出窗
	show: function(param){
		if(param==1){//新增
			Fault_Handling_Record_Add_Modal.open();
		}else if(param==2){//修改
			Assessment_Update_Modal.open();
		}else if(param==3){//审核
			Assessment_Audit_Modal.open();
		}else if(param==4){//批准
			Assessment_Approval_Modal.open();
		}else if(param==5){//改版
			Assessment_Revision_Modal.open();
		}
		Fault_Handling_Record_Open_Modal.validation(); 	//初始化验证

	},
	/**
	 * 清空数据
	 */
	EmptiedData : function(){
		$("#hbh").val("");
		$("#"+this.id+" input:text").val(""); //清空文本框数据
		$("#"+this.id+" textarea").val("");   //清空多行文本数据
		$("#zlhid").val('');
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit_handing');
		attachmentsObj.show({
			djid:null,
			fileHead : true,
			colOptionhead : true,
			fileType:"handling"
		});//初始化附件
	},
	/**
	 * 字段验证
	 */
	validation : function(){
		validatorForm = $('#monitoringForm_bottom').bootstrapValidator({
		       message: '数据不合法',
		       feedbackIcons: {
		          invalid:    'glyphicon glyphicon-remove',
		          validating: 'glyphicon glyphicon-refresh'
		       },
		       fields: {
		    	   hbh: {
		            validators: {
		            	notEmpty: {message: '航班号不能为空'}
		            }
			      }, 
			      zlh: {
		            validators: {
		            	notEmpty: {message: '指令号不能为空'}
		           }
				  } 
		       }
		   });
	},
	//初始化航班号
	openHb: function(){
   	 hbModal.show({
			selected : $("#hbh").val(),
			callback : function(data) {
				$("#hbh").val(formatUndefine(data.paramsMap.hbh));
				$("#hbrq").val(formatUndefine(data.fxrq.substring(0,10)));
				$("#fxjldid").val(data.id);
				
				$('#monitoringForm_bottom').data('bootstrapValidator')  
	 		      .updateStatus("hbh", 'NOT_VALIDATED',null)  
	 		      .validateField("hbh"); 
			},
		});
	},
	//初始化指令号
	openZlh: function(){
		var this_ = this;
	   	 zsjxxjl=this.zsjxxjl;
		 cxjxxjl=this.cxjxxjl;
		 var list = [];
			var a = $("#zlhid").val();
			var b = $("#zlh").val();
			for (var i = 0; i < a.split(",").length; i++) {
				var p = {
					id : a.split(",")[i],
					gdbh : b.split(",")[i]
				};
				list.push(p);
			}
			if (a == "") {
				list = [];
			}
			alertModalZl.show({
			 	checkUserList : list,
				callback : function(data) {
					console.info(data);
					var zlhid='';//工单id
					var zlh = '';//工单编号
					if (data != null && data != "") {
						$.each(data, function(i, row) {
							zlh += row.gdbh+",";
							zlhid += row.id+",";
							this_.getZsCx(row.id);
						});
					}
					if (zlh != '') {
						zlh = zlh.substring(0, zlh.length - 1);
						$("#zlh").val(zlh);
					}
					if (zlhid != '') {
						zlhid = zlhid.substring(0, zlhid.length - 1);
						$("#zlhid").val(zlhid);
					}
					if(cxjxxjl !=''){
						cxjxxjl = cxjxxjl.substring(0, cxjxxjl.length -1);
						$("#cxjxx").val(cxjxxjl);
					}
					if(zsjxxjl !=''){
						zsjxxjl = zsjxxjl.substring(0, zsjxxjl.length -1);
						$("#zsjxx").val(zsjxxjl);
					}
					
//					$("#monitoringForm_bottom").data('bootstrapValidator').destroy();
//					$('#monitoringForm_bottom').data('bootstrapValidator', null);
					Fault_Handling_Record_Open_Modal.validation();
					
					$('#monitoringForm_bottom').data('bootstrapValidator')  
		 		      .updateStatus("zlh", 'NOT_VALIDATED',null)  
		 		      .validateField("zlh"); 
				},
				
			});
	},
	//查询工单
	getZsCx: function(id){
	   	 var param={};
	   	 /**原方法，可做对照参考
	   	  * param.fxjldid=id;
	   	  * var url = basePath+ "/produce/fault/monitoring/getZsCx";
	   	  */
	   	 param.id = id;
		 var url = basePath+ "/produce/fault/monitoring/getZsCxInfo";
		 AjaxUtil.ajax({
				url  : url,
				type : "post",
				data : JSON.stringify(param),
				async: false,
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				success : function(data) {
					
					if(data.zs!=null&&data.zs!=""&&data.zs!=undefined){
							zsjxxjl+=(data.zs+",");
						}
					if(data.cx!=null&&data.cx!=""&&data.cx!=undefined){
							cxjxxjl+=(data.cx+",");
					}
				
				}
			});
	},
	//保存
	saveinfo: function(){
   	 startWait($("#alertModalDcgz"));
		$('#form').data('bootstrapValidator').validate();
		if (!$('#form').data('bootstrapValidator').isValid()) {
			finishWait($("#alertModalDcgz"));
			return false;
		}
	 var attachments = [];
		var responses = uploadObj.responses;
		var len = uploadObj.responses.length;
		if (len != undefined && len > 0) {
			for (var i = 0; i < len; i++) {
				attachments.push({
					'yswjm' : responses[i].attachments[0].yswjm,
					'wbwjm' : responses[i].attachments[0].wbwjm,
					'nbwjm' : responses[i].attachments[0].nbwjm,
					'wjdx' : responses[i].attachments[0].wjdx,
					'cflj' : responses[i].attachments[0].cflj,
					'id' : responses[i].attachments[0].id,
					'operate' : responses[i].attachments[0].operate

				});
			}
		}
		var dellen = delAttachements.length;
		if (dellen != undefined && dellen > 0) {
			for (var i = 0; i < dellen; i++) {
				attachments.push({
					'id' : delAttachements[i].id,
					'operate' : 'DEL'

				});
			}
		}
	 var param = {};
	 param.attachments = attachments;
	 param.mainid=$("#manidinfo").val();
	 param.fjzch=$("#fjzchid").val();
	 param.fxjldid=$("#fxjldid").val();
	 param.hbh=$("#hbh").val();
	 param.hbrq=$("#hbrq").val();
	 param.zlh=$("#zlh").val();
	 param.pgsl=$("#pgsl").val();
	 param.cljg=$("#cljg").val();
	 param.cxjxx=$("#cxjxx").val();
	 param.zsjxx=$("#zsjxx").val();
	 param.bz=$("#bz").val();
	 param.gdid=$("#zlhid").val();
	 param.zt=1;
	 param.dprtcode=$("#dprtId").val();
	 param.whdwid=$("#whdwid").val();
	 param.whrid=$("#gbrid").val();
	 if($("#infoId").val()!=''){
		 param.id=$("#infoId").val();
	 }else{
		 param.id="";
	 }
	 AjaxUtil.ajax({
		url : basePath
				+ "/produce/fault/monitoring/addGzcl",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		modal : $("#alertModalDcgz"),
		success : function(data) {
//			AlertUtil.showMessage('保存成功!',
//					'/produce/fault/monitoring/main?id='+$("#manidinfo").val()+'&pageParam=' + encodePageParam());
			$("#alertModalDcgz").modal("hide");
			$("#alertModalZl").modal("hide");
			$("#alertModalHb").modal("hide");
			finishWait($("#alertModalDcgz"));
			pagination=mainpagination;
			pageParam=encodePageParam()
			finishWait();
			showInfo(1, "desc", "auto");
			decodePageParam();
			refreshPermission();
		}
	});
	},
};

$(document).ready(function(){

	
});



