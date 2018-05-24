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
<title>基地</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

<style>
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
	<input type="hidden" id="whrid" value="${sessionScope.user.id}" />
	<input type="hidden" id="zzjg" value="${user.jgdm}" />
	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>		
			<!-- <input type="hidden" id="adjustHeight" value="10"> -->
			<div class="panel-body padding-bottom-0">
			<div class='row-height searchContent'>
				<div class="col-xs-2 padding-left-0 form-group">
					<button
						class="btn btn-primary padding-top-1 padding-bottom-1"
						type="button" onclick="addBaseMaintenance();">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</button>
				</div>
				<div class=" pull-right padding-left-0 padding-right-0 form-group">
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
						<div
							class=" pull-left padding-left-0 padding-right-0" style="width:250px;">
							<input type="text" class="form-control " id="keyword_search" placeholder="基地描述/维护人" />
						</div>
						<div class=" pull-right padding-left-5 padding-right-0 ">
							<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchBaseMaintenance();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
							</button>
						</div>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
				<div
					class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height "
					style="overflow-x: auto">

					<table
						class="table table-thin table-bordered table-striped table-hover table-set">
						<thead>
							<tr>
								<th class="sort colwidth-7" >
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class=" colwidth-15"  ><div class="important">
								<div class="font-size-12 line-height-18">基地描述</div>
									<div class="font-size-9 line-height-18">Base Description</div></div></th>
								<th class="sort colwidth-15" ><div class="important">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</div></th>
								<th class="sort colwidth-15"  ><div
										class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div></th>
								<th class="colwidth-15" ><div
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

			</div>
		</div>
	</div>

	<!-------添加模态框 start-------->
	<div class="modal fade" id="BaseMaintenanceModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog" style="width: 500px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="xlhExistModalBody">
					<div class="panel-body">
						<div
							class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="font-size-16 line-height-18">基地维护</div>
									<div class="font-size-9 ">Base Maintenance</div>
								</div>
								<div class="panel-body">									
									<div
										class="col-lg-12 col-sm-12 col-xs-12 margin-top-10 padding-left-0 padding-right-0">
										<label
											class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0"><div
												class="font-size-12"><span style="color: red">*</span>基地描述</div>
											<div class="font-size-9">Base Description</div></label>
										<div class="col-xs-9 col-sm-9 col-lg-9 padding-left-4 padding-right-0">
											<input type="text" value="" name="jdms" class="form-control "
												maxlength="15" id="jdms">
											<input type="hidden" value="" name="dpt" class="form-control "
												 id="dpt">
										</div>
									</div>
									<div style="display: none">
									<input  id="id" type="text" name="id" value="" />
									</div>
								<div class="pull-right padding-top-10 margin-top-5">
										<button type="button" onclick="saveBaseMaintenanceData()"
											class="btn btn-primary padding-top-1 padding-bottom-1">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
										</button>
									<button type="button" 
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
								</div>																
								</div>
							</div>
						</div>
					</div>
				</div>			
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 	
	<script type="text/javascript"
		src="${ctx }/static/js/thjw/sys/basemaintenance/BaseMaintenance.js"></script>	
</body>
</html>