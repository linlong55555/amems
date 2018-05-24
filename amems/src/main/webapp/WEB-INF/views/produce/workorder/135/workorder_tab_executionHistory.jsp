<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"
	language="java"%>
<div class="panel-body">
	<div class='padding-bottom-8 row-height'>
		<div class="pull-left padding-left-0" style='margin-right: 3px;' id="woexeHistoryBtn">
			<a href="javascript:void(0);"
				class="btn btn-primary padding-top-1 padding-bottom-1 pull-left">
				<div class="font-size-12">导出</div>
				<div class="font-size-9">Export</div>
			</a>
		</div>
		<div class='clearfix'></div>
	</div>
	<div class="col-lg-12 col-md-12 col-xs-12  padding-left-0 padding-right-0 tab-table-content" style="overflow-x: auto" id="wo135exeHistoryDiv">
		<table class='table table-striped table-thin table-bordered table-set' style='min-width: 1000px'>
			<thead>
				<tr>
					<th class='colwidth-5'>
						<div class="font-size-12 line-height-12">序号</div>
						<div class="font-size-9 line-height-12">No.</div>
					</th>
					<th class='colwidth-10'>
						<div class="font-size-12 line-height-12">工单编号</div>
						<div class="font-size-9 line-height-12">W/O No.</div>
					</th>
					<th class='colwidth-15'>
						<div class="font-size-12 line-height-12">标题</div>
						<div class="font-size-9 line-height-12">Title</div>
					</th>
					<th class='colwidth-10'>
						<div class="font-size-12 line-height-12">工单附件</div>
						<div class="font-size-9 line-height-12">Attachment</div>
					</th>
					<th class='colwidth-10'>
						<div class="font-size-12 line-height-12">FLB</div>
						<div class="font-size-9 line-height-12">FLB</div>
					</th>
					<th class='colwidth-10'>
						<div class="font-size-12 line-height-12">FLB附件</div>
						<div class="font-size-9 line-height-12">Attachment</div>
					</th>
					<th class='colwidth-10'>
						<div class="font-size-12 line-height-12">执行日期</div>
						<div class="font-size-9 line-height-12">Date</div>
					</th>
					<th class='colwidth-15'>
						<div class="font-size-12 line-height-12">实际值</div>
						<div class="font-size-9 line-height-12">Actual Value</div>
					</th>
					<th class='colwidth-15'>
						<div class="font-size-12 line-height-12">计划值</div>
						<div class="font-size-9 line-height-12">Plan Value</div>
					</th>
					<th class='colwidth-10'>
						<div class="font-size-12 line-height-12">完工反馈</div>
						<div class="font-size-9 line-height-12">Feedback</div>
					</th>
					<th class='colwidth-10'>
						<div class="font-size-12 line-height-12">拆装记录</div>
						<div class="font-size-9 line-height-12">Record</div>
					</th>
				</tr>
			</thead>
			<tbody id="workorder135tabexehistory_list">
			</tbody>
		</table>
	</div>
</div>