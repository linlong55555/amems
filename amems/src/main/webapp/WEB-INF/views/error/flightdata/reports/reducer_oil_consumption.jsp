<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<!-- 机身减速器滑油消耗量统计表 -->
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript" src="${ctx}/static/lib/echarts/echarts.js"></script>
<script type="text/javascript">
	var planeData = $.parseJSON('${planeData}');//飞机注册号
</script>
</head>
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar">
			</div>
		
			<div class="clearfix"></div>
			
			<div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="pull-right">
						<div class="pull-left">
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="font-size-12">飞机注册号</div>
								<div class="font-size-9">A/C REG</div>
							</div>
							<div class="pull-left padding-left-8 padding-right-0" style="width: 250px; margin-right:5px;">
								<select class='form-control' id='reducer_oil_consumption_fjzch' name="reducer_oil_consumption_fjzch" >
								</select>
							</div>
						</div>
						<div class="pull-left">
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="font-size-12">飞行日期</div>
								<div class="font-size-9">Flight Date</div>
							</div>
							<div class="pull-left padding-left-8 padding-right-0" style="width: 250px; margin-right:5px;">
								<input type="text" class="date-range-picker form-control" id="reducer_oil_consumption_between_date" readonly />
							</div>
						</div>
						<div class="pull-left">
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</div>
							<div class="pull-left padding-left-8 padding-right-0" style="width: 250px; margin-right:5px;">
								<select id="reducer_oil_consumption_dprtcode" class="form-control " name="reducer_oil_consumption_dprtcode" >
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
									</c:forEach>
								</select>
							</div>
						</div>
				        <div class="padding-left-0 padding-right-0 margin-bottom-10 form-group pull-right">   
				          	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="reducer_oil_consumption.load();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
							</button>
				        </div>
					</div>
				</div>
				
				<!-- chart区 start -->
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="height:400px" id="reducer_oil_consumption_chart">
				</div>
				<!-- chart区  end -->
				<!-- table start -->
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;">
				<table class="table table-thin table-bordered">
					<thead>
						<tr>
							<th>
								<div class="font-size-12 line-height-18">序号</div>
								<div class="font-size-9 line-height-18">No.</div>
							</th>
							<th>
								<div class="font-size-12 line-height-18">飞行日期</div>
								<div class="font-size-9 line-height-18">Flight Date</div>
							</th>
							<th>
								<div class="font-size-12 line-height-18">飞行时间</div>
								<div class="font-size-9 line-height-18">Flight HRS</div>
							</th>
							<th>
								<div class="font-size-12 line-height-18">MGB</div>
								<div class="font-size-9 line-height-18">MGB</div>
							</th>
							<th>
								<div class="font-size-12 line-height-18">IGB</div>
								<div class="font-size-9 line-height-18">IGB</div>
							</th>
							<th>
								<div class="font-size-12 line-height-18">TGB</div>
								<div class="font-size-9 line-height-18">TGB</div>
							</th>
						</tr>
					</thead>
					<tbody id="reducer_oil_consumption_list">
					</tbody>
				</table>
				</div>
				<div class=" col-xs-12  text-center" id="reducer_oil_consumption_pagination">
				</div>
				<!-- table end --> 
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/reports/reducer_oil_consumption.js"></script>
</body>
</html>