<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增组织机构</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
	<input type="hidden" id="whbmid" value="${user.bmdm}" />
	<input type="hidden" id="whrid" value="${sessionScope.user.id}" />
	<input type="hidden" id="orgcode" value="${user.orgcode}" />
	<input type="hidden" id="supperdprtcode" value="${dprtcode}" />
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
										<input type="text" id="dprtcode" name="dprtcode" class="form-control" maxlength="50" >
									    </div>
								</div>							
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18"><span style="color: red">*</span>组织机构名称</div>
										<div class="font-size-9 line-height-18">Org Name</div></label>
									 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
										<input type="text" id="dprtname" name="dprtname" class="form-control" maxlength="16" >
									</div>
								</div>
							
							<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">组织机构类别</div>
										<div class="font-size-9 line-height-18">deptType</div></label>
									 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
										<select class='form-control' id='dprtType' name="dprtType">
									  	<c:forEach items="${dprtType}" var="type">
											<option value="${type}">${erayFns:escapeStr(type)}</option>
										</c:forEach>  
										</select>
									</div>
								</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group"  >
									    <label class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"  >
										<div class="font-size-12 line-height-18">排序号</div>
										<div class="font-size-9 line-height-18">Sort No.</div></label>
										<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
											<input class="form-control" disabled="disabled" value=""  id="pxh" name="pxh" maxlength="5" />
										</div>
								 </div>
								<div class="clearfix"></div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">联系人1</div>
										<div class="font-size-9 line-height-18">Contacts 1</div></label>
									 <div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="lxr1" name="lxr1" class="form-control " maxlength="16" >
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">联系电话1</div>
										<div class="font-size-9 line-height-18">Telephone 1</div></label>
									 <div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="lxdh1" name="lxdh1" class="form-control " maxlength="20" >
									</div>
								</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">传真1</div>
										<div class="font-size-9 line-height-18">Fax 1</div></label>
									 <div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="fax1" name="fax1" class="form-control " maxlength="20" >
									</div>
								</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">Email1</div>
										<div class="font-size-9 line-height-18">E-mail 1</div></label>
									 <div class="col-lg-8 col-xs-8 col-sm-8  padding-left-8 padding-right-0" >
										<input type="text" id="email1" name="email1" class="form-control " maxlength="50" >
									</div>
								</div>
								<div class="clearfix"></div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">联系人2</div>
										<div class="font-size-9 line-height-18">Contacts 2</div></label>
									 <div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="lxr2" name="lxr2" class="form-control " maxlength="16" >
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">联系电话2</div>
										<div class="font-size-9 line-height-18">Telephone 2</div></label>
									 <div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="lxdh2" name="lxdh2" class="form-control " maxlength="20" >
									</div>
								</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">传真2</div>
										<div class="font-size-9 line-height-18">Fax 2</div></label>
									 <div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="fax2" name="fax2" class="form-control" maxlength="20" >
									</div>
								</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">Email2</div>
										<div class="font-size-9 line-height-18">E-mail 2</div></label>
									 <div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0" >
										<input type="text" id="email2" name="email2" class="form-control " maxlength="50" >
									</div>
								</div>
								<div class="clearfix"></div>
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-xs-4 col-lg-1 col-sm-2 text-right padding-left-0 padding-right-0"  ><div
											class="font-size-12 line-height-18">地址</div>
										<div class="font-size-9 line-height-18">Address</div></label>
									 <div class="col-xs-8 col-lg-11 col-sm-10 padding-left-8 padding-right-0" >
									 <textarea class="form-control" id="dz" name="dz"  maxlength="100"></textarea>
									</div>
								</div>
								<div class="clearfix"></div>							
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 form-group"  >
									    <label class="col-xs-4 col-lg-1 col-sm-2 text-right   text-right padding-left-1 padding-right-0 "  >
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div></label>
										<div class="col-xs-8 col-lg-11 col-sm-10 padding-left-8 padding-right-0" >
											<input type="text" class="form-control" id="remark" name="remark"  maxlength="60"/>
										</div>
								 </div>
								 <div class="clearfix"></div>
								 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									    <label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"  >
										<div class="font-size-12 line-height-18">开始日期</div>
										<div class="font-size-9 line-height-18">Start Date</div></label>
										<div
											class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input type="text" class="form-control date-picker"
												maxlength="10" data-date-format="yyyy-mm-dd" id="yxqks" name="yxqks" disabled="disabled"
												 />
										</div>
								 </div>	
								 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									    <label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"  >
										<div class="font-size-12 line-height-18">结束日期</div>
										<div class="font-size-9 line-height-18">End Date</div></label>
										<div
											class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input type="text" class="form-control date-picker"
												maxlength="10" data-date-format="yyyy-mm-dd" id="yxqjs" name="yxqjs" disabled="disabled"
												 />
										</div>
								 </div>	
								 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group"  >
									    <label class="col-lg-4 col-sm-4 col-xs-4 text-right   text-right padding-left-1 padding-right-0 "  >
										<div class="font-size-12 line-height-18">最大用户数</div>
										<div class="font-size-9 line-height-18">Max Users No.</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input class="form-control" disabled="disabled"  id="zczh_max" name="zczh_max" maxlength="5" />
										</div>
								 </div>	
								 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group"  >
									    <label class="col-lg-4 col-sm-4 col-xs-4 text-right   text-right padding-left-1 padding-right-0 "  >
										<div class="font-size-12 line-height-18">最大飞机数</div>
										<div class="font-size-9 line-height-18">Max Plane No.</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input class="form-control" disabled="disabled"  id="zcfj_max" name="zcfj_max" maxlength="5" />
										</div>
								 </div>								 	
								</form>	
								</div>
								</div>										
								<div class=" col-sm-12 padding-left-0 padding-right-0 margin-bottom-10 ">
									<div class="text-right">
									<button
										class="btn btn-primary padding-top-1 padding-bottom-1"
										id="saveAddDprt" ><div
										class="font-size-12">保存</div>
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
			src="${ctx}/static/js/thjw/sys/department/add_department.js"></script>
</body>
</html>

