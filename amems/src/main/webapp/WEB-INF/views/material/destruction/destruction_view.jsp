<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>销毁单</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
.bootstrap-tagsinput {
  width: 95% !important;
}
</style>
<link rel="stylesheet" href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css" type="text/css">
<script type="text/javascript" src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<script type="text/javascript">
	var pageParam = '${param.pageParam}';
</script>

</head>
<body>

	<div class="page-content ">
	<script type="text/javascript">
	$(document).ready(function(){
	//导航
	Navigation(menuCode);
	});
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
	</script>
		<!-- 页面操作区 Start -->
		<div class="panel panel-primary">
			<div class="panel-heading"  id="NavigationBar"></div>
			<input type="hidden" id="dprtcode" value="${user.jgdm}">
			<input type="hidden" id="destructionId" value="${destruction.id}">
			<input type="hidden" id="destructionZt" value="${destruction.zt}">
			
			<div class="panel-body">
				<form id="form">
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class="font-size-16 line-height-18">基本信息</div>
							<div class="font-size-9 ">Basic Info</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">销毁单号</div>
								<div class="font-size-9 line-height-18">Destroy No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control date-picker"  id="xhdh" name="xhdh" type="text" value="${destruction.xhdh }" disabled="disabled" />
								
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">销毁人</div>
								<div class="font-size-9 line-height-18">Destroy Person</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control date-picker"  id="xhr" name="xhr" type="text" value="${destruction.xhr_user.displayName }"  disabled="disabled" />
								
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">销毁日期</div>
								<div class="font-size-9 line-height-18">Destroy Date</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control date-picker"  id="xhrq" name="xhrq" data-date-format="yyyy-mm-dd" type="text" 
								value=<fmt:formatDate value='${destruction.xhrq }' pattern='yyyy-MM-dd' />  disabled="disabled" />
								
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">状态</div>
								<div class="font-size-9 line-height-18">State</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control date-picker"  id="ztText" name="ztText" type="text" value="${destruction.ztText }"  disabled="disabled" />
								
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单人</div>
								<div class="font-size-9 line-height-18">Creator</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control "  id="whr" name="whr"  type="text"  disabled="disabled"
								value='${destruction.zdr_user.displayName }'  /> 
								
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"></span>制单时间</div>
								<div class="font-size-9 line-height-18">Create Time</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control " disabled="disabled"  id="zdsj" name="zdsj"  type="text" 
								value="<fmt:formatDate value='${destruction.zdsj }' pattern='yyyy-MM-dd HH:mm:ss' />" />
								
							</div>
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
								<textarea class="form-control" id="bz" name="bz" maxlength="150"  disabled="disabled" >${destruction.bz }</textarea>
							</div>
						</div>
					</div>
				</form>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
					<div class="clearfix"></div>
					
					<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class=" pull-left margin-right-10" >
							<div class="font-size-16 line-height-18">销毁航材</div>
							<div class="font-size-9 ">Destroy list</div>
						</div>	
					</div>
					
	            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">

						<!-- start:table -->
						<div class="margin-top-10 ">
						<div class="overflow-auto">
							<table id="detailTable" class="table table-thin table-bordered table-striped table-hover" style="min-width:1600px">
								<thead>
							   		<tr>
									<th class='fixed-column  colwidth-15' ><div
											class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th class=' colwidth-25' ><div
											class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div></th>
									<th class=' colwidth-25' ><div
											class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div></th>
									<th  class=" colwidth-8"><div
											class="font-size-12 line-height-18">厂家件号</div>
										<div class="font-size-9 line-height-18">MP/N</div></th>
									<th  class=" colwidth-6"><div
											class="font-size-12 line-height-18">航材类型</div>
										<div class="font-size-9 line-height-18">Airmaterial type</div></th>
									<th  class=" colwidth-15"><div
											class="font-size-12 line-height-18">航材管理级别</div>
										<div class="font-size-9 line-height-18">Aircraft Management Level</div></th>
									<th  class=" colwidth-15"><div
											class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">S/N</div></th>
									<th  class=" colwidth-15"><div
											class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">B/N</div></th>
									<th class=" colwidth-8"><div
											class="font-size-12 line-height-18">适航证号</div>
										<div class="font-size-9 line-height-18">Certificate Of No.</div></th>
									<th class=" colwidth-5"><div
											class="font-size-12 line-height-18">数量</div>
										<div class="font-size-9 line-height-18">Num</div></th>
									<th  class=" colwidth-5"><div
											class="font-size-12 line-height-18">单位</div>
										<div class="font-size-9 line-height-18">Unit</div></th>
									<th class=" colwidth-6"><div
											class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Store</div></th>
									<th class=" colwidth-8"><div
											class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage Location</div></th>
								</tr>
								</thead>
								<tbody id="detailTBody">
								</tbody>
							</table>
							</div>
						</div>
						<!-- end:table -->
			     		<div class="clearfix"></div>
					 </div> 
				</div>
				<div class="clearfix"></div>



	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
	
					<div class="clearfix"></div>
					
					<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class=" pull-left margin-right-10" >
							<div class="font-size-16 line-height-18">上传附件</div>
							<div class="font-size-9 ">File list</div>
						</div>	
					 	<div class="pull-left ">
							<button id="btnGoAdd" style="display:none" title="增加 Add" class="btn btn-primary " onclick="javascript:window.history.go(-1)">
								<i class="icon-plus cursor-pointer"></i>
							</button>
			          	</div>
					</div>
					
					
								
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:900px">
										<thead>
								<tr>
									<th>
									<div class="font-size-12 line-height-18">文件说明</div>
										<div class="font-size-9 line-height-18">File Desc</div>
									</th>
									
									
									<th>
									<div class="font-size-12 line-height-18">文件大小</div>
										<div class="font-size-9 line-height-18">File Size</div>
									</th>
									<th><div class="font-size-12 line-height-18">上传人</div>
										<div class="font-size-9 line-height-18">Uploader</div></th>
									<th><div class="font-size-12 line-height-18">上传时间</div>
										<div class="font-size-9 line-height-18">Upload Time</div></th>					
								</tr>
							</thead>
							    <tbody id="filelist">
									 
								</tbody>
									</table>
				</div>
				<div class="clearfix"></div>













				<!-- <div class="text-right">
               		<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="pageback();">
               			<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
            	</div> -->
			</div>
		</div>
		<!-- 页面操作区 End -->
	<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/work_order.jsp"%>
	
	</div>
		<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
		<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
			<script type="text/javascript"
		src="${ctx}/static/js/thjw/material/destruction/destruction_view.js"></script>
</body>
</html>