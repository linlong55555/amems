<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
	<title>404 - Page does not exist</title>
</head>
<body>

<div  style="width:400px;height:350px;margin:0 auto;;margin-top:100px;">
	<img src="${ctx}/static/assets/img/400.png"/>
	<p style="margin-top:-30px; text-align:right;"><a href="<c:url value="/"/>" style='text-decoration:none;font-weight:bolder;
	color:#0c58ae;font-size:20px;'>返回上一页 Back</a></p></div>
	
	</body>
</html>
