<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>工单管理145</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script>
		var id = "${param.id}";
		var pageParam = '${param.pageParam}';
		var blLx = "${blLx}"; //保留类型 1:故障保留  2:缺陷保留
		var blId = "${blId}"; //保留类型ID
		var gbid = "${param.gbid}"
		var gbbh = "${param.gbbh}";
		var fjzch = "${param.fjzch}";
		var fjjx="${param.fjjx}";
		var fjxlh="${param.fjxlh}";
		var gdid="${param.gdid}";
		var jhKsrq="${param.jhKsrq}";
		var jhJsrq="${param.jhJsrq}";
		var mainidParam = '${mainidParam}';
		var customer = '${customer}'; //确认站点GQ
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
						workorder145_main.doSearch();//调用主列表页查询
					}
				}
			});
		});
	</script>

</head>

<body class="page-header-fixed">
	<div class="page-content" id='workorder145_mainpage'>	
	 	<div class="panel panel-primary">
		    <!-- panel-heading -->
			<div class="panel-heading" id="NavigationBar"></div>
			
			<!-- Start: 主体内容 -->
			<div class="panel-body" style='padding-bottom:2px;'>
			    <!-- Start:条件检索  -->
				<div  class='row-height margin-top-0 searchContent' >
				    <!-- 上传按钮  -->
				    	<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group " >
							<div class="pull-left padding-left-0" style='margin-right:3px;'>
								<a href="#" onclick="workorder145_main.openWinAdd(true)" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='produce:nrc145:main:01' >
									<div class="font-size-12">新增</div>
									<div class="font-size-9">Add</div>
								</a> 
							</div>
							<div class="pull-left" style='margin-right:3px;'>
								<a href="#" onclick="workorder145_main.exportExcel()"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='produce:nrc145:main:09' >
									<div class="font-size-12">导出</div>
									<div class="font-size-9">Export</div>
								</a> 
							</div>
						</div>
						<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group " >
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">反馈状态</div>
								<div class="font-size-9">Status</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">												
								<select class='form-control' id="feedback_search" onchange="workorder145_main.changefkzt()">
									<option value="" selected="true">显示全部All</option>
									<c:forEach items="${feedbackStatusEnum}" var="item">
										<option value="${item.id}">${item.name}</option>
									</c:forEach>
							    </select>
							</div>
						</div>
						  <!-- <div class=" col-lg-2 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group " >
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">关闭状态</div>
								<div class="font-size-9">Status</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">												
								<select class='form-control' id="gb_search" onchange="workorder145_main.changegbzt()">
									<option value="" selected="true">显示全部All</option>
									<option value="1">关闭</option>
									<option value="0">未关闭</option>
							    </select>
							</div>
						</div> -->
						<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group " >
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">飞机注册号</div>
								<div class="font-size-9">A/C Reg No.</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="fjzch_search" class="form-control "  onchange="workorder145_main.changefj()">
								<option value="">显示全部All</option>
								<option value="-1">N/A</option>
								<c:forEach items="${fjList}" var="type">
											<option value="${type.fjzch}" >${erayFns:escapeStr(type.fjzch)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group " style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='工包编号/工卡编号/工单编号/工单标题'  class="form-control" id="wo145search_keyword">
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1"  onclick="workorder145_main.doSearch();">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
		                   		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
		                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  onclick='workorder145_main.search()' >
										<div class='input-group'>
								<div class="input-group-addon">
								<div class="font-size-12">更多</div>
								<div class="font-size-9 margin-top-5" >More</div>
								</div>
								<div class="input-group-addon">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
								</div>
								</div>
						   		</button> 
		                	</div>
						</div>
						</div>
						
					<div class='clearfix'></div>
					<!-- End:关键字搜索 -->
				
					<!-- Start:更多的搜索框 -->
					<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10 margin-bottom-0 margin-top-10  display-none border-cccccc" id="divSearch">
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 " >
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">来源分类</div>
								<div class="font-size-9">Type</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0" id="gdlxDiv">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 " >
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">工单状态</div>
								<div class="font-size-9">Status</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0" id="gdztDiv">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">工包编号</div>
								<div class="font-size-9">Package No.</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left">
								<input type='text' class='form-control' id="gbbh_search" maxlength="15"/>
							</div>
						</div>
		 		<!-- 	<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 " >
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">工单状态</div>
								<div class="font-size-9">Workorder Status</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select class='form-control' id="gdzt_search">
									<option value="" selected="true">显示全部All</option>
									<c:forEach items="${workorderStatusEnum}" var="item">
										<option value="${item.id}">${item.name}</option>
									</c:forEach>
							    </select>
							</div>
						</div>  
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 " >
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">反馈状态</div>
								<div class="font-size-9">FeedBack Status</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select class='form-control' id="fkzt_search">
									<option value="" selected="true">显示全部All</option>
									<c:forEach items="${feedbackStatusEnum}" var="item">
										<option value="${item.id}">${item.name}</option>
									</c:forEach>
							    </select>
							</div>
						</div>  -->
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">ATA章节号</div>
								<div class="font-size-9">ATA</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left input-group">
								<div class='input-group col-xs-12'>
									<input type="hidden"  id="zjhid_search" class="form-control"/>
									<input type="text"  id="zjhName_search" class="form-control readonly-style" readonly="readonly" ondblclick="workorder145_main.openChapterWin()" />
									<span  id="zjhbt_search" class='input-group-addon btn btn-default' onclick="workorder145_main.openChapterWin()">
									 	<i class="icon-search"></i>
									</span>
							    </div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10" >
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">区域</div>
								<div class="font-size-9">Zone</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left">
								<input class='form-control' id='wo145qy_search' name="wo145qy_search">
							</div>
						</div> 
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10" >
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">工作类别</div>
								<div class="font-size-9">Job Type</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left">
								<select class='form-control' id='wo145gzlx_search'></select>
							</div>
						</div> 
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 " >
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="dprtcode_search" class="form-control "  name="dprtcode"  onchange="workorder145_main.changeDprtcode();">
									<c:forEach items="${accessDepartment}" var="type">
											<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1"onclick="workorder145_main.searchreset();">
								<div class="font-size-12">重置</div>
								<div class="font-size-9">Reset</div>
							</button>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>
					<!-- End:更多的搜索框 -->
				</div>
				<!-- End:条件检索  -->
				
				<!-- Start:表格  -->
				<div class='table_pagination'>
					<div  class='col-lg-12 col-md-12 padding-left-0 padding-right-0 table-height' c-height="45%" style="overflow-x: auto;" id="workorder145_table_div">
						<table class='table table-bordered table-striped table-thin table-hover table-set' style='margin-bottom:0px !important;min-width:2500px;' id="workorder145_table" >
							<thead>
								<tr>
									<%-- <th class="viewCol fixed-column colwidth-7 selectAllImg">
										<a href="#" onclick="SelectUtil.performSelectAll('workorder145_table_div')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
										<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('workorder145_table_div')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
									</th> --%>
									<th class='colwidth-15 fixed-column'>
										<div class="font-size-12 line-height-12">操作</div>
				                        <div class="font-size-9 line-height-12">Operation</div>
									</th>
									<th class='colwidth-18 sorting'  onclick="workorder145_main.orderBy('lyrwh')"  id="lyrwh_order">
										<div class="font-size-12 line-height-12">任务号</div>
				                        <div class="font-size-9 line-height-12">Task No.</div>
									</th>
									<th class='colwidth-30 important sorting'  onclick="workorder145_main.orderBy('gdbt')"  id="gdbt_order">
									<div class="important">	
										<div class="font-size-12 line-height-12">缺陷描述</div>
				                        <div class="font-size-9 line-height-12">Defect Desc</div></div>
									</th>
									<th class='colwidth-9 sorting'  onclick="workorder145_main.orderBy('zt')"  id="zt_order">
										<div class="font-size-12 line-height-12">状态</div>
				                        <div class="font-size-9 line-height-12">Status</div>
									</th>
									<th class='colwidth-15'>
										<div class="font-size-12 line-height-12">工卡附件</div>
				                        <div class="font-size-9 line-height-12">Workcard Attachment</div>
									</th>
									<th class='colwidth-10 sorting'  onclick="workorder145_main.orderBy('jh_ksrq')"  id="jh_ksrq_order">
										<div class="font-size-12 line-height-12">计划开始日期</div>
				                        <div class="font-size-9 line-height-12">Plan start date</div>
									</th>
									<th class='colwidth-10 sorting'  onclick="workorder145_main.orderBy('jh_jsrq')"  id="jh_jsrq_order">
										<div class="font-size-12 line-height-12">计划完成日期</div>
				                        <div class="font-size-9 line-height-12">Plan end date</div>
									</th>
									<th class='colwidth-13 sorting'  onclick="workorder145_main.orderBy('zjh')"  id="zjh_order">
										<div class="font-size-12 line-height-12">ATA章节号</div>
				                        <div class="font-size-9 line-height-12">ATA</div>
									</th>
									<th class='colwidth-13 important sorting'  onclick="workorder145_main.orderBy('gdbh')"  id="gdbh_order">
									<div class="important">	
										<div class="font-size-12 line-height-12">工单编号</div>
				                        <div class="font-size-9 line-height-12">Work Order</div></div>
									</th>
									<th class='colwidth-10 sorting'  onclick="workorder145_main.orderBy('gdlx')"  id="gdlx_order">
										<div class="font-size-12 line-height-12">来源分类</div>
				                        <div class="font-size-9 line-height-12">Originating Type</div>
									</th>
									<th class='colwidth-9 sorting'  onclick="workorder145_main.orderBy('zdrq')"  id="zdrq_order">
										<div class="font-size-12 line-height-12">制单日期</div>
				                        <div class="font-size-9 line-height-12">Date</div>
									</th>
									<th class='colwidth-10 sorting'  onclick="workorder145_main.orderBy('gzlb')"  id="gzlb_order">
										<div class="font-size-12 line-height-12">工作类别</div>
				                        <div class="font-size-9 line-height-12">Job Type</div>
									</th>
									<th class='colwidth-10 ' >												
											<div class="font-size-12 line-height-12">区域</div>
					                    	<div class="font-size-9 line-height-12">Zone</div>						              
									</th>
									<th class='colwidth-10 sorting'  onclick="workorder145_main.orderBy('bgr')"  id="bgr_order">
										<div class="font-size-12 line-height-12">报告人</div>
				                        <div class="font-size-9 line-height-12">Reporter</div>
									</th>
									<th class='colwidth-13 important'>
										<div class="font-size-12 line-height-12">来源工卡</div>
				                        <div class="font-size-9 line-height-12">Source</div>
									</th>
									<th class='colwidth-13 sorting'  onclick="workorder145_main.orderBy('fjzch')"  id="fjzch_order">
										<div class="font-size-12 line-height-12">飞机注册号/MSN</div>
				                        <div class="font-size-9 line-height-12">A/C Reg No.</div>
									</th>
									<th class='colwidth-13 important sorting'  onclick="workorder145_main.orderBy('gbbh')"  id="gbbh_order">
									<div class="important">	
										<div class="font-size-12 line-height-12">工包编号</div>
				                        <div class="font-size-9 line-height-12">Plan/actual time</div></div>
									</th>
									<th class='colwidth-7'>
										<div class="font-size-12 line-height-12">计划工时</div>
				                        <div class="font-size-9 line-height-12">Plan Hours</div>
									</th>
									<th class='colwidth-10'>
										<div class="font-size-12 line-height-12">工单附件</div>
				                        <div class="font-size-9 line-height-12">WO Attachment</div>
									</th>
									<th class='colwidth-7'>
										<div class="font-size-12 line-height-12">打印次数</div>
				                        <div class="font-size-9 line-height-12">Print Count</div>
									</th>
									<th class='colwidth-10 sorting'  onclick="workorder145_main.orderBy('whrid')"  id="whrid_order">
										<div class="font-size-12 line-height-12">维护人</div>
				                        <div class="font-size-9 line-height-12">Maintainer</div>
									</th>
									<th class='colwidth-13 sorting'  onclick="workorder145_main.orderBy('whsj')"  id="whsj_order">
										<div class="font-size-12 line-height-12">维护时间</div>
				                        <div class="font-size-9 line-height-12">Maintainer</div>
									</th>
									<th class=" colwidth-10" >
										<div class="font-size-12 line-height-18" >组织机构 </div>
										<div class="font-size-9 line-height-18" >Organization</div>
									</th>
								</tr>
							</thead>
							<tbody id='workorder145_list'>
								
							</tbody>
						</table>
					</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="workorder145_pagination" ></div>
					<div class='clearfix'></div>
				</div>
				<!-- End:表格  -->
				
				<!-- Start:底部  -->
				<div id='bottom_hidden_content' class='displayContent col-xs-12  clearfix padding-top-10 padding-left-0 padding-right-0 padding-top-0' >
					<div id="hideIcon" class="pull-right" style='height:1px;padding-right:8px;margin-top:1px;'>
						<img src="${ctx}/static/images/down.png" onclick='workorder145_main.hideBottom()' style="width:33px;cursor:pointer;">
					</div>
					<ul id="myChildTab" class="nav nav-tabs tabNew-style" style="padding-top:0px !important;">
						<li class="active" >
							<a id='tool' href='#toolTab' data-toggle="tab"  >
								<div class="font-size-12 line-height-12">航材工具</div>
			                    <div class="font-size-9 line-height-9">Tool chain</div>
			                </a>
						</li>
						<li class=""  >
							<a href='#feedbackTool' data-toggle="tab"  >
								<div class="font-size-12 line-height-12">完工反馈</div>
			                    <div class="font-size-9 line-height-9">Feedback</div>
			                </a>
						</li>
						<li class="" >
							<a href='#replacementTab' data-toggle="tab"  >
								<div class="font-size-12 line-height-12">拆换件记录</div>
			                    <div class="font-size-9 line-height-9">Replacement</div>
			                </a>
						</li>
					 </ul>
					 <div class="tab-content" style='padding:0px;'>
						<div id="toolTab" class="tab-pane fade active in" >
							<%@ include file="/WEB-INF/views/produce/workorder/145/workorder_tab_materialTool.jsp"%> 
						</div>
						<div id="feedbackTool" class="tab-pane fade">
							<%@ include file="/WEB-INF/views/produce/workorder/145/workorder_tab_feedback.jsp"%>
						</div>
						<div id="replacementTab" class="tab-pane fade">
							<%@ include file="/WEB-INF/views/produce/workorder/145/workorder_tab_replacementRecord.jsp"%>
						</div>
					 </div>
				</div>
			    <!-- End:底部  -->
			</div>
			<!-- End: 主体内容 -->
			
		</div>
	</div>
	
	<!-- Start: 引入需要的JSP -->
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%> <!-- 主页日志 -->
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%>  <!-- 主页日志 -->
    <%@ include file="/WEB-INF/views/produce/nrc/145/nrc145_add.jsp"%><!-- -工单新增-->
    <%@ include file="/WEB-INF/views/produce/workorder/145/workorder_feedback.jsp"%><!-- -工单反馈 -->
    <%@ include file="/WEB-INF/views/produce/workorder/145/workorder_wg_close.jsp"%><!-- -工单完工关闭 -->
    <%@ include file="/WEB-INF/views/produce/workorder/145/workorder_zd_close.jsp"%><!-- -工单指定结束 -->
    <%@ include file="/WEB-INF/views/produce/workorder/145/workorder_win_wxxmwin.jsp"%> <!-- 来源任务号：维修项目弹框 -->
    <%@ include file="/WEB-INF/views/produce/workorder/145/workorder_win_eo.jsp"%> <!-- 来源任务号：EO弹框 -->
    <%@ include file="/WEB-INF/views/produce/workorder/145/workorder_win_lycard.jsp"%> <!-- 来源任务号：来源工卡弹框 -->
    <%@ include file="/WEB-INF/views/produce/workorder/145/workorder_win_lynrc.jsp"%> <!-- 来源任务号：来源NRC工单弹框 -->
    <%-- <%@ include file="/WEB-INF/views/produce/workorder/145/workorder_win_wcsx.jsp"%> <!-- 来源任务号：完成时限弹框 --> --%>
    <%@ include file="/WEB-INF/views/produce/workorder/145/workpackage145_win.jsp"%> <!-- 145工包弹框 -->
    <%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
       <%@ include file="/WEB-INF/views/open_win/installationlist_replace_145.jsp"%><!-------拆换记录-------->
	<%@ include file="/WEB-INF/views/open_win/installationlist_installed.jsp"%><!-------装上件-------->
	<%@ include file="/WEB-INF/views/open_win/installationlist_removed.jsp"%><!-------拆下件-------->
	<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_parent.jsp"%><!-------父节点-------->
	<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_certificate.jsp"%><!-------证书-------->
	<%@ include file="/WEB-INF/views/open_win/planePosition_search.jsp"%><!-------飞机站位 -------->
	<%@ include file="/WEB-INF/views/open_win/inventory_distribution_details_view.jsp" %>  <!-- 航材工具库存弹窗 -->
      <%@ include file="/WEB-INF/views/open_win/equivalent_substitution_view.jsp"%><!-------等效替代 -------->
       <%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%><!-- ATA章节对话框 -->
    <%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------报告人对话框 -------->
    <!-- Start: 引入需要的JSP -->
    
	<!-- Start: 引入需要的JS -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/nrc/145/nrc145_main.js"></script> <!-- 145工单管理主列表 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/145/workorder_main_tab.js"></script> <!-- 工单table信息 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 附件对话框 -->
	<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
		 <script type="text/javascript" src="${ctx}/static/js/thjw/common/monitor/monitor_unit.js"></script> <!-- 监控项转换js -->
	<script type="text/javascript">
		$(function(){
			
			Navigation(menuCode, '', '', 'GD-9-15', '雷伟', '2017-09-15', '雷伟', '2017-09-15');//初始化导航
			refreshPermission();
			// 重复请求  
			// workorder145_main.decodePageParam();
			workorder145_main.initInfo(); //初始化145工单管理主列表信息
			
			$('.date-picker').datepicker({//初始日期控件
				autoclose : true,
				clearBtn : true
			});
			if(gbid){
				workorder145_main.openWinAdd(true);
				Workorder145AddWin.initFjjx();
				$("#wo145add_gbjx").val(fjjx);
				Workorder145AddWin.initFjzch();
				$("#wo145add_gbid").val(gbid);
				$("#wo145add_gbbh").val(gbbh);
				$("#wo145add_gbfjzch").val(fjzch);
				$("#wo145add_msn").val(fjxlh);
				$("#wo145add_jh_ksrq").val(jhKsrq);
				$("#wo145add_jh_jsrq").val(jhJsrq);
				Workorder145AddWin.loadDatasByJX();
				fjzch='';
				gbid='';
				gbbh='';
				fjxlh='';
				fjjx='';
				jhKsrq='';
				jhJsrq='';
			}

			if(blLx && blId){
				workorder145_main.openWinAdd(); //保留类型直接打开新增界面
				blLx="";
				blId="";
			}
			if(mainidParam){
				workorder145_main.openWinFeedback(mainidParam,true,{}); //打开完工反馈
			}
			if(gdid){
				workorder145_main.openWinEdit(gdid,true);
			}
			
			logModal.init({
				code : 'B_S2_014',
				extparam:{GDLX:[3,9]}//加载日志
			});
		});
	</script>
	
	<!-- End:引入需要的JS -->
</body>
</html>
