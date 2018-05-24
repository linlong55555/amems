<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>飞机维修预测</title>
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var id = "${param.id}";
var pageParam = '${param.pageParam}';
var limitDateStr = '${limitDateStr}';
var currentDateStr = '${currentDateStr}';

	
</script>
<style>
#iconToggleTable:hover{
background:#f9f9f9;

}
.tbody-nonebordered thead tr th{
text-align:left;
}
.tbody-nonebordered tbody tr td{
border-top:0px;
}
</style>

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
					$('#loadlistBtn').trigger('click'); //调用主列表页查询
				}
			}
		});
	});
</script>

</head>

<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content table-table-type">
		<input type="hidden" id="userid" value="${user.id}">
		<input type="hidden" id="id"  />
		<input type="hidden" id="technicalAttachedid"  />
		<input type="hidden" id="djzt"  />
		<input type="hidden" id="state"  />
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<!--导航结束  -->
			<div class="panel-body">
			  <div  class='searchContent margin-top-0  row-height'>
			    	<!-- 上传按钮  -->
					<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<a href="javascript:exportExcel();" permissioncode='produce:maintenance:forecast:main:01'  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission">
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</a> 
					</div>
					
					
					
					<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0" id="acregHtml">
							<select id="fjzch" class="form-control " onchange="maintenance_forecast.changeAc()">
							<option value=""></option>
							<option value="JX_003">JX_003</option>
							<option value="JX_4">JX_4</option>
							</select>
						</div>
					</div>
					<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">截至日期</div>
							<div class="font-size-9">End date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0" >
							<input type="text" id="jzrq"  name="jzrq" class="form-control datepicker " style="width: 100%"  data-date-format="yyyy-mm-dd"  placeholder="请选择日期"   />
						</div>
					</div> 
					<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control " name="dprtcode_search" onchange="maintenance_forecast.changeDprtcode()" >
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<!-- 关键字搜索 -->
					<div class='clearfix'></div>
					<!-- 更多的搜索框 -->
					<div class='searchTable' style='display:none;padding-bottom:10px;'>
					<!-- 表格 -->
					<div>
					<!--  <div class="pull-left" id='iconToggleTable' style='width:30px;border:1px solid #d5d5d5;border-right:0px;display:none;cursor:pointer;vertical-align:middle;text-align:center;' onclick='showTrToggle(this,"current")'><i class='fa fa-angle-double-down ' style='font-size:20px;color:#05a1f6;'></i></div> -->
					 <div  id="tableId">
			                <div class='col-xs-12 padding-left-0 padding-right-0' style='overflow:auto;'>
							<table  id="aircraft_condition_list_table" class="table table-thin tbody-nonebordered table-hover table-set" style='margin-bottom:0px !important;'>
								<thead>
									<tr>
									</tr>
								</thead>
								<tbody id="aircraft_condition_list">
								</tbody>
							</table>
							</div>
							<div class='clearfix'></div>
						</div>	
					<div class='clearfix'></div>
					</div>
				</div>	
				<div class="clearfix"></div>
				</div>
			
				
			
				<%@ include file="/WEB-INF/views/produce/maintenanceforecast/forecast_task.jsp" %> <!--下达指令  -->
		</div>
	</div>
</div>	
  
  <script type="text/javascript" src="${ctx}/static/js/thjw/common/monitor/monitor_unit.js"></script>
  <script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenanceforecast/forecast_list.js"></script>
  <%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
  <script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
 
 <!-- 
 需求：
 1，根据飞机注册号，截至日期，机构查询出飞机的当前值，预期值，目标值。
 2，目标值=当前值+预期值（目标值，预期值都不写表）
 3，搜索是按目标值进行搜索。
 
 任务类型：维修项目/EO/，默认为全部
 维修项目包含:时控，时寿，一般（吴）
 
  -->
</body>
</html>
