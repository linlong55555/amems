<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/produce/next_plan_starting_point.js"></script><!--当前界面js  -->
<div class="panel panel-default" id="next_plan_starting_point_edit">
	<!--标题 STATR -->
	<div class="panel-heading bg-panel-heading">
		<div class="font-size-12 ">下次计划起算点</div>
		<div class="font-size-9">Next Plan Starting Point</div>
	</div>
	<!--标题ENG  -->
	<div class="panel-body padding-bottom-0 padding-left-8 padding-right-8  padding-top-0">	
		<label class='label-input' >
			<span id="jsgs_all">计算公式&nbsp;:&nbsp;<span id="jsgs"></span>
			&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<input id="isHdwz" type='checkbox'  disabled="disabled"/>
			&nbsp;后到为准
		</label>  	 	
		<div id="exists_last_div" class="col-xs-12 margin-top-8 padding-left-0  padding-right-0"  style="overflow-x: auto">
			<table class="table table-bordered table-striped table-hover text-center table-set table-thin" style="min-width: 500px;">
        		<thead>
			   		<tr>
						<th class="colwidth-7" >
							<div class="font-size-12 line-height-12">监控项</div>
							<div class="font-size-9 line-height-12">Monitoring</div>
						</th>
						<th class="colwidth-13" >
							<div class="font-size-12 line-height-12">上次计划</div>
							<div class="font-size-9 line-height-12">Last Plan</div>
						</th>
						<th class="colwidth-13" >
							<div class="font-size-12 line-height-12">上次实际</div>
							<div class="font-size-9 line-height-12">Last Actual</div>
						</th>
						<th class="colwidth-13" >
							<div class="font-size-12 line-height-12">下次计划起算点</div>
							<div class="font-size-9 line-height-12">T0</div>
						</th>
						<th class="colwidth-9" >
							<div class="font-size-12 line-height-12">首检</div>
							<div class="font-size-9 line-height-12">INTI</div>	
						</th>
						<th class="colwidth-9" >
							<div class="font-size-12 line-height-12">周期</div>
							<div class="font-size-9 line-height-12">Cycle</div>
						</th>
						<th class="colwidth-9" >
							<div class="font-size-12 line-height-12">容差(-/+)</div>
							<div class="font-size-9 line-height-12">Tolerance(-/+)</div>
						</th>
						<th class="colwidth-9" style="font-weight:bold;">
							<div class="font-size-12 line-height-12">下次计划</div>
							<div class="font-size-9 line-height-12">Next Plan</div>
						</th>
						<th class="colwidth-9" >
							<div class="font-size-12 line-height-12">实际</div>
							<div class="font-size-9 line-height-12">Actual</div>
						</th>
						<th class="colwidth-9" >
							<div class="font-size-12 line-height-12">剩余</div>
							<div class="font-size-9 line-height-12">Remain</div>
						</th>
					</tr>
				</thead>
        		<tbody id="next_plan_starting_point_list">
           		</tbody>
          	</table>
		</div>
		<div class='clearfix'></div>
		<div id="not_exists_last_div" class="col-xs-6 margin-top-8 padding-left-0  padding-right-0"  style="overflow-x: auto">
			<table class="table table-bordered table-striped table-hover text-center table-set table-thin" style="min-width: 500px;">
        		<thead>
			   		<tr>
						<th class="colwidth-7" >
							<div class="font-size-12 line-height-12">监控项</div>
							<div class="font-size-9 line-height-12">Monitoring</div>
						</th>
						<th class="colwidth-9" >
							<div class="font-size-12 line-height-12">下次计划</div>
							<div class="font-size-9 line-height-12">Next Plan</div>
						</th>
						<th class="colwidth-9" >
							<div class="font-size-12 line-height-12">实际</div>
							<div class="font-size-9 line-height-12">Actual</div>
						</th>
						<th class="colwidth-9" >
							<div class="font-size-12 line-height-12">剩余</div>
							<div class="font-size-9 line-height-12">Remain</div>
						</th>
					</tr>
				</thead>
        		<tbody id="next_plan_list">
           		</tbody>
          	</table>
		</div>
	</div>
</div>
