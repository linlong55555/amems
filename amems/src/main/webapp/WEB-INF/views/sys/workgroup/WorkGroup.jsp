<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工作组</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
.bootstrap-tagsinput {
  width: 100% !important;
}
</style>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
	<input type="hidden" id="whbmid" value="${user.bmdm}" />
	<input type="hidden" id="whrid" value="${sessionScope.user.id}" />
	<input type="hidden" id="zzjg" value="${user.jgdm}" />
	<div class="page-content">
		<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>		
			<!-- <input type="hidden" id="adjustHeight" value="10"> -->
			<div class="panel-body">
				<div class="col-xs-2 padding-left-0">
					<button
						class="btn btn-primary padding-top-1 padding-bottom-1 row-height checkPermission"
						permissioncode='sys:workgroup:main:01'
						type="button" onclick="add();">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</button>
				</div>
				<div class=" pull-right padding-left-0 padding-right-0">
					<div
						class=" pull-left padding-left-5 padding-right-0" style="width: 280px;">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control ">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class=" pull-right padding-left-10 padding-right-0">
		
						<div class=" pull-left padding-left-0 padding-right-0" style="width:250px;" >
							<input placeholder="工作组代码/工作组名称/备注" id="keyword_search" class="form-control " type="text">
						</div>
	                    <div class=" pull-right padding-left-5 padding-right-0 ">   
							<button id="workGroupMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchWorkGroup();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   		</button>
	                    </div> 
                    
                    </div>
				</div>
				
				<div class="clearfix"></div>
				
				<div class="col-xs-12 text-center padding-left-0 padding-right-0 table-h " >

					<table style="min-width:900px"
						class="table table-thin table-bordered table-striped table-hover table-set">
						<thead>
							<tr>
								<th class="colwidth-7" >
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class=" sorting title colwidth-15"  onclick="orderBy('gzzdm')" id="gzzdm_order"><div class="important">
								<div class="font-size-12 line-height-18">工作组代码</div>
									<div class="font-size-9 line-height-18" >WorkGroup Code</div></div></th>
								<th class=" sorting title colwidth-15" onclick="orderBy('gzzmc')" id="gzzmc_order"><div class="important">
									<div class="font-size-12 line-height-18">工作组名称</div>
									<div class="font-size-9 line-height-18">WorkGroup Name</div>
								</div></th>
								<th class="sorting colwidth-13" onclick="orderBy('remark')" id="remark_order"><div class="important">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div></div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('mrbs')" id="mrbs_order">
										<div class="font-size-12 line-height-18">默认标识</div>
										<div class="font-size-9 line-height-18">Mark</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('whrid')" id="whrid_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
								<th class="sorting colwidth-13" onclick="orderBy('whsj')" id="whsj_order"><div
										class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div></th>
									<th class="sorting colwidth-13" onclick="orderBy('whbmid')" id="whbmid_order">
									<div class="font-size-12 line-height-18">维护部门</div>
									<div class="font-size-9 line-height-18">Record Dept.</div>
								</th>
								<th class="colwidth-15"><div
										class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div></th>
							</tr>
						</thead>
						<tbody id="list">
							<!------ plane列

表展示 ------>

						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>

	<!-------添加模态框 start-------->
	<div class="modal fade modal-new" id="addModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog" style="width: 55%;">
			<div class="modal-content">
			 <div class="modal-header modal-header-new" >
	               	<h4 class="modal-title" >
	           			<div class='pull-left'>
	               			<div class="font-size-12" >工作组管理</div>
							<div class="font-size-9 " >WorkGroup Management</div>
					 	</div>
						<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class='clearfix'></div>
					</h4>
          		</div>
				<div class="modal-body padding-bottom-0" id="xlhExistModalBody">
				   <div class="col-xs-12 margin-top-8 ">
              			<div class="input-group-border">
              			<form id="form">
								<div
										class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
										<label
											class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "><div
												class="font-size-12"><span style="color: red">*</span>工作组代码</div>
											<div class="font-size-9">WorkGroup Code</div></label>
										<div
											class="col-lg-10 col-sm-10 col-xs-8 padding-leftright-8 padding-right-0 ">
											<input class="form-control" id="gzzdm" name="gzzdm" 
												placeholder='' maxlength="50"/>
										</div>
									</div>
									
									<div
										class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-top-0 padding-right-0 form-group">
										<label
											class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "><div
												class="font-size-12"><span style="color: red">*</span>工作组名称</div>
											<div class="font-size-9">WorkGroup Name</div></label>
										<div
											class="col-lg-10 col-sm-10 col-xs-8 padding-leftright-8 padding-right-0 ">
											<input class="form-control" id="gzzmc" name="gzzmc"
												placeholder='' maxlength="33"/>
										</div>
									</div>
									<div
										class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-top-0 padding-right-0 form-group">
										<label
											class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "><div
												class="font-size-12"><span style="color: red">*</span>默认标识</div>
											<div class="font-size-9">Mark</div></label>
										<div
											class="col-lg-10 col-sm-10 col-xs-8 padding-leftright-8 padding-right-0 ">
											<label style="margin-right: 20px;font-weight: normal"><input name="mrbs" style="vertical-align: middle;margin-top: -4px;margin-right: 3px;" type="radio" value="0" checked/>初始</label> 
											<label style="font-weight: normal"><input name="mrbs" style="vertical-align: middle;margin-top: -4px;margin-right: 3px;" type="radio" value="1" />默认</label> 
										</div>
									</div>
									
									
									<div
										class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-top-0 padding-right-0 form-group">
										<label
											class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "><div
												class="font-size-12">备注</div>
											<div class="font-size-9">Remark</div></label>
										<div
											class="col-lg-10 col-sm-10 col-xs-8 padding-leftright-8 padding-right-0 ">
											<textarea class="form-control" id="remark" name="remark" style="height:65px"
												placeholder='' maxlength="100"></textarea>
										</div>
									</div>
									<div class='clearfix'></div>
									</form>
									<div style="display: none">
									<input  id="id" type="text" name="id" value="" />
									<input  id="dpt" type="text" name="dpt" value="" />
								</div>
              			</div>
              		</div>
              		<div class='clearfix'></div>
              		</div>
				    <div class="modal-footer">
	          		<div class="col-xs-12 padding-leftright-8" >
						<div class="input-group">
							
		                   	<span class="input-group-addon modalfooterbtn">
		                     	<button type="button" id="save" onclick="save()"
									class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
								<button type="button" onclick="closeModal()"
									class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
		                   	</span>
	              		</div><!-- /input-group -->
					</div>
				</div>
												
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="${ctx }/static/js/thjw/sys/workgroup/WorkGroup.js"></script>
</body>
</html>