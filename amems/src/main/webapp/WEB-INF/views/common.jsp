<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script type="text/javascript" src="${ctx}/static/js/tool/easyui/jquery-1.7.2.min.js"></script> 
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css" /> 
<link rel="stylesheet" href="${ctx}/static/css/work-table.css" type="text/css"></link>

<link rel="stylesheet" type="text/css" href="${ctx}/static/js/tool/easyui/themes/default/easyui.css" />   
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/tool/easyui/themes/icon.css" />
<script type="text/javascript" src="${ctx}/static/js/tool/easyui/jquery.easyui.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/tool/easyui/locale/easyui-lang-zh_CN.js"></script>
<!-- 日期控件 -->
<script type="text/javascript" src="${ctx}/static/js/tool/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="${ctx}/static/js/ms/common.js"></script>

<script type="text/javascript" src="${ctx}/static/js/ms/privilege.js"></script>
<script type="text/javascript" src="${ctx}/static/js/ms/dic.js"></script>


<script type="text/javascript">var btnPriCodeList = '${btnPriCodeList}';</script>
<script type="text/javascript">var basePath = '${ctx}';</script>