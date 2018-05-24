<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>待办事宜</title>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">

	<div class="clearfix"></div>
	<div class="page-content">
		<input type="hidden" id="userid" value="${user.id}">
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<input type="hidden" id="adjustHeight" value="0">
			<!--导航结束  -->
			<div class="panel-body padding-bottom-0">
			<div class="searchContent  row-height">
			<div class="margin-top-0">
				<!-- 添加按钮  -->
				<div class=" col-lg-6 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
					<a href="#" onclick="coverplate.add()" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="basic:coverplateinformation:main:add">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a>
					<a href="#" onclick="showImportModal()"  class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left  checkPermission" permissioncode='basic:coverplateinformation:main:04'>
							<div class="font-size-12">导入</div>
							<div class="font-size-9">Import</div>
					</a>
					<a href="#" onclick="exportExcel()"  class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission" permissioncode='basic:coverplateinformation:main:export'>
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
					</a>
				</div>
				<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
					<span class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 ">机型</div>
						<div class="font-size-9 ">A/C Type</div>
					</span>
					<div  class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
						<select id="fjjx_select" class="form-control" onchange="changeFjjx();"></select>
					</div>
				</div>
				
				<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style="padding-left:15px;">
					<div class="col-xs-12 input-group input-group-search">
						<input type="text" placeholder="盖板编号/盖板名称" class="form-control" id="keyword_search">
	                    <div class="input-group-addon btn-searchnew">
	                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="search();" style="margin-right:0px !important;">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
	                  		</button>
	                    </div>
	                    <!-- 搜索框start -->
	                    <div class="input-group-addon btn-searchnew-more">
		                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight" id="btn" onclick="coverplate.moreBtn();">
							<div class="input-group">
							<div class="input-group-addon">
							<div class="font-size-12">更多</div>
							<div class="font-size-9 margin-top-5">More</div>
							</div>
							<div class="input-group-addon">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
							</div>
				   			</button>
	                	</div>
	                	<!-- 搜索框end -->
					</div>
				</div>				
			</div>
					<div class="col-xs-12 col-lg-12 col-sm-12 triangle padding-top-10 margin-bottom-10 display-none border-cccccc" id="divSearch" style="display: none;">
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">区域</div>
								<div class="font-size-9">Zone</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select class='form-control' id="zone" onchange="search();">
							    </select>
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9" >Organization</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="dprtcode" class="form-control "  name="dprtcode" onchange="changeList()">
									<c:forEach items="${accessDepartment}" var="type">
											<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
								<div class="font-size-12">重置</div>
								<div class="font-size-9">Reset</div>
							</button>
						</div>
					</div>
					<div class='clearfix'></div>
				</div>
				<div class="table_pagination">
						<!-- 表格 -->
						<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" style="height:151px !important;overflow-x: auto;">
							<table  class="table table-thin table-bordered table-striped table-hover table-set" id="coverplate_maintenance_table">
								<thead>
									<tr>
										<th class="fixed-column colwidth-5">
											<div class="font-size-12 line-height-18 ">操作</div>
											<div class="font-size-9 line-height-18">Operation</div>
										</th>
										
										<th class="colwidth-20 sorting" onclick="orderBy('fjjx')" id="fjjx_order">
											<div class="font-size-12 line-height-18">机型</div>
											<div class="font-size-9 line-height-18">A/C Type</div>
										</th>	
																		
																													
										<th  class="sorting  colwidth-15" onclick="orderBy('gbbh')" id="gbbh_order">
											<div class="important">
												<div class="font-size-12 line-height-18">盖板编号</div>
												<div class="font-size-9 line-height-18">Cover Plate No.</div>
											</div>
										</th>	
										<th  class="sorting  colwidth-10" onclick="orderBy('gbmc')" id="gbmc_order">
											<div class="important">
												<div class="font-size-12 line-height-18">盖板名称</div>
												<div class="font-size-9 line-height-18">Cover Plate Name</div>
											</div>
										</th>	
										
										<th  class="sorting  colwidth-10" onclick="orderBy('szqywz')" id="szqywz_order">
											<div class="font-size-12 line-height-18">所在区域位置</div>
											<div class="font-size-9 line-height-18">Zone Location</div>
										</th>
										
										<th  class="sorting  colwidth-8" onclick="orderBy('kggs')" id="kggs_order">
											<div class="font-size-12 line-height-18">开盖工时</div>
											<div class="font-size-9 line-height-18">Open Hours</div>
										</th>
										<th  class="sorting  colwidth-8" onclick="orderBy('hggs')" id="hggs_order">
											<div class="font-size-12 line-height-18">合盖工时</div>
											<div class="font-size-9 line-height-18">Close Hours</div>
										</th>
										
										<th  class="sorting  colwidth-15" onclick="orderBy('qy')" id="qy_order">
											<div class="font-size-12 line-height-18">区域</div>
											<div class="font-size-9 line-height-18">Zone</div>
										</th>
										<th  class="sorting  colwidth-15" onclick="orderBy('rlgbbs')" id="rlgbbs_order">
											<div class="font-size-12 line-height-18">燃料盖板标识</div>
											<div class="font-size-9 line-height-18">Identification</div>
										</th>
										<th class="colwidth-13 sorting" onclick="orderBy('whrxm')" id="whrxm_order">
											<div class="font-size-12 line-height-18">维护人</div>
											<div class="font-size-9 line-height-18">Maintainer</div>
										</th>
										<th class="colwidth-13 sorting" onclick="orderBy('whsj')" id="whsj_order">
											<div class="font-size-12 line-height-18">维护时间</div>
											<div class="font-size-9 line-height-18">Maintenance Time</div>
										</th>
										<th class="colwidth-10 sorting" onclick="orderBy('dprtname')" id="dprtname_order">
											<div class="font-size-12 line-height-18">组织机构</div>
											<div class="font-size-9 line-height-18">Organization</div>
										</th>
										
									</tr>
								</thead>
								<tbody id="coverplate_maintenance_list">
								</tbody>
						</table>
					</div>	
					
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
					</div>
					<div class='clearfix'></div>
				</div>
		</div>
	</div>
</div>	

<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<%@ include file="/WEB-INF/views/basic/coverplateinformation/coverplate_open.jsp" %> 
<script type="text/javascript" src="${ctx}/static/js/thjw/basic/coverplateinformation/coverplate_maintenance_main.js"></script><!--当前界面js  -->
<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
</body>
</html>









