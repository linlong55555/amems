<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
.progress {
    margin-bottom: 5px;
    margin-top: 5px;
}
.progress-bar {
    box-shadow: 0 0;
}
</style>
</head>
<body >

	<div class="page-content" >
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar">
			</div>

			<div class="panel-body padding-bottom-0">
				<div class="col-lg-12">
					<div class="col-lg-4 padding-right-0 row-height">
						<span class="pull-left col-lg-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12">飞机排班日期</div>
							<div class="font-size-9">Flight Date</div>
						</span>
						<div class="col-lg-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control date-range-picker" id="aircraftschedulingDate" readonly/>
						</div>
					</div>
					<div class="col-lg-3 padding-right-0">
						<span class="pull-left col-lg-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12">基地</div>
							<div class="font-size-9">Station</div>
						</span>
						<div class="col-lg-8 padding-left-8 padding-right-0">
							<select class='form-control' id='jd_search' name="jd_search" onchange="refreshSchedulingTable()">
								<c:choose>
									<c:when test="${baseList!=null && fn:length(baseList) > 0}">
										<c:forEach items="${baseList}" var="base">
											<option value="${base.id}">${base.jdms}</option>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<option value="">暂无基地</option>
									</c:otherwise>
								</c:choose>
							</select>
						</div>
					</div>
					<div class="col-lg-3 padding-right-0">
						<span class="pull-left col-lg-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-lg-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " name="dprtcode" onchange="refreshBase()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				
				<div  class="col-xs-12 text-center padding-left-0 padding-right-0 table-h"  style="overflow-x:auto">
		
					<table class="table table-thin table-bordered text-center table-hover tableRe" id="schedulingTable">
					</table>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-------飞机排班维护模态框 start-------->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-header">
					<div class="row">
						<div class="col-lg-3 padding-right-0">
							<div class="font-size-16 line-height-18">飞机排班维护</div>
							<div class="font-size-9 ">Aircraft Scheduling Maintenance</div>
						</div>
						<div class="col-lg-4 col-lg-offset-5 padding-right-0">
							<span class="pull-left col-lg-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12">飞机:</div>
								<div class="font-size-12">日期:</div>
							</span>
							<div class="col-lg-8 padding-left-8 padding-right-0">
								<span id="fjzch_updateModal" class="font-size-12">
								<!-- <button type="button" class="close pull-right" data-dismiss="modal" aria-hidden="true"  style="padding-right: 30px;">&times;</button> -->
								</span>
								<div id="rq_updateModal" class="font-size-12"></div>
							</div>
						</div>
					</div>
				</div>
				<input type="hidden" id="state_updateModal"/>
				<div class="modal-body padding-bottom-0 padding-top-10">
					<div class="panel panel-default text-center">
					    <div class="panel-heading">
					    	<div class="row">
					    		<div class="col-lg-1 padding-right-0">
									<button class="btn btn-default btn-xs" type="button" onclick="buildSchedulingRow()" title="新增飞机排班">
										<i class="icon-plus cursor-pointer color-default cursor-pointer"></i>
									</button>
					    		</div>
					    		<div class="col-lg-4 padding-right-0">
					    			<div class="font-size-12">开始时间</div>
									<div class="font-size-9">Begin Time</div>
					    		</div>
					    		<div class="col-lg-4 padding-right-0">
					    			<div class="font-size-12">结束时间</div>
									<div class="font-size-9">End Time</div>
					    		</div>
					    		<div class="col-lg-3 padding-right-0">
					    			<div class="font-size-12">总计</div>
									<div class="font-size-9">Total</div>
					    		</div>
					    	</div>
					    </div>
					    <div class="panel-body" id="updateModalBody">
					    </div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button"
						class="btn btn-primary padding-top-1 padding-bottom-1"
						 onclick="save()">
						<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
					</button>
					<button type="button"
						class="btn btn-primary padding-top-1 padding-bottom-1"
						data-dismiss="modal" onclick="cancel()">
						<div class="font-size-12">取消排班</div>
						<div class="font-size-9">Cancel</div>
					</button>
					<button type="button"
						class="btn btn-primary padding-top-1 padding-bottom-1"
						data-dismiss="modal">
						<div class="font-size-12">关闭</div>
						<div class="font-size-9">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-------飞机排班维护模态框 End-------->
	
	<!-------飞机排班维护模态框 start-------->
	<div class="modal fade" id="planeViewModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog"  style="width: 1350px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"  style="padding-right: 30px;">&times;</button>
					<div class="font-size-16 line-height-18">飞机视图</div>
					<div class="font-size-9 ">Aircraft View</div>
				</div>
				<div class="modal-body padding-bottom-0">
					<div class="row" style="margin-left: 0;margin-right: 0;overflow: auto;" id="outerDiv">
						<table class="table table-thin table-bordered text-center tableRe">
							<thead>
								<tr>
								<th style="width: 100px;padding: 0px;">  	
									<div class="out">
										<b>小时</b>
										<em>日期</em>
									</div>
								</th>
								<th style="vertical-align:middle;width: 38px;">0</th>
								<th style="vertical-align:middle;width: 38px;">1</th>
								<th style="vertical-align:middle;width: 38px;">2</th>
								<th style="vertical-align:middle;width: 38px;">3</th>
								<th style="vertical-align:middle;width: 38px;">4</th>
								<th style="vertical-align:middle;width: 38px;">5</th>
								<th style="vertical-align:middle;width: 38px;">6</th>
								<th style="vertical-align:middle;width: 38px;">7</th>
								<th style="vertical-align:middle;width: 38px;">8</th>
								<th style="vertical-align:middle;width: 38px;">9</th>
								<th style="vertical-align:middle;width: 38px;">10</th>
								<th style="vertical-align:middle;width: 38px;">11</th>
								<th style="vertical-align:middle;width: 38px;">12</th>
								<th style="vertical-align:middle;width: 38px;">13</th>
								<th style="vertical-align:middle;width: 38px;">14</th>
								<th style="vertical-align:middle;width: 38px;">15</th>
								<th style="vertical-align:middle;width: 38px;">16</th>
								<th style="vertical-align:middle;width: 38px;">17</th>
								<th style="vertical-align:middle;width: 38px;">18</th>
								<th style="vertical-align:middle;width: 38px;">19</th>
								<th style="vertical-align:middle;width: 38px;">20</th>
								<th style="vertical-align:middle;width: 38px;">21</th>
								<th style="vertical-align:middle;width: 38px;">22</th>
								<th style="vertical-align:middle;width: 38px;">23</th>
								<th style="vertical-align:middle;">维护人</th>
								<th style="vertical-align:middle;">维护时间</th>
								</tr>
				          	</thead>
							<tbody id="planeViewBody">
							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer">
				</div>
			</div>
		</div>
	</div>
	<!-------飞机排班维护模态框 End-------->

	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<script type="text/javascript" src="${ctx}/static/js/thjw/productionmessage/aircraftscheduling/aircraftscheduling_main.js"></script>
</body>
</html>