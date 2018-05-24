<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>飞机维修监控</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
	.spacing {
		margin-left:3px;
		margin-right:3px;
	}
</style>

<script type="text/javascript">
	var paramJgdm = '${param.dprtcode}';
	var paramFjzch = '${param.fjzch}';
	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				var selectTab = $("#tablist").find("li.active").index();
				
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					if(selectTab == 0){
						aircraft_maintenance_monitoring_main.search();
					}else if(selectTab == 1){
						eo_monitoring_main.search();
					}else if(selectTab == 2){
						nrc_work_order_main.search();
					}else if(selectTab == 3){
						check_monitoring_main.search();
					}
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content tabcontent-main" id="maintenance_monitoring_main">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="clearfix"></div>
			<div class="panel-body">
				<div class='searchContent margin-top-0 row-height'>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">飞机注册号</div>
							<div class="font-size-9 ">A/C REG</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
							
							<div class="input-group" >
								
								<select id="fjzch_search" class="form-control" onchange="maintenance_monitoring_main.changeModel()">
									<option value="" selected="selected">显示全部All</option>
								</select>
								
		                     	<span  class='input-group-btn' title="当前状态  Status" >
									<i class="current-status glyphicon glyphicon glyphicon-list color-blue cursor-pointer"  style="font-size:15px;float: right;" ></i>
								</span>
		                	</div>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">剩余</div>
							<div class="font-size-9">Remain</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0 input-group" >
							<input type="text" id="sy_search" class="form-control readonly-style" ondblclick="maintenance_monitoring_main.openSurplusWin()" readonly/>
							<span class="input-group-btn">
								<button type="button" id="SY_search_btn" class="btn btn-default" data-toggle="modal" onclick="maintenance_monitoring_main.openSurplusWin()">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
						</span>
						<div  class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' id="warning_search_checkbox" name="warning_search_checkbox" style='vertical-align:text-bottom;' onchange="maintenance_monitoring_main.changeModel()" />
								&nbsp;仅显示预警项目
							</label>
						</div>
					</div>
					
					<!-- 关键字搜索 -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " style='padding-left:15px;float: right;'>
						<div class="col-xs-12 input-group input-group-search">
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"  id="btn" onclick="maintenance_monitoring_main.more();">
								<div class='input-group'>
								<div class="input-group-addon">
								<div class="font-size-12">更多</div>
								<div class="font-size-9 margin-top-5" >More</div>
								</div>
								<div class="input-group-addon">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
								</div>
								</div>
					   			</button>
		                	</div>
						</div>
					</div>
					
					<!-- 更多的搜索框 -->
					<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-bottom-10 display-none search-height border-cccccc" id="divSearch">
						
						<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">计划</div>
								<div class="font-size-9">Plan</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 input-group">
								<input id="plan_search" type="text" class="form-control readonly-style" ondblclick="maintenance_monitoring_main.openPlanWin()" readonly/>
								<span class="input-group-btn">
									<button type="button" id="plan_search_btn" class="btn btn-default" data-toggle="modal" onclick="maintenance_monitoring_main.openPlanWin()">
										<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">ATA章节号</div>
								<div class="font-size-9">ATA</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 input-group">
								<input type="text" id="zjh_search" class="form-control readonly-style" ondblclick="maintenance_monitoring_main.openChapterWin()"  maxlength="20"  readonly/>
								<span class="input-group-btn">
									<button type="button" id="zjh_search_btn" class="btn btn-default" data-toggle="modal" onclick="maintenance_monitoring_main.openChapterWin()">
										<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="dprtcode_search" class="form-control" onchange="maintenance_monitoring_main.changeDprt()" >
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<!-- <div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
							<button type="button"
								class="btn btn-primary padding-top-1 padding-bottom-1"
								onclick="maintenance_monitoring_main.searchreset();">
								<div class="font-size-12">重置</div>
								<div class="font-size-9">Reset</div>
							</button>
						</div> -->
					</div>
					<div class='clearfix'></div>
					
				</div>
				
				<div class="clearfix"></div>
				
				<!-----标签导航start---->
				<div class='first-tab-style' c-height='60%'>
				<ul class="nav nav-tabs tabNew-style" role="tablist" id="tablist">
				  	<li class="active">
				  		<a id='aircraftMaintenanceTab' href="#aircraftMaintenance" onclick="maintenance_monitoring_main.loadAircaraftMaintenanceMonitoring()" data-toggle="tab">
				  			<div class="font-size-12 line-height-12">飞机维修项目监控</div>
			                <div class="font-size-9 line-height-9">Maintenance Monitoring</div>
				  		</a>
				  	</li>
				  	<li >
				  		<a href="#EOMonitoring" onclick="maintenance_monitoring_main.loadEOMonitoring()" data-toggle="tab">
				  			<div class="font-size-12 line-height-12">EO监控</div>
			                <div class="font-size-9 line-height-9">EO Monitoring</div>
				  		</a>
				  	</li>
				  	<li >
				  		<a href="#POMonitoring" onclick="maintenance_monitoring_main.loadPOMonitoring()" data-toggle="tab">
				  			<div class="font-size-12 line-height-12">生产指令监控</div>
			                <div class="font-size-9 line-height-9">Production Order Monitoring</div>
				  		</a>
				  	</li>
				  	<li>
				  		<a href="#NRCWorkOrder" onclick="maintenance_monitoring_main.loadNRCWorkOrder()" data-toggle="tab">
				  			<div class="font-size-12 line-height-12">其它指令</div>
			                <div class="font-size-9 line-height-9">Other Command</div>
				  		</a>
				  	</li>
				  	<li>
				  		<a href="#checkMonitoring" onclick="maintenance_monitoring_main.loadCheckMonitoring()" data-toggle="tab">
				  			<div class="font-size-12 line-height-12">已选择(<span id="check_count">0</span>)</div>
			                <div class="font-size-9 line-height-9">Selected</div>
				  		</a>
				  	</li>
				  	<li>
				  		<a href="#burstification" onclick="maintenance_monitoring_main.loadBurstification()" data-toggle="tab">
				  			<div class="font-size-12 line-height-12">预组包(<span id="package_count">0</span>)</div>
			                <div class="font-size-9 line-height-9">Package</div>
				  		</a>
				  	</li>
				</ul>
				<!-----标签内容start---->
		      	<div class="tab-content margin-bottom-10 ">
					<div class="tab-pane fade in active"  id="aircraftMaintenance">
						<%@ include file="/WEB-INF/views/produce/maintenancemonitoring/aircraft_maintenance_monitoring_main.jsp"%>
					</div>
			        <div class="tab-pane fade" id="EOMonitoring">
		          		<%@ include file="/WEB-INF/views/produce/maintenancemonitoring/eo_monitoring_main.jsp"%>
			        </div>
			        <div class="tab-pane fade" id="POMonitoring">
		          		<%@ include file="/WEB-INF/views/produce/maintenancemonitoring/po_monitoring_main.jsp"%>
			        </div>
			        <div class="tab-pane fade" id="NRCWorkOrder">
		          		<%@ include file="/WEB-INF/views/produce/maintenancemonitoring/nrc_work_order_main.jsp"%>
			        </div>
			        <div class="tab-pane fade" id="checkMonitoring">
		          		<%@ include file="/WEB-INF/views/produce/maintenancemonitoring/check_monitoring_main.jsp"%>
			        </div>
			        <div class="tab-pane fade" id="burstification">
		          		<%@ include file="/WEB-INF/views/produce/maintenancemonitoring/burstification_main.jsp"%>
			        </div>
			        
				</div>
				</div>
				<!-- 隐藏的div -->
				<div id="tab_child_div" class='displayTabContent' style='display:none;'>
				    <!-- <div class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0 padding-top-10"> -->
				     <div id="hideIcon" class="pull-right" style='height:1px;padding-right:8px;margin-top:1px;'>
						<img src="${ctx}/static/images/down.png" onclick='maintenance_monitoring_main.hideBottom()' style="width:33px;cursor:pointer;">
					 </div>
	            	<ul id="myChildTab" class="nav nav-tabs tabNew-style" style="padding-top:0px !important;">
			
						<li id='md_tab' class="child_li_div active" >
							<a href='#mdTab' data-toggle="tab"  >
								<div class="font-size-12 line-height-12">维修清单</div>
			                    <div class="font-size-9 line-height-9">Maintenance Detail</div>
			                 </a>
						</li>
						<li id='tool_tab' class="child_li_div" >
							<a href='#toolTab' data-toggle="tab"  >
								<div class="font-size-12 line-height-12">航材工具</div>
			                    <div class="font-size-9 line-height-9">Material&Tool</div>
			                 </a>
						</li>
						<li id='history_tab' class="child_li_div" >
							<a href='#historyTab' data-toggle="tab" >
								<div class="font-size-12 line-height-12">执行历史</div>
			                    <div class="font-size-9 line-height-9">Execution history</div>
							</a>
						</li>
					
					</ul>
	                <div class="tab-content" style='padding:0px;'>
	                   
						<div id="mdTab" class="child_li_div tab-pane fade active in" >
							<%@ include file="/WEB-INF/views/produce/maintenancemonitoring/maintenance_detail_view.jsp"%> <!-- 维修清单 --> 
						</div>
						<div id="toolTab" class="child_li_div tab-pane fade" >
							<%@ include file="/WEB-INF/views/common/produce/material_tool_list.jsp" %> <!-- 航材工具 -->
						</div>
						<div id="historyTab" class="child_li_div tab-pane fade">
							<%@ include file="/WEB-INF/views/common/produce/executionHistory.jsp" %>  <!-- 执行历史 -->
						</div>
					</div>
	                    
                    <!-- </div> -->
				   <div class='clearfix'></div>
				</div>
				
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenancemonitoring/maintenance_monitoring_main.js"></script>
	<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%><!-- ATA章节对话框 -->
	<%@ include file="/WEB-INF/views/open_win/surplus.jsp"%><!-------剩余对话框 -------->
	<%@ include file="/WEB-INF/views/produce/maintenanceinitialization/initialization_monitoring_edit.jsp"%><!-------编辑对话框 -------->
	<%@ include file="/WEB-INF/views/produce/workpackage/135/workpackage_modal.jsp"%><!-- -新增工包 -->
	<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>
	<%@ include file="/WEB-INF/views/open_win/department.jsp"%><!-- 选择部门 -->
	<%@ include file="/WEB-INF/views/open_win/firm_search.jsp"%><!-- 选择外委供应商-->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<%@ include file="/WEB-INF/views/open_win/current_monitor_data.jsp"%><!------当前监控对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
	
	<%@ include file="/WEB-INF/views/open_win/inventory_distribution_details_view.jsp"%><!-------库存分布详情 -------->
	<%@ include file="/WEB-INF/views/open_win/equivalent_substitution_view.jsp"%><!-------等效替代 -------->
	
	<%@ include file="/WEB-INF/views/common/produce/feedback_replacement_tab_view.jsp" %>  <!-- 完工反馈及拆换件记录详情弹窗 -->
</body>
</html>