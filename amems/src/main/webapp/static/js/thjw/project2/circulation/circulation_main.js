var choseAllIds = [];
var planeModelOption='';
$(document).ready(
		function() {
			Navigation(menuCode, '', '', 'gc_4_2', '岳彬彬', '2017-08-01', '岳彬彬', '2017-08-17');
			goPage(1, "auto", "desc");// 开始的加载默认的内容
			$('#tgqx_search').daterangepicker().prev().on("click", function() {
				$(this).next().focus();
			});
			initMultiselect();
			initLxMultiselect();
		});
// 带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
	var obj = {};
	if ($("input[name='cy']:checked").size() == 0) {
		$("#circulation_list").empty();
		$("#circulation_list").append("<tr class='text-center'><td colspan=\"15\">暂无数据 No data.</td></tr>");
		return false;
	}
	var isjs = '';
	$("input[name='cy']:checked").each(function() {
		if (isjs == 1) {
			isjs = '';
		} else {
			isjs = this.value;
		}
	});
	var searchParam = gatherSearchParam();
	obj.keyword = searchParam.keyword;
	obj.dprtcode = searchParam.dprtcode;
	obj.pagination = {
		page : pageNumber,
		sort : sortType,
		order : sequence,
		rows : 20
	};
	var tgqx = $.trim($("#tgqx_search").val());
	var paramsMap = {};
	if (null != tgqx && "" != tgqx) {
		var tgqxBegin = tgqx.substring(0, 10) + " 00:00:00";
		var tgqxEnd = tgqx.substring(13, 23) + " 23:59:59";
		paramsMap.tgqxBegin = tgqxBegin;
		paramsMap.tgqxEnd = tgqxEnd;
	}
	paramsMap.isjs = isjs;
	paramsMap.jsrid =$("#userId").val();
	paramsMap.dxid = $("#zdbmid").val();
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
	if ($("#lx").val() != null || $("#lx").val() != undefined) {
		var lx=$("#lx").val();
		var lxArr=[];
		for(var i = 0 ; i < lx.length ; i++){
			if('multiselect-all' != lx[i]){
				lxArr.push(lx[i]);
			}
		}
		paramsMap.ywlx = lxArr;
	}
	//paramsMap.bb = $.trim($("#bb_search").val());
	obj.paramsMap = paramsMap;
	startWait();
	AjaxUtil
			.ajax({
				url : basePath + "/project2/bulletin/queryCirculationList",
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(obj),
				success : function(data) {
					finishWait();
					if (data.total > 0) {
						appendContentHtml(data.rows,data.monitorsettings);
						var page_ = new Pagination({
							renderTo : "pagination",
							data : data,
							sortColumn : sortType,
							orderType : sequence,
							extParams : {},
							goPage : function(a, b, c) {
								AjaxGetDatasWithSearch(a, b, c);
							}// ,
						});
						// 标记关键字
						signByKeyword($("#keyword_search").val(),
								[ 3,8 ]);
					} else {
						$("#circulation_list").empty();
						$("#mpagination").empty();
						$("#circulation_list").append("<tr class='text-center'><td colspan=\"15\">暂无数据 No data.</td></tr>");
					}
					new Sticky({
						tableId : 'quality_check_list_table'
					});
				}
			});

}
// 将搜索条件封装
function gatherSearchParam() {
	var searchParam = {};
	searchParam.keyword = $.trim($("#keyword_search").val());// 关键字查询
	searchParam.dprtcode = $.trim($("#zzjg").val());
	return searchParam;
}

function appendContentHtml(list,monitorsettings) {
	var htmlContent = '';
	$.each(list,function(index, row) {
		choseAllIds.push(row.id);
		htmlContent = htmlContent + "<tr  >";
		//htmlContent = htmlContent + "<td class='text-center' ><i class='icon-print color-blue cursor-pointer checkPermission' permissioncode='project:annunciate:main:06' onClick=\"toPrint('"
		//			+ row.id
		//			+ "')\" title='打印 Print'></i></td>";
		htmlContent +=  getWarningColor("#fefefe", row.syts,row.zt, row.isjs,row.tgqx,monitorsettings.yjtsJb1,monitorsettings.yjtsJb2) ;
		htmlContent = htmlContent + "<td class='text-left' title='" + DicAndEnumUtil.getEnumName("projectBusinessEnum",StringUtil.escapeStr(row.paramsMap.ywlx)) + "'>"
		+ DicAndEnumUtil.getEnumName("projectBusinessEnum",StringUtil.escapeStr(row.paramsMap.ywlx)) + "</td>";
		htmlContent = htmlContent + "<td class='text-left ' title='" + StringUtil.escapeStr(row.jstgh) + "' ><a href=\"javascript:viewBulletin('"
		+row.paramsMap.ywlx+"','"+row.id + "','" + row.zt + "')\">" + StringUtil .escapeStr(formatUndefine(row.jstgh))+ "</a></td>";
		htmlContent = htmlContent + "<td class='text-center' title='R" + StringUtil.escapeStr(row.bb) + "'>"+"R"+ StringUtil.escapeStr(row.bb) + "</td>";
		htmlContent = htmlContent + "<td class='text-left' title='" + StringUtil.escapeStr(row.jx) + "'>"
				+ StringUtil.escapeStr(row.jx) + "</td>";
		htmlContent = htmlContent + "<td class='text-center' title='" + StringUtil.escapeStr(row.tgqx == null ? "": row.tgqx.substring(0, 10)) + "'>" + StringUtil.escapeStr(row.tgqx == null ? ""
				: row.tgqx.substring(0, 10)) + "</td>";
		if (row.isjs != 1) {
			htmlContent += "<td class='text-center'>"+ formatUndefine(row.syts) + "</td>";
		} else {
			htmlContent += "<td class='text-center'>-</td>";
		}
		htmlContent = htmlContent + "<td class='text-left' title='" + StringUtil.escapeStr(row.zhut) + "'>"+ StringUtil.escapeStr(row.zhut) + "</td>";
		htmlContent = htmlContent + "<td class='text-left' title='" + StringUtil.escapeStr(row.lysm) + "'>"+ StringUtil.escapeStr(row.lysm) + "</td>";
		htmlContent = htmlContent + "<td class='text-left' >"+ getPgds(row.isList,row.id) + "</td>";
		htmlContent = htmlContent + "<td class='text-center' title='" + StringUtil.escapeStr(row.bfrq == null ? "": row.bfrq.substring(0, 10)) + "'>" + StringUtil.escapeStr(row.bfrq == null ? "" : row.bfrq.substring(0, 10)) + "</td>";
		htmlContent = htmlContent + "<td class='text-center' title='" + StringUtil.escapeStr(row.isjs == 1 ? '已阅': row.isjs == 0 ? '未阅' : '') + "'>" + StringUtil.escapeStr(row.isjs == 1 ? '已阅': row.isjs == 0 ? '未阅' : '') + "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(formatUndefine(row.zdr_user.displayName))+ "'>"+ StringUtil.escapeStr(formatUndefine(row.zdr_user.displayName))+ "</td>";
		htmlContent += "<td class='text-center'>"+ formatUndefine(row.zdsj) + "</td>";
		htmlContent = htmlContent + "<td class='text-left'>" + StringUtil.escapeStr(formatUndefine(row.jg_dprt.dprtname))+ "</td>";
		htmlContent = htmlContent + "</tr>";
	});
	$("#circulation_list").empty();
	$("#circulation_list").html(htmlContent);

}

// 拼接评估单
function getPgds(list,id) {
	var html = "";
	if (list != null) {
		for (var i = 0; i < list.length; i++) {
			if (i == 1) {
				html = html + "　<i class='icon-caret-down' id='" + id
						+ "icon' onclick=switchPgd('" + id
						+ "')></i><div id='" + id
						+ "' style='display:none'>";
			}
			if (i == 0) {
				html += "<a href='javascript:void(0);' title='"
						+ StringUtil.escapeStr(list[i].pgdh)
						+ "' onclick=\"toViewPgd('" + list[i].pgdid
						+ "')\" >" + (StringUtil.escapeStr(list[i].pgdh))+" R"+list[i].bb
						+ "</a>";
			}
			if (i != 0) {
				html += "<a href='javascript:void(0);' title='"
						+ StringUtil.escapeStr(list[i].pgdh)
						+ "' onclick=\"toViewPgd('" + list[i].pgdid
						+ "')\" >" + (StringUtil.escapeStr(list[i].pgdh))+" R"+list[i].bb
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
	TableUtil.resetTableSorting("quality_check_list_table");
	goPage(1, "auto", "desc");
}
// 查看跳转
function viewBulletin(ywlx,id,zt) {
	 if("1"==ywlx){
		 window.open(basePath + "/project2/bulletin/view?id=" + id + "&zt=" + zt); 
	 }
	 if("6"==ywlx){//查看eo
		 window.open(basePath+"/project2/eo/view?id="+id);
	 }
}
// 搜索条件显示与隐藏
function search() {
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		$("#icon").removeClass("fa-chevron-down");
		$("#icon").addClass("fa-chevron-up");
	} else {

		$("#divSearch").css("display", "none");
		$("#icon").removeClass("fa-chevron-up");
		$("#icon").addClass("fa-chevron-down");
	}
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

function searchreset() {
	var zzjgid = $('#zzjgid').val();
	$("#zzjg").val(zzjgid);
	var cy = document.getElementsByName('cy');
	for (var i = 0; i < cy.length; i++) {
		cy[i].checked = 'checked';
	}
	$("#tgqx_search").val("");
	$("#keyword_search").val("");
	initMultiselect();
	initLxMultiselect();
}

//获取预警颜色
function getWarningColor(bgcolor, syts, zt, isjs,tgqx, yjtsJb1, yjtsJb2) {
	if (isjs == 1) {
		return "<td  class='text-center '></td>";
	}
	if(tgqx==null||tgqx==''){
		return "<td  class='text-center '></td>";
	}
	if (yjtsJb1 < Number(syts) && Number(syts) <= yjtsJb2) {
		bgcolor = warningColor.level2;// 黄色		 
		return "<td  class='text-center ' title='"+yjtsJb1+"&lt;剩余通告期限&lt;="+yjtsJb2+"天"+"'>"+ "<i class='iconnew-lightbulbnew' style='font-size:16px;color:" + bgcolor+ "' ></i>" + "</td>";
	}
	if (Number(syts) <= yjtsJb1) {
		bgcolor = warningColor.level1;// 红色
		return "<td  class='text-center ' title='剩余通告期限&lt;="+yjtsJb1+"天"+"' >"+ "<i class='iconnew-lightbulbnew' style='font-size:16px;color:" + bgcolor+ "' ></i>" + "</td>";
	}
	return "<td  class='text-center '></td>";
}

//初始化机型多选下拉框
function initMultiselect() {
	initPlaneList();
	// 生成多选下拉框(机型)
	$("#jxdiv").empty();
	$("#jxdiv").html("<select multiple='multiple'  id='jx'></select>");
	$("#jx").append(planeModelOption);
	planeModelOption = '';
	$("#jx").multiselect({
		buttonClass : 'btn btn-default multiplewidth',
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

$("#zzjg").change(function() {
	initMultiselect();
})

function toViewPgd(id){
	window.open(basePath+"/project2/assessment/view?id="+id);
}

//初始化业务类型
function initLxMultiselect(){
	$("#lxdiv").empty();
	$("#lxdiv").html("<select multiple='multiple'  id='lx'></select>");
	var items=DicAndEnumUtil.getEnum("projectBusinessEnum");
	var options="";
	for(var i=0;i<items.length;i++){
		if(items[i].id=='1'||items[i].id=='6'){//获取维护提示和工程指令类型
			options+="<option value='"
				+ StringUtil.escapeStr(items[i].id) + "'>"
				+ StringUtil.escapeStr(items[i].name) + "</option>";
		}
	
	}
	$("#lx").append(options);
	$("#lx").multiselect({
		buttonClass : 'btn btn-default multiplewidth',
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