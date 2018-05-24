<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
</script>
</head>
<body >
<input type="hidden" id="zzjgid" value="${user.jgdm}" />
<input type="hidden" id="fjzch1" value="${fjzch1}" />
	<div class="page-content" >
	<div class="panel panel-primary">
	<div class="panel-heading" id="NavigationBar"></div>

			<div class="panel-body padding-bottom-0">
				<div class="col-xs-12 pull-right padding-right-0">
					
			 <!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0 row-height" >
					<div class="  pull-left padding-left-0  ">
							<div class="pull-left  text-right padding-left-0 padding-right-0 ">
						<div class="font-size-12">状态</div>
							<div class="font-size-9">State</div></div>
						<div id="gdzt_sel" class=" pull-left padding-left-8 padding-right-0" style="width: 180px; margin-right:10px;">
							 	<select id="gdzt" class="form-control"  name="gdzt"   multiple="multiple" onchange="changeSelectedPlane()">
									<option value="1" selected="selected" >未确认</option>
									<option value="2">已确认</option>
									<option value="3">无需确认</option>
								</select>
						</div>
					</div>
				
					<div class="pull-left" >
						  <div class="pull-left  text-right padding-left-0 padding-right-0"><div
								class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
							</div>
						<div id="fjzch_search_sel" class="padding-left-8 pull-left row-height" style="width: 200px; margin-right:10px;">
								<select class='form-control' id='fjzch_search' name="fjzch_search" onchange="changeSelectedPlane()">
									
								</select>
						</div>
					</div>
					
					
					
					<div class=" pull-left padding-left-0 padding-right-0" style="width:250px;" >
						<input placeholder="任务单号/任务相关信息/备注/调查表编号" id="keyword_search" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                         <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!------------搜索框end------->
				
				</div>
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-top-10  display-none border-cccccc search-height" id="divSearch">
				
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					
		
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0  ">
						<div
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12">完成日期</div>
							<div class="font-size-9">Completion Date</div></div>
						<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " name="date-range-picker" id="flightDate_search" readonly />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  margin-bottom-10 padding-left-0 padding-right-0 ">
						<div class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">任务类型</div>
							<div class="font-size-9">Task Type</div></div>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="rwlx" class="form-control "  name="rwlx">
									<option value="">显示全部</option>
									<option value="1">定检执行任务</option>
									<option value="2">非例行工单任务</option>
									<option value="3">EO工单任务</option>
								</select>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  pull-left padding-left-0 margin-bottom-10 padding-right-0">
						<div class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">任务子类型</div>
							<div class="font-size-9">Task Sub Type</div></div>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="rwzlx" class="form-control "  name="rwzlx">
									<option value="">显示全部</option>
									<option value="1">时控件工单</option>
									<option value="2">附加工单</option>
									<option value="3">排故工单</option>
								</select>
						</div>
					</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  pull-left padding-left-0 margin-bottom-10 padding-right-0">
							<div class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></div>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " name="dprtcode" onchange="changeOrganization()">
								<c:choose>
											<c:when test="${accessDepartment!=null && fn:length(accessDepartment) > 0}">
											<c:forEach items="${accessDepartment}" var="type">
												<option value="${type.id}"
													<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}
												</option>
											</c:forEach>
											</c:when>
											<c:otherwise>
												<option value="">暂无组织机构 No Organization</option>
											</c:otherwise>
										</c:choose>
							</select>
						</div>
					</div>
					</div>
					
					<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0 ">
					
				
					
					<div class="col-lg-2 pull-right text-right padding-right-0 margin-bottom-10">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
					</div> 
				</div>

				<div class="clearfix"></div>
				<div  class="col-xs-12 text-center  padding-left-0 padding-right-0 padding-top-0 table-h"  style="overflow-x:scroll">
		
						<table id="gdgs" class="table table-thin table-bordered table-set" style="min-width:2400px">
						<thead>
							<tr>
							<th class="fixed-column colwidth-5" style="vertical-align: middle;"><div class="font-size-12 line-height-18">操作</div><div class="font-size-9 line-height-18">Operation</div></th>
							<th class="fixed-column colwidth-5" ><div class="font-size-12 line-height-18" >序号</div><div class="font-size-9 line-height-18" >No.</div></th>
							<th class="fixed-column sorting colwidth-15" onclick="orderBy('rwdh')" id="rwdh_order"><div class="important"><div class="font-size-12 line-height-18" >任务单号</div></div><div class="font-size-9 line-height-18" >Task No.</div></th>
							<th class="sorting colwidth-8" onclick="orderBy('fjzch')" id="fjzch_order"><div class="font-size-12 line-height-18" >飞机注册号</div><div class="font-size-9 line-height-18" >A/C REG</div></th>
							<th class="sorting colwidth-30" onclick="orderBy('rwxx')" id="rwxx_order"><div class="important"><div class="font-size-12 line-height-18" >任务相关信息</div></div><div class="font-size-9 line-height-18" >Task Description</div></th>
							<th class="colwidth-8"><div class="font-size-12 line-height-18" >任务类型</div><div class="font-size-9 line-height-18" >Task type</div></th>
							<th class="sorting colwidth-6" onclick="orderBy('fxrq')" id="fxrq_order"><div class="font-size-12 line-height-18" >完成日期</div><div class="font-size-9 line-height-18" >Completion Date</div></th>
							<th class="colwidth-6"><div class="font-size-12 line-height-18" >标准工时</div><div class="font-size-9 line-height-18" >Plan Man-Hours</div></th>
							<th class="colwidth-6"><div class="font-size-12 line-height-18" >实际工时</div><div class="font-size-9 line-height-18" >Actual Man-Hours</div></th>
							<th class="sorting colwidth-6" onclick="orderBy('pcl')" id="pcl_order"><div class="font-size-12 line-height-18" >偏差值(%)</div><div class="font-size-9 line-height-18" >Deviation Value(%)</div></th>
							<th class="sorting colwidth-6" onclick="orderBy('dcbbh')" id="dcbbh_order"><div class="important"><div class="font-size-12 line-height-18" >调查编号</div></div><div class="font-size-9 line-height-18" >Survey No.</div></th>
							<th class="colwidth-6"><div class="font-size-12 line-height-18" >统计人</div><div class="font-size-9 line-height-18" >Statistical Person</div></th>
							<th class="colwidth-6"><div class="font-size-12 line-height-18" >统计日期</div><div class="font-size-9 line-height-18" >Statistical Date</div></th>
							<th class="colwidth-30"><div class="important"><div class="font-size-12 line-height-18" >备注</div></div><div class="font-size-9 line-height-18" >Remark</div></th>
							<th class="colwidth-6" id="init_date_rq"><div class="font-size-12 line-height-18" >状态</div><div class="font-size-9 line-height-18" >State</div></th>
							<th class="colwidth-15" id="init_date_rq"><div class="font-size-12 line-height-18" >组织机构</div><div class="font-size-9 line-height-18" >Organization</div></th>
							</tr>
			          </thead>
						<tbody id="planelist">
						    <!------ plane列表展示 ------>
						</tbody>
					</table>
				</div>
				<div class=" col-xs-12  text-center page-height" id="pagination">
				
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
   <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<script type="text/javascript" src="${ctx }/static/js/thjw/productionmessage/workersimplex/workersimplex_main.js"></script>
</body>
</html>