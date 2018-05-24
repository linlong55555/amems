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
<title>自我管理管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
	<input type="hidden" id="whbmid" value="${user.bmdm}" />
	<input type="hidden" id="whrid" value="${sessionScope.user.id}" />
	<input type="hidden" id="dprtcode" value="${user.jgdm}" />
	<input type="hidden" id="lx" value="${lx}" />
	<div class="page-content">
		<div class="panel panel-primary ">
		<div class="panel-heading" id="NavigationBar"></div>	
			<div class="panel-body padding-bottom-0">
			<div  class='searchContent row-height'>
				<div class="col-xs-2 padding-left-5 form-group" id="add">
					<button
						class="btn btn-primary padding-top-1 padding-bottom-1  checkPermission"
						permissioncode='quality:selfquality:main:01' 
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
							placeholder="" />
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
							class="btn btn-primary dropdown-toggle pull-right padding-top-1 padding-bottom-1"
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
					class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0"
					id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">整改限期</div>
							<div class="font-size-9">Maint Date</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly="readonly"
								id="zgxq_search" />
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">状态</div>
							<div class="font-size-9">Maint mode</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zt_search" class="form-control ">
								<option value="">选择全部 All</option>
								<option value="1">保存</option>
								<option value="2">待处理</option>
								<option value="3">待审核</option>
								<option value="8">作废</option>
								<option value="9">关闭</option>
								<option value="10">完成</option>
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
							<select id="dprtcode_search" class="form-control ">
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
				<div
					class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height"
					style="overflow-x: auto">

					<table id="selfquality_check_list_table" style="min-width:1200px"
						class="table table-thin table-bordered  table-set">
						<thead>
							<tr>
								<th class="fixed-column colwidth-7" >
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class=" sorting colwidth-15" onclick="orderBy('dh')" id="dh_order"><div class="important">
									<div class="font-size-12 line-height-18">审查单号</div>
									<div class="font-size-9 line-height-18">Review No.</div></div>
								</th>
								<th class=" sorting colwidth-13"  onclick="orderBy('zgxq')" id="zgxq_order"><div
										class="font-size-12 line-height-18">整改限期</div>
									<div class="font-size-9 line-height-18" >Rectification Deadline</div>
								</th>
								<th class=" sorting colwidth-13" onclick="orderBy('zgzt')" id="zgzt_order"><div class="important">
										<div class="font-size-12 line-height-18">整改主题</div>
										<div class="font-size-9 line-height-18">Rectification Theme</div>
									</div>
								</th>
								<th class="sorting colwidth-20 title" onclick="orderBy('zrbmid')" id="zrbmid_order"><div class="important">
										<div class="font-size-12 line-height-18">责任部门</div>
										<div class="font-size-9 line-height-18">Responsible Dept.</div>
									</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th class="sorting colwidth-25 title" onclick="orderBy('xfrid')" id="xfrid_order"><div class="important"><div class="important">
										<div class="font-size-12 line-height-18">下发人</div>
										<div class="font-size-9 line-height-18">Hair Downer</div>
									</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('xfsj')" id="xfsj_order"><div class="important"><div class="important">
										<div class="font-size-12 line-height-18">下发时间</div>
										<div class="font-size-9 line-height-18">Release Time</div>
									</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('xfyy')" id="xfyy_order"><div
										class="font-size-12 line-height-18">下发原因</div>
									<div class="font-size-9 line-height-18">Cause</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('xffjid')" id="xffjid_order">
									<div class="font-size-12 line-height-18">下发附件</div>
									<div class="font-size-9 line-height-18">Send Attachment</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('fkrid')" id="fkrid_order">
									<div class="font-size-12 line-height-18">反馈人</div>
									<div class="font-size-9 line-height-18">Feedback People</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('fksj')" id="fksj_order">
									<div class="font-size-12 line-height-18">反馈时间</div>
									<div class="font-size-9 line-height-18">Feedback Time</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('zgsm')" id="zgsm_order">
									<div class="font-size-12 line-height-18">整改说明</div>
									<div class="font-size-9 line-height-18">Rectification Instructions</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('zgfjid')" id="zgfjid_order">
									<div class="font-size-12 line-height-18">整改附件</div>
									<div class="font-size-9 line-height-18">Rectification Attachment</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('shrid')" id="shrid_order">
									<div class="font-size-12 line-height-18">审核人</div>
									<div class="font-size-9 line-height-18">Audit People</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('shsm')" id="shsm_order">
									<div class="font-size-12 line-height-18">审核结论</div>
									<div class="font-size-9 line-height-18">Audit Conclusion</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('shsj')" id="shsj_order">
									<div class="font-size-12 line-height-18">审核时间</div>
									<div class="font-size-9 line-height-18">Audit Time</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('shfjid')" id="shfjid_order">
									<div class="font-size-12 line-height-18">审核附件</div>
									<div class="font-size-9 line-height-18">Rectification Attachment</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('gbrid')" id="gbrid_order">
									<div class="font-size-12 line-height-18">关闭人</div>
									<div class="font-size-9 line-height-18">Closer</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('gbyy')" id="gbyy_order">
									<div class="font-size-12 line-height-18">关闭原因</div>
									<div class="font-size-9 line-height-18">Shutdown Reason</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('gbsj')" id="gbsj_order">
									<div class="font-size-12 line-height-18">关闭时间</div>
									<div class="font-size-9 line-height-18">Shutdown Time</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('gbfjid')" id="gbfjid_order">
									<div class="font-size-12 line-height-18">关闭附件</div>
									<div class="font-size-9 line-height-18">Shutdown Attachment</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('whsj')" id="whsj_order">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('whbmid')" id="whbmid_order">
									<div class="font-size-12 line-height-18">维护部门</div>
									<div class="font-size-9 line-height-18">Record Dept.</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('whrid')" id="whrid_order">
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list">
							<!------ plane列

表展示 ------>

						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>

			</div>
		</div>
	</div>
	<div class="modal fade" id="closeModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria- hidden="true"  >
		<div class="modal-dialog" style="width: 80%;">
			<div class="modal-content">
			
				<div class="modal-body padding-bottom-0" id="xlhExistModalBody">
					<div class="panel-body">
						<div
							class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="font-size-16 line-height-18">质量管理关闭</div>
									<div class="font-size-9 ">Qaulity Management Close</div>
								</div>
								<div class="panel-body">
								<form id="form" >
									<div class="clearfix"></div>
									 <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 margin-bottom-10 padding-right-0 form-group">
										<label class="col-lg-1 col-sm-1 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
											<div class="font-size-12 line-height-18">关闭原因</div>
											<div class="font-size-9 line-height-18">Cause</div>
										</label>
										<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8  padding-right-0 " >
											<textarea class="form-control" id="gbyy" name="gbyy"  maxlength="1300"  ></textarea>
										</div>
									</div>
									<div class="clearfix"></div>
									 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">附件</div>
										<div class="font-size-9 line-height-18">File</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<div class='input-group' >
											<input class="form-control"  id="wbwjm" name="wbwjm" type="text" maxlength="100" />
											<span class='input-group-btn'>
										 	<div id="fileuploader"></div>
											</span>
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
									</form>
									<div class="pull-right padding-top-10">
										<button type="button" id="save" onclick="closeData()"
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
								<input type="hidden" value="" id="djid"/>
								<input type="hidden" value="" id="djdprtcode"/>
							</div>
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
	<script
		src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
	<script type="text/javascript"
		src="${ctx }/static/js/thjw/quality/selfquality/selfquality_main.js"></script>
	<script type="text/javascript"
		src="${ctx}/static/js/thjw/open_win/user.js"></script>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>