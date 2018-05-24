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
	<div class="panel panel-primary">
		<div class="panel-heading  padding-top-3 padding-bottom-1">
		<div id="NavigationBar"></div>
		</div>
		<!-------导航End--->

			<div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
				<form id="form">
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0  " >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">故障保留单号</div>
							<div class="font-size-9 line-height-18">Retain No.</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="gzbldh" name="gzbldh"   value="${erayFns:escapeStr(legacyTrouble.gzbldh)}" readonly="readonly" maxlength="20" class="form-control"  >
							<input type="hidden" id="id" name="id"   value="${legacyTrouble.id }"  >
							
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0  " >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">飞机注册号</div>
							<div class="font-size-9 line-height-18">A/C REG</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							 
							<input type="text" readonly="readonly" class="form-control "  name="fjzch" value="${erayFns:escapeStr(legacyTrouble.fjzch)}"/>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 " >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">飞行记录单</div>
							<div class="font-size-9 line-height-18">Flight Record No.</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
						<!-- 	<select id="fxjldh" class="form-control " name="fxjldh" disabled="disabled">
							</select> --> 
							<input type="text" id="fxjldh" name="fxjldh" value="${erayFns:escapeStr(legacyTrouble.fxjldh)}/${erayFns:escapeStr(legacyTrouble.jlbym)}"  readonly="readonly" class="form-control"  >
							<input type="hidden" id="jlbym" name="jlbym" value="${erayFns:escapeStr(legacyTrouble.jlbym)}"/>
							
							
							
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0  " >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div></label>
						  <div class="col-xs-8  padding-left-8 padding-right-0 form-group">
								<input type="text" id="zjhandname" class="form-control" value="${erayFns:escapeStr(legacyTrouble.zjh)} ${erayFns:escapeStr(legacyTrouble.zjhName)}" 
								disabled="disabled" /> 
								<input type="hidden" id="zjh"class="form-control" />
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">维护人</div>
							<div class="font-size-9 line-height-18">Maintainer</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="whr" name="whr" value="${erayFns:escapeStr(legacyTrouble.whr.displayName)}"  readonly="readonly" class="form-control"  >
							</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">维护时间</div>
							<div class="font-size-9 line-height-18">Maintenance Time</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="whsj" name="whsj" value="<fmt:formatDate value='${legacyTrouble.whsj}' pattern='yyyy-MM-dd HH:mm:ss' />"  readonly="readonly" class="form-control"  >
							</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">状态</div>
							<div class="font-size-9 line-height-18">State</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							<input type="text" id="zt" name="zt" value="${legacyTrouble.ztStr}"  readonly="readonly" class="form-control"  >
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">执行M程序</div>
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
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group" >
						<label class="col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">MEL类型</div>
							<div class="font-size-9 line-height-18">MEL Type</div></label>
						 <div class="col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
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
					
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18">故障描述/措施采取</div>
							<div class="font-size-9 line-height-18">Fault Desc</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea readonly="readonly" style="height:60px" class="form-control" id="gzms" name="gzms"     maxlength="300">${erayFns:escapeStr(legacyTrouble.gzms)}</textarea>
						</div>
					</div>
					
					<div class="clearfix"></div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18">保留内容</div>
							<div class="font-size-9 line-height-18">Contents</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea readonly="readonly" class="form-control" id="scNr" name="scNr"     maxlength="300">${erayFns:escapeStr(legacyTrouble.scNr)}</textarea>
						</div>	
					</div>
					
					<div class="clearfix"></div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18">保留依据</div>
							<div class="font-size-9 line-height-18">Basis</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea readonly="readonly" class="form-control" id="blyj" name="blyj"     maxlength="300">${erayFns:escapeStr(legacyTrouble.blyj)}</textarea>
						</div>	
					</div>
					
					<div class="clearfix"></div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">保留人</div>
							<div class="font-size-9 line-height-18">First Retain</div></label>
						<div class="col-xs-8 padding-left-8 padding-right-0" >
							<input class="form-control " readonly="readonly" value="${erayFns:escapeStr(legacyTrouble.scBlrxx)}"/>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">执照号</div>
							<div class="font-size-9 line-height-18">Licence</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							 <input readonly="readonly" type="text" id="scZzh" name="scZzh"  maxlength="20" value="${erayFns:escapeStr(legacyTrouble.scZzh)}"
							   class="form-control">
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">批准人</div>
							<div class="font-size-9 line-height-18">Approved</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							<input class="form-control " readonly="readonly" value="${erayFns:escapeStr(legacyTrouble.scPzrxx)}"/>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">申请日期</div>
							<div class="font-size-9 line-height-18">App Date</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							<input type="text" id="scSqrq"  name="scSqrq" class="form-control datepicker"  disabled="disabled"
							value="<fmt:formatDate value='${legacyTrouble.scSqrq}' pattern='yyyy-MM-dd' />"  
							style="width: 100%"  data-date-format="yyyy-mm-dd"     />
						</div>
					</div>
					
					</div>
						
						<div class="clearfix"></div>
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">到期日期</div>
								<div class="font-size-9 line-height-18">Expire</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0" >
								<input type="text" id="scDqrq"  name="scDqrq" class="form-control datepicker " disabled="disabled"
								value="<fmt:formatDate value='${legacyTrouble.scDqrq}' pattern='yyyy-MM-dd' />"   
								 style="width: 100%"  data-date-format="yyyy-mm-dd"     />
							</div>
						</div>
					
					<div class="clearfix"></div>
					
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18">再次保留内容</div>
							<div class="font-size-9 line-height-18">Again Retain Contents</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea readonly="readonly" style="height:60px" class="form-control" id="zcNr" name="zcNr"     maxlength="300">${erayFns:escapeStr(legacyTrouble.zcNr)}</textarea>
						</div>	
					</div>
					
					<div class="clearfix"></div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">再次保留人</div>
							<div class="font-size-9 line-height-18">Again Retain</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							<input class="form-control " readonly="readonly" value="${erayFns:escapeStr(legacyTrouble.zcBlrxx)}"/>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">执照号</div>
							<div class="font-size-9 line-height-18">Licence</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							 <input   type="text" id="zcZzh" name="zcZzh" readonly="readonly" maxlength="20" value="${erayFns:escapeStr(legacyTrouble.zcZzh)}"
							   class="form-control">
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">再次批准人</div>
							<div class="font-size-9 line-height-18">Again Approved</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							<input class="form-control " readonly="readonly" value="${erayFns:escapeStr(legacyTrouble.zcPzrxx)}"/>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">再次申请日期</div>
								<div class="font-size-9 line-height-18">Again App Date</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0" >
								<input disabled="disabled" type="text" id="zcSqrq"  name="zcSqrq" class="form-control datepicker"  
								value="<fmt:formatDate value='${legacyTrouble.zcSqrq}' pattern='yyyy-MM-dd' />" 
								style="width: 100%"  data-date-format="yyyy-mm-dd"      />
							</div>
						</div>
				  		<div class="clearfix"></div>
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">再次到期日期</div>
								<div class="font-size-9 line-height-18">Again Expire</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0" >
								<input disabled="disabled" type="text" id="zcDqrq"  name="zcDqrq" class="form-control datepicker "  
								value="<fmt:formatDate value='${legacyTrouble.zcDqrq}' pattern='yyyy-MM-dd' />" 
								 style="width: 100%"  data-date-format="yyyy-mm-dd"     />
							</div>
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
						<label class=" col-lg-1 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-8 margin-bottom-10 " style="width:9%">			
									<div class="font-size-12 line-height-18">
										文件列表</div>
									<div class="font-size-9 line-height-18">List of Files</div>
								
						</label>
						<div class=" col-lg-10 col-sm-8 col-xs-8  padding-left-0 padding-right-0 margin-bottom-10 " style="width:91%">
						<table class="table table-thin table-bordered table-striped table-hover text-center" style="min-width: 900px;">
							<thead>
								<tr>
									<th >
										<div class="font-size-12 line-height-18">文件说明</div>
										<div class="font-size-9 line-height-18">File desc</div>
									</th>
									<th >
										<div class="font-size-12 line-height-18">文件大小</div>
										<div class="font-size-9 line-height-18">File size</div>
									</th>
									<th  >
										<div class="font-size-12 line-height-18">上传人</div>
										<div class="font-size-9 line-height-18">Operator</div></th>
									<th  >
										<div class="font-size-12 line-height-18">时间</div>
										<div class="font-size-9 line-height-18">Operate time</div>
									</th>					
								</tr>
							</thead>
							    <tbody id="filelist">
									 
									 
								</tbody>
						</table>
						</div>
					</div>
					<div class="clearfix">
					</div>
						<div class="text-right padding-top-10">
	                    	<button id="revert" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" ><div
										class="font-size-12">返回</div>
									<div class="font-size-9">Back</div></button>
	                	</div>
					
					</div>
					
                   </form>
                   	
				</div>
			  
				<div class="clearfix"></div>
				<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
				<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
			</div>
		</div>
	</div>
	</div>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	

<script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/legacytrouble/legacytrouble_view.js"></script>

</body>
</html>