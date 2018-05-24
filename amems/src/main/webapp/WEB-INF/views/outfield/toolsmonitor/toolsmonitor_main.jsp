<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工具/设备监控</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				<div class="col-xs-8 col-lg-8 padding-left-0 padding-right-0  pull-right">
				
					<!-- 搜索框start -->
					<div class=" pull-right padding-left-0 padding-right-0 row-height">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input type="text" placeholder='设备工具编号/部件号/设备工具名称/监控部件编号/监控部件名称' id="keyword_search" class="form-control ">
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
					<!-- 搜索框end -->
				</div>
				
				<div class="col-xs-12 col-sm-12 col-lg-12 triangle  padding-top-10  margin-top-10 display-none search-height border-cccccc" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">类型</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
							<select class='form-control' id='hclx_search'>
								<option value="" selected="selected" >显示全部All</option>
								<c:forEach items="${materialTypeEnum}" var="item">
									<c:if test="${item.id == 2 || item.id == 3}">
										<option value="${item.id}" >${item.name}</option>
									</c:if>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">下次检查日期</div>
							<div class="font-size-9">Next Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="jyXcrq_search" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" name="dprtcode_search" class="form-control" onchange="initMonitorsettings()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				
				<div class="clearfix"></div>

				<div class="col-lg-12 table-h col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x: scroll;">
					<table id="tool_main_table" class="table table-thin table-bordered table-set" style="min-width: 2040px;">
						<thead>
							<tr>
								<th class="fixed-column colwidth-5" >	
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-3" >
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('MBJXLH')" id="MBJXLH_order">
									<div class="important">
										<div class="font-size-12 line-height-18">设备工具编号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('BJH')" id="BJH_order">
									<div class="important">
										<div class="font-size-12 line-height-18">部件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('HCYWMS')" id="HCYWMS_order">
									<div class="important">
										<div class="font-size-12 line-height-18">设备工具名称</div>
										<div class="font-size-9 line-height-18">Name</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('HCLX')" id="HCLX_order">
									<div class="font-size-12 line-height-18">类型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('BJXLH')" id="BJXLH_order" >
									<div class="important">
										<div class="font-size-12 line-height-18">监控部件编号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('YWMS')" id="YWMS_order" >
									<div class="important">
										<div class="font-size-12 line-height-18">监控部件名称</div>
										<div class="font-size-9 line-height-18">Name</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('GG')" id="GG_order">
									<div class="font-size-12 line-height-18">规格</div>
									<div class="font-size-9 line-height-18">Specifications</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('XH')" id="XH_order">
									<div class="font-size-12 line-height-18">型号</div>
									<div class="font-size-9 line-height-18">Model</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('JY_ZQ')" id="JY_ZQ_order">
									<div class="font-size-12 line-height-18">周期(天)</div>
									<div class="font-size-9 line-height-18">Cycle</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('JY_SCRQ')" id="JY_SCRQ_order">
									<div class="font-size-12 line-height-18">最近检查日期</div>
									<div class="font-size-9 line-height-18">Last Date</div>
								</th>
								<th  class="colwidth-9 sorting" onclick="orderBy('JY_XCRQ')" id="JY_XCRQ_order">
									<div class="font-size-12 line-height-18">下次检查日期</div>
									<div class="font-size-9 line-height-18">Next Date</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('SYTS')" id="SYTS_order">
									<div class="font-size-12 line-height-18">剩余天数</div>
									<div class="font-size-9 line-height-18">Remain(Day)</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('DPRTCODE')" id="DPRTCODE_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<script type="text/javascript" src="${ctx}/static/js/thjw/outfield/toolsmonitor/toolsmonitor_main.js"></script>
</body>
</html>