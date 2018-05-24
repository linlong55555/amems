<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<%@ include file="/WEB-INF/views/open_win/import.jsp"%> 
<link href="${ctx}/static/lib/BreakingNews/BreakingNews.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/lib/BreakingNews/BreakingNews.js"></script>
<link href="${ctx}/static/lib/webui-popover/jquery.webui-popover.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/lib/webui-popover/jquery.webui-popover.min.js"></script>
<style type="text/css">
	.left-10 {
	    left: 10px !important;
	}
</style>

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
					flb.reload();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				<!-- 搜索框end -->
				<div class='searchContent row-height margin-top-0'>
					<div class="row" style="margin-left: 0px; margin-right: 0px;">
						<div class="pull-left form-group">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" 
								permissioncode='produce:flb:main:01,produce:flb:main:03' style="margin-right:3px;" onclick="flb.add()">
								<div class="font-size-12">新增</div>
								<div class="font-size-9">Add</div>
							</button>
							
							<div class="pull-left" id="message_div">
								<div class="BreakingNewsController easing pull-left" id="messageDiv" style="height: 34px;">
									<div class="bn-title cursor-pointer"></div>
									<ul id="messageUl" style="left: 10px; margin-top: 0px;" class="left-10"></ul>
									<div class="bn-arrows"><span class="bn-arrows-left"></span><span class="bn-arrows-right"></span></div>	
								</div>	
								<div class="pull-left cursor-pointer">
									<img src="${ctx}/static/images/help.png" id="tghelp" style='margin-top:7px;'>
								</div>
							</div>	
						</div>
						
						<div class="pull-right form-group">
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="font-size-12">飞机注册号</div>
								<div class="font-size-9">A/C REG</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="padding-left-8 pull-left" style="width: 200px; margin-right:5px;">
								   <select id="fjzch" class="form-control" onchange="flb.reload()">
								   </select> 
								</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="font-size-12">飞行日期</div>
								<div class="font-size-9">Flight Date</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="padding-left-8 pull-left" style="width: 200px; margin-right:5px;">
								   <input class="form-control date-range-picker" id="fxrq" type="text">
								</div>
							</div>
							<div class="pull-left padding-left-0 padding-right-0">
								<div class=" pull-left padding-left-0 padding-right-0" style="width: 210px;">
									<input placeholder="飞行记录单号/记录本页码" class="form-control" id="keyword" type="text">
								</div>
			                    <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="flb.reload()">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                   		<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" onclick="flb.more()">
										<div class="pull-left text-center">
											<div class="font-size-12">更多</div>
											<div class="font-size-9">More</div>
										</div>
										<div class="pull-left padding-top-5">
											&nbsp;<i class="icon-caret-down font-size-15" id="icon"></i>
										</div>
									</button>
			                    </div> 
							</div>
						</div>
					</div>
				
			
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-4  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-xs-9 col-sm-9 padding-left-9 padding-right-0">
							<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input name="zt" value="1" style="vertical-align:text-bottom" checked="checked" type="checkbox">&nbsp;保存&nbsp;&nbsp;
							</label>
							<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input name="zt" value="2" style="vertical-align:text-bottom" checked="checked" type="checkbox">&nbsp;提交&nbsp;&nbsp;
							</label>
							<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input name="zt" value="12" style="vertical-align:text-bottom" checked="checked" type="checkbox">&nbsp;修订&nbsp;&nbsp;
							</label>
							<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input name="zt" value="9" style="vertical-align:text-bottom" type="checkbox">&nbsp;作废&nbsp;&nbsp;
							</label>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-4  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-9 col-sm-9 padding-left-9 padding-right-0">
							<select id="dprtcode" class="form-control" onchange="flb.changeDprtcode()" >
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="flb.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class="clearfix"></div>
				</div>
				<!-- 搜索框end -->
				<div class="clearfix"></div>
				<!--------------------  表格 start -------------------->
				<div id="flb_table_main">
					<div id="flb_table_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" c-height="55%">
						<table id="flb_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 500px;">
							<thead>                              
								<tr>
									<th class="colwidth-5 fixed-column">
										<div class="font-size-12 line-height-18" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="colwidth-9 sorting" onclick="flb.orderBy('fjzch','')" name="column_fjzch">
										<div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div>
									</th>
									<th class="colwidth-7 sorting" onclick="flb.orderBy('fxrq','')" name="column_fxrq">
										<div class="font-size-12 line-height-18">飞行日期</div>
										<div class="font-size-9 line-height-18">Flight Date</div>
									</th>
									<th class="colwidth-9 sorting" onclick="flb.orderBy('fxjlbh')" name="column_fxjlbh">
										<div class="important">
											<div class="font-size-12 line-height-18">飞行记录单号</div>
											<div class="font-size-9 line-height-18">FLB No.</div>
										</div>
									</th>
									<th class="colwidth-7 sorting" onclick="flb.orderBy('jlbym','')" name="column_jlbym">
										<div class="important">
											<div class="font-size-12 line-height-18">记录本页码</div>
											<div class="font-size-9 line-height-18">Page</div>
										</div>
									</th>
									<th class="colwidth-5 sorting" onclick="flb.orderBy('zt')" name="column_zt">
										<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">Status</div>
									</th>
									<th class="colwidth-9 sorting" onclick="flb.orderBy('zdrid','')" name="column_zdrid">
										<div class="font-size-12 line-height-18">制单人</div>
										<div class="font-size-9 line-height-18">Creator</div>
									</th>
									<th class="colwidth-10 sorting" onclick="flb.orderBy('zdsj','')" name="column_zdsj" >
										<div class="font-size-12 line-height-18">制单时间</div>
										<div class="font-size-9 line-height-18">Create Time</div>
									</th>
									<th class="colwidth-9 sorting" onclick="flb.orderBy('xdrid','')" name="column_xdrid">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th class="colwidth-10 sorting" onclick="flb.orderBy('xdsj','')" name="column_xdsj" >
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th class="colwidth-9">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr> 
							</thead>
							<tbody id="flb_table_list">
							</tbody>
						</table>
					</div>
					
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="flb_table_pagination">
					</div>
					<div class="clearfix"></div>
				</div>
				<!--------------------  表格 end -------------------->
				<div class="clearfix"></div>
				
				<div class='displayContent' id='bottom_hidden_content' style='display:none;overflow-y:auto;' >
					<div class="clearfix"></div>
						<%@ include file="/WEB-INF/views/produce/flb/flightlogbook_record.jsp" %> <!--飞行数据  -->
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>

<%@ include file="/WEB-INF/views/open_win/log.jsp"%><!-------日志 -------->
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%><!-------日志差异 -------->
<%@ include file="/WEB-INF/views/produce/flb/flightlogbook_detail.jsp"%><!-------飞行记录本明细 -------->
<%@ include file="/WEB-INF/views/open_win/installationlist_replace.jsp"%><!-------拆换记录-------->
<%@ include file="/WEB-INF/views/open_win/installationlist_installed.jsp"%><!-------装上件-------->
<%@ include file="/WEB-INF/views/open_win/installationlist_removed.jsp"%><!-------拆下件-------->
<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_parent.jsp"%><!-------父节点-------->
<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_certificate.jsp"%><!-------证书-------->
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%><!-- 章节号 -->
<%@ include file="/WEB-INF/views/open_win/planePosition_search.jsp"%><!-------飞机站位 -------->
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/common/monitor/monitor_unit.js"></script> <!-- 监控项单位设置 -->
<script type="text/javascript" src="${ctx}/static/js/thjw/frame/flb/flightlogbook_main.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>