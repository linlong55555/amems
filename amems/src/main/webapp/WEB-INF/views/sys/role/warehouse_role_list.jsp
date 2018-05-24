<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

<script type="text/javascript" src="${ctx}/static/js/thjw/sys/warehouse_role_list.js"></script>

<div  class="tab-pane fade in active" style="padding-left:15px;padding-right:15px;" id="WarehouseRoleList">
	<div class="col-xs-12 padding-left-0 padding-right-0 ">
		<div class="col-xs-2 col-md-3 padding-left-0 row-height  ">
			<a href="${ctx}/sys/role/intoAddWarehouseRole" data-toggle="modal" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='sys:role:main:09' >
				<div class="font-size-12">新增</div>
				<div class="font-size-9">Add</div>
			</a> 
		</div>
		<!--------搜索框start-------->
		<div class=" pull-right padding-left-0 padding-right-0" >
	
			<div class=" pull-right padding-left-10 padding-right-0">
				<div class=" pull-left padding-left-0 padding-right-0" style="width:250px;" >
					<input placeholder="角色代码/角色名称" id="keyword_search2" class="form-control " type="text">
				</div>
                <div class=" pull-right padding-left-5 padding-right-0 ">   
					<button id="roleMainWarehouseSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision2();">
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
               		</button>
                  </div> 
                 </div>
			</div>
			<!------------搜索框end------->
				
			<div class="clearfix"></div>

			<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="overflow-x: scroll">
			<table class="table table-thin table-bordered table-striped table-hover table-set" >
				<thead>
					<tr>
						<th style="width:130px;">
							<div class="font-size-12 line-height-18 " >操作</div>
							<div class="font-size-9 line-height-18">Operation</div>
						</th>
						<th class="sorting" onclick="orderBy2('role_Code')" id="role_Code_order2">
							<div class="important">
								<div class="font-size-12 line-height-18">库房角色代码</div>
								<div class="font-size-9 line-height-18">Warehouse Role Code</div>
							</div>
						</th>
						<th class="sorting" onclick="orderBy2('role_Name')" id="role_Name_order2">
							<div class="important">
								<div class="font-size-12 line-height-18">库房角色名称</div>
								<div class="font-size-9 line-height-18">Warehouse Role Name</div>
							</div>
						</th>
						<th class="sorting" onclick="orderBy2('role_Remark')" id="role_Remark_order2">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</th>
						<th class="sorting" onclick="orderBy2('DPRT_ID')" id="DPRT_ID_order2">
							<div class="font-size-12 line-height-18">组织机构</div>
							<div class="font-size-9 line-height-18">Organization</div>
						</th>
					</tr>
					</thead>
				<tbody id="list2">
					
				</tbody>
			</table>
		</div>
		<div class="col-xs-12 text-center page-height" id="pagination2">
		</div>
			
		<div class="clearfix"></div>
			
	</div>
</div>

	<!-------角色分配仓库对话框 Start-------->
	<div class="modal fade" id="alertModalStore" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 50%;height:50%;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">角色仓库授权</div>
							<div class="font-size-9 ">Role Store Auth</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
			            	<div class="col-lg-12 col-xs-12">
			            	
			            		<div class="text-right margin-top-10">
				         			<div style="padding-left:0!important;" class="pull-right">   
										<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchStore()">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div> 
									<div style="padding-right:9px!important;" class="col-xs-6 pull-right">
										<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">组织机构</div>
											<div class="font-size-9">Organization</div>
										</span>
										<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
											<select id="dprtcode_store_search" name="dprtcode_store_search" class="form-control " onchange="searchStore()">
												<c:choose>
													<c:when test="${empty accessDepartments}">
														<option value="" >请选择</option>
														<c:forEach items="${accessDepartments}" var="type">
															<option value="${type.id}" >${erayFns:escapeStr(type.dprtname)}</option>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:forEach items="${accessDepartments}" var="type">
															<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</select>
										</div>
									</div>
								</div>
								
								<div class="clearfix"></div>
							
								<!-- start:table -->
								<div class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 margin-top-10 " >
									<table id="open_win_store_table" class="table table-bordered table-striped table-hover text-center table-set" style="min-width:350px">
										<thead>
									   		<tr>
									   			<th class="colwidth-5" style='text-align:center;vertical-align:middle;'>
									   				<a href="#" onclick="SelectUtil.performSelectAll('storeList')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
													<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('storeList')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
									   			</th>
												<th class="colwidth-15">
													<div class="font-size-12 notwrap">仓库号</div>
													<div class="font-size-9 notwrap">Store Code</div>
												</th>
												<th class="colwidth-20">
													<div class="font-size-12 notwrap">仓库名称</div>
													<div class="font-size-9 notwrap">Store Name</div>
												</th>
												<th class="colwidth-15">
													<div class="font-size-12 notwrap">组织机构</div>
													<div class="font-size-9 notwrap">Organization</div>
												</th>
									 		 </tr>
										</thead>
										<tbody id="storeList">
										
										</tbody>
									</table>
								</div>
								<!-- end:table -->
			                	<div class="text-right margin-bottom-10">
									<button type="button" onclick="saveRoleToStore('storeList')"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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
