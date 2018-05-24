var delAttachements = [];
var jstgh = "";
var planeModelOption = '';
var choseAllIds = [];
var bulletinData = [];
var pagination = {};
var oldbmidMap = {};
var oldbmidList = [];
var newbmidMap = {};
var newbmidList = [];
var bulletinData = [];
$(document).ready(
		function() {
			// 导航
			Navigation(menuCode, '', '', 'gc_4_1', '岳彬彬', '2017-08-01', '岳彬彬',
					'2017-08-17');
			decodePageParam();
			// goPage(1,"auto","desc");//开始的加载默认的内容
			initMultiselect();
			validation();
			$('.date-picker').datepicker({
				autoclose : true,
				clearBtn : true
			});
			$("#tgqx_search").daterangepicker({"opens":"left"}).prev().on("click", function() {
				$(this).next().focus();
			});
			refreshPermission();
			// 初始化日志功能
			logModal.init({
				code : 'B_G2_002'
			});
		 
		  //执行待办
		    if(todo_ywid){
		    	if(todo_jd == 1 || todo_jd == 5 || todo_jd == 6){
		    		edit(todo_ywid);
		    	}else if(todo_jd == 2){
		    		review(todo_ywid);
		    	}else if(todo_jd == 3){
		    		approve(todo_ywid);
		    	}else{
		    		add() ;
		    	}
		    	todo_ywid = null;
		    }
		    else if(todo_lyid) {
				add() ;
				//默认机型
				if(todo_fjjx) {
					todo_fjjx = decodeURIComponent(Base64.decode(todo_fjjx));
					if(todo_fjjx){
						$("#fjjx").val(todo_fjjx);
					}
					todo_fjjx = null;
				}
			}
		    
		});
// 带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
	var obj = {};
	var paramsMap = {};
	bulletinData = [];
	var searchParam = gatherSearchParam();
	pagination = {
		page : pageNumber,
		sort : sortType,
		order : sequence,
		rows : 20
	};
	obj.pagination = pagination;
	obj.keyword = searchParam.keyword;
	obj.zt = searchParam.zt;
	obj.dprtcode = searchParam.dprtcode;
	var tgqx = searchParam.tgqx;

	if (null != tgqx && "" != tgqx) {
		var tgqxBegin = tgqx.substring(0, 10) + " 00:00:00";
		var tgqxEnd = tgqx.substring(13, 23) + " 23:59:59";
		paramsMap.tgqxBegin = tgqxBegin;
		paramsMap.tgqxEnd = tgqxEnd;
	}
	if ($("#jx").val() != null || $("#jx").val() != undefined) {
		var jx=$("#jx").val();
		var jxArr=[];
		for(var i = 0 ; i < jx.length ; i++){
			if('multiselect-all' != jx[i]){
				jxArr.push(jx[i]);
			}
		}
		paramsMap.jx = jxArr;
	}
	paramsMap.bb = $.trim($("#bb_search").val());
	obj.paramsMap = paramsMap;
	obj.zdrid = searchParam.zdrid;
	if (id != "") {
		obj.id = id;
		id = "";
	}
	startWait();
	AjaxUtil
			.ajax({
				url : basePath + "/project2/bulletin/queryBulletinList",
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(obj),
				success : function(data) {
					finishWait();
					if (data.total > 0) {
						bulletinData = data.rows;
						appendContentHtml(data.rows, data.monitorsettings);
						var page_ = new Pagination({
							renderTo : "pagination_list",
							data : data,
							sortColumn : sortType,
							orderType : sequence,
							extParams : {},
							goPage : function(a, b, c) {
								AjaxGetDatasWithSearch(a, b, c);
							}

						});
						// 标记关键字
						signByKeyword($("#keyword_search").val(), [ 4, 9 ]);
					} else {
						$("#bulletinlist").empty();
						$("#pagination_list").empty();
						$("#bulletinlist").append("<tr class='text-center'><td colspan=\"17\">暂无数据 No data.</td></tr>");
					}
					new Sticky({
						tableId : 'bulletin_list_table'
					});
				}
			});

}

// 将搜索条件封装
function gatherSearchParam() {
	var searchParam = {};
	searchParam.keyword = $.trim($("#keyword_search").val());// 关键字查询
	searchParam.zt = $.trim($("#zt_search").val());
	searchParam.dprtcode = $.trim($("#zzjg").val());
	searchParam.tgqx = $.trim($("#tgqx_search").val());

	return searchParam;
}
function encodePageParam() {
	var pageParam = {};
	var params = {};
	params.keyword = $.trim($("#keyword_search").val());
	params.zt = $.trim($("#zt_search").val());
	params.zzjg = $.trim($("#zzjg").val());
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

function appendContentHtml(list, monitorsettings) {
	var htmlContent = '';
	choseAllIds = [];
	$.each(list,function(index, row) {
		choseAllIds.push(row.id);
		htmlContent += "<tr onclick='clickRow("+index+",this)'>";
		htmlContent += "<td class='text-center fixed-column'>";		
			htmlContent += "<input type='checkbox' name='checkrow' index='"+index+"' onclick=\"checkRow("+index+",this)\" />";
		htmlContent += "</td>";
		htmlContent += "<td class='text-center fixed-column'>";
		if ((row.zt == 1 || row.zt == 5 || row.zt == 6) && !row.bBbid) {
			htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project2:bulletin:02' onClick=\"edit('"
					+ row.id + "','" + row.zt + "')\" title='修改 Edit'></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='project2:bulletin:07' onClick=\"invalid('"
					+ row.id + "','" + row.zt + "')\" title='删除 Delete '></i>&nbsp;&nbsp;";
		}
		if (row.zt == 2 && !row.bBbid) {
			htmlContent += "<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='project2:bulletin:03' onClick=\"review('"
					+ row.id + "','" + row.zt + "')\" title='审核 Review'></i>&nbsp;&nbsp;";
		}
		if (row.zt == 3 && !row.bBbid) {
			htmlContent += "<i class='icon-check color-blue cursor-pointer checkPermission' permissioncode='project2:bulletin:04' onClick=\"approve('"
					+ row.id + "','" + row.zt + "')\" title='批准 Approve'></i>&nbsp;&nbsp;";
		} 
//		if( row.zt == 4) {
//			htmlContent += "<i class='icon-print color-blue cursor-pointer '  onClick=\"toPrint('"
//			+ row.id + "')\" title='打印 Print'></i>&nbsp;&nbsp;";
//		}
		if( row.zt == 4 && !row.bBbid) {
			htmlContent += "<i class='icon-pencil color-blue cursor-pointer '  onClick=\"revision('"
			+ row.id + "')\" title='改版 Revision'></i>&nbsp;&nbsp;";
		}
		htmlContent += "</td>";
		var isjs=row.zh-row.yy;
		htmlContent +=  getWarningColor("#fefefe", row.syts,row.zt, isjs,row.tgqx,monitorsettings.yjtsJb1,monitorsettings.yjtsJb2) ;
		htmlContent += "<td  class='text-left fixed-column' title='"+StringUtil.escapeStr(formatUndefine(row.jstgh))+"' ><a href=\"javascript:viewBulletin('"
				+ row.id + "','" + row.zt+ "')\">"+ StringUtil.escapeStr(formatUndefine(row.jstgh))+ "</a></td>";
		htmlContent += "<td  class='text-center fixed-column' title='R"+ StringUtil.escapeStr(formatUndefine(row.bb))+ "'>"
				+"R"+ StringUtil.escapeStr(formatUndefine(row.bb))+ "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.jx) + "' >"+ StringUtil.escapeStr(row.jx) + "</td>";
		htmlContent += "<td class='text-center'>"+ (formatUndefine(row.tgqx).substring(0, 10))+ "</td>";
		if (isjs != 0) {
			htmlContent += "<td class='text-center'>"+ formatUndefine(row.syts) + "</td>";
		} else {
			htmlContent += "<td class='text-center'>-</td>";
		}
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(formatUndefine(row.zhut))+ "'>" + StringUtil.escapeStr(formatUndefine(row.zhut))+ "</td>";
		htmlContent += "<td class='text-left'>"+ getPgds(row.isList, row.id) + "</td>";
		htmlContent += "<td class='text-center'>"+"<a href='#' onClick=\"toCirculation('"+ row.id + "')\" >"+row.yy+"/"+row.zh + "</a></td>";
		htmlContent += "<td class='text-center' title='"+(formatUndefine(row.sxrq) == "" ? "": formatUndefine(row.sxrq).substring(0,10))+"'>"+ (formatUndefine(row.sxrq) == "" ? "": formatUndefine(row.sxrq).substring(0,10)) + "</td>";
		htmlContent += "<td title='附件 Attachment' style='text-align:center;'>";
		if(row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0){
			htmlContent += '<i qtid="'+row.id+'" class="attachment-view  glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
		} 		
		htmlContent += "</td>";
		htmlContent += "<td class='text-left' title='"+formatUndefine(DicAndEnumUtil.getEnumName('bulletinStatusEnum', row.zt))+"' >"+ formatUndefine(DicAndEnumUtil.getEnumName('bulletinStatusEnum', row.zt))+ "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(formatUndefine(row.zdr_user.displayName))+ "'>"+ StringUtil.escapeStr(formatUndefine(row.zdr_user.displayName))+ "</td>";
		htmlContent += "<td class='text-center'>"+ formatUndefine(row.zdsj) + "</td>";
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jg_dprt.dprtname))+"' >"+ StringUtil.escapeStr(formatUndefine(row.jg_dprt.dprtname))+ "</td>";
		htmlContent += "</tr>";
	});
	$("#bulletinlist").empty();
	$("#bulletinlist").html(htmlContent);
	initWebuiPopover();
	refreshPermission();
}

function initWebuiPopover(){//初始化
	WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
		return getHistoryAttachmentList(obj);
	});
	$("#bulletin_list_table_div").scroll(function(){
		$('.attachment-view').webuiPopover('hide');
	});
}
function getHistoryAttachmentList(obj){//获取历史附件列表
	var jsonData = [
	   	         {mainid : $(obj).attr('qtid'), type : '其它附件'}
	   	    ];
	 return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
}

// 拼接评估单
function getPgds(list, id) {
	var html = "";
	if (list != null) {
		for (var i = 0; i < list.length; i++) {
			if (i == 1) {
				html = html + "　<i class='icon-caret-down' id='" + id
						+ "icon' onclick=switchPgd('" + id + "')></i><div id='"
						+ id + "' style='display:none'>";
			}
			if (i == 0) {
				html += "<a href='javascript:void(0);' title='"
						+ StringUtil.escapeStr(list[i].pgdh)
						+ "' onclick=\"toViewPgd('" + list[i].pgdid
						+ "')\" >" + (StringUtil.escapeStr(list[i].pgdh))+" R"+StringUtil.escapeStr(list[i].bb)
						+ "</a>";
			}
			if (i != 0) {
				html += "<a href='javascript:void(0);' title='"
						+ StringUtil.escapeStr(list[i].pgdh)
						+ "' onclick=\"toViewPgd('" + list[i].pgdid
						+ "')\" >" + (StringUtil.escapeStr(list[i].pgdh))+" R"+StringUtil.escapeStr(list[i].bb)
						+ "</a>";
				html += "<br>";

			}
			if (i != 0 && i == list.length - 1) {
				html += "</div>";
			}
		}
	}
	return html;
}
// 单行展开评估单
function switchPgd(id) {
	new Sticky({
		tableId : 'bulletin_list_table'
	});
	if ($("#" + id).is(":hidden")) {
		$("#" + id).fadeIn(500);
		$("#" + id + 'icon').removeClass().addClass("icon-caret-up cursor-pointer");
	} else {
		$("#" + id).hide();
		$("#" + id + 'icon').removeClass().addClass("icon-caret-down cursor-pointer");
	}
}
// 列展开评估单
function vieworhidePgd() {
	new Sticky({
		tableId : 'bulletin_list_table'
	});
	var obj = $("th[name='pgd']");
	var flag = obj.hasClass("downward");
	if (flag) {
		obj.removeClass("downward").addClass("upward");
	} else {
		obj.removeClass("upward").addClass("downward");
	}
	for (var i = 0; i < choseAllIds.length; i++) {
		if (flag) {
			$("#" + choseAllIds[i]).fadeIn(500);
			$("#" + choseAllIds[i] + 'icon').removeClass().addClass("icon-caret-up cursor-pointer");
		} else {
			$("#" + choseAllIds[i]).hide();
			$("#" + choseAllIds[i] + 'icon').removeClass().addClass("icon-caret-down cursor-pointer");
		}
	}
}

// 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
function goPage(pageNumber, sortType, sequence) {
	AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
}
// 信息检索
function searchRevision() {
	TableUtil.resetTableSorting("bulletin_list_table");
	goPage(1, "auto", "desc");
}

// 搜索条件重置
function searchreset() {
	var zzjgid = $('#zzjgid').val();
	$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function() {
		$(this).val("");
	});
	$("#divSearch").find("select").each(function() {
		$(this).val("");
	});
	$("#zt_search").val("");

	$("#keyword_search").val("");

	$("#zzjg").val(zzjgid);
	initMultiselect();
}

// 搜索条件显示与隐藏
function search() {
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

// 删除
function invalid(id, zt) {
	var param = {};
	param.id = id;
	var paramsMap = {};
	paramsMap.currentZt = zt;
	param.paramsMap = paramsMap;
	AlertUtil.showConfirmMessage("确定要删除吗？", {
		callback : function() {
			AjaxUtil.ajax({
				url : basePath + "/project2/bulletin/delete",
				type : "post",
				async : false,
				contentType : "application/json;charset=utf-8",
				data : JSON.stringify(param),
				dataType : "json",
				success : function(data) {
					pageParam=encodePageParam();
					AlertUtil.showMessage('删除成功', decodePageParam());
					refreshPermission();
				}
			});
		}
	});
}
// 查看跳转
function viewBulletin(id, zt) {
	window.open(basePath + "/project2/bulletin/view?id=" + id + "&pageParam=" + encodePageParam());
}

// 字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(
			function() { // 重置class
				$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
			});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle.indexOf("sorting_asc") >= 0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
	} else {
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	goPage(currentPage, obj, orderStyle.split("_")[1]);
}

// 获取预警颜色
function getWarningColor(bgcolor, syts, zt, isjs,tgqx, yjtsJb1, yjtsJb2) {
	if (isjs == 0) {
		return "<td  class='text-center fixed-column'></td>";
	}
	if(tgqx==null||tgqx==''){
		return "<td  class='text-center fixed-column'></td>";
	}
	if (yjtsJb1 < Number(syts) && Number(syts) <= yjtsJb2) {
		bgcolor = warningColor.level2;// 黄色		 
		return "<td  class='text-center fixed-column' title='"+yjtsJb1+"&lt;剩余通告期限&lt;="+yjtsJb2+"天"+"'>"+ "<i class='iconnew-lightbulbnew' style='font-size:16px;color:" + bgcolor+ "' ></i>" + "</td>";
	}
	if (Number(syts) <= yjtsJb1) {
		bgcolor = warningColor.level1;// 红色
		return "<td  class='text-center fixed-column' title='剩余通告期限&lt;="+yjtsJb1+"天"+"' >"+ "<i class='iconnew-lightbulbnew' style='font-size:16px;color:" + bgcolor+ "' ></i>" + "</td>";
	}
	return "<td  class='text-center fixed-column'></td>";
}
// 新增
function add() {
	initModalData();
	$("#audited").hide();
	$("#reject").hide();
	$("#approve").hide();
	$("#rejected").hide();
	$("#revision").hide();
	initFjjxOption($("#zzjgid").val());// 加载飞机
	evaluation_modal.init({
		parentId : "addModal",// 第一层模态框id
		isShowed : true,// 是否显示选择评估单的操作列
		zlxl : 1,// 指令类型
		userId : $("#userId").val(),// 评估单指定人员id
		dprtcode : $("#zzjg").val(),// 组织机构
		jx : $("#fjjx").val(),// 机型
	});
	introduce_process_info_class.show({ // 流程信息
		status : 1,// 1,编写,2审核,3批准，4审核驳回,5批准驳回
	});
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
	attachmentsObj.show({
		djid:"",
		fileHead : true,
		colOptionhead : true,
		fileType:"bulletin"
	});//显示附件
	if($("#bfrq").val()==null||$("#bfrq").val()==""){
		TimeUtil.getCurrentDate("#bfrq");                          //取得当前时间
	}
	AlertUtil.hideModalFooterMessage();
	switchHistoryVersion();
	$("#addModal").modal("show");
	$("#addModal #titleName").html("新增维护提示");
	$("#addModal #titleEname").html("Add Technical Bulletin");
}
// 修改
function edit(id, zt) {
	initModalData();
	$("#audited").hide();
	$("#reject").hide();
	$("#approve").hide();
	$("#rejected").hide();
	$("#revision").hide();
	$("#jxtgbh").attr("disabled", true);
	$("#bb").attr("disabled", true);
	$("#current_bb").attr("disabled", true);
	var param = {};
	param.id = id;
	AlertUtil.hideModalFooterMessage();
	$("#addModal #titleName").html("修改维护提示");
	$("#addModal #titleEname").html("Edit Technical Bulletin");
	fillData(param,0);
	$('.date-picker').datepicker('update');
}
// 审核
function review(id,zt) {
	initModalData();
	$("#save").hide();
	$("#submit").hide();
	$("#approve").hide();
	$("#rejected").hide();
	$("#revision").hide();
	$("#wjsmdiv").hide();
	$("#bbmark").hide();
	$("#bfrqmark").hide();
	$("#jxmark").hide();
	$("#ffmark").hide();
	$("#zhutmark").hide();
	$("#addModal input[type='text']").attr("disabled", true);
	$("#addModal input[type='checkbox']").attr("disabled", true);
	$("#addModal input[type='radio']").attr("disabled", true);
	$("#addModal select").attr("disabled", true);
	$("#addModal textarea").attr("disabled", true);
	$("#addModal #jj_div_edit").attr("disabled", true);
	$("#addModal #wxrybtn").hide();
	$("#addModal #ff").addClass("div-readonly-style").removeClass("readonly-style");
	var param = {};
	param.id = id;
	AlertUtil.hideModalFooterMessage();
	$("#addModal #titleName").html("审核维护提示");
	$("#addModal #titleEname").html("Review Technical Bulletin");
	fillData(param,2);
}
// 批准
function approve(id,zt) {
	initModalData();
	$("#save").hide();
	$("#submit").hide();
	$("#reject").hide();
	$("#audited").hide();
	$("#revision").hide();
	$("#wjsmdiv").hide();
	$("#bbmark").hide();
	$("#bfrqmark").hide();
	$("#jxmark").hide();
	$("#ffmark").hide();
	$("#zhutmark").hide();
	$("#addModal input[type='text']").attr("disabled", true);
	$("#addModal input[type='checkbox']").attr("disabled", true);
	$("#addModal input[type='radio']").attr("disabled", true);
	$("#addModal select").attr("disabled", true);
	$("#addModal textarea").attr("disabled", true);
	$("#addModal #wxrybtn").hide();
	$("#addModal #ff").addClass("div-readonly-style").removeClass("readonly-style");
	var param = {};
	param.id = id;
	AlertUtil.hideModalFooterMessage();
	$("#addModal #titleName").html("批准维护提示");
	$("#addModal #titleEname").html("Approve Technical Bulletin");
	fillData(param,3);
}

//改版
function revision(id) {
	initModalData();
	$("#save").hide();
	$("#submit").hide();
	$("#audited").hide();
	$("#reject").hide();
	$("#approve").hide();
	$("#rejected").hide();
	$("#jxtgbh").attr("disabled", true);
	var param = {};
	param.id = id;
	AlertUtil.hideModalFooterMessage();
	$("#addModal #titleName").html("改版维护提示");
	$("#addModal #titleEname").html("Revision Technical Bulletin");
	fillData(param,4);
}

// 回写数据
function fillData(param,obj) {
	AjaxUtil.ajax({
		url : basePath + "/project2/bulletin/edit",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		async : false,
		success : function(data) {
			$("#id").val(data.id);
			$("#zt").val(data.zt);
			$("#dprt").val(data.dprtcode);
			$("#jxtgbh").val(data.jstgh);
			$("#bb").val(data.bb);
			$("#current_bb").val(data.bb);
			$("#zhut").val(data.zhut);
			initFjjxOption(data.dprtcode);// 加载飞机
			$("#fjjx").val(data.jx);
			$("#bfrq").val(data.bfrq == null ? "" : data.bfrq.toString().substr(0, 10));
			$("#sxrq").val(data.sxrq == null ? "" : data.sxrq.toString().substr(0, 10));
			$("#tgqx").val(data.tgqx == null ? "" : data.tgqx.toString().substr(0, 10));
			var rObj = document.getElementsByName('sj');// 涉及
			for (var i = 0; i < rObj.length; i++) {
				if (rObj[i].value == data.sj) {
					rObj[i].checked = 'checked';
				}
			}
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
					if(row.department){
					oldbmidMap[row.department.id] = row;
					oldbmidList.push(row.department.id);
					dprtcode += row.department.id + ","
					dprtname += row.department.dprtname + ","
					}
				});
				dprtcode = dprtcode.substring(0, dprtcode.length - 1);
				dprtname = dprtname.substring(0, dprtname.length - 1);
				$("#ff").html(dprtname);
				$("#ffid").val(dprtcode);
			}
			if (data.isFjxx == 1) {// 附加信息
				$("input[name='is_fjxx']").attr("checked", true);
				document.getElementById('fjxxdiv').style.display="block";
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
				if(data.zt == 3 || data.zt == 2 ){					
					document.getElementById('fjxxpanel').style.display="none";
				}
			}
			evaluation_modal.init({
				parentId : "addModal",
				isShowed : (data.zt==2||data.zt==3)?false:true,
				zlid : data.id,
				zlxl : 1,
				dprtcode :data.dprtcode,
				jx : $("#fjjx").val(),
			});
			introduce_process_info_class.show({ // 流程信息
				status : data.zt,// 1,编写,2审核,3批准，4审核驳回,5批准驳回
				prepared : data.zdr_user.username + " "+ data.zdr_user.realname,// 编写人
				preparedDate : data.zdsj,// 编写日期
				checkedOpinion : data.shyj,// 审核意见
				checkedby : data.shr_user != null ? data.shr_user.username+ " " + data.shr_user.realname : "",// 审核人
				checkedDate : data.shsj,// 审核日期
				checkedResult : data.shjl,// 审核结论
				approvedOpinion : data.pfyj,// 批准意见
				approvedBy : data.pfr_user != null ? data.pfr_user.username+ " " + data.pfr_user.realname : "",// 批准人
				approvedDate : data.pfsj,// 批准日期
				approvedResult : data.pfjl ,// 审批结论
			});
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
			attachmentsObj.show({
				djid:data.id,
				fileHead : (data.zt==2||data.zt==3)?false:true,
				colOptionhead : (data.zt==2||data.zt==3)?false:true,
				fileType:"bulletin"
			});//显示附件
			if(obj==data.zt||(obj==0&&data.zt==1||data.zt==5||data.zt==6)){
				// 改版
				if(obj == 4){
					data.previous = {
						id : data.id,
						bb : data.bb
					};
					$("#current_bb").val("");
					$("#id").val("");
					$("#fBbid").val(data.id);
					$("#introduce_process_info_class_lcxx").hide();
				}
				switchHistoryVersion(data);
				$("#addModal").modal("show");
			}else{
				AlertUtil.showMessage("该数据已更新，请刷新后再进行操作!");
			}			
		}
	});
}

// 初始化机型下拉框
function initFjjxOption(dprtcode) {
	var typeList = acAndTypeUtil.getACTypeList({
		DPRTCODE : dprtcode
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
		$("#zt").val(params.zt);
		$("#zzjg").val(params.zzjg);
		$("#tgqx").val(params.tgqx);
		if (pageParamJson.pagination) {
			goPage(pageParamJson.pagination.page,
					pageParamJson.pagination.sort,
					pageParamJson.pagination.order);
		} else {
			goPage(1, "auto", "desc");
		}
	} catch (e) {
		goPage(1, "auto", "desc");
	}
}
// 刷新页面
function refreshPage() {
	goPage(pagination.page, pagination.sort, pagination.order);
}

// 初始化机型多选下拉框
function initMultiselect() {
	initPlaneList();
	// 生成多选下拉框(机型)
	$("#jxdiv").empty();
	$("#jxdiv").html("<select multiple='multiple'  id='jx'></select>");
	$("#jx").append(planeModelOption);
	planeModelOption = '';
	$("#jx").multiselect({
		buttonClass : 'btn btn-default ',
		buttonWidth: '100%',
		numberDisplayed:100,
		buttonContainer: '<div class="btn-group" style="width:100%;" />',
		includeSelectAllOption : true,
		nonSelectedText:'显示全部 All',
		allSelectedText:'显示全部 All',
		selectAllText: '选择全部 Select All',
		onChange : function(element, checked) {
		}
	});
}

function initPlaneList() {
	var typeList = acAndTypeUtil.getACTypeList({
		DPRTCODE : $("#zzjg").val()
	});
	if (typeList.length > 0) {
		for (var i = 0; i < typeList.length; i++) {
			planeModelOption += "<option value='"
					+ StringUtil.escapeStr(typeList[i].FJJX) + "'>"
					+ StringUtil.escapeStr(typeList[i].FJJX) + "</option>";
		}
	} else {
		planeModelOption = "<option value=''>暂无飞机机型No data</option>";
	}
	return planeModelOption;
}

$("#ff").dblclick(function(){
	openzrdw();
})

function openzrdw() {
	if($("#zt").val()!=2 && $("#zt").val()!=3){
		var this_ = this;
		var dprtname = $("#ff").val();
		var dprtcode = $("#ffid").val();
		departmentModal.show({
			dprtnameList : dprtname,// 部门名称
			dprtcodeList : dprtcode,// 部门id
			type : true,// 勾选类型true为checkbox,false为radio
			parentid : "addModal",
			clearDepartment : false,
			dprtcode : $("#dprt").val()==""?userJgdm:$("#dprt").val(),// 默认登录人当前机构代码
			callback : function(data) {// 回调函数		
				$("#ff", $("#addModal")).html(data.dprtname);
				$("#ffid").val(data.dprtcode);
				newbmidMap={};
				newbmidList=[];
				var departmentList = data.departmentList;
				$.each(departmentList, function(index, row) {
					newbmidMap[row.id] = row;
					newbmidList.push(row.id);
				})
			}
		})
	}
	
}

// 附加信息
$('input[name="is_fjxx"]').click(function() {
	if ($('input[name="is_fjxx"]').attr("checked")) {
		document.getElementById('fjxxdiv').style.display="block";
		$("#fjxxdiv input[type='text']").attr("disabled", true);
		$("#fjxxdiv input[type='radio']").attr("disabled", true);
		$("#fjxxdiv input[type='checkbox']").attr("disabled", false);
		$("#fjxxdiv input[type='radio']").attr("checked", false);
		$("#fjxxdiv input[name='is_wcfjpc']").attr("checked", false);
		$("#fjxxdiv input[name='is_bjzjhs']").attr("checked", false);
	} else {
		document.getElementById('fjxxdiv').style.display="none";
		$("#fjxxdiv input[type=text]").attr("disabled", true);
		$("#fjxxdiv input[type=text]").val("");
		$("#fjxxdiv input[type=radio]").attr("disabled", true);
		$("#fjxxdiv input[type=radio]").attr("checked", false);
		$("#fjxxdiv input[type=checkbox]").attr("disabled", true);
		$("#fjxxdiv input[type=checkbox]").attr("checked", false);
	}
});
// 已完成现有机翼的普装检查
$('input[name="is_wcfjpc"]').click(function() {
	if ($('input[name="is_wcfjpc"]').attr("checked")) {
		$('input[name="is_fj_syxbj"]').attr("disabled", false);
		$("#syxbj_fj").attr("disabled", false);
		$("#eo_mao").attr("disabled", false);
		var isfjsyxbj = document.getElementsByName('is_fj_syxbj');
		for (var i = 0; i < isfjsyxbj.length; i++) {
			if (isfjsyxbj[i].value == 1) {
				isfjsyxbj[i].checked = 'checked';
			}
		}
	} else {
		$('input[name="is_fj_syxbj"]').attr("disabled", true);
		$("#syxbj_fj").attr("disabled", true);
		$('input[name="is_fj_syxbj"]').removeAttr('checked');
		$("#eo_mao").attr("disabled", true);
		$("#eo_mao").val("");
		$("#syxbj_fj").val("");
	}
});
// 部件装机清单
$('input[name="is_bjzjhs"]').click(function() {
	if ($(this).attr("checked")) {
		$('input[name="is_bj_syxbj"]').attr("disabled", false);
		$("#syxbj_bj").attr("disabled", false);
		var isbjsyxbj = document.getElementsByName('is_bj_syxbj');
		for (var i = 0; i < isbjsyxbj.length; i++) {
			if (isbjsyxbj[i].value == 1) {
				isbjsyxbj[i].checked = 'checked';
			}
		}
	} else {
		$('input[name="is_bj_syxbj"]').attr("disabled", true);
		$('input[name="is_bj_syxbj"]').removeAttr('checked');
		$("#syxbj_bj").attr("disabled", true);
		$("#syxbj_bj").val("");
	}
});
// 部件装机清单影响部件
$('input[name="is_bj_syxbj"]').click(function() {
	if ($(this).val() == 0) {
		$("#syxbj_bj").val("");
		$("#syxbj_bj").attr("disabled", true);
	} else {
		$("#syxbj_bj").attr("disabled", false);
	}
	$('input[name="is_bj_syxbj"]').not(this).removeAttr('checked');
});
// 机翼的普装检查影响部件
$('input[name="is_fj_syxbj"]').click(function() {
	if ($(this).val() == 0) {
		$("#syxbj_fj").val("");
		$("#syxbj_fj").attr("disabled", true);
	} else {
		$("#syxbj_fj").attr("disabled", false);
	}
	$('input[name="is_fj_syxbj"]').not(this).removeAttr('checked');
});
// 封装新增修改modal中数据
function getData() {
	var param = {};
	if($("#dprt").val() != ''){
		param.dprtcode = $("#dprt").val();
	}else{
		param.dprtcode = $("#zzjgid").val();
	}	
	param.zdbmid = $("#zdbmid").val();
	param.zdrid = $("#userId").val();
	param.jstgh = $.trim($("#jxtgbh").val());
	param.bb = $("#bb").is(":visible") ? $.trim($("#bb").val()) : $.trim($("#current_bb").val());
	param.zhut = $.trim($("#zhut").val());
	param.jx = $.trim($("#fjjx").val());
	param.bfrq = $.trim($("#bfrq").val());
	param.sxrq = $.trim($("#sxrq").val());
	param.tgqx = $.trim($("#tgqx").val());
	param.sj = $('input[name="sj"]:checked').val();
	param.yxx = $.trim($("#yxx").val());
	param.lysm = $.trim($("#lysm").val());
	param.ckzl = $.trim($("#ckzl").val());
	param.bj = $.trim($("#bj").val());
	param.ms = $.trim($("#ms").val());
	param.cs = $.trim($("#cs").val());
	param.tglb = $('input[name="tglb"]:checked').val();
	param.gbyy = $.trim($('#gbyy').val());
	var paramsMap = {};
	paramsMap.currentZt = $("#zt").val();
	if ($("#ffid").val() != null && $("#ffid").val() != "") {
		if ($("#id").val() == null || $("#id").val() == "") {
			paramsMap.department = $("#ffid").val();
		} else {
			paramsMap.del = comparBmid().del;
			paramsMap.add = comparBmid().add;
		}
	}
	param.paramsMap = paramsMap;
	// 技术评估单数据
	var technicalList = evaluation_modal.getData();
	if (technicalList != null && technicalList != '') {
		var instructionsourceList = [];
		// 组装下达指令数据
		$.each(technicalList, function(index, row) {
			var instructionsource = {};
			instructionsource.dprtcode = row.dprtcode;
			instructionsource.pgdid = row.id;
			instructionsource.pgdh = row.pgdh;
			instructionsource.bb = row.bb;
			instructionsourceList.push(instructionsource);
		});
		param.isList = instructionsourceList;
	}
	if ($('input[name="is_fjxx"]').attr("checked")) {
		param.isFjxx = 1;
		if ($('input[name="is_wcfjpc"]').attr("checked")) {
			param.isWcfjpc = 1;
			param.eoMao = $.trim($("#eo_mao").val());
			param.isFjSyxbj = $("input[name='is_fj_syxbj']:checked").val();
			param.syxbjFj = $.trim($("#syxbj_fj").val());
		} else {
			param.isWcfjpc = 0;
		}
		if ($('input[name="is_bjzjhs"]').attr("checked")) {
			param.isBjzjhs = 1;
			param.isBjSyxbj = $("input[name='is_bj_syxbj']:checked").val();
			param.syxbjBj = $.trim($("#syxbj_bj").val());
		} else {
			param.isBjzjhs = 0;
		}
		if ($('input[name="is_wpc"]').attr("checked")) {
			param.isWpc = 1;
		} else {
			param.isWpc = 0;
		}
	} else {
		param.isFjxx = 0;
	}
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
	param.attachments = attachmentsObj.getAttachments();
	return param;
}
// 保存
function saveData() {
	startWait($("#addModal"));
	$('#form').data('bootstrapValidator').validate();
	if (!$('#form').data('bootstrapValidator').isValid()) {
		AlertUtil.showModalFooterMessage("请根据提示输入正确信息!");
		finishWait($("#addModal"));
		return false;
	}
	if ($("#bfrq").val() > $("#sxrq").val() && $("#sxrq").val() != '') {
		AlertUtil.showModalFooterMessage("生效日期不能早于颁发日期!");
		finishWait($("#addModal"));
		return false
	}
	if ($("#bfrq").val() > $("#tgqx").val() && $("#tgqx").val() != '') {
		AlertUtil.showModalFooterMessage("通告期限日期不能早于颁发日期!");
		finishWait($("#addModal"));
		return false
	}
	if ($("#sxrq").val() != '' && $("#tgqx").val() != '' && $("#sxrq").val() > $("#tgqx").val()) {
		AlertUtil.showModalFooterMessage("通告期限日期不能早于生效日期!");
		finishWait($("#addModal"));
		return false
	}
	if($("#ffid").val() == ''){
		AlertUtil.showModalFooterMessage("分发部门不能为空!");
		finishWait($("#addModal"));
		return false
	}
	var param = {};
	param = getData();
	// 审批审核驳回的保存不修改状态
	if ($("#zt").val() != 5 && $("#zt").val() != 6) {
		param.zt = 1;
	} else {
		param.zt = $("#zt").val();
	}
	var id = $("#id").val();
	if (id != null && id != '') {
		param.id = id;
		url = basePath + "/project2/bulletin/update";
		var message = "修改成功!";
	} else {
		url = basePath + "/project2/bulletin/add";
		var message = "保存成功!";
	}
	sendDataRequest(param, url, message)
}
// 提交
function submitData() {
	startWait($("#addModal"));
	$('#form').data('bootstrapValidator').validate();
	if (!$('#form').data('bootstrapValidator').isValid()) {
		AlertUtil.showModalFooterMessage("请根据提示输入正确信息!");
		finishWait($("#addModal"));
		return false;
	}
	if ($("#bfrq").val() > $("#sxrq").val() && $("#sxrq").val() != '') {
		AlertUtil.showModalFooterMessage("生效日期不能早于颁发日期!");
		finishWait($("#addModal"));
		return false
	}
	if ($("#bfrq").val() > $("#tgqx").val() && $("#tgqx").val() != '') {
		AlertUtil.showModalFooterMessage("通告期限日期不能早于颁发日期!");
		finishWait($("#addModal"));
		return false
	}
	if ($("#sxrq").val() != '' && $("#tgqx").val() != '' && $("#sxrq").val() > $("#tgqx").val()) {
		AlertUtil.showModalFooterMessage("通告期限日期不能早于生效日期!");
		finishWait($("#addModal"));
		return false
	}
	if($("#ffid").val() == ''){
		AlertUtil.showModalFooterMessage("分发部门不能为空!");
		finishWait($("#addModal"));
		return false
	}
	var param = {};
	param = getData();
	param.zt = 2;
	var id = $("#id").val();
	if (id != null && id != '') {
		param.id = id;
		url = basePath + "/project2/bulletin/update";
	} else {
		url = basePath + "/project2/bulletin/add";
	}
	var message = "提交成功!";
	tip("确定要提交吗？",param,url,message);
}
// 审核
function auditedData(obj) {
	var shyj = introduce_process_info_class.getData().shenhe;
		var param = packingReview();
		param.shyj = shyj;
		var paramsMap = {};
		paramsMap.currentZt = $("#zt").val();
		param.paramsMap = paramsMap;
		var url = basePath + "/project2/bulletin/review";
		var message = "";
		if (obj == '3') {
			param.zt = 3;
			param.shjl = 3;
			message = "审核通过成功!";
			tip("确定要审核通过吗？",param,url,message);
		} else {
			param.zt = 5;
			param.shjl = 5;
			message = "审核驳回成功!";
			tip("确定要审核驳回吗？",param,url,message);
		}
}

// 批准
function approvedData(obj) {
	var pfyj = introduce_process_info_class.getData().shenpi;
		var param = packingApprove();
		param.pfyj = pfyj;
		var paramsMap = {};
		paramsMap.currentZt = $("#zt").val();
		param.paramsMap = paramsMap;
		var url = basePath + "/project2/bulletin/review";
		var message = '';
		if (obj == '4') {
			param.zt = 4;
			param.pfjl = 4;
			message = "批准通过!";
			tip("确定要批准通过吗？",param,url,message);
		} else {
			param.zt = 6;
			param.pfjl = 6;
			message = "批准驳回成功!";
			tip("确定要批准驳回吗？",param,url,message);
		}
}

function tip(tipmessage,param,url,message){
	AlertUtil.showConfirmMessage(tipmessage, {
		callback : function() {
				sendDataRequest(param, url, message);
		}
	});
	finishWait($("#addModal"));
}

// 组装审核内容
function packingReview() {
	var param = {}
	param.shrid = $("#userId").val();
	param.id = $("#id").val();
	param.shbmid = $("#zdbmid").val();
	return param;
}
// 组装批复内容
function packingApprove() {
	var param = {}
	param.pfrid = $("#userId").val();
	param.id = $("#id").val();
	param.pfbmid = $("#zdbmid").val();
	return param;
}
// 发送请求
function sendDataRequest(param, url, message) {
	AjaxUtil.ajax({
		url : url,
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		modal : $("#addModal"),
		success : function(data) {
			id = data;				
			pageParam=encodePageParam();
			AlertUtil.showMessage(message, decodePageParam());		
			$("#addModal").modal("hide");
			finishWait($("#addModal"));
			refreshPermission();
		}
	});
}
// 组装新增删除的部门便于后台写Rec
function comparBmid() {
	var map = {};
	var addbmidList = "";
	for (var i = 0; i < newbmidList.length; i++) {
		if (oldbmidMap[newbmidList[i]] == null
				|| oldbmidMap[newbmidList[i]] == undefined) {
			addbmidList += newbmidList[i] + ",";
		}
	}
	addbmidList = addbmidList.substring(0, addbmidList.length - 1);
	var delmidList = '';
	for (var i = 0; i < oldbmidList.length; i++) {
		if (newbmidList.length > 0 && newbmidMap[oldbmidList[i]] == null) {
			delmidList += oldbmidMap[oldbmidList[i]].id + ",";
		}
	}
	delmidList = delmidList.substring(0, delmidList.length - 1);
	map.add = addbmidList;
	map.del = delmidList;
	return map;
}

function validation() {
	validatorForm = $("#form").bootstrapValidator({
		message : '数据不合法',
		feedbackIcons : {
			// valid: 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			jxtgbh : {
				validators : {
					regexp : {
						regexp : /^[a-zA-Z0-9-_\x21-\x7e]{1,50}$/,
						message : '只能输入长度不超过50个字符的英文、数字、英文特殊字符!'
					}
				}
			},
			bb : {
				validators : {
					notEmpty : {
						message : '版本不能为空'
					},
					regexp : {
						regexp : /^\d+(\.\d{1,2})?$/,
						message : '请输入整数或小数点后2位小数'
					}
				}
			},
			current_bb : {
				validators : {
					notEmpty : {
						message : '版本不能为空'
					},
					regexp : {
						regexp : /^\d+(\.\d{1,2})?$/,
						message : '请输入整数或小数点后2位小数'
					},
					callback: {
                        message: '版本必须大于上个版本',
                        callback: function(value, validator) {
                        	var previous = $("#previous_bb").text();
                        	if(value && previous && !isNaN(value) && parseFloat(value) <= parseFloat(previous)){
                        		return false;
                        	}
                        	return true;
                        }
                    }
				}
			},
			bfrq : {
				validators : {
					notEmpty : {
						message : '颁发日期不能为空'
					}
				}
			},
			zhut : {
				validators : {
					notEmpty : {
						message : '主题不能为空'
					}
				}
			},
			fjjx : {
				validators : {
					notEmpty : {
						message : '机型不能为空'
					}
				}
			}
		}
	})
}
// 初始化modal数据
function initModalData() {
	$("#addModal input[type='text']").val("");
	$("#addModal #ff").html("");
	$("#addModal #ff").removeClass("div-readonly-style").addClass("readonly-style");
	$("#addModal button[type='button']").show();	
	$("#addModal input[type='text']").attr("disabled", false);
	$("#addModal input[type='checkbox']").attr("disabled", false);
	$("#addModal input[type='radio']").attr("disabled", false);
	$("#bbmark").show();
	$("#bfrqmark").show();
	$("#jxmark").show();
	$("#ffmark").show();
	$("#zhutmark").show();
	$("#addModal #wxrybtn").show();
	var sj = document.getElementsByName('sj');
	for (var i = 0; i < sj.length; i++) {
		if (sj[i].value == 1) {
			sj[i].checked = 'checked';
		}
	}
	var tglb = document.getElementsByName('tglb');
	for (var i = 0; i < tglb.length; i++) {
		if (tglb[i].value == 1) {
			tglb[i].checked = 'checked';
		}
	}
	$("#addModal input[name='is_fjxx']").attr("checked",false);
	document.getElementById('fjxxdiv').style.display="none";
	document.getElementById('fjxxpanel').style.display="block";
	$("#addModal textarea").val("");
	$("#addModal textarea").attr("disabled", false);
	$("#addModal select").attr("disabled", false);
	$("#addModal select").empty();
	$("#addModal #id").val("");
	$("#addModal #ffid").val("");
	$("#addModal #zt").val("");
	$("#addModal #dprt").val("");
	$("#addModal #bfrq").datepicker("update");
	$("#addModal #sxrq").datepicker("update");
	$("#addModal #tgqx").datepicker("update");
	oldbmidMap = {};
	oldbmidList = [];
	newbmidMap = {};
	newbmidList = [];
}

$("#addModal").on("hidden.bs.modal", function() {
	$("#form").data('bootstrapValidator').destroy();
	$('#form').data('bootstrapValidator', null);
	validation();
});

$("#fjjx").change(function() {
	evaluation_modal.changeParam({
		jx : $("#fjjx").val(),// 机型
	});
})

$("#zzjg").change(function() {
	initMultiselect();
})

$("#zt_search, #bb_search").change(function() {
	decodePageParam();
})

$('#sxrq').datepicker({
	autoclose : true,
	clearBtn : true
}).on('hide',function(e) {
	$('#form1').data('bootstrapValidator').updateStatus('sxrq',
				'NOT_VALIDATED', null).validateField('sxrq');
});
$('#bfrq').datepicker({
	autoclose : true,
	clearBtn : true
}).on(
		'hide',
		function(e) {
			$('#form').data('bootstrapValidator').updateStatus('bfrq',
					'NOT_VALIDATED', null).validateField('bfrq');
		});
$('#tgqx').datepicker({
	autoclose : true,
	clearBtn : true
}).on(
		'hide',
		function(e) {
			$('#form').data('bootstrapValidator').updateStatus('tgqx',
					'NOT_VALIDATED', null).validateField('tgqx');
		});

// 只能输入数字和小数,保留两位
function clearNoNumTwo(obj) {
	// 先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g, "");
	// 必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g, "");
	// 保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g, ".");
	// 保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",
			".");

	var str = obj.value.split(".");
	if (str.length > 1) {
		if (str[1].length > 2) {
			obj.value = str[0] + "." + str[1].substring(0, 2);
		}
	}
	if (obj.value.length > 1 && obj.value.substring(0, 1) == 0) {
		if (obj.value.substring(1, 2) != ".") {
			obj.value = 0;
		}
	}
	obj.value = validateMax(obj.value);
	$("#form").data('bootstrapValidator').destroy();
	$('#form').data('bootstrapValidator', null);
	validation();
	function validateMax(_value) {
		if (Number(_value) > 9999999999.99) {
			return validateMax(_value.substr(0, _value.length - 1));
		}
		return _value;
	}
}

function toCirculation(id){
	var param = {};
	param.id = id;
	AjaxUtil.ajax({
		url : basePath + "/project2/bulletin/viewMark",
		type : "post",
		async : false,
		contentType : "application/json;charset=utf-8",
		data : JSON.stringify(param),
		dataType : "json",
		success : function(data) {
			if(data.length>0){
				var htmlContent='';
				$.each(data,function(index,row){
					htmlContent += "<tr>";
					htmlContent += "<td >"+StringUtil.escapeStr(row.department?row.department.dprtname:"")+"</td>";
					htmlContent += "<td class='text-center'>"+(row.isJs==1?"已阅":row.isJs==0?"未阅":"")+"</td>";
					htmlContent += "<td class='text-center'>"+(row.isJs==1?formatUndefine(row.whsj):"")+"</td>";
					htmlContent += "</tr>";
				});
				$("#viewMarkupStatus_List").empty();
				$("#viewMarkupStatus_List").html(htmlContent);
			}
		}
	});
	$("#viewMarkupStatus_modal").modal("show");
}

function checkRow(index,this_){
	var flag = $(this_).is(":checked");
	if(flag){
		$(this_).removeAttr("checked");
	}else{
		$(this_).attr("checked",true);
	}
}
function clickRow(index,this_){
	
	var $checkbox1 = $("#bulletinlist :checkbox[name='checkrow']:eq("+index+")");
	var $checkbox2 = $(".sticky-col :checkbox[name='checkrow']:eq("+index+")");
	var checked = $checkbox1.is(":checked");
	$checkbox1.attr("checked", !checked);
	$checkbox2.attr("checked", !checked);
	new Sticky({tableId:'bulletin_list_table'}); //初始化表头浮动
}

//打开批量审核批准对话框
function batchApproveWin(isApprovel){
	var idArr = [];
	var approvalArr = [];
	var approvalNotArr = [];
	var zt = isApprovel?3:2;
	$("#bulletinlist").find("tr input:checked").each(function(){
		var index = $(this).attr("index");
		var bulletin = bulletinData[index];
		if(bulletin.zt == zt ){
			idArr.push(bulletin.id);
			approvalArr.push(bulletin.jstgh);
   	 	}else{
   	 		approvalNotArr.push(bulletin.jstgh);
   	 	}
		
	});
	if(approvalArr.length == 0 && approvalNotArr.length == 0){
		AlertUtil.showMessage("请选中数据后再进行操作！");
	}else{
		BatchApprovelModal.show({
			approvalArr:approvalArr,//审核可操作的数据
			approvalNotArr:approvalNotArr,//审核不可操作的数据
			isApprovel:isApprovel,//判断审核还是批准
			callback: function(data){//回调函数
				if(idArr.length > 0){
					batchApproval(idArr,data,isApprovel);
				}
			}
		});
	}
}

function batchApproval(idList,data,isApprovel){
	var url = basePath+"/project2/bulletin/updateBatchAudit";
	var message = '批量审核成功!';
	if(isApprovel){
		url = basePath+"/project2/bulletin/updateBatchApprove";
		message = '批量批准成功!';
	}
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		type:"post",
		data:{
			idList:idList,yj:data.yj
		},
		dataType:"json",
		success:function(message){
			finishWait();
			AlertUtil.showMessage(message);
			refreshPage();
		}
	});
}

function exportExcel(){
	var paramsMap={};
	if (null != tgqx && "" != tgqx) {
		var tgqxBegin = tgqx.substring(0, 10) + " 00:00:00";
		var tgqxEnd = tgqx.substring(13, 23) + " 23:59:59";
		paramsMap.tgqxBegin = tgqxBegin;
		paramsMap.tgqxEnd = tgqxEnd;
	}
	if ($("#jx").val() != null || $("#jx").val() != undefined) {
		paramsMap.jx = $("#jx").val();
	}
	
	var keyword = $.trim($("#keyword_search").val());// 关键字查询
	var zt = $.trim($("#zt_search").val());
	var dprtcode = $.trim($("#zzjg").val());
	var tgqx = $.trim($("#tgqx_search").val());
	var tgqxBegin='';
	var tgqxEnd='';
	if (null != tgqx && "" != tgqx) {
		tgqxBegin = tgqx.substring(0, 10) + " 00:00:00";
		tgqxEnd = tgqx.substring(13, 23) + " 23:59:59";
	}
	var jx =[];
	var jxArr="";
	if ($("#jx").val() != null || $("#jx").val() != undefined) {
		jx = $("#jx").val();
		for(var i = 0 ; i < jx.length ; i++){
			if('multiselect-all' != jx[i]){
				jxArr +=jx[i]+",";
			}
		}
	}
	if(jxArr!=""){
		jxArr = jxArr.substring(0,jxArr.length-1);
	}
	
	 window.open(basePath+"/project2/bulletin/bulletinExcel.xls?jxArr=" + encodeURIComponent(jxArr) + "&tgqxBegin="
	 			+ encodeURIComponent(tgqxBegin)+"&tgqxEnd="+encodeURIComponent(tgqxEnd) + "&zt=" + zt + "&tgqx=" + encodeURIComponent(tgqx )+ "&dprtcode="
	 			+ dprtcode + "&keyword="+ encodeURIComponent(keyword));
}

function toViewPgd(id){
	window.open(basePath+"/project2/assessment/view?id="+id);
}

//全选
function performSelectAll(){
	$(":checkbox[name=checkrow]").attr("checked", true);
}
//取消全选
function performSelectClear(){
	$(":checkbox[name=checkrow]").attr("checked", false);
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
	WebuiPopoverUtil.initWebuiPopover('attachment-view2',"#addModal",function(obj){
		return getHistoryBbList(id);
	});
}

//获取历史版本列表
function getHistoryBbList (id){
	return bulletin_history_alert_Modal.getHistoryBbList(id);
}

//改版
function revisionData() {
	startWait($("#addModal"));
	$('#form').data('bootstrapValidator').validate();
	if (!$('#form').data('bootstrapValidator').isValid()) {
		AlertUtil.showModalFooterMessage("请根据提示输入正确信息!");
		finishWait($("#addModal"));
		return false;
	}
	if ($("#bfrq").val() > $("#sxrq").val() && $("#sxrq").val() != '') {
		AlertUtil.showModalFooterMessage("生效日期不能早于颁发日期!");
		finishWait($("#addModal"));
		return false
	}
	if ($("#bfrq").val() > $("#tgqx").val() && $("#tgqx").val() != '') {
		AlertUtil.showModalFooterMessage("通告期限日期不能早于颁发日期!");
		finishWait($("#addModal"));
		return false
	}
	if ($("#sxrq").val() != '' && $("#tgqx").val() != '' && $("#sxrq").val() > $("#tgqx").val()) {
		AlertUtil.showModalFooterMessage("通告期限日期不能早于生效日期!");
		finishWait($("#addModal"));
		return false
	}
	if($("#ffid").val() == ''){
		AlertUtil.showModalFooterMessage("分发部门不能为空!");
		finishWait($("#addModal"));
		return false
	}
	var param = {};
	param = getData();
	param.zt = 1;
	param.fBbid = $("#fBbid").val();
	var url = basePath + "/project2/bulletin/revision";
	var message = "改版成功!";
	tip("确定要改版吗？",param,url,message);
}