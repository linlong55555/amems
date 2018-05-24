<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div id="eo_history_view_alert_Modal" style='display: none;padding:0px;'>
	<!-- 表格 -->
	<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 padding-bottom-0 padding-top-0" style="overflow-x: auto;margin:0px;width:100%;">
		<table class="table-thin table-set text-left webui-popover-table" style='border:0px;margin-bottom:0px !important;' width='100%'>
			<thead>
				<tr style='height:35px;'>
					<th class="colwidth-15">
					   <div class="font-size-12 line-height-12">EO编号</div>
			           <div class="font-size-9 line-height-12">EO No.</div>
				   	</th>
				   	<th class="colwidth-5">
					   <div class="font-size-12 line-height-12">版本</div>
			           <div class="font-size-9 line-height-12">Rev.</div>
				   	</th>
				   	<th class="colwidth-7">
					   <div class="font-size-12 line-height-12">状态</div>
			           <div class="font-size-9 line-height-12">Status</div>
				   	</th>
				   	<th class="colwidth-9">
					   <div class="font-size-12 line-height-12">日期</div>
			           <div class="font-size-9 line-height-12">Date</div>
				   	</th>
				</tr>
			</thead>
			<tbody id="history_bb_list">
			</tbody>
		</table>
	</div>
	<div class='clearfix'></div>
</div>
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineering/eo_history_win.js"></script>