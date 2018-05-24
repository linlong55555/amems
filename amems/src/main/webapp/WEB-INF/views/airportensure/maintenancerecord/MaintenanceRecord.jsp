<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>设施维修</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
.new_style {
    position: relative;
    border-collapse: separate;
    }
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
	<input type="hidden" id="supercode" value="${superdprtcode}" />
	<input type="hidden" id="orgcode" value="${user.orgcode}" />
	<div class="page-content">
		<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>		
			<input type="hidden" id="adjustHeight" value="10">
			<div class="panel-body padding-bottom-0">
			<div class='searchContent margin-top-0 row-height'>
				<div class="col-xs-2 padding-left-0 form-group">
					<button
						class="btn btn-primary padding-top-1 padding-bottom-1 row-height checkPermission"
						permissioncode='airportensure:maintenancerecord:main:01'
						type="button" onclick="add();">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</button>
				</div>
				<div class=" pull-right padding-left-0 padding-right-0 form-group">

					<div
						class="col-sm-5 col-lg-5 pull-left padding-left-0 padding-right-0"
						style="margin-left: 20px; width: 200px">
						<input type="text" class="form-control " id="keyword_search"
							placeholder="维修单编号/维修人员/维修设备/维护人/维修内容" />
					</div>
					<div class=" pull-right padding-left-5 padding-right-0 ">
						<button type="button"
							class=" btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchRecord();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
						</button>
						&nbsp;
						<button type="button"
							class="btn btn-primary dropdown-toggle pull-right padding-top-1 padding-bottom-1 resizeHeight"
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
				<div class="clearfix"></div>
				<div
					class="col-xs-12 col-sm-12 col-lg-12 triangle  padding-top-10 margin-top-0 margin-bottom-10 display-none border-cccccc"
					id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">维修日期</div>
							<div class="font-size-9">Maint Date</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly="readonly"
								id="wxsj" />
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">维修方式</div>
							<div class="font-size-9">Maint mode</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="wxfs" class="form-control ">
								<option value="0">查询全部</option>
								<option value="1">自行维修</option>
								<option value="2">外包维修</option>
							</select>
						</div>
					</div>

					<div
						class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
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
					<div class="col-lg-2  text-right  pull-right padding-right-0 margin-bottom-10">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class="clearfix"></div>
				</div>
				<div class="col-lg-12 col-md-12 padding-left-0  padding-right-0 margin-top-0 table-h table-set" style="overflow-x: auto;">

					<table id="maintenanceRecord_check_list_table" style="min-width:1200px"
						class="table table-thin table-bordered table-striped table-hover table-set">
						<thead>
							<tr>
								<th class="fixed-column colwidth-7" >
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class=" sorting colwidth-9"  onclick="orderBy('wxsj')" id="wxsj_order"><div
										class="font-size-12 line-height-18">维修日期</div>
									<div class="font-size-9 line-height-18" >Maint Date</div></th>
								<th class=" sorting colwidth-13" onclick="orderBy('wxdbh')" id="wxdbh_order"><div class="important">
									<div class="font-size-12 line-height-18">维修单编号</div>
									<div class="font-size-9 line-height-18">Maint No.</div></div>
								</th>
								<th class=" sorting colwidth-13" onclick="orderBy('wxrymc')" id="wxrymc_order"><div class="important">
										<div class="font-size-12 line-height-18">维修人员</div>
										<div class="font-size-9 line-height-18">Maint man</div>
									</div></th>
								<th class="sorting colwidth-20 title" onclick="orderBy('wxdx')" id="wxdx_order"><div class="important">
										<div class="font-size-12 line-height-18">维修设备</div>
										<div class="font-size-9 line-height-18">Maint eqpt.</div>
									</div></th>
								<th class="sorting colwidth-10" onclick="orderBy('wxfs')" id="wxfs_order">
									<div class="font-size-12 line-height-18">维修方式</div>
									<div class="font-size-9 line-height-18">Maint mode</div>
								</th>
								<th class="sorting colwidth-25 title" onclick="orderBy('wxnr')" id="wxnr_order"><div class="important"><div class="important">
										<div class="font-size-12 line-height-18">维修内容</div>
										<div class="font-size-9 line-height-18">Maint content</div>
									</div></th>
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
							<!------ plane列

表展示 ------>

						</tbody>
					</table>
				</div>
				<div class=" col-xs-12  text-center margin-top-10 page-height" id="pagination"></div>

			</div>
		</div>
	</div>

	<!-------添加模态框 start-------->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria- hidden="true"  >
		<div class="modal-dialog" style="width: 80%;">
			<div class="modal-content">
			
				<div class="modal-body padding-bottom-0" id="xlhExistModalBody">
					<div class="panel-body">
						<div
							class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="font-size-16 line-height-18">维修记录单</div>
									<div class="font-size-9 ">MaintenanceRecord</div>
								</div>
								<div class="panel-body">
								<form id="form" >
									<div class="clearfix"></div>
									<div style="display:none;" id="div-wxdbh"
										class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group">
										<label
											class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-10 padding-right-0 form-group"><div
												class="font-size-12">维修单编号</div>
											<div class="font-size-9">Maint No.</div></label>
										<div
											class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
												<input type="text" class="form-control"
												 id="wxd" name="wxd" 
												readonly />
										</div>
									</div>								
									<div id="div-wxrq"
										class="col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group">
										<label id="lab-wxrq"
											class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12"><span style="color: red" id="wxrqmark">*</span>维修日期</div>
											<div class="font-size-9">Maint Date</div>
										</label>
										<div
											class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input type="text" class="form-control date-picker"
												maxlength="10" data-date-format="yyyy-mm-dd" id="wxrq" name="wxrq"
												 />
										</div>
									</div>
									<div id="div-wxfs"
										class=" col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-10 margin-bottom-0 form-group">
										<label
											class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
												class="font-size-12">维修方式</div>
											<div class="font-size-9">Maint mode</div></label>
										<div
											 class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<select id="wxfsid" class="form-control ">
												<option value="1">自行维修</option>
												<option value="2">外包维修</option>
											</select>
										</div>
									</div>
									<div id="div-wxry"
										class=" col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group">
										<label
											class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">维修人员</div>
											<div class="font-size-9">Maint man</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left input-group">
											<div class='input-group ' id="ycwxry">
												<input type="text" value="" name="wxrymc" class="form-control "
												 id="wxrymc" maxlength="16"/>
												<span class="input-group-btn" id="wxrybutton">
													<button type="button" class="btn btn-primary " id="wxrybtn"
													 data-toggle="modal"
													onclick="openWinwhrymc()">
													<i class="icon-search cursor-pointer"></i>
													</button>
												</span>
											</div>
										</div>
										<div style="display: none;">
											<input type="text" value="" name="wxryid" id="wxryid">
											<input type="text" value="" name="wxrymcs" id="wxrymcs">
										</div>
										
									</div>
									<div class="clearfix"></div>
									<div 
										class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
										<label id="lab-wxsb"
											class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 form-group"><div
												class="font-size-12"><span style="color: red" id="wxsbmark">*</span>维修设备</div>
											<div class="font-size-9">Maint eqpt.</div></label>
										<div id="div-wxsb"
											class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
											<input class="form-control" id="wxdx" name="wxdx" 
												placeholder='' maxlength="60"/>
										</div>
									</div>
									
									<div  
										class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-top-0 padding-right-0 form-group">
										<label
											class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "><div
												class="font-size-12"><span style="color: red" id="wxnrmark">*</span>维修内容</div>
											<div class="font-size-9">Maint content</div></label>
										<div
											class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
											<textarea class="form-control" id="wxnr" name="wxnr"
												placeholder='' maxlength="600"></textarea>
										</div>
									</div>
									</form>
									
									<div id="wjsm"
										class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-top-0 padding-right-0">
										<label 
											class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "><div
												class="font-size-12 line-height-18">
												文件说明
											</div>
											<div class="font-size-9 line-height-18">File Desc</div> </label>
										<div
											class="col-lg-5 col-sm-5 col-xs-8 padding-left-8 padding-right-0 ">
											<div id="wj"
												class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
												<div
													class="col-lg-10 col-sm-10 col-xs-10 padding-left-0 padding-right-0">
													<input type="text" id="wbwjm" name="wbwjm" placeholder='' maxlength="90"
														class="form-control ">
												</div>
												<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-2 padding-left-3 padding-right-0"></div>
											</div>
										</div>
									</div>

									<div id="lookfile" style="overflow-x:auto;" 
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
										<button type="button" id="save" onclick="saveMaintenanRecordData()"
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
								</div>
								<div style="display: none">
									<input  id="id" type="text" name="id" value="" />
									<input  id="dpt" type="text" name="dpt" value="" />
									<input type="text" id="wxdbh" name="wxdbh" ,value="">
								</div>
							</div>
						</div>
					</div>
				</div>
				
				
			</div>
		</div>
	</div>
	<!-------添加模态框end-------->
	<div class="modal fade" id="userModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">用户列表</div>
							<div class="font-size-9 ">User List</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
							<div class="col-lg-12 col-xs-12">
									<div
										class=" pull-right padding-left-0 padding-right-0 margin-top-10">
										<div
											class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
											<input type="text" class="form-control "placeholder='用户名称' id="u_realname_search" /> <input
												type="hidden" class="form-control " id="state" value="1" />
										</div>
									 <div class=" pull-right padding-left-5 padding-right-0 "> 
									<button name="keyCodeSearch"
									 type="button" onclick="userModal.search()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										style="float: left; margin-left: 10px;">
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
											class="table table-bordered table-striped table-hover text-center table-set"
											style="">
											<thead>
												<tr>
													<th width="50px">
														<div class="font-size-12 notwrap">选择</div>
														<div class="font-size-9 notwrap">Choice</div>
													</th>
													<th><div class="important">
														<div class="font-size-12 notwrap">用户名称</div>
														<div class="font-size-9 notwrap">User Name</div>
														</div>
													</th>
													<th>
														<div class="font-size-12 notwrap">机构部门</div>
														<div class="font-size-9 notwrap">Department</div>
													</th>
												</tr>
											</thead>
											<tbody id="userlist">

											</tbody>
										</table>
									</div>
									<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="user_pagination"></div>
								</div>
								<!-- end:table -->
								<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="userModal.setUser()"
										class="btn btn-primary padding-top-1 padding-bottom-1">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" onclick="userModal.clearUser()" id="userModal_btn_clear"
										class="btn btn-primary padding-top-1 padding-bottom-1">
										<div class="font-size-12">清空</div>
										<div class="font-size-9">Clear</div>
									</button>
									<button type="button"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										onclick="closeUserMultiModal()">
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
</div>
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<link
		href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css"
		rel="stylesheet">
	<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/user.js"></script>
	<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/js/thjw/airportensure/maintenancerecord/MaintenanceRecord.js"></script>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>