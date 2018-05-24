<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="auths" value="${sessionScope.auths}"/>
<c:set var="logonuser" value="${sessionScope.user}"/>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title><sitemesh:title default="机务维修管理系统"></sitemesh:title></title>
<link
	href="${ctx}/static/assets/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${ctx}/static/assets/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />

<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<!---<link href="${ctx}/static/assets/css/style-metronic.css"
	rel="stylesheet" type="text/css" />--->
<link href="${ctx}/static/assets/css/style.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/assets/css/style-responsive.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/assets/css/plugins.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/assets/css/themes/light.css" rel="stylesheet"
	type="text/css" id="style_color" />
<link href="${ctx}/static/assets/css/custom.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/assets/css/pbs.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/assets/css/english.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/assets/css/highslide.css" rel="stylesheet"
	type="text/css" />
<link
	href="${ctx}/static/assets/plugins/bootstrap-multiselect/bootstrap-multiselect.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/assets/plugins/data-tables/DT_bootstrap.css"
	rel="stylesheet" type="text/css" />
<link
	href="${ctx}/static/assets/plugins/bootstrap-datepicker/css/datepicker.css"
	rel="stylesheet" type="text/css" />
<link
	href="${ctx}/static/assets/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css"
	rel="stylesheet" />

<link rel="stylesheet" type="text/css" href="${ctx}/static/assets/plugins/jstree/dist/themes/default/style.min.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/uploadify/uploadify.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/assets/plugins/bootstrap-datetimepicker/jquery.datetimepicker.css"/>
<%-- <link rel="stylesheet" href="${ctx}/static/assets/plugins/tree/awesomeStyle/awesome.css" type="text/css"> --%>
<link rel="stylesheet" href="${ctx}/static/assets/plugins/tree/zTreeStyle/zTreeStyle.css" type="text/css">

<script src="${ctx}/static/assets/plugins/jquery-1.10.2.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/scrolltext.js"
	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/Chart.js"
	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/jquery-migrate-1.2.1.min.js"
	type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script
	src="${ctx}/static/assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/bootstrap/js/jquery.bootstrap.teninedialog.v3.min.js"
	type="text/javascript"></script>
	
<!--  参考http://www.xnwai.com/teninedialog/    jquery.bootstrap.teninedialog.js使用说明 -->
<script src="${ctx}/static/assets/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>

<script src="${ctx}/static/assets/plugins/jquery.blockui.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/jquery.cokie.min.js"
	type="text/javascript"></script>
<script
	src="${ctx}/static/assets/plugins/bootstrap-multiselect/bootstrap-multiselect.js"
	type="text/javascript"></script>
<script
	src="${ctx}/static/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"
	type="text/javascript"></script>
<script
	src="${ctx}/static/assets/plugins/bootstrap-daterangepicker/moment.js"></script>
<script
	src="${ctx}/static/assets/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="${ctx}/static/js/common.js"></script>
<script src="${ctx}/static/assets/plugins/jstree/dist/jstree.min.js"></script>
<script src="${ctx}/static/js/uploadify/jquery.uploadify.min.js"></script>
<script src="${ctx}/static/assets/plugins/bootstrap-datetimepicker/jquery.datetimepicker.js"></script>
<script src="${ctx}/static/js/jqueryfileupload/vendor/jquery.ui.widget.js"></script>
<script src="${ctx}/static/js/jqueryfileupload/jquery.iframe-transport.js"></script>
<script src="${ctx}/static/js/jqueryfileupload/jquery.fileupload.js"></script>
<script src="${ctx}/static/assets/plugins/tree/js/jquery.ztree.core-3.5.js"></script> <!-- 树标签 依赖 -->
<script src="${ctx}/static/assets/plugins/tree/js/jquery.ztree.excheck-3.5.js"></script> <!-- 树标签 依赖 -->
<script src="${ctx}/static/assets/plugins/tree/js/jquery.ztree.exedit-3.5.js"></script> 
<script src="${ctx}/static/dialog/bootstrapQ.js"></script>
<script src="${ctx}/static/dialog/bootstrapQ.min.js"></script>
<script src="${ctx}/static/dialog/qiao.js"></script>

<!-- END CORE PLUGINS -->

<!-- END THEME STYLES -->
<!-- <link rel="shortcut icon" href="favicon.ico" /> -->
<style>
table>thead>tr>th {
	text-align: center;
}

.tableRe>thead>tr>th {
	text-align: center;
}

#indexList>tbody>tr {
	cursor: pointer;
}

.notice {
	height: 40px;
	overflow: hidden;
}

.noticTipTxt {
	color: #ff7300;
	height: 22px;
	line-height: 22px;
	overflow: hidden;
}

.noticTipTxt li {
	height: 22px;
	line-height: 22px;
	margin-left: 12px;
}

.head {
	font-size: 18px;
	padding-left: 15px;
	line-height: 35px;
	-webkit-box-sizing: content-box;
	-moz-box-sizing: content-box;
	box-sizing: content-box;
	background: #f7f7f7;
	background-image: -webkit-gradient(linear, left 0, left 100%, from(#fff),
		to(#eee) );
	background-image: -webkit-linear-gradient(top, #fff, 0%, #eee, 100%);
	background-image: -moz-linear-gradient(top, #fff 0, #eee 100%);
	background-image: linear-gradient(to bottom, #fff 0, #eee 100%);
	background-repeat: repeat-x;
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff',
		endColorstr='#ffeeeeee', GradientType=0 );
	color: #333;
	border-bottom: 1px solid #DDD;
}

table {
	border-collapse: collapse;
}

.top {
	background: #58bbf7;
	border: 1px solid black;
}

.bottom {
	background: #f7f7f7;
	background-image: -webkit-gradient(linear, left 0, left 100%, from(#d7d7d7),
		to(#aeaeae) );
	background-image: -webkit-linear-gradient(top, #d7d7d7, 0%, #aeaeae, 100%);
	background-image: -moz-linear-gradient(top, #d7d7d7 0, #aeaeae 100%);
	background-image: linear-gradient(to bottom, #d7d7d7 0, #aeaeae 100%);
	background-repeat: repeat-x;
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#d7d7d7',
		endColorstr='#aeaeae', GradientType=0 );
	border: 1px solid black;
}

.topFinish {
	background: #3dae4e;
	border: 1px solid black;
}

.span1 {
	font-size: 14px;
	color: white;
}

.GMonth {
	text-align: center;
}

.table1>tbody>tr:first-child {
	height: 43px;
}

.table1 tr {
	height: 25px;
}

.table1 tr td {
	width: 75px;
	text-align: center;
	border: 1px solid #ccc;
}

.table1 tr:first-child td {
	border-top: none;
}

.table1 tr td:first-child {
	border-left: none;;
}

.table1 tr td:last-child {
	border-right: none;;
}

.table2 tr td {
	text-align: center;
	border: 1px solid #cccccc;
}

#dvb {
	position: absolute;
	display: none;
	padding: 10px;
	background: white;
	border: 1px solid #ccc;
}

.padding-5 {
	padding: 5px;
}
</style>
<sitemesh:head></sitemesh:head>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
<input type="hidden" id="authTotal" value=""/> 
	<!-- BEGIN HEADER -->
	<div class="header navbar navbar-fixed-top">
		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="header-inner">
			<!-- BEGIN LOGO -->
			<a class="navbar-brand" href="${ctx}/demo/revisionlist">
				<img src="${ctx}/static/assets/img/logo.png" alt="logo"
				class="img-responsive" />
			</a>
			<!-- END LOGO -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse"> <img
				src="${ctx}/static/assets/img/menu-toggler.png" alt="" />
			</a>

			<ul class="nav navbar-nav pull-right">
				<li><div style="height: 35px; line-height: 36px;">欢迎您</div></li>
				<li class="dropdown user"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" data-hover="dropdown"
					data-close-others="true"> <!--<img alt="" src="assets/img/avatar1_small.jpg"/>-->
						<span class="username"> ${sessionScope.user.username}</span> <i class="fa fa-angle-down"></i>
				</a>
					<ul class="dropdown-menu pull-right">
						<!-- <li><a href="extra_profile.html"> <i class="fa fa-user"></i>
								My Profile
						</a></li>
						<li><a href="page_calendar.html"> <i
								class="fa fa-calendar"></i> My Calendar
						</a></li>
						<li><a href="inbox.html"> <i class="fa fa-envelope"></i>
								My Inbox <span class="badge badge-danger"> 3 </span>
						</a></li>
						<li><a href="#"> <i class="fa fa-tasks"></i> My Tasks <span
								class="badge badge-success"> 7 </span>
						</a></li>
						<li class="divider"></li> -->
						<li><a href="javascript:;" id="trigger_fullscreen"> <i
								class="fa fa-arrows"></i> Full Screen
						</a></li>
						
						<!-- <li><a href="extra_lock.html"> <i class="fa fa-lock"></i>
								Lock Screen
						</a></li> -->
						<li><a href="javascript:;" data-toggle="modal" onclick="showChangePassword();"> <i class="fa fa-edit"></i>Change  password
						</a></li>
						<li><a href="javascript:;" onclick="logout();"> <i class="fa fa-key"></i> Log
								Out
						</a></li>
						
					</ul></li>
				<!-- END USER LOGIN DROPDOWN -->
			</ul>
			<!-- END TOP NAVIGATION MENU -->
		</div>
		<!-- END TOP NAVIGATION BAR -->
	</div>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar-wrapper " >
			<div class="page-sidebar navbar-collapse collapse">
				<!-- BEGIN SIDEBAR MENU -->
				<ul class="page-sidebar-menu" data-auto-scroll="true"
					data-slide-speed="200">

					<li class="sidebar-toggler-wrapper" style="margin-bottom: 5px;">
						<div class="col-md-9 pull-left information" id="simpleInfo" style="display: none">
						<div> <span
								class="text-right">Rev No.</span> <span
								class="text-left padding-left-0 color-red">${rid}</span></div>
						<div class="">
							<span class="text-right">A/C Reg No.</span> <span
								class=" text-left padding-left-0 color-red">${aircode}</span></div>
						</div> 
						<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
						
						<div class="sidebar-toggler hidden-phone"></div> <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
						<div class="clearfix"></div>
					</li>
					<!---首页---->
					<li class="start" id="homePage">
					<a href="${ctx}/demo/revisionlist"> <i
							class="fa fa-home pull-left "
							style="margin-top: 7px; font-size: 17px;"></i> 
							<span class="title pull-left" id="homePageMenu">
								<div class="font-size-12 ">首页</div>
								<div class="font-size-9 ">Home</div>
						   </span> 
						  <span class="selected"></span>
						  <div class="clearfix"></div>
					</a></li>
					<!---工单管理--->
					<li class="" id="workOrderPage" style="display: none;">
					 <a href="javascript:;"> <i
							class="fa fa-cogs pull-left"
							style="margin-top: 7px; font-size: 17px;"></i> <span
							class="title pull-left">
								<div class="font-size-12 ">工单管理</div>
								<div class="font-size-9 ">Work Order Management</div>
						</span> <span class="selected"> </span> <span class="arrow open">
						</span>
							<div class="clearfix"></div>
					</a>
						<ul class="sub-menu">
							<li id="workOrderSubMenuOrderList"><a href="${ctx}/main/work/${rid}/listpage">
									<div class="font-size-12 ">工单列表</div>
									<div class="font-size-9 ">Work Order List</div>
									<span class="selected"> </span> 
							    </a>
							</li>
							<li id="workOrderbyday" style="display: none;"><a href="${ctx}/main/work/${rid}/workorderbyday">
									<div class="font-size-12 ">工单列表 By Day</div>
									<div class="font-size-9 ">Work Order List By Day</div>
									<span class="selected"> </span> 
							</a></li>

						</ul>
						</li>
					<!---批量处理,批量处理需要关联revision,修改时间:2015,1,1---->
					<li id="batchProcessingPage" style="display: none;">
					<a href="${ctx}/main/groupchange/${rid}/home"> <i
							class="fa fa-tags pull-left "
							style="margin-top: 7px; font-size: 17px;"></i> <span
							class="title pull-left">
								<div class="font-size-12 ">批量处理</div>
								<div class="font-size-9 ">Batch Processing</div>
						</span>
						  <span class="selected"></span>
							<div class="clearfix"></div>
					</a></li>
			<%-- 		<!---统计报表--->
					<li id="statisticalReportsPage" style="display: none"><a href="${ctx}/main/report/showreport"> <i
							class="fa fa-bar-chart-o pull-left"
							style="margin-top: 7px; font-size: 17px;"></i> <span
							class="title pull-left">
								<div class="font-size-12 ">统计报表</div>
								<div class="font-size-9 ">Statistical Reports</div>
						</span>
						   <span class="selected"></span>
							<div class="clearfix"></div>
					</a></li> --%>
					<!---技术文件管理--->
					<li class="" id="technicalDocumentsPage" style="display: none;">
						 <a href="javascript:;"> <i
							class="fa fa-folder-open pull-left"
							style="margin-top: 7px; font-size: 17px;"></i> <span
							class="title pull-left">
								<div class="font-size-12 ">技术文件管理</div>
								<div class="font-size-9 ">TCF</div>
						</span> <span class="selected"> </span> <span class="arrow open">
						</span>
							<div class="clearfix"></div>
						</a> 
						<ul class="sub-menu">
							<li id="technicalDocumentsUploadList"><a href="${ctx}/project/technicalfile/mainupload">
									<div class="font-size-12 ">技术文件上传</div>
									<div class="font-size-9 ">Technical Files Upload</div>
									<span class="selected"> </span> 
							    </a>
							</li>
							      
							      
							<li id="technicalDocumentsAssessList" ><a href="${ctx}/project/technicalfile/mainassess">
									<div class="font-size-12 ">技术文件评估</div>
									<div class="font-size-9 ">Technical Files Assess</div>
									<span class="selected"> </span> 
							</a>
							</li>
		
							<li id="technicalDocumentsAuditList" ><a href="${ctx}/project/technicalfile/mainupload">
									<div class="font-size-12 ">技术文件审核 </div>
									<div class="font-size-9 ">Technical Files Audit</div>
									<span class="selected"> </span> 
							</a>
							</li>
							
							<li id="technicalDocumentsApprovalList"><a href="${ctx}/project/technicalfile/mainupload">
									<div class="font-size-12 ">技术文件审批</div>
									<div class="font-size-9 ">Technical Files Approval</div>
									<span class="selected"> </span> 
							</a>
							</li>
						</ul>
					</li>
					
						
 				<!---工卡数据库管理--->
					<li class="" id="dbManageMenu" style="display: none"><a href="javascript:;"> <i
							class="fa fa-puzzle-piece pull-left"
							style="margin-top: 7px; font-size: 17px;"></i> <span
							class="title pull-left">
								<div class="font-size-12 ">系统管理</div>
								<div class="font-size-9 ">System Management</div>
						</span> <span class="selected"> </span> <span class="arrow open">
						</span>
							<div class="clearfix"></div>
					</a>
						<ul class="sub-menu">
							<li class="" id="taskListMenu" style="display: none"><a href="${ctx}/sys/user/main">
									<div class="font-size-12 ">用户管理</div>
									<div class="font-size-9 ">Job card maintenance</div>
							</a></li>
								<!---菜单管理--->
					<li id="MenuPage" style="display: block"><a href="${ctx}/sys/menu/main"> 
								<div class="font-size-12 ">菜单管理</div>
								<div class="font-size-9 ">Main Management</div>
					</a></li>
					<!-- 按钮管理 -->
					<li id="ButtonPage" style="display: block"><a href="${ctx}/sys/button/main">
								<div class="font-size-12 ">按钮管理</div>
								<div class="font-size-9 ">Button Management</div>
					</a></li>
							<!---角色管理--->
					<li id="roleMainPage" style="display: none"><a href="${ctx}/sys/role/main">
								<div class="font-size-12 ">角色管理</div>
								<div class="font-size-9 ">Role Management</div>
					</a></li>
							
					</ul></li>
						 
					<!---维修方案管理--->
					<li class="" id="maintenance" style="display: none;">
						 <a href="javascript:;"> <i
							class="fa fa-folder-open pull-left"
							style="margin-top: 7px; font-size: 17px;"></i> <span
							class="title pull-left">
								<div class="font-size-12 ">维修方案管理</div>
								<div class="font-size-9 ">Maintenance Management</div>
						</span> <span class="selected"> </span> <span class="arrow open">
						</span>
							<div class="clearfix"></div>
						</a> 
						
						<ul class="sub-menu">
							<li id="planemodeldata" >
								<a href="${ctx}/project/planemodeldata/main">
									<div class="font-size-12 ">机型设置</div>
									<div class="font-size-9 ">Model Set</div>
									<span class="selected"> </span> 
								</a>
							</li>
							
						    <li id="fixchapter">
								<a href="${ctx}/project/fixchapter/main" >
									<div class="font-size-12 ">ATA章节</div>
									<div class="font-size-9 ">ATA FixChapter</div>
									<span class="selected"> </span>
								</a>
							</li>
							<li id="maintenanceList" style="display:">
								<a href="${ctx}/project/maintenance/main" >
									<div class="font-size-12 ">维修方案列表</div>
									<div class="font-size-9 ">Maintenance List</div>
									<span class="selected"> </span>
								</a>
							</li>
							<li id="fixedcheckitemList" style="display:">
								<a href="${ctx}/project/fixedcheckitem/main" >
									<div class="font-size-12 ">定检项目列表</div>
									<div class="font-size-9 ">Fixed Check Item List</div>
									<span class="selected"> </span>
								</a>
							</li>
						</ul>
					</li> 
						<li class="" id="spentHourMenu" style="display: block">
							<a href="javascript:;">
								<i class="fa  fa-user pull-left" style="margin-top: 7px; font-size: 17px;"></i>
								<span class="title pull-left">
									<div class="font-size-12 ">工程管理</div>
									<div class="font-size-9 ">GongChen GuanLi</div>
								</span>
								<span class="selected"></span> 
								<span class="arrow open"></span>
								<div class="clearfix"></div>
							</a>
							<ul class="sub-menu">
								<li id="AnnunciateMenu" style="display:">
									<a href="${ctx}/project/annunciate/main" >
										<div class="font-size-12 ">维护提示</div>
										<div class="font-size-9 ">JiShu TongGao</div>
										<span class="selected"> </span>
									</a>
								</li>
								<li id="AnnunciateMenu" style="display:">
									<a href="${ctx}/project/instruction/main" >
										<div class="font-size-12 ">技术指令</div>
										<div class="font-size-9 ">JiShu ZhiLing</div>
										<span class="selected"> </span>
									</a>
								</li>
								<li id="revisionnoticebook" style="display:">
									<a href="${ctx}/project/revisionNoticeBook/main" >
										<div class="font-size-12 ">修订通知书</div>
										<div class="font-size-9 ">XDTZS</div>
										<span class="selected"> </span>
									</a>
								</li>
							</ul>
						</li> 
						
						<li class="" id="treeMenu" style="display: block">
							<a href="javascript:;">
								<i class="fa  fa-user pull-left" style="margin-top: 7px; font-size: 17px;"></i>
								<span class="title pull-left">
									<div class="font-size-12 ">树形菜单</div>
									<div class="font-size-9 ">--</div>
								</span>
								<span class="selected"></span> 
								<span class="arrow open"></span>
								<div class="clearfix"></div>
							</a>
							<ul class="sub-menu">
								 <ul id="zTree" class="ztree"></ul>
							</ul>
						</li> 
						
						<c:forEach items="${menuModuleList}" var="module">
							<div id="${module.id}" title="${module.menuName}" style="padding:0 10px; height:35px;" >
								<table id="menu_tree_${module.id}" ></table>
							</div>
						</c:forEach>
						
						 
					<!-- 裴秀的UI测试界面	 -->
				</ul>
				<!-- END SIDEBAR MENU -->
			</div>
		</div>

		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<sitemesh:body />
		</div>
		<!-- END CONTENT -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<div class="modal fade" id="changePassword" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<div class="font-size-16 line-height-18">修改密码</div>
				<div class="font-size-9 ">Change Password</div>
					</div>
					<div class="modal-body" >
					 <div class="col-sm-12 col-xs-12" style="margin-top:10px;">
							 <span class="col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div class="font-size-12 " >用户名</div><div class="font-size-9 line-height-18" >name</div></span><div class="col-sm-8 col-xs-8">
							 <input type="text" class="form-control" value="${sessionScope.user.username}" readonly="readonly"/>
							</div> </div> 
							<div class="col-sm-12 col-xs-12" style="margin-top:10px;">
							 <span class="col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div class="font-size-12 " >原密码</div><div class="font-size-9 line-height-18" >old password</div></span><div class="col-sm-8 col-xs-8">
							 <input type="password" class="form-control" id="oldPassword"/>
							</div> </div> 
							<div class="col-sm-12 col-xs-12" style="margin-top:10px;">
							 <span class="col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div class="font-size-12 " >新密码</div><div class="font-size-9 line-height-18" >new password</div></span><div class="col-sm-8 col-xs-8">
							 <input type="password" class="form-control" id="newPassword"/>
							</div> </div>
                            <div class="col-sm-12 col-xs-12" style="margin-top:10px;">
							 <span class="col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div class="font-size-12 " >确认新密码</div><div class="font-size-9 line-height-18" >confirm password </div></span><div class="col-sm-8 col-xs-8">
							 <input type="password" class="form-control" id="confirmPassword"/>
							</div> </div> 	
							<div id="changeErrorInfo"></div>						
							<div class="clearfix"></div>
					</div>
					<div class="modal-footer">
						<!--<button type="button" class="btn btn-default">Save changes
							</button>-->
						<button type="button" onclick="editPassword();" class="btn btn-primary padding-top-1 padding-bottom-1" >
							<div class="font-size-16 line-height-18">修改</div>
				<div class="font-size-9 ">edit</div></button>
				<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
							<div class="font-size-16 line-height-18">关闭</div>
				<div class="font-size-9 ">Close</div></button>
					</div>
				</div>
			</div>
		</div>
	<div class="footer">
		<div class="footer-inner"></div>
		<div class="footer-tools">
			<span class="go-top"> <i class="fa fa-angle-up"></i>
			</span>
		</div>
	</div>
	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
   <script src="assets/plugins/respond.min.js"></script>
   <script src="assets/plugins/excanvas.min.js"></script> 
   <![endif]-->
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->

<!-- <script src="${ctx}/static/assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> -->
<script src="${ctx}/static/assets/scripts/core/app.js"></script>

<script type="text/javascript">

	var basePath = '${ctx}';
	var zTree;
	$(document).ready(function() {
		App.init();
		var username="${user.username}";
			$("#statisticalReportsPage").css("display","block");
			$("#roleMainPage").css("display","block");
			$("#technicalDocumentsPage").css("display","block");
			$("#revisionManagementPage").css("display","block");
			$("#revisionManagementSubMenuControl").css("display","block");
			$("#dbManageMenu").css("display","block");
			$("#taskListMenu").css("display","block");
			$("#accessListMenu").css("display","block");
			$("#configurationManagementPage").css("display","block");
			$("#deptTradeConfiguration").css("display","block");
			$("#miConfiguration").css("display","block");
			$("#empTradeConfiguration").css("display","block");
			$("#hangarMaintenance").css("display","block");
			$("#contractmanagementsubmenu").css("display","block");
			$("#revisionSettop").css("display","block");
			$("#importManagementPage").css("display","block");
			$("#imptFilesMenu").css("display","block");
			$("#failedDataMenu").css("display","block");
			$("#userMenu").css("display","block");
			$("#userlistMenu").css("display","block");
			$("#spentHourMenu").css("display","block");
			$("#dailyCardMenu").css("display","block");	
			$("#personalTimingMenu").css("display","block");	
			$("#loginBatchBookOnOffMenu").css("display","block");
			$("#maintenance").css("display","block");
			
			
			var setting = {  
					
					 data: {
						   key: {
								name: "name"
						   },
						   simpleData: {
							   enable: true,
							   idKey: "id",
							   pIdKey: "pId",
							   rootPId: 0
						   }
					   },

			        isSimpleData : true,              //数据是否采用简单 Array 格式，默认false  
			        treeNodeKey : "id",               //在isSimpleData格式下，当前节点id属性  
			        treeNodeParentKey : "pId",        //在isSimpleData格式下，当前节点的父节点id属性  
			        showLine : true,                  //是否显示节点间的连线  
			        checkable : true                  //每个节点上是否显示 CheckBox  
			    };  
			
			var treeNodes = [   
			                 {"id":1, "pId":0, "name":"test1"},   
			                 {"id":11, "pId":1, "name":"test11"},   
			                 {"id":12, "pId":1, "name":"test12"},   
			                 {"id":111, "pId":11, "name":"test111"},   
			             ]; 
			zTree = $.fn.zTree.init($("#zTree"), setting, treeNodes);
			 
	});
	function showChangePassword(){
	   $("#changePassword").modal("show");
	   $("#changeErrorInfo").html("");
	   $("#oldPassword").val("");
	   $("#newPassword").val("");
	   $("#confirmPassword").val("");
	}
	function editPassword(){
		var oldPassword=$.trim($("#oldPassword").val());
		var newPassword=$.trim($("#newPassword").val());
		var confirmPassword=$.trim($("#confirmPassword").val());
		$("#changeErrorInfo").html("");
		if(oldPassword == "" || newPassword == "" || confirmPassword == ""){
			$("#changeErrorInfo").html("<font color='red'>原密码,新密码或确认密码不能为空.</font>");
			return false;
		}
		if (oldPassword == newPassword){
			$("#changeErrorInfo").html("<font color='red'>新密码与原密码不能相同.</font>");
			return false;
		}
		if (newPassword != confirmPassword){
			$("#changeErrorInfo").html("<font color='red'>新密码与确认密码不一致.</font>");
			return false;
		}
		
	    var obj={};
		obj.name = '${sessionScope.user}';
		obj.pass=oldPassword;
		obj.confirmpass=confirmPassword;
		startWait();
		$.ajax({
			   url:"${ctx}/user/authen/changepass",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				   finishWait();
				   if(data.status=="Y"){
					  //alert("修改密码成功.");
					   $("#changePassword").modal("hide");
				   } else {
					    $("#changeErrorInfo").html("<font color='red'>原始密码错误.</font>");
						return false;
				   }
  	          },
  	          error:function(){
  	        	    finishWait();
  	          }
		    });
	}
	
	function logout(){
		sessionStorage.removeItem("loginName");
		window.location.href = "${ctx}/user/authen/logout";
	}
	
	function checkAuth(str){
		if(str==null || str==""){
			return true;
		}
		var authTotal=$("#authTotal").val();
		if(authTotal==null || authTotal==""){
			return false;
		}
		var auths=authTotal.split(",");
		for(var i=0;i<auths.length;i++){
			if(str==auths[i]){
				return true;
			}
		}
		return false;
	}
	 
</script>
</html>