<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>入库管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript" src="${ctx}/static/lib/echarts/echarts.min.js"></script>
</head>
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<ul class="page-breadcrumb breadcrumb">
					<li>
						<i class="icon-home pull-left"></i> 
						<a href="${ctx}/main" class="pull-left">
							<div class="font-size-12 line-height-12">首页</div>
							<div class="font-size-9 ">Home</div>
						</a> 
						<i class="icon-angle-right pull-left"></i>
					</li>
					<li>
						<a onclick="javascript:void(0)" class="pull-left">
							<div class="font-size-12 line-height-12">航材管理</div>
							<div class="font-size-9 ">Air Material</div>
						</a>
						<i class="icon-angle-right pull-left"></i>
					</li>
					<li>
						<a onclick="javascript:void(0)" class="pull-left">
							<div class="font-size-12 line-height-12">入库管理</div>
							<div class="font-size-9 ">Stock In</div>
						</a>
					</li>
				</ul>
			</div>
		
			<div class="clearfix"></div>
			
			<div class="panel-body" id="demo" style="width:100%;height:400px">
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/echarts_demo/demo.js"></script>
</body>
</html>