<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改维修方案</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
	<input type="hidden" id="dprtcode" value="${maintenance.dprtcode}" />
	<input type="hidden" id="oldjx" value="${erayFns:escapeStr(maintenance.jx)}" />
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>		
			<div class="panel-body">
			
				<form id="form">
					<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class="font-size-16 line-height-18">基本信息</div>
						<div>Basic Information</div>
					</div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12 margin-bottom-10 padding-right-0">
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>维修方案编号</div>
								<div class="font-size-9 line-height-18">No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control " id="wxfabh" value="${erayFns:escapeStr(maintenance.wxfabh)}" readonly />
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>维修方案名称</div>
								<div class="font-size-9 line-height-18">Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control " value="${erayFns:escapeStr(maintenance.zwms)}" id="zwms" name="zwms" maxlength="16" />
								<input type="hidden" id="id" value="${maintenance.id}" />
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>版本</div>
								<div class="font-size-9 line-height-18">Revision</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="bb" name="bb" value="${erayFns:escapeStr(maintenance.bb)}" class="form-control " maxlength="10"  onkeyup='clearNoNumTwo(this)'/>
							</div>
						</div>	
						
						<%-- <div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>计划生效日期</div>
								<div class="font-size-9 line-height-18">Plan Effective Date</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control date-picker" id="jhSxrq" name="jhSxrq" value="<fmt:formatDate value='${maintenance.jhSxrq}' pattern='yyyy-MM-dd' />" readonly data-date-format="yyyy-mm-dd" type="text" />
							</div>
						</div> --%>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>机型</div>
								<div class="font-size-9 line-height-18">Model</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<c:choose>
									<c:when test="${empty maintenance.fBbid}">
										<select class="form-control " id="jx" name="jx"></select>
									</c:when>
									<c:otherwise>
										<select class="form-control " id="jx" name="jx" disabled=true></select>
									</c:otherwise>
								</c:choose>
								
							</div>
						</div>
					
						<div class="clearfix"></div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单人</div>
								<div class="font-size-9 line-height-18">Creator</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="zdr" name="zdr" value="${erayFns:escapeStr(maintenance.zdr.displayName)}" class="form-control " readonly/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单时间</div>
								<div class="font-size-9 line-height-18">Create Time</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control" id="zdsj" name="zdsj" value="<fmt:formatDate value='${maintenance.zdsj}' pattern='yyyy-MM-dd HH:mm:ss' />" readonly type="text" />
							</div>
						</div>
					
				 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea class="form-control" id="bz" name="bz" maxlength="300">${erayFns:escapeStr(maintenance.bz)}</textarea>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</form>
			<div class="text-right">
                   <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="update()">
                   	<div class="font-size-12">保存</div>
					<div class="font-size-9">Save</div>
				</button>
              		<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()">
              			<div class="font-size-12">返回</div>
					<div class="font-size-9">Back</div>
				</button>
			</div>
		
		</div>
	</div>

		<!-- 编辑维修方案End -->
		
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/maintenance_edit.js"></script>
</body>
</html>