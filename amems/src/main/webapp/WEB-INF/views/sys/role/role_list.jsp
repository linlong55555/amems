<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
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
<body class="page-header-fixed">
	<input type="hidden" id="userId" value="" />
	<input id="dprts" type="hidden"/> 
	<div class="page-content">
		<div class="panel panel-primary">
		 	<div class="panel-heading" id="NavigationBar"></div>
		<input type="hidden" id="adjustHeight" value="90">
		
			<div class="clearfix"></div>
			
			<div  class="panel-body" style="padding: 10px 10px 0 10px;">
				<!-----标签导航start---->
				<div  class="nav nav-tabs" style='padding-top:0px;padding-left:0px;' id='djTab'>
					<ul class="nav nav-tabs pull-left" role="tablist" id="tablist" style='margin-bottom:0px;margin-top:0px;border:0px;'>
						<li  class="active">
							<a href="#FunctionalRoleList" data-toggle="tab">功能角色 Functional Role</a>
						</li>
						<li >
							<a  href="#ModelRoleList" data-toggle="tab">机型角色 Model Role </a>
						</li>
						<li >
							<a  href="#WarehouseRoleList" data-toggle="tab">库房角色 Warehouse Role</a>
						</li>
					</ul>
					<div class=" pull-right padding-left-0 padding-right-0" style='margin-top:2px;' id='searchTabBtn'>
					    <span class=" pull-left padding-left-0 padding-right-0" style="margin-top:5px;padding-right:5px">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
	                    <div class=" pull-right padding-right-0 ">   
							<select id="dprtcode" class="form-control"  name="dprtcode" onchange="changeSelectedPlane()" style="width: 200px;" >
								<c:if test="${accessDepartments== null || fn:length(accessDepartments) > 1}">
									<option value="">显示全部 All</option>
								</c:if>
								<c:forEach items="${accessDepartments}" var="type">
									<option value="${type.id}" >${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
	                 		</select>
	                   </div> 
	                   
		    		 </div>
	    		 </div>
				<!-----标签内容start---->
				<div class="tab-content">
				<div class="tab-pane fade in active" id="FunctionalRoleList">
					<div class="col-xs-12 padding-left-0 padding-right-0 ">
						<div class="col-xs-2 col-md-3 padding-left-0 row-height  ">
							<a href="${ctx}/sys/role/intoAddRole" data-toggle="modal" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='sys:role:main:01'  >
								<div class="font-size-12">新增</div>
								<div class="font-size-9">Add</div>
							</a> 
						</div>
						
					<!--------搜索框start-------->
					<div class=" pull-right padding-left-0 padding-right-0" >
				
					<div class=" pull-right padding-left-10 padding-right-0">
						<div class=" pull-left padding-left-0 padding-right-0" style="width:250px;" >
							<input placeholder="角色代码/角色名称" id="keyword_search" class="form-control " type="text">
						</div>
	                    <div class=" pull-right padding-left-5 padding-right-0 ">   
							<button id="roleMainFunctionalSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
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
								<th class="sorting" onclick="orderBy('role_Code')" id="role_Code_order">
									<div class="important">
										<div class="font-size-12 line-height-18">功能角色代码</div>
											<div class="font-size-9 line-height-18">Role Code</div>
									</div>
								</th>
								<th class="sorting" onclick="orderBy('role_Name')" id="role_Name_order">
									<div class="important">
										<div class="font-size-12 line-height-18">功能角色名称</div>
										<div class="font-size-9 line-height-18">Role Name</div>
									</div>
								</th>
								<th class="sorting" onclick="orderBy('role_Remark')" id="role_Remark_order">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</th>
								<th class="sorting" onclick="orderBy('DPRT_ID')" id="DPRT_ID_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list">
						
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height" id="pagination">
				
				</div>
				
				<div class="clearfix"></div>
				
			</div>
		</div>
		<div class="tab-pane fade" id="ModelRoleList">
			<div class="row feature">
				 <%@ include
					file="/WEB-INF/views/sys/role/model_role_list.jsp"%> 
			</div>
		</div>
		<div class="tab-pane fade" id="WarehouseRoleList">
			<div class="row feature">
				 <%@ include
					file="/WEB-INF/views/sys/role/warehouse_role_list.jsp"%> 
			</div>
		</div>
	</div>
</div>
</div>
</div>
	<!-------角色分配组织机构对话框 Start-------->
	<div class="modal fade" id="alertModalDprt" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:500px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">角色组织机构授权</div>
							<div class="font-size-9 ">Assign Organization</div>
						</div>
						<input id="id"  type="hidden" /> 
						<div class="panel-body padding-top-0 padding-bottom-0">
			            	<div class="col-lg-12 col-xs-12">
							 <!-- 菜单树-->
							<div id="tab_trade" class="tab-pane active col-xs-3" style="padding:10px;">
								<div class="zTreeDemoBackground">
									<ul id="treeDemo" class="ztree" ></ul>
								</div>
							</div>
							<!-- 菜单树 -->
							
							<div class="clearfix"></div>
							
			                	<div class="text-right margin-bottom-10">
									<button type="button" onclick="save()" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/sys/role_list.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/select.js"></script>
</body>
</html>