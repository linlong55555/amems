<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body>
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading " id="NavigationBar"></div>
			<div class="panel-body" id="flb_view">
				<input type="hidden" id="fxjlbid" value="${param.id}"/>
				<input type="hidden" id="dprtcode"/>
				<%@ include file="/WEB-INF/views/produce/flb/flightlogbook_common.jsp"%>
			</div>
		</div>
	</div>
	
<%@ include file="/WEB-INF/views/open_win/installationlist_replace.jsp"%><!-------拆换记录-------->
<%@ include file="/WEB-INF/views/open_win/installationlist_installed.jsp"%><!-------装上件-------->
<%@ include file="/WEB-INF/views/open_win/installationlist_removed.jsp"%><!-------拆下件-------->
<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_parent.jsp"%><!-------父节点-------->
<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_certificate.jsp"%><!-------证书-------->
<%@ include file="/WEB-INF/views/open_win/planePosition_search.jsp"%><!-------飞机站位 -------->
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/monitor/monitor_unit.js"></script> <!-- 监控项单位设置 -->
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/flb/flightlogbook_view.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/flb/flightlogbook_detail.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/flb/flightlogbook_work.js"></script>
</body>
</html>