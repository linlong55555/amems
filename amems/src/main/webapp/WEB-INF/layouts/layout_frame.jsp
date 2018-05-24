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
<title id='bodyTitle'><sitemesh:title default="机务维修管理系统"></sitemesh:title></title>


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


.icon_shot{float:left;vertical-align:middle;height:38px;line-height:38px;}
.icon_shot a {
    color: #fff;
    float: left;
    font-size: 16px;
    height: 38px;
    width: 24px;
    display: block;
    overflow: hidden;
    text-align: center;
    text-decoration: none;
}

.icon_shot a:hover {
background:#6bbdff;
}
.page-content{
	margin-left : 0;
	padding: 0 0 0 0 !important;
	margin-top : 5px;
}
.page-container{
	margin-top : 0px !important;
}
#floatIcon{
	display: none !important;
}
.checkPermission{
	display: none !important;
}
.returnBtn{
	display: none !important;
}

</style>
<sitemesh:head></sitemesh:head>
<script src="${ctx}/static/security.js"></script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<input type="hidden" id="authTotal" value="" />

	<!-- BEGIN CONTAINER -->
	<div class="page-container" >
		<!-- BEGIN SIDEBAR -->
		<!-- BEGIN SIDEBAR -->
		<div class="display-none sidebar-toggler-wrapper">
			<div id="pageLeftMenuCollapse" class="sidebar-toggler hidden-phone"></div>
			<div class="clearfix"></div>
		</div>
		<div class="page-sidebar-wrapper">
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

</body>
<!-- END BODY -->

<script src="${ctx}/static/assets/scripts/core/app.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		adjustPage();
		//App.init();
	});
	
	// 根据frame嵌入调整页面元素
	function adjustPage(){
		$("#NavigationBar").remove();
		$(".page-content>.panel>.panel-heading").remove();
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
	
</script>

</html>