<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>飞机排班</title>
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
		<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			  <div  class='searchContent  row-height'>
				<div class="margin-top-0">
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12   padding-left-0 padding-right-0  form-group">
						<span class="pull-left col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12">排班日期</div>
							<div class="font-size-9">Flight Date</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<input type="text" class="form-control date-range-picker readonly-style"  readonly="readonly"  id="aircraftschedulingDate" />
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">基地</div>
							<div class="font-size-9">Station</div>
						</span>
						<div  class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<select id="jd_search" name="jd_search" class="form-control" onchange="refreshSchedulingTable()">
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
						</span>
						<div  class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " onchange="onchangeDprtcode()">								
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class='clearfix'></div>
				</div>
			</div>
			<div class='table_pagination'>
				<div  class="col-xs-12 text-center padding-left-0 padding-right-0 table-height"  c-height="55%" id="tableId" style="overflow-x: auto;">
					<table class="table table-thin table-bordered text-center table-hover tableRe" id="schedulingTable">
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
	
<!-------飞机排班维护模态框 start-------->	
<div class="modal fade in modal-new" id="updateModal" tabindex="-1" role="dialog"  aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style='width:65%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
				<h4 class="modal-title" >
					<div class='pull-left'>
						<div class="font-size-14">飞机排班维护</div>
						<div class="font-size-12">Aircraft Scheduling Maintenance</div> 
					</div>
					<div class='pull-right' >
				  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
			  		<div class='clearfix'></div>
				</h4>
			</div>
			<input type="hidden" id="state_updateModal"/>
            <div class="modal-body" id="AssignEndModalBody">
   		        <div class="col-xs-12 margin-top-0 padding-left-10 padding-right-8 ">
 					<div class="input-group-border  padding-left-0 fixedModalHeader margin-top-8">
							<div class="col-xs-4 padding-left-0 padding-right-0 form-group">
								<label class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2" id="chinaHead">飞机</div>
									<div class="font-size-9" id="englishHead">Aircraft</div>
								</label>
								<div class="col-xs-9 padding-leftright-8">
									<span class="form-control"   id="fjzch_updateModal" name="fjzch_updateModal" disabled="disabled"></span>
								</div>
							</div>
							<div class="col-xs-4 padding-left-0 padding-right-0 form-group">
								<label class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2" id="chinaHead">日期</div>
									<div class="font-size-9" id="englishHead">Date</div>
								</label>
								<div class="col-xs-9 padding-leftright-8">
									<div class="form-control"   id="rq_updateModal" name="rq_updateModal" disabled="disabled"></div>
								</div>
							</div>
							<div class='clearfix'></div>
						</div>
						<div class="nonFixedContent margin-top-0 margin-bottom-0" >
							<div class="panel panel-default text-center margin-top-0 margin-bottom-0" >
							    <div class="panel-heading">
							    	<div class="row col-xs-12">
							    		 <div class="col-xs-12 margin-top-0 padding-left-10 padding-right-8">
								    		<div class="col-lg-1 padding-right-0 padding-left-0">
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
								    		<div class='clearfix'></div>
								    	</div>
							    	</div>
							    	<div class='clearfix'></div>
							    </div>
							    <div class="panel-body" id="updateModalBody">
							    </div>
							</div>
						</div>
						
						<div class='clearfix'></div>
						<div class='table_pagination padding-leftright-8'>
						</div>
					
	           </div>
               <div class="clearfix"></div>              
           	</div>
            <div class="clearfix"></div>              
   			<div class="modal-footer ">
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="pull-left modalfootertip" >
			                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
						<span class="input-group-addon modalfooterbtn">
						   <button id="baocun" type="button" onclick="save();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
						   <button id="baocun" type="button" onclick="cancel();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">取消排班</div>
								<div class="font-size-9">Cancel</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
						</span>
					</div>
				</div>
	 		</div>
		</div>
   </div>
</div>	
<!-------飞机排班维护模态框 End-------->	

<!-------飞机排班维护模态框 start-------->
<div class="modal fade in modal-new" id="planeViewModal" tabindex="-1" role="dialog"  aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style='width:98%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
				<h4 class="modal-title" >
					<div class='pull-left'>
						<div class="font-size-14">飞机视图</div>
						<div class="font-size-12">Aircraft View</div> 
					</div>
					<div class='pull-right' >
				  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
			  		<div class='clearfix'></div>
				</h4>
			</div>
            <div class="modal-body" id="AssignEndModalBody">
   		        <div class="col-xs-12 margin-top-0 padding-left-10 padding-right-8">
 					<div class=" margin-top-8 padding-left-0">
						<div class="modal-body padding-bottom-0">
							<div class="row col-xs-12 padding-right-0" style="overflow: auto;" id="outerDiv" >
								<table class="table table-thin table-bordered text-center tableRe">
									<thead>
										<tr>
										<th style="width: 90px;padding: 0px;">  	
											<div class="plane-scheduling">
												<span class='first-span'>小时</span>
												<span class='second-span'>日期</span>
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
						<div class='clearfix'></div>
						<div class='table_pagination padding-leftright-8'>
						</div>
					</div>
	           </div>
               <div class="clearfix"></div>              
           	</div>
            <div class="clearfix"></div>              
   			<div class="modal-footer ">
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="pull-left modalfootertip" >
			                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
						<span class="input-group-addon modalfooterbtn">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
						</span>
					</div>
				</div>
	 		</div>
		</div>
   </div>
</div>	
<!-------飞机排班维护模态框 End-------->	
	

<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/aircraftscheduling/aircraftscheduling_main.js"></script>
</body>
</html>