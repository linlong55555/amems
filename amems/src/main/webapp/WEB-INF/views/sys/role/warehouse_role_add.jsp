<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body>
	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
              		<form id="form">
              			<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
							<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">
										<span style="color: red">*</span>角色代码
									</div>
									<div class="font-size-9 line-height-18">Role Code</div>
								</label>
								<div class=" col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control" name="roleCode" maxlength="30" id="roleCode" >
								</div>
							</div>
							
							<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">
										<span style="color: red">*</span>库房角色名称
									</div>
									<div class="font-size-9 line-height-18">Role Name</div>
								</label>
								 <div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0" >
									<input type="text" class="form-control" id="roleName" maxlength="30" name="roleName">
								</div>
							</div>
					
							<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</label>
						 		<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0" >
								<select id="dprtcode" name="dprtcode" class="form-control ">
									<c:forEach items="${accessDepartments}" var="type">
										<option value="${type.id}" >${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					
						<div class="clearfix"></div>
					
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							<label class="col-lg-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="roleRemark" name="roleRemark" maxlength="50" ></textarea>
							</div>
						</div>
					</div>
				</form>
				
			    <div class="clearfix"></div>
			    
			</div>
		  <div class="text-right">
                   <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="save()">
	                   	<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
				   </button>
                   <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:go()">
                   		<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
            </div>
				
			<div class="clearfix"></div>
			
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/sys/warehouse_role_add.js"></script>
</body>
</html>