<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>

<script>
	var message = '${message}';
	if(message){
		alert(message);
	}
	var error_url = '${sso_login_error_url}';
	if(error_url.indexOf("http") == 0){
		window.location.href = error_url;
	}else{
		window.location.href = "${ctx}/"+error_url;
	}
</script>

</head>
<body>
</body>
</html>