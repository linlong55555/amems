<%@ page contentType="text/html; charset=utf-8" language="java" import="com.eray.thjw.util.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx}/static/security.js"></script>

<script src="${ctx}/static/assets/plugins/jquery-1.10.2.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/assets/plugins/jquery.cokie.min.js"
	type="text/javascript"></script>	
<%@ include file="/WEB-INF/views/alert.jsp"%>

<c:if test="${GETIP_STYLE eq 'CLIENT' }">
	<script type="text/javascript" src="http://pv.sohu.com/cityjson?ie=utf-8"></script><script type="text/javascript">// < ![CDATA[
		
	</script>
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎登录机务维修管理系统</title>
</head>
<body style="background:url(${ctx}/static/images/bg.png)no-repeat center -160px;">
	<div id="mainBody">
	<input value="${modulus}"  id="modulus" type="hidden"></input>
	<input value="${exponent}" id="exponent" type="hidden"></input>
<!-- 		<div id="cloud1" class="cloud"></div>
		<div id="cloud2" class="cloud"></div> -->
	</div>
	<div class="logintop">
		<span>欢迎登录 Welcome</span>
		<ul>
			<li><a href="javascript:;" onclick="lookPDF();">帮助 Help</a></li>
		</ul>
	</div>
	<div class="loginbody ">
		<!-- <span class="systemlogo"></span> -->
		<div class="loginbox box-shadow-1">
			<h1>
		
				<em>用户登录</em>User Login
			</h1>
			<form action="">
				<ul>
					<li><input autocomplete="off"   name="username" id="username" type="text"
						class="loginuser" placeholder="请输入帐号 Account" /></li>
					<li><a id="pwsSwitch" class="eyeclose"></a> <input autocomplete="off"  
						name="password" id="password" type="password" class="loginpwd"
						placeholder="请输入密码 Password" /></li>
					<li class="code_img"><input  id="imageCode" name="imageCode" autocomplete="off" class="yzm" type="text" placeholder="请输入验证码 Verification code"> <img id="ckCode" src="${ctx}/homepage/generateCkCode" alt="点击刷新验证码 Click Refresh verification code" />
					</li>
					<li>
						<input id="loginbtn" name="loginbtn" type="button" class="loginbtn" value="登录 Log on" />
					 	<label id="message" style="color: red;"></label>
					</li>
				</ul>
			</form>
		</div>
	</div>
	
	
<!-- 	<div class="loginbm">
		©易瑞信息 2016 <a href="http://www.e-ray.com.cn/">e-ray.com.cn</a>
	</div> -->
	<link href="${ctx}/static/css/login.css" rel="stylesheet"
		type="text/css" />

<%-- 	<script src="${ctx}/static/js/thjw/common/cloud.js"
		type="text/javascript"></script> --%>
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
	<script type="text/javascript">
		var basePath = '${ctx}';
		$(function() {
			var uname = $.cookie('uname');
			if(uname != undefined){
				$('#username').val(uname)
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
			$('.loginbtn').on('click', function() {
				var password=$('#password').val();
				//加密
				var modulus = $('#modulus').val();
				var exponent = $('#exponent').val();
				var key = RSAUtils.getKeyPair(exponent, '', modulus);
				var pwd1 = RSAUtils.encryptedString(key, password);
				var data = {
					lastip:lastip,
					username : $('#username').val(),
					password : pwd1,
					imageCode : $('#imageCode').val()
				};
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
		};
		//帮助手册
		function lookPDF() {
			var url="${ctx}/sys/user/UserHelperPdf";
			window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url),
					'PDF','width:50%;height:50%;top:100;left:100;');
		}
	</script>
</body>
</html>
