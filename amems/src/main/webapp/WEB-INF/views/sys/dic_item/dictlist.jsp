<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>数据字典管理(New)</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style>
.sjzdTable tbody tr {
	cursor: pointer;
}

.sjzdTable thead tr th {
	border: 0px;
}

.sjzdTable tbody tr td {
	padding-top: 5px;
	padding-right: 5px;
	border-left: 0px !important;
	border-right: 0px !important;
}

.list-group-item {
	cursor: pointer;
}

.list-group-item:hover {
	background: #f5f5f5;
}

.list-group-item.active {
	background: #dbe2f7;
}

.active1 {
background:#eaeaea!important;
}
</style>
</head>
<body class="page-header-fixed">
 <div class="page-content-wrapper" >
	<input maxlength="20" type="hidden" id="userId" value="" />
	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body" style="height: 536px; overflow-y: auto;">
				<div class="col-sm-3" style="border-right: 1px solid #ccc;padding-left:0px;padding-right:10px;padding-top:0px" id="seperateLine">
					<div> 
						<div class="margin-top-0">
							<div class="col-lg-12  padding-bottom-1"
								style="padding-left: 0px; padding-right: 0px">
								<span
									class=" col-lg-3 pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">组织机构</div>
									<div class="font-size-9">Organization</div>
								</span>
								<div class="col-xs-9  padding-left-8 padding-right-0">
									<input type="hidden" id="zzjgid" value="${user.jgdm}" /> <select
										id="account_dprtcode" class="form-control "
										name="account_dprtcode" onchange="changeOrganization()">
										<c:if test="${dprtcode==user.orgcode}">
											<option value="-1">公共</option>
										</c:if>
										<c:forEach items="${accessDepartments}" var="type">
											<option value="${type.id}">${erayFns:escapeStr(type.dprtname)}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div
								class='col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-10' >
								<table class="table table-thin table-set" id="listTbale">
									<thead>
										<tr>
										<th class='colwidth-7'>
											<div class="font-size-12 line-height-18"  align="left">数据类型</div>
											<div class="font-size-9 line-height-18" align="left">Data Type</div>
										</th>
										<th class='colwidth-3'>
										
										</th>
										</tr>
									</thead>
									<tbody id="list">
									
									</tbody>
								</table>
								<!-- <div >
									<div style='border: 1px solid #ddd; border-bottom: 0px; margin-bottom: 0px; padding-left: 15px;padding-top:3px;padding-bottom:3px;'>									
									<div class="font-size-12 line-height-18">
									<span style="font-weight: bold;">数据类型</span>
									</div>	          
						            <div class="font-size-9 line-height-18">Data Type</div>						            
						            </div>
						            
						            
						            <div style='border: 1px solid #ddd; border-bottom: 0px; margin-bottom: 0px; padding-left: 15px;padding-top:3px;padding-bottom:3px;'>									
									<div class="font-size-12 line-height-18">
									<span style="font-weight: bold;">数据类型</span>
									</div>	          
						            <div class="font-size-9 line-height-18">Data Type</div>						            
						            </div>
						            
									<ul class="list-group margin-bottom-0" style='margin-top: 0px;height:83.5%;overflow: auto;' id="list" >
									</ul>
								</div> -->
							</div>
						</div>
					</div>
				</div>

				<div id="right_div1" class="col-sm-9" style="padding-left:10px;padding-right:0px">
					<%@ include file="/WEB-INF/views/sys/dic_item/dictitemlist.jsp"%>
				</div>
			</div>
		</div>
	</div>
	<!-- 新增字典项明细模态框 -->
	<div class="modal fade in modal-new" id="ModalAddDicItem" tabindex="-1"
		role="dialog" aria-labelledby="open_win_maintenance_project_modal"
		aria-hidden="true" data-backdrop='static' data-keyboard=false>
		<div class="modal-dialog" style="width: 420px !important;">
			<div class="modal-content">
				<!-- header放在这里 -->

				<div class="modal-header modal-header-new">
					<h4 class="modal-title">
						<div class='pull-left'>
							<div class="font-size-14" id="chinaHead">新增字典项明细</div>
							<div class="font-size-12" id="englishHead">Add DicItem</div>
						</div>
						<div class="pull-right">
							<button type="button" class="icon-remove modal-close"
								data-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class='clearfix'></div>
					</h4>
				</div>

				<div class="modal-body padding-bottom-0 margin-top-0"
					id="alertModalUserBody">
					<div class="col-xs-12 margin-top-8">
						<div class="input-group-border">
							<div class="panel-body padding-top-0 padding-bottom-0">
								<div>
									<form id="form">
										<div
											class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
											<div
												class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
												<label
													class="col-xs-2 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">
														<span style="color: red">*</span>值
													</div>
													<div class="font-size-9 line-height-18">Value</div>
												</label>
												<div
													class=" col-xs-10 padding-left-8 padding-right-0 form-group">
													<input type="text" id="sz" name="sz" class="form-control"
														maxlength="16">
												</div>
											</div>

											<div
												class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
												<label
													class="col-xs-2 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">
														<span style="color: red"></span>描述
													</div>
													<div class="font-size-9 line-height-18">Description</div>
												</label>
												<div
													class=" col-xs-10 padding-left-8 padding-right-0 form-group">
													<input type="text" id="mc" name="mc" class="form-control"
														maxlength="100">
												</div>
											</div>

											<div
												class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
												<label
													class="col-xs-2 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">
														<span style="color: red"></span>项次
													</div>
													<div class="font-size-9 line-height-18">Item No.</div>
												</label>
												<div
													class=" col-xs-10 padding-left-8 padding-right-0 form-group">
													<input type="text" id="xc" name="xc" class="form-control"
														maxlength="10">
												</div>
											</div>

										</div>
									</form>
								</div>
							</div>
						</div>

					</div>
					<div class="clearfix"></div>
				</div>

				<div class="modal-footer ">
					<div class="col-xs-12 padding-leftright-8">
						<div class="input-group">
							<span class="input-group-addon modalfootertip"> <i
								class='glyphicon glyphicon-info-sign alert-info'></i>
							<p class="alert-info-message" ></p>
							</span> <span class="input-group-addon modalfooterbtn">
								<button type="button" id="save" onclick="saveDicItem();"
									class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
								<button type="button" data-dismiss="modal"
									class="btn btn-primary padding-top-1 padding-bottom-1 margin-right-5">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
							</span>
						</div>
					</div>
				</div>


			</div>
		</div>
	</div>

	<!-------新增字典项明细模态框end-------->
	<!-- 修改字典项明细模态框strat -->

	<div class="modal fade in modal-new" id="ModalEditDicItem" tabindex="-1"
		role="dialog" aria-labelledby="open_win_maintenance_project_modal"
		aria-hidden="true" data-backdrop='static' data-keyboard=false>
		<div class="modal-dialog" style="width: 420px !important;">
			<div class="modal-content">
				<!-- header放在这里 -->

				<div class="modal-header modal-header-new">
					<h4 class="modal-title">
						<div class='pull-left'>
							<div class="font-size-14" id="chinaHead">修改字典项明细</div>
							<div class="font-size-12" id="englishHead">Edit DicItem</div>
						</div>
						<div class="pull-right">
							<button type="button" class="icon-remove modal-close"
								data-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class='clearfix'></div>
					</h4>
				</div>

				<div class="modal-body padding-bottom-0 margin-top-0"
					id="alertModalUserBody">
					<div class="col-xs-12 margin-top-8">
						<div class="input-group-border">
							<div class="panel-body padding-top-0 padding-bottom-0">
								<div>
									<form id="form2">
										<div
											class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
											<div
												class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
												<label
													class="col-xs-2 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">
														<span style="color: red">*</span>值
													</div>
													<div class="font-size-9 line-height-18">Value</div>
												</label>
												<div
													class=" col-xs-10 padding-left-8 padding-right-0 form-group">
													<input type="text" id="sz2" name="sz2" class="form-control"
														maxlength="16"> <input type="hidden" id="temp_sz" />
													<input type="hidden" id="id" /> <input type="hidden"
														id="tid" />
												</div>
											</div>
											<div
												class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
												<label
													class="col-xs-2 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">
														<span style="color: red"></span>描述
													</div>
													<div class="font-size-9 line-height-18">Description</div>
												</label>
												<div
													class=" col-xs-10 padding-left-8 padding-right-0 form-group">
													<input type="text" id="mc2" name="mc2" class="form-control"
														maxlength="100"> <input type="hidden" id="temp_mc" />
												</div>
											</div>
											<div
												class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
												<label
													class="col-xs-2 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">
														<span style="color: red"></span>项次
													</div>
													<div class="font-size-9 line-height-18">Item No.</div>
												</label>
												<div
													class=" col-xs-10 padding-left-8 padding-right-0 form-group">
													<input type="text" id="xc2" name="xc2"
														class=" form-control" maxlength="10"> <input
														type="hidden" id="temp_xc" />
												</div>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
				
				<div class="modal-footer ">
					<div class="col-xs-12 padding-leftright-8">
						<div class="input-group">
							<span class="input-group-addon modalfootertip"> <i
								class='glyphicon glyphicon-info-sign alert-info'></i>
							<p class="alert-info-message" ></p>
							</span> <span class="input-group-addon modalfooterbtn">
								<button type="button" id="save" onclick="editDictItem();"
									class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
								<button type="button" data-dismiss="modal"
									class="btn btn-primary padding-top-1 padding-bottom-1 margin-right-5">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div><!-- wrapper -->

	<!-------修改字典项明细模态框end-------->
	<script type="text/javascript"
		src="${ctx}/static/js/thjw/sys/dictlist.js"></script>
</body>
</html>
