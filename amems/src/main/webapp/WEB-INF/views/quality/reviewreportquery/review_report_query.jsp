<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>质量审核报告</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>

<script type="text/javascript">
	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					loadMainData();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<div class="page-content">
		<div class="panel panel-primary" id="panel">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				<div class='searchContent row-height margin-top-0'>
					<div
						class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
						<a href="javascript:void(0);" onclick="openWinView()"
							class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission"
							permissioncode="quality:qualityreviewreport:main:07">
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</a>
					</div>
					
					<!--
					
					<div
						class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
						<div class='pull-right'>
							<label class='padding-left-5'
								style='margin-top: 6px; font-weight: normal;'> <input
								type='checkbox' onchange="loadMainData();" checked="checked"
								name='type_radio' value='0' style='vertical-align: text-bottom;' />&nbsp;保存&nbsp;&nbsp;
							</label> <label class='padding-left-5'
								style='margin-top: 6px; font-weight: normal;'> <input
								type='checkbox' onchange="loadMainData();" checked="checked"
								name='type_radio' value='1' style='vertical-align: text-bottom;' />&nbsp;提交&nbsp;&nbsp;
							</label> <label class='padding-left-5'
								style='margin-top: 6px; font-weight: normal;'> <input
								type='checkbox' onchange="loadMainData();" checked="checked"
								name='type_radio' value='5&6'
								style='vertical-align: text-bottom;' />&nbsp;驳回&nbsp;&nbsp;
							</label> <label class='padding-left-5'
								style='margin-top: 6px; font-weight: normal;'> <input
								type='checkbox' onchange="loadMainData();" checked="checked"
								name='type_radio' value='2' style='vertical-align: text-bottom;' />&nbsp;已审核&nbsp;&nbsp;
							</label> <label class='padding-left-5'
								style='margin-top: 6px; font-weight: normal;'> <input
								type='checkbox' onchange="loadMainData();" checked="checked"
								name='type_radio' value='3' style='vertical-align: text-bottom;' />&nbsp;已审批&nbsp;&nbsp;
							</label>
						</div>
					</div>
					
					
					  -->
					  
					 <div
							class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
							<span
								class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">审核日期</div>
								<div class="font-size-9 ">Date</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<input class='form-control' id="shrq_search"
									name='date-range-picker' />
							</div>
						</div>
					
					<div
						class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
						<span
							class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">审核对象</div>
							<div class="font-size-9 ">Object</div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<div class='input-group' style='width: 100%'>
								<input type="hidden" id="quality_audit_module_search_shdxid">
								<input type="hidden" id="quality_audit_module_search_shdxbh">
								<input type="hidden" id="quality_audit_module_search_shdxmc">
								<input type="text" id="quality_audit_module_search_shdx"
									class="form-control" maxlength="20" /> <span
									class="input-group-btn">
									<button type="button" class="btn btn-default"
										data-toggle="modal" onclick="openzrdw('shdx')">
										<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
					<div
						class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right"
						style='padding-left: 15px;'>

						<!-- 搜索框start -->
						<div class="col-xs-12 input-group input-group-search">

							<input type="text" id="keyword_search" class="form-control "
								placeholder='审核报告编号/审核概述'>
							<div class="input-group-addon btn-searchnew">
								<button type="button"
									class=" btn btn-primary padding-top-1 padding-bottom-1 "
									onclick="loadMainData();">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
								</button>
							</div>
							<div class="input-group-addon btn-searchnew-more">
								<button type="button"
									class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"
									id="btn" onclick="getMore();">
									<div class='input-group'>
										<div class="input-group-addon">
											<div class="font-size-12">更多</div>
											<div class="font-size-9 margin-top-5">More</div>
										</div>
										<div class="input-group-addon">
											&nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
										</div>
									</div>
								</button>
							</div>
						</div>
						<!-- 搜索框end -->

					</div>
					<div class='clearfix'></div>
					<!-- 更多的搜索框 -->
					<div
						class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-bottom-10 display-none border-cccccc"
						id="divSearch">

						<div
							class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span
								class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">类型</div>
								<div class="font-size-9 ">Type</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select class='form-control' id="lx_search"
									onchange="loadMainData();">
									<option value="">全部</option>
									<option value="1">内部</option>
									<option value="2">外部</option>
								</select>
							</div>
						</div>


						<div
							class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span
								class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">审核类别</div>
								<div class="font-size-9 ">Category</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select class='form-control' id="shlb_search"
									onchange="loadMainData();">
									<option value="">全部</option>
									<option value="10">计划审核</option>
									<option value="20">非计划审核</option>
								</select>
							</div>
						</div>
						<div
							class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span
								class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="dprtcode" class="form-control " name="dprtcode"
									onchange="loadMainData();">
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}"
											<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="col-lg-2  text-right padding-right-0 pull-right"
							style="margin-bottom: 10px;">
							<button type="button"
								class="btn btn-primary padding-top-1 padding-bottom-1"
								onclick="resert();">
								<div class="font-size-12">重置</div>
								<div class="font-size-9">Reset</div>
							</button>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
				<div id="audit_report_main_table_top_div"
					class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-h table-set"
					style="overflow-x: auto;">
					<table id="work_card_main_table"
						class="table table-thin table-bordered table-striped table-hover table-set">
						<thead>
							<tr>
								<th class="colwidth-13 sorting"
									onclick="orderBy('zlshbgbh')" id="zlshbgbh_order">
									<div class="important">
									<div class="font-size-12 line-height-18">审核报告编号</div>
									<div class="font-size-9 line-height-18">Audit report No.</div>
									</div>
								</th>
								<th class="colwidth-13 sorting"
									onclick="orderBy('shgy')" id="shgy_order">
									<div class="important">
									<div class="font-size-12 line-height-18">审核概述</div>
									<div class="font-size-9 line-height-18">Overview</div>
									</div>
								</th>
								<th class="colwidth-5 sorting"
									onclick="orderBy('lx')" id="lx_order">

									<div class="font-size-12 line-height-18">类型</div>
									<div class="font-size-9 line-height-18">Type</div>

								</th>
								<th class="colwidth-9 sorting"
									onclick="orderBy('shdxmc')" id="shdxmc_order">
									<div class="font-size-12 line-height-18">审核对象</div>
									<div class="font-size-9 line-height-18">Object</div>
								</th>
								<th class="colwidth-9 sorting"
									onclick="orderBy('shlb')" id="shlb_order">
									<div class="font-size-12 line-height-18">审核类别</div>
									<div class="font-size-9 line-height-18">Category</div>
								</th>
								<th class="colwidth-8 sorting"
									onclick="orderBy('shrq')" id="shrq_order">
									<div class="font-size-12 line-height-18">审核日期</div>
									<div class="font-size-9 line-height-18">Date</div>

								</th>
								<th class="colwidth-10 sorting"
									onclick="orderBy('shcy')" id="shcy_order">
									<div class="font-size-12 line-height-18">审核成员</div>
									<div class="font-size-9 line-height-18">Audit member</div>
								</th>
								<th class="colwidth-5">
									<div class="font-size-12 line-height-18">附件</div>
									<div class="font-size-9 line-height-18">Attachment</div>
								</th>
								<th class="colwidth-10 sorting"
									onclick="orderBy('realname')"
									id="realname_order"
									>
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="colwidth-10 sorting"
									onclick="orderBy('whsj')"
									id="whsj_order"
									>
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="audit_report_main_table_list">

						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="auditdreport_main_table_pagination"></div>
				<div class="clearfix"></div>
				<!-- 
				<%@ include file="/WEB-INF/views/quality/reviewreformmeasures/measures_record.jsp"%>
				
				 -->

			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/open_win/department.jsp"%><!-- 选择部门 -->
<%-- 	<%@ include file="/WEB-INF/views/quality/reviewreportquery/review_report_view.jsp"%><!------- 查看质量审核报告-------> --%>
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript"
		src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script>
	<!-- 控件对话框 -->

	<script type="text/javascript">
		
		$(document).ready(
				function() {
					Navigation(menuCode, "", "");//初始化导航
					$('input[name=date-range-picker]').daterangepicker().prev()
							.on("click", function() {
								$(this).next().focus();
							});
					setShrq();
					decodePageParam();
				});
		
		 function setShrq(){
			 TimeUtil.getCurrentDate(function(date) {
					 var startStr = date; 
					 startStr=startStr.replaceAll('/','-');
					 startStr=addMonth(startStr,-6);
					 var endStr = date;
					 $("#shrq_search").data('daterangepicker').setStartDate(startStr);
					 $("#shrq_search").data('daterangepicker').setEndDate(endStr);
					 $("#shrq_search").val(startStr + " ~ " + endStr);
				});
		 } 
		 function addMonth(date, months){ 
				var d=new Date(Date.parse(date.replace(
						/-/g, "/"))); 
				d.setMonth(d.getMonth()+months); 
				var month=d.getMonth()+1; 
				var day = d.getDate(); 
				if(month<10){ 
				month = "0"+month; 
				} 
				if(day<10){ 
				day = "0"+day; 
				} 
				var val = d.getFullYear()+"-"+month+"-"+day; 
				return val; 
			} 

		function view(zlshbh) {
			var dprtcode = $("#dprtcode").val();
			window.open(basePath
					+ "/quality/qualityreviewreportquery/view?dprtcode="
					+ dprtcode + "&zlshbh=" + zlshbh);

		}
		function decodePageParam() {
			try {
				var decodeStr = Base64.decode(pageParam);
				var pageParamJson = JSON.parse(decodeStr);
				var params = pageParamJson.params;
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

		function goPage(pageNumber, sortType, sequence) {
			loadMainData(pageNumber, sortType, sequence);
		}

		function loadMainData(pageNumber, sortType, sequence) {
			var searchParams = gatherSearchParam();
			//添加一个参数,区分用户点击的是查看质量审核报告
			searchParams.paramsMap.isView = true;
			searchParams.paramsMap.currentUserBmdm= currentUser.bmdm;
			pagination = {
					page : pageNumber,
					sort : sortType,
					order : sequence,
					rows : 20
				};
			searchParams.pagination=pagination;
			startWait();
			AjaxUtil.ajax({
				url : basePath
						+ "/quality/qualityreviewreport/getAuditReportList",
				type : "post",
				data : JSON.stringify(searchParams),
				dataType : "json",
				contentType : "application/json;charset=utf-8",
				success : function(data) {
					//finishWait();
					//appendHtml(data);
					finishWait();
					if (data.total > 0) {
						appendHtml(data.rows);
						var page_ = new Pagination({
							renderTo : "auditdreport_main_table_pagination",
							data : data,
							sortColumn : sortType,
							orderType : sequence,
							extParams : {},
							goPage : function(a, b, c) {
								this_.loadMainData(a, b, c);
							}

						});
						// 标记关键字                                                                                                                             
						signByKeyword($("#keyword_search").val(), [ 1,2 ],"#audit_report_main_table_list tr td");
					} else {
						$("#audit_report_main_table_list").empty();
						$("#auditdreport_main_table_pagination").empty();
						$("#audit_report_main_table_list").append("<tr class='text-center'><td colspan=\"11\">暂无数据 No data.</td></tr>");
					}
					new Sticky({
						tableId : 'audit_report_main_table'
					});
				}

			});
		}

		function appendHtml(data) {
			if (data && data.length > 0) {
				var html = "";
				$
						.each(
								data,
								function(index, row) {
									html += "<tr>";
									html+="<td title="+row.zlshbgbh+"><a href='#' onClick=\"view('"+row.zlshbgbh + "')\">"+row.zlshbgbh+ "</a></td>";
									if(row.shgy){
										html += "<td title="+row.shgy+">"
										+ row.shgy + "</td>";
									}else{
										html+="<td></td>";
									}
									
									var lx = "";
									if ("1" == row.lx) {
										lx = "内部"
									} else if ("2" == row.lx) {
										lx = "外部"
									}
									html += "<td title="+lx+">" + lx + "</td>";
									html+="<td title='"+row.shdxmc+"'>"+row.shdxmc+"</td>";									var shlb = "";
									if ("10" == row.shlb) {
										shlb = "计划审核"
									} else if ("20" == row.shlb) {
										shlb = "非计划审核"
									}
									html += "<td title="+shlb+">" + shlb
											+ "</td>";
									if (row.shrq) {
										html += "<td title="
												+ row.shrq.substring(0, 10)
												+ ">"
												+ row.shrq.substring(0, 10)
												+ "</td>";
									} else {
										html += "<td></td>";
									}
									//html += "<td title="+row.shcy+">"
									//		+ row.shcy + "</td>";
									
									  if(!row.auditPerson){
										  html+="<td></td>";  
									  }else{
										 var ars= row.auditPerson.shcy.split(",");
										 var shcyMcs="";
										 for(var i=0;i<ars.length;i++){
											 shcyMcs+=ars[i].split("#_#")[3]+",";
										 }
										 shcyMcs=shcyMcs.substr(0,shcyMcs.length-1);
										 html+="<td title="+shcyMcs+">"+shcyMcs+"</td>";  
									  }
									html += "<td title='附件 Attachment' class='text-center'>";
									if (row.attachments != null
											&& row.attachments.length > 0) {
										html += '<i id="'+row.id+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';

									}
									html += "</td>";
									html += "<td title="+row.user.displayName+">"
											+ row.user.displayName + "</td>";
									html += "<td title="
											+ row.whsj.substring(0, 10) + ">"
											+ row.whsj.substring(0, 10)
											+ "</td>";
									html += "<td title="
											+ AccessDepartmentUtil
													.getDpartName(row.dprtcode)
											+ ">"
											+ AccessDepartmentUtil
													.getDpartName(row.dprtcode)
											+ "</td>";
									html += "</tr>";
								});
				$("#audit_report_main_table_list").empty();
				$("#audit_report_main_table_list").append(html);
				initFile();
			}
				refreshPermission();

		}

		function initFile() {
			WebuiPopoverUtil.initWebuiPopover('attachment-view', 'body',
					function(obj) {
						return getHistoryAttachmentList(obj);
					});
			$("#work_card_main_table_top_div").scroll(function() {
				$('.attachment-view').webuiPopover('hide');
			});
		}

		function getHistoryAttachmentList(obj) {
			var jsonData = [ {
				mainid : $(obj).attr('id'),
				type : '附件'
			} ];
			return history_attach_alert_Modal
					.getHistoryAttachmentList(jsonData);
		}
		function getMore() {
			if ($("#divSearch", $("#panel")).css("display") == "none") {
				$("#divSearch", $("#panel")).css("display", "block");
				$("#icon", $("#panel")).removeClass("icon-caret-down");
				$("#icon", $("#panel")).addClass("icon-caret-up");
			} else {
				$("#divSearch", $("#panel")).css("display", "none");
				$("#icon", $("#panel")).removeClass("icon-caret-up");
				$("#icon", $("#panel")).addClass("icon-caret-down");
			}

		}
		function openzrdw(obj) {
			var dprtname = $("#quality_audit_module" + "_" + obj).val();
			var dprtcode = $("#quality_audit_module" + "_" + obj + "id").val();
			departmentModal.show({
				dprtnameList : dprtname,// 部门名称
				dprtcodeList : dprtcode,// 部门id
				jdbs : null,
				type : false,
				dprtcode : $("#dprtcode").val(),//
				callback : function(data) {// 回调函数
					$("#quality_audit_module_search_" + obj).val(
							data.dprtbm + " " + data.dprtname);
					$("#quality_audit_module_search_" + obj + "bh").val(
							data.dprtbm);
					$("#quality_audit_module_search_" + obj + "mc").val(
							data.dprtname);
					$("#quality_audit_module_search_" + obj + "id").val(
							data.dprtcode);
				}
			});
		}
		function resert() {
			$("#shdxbh").val("");
			$("#keyword").val("");
			$("#shrq_search").val("");
			$("#lx").find("option[value='']").attr("selected", true);
			$("#shlb").find("option[value='']").attr("selected", true);
			$("#dprtcode").children("option[value='" + userJgdm + "']").attr(
					"selected", true);
			$("#quality_audit_module_search_shdxmc").val("");
			$("#quality_audit_module_search_shdxbh").val("");
			$("#quality_audit_module_search_shdxid").val("");
			$("#quality_audit_module_search_shdx").val("");

		}
		function gatherSearchParam() {
			var searchParams = {};
			var paramsMap = {};
			var shrqbegin = null;
			var shrqend = null;
			// 审核对象名称
			var shdxmc = $.trim($("#quality_audit_module_search_shdxmc").val());
			//审核对象编号
			var shdxbh = $.trim($("#quality_audit_module_search_shdxbh").val());
			//审核对象
			var shdx = $.trim($("#quality_audit_module_search_shdx").val());
			//审核对象id
			var shdxid = $.trim($("#quality_audit_module_search_shdxid").val());

			//关键字
			var keyword = $.trim($("#keyword_search").val());
			//审核日期
			var shrq = $("#shrq_search").val();

			if (shrq) {
				shrqbegin = shrq.substring(0, 10);
				shrqend = shrq.substring(13, 23);
			}
			//类型
			var lx = $("#lx_search").val();
			//审核类别
			var shlb = $("#shlb_search").val();
			//组织机构
			var dprtcode = $("#dprtcode").val();

			if (shdx == (shdxbh + " " + shdxmc)) {
				searchParams.shdxbh = shdxbh;
			} else {
				paramsMap.shrn = $.trim(shdx);
			}

			paramsMap.keyword = keyword;
			paramsMap.shrqbegin = shrqbegin;
			paramsMap.shrqend = shrqend;
			searchParams.paramsMap = paramsMap;
			searchParams.shrid = shdxid;
			searchParams.lx = lx;
			searchParams.shlb = shlb;
			searchParams.dprtcode = dprtcode;

			return searchParams;

		}

		//搜索条件显示与隐藏 
		function search() {
			if ($("#divSearch").css("display") == "none") {
				$("#divSearch").css("display", "block");
				App.resizeHeight();
				$("#icon").removeClass("icon-caret-down");
				$("#icon").addClass("icon-caret-up");
			} else {
				$("#divSearch").css("display", "none");
				App.resizeHeight();
				$("#icon").removeClass("icon-caret-up");
				$("#icon").addClass("icon-caret-down");
			}
		}

		//查看
		function viewAuditReport() {
			$("#review_report_view_alert_Modal").modal("show");
		}
		function orderBy(obj){
	 		var orderStyle = $("#" + obj + "_order").attr("class");
			$("th[id$=_order]").each(function() { //重置class
				$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
			});
			$("#" + obj + "_" + "order").removeClass("sorting");
			var orderType = "asc";
			if (orderStyle.indexOf("sorting_asc") >= 0) {
				$("#" + obj + "_" + "order").addClass("sorting_desc");
				orderType = "desc";
			} else {
				$("#" + obj + "_" + "order").addClass("sorting_asc");
				orderType = "asc";
			}
			orderStyle = $("#" + obj + "_order").attr("class");
			var currentPage = $("#pagination li[class='active']").text();
			this.goPage(currentPage,obj,orderStyle.split("_")[1]);		
			
		}
	</script>
</body>
</html>