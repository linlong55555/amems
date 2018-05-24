<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
	<title>404 - Page does not exist</title>
	<link href="${ctx}/static/assets/css/style.css" rel="stylesheet"
	type="text/css" />
</head>

<body>
<div style="height: 300px; margin: 40px auto 0px; width: 435px;">
	<img src="${ctx}/static/assets/img/404.png">
	<a class="back_btn" href="javascript:history.go(-1);">返回 Back</a></div>
	
	<div style="height: 300px; margin: 40px auto 0px; width: 435px; display:none;">
	<img src="${ctx}/static/assets/img/400.png">
	<p  style="text-align:center;margin-top: 0px;">Sorry, You do not have access.</p>
	<a class="back_btn" href="javascript:history.go(-1);">返回 Back</a>
	</div>
	
	<div style="height: 300px; margin: 40px auto 0px; width: 435px; display:none;">
	<img src="${ctx}/static/assets/img/500.png">
	<p  style="text-align:center;margin-top: 0px;">Sorry, Error accessing page.</p>
	<a class="back_btn" href="javascript:history.go(-1);">返回 Back</a>
	</div>
	
</body>
</html>