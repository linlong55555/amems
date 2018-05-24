<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>维修方案改版</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script>
	var pageParam = '${param.pageParam}';
	</script>
	<style type="text/css">
	
	.multiselect{
	overflow:hidden;
	}
	</style>
</head>
<body>
	<input type="hidden" id="dprtcode" value="${maintenance.dprtcode}" />
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>			
			<div class="panel-body">
			
				<form id="form">
					<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class="font-size-16 line-height-18">基本信息</div>
						<div>Basic Information</div>
					</div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">维修方案编号</div>
								<div class="font-size-9 line-height-18">No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="wxfabh"  name="wxfabh" value="${erayFns:escapeStr(maintenance.wxfabh)}" class="form-control " readonly/>
								<input type="hidden" id="id" value="${maintenance.id}" />
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">维修方案名称</div>
								<div class="font-size-9 line-height-18">Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="zwms" name="zwms" value="${erayFns:escapeStr(maintenance.zwms)}" class="form-control " readonly/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">原版本</div>
								<div class="font-size-9 line-height-18">Revision</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="bb" name="bb" value="${erayFns:escapeStr(maintenance.bb)}" class="form-control " placeholder='' onkeyup='clearNoNumTwo(this)' readonly/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>新版本号</div>
								<div class="font-size-9 line-height-18">New Revision</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="newbb" name="newbb" value="${erayFns:escapeStr(maintenance.bb)}" class="form-control " placeholder=''  maxlength="10" onkeyup='clearNoNumTwo(this)'/>
							</div>
						</div>
					
						<div class="clearfix"></div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">机型</div>
								<div class="font-size-9 line-height-18">Model</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="jx" name="jx" value="${erayFns:escapeStr(maintenance.jx)}" class="form-control " readonly />
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单人</div>
								<div class="font-size-9 line-height-18">Creator</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="zdr" name="zdr" value="${erayFns:escapeStr(maintenance.zdr.displayName)}" class="form-control " readonly/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单时间</div>
								<div class="font-size-9 line-height-18">Create Time</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control" id="zdsj" name="zdsj" value="<fmt:formatDate value='${maintenance.zdsj}' pattern='yyyy-MM-dd HH:mm:ss' />" readonly type="text" />
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 " >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">修订通知书</div>
								<div class="font-size-9 line-height-18">Notice</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class=" " id="xdtzsid" name="xdtzsid" multiple="multiple">
								
								</select>
							</div>
						</div>
						
					</div>
					<div class="clearfix"></div>
				</form>
				<div class="text-right margin-top-10 margin-right-0">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/maintenance_modify.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/select.js"></script>
</body>
</html>