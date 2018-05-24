<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
</script>
</head>
<body>
<input type="hidden" id="adjustHeight" value="50">
	<input type="hidden" id="zzjgid" value="${user.jgdm}" />
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body ">

				<div
					class="col-xs-12 padding-left-0 padding-right-0" id="test11">
	<div class=" padding-left-0 padding-right-0 row-height">
						<div class=" padding-left-0 row-height  ">
							<button type="button" onclick="outexcel();"
								class="btn btn-primary padding-top-1 padding-bottom-1 pull-left">
								<div class="font-size-12">导出</div>
								<div class="font-size-9">Export</div>
						</div>
					</div>
					

 <!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0" >
			
					<div class="pull-left" >
						  <div class="pull-left  text-right padding-left-0 padding-right-0"><div
								class="font-size-12">飞行日期</div>
							<div class="font-size-9">Flight Time</div>
							</div>
						<div id="fjzch_search_sel" class=" padding-left-8 pull-left" style="width: 200px; margin-right:10px;">
								<input type="text" class="form-control "  name="date-range-picker"
								id="flightDate_search" readonly />
						</div>
					</div>
				
				<div class="pull-left" >
						  <div class="pull-left  text-right padding-left-0 padding-right-0">
						  <div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
							</div>
						<div id="fjzch_search_sel" class=" padding-left-8 pull-left" style="width: 200px; margin-right:10px;">
								<select class='form-control' id='fjzch_search' name="fjzch_search" onchange="changeSelectedPlane()">
								</select>
						</div>
					</div>
				
	
				<div class="pull-left" >
						  <div class="pull-left  text-right padding-left-0 padding-right-0"><div
								class="font-size-12">状态</div>
							<div class="font-size-9">State</div>
							</div>
						<div id="fjzch_search_sel" class=" padding-left-8 pull-left" style="width: 200px; margin-right:10px;">
									<select id="zt" class="form-control " name="zt" onchange="changeSelectedPlane()">
								<option value="">显示全部 All</option>
								<option value="2">提交 Submit</option>
								<option value="12">修订 Revise</option>
								<option value="11">作废 Delete</option>
							</select>
						</div>
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


				<div class="col-xs-12 triangle  padding-top-10 margin-top-10  display-none border-cccccc search-height" id="divSearch">
				
					
					<div class="col-xs-12 col-lg-12 col-sm-12 padding-left-0 padding-right-0 ">
					
						<div class="col-xs-12 col-sm-6 col-lg-3  pull-left padding-left-0 padding-right-0 margin-bottom-10 ">
							<div class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></div>
						<div id="gdzt_sel" class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
				 				<select id="dprtcode" class="form-control " name="dprtcode"
								onchange="changeOrganization()">
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
				<div class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h" style="overflow-x: scroll">
					<table id="indexList" class="table table-thin table-bordered table-striped text-center tableRe" >
						<thead>
							<tr class="nav-tabs">
								<th colspan="6"><div class="font-size-12 line-height-18">基本信息</div>
									<div class="font-size-9 line-height-18">Basic Info</div></th>
								<th colspan="8"><div class="font-size-12 line-height-18">机身</div>
									<div class="font-size-9 line-height-18">Fuselage</div></th>
								<th colspan="4"><div class="font-size-12 line-height-18">左发</div>
									<div class="font-size-9 line-height-18">L/Engine</div></th>
								<th colspan="6"><div class="font-size-12 line-height-18">左发循环</div>
									<div class="font-size-9 line-height-18">L/E CYCS.</div></th>
								<th colspan="6"><div class="font-size-12 line-height-18">左发FEDEC</div>
									<div class="font-size-9 line-height-18">L/E FEDEC</div></th>
								<th colspan="4"><div class="font-size-12 line-height-18">右发</div>
									<div class="font-size-9 line-height-18">R/Engine</div></th>
								<th colspan="6"><div class="font-size-12 line-height-18">右发循环</div>
									<div class="font-size-9 line-height-18">R/E CYCS.</div></th>
								<th colspan="6"><div class="font-size-12 line-height-18">右发FEDEC</div>
									<div class="font-size-9 line-height-18">R/E FEDEC</div></th>
								<th colspan="3"><div class="font-size-12 line-height-18">特殊监控</div>
									<div class="font-size-9 line-height-18">Special Monitoring</div></th>
								<th colspan="1"><div class="font-size-12 line-height-18">作废原因</div>
									<div class="font-size-9 line-height-18">Reason</div></th>
							</tr>
							<tr>
								<th style="vertical-align: middle;min-width:60px;"><div
										class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div></th>
								<th style="min-width: 100px;" class="sorting "
									onclick="orderBy('fxrq')" id="fxrq_order"><div
										class="font-size-12 line-height-18">飞行日期</div>
									<div class="font-size-9 line-height-18">Flight date</div></th>
								<th  style="min-width: 150px;" class="sorting "
									onclick="orderBy('fxjldh')" id="fxjldh_order"><div
										class="font-size-12 line-height-18">飞行记录单单号</div>
									<div class="font-size-9 line-height-18">Flight Record Number</div></th>
								<th style="min-width: 100px;" class="sorting "
									onclick="orderBy('jlbym')" id="jlbym_order">
									<div class="font-size-12 line-height-18">记录本页码</div>
									<div class="font-size-9 line-height-18">Flight record</div>
								</th>	
								<th style="min-width: 100px;" class="sorting "
									onclick="orderBy('hc')" id="hc_order"><div
										class="font-size-12 line-height-18">航次</div>
									<div class="font-size-9 line-height-18">Flight Number</div></th>
								<th  style="min-width: 100px;" class="sorting "
									onclick="orderBy('zt')" id="zt_order"><div
										class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div></th>
						
								<th style="min-width: 100px;" class="sorting "
									onclick="orderBy('fxsj')" id="fxsj_order"><div
										class="font-size-12 line-height-18">本次飞行时间</div>
									<div class="font-size-9 line-height-18">Flight HRS.</div></th>
								<th style="min-width: 100px;"><div
										class="font-size-12 line-height-18">总飞行时间</div>
									<div class="font-size-9 line-height-18">Total HRS.</div></th>
								<th style="min-width: 100px;" class="sorting "
									onclick="orderBy('yl')" id="yl_order"><div
										class="font-size-12 line-height-18">燃油量</div>
									<div class="font-size-9 line-height-18">Fuel</div></th>
								<th  style="min-width: 100px;" id="qljxh"><div
										class="font-size-12 line-height-18">本次/总起落循环</div>
									<div class="font-size-9 line-height-18">A/C CYCS.</div></th>
								<th style="min-width: 250px;" id="ssdsj"><div
										class="font-size-12 line-height-18">本次/总搜索灯时间(序号)</div>
									<div class="font-size-9 line-height-18">Search Time</div></th>
								<th style="min-width: 250px;"><div
										class="font-size-12 line-height-18">本次/总外吊挂循环(序号)</div>
									<div class="font-size-9 line-height-18">E/S CYCS.</div></th>
								<th style="min-width: 250px;"><div
										class="font-size-12 line-height-18">本次/总绞车循环(序号)</div>
									<div class="font-size-9 line-height-18">Hoist CYCS.</div></th>
								<th style="min-width: 250px;"><div
										class="font-size-12 line-height-18">本次/总绞车时间(序号)</div>
									<div class="font-size-9 line-height-18">Hoist Time</div></th>
								<th style="min-width: 250px;" id="init_date_rq"><div
										class="font-size-12 line-height-18">1#左发总时间(序号)</div>
									<div class="font-size-9 line-height-18">L/Engine Time</div></th>
								<th style="min-width: 100px;" id="init_time_jsfx"><div
										class="font-size-12 line-height-18">滑耗</div>
									<div class="font-size-9 line-height-18">Oil</div></th>
								<th style="min-width: 100px;" id="init_time_ssd"><div
										class="font-size-12 line-height-18">温度余度</div>
									<div class="font-size-9 line-height-18">Temperature</div></th>
								<th style="min-width: 100px;" id="init_time_jc"><div
										class="font-size-12 line-height-18">功率余度</div>
									<div class="font-size-9 line-height-18">watts</div></th>
								<th style="min-width: 100px;" id="f1N1"><div
										class="font-size-12 line-height-18">N1</div>
									<div class="font-size-9 line-height-18">N1</div></th>
								<th style="min-width: 100px;" id="f1N2"><div
										class="font-size-12 line-height-18">N2</div>
									<div class="font-size-9 line-height-18">N2</div></th>
								<th style="min-width: 100px;" id="f1N3"><div
										class="font-size-12 line-height-18">N3</div>
									<div class="font-size-9 line-height-18">N3</div></th>
								<th style="min-width: 100px;" id="f1N4"><div
										class="font-size-12 line-height-18">N4</div>
									<div class="font-size-9 line-height-18">N4</div></th>
								<th style="min-width: 100px;" id="f1N5"><div
										class="font-size-12 line-height-18">N5</div>
									<div class="font-size-9 line-height-18">N5</div></th>
								<th style="min-width: 100px;" id="f1N6"><div
										class="font-size-12 line-height-18">N6</div>
									<div class="font-size-9 line-height-18">N6</div></th>
								<th style="min-width: 100px;" id="f1N1"><div
										class="font-size-12 line-height-18">N1</div>
									<div class="font-size-9 line-height-18">N1</div></th>
								<th  style="min-width: 100px;" id="f1N2"><div
										class="font-size-12 line-height-18">N2</div>
									<div class="font-size-9 line-height-18">N2</div></th>
								<th style="min-width: 100px;" id="f1N3"><div
										class="font-size-12 line-height-18">N3</div>
									<div class="font-size-9 line-height-18">N3</div></th>
								<th  style="min-width: 100px;" id="f1N4"><div
										class="font-size-12 line-height-18">N4</div>
									<div class="font-size-9 line-height-18">N4</div></th>
								<th style="min-width: 100px;"  id="f1N5"><div
										class="font-size-12 line-height-18">N5</div>
									<div class="font-size-9 line-height-18">N5</div></th>
								<th  style="min-width: 100px;" id="f1N6"><div
										class="font-size-12 line-height-18">N6</div>
									<div class="font-size-9 line-height-18">N6</div></th>
								<th style="min-width: 250px;" id="init_loop_r_n1"><div
										class="font-size-12 line-height-18">2#右发总时间(序号)</div>
									<div class="font-size-9 line-height-18">R/Engine Time</div></th>
								<th style="min-width: 100px;" id="init_loop_r_n1"><div
										class="font-size-12 line-height-18">滑耗</div>
									<div class="font-size-9 line-height-18">Oil</div></th>
								<th style="min-width: 100px;" id="init_loop_r_n1"><div
										class="font-size-12 line-height-18">温度余度</div>
									<div class="font-size-9 line-height-18">Temperature</div></th>
								<th style="min-width: 100px;"  id="init_loop_r_n1"><div
										class="font-size-12 line-height-18">功率余度</div>
									<div class="font-size-9 line-height-18">watts</div></th>
								<th style="min-width: 100px;" id="f2N1"><div
										class="font-size-12 line-height-18">N1</div>
									<div class="font-size-9 line-height-18">N1</div></th>
								<th style="min-width: 100px;" id="f2N2"><div
										class="font-size-12 line-height-18">N2</div>
									<div class="font-size-9 line-height-18">N2</div></th>
								<th style="min-width: 100px;" id="f2N3"><div
										class="font-size-12 line-height-18">N3</div>
									<div class="font-size-9 line-height-18">N3</div></th>
								<th style="min-width: 100px;" id="f2N4"><div
										class="font-size-12 line-height-18">N4</div>
									<div class="font-size-9 line-height-18">N4</div></th>
								<th style="min-width: 100px;" id="f2N5"><div
										class="font-size-12 line-height-18">N5</div>
									<div class="font-size-9 line-height-18">N5</div></th>
								<th style="min-width: 100px;" id="f2N6"><div
										class="font-size-12 line-height-18">N6</div>
									<div class="font-size-9 line-height-18">N6</div></th>
								<th style="min-width: 100px;" id="f1N1"><div
										class="font-size-12 line-height-18">N1</div>
									<div class="font-size-9 line-height-18">N1</div></th>
								<th style="min-width: 100px;" id="f1N2"><div
										class="font-size-12 line-height-18">N2</div>
									<div class="font-size-9 line-height-18">N2</div></th>
								<th style="min-width: 100px;" id="f1N3"><div
										class="font-size-12 line-height-18">N3</div>
									<div class="font-size-9 line-height-18">N3</div></th>
								<th style="min-width: 100px;" id="f1N4"><div
										class="font-size-12 line-height-18">N4</div>
									<div class="font-size-9 line-height-18">N4</div></th>
								<th style="min-width: 100px;" id="f1N5"><div
										class="font-size-12 line-height-18">N5</div>
									<div class="font-size-9 line-height-18">N5</div></th>
								<th  style="min-width: 100px;" id="f1N6"><div
										class="font-size-12 line-height-18">N6</div>
									<div class="font-size-9 line-height-18">N6</div></th>
								<th style="min-width: 100px;" id="ts1"><div
										class="font-size-12 line-height-18">TS1</div>
									<div class="font-size-9 line-height-18">TS1</div></th>
								<th style="min-width: 100px;" id="ts2"><div
										class="font-size-12 line-height-18">TS2</div>
									<div class="font-size-9 line-height-18">TS2</div></th>
								<th  style="min-width: 150px;" id="ms"><div
										class="font-size-12 line-height-18">特殊情况</div>
									<div class="font-size-9 line-height-18">Special Circumstances</div></th>
								<th style="min-width: 250px;" id="ms"><div
										class="font-size-12 line-height-18">作废原因</div>
									<div class="font-size-9 line-height-18">Reason</div></th>
							</tr>
						</thead>
						<tbody id="planelist">
							<!------ plane列表展示 ------>
						</tbody>
					</table>
				</div>
				<div class=" col-xs-12  text-center page-height padding-left-0 padding-right-0" id="pagination1">

				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>

	<script type="text/javascript"
		src="${ctx }/static/js/thjw/flightdata/flightrrcord/flightrRcord_main.js"></script>
</body>
</html>