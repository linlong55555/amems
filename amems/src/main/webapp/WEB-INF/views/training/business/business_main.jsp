<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Home</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%><style type="text/css">
.bootstrap-tagsinput {width: 100% !important;}
</style>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body >
<input type="hidden" id="zddwid" value="${user.bmdm}" />
<input type="hidden" id="gbrid" value="${sessionScope.user.id}" />
<input type="hidden" id="username" value="${sessionScope.user.username}" />
<input type="hidden" id="realname" value="${sessionScope.user.realname}" />
		<div class="page-content table-table-type" id='business_main_pageContent'>
			<div class="panel panel-primary" >
				<div class="panel-heading  "> 
				<div id="NavigationBar"></div>
				</div>
				<div class="panel-body col-xs-12 padding-bottom-0">
				<div class='searchContent row-height' >
				<div class="col-xs-3 padding-left-0">
					<a href="javascript:add();" permissioncode='training:business:manage:01'  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
					<a href="javascript:showImportModal();" permissioncode='training:business:manage:06' type="button" class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission">
						<div class="font-size-12">导入</div>
						<div class="font-size-9">Export</div>
					</a>
					<a href="javascript:exportExcel();" type="button" class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 checkPermission" permissioncode='training:business:manage:07'>
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a>
				</div>
				<div class=" pull-right padding-left-0 padding-right-0 form-group " >
				<div class="pull-left">
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</div>
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="padding-left-8 pull-left" style="width: 230px; margin-right:5px;">
							   <select id="dprtcode" class="form-control ">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
							</div>
						</div>
                    </div>
				
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='岗位编号/岗位名称/备注' id="keyword"
							class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button"
							class=" btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchFjgzRecord();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
						</button>
                    </div> 
				</div>

				<div class="clearfix"></div>
				</div>

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" id="tableId"
					style="overflow-x: auto;">
					<table
						class="table table-thin table-bordered   table-set" id="fjgz_record_sheet_table">
						<thead>
							<tr>
								<th class="colwidth-5 ">
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-10 sorting"  onclick="orderBy('dgbh')" id="dgbh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">岗位编号</div>
										<div class="font-size-9 line-height-18">Business No.</div>
									</div>
								</th>
								<th class="colwidth-15 sorting"  onclick="orderBy('dgmc')" id="dgmc_order">
									<div class="important">
										<div class="font-size-12 line-height-18">岗位名称</div>
										<div class="font-size-9 line-height-18">Business Name</div>
									</div>
								</th>
								<th class="colwidth-15 sorting"  onclick="orderBy('bz')" id="bz_order">
									<div class="important">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</div>
								</th>
								<th class="colwidth-30">
									<div class="font-size-12 line-height-18">人员</div>
									<div class="font-size-9 line-height-18">Personnel</div>
								</th>
									<th class="sorting colwidth-13" onclick="orderBy('username')" id="username_order">
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintianer</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('whsj')" id="whsj_order"><div
										class="font-size-12 line-height-18">维护时间</div>
								<div class="font-size-9 line-height-18">Maintenance Time</div></th>
								<th class="colwidth-15"><div
										class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div></th>
							</tr>
						</thead>
						<tbody id="fjgzjk_list">

						</tbody>
					</table>
				</div>
				<div class=" col-xs-12  text-center page-height padding-left-0 padding-right-0"  id="fjgzjk_pagination"></div>
				<div class="clearfix"></div>
            <div id='dcgzcl_div' class='bottom_hidden_content' style='display:none;'>
             <%@ include file="/WEB-INF/views/training/business/businesspersonnel_main.jsp"%> 
            </div>
			</div>
			<div class="clearfix"></div>
			</div>
</div>

	<input type="hidden" class="form-control " id="id" name="id" />
	<!-- start新增修改提示框 -->
	<div class="modal fade" id="alertModaladdupdate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:50%;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18" id="accredit"></div>
							<div class="font-size-9 " id="accreditrec"></div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
							<form id="form1">
							<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 margin-top-10 padding-right-0  form-group">
								<label class="pull-left col-lg-4 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>岗位编号</div>
									<div class="font-size-9">Business No.</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control " maxlength="50" id="dgbh" name="dgbh" />
									<input type="hidden" class="form-control " id="businessid" name="businessid" />
								</div>
							</div>
							<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 margin-top-10 padding-right-0  form-group">
								<label class="pull-left col-lg-4 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>岗位名称</div>
									<div class="font-size-9">Business Name</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control " maxlength="50" id="dgmc" name="dgmc" />
								</div>
							</div>
								
							<div class="clearfix"></div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<label class="pull-left col-lg-2 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">备注</div>
									<div class="font-size-9">Remark</div>
								</label>
								<div class="col-lg-10 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
									<textarea class="form-control" id="bz"   maxlength="300"></textarea>
								</div>
							</div>
							
							<div class="clearfix"></div>
							
					     	<div class="text-center margin-top-0 padding-buttom-10 ">
	                     		<button type="button" class="pull-right btn btn-primary padding-top-1 margin-right-0 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button> 
								<button id="baocuns" type="button" class="pull-right btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 margin-bottom-10" onclick="saveUpdate()">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
		                   		</button>
                    		</div>
							</form>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- end新增修改提示框-->

<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/thjw/training/business/business_main.js"></script>
</body>
</html>