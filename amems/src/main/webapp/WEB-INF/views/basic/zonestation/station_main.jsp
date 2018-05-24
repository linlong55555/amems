<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>区域管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	
	<input type="hidden" id="adjustHeight" value="0" />
	<div class="clearfix"></div>
	<div class="page-content">
	<input type="hidden" id="userid" value="${user.id}">
	<input type="hidden" id="dprtId" value="${user.jgdm}">
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<!--导航结束  -->
			<div class="panel-body padding-bottom-0" >
				 <div class='searchContent row-height' >
					 <div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0 form-group checkPermission">
						<a href="javascript:add();" permissioncode='basic:station:main:add'  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission">
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a> 
						<a href="javascript:showImportModal();"  permissioncode='basic:station:main:export' type="button" class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission">
							<div class="font-size-12">导入</div>
							<div class="font-size-9">Export</div>
						</a>
						<a href="javascript:exportExcel();" permissioncode='basic:station:main:import' class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" style="float: left; margin-left: 10px;">
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Import</div>
						</a>
					</div>
					 
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">机型</div>
							<div class="font-size-9 ">A/C Type</div>
						</span>
						<div  class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<select id="fjjx" class="form-control" onchange="search();"></select>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group ">
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
						   	<select id="dprtcode" class="form-control "  name="dprtcode" onchange="changeList()">
								<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>							
							
							
						</div>
					</div>		
							
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style="padding-left:15px;">
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder="飞机站位/描述" class="form-control" id="keyword_search">
		                    <div class="input-group-addon btn-searchnew">
		                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="search();" style="margin-right:0px !important;">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
						</div>
					</div>					
					 <div class="clearfix"></div>
				</div>
				<!-- 搜索框end -->
				
				
			<div class="clearfix"></div>
				
			<div class='table_pagination' id="searchTable">
				<!-- 表格 -->
					<div class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height" style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-striped table-hover table-set">
							<thead>
								<tr>
									<th class="fixed-column colwidth-5" >
										<div class="font-size-12 line-height-18" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('jx')" id="jx_order">
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</th>									
									<th class="colwidth-10 sorting" onclick="orderBy('sz')" id="sz_order">
										<div class="important">
											<div class="font-size-12 line-height-18">飞机站位</div>
											<div class="font-size-9 line-height-18">Station</div>
										</div>
									</th>
									
									<th class="colwidth-15 sorting" onclick="orderBy('ms')" id="ms_order">
										<div class="important">
											<div class="font-size-12 line-height-18">描述</div>
											<div class="font-size-9 line-height-18">Description</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('whrid')" id="whrid_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('whsj')" id="whsj_order">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
									
									
								</tr>
							</thead>
							<tbody id="zoneStation_list"></tbody>
					</table>
				</div>	
				
				<!-- <div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
				</div> -->
				<div class='clearfix'></div>
			</div>	
			<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0"  id="pagination"></div>
		</div>
	</div>
</div>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<%@ include file="/WEB-INF/views/basic/zonestation/station_open.jsp" %> 
    <%@ include file="/WEB-INF/views/open_win/import.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/thjw/basic/zonestation/station_main.js"></script>
</body>
</html>
