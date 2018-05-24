function formatYearMonthTime(str) {
	if (str != "" && str != "undefined" && typeof (str) != "undefined") {
		str = str.split(" ");
		var year = str[2];
		var month = formatMonth(str[0]);
		var day = formatDay(str[1].substring(0, str[1].length - 1));
		// var time = str[3] + str[4];
		// str = year + month + day + " " + time;
		str = year + month + day;
		return str;
	} else {
		return "";
	}
}

function formatYearMonthTimeSpilt(str) {
	if (str != "" && str != "undefined" && typeof (str) != "undefined") {
		str = str.split(" ");
		var year = str[2];
		var month = formatMonth(str[0]);
		var day = formatDay(str[1].substring(0, str[1].length - 1));
		str = year + "-" + month +"-" + day;
		return str;
	} else {
		return "";
	}
}

function formatYearMonthTimes(str) {
	if (str != "" && str != "undefined" && typeof (str) != "undefined") {
		str = str.split(" ");
		var year = str[2];
		var month = formatMonth(str[0]);
		var day = formatDay(str[1].substring(0, str[1].length - 1));
		
		if(str[4] != "" && str[4] != "undefined" && typeof (str[4]) != "undefined" 
			&& str[3] != "" && str[3] != "undefined" && typeof (str[3]) != "undefined"){
			var h = parseInt(str[3].split(":")[0]);
			var m = str[3].split(":")[1];
			var s = str[3].split(":")[2];
			
			if(str[4].toLowerCase() == "pm"){
				h = h + 12;
				return year+"-" + month +"-" + day + " " + h +":"+m+":"+s;
			}else{
				if(h<10){
					return year+"-" + month +"-" + day + " 0" + h +":"+m+":"+s;
				}else{
					return year+"-" + month +"-" + day + " " + h +":"+m+":"+s;
				}
			}
		}else{
			return year+"-" + month +"-" + day + "0:0:0";
		}
		
	} else {
		return "";
	}
}

String.prototype.replaceAll = function(s1,s2){
   return this.replace(new RegExp(s1,"gm"),s2);
}

//将时间戳变成yyyyMMdd .2015.5.25
function getLocalTime(nS) { 
	  var str = new Date(parseInt(nS)).toLocaleString().substr(0,17);
	  var ymd = str.split(" ")[0];
	  var ymdSplit = ymd.split("\/");
	  var year_ = ymdSplit[0];
	  var month_ = ymdSplit[1];
	  if (parseInt(month_) <10){
		  month_ = "0" + month_;
	  }
	  var date_ = ymdSplit[2];
	  if (parseInt(date_) <10){
		  date_ = "0" + date_;
	  }
      return year_ + month_ + date_;    
} 

function Multiply(arg1, arg2) {
    var m = 0; //扩大后的两数相乘比初始值相乘扩大的倍数
    var s1 = arg1.toString();
    var s2 = arg2.toString();

    try {
        //获取第一个参数的小数部分长度，去掉小数点后，小数部分的长度就是初始值的小数点右移的位数
        m += s1.split(".")[1].length;
    } catch (e) {
    }

    try {
        ////获取第二个参数的小数部分长度，去掉小数点后，小数部分的长度就是初始值的小数点右移的位数
        m += s2.split(".")[1].length;
    } catch (e) {
    }

    //返回值：将参数的小数点去掉然后相乘，最后除以Mah.pow(10,m)
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}
function indexOfTime(str) {
	if (str != "" && str != "undefined" && typeof (str) != "undefined" && str != null) {
		if(str.indexOf(" ")!=-1){
		return str.split(" ")[0];
		}
	} else {
		return "";
	}
}

function formatYearMonthTimeforGrant(str) {
	if (str != "" && str != "undefined" && typeof (str) != "undefined") {
		str = str.split(" ");
		var year = str[2];
		var month = formatMonth(str[0]);
		var day = formatDay(str[1].substring(0, str[1].length - 1));
		// var time = str[3] + str[4];
		// str = year + month + day + " " + time;
		str = year + "-"+month + "-" +day;
		return str;
	} else {
		return "";
	}
}
function formatYearMonthDate(str){
if (str != "" && str != "undefined" && typeof (str) != "undefined") {
var year=str.substr(0,4);
var month=str.substr(4,2);
var day=str.substr(6,2);
str = year + "-"+month + "-" +day;
		return str;
}else {
		return "";
	}
}

function formatDay(day) {
	if (day >= 1 && day <= 9) {
		day = "0" + day;
	}
	return day;
}
function formatMonth(month) {
	if (month == "Jan") {
		month = "01";
	} else if (month == "Feb") {
		month = "02";
	} else if (month == "Mar") {
		month = "03";
	} else if (month == "Apr") {
		month = "04";
	} else if (month == "May") {
		month = "05";
	} else if (month == "Jun") {
		month = "06";
	} else if (month == "Jul") {
		month = "07";
	} else if (month == "Aug") {
		month = "08";
	} else if (month == "Sep") {
		month = "09";
	} else if (month == "Oct") {
		month = "10";
	} else if (month == "Nov") {
		month = "11";
	} else if (month == "Dec") {
		month = "12";
	}
	return month;
}

// wait请稍等
function startWait($obj) {
	if($obj){
		$obj.block({
			message : '<div class="waitMask " style="margin:0 auto;"></div>', // 取
			// growlUI
			// div
			// 中内容作为消息
			
			showOverlay : true, // 不显示遮罩层
			
			css : {
				background : 'transparent',
				border : 'none',
				'z-index' : '99999'	// 堆叠在模态框的最上层，避免被挡住
			},
			overlayCSS : {
				backgroundColor : '#999'
			}
		});
	}else{
		jQuery.blockUI({
			message : '<div class="waitMask " style="margin:0 auto;"></div>', // 取
			// growlUI
			// div
			// 中内容作为消息
			
			showOverlay : true, // 不显示遮罩层
			
			css : {
				background : 'transparent',
				border : 'none',
				'z-index' : '99999'	// 堆叠在模态框的最上层，避免被挡住
			},
			overlayCSS : {
				backgroundColor : '#999'
			}
		});
	}

}
// wait请稍等
// 关闭wait
function finishWait($obj) {
	if($obj){
		$obj.unblock();
	}else{
		jQuery.unblockUI();
	}
}
// 关闭wait

// 生成随机数
function generateMixed(n) {
	var chars = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
			'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' ];
	var res = "";
	for ( var i = 0; i < n; i++) {
		var id = Math.ceil(Math.random() * 35);
		res += chars[id];
	}
	return res;
}
//
 function formatUndefine(str){
 if(str==undefined){
	 str=""
	}
	 return str;
}
 
 //通用方法
String.prototype.startWith=function(str){     
	  var reg=new RegExp("^"+str);     
	  return reg.test(this);        
}  

//通用方法
String.prototype.endWith=function(str){ 
	var reg=new RegExp(str+"$"); 
	return reg.test(this); 
}

//从数组中移除一个元素
Array.prototype.removeDataFromArray = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
	  this.splice(index, 1);
	}
};

//时间工具类，格式化时间,得到标准时间
function formatDate(dateStr) {//20140724
	if (dateStr != "") {
		var year = dateStr.substr(0, 4);
		var month = dateStr.substr(4, 2);
		var date = dateStr.substr(6, 2);
		var cal = new Date(year, month, date);
		return cal;
	}
}

function splitLength(str,len){
var strInfo="";
  if(str!=""){
  if(str.length>=len){
    strInfo=str.substr(0,len);
	}else{
	strInfo=str;
	}
	
 }
    return strInfo;
}

 function maxLength(str,str1,len){
 var flag=false;
 var objStr="";
 if(str!=""&&str!=undefined){

 if(str.length>len){
    alert(str1+"长度不能长于"+len+"个字");
	flag=true;
	}
	
 }
 return flag;
 }
 


 