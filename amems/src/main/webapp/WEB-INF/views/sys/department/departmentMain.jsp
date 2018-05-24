<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>组织机构管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<input maxlength="20" type="hidden" id="userId" value="" />
<input type="hidden" id="orgcode" value="${user.orgcode}" />
<input type="hidden" id="dprtcode" value="${dprtcode}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
		<div class="page-content">
			<div class="panel panel-primary">
		    <div class="panel-heading" id="NavigationBar"></div>
			   <div class="panel-body">
			
				<div class="col-xs-2 col-md-3 padding-left-0 ">
					<a href="#" data-toggle="modal" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='sys:department:main:01'  onclick="add()">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
				</div>
				
				<div class=" pull-right padding-left-0 padding-right-0">
				    <div class=" pull-left padding-left-5 padding-right-0 row-height" style="width: 250px;">
						<input type="text" placeholder='组织机构代码/组织机构名称' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button id="departmentMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                    </div> 
				  </div> 
				
				
               <div class="clearfix"></div>

					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="margin-top: 10px;">
						<table
							class="table table-thin table-bordered table-striped table-hover table-set">
							<thead>
								<tr>
									<th class="colwidth-5" style="vertical-align: middle;"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th class="colwidth-10 sorting" style="vertical-align: middle;"  onclick="orderBy('dprtcode')" id="dprtcode_order">
									   <div class="important">
								     	 <div class="font-size-12 line-height-18">组织机构代码</div>
										 <div class="font-size-9 line-height-18">Organization Code</div>
									 	</div> 
									</th>
									<th class="colwidth-10 sorting" style="vertical-align: middle;"  onclick="orderBy('dprtname')" id="dprtname_order">
									   <div class="important">
										<div class="font-size-12 line-height-18">组织机构名称</div>
										<div class="font-size-9 line-height-18">Organization Name</div>
										</div>
									</th>
									<th class="colwidth-7 sorting" style="vertical-align: middle;" onclick="orderBy('zczh_max')" id="zczh_max_order">
									     <div class="font-size-12 line-height-18">最大用户注册数量</div>
										 <div class="font-size-9 line-height-18">Max Users Reg</div>
									</th>
									<th class="colwidth-7 sorting" style="vertical-align: middle;"  onclick="orderBy('zcfj_max')" id="zcfj_max_order">
									     <div class="font-size-12 line-height-18">最大飞机注册数量</div>
										 <div class="font-size-9 line-height-18">Max Plane Reg</div>
									</th>
									<th class="colwidth-7 sorting" style="vertical-align: middle;"  onclick="orderBy('yxqks')" id="yxqks_order">
									     <div class="font-size-12 line-height-18">开始日期</div>
										 <div class="font-size-9 line-height-18">Start Date</div>
									</th>
									<th class="colwidth-7 sorting" style="vertical-align: middle;"  onclick="orderBy('yxqjs')" id="yxqjs_order">
									     <div class="font-size-12 line-height-18">结束日期</div>
										 <div class="font-size-9 line-height-18">End Date</div>
									</th>
									<th class="colwidth-15 sorting" style="vertical-align: middle;"  onclick="orderBy('remark')" id="remark_order">
									     <div class="font-size-12 line-height-18">备注</div>
										 <div class="font-size-9 line-height-18">Remark</div>
									</th>
								</tr>
							</thead>
							<tbody id="list">
							</tbody>
						</table>
					</div>
					
					<div class="col-xs-12  page-height text-center" id="pagination">
						
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/sys/department/department.js"></script>
</body>
</html>
