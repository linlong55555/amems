<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改飞机基本信息</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var pageParam = '${param.pageParam}';
</script>
<style type="text/css">
.row {
	margin-left: 0px;
    margin-right: 0px;
}
.font_width {
    margin-bottom: 0;
}
.text_width {
	width:23%;
}
</style>
</head>
<body>

	<div class="page-content">
		<form id="planeForm">
		<!-- 查看工单Start -->
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar">
			</div>

			<div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom:10px;">
					<input type="hidden" id="operationType" value="${type}"/>
					<div class="panel panel-default">
						<div class="panel-heading">
						<h3 class="panel-title">飞机基本信息 Basic Information</h3>
						</div>
						<div class="panel-body padding-bottom-0">
						 <input type='hidden' id='gjdjzfjidinput' value="${erayFns:escapeStr(pd.gjdjzfjid)}" />
						 <input type='hidden' id='shzfjidinput' value="${erayFns:escapeStr(pd.shzfjid)}" />
						 <input type='hidden' id='wxdtzzfjidinput' value="${erayFns:escapeStr(pd.wxdtzzfjid)}" />
							<div class="col-lg-12 col-sm-12 col-xs-12">
								<div class="col-lg-3 col-sm-6 col-xs-12 padding-right-0 form-group"  >
									<label  class="col-lg-5 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18"></span>机型</div>
										<div class="font-size-9 line-height-18">Model</div></label>
										<div class=" col-lg-7 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
											
											<input type="text" class="form-control" name="fjjx" id="fjjx" value="${erayFns:escapeStr(pd.fjjx)}" disabled="disabled"/>
									</div>
								</div>
								
								<div class="col-lg-3 col-sm-6 col-xs-12 padding-right-0 form-group">
									<label  class="col-lg-5 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div></label>
										<div class=" col-lg-7 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control" name="fjzch" id="fjzch" value="${erayFns:escapeStr(pd.fjzch)}" disabled="disabled"/>
									</div>
								</div>
								
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-right-0 form-group">
									<label class="col-lg-5 col-sm-5 col-xs-5 text-right padding-left-8 padding-right-0"><div
											class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">S/N</div></label>
									 <div class="col-lg-7 col-sm-7 col-xs-7 padding-left-8 padding-right-0" >
										<input type="text" class="form-control" name="xlh" id="xlh" value="${erayFns:escapeStr(pd.xlh)}" disabled="disabled"/>
									</div>
								</div>
								
								<div class="col-lg-3 col-sm-6 col-xs-12 padding-right-0 form-group">
									<label class="col-lg-5 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">备注名</div>
										<div class="font-size-9 line-height-18">Remark</div> </label>
									<div class="col-lg-7 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control" name="bzm" id="bzm" value="${erayFns:escapeStr(pd.bzm)}" maxlength="16"/>
									</div>
								</div>
							</div>
							
						<%-- 	<div class="col-lg-12 col-sm-12 col-xs-12">
							 		<div class="col-lg-3 col-sm-6 col-xs-12  padding-right-0 form-group">
									<label class="col-lg-5 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">国籍登记证号</div>
										<div class="font-size-9 line-height-18">Registration</div> </label>
									<div class="col-lg-7 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control" name="gjdjzh" id="gjdjzh" maxlength="50" value="${erayFns:escapeStr(pd.gjdjzh)}"/>
									</div>
								</div>
								<div class="col-lg-3 col-sm-6 col-xs-12   padding-right-0 form-group">
									<label class="col-lg-5 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>适航证号</div>
										<div class="font-size-9 line-height-18">Certificate</div>
									</label>
									<div class=" col-lg-7 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control" name="shzh" id="shzh" maxlength="50" value="${erayFns:escapeStr(pd.shzh)}"/>
									</div>
								</div>
								<div class="col-lg-3 col-sm-6 col-xs-12 padding-right-0 form-group">
									<label class="col-lg-5 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">无线电台许可证号</div>
										<div class="font-size-9 line-height-18">Station license</div>
									</label>
									<div class=" col-lg-7 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control" name="wxdtxkzh" id="wxdtxkzh" maxlength="50" value="${erayFns:escapeStr(pd.wxdtxkzh)}"/>
									</div>
								</div>
								
								<div class="col-lg-3 col-sm-6 col-xs-12 padding-right-0 form-group">
									<label class="col-lg-5 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">电台执照监控日期</div>
										<div class="font-size-9 line-height-18">Exp.</div>
									</label>
									<div class=" col-lg-7 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control date-picker" name="dtzzjkrq" id="dtzzjkrq" data-date-format="yyyy-mm-dd" value="<fmt:formatDate value='${pd.dtzzjkrq}' pattern='yyyy-MM-dd' />" maxlength="10"/>
									</div>
								</div>
								</div> --%>
							<div class="col-lg-12 col-sm-12 col-xs-12">
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-right-0 form-group">
									<label class="col-lg-5 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">出厂日期</div>
										<div class="font-size-9 line-height-18">Production Date</div>
									</label>
									<div class=" col-lg-7 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
										<input class="form-control date-picker" name="ccrq" data-date-format="yyyy-mm-dd" type="text" maxlength="10"  id="ccrq" value="<fmt:formatDate value='${pd.ccrq}' pattern='yyyy-MM-dd' />"/>
									</div>
								</div>
								<div class="col-lg-3 col-sm-6 col-xs-12 padding-right-0 form-group" >
									<label  class="col-lg-5 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18"><span style="color: red" id="showremark">*</span>基地</div>
										<div class="font-size-9 line-height-18">Station</div></label>
										<div class=" col-lg-7 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
											<select class='form-control' id='jd' name="jd">
												<c:choose>
													<c:when test="${baseList!=null && fn:length(baseList) > 0}">
														<c:forEach items="${baseList}" var="base">
															<option value="${base.id}" ${pd.jd == base.id ? 'selected = "selected"' : ''}>${erayFns:escapeStr(base.jdms)}</option>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<option value="">暂无基地</option>
													</c:otherwise>
												</c:choose>
											</select>
									</div>
								</div>
								<div><input type="hidden" value="${pd.dprtCode }" id="dprtcode"></div>
							</div>
							<div class="col-lg-12 col-sm-12 col-xs-12">
								<div class="col-sm-12 col-xs-12 form-group padding-right-0">
									<label class="col-xs-1 text-right padding-left-0 padding-right-0" style="width: 10%">
										<div class="font-size-12 line-height-18">机身改装记录</div>
										<div class="font-size-9 line-height-18">Record</div>
									</label>
									<div class="col-xs-11 padding-left-8 padding-right-0" style="width: 90%">
										<textarea rows="3" name="jsgzjl" class="form-control" id="jsgzjl" maxlength="166">${erayFns:escapeStr(pd.jsgzjl)}</textarea>
									</div>
								</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12">
								<div class="col-sm-12 col-xs-12 form-group padding-right-0">
									<label class="col-xs-1 text-right padding-left-0 padding-right-0" style="width: 10%">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</label>
									<div class="col-xs-11 padding-left-8 padding-right-0" style="width: 90%">
										<textarea rows="3" name="bz" class="form-control" id="bz" maxlength="300">${erayFns:escapeStr(pd.bz)}</textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					
					<div class="panel panel-default">
				        <div class="panel-heading">
							<h3 class="panel-title">飞机三证 The plane 3 card</h3>
					   </div>
					<div class="panel-body padding-bottom-5">	
					
					   	<!-- 国籍登记证号 -->	 	
					<div class="col-lg-12 padding-left-0 padding-right-0">
							<div class="col-lg-4 col-sm-6 col-xs-12  padding-right-0 form-group" id="gjdjdiv">
								<label class="col-lg-4 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">国籍登记证号</div>
									<div class="font-size-9 line-height-18">Registration</div> 
								</label>
								<div class="col-lg-8 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
									<input type="text" class="form-control" id='gjdjzh' name='gjdjzh' value='${erayFns:escapeStr(pd.gjdjzh)}'/>
								</div>
							</div>
							
							<div class="col-lg-4 col-sm-6 col-xs-12   padding-right-0 form-group" id="gjdjrqdiv">
								<label class="col-lg-3 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">有效日期</div>
									<div class="font-size-9 line-height-18">Validity</div>
								</label>
								<div class=" col-lg-9 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
									<input type="text" class="form-control date-picker" data-date-format="yyyy-mm-dd"  id='gjdjzh_yxq' name='gjdjzh_yxq' value="<fmt:formatDate value='${pd.gjdjzjkrq}' pattern='yyyy-MM-dd' />" />
								</div>
							</div>
							
							<div class="col-lg-4 col-sm-6 col-xs-12 padding-right-0 form-group" id="gjdjzhdiv">
								<label class="col-lg-3 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">附件</div>
									<div class="font-size-9 line-height-18">Attachment</div>
								</label>
								<div class="col-xs-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0 "  >
							    <div class='input-group' >
								<input class="form-control"   type="text"  maxlength="90" id='gjdjzh_fj'/>
								<span class='input-group-btn'>
								 <div id="fileuploader"></div>
								</span>
						        </div>
							    </div>
							</div>
						</div> 
						<!-- 适航证号 -->
						<div class="col-lg-12 padding-left-0 padding-right-0">
							<div class="col-lg-4 col-sm-6 col-xs-12  padding-right-0 form-group" id="shzhdiv">
								<label class="col-lg-4 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">适航证号</div>
									<div class="font-size-9 line-height-18">Airworthiness</div> 
								</label>
								<div class="col-lg-8 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
									<input type="text" class="form-control" id='shzh' name='shzh' value="${erayFns:escapeStr(pd.shzh)}"/>
								</div>
							</div>
							
							<div class="col-lg-4 col-sm-6 col-xs-12   padding-right-0 form-group" id="shzhrqdiv">
								<label class="col-lg-3 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">有效日期</div>
									<div class="font-size-9 line-height-18">Validity</div>
								</label>
								<div class=" col-lg-9 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
									<input type="text" class="form-control date-picker" data-date-format="yyyy-mm-dd"  id='shzh_yxq' name='shzh_yxq' value="<fmt:formatDate value='${pd.shzjkrq}' pattern='yyyy-MM-dd' />" />
								</div>
							</div>
							
							<div class="col-lg-4 col-sm-6 col-xs-12 padding-right-0 form-group" id="shzhfjdiv">
								<label class="col-lg-3 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">附件</div>
									<div class="font-size-9 line-height-18">Attachment</div>
								</label>
								<div class="col-xs-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0 "  >
							    <div class='input-group' >
								<input class="form-control"   maxlength="90" type="text" id='shzh_fj'/>
								<span class='input-group-btn'>
								 <div id="fileuploaderairworthiness"></div>
								</span>
						        </div>
							    </div>
							</div>
						</div>
						<!-- 无线电台许可证号 -->
						<div class="col-lg-12 padding-left-0 padding-right-0">
							<div class="col-lg-4 col-sm-6 col-xs-12  padding-right-0 form-group" id="wxdddiv">
								<label class="col-lg-4 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">无线电台许可证号</div>
									<div class="font-size-9 line-height-18">Wireless station</div> 
								</label>
								<div class="col-lg-8 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
									<input type="text" class="form-control" id='wxdtxkzh' name='wxdtxkzh' value="${erayFns:escapeStr(pd.wxdtxkzh)}"/>
								</div>
							</div>
							
							<div class="col-lg-4 col-sm-6 col-xs-12   padding-right-0 form-group" id="wxddrqdiv">
								<label class="col-lg-3 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">有效日期</div>
									<div class="font-size-9 line-height-18">Validity</div>
								</label>
								<div class=" col-lg-9 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
									<input type="text" class="form-control date-picker" data-date-format="yyyy-mm-dd"   id='wxdtxkzh_yxq' name='wxdtxkzh_yxq' value="<fmt:formatDate value='${pd.dtzzjkrq}' pattern='yyyy-MM-dd' />" />
								</div>
							</div>
							
							<div class="col-lg-4 col-sm-6 col-xs-12 padding-right-0 form-group" id="wxdtxkzhdiv">
								<label class="col-lg-3 col-sm-5 col-xs-5 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">附件</div>
									<div class="font-size-9 line-height-18">Attachment</div>
								</label>
								<div class="col-xs-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0 "  >
							    <div class='input-group' >
								<input class="form-control" type="text"  maxlength="90" id='wxdtxkzh_fj'/>
								<span class='input-group-btn'>
								 <div id="fileuploaderwireless"></div>
								</span>
						        </div>
							    </div>
							</div>
						</div>
					
					 <!-- <div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
							<div class="font-size-9 line-height-18">File Description</div></label>
						 <div class="col-xs-9 padding-left-8 padding-right-0" >
							<input type="text" id="wbwjm" name="wbwjm"  maxlength="100" class="form-control "  >
						</div>
					 </div>
				     <div class=" col-lg-1 col-sm-1 col-xs-2  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-12 "  style="margin-left: 5px ;padding-left: 0"></div>
					 </div>  -->
					
					 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow: auto;">
						<table class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 900px">
							<thead>
								<tr>
									<th style="width:40px;"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th style="width:100px;">
									<div class="font-size-12 line-height-18">文件类型</div>
										<div class="font-size-9 line-height-18">Type</div>
									</th>
									<th style="width:400px;">
									<div class="font-size-12 line-height-18">文件说明</div>
										<div class="font-size-9 line-height-18">Description</div>
									</th>
									<th style="width:100px;">
									<div class="font-size-12 line-height-18">文件大小</div>
										<div class="font-size-9 line-height-18">File Size</div>
									</th>
									<th style="width:100px;"><div class="font-size-12 line-height-18">上传人</div>
										<div class="font-size-9 line-height-18">Uploader</div></th>
									<th style="width:200px;"><div class="font-size-12 line-height-18">上传时间</div>
										<div class="font-size-9 line-height-18">Upload Time</div></th>					
								</tr>
							</thead>
							    <tbody id="filelist">
									 
								</tbody>
						</table>
					 </div>
					</div>
				</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">飞机进入系统时初始化信息 Aircraft Init Data</h3>
						</div>
						<div class="panel-body padding-bottom-0">
							<div class="col-lg-12 col-sm-12 col-xs-12 ">
								<div class="col-lg-2 col-sm-6 col-xs-12 padding-right-0 form-group padding-left-0" >
									<label  class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">日期</div>
										<div class="font-size-9 line-height-18">Date</div></label>
										<div class=" col-xs-7 padding-left-8 padding-right-0">
										<input class="form-control date-picker" name="init_date_rq" data-date-format="yyyy-mm-dd" type="text"  id="init_date_rq" value="${pd.init_date_rq}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12 padding-right-0 form-group padding-left-0" >
									<label  class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">机身飞行时间</div>
										<div class="font-size-9 line-height-18">Flight HRS</div></label>
										<div class=" col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control" name="init_time_jsfx" value="${pd.init_time_jsfx}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">搜索灯时间</div>
										<div class="font-size-9 line-height-18">SearchLight HRS</div></label>
									 <div class="col-xs-7 padding-left-8 padding-right-0" >
										<input type="text" class="form-control " name="init_time_ssd" value="${pd.init_time_ssd}" maxlength="10"/>
									</div>
								</div>
							 	<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">绞车时间</div>
										<div class="font-size-9 line-height-18">Winch HRS</div> </label>
									<div class="col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control " name="init_time_jc" value="${pd.init_time_jc}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">起落循环</div>
										<div class="font-size-9 line-height-18">Flight CYCS</div></label>
									 <div class="col-xs-7 padding-left-8 padding-right-0" >
										<input type="text" class="form-control " name="init_loop_qlj" value="${pd.init_loop_qlj}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">绞车循环</div>
										<div class="font-size-9 line-height-18">Winch CYCS</div> </label>
									<div class="col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control " name="init_loop_jc" value="${pd.init_loop_jc}" maxlength="10"/>
									</div>
								</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12">
								
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">外吊挂循环</div>
										<div class="font-size-9 line-height-18">CYCS</div></label>
									 <div class="col-xs-7 padding-left-8 padding-right-0" >
										<input type="text" class="form-control " name="init_loop_wdg" value="${pd.init_loop_wdg}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">TS1</div>
										<div class="font-size-9 line-height-18">TS1</div></label>
									 <div class="col-xs-7 padding-left-8 padding-right-0" >
										<input type="text" class="form-control " name="init_loop_ts1" value="${pd.init_loop_ts1}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">TS2</div>
										<div class="font-size-9 line-height-18">TS2</div> </label>
									<div class="col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control " name="init_loop_ts2" value="${pd.init_loop_ts2}" maxlength="10"/>
									</div>
									<div class="clearfix"></div>
								</div>
								
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12">
								
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">1# N1</div>
										<div class="font-size-9 line-height-18">N1</div></label>
									 <div class="col-xs-7 padding-left-8 padding-right-0" >
										<input type="text" class="form-control " name="init_loop_l_n1" value="${pd.init_loop_l_n1}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">1# N2</div>
										<div class="font-size-9 line-height-18">N2</div> </label>
									<div class="col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control " name="init_loop_l_n2" value="${pd.init_loop_l_n2}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">1# N3</div>
										<div class="font-size-9 line-height-18">N3</div></label>
									 <div class="col-xs-7 padding-left-8 padding-right-0" >
										<input type="text" class="form-control " name="init_loop_l_n3" value="${pd.init_loop_l_n3}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">1# N4</div>
										<div class="font-size-9 line-height-18">N4</div> </label>
									<div class="col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control " name="init_loop_l_n4" value="${pd.init_loop_l_n4}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">1# N5</div>
										<div class="font-size-9 line-height-18">N5</div></label>
									 <div class="col-xs-7 padding-left-8 padding-right-0" >
										<input type="text" class="form-control " name="init_loop_l_n5" value="${pd.init_loop_l_n5}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">1# N6</div>
										<div class="font-size-9 line-height-18">N6</div> </label>
									<div class="col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control " name="init_loop_l_n6" value="${pd.init_loop_l_n6}" maxlength="10"/>
									</div>
								</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12">
								
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">2# N1</div>
										<div class="font-size-9 line-height-18">N1</div></label>
									 <div class="col-xs-7 padding-left-8 padding-right-0" >
										<input type="text" class="form-control " name="init_loop_r_n1" value="${pd.init_loop_r_n1}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">2# N2</div>
										<div class="font-size-9 line-height-18">N2</div> </label>
									<div class="col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control " name="init_loop_r_n2" value="${pd.init_loop_r_n2}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">2# N3</div>
										<div class="font-size-9 line-height-18">N3</div></label>
									 <div class="col-xs-7 padding-left-8 padding-right-0" >
										<input type="text" class="form-control " name="init_loop_r_n3" value="${pd.init_loop_r_n3}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">2# N4</div>
										<div class="font-size-9 line-height-18">N4</div> </label>
									<div class="col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control " name="init_loop_r_n4" value="${pd.init_loop_r_n4}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12  padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">2# N5</div>
										<div class="font-size-9 line-height-18">N5</div></label>
									 <div class="col-xs-7 padding-left-8 padding-right-0" >
										<input type="text" class="form-control " name="init_loop_r_n5" value="${pd.init_loop_r_n5}" maxlength="10"/>
									</div>
								</div>
								<div class="col-lg-2 col-sm-6 col-xs-12 padding-right-0 form-group padding-left-0">
									<label class="col-xs-5 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">2# N6</div>
										<div class="font-size-9 line-height-18">N6</div> </label>
									<div class="col-xs-7 padding-left-8 padding-right-0">
										<input type="text" class="form-control " name="init_loop_r_n6" value="${pd.init_loop_r_n6}" maxlength="10"/>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="text-right">
                     	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="save()" id="saveBtn">
                     		<div class="font-size-12">提交</div>
							<div class="font-size-9">Submit</div>
						</button>
						
                     	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:goToMainPage();">
                     		<div class="font-size-12">返回</div>
							<div class="font-size-9">Back</div>
						</button>
						
                     </div>
				</div>

					</div>
				</div>
				
				</form>
			</div>
<!-- 查看工单End -->

		<!-------alert对话框 Start-------->
	<div class="modal fade" id="alertAfterModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertAfterModalBody"></div>
				<div class="modal-footer">
					<button type="button" onclick="goToMainPage();" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
						<div class="font-size-12">关闭</div>
						<div class="font-size-9">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-------alert对话框 End-------->
	<!-------alert error对话框 Start-------->
	<div class="modal fade" id="alertErrorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertErrorModalBody">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
						<div class="font-size-12">关闭</div>
						<div class="font-size-9">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-------alert error对话框 End-------->
	</div>
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
	<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
	<script src="${ctx}/static/js/thjw/productionplan/planeData/planeData_edit.js"></script>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript">
	var type = '${type}';
	if(type == 'view'){
		$('#planeForm input').attr('disabled','disabled');
		$('#planeForm textarea').attr('disabled','disabled');
		$('#planeForm select').attr('disabled','disabled');
		$('#title_cn').html("查看");
		$('#title_eng').html("View");
		$("#showremark").hide();
		$('#saveBtn').hide();
		$("#wxdtxkzhdiv").hide();
		$("#shzhfjdiv").hide();
		$("#gjdjzhdiv").hide();
		$("#gjdjdiv").removeClass().addClass("col-lg-6 col-sm-6 col-xs-12  padding-right-0 form-group");
		$("#gjdjdiv").children(":first").css("width", "23%");
		$("#gjdjrqdiv").removeClass().addClass("col-lg-6 col-sm-6 col-xs-12  padding-right-0 form-group");
		$("#gjdjrqdiv").children(":first").css("width", "23%");
		$("#gjdjrqdiv").children().eq(1).css("width", "74%");
		$("#shzhdiv").removeClass().addClass("col-lg-6 col-sm-6 col-xs-12  padding-right-0 form-group");
		$("#shzhdiv").children(":first").css("width", "23%");
		$("#shzhrqdiv").removeClass().addClass("col-lg-6 col-sm-6 col-xs-12  padding-right-0 form-group");
		$("#shzhrqdiv").children(":first").css("width", "23%");
		$("#shzhrqdiv").children().eq(1).css("width", "74%");
		$("#wxdddiv").removeClass().addClass("col-lg-6 col-sm-6 col-xs-12  padding-right-0 form-group");
		$("#wxdddiv").children(":first").css("width", "23%");
		$("#wxddrqdiv").removeClass().addClass("col-lg-6 col-sm-6 col-xs-12  padding-right-0 form-group");
		$("#wxddrqdiv").children(":first").css("width", "23%");
		$("#wxddrqdiv").children().eq(1).css("width", "74%");
		$('#fileuploader').parent().parent().find('.form-group').hide();
		if($('#jsgzjl').text()==''){
			$('#jsgzjl').removeAttr('placeholder');
		}
	}
	</script>
</body>
</html>