<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="erayFns" uri="/WEB-INF/tld/erayFns.tld"%>
<script type="text/javascript" src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<link rel="stylesheet" href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css" type="text/css">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">

//预警颜色码
var warningColor = {
	level1:"#F7630C",//橙色
	level2:"#FFB900",//黄色
	level3:"#ececec"//灰色
}
 
var basePath = '${ctx}';
var userId = '${sessionScope.user.id}';
var userBmdm = '${sessionScope.user.bmdm}';
var userName = '';
var displayName = '';
var userJgdm = '${sessionScope.user.jgdm}';
var userType = '${sessionScope.user.userType}';
var menuCode = '${menuCodeHigh}';
var deptInfo = '';

var buttonPermissions ;
var accessDepartment ;
var currentUser ;
var userStoreList ;
var userMenuListJson;
var userACRegList;//用户具有的飞机注册号权限   DPRTCODE,FJJX,FJZCH
var userACReg135145List;//用户具有的飞机注册号权限   DPRTCODE,FJJX,FJZCH


$.ajax({
   async: false,
   url:basePath+"/common/getSessions",
   type: "post",
   success:function(data){
	  if(data && data.dicts){
		  buttonPermissions = data.dicts.btnPriCodeListJson?data.dicts.btnPriCodeListJson:[];
		  accessDepartment = data.dicts.accessDepartmentJson?data.dicts.accessDepartmentJson:[];
		  currentUser = data.dicts.userJson?data.dicts.userJson:{};
		  userStoreList = data.dicts.userStoreList?data.dicts.userStoreList:[];
		  userMenuListJson = data.dicts.userMenuListJson?data.dicts.userMenuListJson:[];
		  userACRegList = data.dicts.userACRegListJson?data.dicts.userACRegListJson:[];
		  userACReg135145List = data.dicts.userACReg135145ListJson?data.dicts.userACReg135145ListJson:[];
		  deptInfo = data.dicts.deptInfoJson?data.dicts.deptInfoJson:[];
		  userName = currentUser.username;
		  displayName = currentUser.displayName;
	  }
   }
}); 



var userACTypeList = [];//用户具有的机型权限   DPRTCODE,FJJX
if(userACRegList && userACRegList.length > 0){
	$.each(userACRegList, function(index, row){
		var flag = true;
		$.each(userACTypeList, function(index1, row1){
			if(row1.DPRTCODE == row.DPRTCODE && row1.FJJX == row.FJJX){
				flag = false;
				return false;
			}
		});
		if(flag){
			userACTypeList.push({DPRTCODE:row.DPRTCODE, FJJX:row.FJJX});
		}
	});
}

var acAndTypeUtil = {
	//param:{DPRTCODE:?,FJJX:?}机构必填
	getACRegList:function(param){
		
		var result = [];
		$.each(userACRegList, function(index, row){
			//传入了机构，和机型
			if(param.FJJX && param.DPRTCODE && (param.FJJX == row.FJJX)
					 && (param.DPRTCODE == row.DPRTCODE)  && row.FJZCH != "-" ){
				result.push(row);
			}//只传入了机构
			else if(param.DPRTCODE && !param.FJJX && (param.DPRTCODE == row.DPRTCODE) && row.FJZCH != "-" ){
				result.push(row);
			}
		});
		result.sort(function(a, b){
			return (a.FJZCH < b.FJZCH) ? -1 : 1;
		})
		return result;
		
	} ,
	//param:{DPRTCODE:?,FJJX:?}机构必填 135,145 飞机注册号合集
	getACReg135145List:function(param){
		
		var result = [];
		$.each(userACReg135145List, function(index, row){
			//传入了机构，和机型
			if(param.FJJX && param.DPRTCODE && (param.FJJX == row.FJJX)
					 && (param.DPRTCODE == row.DPRTCODE)  && row.FJZCH != "-" ){
				result.push(row);
			}//只传入了机构
			else if(param.DPRTCODE && !param.FJJX && (param.DPRTCODE == row.DPRTCODE) && row.FJZCH != "-" ){
				result.push(row);
			}
		});
		result.sort(function(a, b){
			return (a.FJZCH < b.FJZCH) ? -1 : 1;
		})
		return result;
		
	} 
	//param:{DPRTCODE:?}机构必填
	,getACTypeList:function(param){
		var result = [];
		$.each(userACTypeList, function(index, row){
			//只传入了机构
			if(param.DPRTCODE && (param.DPRTCODE == row.DPRTCODE)){
				result.push(row);
			}
		});
		result.sort(function(a, b){
			return (a.FJJX < b.FJJX) ? -1 : 1;
		})
		return result;
	}
};

</script>
<script type="text/javascript" src="${ctx}/static/js/ms/privilege.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/ajax.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/NavigationBar.js"></script>

<script type="text/javascript">
	
	var DicAndEnumUtil = {
			
			data:{}
			,setData:function(returnData){
				this.data = returnData;
			}
			,loadDicAndEnums:function (){
				var util = this;
				AjaxUtil.ajax({
					   async: false,
					   url:basePath+"/common/loadDicAndEnums",
					   type: "post",
					   success:function(data){
						  util.setData(data);
				      },
				      error:function(data){
				    	     alert("system error.");
				      }
			    }); 
			}
			,getEnumName:function(enumKey,enumId){
				var enumname = ''
				var enumMap = this.data.enumMap!=undefined?this.data.enumMap:{};
				var items = enumMap[enumKey]!=undefined?enumMap[enumKey]:[];
				var len = items.length;
				
				for(i=0;i<len;i++){
					
					if(items[i].id == enumId ){
						enumname = items[i].name;
						break;
					}
					
				}
				return enumname ; 
				
			}
			,getEnum:function(enumKey){
				var enumMap = this.data.enumMap!=undefined?this.data.enumMap:{};
				var items = enumMap[enumKey]!=undefined?enumMap[enumKey]:[];
				return items; 
			}
		   
			,registerEnum:function(enumKey,elementId){
				 if(this.data.enumMap!=undefined ){
					 var enumMap = this.data.enumMap!=undefined?this.data.enumMap:{};
					 var items = enumMap[enumKey]!=undefined?enumMap[enumKey]:[];
					 var len = items.length;
					 for(i=0;i<len;i++){
						$('#'+elementId).append("<option value='"+items[i].id+"' >"+StringUtil.escapeStr(items[i].name)+"</option>")
					 }
				 }
				 
			}
			//获取数据字典项
			,getDict:function(dicId, dprtcode){
				var dicMap = this.data.dicMap!=undefined?this.data.dicMap:{};
				if(dicMap[dprtcode]){
					if(dicMap[dprtcode][dicId]){
						return dicMap[dprtcode][dicId];
					}else{
						if(dicMap["-1"]){
							if(dicMap["-1"][dicId]){
								return dicMap["-1"][dicId];
							}else{
								return [];
							}
						}
						return [];
					}
				}else if(dicMap["-1"]){
					if(dicMap["-1"][dicId]){
						return dicMap["-1"][dicId];
					}else{
						return [];
					}
				}else{
					return [];
				}
			}
			//TODO 带处理 
			,registerDic:function(dicId,elementId,dprtcode){ 

				 /* if(this.data.dicMap!=undefined ){
					 var dicMap = this.data.dicMap!=undefined?this.data.dicMap:{};
					 var items = dicMap[dicId]!=undefined?dicMap[dicId]:[];
					 var len = items.length;
					 
					 for(i=0;i<len;i++){
						 $('#'+elementId).append("<option value='"+StringUtil.escapeStr(items[i].id)+"' >"+StringUtil.escapeStr(items[i].name)+"</option>")
					 }
				 } */
				 if(typeof dprtcode == undefined || dprtcode==null || dprtcode == ''){
					dprtcode = userJgdm;
		 		}
				 if(this.data.dicMap!=undefined ){
					 var dicMap = this.data.dicMap!=undefined?this.data.dicMap:{};
					 var items = this.getDict(dicId, dprtcode);
					 var len = items.length;
					 
					 for(i=0;i<len;i++){
						 $('#'+elementId).append("<option value='"+StringUtil.escapeStr(items[i].id)+"' >"+StringUtil.escapeStr(items[i].name)+"</option>")
					 }
				 }
				 
			}
			//TODO 带处理 
			,getDicItems:function(dicId,dprtcode){ 				
				 /* if(this.data.dicMap!=undefined ){
					 var dicMap = this.data.dicMap!=undefined?this.data.dicMap:{};
					 var items = dicMap[dicId]!=undefined?dicMap[dicId]:[];
					 return items;
				 } */
				 
				 if(typeof dprtcode == undefined || dprtcode==null || dprtcode == ''){
						console.info('未指定机构，采用默认机构');
						dprtcode = userJgdm;
				 }
				 if(this.data.dicMap!=undefined ){
					 var dicMap = this.data.dicMap!=undefined?this.data.dicMap:{};
					 var items = this.getDict(dicId, dprtcode);
					 return items;
				 } 
			}
			//TODO 带处理 
			,registerDicBySelect:function(dicId,element,dprtcode){ 
				 /* if(this.data.dicMap!=undefined ){
					 var dicMap = this.data.dicMap!=undefined?this.data.dicMap:{};
					 var items = dicMap[dicId]!=undefined?dicMap[dicId]:[];
					 var len = items.length;
					 
					 for(i=0;i<len;i++){
						 
						$(element).append("<option value='"+StringUtil.escapeStr(items[i].id)+"' >"+StringUtil.escapeStr(items[i].name)+"</option>")
						
					 }
				 } */
				if(this.data.dicMap!=undefined ){
					if(typeof dprtcode == undefined || dprtcode==null || dprtcode == ''){
						console.info('未指定机构，采用默认机构');
						dprtcode = userJgdm;
					}
					 var dicMap = this.data.dicMap!=undefined?this.data.dicMap:{};
					 var items = this.getDict(dicId, dprtcode);
					 var len = items.length;
					 
					 for(i=0;i<len;i++){
						 
						$(element).append("<option value='"+StringUtil.escapeStr(items[i].id)+"' >"+StringUtil.escapeStr(items[i].name)+"</option>")
						
					 }
				 }
			},
			//获取字典项描述，适用于数据字典在页面中title方式显示字典描述
			getDicItemDesc : function(dicId, value, dprtcode) {
				if (typeof dprtcode == undefined || dprtcode == null
						|| dprtcode == '') {
					console.info('未指定机构，采用默认机构');
					dprtcode = userJgdm;
				}
				var items = this.getDict(dicId, dprtcode);
				var len = items.length;
				for (i = 0; i < len; i++) {
					if(items[i].id == value){
						var desc = items[i].desc;
						if(typeof dprtcode == undefined || dprtcode == null
								|| dprtcode == ''){
							return value;
						}
						return desc;
					}
				}
				return value;
			}
	};

	DicAndEnumUtil.loadDicAndEnums();

	/**
	 * 访问机构代码
	 * @param dprtcode	机构代码
	 * @return dpartName 机构名称
	 * @return infoType 组织机构信息类别
	 */
	var AccessDepartmentUtil = {
		getDpartName : function(dprtcode) {
			var dpartName = '';
			if (null == accessDepartment) {
				return dpartName;
			}
			$.each(accessDepartment, function(i, obj) {
				if (dprtcode == obj.id) {
					dpartName = obj.dprtname;
				}
			});
			return dpartName;
		},
		getDpartinfoType : function(dprtcode) {
			var infoType = '';
			if (null == accessDepartment) {
				return infoType;
			}
			$.each(accessDepartment, function(i, obj) {
				if (dprtcode == obj.id) {
					var dprtInfo = obj.deptInfo;
					if(dprtInfo != null && dprtInfo.deptType != null){
						infoType = dprtInfo.deptType;
					}
				}
			});
			return infoType;
		}
	};

	/**
	 * 标记关键字
	 * @param keyword	关键字的值
	 * @param columns	需要被标记的列（数组）
	 */
	function signByKeyword(keyword, columns, path) {

		var defaultPath = "tbody tr td";
		keyword = $.trim(keyword);
		
		keyword	&& $.each(columns, function(i, obj) {
			$((path || defaultPath) + ":nth-child(" + obj + ")").each(function() {
				var content = $(this);
				var value = content.text();
				if (content.find("[name='keyword']").length >= 1) {
					content = content.find("[name='keyword']");
					value = content.text();
				} else {
					// 当td里面包含的不直接是文本内容，而是html代码嵌套，则取最里面一层的内容作为文本内容
					while (content.children().length >= 1) {
						content = content.children().first();
						value = content.text();
					}
				}
				var startIndex = value.toUpperCase().indexOf(keyword.toUpperCase());
				if (startIndex != -1) {
					content.html([StringUtil.escapeStr(value.substr(0,startIndex)),
									"<font style='color:red'>",
									StringUtil.escapeStr(value.substr(startIndex,keyword.length)),
									"</font>",
									StringUtil.escapeStr(value.substr(parseInt(startIndex)+ parseInt(keyword.length)))]
									.join(""));
				}
			});
		});
	}

	/**
	 * 格式化工具
	 */
	var FormatUtil = {
		/**
		 * 替换null或undefined，必须在生成html之后调用
		 * 默认无参为table布局，如果是非table布局，则手动输入选择器。
		 * 例如：FormatUtil.removeNullOrUndefined("#id input")，则会替换该id下的所有输入框
		 */
		removeNullOrUndefined : function(path) {
			path = path || "tbody tr td";
			$(path).each(
					function() {
						if ($(this).text() == "null"
								|| $(this).text() == "undefined") {
							$(this).text("");
						}
						if ($(this).attr("title") == "null"
								|| $(this).attr("title") == "undefined") {
							$(this).attr("title", "");
						}
					});
		}
	};

	/**
	 * 格式化字符串工具
	 */
	var StringUtil = {
		subString : function(str, length) {
			if (typeof (length) == "undefined") {
				length = 20;
			}
			if (str == null || str == '' || str == "undefined") {
				return '';
			}
			if (str.length > length) {
				str = str.substring(0, length) + "...";
			}
			return str;
		},
		title : function(str, length) {
			if (typeof (length) == "undefined") {
				length = 20;
			}
			if (str == null || str == '' || str == "undefined"
					|| str.length <= length) {
				return '';
			}
			return str;
		},
		escapeStr : function(str) {
			if (typeof (str) == 'undefined' || str == undefined || str == null) {
				return "";
			}
			if (typeof (str) != 'string') {
				return str;
			}
			str = str.replaceAll("&", "&amp;");
			str = str.replaceAll("<", "&lt;");
			str = str.replaceAll(">", "&gt;");
			//str = str.replaceAll(" ", "&nbsp;");
			str = str.replaceAll("'", "&#39;");
			str = str.replaceAll("\"", "&quot;");
			//str = str.replaceAll("\n", "<br>");
			return str;
		},
		// 替换输入框、下拉框为文本
		replaceAsText : function(selector) {
			$("#" + selector + " input:visible").each(function() {
				var content;
				if (this.type == "text") {
					content = $(this).val();
				} else if (this.type == "checkbox") {
					content = this.checked ? "是" : "否";
				}
				$(this).parent().attr("title", content);
				$(this).parent().text(content);
			});
			$("#" + selector + " select:visible").each(function() {
				var content = $(this).find("option:selected").text();
				$(this).parent().attr("title", content);
				$(this).parent().text(content);
			});
			$("#" + selector + " textarea").each(function() {
				var content = $(this).val();
				$(this).parent().attr("title", content);
				$(this).parent().text(content);
			});
		}
	};

	/**
	 * 全选反选工具,selectAllId全选id,selectNodeId列表id
	 */
	var SelectUtil = {
		selectAll : function(selectAllId, selectNodeId) {//全选或不选
			var this_ = this;
			//如果选中全选
			if ($("#" + selectAllId).is(":checked")) {
				$("#" + selectNodeId).find("tr input[type='checkbox']").each(
						function() {
							$(this).attr("checked", 'true');//点击全选
						});
			} else {
				$("#" + selectNodeId).find("tr input[type='checkbox']:checked")
						.each(function() {
							$(this).removeAttr("checked");//点击取消全选
						});
			}
		},
		selectNode : function(selectAllId, selectNodeId) {//单选后是否全选
			var this_ = this;
			var checkNum = $("#" + selectNodeId).find(
					"tr input[type='checkbox']:checked").length;//选中个数
			var totalNum = $("#" + selectNodeId).find("tr").length;//总数
			if (checkNum == totalNum && totalNum != 0) {
				$("#" + selectAllId).attr("checked", 'true');//选中全选
			} else {
				$("#" + selectAllId).removeAttr("checked");//取消全选
			}
		},
		checkRow : function(e, selectAllId, selectNodeId) {//选中一行
			if ($(e).is(":checked")) {
				$(e).removeAttr("checked");
			} else {
				$(e).attr("checked", "true");
			}
			this.selectNode(selectAllId, selectNodeId);
		},
		rowonclick : function(id) {
			$("#" + id + " tr").click(
					function(event) {
						// 避免复选框重复选择
						if ($(event.target).attr("type") == "checkbox") {
							return;
						}
						var checked = $(this).find("input[type='checkbox']")
								.is(":checked");
						$(this).find("input[type='checkbox']").attr("checked",
								!checked);
					});
		},
		selectRadioRow : function(obj) {//选中一行radio
			$(obj).find("input[type='radio']").attr("checked", true);
		},
		clickRow : function(index, id, name) {//选中一行checkbox,id:tbody id,name:row name
			var $checkbox1 = $("#" + id + " :checkbox[name='" + name + "']:eq("
					+ index + ")");
			var $checkbox2 = $(".sticky-col :checkbox[name='" + name + "']:eq("
					+ index + ")");
			var checked = $checkbox1.is(":checked");
			$checkbox1.attr("checked", !checked);
			$checkbox2.attr("checked", !checked);
		},
		
		/* 选择复选框 */
		selectCheckbox : function(e,index,id,name,obj){
			e = e || window.event;  
		    if(e.stopPropagation) { //W3C阻止冒泡方法  
		        e.stopPropagation();  
		    } else {  
		        e.cancelBubble = true; //IE阻止冒泡方法  
		    }
		    var $checkbox1 = $("#" + id + " :checkbox[name='" + name + "']:eq("
	 				+ index + ")");
	 		var $checkbox2 = $(".sticky-col :checkbox[name='" + name + "']:eq("
	 				+ index + ")");
		    if(!$(obj).parents("table").hasClass("sticky-col")){
		    	var checked = $checkbox1.is(":checked");
		 		$checkbox1.attr("checked", checked);
		 		$checkbox2.attr("checked", checked);	
		    }else{
		    	var checked = $checkbox2.is(":checked");
		 		$checkbox1.attr("checked", checked);
		 		$checkbox2.attr("checked", checked);
		    }
		},
		performSelectAll : function(selectNodeId) {//全选
			$(":checkbox", $("#" + selectNodeId)).attr("checked", true);
		},
		performSelectClear : function(selectNodeId) {//清空选中
			$(":checkbox", $("#" + selectNodeId)).removeAttr("checked");//点击取消全选
		}
	};

	/**
	 * 时间工具
	 * 使用方法： TimeUtil.operateTime("152.168", "0", TimeUtil.Separator.DOT, TimeUtil.Operation.ADD);
	 * 后2个参数可为空，默认分隔符是“：”，默认操作是“+”，
	 */
	var TimeUtil = {
		compareDate : function(d1Str, d2Str) {
			return ((new Date(d1Str.replace(/-/g, "\/"))) > (new Date(d2Str
					.replace(/-/g, "\/"))));
		},
		//日期相减返回天数
		dateMinus : function(d1Str, d2Str) {
			var d1date = new Date(d1Str.replace(/-/g, "/"));
			var d2date = new Date(d2Str.replace(/-/g, "/"));
			var days = d2date.getTime() - d1date.getTime();
			var day = parseInt(days / (1000 * 60 * 60 * 24));
			return day; 
		},
		//获取当前数据库日期
		getCurrentDate : function(obj) {
			AjaxUtil.ajax({
				url : basePath + "/common/sysdate?t=" + new Date().getTime(),
				type : "get",
				success : function(data) {
					if (data && data.sysdate) {
						data.sysdate = data.sysdate.substr(0, 10);
						if (typeof (obj) == "undefined") {
							return;
						}
						if (typeof (obj) == "function") {
							obj(data.sysdate);
							return;
						}
						if (typeof (obj) == "string") {
							$(obj).val(data.sysdate);
						}
					}
				}
			});
		},
		//获取当前数据库时间
		getCurrentTime : function(obj) {
			AjaxUtil.ajax({
				url : basePath + "/common/sysdate?t=" + new Date().getTime(),
				type : "get",
				success : function(data) {
					if (data && data.sysdate) {
						if (typeof (obj) == "undefined") {
							return;
						}
						if (typeof (obj) == "function") {
							obj(data.sysdate);
							return;
						}
						if (typeof (obj) == "string") {
							$(obj).val(data.sysdate);
						}
					}
				}
			});
		},
		//根据日期、天数、运算符获取日期
		dateOperator : function(date, days, operator) {
			if (typeof (date) == "undefined" || date == null || date == '') {
				return '';
			}
			if (typeof (days) == "undefined" || days == null || days == '') {
				return '';
			}
			if (typeof (operator) == "undefined") {
				operator = this.Operation.ADD;
			}
			date = date.replace(/-/g, "/"); //更改日期格式  

			var nd = new Date(date);
			nd = nd.valueOf();
			if (operator == this.Operation.ADD) {
				nd = nd + days * 24 * 60 * 60 * 1000;
			} else if (operator == this.Operation.SUBTRACT) {
				nd = nd - days * 24 * 60 * 60 * 1000;
			} else {
				return false;
			}
			nd = new Date(nd);
			var y = nd.getFullYear();
			var m = nd.getMonth() + 1;
			var d = nd.getDate();
			if (m <= 9)
				m = "0" + m;
			if (d <= 9)
				d = "0" + d;
			var cdate = y + "-" + m + "-" + d;
			return cdate;
		},
		//根据日期、月数、运算符获取日期
		dateOperator4Month : function(date, m, operator) {
			if (typeof (date) == "undefined" || date == null || date == '') {
				return '';
			}
			if (typeof (m) == "undefined" || m == null || m == '') {
				return '';
			}
			if (typeof (operator) == "undefined") {
				operator = this.Operation.ADD;
			}
			date = date.replace(/-/g, "/"); //更改日期格式  

			var nd = new Date(date);
			if (operator == this.Operation.ADD) {
				nd.setMonth(nd.getMonth() + m*1);
			} else if (operator == this.Operation.SUBTRACT) {
				nd.setMonth(nd.getMonth() - m*1);
			} else {
				return false;
			}
			var y = nd.getFullYear();
			var m = nd.getMonth() + 1;
			var d = nd.getDate();
			if (m <= 9)
				m = "0" + m;
			if (d <= 9)
				d = "0" + d;
			var cdate = y + "-" + m + "-" + d;
			return cdate;
		},
		// 分隔符
		Separator : {
			COLON : ":",
			DOT : "."
		},
		// 操作
		Operation : {
			ADD : "+",
			SUBTRACT : "-"
		},
		// 时间操作
		operateTime : function(time1, time2, separator, operation) {
			time1 = "" + time1;
			time2 = "" + time2;
			var minuteAllTotal = 0;
			// 转换成分钟
			var minute1Total = this.convertToMinute(time1);
			var minute2Total = this.convertToMinute(time2);
			separator = separator || this.Separator.COLON;
			operation = operation || this.Operation.ADD;
			if (operation == this.Operation.ADD) {
				minuteAllTotal = minute1Total + minute2Total;
			} else if (operation == this.Operation.SUBTRACT) {
				minuteAllTotal = minute1Total - minute2Total;
			}
			return this.convertToHour(minuteAllTotal, separator);
		},
		// 转换成分钟
		convertToMinute : function(time) {
			var hour = parseFloat(this.getHour(time));
			var minute = parseFloat(this.getMinute(time));
			if (isNaN(hour) || isNaN(minute)) {
				return 0;
			}
			return hour * 60 + minute;
		},
		// 转换成小时
		convertToHour : function(minuteTotal, separator) {
			if(!minuteTotal && minuteTotal != 0){
				return null;
			}
			var reverseFlag = false;
			if (minuteTotal < 0) {
				reverseFlag = true;
				minuteTotal = 0 - minuteTotal;
			}
			var hour = parseInt(minuteTotal / 60);
			var minute = minuteTotal % 60;
			if(minute == 0){
				return (reverseFlag ? "-" : "") + hour;
			}
			return (reverseFlag ? "-" : "") + hour + separator
					+ (minute < 10 ? "0" + minute : minute);
		},
		// 获取小时
		getHour : function(time) {
			var hour = time;
			$.each(TimeUtil.Separator, function(i, obj) {
				if (time.indexOf(obj) != -1) {
					hour = time.split(obj)[0];
					return false;
				}
			});
			return hour;
		},
		// 获取分钟
		getMinute : function(time) {
			var minute = 0;
			$.each(this.Separator, function(i, obj) {
				if (time.indexOf(obj) != -1) {
					minute = time.split(obj)[1];
					if (time.indexOf("-") != -1) {
						minute = 0 - minute;
					}
					return false;
				}
			});
			return minute;
		},
		// 组合时间
		combine : function(hour, minute, separator) {
			return (hour || "0") + separator + (minute || "0");
		},
		// 添加时间验证 
		addTimeValidate : function(selector) {
			var input = $(selector);
			if (input.val().indexOf(":") == -1) {
				input.val(input.val() + ":");
			}

			input.keyup(function() {
				// 当前输入框对象
				var obj = $(this);
				// 时间正则表达式
				var reg = /^([0-9]+)?((\:)?[0-5]?[0-9]?)?$/;
				// 当前输入值
				var value = obj.val();
				var value_new = obj.val();
				// 不满足正则回退
				while (!(reg.test(value_new)) && value_new.length > 0) {
					value_new = value_new.substr(0, value_new.length - 1);
				}
				var position = -1;
				// 删除冒号再添加
				if (value_new.indexOf(":") == -1) {
					value_new += ":";
					position = value_new.length - 1;
				}
				if (value != value_new) {
					obj.val(value_new);
				}
				// 输入框光标移至冒号前
				if (position != -1) {
					var o = obj.get(0);
					if (o.createTextRange) {//IE浏览器
						var range = o.createTextRange();
						range.collapse(true);
						range.moveEnd("character", position);
						range.moveStart("character", position);
						range.select();
					} else {
						o.setSelectionRange(position, position);
						o.focus();
					}
				}

			});

			input.blur(function() {
				// 当前输入框对象
				var obj = $(this);
				// 当前输入值
				var value = obj.val();
				if (value.indexOf(":") != -1) {
					var hour = value.split(":")[0] || "0";
					var minute = value.split(":")[1] || "0";
					// 分钟补零
					if (parseInt(minute) < 10) {
						minute = "0" + parseInt(minute);
					}
					value = hour + ":" + minute;
				}
				obj.val(value);
			});
		}
	};

	/**
	 * 列表收缩展开工具
	 * 使用方法： CollapseOrExpandUtil.collapseOrExpandAll(this);
	 * th_class为th的class选择器;td_class为td隐藏div的class选择器;table_id为table的id,主要是动态表格行展开时样式,可为空
	 */
	CollapseOrExpandUtil = {
		id : "CollapseOrExpandUtil",
		collapseOrExpandAll : function(obj) {
			var this_ = this;
			var flag = $(obj).hasClass("downward");
			var th_class = $(obj).attr("th_class");
			var td_class = $(obj).attr("td_class");
			var table_id = $(obj).attr("table_id");
			if (flag) {
				$("." + th_class).removeClass("downward").addClass("upward");
				$("." + td_class).fadeIn(500);
				$("." + td_class).prev().removeClass("icon-caret-down")
						.addClass("icon-caret-up");
			} else {
				$("." + th_class).removeClass("upward").addClass("downward");
				$("." + td_class).hide();
				$("." + td_class).prev().removeClass("icon-caret-up").addClass(
						"icon-caret-down");
			}
			if (table_id != null && table_id != ''
				&& typeof table_id != undefined) {
				new Sticky({
					tableId : table_id
				});
			}
		},
		collapseOrExpandRow : function(td_class, table_id) {
			if (table_id != null && table_id != ''
					&& typeof table_id != undefined) {
				new Sticky({
					tableId : table_id
				});
			}
			var flag = $("." + td_class).next().is(":hidden");
			if (flag) {
				$("." + td_class).next().fadeIn(500);
				$("." + td_class).removeClass("icon-caret-down");
				$("." + td_class).addClass("icon-caret-up");
			} else {
				$("." + td_class).next().hide();
				$("." + td_class).removeClass("icon-caret-up");
				$("." + td_class).addClass("icon-caret-down");
			}
		},
		collapseOrExpand : function(this_, table_id) {
			var flag = $(this_).next().is(":hidden");
			if (flag) {
				$(this_).next().fadeIn(500);
				$(this_).removeClass("icon-caret-down");
				$(this_).addClass("icon-caret-up");
			} else {
				$(this_).next().hide();
				$(this_).removeClass("icon-caret-up");
				$(this_).addClass("icon-caret-down");
			}
			if (table_id != null && table_id != ''
				&& typeof table_id != undefined) {
				new Sticky({
					tableId : table_id
				});
			}
		}
	}

	/**
	 * 列表收缩展开工具
	 * 使用方法： ModalUtil.showModal(id);弹窗modal显示
	 * ModalUtil.modalBodyHeight(id)设置高度
	 */
	ModalUtil = {
		id : "ModalUtil",
		showModal : function(id) {
			var this_ = this;
			$("#" + id).modal("show");
// 			$('#' + id).on('shown.bs.modal', function() {
// 				this_.modalBodyHeight(id);
// 				$("#"+id+" .modal-body").prop('scrollTop','0');
// 			});
		},
		showSearchModal : function(parentid, id, paginationId) {
// 			var this_ = this;
// 			$("#" + id).modal("show");
// 			this_.searchModal(parentid, id, paginationId);
// 			$('#' + id).on('shown.bs.modal', function() {
// 				this_.searchModal(parentid, id, paginationId);
// 			});
			//隐藏Modal时验证销毁重构
			/* $("#"+id).on("hidden.bs.modal", function() {
				this_.searchModal(parentid,id,paginationId);
			}); */
		},
		modalBodyHeight : function(id) {
			//window高度
// 			var windowHeight = $(window).height()
// 			//modal-footer的高度
// 			var modalFooterHeight = $("#" + id + " .modal-footer")
// 					.outerHeight() || 0;
// 			//modal-header 的高度
// 			var modalHeaderHeight = $("#" + id + " .modal-header")
// 					.outerHeight() || 0;
// 			//modal-dialog的margin-top值
// 			var modalDialogMargin = parseInt($("#" + id + " .modal-dialog")
// 					.css("margin-top")) || 0
// 			//modal-body 的设置
// 			var modalBodyHeight = windowHeight - modalFooterHeight
// 					- modalHeaderHeight - modalDialogMargin * 2 - 8;
// 			$("#" + id + " .modal-body").attr(
// 					'style',
// 					'max-height:' + modalBodyHeight
// 							+ 'px !important;overflow: auto;margin-top:0px;');
		},
		searchModal : function(parentid, id, paginationId) {

// 			var windowHeight = $(window).height();

// 			var modalFooterHeight = $("#" + id + " .modal-footer")
// 					.outerHeight() || 0;

// 			var modalHeaderHeight = $("#" + id + " .modal-header")
// 					.outerHeight() || 0;

// 			var modalDialogMargin = parseInt($("#" + id + " .modal-dialog")
// 					.css("margin-top")) || 0;

// 			var modalSearch = $("#" + id + " .modalSearch").outerHeight() || 0;

// 			var modalpagination = $("#" + paginationId).outerHeight() || 0;

// 			if (parentid == null || parentid == "") {

// 				var modalBodyHeight = windowHeight - modalFooterHeight
// 						- modalHeaderHeight - modalDialogMargin * 2 - 8;

// 			} else {
// 				var parentMargin = parseInt($("#" + parentid + " .modal-dialog")
// 						.css("margin-top")) || 0;

// 				var parentHeader = $("#" + parentid + " .modal-header")
// 						.outerHeight() || 0;

// 				var parentFooter = $("#" + parentid + " .modal-footer")
// 						.outerHeight() || 0;

// 				$("#" + id).css("margin-top", (parentHeader + 10) + "px");

// 				var parentHeight = parseInt($("#" + parentid).css("height")) || 0;

// 				var modalBodyHeight = parentHeight - parentHeader - 10
// 						- parentFooter - 10 - modalFooterHeight
// 						- modalHeaderHeight - 8 - parentMargin * 2;

// 			}
// 			var tableSearchheight = modalBodyHeight - modalpagination
// 					- modalSearch - 18;

// 			$("#" + id + " #searchTable").attr(
// 					'style',
// 					'max-height:' + tableSearchheight
// 							+ 'px !important;overflow: auto;');

		}
	}

	var TableUtil = {

		/**
		 * 选中行可见
		 * @param row	目标行	
		 * @param topDiv	上方div
		 * @param corrected_value	修正值
		 */
		makeTargetRowVisible : function(row, topDiv, corrected_value) {

			// 选中行的高度
			var row_offset = row.offset().top;
			// table的高度
			var table_offset = topDiv.offset().top;
			// thead的高度
			var table_head = topDiv.find("thead").outerHeight();
			// 修正值
			corrected_value = corrected_value || 1;

			// 需要偏移的高度
			var offset = row_offset - table_offset - table_head
					- corrected_value;
			if (offset > 0) {
				topDiv.scrollTop(offset);
			}

		},
		
		/**
		 * table添加title
		 * @param selector	选择器
		 */
		addTitle : function(selector){
			selector = selector || "table tr td";
			$(selector).each(function(){
				if(!$(this).attr("title") && $.trim($(this).text()) != ""){
					$(this).attr("title", $(this).text());
				}
			});
		},
		showDisplayContent:function(){
			$(".displayContent").show();
			App.resizeHeight();
		},
		hideDisplayContent:function(){
			$(".displayContent").hide();
			App.resizeHeight();
		},
		//重置排序图标
		resetTableSorting : function(tableId){
			$(".sorting_desc", $("#"+tableId)).removeClass("sorting_desc").addClass("sorting");
			$(".sorting_asc", $("#"+tableId)).removeClass("sorting_asc").addClass("sorting");
		}
	};
	 
	function goHistory(obj) {
		obj.history.go(-1)
	}
	
	
	

	/**监控值检查*/
	var monitor_setting_check = {
		/**是合法飞行循环*/
		isFh: function($obj,value){
			 
			var result = true;
			var reg = /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/;
			var value = $obj.val();
			value = value.replace(/：/g, ":");
			while(!(reg.test(value)) && value.length > 0){
				value = value.substr(0, value.length-1);
				result = false;
		    }
			$obj.val(value);
			return result;
		},
		/**是合法飞行小时*/
		isFc: function($obj,value){
			var reg =  /^[1-9]\d*$/;
			  
			//value = value.replace(/：/g, ":");
			if(!(reg.test(value)) && value.length > 0){
				value = value.substr(0, value.length-1);
				$obj.val('0');
				return false;
		    }
			return true;
		},
	}
	
</script>
<%@ include file="/WEB-INF/views/alert.jsp"%>
<style>

.ajax-file-upload_ext {
    background: #428bca  none repeat scroll 0 0;
    border: medium none;
    border-radius: 4px;
    color: #fff;
    cursor: pointer;
     display: inline-block; 
    font-family: Arial,Helvetica,sans-serif;
    font-size: 12px;
    /* font-weight: bold; */
    height: 34px;
    width: 48px;
    line-height: 16px;
    margin: 0;
    /* padding: 3px 22px 0 22px; */
    text-align: center;
    text-decoration: none;
    vertical-align: middle;
}

</style>

