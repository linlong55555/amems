<%@ page contentType="text/html; charset=utf-8" language="java"

	import="java.sql.*" errorPage=""%>
<%-- <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<%@ include file="/WEB-INF/views/open_win/import.jsp"%> 

<style>
.bootstrap-tagsinput {
  width: 100% !important;
}
</style>
<script>

var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>

</head>
<body>

	<input type="hidden" id="zzjg" value="${user.jgdm}" />
	<div class="clearfix"></div>
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<div  class='searchContent margin-top-0 row-height' >
				<div class="col-xs-2 col-md-3 padding-left-0 form-group">
					<button class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission " permissioncode='sys:contactperson:manage:01'
						type="button" onclick="addBaseMaintenance();" >
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</button>
					<button type="button"
						class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode='sys:contactperson:manage:04'
						onclick="showImportModal()">
						<div class="font-size-12">导入</div>
						<div class="font-size-9">Import</div>
					</button>
					<button type="button"
						class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode='sys:contactperson:manage:export' onclick="exportExcel()">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</button>
				
				</div>
				<div class=" pull-right padding-left-0 padding-right-0 form-group">
					<div
						class=" pull-left padding-left-5 padding-right-0" style="width: 280px;">					
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
					<div class=" pull-right padding-left-10 padding-right-0">
						<div
							class=" pull-left padding-left-0 padding-right-0" style="width:250px;">
							<input type="text" class="form-control " id="keyword_search" placeholder="联系人/职位" />
						</div>
						<div class=" pull-right padding-left-5 padding-right-0 ">
							<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 resizeHeight" onclick="searchBaseMaintenance();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
							</button>
						</div>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
				<div class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height" style="overflow-x: auto">
					<table  id="jrsq"
						class="table table-thin table-bordered table-striped table-hover table-set">
						<thead>
							<tr>
								<th class="sort colwidth-7" >
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class=" colwidth-15 sorting" name="column_lxr" onclick="orderBy('lxr',this)">
								<div class="important">
								<div class="font-size-12 line-height-18">联系人</div>
									<div class="font-size-9 line-height-18">Contact</div></div>
								</th>
								<th class=" colwidth-15 sorting" name="column_gysmc" onclick="orderBy('gysmc',this)">
										<div class="font-size-12 line-height-18">供应商</div>
										<div class="font-size-9 line-height-18">Supplier</div>
								</th>
								<th class=" colwidth-15 sorting" name="column_zw" onclick="orderBy('zw',this)">
									<div class="important">
										<div class="font-size-12 line-height-18">职位</div>
										<div class="font-size-9 line-height-18">Position</div>
									</div>
								</th>
								<th class=" colwidth-15 sorting" name="column_sj" onclick="orderBy('sj',this)">
										<div class="font-size-12 line-height-18">手机</div>
										<div class="font-size-9 line-height-18">Phone</div>
								</th>
								<th class=" colwidth-15 sorting" name="column_zj" onclick="orderBy('cz',this)">
									<div class="font-size-12 line-height-18">座机</div>
									<div class="font-size-9 line-height-18">Tel</div>
								</th>
								<th class=" colwidth-15 sorting" name="column_cz" onclick="orderBy('yxdz',this)">
									<div class="font-size-12 line-height-18">传真</div>
									<div class="font-size-9 line-height-18">Fax</div>
								</th>
								<th class=" colwidth-15 sorting" name="column_yxdz" onclick="orderBy('yxdz',this)">
									<div class="font-size-12 line-height-18">邮箱</div>
									<div class="font-size-9 line-height-18">E-mail</div>
								</th>
								<th class=" colwidth-15 sorting" name="column_qq" onclick="orderBy('qq',this)">
									<div class="font-size-12 line-height-18">QQ</div>
									<div class="font-size-9 line-height-18">QQ</div>
								</th>
								<th class=" colwidth-15 sorting" name="column_wx" onclick="orderBy('wx',this)">
									<div class="font-size-12 line-height-18">微信</div>
									<div class="font-size-9 line-height-18">Wechat</div>
								</th>
								<th class=" colwidth-15 sorting" name="column_bz" onclick="orderBy('bz',this)">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</th>
								<th class="sort colwidth-15 sorting" name="column_realname" onclick="orderBy('realname',this)">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="sort colwidth-15 sorting" name="column_whsj" onclick="orderBy('whsj',this)"><div
										class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div></th>
								<th class="colwidth-15" ><div
										class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div></th>
							</tr>
						</thead>
						<tbody id="list">
						</tbody>
					</table>
				</div>
				
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
				
				</div>
			</div>
		</div>
		
				<!-------添加模态框 start-------->
	<input  id="id" type="hidden" name="id" value="" />
	<div class="modal fade" id="BaseMaintenanceModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog" style="width: 700px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="xlhExistModalBody">
					<div class="panel-body">
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0">
							<input type="hidden" value="" name="dpt" class="form-control " id="dpt">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="font-size-16 line-height-18">联系人维护</div>
									<div class="font-size-9 ">Contact Maintenance</div>
								</div>
								<form id='form1'>
								<div class="panel-body">									
									<div class="col-lg-6 col-sm-6 col-xs-12 margin-top-0 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12"><span style="color: red">*</span>联系人</div>
											<div class="font-size-9">Contact</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-4 padding-right-0">
											<input type="text"  name="lxr" class="form-control " maxlength="100" id="lxr">
										</div>
									</div>
									<div class="col-lg-6 col-sm-6 col-xs-12 margin-top-0 padding-left-0 padding-right-0 form-group">
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">供应商</div>
											<div class="font-size-9 line-height-18">Supplier</div>
										</label>
										<div class="padding-left-8 padding-right-0  input-group" style="padding-left:15px !important" >
											<input type="text" class="form-control"  name="gysmc" maxlength="100" id="gysmc" />
											
											<span class="input-group-btn" id="account_select_btn">
												<button type="button" onclick="openAccountSelect();" class="btn btn-primary">
													<i class="icon-search" ></i>
												</button>
											</span>
											<span id="account_untie_btn" class="input-group-btn">
												<button type="button" onclick="accountuntie();" class="btn btn-primary">
												<i class="icon-unlink" ></i>
												</button>
											</span>
											<input type="hidden" class="form-control" name="csid" id="csid" />
										</div>
									</div>
									<div class="clearfix"></div>
									<div class="col-lg-6 col-sm-6 col-xs-12 margin-top-0 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">职位</div>
											<div class="font-size-9">Position</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-4 padding-right-0">
											<input type="text" name="zw" class="form-control " maxlength="100" id="zw">
										</div>
									</div>
									<div class="col-lg-6 col-sm-6 col-xs-12 margin-top-0 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">手机</div>
											<div class="font-size-9">Phone</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-4 padding-right-0">
											<input type="text"  name="sj" class="form-control " maxlength="11" id="sj">
										</div>
									</div>
									<div class="clearfix"></div>
									<div class="col-lg-6 col-sm-6 col-xs-12 margin-top-0 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">座机</div>
											<div class="font-size-9">Tel</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-4 padding-right-0">
											<input type="text"  name="zj" class="form-control " maxlength="15" id="zj">
										</div>
									</div>
									<div class="col-lg-6 col-sm-6 col-xs-12 margin-top-0 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">传真</div>
											<div class="font-size-9">Fax</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-4 padding-right-0">
											<input type="text" name="cz" class="form-control " maxlength="15" id="cz">
										</div>
									</div>
									<div class="clearfix"></div>
									<div class="col-lg-6 col-sm-6 col-xs-12 margin-top-0 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">邮箱</div>
											<div class="font-size-9">E-mail</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-4 padding-right-0">
											<input type="text" name="yxdz" class="form-control " maxlength="35" id="yxdz">
										</div>
									</div>
									<div class="col-lg-6 col-sm-6 col-xs-12 margin-top-0 padding-left-0 padding-right-0">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">QQ</div>
											<div class="font-size-9">QQ</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-4 padding-right-0">
											<input type="text" name="qq" class="form-control " maxlength="11" id="qq">
										</div>
									</div>
									<div class="clearfix"></div>
									<div class="col-lg-6 col-sm-6 col-xs-12 margin-top-0 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">微信</div>
											<div class="font-size-9">Wechat</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-4 padding-right-0">
											<input type="text"  name="wx" class="form-control " maxlength="15" id="wx">
										</div>
									</div>
									<div class="clearfix"></div>
									<div class="col-lg-12 col-sm-12 col-xs-12 margin-top-0 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-2 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">备注</div>
											<div class="font-size-9">Remark</div></label>
										<div class="col-lg-10 col-sm-10 col-xs-9 padding-left-4 padding-right-0">
											<textarea type="text"  name="bz" class="form-control " maxlength="300" id="bz">
											</textarea>
										</div>
									</div>
									
									
									<div class="pull-right padding-top-10 margin-top-5">
										<button type="button" onclick="saveBaseMaintenanceData()"
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
								</for
							</div>
						</div>
					</div>
				</div>			
		</div>
	</div>
</div>
	</div>

	
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
 
<%@ include file="/WEB-INF/views/open_win/aerialmaterialfirm_select.jsp"%> 
	<script type="text/javascript"
		src="${ctx }/static/js/thjw/sys/contactperson/contactperson_main.js"></script>	
</body>
</html>