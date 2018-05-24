<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

</head>
<body class="page-header-fixed">
<!-- 隐藏域 -->
<input type="hidden" value="1" id="hclxType">
<!-- 隐藏域end -->
	<div class="clearfix"></div>
	<div class="page-content tab-only" id="maintenance_initialization_main">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="clearfix"></div>
			<div class="panel-body">
				<div class="clearfix"></div>
				
				<!-----标签导航start---->
				<div>
				<ul class="nav nav-tabs tabNew-style" role="tablist" id="tablist">
				  	<li class="active">
				  		<a id='aircraftMaintenanceTab' href="#aircraftMaintenance"  data-toggle="tab">
				  			<div class="font-size-12 line-height-12">外场部件清单</div>
			                <div class="font-size-9 line-height-9">Outfield Parts List</div>
				  		</a>
				  	</li>
				  	<li >
				  		<a id='EOmonitoringTab' href="#EOMonitoring"  data-toggle="tab">
				  			<div class="font-size-12 line-height-12">在途部件清单：采购合同但未入库的部件清单</div>
			                <div class="font-size-9 line-height-9">transit Parts List</div>
				  		</a>
				  	</li>
				</ul>
				<!-----标签内容start---->
		      	<div class="tab-content">
					<div class="tab-pane fade in active"  id="aircraftMaintenance">
						<%-- <%@ include file="/WEB-INF/views/produce/maintenanceinitialization/aircraft_maintenance_monitoring_main.jsp"%> --%>
						<%@ include file="/WEB-INF/views/material2/stock/material/outfield_main.jsp"%>
					</div>
			        <div class="tab-pane fade" id="EOMonitoring">
		          		<%-- <%@ include file="/WEB-INF/views/produce/maintenanceinitialization/eo_monitoring_main.jsp"%> --%>
		          		<%@ include file="/WEB-INF/views/material2/stock/material/transit_main.jsp"%>
			        </div>
				</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/stock/material/outsideStock_main.js"></script>
	<%@ include file="/WEB-INF/views/common/produce/material_cq.jsp" %> 
</body>
</html>