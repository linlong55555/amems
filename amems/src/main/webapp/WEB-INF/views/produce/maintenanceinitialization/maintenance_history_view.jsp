<%@ page contentType="text/html; charset=utf-8" language="java" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>维修历史清单</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="id" value='${id}' >
	<input type="hidden" id="fjzch" value='${fjzch}' >
	<input type="hidden" id="dprtcode" value='${dprtcode}' >
	<input type="hidden" id="historyType" value='${type}' >
		<!-------导航Start--->
		<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<!-- BEGIN CONTENT -->
	<div class="page-content" id="m_history_view" >
		<div class="panel panel-primary ">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body panel-body-new">
				
				<div id="m_task_info">
					<%@ include file="/WEB-INF/views/common/produce/task_maintenance_info.jsp" %> <!-- 维修任务信息 -->
				</div>
				<div id="eo_task_info">
					<%@ include file="/WEB-INF/views/common/produce/task_eo_info.jsp" %> <!-- EO任务信息 -->	
				</div>
				<div id="po_task_info">
					<%@ include file="/WEB-INF/views/common/produce/task_po_info.jsp" %> <!-- 生产指令任务信息 -->	
				</div>
				
				<div class='input-group-border' style='padding-top:0px;'>
				<%@ include file="/WEB-INF/views/common/produce/executionHistory.jsp" %>  <!-- 执行历史 -->
				</div>
				
				<div class="clearfix"></div>	
																								
			</div>
		</div>
	</div>		
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenanceinitialization/maintenance_history_view.js"></script>
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
<%@ include file="/WEB-INF/views/common/produce/feedback_replacement_tab_view.jsp" %>  <!-- 完工反馈及拆换件记录详情弹窗 -->
</body>
</html>