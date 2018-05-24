<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
<title>自我质量管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body >
	<input type="hidden" id="whbmid" value="${user.bmdm}" />
	<input type="hidden" id="whrid" value="${sessionScope.user.id}" />
	<input type="hidden" id="dprtcode" value="${user.jgdm}" />
		<div class="page-content ">
			<div class="panel panel-primary" >
				<div class="panel-heading  " id="NavigationBar"> </div>
				<div class="panel-body">
				<form id="form">	
				<div class="panel panel-default">
				<div class="panel-body ">
				    <div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" style="display: none;">
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>审查单号</div>
							<div class="font-size-9 line-height-18">Review No.</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" value="" name="dh" class="form-control " id="dh" />
						</div>
					  </div>
					
					 <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0  margin-bottom-10 padding-right-0 form-group">
							<label class="col-lg-1 col-sm-1 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
									<div class="font-size-12 line-height-18">标题</div>
									<div class="font-size-9 line-height-18">Title</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8  padding-right-0 " >
									<textarea class="form-control" id="zgzt" name="zgzt" placeholder='长度最大为300' maxlength="160" style="min-height:80px" ></textarea>
							</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>整改期限</div>
								<div class="font-size-9">Rectification Deadline</div>
							</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control date-picker"
									maxlength="10" data-date-format="yyyy-mm-dd" id="zgxq" name="zgxq" />
								</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>责任部门</div>
								<div class="font-size-9">Responsible Dept.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left input-group">
									<div class='input-group col-lg-12 col-sm-12 col-xs-12 '>
										<input type="text" value="" name="zrbm" class="form-control " readonly="readonly" id="zrbm" />									
										</div>
										<span class="input-group-btn" >
												<button type="button" class="btn btn-primary "
													 data-toggle="modal"onclick="openZrbm()">
													<i class="icon-search cursor-pointer"></i>
												</button>
										</span>											
									</div>
									
							<div style="display: none;">
								<input type="text" value="" name="zrbmid" id="zrbmid">
							</div>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="panel panel-default">
					<div class="panel-body ">
					 <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 margin-bottom-10 padding-right-0 form-group">
							<label class="col-lg-1 col-sm-1 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>整改原因</div>
									<div class="font-size-9 line-height-18">Cause</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8  padding-right-0 " >
									<textarea class="form-control" id="xfyy" name="xfyy"  maxlength="1300"  ></textarea>
							</div>
					</div>
					 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Enclosure</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<div class='input-group' >
								<input class="form-control"  id="wbwjm" name="wbwjm" type="text" maxlength="100" />
								<span class='input-group-btn'>
								 <div id="fileuploader"></div>
								</span>
						    </div>
						</div>
					  </div>
					  <div class="clearfix"></div>
					  <div class='col-lg-12 col-sm-12 col-xs-12 padding-left-0 margin-bottom-10 padding-right-0  '>
					  <table class="table table-thin table-bordered table-striped table-hover text-center table-set">
						<thead>
							<tr>
								<th class="colwidth-7" id="operat"><div 
										class="font-size-12 line-height-18 ">操作</div>
									<div class="font-size-9 line-height-18">Operation</div></th>
								<th class="colwidth-20">
									<div class="font-size-12 line-height-18">文件说明</div>
									<div class="font-size-9 line-height-18">File Desc</div>
								</th>
								<th class="colwidth-10">
									<div class="font-size-12 line-height-18">文件大小</div>
									<div class="font-size-9 line-height-18">File Size</div>
								</th>
								<th class="colwidth-13"><div class="font-size-12 line-height-18">上传人</div>
									<div class="font-size-9 line-height-18">Uploader</div></th>
								<th class="colwidth-13"><div class="font-size-12 line-height-18">上传时间</div>
									<div class="font-size-9 line-height-18">Upload Time</div></th>
							</tr>
						</thead>
						<tbody id="filelist">
                                       
						</tbody>
					</table>
					</div>
					</div>
					</div>
				</form>
				</div>
					  <div class="pull-right ">
						<button type="button" id="save" onclick="save()"
							class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
							<button type="button" id="save" onclick="xf()"
							class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">下发</div>
							<div class="font-size-9">Send</div>
						</button>
						<button type="button" onclick="back()"
								class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">返回</div>
								<div class="font-size-9">Back</div>
						</button>
					</div>
				
				<div class="clearfix"></div>
				</div>
			</div>
	<div class="modal fade" id="departmentModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 750px !important;">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="UserMultiModalBody">
						<div class="panel panel-primary">
							<div class="panel-heading  padding-top-3 padding-bottom-1">
								<div class="font-size-16 line-height-18">责任单位</div>
								<div class="font-size-9 ">Response Unit</div>
							</div>
							<div class="panel-body padding-top-0 padding-bottom-0">
								<div class="col-lg-12 col-xs-12">
									
										<div
											class=" pull-right padding-left-0 padding-right-0 margin-top-10">
											<div
												class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
												<input type="text" class="form-control "placeholder='部门名称'
													id="dprtname" /> 
											</div>
										
										 <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button type="button" onclick="departmentModal.search()"
											class=" btn btn-primary padding-top-1 padding-bottom-1">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
										</button>
										</div>
										</div>
										<div class="clearfix"></div>
									
									<!-- start:table -->
									<div class="margin-top-10 ">
										<div class="overflow-auto">
											<table
												class="table table-bordered table-striped table-hover text-left"
												style="">
												<thead>
													<tr>
														<th>
															<div class="font-size-12 notwrap">序号</div>
															<div class="font-size-9 notwrap">No.</div>
														</th>
														<th>
														<div class="important">
															<div class="font-size-12 notwrap">部门名称</div>
															<div class="font-size-9 notwrap">Department</div>
															</div>
														</th>
														<th>
															<div class="font-size-12 notwrap">组织机构</div>
															<div class="font-size-9 notwrap">Organization</div>
														</th>
													</tr>
												</thead>
												<tbody id="um_dprtmentlist">

												</tbody>
											</table>
											<div class=" col-xs-12  text-center "
												style="margin-top: 0px; margin-bottom: 0px;"
												id="um_pagination"></div>
										</div>
									</div>									
									<!-- end:table -->
									<div class="text-right margin-top-10 margin-bottom-10">
										<button type="button" onclick="departmentModal.setUser()"
											class="btn btn-primary padding-top-1 padding-bottom-1">
											<div class="font-size-12">确定</div>
											<div class="font-size-9">Confirm</div>
										</button>
										<button type="button"
											class="btn btn-primary padding-top-1 padding-bottom-1"
											data-dismiss="modal">
											<div class="font-size-12">关闭</div>
											<div class="font-size-9">Close</div>
										</button>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/selfquality/selfquality_add.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/selfquality/department.js"></script>
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>