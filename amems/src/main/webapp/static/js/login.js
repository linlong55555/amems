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
	
	$(".spinner").hide();
	$('#message').hide();
	$('.loginbtn').click(function() {
		var password=$('#password').val();
		//加密
		var key = RSAUtils.getKeyPair(exponent, '', modulus);
		var pwd1 = RSAUtils.encryptedString(key, password);
		var _forTest = '';
		try{
			_forTest = forTest;
		}catch(e){}
		var data = {
			lastip:lastip,
			username : $('#username').val(),
			password : pwd1,
			imageCode : $('#imageCode').val(),
			mac:$('#mac').val(),
			customer:customer,
			forTest:_forTest,
		};
		$(".login-input-field").attr("disabled", true);
		$('#message').hide();
		$(".spinner").show();
		$.ajax({
			url : basePath + "/login",
			type : "post",
			dataType : "json",
			async : true,
			data : data,
			success : function(data) {
				if (data.success) {
					window.location.href = basePath + "/main";
				} else {
					$(".spinner").hide();
					$(".login-input-field").removeAttr("disabled");
					$('#message').html(data.message);
					if ($('#message').is(":hidden")) {
						$('#message').fadeIn(2000);
					}
					$('#ckCode').trigger('click');
				}
			},
			error :function(data){
				$(".spinner").hide();
				$(".login-input-field").removeAttr("disabled");
				$('#message').html("登录失败！");
				if ($('#message').is(":hidden")) {
					$('#message').fadeIn(2000);
				}
				$('#ckCode').trigger('click');
			}
		});

	});

	$('#ckCode').on('click', function() {
		var url = basePath + '/homepage/generateCkCode'
		var timestamp = new Date().getTime();
		$(this).attr("src", url + '?t=' + timestamp);
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
	var url=basePath+"/sys/user/UserHelperPdf";
	window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url),
			'PDF','width:50%;height:50%;top:100;left:100;');
}
