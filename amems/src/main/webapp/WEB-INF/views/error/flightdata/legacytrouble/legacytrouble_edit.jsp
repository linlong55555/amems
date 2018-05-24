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
<title>修改故障保留单</title>
</head>
<body>
<input id="newFileName" type="hidden"/>
<input id="relativePath" type="hidden"/>
<input id="fileName" type="hidden"/>
	<div class="page-content " >
		<div class="panel panel-primary">
			<div class="panel-heading  padding-top-3 padding-bottom-1">
				<div id="NavigationBar"></div> 
			</div>
			<input type="hidden" id="dprtcode" name="dprtcode"   value="${legacyTrouble.dprtcode}"  >
 
			<div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
				<div id="form">
					<div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18"><span style="color: red">*</span>故障保留单号</div>
									<div class="font-size-9 line-height-18">Retain No.</div>
								</label>
								<div class=" col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="gzbldh" name="gzbldh" placeholder='长度最大为20' value="${erayFns:escapeStr(legacyTrouble.gzbldh)}"  maxlength="20" class="form-control"  >
								<input type="hidden" id="id" name="id"   value="${legacyTrouble.id}"  >
								
								
							</div>
					</div>
					<div class="  col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18"><span style="color: red">*</span>飞机注册号</div>
								<div class="font-size-9 line-height-18">A/C REG</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0" >
							 <input type="hidden" value="${erayFns:escapeStr(legacyTrouble.fjzch)}" id="fjzch_hide"/>
								<select id="fjzch" class="form-control " name="fjzch">
									 
								</select>
							</div>
					</div>
					<div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0  form-group" >
							   <label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18"><span style="color: red">*</span>飞行记录单</div>
								<div class="font-size-9 line-height-18">Flight Record No.</div>
								</label>
								<div class=" col-xs-8 padding-left-8 padding-right-0">
									<select id="fxjldh" class="form-control " name="fxjldh">
									</select> 
									<input type="hidden" id="fxjldh_hide" name="fxjldh_hide" value="${erayFns:escapeStr(legacyTrouble.fxjldh)}"/>
									<input type="hidden" id="jlbym" name="jlbym" value="${erayFns:escapeStr(legacyTrouble.jlbym)}"/>
								</div>
					</div>
					<div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18"><span style="color: red">*</span>ATA章节号</div>
								<div class="font-size-9 line-height-18">ATA</div></label>
							<div class="padding-left-8 padding-right-0 input-group">
									<input type="text" id="zjhandname" name="zjhandname"  class="form-control" value="${erayFns:escapeStr(legacyTrouble.zjh)} ${erayFns:escapeStr(legacyTrouble.zjhName)}" 
									readonly="readonly" /> 
									<input type="hidden" id="zjh"class="form-control" value="${erayFns:escapeStr(legacyTrouble.zjh)}" />
								<span class="input-group-btn">
									<button onclick="openChapterWin();" class="btn btn-primary">
										<i class="icon-search" ></i>
									</button>
								</span>
							
							</div>
					</div>
					<div class="clearfix"></div>
					<div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0  form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>维护人</div>
							<div class="font-size-9 line-height-18">Maintainer</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="whr" name="whr" value="${legacyTrouble.whr.displayName}"  readonly="readonly" class="form-control"  >
							</div>
					</div>
					<div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>维护时间</div>
							<div class="font-size-9 line-height-18">Maintenance Time</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="whsj" name="whsj" value="<fmt:formatDate value='${legacyTrouble.whsj}' pattern='yyyy-MM-dd HH:mm:ss' />"  readonly="readonly" class="form-control"  >
							</div>
					</div>
					
					<div class="  col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18"><span style="color: red">*</span>状态</div>
								<div class="font-size-9 line-height-18">State</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0" >
								<input type="hidden" id="zt" name="zt" value="${legacyTrouble.zt}"    >	 
								<input type="text" id="ztStr" name="ztStr" value="${legacyTrouble.ztStr}"  readonly="readonly" class="form-control"  >
							</div>
					</div>
					<div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>执行M程序</div>
							<div class="font-size-9 line-height-18">Execute M Program</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							 <input type="checkbox" id='isM' name='isM'
							 <c:if test="${1 eq legacyTrouble.isM }">
							  checked="checked"
							  </c:if>
							 
							 />
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>MEL类型</div>
							<div class="font-size-9 line-height-18">MEL Type</div></label>
						 <div class="col-sm-10 col-xs-8 padding-left-8 padding-right-0" >
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_A' name='mel' value='A' 
								<c:if test="${'A' eq legacyTrouble.mel }">
								  checked="checked"
								 </c:if>
								/>A</label>
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_B' name='mel' value='B'
								<c:if test="${'B' eq legacyTrouble.mel }">
								  checked="checked"
								 </c:if>
								 />B</label>
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_C' name='mel' value='C'
								<c:if test="${'C' eq legacyTrouble.mel }">
								  checked="checked"
								 </c:if>
								 />C</label>
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_D' name='mel' value='D'
								<c:if test="${'D' eq legacyTrouble.mel }">
								  checked="checked"
								 </c:if>
								 />D</label>
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_NA' name='mel' value='NA'
								<c:if test="${'NA' eq legacyTrouble.mel }">
								  checked="checked"
								 </c:if>
								 />NA</label>
						</div>
					</div>
					<div class="clearfix"></div>		
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-1 col-sm-1 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18"><span style="color: red">*</span>故障描述/措施采取</div>
							<div class="font-size-9 line-height-18">Fault Desc</div>
						</label>
						<div class="col-lg-11 col-sm-11 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea class="form-control" style="height:60px" id="gzms" name="gzms" placeholder='长度最大为300'   maxlength="300">${erayFns:escapeStr(legacyTrouble.gzms)}</textarea>
						</div>
					</div>	
					<div class="clearfix"></div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-1 col-sm-1 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18"><span style="color: red">*</span>保留内容</div>
							<div class="font-size-9 line-height-18">Contents</div>
						</label>
						<div class="col-lg-11 col-sm-11 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea class="form-control" id="scNr" name="scNr" placeholder='长度最大为300'   maxlength="300">${erayFns:escapeStr(legacyTrouble.scNr)} </textarea>
						</div>	
					</div>
					
					<div class="clearfix"></div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-1 col-sm-1 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18"><span style="color: red">*</span>保留依据</div>
							<div class="font-size-9 line-height-18">Basis</div>
						</label>
						<div class="col-lg-11 col-sm-11 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea class="form-control" id="blyj" name="blyj" placeholder='长度最大为300'   maxlength="300">${erayFns:escapeStr(legacyTrouble.blyj)}</textarea>
						</div>	
					</div>
					
					<div class="clearfix"></div>
					<div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>保留人</div>
							<div class="font-size-9 line-height-18">First Retain</div></label>
						  
						<div class="padding-left-8 padding-right-0 input-group">
							<input type="text" class="form-control" name="scBlrmc" id="scBlrmc" value="${erayFns:escapeStr(legacyTrouble.scBlrxx)}" readonly />
							<input type="hidden" id="scBlrid" value="${legacyTrouble.scBlrid}"/>
							<span class="input-group-btn">
								<button onclick="javascript:scBlr.selectUser1();" class="btn btn-primary">
									<i class="icon-search" ></i>
								</button>
							</span>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>执照号</div>
							<div class="font-size-9 line-height-18">Licence</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							 <input type="text" id="scZzh" name="scZzh"  maxlength="20" value="${erayFns:escapeStr(legacyTrouble.scZzh)}"
							 placeholder='长度最大为20' class="form-control">
						</div>
					</div>	
					<div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>批准人</div>
							<div class="font-size-9 line-height-18">Approved</div></label>
						<div class=" padding-left-8 padding-right-0 input-group">
						    <input type="text" class="form-control" name="scPzrmc" id="scPzrmc"  value="${erayFns:escapeStr(legacyTrouble.scPzrxx)}" readonly />
							<input type="hidden" id="scPzrid" value="${legacyTrouble.scPzrid}"/>
							<span class="input-group-btn">
								<button onclick="javascript:scPzr.selectUser1();" class="btn btn-primary">
									<i class="icon-search" ></i>
								</button>
							</span>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>申请日期</div>
							<div class="font-size-9 line-height-18">App Date</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							<input type="text" id="scSqrq"  name="scSqrq" class="form-control datepicker" 
							value="<fmt:formatDate value='${legacyTrouble.scSqrq}' pattern='yyyy-MM-dd' />"  
							style="width: 100%"  data-date-format="yyyy-mm-dd"  placeholder="请选择日期"   />
						</div>
					</div>
					<div class="clearfix"></div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
					<div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>到期日期</div>
							<div class="font-size-9 line-height-18">Expire</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							<input type="text" id="scDqrq"  name="scDqrq" class="form-control datepicker "
							value="<fmt:formatDate value='${legacyTrouble.scDqrq}' pattern='yyyy-MM-dd' />"   
							 style="width: 100%"  data-date-format="yyyy-mm-dd"  placeholder="请选择日期"   />
						</div>
					</div>
					 <div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
							<div class="font-size-9 line-height-18">File Desc</div>
						</label>
						<div class="col-lg-6 col-sm-6 col-xs-6 padding-left-8 padding-right-0" >
							<input type="text" id="wbwjm" name="wbwjm" placeholder='' class="form-control "  >
						</div>
						<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-2 "  style="margin-left: 0;padding-left: 0"></div>
					</div>
					</div>
					<div class="clearfix"></div>
					</div>
			</div>
					
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;">
					<table class="table table-thin table-bordered table-striped table-hover text-center">
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
					<div class="text-right ">
                        <button onclick="save()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10 checkPermission"
                          permissioncode='flightdata:legacytrouble:edit'>
                          <div class="font-size-12">保存</div>
						  <div class="font-size-9">Save</div>
						</button>
						<button onclick="committed()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10 " 
						>
						<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
						</button>
                     	<button id="revert" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" ><div
										class="font-size-12">返回</div>
									<div class="font-size-9">Back</div></button>
                     </div>
				</div>
				<div class="clearfix"></div>
                
				</div>
				  
				<div class="clearfix"></div>
				
			</div>
		</div>
	</div>
	</div>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	 
<script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/legacytrouble/legacytrouble_edit.js"></script>
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>

 <%@ include file="/WEB-INF/views/open_win/user.jsp"%>
</body>
</html>