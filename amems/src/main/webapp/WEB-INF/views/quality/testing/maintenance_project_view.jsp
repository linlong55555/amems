<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!-- 适用维修项目  Maintenance Item -->
<div class="panel panel-default" id="maintenance_project_view">
      <div class="panel-heading">
  		<h3 class="panel-title">适用维修项目  Maintenance Item</h3>
      </div>
	<div class="panel-body">	
     	<div  class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow: auto;" >
			<table class="table table-thin table-bordered table-set" >
				<thead>
					<tr>
						<th class="colwidth-10">
							<div class="font-size-12 line-height-12">任务号</div>
							<div class="font-size-9 line-height-12">Task No.</div>
						</th>
						<th class="colwidth-5">
							<div class="font-size-12 line-height-12">版本</div>
							<div class="font-size-9 line-height-12">Rev.</div>
						</th>
						<th class="colwidth-25">
							<div class="font-size-12 line-height-12">任务描述</div>
							<div class="font-size-9 line-height-12">Task Description</div>
						</th>
						<th class="colwidth-9">
							<div class="font-size-12 line-height-12">监控项目</div>
							<div class="font-size-9 line-height-12">Monitor Item</div>
						</th>
						<th class="colwidth-9">
								<div class="font-size-12 line-height-12">首检</div>
								<div class="font-size-9 line-height-12">INTI</div>	
						</th>
						<th class="colwidth-7">
							<div class="font-size-12 line-height-12">周期</div>
							<div class="font-size-9 line-height-12">Cycle</div>
						</th>
						<th class="colwidth-11">
							<div class="font-size-12 line-height-12">容差(-/+)</div>
							<div class="font-size-9 line-height-12">Tolerance(-/+)</div>
						</th>
					</tr>
				</thead>
				<tbody id="common_maintenance_list">
					<tr><td class="text-center" colspan="7">暂无数据 No data.</td></tr>
				</tbody>
			</table>
			<input id="common_skbs" type="hidden">
			<input id="common_ssbs" type="hidden">
		</div>
	</div>	
</div>	
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/testing/maintenance_project_view.js"></script><!--当前js  -->
<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>
