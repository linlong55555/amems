<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java"%>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/produce/material_tool_list.js"></script><!--当前界面js  -->
<div class="panel-body" id="material_tool_list">
	<!-- <div class='padding-bottom-8 row-height'>
		<div class="pull-left padding-left-0" style='margin-right:3px;'>
			<a href="javascript:void(0);"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" >
				<div class="font-size-12">领料申请</div>
				<div class="font-size-9">Application</div>
			</a> 
		</div>
		<div class="pull-left padding-left-0" style='margin-right: 3px;' id="wo135materialToolBtn">
			<a href="javascript:void(0);"
				class="btn btn-primary padding-top-1 padding-bottom-1 pull-left">
				<div class="font-size-12">导出</div>
				<div class="font-size-9">Download</div>
			</a>
		</div>
		<div class='clearfix'></div>
	</div> -->
	<div id="material_tool_list_top_div" class="col-lg-12 col-md-12 col-xs-12 padding-left-0 padding-right-0"
		style="overflow-x: auto">
		<table id="material_tool_list_table" class='table table-bordered table-thin table-striped table-hover table-set'>
			<thead>
				<tr>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-12">类型</div>
						<div class="font-size-9 line-height-12">Type</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-12">件号</div>
						<div class="font-size-9 line-height-12">PN</div>
					</th>
					<th class="colwidth-9">
						<div class="font-size-12 line-height-12">型号</div>
						<div class="font-size-9 line-height-12">Modal</div>
					</th>
					<th class="colwidth-15">
						<div class="font-size-12 line-height-12">名称</div>
						<div class="font-size-9 line-height-12">Name</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-12">件号来源</div>
						<div class="font-size-9 line-height-12">Source</div>
					</th>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-12">需求数量</div>
						<div class="font-size-9 line-height-12">QTY</div>
					</th>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-12">库存数</div>
						<div class="font-size-9 line-height-12">Inventory</div>
					</th>
					<th class="colwidth-5">
						<div class="font-size-12 line-height-12">单位</div>
						<div class="font-size-9 line-height-12">Unit</div>
					</th>
					<th class="colwidth-5">
						<div class="font-size-12 line-height-12">缺件</div>
						<div class="font-size-9 line-height-12">M/P</div>
					</th>
					<th class="colwidth-15">
						<div class="font-size-12 line-height-12">互换信息</div>
						<div class="font-size-9 line-height-12">Swap Info</div>
					</th>
				</tr>
			</thead>
			<tbody id="material_tool_list_tbody">
			</tbody>
		</table>
	</div>
</div>
