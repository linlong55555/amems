$(function() {
	// 开始的加载默认的内容
//	goPage_tableView(1, "auto", "desc");
	// 回车搜索
	document.onkeydown = function(event) {
		e = event ? event : (window.event ? window.event : null);
		if (e.keyCode == 13) {
			if ($("#keyword_search_tableView").is(":focus")) {
				searchTableView();
			}
		}
	}
	
	/**
	 * 维护子部件关联 添加行选中事件
	 */
	$("#loadingList tr").live("click", function(event) {
		// 避免复选框重复选择
		if ($(event.target).attr("type") == "checkbox") {
			return;
		}
		var checkbox = $(this).find(":checkbox");
		if (checkbox.attr("checked") == "checked") {
			checkbox.removeAttr("checked");
		} else {
			checkbox.attr("checked", "checked");
		}
	});

	$('input.date-range-picker').daterangepicker().prev().on("click", function() {
		$(this).next().focus();
	});
	
	/**
	 * 自动带入航次主数据的数据
	 */
	$("#jh_zs").blur(function(){
		AjaxUtil.ajax({
			   url:basePath+"/material/material/selectByBjhAndDprt",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify({
				   bjh : $.trim($("#jh_zs").val()),
				   dprtcode : $.trim($("#dprtcode_head").val())
			   }),
			   success:function(data){
				   if(data){
					   $("#zwms_zs").val(data.zwms);
					   $("#ywms_zs").val(data.ywms);
					   $("#cjjh_zs").val(data.cjjh);
					   $("#zjh_zs").val(data.zjh);
					   if(data.fixChapter){
						   $("#zjh_show_zs").val($.trim((data.fixChapter.zjh||"")+"  "+ (data.fixChapter.zwms||"")));
					   }
				   }
		      }
	    }); 
	});
});

var llklxMap = {
	1 : "无履历卡",
	2 : "原装履历卡",
	3 : "自制履历卡"
};

var isDjMap = {
	1 : "是",
	0 : "否"
};

var kzlxMap = {
	1 : "时控件",
	2 : "时寿件",
	3 : "非控制件"
};

/**
 * 带条件搜索的翻页
 */
function AjaxGetDatasWithSearch_tableView(pageNumber, sortType, sequence) {
	var searchParam = gatherSearchParam_tableView();
	searchParam.pagination = {
		page : pageNumber,
		sort : sortType,
		order : sequence,
		rows : 20
	};
	// obj.id = '1'';//搜索条件
	startWait();
	AjaxUtil.ajax({
		url : basePath + "/productionplan/loadingList/queryEditableTable",
		type : "post",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : JSON.stringify(searchParam),
		success : function(data) {
			finishWait();
			if (data.total > 0) {
				appendContentHtml_tableView(data.rows, searchParam.keyword);
				new Pagination({
					renderTo : "mpagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage:function(pageNumber,sortType,sequence){
						AjaxGetDatasWithSearch_tableView(pageNumber,sortType,sequence);
					}
				}); 
				removeNullOrUndefined();
				// 标记关键字
				signByKeyword($("#keyword_search_tableView").val(), [ 4, 5, 6,
						7, 8, 9, 10, 18, 20, 22, 27 ]);
			} else {
				$("#planelist").empty();
				$("#mpagination").empty();
				$("#planelist")
						.append("<tr><td colspan=\"27\">暂无数据 No data.</td></tr>");
			}
			new Sticky({tableId:'planelist_table'});
		}
	});

}

/**
 * 封装搜索条件
 * 
 * @returns
 */
function gatherSearchParam_tableView() {
	var searchParam = {};
	searchParam.keyword = $.trim($("#keyword_search_tableView").val());
	searchParam.fjzch = $.trim($("#fjzch_search").val());
	
	var history = $("#zt_search").val();
	var zt = $("#zt_tableView").val();
	if(history == 1){	// 当前
		if(zt == '' || zt == 1){
			searchParam.zt = 1;
		}else{
			searchParam.zt = 0;
		}
	}else{	// 历史
		searchParam.zt = zt;
	}
	searchParam.wz = $.trim($("#wz_tableView").val());
	var scrq = $.trim($("#scrq_tableView").val());
	if (scrq != '') {
		searchParam.beginScrq = scrq.substring(0, 10);
		searchParam.endScrq = scrq.substring(13, 23);
	}
	var azrq = $.trim($("#azrq_tableView").val());
	if (azrq != '') {
		searchParam.beginAzrq = azrq.substring(0, 10);
		searchParam.endAzrq = azrq.substring(13, 23);
	}
	var scrq = $.trim($("#scrq_tableView").val());
	if (scrq != '') {
		searchParam.beginScrq = scrq.substring(0, 10);
		searchParam.endScrq = scrq.substring(13, 23);
	}
	var ccrq = $.trim($("#ccrq_tableView").val());
	if (ccrq != '') {
		searchParam.beginCcrq = ccrq.substring(0, 10);
		searchParam.endCcrq = ccrq.substring(13, 23);
	}
	searchParam.azjldh = $.trim($("#azjldh_tableView").val());
	searchParam.ccjldh = $.trim($("#ccjldh_tableView").val());
	searchParam.llklx = $.trim($("#llklx_tableView").val());
	searchParam.llkbh = $.trim($("#llkbh_tableView").val());
	searchParam.bz = $.trim($("#bz_tableView").val());
	searchParam.kzlx = $.trim($("#kzlx_tableView").val());
	searchParam.isDj = $.trim($("#isDj_tableView").val());
	searchParam.dprtcode = $.trim($("#dprtcode_head").val());
	return searchParam;
}

/**
 * 拼接table数据
 * 
 * @param list
 */
function appendContentHtml_tableView(list, keyword) {
	var htmlContent = '';
	$
			.each(
					list,
					function(index, row) {
						var componentHistoryContent = (row.cj == 0 || row.isSync == 0? StringUtil.escapeStr(row.jh) : 
										("<a href='javascript:void(0);' " +
										"onclick='goToComponentHistoryPage(this)' " +
										"jh='"+StringUtil.escapeStr(row.jh)+"' " +
										"xlh='"+StringUtil.escapeStr(row.xlh)+"' " +
										"dprtcode='"+StringUtil.escapeStr(row.dprtcode)+"'>"+StringUtil.escapeStr(row.jh)+"</a>"));
						htmlContent += [
								"<tr bgcolor='"
										+ (row.cj == 0 ? "#ececec" : (
												row.cj == 1 ? "#f6f6f6" : "#fefefe")) + "'>",
								"<td class='fixed-column'>",
								(row.zt == 1 ? "<i class='icon-edit color-blue cursor-pointer' onClick='modify_tableView(\""
										+ row.id
										+ "\")' title='修改 Edit'></i>&nbsp;&nbsp;"
										: ""),
								(row.zt == 1 ? "<i class='icon-remove color-blue cursor-pointer' onClick='alertScrapWarnMessage(\""
										+ row.id
										+ "\",\""
										+ row.cj + "\")' title='作废 Scrap'></i>"
										: ""),
								"</td>",
								"<td>"
										+ DicAndEnumUtil.getEnumName(
												'componentOperationEnum',
												row.zt) + "</td>",
								"<td>" + (row.cj <= 1 ? "*" : "") + "</td>",
								"<td style='text-align: left' title='"+(StringUtil.escapeStr((row.zjh || "") + (row.zjh?"  ":"") + StringUtil.escapeStr(row.zjhms || "")))+"'>"
										+ StringUtil.escapeStr((row.zjh || "") + "  " + StringUtil.escapeStr(row.zjhms || "")) + "</td>",
								"<td style='text-align: left' title='"+StringUtil.escapeStr(row.ywmc)+"'>" + StringUtil.escapeStr(row.ywmc)
										+ "</td>",
								"<td style='text-align: left' title='"+StringUtil.escapeStr(row.zwmc)+"'>" + StringUtil.escapeStr(row.zwmc) + "</td>",
								"<td style='text-align: left' title='"+StringUtil.escapeStr(row.jh)+"'>"+componentHistoryContent+"</td>",
								"<td style='text-align: left' title='"+StringUtil.escapeStr(row.xlh)+"'>" + StringUtil.escapeStr(row.xlh)
										+ "</td>",
								"<td style='text-align: left'>" + StringUtil.escapeStr(row.nbsbm) + "</td>",
								"<td style='text-align: left'>" + StringUtil.escapeStr(row.cjjh) + "</td>",
								"<td style='text-align: left'>" + StringUtil.escapeStr(row.pch) + "</td>",
								"<td>" + StringUtil.escapeStr(row.zjsl) + "</td>",
								"<td style='text-align: left'>"
										+ DicAndEnumUtil.getEnumName(
												'planeComponentPositionEnum',
												row.wz) + "</td>",
								"<td>" + (row.scrq || "").substr(0, 10)
										+ "</td>",
								"<td style='text-align: left' title='"+StringUtil.escapeStr(row.xkzh)+"'>" + StringUtil.escapeStr(row.xkzh) + "</td>",
								"<td style='text-align: left' title='"+StringUtil.escapeStr(row.shzh)+"'>" + StringUtil.escapeStr(row.shzh) + "</td>",
								"<td>" + (row.azrq || "").substr(0, 10)
										+ "</td>",
								"<td style='text-align: left'>" + StringUtil.escapeStr(row.azjldh) + "</td>",
								"<td>" + (row.ccrq || "").substr(0, 10)
										+ "</td>",
								"<td style='text-align: left'>" + StringUtil.escapeStr(row.ccjldh) + "</td>",
								"<td style='text-align: left'>" + llklxMap[row.llklx] + "</td>",
								"<td style='text-align: left' title='"+StringUtil.escapeStr(row.llkbh)+"'>" + StringUtil.escapeStr(row.llkbh) + "</td>",
								"<td style='text-align: left'>" + kzlxMap[row.kzlx] + "</td>",
								"<td>" + (row.timeMonitorFlag != 1 ? 
										(row.timeMonitorFlag == 2 ? "<i class='icon-ok color-blue'></i>" : "<i class='icon-remove color-red'></i>") : "-") + "</td>",
								"<td>" + isDjMap[row.isDj] + "</td>",
								"<td>" + (row.fixMonitorFlag != 1 ? 
										(row.fixMonitorFlag == 2 ? "<i class='icon-ok color-blue'></i>" : "<i class='icon-remove color-red'></i>") : "-") + "</td>",
								"<td style='text-align:left;' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>",
								"</tr>" ].join("");
					});
	$("#planelist").empty();
	$("#planelist").html(htmlContent);

}

function removeNullOrUndefined() {
	$("tbody tr td").each(function() {
		if ($(this).text() == "null" || $(this).text() == "undefined") {
			$(this).text("");
		}
	});
}

/**
 * 字段排序（id以_order结尾的数据）
 * 
 * @param obj
 */
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { // 重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	var orderType = "asc";
	if (orderStyle == "sorting_asc") {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
		orderType = "desc";
	} else {
		$("#" + obj + "_" + "order").addClass("sorting_asc");
		orderType = "asc";
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#mpagination li[class='active']").text();
	goPage_tableView(currentPage, "b." + obj, orderType);
}

/**
 * 跳转至指定页数
 * 
 * @param pageNumber
 *            当前页码
 * @param sortType
 *            排序字段
 * @param sequence
 *            排序规则
 */
function goPage_tableView(pageNumber, sortType, sequence) {
	AjaxGetDatasWithSearch_tableView(pageNumber, sortType, sequence);
}

/**
 * 表单视图搜索
 */
function searchTableView() {
	goPage_tableView(1, "auto", "desc");
}

// 搜索条件重置
function searchreset() {
	$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function() {
		$(this).val("");
	});

	$("#divSearch").find("select").each(function() {
		$(this).val("");
	});
	$("#keyword_search").val("");
	$("#keyword_search_tableView").val("");
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

/**
 * 表单视图-修改
 */
function modify_tableView(id) {
	validatePlaneZt();
	$("#current_id_modal").val(id);
	$("#mountComponentModal").modal("show");
	$('#mountComponentModal').on('shown.bs.modal', function (e) {
		$("#loadingList_tree_modal").height($("#componentForm_modal").height()-121);
	});
	resetMountComponentDataModal();
	initMountComponentValidateModal();
	var detail = fillMountComponentDataModal(id);
	refreshModalTree(detail);
}

function refreshModalTree(detail, expandAll){
	var treeObj = $.fn.zTree.getZTreeObj("loadingList_tree_modal");
	if(treeObj){
		treeObj.destroy();
	}
	AjaxUtil.ajax({
		   url:basePath+"/productionplan/loadingList/queryEditableParentTree", 
		   type: "post",
		   dataType:"json",
		   contentType:"application/json;charset=utf-8",
		   data:JSON.stringify({
			   fjzch:$.trim($("#fjzch_search").val()),
			   zt:1,
			   notCj:2,
			   notId:detail?detail.id:"",
			   keyword:$.trim($("#keyword_search_modal").val()),
			   dprtcode:$.trim($("#dprtcode_head").val()),
		   }),
		   success:function(list){
			   var setting = {
					   view: {
						   showIcon: false,
						   selectedMulti: false,
						   nameIsHTML: false
					   },
					   check: {enable: false,autoCheckTrigger: true}, 
					   data: {
						   key: {
								name: "displayName"
						   },
						   simpleData: {
							   enable: true,
							   idKey: "id",
							   pIdKey: "pId",
							   rootPId: null
						   }
					   },
					   callback: {
						   beforeClick: beforeClickModal
					   }
			   };
			   var treeObj = $.fn.zTree.init($("#loadingList_tree_modal"), setting, list);
			   var nodes = treeObj.transformToArray(treeObj.getNodes());
			   if(expandAll){
				   treeObj.expandAll(true); 
			   }else{
				   treeObj.expandNode(nodes[0]);
			   }
			   $.each(nodes, function(index, node){
				   node.title = StringUtil.escapeStr(node.name);  
				   treeObj.updateNode(node); 
			   });
			   // 模拟点击节点
			   if(detail){
				   var node = treeObj.getNodeByParam("id", detail.fjdid, null);
				   if(node){
					   treeObj.selectNode(node);
					   refreshStatusModal(node.cj);
				   }
			   }
	      }
	    }); 
}

/**
 * 导出Excel
 */
function outLodingListExcel() {
	var zt='';
	if ($("#zt_search").val() != "all") {
		zt = $.trim($("#zt_search").val());
	}
	var scrq = $.trim($("#scrq_tableView").val());
	var beginScrq='';
	var endScrq='';
	if (scrq != '') {
		beginScrq = scrq.substring(0, 10);
		endScrq = scrq.substring(13, 23);
	}
	var azrq = $.trim($("#azrq_tableView").val());
	var beginAzrq='';
	var endAzrq='';
	if (azrq != '') {
		beginAzrq = azrq.substring(0, 10);
		endAzrq = azrq.substring(13, 23);
	}
	var scrq = $.trim($("#scrq_tableView").val());
	if (scrq != '') {
		searchParam.beginScrq = scrq.substring(0, 10);
		searchParam.endScrq = scrq.substring(13, 23);
	}
	var ccrq = $.trim($("#ccrq_tableView").val());
	if (ccrq != '') {
		searchParam.beginCcrq = ccrq.substring(0, 10);
		searchParam.endCcrq = ccrq.substring(13, 23);
	}
	window.open(basePath+"/productionplan/loadingList/LoadingListExcel.xls?fjzch="
			+ encodeURIComponent($("#fjzch_search").val()) + "&zt=" + zt+"&beginScrq="+beginScrq
			+"&endScrq="+endScrq+"&beginAzrq="+beginAzrq+"&endAzrq="+endAzrq
			+ "&keyword=" + encodeURIComponent($.trim($("#keyword_search_tableView").val())) + "&wz="
			+ encodeURIComponent($("#wz_tableView").val()) 
			+ "&azjldh=" + encodeURIComponent($.trim($("#azjldh_tableView").val()))  + "&ccjldh="
			+ $.trim($("#ccjldh_tableView").val()) + "&ccrq="
			+ $("#ccrq_tableView").val() + "&llklx="
			+ $("#llklx_tableView").val() + "&llkbh="
			+ encodeURIComponent($.trim($("#llkbh_tableView").val())) + "&bz=" + encodeURIComponent($.trim($("#bz_tableView").val()))
			+ "&kzlx=" + encodeURIComponent($("#kzlx_tableView").val()) + "&isDj="
			+ $("#isDj_tableView").val()+"&dprtcode="+$("#dprtcode_head").val());
	
}

	/**
	 * 链接至部件履历页面
	 * @param btn
	 */
	function goToComponentHistoryPage(obj){
		var jh = encodeURIComponent($(obj).attr('jh'));
		var xlh = encodeURIComponent($(obj).attr('xlh'));
		var dprtcode = encodeURIComponent($(obj).attr('dprtcode'));
		window.open(basePath+"/flightdata/componenthistory/view?jh="+jh+'&xlh='+xlh+'&dprtcode='+dprtcode);
	}
	
	/**
	 * 刷新位置状态
	 * @param cj
	 */
	function refreshStatusModal(cj){
		if(cj == 0){
			$("#wz_zs").removeAttr("disabled");
		}else{
			$("#wz_zs").attr("disabled","disabled");
		}
	}
	
	/**
	 * 初始化验证
	 */
	function initMountComponentValidateModal(){
		if($("#componentForm_modal").data('bootstrapValidator')){
			$("#componentForm_modal").data('bootstrapValidator').destroy();
			$('#componentForm_modal').data('bootstrapValidator', null);
		}
		 var validatorForm =  $('#componentForm_modal').bootstrapValidator({
			 	excluded: [':disabled'],
		        message: '数据不合法',
		        feedbackIcons: {
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	jh_zs: {
		                validators: {
		                	regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    },
		                    notEmpty: {
		                        message: '件号不能为空'
		                    }
		                }
		            },
		            zjh_zs: {
		                validators: {
		                    notEmpty: {
		                        message: 'ATA章节号不能为空'
		                    }
		                }
		            },
		            cjjh_zs: {
		                validators: {
		                	regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            nbsbm_zs: {
		                validators: {
		                	regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            zjsl_zs: {
		            	validators: {
		                	regexp: {
		                        regexp: /^([1-9]|([1-9]\d+))$/,
		                        message: '只能输入大于1的正整数'
		                    },
		                    callback: {
		                        message: '装机数量不可大于库存数量',
		                        callback: function(value, validator) {
		                			var max = $("#zjsl_max").val();
		                			if(max && value && parseInt(value) > parseInt(max)){
		                				return false;
		                			}
		                            return true;
		                        }
		                    }
		                }
		            },
		            xlh_zs: {
		                validators: {
		                	regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    },
		                	callback: {
		                        message: '时控件和定检件的序列号为必输项',
		                        callback: function(value, validator) {
		                        	var kzlx = $("#kzlx_zs").val();
		                			var isDj = $("#isDj_zs").val();
		                			var xlh = $("#xlh_zs").val();
		                			if(!xlh && (kzlx == 1 || kzlx == 2 || isDj == 1)){
		                				return false;
		                			}
		                            return true;
		                        }
		                    }
		                }
		            },
		            xkzh_zs: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },	
		            shzh_zs: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },	
		            pch_zs: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            llkbh_zs: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            azjldh_zs: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            ccjldh_zs: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            parent_zs: {
		                validators: {
		                    notEmpty: {
		                        message: '父节点不能为空'
		                    }
		                }
		            },
		        }
		    });
	}
	
	//打开ATA章节号对话框
	function openChapterWinModal(){
		var zjh = $.trim($("#zjh_zs").val());
		FixChapterModal.show({
			selected:zjh,//原值，在弹窗中默认勾选
			callback: function(data){//回调函数
				var wczjh = '';
				var wczjhName = '';
				
					wczjh = StringUtil.escapeStr(data.zjh);
					wczjhName = StringUtil.escapeStr(data.zwms);
					wczjhName = wczjh + " " + wczjhName;
				
				$("#zjh_zs").val(wczjh);
				$("#zjh_show_zs").val(wczjhName);
				$("#componentForm_modal").data('bootstrapValidator').resetForm(false); 
			}
		})
	}
	
	/**
	 * 重置装机清单详细信息
	 */
	function resetMountComponentDataModal(){
		$("#componentForm_modal :input").not(":button, :submit, :reset, :checkbox, :radio, select").val("");
		$("#llklx_zs").val("1");
		$("#kzlx_zs").val("1");
		$("#isDj_zs").val("1");
		$("#zjsl_zs").val("1");
		$("#zjsl_max").val("");
		$("#fjzch_zs").val($("#fjzch_search").val());
    	$("#fjjx_zs").val($("#fjzch_search option:selected").attr("fjjx"));
    	$("#jh_zs").removeAttr("disabled");
		$("#xlh_zs").removeAttr("disabled");
		$("#zjsl_zs").removeAttr("disabled");
		
		$("#componentForm_modal input").removeAttr("disabled");
		$("#componentForm_modal textarea").removeAttr("disabled");
		$("#componentForm_modal select").removeAttr("disabled");
		$("#main_parent_btn_zs").removeAttr("disabled");
		
		$('#fjjx_zs').attr("disabled","disabled");
		$('#fjzch_zs').attr("disabled","disabled");
		$('#zjh_show_zs').attr("disabled","disabled");
		$('#parent_show_zs').attr("disabled","disabled");
		$("#main_insertOrUpdate_btn_zs").show();
		$("#main_chapter_btn_zs").removeAttr("disabled");
	}
	
	/**
	 * 回填装上件信息
	 */
	function fillMountComponentDataModal(id){
		var detail;
		if(id){
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/productionplan/loadingList/loadEditableDetail",
				type:"post",
				data:JSON.stringify({
					id : id
				}),
				dataType:"json",
				contentType: "application/json;charset=utf-8",
				success:function(data){
					detail = data;
					$("#current_id_modal").val(data.id);
					$("#fjzch_zs").val(data.fjzch);
		         	$("#fjjx_zs").val($("#fjzch_search option:selected").attr("fjjx"));
		         	$("#jh_zs").val(data.jh);
		         	$("#xlh_zs").val(data.xlh);
		         	$("#pch_zs").val(data.pch);
		         	$("#zjh_zs").val(data.zjh);
		         	$("#zjh_show_zs").val($.trim((data.zjh || "") + "  "+ (data.zjhms || "")));
		         	$("#cjjh_zs").val(data.cjjh);
		         	$("#zwms_zs").val(data.zwmc);
		         	$("#ywms_zs").val(data.ywmc);
		         	$("#bjgzjl_zs").val(data.bjgzjl);
		         	$("#zjsl_zs").val(data.zjsl);
		     		$("#zjsl_max").val(data.zjslMax);
		         	$("#scrq_zs").val((data.scrq||"").substr(0, 10));
		         	$("#xkzh_zs").val(data.xkzh);
		         	$("#shzh_zs").val(data.shzh);
		         	$("#llkbh_zs").val(data.llkbh);
					$("#bz_zs").val(data.bz);
					$("#azrq_zs").val((data.azrq||"").substr(0, 10));
		         	$("#azjldh_zs").val(data.azjldh);
		         	$("#ccrq_zs").val((data.ccrq||"").substr(0, 10));
		         	$("#ccjldh_zs").val(data.ccjldh);
		         	$("#nbsbm_zs").val(data.nbsbm);
		         	$("#bz_zs").val(data.bz);
		         	$("#wz_zs").val(data.wz);
		         	$("#parent_zs").val(data.fjdid);
		         	if(data.parent){
		         		$("#parent_show_zs").val(data.parent.displayName);
		         		$("#parent_show_zs").attr("title", data.parent.displayName);
		         	}
		         	
		         	$("#llklx_zs").val(data.llklx||"1");
		     		$("#kzlx_zs").val(data.kzlx||"1");
		     		$("#isDj_zs").val(data.isDj != 0 ? (data.isDj||"1") : "0");
		     		
		     		$("#scrq_zs").datepicker("update");
		     		$("#azrq_zs").datepicker("update");
		     		$("#ccrq_zs").datepicker("update");
		     		
		    		if(data.jh && data.xlh && data.isSync == 1){
		    			$("#jh_zs").attr("disabled","disabled");
		    			$("#xlh_zs").attr("disabled","disabled");
		    		}
		    		
		    		if(data.cj==0){
		    			$("#componentForm_modal input").attr("disabled","disabled");
		    			$("#componentForm_modal textarea").attr("disabled","disabled");
		    			$("#componentForm_modal select").attr("disabled","disabled");
		    			$("#main_insertOrUpdate_btn_zs").hide();
		    			$("#main_chapter_btn_zs").attr("disabled","disabled");
		    			$("#main_parent_btn_zs").attr("disabled","disabled");
		    		}
		    		
		    		limitCount();
				}
			});
		}
		return detail;
	}
	
	/**
	 * 新增或更新飞机部件信息
	 */
	function insertOrUpdateModal(){
		var msg = validatePlaneZt();
		if(msg){
			AlertUtil.showErrorMessage(msg);
			return false;
		}
		$('#componentForm_modal').data('bootstrapValidator').validate();
		  if(!$('#componentForm_modal').data('bootstrapValidator').isValid()){
			return false;
		  }
		var treeObj = $.fn.zTree.getZTreeObj("loadingList_tree_modal");
		var node = treeObj.getSelectedNodes()[0];
		
		if(!node){
			AlertUtil.showErrorMessage("请选择父节点！");
			return false;
		}
		
		var param = {
			id : $("#current_id_modal").val(),
			cj : parseInt(node.cj)+1,
			fjdid : node.id,
			jh : $("#jh_zs").val(),
			fjzch : $("#fjzch_zs").val(),
			xlh : $("#xlh_zs").val(),
			zjh : $("#zjh_zs").val(),
			cjjh : $("#cjjh_zs").val(),
			zwmc : $("#zwms_zs").val(),
			ywmc : $("#ywms_zs").val(),
			nbsbm : $("#nbsbm_zs").val(),
			shzh : $("#shzh_zs").val(),
			xkzh : $("#xkzh_zs").val(),
			pch : $("#pch_zs").val(),
			bjgzjl : $("#bjgzjl_zs").val(),
			zjsl : $("#zjsl_zs").val(),
			wz : $("#wz_zs").val(),
			scrq : $("#scrq_zs").val(),
			bz : $("#bz_zs").val(),
			azrq : $("#azrq_zs").val(),
			azjldh : $("#azjldh_zs").val(),
			ccrq : $("#ccrq_zs").val(),
			ccjldh : $("#ccjldh_zs").val(),
			llkbh : $("#llkbh_zs").val(),
			bjlx : 1,
			zt : 1,
			llklx : $("#llklx_zs").val(),
			kzlx : $("#kzlx_zs").val(),
			isDj : $("#isDj_zs").val(),
			dprtcode : $("#dprtcode_head").val()
		};
		if(param.xlh != "" && $("#warn_ensure_hid").val() != param.xlh){
			var returnFlag = false;
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/productionplan/loadingList/judgeXlhExist",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				contentType: "application/json;charset=utf-8",
				success:function(data){
					if(data.isExist){
						var ll = data.loadingList;
						if(ll.id != param.id && ll.zt != 1){
							returnFlag = true;
							$("#xlhExistModalBody").html("已存在件号为："+ll.jh+"，序列号为："+ll.xlh+"，状态为"+ztMap[ll.zt]+"的部件，是否确认操作？");
							$("#xlhExistModal").modal("show");
							$("#warn_ensure_hid").val(ll.xlh);
						}
					}
				}
			});
			if(returnFlag){
				return false;
			}
		}
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/productionplan/loadingList/insertOrUpdateEditable",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(data){
				searchTableView();
				AlertUtil.showMessage(($("#current_id_modal").val() ? "修改" : "新增") +"部件基本信息成功!");
				$("#warn_ensure_hid").val("");
				$("#mountComponentModal").modal("hide");
			}
		});
	}
	
	/**
	  * 限制装机数量
	  */
	 function limitCount(){
		 var xlh = $.trim($("#xlh_zs").val());
		 if(xlh){
			 $("#zjsl_zs").attr("disabled","disabled");
			 $("#zjsl_zs").val(1);
		 }else{
			 $("#zjsl_zs").removeAttr("disabled");
		 }
	 }
	 
	 /**
	  * 重新验证序列号
	  */
	 function reValidateXlhZs(){
		 $('#componentForm_modal').data('bootstrapValidator')  
         .updateStatus("xlh_zs", 'NOT_VALIDATED',null)  
         .validateField("xlh_zs");
	 }
	 
	 /**
	  * 弹出选择父节点modal
	  */
	 function openChooseParentModal(){
		 $("#chooseParentModal").modal("show");
	 }
	 
	 /**
	  * 选择父节点事件
	  * @param event
	  * @param treeId
	  * @param treeNode
	  */
	function chooseParentModal(){
		var treeObj = $.fn.zTree.getZTreeObj("loadingList_tree_modal");
		var treeNode = treeObj.getSelectedNodes()[0];
		$("#parent_zs").val(treeNode.id);
		$("#parent_show_zs").val(treeNode.displayName);
		$("#parent_show_zs").attr("title", treeNode.displayName);
		$("#componentForm_modal").data('bootstrapValidator').resetForm(false); 
		if(treeNode.cj != 0){
			$("#wz_zs").val(treeNode.wz);
		}
		refreshStatusModal(treeNode.cj);
	}
	
	function beforeClickModal(treeId, treeNode, clickFlag){
    	if(treeNode.id == '0' 
		 	|| treeNode.id == 'js'
	 		|| treeNode.id == 'zf'
 			|| treeNode.id == 'yf'
			|| treeNode.id == 'ssd'
			|| treeNode.id == 'wdg'
			|| treeNode.id == 'jc'){
    		return false;
    	}
    	return true;
	}
