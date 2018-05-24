<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script>
	var roleToModel = $.parseJSON('${roleToModel}');
	</script>
</head>
<body>
	<div class="page-content">
		<input type="hidden" id="id" value="${id}"/>
		<input type="hidden" id="jgdm" value="${user.jgdm}"/>
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
         		<form id="form">
         			<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
						<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">角色代码</div>
								<div class="font-size-9 line-height-18">Role Code</div>
							</label>
							<div class=" col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<input type="text" class="form-control" readonly="readonly" value="${erayFns:escapeStr(role.roleCode)}"/>
							</div>
						</div>
						<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">
									<span style="color: red">*</span>机型角色名称
								</div>
								<div class="font-size-9 line-height-18">Role Name</div>
							</label>
					   		<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0" >
								<input type="text" class="form-control " id="roleName" maxlength="30" name="roleName" value="${erayFns:escapeStr(role.roleName)}" >
							</div>
						</div>
						<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">组织机构</div>
								<div class="font-size-9 line-height-18">Organization</div>
							</label>
						 <div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0" >
							<input type="hidden" class="form-control " id="dprtcode"  name="dprtcode" value="${role.dprtId}" >
							<input type="text" class="form-control " id="dprtName"  name="dprtName" value="${erayFns:escapeStr(role.dprtName)}"  disabled="disabled">
						 </div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<label class="col-lg-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0 ">
							<textarea class="form-control" id="roleRemark" name="roleRemark" maxlength="50" >${erayFns:escapeStr(role.roleRemark)}</textarea>
						</div>
					</div>
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 padding-top-10" style="margin-bottom:10px; overflow-x:auto" >
						<table class="table table-thin table-bordered table-striped table-hover table-set" style="min-width:1000px!important;">
							<thead>
								<tr>
									<th class=" colwidth-10" >
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
									<th class=" colwidth-15" >
										<div class="font-size-12 line-height-18">飞机机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</th>
									<th>
										<div class="font-size-12 line-height-18 ">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div>
									</th>
								</tr>
							</thead>
							<tbody id="list">
							
							</tbody>
						</table>
					</div>
				</div>
			</form>
		</div>
		<div class="text-right">
            <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="modify()">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/sys/model_role_modify.js"></script>
</body>
</html>