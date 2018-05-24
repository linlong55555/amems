$(document).ready(
		function() {
			Navigation(menuCode, '查看部件履历', 'View');
			$('#dprtcode').val(formatUndefine(params.dprtcode));
			loadInfo(params);

			$('#loadForm').bootstrapValidator({
				message : '数据不合法',
				feedbackIcons : {
					// valid: 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {

					jh : {
						validators : {
							notEmpty : {
								message : '件号不能为空'
							}
						}
					},
					xlh : {
						validators : {
							notEmpty : {
								message : '序列号不能为空'
							}
						}
					}

				}
			});

			$('#loadbtn').on(
					'click',
					function() {
						$('#loadForm').data('bootstrapValidator').validate();
						if (!$('#loadForm').data('bootstrapValidator')
								.isValid()) {
							return false;
						}

						var jh = $('#jh').val();
						var xlh = $('#xlh').val();
						var dprtcode = $('#dprtcode').val();
						window.location.href = basePath
								+ "/flightdata/componenthistory/view?jh=" + encodeURIComponent(jh)
								+ '&xlh=' + encodeURIComponent(xlh)+"&dprtcode="+encodeURIComponent(dprtcode);

					})

			// 模态窗显示上机部件
			$('#viewParent').on('click', function() {

				var fjdxlh = $('#fjdxlh').val();
				var fjdjh = $.trim($('#fjdjh').val());
				var dprtcode = $('#dprtcode').val(); 
				var param = {
					xlh : fjdxlh,
					jh : fjdjh , 
					dprtcode:dprtcode
				};

				var cj = $('#cj').val();
				// 只显示二级部件或以下的上级部件
				if ($('#cj').val() != '' && cj > 1 && fjdjh.length>0) {
					$("#myModal").modal("show");
					loadRelInfo(param);
				}
			});

		});

/**
 * 查看子部件信息
 * 
 * @param jh
 * @param xlh
 */
function loadInfoBySub(obj) {
	 var jh = decodeURIComponent($(obj).attr('jh'));
	 var xlh = decodeURIComponent($(obj).attr('xlh'));
	 var dprtcode = decodeURIComponent($(obj).attr('dprtcode'));
	 
	var param = {
		xlh : xlh,
		jh : jh,
		dprtcode : params.dprtcode
	};
	$("#myModal").modal("show");
	loadRelInfo(param);
}

/**
 * 加载当前部件
 * 
 * @param loadParams
 * @param domOptions
 */
function loadInfo(loadParams) {
	 
	AjaxUtil.ajax({
		url : basePath + "/flightdata/componenthistory/viewData",
		type : "get",
		data : loadParams,
		async : false,
		cache:false,
		success : function(data) {
			var defaults = {
				FJZCH : '',
				ZWMS : '',
				YWMS : '',
				ZT : '',
				ZTTEXT : '',
				CJJH : ''

				,
				CJ : '',
				WZ : '',
				ZJH : '',
				FJDMC : '',
				FJDXLH : '',
				FJDJH : ''
			};

			if (data.partsInfo != null) {
				componentInfo = $.extend(defaults, data.partsInfo);
				$('#fjzch').val(componentInfo.FJZCH);
				$('#zwmc').val(componentInfo.ZWMS);
				$('#ywms').val(componentInfo.YWMS);
				$('#zt').val(componentInfo.ZT);

				$('#cjjh').val(componentInfo.CJJH);
				$('#cj').val(componentInfo.CJ);
				$('#bz').val(formatUndefine(componentInfo.BZ));
				if(componentInfo.WZ===''){
					$('#wz').val('');
				}
				else{
					$('#wz').val(DicAndEnumUtil.getEnumName('partsPositionEnum', formatUndefine(componentInfo.WZ)));
				}
				
				$('#zjh').val(componentInfo.ZJH);

				// 上级部件
				$('#sjbj').val(componentInfo.FJDMC);

				$('#fjdxlh').val(componentInfo.FJDXLH);
				$('#fjdjh').val(componentInfo.FJDJH);
 
				appendDisassemblyRecordsHtml(data.partsDisassemblyRecords,"#list");
				 
				if('未在机'!=data.partsInfo.ZT && data.subComponents != null && data.partsInfo.CJ <2){
					$("#currentbjDiv").show();
					appendSubComponentsHtml(data.subComponents, "#sublist");
				}
				else{
					/*$('.subBlock').hide();
					$("#sublist").parent().parent().remove();*/
					$("#currentbjDiv").hide();
					
				}
				
			} else {
				AlertUtil.showErrorMessage('找不到数据');
			}
		}
	});
}

/**
 * 加载关联部件
 * 
 * @param loadParams
 * @param domOptions
 */
function loadRelInfo(loadParams) {
	if (fjdxlh != '' && fjdjh != '') {
		AjaxUtil.ajax({
			url : basePath + "/flightdata/componenthistory/viewData",
			type : "get",
			data : loadParams,
			async : false,
			cache:false,
			success : function(data) {
				var defaults = {
					FJZCH : '',
					ZWMS : '',
					YWMS : '',
					ZT : '',
					ZTTEXT : '',
					CJJH : ''

					,
					CJ : '',
					WZ : '',
					ZJH : '',
					FJDMC : ''

				};

				if (data.partsInfo != null) {
					
					componentInfo = $.extend(defaults, data.partsInfo);
					$('#myModal #jh').val(loadParams.jh);
					$('#myModal #xlh').val(loadParams.xlh);

					$('#myModal #fjzch').val(componentInfo.FJZCH);
					$('#myModal #zwmc').val(componentInfo.ZWMS);
					$('#myModal #ywms').val(componentInfo.YWMS);
					$('#myModal #zt').val(componentInfo.ZT);

					$('#myModal #cjjh').val(componentInfo.CJJH);
					$('#myModal #cj').val(componentInfo.CJ);
					$('#myModal #wz').val(
							formatUndefine(DicAndEnumUtil.getEnumName(
									'partsPositionEnum', componentInfo.WZ)));
					$('#myModal #zjh').val(componentInfo.ZJH);

					// 上级部件
					$('#myModal #sjbj').val(componentInfo.FJDMC);

					appendDisassemblyRecordsHtml(data.partsDisassemblyRecords,
							"#myModal #list");
					
					if(null != data.partsInfo.CJ && data.partsInfo.CJ <2 ){
						$("#relationbj").show();
						appendSubComponentsHtml(data.subComponents,
						"#myModal #sublist");
					}
					else{
//						$('#myModal .subBlock').hide();
						$("#relationbj").hide();
						
					}
				} else {
					AlertUtil.showErrorMessage('找不到数据');
				}
			}
		});
	} else {
		AlertUtil.showErrorMessage('部件号，序列号不能为空');
	}

}

/**
 * 加载当前部件拆解记录列表
 * 
 * @param list
 */
function appendDisassemblyRecordsHtml(list, domStr) {

	var htmlContent = '';
	$.each(list, function(index, row) {
		if (index % 2 == 0) {
			htmlContent = htmlContent + "<tr   bgcolor=\"#f9f9f9\">";
		} else {
			htmlContent = htmlContent + "<tr   bgcolor=\"#fefefe\">";
		}

		var defaults = {
			FJZCH : ''

			,
			AZ_ZXRQ : '',
			AZ_JLD : '',
			AZ_GZZXM : ''

			,
			GDSX : '',
			GDSX_DW : '',
			BJYY : '',
			BJYY_DW : ''

			,
			CJ_ZXRQ : '',
			CJ_JLD : '',
			CJ_GZZXM : ''

			,
			CJ_JLD : '',
			CJ_JLD : ''

			,
			CJ_ZSJJH : '',
			CJ_ZSJXLH : '',
			ZJSY : ''

			,
			ZSSY : '',
			CXYY : '',
			CXSY : '' ,
			AZ_BZ:'',
			CJ_BZ:''
		};
		row = $.extend(defaults, row);

		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'>"
				+ formatUndefine(StringUtil.escapeStr(row.FJZCH) ) + "</td>";

		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-center'>"
				+ formatUndefine((row.AZ_ZXRQ||'').substring(0,10)) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'>"
				+ formatUndefine(row.AZ_JLD) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'>"
				+ formatUndefine(StringUtil.escapeStr(row.AZ_GZZXM)) + "</td>";

		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'>"
				+ convertLine(formatUndefine(row.GDSX)) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'>"
				+ convertLine(formatUndefine(row.BJYY)) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'>"
				+ convertLine(formatUndefine(row.ZSSY)) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title=\""+StringUtil.escapeStr(row.AZ_BZ)+"\">"
		+ StringUtil.escapeStr(StringUtil.subString(row.AZ_BZ,20)) + "</td>";
		

		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-center'>"
				+ formatUndefine((row.CJ_ZXRQ||'').substring(0,10)) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'>"
				+ formatUndefine(StringUtil.escapeStr(row.CJ_JLD) ) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'>"
				+ formatUndefine(StringUtil.escapeStr(row.CJ_GZZXM) ) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'>"
				+ convertLine(formatUndefine(row.CXYY)) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'>"
				+ convertLine(formatUndefine(row.CXSY)) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title=\""+StringUtil.escapeStr(row.CJ_BZ)+"\">"
		+ StringUtil.escapeStr(StringUtil.subString(row.CJ_BZ,20)) + "</td>";
		
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'>"
				+ formatUndefine(row.CJ_ZSJJH + ' ' + row.CJ_ZSJXLH) + "</td>";

		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'>"
				+ convertLine(formatUndefine(row.ZJSY)) + "</td>";
		htmlContent = htmlContent + "</tr>";

	});
	$(domStr).empty();
	$(domStr).html(htmlContent);

}

/**
 * 加载子部件列表
 * 
 * @param list
 */
function appendSubComponentsHtml(list, domStr) {
	var htmlContent = '';
	$.each(list, function(index, row) {
		if (index % 2 == 0) {
			htmlContent = htmlContent + "<tr   bgcolor=\"#f9f9f9\">";
		} else {
			htmlContent = htmlContent + "<tr   bgcolor=\"#fefefe\">";
		}

		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.JH) ) +"'>"
				+ formatUndefine(StringUtil.escapeStr(row.JH)) + "</td>";
		/*htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'  title='"+formatUndefine(StringUtil.escapeStr(row.XLH) ) +"'>"
		+ "<a href=\"javascript:void(0);\" onClick=\"loadInfoBySub('"
		+ StringUtil.escapeStr(row.JH) + "','" + StringUtil.escapeStr(row.XLH) + "')\">" + formatUndefine(StringUtil.escapeStr(row.XLH))
		+ "</a>" + "</td>";*/
		
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'  title='"+formatUndefine(StringUtil.escapeStr(row.XLH) ) +"'>"
		+ "<a href=\"javascript:void(0);\" onClick=\"loadInfoBySub(this)\" "
		 + " jh="+encodeURIComponent(row.JH)+""
		 + " xlh="+encodeURIComponent(row.XLH)+""
		 + " dprtcode="+encodeURIComponent(row.DPRTCODE)+""
		
		+">" 
		+ formatUndefine(StringUtil.escapeStr(row.XLH))
		+ "</a>" + "</td>";
		

		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'  title='"+formatUndefine(StringUtil.escapeStr(row.NBSBM) ) +"'>"
				+ formatUndefine(StringUtil.escapeStr(row.NBSBM)) + "</td>";
		
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'  title='"+formatUndefine(StringUtil.escapeStr(row.PCH) ) +"'>"
		+ formatUndefine(StringUtil.escapeStr(row.PCH)) + "</td>";

		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'  title='"+formatUndefine(StringUtil.escapeStr(row.ZWMC)) +"'>"
				+ formatUndefine(StringUtil.escapeStr(row.ZWMC) ) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'  title='"+formatUndefine(StringUtil.escapeStr(row.YWMC) ) +"'>"
				+ formatUndefine(StringUtil.escapeStr(row.YWMC)) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'  title='"+formatUndefine(StringUtil.escapeStr(row.ZTTEXT)) +"'>"
				+ formatUndefine(StringUtil.escapeStr(row.ZTTEXT)) + "</td>";

		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'  title='"+formatUndefine(StringUtil.escapeStr(row.CJJH) ) +"'>"
				+ formatUndefine(StringUtil.escapeStr(row.CJJH)) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'  title='"+formatUndefine(StringUtil.escapeStr(row.ZJH)) +"'>"
				+ formatUndefine(StringUtil.escapeStr(row.ZJH) ) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-right' title='"+formatUndefine(row.CJ) +"'>"
				+ formatUndefine(row.CJ) + "</td>";
		htmlContent = htmlContent
				+ "<td style=\"vertical-align: middle;\" class='text-left' title='"+formatUndefine(DicAndEnumUtil.getEnumName(
						'partsPositionEnum', row.WZ)) +"'>"
				+ formatUndefine(DicAndEnumUtil.getEnumName(
						'partsPositionEnum', row.WZ)) + "</td>";

		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-right' title='"+formatUndefine(row.ZJSL) +"'>"
				+ formatUndefine(row.ZJSL) + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-center' title='"+formatUndefine((row.AZRQ||'').substring(0,10)) +"'>"
				+ formatUndefine((row.AZRQ||'').substring(0,10))  + "</td>";
		htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.BZ) ) +"'>"
				+ formatUndefine(StringUtil.escapeStr(row.BZ) ) + "</td>";

		htmlContent = htmlContent + "</tr>";

	});
	$(domStr).empty();
	$(domStr).html(htmlContent);

}

// 查看revision对应的工卡列表
function goToLinkPage(obj, rid) {
	obj.stopPropagation(); // 屏蔽父元素的click事件
	window.location = basePath + "/main/work/listpage?rid=" + rid;
}

// 字段排序
function orderBy(sortField) {
	var sortColum = $("#" + sortField + "_order");
	var orderStyle = sortColum.attr("class");
	$("th[id$=_order]").each(function() { // 重置class
		$(this).removeClass().addClass("sorting");
	});
	sortColum.removeClass("sorting");
	if (orderStyle == "sorting_asc") {
		sortColum.addClass("sorting_desc");
	} else {
		sortColum.addClass("sorting_asc");
	}
	orderStyle = $("#" + sortField + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	currentPage = currentPage == '' ? 1 : currentPage;
	// sortField = 'b1.jh'
	goPage(currentPage, orderStyle.split("_")[1], sortField);
}

// 搜索条件重置
function searchreset() {
	$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("select").each(function() {
		$(this).val("");
	})

	$("#keyword").val("")
}

function searchRevision() {
	goPage(1, "", "");
}

// 回车事件控制
/*
 * document.onkeydown = function(event){ e = event ? event :(window.event ?
 * window.event : null); if(e.keyCode==13){ if($("#workOrderNum").is(":focus")){
 * $("#homeSearchWorkOrder").click(); } } }
 */

function more() {
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

function convertLine(str) {
	var result = '';
	var arr = str.split(',');
	$.each(arr, function() {
		result += this + '<br>';
	})
	return result;
}

function componenthistoryUploadOutExcel() {
	var jh = $("#jh").val();
	var xlh = $("#xlh").val();
	var fjzch = $("#fjzch").val();
	var dprtcode = $("#dprtcode").val();
	window.open(basePath+"/flightdata/componenthistory/componenthistoryOutExcel?jh=" + encodeURIComponent(jh) + "&xlh=" + encodeURIComponent(xlh) + "&fjzch="
			+ encodeURIComponent(fjzch) + "&dprtcode=" + dprtcode);
}