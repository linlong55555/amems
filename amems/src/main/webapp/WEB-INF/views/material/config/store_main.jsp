<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>仓库主数据</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="" />
	<input type="hidden" id="isStoreData" value="${isStoreData}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<div class='row-height searchContent'>
			
			<%
			    if("true".equals(request.getAttribute("isStoreData").toString())){//如果是仓库数据管理%>
			    <div class="col-xs-2 col-md-3 padding-left-0 form-group">
					<a href="#" onclick="add()" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="material:store2:main:01">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a>
					<a href="#" onclick="exportExcel()"  class="btn btn-primary padding-top-1 margin-left-6 padding-bottom-1 pull-left checkPermission" permissioncode="material:store2:main:05">
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
					</a> 
				</div>
			    	
			   <% }else{//如果是仓库主数据%>			    	
			     <div class="col-xs-2 col-md-3 padding-left-0 form-group">
					<a href="#" onclick="add()" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="material:store:main:01">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a>
					<a href="#" onclick="exportExcel()"  class="btn btn-primary padding-top-1 margin-left-6 padding-bottom-1 pull-left checkPermission" permissioncode="material:store:main:05">
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
					</a> 
				</div>
			    <%}			
			%>
			
			
			
				
				<div class=" pull-right padding-left-0 padding-right-0 form-group">	
				
					<div class=" pull-left padding-left-5 padding-right-0" style="width: 350px;">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">仓库类别</div>
							<div class="font-size-9">Warehouse Type</div>
						</span>
						<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="cklb_search" class="form-control"
									onchange="changeType()">
									<option value="" selected="selected">显示全部All</option>
									<%
										if ("true".equals(request.getAttribute("isStoreData").toString())) {//如果是仓库数据管理
									%>
									<c:forEach items="${storeType2Enum}" var="item">
										<option value="${item.id}">${item.name}</option>
									</c:forEach>

									<%
										} else {//如果是仓库主数据
									%>
									<c:forEach items="${storeTypeEnum}" var="item">
										<option value="${item.id}">${item.name}</option>
									</c:forEach>
									<%
										}
									%>
								</select>
							</div>
					</div>	
					
					<!-- 搜索框start -->
					<div class=" pull-right padding-left-10 padding-right-0">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input type="text" placeholder='仓库编号/仓库名称/仓库地址/库管员/备注/维护人' id="keyword_search" class="form-control ">
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
				
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-bottom-10 display-none border-cccccc" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">维护日期</div>
							<div class="font-size-9">Operate Time</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="whrq_search" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" name="dprtcode_search" class="form-control ">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
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
				</div>

				<div class="col-lg-12 table-height col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;overflow: auto; height: auto;">
					<table id="store_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1610px;">
						<thead>
							<tr>
								<th class="fixed-column colwidth-7" >
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('ckh')" id="ckh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">仓库编号</div>
										<div class="font-size-9 line-height-18">Warehouse No.</div>
									</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('ckmc')" id="ckmc_order">
									<div class="important">
										<div class="font-size-12 line-height-18">仓库名称</div>
										<div class="font-size-9 line-height-18">Warehouse Name</div>
									</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('ckdz')" id="ckdz_order">
									<div class="important">
										<div class="font-size-12 line-height-18">仓库地址</div>
										<div class="font-size-9 line-height-18">Warehouse Address</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('cklb')" id="cklb_order">
									<div class="font-size-12 line-height-18">仓库类别</div>
									<div class="font-size-9 line-height-18">Warehouse Type</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('kgyid')" id="kgyid_order">
									<div class="important">
										<div class="font-size-12 line-height-18">库管员</div>
										<div class="font-size-9 line-height-18">Warehouse Manager</div>
									</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('bz')" id="bz_order">
									<div class="important">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('cjrid')" id="cjrid_order">
									<div class="important">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('cjsj')" id="cjsj_order">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
				<div class="clearfix"></div>
			</div>
		</div>
</div>
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<%@ include file="/WEB-INF/views/material/config/store_open.jsp" %> 
<%@ include file="/WEB-INF/views/open_win/user.jsp"%><!-------用户对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/material/config/store_main.js"></script>
</body>
</html>