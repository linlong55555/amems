<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

<script type="text/javascript" src="${ctx}/static/js/thjw/sys/model_role_list.js"></script>

<div  class="tab-pane fade in active" style="padding-left:15px;padding-right:15px;" id="ModelRoleList">
	<div class="col-xs-12 padding-left-0 padding-right-0 ">
		<div class="col-xs-2 col-md-3 padding-left-0 row-height  ">
			<a href="${ctx}/sys/role/intoAddModelRole" data-toggle="modal" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='sys:role:main:06' >
				<div class="font-size-12">新增</div>
				<div class="font-size-9">Add</div>
			</a> 
		</div>
		<!--------搜索框start-------->
		<div class=" pull-right padding-left-0 padding-right-0" >
	
			<div class=" pull-right padding-left-10 padding-right-0">
				<div class=" pull-left padding-left-0 padding-right-0" style="width:250px;" >
					<input placeholder="角色代码/角色名称" id="keyword_search1" class="form-control " type="text">
				</div>
                <div class=" pull-right padding-left-5 padding-right-0 ">   
					<button id="roleMainModelSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision1();">
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
						<th style="width:130px;"><div class="font-size-12 line-height-18 " >操作</div>
							<div class="font-size-9 line-height-18">Operation</div>
						</th>
						<th class="sorting" onclick="orderBy1('role_Code')" id="role_Code_order1">
							<div class="important">
								<div class="font-size-12 line-height-18">机型角色代码</div>
								<div class="font-size-9 line-height-18">Role Code</div>
							</div>
						</th>
						<th class="sorting" onclick="orderBy1('role_Name')" id="role_Name_order1">
							<div class="important">
								<div class="font-size-12 line-height-18">机型角色名称</div>
								<div class="font-size-9 line-height-18">Role Name</div>
							</div>
						</th>
						<th class="sorting" onclick="orderBy1('role_Remark')" id="role_Remark_order1">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</th>
						<th class="sorting" onclick="orderBy1('DPRT_ID')" id="DPRT_ID_order1">
							<div class="font-size-12 line-height-18">组织机构</div>
							<div class="font-size-9 line-height-18">Organization</div>
						</th>
					</tr>
				</thead>
				<tbody id="list1">
				
				</tbody>
			</table>
		</div>
		<div class="col-xs-12 text-center page-height" id="pagination1">
		</div>
		
		<div class="clearfix"></div>
		
	</div>
</div>

			