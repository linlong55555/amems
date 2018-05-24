<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/produce/next_plan.js"></script><!--当前界面js  -->
<div class="panel panel-default" id="introduce_process_info_class_lcxx">
	<!--标题 STATR -->
	<div class="panel-heading bg-panel-heading">
		<div class="font-size-12 ">下次计划</div>
		<div class="font-size-9">Next Plan</div>
	</div>
	<!--标题ENG  -->
	<div class="panel-body padding-bottom-0 padding-left-8 padding-right-0  padding-top-0">	  	 	
		<div class="col-xs-6 margin-top-8 padding-left-0  padding-right-0" >
		
			<table class="table table-bordered table-striped table-hover text-center table-set table-thin" style="min-width: 500px;">
        		<thead>
			   		<tr>
						<th class="colwidth-10" >
							<label class='label-input' >
							<input id="isBj" name='isBj' type='checkbox' />
								&nbsp;后到为准
							</label>
						</th>
						<th class="colwidth-7" >
							<div class="font-size-12 line-height-12">首检</div>
							<div class="font-size-9 line-height-12">INTI</div>	
						</th>
						<th class="colwidth-7" >
							<div class="font-size-12 line-height-12">周期</div>
							<div class="font-size-9 line-height-12">Cycle</div>
						</th>
						<th class="colwidth-10" >
							<div class="font-size-12 line-height-12">容差(-/+)</div>
							<div class="font-size-9 line-height-12">Tolerance(-/+)</div>
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
        		<tbody id="maintenance_version_list">
        			<tr>
	        			<td>日历</td>
	        			<td>24MON</td>
	        			<td>6MON</td>
	        			<td>-10/+10DAY</td>
	        			<td>2018-01-01</td>
	        			<td>2017-07-20</td>
	        			<td>130DAY</td>
        			</tr>
        			<tr>
	        			<td>飞行小时</td>
	        			<td>10000HRS</td>
	        			<td>500HRS</td>
	        			<td>-10/+10HRS</td>
	        			<td>10400HRS</td>
	        			<td>10050HRS</td>
	        			<td>350HRS</td>
        			</tr>
        			<tr>
	        			<td>飞行循环</td>
	        			<td>5000CYC</td>
	        			<td>200CYC</td>
	        			<td>-5/+5CYC</td>
	        			<td>5200CYC</td>
	        			<td>5020CYC</td>
	        			<td>180CYC</td>
        			</tr>
           		</tbody>
          	</table>
		
		</div>
	</div>
</div>
