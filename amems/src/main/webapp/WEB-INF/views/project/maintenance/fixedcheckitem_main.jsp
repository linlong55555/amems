<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script>
	var id = "${param.id}";
	var rwxfabh = "${param.wxfabh}";
	var pageParam = '${param.pageParam}';
	</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="" />
	<input type="hidden" id="wxfabh" value="${erayFns:escapeStr(wxfabh)}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			
			<div class="panel-body padding-bottom-0">
			
				<div class="col-xs-4 col-md-4 padding-left-0">
					<a href="javascript:void(0);" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="project:fixedcheckitem:main:01" onclick="add()">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
					
					<a href="javascript:order();" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="project:fixedcheckitem:main:07" style="float: left; margin-left: 10px;">
						<div class="font-size-12">调整排序</div>
						<div class="font-size-9">Order</div>
					</a>
					
					<button id="batchReview" type="button" onclick="batchApproveWin(false);"  style="margin-left:10px"
						class="btn btn-primary padding-top-1 padding-bottom-1 pull-left">
						<div class="font-size-12">批量审核</div>
						<div class="font-size-9">Review</div>
					</button>
					<button id="batchApprove" type="button" onclick="batchApproveWin(true);"  style="margin-left:10px"
						class="btn btn-primary padding-top-1 padding-bottom-1 pull-left">
						<div class="font-size-12">批量批准</div>
						<div class="font-size-9">Approve</div>
					</button>
					
				</div>
				
				<div class=" pull-right padding-left-0 padding-right-0">

					<div class="pull-left padding-left-5 padding-right-0" style="width: 250px;">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">维修方案</div>
							<div class="font-size-9">Maintenance</div>
						</span>
						<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="wxfabh_search" class="form-control" onchange="changeBhLoad()">
							</select>
						</div>
					</div>	
				
					<div class="pull-left padding-left-10 padding-right-1 margin-right-1" style="width: 140px;">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-5">
							<div class="font-size-12">版本</div>
							<div class="font-size-9">Revision</div>
						</span>
						<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="bb_search" class="form-control" onchange="searchRevision()"></select>
						</div>
					</div>	
				
					<!-- 搜索框start -->
					<div class="pull-right padding-left-10 padding-right-0 row-height">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 240px;">
							<input type="text" placeholder='定检编号/定检名称/备注/版本/制单人' id="keyword_search" class="form-control ">
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
				
				<div class="col-xs-12 triangle  padding-top-10 margin-top-10 display-none border-cccccc search-height" id="divSearch">
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">排序号</div>
							<div class="font-size-9">Order No.</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" id="pxh_search"  class="form-control"  onkeyup='clearNoNum(this)' />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">审批状态</div>
							<div class="font-size-9">State</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='spzt_search' name="spzt_search" >
								<option value="" >显示全部All</option>
								<c:forEach items="${approveStatusEnum}" var="item">
									<c:if test="${item.id != 4}">
								  		<option value="${item.id}" >${item.name}</option>
									</c:if>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">有效性</div>
							<div class="font-size-9">Effectivity</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='yxx_search' name="yxx_search" >
								<option value="" >显示全部All</option>
								<c:forEach items="${effectiveEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control " name="dprtcode_search" onchange="changeOrganization()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">实际生效日期</div>
							<div class="font-size-9">Actual date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " name="date-range-picker" id="sjSxrq_search" readonly />
						</div>
					</div>

					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">制单日期</div>
							<div class="font-size-9">Create Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="zdrq_search" readonly />
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

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="overflow: auto; height: auto;">
					<table id="fix_main_table" class="table table-thin table-bordered table-striped table-hover text-left table-set" style="min-width: 1830px;">
						<thead>
							<tr>
								<th class="viewCol fixed-column colwidth-5">
									<a href="#" onclick="performSelectAll()" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
									<a href="#" class="margin-left-5" onclick="performSelectClear()" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
								</th>
								<th class="viewCol fixed-column colwidth-10">
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('pxh')" id="pxh_order">
									<div class="font-size-12 line-height-18">排序号</div>
									<div class="font-size-9 line-height-18">Order No.</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('djbh')" id="djbh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">定检编号</div>
										<div class="font-size-9 line-height-18">Fixed No.</div>
									</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('zwms')" id="zwms_order">
									<div class="important">
										<div class="font-size-12 line-height-18">定检名称</div>
										<div class="font-size-9 line-height-18">Fixed Name</div>
									</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('bz')" id="bz_order">
									<div class="important">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('spzt')" id="spzt_order">
									<div class="font-size-12 line-height-18">审批状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('yxx')" id="yxx_order">
									<div class="font-size-12 line-height-18">有效性</div>
									<div class="font-size-9 line-height-18">Effectivity</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('bb')" id="bb_order">
									<div class="important">
										<div class="font-size-12 line-height-18">版本</div>
										<div class="font-size-9 line-height-18">Revision</div>
									</div>
								</th>
								<th class="colwidth-30">
									<div class="font-size-12 line-height-18">监控条件</div>
									<div class="font-size-9 line-height-18">Condition</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('sxsj')" id="sxsj_order">
									<div class="font-size-12 line-height-18">实际生效日期</div>
									<div class="font-size-9 line-height-18">Actual Date</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('username')" id="username_order">
									<div class="important">
										<div class="font-size-12 line-height-18">制单人</div>
										<div class="font-size-9 line-height-18">Creator</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('zdsj')" id="zdsj_order">
									<div class="font-size-12 line-height-18">制单时间</div>
									<div class="font-size-9 line-height-18">Create Time</div>
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

	<!-------调整排序对话框 Start-------->
	<div class="modal fade" id="alertModalOrder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalOrderBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">调整排序</div>
							<div class="font-size-9 ">Order</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
			            	<div class="col-lg-12 col-xs-12">
								<!-- start:table -->
								<div class="margin-top-10 ">
									<div class="overflow-auto" style="height:400px;overflow-y:auto;">
										<table class="table table-bordered table-striped table-hover text-center" style="">
											<thead>
										   		<tr>
													<th>
														<div class="font-size-12 notwrap">排序号</div>
														<div class="font-size-9 notwrap">Order No.</div>
													</th>
													<th>
														<div class="font-size-12 notwrap">定检编号</div>
														<div class="font-size-9 notwrap">Fixed Check No.</div>
													</th>
													<th>
														<div class="font-size-12 notwrap">定检名称</div>
														<div class="font-size-9 notwrap">Fixed Check Name</div>
													</th>
										 		 </tr>
											</thead>
											<tbody id="rotatable">
											
											</tbody>
										</table>
									</div>
								</div>
								<!-- end:table -->
					     		<div class="clearfix"></div>
						 	 </div>
						 	 <div class="text-center margin-top-10 padding-buttom-10">
				     			<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="saveOrder()" >
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
		                   		</button>
		                   		&nbsp;&nbsp;&nbsp;&nbsp;
                    				<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>&nbsp;&nbsp;&nbsp;&nbsp;
                   			 </div>
                   			 <br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-------调整排序对话框 End-------->
		
</div>
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 	
<script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/fixedcheckitem_main.js"></script>
<%@ include file="/WEB-INF/views/open_win/batchApprovel.jsp"%><!-------批量审批对话框 -------->
</body>
</html>