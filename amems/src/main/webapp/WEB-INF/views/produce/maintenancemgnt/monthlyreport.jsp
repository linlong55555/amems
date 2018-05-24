<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	
	<script type="text/javascript">
		$(document).ready(function(){
			//回车事件控制
			$(this).keydown(function(event) {
				e = event ? event :(window.event ? window.event : null); 
				if(e.keyCode==13){
					var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
					if(formatUndefine(winId) != ""){
						$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
					}else{
						$('#aircraftinfoMainSearch').trigger('click'); //调用主列表页查询
					}
				}
			});
		});
	</script>	
</head>
<body class="page-header-fixed">
<input type="hidden" id="dprtId" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="userType" value="${user.userType}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
<div class="page-content">
	<div class="panel panel-primary">
		<div class="panel-heading  ">
			<div id="NavigationBar"></div>
		</div>
		<div class="panel-body padding-bottom-0" id='page_body'>
		<div class='text-center worktimeInfo' style="position:relative;" >
			<div class="pull-left">
				<div class="pull-left text-right padding-left-0 padding-right-0">
					<div class="font-size-12">飞机注册号</div>
					<div class="font-size-9">A/C REG</div>
				</div>
				<div class="pull-left text-right padding-left-0 padding-right-0">
					<div class="padding-left-8 pull-left" style="width: 130px; margin-right:5px;">
					   <select id="fjzch" class="form-control" onchange="monthlyReport.loadData()"></select> 
					</div>
				</div>
				
				<div class="pull-left text-right padding-left-0 padding-right-0">
					<div class="font-size-12">月份</div>
					<div class="font-size-9">Month</div>
				</div>
				<div class="pull-left text-right padding-left-0 padding-right-0">
					<div class="padding-left-8 pull-left" style="width: 70px; margin-right:5px;">
					   <input type="text" class="form-control" id="month" onchange="monthlyReport.loadData()" readonly/>	
					</div>
				</div>
				
				<span class="pull-left" style="line-height:34px;margin-right:8px;margin-left:8px;" onclick="monthlyReport.loadData()">
					<i class="icon-refresh color-blue cursor-pointer" ></i>
				</span>
			</div>
		    <div style="width:400px;margin:0 auto;">
			<span class="pull-left" style="line-height:34px;margin-right:8px;font-size:18px;">
				飞机<span id="fjzch_title"></span><span id="month_title"></span>执管报告
			</span>
			<div class="clearfix"></div>
			</div>
			<div id="config_title">
				<a href="javascript:;" style="position:absolute;right:0px;top:10px;text-decoration:underline;" onclick="monthlyReport.setWorkHourConfig()" ></a>
			</div>
			<input type="hidden" id="configId" />
			<input type="hidden" id="configYf" />
		</div>
		<!-- 机身及发动机小时数统计 -->
		<div class="panel panel-primary margin-top-10 margin-bottom-8">
			<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
				<a data-toggle="collapse" data-parent="#accordion" href="#hoursStatisticsCollapsed" class="">
				<div class="pull-left">
				<input class="selectCheckbox" checked="checked" style="margin-top:6px;margin-right:8px;" type="checkbox">
				</div>
				<div class="pull-left">
				
				<div class="font-size-12">机身及发动机小时数统计</div>
				<div class="font-size-9 ">Fuselage And Engine Hours Statistics</div>
				</div>
				<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
				<div class="clearfix"></div>
				</a>
			</div>
			<div id="hoursStatisticsCollapsed" class="panel-collapse collapse in" style="height: auto;">
				<div class="panel-body padding-left-0 padding-right-0" style="padding-bottom:0px;padding-top:8px;">
			    <table id="" class="table table-thin table-striped table-hover table-set" style="margin-bottom:0px !important;">
						<thead>                              
							<tr>
								<th class='colwidth-13'>
									<div class="font-size-12 line-height-18">项目</div>
									<div class="font-size-9 line-height-18">Item</div>
								</th>
								<th class='colwidth-13'>
								    <div class="font-size-12 line-height-18">型号</div>
									<div class="font-size-9 line-height-18">Model</div>
							    </th>
							    <th class='colwidth-13'>
								    <div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">S/N</div>
							    </th>
							    <th class='colwidth-13'>
								    <div class="font-size-12 line-height-18">小时数(本月)</div>
									<div class="font-size-9 line-height-18">Hour(Month)</div>
							    </th>
							    <th class='colwidth-13'>
								    <div class="font-size-12 line-height-18">循环数(本月)</div>
									<div class="font-size-9 line-height-18">Cycle(Month)</div>
							    </th>
							    <th class='colwidth-13'>
								    <div class="font-size-12 line-height-18">小时数（自出厂）</div>
									<div class="font-size-9 line-height-18">Hour(Total)</div>
							    </th>
							    <th class='colwidth-13'>
								    <div class="font-size-12 line-height-18">起落数/循环数</div>
									<div class="font-size-9 line-height-18">Cycle</div>
							    </th>
							</tr>
						</thead>
						<tbody id="hoursStatisticsList">
						</tbody>
					</table>
			    </div>
			</div>
		</div>
		
		<!-- 航线例行维修工作 -->
		<div class="panel panel-primary margin-bottom-8" >
			<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
				<a data-toggle="collapse" data-parent="#accordion" href="#routineWorkCollapsed" class="">
				<div class="pull-left">
				<input class="selectCheckbox" style="margin-top:6px;margin-right:8px;" type="checkbox">
				</div>
				<div class="pull-left">
				
				<div class="font-size-12">航线例行维修工作</div>
				<div class="font-size-9 ">Routine Maintenance Work</div>
				</div>
				<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
				
				<div class="pull-left" style="margin-left:10px;height:32px;line-height:32px;">
					<span>例行工时共计：</span><span id="lxgs"></span>
				</div>
				
				<div class="clearfix"></div>
				</a>
			</div>
			<div id="routineWorkCollapsed" class="panel-collapse collapse" style="height: auto;">
				<div class="panel-body padding-left-0 padding-right-0" style="padding-bottom:0px;padding-top:8px;">
			    <table id="" class="table table-thin table-striped table-hover table-set" style="margin-bottom:0px !important;">
						<thead>                              
							<tr>
								<th class='colwidth-7'>
									<div class="font-size-12 line-height-18" >日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class='colwidth-10'>
									<div class="font-size-12 line-height-18">起飞机场</div>
									<div class="font-size-9 line-height-18">DEP</div>
								</th>
								<th class='colwidth-10'>
								    <div class="font-size-12 line-height-18">落地机场</div>
									<div class="font-size-9 line-height-18">DES</div>
							    </th>
							    <th class='colwidth-7'>
								    <div class="font-size-12 line-height-18">工时</div>
									<div class="font-size-9 line-height-18">Working Hours</div>
							    </th>
							</tr>
						</thead>
						<tbody id="routineWorkList">
						</tbody>
					</table>
			    </div>
			</div>
		</div>
		
		<!-- 航线非例行维修工作 -->
		<div class="panel panel-primary margin-bottom-8">
			<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
				<a data-toggle="collapse" data-parent="#accordion" href="#nonRoutineWorkCollapsed" class="">
				<div class="pull-left">
				<input class="selectCheckbox" style="margin-top:6px;margin-right:8px;" type="checkbox">
				</div>
				<div class="pull-left">
				
				<div class="font-size-12">航线非例行维修工作 </div>
				<div class="font-size-9 ">Non Routine Maintenance Work</div>
				</div>
				<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
				<div class="clearfix"></div>
				</a>
			</div>
			<div id="nonRoutineWorkCollapsed" class="panel-collapse collapse" style="height: auto;">
				<div class="panel-body padding-left-0 padding-right-0" style="padding-bottom:0px;padding-top:8px;">
			    <table id="" class="table table-thin table-striped table-hover table-set" style="margin-bottom:0px !important;">
						<thead>                              
							<tr>
								<th class='colwidth-3'>
									<div class="font-size-12 line-height-18" >序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class='colwidth-7'>
									<div class="font-size-12 line-height-18" >日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class='colwidth-10'>
									<div class="font-size-12 line-height-18">工卡号</div>
									<div class="font-size-9 line-height-18">Work Card No.</div>
								</th>
								<th class='colwidth-10'>
								    <div class="font-size-12 line-height-18">内容</div>
									<div class="font-size-9 line-height-18">Content</div>
							    </th>
							    <th class='colwidth-7'>
								    <div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div>
							    </th>
							</tr>
						</thead>
						
						<tbody id="nonRoutineWorkList">
						</tbody>
					</table>
			    </div>
			</div>
		</div>
		
		<!-- EO/MAO执行情况 -->
		<div class="panel panel-primary margin-bottom-8">
			<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
				<a data-toggle="collapse" data-parent="#accordion" href="#eoMaoImplementationCollapsed" class="">
				<div class="pull-left">
				<input class="selectCheckbox" style="margin-top:6px;margin-right:8px;" type="checkbox">
				</div>
				<div class="pull-left">
				
				<div class="font-size-12">EO/MAO执行情况 </div>
				<div class="font-size-9 ">EO/MAO Implementation</div>
				</div>
				<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
				<div class="clearfix"></div>
				</a>
			</div>
			<div id="eoMaoImplementationCollapsed" class="panel-collapse collapse" style="height: auto;">
				<div class="panel-body padding-left-0 padding-right-0" style="padding-bottom:0px;padding-top:8px;">
			    <table id="" class="table table-thin table-striped table-hover table-set" style="margin-bottom:0px !important;">
						<thead>                              
							<tr>
								<th class='colwidth-3'>
									<div class="font-size-12 line-height-18" >序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class='colwidth-7'>
									<div class="font-size-12 line-height-18" >EO编号</div>
									<div class="font-size-9 line-height-18">EO No.</div>
								</th>
								<th class='colwidth-25'>
									<div class="font-size-12 line-height-18">描述</div>
									<div class="font-size-9 line-height-18">Desc</div>
								</th>
								<th class='colwidth-5'>
								    <div class="font-size-12 line-height-18">完成/进展情况</div>
									<div class="font-size-9 line-height-18">Progress</div>
							    </th>
							</tr>
						</thead>
						
						<tbody id="eoMaoImplementationList">
						</tbody>
					</table>
			    </div>
			</div>
		</div>
		
		<!-- 适航指令,厂家服务通告等评估情况 -->
		<div class="panel panel-primary margin-bottom-8">
			<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
				<a data-toggle="collapse" data-parent="#accordion" href="#evaluationSituationsCollapsed" class="">
				<div class="pull-left">
				<input class="selectCheckbox" style="margin-top:6px;margin-right:8px;" type="checkbox">
				</div>
				<div class="pull-left">
				
				<div class="font-size-12">适航指令,厂家服务通告等评估情况 </div>
				<div class="font-size-9 ">AD,SB Assessment</div>
				</div>
				<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
				<div class="clearfix"></div>
				</a>
			</div>
			<div id="evaluationSituationsCollapsed" class="panel-collapse collapse" style="height: auto;">
				<div class="panel-body padding-left-0 padding-right-0" style="padding-bottom:0px;padding-top:8px;">
			    <table id="" class="table table-thin table-striped table-hover table-set" style="margin-bottom:0px !important;">
						<thead>                              
							<tr>
								<th class='colwidth-7'>
									<div class="font-size-12 line-height-18" >文件号</div>
									<div class="font-size-9 line-height-18">Doc No.</div>
								</th>
								<th class='colwidth-10'>
									<div class="font-size-12 line-height-18">接收日期</div>
									<div class="font-size-9 line-height-18">Receive Date</div>
								</th>
								<th class='colwidth-10'>
								    <div class="font-size-12 line-height-18">评估结果</div>
									<div class="font-size-9 line-height-18">Result</div>
							    </th>
							    <th class='colwidth-10'>
								    <div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
							    </th>
							</tr>
						</thead>
						
						<tbody id="evaluationSituationsList">
						</tbody>
					</table>
			    </div>
			</div>
		</div>
		
		<!-- 飞机故障/缺陷监控 -->
		<div class="panel panel-primary margin-bottom-8">
			<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
				<a data-toggle="collapse" data-parent="#accordion" href="#faultMonitorCollapsed" class="">
				<div class="pull-left">
				<input class="selectCheckbox" style="margin-top:6px;margin-right:8px;" type="checkbox">
				</div>
				<div class="pull-left">
				
				<div class="font-size-12">飞机故障/缺陷监控 </div>
				<div class="font-size-9 ">Aircraft Fault Monitor</div>
				</div>
				<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
				<div class="clearfix"></div>
				</a>
			</div>
			<div id="faultMonitorCollapsed" class="panel-collapse collapse" style="height: auto;">
				<div class="panel-body padding-left-0 padding-right-0" style="padding-bottom:0px;padding-top:8px;">
			    <table id="" class="table table-thin table-striped table-hover table-set" style="margin-bottom:0px !important;">
						<thead>                              
							<tr>
								<th class='colwidth-7'>
									<div class="font-size-12 line-height-18" >保留单号</div>
									<div class="font-size-9 line-height-18">DD No.</div>
								</th>
								<th class='colwidth-10'>
									<div class="font-size-12 line-height-18">故障描述</div>
									<div class="font-size-9 line-height-18">Defect Desc</div>
								</th>
								<th class='colwidth-10'>
								    <div class="font-size-12 line-height-18">保留原因</div>
									<div class="font-size-9 line-height-18">Reason</div>
							    </th>
							    <th class='colwidth-7'>
								    <div class="font-size-12 line-height-18">保留日期</div>
									<div class="font-size-9 line-height-18">Reserve Date</div>
							    </th>
							    <th class='colwidth-15'>
								    <div class="font-size-12 line-height-18">到期日期</div>
									<div class="font-size-9 line-height-18">Expiring Date</div>
							    </th>
							</tr>
						</thead>
						
						<tbody id="faultMonitorList">
						</tbody>
					</table>
			    </div>
			</div>
		</div>
		
		<!-- 非例行维修工作费用 -->
		<div class="panel panel-primary margin-bottom-8">
			<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
				<a data-toggle="collapse" data-parent="#accordion" href="#nonRoutinePaymentCollapsed" class="">
				<div class="pull-left">
				<input class="selectCheckbox" style="margin-top:6px;margin-right:8px;" type="checkbox">
				</div>
				<div class="pull-left">
				
				<div class="font-size-12">费用 </div>
				<div class="font-size-9 ">Payment</div>
				</div>
				<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
				
				<div class="pull-left" style="margin-left:10px;height:32px;line-height:32px;">
					<span>非例行工时共计：</span><span id="flxgs"></span>
					<span style="margin-left:15px;">非例行工时费共计：</span><span id="flxfy"></span>
					<span style="margin-left:15px;">常用耗材费用：</span><span id="cyhcfy"></span>
				</div>
				<div class="clearfix"></div>
				</a>
			</div>
			<div id="nonRoutinePaymentCollapsed" class="panel-collapse collapse" style="height: auto;">
				<div class="panel-body padding-left-0 padding-right-0" style="padding-bottom:0px;padding-top:8px;">
				<label style="padding-left: 15px;padding-top: 5px;">1）非例行维修工作费用</label>
			    <table id="" class="table table-thin table-striped table-hover table-set" style="margin-bottom:0px !important;">
						<thead>                              
							<tr>
								<th class='colwidth-7'>
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class='colwidth-10'>
									<div class="font-size-12 line-height-18">日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class='colwidth-10'>
								    <div class="font-size-12 line-height-18">工卡号</div>
									<div class="font-size-9 line-height-18">Work Card No.</div>
							    </th>
							    <th class='colwidth-12'>
								    <div class="font-size-12 line-height-18">内容</div>
									<div class="font-size-9 line-height-18">Content</div>
							    </th>
							    <th class='colwidth-7'>
								    <div class="font-size-12 line-height-18">工时</div>
									<div class="font-size-9 line-height-18">MHRs</div>
							    </th>
							</tr>
						</thead>
						
						<tbody id="nonRoutinePaymentList">
						</tbody>
					</table>
					<label style="padding-left: 15px;padding-top: 5px;">2）航材费用</label>
					<div class="clearfix"></div>
					<label style="padding-left: 50px;padding-top: 5px;">常用耗材费  ：<span id="cyhcfywb"></span></label>
			    </div>
			</div>
		</div>
		
		</div>
		<div class="panel-footer" style="padding-top:5px;padding-bottom:5px;">
			<div class="col-xs-12 padding-left-0 padding-right-0">
				<div class="input-group">
					<span class="input-group-addon modalfootertip">
			        	<i class="glyphicon glyphicon-info-sign alert-info" style="display: none;"></i><p class="alert-info-message"></p>
					</span>
			        <span class="input-group-addon modalfooterbtn">
			        	<div class="btn-group dropup">
						    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" style="height:34px;" onclick='monthlyReport.exportWord()'>
						    	<div class="font-size-12" style=''>导出</div>
								<div class="font-size-9 " style='margin-top:0px;'>Export</div>
                            </button>
						</div>
			        </span>
		        </div>
			</div>
			<div class="clearfix"></div>
		</div>
		</div>
	</div>
	
	
	<%@ include file="/WEB-INF/views/produce/maintenancemgnt/workhourconfig.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenancemgnt/monthlyreport.js"></script>
</body>
</html>