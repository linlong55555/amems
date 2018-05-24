$(function() {
	var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
	  
    var isOpera = userAgent.indexOf("Opera") > -1;  
  
    if (userAgent.indexOf("Chrome") > -1 ){  
    	$("#pn").removeClass("padding-top-6").addClass("padding-top-8");
		$("#pr").removeClass("padding-top-6").addClass("padding-top-8");
		$("#sn").removeClass("padding-top-6").addClass("padding-top-8");
		$("#bn").removeClass("padding-top-6").addClass("padding-top-8");
		$("#ewm").removeClass("pull-left").addClass("pull-right");
    }  
    if( userAgent.indexOf("Firefox") > -1){
    	$("#pn").removeClass("padding-top-6").addClass("padding-top-8");
		$("#pr").removeClass("padding-top-6").addClass("padding-top-8");
		$("#sn").removeClass("padding-top-6").addClass("padding-top-8");
		$("#bn").removeClass("padding-top-6").addClass("padding-top-8");
		$("#ewm").removeClass("pull-left").addClass("pull-right");
		$("#txm").css({ "padding-top": "20px"});
    }
    if (!!window.ActiveXObject || "ActiveXObject" in window) {  
    	$("#pn").removeClass("padding-top-6").addClass("padding-top-8");
		$("#pr").removeClass("padding-top-6").addClass("padding-top-8");
		$("#sn").removeClass("padding-top-6").addClass("padding-top-8");
		$("#bn").removeClass("padding-top-6").addClass("padding-top-8"); 
		$("#ewm").removeClass("pull-left").addClass("pull-right");
  
    }; //判断是否IE浏览器  
});


function preview(oper){
	if (!!window.ActiveXObject || "ActiveXObject" in window) { //是否ie
		   remove_ie_header_and_footer();
		  }
		if(navigator.userAgent.indexOf("Firefox") > -1){
		    hideNavigationBar();
		}
	if (oper < 10){
		bdhtml=window.document.body.innerHTML;
		sprnstr="<!--startprint"+oper+"-->";
		eprnstr="<!--endprint"+oper+"-->";
		prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); 
		prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
		window.document.body.innerHTML=prnhtml;
		window.print();
		window.document.body.innerHTML=bdhtml;
		} else {
		window.print();
		}
}

function remove_ie_header_and_footer() {
	  var hkey_path;
	  hkey_path = "HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
	  try {
	   var RegWsh = new ActiveXObject("WScript.Shell");
	   RegWsh.RegWrite(hkey_path + "header", "");
	   RegWsh.RegWrite(hkey_path + "footer", "");
	  } catch (e) {
	  }
}
function hideNavigationBar(){
	$("#NavigationBar").hide();
}