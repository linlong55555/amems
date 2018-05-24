var delAttachements = [];
var lxjcjgDiv=document.getElementById('lxjcjg-div');
$(document).ready(function() {
	Navigation(menuCode);
	decodePageParam();
	var sjlx=parseInt($("#sjlx").val());
	logModal.init({
		code : 'B_G_015',extparam:{SJLX:sjlx}
	});

	$("#fxrq_search").daterangepicker().prev().on("click", function() {
		$(this).next().focus();
	});
	$("#xlrq_search").daterangepicker().prev().on("click", function() {
		$(this).next().focus();
	});
	$('#fxrq').datepicker({
		autoclose : true,
		clearBtn : true
	}).on('hide', function(e) {
		  $('#form').data('bootstrapValidator')  
		     .updateStatus('fxrq', 'NOT_VALIDATED',null)  
		     .validateField('fxrq');
		  
});
	$('#xlrq').datepicker({
		autoclose : true,
		clearBtn : true
	}).on('hide', function(e) {
		  $('#form').data('bootstrapValidator')  
		     .updateStatus('xlrq', 'NOT_VALIDATED',null)  
		     .validateField('xlrq');
		  
});;
	refreshPermission();
	validation();
	$(' input[name="is_xlxjc"]').click(function(){
		  if($(this).val()==0){
			  lxjcjgDiv.style.display="none";
			  $("#lxjcjg").val("");
		  }else{
			  lxjcjgDiv.style.display="block";
		  }
		  });
});
var pagination = {};
/**
 * 编码页面查询条件和分页
 * 
 * @returns
 */
function encodePageParam() {
	var pageParam = {};
	var params = {};
	params.keyword = $.trim($("#keyword_search").val());
	params.dprtcode = $.trim($("#dprtcode_search").val());
	params.fxrq = $.trim($("#fxrq_search").val());
	params.xlrq = $.trim($("#xlrq_search").val());
	params.xlfs = $.trim($("#xlfs_search").val());
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

/**
 * 解码页面查询条件和分页 并加载数据
 * 
 * @returns
 */
function decodePageParam() {
	try {
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#keyword_search").val(params.keyword);
		$("#fxrq_search").val(params.fxrq);
		$("#xlrq_search").val(params.xlrq);
		$("#xlfs_search").val(params.xlfs);
		$("#dprtcode_search").val(params.dprtcode);
		if (pageParamJson.pagination) {
			goPage(pageParamJson.pagination.page,
					pageParamJson.pagination.order,
					pageParamJson.pagination.sort);
		} else {
			goPage(1, "desc", "auto");
		}
	} catch (e) {
		goPage(1, "desc", "auto");
	}
}

function goPage(pageNumber, sortType, sortField) {
	AjaxGetDatasWithSearch(pageNumber, sortType, sortField);
}

function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
	var searchParam = {};
	searchParam.keyword = $.trim($("#keyword_search").val());
	var paramsMap={};
	var fxrq = $.trim($("#fxrq_search").val());
	if(null != fxrq && "" != fxrq){
		paramsMap.fxrqDateBegin = fxrq.substring(0,4)+"-"+fxrq.substring(5,7)+"-"+fxrq.substring(8,10)+" 00:00:00";
		paramsMap.fxrqDateEnd = fxrq.substring(12,17)+"-"+fxrq.substring(18,20)+"-"+fxrq.substring(21,23)+" 23:59:59";
	}
	var xlrq = $.trim($("#xlrq_search").val());
	if(null != xlrq && "" != xlrq){
		paramsMap.xlrqDateBegin = xlrq.substring(0,4)+"-"+xlrq.substring(5,7)+"-"+xlrq.substring(8,10)+" 00:00:00";
		paramsMap.xlrqDateEnd = xlrq.substring(12,17)+"-"+xlrq.substring(18,20)+"-"+xlrq.substring(21,23)+" 23:59:59";
	}
	var dprtcode = $.trim($("#dprtcode_search").val());
	if (dprtcode != '') {
		searchParam.dprtcode = dprtcode;
	}
	searchParam.sjlx=$("#sjlx").val();
	var xlfs = $.trim($("#xlfs_search").val());
	if (xlfs != '') {
		searchParam.xlfs =xlfs;
	}
	pagination = {
		page : pageNumber,
		sort : sequence,
		order : sortType,
		rows : 20
	};
	searchParam.pagination = pagination;
	if (id != "") {
		searchParam.id = id;
		id = "";
	}
	searchParam.paramsMap=paramsMap;
	startWait();
	AjaxUtil.ajax({
		url : basePath + "/project/shapestructurerepair/queryList",
		type : "post",
		data : JSON.stringify(searchParam),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			finishWait();
			if (data.total > 0) {
				appendContentHtml(data.rows);
				new Pagination({
					renderTo : "pagination",
					data : data,
					sortColumn : sortType,
					orderType : sequence,
					goPage : function(a, b, c) {
						AjaxGetDatasWithSearch(a, b, c);
					}
				});
				// 替换null或undefined
				FormatUtil.removeNullOrUndefined();
				// 标记关键字
				signByKeyword($.trim($("#keyword_search").val()),
						[ 2,3,4,5,10 ],"#list tr td")
			} else {
				$("#list").empty();
				$("#pagination").empty();
				$("#list").append(
						"<tr><td colspan=\"16\">暂无数据 No data.</td></tr>");
			}
			new Sticky({
				tableId : 'repairRecord_check_list_table'
			});
		}
	});

}

var choses=[];
var choseAllIds=[];
function appendContentHtml(list) {
	choses=[];
	choseAllIds=[];
	var htmlContent = '';
	$.each(list,function(index, row) {
						htmlContent +=  "<tr>";
						htmlContent +=	"<td style=\"vertical-align: middle;\" class='fixed-column text-center'><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project:shapestructurerepair:main:02,project:shapestructurerepair:main1:02' onClick=\"update('"+ row.id +"','"+row.xlqfj+"','"+row.xlhfj+ "')\" title='修改 Edit'></i></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='project:shapestructurerepair:main:03,project:shapestructurerepair:main1:03' onClick=\"Delete('"+ row.id
												+ "')\" title='删除  Delete'></i>&nbsp;&nbsp;<i class='icon-search color-blue cursor-pointer checkPermission' permissioncode='project:shapestructurerepair:main:02,project:shapestructurerepair:main1:02' title='查看  View' onClick=\"view('"+ row.id +"')\" ></i></td>";
						htmlContent +=	"<td style=\"vertical-align: middle;\" class='text-left'>"+ row.fjzch+ "</td>";
						htmlContent +=	"<td style=\"vertical-align: middle;\" class=' text-left' title='"+StringUtil.escapeStr(row.zjh)+" "+StringUtil.escapeStr(row.fixChapter!=null?row.fixChapter.ywms:"")+ "' >"+StringUtil.escapeStr(row.zjh)+" "+StringUtil.escapeStr(row.fixChapter!=null?row.fixChapter.ywms:"")+ "</td>";
						htmlContent +=	"<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.qxms) + "'>"+ StringUtil.escapeStr(row.qxms) + "</td>";
						htmlContent +=	"<td style=\"vertical-align: middle;\" class='text-left' title='"+ StringUtil.escapeStr(row.wz) + "'>"+ StringUtil.escapeStr(row.wz) + "</td>";
						htmlContent +=	"<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
						if(row.attachments && row.attachments.length > 0){
							htmlContent += '<i qid="'+row.xlqfj+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
						}
						htmlContent +=	"</td>";
										/*"<td style=\"vertical-align: middle;\" class='text-left' >"+ getAttacment(row.attachments,"xlq",row.id) + "</td>",*/
						htmlContent += "<td style=\"vertical-align: middle;\" class='text-center' title='"+ StringUtil.escapeStr(row.fxrq.substring(0,10)) + "'>"+ StringUtil.escapeStr(row.fxrq.substring(0,10)) + "</td>";
						htmlContent += "<td style=\"vertical-align: middle;\" class='text-center' title='"+ StringUtil.escapeStr(row.xlrq.substring(0,10)) + "'>"+ StringUtil.escapeStr(row.xlrq.substring(0,10)) + "</td>";
						htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' >"+ (row.xlfs==1?"永久修理":"临时修理") + "</td>";
						htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+ StringUtil.escapeStr(row.xlyj) + "'>"+ StringUtil.escapeStr(row.xlyj) + "</td>";
						htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
						console.info(row.xlyjAttachment);
						if(row.xlyjAttachment && row.xlyjAttachment.length > 0){
							htmlContent += '<i xlyjfj="'+row.xlyjfj+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
						}
						htmlContent += "</td>",
						htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+ StringUtil.escapeStr(row.lxjcjg) + "'>"+ StringUtil.escapeStr(row.lxjcjg) + "</td>";
						htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
						if(row.xlhAttachment && row.xlhAttachment.length > 0){
							htmlContent += '<i hid="'+row.xlhfj+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
						}
						htmlContent += "</td>",
										/*"<td style=\"vertical-align: middle;\" class='text-left' >"+ getAttacment(row.xlhAttachment,"xlh",row.id) + "</td>",*/
						htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+(row.whr==null?"":StringUtil.escapeStr(row.whr.username)+' '+StringUtil.escapeStr(row.whr.realname))+"'>" + (row.whr==null?"":StringUtil.escapeStr(row.whr.username)+' '+StringUtil.escapeStr(row.whr.realname))+ "</td>";
						htmlContent += "<td style=\"vertical-align: middle;\" class='text-center'>" + row.whsj + "</td>";
						htmlContent += "<td style=\"vertical-align: middle;\" class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode)) + "</td>";
						htmlContent += "</tr>";
					});
	$("#list").empty();
	$("#list").html(htmlContent);
	initWebuiPopover();
}
function initWebuiPopover(){//初始化
	WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
		return getHistoryAttachmentList(obj);
	});
	$("#structrucRepair_table").scroll(function(){
		$('.attachment-view').webuiPopover('hide');
	});
}
function getHistoryAttachmentList(obj){//获取历史附件列表
	var jsonData = [
         {mainid : $(obj).attr('qid'), type : '修理前照片'},
         {mainid : $(obj).attr('hid'), type : '修理后照片'},
         {mainid : $(obj).attr('xlyjfj'), type : '修理依据附件'}
    ];
	return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
}


function searchRecord() {
	TableUtil.resetTableSorting("repairRecord_check_list_table");
	goPage(1, "desc", "auto");
}

function more() {
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {

		$("#divSearch").css("display", "none");
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
}

function add(type) {
	if(type){
		$("#Cname").html("外形缺损");
		$("#Ename").html("Shape Defect");
	}else{
		$("#Cname").html("结构修理");
		$("#Ename").html("Structural Repair");
	}
	clearData();
	loadFjzch();
	$("#wxrybutton").show();
	$("#xlyjmark").show();
	$("#xlrqmark").show();
	$("#qxmsmark").show();
	$("#wzmark").show();
	$("#fxrqmark").show();
	$("#fjzchmark").show();
	$("#operat").show();
	$("#operat1").show();
	$("#xlqfile-div").show();
	$("#xlhfile-div").show();
	lxjcjgDiv.style.display="block";
	$("#addModal input").attr("disabled", false);
	$("#addModal textarea").attr("disabled", false);
	$("#addModal select").attr("disabled", false);
	$("#fileuploader").attr("disabled", false);
	$("#addModal i").attr("onclick");
	$("#wxrybtn").removeAttr("disabled");
	$("#save").show();
	$("#addModal").modal("show");
	$('#addModal').on('shown.bs.modal', function() {
		AlertUtil.hideModalFooterMessage();
		ModalUtil.modalBodyHeight("addModal");
	})
}


function saveRecord() {
	$('#form').data('bootstrapValidator').validate();
	if (!$('#form').data('bootstrapValidator').isValid()) {
		AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
		finishWait($("#addModal"));
		return false;
	}
	if($.trim($('#fxrq').val())>$.trim($('#xlrq').val())){
		AlertUtil.showModalFooterMessage("发现日期日期不能大于修理日期！");
		finishWait($("#addModal"));
		return false
	}
	var attachments = [];
	var xlhAttachment=[];
	var xlyjAttachment=[];
	
	var responses = uploadObj.responses;
	var len = uploadObj.responses.length;
	
	var lxhresponses=xlhuploadObj.responses;
	var lxhlen = xlhuploadObj.responses.length;
	
	var xlyjresponses=xlyjuploadObj.responses;
	var xlyjlen = xlyjuploadObj.responses.length;
	
	if (len != undefined && len > 0) {
		for (var i = 0; i < len; i++) {
			attachments.push({
				'yswjm' : responses[i].attachments[0].yswjm,
				'wbwjm' : responses[i].attachments[0].wbwjm,
				'nbwjm' : responses[i].attachments[0].nbwjm,
				'wjdx' : responses[i].attachments[0].wjdx,
				'cflj' : responses[i].attachments[0].cflj,
				'id' : responses[i].attachments[0].id,
				'hzm' : responses[i].attachments[0].hzm,
				'operate' : responses[i].attachments[0].operate

			});
		}
	}
	if (lxhlen != undefined && lxhlen > 0) {
		for (var i = 0; i < lxhlen; i++) {
			xlhAttachment.push({
				'yswjm' : lxhresponses[i].attachments[0].yswjm,
				'wbwjm' : lxhresponses[i].attachments[0].wbwjm,
				'nbwjm' : lxhresponses[i].attachments[0].nbwjm,
				'wjdx' : lxhresponses[i].attachments[0].wjdx,
				'cflj' : lxhresponses[i].attachments[0].cflj,
				'id' : lxhresponses[i].attachments[0].id,
				'hzm' : lxhresponses[i].attachments[0].hzm,
				'operate' : lxhresponses[i].attachments[0].operate

			});
		}
	}
	if (xlyjlen != undefined && xlyjlen > 0) {
		for (var i = 0; i < xlyjlen; i++) {
			xlyjAttachment.push({
				'yswjm' : xlyjresponses[i].attachments[0].yswjm,
				'wbwjm' : xlyjresponses[i].attachments[0].wbwjm,
				'nbwjm' : xlyjresponses[i].attachments[0].nbwjm,
				'wjdx' : xlyjresponses[i].attachments[0].wjdx,
				'cflj' : xlyjresponses[i].attachments[0].cflj,
				'id' : xlyjresponses[i].attachments[0].id,
				'hzm' : xlyjresponses[i].attachments[0].hzm,
				'operate' : xlyjresponses[i].attachments[0].operate
				
			});
		}
	}
	/*var dellen = delAttachements.length;
	if (dellen != undefined && dellen > 0) {
		for (var i = 0; i < dellen; i++) {
			attachments.push({
				'id' : delAttachements[i].id,
				'operate' : 'DEL'

			});
		}
	}*/
	var param = {};
	param.delAttachements = delAttachements;
	param.fjzch=$("#fjzch").val();
	param.sjlx=$("#sjlx").val();
	param.fxrq = $("#fxrq").val().substring(0, 10);
	param.zjh = $("#zjhid").val();
	param.wz = $.trim($("#wz").val());
	param.qxms = $.trim($("#qxms").val());
	param.xlrq = $("#xlrq").val().substring(0, 10);
	param.xlfs = $(' input[name="xlfs"]:checked ').val();
	param.xlyj=$.trim($("#xlyj").val());
	param.is_xlxjc = $(' input[name="is_xlxjc"]:checked ').val();
	param.lxjcjg=$.trim($("#lxjcjg").val());
	param.is_gb = $(' input[name="is_gb"]:checked ').val();
	param.is_jrwxfq = $(' input[name="is_jrwxfq"]:checked ').val();
	param.whbmid = $("#whbmid").val();
	param.whrid = $("#whrid").val();
	param.id = $("#id").val();
	if($("#id").val()!=''){
	param.dprtcode=$("#dpt").val();
	}
	if($("#id").val()==''){
		param.dprtcode=$("#dprtcode").val();
	}
	param.zt=1;
	param.attachments = attachments;
	param.xlhAttachment = xlhAttachment;
	param.xlyjAttachment =xlyjAttachment;
	startWait($("#addModal"));
	AjaxUtil.ajax({
		url : basePath
				+ "/project/shapestructurerepair/addRecord",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		modal : $("#addModal"),
		success : function(data) {
			$("#addModal").modal("hide");
			finishWait($("#addModal"));
			goPage(1, "desc", "auto");
			/*if($("#sjlx").val()==1){
			AlertUtil.showMessage('保存成功!',
					'/project/shapestructurerepair/main?id=' + data
							+ '&pageParam=' + encodePageParam());
			finishWait();
			refreshPermission();
			uploadObj.responses = [];
			}
			if($("#sjlx").val()==2){
				AlertUtil.showMessage('保存成功!',
						'/project/shapestructurerepair/main1?id=' + data
								+ '&pageParam=' + encodePageParam());
				finishWait();
				refreshPermission();
				uploadObj.responses = [];
				}*/
		}
	});
}

function Delete(id) {
		var param = {};
		param.id = id;
		param.whbmid = $("#whbmid").val();
		param.whrid = $("#whrid").val();
		startWait();
		AlertUtil.showConfirmMessage("您确定要删除吗？", {
			callback : function() {
				AjaxUtil.ajax({
					url : basePath
							+ "/project/shapestructurerepair/deleteRecord",
					type : "post",
					data : JSON.stringify(param),
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					success : function(data) {
						finishWait();
						goPage(1, "desc", "auto");
						/*if($("#sjlx").val()==1){
						AlertUtil.showMessage('操作成功!',
								'/project/shapestructurerepair/main?pageParam='
										+ encodePageParam());
						}
						if($("#sjlx").val()==2){
							AlertUtil.showMessage('操作成功!',
									'/project/shapestructurerepair/main1?pageParam='
											+ encodePageParam());
						}*/
						refreshPermission();
					}
				});
			}
		});
}
//打开ATA章节号对话框
function openChapterWin(){
	var zjh = $.trim($("#zjhid").val());
	var dprtcode = $("#dprtcode").val();
	FixChapterModal.show({
		selected:zjh,//原值，在弹窗中默认勾选
		dprtcode:dprtcode,//机构代码
		callback: function(data){//回调函数
			var zjh=data.zjh==null?"":data.zjh;
			var name=data.ywms==null?"":StringUtil.escapeStr(data.ywms);
			$("#zjh").val(zjh+" "+name);
			$("#zjhid").val(zjh);
		}
	})
}

function loadFjzch(){
	 var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
	 	var planeRegOption = '';
		if(planeDatas != null && planeDatas.length >0){
			$.each(planeDatas,function(i,planeData){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
			});
		}
		$("#fjzch").html(planeRegOption); 
}

function update(id,xlqfj,xlhfj) {
		if($("#wc_type").val() == 1){
			$("#Cname").html("外形缺损");
			$("#Ename").html("Shape Defect");
		}else{
			$("#Cname").html("结构修理");
			$("#Ename").html("Structural Repair");
		}
	
		clearData();
		loadFjzch();
		$("#wxrybutton").show();
		$("#xlyjmark").show();
		$("#xlrqmark").show();
		$("#qxmsmark").show();
		$("#wzmark").show();
		$("#fxrqmark").show();
		$("#fjzchmark").show();
		$("#operat").show();
		$("#operat1").show();
		var param = {};
		param.id = id;
		AjaxUtil.ajax({
			url : basePath + "/project/shapestructurerepair/edit",
			type : "post",
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : JSON.stringify(param),
			success : function(data) {
				$("#fjzch").val(StringUtil.escapeStr(data.fjzch));
				$("#fxrq").val(data.fxrq.toString().substr(0, 10));
				$("#fxrq").datepicker("update");
				$("#zjhid").val(StringUtil.escapeStr(data.zjh));
				var zjh=(data.zjh?data.zjh:"");
				$("#zjh").val(StringUtil.escapeStr(zjh)+" "+StringUtil.escapeStr((data.fixChapter?data.fixChapter.ywms:'')));
				$("#wz").val(StringUtil.escapeStr(data.wz));
				$("#qxms").val(StringUtil.escapeStr(data.qxms));
				$("#xlrq").val(data.xlrq.toString().substr(0, 10));
				$("#xlrq").datepicker("update");
				var xlfs = document.getElementsByName("xlfs"); 			
				for (var i=0; i<xlfs.length; i++){  
				       if (xlfs[i].value==data.xlfs) {  
				    	   xlfs[i].checked= true;  
				           break;  
				       }  
				   }
				var is_xlxjc = document.getElementsByName("is_xlxjc"); 			
				for (var i=0; i<is_xlxjc.length; i++){  
				       if (is_xlxjc[i].value==data.is_xlxjc) {  
				    	   is_xlxjc[i].checked= true;  
				           break;  
				       }  
				   }  
				$("#xlyj").val(data.xlyj);			
				if(data.is_xlxjc==1){
					  lxjcjgDiv.style.display="block";
					  $("#lxjcjg").val(data.lxjcjg);
				}
				if(data.is_xlxjc==0){
					 lxjcjgDiv.style.display="none";
				}
				$("#id").val(data.id);
				var is_gb = document.getElementsByName("is_gb"); 			
				for (var i=0; i<is_gb.length; i++){  
				       if (is_gb[i].value==data.is_gb) {  
				    	   is_gb[i].checked= true;  
				           break;  
				       }  
				   }
				var is_jrwxfq = document.getElementsByName("is_jrwxfq"); 			
				for (var i=0; i<is_jrwxfq.length; i++){  
					if (is_jrwxfq[i].value==data.is_jrwxfq) {  
						is_jrwxfq[i].checked= true;  
						break;  
					}  
				}
				if(data.attachments!=null&&data.attachments!=''){
					loadAttachments(data.attachments,'filelist')
				}
				if(data.xlhAttachment!=null&&data.xlhAttachment!=''){
					loadAttachments(data.xlhAttachment,'xlhfilelist')
				}
				if(data.xlyjAttachment!=null&&data.xlyjAttachment!=''){
					loadAttachments(data.xlyjAttachment,'xlyjfilelist')
				}
				$("#dpt").val(data.dprtcode);
				$("#xlqfile-div").show();
				$("#xlhfile-div").show();
				$("#addModal input").attr("disabled", false);
				$("#addModal textarea").attr("disabled", false);
				$("#addModal select").attr("disabled", false);
				$("#fileuploader").attr("disabled", false);
				$("#addModal i").attr("onclick");
				$("#wxrybtn").removeAttr("disabled");
				$("#save").show();
				$("#fjzch").attr("disabled",true);
				$("#addModal").modal("show");
				$('#addModal').on('shown.bs.modal', function() {
					AlertUtil.hideModalFooterMessage();
					ModalUtil.modalBodyHeight("addModal");
				})
			}
		});
}

function loadAttachments(list,obj,type){
				$("#"+obj).empty();
				var attachments = list;
				var len = attachments.length;
				if (len > 0) {
					var trHtml = '';
					for (var i = 0; i < len; i++) {
						trHtml = trHtml
								+ '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
								+ attachments[i].id + '\' key=\''
								+ attachments[i].id + '\' >';
						if(type!="look"){
						trHtml = trHtml + '<td class="text-center"><div>';
						trHtml = trHtml
								+ '<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''
								+ attachments[i].id
								+ '\')" title="删除 Delete">  ';
						trHtml = trHtml + '</div></td>';
						
						}
						trHtml = trHtml
								+ '<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].wbwjm)+'.'+StringUtil.escapeStr(attachments[i].hzm)+'" > <a onclick="downloadfile(\''
								+ attachments[i].id + '\')" >'
								+ StringUtil.escapeStr(attachments[i].wbwjm)+'.'+StringUtil.escapeStr(attachments[i].hzm)
								+ '</a></td>';
						trHtml = trHtml + '<td class="text-left">'
								+ attachments[i].wjdxStr + ' </td>';
						trHtml = trHtml + '<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].czrname)+'">'
								+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
						trHtml = trHtml + '<td class="text-center">'
								+ attachments[i].czsj + '</td>';
						trHtml = trHtml
								+ '<input type="hidden" name="relativePath" value=\''
								+ attachments[i].relativePath
								+ '\'/>';
						trHtml = trHtml + '</tr>';
					}
					$('#'+obj).append(trHtml);
				}	
}

var uploadObj = $("#fileuploader")
		.uploadFile(
				{
					url : basePath + "/common/ajaxUploadFile",
					multiple : true,
					dragDrop : false,
					showStatusAfterSuccess : false,
					showDelete : false,
					formData : {
						"fileType" : "shapestructurerepair"
						/*"wbwjm" : function() {
							return $('#wbwjm').val()
						}*/
					},
					fileName : "myfile",
					returnType : "json",
					removeAfterUpload : true,				
					showStatusAfterError: false,
					/*uploadStr : "<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",*/
					uploadStr:"<i class='fa fa-upload'></i>",
					uploadButtonClass: "btn btn-default",
					onSuccess : function(files, data, xhr, pd) // 上传成功事件，data为后台返回数据
					{
						
						var len = $("#filelist").find("tr").length;
						if (len == 1) {
							if($("#filelist").find("td").length ==1){
								$("#filelist").empty();
							}
						}
						
						
						var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
								+ data.attachments[0].uuid + '\'>';
						trHtml = trHtml + '<td class="text-center"><div>';
						trHtml = trHtml
								+ '<i class="icon-trash color-blue cursor-pointer"  onclick="delfile(\''
								+ data.attachments[0].uuid
								+ '\')" title="删除 Delete">  ';
						trHtml = trHtml + '</div></td>';
						trHtml = trHtml + '<td title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'.'+StringUtil.escapeStr(data.attachments[0].hzm)+'" class="text-left">'
								+ StringUtil.escapeStr(data.attachments[0].wbwjm) + '.'+StringUtil.escapeStr(data.attachments[0].hzm)+'</td>';
						trHtml = trHtml + '<td class="text-left">'
								+ data.attachments[0].wjdxStr + ' </td>';
						trHtml = trHtml + '<td class="text-left">'
								+ data.attachments[0].user.username + " "
								+ data.attachments[0].user.realname + '</td>';
						trHtml = trHtml + '<td class="text-center">' + data.attachments[0].czsj
								+ '</td>';
						trHtml = trHtml
								+ '<input type="hidden" name="relativePath" value=\''
								+ data.attachments[0].relativePath + '\'/>';

						trHtml = trHtml + '</tr>';
						$('#filelist').append(trHtml);
					}
					,onSubmit : function (files, xhr) {
						var wbwjm  = $.trim($('#wbwjm').val());
						if(wbwjm.length>0){
							if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
				            	$('#wbwjm').focus();
				            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \ | : \" * ?");
				            	
				            	$('.ajax-file-upload-container').html("");
								uploadObj.selectedFiles -= 1;
								return false;
				            } 
				            else{
				            	return true; 
				            }
						}else{
							return true;
						}
					}

				});

var xlhuploadObj = $("#xlhfileuploader")
.uploadFile(
		{
			url : basePath + "/common/ajaxUploadFile",
			multiple : true,
			dragDrop : false,
			showStatusAfterSuccess : false,
			showDelete : false,
			formData : {
				"fileType" : "shapestructurerepair"
				/*"wbwjm" : function() {
					return $('#xlhfj').val()
				}*/
			},
			fileName : "myfile",
			returnType : "json",
			removeAfterUpload : true,				
			showStatusAfterError: false,
			uploadStr:"<i class='fa fa-upload'></i>",
			uploadButtonClass: "btn btn-default",
			onSuccess : function(files, data, xhr, pd) // 上传成功事件，data为后台返回数据
			{
				
				var len = $("#xlhfilelist").find("tr").length;
				if (len == 1) {
					if($("#xlhfilelist").find("td").length ==1){
						$("#xlhfilelist").empty();
					}
				}
				
				var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
						+ data.attachments[0].uuid + '\'>';
				trHtml = trHtml + '<td class="text-center" ><div>';
				trHtml = trHtml
						+ '<i class="icon-trash color-blue cursor-pointer"  onclick="delfile(\''
						+ data.attachments[0].uuid
						+ '\')" title="删除 Delete">  ';
				trHtml = trHtml + '</div></td>';
				trHtml = trHtml + '<td title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'.'+StringUtil.escapeStr(data.attachments[0].hzm)+'" class="text-left">'
						+ StringUtil.escapeStr(data.attachments[0].wbwjm) + '.'+StringUtil.escapeStr(data.attachments[0].hzm)+'</td>';
				trHtml = trHtml + '<td class="text-left">'
						+ data.attachments[0].wjdxStr + ' </td>';
				trHtml = trHtml + '<td class="text-left">'
						+ data.attachments[0].user.username + " "
						+ data.attachments[0].user.realname + '</td>';
				trHtml = trHtml + '<td class="text-center">' + data.attachments[0].czsj
						+ '</td>';
				trHtml = trHtml
						+ '<input type="hidden" name="relativePath" value=\''
						+ data.attachments[0].relativePath + '\'/>';

				trHtml = trHtml + '</tr>';
				$('#xlhfilelist').append(trHtml);
			}
			,onSubmit : function (files, xhr) {
				var wbwjm  = $.trim($('#xlhfj').val());
				if(wbwjm.length>0){
					if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
		            	$('#wbwjm').focus();
		            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \ | : \" * ?");
		            	
		            	$('.ajax-file-upload-container').html("");
						xlhuploadObj.selectedFiles -= 1;
						return false;
		            } 
		            else{
		            	return true; 
		            }
				}else{
					return true;
				}
			}

		});

var xlyjuploadObj = $("#xlyjfileuploader")
.uploadFile(
		{
			url : basePath + "/common/ajaxUploadFile",
			multiple : true,
			dragDrop : false,
			showStatusAfterSuccess : false,
			showDelete : false,
			formData : {
				"fileType" : "shapestructurerepair"
				/*"wbwjm" : function() {
					return $('#xlhfj').val()
				}*/
			},
			fileName : "myfile",
			returnType : "json",
			removeAfterUpload : true,				
			showStatusAfterError: false,
			uploadStr:"<i class='fa fa-upload'></i>",
			uploadButtonClass: "btn btn-default",
			onSuccess : function(files, data, xhr, pd) // 上传成功事件，data为后台返回数据
			{
				
				var len = $("#xlyjfilelist").find("tr").length;
				if (len == 1) {
					if($("#xlyjfilelist").find("td").length ==1){
						$("#xlyjfilelist").empty();
					}
				}
				
				var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
						+ data.attachments[0].uuid + '\'>';
				trHtml = trHtml + '<td class="text-center" ><div>';
				trHtml = trHtml
						+ '<i class="icon-trash color-blue cursor-pointer"  onclick="delfile(\''
						+ data.attachments[0].uuid
						+ '\')" title="删除 Delete">  ';
				trHtml = trHtml + '</div></td>';
				trHtml = trHtml + '<td title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'.'+StringUtil.escapeStr(data.attachments[0].hzm)+'" class="text-left">'
						+ StringUtil.escapeStr(data.attachments[0].wbwjm) + '.'+StringUtil.escapeStr(data.attachments[0].hzm)+'</td>';
				trHtml = trHtml + '<td class="text-left">'
						+ data.attachments[0].wjdxStr + ' </td>';
				trHtml = trHtml + '<td class="text-left">'
						+ data.attachments[0].user.username + " "
						+ data.attachments[0].user.realname + '</td>';
				trHtml = trHtml + '<td class="text-center">' + data.attachments[0].czsj
						+ '</td>';
				trHtml = trHtml
						+ '<input type="hidden" name="relativePath" value=\''
						+ data.attachments[0].relativePath + '\'/>';

				trHtml = trHtml + '</tr>';
				$('#xlyjfilelist').append(trHtml);
			}
			,onSubmit : function (files, xhr) {
				var wbwjm  = $.trim($('#xlyjfj').val());
				if(wbwjm.length>0){
					if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
		            	$('#wbwjm').focus();
		            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \ | : \" * ?");
		            	
		            	$('.ajax-file-upload-container').html("");
						xlhuploadObj.selectedFiles -= 1;
						return false;
		            } 
		            else{
		            	return true; 
		            }
				}else{
					return true;
				}
			}

		});
/**
 * 添加时删除附件
 * 
 * @param uuid
 */
function delfile(uuid) {
	var responses1 = uploadObj.responses;
	var objLen1 = uploadObj.responses.length;
	var fileArray1 = [];
	var key = $("#" + uuid).attr("key");
	$('#' + uuid).remove();
	//var waitDelArray = [];
	if (objLen1 > 0) {
		for (var i = 0; i < objLen1; i++) {
			if (responses1[i].attachments[0].uuid != uuid) {
				fileArray1.push(responses1[i]);
			}
		}
		uploadObj.responses = fileArray1;
		uploadObj.selectedFiles -= 1;
	}
	
	var responses2 = xlhuploadObj.responses;
	var objLen2 = xlhuploadObj.responses.length;
	var fileArray2 = [];
	if (objLen2 > 0) {
		for (var i = 0; i < objLen2; i++) {
			if (responses2[i].attachments[0].uuid != uuid) {
				fileArray2.push(responses2[i]);
			}
		}
		xlhuploadObj.responses = fileArray2;
		xlhuploadObj.selectedFiles -= 1;
	}
	
	var len1 = $("#xlhfilelist").find("tr").length;
	if (len1 < 1) {
		$("#xlhfilelist").append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	}
	var len2 = $("#filelist").find("tr").length;
	if (len2 < 1) {
		$("#filelist").append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	}
	var len3 = $("#xlyjfilelist").find("tr").length;
	if (len3 < 1) {
		$("#xlyjfilelist").append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	}
	if(key){
		delAttachements.push(key);
	}
}
/**
 * 清空记录单modal数据
 */
function clearData(){
	$("#fjzch").val("");
	$("#fxrq").val("");
	$("#fxrq").datepicker("update");
	$("#zjh").val("");
	$("#zjhid").val("");
	$("#wz").val("");
	$("#qxms").val("");
	$("#xlrq").val("");
	$("#xlhfilelist").empty();
	$("#xlyjfilelist").empty();
	$("#filelist").empty();
	$("#xlrq").datepicker("update");
	var xlfs = document.getElementsByName("xlfs");  
	    for (var i=0; i<xlfs.length; i++){  
	        if (xlfs[i].value=="1") {  
	        	xlfs[i].checked= true;  
	            break;  
	        }  
	 }
	$("#xlyj").val("");
	var xlxjc = document.getElementsByName("is_xlxjc");  
    for (var i=0; i<xlxjc.length; i++){  
        if (xlxjc[i].value=="1") {  
        	xlxjc[i].checked= true;  
            break;  
        }  
    }
    $("#lxjcjg").val("");
    var gb = document.getElementsByName("is_gb");  
    for (var i=0; i<gb.length; i++){  
        if (gb[i].value=="1") {  
        	gb[i].checked= true;  
            break;  
        }  
    }
    var jrwxfq = document.getElementsByName("is_jrwxfq");  
    for (var i=0; i<jrwxfq.length; i++){  
    	if (jrwxfq[i].value=="1") {  
    		jrwxfq[i].checked= true;  
    		break;  
    	}  
    }
	$("#id").val("");
	$("#dpt").val("");
	$('#filelist').html("");
	$('#xlhfilelist').html("");
	$('#xlyjfilelist').html("");
	$("#wbwjm").val("");
	$("#xlhfj").val("");
	uploadObj.responses.length=0;
	xlhuploadObj.responses.length=0;
	xlyjuploadObj.responses.length=0;
	$("#filelist").append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	$("#xlhfilelist").append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	$("#xlyjfilelist").append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	delAttachements = [];
}	

/**
 * 修改时删除附件
 */
function deletefile(uuid) {
	var key = $('#' + uuid).attr('key');
	if (key == undefined || key == null || key == '') {
		var responses = uploadObj.responses;
		var len = uploadObj.responses.length;
		var fileArray = [];
		//var waitDelArray = [];
		if (len > 0) {
			for (var i = 0; i < len; i++) {
				if (responses[i].attachments[0].uuid != uuid) {
					fileArray.push(responses[i]);
				}
			}
			uploadObj.responses = fileArray;
			uploadObj.selectedFiles -= 1;
		}
		$('#' + uuid).remove();

	} else {
		$('#' + uuid).remove();
		delAttachements.push({
			id : key
		});
	}
	
	var len1 = $("#xlhfilelist").find("tr").length;
	if (len1 < 1) {
		$("#xlhfilelist").append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	}
	var len2 = $("#filelist").find("tr").length;
	if (len2 < 1) {
		$("#filelist").append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	}
	var len3 = $("#xlyjfilelist").find("tr").length;
	if (len3 < 1) {
		$("#xlyjfilelist").append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	}
	
	
}
/**
 * 下载附件
 */
function downloadfile(id) {
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);

}

function view(id){
	window.open(basePath+"/project/shapestructurerepair/view?id="+id);
}

function look(id,xlqfj,xlhfj) {
	clearData();
	$("#xlyjmark").hide();
	$("#xlrqmark").hide();
	$("#qxmsmark").hide();
	$("#wzmark").hide();
	$("#fxrqmark").hide();
	$("#fjzchmark").hide();
	$("#operat").hide();
	$("#operat1").hide();
	$("#wxrybutton").hide();
	loadFjzch();
	var param = {};
	param.id = id;
	AjaxUtil.ajax({
		url : basePath + "/project/shapestructurerepair/edit",
		type : "post",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : JSON.stringify(param),
		success : function(data) {
			$("#fjzch").val(data.fjzch);
			$("#fxrq").val(data.fxrq.toString().substr(0, 10));
			$("#fxrq").datepicker("update");
			var zjh=data.zjh==null?"":data.zjh;
			$("#zjh").val(zjh+" "+(data.fixChapter?data.fixChapter.ywms:''));
			$("#wz").val(data.wz);
			$("#qxms").val(data.qxms);
			$("#xlrq").val(data.xlrq.toString().substr(0, 10));
			$("#xlrq").datepicker("update");
			var xlfs = document.getElementsByName("xlfs"); 			
			for (var i=0; i<xlfs.length; i++){  
			       if (xlfs[i].value==data.xlfs) {  
			    	   xlfs[i].checked= true;  
			           break;  
			       }  
			   }
			var is_xlxjc = document.getElementsByName("is_xlxjc"); 			
			for (var i=0; i<is_xlxjc.length; i++){  
			       if (is_xlxjc[i].value==data.is_xlxjc) {  
			    	   is_xlxjc[i].checked= true;  
			           break;  
			       }  
			   }  
			if(data.is_xlxjc==1){
				  lxjcjgDiv.style.display="block";
				  $("#lxjcjg").val(data.lxjcjg);
			}
			if(data.is_xlxjc==0){
				 lxjcjgDiv.style.display="none";
			}
			$("#xlyj").val(data.xlyj);
			var is_gb = document.getElementsByName("is_gb"); 			
			for (var i=0; i<is_gb.length; i++){  
			       if (is_gb[i].value==data.is_gb) {  
			    	   is_gb[i].checked= true;  
			           break;  
			       }  
			   }
			var is_jrwxfq = document.getElementsByName("is_jrwxfq"); 			
			for (var i=0; i<is_jrwxfq.length; i++){  
				if (is_jrwxfq[i].value==data.is_jrwxfq) {  
					is_jrwxfq[i].checked= true;  
					break;  
				}  
			}
			if(data.attachments!=null&&data.attachments!=''){
				loadAttachments(data.attachments,'filelist',"look")
			}
			if(data.xlhAttachment!=null&&data.xlhAttachment!=''){
				loadAttachments(data.xlhAttachment,'xlhfilelist',"look")
			}
			if(data.xlyjAttachment!=null&&data.xlyjAttachment!=''){
				loadAttachments(data.xlyjAttachment,'xlyjfilelist',"look")
			}
			$("#xlqfile-div").hide();
			$("#xlhfile-div").hide();
			$("#addModal input").attr("disabled", true);
			$("#addModal textarea").attr("disabled", true);
			$("#addModal select").attr("disabled", true);
			$("#fileuploader").attr("disabled", true);
			$("#addModal i").attr("onclick", false);
			$("#wxrybtn").attr("disabled", "disabled");
			$("#save").hide();
			$("#addModal").modal("show");
			$('#addModal').on('shown.bs.modal', function() {
				AlertUtil.hideModalFooterMessage();
				ModalUtil.modalBodyHeight("addModal");
			})
		}
	});
	
}
// 字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(
			function() { // 重置class
				$(this).removeClass("sorting_desc").removeClass("sorting_asc")
						.addClass("sorting");
			});
	$("#" + obj + "_" + "order").removeClass("sorting");
	var orderType = "asc";
	if (orderStyle.indexOf("sorting_asc") >= 0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
		orderType = "asc";
	} else {
		orderType = "desc";
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	goPage(currentPage, orderStyle.split("_")[1], obj);
}
// 重置
function searchreset() {
	var zzjg = $("#dprtcode").val();
	$("#keyword_search").val("");
	$("#fxrq_search").val("");
	$("#fxrq_search").each(function() {
		$(this).val("");
	});
	$("#xlrq_search").val("");
	$("#xlrq_search").each(function() {
		$(this).val("");
	});
	$("#dprtcode_search").each(function() {
		$(this).val("");
	});
	
	$("#dprtcode_search").val(zzjg);
	$("#xlfs_search").val(0);
	searchRecord();
}

// 隐藏Modal时验证销毁重构
$("#addModal").on("hidden.bs.modal", function() {
	$("#form").data('bootstrapValidator').destroy();
	$('#form').data('bootstrapValidator', null);
	validation();
});
function validation() {
	validatorForm = $("#form").bootstrapValidator({
		message : '数据不合法',
		feedbackIcons : {
			// valid: 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			fjzch : {
				validators : {
					notEmpty : {
						message : '飞机注册号不能为空'
					}
				}
			},
			fxrq : {
				validators : {
					notEmpty : {
						message : '发现日期不能为空'
					}
				}
			},
			wz : {
				validators : {
					notEmpty : {
						message : '位置不能为空'
					}
				}
			},
			qxms : {
				validators : {
					notEmpty : {
						message : '缺陷描述不能为空'
					}
				}
			},
			xlrq : {
				validators : {
					notEmpty : {
						message : '修理日期不能为空'
					}
				}
			},
			xlyj : {
				validators : {
					notEmpty : {
						message : '修理依据不能为空'
					}
				}
			}
		}
	}).on('hide', function(e) {
		  $('#form').data('bootstrapValidator')  
		     .updateStatus('fxrq', 'NOT_VALIDATED',null)  
		     .validateField('fxrq');
		  
  });
}

function getAttacment(list,type,id){
	var attachment='';
	if(list!=null){
			if (list.length>0) {
				var attachments = list;
				var len = list.length;
				if (len > 0) {
					for (var i = 0; i < len; i++) {
					if(i!=1){
						if(type=="xlq"){
							choses.push(attachments[i].id);						
						}else if(type=="xlh"){
							choseAllIds.push(attachments[i].id);
						}else{
							
						}
					}
					if (i == 1) {
						if(type=="xlq"){
							choses.push(id);						
						}else if(type=="xlh"){
							choseAllIds.push(id);
						}else{
							
						}
						attachment = attachment
								+ "　<i class='icon-caret-down cursor-pointer' name='"
								+ id
								+ "icon' onclick=switchFj('"
								+ id + "')></i><div class='"
								+ id
								+ "' style='display:none'>";
						}
					if(i==0){
						attachment +="<a href='javascript:void(0);' title='"+StringUtil.escapeStr(attachments[i].wbwjm) +"."+ StringUtil.escapeStr(attachments[i].hzm)+"' onclick=\"downloadfile('"+attachments[i].id+"')\" >"+substring0To13(StringUtil.escapeStr(attachments[i].wbwjm)) +"."+ StringUtil.escapeStr(attachments[i].hzm)+"</a>";
					}
					if (i != 0) {
						attachment +="<a href='javascript:void(0);' title='"+StringUtil.escapeStr(attachments[i].wbwjm) +"."+ StringUtil.escapeStr(attachments[i].hzm)+"' onclick=\"downloadfile('"+attachments[i].id+"')\" >"+substring0To25(StringUtil.escapeStr(attachments[i].wbwjm)) +"."+ StringUtil.escapeStr(attachments[i].hzm)+"</a>";
						attachment += "<br>";

					}
					if (i != 0 && i == len - 1) {
						attachment += "</div>";
					}
				}
				 }
			}
	}
	return attachment;
	
}

function switchFj(id) {
	new Sticky({tableId:'repairRecord_check_list_table'});
		if ($("." + id).is(":hidden")) {
			$("." + id).fadeIn(500);
			$("[name=" + id + 'icon]').removeClass().addClass("icon-caret-up cursor-pointer");
		} else {
			$("." + id).hide();
			$("[name=" + id + 'icon]').removeClass().addClass("icon-caret-down cursor-pointer");
		}
	}
function substring0To25(str){
	if(str!="" && str!=null ){
		if(str.length>=15){
			return str.substring(0,15)+"...";
		}
		return str;
	}
	return str;
}
function substring0To13(str){
	if(str!="" && str!=null ){
		if(str.length>=13){
			return str.substring(0,13)+"...";
		}
		return str;
	}
	return str;
}


function vieworhideXlhFj(){
	new Sticky({tableId:'repairRecord_check_list_table'});
	var obj = $("th[name='fj']");
	var flag = obj.hasClass("downward");
	if(flag){
		obj.removeClass("downward").addClass("upward");
	}else{
		obj.removeClass("upward").addClass("downward");
	}
	for(var i=0;i<choseAllIds.length;i++){
		if(flag){				
			$("."+choseAllIds[i]).fadeIn(500);
			$("[name=" + choseAllIds[i] + 'icon]').removeClass().addClass("icon-caret-up cursor-pointer");
		}else{
			$("."+choseAllIds[i]).hide();
			$("[name=" + choseAllIds[i] + 'icon]').removeClass().addClass("icon-caret-down cursor-pointer");
		}
	}
	for(var i=0;i<choses.length;i++){
		if(flag){				
			$("."+choses[i]).fadeIn(500);
			$("[name=" + choses[i] + 'icon]').removeClass().addClass("icon-caret-up cursor-pointer");
		}else{
			$("."+choses[i]).hide();
			$("[name=" + choses[i] + 'icon]').removeClass().addClass("icon-caret-down cursor-pointer");
		}
	}
}




function openPosition(){
	var dprtcode = '54205082-00b8-4446-a24a-f2717ef315d7';
	var jx = 'EC225';
	var positionList = [];	//回显对象
	var position = {};
	position.id="1";
	position.sz="飞机站位1";
	positionList.push(position);
	PositionModal.show({
		selected:positionList,//原值，在弹窗中默认勾选
		dprtcode:dprtcode,
		parentid:'AddalertModal',
		jx:jx,
		callback: function(data){//回调函数
			alert(JSON.stringify(data));
			
		}
	});
}

//导出
function exportExcel(){
	var searchParam = {};
	searchParam.keyword = $.trim($("#keyword_search").val());
	var paramsMap={};
	var fxrq = $.trim($("#fxrq_search").val());
	if(null != fxrq && "" != fxrq){
		paramsMap.fxrqDateBegin = fxrq.substring(0,4)+"-"+fxrq.substring(5,7)+"-"+fxrq.substring(8,10)+" 00:00:00";
		paramsMap.fxrqDateEnd = fxrq.substring(12,17)+"-"+fxrq.substring(18,20)+"-"+fxrq.substring(21,23)+" 23:59:59";
	}else{
		paramsMap.fxrqDateBegin = "";
		paramsMap.fxrqDateEnd = "";
	}
	var xlrq = $.trim($("#xlrq_search").val());
	if(null != xlrq && "" != xlrq){
		paramsMap.xlrqDateBegin = xlrq.substring(0,4)+"-"+xlrq.substring(5,7)+"-"+xlrq.substring(8,10)+" 00:00:00";
		paramsMap.xlrqDateEnd = xlrq.substring(12,17)+"-"+xlrq.substring(18,20)+"-"+xlrq.substring(21,23)+" 23:59:59";
	}else{
		paramsMap.xlrqDateBegin = "";
		paramsMap.xlrqDateEnd = "";
	}
	searchParam.dprtcode = $.trim($("#dprtcode_search").val());
	searchParam.sjlx=$("#sjlx").val();
	searchParam.xlfs =$.trim($("#xlfs_search").val());
	
	window.open(basePath + "/project/shapestructurerepair/shapeStructureRepair.xls?dprtcode="
			+ encodeURIComponent(searchParam.dprtcode)+ "&xlfs="+encodeURIComponent(searchParam.xlfs)+"&keyword="
			+ encodeURIComponent(searchParam.keyword) + "&sjlx="+encodeURIComponent(searchParam.sjlx)+"&paramsMap[fxrqDateBegin]="
			+ encodeURIComponent(paramsMap.fxrqDateBegin) + "&paramsMap[fxrqDateEnd]=" + encodeURIComponent(paramsMap.fxrqDateEnd)+"&paramsMap[xlrqDateBegin]="
			+ encodeURIComponent(paramsMap.xlrqDateBegin) + "&paramsMap[xlrqDateEnd]=" + encodeURIComponent(paramsMap.xlrqDateEnd));
}








