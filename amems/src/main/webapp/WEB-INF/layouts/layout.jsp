<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="auths" value="${sessionScope.auths}" />
<c:set var="logonuser" value="${sessionScope.user}" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> 
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<link rel="shortcut icon" href="${ctx}/static/assets/img/eray_ico.ico" />
<title id='bodyTitle'><sitemesh:title default="维修管理系统"></sitemesh:title></title>


<link
	href="${ctx}/static/assets/plugins/font-awesome-4.7.0/font-awesome-4.7.0/css/font-awesome.css"
	rel="stylesheet" type="text/css" /> 
<link
	href="${ctx}/static/assets/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${ctx}/static/assets/plugins/icomoon/style.css"
	rel="stylesheet" type="text/css" /> 

<link
	href="${ctx}/static/assets/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />

<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<!---<link href="${ctx}/static/assets/css/style-metronic.css"
	rel="stylesheet" type="text/css" />--->
<link href="${ctx}/static/assets/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/assets/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/assets/css/plugins.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/assets/css/themes/light.css" rel="stylesheet"	type="text/css" id="style_color" />
<link href="${ctx}/static/assets/css/custom.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}/static/assets/css/pbs.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}/static/assets/css/english.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}/static/assets/css/highslide.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}/static/assets/plugins/bootstrap-multiselect/bootstrap-multiselect.css"	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/assets/plugins/data-tables/DT_bootstrap.css"	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/assets/plugins/bootstrap-datepicker/css/datepicker.css"	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/assets/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css"	rel="stylesheet" />
<link rel="stylesheet" type="text/css"	href="${ctx}/static/assets/plugins/jstree/dist/themes/default/style.min.css" />
<link rel="stylesheet" type="text/css"	href="${ctx}/static/assets/plugins/bootstrap-datetimepicker/jquery.datetimepicker.css" />
<%-- <link rel="stylesheet" href="${ctx}/static/assets/plugins/tree/awesomeStyle/awesome.css" type="text/css"> --%>
<link rel="stylesheet"	href="${ctx}/static/assets/plugins/tree/zTreeStyle/zTreeStyle.css"	type="text/css">
<link rel="stylesheet"	href="${ctx}/static/js/bootstrapvalidator-master/css/bootstrapValidator.min.css"	type="text/css">
<link rel="stylesheet"	href="${ctx}/static/js/mcustomscrollbar/jquery.mCustomScrollbar.min.css"	type="text/css">
<!--左右移样式  -->
<link href="${ctx}/static/css/prettify.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}/static/css/bootstrap-duallistbox.css"	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/lib/sticky/component.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}/static/lib/webui-popover/jquery.webui-popover.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/assets/css/stylenew.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx}/static/assets/plugins/jquery-1.10.2.min.js"	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/scrolltext.js"	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/Chart.js"	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/jquery-migrate-1.2.1.min.js"	type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script	src="${ctx}/static/assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js"	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/bootstrap/js/bootstrap.min.js"	type="text/javascript"></script>
<script	src="${ctx}/static/assets/plugins/bootstrap/js/jquery.bootstrap.teninedialog.v3.min.js"	type="text/javascript"></script>
<!--  参考http://www.xnwai.com/teninedialog/    jquery.bootstrap.teninedialog.js使用说明 -->
<script	src="${ctx}/static/assets/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/jquery.blockui.min.js"	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/jquery.cokie.min.js"	type="text/javascript"></script>
<script	src="${ctx}/static/assets/plugins/bootstrap-multiselect/bootstrap-multiselect.js"	type="text/javascript"></script>
<script	src="${ctx}/static/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"	type="text/javascript"></script>
<script	src="${ctx}/static/assets/plugins/bootstrap-daterangepicker/moment.js"></script>
<script	src="${ctx}/static/assets/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="${ctx}/static/js/common.js"></script>
<script src="${ctx}/static/assets/plugins/jstree/dist/jstree.min.js"></script>
<script type="text/javascript"src="${ctx}/static/js/jquery.form.js"></script>
<script src="${ctx}/static/js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
<script	src="${ctx}/static/assets/plugins/bootstrap-datetimepicker/jquery.datetimepicker.js"></script>
<script	src="${ctx}/static/assets/plugins/tree/js/jquery.ztree.core-3.5.js"></script>
<!-- 树标签 依赖 -->
<script	src="${ctx}/static/assets/plugins/tree/js/jquery.ztree.excheck-3.5.js"></script>
<!-- 树标签 依赖 -->
<script	src="${ctx}/static/assets/plugins/tree/js/jquery.ztree.exedit-3.5.js"></script>
<script src="${ctx}/static/dialog/bootstrapQ.min.js"></script>
<script src="${ctx}/static/dialog/qiao.js"></script>
<script src="${ctx}/static/js/bootstrapvalidator-master/js/bootstrapValidator.min.js"></script>
<script src="${ctx}/static/js/bootstraptypeahead-master/bootstrap3-typeahead.min.js"></script>
<script src="${ctx}/static/js/mcustomscrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${ctx}/static/assets/plugins/jquery.ba-throttle-debounce.min.js"></script>
<script type="text/javascript"src="${ctx}/static/lib/sticky/jquery.stickyheader.js"></script>
<script type="text/javascript"src="${ctx}/static/lib/base64/base64.min.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/webui-popover/jquery.webui-popover.min.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/maskedinput/jquery.maskedinput.js"></script>
<link rel="stylesheet"	href="${ctx}/static/assets/plugins/bootstrap-fileinput/bootstrap-fileinput.css"	type="text/css">
<script	src="${ctx}/static/assets/plugins/bootstrap-fileinput/bootstrap-fileinput.js"></script>
<script	src="${ctx}/static/assets/plugins/bootstrap-fileinput/zh.js"></script>
<link rel="stylesheet"	href="${ctx}/static/assets/plugins/bootstrap-select/bootstrap-select.min.css"	type="text/css">
<script	src="${ctx}/static/assets/plugins/bootstrap-select/bootstrap-select.min.js"></script>
<script	src="${ctx}/static/assets/plugins/bootstrap-select/defaults-zh_CN.min.js"></script>

<!-- END CORE PLUGINS -->

<!-- END THEME STYLES -->
<!-- <link rel="shortcut icon" href="favicon.ico" /> -->
<style>
#bookmarkUl{
list-style:none;
padding-left:0px;
border-top:1px solid #d5d5d5;
}
#bookmarkUl li{
border:1px solid #d5d5d5;
border-top:0px;
}
#bookmarkUl li a{
display:block;
cursor:pointer;
}
#bookmarkUl li a:hover{
background:#c5dcf9;
}
.bookmarkli:hover{
border:1px solid red;
}
.dropdialoghover{
/*  border:1px solid #127cc3;
background:#46a7e6; */
}
/* .iconstar {
    color: #fff;
    font-size: 16px;
    height: 38px;
    margin-right: 5px;
    width: 24px;
    display: block;
    overflow: hidden;
    text-align: center;
} */
/* .iconbook{ color:#fff; font-size: 16px;
    height: 38px;  width: 24px; text-align: center;overflow: hidden;
    display: block;}   */

.dropdialoghover_right{
/*  border:1px solid #127cc3;
background:#ccc; */
border-left:0px;
}
.dropdialoghoveractive{
/* border:1px solid #127cc3;
background:#46a7e6; 
padding-top:5px;
padding-bottom:5px;*/
color:yellow !important;
}
.dropdialoghover_rightactive{
/* border:1px solid #127cc3;
background:#46a7e6; */
border-left:0px;
}

.dropdialogbookmarkhoveractive{
/* border:1px solid #127cc3;
background:#46a7e6; */

}
.dropdialogbookmarkhover_rightactive{
/* border:1px solid #127cc3;
background:#46a7e6; */
border-left:0px;
color:yellow !important;
}
#bookmarkUl li label{
color:#333;
height:23px;
line-height:23px;
border-right:1px solid #d5d5d5;
padding-left:8px;
font-weight:normal;
}
#bookmarkUl li label:last-child{
border-right:0px;
}
.colwidth-14{
width:140px;
}
.colwidth-35{
width:350px;
}
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

.hover_p:hover{
text-decoration:underline;
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
		to(#eee));
	background-image: -webkit-linear-gradient(top, #fff, 0%, #eee, 100%);
	background-image: -moz-linear-gradient(top, #fff 0, #eee 100%);
	background-image: linear-gradient(to bottom, #fff 0, #eee 100%);
	background-repeat: repeat-x;
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff',
		endColorstr='#ffeeeeee', GradientType=0);
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
		to(#aeaeae));
	background-image: -webkit-linear-gradient(top, #d7d7d7, 0%, #aeaeae, 100%);
	background-image: -moz-linear-gradient(top, #d7d7d7 0, #aeaeae 100%);
	background-image: linear-gradient(to bottom, #d7d7d7 0, #aeaeae 100%);
	background-repeat: repeat-x;
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#d7d7d7',
		endColorstr='#aeaeae', GradientType=0);
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
.favicon{
padding-left:5px !important;
padding-right:5px !important;
}
.favicon i{
color:white;
}
.favicon:link{
background:none !important;
outline:none;
}
.favicon:visited,.favicon:hover,.favicon:active{
background:#4f4f4f !important;
}
.dropdialog-div {
    position: relative;
    width: 24px;
    height: 38px;
}
.dropdown-dialog {
    position: absolute;
    top: 70%;
    right:142px !important;
    z-index: 1000;
    display: none;
    float: left;
    min-width: 200px;
    padding: 6px 0 14px 0;
    margin: 2px 0 0;
    font-size: 14px;
    text-align: left;
    list-style: none;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid rgba(0, 0, 0, .15);
    border-radius: 4px;
    box-shadow: 0 6px 12px rgba(0, 0, 0, .175);
}
	
 .dropdown-dialogBook {
    position: absolute;
    top: 70%;
    right:120px !important;
    z-index: 1000;
    display: none;
    float: left;
    min-width: 200px;
    padding: 6px 0 14px 0;
    margin: 2px 0 0;
    font-size: 14px;
    text-align: left;
    list-style: none;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid rgba(0, 0, 0, .15);
    border-radius: 4px;
    box-shadow: 0 6px 12px rgba(0, 0, 0, .175);
}
	
	.dropdown-dialog p{margin-bottom:6px !important;}
	
	.dropdown-dialog label,.dropdown-dialog p,.dropdown-dialogBook label{
	font-weight:normal;
	padding-left:10px;
	color:black;
	padding-top:0px;
	height:25px; margin: 0;
	line-height:25px;
	}
	.dropdown-dialogBook p{
	font-weight:normal;
	color:black;
	padding-top:0px;
	height:24px;
	line-height:22px;
	margin-top:0px;
	font-size:12px;
	margin-bottom:0px;
	}
.dropdown-dialogBook p a {
    padding-left: 10px;
    color: black;
    display: block;
    width: 193px !important;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    text-decoration: none;
    text-align: left;
    height: 20px;
    line-height: 20px;
    font-size: 12px;
}
	.dropdown-dialogBook p a:hover{
	background:#f5f5f5;    text-decoration: none;
	}
	.dropdown-dialog.pull-right,.dropdown-dialogBook.pull-right {
  right: 0;
  left: auto;
}
.dropdown-dialog:before,.dropdown-dialogBook:before {
	position: absolute;
	top: -7px;
	right: 4px;
	display: inline-block !important;
	border-right: 7px solid transparent;
	border-bottom: 7px solid #ccc;
	border-left: 7px solid transparent;
	border-bottom-color: rgba(0, 0, 0, 0.2);
	content: '';
}

.dropdown-dialog:after,.dropdown-dialogBook:after {
	position: absolute;
	top: -6px;
	right: 4px;
	display: inline-block !important;
	border-right: 6px solid transparent;
	border-bottom: 6px solid #fff;
	border-left: 6px solid transparent;
	content: '';
}
.dropdown-dialog  p.favnull{
font-size:12px;
color:red;
height:12px;
line-height:12px;
}
#showHideIcon{
	cursor:pointer;
	margin-top:10px;
	width:12px;
	height:12px;
	line-height:6px;
	font-size:12px;
	font-weight:bold;
	padding:0px;
	border:1px solid black;
	text-align:center;
}




</style>
<sitemesh:head></sitemesh:head>
<script src="${ctx}/static/security.js"></script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<input type="hidden" id="authTotal" value="" />
	<!-- BEGIN HEADER -->
	<div class="header navbar navbar-fixed-top">
		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="header-inner">
			
<%-- 			<a class="navbar-brand" href="javascript:void(0);"> <img
				src="${ctx}/static/assets/img/logo_03.png" alt="logo" />
			</a>
			<div class='pull-right' >
			<div style='float:left;vertical-align:middle;height:46px;line-height:46px;margin-right:10px;'>
			 <div class="dropdialog-div pull-left">
			   <i class="icon-star cursor-pointer" data-toggle="dropdialog" style='color:white;font-size:16px;padding-left:5px;padding-right:5px;border-right:1px solid #127cc3;'></i>
			   <div  class="dropdown-dialog pull-right">
					<p ><i class="icon-star cursor-pointer" style='font-size:20px;'></i><label>编辑此书签</label></p>
					<p ><span class='pull-left' style='font-size:12px;'>名称(N)：</span><input class='form-control' type='text' style='width:120px;height:25px;font-size:12px;' id='favName' maxlength="30"/></p>
					<p class='favnull' style='display:none;'>*名称不能为空</p>
					<div class='text-right' style='padding-right:10px;height:20px;line-height:20px;'>
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 "  onclick="addOrUpdate()">
						<div class="font-size-12">完成</div>
			   	    </button>
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="deleteShortcut()">
						<div class="font-size-12">移除此书签</div>
					</button>
			      </div>
				</div>
			 </div>
			 <div class="dropdialog-div pull-left"  >
			    <i class="icon-book cursor-pointer " data-toggle="dropdialogBook" style='color:white;font-size:16px;padding-left:6px;padding-right:5px;' ></i>
			    <div  class="dropdown-dialogBook pull-right">
			        <p style='padding-left:8px;border-bottom:1px solid #d5d5d5;cursor:pointer;' class='hover_p' onclick='showBookMark(event)'><i class='icon-cog' style='font-size:12px;'></i> 书签管理&nbsp;&nbsp;</p>
			         <p style='padding-left:8px;color:#999;'>最近的书签&nbsp;&nbsp;</p>
			    	<div id='bookmarksite'>
			    	</div>
			     </div>
			 </div>
			</div>
			<ul class="nav navbar-nav pull-left">
			  
				<li><i class="icon-sitemap"></i>所属组织 Organization：<em><script>document.write(AccessDepartmentUtil.getDpartName('${sessionScope.user.jgdm}'));</script></em><em>&nbsp;&nbsp;&nbsp;&nbsp;${sessionScope.user.department.dprtname}</em></li>
				<li><i class="icon-sitemap"></i>所属部门：<em>${sessionScope.user.department.dprtname}</em></li>
				<li class="dropdown user"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" data-hover="dropdown"
					data-close-others="true"> <i class="icon-group"></i><span
						class="username" id='userinfoMessage' title="${sessionScope.user.displayName}"> ${sessionScope.user.displayName}</span> <i
						class="icon-angle-down"></i>
						<input type="hidden" id="dhusername" value="${sessionScope.user.username}"/>
						<input type="hidden" id="dhrealname" value="${sessionScope.user.realname}"/>				
				</a>
					<ul class="dropdown-menu pull-right">
						<li><a data-toggle="modal"
							onclick="showChangeInfo();" class='cursor-pointer'> <i class="icon-edit"></i>Modify Personal Info
						</a></li>
						<li><a href="javascript:void(0);" onclick="logout();"> <i
								class="icon-key"></i> Log Out
						</a></li>
						<li><a href="javascript:void(0);" onclick="lookPDF();"> <i
								class="icon-info-sign"></i> Help
						</a></li>
					</ul></li>
				<!-- END USER LOGIN DROPDOWN -->
			</ul>
			</div>
			<div class="clearfix"></div>  --%>
			
			
			<div class="navbar-brand pull-left">
			<a id="logo_btn" hasInex='0' href="javascript:void(0);"> <img src="${ctx}/static/login/${customer}/images/layoutlogo${forTest}.png" alt="logo" /></a>
			</div>
			<div id="collspanLeftMenuBtn" class="zdie pull-left">
				<a href="javascript:void(0);" onclick="collspanLeftMenu();"> 
					<i id="collspanLeftMenuBtnI" class="icon-caret-left"></i>
				</a>
			</div>
			<ul id="pageHeaderMenuUL" class=" header-down pull-left ">
				<c:forEach items="${userMenuList}" var="module">
					<c:choose>
						<c:when test="${module.menuType=='1'}">
							<a id="menu_${module.id}" menuId="${module.id}" menuCode="${module.menuCode}" menuType="1" href="javascript:void(0);"
								onclick="loadLeftMenu('${module.id}');">
								<p class="font-size-12" id="menu_cn_${module.id}">${module.menuName}</p>
								<p class="font-size-9" id="menu_fn_${module.id}">${module.menuFname}</p>
							</a>
						</c:when>
						<c:otherwise>
							<a id="menu_${module.id}" menuId="${module.id}" menuCode="${module.menuCode}"  menuType="2" href="${ctx}/${module.path}">
								<p class="font-size-12" id="menu_cn_${module.id}">${module.menuName}</p>
								<p class="font-size-9" id="menu_fn_${module.id}">${module.menuFname}</p>
							</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
			
			<div class='pull-right' >
			<div  class="icon_shot" >
			 <a>
			   <i class="icon-star cursor-pointer " data-toggle="dropdialog" style='color:white;font-size:16px;'></i>
			   <div  class="dropdown-dialog pull-right">
					<p ><i class=" cursor-pointer" style='font-size:15px;'></i><label style='padding-left:0;'>编辑此书签</label></p>
					<p ><span class='pull-left' style='font-size:12px;'>名称(N)：</span><input class='form-control' type='text' style='width:120px;height:25px;font-size:12px;margin:0; padding:0;' id='favName' maxlength="30"/></p>
					<p class='favnull' style='display:none;'>*名称不能为空</p>
					<div class='text-right' style='padding-right:10px;height:20px;line-height:20px;'>
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 "  onclick="addOrUpdate()">
						<div class="font-size-12">完成</div>
			   	    </button>
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="deleteShortcut()">
						<div class="font-size-12">移除此书签</div>
					</button>
			      </div>
				</div>
			 </a>
			 <a >
			    <i class="icon-book cursor-pointer  " data-toggle="dropdialogBook" style='color:white;font-size:16px;' ></i>
			    <div  class="dropdown-dialogBook pull-right">
			         <p style='padding-left:8px;border-bottom:1px solid #d5d5d5;cursor:pointer;' class='hover_p' onclick='showBookMark(event)'><i class='icon-cog' style='font-size:12px;'></i> 书签管理&nbsp;&nbsp;</p>
			         <p style='padding-left:8px;color:#999;'>最近的书签&nbsp;&nbsp;</p>
			    	<div id='bookmarksite'>
			    	</div>
			     </div>
			 </a>
			</div>
			<ul class="nav navbar-nav pull-left">
<%-- 				<li><i class="icon-sitemap"></i>所属组织 Organization：<em><script>document.write(AccessDepartmentUtil.getDpartName('${sessionScope.user.jgdm}'));</script></em><em>&nbsp;&nbsp;&nbsp;&nbsp;${sessionScope.user.department.dprtname}</em></li>
				<li><i class="icon-sitemap"></i>所属部门：<em>${sessionScope.user.department.dprtname}</em></li> --%>
				<li class="dropdown user">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"data-close-others="true"> 
					<i class="icon-user"></i>
					<span class="username" id='userinfoMessage' title="${sessionScope.user.displayName}"> ${sessionScope.user.displayName}</span>
					 <i class="icon-angle-down"></i>
						<input type="hidden" id="dhusername" value="${sessionScope.user.username}"/>
						<input type="hidden" id="dhrealname" value="${sessionScope.user.realname}"/>	
						
						<input type="hidden" id="osmsRemoteIP" value="${osmsRemoteIP}"/>	
						<input type="hidden" id="clientIP" value="${clientIP}"/>			
				</a>
					<ul class="dropdown-menu pull-right">
				     	<li class="line1"><span><script>document.write(AccessDepartmentUtil.getDpartName('${sessionScope.user.jgdm}'));</script></span></li>
						<li><a data-toggle="modal"
							onclick="showChangeInfo();" class='cursor-pointer'> <i class="icon-edit"></i>Modify Personal Info
						</a></li>
						<li><a href="javascript:void(0);" onclick="logout();"> <i
								class="icon-key"></i> Log Out
						</a></li>
						<li><a href="javascript:void(0);" onclick="updateLog();"> <i
								class="icon-list"></i> Update Log
						</a></li>
						<li><a href="javascript:void(0);" onclick="lookPDF();"> <i
								class="icon-info-sign"></i> Help
						</a></li>
					</ul></li>
				<!-- END USER LOGIN DROPDOWN -->
			</ul>
			</div>		
			<!-- END TOP NAVIGATION MENU -->
		</div>
	</div>
	<!-- END HEADER -->
	<div class="clearfix"></div>


	<!-- BEGIN CONTAINER -->
	<div class="page-container" >
		<!-- BEGIN SIDEBAR -->
		<!-- BEGIN SIDEBAR -->
		<div class="display-none sidebar-toggler-wrapper">
			<div id="pageLeftMenuCollapse" class="sidebar-toggler hidden-phone"></div>
			<div class="clearfix"></div>
		</div>
		<div class="page-sidebar-wrapper">
			<div class="page-sidebar navbar-collapse collapse "
				style="position: absolute; bottom: 31px; top: 0px;">
				<ul id="pageLeftMenuUL" class="page-sidebar-menu "
					data-auto-scroll="true" data-slide-speed="200">
				</ul>
			</div>
			<div class="foot" id="copyright">
				<div class="loginbm">
					<!-- ©易瑞信息 2016 <a href="http://www.e-ray.com.cn/">e-ray.com.cn</a> -->
				</div>
			</div>
			<div class="foot2" id="copyright2">
				<div class="loginbm2"></div>
			</div>
		</div>

		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper" style="margin-left: 0 !important;">
			<sitemesh:body />
		</div>
		<!-- END CONTENT -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<div class="modal fade modal-new" id="changePassword" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" >
		<div class="modal-dialog" style='width:550px;'>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<div class="font-size-16 line-height-18">修改用户信息</div>
					<div class="font-size-9 ">Modify Personal Info</div>
				</div>
				<div class="modal-body">
				<form id="forminfo" >
				   <%--  <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0" style="margin-top: 10px;">
						<span
							class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 ">用户账号</div>
							<div class="font-size-9 line-height-18">User Account</div></span>
						<div class="col-sm-8 col-xs-8 ">
							<input type="text" class="form-control"
								value="${sessionScope.user.accountName}"  readonly="readonly" />
						</div>
					</div> --%>
					<input type="hidden" id="attId_layout" class="form-control" value="${sessionScope.user.attId}" readonly="readonly" />
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0" style="margin-top: 10px;">
						<span
							class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 ">用户名称</div>
							<div class="font-size-9 line-height-18">User Name</div></span>
						<div class="col-sm-8 col-xs-8">
							<input type="text" class="form-control"
								value="${sessionScope.user.username}" readonly="readonly" />
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" style="margin-top: 10px;">
						<span
							class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 "><span style="color: red">*</span>姓名</div>
							<div class="font-size-9 line-height-18">Name</div></span>
						<div class="col-sm-8 col-xs-8">
							<input type="text" class="form-control"
								value="${sessionScope.user.realname}" maxlength="100" id='realname' name='realname'/>
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<span
							class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 ">固定电话</div>
							<div class="font-size-9 line-height-18">Cellphone</div></span>
						<div class="col-sm-8 col-xs-8">
							<input type="text" class="form-control"
								value="${sessionScope.user.phone}" id='phone' maxlength="20"  name="phone" />
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<span
							class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 ">手机</div>
							<div class="font-size-9 line-height-18">Phone</div></span>
						<div class="col-sm-8 col-xs-8">
							<input type="text" class="form-control"
								value="${sessionScope.user.cellphone}" id='cellphone' maxlength="20"  name="cellphone"/>
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0" >
						<span
							class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 "><span style="color: red">*</span>性别</div>
							<div class="font-size-9 line-height-18">Sex</div></span>
						<div class="col-sm-8 col-xs-8">
							<select  class="form-control"  id='sex'>
								<option  <c:if test="${sessionScope.user.sex==2}">selected='selected'</c:if> value="2">女</option>
								<option  <c:if test="${sessionScope.user.sex==1}">selected='selected'</c:if>  value="1">男</option>
							</select>
						</div>
					</div>
					<div>
					<div class='class="col-sm-12 col-xs-12 padding-left-0 padding-right-0' >
						<div class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0" >
							<div
								class="font-size-12 ">修改密码</div>
							<div class="font-size-9 line-height-18">Edit Password</div>
						</div>
						<div class="col-sm-10 col-xs-8">
							<label onclick='showPassword(this)' title='修改密码' id='showHideIcon' >+</label>
						</div>
					</div>
					</div>
					<div style="display:none;" id='passwordDiv'>
						<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-top: 10px;">
							<span
								class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 "><span style="color: red">*</span>原密码</div>
								<div class="font-size-9 line-height-18">Old Password</div></span>
							<div class="col-sm-10 col-xs-8">
								<input type="password" class="form-control" id="oldPassword" />
							</div>
						</div>
						<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-top: 10px;">
							<span
								class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 "><span style="color: red">*</span>新密码</div>
								<div class="font-size-9 line-height-18">New Password</div></span>
							<div class="col-sm-10 col-xs-8">
								<input type="password" class="form-control" id="newPassword" />
							</div>
						</div>
						<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-top: 10px;">
							<span
								class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 "><span style="color: red">*</span>确认新密码</div>
								<div class="font-size-9 line-height-18">Confirm Pwd</div></span>
							<div class="col-sm-10 col-xs-8">
								<input type="password" class="form-control" id="confirmPassword" />
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					<%--  <div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
						<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">电子签名</div>
							<div class="font-size-9 line-height-18">Electronic Signature</div></span>
						 <div class="col-sm-10 col-xs-8" class="form-group" id="uploadForm" enctype='multipart/form-data'>
			                <div class="fileinput fileinput-new" data-provides="fileinput"  id="exampleInputUpload">
			                    <div class="fileinput-new thumbnail" style="width: 200px;height: auto;max-height:150px;">
			                        <img id='picImg' style="width: 100%;height: auto;max-height: 140px;" src="${ctx}/static/images/noimage.png" alt="" />
			                    </div>
			                    <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 200px; max-height: 150px;"></div>
			                    <div>
			                        <span class="btn btn-primary btn-file">
			                            <span class="fileinput-new" >选择文件</span>
			                            <span class="fileinput-exists">换一张</span>
			                            <input type="file" name="pic1" id="picID" accept="image/jpeg,image/x-png"/>
			                        </span>
			                        <a href="javascript:;" class="btn btn-warning fileinput-exists" data-dismiss="fileinput">移除</a>
			                    </div>
			                </div>
						</div> 
					</div>  --%>
					
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">电子签名</div>
							<div class="font-size-9">Electronic Signature</div>
						</label>
						<div class="col-sm-10 col-xs-8 padding-leftright-8">
							<!-- <input type="file" id="uploadfile" /> -->
							 <input id="file"  name="myfile" type="file" data-show-caption="true" >  
               				 <p class="help-block">支持jpg、png格式，大小不超过50k</p>  
					    </div>
					    
					</div>
					
					<div class="col-sm-12" style="margin-top: 10px;">
						<div id="changeErrorInfo"></div>
					</div>
					<div class="clearfix"></div>
					</form>
				</div>
				<div class="modal-footer" style="text-align: right">
					<!--<button type="button" class="btn btn-default">Save changes
							</button>-->
					<button type="button" onclick="editUserInfo();"
						class="btn btn-primary padding-top-1 padding-bottom-1">
						<div class="font-size-12 line-height-18">保存</div>
						<div class="font-size-9 ">Save</div>
					</button>
					<button type="button"
						class="btn btn-primary padding-top-1 padding-bottom-1"
						data-dismiss="modal" >
						<div class="font-size-12 line-height-18">关闭</div>
						<div class="font-size-9 ">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 用户信息提示框 -->
	<div class="modal fade" id="userinfotipModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" >
		<div class="modal-dialog" style='width:550px;'>
			<div class="modal-content">
				<div class="modal-body" id='userinfotip'>
				</div>
				<div class="modal-footer" style="text-align: right">
					<button type="button"
						class="btn btn-primary padding-top-1 padding-bottom-1"
						data-dismiss="modal" onclick="hideInfoModal()">
						<div class="font-size-12 line-height-18">关闭</div>
						<div class="font-size-9 ">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade modal-new" id="bookmarkModal" tabindex="-1" role="dialog"
		aria-labelledby="bookmarkModal" aria-hidden="true" data-backdrop="static" >
		<div class="modal-dialog" style='width:550px;'>
			<div class="modal-content">
			     <div class='modal-header' style='padding-top:8px;padding-bottom:8px;'>
			     <div class="modal-title" >
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true"></button>
							<label class="font-size-13" style='font-weight:normal;'>书签管理&nbsp;&nbsp;<i class='icon-plus color-blue cursor-pointer' onclick='addbookMarkDialog()' title='添加书签'></i></label>
						</div>
			     </div>
				<div class="modal-body" >
				    <div class='clearfix'></div>
					<ul id='bookmarkUl' style='margin-top:8px;margin-bottom:8px;'>
					
					</ul>
					<div class='clearfix'></div>
				</div>
				<div class="modal-footer" style="text-align: right">
					<button type="button"
						class="btn btn-primary padding-top-1 padding-bottom-1"
						data-dismiss="modal" >
						<div class="font-size-12 line-height-18">关闭</div>
						<div class="font-size-9 ">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="addbookmarkModal" tabindex="-1" role="dialog"
		aria-labelledby="addbookmarkModal" aria-hidden="true" data-backdrop="static" >
		<div class="modal-dialog" style='width:300px;'>
			<div class="modal-content">
			     <div class='modal-header' style='padding-top:8px;padding-bottom:8px;'>
			     <div class="modal-title" >
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true"></button>
							<label class="font-size-13" style='font-weight:normal;'>新增书签</label>
						</div>
			     </div>
				<div class="modal-body" >
				    <p ><label style='font-size:12px;width:65px;'>名称(N)：</label><input type='text' style='width:200px;height:25px;font-size:12px;padding-left:3px;padding-right:3px;border: 1px solid #ccc;' id='favNameoutsite' maxlength="30"/></p>
					<p style='margin-top:8px;'><label style='font-size:12px;width:65px;'>地址(W)：</label><input type='text' style='width:200px;height:25px;font-size:12px;border: 1px solid #ccc;padding-left:3px;padding-right:3px;' id='dzoutsite' maxlength="30"/></p>
				</div>
				<div class="modal-footer" style="text-align: right">
				    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 "  onclick="addOrUpdateOutSite()">
								<div class="font-size-12 line-height-18">保存</div>
						<div class="font-size-9 ">Confirm</div>
					 </button>
					<button type="button"
						class="btn btn-primary padding-top-1 padding-bottom-1"
						data-dismiss="modal" onclick="hideaddbookmarkModal()">
						<div class="font-size-12 line-height-18">关闭</div>
						<div class="font-size-9 ">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editbookmarkModal" tabindex="-1" role="dialog"
		aria-labelledby="editbookmarkModal" aria-hidden="true" data-backdrop="static" >
		<div class="modal-dialog" style='width:300px;'>
			<div class="modal-content">
			     <div class='modal-header' style='padding-top:8px;padding-bottom:8px;'>
			     <div class="modal-title" >
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true"></button>
							<label class="font-size-13" style='font-weight:normal;'>书签编辑</label>
						</div>
			     </div>
				<div class="modal-body" >
				    <input type='hidden' id='bookmarkid'/>
				    <input type='hidden' id='bookmarktype'/>
				    <p ><label style='font-size:12px;width:65px;'>名称(N)：</label><input type='text' style='width:200px;height:25px;font-size:12px;' id='eaitfavNameoutsite' maxlength="30"/></p>
					<p style='margin-top:8px;'><label style='font-size:12px;width:65px;'>地址(W)：</label><input type='text' style='width:200px;height:25px;font-size:12px;' id='editdzoutsite' maxlength="30"/></p>
				</div>
				<div class="modal-footer" style="text-align: right">
				    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 "  onclick="editOrUpdateOutSite()">
								<div class="font-size-12 line-height-18">保存</div>
						<div class="font-size-9 ">Confirm</div>
					 </button>
					<button type="button"
						class="btn btn-primary padding-top-1 padding-bottom-1"
						data-dismiss="modal" >
						<div class="font-size-12 line-height-18">关闭</div>
						<div class="font-size-9 ">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>

</body>
<!-- END BODY -->
	<%@ include file="/WEB-INF/views/sys/updatelog/update_log.jsp"%><!-------新增对话框对话框 -------->
<script src="${ctx}/static/assets/scripts/core/app.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		var dhusername=$("#dhusername").val();
		var dhrealname=$("#dhrealname").val();		
		if(dhrealname.length>20){
			dhrealname=dhrealname.substring(0,20);
		}
		$("#userinfoMessage").text(dhusername+" "+dhrealname);
		App.init();
		//初始化菜单
		loadLeftMenu();
		loadFavicon();
		loadFaviconBook();
		validateInfo();	
		// 隐藏Modal时验证销毁重构
		$("#changePassword").on("hidden.bs.modal", function() {
			$("#forminfo").data('bootstrapValidator').destroy();
			$('#forminfo').data('bootstrapValidator', null);
			validateInfo();
		});
		$("i[data-toggle='dropdialog']").mouseover(function(){
			$(this).addClass("dropdialoghover");
			$("i[data-toggle='dropdialogBook']").addClass("dropdialoghover_right");
		})
		$("i[data-toggle='dropdialog']").mouseout(function(){
			$(this).removeClass("dropdialoghover");
			$("i[data-toggle='dropdialogBook']").removeClass("dropdialoghover_right");
		})
		$("i[data-toggle='dropdialogBook']").mouseover(function(){
			$(this).addClass("dropdialoghover_right");
			$("i[data-toggle='dropdialog']").addClass("dropdialoghover");
		})
		$("i[data-toggle='dropdialogBook']").mouseout(function(){
			$(this).removeClass("dropdialoghover_right");
			$("i[data-toggle='dropdialog']").removeClass("dropdialoghover");
		})
		
	});
	var imgData = {};
	var imgVail = false;
	var userImgType = 1;
	function initFileInput(ctrlName,uploadUrl) {      
		var attId = $("#attId_layout").val();
		var url = basePath+'/common/preview/'+attId+"_"+1+'?fileName=aa';
		var initialPreview =[];
		if(attId){
			initialPreview =[
	         //预览图片的设置  
	         "<img  src="+url+" class='file-preview-image' alt='电子签名' title='电子签名' style='width:100%'>",
	 ]
		}
        var control = $('#' + ctrlName);   
        control.fileinput({  
            language: 'zh', //设置语言  
            uploadUrl: uploadUrl,  //上传地址  
            showUpload: false, //是否显示上传按钮  
            showRemove:true,  
            dropZoneEnabled: false,  
            showCaption: true,//是否显示标题  
            minImageWidth: 1, //图片的最小宽度
            minImageHeight: 1,//图片的最小高度
            maxImageWidth: 300,//图片的最大宽度
            maxImageHeight: 150,//图片的最大高度
            maxFileSize:50,//单位为kb，如果为0表示不限制文件大小
            allowedPreviewTypes: ['image'],  
            allowedFileTypes: ['image'],  
            allowedFileExtensions:  ['jpg', 'png'],  
            layoutTemplates : {
            	actionDelete : '',
            	actionUpload : ''
            },
            maxFileCount: 1,
            initialPreview: initialPreview
         }).on("filebatchselected", function(event, files) {
        	imgVail = true;
        	if(files !=""){
        		if(imgData.yswjm){
		    		$(".kv-preview-thumb.file-preview-success").eq(0).remove();
		    	}
        		$(this).fileinput("upload");  
        		imgVail = false;
        		userImgType = 1;
        	}
            })  
            .on("fileuploaded", function(event, data) { 
            	if(data){
            		imgData.yswjm = data.response.attachments[0].yswjm;
                	imgData.wbwjm = data.response.attachments[0].wbwjm;
                	imgData.nbwjm = data.response.attachments[0].nbwjm;
                	imgData.cflj = data.response.attachments[0].cflj;
                	imgData.wjdx = data.response.attachments[0].wjdx;
                	imgData.hzm = data.response.attachments[0].hzm;
            	}
                $("#path").attr("value",data.response); 
        }).on("fileerror",function(event, data, msg){
        	
        }).on("fileclear",function(event, data, msg){
        	imgVail = false;
        	userImgType = 2;
        	$(".fileinput-remove-button").css("display","none");
        })
    } 
	
/* 	 $("#file").fileinput({

        language: 'zh', //设置语言
        
        uploadUrl:basePath+"/common/ajaxUploadImg", //上传的地址

       allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀

       //uploadExtraData:{"id": 1, "fileName":'123.mp3'},

        uploadAsync: true, //默认异步上传

        showUpload:false, //是否显示上传按钮

        showRemove :false, //显示移除按钮

        showPreview :true, //是否显示预览

        showCaption:false,//是否显示标题

        browseClass:"btn btn-primary", //按钮样式    

       dropZoneEnabled: false,//是否显示拖拽区域
      
       minImageWidth: 1, //图片的最小宽度

       minImageHeight: 1,//图片的最小高度

       //maxImageWidth: 50,//图片的最大宽度

       //maxImageHeight: 50,//图片的最大高度

        maxFileSize:300,//单位为kb，如果为0表示不限制文件大小

       minFileCount: 1,

        maxFileCount:1, //表示允许同时上传的最大文件个数

        enctype:'multipart/form-data',

       validateInitialCount:true,

        previewFileIcon: "<iclass='glyphicon glyphicon-king'></i>",

       msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",

   }).on("fileuploaded", function (event, data, previewId, index){

         

});  */
	
    function validateInfo(){
    	validatorForm = $('#forminfo').bootstrapValidator({
            message: '数据不合法',
            feedbackIcons: {
                //valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                realname: {
                    validators: {
                        notEmpty: {
                            message: '姓名不能为空'
                        },
                        stringLength: {
                            max: 100,
                            message: '长度最多不超过100个字符'
                        }
                    }
                },
                cellphone: {
	                validators: {
	                	regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]{0,20}$/,
	                        message: '手机格式不正确'
	                    }
	                }
	            },
	            phone: {
	                validators: {
			            regexp: {
		                    regexp: /^[^\u4e00-\u9fa5]{0,20}$/,
		                    message: '电话格式不正确'
		                }
	                }
	            }     	            
            }
        });
    }
	function showChangeInfo() {
		cleardata();
		$("#showHideIcon").text("+");
		$("#passwordDiv").css("display","none");
		$("#changePassword").modal("show");
		$("#realname").val("${sessionScope.user.realname}");//姓名
		$("#cellphone").val("${sessionScope.user.cellphone}");//固定电话
		$("#phone").val("${sessionScope.user.phone}");//手机号码
		$("#sex").val("${sessionScope.user.sex}");//性别
		$("#changeErrorInfo").html("");
		$("#oldPassword").val("");
		$("#newPassword").val("");
		$("#confirmPassword").val("");
		$("#oldPassword").attr("readonly", false);
		$("#newPassword").attr("readonly", false);
		$("#confirmPassword").attr("readonly", false);
		
		 var path = basePath+"/common/ajaxUploadImg";   
	     initFileInput("file",path); 
	}
	function showPassword(obj){
		if($(obj).text()=="+"){
			$("#passwordDiv").show();
			$(obj).text("-")
		}else{
			$("#passwordDiv").hide();
			if($("#changeErrorInfo").find("font").text()=="原密码,新密码或确认密码不能为空." || $("#changeErrorInfo").find("font").text()=="新密码与原密码不能相同." || $("#changeErrorInfo").find("font").text()=="新密码与确认密码不一致."){
				$("#changeErrorInfo").html("");
			}
			$(obj).text("+")
		}
		
	}
	
	var modulus ="";
	var exponent ="";
	AjaxUtil.ajax({
		url:basePath + "/sys/user/publicKey",
		type:"post",
		async: false,
		data:{
		},
		dataType:"json",
		success:function(data){
			if(data != null){
				modulus = data.modulus;
				exponent = data.exponent;
				
			}
		}
	});
	function editUserInfo() {
		 $('#forminfo').data('bootstrapValidator').validate();
		  if(!$('#forminfo').data('bootstrapValidator').isValid()){
			return false;
		  }
		  if(imgVail){
			  //$("#userinfotipModal").modal("show");
			  //$("#userinfotip").text("请根据提示上传正确的电子签名!");
			  return false;
		  }
		var oldPassword = $.trim($("#oldPassword").val());
		var newPassword = $.trim($("#newPassword").val());
		var confirmPassword = $.trim($("#confirmPassword").val());
		var realname = $.trim($("#realname").val());//姓名
		var cellphone = $.trim($("#cellphone").val());//固定电话
		var phone = $.trim($("#phone").val());//手机号码
		var sex = $.trim($("#sex").val());//性别
		$("#changeErrorInfo").html("");
		if($("#showHideIcon").text()=='-'){
			if (oldPassword == "" || newPassword == "" || confirmPassword == "") {
				$("#changeErrorInfo").html(
						"<font color='red'>原密码,新密码或确认密码不能为空.</font>");
				return false;
			}
			if (oldPassword == newPassword) {
				$("#changeErrorInfo").html("<font color='red'>新密码与原密码不能相同.</font>");
				return false;
			}
			if (newPassword != confirmPassword) {
				$("#changeErrorInfo").html("<font color='red'>新密码与确认密码不一致.</font>");
				return false;
			}
		}else{
			oldPassword='';
			newPassword='';
			confirmPassword=''; 
		}
		var key = RSAUtils.getKeyPair(exponent,'', modulus);
		var pwd = RSAUtils.encryptedString(key, oldPassword);
		var pwd1 = RSAUtils.encryptedString(key, confirmPassword);
		var obj = {};
		obj.id = '${sessionScope.user.id}';
		obj.password = pwd;
		obj.newpassword = pwd1;
		obj.realname = realname;//姓名
		obj.cellphone = cellphone;//固定电话
		obj.phone = phone;//手机号码
		obj.sex = sex;//性别
		obj.attId = userImgType;
		if(imgData != null && userImgType == 1 && imgData.yswjm){
		obj.attachment =imgData;
		}
		startWait();
			$.ajax({
				url : basePath + '/sys/user/updateUserInfo',
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(obj),
				success : function(data) {
					finishWait();
					if (data.status == "success") {
						/* $("#changePassword").modal("hide"); */
						var str="${sessionScope.user.username} "+realname;
						$("#userinfoMessage").text(str)
						$("#userinfotipModal").modal("show");
						$("#userinfotip").text("用户信息修改成功!");
						window.location.reload();
						/* AlertUtil.showMessage("用户信息修改成功!"); */
					} else {
						$("#changeErrorInfo").html(
								"<font color='red'>"+data.massage+"</font>");
						return false;
					}
				},
				error : function() {
					finishWait();
				}
			});
		
	}
	function hideInfoModal(){
		$("#changePassword").modal("hide");
	}
	function logout() {
		sessionStorage.removeItem("loginName");
		window.location.href = "${ctx}/logout";
		
		//退出osms系统
		$.ajax({
	    	url:$("#osmsRemoteIP").val()+"/user/authen/ssoLogout",
	    	type:"post",
	   		dataType:"jsonp",
	   		data:{"tokenkey":$("#clientIP").val()+$("#dhusername").val()},
	   		 success:function(data){
	   			
	   		 },
	   		 error:function(){
	   		 }
	    });  
	}
	function lookPDF() {
		
	var url="${ctx}/sys/user/UserHelperPdf";
	window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url),
			'PDF','width:50%;height:50%;top:100;left:100;');
	}
	function cleardata(){
		$("#changePassword input[type='txex']").val("");
	}
	function loadLeftMenu(id) {
		var menuIdPath = '${menuCodeHigh}';
		var menuIdPathArray = menuIdPath.split(",");
		if (typeof (id) == 'undefined') {
			id = menuIdPathArray[0];
		}
		
		var $menuA = $("#pageHeaderMenuUL").find("a:eq(0)");
		
		if($menuA.length == 0){
			return;
		}
		if($menuA.attr("menuCode")=="index"){
			$("#logo_btn").attr("menuType", "2");
			$("#logo_btn").attr("menuId", $menuA.attr("menuId"));
			$("#logo_btn").attr("href", $menuA.attr("href"));
			$menuA.hide();
		}
		
		if (id == "") {
			if($menuA.attr("menuType") == "2"){
				window.location.href = $menuA.attr("href");
			}
			id = $menuA.attr("menuId");
		}else{
			if($("#mainPageFlag").val() == "1"){//main.jsp中的标识 
				if($menuA.attr("menuType") == "2"){
					window.location.href = $menuA.attr("href");
				}
			}
		}
		
		showLeftMenu();
		$("#pageHeaderMenuUL").find("a.on").removeClass("on");
		$("#menu_" + id).addClass("on");

		var _hasActived = false;
		
		//获取当前菜单
		var tempMenu = null;
		if(userMenuListJson){
			$.each(userMenuListJson, function(index, value, array) {
			  	if(value.id == id){
			  		tempMenu = value;
			  		return false;
			  	}
			});
		}
		var html = "";
		if (tempMenu) {
			if (tempMenu.menuType == '2') {
				hideLeftMenu();
			}
			if (tempMenu.children && tempMenu.children.length > 0) {
				$.each(tempMenu.children, function(index, row) {
					if ($.inArray(row.id + "", menuIdPathArray) >= 0) {
						_hasActived = true;
						html += '<li class="active" id="menu_'+row.id+'" style="display: block">';
					} else {
						html += '<li id="menu_'+row.id+'" style="display: block">';
					}

					if (row.menuType == '2' && row.path) {
						if (row.path.startWith("http")) {
							html += '<a class="outsite" href="javascript:void(0);" url="' + row.path + '" >';
						} else {
							html += '<a class="insite"  href="javascript:void(0);" url="${ctx}/'+ row.path + '" >';
						}
					} else {
						html += '<a href="javascript:void(0);" >';
					}
					html += '<i class="pull-left '+row.iconPath+'" ></i> ';
					html += '<span class="title pull-left">';
					html += '<div class="font-size-12 " id="menu_cn_'+row.id+'" title="'+row.menuName+'">' + row.menuName + '</div>';
					html += '<div class="font-size-9 " id="menu_fn_'+row.id+'" title="'+row.menuFname+'">' + row.menuFname + '</div>';
					html += '</span> ';
					html += '<span class="selected"></span>';
					if (row.menuType == '1') {//父节点
						html += '<span class="arrow"></span>';
					}
					html += '<div class="clearfix"/></a>';
					if (row.children && row.children.length > 0) {
						html += '<ul class="sub-menu">';
						$.each(row.children, function(index1, row1) {
							if ($.inArray(row1.id+ "", menuIdPathArray) >= 0) {
								_hasActived = true;
								html += '<li class="active" id="menu_'+row1.id+'" modecode="'+id+'">';
							} else {
								html += '<li id="menu_'+row1.id+'" modecode="'+id+'">';
							}

							var _aClass = "insite";
							if (row1.path.startWith('http')) {
								_aClass = "outsite";
								html += '<a class="'+_aClass+'" id="menu_href_'+row1.id+'" url="'+row1.path+'" href="#">';
							}else{
								html += '<a class="'+_aClass+'" id="menu_href_'+row1.id+'" url="${ctx}/'+row1.path+'" href="#">';
							}

							html += '<i class="pull-left '+row1.iconPath+'" ></i> ';
							html += '<div class="font-size-12 " id="menu_cn_'+row1.id+'" title="'+row1.menuName+'">'+ row1.menuName + '</div>';
							html += '<div class="font-size-9 " id="menu_fn_'+row.id+'" title="'+row1.menuFname+'">' + row1.menuFname + '</div>';
							html += '<span class="selected"></span>';
							html += '</a>';
							html += '</li>';
						});
						html += '</ul>';
					}
					html += '</li>';
				});
			}
		}

		$("#pageLeftMenuUL").html(html);

		$('.outsite').on('click', function() {
			var menu_top = $('#mCSB_1_container').css('top');
			$.cookie('menu_top', menu_top, {
				expires : 1,
				path : '/'
			});
			window.open($(this).attr('url'));
		})

		$('.insite').on('click', function() {
			var menu_top = $('#mCSB_1_container').css('top');
			$.cookie('menu_top', menu_top, {
				expires : 1,
				path : '/'
			});
			window.location.href = $(this).attr('url');
		})

		var menuHeight = $(window).height() - $('.header').outerHeight()
				- $('.footer').outerHeight() - 13;
		$(".page-sidebar").mCustomScrollbar({
			mouseWheel : {
				enable : true,
				scrollAmount : 200,
			},
			setHeight : 0,
			theme : "minimal-dark",
		});

		$(".resizeHeight").click(function() {
			App.resizeHeight();
		});
		$("#mCSB_1").height(menuHeight);

		var menu_top = $.cookie('menu_top');
		if (_hasActived && menu_top != undefined && menu_top != '') {
			$('#mCSB_1_container').css('top', menu_top);
		} else {
			$('#mCSB_1_container').css('top', 0);
		}
		
		$("#mCSB_1").hover(function(){
			if($.cookie && $.cookie('sidebar_closed') === '1'){
				 $("#collspanLeftMenuBtn .icon-caret-right").removeClass().addClass("icon-caret-left");
				 $('body').removeClass("page-sidebar-closed");
				 $(".sidebar-toggler").css("left","190px");
				 $("#copyright").fadeIn(100);
				 $("#copyright2").hide();
			}
		},function(){
			if($.cookie && $.cookie('sidebar_closed') === '1'){
				$('body').addClass('page-sidebar-closed');
	            $(".sidebar-toggler").css("left","3px");
	            $("#copyright").hide();
	            $("#copyright2").fadeIn(100);
	            $("#collspanLeftMenuBtn .icon-caret-left").removeClass().addClass("icon-caret-right");
			}
		});
	}

	function hideLeftMenu() {
		$("#collspanLeftMenuBtnI").hide();
		$(".page-sidebar-wrapper").css({
			display : "none"
		});
		$(".sidebar-toggler-wrapper").css({
			display : "none"
		});
		$(".page-sidebar-closed").css("cssText", "margin-left: 0 !important;");
		$(".page-content").css("cssText", "margin-left: 0 !important;");
	}

	function showLeftMenu() {
		$("#collspanLeftMenuBtnI").show();
		$(".page-sidebar-wrapper").css({
			display : ""
		});
		$(".sidebar-toggler-wrapper").css({
			display : ""
		});
		$(".page-sidebar-closed").removeAttr("style");
		$(".page-content").removeAttr("style");
	}

	function collspanLeftMenu() {
		$("#pageLeftMenuCollapse").trigger("click");
		var iClass = $("#collspanLeftMenuBtnI").attr("class");
		$("#collspanLeftMenuBtnI").removeClass();
		if(iClass == "icon-caret-right"){
			$("#collspanLeftMenuBtnI").addClass("icon-caret-left");
		}else{
			$("#collspanLeftMenuBtnI").addClass("icon-caret-right");
		}
	}
	
	/**
	 * 保存或修改快捷菜单
	 */
	function addOrUpdate(){
		var customShortcut = {};
		//页面上增加
		var zwms = $.trim($("#favName").val());
		
		if(zwms == "" || zwms == null){
			/* AlertUtil.showErrorMessage("名称不能为空!"); */
			$(".favnull").css("display","block");
			return false;
		}
		
		customShortcut.cdid = $.trim(menuCode.split(",").pop());
		customShortcut.zwms = zwms;
		customShortcut.ywms = "";
			//保存
			AjaxUtil.ajax({
				url:basePath+"/sys/custom/shortcut/addOrUpdate",
				type: "post",
				dataType:"json",
				data:customShortcut,
				success:function(data){
					$(".dropdown-dialog").css("display","none");
					$("i[data-toggle='dropdialog']").removeClass("dropdialoghoveractive");
				    $("i[data-toggle='dropdialogBook']").removeClass("dropdialoghover_rightactive");
				    $("#favName").val('');
				    //AlertUtil.showMessage('保存成功!');
				}
			});
	}
	
	/**
	 * 删除快捷菜单
	 */
	function deleteShortcut(){
		var cdid = $.trim(menuCode.split(",").pop());
		AjaxUtil.ajax({
			url:basePath+"/sys/custom/shortcut/remove",
			type: "post",
			dataType:"json",
			data:{id: cdid,type : 0},
			success:function(data){
				$(".dropdown-dialog").css("display","none");
				$("i[data-toggle='dropdialog']").removeClass("dropdialoghoveractive");
			    $("i[data-toggle='dropdialogBook']").removeClass("dropdialoghover_rightactive");
			    $("#favName").val('');
			    //AlertUtil.showMessage('删除成功!');
			}
		});
	}
	
	/* 收藏小图标 */
	function loadFavicon(){
		
		$("i[data-toggle='dropdialog']").click(function(e){
			 e = e || window.event;  
		    if(e.stopPropagation) { //W3C阻止冒泡方法  
		        e.stopPropagation();  
		    } else {  
		        e.cancelBubble = true; //IE阻止冒泡方法  
		    } 
		    if( $(".dropdown-dialog").css("display")=="none"){
		    	$(".dropdown-dialogBook").css("display","none");
		    	 $("i[data-toggle='dropdialog']").removeClass("dropdialogbookmarkhoveractive");
				 $("i[data-toggle='dropdialogBook']").removeClass("dropdialogbookmarkhover_rightactive");
		    	$(".favnull").css("display","none");
		         $(".dropdown-dialog").css("display","block");
		        
		         $("i[data-toggle='dropdialog']").addClass("dropdialoghoveractive");
		         $("i[data-toggle='dropdialogBook']").addClass("dropdialoghover_rightactive");
		         $("#favName").val($('#bodyTitle').text());
				 }else{
				      $(".dropdown-dialog").css("display","none");
				      $("i[data-toggle='dropdialog']").removeClass("dropdialoghoveractive");
				      $("i[data-toggle='dropdialogBook']").removeClass("dropdialoghover_rightactive");
				      $("#favName").val('');	
				 }
           $(".dropdown-dialog").click(function(e){
				  e = e || window.event;  
		    if(e.stopPropagation) { //W3C阻止冒泡方法  
		        e.stopPropagation();  
		    } else {  
		        e.cancelBubble = true; //IE阻止冒泡方法  
		    } 
		    $(".dropdown-dialogBook").css("display","none");
			$(this).css("display","block");
			})
        //点击document时关闭对话框
		$(document).click(function(e){
		    if(	$(".dropdown-dialog").css("display")=="block"){
		    $("#favName").val('');	
			$(".dropdown-dialog").css("display","none");
			$("i[data-toggle='dropdialog']").removeClass("dropdialoghoveractive");
		    $("i[data-toggle='dropdialogBook']").removeClass("dropdialoghover_rightactive");
			}
			})
			})
	 }
	/*  */
	function loadFaviconBook(){
		$("i[data-toggle='dropdialogBook']").click(function(e){
			
			loadbookmarksite();
			e = e || window.event;  
		    if(e.stopPropagation) { //W3C阻止冒泡方法  
		        e.stopPropagation();  
		    } else {  
		        e.cancelBubble = true; //IE阻止冒泡方法  
		    } 
		    if( $(".dropdown-dialogBook").css("display")=="none"){
		    	 $("i[data-toggle='dropdialog']").removeClass("dropdialoghoveractive");
				 $("i[data-toggle='dropdialogBook']").removeClass("dropdialoghover_rightactive");
		    	 $(".dropdown-dialog").css("display","none");
		    	 
		         $(".dropdown-dialogBook").css("display","block");
		        
		         $("i[data-toggle='dropdialog']").addClass("dropdialogbookmarkhoveractive");
		         $("i[data-toggle='dropdialogBook']").addClass("dropdialogbookmarkhover_rightactive");
				 }else{
				      $(".dropdown-dialogBook").css("display","none");
				      $("i[data-toggle='dropdialog']").removeClass("dropdialogbookmarkhoveractive");
					  $("i[data-toggle='dropdialogBook']").removeClass("dropdialogbookmarkhover_rightactive");
				 }

			$(".dropdown-dialogBook").click(function(e){
				  e = e || window.event;  
		    if(e.stopPropagation) { //W3C阻止冒泡方法  
		        e.stopPropagation();  
		    } else {  
		        e.cancelBubble = true; //IE阻止冒泡方法  
		    } 
		    $(".dropdown-dialog").css("display","none");
			$(this).css("display","block");
			})
            //点击document时关闭对话框
		   $(document).click(function(e){
		    if(	$(".dropdown-dialogBook").css("display")=="block"){
				$(".dropdown-dialogBook").css("display","none");
				$("i[data-toggle='dropdialog']").removeClass("dropdialogbookmarkhoveractive");
			    $("i[data-toggle='dropdialogBook']").removeClass("dropdialogbookmarkhover_rightactive");
				}
			})
			})
	}
	function loadbookmarksite(){
		var bookmarksite='';
		$.ajax({
			url:basePath+"/sys/custom/block/queryListByUserId",
			type: "get",
			cache:false,
			async:false,
			dataType:"json", 
			success:function(data){
				
			//加载用户快捷方法
			var str='';
			$.each(data.shortcut, function(index, row){
				var menuName = formatUndefine(row.zwms);
				if(menuName == '' && row.menu != null){
					menuName = formatUndefine(row.menu.menuName);
				}
				
				str += "<p><a href=\""+(row.menu?(basePath+"/"+row.menu.path):"javascript:void(0);")+"\" title='"+StringUtil.escapeStr(menuName)+"&#13"+(row.menu?(basePath+"/"+row.menu.path):"")+"'>"+
				"<img src='${ctx}/static/assets/img/logo_04.png' alt='logo' style='width:16px;height:16px; margin-right: 5px;margin-top: -4px;' />"+StringUtil.escapeStr(menuName)+"</a></p>";
			})
			bookmarksite+=str;
    		}
    	})
		$.ajax({
			url:basePath+"/sys/custom/mark/queryBookMarkListByUserId",
			type: "get",
			cache:false,
			async:false,
			dataType:"json", 
			success:function(data){
			$("#outsite").html('');	
			var str='';
			$.each(data.bookmark, function(index, row){
				var dz=" "+StringUtil.escapeStr(row.mc)+"&#13 "+(row.dz)
				str += "<p><a href=\""+(row.dz)+"\" title='"+dz+"'>"+
				"<img src='${ctx}/static/assets/img/logo_04.png' alt='logo' style='width:16px;height:16px; margin-right: 5px;margin-top: -4px;' />"+StringUtil.escapeStr(row.mc)+"</a></p>";
			})
			bookmarksite+=str;
    		}
    	})
    	if(bookmarksite==''){
    		bookmarksite='<p style="padding-left:8px;">暂无数据 No data.</p>'
    	}
		$("#bookmarksite").html(bookmarksite);
	}
	
	function showBookMark(e){
		  e = e || window.event;  
		    if(e.stopPropagation) { //W3C阻止冒泡方法  
		        e.stopPropagation();  
		    } else {  
		        e.cancelBubble = true; //IE阻止冒泡方法  
		    } 
		$(".dropdown-dialogBook").css("display","none");
		$("i[data-toggle='dropdialog']").removeClass("dropdialogbookmarkhoveractive");
	    $("i[data-toggle='dropdialogBook']").removeClass("dropdialogbookmarkhover_rightactive");
		$("#bookmarkModal").modal("show");
		loadBookMarkMessage();
	}
	function loadBookMarkMessage(){
		var bookmarkStr='';
		var bookmarkContent='<li style="background:#ececec;"><label class="colwidth-5" >操作</label><label class="colwidth-10">名称</label><label class="colwidth-35">地址</label></li>';
		$.ajax({
			url:basePath+"/sys/custom/block/queryListByUserId",
			type: "get",
			cache:false,
			async:false,
			dataType:"json", 
			success:function(data){
			//加载用户快捷方法
			var str='';
			$.each(data.shortcut, function(index, row){
				var menuName = formatUndefine(row.zwms);
				if(menuName == '' && row.menu != null){
					menuName = formatUndefine(row.menu.menuName);
				}
				
				str += "<li><a href='javascript:void(0);' onclick='showHref(\""+(basePath+"/"+row.menu.path)+"\")' title='"+StringUtil.escapeStr(menuName)+"&#13"+(row.menu?(basePath+"/"+row.menu.path):"")+"'>"+
				"<label class='colwidth-5'><i class='icon-edit color-blue cursor-pointer' style='visibility:hidden;'></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer ' onclick='deleteBookMark(event,\""+row.id+"\",1)'></i></label><label class='colwidth-10'>"+StringUtil.escapeStr(menuName)+"</label><label class='colwidth-35'>"+(row.menu?(basePath+"/"+row.menu.path):"javascript:void(0);")+"</label></a></li>";
			})
			bookmarkStr+=str;
    		}
    	})
    	$.ajax({
			url:basePath+"/sys/custom/mark/queryBookMarkListByUserId",
			type: "get",
			cache:false,
			async:false,
			dataType:"json", 
			success:function(data){	
			var str='';
			$.each(data.bookmark, function(index, row){
				var dz = " "+(StringUtil.escapeStr(row.mc)+"&#13"+" "+(row.dz));
				str += "<li><a href='javascript:void(0);' onclick='showHref(\""+(row.dz)+"\")'  title='"+dz+"'>"+
				"<label class='colwidth-5'><i class='icon-edit color-blue cursor-pointer' onclick='editBookMark(event,\""+row.id+"\",1)'></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer ' onclick='deleteBookMark(event,\""+row.id+"\",0)'></i></label><label class='colwidth-10'>"+StringUtil.escapeStr(row.mc)+"</label><label class='colwidth-35'>"+(row.dz)+"</label></a></li>";
			})
			bookmarkStr+=str;
    		}
    	})
		if(bookmarkStr==''){
			bookmarkContent+="<li class='text-center'>暂无数据 No data.<li>";
		}else{
			bookmarkContent+=bookmarkStr;	
		}
    	$("#bookmarkUl").html(bookmarkContent);
	}
	/* 打开书签对话框  */
	function addbookMarkDialog(){
		$("#addbookmarkModal").modal("show");
	}
	/* 添加书签 */
	function addOrUpdateOutSite(){
		
		var bookmark = {};
		//页面上增加
		var mc = $.trim($("#favNameoutsite").val());
		if(mc == "" || mc == null){
			$(".favnull").css("display","block");
			return false;
		}
		var dz=$.trim($("#dzoutsite").val());
		bookmark.mc = mc;
		bookmark.dz = dz;
		//保存
		AjaxUtil.ajax({
			url:basePath+"/sys/custom/bookmark/addOrUpdateOutSite",
			type: "post",
			dataType:"json",
			data:bookmark,
			success:function(data){
				loadBookMarkMessage()
				$("#favNameoutsite").val("");
				$("#dzoutsite").val("");
			}
		});
		$("#addbookmarkModal").modal("hide");
	}
	/* 删除书签 */
	function deleteBookMark(e,id,type){
		 e = e || window.event;  
		if(e.stopPropagation) { //W3C阻止冒泡方法  
		    e.stopPropagation();  
		 } else {  
		     e.cancelBubble = true; //IE阻止冒泡方法  
		 }
		AjaxUtil.ajax({
			url:basePath+"/sys/custom/bookmark/remove",
			type: "post",
			dataType:"json",
			data:{id:id,type : type},
			success:function(data){
				
			    loadBookMarkMessage()
			}
		});
	}
	/* 链接 */
	function showHref(url){
		window.location.href=url;
	}
	/*编辑书签 */
	function editBookMark(e,id,type){
		 e = e || window.event;  
			if(e.stopPropagation) { //W3C阻止冒泡方法  
			    e.stopPropagation();  
			 } else {  
			     e.cancelBubble = true; //IE阻止冒泡方法  
			 }
			$.ajax({
				url:basePath+"/sys/custom/mark/queryBookMarkByUserId",
				type: "get",
				cache:false,
				async:false,
				dataType:"json", 
				data:{id:id,type : 0},
				success:function(data){	
					$("#bookmarkid").val(data.bookmark[0].id)
					$("#editdzoutsite").val(data.bookmark[0].dz);
					$("#eaitfavNameoutsite").val(data.bookmark[0].mc);
					$("#editbookmarkModal").modal("show");
	    		}
	    	})		
	}
	/* 编辑书签 */
	function editOrUpdateOutSite(){
		var obj={};
		obj.id=$.trim($("#bookmarkid").val());
		obj.mc=$.trim($("#eaitfavNameoutsite").val());
		obj.dz=$.trim($("#editdzoutsite").val());
		$.ajax({
			url : basePath + '/sys/custom/mark/updateBookMark',
			type : "post",
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : JSON.stringify(obj),
			success:function(data){	
				 loadBookMarkMessage()
				$("#editbookmarkModal").modal("hide");
    		}
    	})	 
	}
	function hideaddbookmarkModal(){
		$("#favNameoutsite").val("");
		$("#dzoutsite").val("");
	}
	/* 更新日志 */
	function updateLog(){
		$("#update_log_alert_Modal").modal("show");
		$("#update_log_alert_Modal").on("shown.bs.modal",function(){
			updateLogAlert.init();
		})
		
	}
</script>

</html>