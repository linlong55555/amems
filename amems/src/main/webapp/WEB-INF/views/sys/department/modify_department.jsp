<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改组织机构</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
	.line {
	    border-bottom: 0px;
	}
</style>
</head>
<body>
	<input type="hidden" id="whbmid" value="${user.bmdm}" />
	<input type="hidden" id="whrid" value="${sessionScope.user.id}" />
	<input type="hidden" id="orgcode" value="${user.orgcode}" />
	<input type="hidden" id="supperdprtcode" value="${dprtcode}" />
	<input type="hidden" id="zzjg" value="${sessionScope.user.jgdm}" />
	<div class="page-content ">
		<input maxlength="20" type="hidden" id="isScheduler"
			value="${isScheduler }" /> <input maxlength="20" type="hidden"
			id="isDuration" value="${isDuration }" /> <input maxlength="20"
			type="hidden" id="isActualhours" value="${isActualhours }" />
	
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">	
			<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">组织机构信息</h4>
					</div>
					<div class="panel-body">
		           		<form id="form">						
								<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									    <label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18"><span style="color: red">*</span>组织机构代码</div>
										<div class="font-size-9 line-height-18">Org Code</div></label>
										<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" id="dprtcode" disabled="disabled" value="${erayFns:escapeStr(department.dprtcode) }"  name="dprtcode" class="form-control"  >
									    </div>
								</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18"><span style="color: red">*</span>组织机构名称</div>
										<div class="font-size-9 line-height-18">Org Name</div></label>
									 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
										<input type="text" id="dprtname" disabled="disabled" value="${erayFns:escapeStr(department.dprtname) }" name="dprtname" class="form-control "  >
									</div>
								</div>
							
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">组织机构类别</div>
										<div class="font-size-9 line-height-18">deptType</div></label>
									 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
										<select class='form-control' id='dprtType' name="dprtType" disabled="disabled">
									<c:forEach items="${dprtType}" var="type">
											<option value="${type}" <c:if test="${deptInfo.deptType  eq  type}">selected='selected'</c:if> >${erayFns:escapeStr(type)}</option>
										</c:forEach> 	
									</select>
								</div>
								</div>
								 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group"  >
									    <label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"  >
										<div class="font-size-12 line-height-18">排序号</div>
										<div class="font-size-9 line-height-18">Sort No.</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input class="form-control" disabled="disabled" value="${department.pxh }"  id="pxh" name="pxh" maxlength="5"/>
										</div>
								 </div>
								<div class="clearfix"></div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">联系人1</div>
										<div class="font-size-9 line-height-18">Contacts 1</div></label>
									 <div class="col-xs-8 col-lg-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="lxr1" value="${erayFns:escapeStr(deptInfo.lxr1) }" name="lxr1" class="form-control" maxlength="16"   >
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">联系电话1</div>
										<div class="font-size-9 line-height-18">Telephone 1</div></label>
									 <div class="col-xs-8 col-lg-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="lxdh1" value="${erayFns:escapeStr(deptInfo.lxdh1) }" name="lxdh1" class="form-control " maxlength="20"  >
									</div>
								</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">传真1</div>
										<div class="font-size-9 line-height-18">Fax 1</div></label>
									 <div class="col-xs-8 col-lg-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="fax1" value="${erayFns:escapeStr(deptInfo.fax1) }" name="fax1" class="form-control " maxlength="20" >
									</div>
								</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">Email1</div>
										<div class="font-size-9 line-height-18">E-mail 1</div></label>
									 <div class="col-xs-8 col-lg-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="email1" value="${deptInfo.email1 }" name="email1" class="form-control " maxlength="50" >
									</div>
								</div>
								<div class="clearfix"></div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">联系人2</div>
										<div class="font-size-9 line-height-18">Contacts 2</div></label>
									 <div class="col-xs-8 col-lg-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="lxr2" value="${erayFns:escapeStr(deptInfo.lxr2) }" name="lxr2" class="form-control " maxlength="16" >
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">联系电话2</div>
										<div class="font-size-9 line-height-18">Telephone 2</div></label>
									 <div class="col-xs-8 col-lg-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="lxdh2" value="${erayFns:escapeStr(deptInfo.lxdh2) }" name="lxdh2" class="form-control " maxlength="20" >
									</div>
								</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group"  >
									<label class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">传真2</div>
										<div class="font-size-9 line-height-18">Fax 2</div></label>
									 <div class="col-xs-8 col-lg-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="fax2" value="${erayFns:escapeStr(deptInfo.fax2) }" name="fax2" class="form-control " maxlength="20"  >
									</div>
								</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">Email2</div>
										<div class="font-size-9 line-height-18">E-mail 2</div></label>
									 <div class="col-xs-8 col-lg-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="email2" value="${erayFns:escapeStr(deptInfo.email2) }" name="email2" class="form-control "maxlength="50"  >
									</div>
								</div>
								<div class="clearfix"></div>
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-xs-4 col-lg-1 col-sm-2 text-right padding-left-0 padding-right-0" ><div
											class="font-size-12 line-height-18">地址</div>
										<div class="font-size-9 line-height-18">Address</div></label>
									 <div class="col-xs-8 col-lg-11 col-sm-10 padding-left-8 padding-right-0" >
									 	<textarea class="form-control" id="dz" name="dz"  maxlength="100">${erayFns:escapeStr(deptInfo.dz) }</textarea>
										
									</div>
								</div>
								<div class="clearfix"></div>							
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 form-group"  >
									    <label class="col-lg-1 col-sm-2 col-xs-4 text-right  text-right padding-left-1 padding-right-0 "  >
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div></label>
										<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0" >
											<input type="text" class="form-control" id="remark" name="remark" value="${erayFns:escapeStr(deptInfo.remark) }" maxlength="60"/>
										</div>
								 </div>
								 <div class="clearfix"></div>
								 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									    <label class="col-xs-4 col-lg-4 col-sm-4  text-right padding-left-0 padding-right-0"  >
										<div class="font-size-12 line-height-18">开始日期</div>
										<div class="font-size-9 line-height-18">Start Date</div></label>
										<div
											class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input type="text" class="form-control date-picker"
												maxlength="10" data-date-format="yyyy-mm-dd" id="yxqks" name="yxqks" value="${yxqks }" disabled="disabled"
												 />
										</div>
								 </div>	
								 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									    <label class="col-xs-4 col-lg-4 col-sm-4  text-right padding-left-0 padding-right-0"  >
										<div class="font-size-12 line-height-18">结束日期</div>
										<div class="font-size-9 line-height-18">End Date</div></label>
										<div
											class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input type="text" class="form-control date-picker"
												maxlength="10" data-date-format="yyyy-mm-dd" id="yxqjs" name="yxqjs" value="${yxqjs }" disabled="disabled"
												 />
										</div>
								 </div>	
								 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
									    <label class="col-xs-4 col-lg-4 col-sm-4  text-right padding-left-0 padding-right-0 "  >
										<div class="font-size-12 line-height-18">最大用户数</div>
										<div class="font-size-9 line-height-18">Max Users No.</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0">
											<input class="form-control" disabled="disabled" value="${deptInfo.zczh_max }"  id="zczh_max" name="zczh_max" maxlength="5" />
										</div>
								 </div>	
								 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group"  >
									    <label class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"  >
										<div class="font-size-12 line-height-18">最大飞机数</div>
										<div class="font-size-9 line-height-18">Max Plane No.</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0">
											<input class="form-control" disabled="disabled" value="${deptInfo.zcfj_max }"  id="zcfj_max" name="zcfj_max" maxlength="5"/>
										</div>
								 </div>	
								
							</form>											
								 	<input type="hidden" value="${department.id }" id="id"/>	
							
	<!--  						<div class=" col-sm-12 padding-left-0 padding-right-0 margin-bottom-10 ">
								<div class="text-right">
								<button
									class="btn btn-primary padding-top-1 padding-bottom-1"
									id="saveAddDprt" onclick="save()"><div
										class="font-size-12">保存</div>
									<div class="font-size-9">Save</div></button>
								<button class="btn btn-primary padding-top-1 padding-bottom-1"
									onclick="javascript:window.history.go(-1)">
									<div class="font-size-12">返回</div>
									<div class="font-size-9">Back</div>
								</button>
								</div>
							</div>-->
						</div>
					</div>
				<div class="clearfix"></div>
				<div class="panel panel-default">
					<div class="panel-heading">
							<h4 class="panel-title">部门信息</h4>
					</div>
					<div class="panel-body">
							<div class="tab-pane fade in active" id="planeLoadingList">
						<div id="treeView">
							<input type="hidden" id="current_id" />
							<div class=" feature">
								<!-- 部门信息树-->
								<div class=" col-xs-12 col-lg-3 col-sm-3" style="padding: 0 0 10px 0;" id="left_div">
									<div class="col-sm-12  padding-left-0">
										<button type="button"
											class="btn btn-default padding-top-1 padding-bottom-1"
											onclick="addXjDprtment()">
											<div class="font-size-12">新增</div>
											<div class="font-size-9">Add</div>
										</button>
										&nbsp;&nbsp;
										<button class="btn btn-default padding-top-1 padding-bottom-1"
											onclick="InvalidDprtment()">
											<div class="font-size-12">删除</div>
											<div class="font-size-9">Delete</div>
										</button>
									</div>
									<div class="clearfix"></div>
									<div id="tab_trade" class="tab-pane active col-xs-12">
										<div class="zTreeDemoBackground" style="overflow-y: auto; height: 160px;">
											<ul id="treeDemo" class="ztree" ></ul>
										</div>
									</div>
								</div>
								<div class="col-xs-12 col-lg-9 col-sm-9 padding-left-0 padding-right-0" id="right_div">
											<div class="col-sm-12 padding-left-0 padding-right-0">
												<div class="panel panel-default">
													<div
														class="panel-heading col-xs-12 margin-bottom-10 padding-right-0"
														style="padding-top: 2px !important; padding-bottom: 2px !important;">
														<div class="pull-left">
															<h3 class="panel-title padding-top-5">部门基本信息</h3>
														</div>									
													</div>
													<div class="panel-body">
													<form id="form1">
														<div
															class="col-xs-12 col-lg-12 col-sm-12  padding-left-0 padding-right-0 margin-bottom-0">
															<div
																class="col-xs-12 col-lg-4 col-sm-4 padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">上级部门代码</div>
																	<div class="font-size-9 line-height-18">Sup Dept Code</div></label>
																<div class=" col-xs-8 col-lg-8 col-sm-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="sjdprtcode"
																		name="sjdprtcode" disabled="disabled">
																</div>
																<input type="hidden" value="" id="sjid">
															</div>
															<div
																class="col-xs-12 col-lg-4 col-sm-4 padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">上级部门名称</div>
																	<div class="font-size-9 line-height-18">Sup Dept Name</div></label>
																<div class=" col-xs-8 col-lg-8 col-sm-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="sjdprtname"
																		name="sjdprtname" disabled="disabled">
																</div>
															</div>
															
																<div
																class="col-xs-12 col-lg-4 col-sm-4 padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18"><span style="color: red">*</span>部门代码
																	</div>
																	<div class="font-size-9 line-height-18">Dept Code</div></label>
																<div class=" col-xs-8 col-lg-8 col-sm-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="bmdm" disabled="disabled"
																		name="bmdm" maxlength="50">
																</div>
															</div>
														</div>
														<div class="clearfix"></div>
														<div
															class="col-xs-12 col-lg-12 col-sm-12  padding-left-0 padding-right-0 margin-bottom-0">
															<div
																class="col-xs-12 col-lg-4 col-sm-4 padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">
																		<span style="color: red">*</span>部门名称
																	</div>
																	<div class="font-size-9 line-height-18">Dept Name</div></label>
																<div class=" col-xs-8 col-lg-8 col-sm-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="bmmc" disabled="disabled"
																		name="bmmc" maxlength="16" >
																</div>
															</div>
															<div
																class="col-xs-12 col-lg-4 col-sm-4 padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">
																		排序号
																	</div>
																	<div class="font-size-9 line-height-18">Sort No.</div></label>
																<div class=" col-xs-8 col-lg-8 col-sm-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="dprtpxh"
																		name="dprtpxh" maxlength="5" >
																</div>
															</div>
															<div class="col-xs-12 col-lg-4 col-sm-4 padding-left-0 padding-right-0 form-group" id="jd">
																<label
																	class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">
																		是否基地
																	</div>
																	<div class="font-size-9 line-height-18">Yes/No Base</div></label>
																<div class=" col-xs-8 col-lg-8 col-sm-8 padding-left-8 padding-right-0">
																	<label style="margin-right: 20px;font-weight: normal"><input name="jd" type="radio" value="1" />是</label> 
																	<label style="font-weight: normal"><input name="jd" type="radio" value="0" checked/>否</label> 
																</div>
															</div>
														</div>										
														<div class="clearfix"></div>
														<div
															class="col-xs-12 col-lg-12 col-sm-12  padding-left-0 padding-right-0 margin-bottom-0">
															<label
																class="col-xs-1 text-right padding-left-0 padding-right-0"  style="width: 11.11%"><div
																	class="font-size-12 line-height-18">备注</div>
																<div class="font-size-9 line-height-18">Remark</div></label>
															<div class=" col-xs-11 padding-left-8 padding-right-0" style="width: 88.88%">																												
												<!--  			<input type="text" class="form-control" id="bz" name="bz" name="bz"  maxlength="60"/>		-->								
																<textarea rows="1" class="form-control" name="bz" style="height:45px"
																	id="bz" name="bz" maxlength="60"></textarea>
															</div>
														</div>
														</form>
													</div>
												</div>
											</div>
										</div>
								</div>
							</div>
						</div>
					</div>
					</div>
					<div class="col-sm-12 padding-left-0 padding-right-0 margin-bottom-10">
						<div class="text-right">
							<button id="main_insertOrUpdate_btn" type="button"
								class="btn btn-primary padding-top-1 padding-bottom-1" onclick="adddprtment()">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
							</button>
							<button class="btn btn-primary padding-top-1 padding-bottom-1"
									onclick="back()">
								<div class="font-size-12">返回</div>
								<div class="font-size-9">Back</div>
							</button>
					</div>
				</div>
		</div>
	</div>
</div>
		<%@ include file="/WEB-INF/views/alert.jsp"%>
		<script type="text/javascript"
			src="${ctx}/static/js/thjw/sys/department/modify_department.js"></script>
</body>
</html>

