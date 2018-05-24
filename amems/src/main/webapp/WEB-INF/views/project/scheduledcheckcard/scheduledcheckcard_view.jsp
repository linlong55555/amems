<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看修订通知书</title>
<%@ include file="/WEB-INF/views/common.jsp"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
</head>
<body>
<form id="modifyRevisionNoticeBookForm" method="post" enctype="multipart/form-data" accept-charset="utf-8">
	<table class="commonTab" border="1" style="border-color:#e3e8f9;">
		<tr bgcolor="#f4f4f4">
			<td height="30" align="left" colspan="6" style="margin-left:20px;"><b>修订通知书信息</b></td>
		</tr>
		
		<tr>
			<td width="15%" height="30">制单人：</td>
			<td align="left">${zdr.realname}</td>
			
			<td width="15%" height="30">制单日期：</td>
			<td align="left"><fmt:formatDate value="${revisionNoticeBook.zdsj}" pattern="yyyy-MM-dd" /></td>
			
			<td width="15%" height="30">状态：</td>
			<td align="left">
			  <c:choose>
			     <c:when test="${revisionNoticeBook.zt == 1}">未提交</c:when>
			     <c:when test="${revisionNoticeBook.zt == 2}">已提交</c:when>
			     <c:when test="${revisionNoticeBook.zt == 9}">指定结束</c:when>
			   </c:choose>
			</td>
		</tr>
		
		<tr>
			<td width="15%" height="30">技术指令号：</td>
			<td align="left"><span id="jszlh">${revisionNoticeBook.jszlh}</span></td>
		
			<td width="15%" height="30">通知书类型：</td>
			<td align="left">
			   <c:choose>
			     <c:when test="${revisionNoticeBook.tzslx == 1}">维修方案</c:when>
			     <c:when test="${revisionNoticeBook.tzslx == 2}">MEL</c:when>
			     <c:when test="${revisionNoticeBook.tzslx == 3}">工单</c:when>
			   </c:choose>
			</td>
			
			<td width="15%" height="30">机型：</td>
			<td align="left">${revisionNoticeBook.jx}</td>
		</tr>
		
		<tr>
			<td width="15%" height="30">评估单号：</td>
			<td align="left"><input id="pgdh" type="text" style="width:210px;" class="easyui-combogrid" /></td>
			
			<td width="15%" height="30">参考资料：</td>
			<td align="left">${revisionNoticeBook.ckzl}</td>
		
			<td width="15%" height="30">修订主题：</td>
			<td align="left">${revisionNoticeBook.xdzt}</td>
		</tr>
		
		<tr>
			<td width="15%" height="30">修订人：</td>
			<td align="left">${xdr.realname}</td>
			
			<td width="15%" height="30">修订期限：</td>
			<td align="left" colspan="3"><fmt:formatDate value="${revisionNoticeBook.xdqx}" pattern="yyyy-MM-dd" /></td>
		</tr>
		
		<tr>
			<td height="15%" align="right">修订内容：</td>
			<td align="left" colspan="5">${revisionNoticeBook.xdnr}</td>
		</tr>
	</table>
</form>

<script>
//加载事件
$(document).ready(function() {
  //浏览器名称
  var browserName = navigator.userAgent.toLowerCase();

  String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
  };
  
  //关闭评估单列表对话框
  $('#pgdh-dialog').dialog('close');

  //评估单号列表
  var pgdhList = '${pgdhList}';
  
  //json对象
  var jsonObj = eval('('+pgdhList+')');
  
  //评估单下拉列表框加载数据
  $("#pgdh").combogrid("grid").datagrid("loadData", jsonObj);
  
  //评估单文本框赋值
  $("#pgdh").combogrid("setValue", '${pgdhs}');
});
</script>

<!-- 引入js脚本 -->
<script type="text/javascript" src="${ctx}/static/js/tool/easyui/plugins/jquery.combo.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/revisionNoticeBook/view_revisionNoticeBook.js"></script>
</body>
</html>