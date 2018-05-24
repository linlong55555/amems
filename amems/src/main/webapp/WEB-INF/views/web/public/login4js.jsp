<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%-- <%@ include file="/WEB-INF/views/alert.jsp"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎登录航空维修工程管理系统</title>
<script src="${ctx}/static/assets/plugins/jquery-1.10.2.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/jquery.cokie.min.js"
	type="text/javascript"></script>	
<script src="${ctx}/static/security.js"></script>
</head>
<c:if test="${GETIP_STYLE eq 'CLIENT' }">
	<script type="text/javascript" src="http://pv.sohu.com/cityjson?ie=utf-8"></script><script type="text/javascript">// < ![CDATA[
		
	</script>
</c:if>
<script>
	var basePath = '${ctx}';
	var customer = "js";//用户，用于layout页面展示logo
</script>
<body style="background:#fff url(${ctx}/static/images/bg.png)no-repeat center -180px;">
	<div id="mainBody">
		<input value="${modulus}"  id="modulus" type="hidden"></input>
		<input value="${exponent}" id="exponent" type="hidden"></input>
		<div id="cloud1" class="cloud"></div>
		<div id="cloud2" class="cloud"></div> 
	</div>
	<div class="logintop">
		<span>欢迎登录 Welcome</span>
		<ul>
			<li style="color:#fff;">客户端代码 Client code：<em id="spanMac"></em></li>
			<li><a href="javascript:;" onclick="lookPDF();">帮助 Help</a></li>
		</ul>
	</div> 
	<div class="loginbody ">
	
	
	
		<div class="loginbox box-shadow-1">
			<form action="">
				<ul>
					<li><input autocomplete="off"   name="username" id="username" type="text"
						class="loginuser" placeholder="请输入帐号 Account" /></li>
					<li><a id="pwsSwitch" class="eyeclose"></a> <input autocomplete="off"  
						name="password" id="password" type="password" class="loginpwd"
						placeholder="请输入密码 Password" /></li>
					<li class="code_img"><input  id="imageCode" name="imageCode" autocomplete="off" class="yzm" type="text" placeholder="请输入验证码 Verification code"> <img id="ckCode" src="" alt="点击刷新验证码 Click Refresh verification code" />
					</li>
					<li>
						<input id="loginbtn" name="loginbtn" type="button" class="loginbtn pull-left" value="登录 Log on" />
						<div class='pull-left' style='margin-left:8px;'>
						 	<label id="message" style="color: red;"></label>
						 	<div class="spinner" style="display:none;">
							  <div class="rect1"></div>
							  <div class="rect2"></div>
							  <div class="rect3"></div>
							  <div class="rect4"></div>
							  <div class="rect5"></div>
							</div>
						</div>
						<div class='clearfix'></div>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="loginbm">
		©航空维修工程管理系统 2017 <!-- <a href="http://www.e-ray.com.cn/">e-ray.com.cn</a> -->
	</div> 
	<link href="${ctx}/static/login/js/css/login.css" rel="stylesheet"
		type="text/css" />

 	<script src="${ctx}/static/js/thjw/common/cloud.js"
		type="text/javascript"></script> 
	<script src="${ctx}/static/assets/plugins/jquery.blockui.min.js"
		type="text/javascript"></script>
	<link
		href="${ctx}/static/assets/plugins/bootstrap/css/bootstrap.min.css"
		rel="stylesheet" type="text/css" />
	<link href="${ctx}/static/assets/plugins/data-tables/DT_bootstrap.css"
		rel="stylesheet" type="text/css" />
	<script
		src="${ctx}/static/assets/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="${ctx}/static/js/thjw/common/ajax.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/common.js"></script>
	
	<object classid="CLSID:76A64158-CB41-11D1-8B02-00600806D9B6" id="locator" VIEWASTEXT>
	</object>
	<object classid="CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223" id="foo">
	</object>
  	<input type="hidden" id="mac" value=""/>
  	<script LANGUAGE="JScript">
  //        var service = locator.ConnectServer();
  //        var MACAddr ;
//           var IPAddr ;
//           var DomainAddr;
//           var sDNSName;
 //         service.Security_.ImpersonationLevel=3;
 //         service.InstancesOfAsync(foo, 'Win32_NetworkAdapterConfiguration');
    </script>
   <script FOR="foo" EVENT="OnCompleted(hResult,pErrorObject, pAsyncContext)" LANGUAGE="JScript">
 //    document.getElementById("mac").value=unescape(MACAddr);
  //   document.getElementById("spanMac").innerText=unescape(MACAddr);
//      document.forms[0].txtIPAddr.value=unescape(IPAddr);
//      document.forms[0].txtDNSName.value=unescape(sDNSName);
 </script>
<script FOR="foo" EVENT="OnObjectReady(objObject,objAsyncContext)" LANGUAGE="JScript">
//  if(objObject.IPEnabled != null && objObject.IPEnabled != "undefined" && objObject.IPEnabled == true)
//  {

 // 	if(objObject.MACAddress != null && objObject.MACAddress != "undefined")
 //        MACAddr = objObject.MACAddress;

//    if(objObject.IPEnabled && objObject.IPAddress(0) != null && objObject.IPAddress(0) != "undefined")
//          IPAddr = objObject.IPAddress(0);

//    if(objObject.DNSHostName != null && objObject.DNSHostName != "undefined")
//       sDNSName = objObject.DNSHostName;

//   }
</script>
    
    
	<script type="text/javascript">
		var basePath = '${ctx}';
		$(function() {
			var modulus = "";
			var exponent = "";
			//获取公钥
			$.ajax({
				url : basePath + "/homepage/publickey",
				type : "get",
				cache: false,
				dataType : "json",
				async : false,
				success : function(data) {
					if(data){
						modulus = data.modulus;
						exponent = data.exponent;
						$("#password").attr("placeholder", "请输入密码 Password");
						$("#password").removeAttr("disabled");
					}
				}
			});
			
			var timestamp = new Date().getTime();
			$("#ckCode").attr("src", basePath + '/homepage/generateCkCode?t=' + timestamp);
			
			var uname = $.cookie('uname');
			if(uname != undefined){
				$('#username').val(uname);
			}
			
			var lastip = '';	 
			//客户端获取IP
			if("${GETIP_STYLE}" == 'CLIENT'){					
				try
				{
					lastip = returnCitySN["cip"];
				}
				catch(err)
				{
				    console.info('客户端获取IP失败');
				}
			}
			
			
			$('#message').hide();
			$(".spinner").hide();
			$("#loginbtn").removeAttr("disabled"); 
			
			$('.loginbtn').on('click', function() {
				if ($.trim($('#username').val()) == ""){
					$('#message').html("用户名不能为空");
					if ($('#message').is(":hidden")) {
						$('#message').fadeIn(2000);
					}
					return false;
				}
				
				if ($.trim($('#password').val()) == ""){
					$('#message').html("密码不能为空");
					if ($('#message').is(":hidden")) {
						$('#message').fadeIn(2000);
					}
					return false;
				}
				
				if ($('#imageCode').val() == "") {
					$('#message').html("验证码不能为空");
					if ($('#message').is(":hidden")) {
						$('#message').fadeIn(2000);
					}
					return false;
				}
				
				
				var password=$('#password').val();
				//加密
				//var modulus = $('#modulus').val();
				//var exponent = $('#exponent').val();
				var key = RSAUtils.getKeyPair(exponent, '', modulus);
				var pwd1 = RSAUtils.encryptedString(key, password);
				var data = {
					lastip:lastip,
					username : $('#username').val(),
					password : pwd1,
					imageCode : $('#imageCode').val(),
					mac:$('#mac').val(),
					customer :customer
				};
				
				$(".spinner").show();
				$('#message').hide();
				$(this).attr("disabled","disabled"); //提交按钮置灰
				
				AjaxUtil.ajax({
					url : basePath + "/login",
					type : "post",
					dataType : "json",
					async : false,
					data : data,
					success : function(data) {
						if (data.success) {
							window.location.href = basePath + "/main";
						} else {
							$(".spinner").hide();
							$('#loginbtn').removeAttr("disabled"); 
							$('#message').html(data.message);
							if ($('#message').is(":hidden")) {
								$('#message').fadeIn(2000);
							}
							//$('#message').fadeOut(2000);
							$('#ckCode').trigger('click');
						}
					}
				});

			});

			$('#ckCode').on('click', function() {
				var url = basePath + '/homepage/generateCkCode'
				var timestamp = new Date().getTime();
				$(this).attr("src", url + '?' + timestamp);
			});

			$('#pwsSwitch').on('click', function() {
				if ($(this).hasClass('eyeclose')) {
					$(this).removeClass('eyeclose');
					$(this).addClass('eyeopen');
					$('#password').attr('type', 'input');
				} else if ($(this).hasClass('eyeopen')) {
					$(this).addClass('eyeclose');
					$(this).removeClass('eyeopen');
					$('#password').attr('type', 'password');
				}
			});
		});

		//回车事件控制
		document.onkeydown = function(event) {
			e = event ? event : (window.event ? window.event : null);
			if (e.keyCode == 13) {
				$('.loginbtn').trigger('click');
			}
		}
		//帮助手册
		function lookPDF() {
			var url="${ctx}/sys/user/UserHelperPdf";
			window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url),
					'PDF','width:50%;height:50%;top:100;left:100;');
		}
	</script>
</body>
</html>
