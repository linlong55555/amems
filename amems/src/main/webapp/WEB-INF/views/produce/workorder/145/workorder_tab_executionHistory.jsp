<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"
	language="java"%>
<div class="panel-body">
	<div class='padding-bottom-8 row-height'>
		<div class="pull-left padding-left-0" style='margin-right: 3px;' id="woexeHistoryBtn">
			<a href="javascript:void(0);"
				class="btn btn-primary padding-top-1 padding-bottom-1 pull-left">
				<div class="font-size-12">导出</div>
				<div class="font-size-9">Download</div>
			</a>
		</div>
		<div class='clearfix'></div>
	</div>
	<div class="col-lg-12 col-md-12 col-xs-12  padding-left-0 padding-right-0" style="overflow-x: auto" id="wo145exeHistoryDiv">
		<table class='table table-striped table-thin table-bordered' style='min-width: 2000px'>
			<thead>
				<tr>
					<th>
						<div class="font-size-12 line-height-12">ATA章节号</div>
						<div class="font-size-9 line-height-12">ATA</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">项目类型</div>
						<div class="font-size-9 line-height-12">Project type</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">任务号</div>
						<div class="font-size-9 line-height-12">Task no.</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">版本</div>
						<div class="font-size-9 line-height-12">Rev.</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">参考号</div>
						<div class="font-size-9 line-height-12">Reference no.</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">任务描述</div>
						<div class="font-size-9 line-height-12">Task description</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">工单号</div>
						<div class="font-size-9 line-height-12">Work order no.</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">工单附件</div>
						<div class="font-size-9 line-height-12">Attachment</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">标题</div>
						<div class="font-size-9 line-height-12">Title</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">FLB</div>
						<div class="font-size-9 line-height-12">FLB</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">FLB附件</div>
						<div class="font-size-9 line-height-12">Attachment</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">件号</div>
						<div class="font-size-9 line-height-12">PN</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">型号</div>
						<div class="font-size-9 line-height-12">Model</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">序列号</div>
						<div class="font-size-9 line-height-12">SN</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">执行日期</div>
						<div class="font-size-9 line-height-12">Date</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">实际值</div>
						<div class="font-size-9 line-height-12">Actual</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">计划值</div>
						<div class="font-size-9 line-height-12">Planned</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">故障信息</div>
						<div class="font-size-9 line-height-12">Fault info</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">处理措施</div>
						<div class="font-size-9 line-height-12">Measures</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">工作者</div>
						<div class="font-size-9 line-height-12">Workers</div>
					</th>
					<th>
						<div class="font-size-12 line-height-12">检查者</div>
						<div class="font-size-9 line-height-12">Inspectors</div>
					</th>
				</tr>
			</thead>
			<tbody id="workorder145tabexehistory_list">
			</tbody>
		</table>
	</div>
</div>