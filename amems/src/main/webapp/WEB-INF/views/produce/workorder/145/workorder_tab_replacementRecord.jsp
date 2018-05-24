<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java"%>
<div class="panel-body">
	<div style='overflow: auto;' class='col-xs-12 padding-left-0 padding-right-0 tab-table-content'>
		<table
			class='table table-striped table-thin table-hover table-bordered text-center table-set'
			style='margin-bottom: 0px !important;'>
			<thead>
				<tr>
					<th class="fixed-column colwidth-5">
						<div class="font-size-12 line-height-18">序号</div>
						<div class="font-size-9 line-height-18">No.</div>
					</th>
					<th class="fixed-column colwidth-10">
						<div class="font-size-12 line-height-18">工单编号</div>
						<div class="font-size-9 line-height-18">WorkOrder No.</div>
					</th>
					<th class="colwidth-10 " onclick="orderBy('jswjlx')"
						id="jswjlx_order">
						<div class="font-size-12 line-height-18">装上件号</div>
						<div class="font-size-9 line-height-18">Mount P/N</div>
					</th>
					<th class="colwidth-10 " onclick="orderBy('jswjly')"
						id="jswjly_order">
						<div class="font-size-12 line-height-18">装上序列号</div>
						<div class="font-size-9 line-height-18">Mount S/N</div>
					</th>
					<th class="colwidth-13 " onclick="orderBy('jswjbh')"
						id="jswjbh_order">
						<div class="font-size-12 line-height-18">装上件名称</div>
						<div class="font-size-9 line-height-18">Mount Name</div>
					</th>
					<th class="colwidth-13 " onclick="orderBy('zssj')"
						id="jswjbh_order">
						<div class="font-size-12 line-height-18">装上时间</div>
						<div class="font-size-9 line-height-18">Mount Time</div>
					</th>
					<th class="colwidth-10 " onclick="orderBy('bb')"
						id="bb_order">
						<div class="font-size-12 line-height-18">拆下件号</div>
						<div class="font-size-9 line-height-18">Remove P/N</div>
					</th>
					<th class="colwidth-10 " onclick="orderBy('xzah')"
						id="xzah_order">
						<div class="font-size-12 line-height-18">拆下序列号</div>
						<div class="font-size-9 line-height-18">Remove S/N</div>
					</th>
					<th class="colwidth-13 " onclick="orderBy('jswjzt')"
						id="jswjzt_order">
						<div class="font-size-12 line-height-18">拆下件名称</div>
						<div class="font-size-9 line-height-18">Remove Name</div>
					</th>
					<th class="colwidth-13 " onclick="orderBy('gljswjid')"
						id="gljswjid_order">
						<div class="font-size-12 line-height-18">拆下时间</div>
						<div class="font-size-9 line-height-18">Remove Time</div>
					</th>
					<th class="colwidth-25 " onclick="orderBy('zt')"
						id="zt_order">
						<div class="font-size-12 line-height-18">拆装原因</div>
						<div class="font-size-9 line-height-18">Dismantling Reason</div>
					</th>
				</tr>
			</thead>
			<tbody id="workorder145chjtab_list"></tbody>
		</table>
	</div>
</div>
