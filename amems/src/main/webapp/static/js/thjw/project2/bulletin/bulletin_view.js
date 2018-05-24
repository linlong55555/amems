var zt = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];
var zts = [ "未评估", "已评估", "已审核", "已批准", "中止（关闭）", "审核驳回", "批准驳回", "", "作废", "指定结束" ];
var pgdids = [];
var oldScwjList = [];
$(function() {
	var param = {};
	param.id = $("#bulletinId").val();
	initFjjxOption();// 加载飞机	
		var paramsMap = {};
		paramsMap = {
			"view" : "1" //查看页面对当前技术通告做标识 
		};
	param.paramsMap = paramsMap;	
	fillData(param);//填充页面内容
	evaluation_modal.init({  //加载评估单
		isShowed : false,
		zlid : $("#bulletinId").val(),
		changeCss:true,
		isShowAll:false,
	});
});

function fillData(param) {
	AjaxUtil.ajax({
		url : basePath + "/project2/bulletin/edit",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		async : false,
		success : function(data) {
			$("#jxtgbh").val(data.jstgh);
			$("#bb").val(data.bb);
			$("#current_bb").val(data.bb);
			$("#zhut").val(data.zhut);
			$("#jx").val(data.jx);
			$("#bfrq").val(data.bfrq == null ? "" : data.bfrq.toString().substr(0, 10));
			$("#sxrq").val(data.sxrq == null ? "" : data.sxrq.toString().substr(0, 10));
			$("#tgqx").val(data.tgqx == null ? "" : data.tgqx.toString().substr(0, 10));
			var rObj = document.getElementsByName('sj');// 涉及
			for (var i = 0; i < rObj.length; i++) {
				if (rObj[i].value == data.sj) {
					rObj[i].checked = 'checked';
				}
			}
			$("#zt_input").val(DicAndEnumUtil.getEnumName('bulletinStatusEnum', data.zt));
			$("#yxx").val(data.yxx);
			$("#lysm").val(data.lysm);
			$("#ckzl").val(data.ckzl);
			$("#bj").val(data.bj);
			$("#ms").val(data.ms);
			$("#cs").val(data.cs);
			var tglb = document.getElementsByName('tglb');
			for (var i = 0; i < tglb.length; i++) {
				if (tglb[i].value == data.tglb) {
					tglb[i].checked = 'checked';
				}
			}
			$("#gbyy").val(data.gbyy);
			var departmentList = data.dDepartmentList;// 分发部门
			if (departmentList != null && departmentList.length > 0) {
				var dprtcode = '';
				var dprtname = '';
				$.each(departmentList, function(index, row) {
					dprtcode += row.department.id + ","
					dprtname += row.department.dprtname + ","
				});
				dprtcode = dprtcode.substring(0, dprtcode.length - 1);
				dprtname = dprtname.substring(0, dprtname.length - 1);
				$("#ff").html(dprtname);
				$("#ffid").val(dprtcode);
			}
			if (data.isFjxx == 1) {// 附加信息
				$("input[name='is_fjxx']").attr("checked", true);
				if (data.isWcfjpc == 1) {// 已完成现有机翼的普装检查
					$("input[name='is_wcfjpc']").attr("checked", true);
					$("#eo_mao").val(data.eoMao);
					if (data.isFjSyxbj == 1) {// 有受影响部件
						$("#syxbj_fj").val(data.syxbjFj);
					} else {
						$("#syxbj_fj").attr("disabled", true);
					}
					var isfjsyxbj = document.getElementsByName('is_fj_syxbj');
					for (var i = 0; i < isfjsyxbj.length; i++) {
						if (isfjsyxbj[i].value == data.isFjSyxbj) {
							isfjsyxbj[i].checked = 'checked';
						}
					}
				} else {
					$("input[name='is_wcfjpc']").attr("checked", false);
					$("#eo_mao").val("");
					$("#eo_mao").attr("disabled", true);
					$("input[name='is_fj_syxbj']").attr("checked", false);
					$("input[name='is_fj_syxbj']").attr("disabled", true);
					$("#syxbj_fj").attr("disabled", true);
				}
				if (data.isBjzjhs == 1) {// 部件装机清单
					$("input[name='is_bjzjhs']").attr("checked", true);
					if (data.isBjSyxbj == 1) {// 有受影响部件
						$("#syxbj_bj").val(data.syxbjBj);
					} else {
						$("#syxbj_bj").attr("disabled", true);
					}
					var isbjsyxbj = document.getElementsByName('is_bj_syxbj');
					for (var i = 0; i < isbjsyxbj.length; i++) {
						if (isbjsyxbj[i].value == data.isBjSyxbj) {
							isbjsyxbj[i].checked = 'checked';
						}
					}
				} else {
					$("input[name='is_bjzjhs']").attr("checked", false);
					$("#syxbj_bj").attr("disabled", true);
					$("input[name='is_bj_syxbj']").attr("checked", false);
				}
				if (data.isWpc == 1) {// 普查
					$("input[name='is_wpc']").attr("checked", true);
				} else {
					$("input[name='is_wpc']").attr("checked", false);
				}
			} else {
				document.getElementById('fjxxpanel').style.display="none";
			}
			 introduce_process_info_class.show({  //流程信息
					status:5,//1,编写,2审核,3批准，4审核驳回,5批准驳回
					prepared:data.zdr_user.username +" "+data.zdr_user.realname,//编写人
					preparedDate:data.zdsj,//编写日期
					checkedOpinion:data.shyj,//审核意见
					checkedby:data.shr_user!=null?data.shr_user.username+" " +data.shr_user.realname:"",//审核人
					checkedDate:data.shsj,//审核日期
					checkedResult:data.shjl,//审核结论
					approvedOpinion:data.pfyj,//批准意见
					approvedBy:data.pfr_user!=null?data.pfr_user.username +" " + data.pfr_user.realname:"",//批准人
					approvedDate:data.pfsj,//批准日期
					approvedResult:data.pfjl,//审批结论
				 });
			 var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
				attachmentsObj.show({
					djid:data.id,
					fileHead : false,
					colOptionhead : false,
					fileType:"bulletin"
				});//显示附件
				switchHistoryVersion(data);
		}
	});
}

function viewMainTechnicalfile(id, dprtcode) {
	window.open(basePath + "/project/technicalfile/findApprovalFileUpload/"
			+ $.trim(id) + "/" + $.trim(dprtcode) + "");
}

// 初始化机型下拉框
function initFjjxOption() {
	var typeList = acAndTypeUtil.getACTypeList({
		DPRTCODE : $("#zzjgid").val()
	});
	if (typeList.length > 0) {
		for (var i = 0; i < typeList.length; i++) {
			$("#fjjx").append(
					"<option value='" + StringUtil.escapeStr(typeList[i].FJJX)
							+ "'>" + StringUtil.escapeStr(typeList[i].FJJX)
							+ "</option>");
		}
	} else {
		$("#fjjx").append("<option value=''>暂无飞机机型No data</option>");
	}
}

function back() {
	window.location.href = basePath + "/project2/bulletin/main?id="
			+ $("#bulletinId").val() + "&pageParam=" + pageParam;
}

//切换显示历史版本
function switchHistoryVersion(data){
	if(data && data.previous){
		$("#bbViewHistoryDiv").show();
		$("#bbNoViewHistoryDiv").hide();
		initHistoryBb(data.id);
		$("#previous_bb").text(data.previous.bb);
		$("#previous_id").val(data.previous.id)
	}else{
		$("#bbViewHistoryDiv").hide();
		$("#bbNoViewHistoryDiv").show();
	}
}

//查看前一版本的
function viewPrevious(){
	var id = $("#previous_id").val();
	window.open(basePath + "/project2/bulletin/view?id=" + id);
}

//初始化历史版本
function initHistoryBb (id){
	WebuiPopoverUtil.initWebuiPopover('attachment-view2',"body",function(obj){
		return getHistoryBbList(id);
	});
}

//获取历史版本列表
function getHistoryBbList (id){
	return bulletin_history_alert_Modal.getHistoryBbList(id);
}

