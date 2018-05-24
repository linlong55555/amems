<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${ctx}/static/lib/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/lib/bootstrap-switch/js/bootstrap-switch.min.js"></script>
<link rel="stylesheet"
	href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var syscode= "${param.syscode }";
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
	<div class="page-content">
		<div class="panel panel-primary">			
				<div class="pane panel-primary">
					<div class="panel-heading" id="NavigationBar"></div>
					<input type="hidden" id="adjustHeight" value="10">
					<div class="panel-body padding-bottom-0">
						<div
							class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0">
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="panel-title margin-bottom-10">
									<div class=" pull-left padding-left-5 padding-right-0 padding-top-0">
										<div class="font-size-14 line-height-14" >系统设置</div>
										<div class="font-size-9  line-height-14">System Setting</div>
									</div>
							
									<div class=" pull-right padding-left-5 padding-right-0 padding-top-0" style="width: 280px;">
										<div class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-14 line-height-14">系统描述</div>
										<div class="font-size-9 line-height-14">System Description</div>
									</div>
									<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 pull-right">
										<select id="sysCode" class="form-control ">
											<c:forEach items="${info}" var="type">
												<option value="${type.syscode}"	>${erayFns:escapeStr(type.ms)}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="panel-body">
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10" id="LOGIN_OUT_id" style="display: none;">
									<div
											class="col-lg-2 col-sm-2 col-xs-2 margin-top-0 padding-left-0 padding-right-0">
										<label
											class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height"><div
												class="font-size-12">退出系统转向URL</div></label>
										</div>
										<div 
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<input type="hidden" value="LOGIN_OUT" id="LOGIN_OUT_PZBM"/>									
											<input type="text"  class="form-control" maxlength="160"
											id="LOGIN_OUT" value="" name="LOGIN_OUT" /> <span style="color: #F00"></span></div>
									</div>														
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10" id="SESSION_LOSE_id" style="display: none;">
									<div
											class="col-lg-2 col-sm-2 col-xs-2 margin-top-0 padding-left-0 padding-right-0">
										<label
											class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height"><div
												class="font-size-12">Session失效转向URL</div></label>
												</div>
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<input type="hidden" value="SESSION_LOSE" id="SESSION_LOSE_PZBM"/>
											<input type="text" class="form-control" id="SESSION_LOSE" value="" name="SESSION_LOSE" maxlength="160" /> </div>
									</div>
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10" id="LOGIN_ERROR_id" style="display: none;">
									<div
											class="col-lg-2 col-sm-2 col-xs-2 margin-top-0 padding-left-0 padding-right-0">
										<label
											class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height"><div
												class="font-size-12">登录失败转向URL</div></label>
											</div>	
												<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<input type="hidden" value="LOGIN_ERROR" id="LOGIN_ERROR_PZBM"/>
											<input type="text" id="LOGIN_ERROR" value="" name="LOGIN_ERROR" maxlength="160" class="form-control "/> 
										</div>
									</div>
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10" id="ATT_ROOT_PATH_id" style="display: none;">
									<div
											class="col-lg-2 col-sm-2 col-xs-2 margin-top-0 padding-left-0 padding-right-0">
										<label
											class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height"><div
												class="font-size-12">文件根目录</div></label>
											</div>	
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<input type="hidden" value="ATT_ROOT_PATH" id="ATT_ROOT_PATH_PZBM"/>
											<input type="text" id="ATT_ROOT_PATH" value="" name="ATT_ROOT_PATH" class="form-control " maxlength="160" /></div>
									</div>
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10" id="DEFAULT_PASSWORD_id" style="display: none;">
									<div
											class="col-lg-2 col-sm-2 col-xs-2 margin-top-0 padding-left-0 padding-right-0">
										<label
											class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height"><div
												class="font-size-12">默认密码</div></label>
											</div>	
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<input type="hidden" value="DEFAULT_PASSWORD" id="DEFAULT_PASSWORD_PZBM"/>
											<input type="text" id="DEFAULT_PASSWORD" value="" name="DEFAULT_PASSWORD" class="form-control " maxlength="160" /></div>
									</div>
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10" id="MAC_LIMIT_id" style="display: none;">
										<div
											class="col-lg-2 col-sm-2 col-xs-2 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height"><div
												class="font-size-12">MAC限制</div></label>
										</div>	
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
												<input type="hidden" value="MAC_LIMIT" id="MAC_LIMIT_PZBM"/>
												<div id="customDiv"></div>
										</div>									
								  </div>
								  <div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10" id="SUPER_AGENCY_id" style="display: none;">
										<div
											class="col-lg-2 col-sm-2 col-xs-2 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height"><div
												class="font-size-12">超级机构</div></label>
										</div>	
											<div
												class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
												<input type="hidden" value="SUPER_AGENCY" id="SUPER_AGENCY_PZBM"/>
												<input type="text" id="SUPER_AGENCY" value="" name="SUPER_AGENCY" class="form-control " maxlength="160" /></div>
									</div>
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10" id="PAGING_NUM_id" style="display: none;">
										<div
											class="col-lg-2 col-sm-2 col-xs-2 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height"><div
												class="font-size-12">分页数</div></label>
										</div>	
											<div
												class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
												<input type="hidden" value="PAGING_NUM" id="PAGING_NUM_PZBM"/>
												<input type="text" id="PAGING_NUM" value="" name="PAGING_NUM" class="form-control " maxlength="500" /></div>
									</div>
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10" id="AGENCY_TYPE_id" style="display: none;">
										<div
											class="col-lg-2 col-sm-2 col-xs-2 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height"><div
												class="font-size-12">机构类型</div></label>
										</div>	
											
												<div 
												class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
												<input type="hidden" value="AGENCY_TYPE" id="AGENCY_TYPE_PZBM"/>
												<input type="text"	id="AGENCY_TYPE" value="" name="AGENCY_TYPE" class="form-control " maxlength="160" />
												</div>
												<div class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
													<button class="line6 " onclick="addJglx()">
														<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
													</button>
												</div>
																		
									</div>											
								</div>
							</div>
						</div>
						<div class="pull-right">
							<button 
								class="btn btn-primary padding-top-0 padding-bottom-1 checkPermission" permissioncode='sys:settings:main:01' 
								onclick="save()">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
						</div>
					</div>
				</div>
			
		</div>
	</div>
	<script type="text/javascript"
		src="${ctx }/static/js/thjw/sys/globleSystemSetting/globleSystemSetting.js"></script>
</body>
</html>
