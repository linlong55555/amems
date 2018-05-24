<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

<div id="burstification_view_Modal" style='display: none;padding:0px;width:100px;' >
	<!-- 表格 -->
	<div class="padding-left-0 padding-right-0 padding-bottom-0 padding-top-0" style="margin:0px;width:100%;">
		<ul style="list-style:none;" class='burstification_style'>
			<li class='bottom-line'><a href="javascript:burstification_view_Modal.openWinAdd();"><i class='fa fa-plus'></i>创建新工包</a></li>
			<li id="existsPackage_li" class='packageslist'>添加到现有工包:</li>
			<div id="existsPackage" style="overflow-y: auto;height: 300px;">
				<li><a href="javascript:burstification_view_Modal.openWinAdd();">50-2934</a></li>
				<li><a href="javascript:burstification_view_Modal.openWinAdd();">50-2935</a></li>
				<li><a href="javascript:burstification_view_Modal.openWinAdd();">50-2936</a></li>
				<li><a href="javascript:burstification_view_Modal.openWinAdd();">50-2937</a></li>
			</div>
		</ul>
	</div>
	<div class='clearfix'></div>
</div>
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenancemonitoring/burstification_view_win.js"></script>
