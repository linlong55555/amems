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
<body class="page-header-fixed">
	<input type="hidden" id="xgdjid" />
	<input type="hidden" id="ckbmid" />
	<input type="hidden" id="zzjgid" value="${user.jgdm}" />
		<input type="hidden" id="adjustHeight" value="90">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">

		<div class="panel panel-primary">
<div class="panel-heading" id="NavigationBar"></div>

			<div class="clearfix"></div>
			<div class="panel-body padding-bottom-0" style="padding-bottom:0px;padding-top:10px;padding-left:10px;padding-right:10px">
				<!-----标签导航start---->
				<ul class="nav nav-tabs" role="tablist" id="tablist">
					<li role="presentation" class="active"><a href="#outstock"  >领用出库Use OutStock</a></li>
					<li role="presentation"><a href="#senda" >送修出库Repair OutStock</a></li>
					<li role="presentation"><a href="#inventory" id="inventory_li">库存出库OutStock</a></li>
					<li role="presentation"><a href="#history" >出库历史OutStock history</a></li>
				</ul>
				<!-----标签内容start---->
				<div class="tab-content margin-bottom-10">
						<div class="tab-pane fade in active"  id="outstock">
	 				
					<div class="col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
					
		
			
				<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0" >
		
					<div class=" pull-left padding-left-0 padding-right-0" style="width:250px;" >
						<input placeholder="领用申请单号" id="keyword_search_li" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button id="UseOutStock" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
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
			

			<div	class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-0 margin-top-10 display-none border-cccccc" id="divSearch">

				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
						<div
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12">申请日期</div>
							<div class="font-size-9">Application Date</div>
						</div>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " name="date-range-picker"
								id="flightDate_search" readonly />
						</div>
					</div>

					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
						<div
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12">申请人</div>
							<div class="font-size-9">Applicant</div>
						</div>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class="form-control " id="sqrname" name="sqrname" />
						</div>
					</div>

					<div
						class="col-lg-3 col-sm-6 col-xs-12 margin-bottom-10 padding-left-0 padding-right-0">
						<div
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">仓库</div>
							<div class="font-size-9">Store</div>
						</div>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='ckh' name="ckh">
						
							</select>
						</div>
					</div>


					<div
						class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10  ">
						<div
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</div>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " name="dprtcode"
								onchange="changeSelectedPlane()">
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
					<div class="col-lg-2 pull-right text-right padding-right-0"
						style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>

				</div>

			</div>
		</div>
		<div class="col-xs-12 text-center margin-bottom-5 padding-left-0 padding-right-0 padding-top-0 "  style="overflow-x:auto">
		<table class="table table-thin table-bordered table-set" style="min-width:1000px;overflow: auto;">
			<thead>
				<tr>
					<th class="colwidth-5" style="vertical-align: middle;"><div class="font-size-12 line-height-18">序号</div>
						<div class="font-size-9 line-height-18">No.</div></th>
					<th class="sorting colwidth-15"
						onclick="orderBy('lysqdh')" id="lysqdh_order"><div class="important"><div
							class="font-size-12 line-height-18">领用申请单号</div></div>
						<div class="font-size-9 line-height-18">Application No.</div></th>
					<th  class="sorting colwidth-15" onclick="orderBy('sqrid')" id="sqrid_order"><div
							class="font-size-12 line-height-18">申请人</div>
						<div class="font-size-9 line-height-18">Applicant</div></th>
					<th  class="sorting colwidth-15" onclick="orderBy('sqrq')" id="sqrq_order"><div
							class="font-size-12 line-height-18">申请日期</div>
						<div class="font-size-9 line-height-18">Application Date</div></th>
					<th  class="sorting colwidth-15" onclick="orderBy('fjzch')" id="fjzch_order"><div
							class="font-size-12 line-height-18">飞机注册号</div>
						<div class="font-size-9 line-height-18">A/C REG</div></th>
					<th class="colwidth-30"><div class="font-size-12 line-height-18">领用原因</div>
						<div class="font-size-9 line-height-18">Use Cause</div></th>
					<th class="colwidth-15"><div class="font-size-12 line-height-18">组织机构</div>
						<div class="font-size-9 line-height-18">Organization</div></th>
				</tr>
			</thead>
			<tbody id="list">
			</tbody>
		</table>
		</div>
		<div class=" col-xs-12  text-center " style="margin-top: 0px; margin-bottom: 0px;" id="pagination">
		</div>
		<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class=" pull-left margin-right-10" >
							<div class="font-size-16 line-height-18">领用信息</div>
							<div class="font-size-9 ">Use Info</div>
						</div>	
						
			<div
				class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">领用申请单号</div>
									<div class="font-size-9">Application No.</div>
				</span>
				<div
					class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<span id="lysqdh"></span>
				</div>
			</div>

			<div
				class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">申请人</div>
									<div class="font-size-9">Applicant</div>
				</span>
				<div
					class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<span id="sqr"></span>
				</div>
			</div>

			<div
				class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">申请日期</div>
							<div class="font-size-9">Application Date</div>
				</span>
				<div
					class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<span id="sqrq"></span>
				</div>
			</div>
						
					 	<div class="pull-left ">
							<button id="btnGoAdd" style="display:none" class="btn btn-primary " onclick="javascript:window.history.go(-1)">
								<i class="icon-plus cursor-pointer" ></i>
							</button>
			          	</div>
					</div>	
		<div
			class="col-lg-12 col-sm-12 col-xs-12 line4 widget-body clearfix padding-top-10 margin-bottom-10">
		

			<div class="clearfix"></div>

			<form id="form">

				<div
					class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label
						class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">
							出库人
						</div>
						<div class="font-size-9 line-height-18">Operator</div>
					</label>
					
					<div class=" col-xs-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
						<div class='input-group'>
							<input type="text" class="form-control" value="${erayFns:escapeStr(user.username)} ${erayFns:escapeStr(user.realname)}" name="username" id="username" readonly />
							<input class="form-control " type="hidden" value="${user.id}" type="text" id="userid" name="userid" readonly="readonly" />
							<span class='input-group-btn'>
							  <button type="button" onclick='selectUser()' class='btn btn-primary'><i class='icon-search'></i>
							</button>
							</span>
					    </div>
					</div>
				
				</div>


				<div
					class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
					<label
						class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">
							出库日期
						</div>
						<div class="font-size-9 line-height-18">Outstock Date</div>
					</label>
					<div
						class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group">
						<input class="form-control date-picker" id="ckrq" name="ckrq"
							data-date-format="yyyy-mm-dd" type="text" />
					</div>
				</div>

				<div
					class=" col-lg-6 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
					<label
						class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">备注</div>
						<div class="font-size-9 line-height-18">Remark</div>
					</label>
					<div
						class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group">
						<input class="form-control " type="text" id="outstockbz" name="outstockbz"
							placeholder='长度最大为300' maxlength="300" />
					</div>
				</div>
			</form>
		</div>
		<div class="col-xs-12 text-center margin-bottom-5 padding-left-0 padding-right-0 padding-top-0 "  style="overflow-x:scroll">
		<table class="table table-thin table-bordered table-set" style="min-width:1300px">
			<thead>
				<tr>
					<th class="colwidth-5" style="vertical-align: middle;"><div class="font-size-12 line-height-18">操作</div>
						<div class="font-size-9 line-height-18">Operation</div></th>
					<th class="colwidth-5"><div class="font-size-12 line-height-18">序号</div>
						<div class="font-size-9 line-height-18">No.</div></th>
					<th class="colwidth-10"><div class="font-size-12 line-height-18">件号</div>
						<div class="font-size-9 line-height-18">P/N</div></th>
					<th class="colwidth-10"><div class="font-size-12 line-height-18">GRN</div>
						<div class="font-size-9 line-height-18">GRN</div></th>
					<th class="colwidth-20"><div class="font-size-12 line-height-18">英文名称</div>
						<div class="font-size-9 line-height-18">F.Name</div></th>
					<th class="colwidth-15"><div class="font-size-12 line-height-18">中文名称</div>
						<div class="font-size-9 line-height-18">CH.Name</div></th>
					<th class="colwidth-10"><div class="font-size-12 line-height-18">厂家件号</div>
						<div class="font-size-9 line-height-18">MP/N</div></th>
					<th class="colwidth-10"><div class="font-size-12 line-height-18">航材类型</div>
						<div class="font-size-9 line-height-18">Airmaterial type</div></th>
					<th class="colwidth-15"><div class="font-size-12 line-height-18">序列号</div>
						<div class="font-size-9 line-height-18">S/N</div></th>
					<th class="colwidth-15"><div class="font-size-12 line-height-18">批次号</div>
						<div class="font-size-9 line-height-18">P/N</div></th>
					<th class="colwidth-7"><div class="font-size-12 line-height-18">适航证号</div>
						<div class="font-size-9 line-height-18">Certificate</div></th>
					<th class="colwidth-10"><div class="font-size-12 line-height-18">仓库</div>
						<div class="font-size-9 line-height-18">Storage </div></th>
					<th class="colwidth-10"><div class="font-size-12 line-height-18">库位</div>
						<div class="font-size-9 line-height-18">Storage Location</div></th>
					<th class="colwidth-10"><div class="font-size-12 line-height-18">申请数</div>
						<div class="font-size-9 line-height-18">Application num</div></th>
					<th class="colwidth-10"><div class="font-size-12 line-height-18">库存数</div>
						<div class="font-size-9 line-height-18">Stock Num</div></th>
					<th class="colwidth-10"><div class="font-size-12 line-height-18">出库数</div>
						<div class="font-size-9 line-height-18">Outstock Num</div></th>
				</tr>
			</thead>
			<tbody id="list1">
			</tbody>
		</table>
</div>
		<div class="text-right margin-bottom-0">
			<button onclick="putoutstorage()"
				class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10 checkPermission" permissioncode='aerialmaterial:outstock:main:01'>
				<div class="font-size-12">出库</div>
				<div class="font-size-9">Outstock</div>
			</button>
		</div>
	</div>
	
	<div class="tab-pane fade" id="senda">
		<div class="row feature">
			<%@ include file="/WEB-INF/views/material/outstock/materialrepair_main.jsp"%>
		</div>
	</div>
	
	<div class="tab-pane fade" id="inventory">
		<div class="row feature">
		<%@ include file="/WEB-INF/views/material/outstock/warehouse_main.jsp"%>
		</div>
	</div>
	
	<div class="tab-pane fade" id="history">
		<div class="row feature">
		<%@ include file="/WEB-INF/views/material/outstock/outboundhistory_main.jsp"%>
		</div>
	</div>
	

	
	

	<!-------航材对话框 Start-------->
	<div class="modal fade" id="alertModalMaterialWinAdd" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 60%;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">库存选择</div>
							<div class="font-size-9 ">Stock Choice </div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
							<div class="col-lg-12 col-xs-12">

								<!-- start:table -->
								<div class="margin-top-10 ">
									<div class="col-xs-12 text-center margin-bottom-5 padding-left-0 padding-right-0 padding-top-0 "  style="overflow-x:scroll">
										<table class="table table-bordered table-striped table-hover table-set" style="min-width:1200px">
											<thead>
												<tr>
													<th class="colwidth-5" style="vertical-align: middle;">
														<div class="font-size-12 ">序号</div>
														<div class="font-size-9 ">No.</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 ">件号</div>
														<div class="font-size-9 ">P/N</div>
													</th class="colwidth-15">
														<th>
														<div class="font-size-12 ">英文名称</div>
														<div class="font-size-9 ">F.Name</div>
													</th>
													<th class="colwidth-15">
														<div class="font-size-12 ">中文名称</div>
														<div class="font-size-9 ">CH.Name</div>
													</th>
												
													<th class="colwidth-10">
														<div class="font-size-12 ">厂家件号</div>
														<div class="font-size-9 ">MP/N</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 ">航材类别</div>
														<div class="font-size-9 ">Aircraft Type</div>
													</th>
													<th class="colwidth-15">
														<div class="font-size-12 ">序列号/批次号</div>
														<div class="font-size-9 ">S/N</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 ">适航证号</div>
														<div class="font-size-9 ">Certificate</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 ">仓库</div>
														<div class="font-size-9 ">Storage </div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 ">库位</div>
														<div class="font-size-9 ">Storage Location</div>
													</th>
													<th class="colwidth-6">
														<div class="font-size-12 ">库存数</div>
														<div class="font-size-9 ">Stock Num</div>
													</th>
												</tr>
											</thead>
											<tbody id="list2">

											</tbody>
										</table>
									</div>
								</div>
								<!-- end:table -->
								<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	
	<!-------alert对话框 Start-------->
<div class="modal fade" id="alertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalBody"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
					<div class="font-size-12">关闭</div>
					<div class="font-size-9">Close</div>
				</button>
			</div>
		</div>
	</div>
</div>
	
	<!-------航材对话框 End-------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/select.js"></script>
	<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
	<script type="text/javascript"
		src="${ctx}/static/js/thjw/material/outstock/outstock_main.js"></script>
</body>
</html>