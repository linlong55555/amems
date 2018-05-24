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
	<input type="hidden" id="zzjgid" value="${user.jgdm}" />
	<div class="clearfix"></div>
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar">
			</div>
			<div class="clearfix"></div>
			<div class="panel-body padding-bottom-0">
			 <div  class='searchContent margin-top-0 row-height' >
				<div class="margin-bottom-0 col-xs-12 padding-left-0 padding-right-0 ">
					<div class="col-xs-1 col-md-1 padding-left-0 form-group">
						<a href="javascript:void(0);" onclick="account.add();" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="aerialmaterial:requisition:main:01">
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a>
					</div>
					<div class="pull-right padding-left-0 padding-right-0 form-group">
					
					<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
							</span>
							<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<select id="account_dprtcode" class="form-control " name="account_dprtcode" onchange="account.load();">
											<c:if test="${accessDepartments== null || fn:length(accessDepartments) > 1}">
												<option value="">显示全部 All</option>
											</c:if>
											<c:forEach items="${accessDepartments}" var="type">
												<option value="${type.id}" >${erayFns:escapeStr(type.dprtname)}</option>
											</c:forEach>
											<option value="yes">无组织机构</option>
										</select>
							</div>
						</div>	
					
					
						<div class="padding-left-0 padding-right-0 pull-left" style="width:250px;">
								<span class="pull-left col-xs-2 col-sm-2 padding-left-10 text-right padding-left-0 padding-right-0">
								<div class="font-size-12" >状态</div>
									<div class="font-size-9">State</div></span>
								<div class="col-xs-10 col-sm-10 padding-left-8 padding-right-0">
									<select id="account_state" class="form-control "  name="account_state" onchange="account.load();">
										<option value="">显示全部All</option>
									</select>
								</div>
							</div>
							
						<div class="pull-left padding-left-10 padding-right-0" style="width:250px;">
							<input type="text" class="form-control row-height" id="account_keyword_search" placeholder="账号"/>
						</div>
				        <div class="pull-right padding-left-5 padding-right-0">   
				            <button id="zhglSearch" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="account.load();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
							</button>
				        </div>    
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
				<div id="account_table_div" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height" >
					<table class="table table-thin table-bordered table-striped table-hover table-set" id="account_table" >
						<thead>
							<tr>
								<th style="width:100px;">
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th style="width:50px;">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="sorting" name="account_column_username" onclick="account.orderBy('username', this)">
									<div class="important">
										<div class="font-size-12 line-height-18">账号</div>
										<div class="font-size-9 line-height-18">Account</div>
									</div>
								</th>
								<th class="sorting" name="account_column_state" onclick="account.orderBy('state', this)">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</th>
								<th class="sorting" name="account_column_jgdm" onclick="account.orderBy('jgdm', this)">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="account_list">
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="account_pagination">
				</div>
			</div>
		</div>
	</div>
	<!-- 新增账号模态窗 start -->
	<div class="modal fade" id="addAccountModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
   
				  	<div class="panel panel-primary margin-top-10">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">新增账号</div>
							<div class="font-size-9 ">Add</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
			            	<div class="col-lg-12 col-xs-12 padding-top-10 padding-right-0 padding-left-0">
       							<form id="add_account_form">
						  			<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
       									<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18"><span style="color: red">*</span>组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div></label>
								 	<div class="col-xs-9 padding-left-8 padding-right-0" >
									<select id="account_jgdm" name="account_jgdm"  class="form-control" >
										<c:forEach items="${accessDepartments}" var="type">
											<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
										</c:forEach>
									</select>
									</div>	
								</div>
			            			<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10   form-group">
										<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span style="color: red">*</span>账号</div>
											<div class="font-size-9 line-height-18">Account</div>
										</label>
										<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
											<input type="text" class="form-control" maxlength="50" name="account_username" id="account_username" value="" />
										</div>
									</div>
									
									
			            		</form>
							</div> 
		                	<div class="text-right margin-top-10 margin-bottom-10 ">
								<button type="button" onclick="account.saveAccount();"
									class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>									
			                </div>
			     			<div class="clearfix"></div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 新增账号模态窗 end -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/sys/account/account_main.js"></script>
</body>
</html>