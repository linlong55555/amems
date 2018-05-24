<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/history_certificate_win.js"></script>
<div id="history_certificate_alert_Modal" style='display: none;padding:0px;'>
	<!-- 表格 -->
	<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 padding-bottom-0 padding-top-0" style="overflow-x: auto;margin:0px;width:100%;">
			<table class="table-thin table-set text-left webui-popover-table" style='border:1px;margin-bottom:1px !important;' width='100%'>
				<thead>
					<tr style='height:35px;'>
						<th class="colwidth-9">
							<div class="font-size-12 line-height-18">证书类型</div>
							<div class="font-size-9 line-height-18">Certificate Type</div>
						</th>
						<th class="colwidth-9 ">
							<div class="font-size-12 line-height-18">证书编号</div>
							<div class="font-size-9 line-height-18">Certificate No</div>
						</th>
						<th class="colwidth-15 ">
							<div class="font-size-12 line-height-18">存放位置</div>
							<div class="font-size-9 line-height-18">Location</div>
						</th>
						<th class="colwidth-9 ">
						   <div class="font-size-12 line-height-18">签署日期</div>
				           <div class="font-size-9 line-height-18">Date</div>
					   	</th>
					   	<th class="colwidth-17 ">
						   <div class="font-size-12 line-height-18">附件</div>
				           <div class="font-size-9 line-height-18">File</div>
					   	</th>
					</tr>
				</thead>
				<tbody id="certigicate_List">
				</tbody>
		</table>
	</div>
	<div class='clearfix'></div>
</div>

<script src="${ctx}/static/js/thjw/common/preview.js"></script>