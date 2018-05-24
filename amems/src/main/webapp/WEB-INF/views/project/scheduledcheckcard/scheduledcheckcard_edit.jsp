<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改修订通知书</title>
<%@ include file="/WEB-INF/views/common.jsp"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
</head>
<body>
<form id="modifyRevisionNoticeBookForm" method="post" enctype="multipart/form-data" accept-charset="utf-8">
	<table class="commonTab" border="1" style="border-color:#e3e8f9;">
		<tr bgcolor="#f4f4f4">
			<td height="30" align="left" colspan="5" style="margin-left:20px;"><b>修订通知书信息</b></td>
			<td align="right">
			   <a id="saveRevisionNoticeBook" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);">保存</a>
			   <a id="submitRevisionNoticeBook" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0);">提交</a>
			</td>
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
			   </c:choose>
			</td>
		</tr>
		
		<tr>
			<td width="15%" height="30">技术指令号：</td>
			<td align="left"><span id="jszlh">${revisionNoticeBook.jszlh}</span></td>
		
			<td width="15%" height="30">通知书类型：</td>
			<td>
			   <select id="tzslx">
			      <option value="1" <c:if test="${revisionNoticeBook.tzslx == 1}">selected</c:if>>维修方案</option>
			      <option value="2" <c:if test="${revisionNoticeBook.tzslx == 2}">selected</c:if>>MEL</option>
			      <option value="3" <c:if test="${revisionNoticeBook.tzslx == 3}">selected</c:if>>工单</option>
			   </select>
			</td>
			
			<td width="15%" height="30">机型：</td>
			<td>
			   <select id="jx">
			      <option value="EC305" <c:if test="${revisionNoticeBook.tzslx eq 'EC305'}">selected</c:if>>EC305</option>
			      <option value="S76D" <c:if test="${revisionNoticeBook.tzslx eq 'S76D'}">selected</c:if>>S76D</option>
			   </select>
			</td>
		</tr>
		
		<tr>
			<td width="15%" height="30">评估单号：</td>
			<td align="left"><input id="pgdh" type="text" style="width:210px;" class="easyui-combogrid" />
		      <a href="javascript:void(0)" 
		         class="easyui-linkbutton" 
			     data-options="iconCls:'icon-search',plain:'false'" 
			     onclick="openDialog();">
		      </a>
			</td>
			
			<td width="15%" height="30">参考资料：</td>
			<td><input id="ckzl" type="text" style="width:90%" value="${revisionNoticeBook.ckzl}" /></td>
		
			<td width="15%" height="30"><span style="color:red">*</span>修订主题：</td>
			<td><input id="xdzt" type="text" style="width:90%" value="${revisionNoticeBook.xdzt}" /></td>
		</tr>
		
		<tr>
			<td width="15%" height="30"><span style="color:red">*</span>修订人：</td>
			<td align="left">
			   <select id="xdr">
			      <c:forEach var="item" items="${xdrList}" varStatus="status">
			         <option value="${item.id}" <c:if test="${item.id eq revisionNoticeBook.xdrid}">selected</c:if>>${item.realname}</option>
			      </c:forEach>
			   </select>
			</td>
			
			<td width="15%" height="30"><span style="color:red">*</span>修订期限：</td>
			<td align="left" colspan="3">
			   <input id="xdqx" 
			          type="text"
			          class="easyui-datebox"
			          style="width:100%"
			          data-options="formatter:myformatter,parser:myparser"
			          value="<fmt:formatDate value="${revisionNoticeBook.xdqx}"
			          pattern="yyyy-MM-dd" />"
			          minDate="%y-{%M-1}-%d"
			          maxDate="{%y+2}-%M-%d" />
			</td>
		</tr>
		
		<tr>
			<td height="30" align="right"><span style="color:red">*</span>修订内容:</td>
			<td colspan="5"><textarea id="xdnr" style="width:99%;height:300px;">${revisionNoticeBook.xdnr}</textarea></td>
		</tr>
	</table>
</form>

<!-- 评估单列表模态框 -->
<div id="pgdh-dialog" class="easyui-dialog" style="margin-left:3px;" align="center">
   <form id="modifyPgdhListForm" method="post" enctype="multipart/form-data" accept-charset="utf-8">
      <table border="0" width="600" cellspacing="0" cellpadding="2">
      	 <tr bgcolor="#f4f4f4">
			<td height="30" colspan="3"><b>查询条件</b></td>
		 </tr>
         <tr>
            <td height="30" align="right">评估单号：</td>
            <td><input id="queryPgdh" type="text" /></td>
            
            <td align="right">
               <a id="modifyQueryPgdhList" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </td>
         </tr>
      </table>
      <div style="margin-top:5px;"></div>
      <table id="modifyPgdhList"></table>
   </form>
</div>

<!-- 隐藏域begin -->
<input id="id" type="hidden" value="${revisionNoticeBook.id}" />
<input id="pgdids" type="hidden" value="${pgdids}" />
<input id="pgdhs" type="hidden" value="${pgdhs}" />
<!-- 隐藏域begin -->

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
<script type="text/javascript" src="${ctx}/static/js/thjw/project/revisionNoticeBook/modify_revisionNoticeBook.js"></script>
</body>
</html>