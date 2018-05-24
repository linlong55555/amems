<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>定检工卡</title>
<%@ include file="/WEB-INF/views/common.jsp"%>
<style>
   .revisionNoticeBookOperate {
      text-decoration:none;
      color:blue;
    }
</style>
</head>
<body>
<table border="0" width="100%" cellspacing="0" cellpadding="4">
       <tr bgcolor="#f4f4f4">
		<td height="30" colspan="7"><b>查询条件</b>
		</td>
	</tr>
	<tr>
		<td width="10%" height="30" align="left">查询关键字：</td>
		<td width="10%"><input type="text" id="roleName" name="roleName"  />
		</td>
		 
		<td ><a id="btnQuery1" href="javascript:void(0);"
			class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		</td>
	</tr>
</table>

<!-- 需要 显示飞机机型，定检项目编号， -->
<!-- 
b_g_013 定检工卡
b_g_01301 定检相关工卡

b_g_006 工单基础表
b_g_00601 航材耗材工具信息
b_g_00602 工作内容
b_g_00603 工单附件表
b_g_00604 执行对象明细

查询条件：
定检工卡编号  
工作主题
关联定检项目
厂家工卡
 -->

<div style="margin-top:10px;"></div>

<!-- 数据表格 -->
<table id="grid"></table>

<!-- 引入js脚本 -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project/scheduledcheckcard/scheduledcheckcard_main.js"></script>
</body>
</html>
