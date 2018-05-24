<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
<title>新增故障保留单</title>
</head>
<body>
<input id="newFileName" type="hidden"/>
<input id="relativePath" type="hidden"/>
<input id="fileName" type="hidden"/>

	<div class="page-content " >
		<!-------导航Start--->
		
		<!-------导航End--->
		<!-- 查看工单Start -->
		<div class="panel panel-primary">
			<div class="panel-heading  padding-top-3 padding-bottom-1">
			<div id="NavigationBar"></div>
			</div>

			<div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
				<form id="form">
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>故障保留单号</div>
							<div class="font-size-9 line-height-18">Retain No.</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="gzbldh" name="gzbldh" placeholder='长度最大为20' value="${erayFns:escapeStr(legacyTrouble.gzbldh)}" readonly="readonly" maxlength="20" class="form-control"  >
							<input type="hidden" id="id" name="id"   value="${legacyTrouble.id }"  >
							<input type="hidden" id="dprtcode" name="dprtcode"   value="${legacyTrouble.dprtcode }"  >
							
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>飞机注册号</div>
							<div class="font-size-9 line-height-18">A/C REG</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							 
							<input type="text" readonly="readonly" class="form-control "  name="fjzch" value="${erayFns:escapeStr(legacyTrouble.fjzch)}"/>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>飞行记录单</div>
							<div class="font-size-9 line-height-18">Flight Record No.</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="fxjldh" name="fxjldh" value="${erayFns:escapeStr(legacyTrouble.fxjldh)}"  readonly="readonly" class="form-control"  >
							<input type="hidden" id="jlbym" name="jlbym" value="${erayFns:escapeStr(legacyTrouble.jlbym)}"/>
							 
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>维护人</div>
							<div class="font-size-9 line-height-18">Maintainer</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="whr" name="whr" value="${erayFns:escapeStr(legacyTrouble.whr.displayName)}"  readonly="readonly" class="form-control"  >
							</div>
					</div>
					<div class="clearfix"></div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>维护时间</div>
							<div class="font-size-9 line-height-18">Maintenance Time</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="whsj" name="whsj" value="<fmt:formatDate value='${legacyTrouble.whsj}' pattern='yyyy-MM-dd HH:mm:ss' />"  readonly="readonly" class="form-control"  >
							</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div></label>
						 <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 ">
								<input type="text" id="zjhandname" name="zjhandname" class="form-control" value="${erayFns:escapeStr(legacyTrouble.zjh)} ${erayFns:escapeStr(legacyTrouble.zjhName )}" 
								readonly="readonly"/> 
								<input type="hidden" id="zjh"class="form-control" value="${erayFns:escapeStr(legacyTrouble.zjh)}"/>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>状态</div>
							<div class="font-size-9 line-height-18">State</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
								<%-- <select class="form-control " id="zt" disabled="disabled">
									<c:forEach items="${legacyTroubleStatusEnum}" var="item" >
										<option value="${item.id}" 
										<c:if test="${item.id eq legacyTrouble.zt }"> selected="selected"  </c:if>
										>${erayFns:escapeStr(item.name)}</option>
									</c:forEach>
								</select> --%>
								<input type="text" id="zt" name="zt" value="${legacyTrouble.ztStr}"  readonly="readonly" class="form-control"  >
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>执行M程序</div>
							<div class="font-size-9 line-height-18">Execute M Program</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							 <input type="checkbox" id='isM' name='isM'   disabled="disabled"
							 <c:if test="${1 eq legacyTrouble.isM }">
							  checked="checked"
							  </c:if>
							 />
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group" >
						<label class="col-sm-3 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>MEL类型</div>
							<div class="font-size-9 line-height-18">MEL Type</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_A' name='mel' value='A'   disabled="disabled"
								<c:if test="${'A' eq legacyTrouble.mel }">
								  checked="checked"
								 </c:if>
								/>A</label>
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_B' name='mel' value='B'  disabled="disabled"
								<c:if test="${'B' eq legacyTrouble.mel }">
								  checked="checked"
								 </c:if>
								 />B</label>
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_C' name='mel' value='C'  disabled="disabled"
								<c:if test="${'C' eq legacyTrouble.mel }">
								  checked="checked"
								 </c:if>
								 />C</label>
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_D' name='mel' value='D'  disabled="disabled"
								<c:if test="${'D' eq legacyTrouble.mel }">
								  checked="checked"
								 </c:if>
								 />D</label>
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_NA' name='mel' value='NA'  disabled="disabled"
								<c:if test="${'NA' eq legacyTrouble.mel }">
								  checked="checked"
								 </c:if>
								 />NA</label>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18">故障描述/措施采取</div>
							<div class="font-size-9 line-height-18">Fault Desc</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea readonly="readonly" style="height:60px"  class="form-control" id="gzms" name="gzms" placeholder='长度最大为300'   maxlength="300">${erayFns:escapeStr(legacyTrouble.gzms)}</textarea>
						</div>
					</div>
					
					<div class="clearfix"></div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18">保留内容</div>
							<div class="font-size-9 line-height-18">Contents</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea readonly="readonly" class="form-control" id="scNr" name="scNr" placeholder='长度最大为300'   maxlength="300">${erayFns:escapeStr(legacyTrouble.scNr)}</textarea>
						</div>	
					</div>
					
					<div class="clearfix"></div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18"><span style="color: red">*</span>保留依据</div>
							<div class="font-size-9 line-height-18">Basis</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea readonly="readonly" class="form-control" id="blyj" name="blyj"    maxlength="300">${erayFns:escapeStr(legacyTrouble.blyj)}</textarea>
						</div>	
					</div>
					
					<div class="clearfix"></div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>保留人</div>
							<div class="font-size-9 line-height-18">First Retain</div></label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="scBlrmc" id="scBlrmc" value="${erayFns:escapeStr(legacyTrouble.scBlrxx)}" readonly />
							<input type="hidden" id="scBlrid" value="${legacyTrouble.scBlrid}"/>
						</div>
						
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>执照号</div>
							<div class="font-size-9 line-height-18">Licence</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							 <input readonly="readonly" type="text" id="scZzh" name="scZzh"  maxlength="20" value="${erayFns:escapeStr(legacyTrouble.scZzh)}"
							 placeholder='长度最大为20' class="form-control">
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>批准人</div>
							<div class="font-size-9 line-height-18">Approved</div></label>
						 
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="scPzrmc" id="scPzrmc"  value="${erayFns:escapeStr(legacyTrouble.scPzrxx)}" readonly />
							<input type="hidden" id="scPzrid" value="${legacyTrouble.scPzrid}"/>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18"><span style="color: red"></span>申请日期</div>
								<div class="font-size-9 line-height-18">App Date</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0" >
								<input type="text" id="scSqrq"  name="scSqrq" class="form-control datepicker" readonly="readonly"
								value="<fmt:formatDate value='${legacyTrouble.scSqrq}' pattern='yyyy-MM-dd' />"  
								style="width: 100%"  data-date-format="yyyy-mm-dd"  placeholder="请选择日期"   />
							</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>到期日期</div>
							<div class="font-size-9 line-height-18">Expire</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							<input type="text" id="scDqrq"  name="scDqrq" class="form-control datepicker " readonly="readonly"
							value="<fmt:formatDate value='${legacyTrouble.scDqrq}' pattern='yyyy-MM-dd' />"   
							 style="width: 100%"  data-date-format="yyyy-mm-dd"  placeholder="请选择日期"   />
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18"><span style="color: red">*</span>再次保留内容</div>
							<div class="font-size-9 line-height-18">Again Retain Contents</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea  class="form-control" id="zcNr" name="zcNr" placeholder='长度最大为300' style="height:60px"  maxlength="300">${erayFns:escapeStr(legacyTrouble.zcNr)}</textarea>
						</div>	
					</div>
					
					<div class="clearfix"></div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>再次保留人</div>
							<div class="font-size-9 line-height-18">Again Retain</div></label>
						<div class="padding-left-8 padding-right-0 input-group">
						 
						   	<input type="text" class="form-control" name="zcBlrmc" id="zcBlrmc" value="${erayFns:escapeStr(legacyTrouble.zcBlrxx)}" readonly />
							<input type="hidden" id="zcBlrid" value="${legacyTrouble.zcBlrid}"/>
							<span class="input-group-btn">
							<button  onclick="javascript:zcBlr.selectUser1();" type="button" 	class="btn btn-primary padding-top-4 padding-bottom-4"  >
								<i class="icon-search cursor-pointer" ></i>
							</button>
							</span>
						
					  </div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>执照号</div>
							<div class="font-size-9 line-height-18">Licence</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							 <input   type="text" id="zcZzh" name="zcZzh"  maxlength="20" value="${erayFns:escapeStr(legacyTrouble.zcZzh)}"
							 placeholder='长度最大为20' class="form-control">
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>再次批准人</div>
							<div class="font-size-9 line-height-18">Again Approved</div></label>
						 
					   <div class="padding-left-8 padding-right-0 input-group"">					   		
						  	<input type="text" class="form-control" name="zcPzrmc" id="zcPzrmc"  value="${erayFns:escapeStr(legacyTrouble.zcPzrxx)}" readonly />							
							<input type="hidden" id="zcPzrid" value="${legacyTrouble.zcPzrid}"/>
							<span class="input-group-btn">
								<button onclick="javascript:zcPzr.selectUser1();" type="button"  	class="btn btn-primary padding-top-4 padding-bottom-4"  >
									<i class="icon-search cursor-pointer" ></i>
								</button>
							</span>
						</div>
						
						
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0  form-group" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18"><span style="color: red">*</span>再次申请日期</div>
								<div class="font-size-9 line-height-18">Again App Date</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0" >
								<input type="text" id="zcSqrq"  name="zcSqrq" class="form-control datepicker"  
								value="<fmt:formatDate value='${legacyTrouble.zcSqrq}' pattern='yyyy-MM-dd' />" 
								style="width: 100%"  data-date-format="yyyy-mm-dd"  placeholder="请选择日期"   />
							</div>
					</div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18"><span style="color: red">*</span>再次到期日期</div>
								<div class="font-size-9 line-height-18">Again Expire</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0" >
								<input type="text" id="zcDqrq"  name="zcDqrq" class="form-control datepicker "  
								value="<fmt:formatDate value='${legacyTrouble.zcDqrq}' pattern='yyyy-MM-dd' />" 
								 style="width: 100%"  data-date-format="yyyy-mm-dd"  placeholder="请选择日期"   />
							</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
							<div class="font-size-9 line-height-18">File Name</div>
						</label>
						<div class=" col-xs-6 padding-left-8 padding-right-0" >
							<input type="text" id="wbwjm" name="wbwjm" placeholder='' class="form-control "  >
						</div>
						<div id="fileuploader" class="col-xs-2 padding-right-0 "  style="margin-left:0;padding-left: 0"></div>
					</div>
					</div>
					<div class="clearfix"></div> 
					</form>
					</div>
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-top: 10px;overflow: auto;">
						<table class="table table-thin table-bordered table-striped table-hover text-center" style="min-width: 900px">
							<thead>
								<tr>
									<th style="width:110px;"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									
									<th >
									<div class="font-size-12 line-height-18">文件说明</div>
										<div class="font-size-9 line-height-18">File desc</div>
									</th>
									
									<th >
									<div class="font-size-12 line-height-18">文件大小</div>
										<div class="font-size-9 line-height-18">File size</div>
									</th>
									
									
									<th  ><div class="font-size-12 line-height-18">上传人</div>
										<div class="font-size-9 line-height-18">Operator</div></th>
									<th  ><div class="font-size-12 line-height-18">时间</div>
										<div class="font-size-9 line-height-18">Operate time</div></th>					
								</tr>
							</thead>
							    <tbody id="filelist">
									 
									 
								</tbody>
						</table>
						<div class="text-right">
                        <button onclick="save()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10 checkPermission"
                          permissioncode='flightdata:legacytrouble:resave'>
                          <div class="font-size-12">保存</div>
						  <div class="font-size-9">Save</div>
						</button>&nbsp;
						 
                     	<button id="revert" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" ><div
										class="font-size-12">返回</div>
									<div class="font-size-9">Back</div></button>
                     </div>
					</div>
				</div>
				</div>
				  
				<div class="clearfix"></div>
				
			</div>
		</div>
	</div>
	</div>
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/legacytrouble/legacytrouble_resave.js"></script>

</body>
</html>