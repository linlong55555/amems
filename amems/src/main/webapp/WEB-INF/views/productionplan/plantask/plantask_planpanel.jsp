
<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<link >
<script type="text/javascript">
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
			<div class="clearfix"></div>
			<div class="panel-body">
				<!-----标签导航start---->
				<ul class="nav nav-tabs" role="tablist" id="tablist">
				  <li role="presentation"   class="active" >
				  		<a  id="isPartBlock" href="#ispart" data-toggle="tab">飞机任务 Airplane Task</a>
				  		<span class="info-label danger-bg" id="zjjs"></span>
				  </li>
				  <li role="presentation"   >
					  <a id="noPartBlock"  href="#nopart" data-toggle="tab">非装机任务 Other Task</a>
					  <span class="info-label danger-bg" id="fzjjs"></span>
				  </li>
				</ul>
				 <%@ include file="/WEB-INF/views/open_win/log.jsp"%>
				 <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
				<!-----标签内容start---->
		      	<div class="tab-content margin-bottom-10">
					<div class="tab-pane fade in active"  id="ispart">
						 <%@ include file="/WEB-INF/views/productionplan/plantask/planpanel_ispart.jsp"%>
					</div>
			        <div class="tab-pane fade" id="nopart">
		          		<%@ include file="/WEB-INF/views/productionplan/plantask/planpanel_nopart.jsp"%>
			        </div>
				</div>
			</div>
		</div>
	</div>
</body>
 
</html>