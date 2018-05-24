$(function(){
$('input.easyui-validatebox').validatebox('disableValidation')
            .focus(function () { $(this).validatebox('enableValidation'); })
            .blur(function () { $(this).validatebox('validate'); });

});

//只能输入数字和小数
function clearNoNum(obj)
{
    //先把非数字的都替换掉，除了数字和.
    obj.value = obj.value.replace(/[^\d.]/g,"");
    //必须保证第一个为数字而不是.
    obj.value = obj.value.replace(/^\./g,"");
    //保证只有出现一个.而没有多个.
    obj.value = obj.value.replace(/\.{2,}/g,".");
    //保证.只出现一次，而不能出现两次以上
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

function ww4(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    var h = date.getHours();
    return  y+'年'+(m<10?('0'+m):m)+'月'+(d<10?('0'+d):d)+'日'+(h<10?('0'+h):h)+'点';
    
  }
  function w4(s){
    var reg=/[\u4e00-\u9fa5]/;  //利用正则表达式分隔
    var ss = (s.split(reg));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    var h = parseInt(ss[3],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h)){
      return new Date(y,m-1,d,h);
    } else {
      return new Date();
    }
  }

function addTab(id, title, url) {
	//如果这个标题的界面存在，则被选中
	if ($('#mainFrame').tabs('exists', title)) {
		$('#mainFrame').tabs('select', title);
	} else {//如果不存在则创建这个界面
	$('#mainFrame').tabs('add',{
	    title: title,    
	    content:"<iframe id='ifr_'"+ id +"' width='100%' height='100%' frameborder='no' border='0' style='height:100%' src='"+ url +"'></iframe>",    
	    closable:true
	});
	}
}

function openWin(id,title, url, w, h) {
	$("#"+id).window({
		title: title,
	    width: w,
	    height: h,
	   // left:(screen.width-w)/2-220,
	   // top:(screen.height-h)/2-154,
	    modal: true,
	    closable: true,
	    collapsible: false,
	    minimizable: false,
	    maximizable: false,
	    shadow: true,
	    content: "<iframe name='picture' width='100%' height='100%' frameborder='no' border='0' style='height:100%' src='" + url + "' />",
	    onClose: function(){
	    }
	    
	}); 
}

function comboboxLoad(iptid,id,text,tabname){
	$('#'+iptid).combobox({    
	    url:"comboboxLoad.do?id="+id+"&text="+text+"&tabname="+tabname,    
	    valueField:'id',    
	    textField:'text'   
	});
}

function formatCurrency(num) {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
    num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    cents = num%100;
    num = Math.floor(num/100).toString();
    if(cents<10)
    cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    num = num.substring(0,num.length-(4*i+3))+','+
    num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num + '.' + cents);
}

function formatIntCurrency(num) {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
    num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    num = Math.floor(num/100).toString();
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    num = num.substring(0,num.length-(4*i+3))+','+
    num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num);
}

//去掉字符串后面的逗号
function removeLastComma(objStr){
	var str = '';
	if(objStr != '' && objStr.length > 0){
		str = objStr.substring(0,objStr.length-1);
	}
	return str;
}

//js获取项目根路径，如： http://localhost:8083/uimcardprj
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}

//登录超时时执行方法
function login_timeout(){
	parent.login_timeout(); 
}