<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<!-- 发动机健康监控-->
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript" src="${ctx}/static/lib/echarts/echarts.min.js"></script>
<script type="text/javascript">
	var planeData = $.parseJSON('${planeData}');//飞机注册号
</script>

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
					$('#engineHealthMonitorapuSearch').trigger('click');//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content  ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar">
			</div>
		
			<div class="clearfix"></div>
			
			<div class="panel-body padding-bottom-0">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="pull-right">
						<div class="pull-left">
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="font-size-12">飞机注册号</div>
								<div class="font-size-9">A/C REG</div>
							</div>
							<div class="pull-left padding-left-8 padding-right-0" style="width: 250px; margin-right:5px;">
								<select class='form-control' id='engine_health_monitor_fjzch' name="engine_health_monitor_fjzch" >
								</select>
							</div>
						</div>
						<div class="pull-left">
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="font-size-12">飞行日期</div>
								<div class="font-size-9">Flight Date</div>
							</div>
							<div class="pull-left padding-left-8 padding-right-0" style="width: 250px; margin-right:5px;">
								<input type="text" class="date-range-picker form-control readonly-style" id="engine_health_monitor_between_date" readonly />
							</div>
						</div>
						<div class="pull-left">
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</div>
							<div class="pull-left padding-left-8 padding-right-0" style="width: 250px; margin-right:5px;">
								<select id="engine_health_monitor_dprtcode" class="form-control " name="engine_health_monitor_dprtcode" >
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
									</c:forEach>
								</select>
							</div>
						</div>
				        <div class="padding-left-0 padding-right-0 margin-bottom-10 form-group pull-right">   
				          	<button id="engineHealthMonitorapuSearch" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="engine_health_monitor.load();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
							</button>
				        </div>
					</div>
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-8 padding-right-8">
						<!-- chart区 start -->
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="height:400px" id="engine_health_monitor_chartFirst">
						</div>
						<!-- chart区  end -->
					</div>			
				</div>
				
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineerMonitor/apuMonitor_main.js"></script>
</body>
</html>