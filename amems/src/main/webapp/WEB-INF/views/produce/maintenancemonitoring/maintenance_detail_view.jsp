<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenancemonitoring/maintenance_detail_list.js"></script><!--当前界面js  -->
<div id="maintenance_detail_list" class="panel-body  padding-bottom-10 padding-left-10 padding-right-10 padding-top-10" >
	<div id="maintenance_detail_list_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto">
		<table id="maintenance_detail_list_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width:1100px">
			<thead>
				<tr>
			   		<th class="colwidth-7">
				   		<div class="font-size-12 line-height-12">项目类型</div>
		           		<div class="font-size-9 line-height-12">Type</div>
			   		</th>
			   		<th class="colwidth-10">
				   		<div class="font-size-12 line-height-12">任务号</div>
		           		<div class="font-size-9 line-height-12">Task No.</div>
			   		</th>
		   			<th class="colwidth-7">
				   		<div class="font-size-12 line-height-12">版本</div>
		           		<div class="font-size-9 line-height-12">Rev.</div>
			   		</th>
			   		<th class="colwidth-10">
				   		<div class="font-size-12 line-height-12">参考号</div>
		           		<div class="font-size-9 line-height-12">Ref No</div>
			   		</th>
		   			<th class="colwidth-15">
			   			<div class="font-size-12 line-height-12">任务描述</div>
	           			<div class="font-size-9 line-height-12">Task Description</div>
		   			</th>
		   			<th class="colwidth-10">
			   			<div class="font-size-12 line-height-12">件号</div>
	           			<div class="font-size-9 line-height-12">P/N</div>
		   			</th>
		   			<th class="colwidth-10">
			   			<div class="font-size-12 line-height-12">序列号</div>
	           			<div class="font-size-9 line-height-12">S/N</div>
		   			</th>
		   			<th class="colwidth-10">
			   			<div class="font-size-12 line-height-12">工作类别</div>
	           			<div class="font-size-9 line-height-12">Category</div>
		   			</th>
		   			<th class="colwidth-10">
			   			<div class="font-size-12 line-height-12">上次计划</div>
	           			<div class="font-size-9 line-height-12">Last Plan</div>
		   			</th>
		   			<th class="colwidth-10">
			   			<div class="font-size-12 line-height-12">上次实际</div>
	           			<div class="font-size-9 line-height-12">Last Actual</div>
		   			</th>
		   			<th class="colwidth-13">
			   			<div class="font-size-12 line-height-12">下次计划起算点</div>
	           			<div class="font-size-9 line-height-12">Next Plan Point</div>
		   			</th>
		   			<th class="colwidth-10">
			   			<div class="font-size-12 line-height-12">周期</div>
	           			<div class="font-size-9 line-height-12">Cycle</div>
		   			</th>
		   			<th class="colwidth-10">
			   			<div class="font-size-12 line-height-12">容差(-/+)</div>
	           			<div class="font-size-9 line-height-12">Tolerance(-/+)</div>
		   			</th>
		   			<th class="colwidth-10">
			   			<div class="font-size-12 line-height-12">下次计划</div>
	           			<div class="font-size-9 line-height-12">Next Plan</div>
		   			</th>
		   			<th class="colwidth-10">
						<div class="font-size-12 line-height-18">实际</div>
						<div class="font-size-9 line-height-18">Actual</div>
					</th>
		   			<th class="colwidth-10">
			   			<div class="font-size-12 line-height-12">剩余</div>
	           			<div class="font-size-9 line-height-12">Remain</div>
		   			</th>
		   			<th class="colwidth-13">
			   			<div class="font-size-12 line-height-12">下次计划日期</div>
	           			<div class="font-size-9 line-height-12">Next Plan Date</div>
		   			</th>
		   			<th class="colwidth-10">
			   			<div class="font-size-12 line-height-12">后到为准</div>
	           			<div class="font-size-9 line-height-12">After Arrival</div>
		   			</th>
		   			<th class="colwidth-15">
			   			<div class="font-size-12 line-height-12">工卡编号</div>
	           			<div class="font-size-9 line-height-12">Work Card No.</div>
		   			</th>
		   			<th class="colwidth-10">
			   			<div class="font-size-12 line-height-12">工卡附件</div>
	           			<div class="font-size-9 line-height-12">Attachment</div>
		   			</th>
		   			<th class="colwidth-15">
				   		<div class="font-size-12 line-height-12">ATA章节号</div>
		           		<div class="font-size-9 line-height-12">ATA</div>
			   		</th>
			   		<th class="colwidth-15" >
						<div class="font-size-12 line-height-18">维修项目大类</div>
						<div class="font-size-9 line-height-18">Classification</div>
					</th>
				</tr>
			</thead>
			<tbody id="maintenance_detail_list_tbody">
			</tbody>
		</table>
	</div>
</div>