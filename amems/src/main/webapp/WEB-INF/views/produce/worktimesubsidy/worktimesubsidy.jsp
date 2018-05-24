<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>工时补贴统计</title>
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
		<div class=" panel-body padding-bottom-0" id='demand_apply_body'>
		<div class='text-center worktimeInfo' style="position:relative;" >
		    <div style="width:400px;margin:0 auto;">
			<span class="pull-left" style="line-height:34px;margin-right:8px;font-size:18px;">维修工程部补贴统计表</span>
				<input type="text" class="form-control pull-left" style="width:70px;" name="datetimepicker" id="year" data-date-format="yyyy-mm" readonly />			
			<span class="pull-left" style="line-height:34px;margin-right:8px;margin-left:8px;" onclick="loadDatail()">
				<i class="icon-refresh color-blue cursor-pointer" ></i>
			</span>
			<div class="clearfix"></div>
			</div>
			<a href="javascript:;" style="position:absolute;right:0px;top:10px;" onclick="showHourTime()">设置工时单价</a>
		</div>
		<!-- 人员补贴汇总 -->
		<div class="panel panel-primary margin-top-10 margin-bottom-8" id="summary-panel" >
			<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
				<a data-toggle="collapse" data-parent="#accordion" href="#summaryCollapsed" class="">
				<div class="pull-left">
				<input class="selectCheckbox" checked="checked" style="margin-top:6px;margin-right:8px;" type="checkbox">
				</div>
				<div class="pull-left">
				
				<div class="font-size-12">人员补贴汇总</div>
				<div class="font-size-9 ">Summary Of Personnel Subsidy</div>
				</div>
				<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
				<div class="clearfix"></div>
				</a>
			</div>
			<div id="summaryCollapsed" class="panel-collapse collapse in" style="height: auto;">
				<div class="panel-body padding-left-0 padding-right-0" style="padding-bottom:0px;padding-top:8px;">
			    <table id="" class="table table-thin table-striped table-hover table-set" style="margin-bottom:0px !important;">
						<thead>                              
							<tr>
								<th class='colwidth-5'>
									<div class="font-size-12 line-height-18" >序号</div>
									<div class="font-size-9 line-height-18">Item</div>
								</th>
								<th class='colwidth-13' onclick="" name="">
									<div class="font-size-12 line-height-18">员工工号</div>
									<div class="font-size-9 line-height-18">Employee No.</div>
								</th>
								<th class='colwidth-13' onclick="" name="" >
									    <div class="font-size-12 line-height-18">姓名</div>
										<div class="font-size-9 line-height-18">Name</div>
							    </th>
								<th  onclick="" name="" style="text-align:left !important;">
									<div class="font-size-12 line-height-18">工时补贴金额（<span id="total_biz"></span>）</div>
									<div class="font-size-9 line-height-18">Amount Of Time</div>
								</th>
							</tr>
						</thead>
						<tbody id="totalList">
						</tbody>
					</table>
			    </div>
			</div>
		</div>
		
		<!-- 维修工时明细 -->
		
		<div class="panel panel-primary margin-bottom-8" >
			<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
				<a data-toggle="collapse" data-parent="#accordion" href="#maintenanceCollapsed" class="">
				<div class="pull-left">
				<input class="selectCheckbox" style="margin-top:6px;margin-right:8px;" type="checkbox">
				</div>
				<div class="pull-left">
				
				<div class="font-size-12">维修工时明细</div>
				<div class="font-size-9 ">Maintenance Time And Detail</div>
				</div>
				<div class="pull-left" style="margin-left:10px;height:32px;line-height:32px;">
					<span >总计工时：</span><span id="wx_zgs">0</span>
					<span style="margin-left:15px;">总计金额：</span><span id="wx_zje">0</span>
					<span style="margin-left:15px;">工时单价</span><span id="wx_dj">:0</span>
				</div>
				<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
				<div class="clearfix"></div>
				</a>
			</div>
			<div id="maintenanceCollapsed" class="panel-collapse collapse" style="height: auto;">
				<div class="panel-body padding-left-0 padding-right-0" style="padding-bottom:0px;padding-top:8px;">
			    <table id="" class="table table-thin table-striped table-hover table-set" style="margin-bottom:0px !important;">
						<thead>                              
							<tr>
								<th class='colwidth-7'>
									<div class="font-size-12 line-height-18" >工作日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class='colwidth-10' onclick="" name="">
									<div class="font-size-12 line-height-18">员工工号</div>
									<div class="font-size-9 line-height-18">Employee No.</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									    <div class="font-size-12 line-height-18">姓名</div>
										<div class="font-size-9 line-height-18">Name</div>
							    </th>
							    <th class='colwidth-7' onclick="" name="" >
									    <div class="font-size-12 line-height-18">工时</div>
										<div class="font-size-9 line-height-18">Working Hours</div>
							    </th>
								<th  onclick="" name=""  class="colwidth-12" style="text-align:left !important;">
									<div class="font-size-12 line-height-18">工时补贴金额（<span id="wx_biz"></span>）</div>
									<div class="font-size-9 line-height-18">Amount Of Time</div>
								</th>
								<th class='colwidth-30' onclick="" name="" >
									    <div class="font-size-12 line-height-18">工作描述</div>
										<div class="font-size-9 line-height-18">Desc</div>
							    </th>
							    <th class='colwidth-10' onclick="" name="" >
									    <div class="font-size-12 line-height-18">工卡号</div>
										<div class="font-size-9 line-height-18">Card No.</div>
							    </th>
							</tr>
						</thead>
						<tbody id="wxgsList">
						</tbody>
					</table>
			    </div>
			</div>
		</div>
		
		<!-- 清洁工时明细 -->
		
		<div class="panel panel-primary margin-bottom-8">
			<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
				<a data-toggle="collapse" data-parent="#accordion" href="#cleaningCollapsed" class="">
				<div class="pull-left">
				<input class="selectCheckbox" style="margin-top:6px;margin-right:8px;" type="checkbox">
				</div>
				<div class="pull-left">
				
				<div class="font-size-12">清洁工时明细 </div>
				<div class="font-size-9 ">Cleaner Detail</div>
				</div>
				<div class="pull-left" style="margin-left:10px;height:32px;line-height:32px;">
					<span >总计工时：</span><span id="qj_zgs">0</span>
					<span style="margin-left:15px;">总计金额：</span><span id="qj_zje">0</span>
					<span style="margin-left:15px;">工时单价</span><span id="qj_dj">:0</span>
				</div>
				<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
				<div class="clearfix"></div>
				</a>
			</div>
			<div id="cleaningCollapsed" class="panel-collapse collapse" style="height: auto;">
				<div class="panel-body padding-left-0 padding-right-0" style="padding-bottom:0px;padding-top:8px;">
			    <table id="" class="table table-thin table-striped table-hover table-set" style="margin-bottom:0px !important;">
						<thead>                              
							<tr>
								<th class='colwidth-7'>
									<div class="font-size-12 line-height-18" >工作日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class='colwidth-10' onclick="" name="">
									<div class="font-size-12 line-height-18">员工工号</div>
									<div class="font-size-9 line-height-18">Employee No.</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									    <div class="font-size-12 line-height-18">姓名</div>
										<div class="font-size-9 line-height-18">Name</div>
							    </th>
							    <th class='colwidth-7' onclick="" name="" >
									    <div class="font-size-12 line-height-18">工时</div>
										<div class="font-size-9 line-height-18">Working Hours</div>
							    </th>
								<th  onclick="" name=""  class="colwidth-12" style="text-align:left !important;">
									<div class="font-size-12 line-height-18">工时补贴金额（<span id="qj_biz"></span>）</div>
									<div class="font-size-9 line-height-18">Amount Of Time</div>
								</th>
								<th class='colwidth-30' onclick="" name="" >
									    <div class="font-size-12 line-height-18">工作描述</div>
										<div class="font-size-9 line-height-18">Desc</div>
							    </th>
							    <th class='colwidth-10' onclick="" name="" >
									    <div class="font-size-12 line-height-18">工卡号</div>
										<div class="font-size-9 line-height-18">Card No.</div>
							    </th>
							</tr>
						</thead>
						
						<tbody id="qjgsList">
						</tbody>
					</table>
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
						    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" style="height:34px;" onclick='mgnt_main.openWinAdd(10)'>
						    	<div class="font-size-12" style=''>导出</div>
								<div class="font-size-9 " style='margin-top:0px;'>Download</div>
                            </button>
						    <button type="button" class="btn btn-primary dropdown-toggle dropdown-toggle-new pull-left" data-toggle="dropdown"  >
						       <i class="font-size-15 icon-caret-down"></i>
						    </button>
						    <ul class="dropdown-menu text-left dropdown-toggle-menu-new dropdown-menu-right" >
								<li ><a href="JavaScript:exportPdf();">导出PDF</a></li>
								<li ><a href="JavaScript:exportExcel();" >导出Excel</a></li>
							</ul>
						</div>
			        </span>
		        </div>
			</div>
			<div class="clearfix"></div>
		</div>
		</div>
	</div>
	
	
	<%@ include file="/WEB-INF/views/produce/worktimesubsidy/settingtimeprice.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/worktimesubsidy/worktimesubsidy.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
</body>
</html>