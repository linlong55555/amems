<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增修订通知书</title>
<%@ include file="/WEB-INF/views/common.jsp"%>
</head>
<body>
<form id="addRevisionNoticeBookForm" method="post" enctype="multipart/form-data" accept-charset="utf-8">
	<table class="commonTab"  style="border:1px solid #e3e8f9;">
		<tr bgcolor="#f4f4f4">
			<td height="30" align="left" colspan="5" style="margin-left:20px;"><b>修订通知书信息</b></td>
			<td align="right">
			   <a id="saveRevisionNoticeBook" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);">保存</a>
			   <a id="submitRevisionNoticeBook" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0);">提交</a>
			</td>
		</tr>
		
		<tr>
			<td width="15%" height="30">制单人：</td>
			<td align="left"><span id="zdr">${user.realname}</span></td>
			
			<td width="15%" height="30">制单日期：</td>
			<td align="left"><fmt:formatDate value="${zdrq}" pattern="yyyy-MM-dd" /></td>
			
			<td width="15%" height="30">状态：</td>
			<td align="left">未提交</td>
		</tr>
		
		<tr>
		    <!-- 
			<td width="15%" height="30">技术指令号：</td>
			<td align="left"><span id="jszlh">${jszlh}</span></td>
			 -->
		
			<td width="15%" height="30">通知书类型：</td>
			<td>
			   <select id="tzslx">
			      <option value="1">维修方案</option>
			      <option value="2">MEL</option>
			      <option value="3">工单</option>
			   </select>
			</td>
			
			<td width="15%" height="30">机型：</td>
			<td>
			   <select id="jx">
			      <option value="EC305">EC305</option>
			      <option value="S76D">S76D</option>
			   </select>
			</td>
			
			<td width="15%" height="30">评估单号：</td>
			<td align="left"><input id="pgdh" type="text" style="width:210px;" class="easyui-combogrid" />
		      <a href="javascript:void(0)" 
		         class="easyui-linkbutton" 
			     data-options="iconCls:'icon-search',plain:'false'" 
			     onclick="openDialog();">
		      </a>
			</td>
		</tr>
		
		<tr>
			<td width="15%" height="30">参考资料：</td>
			<td><input id="ckzl" type="text" style="width:100%" /></td>
			
			<td width="15%" height="30"><span style="color:red">*</span>修订主题：</td>
			<td><input id="xdzt" type="text" style="width:100%" /></td>
			
			<td width="15%" height="30">修订人：</td>
			<td align="left"><span id="xdr">${user.realname}</span></td>
		</tr>
		
		<tr>
			<td width="15%" height="30"><span style="color:red">*</span>修订期限：</td>
			<td align="left" colspan="5">
			   <input id="xdqx" 
			          type="text" 
				      class="easyui-datebox" 
				      style="width:100%"
				      data-options="formatter:myformatter,parser:myparser" 
				      minDate="%y-{%M-1}-%d" 
				      maxDate="{%y+2}-%M-%d" 
				      value="<fmt:formatDate value="${xdqx}" pattern="yyyy-MM-dd" />" />
			</td>
		</tr>
		
		<tr>
			<td height="30" align="right"><span style="color:red">*</span>修订内容:</td>
			<td colspan="5"><textarea id="xdnr" style="width:99%;height:300px;"></textarea>&nbsp;</td>
		</tr>
	</table>
</form>

<!-- 评估单列表模态框 -->
<div id="pgdh-dialog" class="easyui-dialog" style="margin-left:3px;" align="center">
   <form id="addPgdhListForm" method="post" enctype="multipart/form-data" accept-charset="utf-8">
      <table border="0" width="600" cellspacing="0" cellpadding="2">
		 <tr bgcolor="#f4f4f4">
			<td height="30" colspan="3"><b>查询条件</b></td>
		 </tr>
         <tr>
            <td height="30" align="right">评估单号：</td>
            <td><input id="queryPgdh" type="text" /></td>
            
            <td align="right">
               <a id="addQueryPgdhList" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </td>
         </tr>
      </table>
      <div style="margin-top:5px;"></div>
      <table id="addPgdhList"></table>
   </form>
</div>

<!-- 隐藏域begin -->
<input id="zdrid" type="hidden" value="${user.id}" />
<input id="xdrid" type="hidden" value="${user.id}" />
<input id="dprtcode" type="hidden" value="${user.jgdm}" />
<input id="pgdids" type="hidden" />
<input id="pgdhs" type="hidden" />
<!-- 隐藏域begin -->

<!-- 引入js脚本 -->
<script type="text/javascript" src="${ctx}/static/js/tool/easyui/plugins/jquery.combo.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/revisionNoticeBook/add_revisionNoticeBook.js"></script>
</body>
</html>