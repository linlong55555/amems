<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%response.setStatus(200);%>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
%>

<!DOCTYPE html>
<html>
<head>
	<title>500 - System internal error</title>
</head>

<body>
	
	<div style="margin-left:auto;margin-right:auto;margin-top:100px;">
		<img style="clear: both; display: block; margin:auto; " src="${ctx}/static/assets/img/500.png"/>
		<p style="text-align: center; margin-top: 20px;">
			<span style="font-weight: normal; font-size: 18px;">系统异常，请稍后再试。</span>
		</p>
		<p style="text-align: center; margin-top: 20px;">
			<span style="font-weight: normal; font-size: 18px;">System exception, please try again later.</span>
		</p>
		<p style="text-align: center; margin-top: 20px;">
			<a href="javascript:void(0);" onclick="window.history.back();" style='text-decoration:none;font-weight:normal; color:#0c58ae;font-size:18px;'>返回上一页 Back</a>
		</p>
	</div>
	
</body>
</html>
