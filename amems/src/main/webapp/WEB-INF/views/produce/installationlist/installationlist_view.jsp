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
			<div class="panel-body" id="installation_view">
				<input type="hidden" id="zjqdid" value="${param.id}"/>
				<input type="hidden" id="dataType" value="${param.type}"/>
				<select id="fjzch" style="display: none;"></select>
				<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_common.jsp"%>
			</div>
		</div>
	</div>
<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_certificate.jsp"%>
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/installationlist/installationlist_main.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/installationlist/installationlist_table.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
<script type="text/javascript" src="${ctx}/static/js/thjw/common/monitor/monitor_unit.js"></script> <!-- 监控项单位设置 -->
<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/installationlist/installationlist_view.js"></script>
</body>
</html>