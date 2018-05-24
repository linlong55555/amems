<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>  
    <title>Error page</title>  
    <style type="text/css">
	.sidebar-toggler-wrapper{
		display: none;
	}

	.page-sidebar-wrapper{
		display: none;
	}
	.header{
		display: none;
	}
</style>
</head>  
<body style="margin: 0;padding: 0;">  
    <div  style="width:400px;height:350px;margin:0 auto;">
		<img src="${ctx}/static/assets/img/400.png"/>
		<p style="text-align: center; margin-top: 20px;">
		<span style="font-weight: normal; font-size: 18px;"><%= exception.getMessage()%></span>
		<a href="javascript:window.close();">关闭</a>
		</p>
	</div>
</body>  
</html> 

