<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
	<title>Access restricted</title>
</head>
<body>
	<div style="margin-left:auto;margin-right:auto;margin-top:100px;">
		<img style="clear: both; display: block; margin:auto; " src="${ctx}/static/assets/img/400.png"/>
		<p style="text-align: center; margin-top: 20px;">
			<span style="font-weight: normal; font-size: 18px;">您的权限超限，请联系管理员处理。</span>
		</p>
		<p style="text-align: center; margin-top: 20px;">
			<span style="font-weight: normal; font-size: 18px;">Request address is limited, please contact the administrator.</span>
		</p>
		<p style="text-align: center; margin-top: 20px;">
			<a href="javascript:void(0);" onclick="window.history.back();" style='text-decoration:none;font-weight:normal; color:#0c58ae;font-size:18px;'>返回上一页 Back</a>
		</p>
	</div>
	
	</body>
</html>
