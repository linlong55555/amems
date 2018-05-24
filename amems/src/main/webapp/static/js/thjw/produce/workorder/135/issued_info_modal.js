issued_info_modal = {
	id:'issued_info_modal',
	data :[],
	wpId:"",
	param:{
		id:null,
		dprtcode:null,
		fjzch:null,
		msn:null,
		fjjx:null,
		gdbt:null,
		gdbh:null,
	},
	show:function(param){
		if(param){
			$.extend(this.param, param);
		}
		$("#"+this.id+" select").empty();
		$("#"+this.id+"_wplist").html("");
		this.initModal();
		this.loadWp();
		DicAndEnumUtil.registerDic('71',this.id+'_wxlx',this.param.dprtcode);
		AlertUtil.hideModalFooterMessage();
		$("#"+this.id).modal("show");
		$('input:radio[name="issued_info_modal_yjzxdw_radio"]').change( function(){
			var yjzxdw = $(this).val();
				if(yjzxdw==0){
					$("#issued_info_modal_yjzxdw_bmdiv").show();
					$("#issued_info_modal_yjzxdw_gysdiv").hide();
					$("#issued_info_modal_gysid").val("");
					$("#issued_info_modal_gys").val("");
				}else{
					$("#issued_info_modal_yjzxdw_bmdiv").hide();
					$("#issued_info_modal_yjzxdw_gysdiv").show();
					$("#issued_info_modal_yjzxdw").val("");
					$("#issued_info_modal_yjzxdwid").val("");
				}
		});
	},
	loadWp:function(){
		var this_ = this;
		var param = {};
		param.fjzch = this_.param.fjzch;
		param.dprtcode = this_.param.dprtcode;
		AjaxUtil.ajax({
			url:basePath+"/produce/workpackage/getIssuedWp",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			success:function(data){
				if(data.length>0){
					this_.data = data;
					this_.appendHtml(data);
				}
				this_.addWp();
			}
		});
	},
	appendHtml:function(list){		
		var html = "";
		$.each(list,function(index,row){
			if(index == 0){
				html += "<li ><a href='#' index='"+index+"' onclick=issued_info_modal.loadWpDetail(this) title='"+row.gbbh+"' >"+row.gbbh+"</a></li>";
			}else{
				html += "<li ><a href='#' index='"+index+"' onclick=issued_info_modal.loadWpDetail(this) title='"+row.gbbh+"' >"+row.gbbh+"</a></li>";
			}		
		});
		$("#"+this.id+"_wplist").append(html);
	},
	loadWpDetail:function(obj){
		$(obj).parent().parent("ul").find("li").removeClass("active");
		$(obj).parent("li").addClass("active");
		this.fillData($(obj).attr("index"));
	},
	fillData:function(index){
		var this_ = this;
		this_.initModal();
		var data = this_.data[index];
		this_.wpId=data.id;
		$("#"+this_.id+"_workpackageName").html("工包"+data.gbbh);
		$("#"+this_.id+"_fjzch").attr("disabled","disabled");
		$("#"+this_.id+"_gbbh").attr("disabled","disabled");
		$("#"+this_.id+"_gbzt_div").show();
		$("#"+this_.id+"_fjzch").val(data.fjzch);
		$("#"+this_.id+"_msn").val(data.aircrafInfo==null?"":data.aircrafInfo.xlh);
		$("#"+this_.id+"_jx").val(data.aircrafInfo==null?"":data.aircrafInfo.fjjx);
		$("#"+this_.id+"_gbbh").val(data.gbbh);
		$("#"+this_.id+"_ms").val(data.gbmc);
		$("#"+this_.id+"_zdrq").val(data.zdrq==null?"":data.zdrq.substring(0,10));
		$("#"+this_.id+"_zdrq").attr("disabled","disabled");
		$("#"+this_.id+"_jhksrq").val(data.jhKsrq==null?"":data.jhKsrq.substring(0,10));
		$("#"+this_.id+"_jhwcrq").val(data.jhJsrq==null?"":data.jhJsrq.substring(0,10));
		$("#"+this_.id+"_wxlx").val(data.wxlx);
		var yjzxdw=data.jhWwbs;
		this_.checkRadio(yjzxdw);
		if(yjzxdw==0){
			$("#"+this_.id+"_yjzxdw_bmdiv").show();
			$("#"+this_.id+"_yjzxdw_gysdiv").hide();
			$("#"+this_.id+"_yjzxdwid").val(data.jhZxdwid);
			$("#"+this_.id+"_yjzxdw").val(data.jhZxdw);
			$("#"+this_.id+"_yjzxdw_do").val(data.jhZxdw);
		}else{
			$("#"+this_.id+"_yjzxdw_bmdiv").hide();
			$("#"+this_.id+"_yjzxdw_gysdiv").show();
			$("#"+this_.id+"_gysid").val(data.jhZxdwid);
			$("#"+this_.id+"_gys").val(data.jhZxdw);
		}
		$("#"+this_.id+"_xfdwid").val(StringUtil.escapeStr(data.gbxfdwid));
		$("#"+this_.id+"_xfdw").val(StringUtil.escapeStr(data.xfdwDepartment==null?"":data.xfdwDepartment.dprtname));
		$("#"+this_.id+"_gbzt").val(DicAndEnumUtil.getEnumName('workpackageStatusEnum', data.zt));
		$("#"+this_.id+"_gzyq").val(data.gzyq);
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents(this.id+'_attachments_list_edit');
		attachmentsObj.show({
			djid:data.id,
			fileHead : false,
			colOptionhead : false,
			fileType:"workpackage",
		});//显示附件
	},
	checkRadio:function(obj){
		var this_=this;
		var xfdw = document.getElementsByName(this_.id+"_yjzxdw_radio");
		for(var i=0;i<xfdw.length;i++){
			if(xfdw[i].value==obj){
				xfdw[i].checked='checked';
			}
		}
	},
	initModal:function(){
		this.wpId = "";
		$("#"+this.id+" input[type='text']").val("");
		$("#"+this.id+" input[type='hidden']").val("");
		$("#"+this.id+" textarea").val("");
		$("#"+this.id+" input[type='text']").attr("disabled",true);
		$("#"+this.id+" select").attr("disabled",true);
		$("#"+this.id+" input[type='radio']").attr("disabled",true);
		this.checkRadio(0);
		$("#"+this.id+"_xfdwbtn").hide();
		$("#"+this.id+"_nbzxdwbtn").hide();
		$("#"+this.id+"_wbzxdwbtn").hide();
		$("#"+this.id+"_gbbhmark").hide();
		$("#"+this.id+"_msmark").hide();
		$("#"+this.id+" textarea").attr("disabled",true);
		$("#"+this.id+"_xfdw").addClass("div-readonly-style").removeClass("readonly-style");
		$("#"+this.id+"_gys").addClass("div-readonly-style").removeClass("readonly-style");
	},
	addWp:function(){
		var this_ = this;
		this_.clearModal();
		$("#"+this_.id+"_workpackageName").html("");
		$("#"+this_.id+"_fjzch").val(this_.param.fjzch);
		$("#"+this_.id+"_msn").val(this_.param.msn);
		$("#"+this_.id+"_jx").val(this_.param.fjjx);
		$("#"+this_.id+"_gbbh").val(this_.param.gdbh);
		$("#"+this_.id+"_ms").val(this_.param.gdbt);
		TimeUtil.getCurrentDate("#"+this.id+"_zdrq");
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents(this.id+'_attachments_list_edit');
		attachmentsObj.show({
			djid:"",
			fileHead : true,
			colOptionhead : true,
			fileType:"workpackage",
		});//显示附件
		$("#issued_info_modal_wplist").find("li").removeClass("active");
	},
	clearModal:function(){
		this.wpId = "";
		$("#"+this.id+" input[type='text']").val("");
		$("#"+this.id+" input[type='hidden']").val("");
		$("#"+this.id+" textarea").val("");
		$("#"+this.id+" input[type='text']").attr("disabled",false);
		$("#"+this.id+" select").attr("disabled",false);
		$("#"+this.id+" input[type='radio']").attr("disabled",false);
		$("#"+this.id+"_xfdwbtn").show();
		$("#"+this.id+"_nbzxdwbtn").show();
		$("#"+this.id+"_wbzxdwbtn").show();
		$("#"+this.id+"_gbbhmark").show();
		$("#"+this.id+"_msmark").show();
		$("#"+this.id+"_gbzt_div").hide();
		$("#"+this.id+" textarea").attr("disabled",false);
		$("#"+this.id+"_msn").attr("disabled",true);
		$("#"+this.id+"_jx").attr("disabled",true);
		$("#"+this.id+"_fjzch").attr("disabled",true);
		$("#"+this.id+"_xfdw").addClass("readonly-style").removeClass("div-readonly-style");
		$("#"+this.id+"_gys").addClass("readonly-style").removeClass("div-readonly-style");
		this.checkRadio(0);
		$("#issued_info_modal_yjzxdw_bmdiv").show();
		$("#issued_info_modal_yjzxdw_gysdiv").hide();
	},
	validation:function(){
		var this_=this;
		validatorForm = $("#"+this_.id+"_form").bootstrapValidator({
			message : '数据不合法',
			feedbackIcons : {
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				issued_info_modal_gbbh : {
					validators : {
						notEmpty : {
							message : '工包编号不能为空'
						},
						regexp : {
							regexp : /^[a-zA-Z0-9-_\x21-\x7e]{1,50}$/,
							message : '只能输入长度不超过50个字符的英文、数字、英文特殊字符!'
						}
					}
				},
				issued_info_modal_ms : {
					validators : {
						notEmpty : {
							message : '描述不能为空'
						},
					}
				}
			}
		})
	},
	openGys:function(){
		var this_=this;
		  ManufacturerModal.show({
				selected : $("#"+this_.id+"_gysid").val(),// 对象ID
				parentid : "issued_info_modal",
				callback : function(data) {// 回调函数
					if(data){
						$('#'+this_.id +'_gysid').val(data.id);
						$('#'+this_.id +'_gys').val(data.gysbm+" "+data.gysmc);
					}
				}
			}); 
	},
	openzrdw : function(obj,jdbs){
		var this_ = this;
		var dprtname = $("#"+this_.id+"_"+obj).val();
		var dprtcode = $("#"+this_.id+"_"+obj+"id").val();
		departmentModal.show({
			dprtnameList : dprtname,// 部门名称
			dprtcodeList : dprtcode,// 部门id
			jdbs:jdbs,
			type : false,// 勾选类型true为checkbox,false为radio
			dprtcode : this_.param.dprtcode,// 默认登录人当前机构代码
			callback : function(data) {// 回调函数
				$("#"+this_.id+"_"+obj).val(data.dprtname);
				$("#"+this_.id+"_"+obj+"id").val(data.dprtcode);
				if(jdbs=='1'){
					$("#"+this_.id+"_"+obj+"_do").val(data.dprtname);
				}
			}
		})
	},
	getData:function(){
		var this_=this;
		var param={};
		param.dprtcode=this_.param.dprtcode;
		param.fjzch=$.trim($("#"+this_.id+"_fjzch").val());
		param.gbbh=$.trim($("#"+this_.id+"_gbbh").val());
		param.gbmc=$.trim($("#"+this_.id+"_ms").val());
		param.gbxfdwid=$.trim($("#"+this_.id+"_xfdwid").val());
		param.wxlx=$.trim($("#"+this_.id+"_wxlx").val());
		param.zdrq=$.trim($("#"+this_.id+"_zdrq").val());
		var jhWwbs=$.trim($("input[name='"+this_.id+"_yjzxdw_radio']:checked").val());
		param.jhWwbs=jhWwbs;
		if(jhWwbs==0){
			if($.trim($("#"+this_.id+"_yjzxdw").val())==$.trim($("#"+this_.id+"_yjzxdw_do").val())){
				param.jhZxdw =$.trim($("#"+this_.id+"_yjzxdw").val());
				param.jhZxdwid=$.trim($("#"+this_.id+"_yjzxdwid").val());
			}else{
				param.jhZxdw =$.trim($("#"+this_.id+"_yjzxdw").val());
			}
			
		}else{
			param.jhZxdwid=$.trim($("#"+this_.id+"_gysid").val());
			param.jhZxdw =$.trim($("#"+this_.id+"_gys").val());
		}				
		param.jhKsrq=$.trim($("#"+this_.id+"_jhksrq").val());
		param.jhJsrq=$.trim($("#"+this_.id+"_jhwcrq").val());
		param.gzyq=$.trim($("#"+this_.id+"_gzyq").val());
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents(this_.id+'_attachments_list_edit');
		param.attachments = attachmentsObj.getAttachments();
		return param;
	},
	saveData:function(){
		var this_ = this;
		var data = {};
		var url = "";
		if(this_.wpId ==""){//工包为新增的
			if($.trim($("#"+this_.id+"_gbbh").val()) == ''|| null == $.trim($("#"+this_.id+"_gbbh").val())){
				AlertUtil.showModalFooterMessage("工包号不能为空!");
				return false;
			}
			if($.trim($("#"+this_.id+"_ms").val()) =='' || null == $.trim($("#"+this_.id+"_ms").val())){
				AlertUtil.showModalFooterMessage("描述不能为空!");
				return false;
			}
			if($("#"+this_.id+"_jhksrq").val() != '' && $("#"+this.id+"_jhwcrq").val() != '' && $("#"+this.id+"_jhksrq").val()>$("#"+this.id+"_jhwcrq").val()){
				AlertUtil.showModalFooterMessage("计划开始日期不能晚于计划完成日期!");
				return false;
			}
			if($("#"+this_.id+"_zdrq").val() == ''){
				AlertUtil.showModalFooterMessage("制单日期不能为空!");
				return false;
			}
			data = this_.getData();
			var paramsMap={};
			paramsMap.woId = this_.param.id;
			data.paramsMap = paramsMap;
			url = basePath+"/produce/workorder/doWoIssued";
		}else{//选择的工包
			data.gbid = this_.wpId;
			data.id = this_.param.id;
			url = basePath+"/produce/workorder/doIssued";
		}
		this_.doRequest(data,url);
	},
	doRequest:function(data,url){
		var this_ = this;
		startWait($("#"+this_.id));
		AjaxUtil.ajax({
			url:url,
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(data),
			dataType:"json",
			modal:$("#issued_info_modal"),
			success:function(data){
				finishWait($("#"+this_.id));
				$("#"+this_.id).modal("hide");
				id = this_.param.id;		
				pageParam=workorder135_main.encodePageParam();
				workorder135_main.decodePageParam();
				AlertUtil.showMessage("下发成功!");
			}
		});
	}
}
//自定义alert效果
function customAlertResizeHeight(modalBodyHeight){
	var actualHeight=parseInt($("#"+issued_info_modal.id).find(".modal-body").css("height"));
	var maxHeight=parseInt($("#"+issued_info_modal.id).find(".modal-body").css("max-height"));
	var leftHeight=$("#issued_info_modal_wplist").outerHeight();
	var rightIssuedInfo=$("#right_issued_info").outerHeight();
	if(leftHeight<rightIssuedInfo){
		if(rightIssuedInfo+60<maxHeight){
			$("#issued_info_modal_wplist").css("min-height",rightIssuedInfo+"px")
			$("#issued_info_modal_wplist").parent("div").css({"height":rightIssuedInfo+"px","overflow":"auto"});
			$("#right_issued_info").css({"height":rightIssuedInfo+"px","overflow":"auto"});
		}else{
			$("#issued_info_modal_wplist").css("min-height",maxHeight-60+"px")
			$("#issued_info_modal_wplist").parent("div").css({"height":maxHeight-60+"px","overflow":"auto"});
			$("#right_issued_info").css({"height":maxHeight-60+"px","overflow":"auto"});
		}
	}else{
		if(leftHeight+60<maxHeight){
			$("#issued_info_modal_wplist").css("min-height",leftHeight+"px")
			$("#issued_info_modal_wplist").parent("div").css({"height":leftHeight+"px","overflow":"auto"});
			$("#right_issued_info").css({"height":leftHeight+"px","overflow":"auto"});
		}else{
			$("#issued_info_modal_wplist").css("min-height",modalBodyHeight-60+"px")
			$("#issued_info_modal_wplist").parent("div").css({"height":modalBodyHeight-60+"px","overflow":"auto"});
			$("#right_issued_info").css({"height":modalBodyHeight-60+"px","overflow":"auto"});
		}
	}
	$("#issued_info_modal_wplist").prop('scrollTop','0');
	$("#right_issued_info").prop('scrollTop','0');
}
function hideAlertResizeHeight(){
	$("#issued_info_modal_wplist").css("min-height","auto")
	$("#issued_info_modal_wplist").parent("div").css({"height":"auto","overflow":"auto"});
	$("#right_issued_info").css({"height":"auto","overflow":"auto"});
	$("#"+issued_info_modal.id).find(".modal-body").css("max-height","auto")
}