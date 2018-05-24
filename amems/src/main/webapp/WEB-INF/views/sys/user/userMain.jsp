<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script>
		var id = "${param.id}";
		var pageParam = '${param.pageParam}';
	</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="userId" value="" />
	<div class="clearfix"></div>
	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<div  class='searchContent margin-top-0 row-height' >
				<div class="margin-bottom-0 col-xs-12 padding-left-0 padding-right-0">
					<div class="col-md-3 padding-left-0 form-group">
						<a href="#" onclick="add()" data-toggle="modal" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='sys:user:main:01'  >
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a> 
					 	<a href="#" onclick="showImportModal()"  class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission" permissioncode='sys:user:main:06'>
							<div class="font-size-12">导入</div>
							<div class="font-size-9">Import</div>
						</a>
						<a href="#" onclick="exportExcel()"  class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission" permissioncode='sys:user:main:07'>
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</a>
					</div>
					<div class="pull-right padding-left-0 padding-right-0 form-group">
						<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</span>
							<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="dprtcode" class="form-control " name="dprtcode" onchange="changeSelectedPlane()">
									<c:if test="${accessDepartments== null || fn:length(accessDepartments) > 1}">
										<option value="">显示全部 All</option>
									</c:if>
									<c:forEach items="${accessDepartments}" var="type">
										<option value="${type.id}" >${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>	
						
						<div class="pull-left padding-left-10 padding-right-0" style="width:250px;">
							<input type="text" placeholder='登录账号/用户代码/姓名/固定电话/手机'  id="keyword_search" class="form-control">
						</div>
						
				        <div class="pull-right padding-left-5 padding-right-0">   
				            <button id="userMainSearch" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
							</button>
							<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" onclick="search();">
								<div class="pull-left text-center">
									<div class="font-size-12">更多</div>
									<div class="font-size-9">More</div>
								</div>
								<div class="pull-left padding-top-5">&nbsp;
									 <i class="font-size-15 icon-caret-down" id="icon"></i>
								</div>
					   		</button>
				        </div>    
					</div>
					<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-bottom-10 display-none border-cccccc" id="divSearch">
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
								<span class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12" >状态</div>
									<div class="font-size-9">State</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<select id="state" class="form-control "  name="state" >
										<option value="">显示全部 All</option>
										<option value="0">锁定</option>
										<option value="1">解锁</option>
									</select>
								</div>
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
								<span class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12" >部门</div>
									<div class="font-size-9">Department</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<select id="zxdw_search" class="form-control" ">
										<option value="">显示全部 All</option>
									</select>
								</div>
							</div>
							
							<div class="col-lg-3 col-sm-3 col-xs-12 pull-right text-right padding-right-0" style="margin-bottom: 10px;">
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
									<div class="font-size-12">重置</div>
									<div class="font-size-9">Reset</div>
								</button>
							</div> 
						</div>
					</div>
				</div>
				
				<div class="clearfix"></div>
                </div>
				<div class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height" >
					<table id="quality_check_list_table" class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:1300px !important;">
						<thead>
							<tr>
								<th class="fixed-column colwidth-7"><div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="fixed-column colwidth-10 sorting" onclick="orderBy('accountName')" id="accountName_order">
									<div class="important">
									<div class="font-size-12 line-height-18">登录账号</div>
									<div class="font-size-9 line-height-18">Login Account</div></div>
								</th>
								<th class="fixed-column colwidth-10 sorting" onclick="orderBy('username')" id="username_order">
									<div class="important">
									<div class="font-size-12 line-height-18">用户代码</div>
									<div class="font-size-9 line-height-18">User Code</div></div>
								</th>
								<th class="fixed-column colwidth-7 sorting" onclick="orderBy('realName')" id="realName_order">
									<div class="important">
									<div class="font-size-12 line-height-18">姓名</div>
									<div class="font-size-9 line-height-18">Name</div></div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('phone')" id="phone_order">
									<div class="important">
									<div class="font-size-12 line-height-18">固定电话</div>
									<div class="font-size-9 line-height-18">Telephone</div></div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('cellphone')" id="cellphone_order">
									<div class="important">
									<div class="font-size-12 line-height-18">手机号</div>
									<div class="font-size-9 line-height-18">Mobile Phone</div></div>
								</th>
								<th class="colwidth-3 sorting" onclick="orderBy('sex')" id="sex_order">
									<div class="font-size-12 line-height-18">性别</div>
									<div class="font-size-9 line-height-18">Sex</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('email')" id="email_order">
									<div class="font-size-12 line-height-18">电子邮箱</div>
									<div class="font-size-9 line-height-18">E-mail</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('lastvisit')" id="lastvisit_order">
									<div class="font-size-12 line-height-18">最后登入时间</div>
									<div class="font-size-9 line-height-18">Last Login Time</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('lastip')" id="lastip_order">
									<div class="font-size-12 line-height-18">最后登入IP</div>
									<div class="font-size-9 line-height-18">Last Login IP</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('bmdm')" id="bmdm_order">
									<div class="font-size-12 line-height-18">部门</div>
									<div class="font-size-9 line-height-18">Department</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('jgdm')" id="jgdm_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="userlist">
						
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
				
				</div>
				
				<div class="clearfix"></div>
				
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx }/static/js/thjw/sys/userlist.js"></script>
<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
</body>
</html>