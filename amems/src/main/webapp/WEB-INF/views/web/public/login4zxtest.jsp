<%@ page contentType="text/html; charset=utf-8" language="java"
	 errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎登录航空维修工程管理系统</title>
<link href="${ctx}/static/login/zx/css/login.css" rel="stylesheet"
	type="text/css" />
<script src="${ctx}/static/assets/plugins/jquery-1.10.2.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/jquery.cokie.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/security.js"></script>
<script src="${ctx}/static/js/login.js"
	type="text/javascript"></script>
<c:if test="${GETIP_STYLE eq 'CLIENT' }">
	<script type="text/javascript" src="http://pv.sohu.com/cityjson?ie=utf-8"></script><script type="text/javascript">// < ![CDATA[
	</script>
</c:if>
<script>
	var basePath = '${ctx}';
	var customer = "zx";
	var forTest = 'test';
</script>
</head>
<body>
	<div class="logintop">
		<span>欢迎登录 Welcome !</span>
		<ul>
			<li><a href="javascript:;" onclick="lookPDF();">帮助 Help</a></li>
		</ul>
	</div> 
	<div class="testsign"></div>
	<div class="loginbody ">
		<div class="loginbox box-shadow-1">
			<form action="">
				<ul>
					<li><input autocomplete="off"   name="username" id="username" type="text"
						class="loginuser login-input-field" placeholder="请输入帐号 Account" />
					</li>
					<li><a id="pwsSwitch" class="eyeclose"></a> <input autocomplete="off"  
						name="password" disabled id="password" type="password" class="loginpwd login-input-field"
						placeholder="加载控件中 Loading..." />
					</li>
					<li class="code_img"><input id="imageCode" name="imageCode" autocomplete="off" class="yzm login-input-field" type="text" placeholder="请输入验证码 Verification code"> <img id="ckCode" src="" alt="点击刷新验证码 Click Refresh verification code" />
					</li>
					<li>
						<input id="loginbtn" name="loginbtn" type="button" class="loginbtn login-input-field" value="登录 Login" />
					 	<label id="message" style="color: red;display:none;"></label>
					 	<div class="spinner" style="display:none;">
						  <div class="rect1"></div>
						  <div class="rect2"></div>
						  <div class="rect3"></div>
						  <div class="rect4"></div>
						  <div class="rect5"></div>
						</div>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear"></div> 
	<div class="loginbm">©航空维修工程管理系统 2017</div> 
	<object classid="CLSID:76A64158-CB41-11D1-8B02-00600806D9B6" id="locator" VIEWASTEXT>
	</object>
	<object classid="CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223" id="foo">
	</object>
  	<input type="hidden" id="mac" value=""/>
  	<script LANGUAGE="JScript">
          var service = locator.ConnectServer();
          var MACAddr ;
          service.Security_.ImpersonationLevel=3;
          service.InstancesOfAsync(foo, 'Win32_NetworkAdapterConfiguration');
    </script>
    <script FOR="foo" EVENT="OnCompleted(hResult,pErrorObject, pAsyncContext)" LANGUAGE="JScript">
     document.getElementById("mac").value=unescape(MACAddr);
     document.getElementById("spanMac").innerText=unescape(MACAddr);
 	</script>
	<script FOR="foo" EVENT="OnObjectReady(objObject,objAsyncContext)" LANGUAGE="JScript">
	  if(objObject.IPEnabled != null && objObject.IPEnabled != "undefined" && objObject.IPEnabled == true){
	  		if(objObject.MACAddress != null && objObject.MACAddress != "undefined")
	    		MACAddr = objObject.MACAddress;
	  }
	</script>
</body>
</html>
