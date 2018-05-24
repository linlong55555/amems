<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>借调对象列表</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<input maxlength="20" type="hidden" id="userId" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
<div class="page-content ">
			<!-- from start -->
	<div class="panel panel-primary">
    <div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<div  class='searchContent row-height' >
			<input type="hidden" id="zzjgid" name="zzjgid"  value="${user.jgdm}" />
                <div class="col-xs-6 padding-left-0 form-group">
					<a href="" data-toggle="modal" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission
					" permissioncode="aerialmaterial:secondment:main:01" onclick="add()">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
				</div>  
				<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0 form-group">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='借调对象描述' id="keyword_search" class="form-control ">
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
				<!------------搜索框end------->
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0  margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">借调对象类型</div>
							<div class="font-size-9">S/O Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
						       <select  id="jddxlx_search" name="jddxlx_search" class="form-control">
					                 <option value="" selected="true" >查看全部</option>
					                 <c:forEach items="${secondmentTypeEnum}" var="type">
									   <option value="${type.id}" >${type.name}</option>
							     	 </c:forEach>
						       </select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0  margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
						       <select  id="dprtcode_search" name="dprtcode_search" class="form-control">
					                 <c:forEach items="${accessDepartment}" var="type">
									   <option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
							     	 </c:forEach>
						       </select>
						</div>
					</div>
					<div class="col-lg-2 pull-right text-right padding-right-0" style="margin-bottom: 10px;">

						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1 "
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>	
				<div class="clearfix"></div>
                </div>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" style="overflow-x: auto;">
					<table id="secondment_list_table" class="table-set table table-thin table-bordered table-striped table-hover" >
						<thead>
							<tr>
								<th class="fixed-column colwidth-7">
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>

								<th class="sorting colwidth-10" onclick="orderBy('jddxlx')" id="jddxlx_order">
									<div class="font-size-12 line-height-18">借调对象类型</div>
									<div class="font-size-9 line-height-18">S/O Type</div>
								</th>

								<th  class="sorting colwidth-28" onclick="orderBy('jddxms')" id="jddxms_order">
									 <div class="important">
									  <div class="font-size-12 line-height-18">借调对象描述</div>
									  <div class="font-size-9 line-height-18">S/O Desc</div>
									</div>
								</th>
								<th class="sorting colwidth-28" onclick="orderBy('bz')" id="bz_order">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('whrid')" id="whrid_order">
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('whsj')" id="whsj_order">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('dprtcode')" id="dprtcode_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
				</div>
			</div>
		</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/secondment/secondmentlist.js"></script>
</body>
</html>
