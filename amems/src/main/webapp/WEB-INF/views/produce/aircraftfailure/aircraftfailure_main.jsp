<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
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
					aircraftfailure.reload();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				<!-- 搜索框end -->
				<div class='searchContent row-height margin-top-0'>
					<div class="row" style="margin-left: 0px; margin-right: 0px;">
						
						<div class="pull-left form-group">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" onclick="aircraftfailure.exportExcel()">
								<div class="font-size-12">导出</div>
								<div class="font-size-9">Export</div>
							</button>
						</div>
						
						<div class="pull-right form-group">
							<div class="pull-left text-right padding-left-10 padding-right-0">
								<div class="font-size-12">飞机注册号</div>
								<div class="font-size-9">A/C REG</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="padding-left-8 pull-left" style="width: 200px; margin-right:5px;">
								   <select id="fjzch" class="form-control" onchange="aircraftfailure.reload()">
								   </select> 
								</div>
							</div>
							<div class="pull-left text-right padding-left-10 padding-right-0">
								<div class="font-size-12">日期</div>
								<div class="font-size-9">Date</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="padding-left-8 pull-left" style="width: 200px; margin-right:5px;">
								   <input class="form-control date-range-picker" id="fxrq" type="text">
								</div>
							</div>
							<div class="pull-left text-right padding-left-10 padding-right-0">
								<div class="font-size-12">故障报告</div>
								<div class="font-size-9">PIREP.</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="padding-left-8 pull-left" style="width: 200px; margin-right:5px;">
								   <select id="gzbg" class="form-control" onchange="aircraftfailure.reload()">
								   	<option value="">显示全部All</option>
									<option value="机组">机组</option>
									<option value="地面">地面</option>
								   </select> 
								</div>
							</div>
							<div class="pull-left padding-left-0 padding-right-0">
								<div class=" pull-left padding-left-0 padding-right-0" style="width: 210px;">
									<input placeholder="故障信息/处理措施/工作站点" class="form-control" id="keyword" type="text">
								</div>
			                    <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="aircraftfailure.reload()">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                   		<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" onclick="aircraftfailure.more()">
										<div class="pull-left text-center">
											<div class="font-size-12">更多</div>
											<div class="font-size-9">More</div>
										</div>
										<div class="pull-left padding-top-5">
											&nbsp;<i class="icon-caret-down font-size-15" id="icon"></i>
										</div>
									</button>
			                    </div> 
							</div>
						</div>
					</div>
				
			
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-4  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-9 col-sm-9 padding-left-9 padding-right-0">
							<select id="dprtcode" class="form-control" onchange="aircraftfailure.changeDprtcode()" >
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="aircraftfailure.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class="clearfix"></div>
				</div>
				<!-- 搜索框end -->
				<div class="clearfix"></div>
				<!--------------------  表格 start -------------------->
				<div id="aircraftfailure_table_main">
					<div id="aircraftfailure_table_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" c-height="55%">
						<table id="aircraftfailure_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 500px;">
							<thead>                              
								<tr>
									<th class="colwidth-3">
										<div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div>
									</th>
									<th class="colwidth-9 sorting" onclick="aircraftfailure.orderBy('fjzch','S2006')" name="column_fjzch">
										<div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div>
									</th>
									<th class="colwidth-7 sorting" onclick="aircraftfailure.orderBy('fxrq','S2006')" name="column_fxrq">
										<div class="font-size-12 line-height-18">日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									</th>
									<th class="colwidth-7 sorting" onclick="aircraftfailure.orderBy('gzbg','S200604')" name="column_gzbg">
										<div class="font-size-12 line-height-18">故障报告</div>
										<div class="font-size-9 line-height-18">PIREP.</div>
									</th>
									<th class="colwidth-20">
										<div class="important">
											<div class="font-size-12 line-height-18">故障信息</div>
											<div class="font-size-9 line-height-18">Fault Info</div>
										</div>
									</th>
									<th class="colwidth-20">
										<div class="important">
											<div class="font-size-12 line-height-18">处理措施</div>
											<div class="font-size-9 line-height-18">Action</div>
										</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18">工作者</div>
										<div class="font-size-9 line-height-18">Worker</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-18">检查者</div>
										<div class="font-size-9 line-height-18">Checker</div>
									</th>
									<th class="colwidth-10">
										<div class="important">
											<div class="font-size-12 line-height-18">工作站点</div>
											<div class="font-size-9 line-height-18">Station</div>
										</div>
									</th>
								</tr> 
							</thead>
							<tbody id="aircraftfailure_table_list">
							</tbody>
						</table>
					</div>
					
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="aircraftfailure_table_pagination">
					</div>
					<div class="clearfix"></div>
				</div>
				<!--------------------  表格 end -------------------->
				<div class="clearfix"></div>
			</div>
		</div>
	</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/aircraftfailure/aircraftfailure_main.js"></script>
</body>
</html>