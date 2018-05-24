<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	window.onload=function(){
		var basePath = '${ctx}';
		var url = '${url}';
		var token = '${token}';
		var mark = '${mark}';
		var uri = url + "?token=" + token;
		if(mark){
			uri += ("&mark=" + mark);
		}
		// 弹出新页面
		window.open(uri);
		// 页面跳转回原页面
		if(document.referrer && document.referrer != window.location.href){
			window.location.href = document.referrer;
		}else{
			window.location.href = basePath + "/index";
		}
	}
</script>
</html>