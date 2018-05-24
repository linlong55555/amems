<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div id="history_attach_alert_Modal" style='display: none;padding:0px;'>
	<!-- 表格 -->
	<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 padding-bottom-0 padding-top-0" style="overflow-x: auto;margin:0px;width:100%;">
			<table class="table-thin table-set text-left webui-popover-table" style='border:0px;margin-bottom:0px !important;' width='100%'>
				<thead>
					<tr style='height:35px;'>
						<th width='30%'>
						   <div class="font-size-12" style="line-height: 14px;">附件类型</div>
				           <div class="font-size-9" style="line-height: 14px;">Type</div>
					   	</th>
					   	<th width='70%'>
						   <div class="font-size-12" style="line-height: 14px;">文件</div>
				           <div class="font-size-9" style="line-height: 14px;">File</div>
					   	</th>
					</tr>
				</thead>
				<tbody id="history_list">
				</tbody>
		</table>
	</div>
	<div class='clearfix'></div>
</div>
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/history_attach_win.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>