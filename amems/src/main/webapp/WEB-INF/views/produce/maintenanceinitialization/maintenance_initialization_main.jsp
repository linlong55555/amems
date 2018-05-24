<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>


<script type="text/javascript">
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
						$('#aircraft_maintenance_monitoring_mainSearch').trigger('click');
					}else if(selectTab == 1){
						$('#eo_monitoring_mainSearch').trigger('click');
					}
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content tabcontent-main" id="maintenance_initialization_main">
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
							<select id="fjzch_search" class="form-control" onchange="maintenance_initialization_main.changeModel()">
								<option value="" selected="selected">显示全部All</option>
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0 label-input-div" >
							<select id="dprtcode_search" class="form-control" onchange="maintenance_initialization_main.changeDprt()" >
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
				
				<div class="clearfix"></div>
				
				<!-----标签导航start---->
				<div class='first-tab-style'>
				<ul class="nav nav-tabs tabNew-style" role="tablist" id="tablist">
				  	<li class="active">
				  		<a id='aircraftMaintenanceTab' href="#aircraftMaintenance"  data-toggle="tab">
				  			<div class="font-size-12 line-height-12">飞机维修项目监控</div>
			                <div class="font-size-9 line-height-9">Maintenance Monitoring</div>
				  		</a>
				  	</li>
				  	<li >
				  		<a id='EOmonitoringTab' href="#EOMonitoring"  data-toggle="tab">
				  			<div class="font-size-12 line-height-12">EO监控</div>
			                <div class="font-size-9 line-height-9">EO Monitoring</div>
				  		</a>
				  	</li>
				  	<li >
				  		<a id='POmonitoringTab' href="#POMonitoring"  data-toggle="tab">
				  			<div class="font-size-12 line-height-12">生产指令</div>
			                <div class="font-size-9 line-height-9">Production Order</div>
				  		</a>
				  	</li>
				</ul>
				<!-----标签内容start---->
		      	<div class="tab-content">
					<div class="tab-pane fade in active"  id="aircraftMaintenance">
						<%@ include file="/WEB-INF/views/produce/maintenanceinitialization/aircraft_maintenance_monitoring_main.jsp"%>
					</div>
			        <div class="tab-pane fade" id="EOMonitoring">
		          		<%@ include file="/WEB-INF/views/produce/maintenanceinitialization/eo_monitoring_main.jsp"%>
			        </div>
			        <div class="tab-pane fade" id="POMonitoring">
		          		<%@ include file="/WEB-INF/views/produce/maintenanceinitialization/po_monitoring_main.jsp"%>
			        </div>
				</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenanceinitialization/maintenance_initialization_main.js"></script>
	<%@ include file="/WEB-INF/views/produce/maintenanceinitialization/initialization_monitoring_edit.jsp"%><!-------编辑对话框 -------->
	<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>