<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
	<input type="hidden" id="dprtcode" value="${user.jgdm}" />
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>		
			<div class="panel-body">
			
				<form id="form">
					<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class="font-size-16 line-height-18">基本信息</div>
						<div>Basic Information</div>
					</div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-right-0">
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>维修方案名称</div>
								<div class="font-size-9 line-height-18">Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control " id="zwms" name="zwms" maxlength="16"/>
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group " >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>版本</div>
								<div class="font-size-9 line-height-18">Revision</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="bb" name="bb" class="form-control " maxlength="10" onkeyup='clearNoNumTwo(this)'/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>机型</div>
								<div class="font-size-9 line-height-18">Model</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class="form-control " id="jx" name="jx">
								</select>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					
				 	<div class="col-lg-12 col-sm-12 col-xs-12 margin-bottom-10 padding-right-0">
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea class="form-control" id="bz" name="bz" maxlength="300"></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
				
			</form>
			<div class="text-right">
                   <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="save()">
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

		<!-- 基本信息 End -->
		
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/maintenance_add.js"></script>
</body>
</html>