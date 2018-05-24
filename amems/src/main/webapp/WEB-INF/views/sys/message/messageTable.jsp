<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css" type="text/css">
<script type="text/javascript" src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>消息</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
.bootstrap-tagsinput { width: 100% !important;}
</style>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
	<input type="hidden" id="whbmid" value="${user.bmdm}" />
	<input type="hidden" id="whrid" value="${sessionScope.user.id}" />
	<input type="hidden" id="zzjg" value="${user.jgdm}" />
	<div class="page-content">
		<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>		
			<input type="hidden" id="adjustHeight" value="10">
			<div class="panel-body">
				<div class="col-xs-1 col-lg-1 padding-left-0">
					<button
						class="btn btn-primary padding-top-1 padding-bottom-1 row-height checkPermission"
						permissioncode='sys:message:main:01' type="button" onclick="add();">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</button>
				</div>
				<div class=" pull-right padding-left-0 padding-right-0">
				<div class="pull-left">
					<div class="pull-left text-right padding-left-0 padding-right-0"style="width:300px">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-10 padding-right-0"><div
								class="font-size-12">发布日期</div>
							<div class="font-size-9">Release Date</div></span>
						<div
							class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly="readonly"
								id="fbsj" />
						</div>
					</div>
				</div>
				<div class="pull-left" style="width:200px">
					<div class="pull-left text-right padding-left-0 padding-right-0 margin-left-5"style="width:190px">
						<span
							class="pull-left col-xs-2 col-sm-2 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">状态</div>
							<div class="font-size-9">State</div></span>
						<div
							class=" col-xs-10 col-sm-10 padding-left-8 padding-right-0">
							<select id="zt" class="form-control ">
								<option value="">显示全部All</option>
								<option value="0">保存</option>
								<option value="1">发布</option>
								<option value="9">关闭</option>
							</select>
						</div>
					</div>
				</div>
					<div
						class=" pull-left padding-left-0 padding-right-0 margin-left-5" style="width: 250px;">
						<input type="text" class="form-control " id="keyword_search"
							placeholder="标题/内容/维护人" />
					</div>
					<div class=" pull-right padding-left-5 padding-right-0 resizeHeight">
						<button id="messageTableMainSearch" type="button"
							class=" btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchRecord();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
						</button>
						&nbsp;
						<button type="button"
							class="btn btn-primary dropdown-toggle pull-right padding-top-1 padding-bottom-1 resizeHeight "
							id="btn" onclick="more()">
							<div class="pull-left text-center">
								<div class="font-size-12">更多</div>
								<div class="font-size-9">More</div>
							</div>
							<div class="pull-left padding-top-5">
								&nbsp;<i class="icon-caret-down font-size-15" id="icon"></i>
							</div>
						</button>
					</div>
				</div>
				<div
					class="col-xs-12 col-sm-12 col-lg-12 triangle  padding-top-10 margin-top-10 display-none border-cccccc search-height"
					id="divSearch">
					<div class="col-xs-12 col-lg-3 col-sm-6 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">紧急度</div>
							<div class="font-size-9">Maint mode</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="jjd" class="form-control ">
								<option value="0">查询全部</option>
								<option value="1">普通</option>
								<option value="9">紧急</option>
							</select>
						</div>
					</div>
					<div
						class="col-xs-12 col-lg-3 col-sm-6 padding-left-0 padding-right-0 margin-bottom-10">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control ">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-lg-2 text-right  pull-right padding-right-0"
						style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div
					class="col-xs-12 text-center padding-left-0 padding-right-0 table-h "
					style="overflow-x: auto">

					<table id="maintenanceRecord_check_list_table" style="min-width:1200px"
						class="table table-thin table-bordered table-striped table-hover table-set">
						<thead>
							<tr>
								<th class="fixed-column colwidth-7" >
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class=" sorting title colwidth-25"  onclick="orderBy('bt')" id="bt_order"><div class="important">
								<div class="font-size-12 line-height-18">标题</div>
									<div class="font-size-9 line-height-18" >Title</div></div></th>
								<th class=" sorting title colwidth-25" onclick="orderBy('nr')" id="nr_order"><div class="important">
									<div class="font-size-12 line-height-18">内容</div>
									<div class="font-size-9 line-height-18">Contents</div>
								</div></th>
								<th class=" sorting colwidth-25" onclick="orderBy('zrdw')" id="zrdw_order">
										<div class="font-size-12 line-height-18">责任单位</div>
										<div class="font-size-9 line-height-18">Response Unit</div>
								</th>
								<th class="sorting colwidth-13 " onclick="orderBy('fbsj')" id="fbsj_order">
										<div class="font-size-12 line-height-18">发布时间</div>
										<div class="font-size-9 line-height-18">Release Time</div>
									</div></th>
								<th class="sorting colwidth-10" onclick="orderBy('yxq_Ks')" id="yxq_Ks_order">
									<div class="font-size-12 line-height-18">消息有效期_开始</div>
									<div class="font-size-9 line-height-18">message Start</div>
								</th>
								<th class="sorting colwidth-10" onclick="orderBy('yxq_Js')" id="yxq_Js_order">
										<div class="font-size-12 line-height-18">消息有效期_结束</div>
										<div class="font-size-9 line-height-18">message End</div>
								</th>
								<th class="sorting colwidth-10" onclick="orderBy('jjd')" id="jjd_order">
										<div class="font-size-12 line-height-18">紧急度</div>
										<div class="font-size-9 line-height-18">Urgency Degree</div>
								</th>
								<th class="sorting colwidth-7" onclick="orderBy('zt')" id="zt_order">
										<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">State</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('whrid')" id="whrid_order"><div class="important"><div class="important">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</div></th>
								<th class="sorting colwidth-13" onclick="orderBy('whsj')" id="whsj_order"><div
										class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div></th>
									<th class="sorting colwidth-13" onclick="orderBy('whbmid')" id="whbmid_order">
									<div class="font-size-12 line-height-18">维护部门</div>
									<div class="font-size-9 line-height-18">Record Dept.</div>
								</th>
								<th class="colwidth-15"><div
										class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div></th>
							</tr>
						</thead>
						<tbody id="list">
							<!------ plane列表展示 ------>

						</tbody>
					</table>
				</div>
				<div class=" col-xs-12  text-center margin-top-10 page-height" id="pagination"></div>

			</div>
		</div>
	</div>

	<!-------添加模态框 start-------->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog" style="width: 80%; height: auto">
			<div class="modal-content">
			
				<div class="modal-body padding-bottom-0" id="xlhExistModalBody">
					<div class="panel-body">
						<div
							class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="font-size-16 line-height-18">消息管理</div>
									<div class="font-size-9 ">Message</div>
								</div>
								<div class="panel-body">
								<form id="form">
								<div
										class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
										<label
											class="col-lg-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 "><div
												class="font-size-12"><span style="color: red">*</span>标题</div>
											<div class="font-size-9">Title</div></label>
										<div
											class="col-lg-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0 ">
											<textarea class="form-control" id="bt" name="bt" 
												placeholder='' maxlength="100"></textarea>
										</div>
									</div>
									
									<div
										class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-top-0 padding-right-0 form-group">
										<label
											class="col-lg-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 "><div
												class="font-size-12"><span style="color: red">*</span>内容</div>
											<div class="font-size-9">Content</div></label>
										<div
											class="col-lg-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0 ">
											<textarea class="form-control" id="nr" name="nr"
												placeholder='' maxlength="1200"></textarea>
										</div>
									</div>
									</form>
									<div 
										class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
										<label id="lab-wxrq"
											class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">开始日期</div>
											<div class="font-size-9">Start Date</div>
										</label>
										<div
											class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input type="text" class="form-control date-picker"
												maxlength="10" data-date-format="yyyy-mm-dd" id="yxqKs" name="yxqKs"
												 />
										</div>
									</div>
									<div 
										class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group">
										<label id="lab-wxrq"
											class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">结束日期</div>
											<div class="font-size-9">End Date</div>
										</label>
										<div
											class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0 margin-bottom-10">
											<input type="text" class="form-control date-picker"
												maxlength="10" data-date-format="yyyy-mm-dd" id="yxqJs" name="yxqJs"
												 />
										</div>
									</div>
									<div 
										class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group">
										<label
											class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0"><div
												class="font-size-12">紧急度</div>
											<div class="font-size-9">Urgency Degree</div></label>
										<div
											 class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<select id="jjdid" class="form-control ">
												<option value="1">普通</option>
												<option value="9">紧急</option>
											</select>
										</div>
									</div>
									<div 
										class="col-lg-12 col-sm-12 col-xs-12  padding-top-0 padding-left-0 padding-right-0 margin-bottom-10 form-group">
										<label
											class="col-lg-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">责任单位</div>
											<div class="font-size-9">Response Unit</div>
										</label>
										<div class="col-lg-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0 pull-left input-group">
											<input type="text" value="" name="zrdw" class="form-control " placeholder='' maxlength="100"
												id="zrdw">
												<span class="input-group-btn" id="wxrybtn" >
												<button type="button" class="btn btn-primary form-control" 
												 data-toggle="modal"
												onclick="openzrdw()">
												<i class="icon-search cursor-pointer"></i>
											</button></span>
										</div>									
									</div>
									
									
									<div id="wjsm"
										class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-top-0 padding-right-0">
										<label 
											class="col-lg-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 "><div
												class="font-size-12 line-height-18">
												文件说明
											</div>
											<div class="font-size-9 line-height-18">File Desc</div> </label>
										<div
											class="col-lg-5 col-sm-5 col-xs-9 padding-left-8 padding-right-0 ">
											<div id="wj"
												class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
												<div
													class="col-lg-10 col-sm-10 col-xs-10 padding-left-0 padding-right-0">
													<input type="text" id="wbwjm" name="wbwjm" placeholder='' maxlength="100"
														class="form-control ">
												</div>
												<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-2 padding-left-3 padding-right-0"></div>
											</div>
										</div>
									</div>

									<div  id="lookfile" style="overflow-x:auto;" 
										class="col-lg-12 col-sm-12 col-xs-12 margin-top-10 padding-left-0 padding-right-0">
										<table
											class="table table-thin table-bordered table-striped table-hover text-center table-set">
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
									<div class="pull-right padding-top-5">
										<button type="button" id="save" onclick="saveMessage('save')"
											class="btn btn-primary padding-top-1 padding-bottom-1">
											<div class="font-size-12">保存</div>
											<div class="font-size-9">Save</div>
										</button>
										<button type="button" id="fb" onclick="saveMessage('fb')"
												class="btn btn-primary padding-top-1 padding-bottom-1">
												<div class="font-size-12">发布</div>
												<div class="font-size-9">Release</div>
										</button>
										<button type="button"
												class="btn btn-primary padding-top-1 padding-bottom-1"
												data-dismiss="modal">
											<div class="font-size-12">关闭</div>
											<div class="font-size-9">Close</div>
										</button>
									</div>
								</div>
								<div style="display: none">
									<input  id="id" type="text" name="id" value="" />
									<input  id="dpt" type="text" name="dpt" value="" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-------修改模态框end-------->
		<div class="modal fade" id="dprtmentMultiModal" tabindex="-1" role="dialog"
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
													id="dprtname" /> <input type="hidden"
													class="form-control " id="um_state" value="1" />
											</div>
										
										 <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button name="keyCodeSearch" type="button" onclick="dprtmentMultiModal.search()"
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
									<div
										class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
										<label
											class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-12 line-height-18">责任单位</div>
											<div class="font-size-9 line-height-18">Response Unit</div>
										</label>
										<div
											class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
											<input class="form-control" id="um_selectDprtname" type="text"  />
										</div>
									</div>
									<!-- end:table -->
									<div class="text-right margin-top-10 margin-bottom-10">
										<button type="button" onclick="dprtmentMultiModal.setData()"
											class="btn btn-primary padding-top-1 padding-bottom-1">
											<div class="font-size-12">确定</div>
											<div class="font-size-9">Confirm</div>
										</button>
										<button type="button"
											class="btn btn-primary padding-top-1 padding-bottom-1"
											onclick="closemodal()">
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

	<link
		href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css"
		rel="stylesheet">
	<script
		src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
	<script type="text/javascript"
		src="${ctx }/static/js/thjw/sys/message/messageTable.js"></script>
	<script type="text/javascript"
		src="${ctx }/static/js/thjw/sys/message/dprtment.js"></script>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>