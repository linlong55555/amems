<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
 <style type="text/css">
.multiselect{
width:500px;
overflow:hidden;
}
.multiselect-container.dropdown-menu{
width:500px;
}
</style>
<body>
<!-- 隐藏域 -->
<input type="hidden"  id="jg" class="form-control" value="1" />
<input type="hidden"  id="engineeringId" class="form-control" value="${engineering.id}" />
<input type="hidden"  id="engineeringGczlbh" class="form-control" value="${erayFns:escapeStr(engineering.gczlbh)}" />
<input type="hidden"  id="engineeringZt" class="form-control" value="${engineering.zt}" />
<input type="hidden"  id="engineeringDprtcode" class="form-control" value="${engineering.dprtcode}" />
<input type="hidden" name="jgdm" id="jgdm" value="${user.jgdm}" />
<input type="hidden" name="dprtcode" id="dprtcode" value="${engineering.dprtcode}" />
<!-- end隐藏域 -->
<script type="text/javascript">
var pageParam = '${param.pageParam}';
$(document).ready(function(){
//导航
Navigation(menuCode,"查看EO指令","View");
});
</script>
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0  padding-right-0">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">基本信息 Basic Information</h3>
						</div>
						<div class="panel-body">
						
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18"><span style="color: red">*</span>工程指令编号</div>
										<div class="font-size-9 line-height-18">EO No.</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text"  id="gczlbh" class="form-control" value="${erayFns:escapeStr(engineering.gczlbh)}" disabled="disabled"/>
										</div>
									</div>
								
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
												class="font-size-12 line-height-18"><span style="color: red">*</span>机型</div>
											<div class="font-size-9 line-height-18">Model</div></label>
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input class="form-control date-picker" id="jx" name="jx" type="text" value="${erayFns:escapeStr(engineering.fjjx)}" disabled="disabled"/>
										</div>
									</div>
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
												class="font-size-12 line-height-18">ATA章节号</div>
											<div class="font-size-9 line-height-18">ATA</div></label>
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input class="form-control date-picker" id="zjhName" name="zjhName" type="text" value="${erayFns:escapeStr(engineering.zjh)}" disabled="disabled"/>
											<input class="form-control date-picker" id="zjh" name="zjh" type="hidden" value="${erayFns:escapeStr(engineering.zjh)}" disabled="disabled"/>
										</div>
									</div>
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
												class="font-size-12 line-height-18">版本</div>
											<div class="font-size-9 line-height-18">Revision</div></label>
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input class="form-control date-picker" id="bb" name="bb" type="text" value="${erayFns:escapeStr(engineering.bb) }" disabled="disabled"/>
										</div>
									</div>
									
								</div>
							</div>
						
						
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
												class="font-size-12 line-height-18">EO类型</div>
											<div class="font-size-9 line-height-18">EO Type</div></label>
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input type="hidden" name="engineeringEolx" id="engineeringEolx" value="${engineering.eolx }" >
											<select class=" " id="eolx" name="eolx" multiple="multiple" disabled="disabled">
														<option value="1">改装</option>
														<option value="2">检查</option>
														<option value="3">修理/偏离</option>
											</select>
										</div>
									</div>
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
												class="font-size-12 line-height-18">状态</div>
											<div class="font-size-9 line-height-18">State</div></label>
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input class="form-control date-picker" id="zt" name="zt" type="text" maxlength="50" disabled="disabled" value="${engineering.ztText }" />
										</div>
									</div>
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
												class="font-size-12 line-height-18">制单人</div>
											<div class="font-size-9 line-height-18">Creator</div></label>
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input class="form-control date-picker"  id="zdr" name="zdr" data-date-format="yyyy-mm-dd" type="text" disabled="disabled" value="${erayFns:escapeStr(engineering.zdr_user.displayName)}" />
										</div>
									</div>
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
												class="font-size-12 line-height-18">制单时间</div>
											<div class="font-size-9 line-height-18">Create Time</div></label>
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input class="form-control"  name="zdsj" value="<fmt:formatDate value='${engineering.zdsj}' pattern='yyyy-MM-dd HH:mm:ss' />" id="zdsj" disabled="disabled"/>
										</div>
									</div>

								</div>

							</div>
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 form-group">
									<div class="font-size-12 line-height-18">
										关联评估单
									</div>
									<div class="font-size-9 line-height-18">Evaluation</div>
								</label>

								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group" style="overflow-x:auto;">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:900px">
										<thead>
											<tr>
												<th  class="colwidth-7">
												   <div class="font-size-12 line-height-18">评估单号</div>
													<div class="font-size-9 line-height-18">Assessment No.</div>
												</th>
												<th  class="colwidth-5">
												   <div class="font-size-12 line-height-18">来源</div>
													<div class="font-size-9 line-height-18">Source </div>
												</th>
												<th  class="colwidth-15">
												   <div class="font-size-12 line-height-18">参考资料</div>
													<div class="font-size-9 line-height-18">Reference Material</div>
												</th>
												<th  class="colwidth-7">
												    <div class="font-size-12 line-height-18">生效日期</div>
													<div class="font-size-9 line-height-18">Effective Date</div>
												</th>
												<th  class="colwidth-13">
												    <div class="font-size-12 line-height-18">机型工程师</div>
													<div class="font-size-9 line-height-18">Engineer</div>
												</th>
												<th  class="colwidth-7">
												    <div class="font-size-12 line-height-18">评估期限</div>
													<div class="font-size-9 line-height-18">Assess period</div>
												</th>
												<th  class="colwidth-5">
												    <div class="font-size-12 line-height-18">评估状态</div>
													<div class="font-size-9 line-height-18">State</div>
												</th>
											</tr>
										</thead>
										<tbody id="Annunciatelist">
										</tbody>
									</table>
									<input type="hidden" name="pgjx" id="pgjx" />
								</div>
							</div>
							<form id="form">
								<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
									
									<label  class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>主题</div>
										<div class="font-size-9 line-height-18">Subject</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
										<input maxlength="100" name="zhut" id="zhut" class="form-control date-picker" data-bv-field="zhut" type="text" value="${erayFns:escapeStr(engineering.zhut) }" disabled="disabled">
									</div>
								</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">相关改装号</div>
										<div class="font-size-9 line-height-18">MOD No.</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input class="form-control date-picker" id="xggzh" name="xggzh" type="text" value="${erayFns:escapeStr(engineering.xggzh) }" disabled="disabled" maxlength="50" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">重检</div>
										<div class="font-size-9 line-height-18">Reinspection</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
										<input class="form-control date-picker" id="cj" name="cj" type="text" value="${erayFns:escapeStr(engineering.cj) }" disabled="disabled" maxlength="50" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">重量与平衡影响</div>
										<div class="font-size-9 line-height-18">Weight And Balance</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
										<label style="margin-right: 20px;font-weight: normal">
											<input name="isZlphyx" type="radio" value="1" disabled="disabled" <c:if test="${engineering.isZlphyx eq 1 }">checked</c:if> />是
										</label> 
										<label style="font-weight: normal">
											<input name="isZlphyx" type="radio" value="0" disabled="disabled" <c:if test="${engineering.isZlphyx eq 0 }">checked</c:if> />否
										 </label> 
									</div>
								</div>
					
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">重复执行与时限</div>
										<div class="font-size-9 line-height-18">Repeated Execution Limit</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
										<label style="margin-right: 20px;font-weight: normal">
											<input name="isCfzxsx" type="radio" value="1" disabled="disabled" <c:if test="${engineering.isCfzxsx eq 1 }">checked</c:if> />是
										</label> 
										<label style="font-weight: normal">
											<input name="isCfzxsx" type="radio" value="0" disabled="disabled" <c:if test="${engineering.isCfzxsx eq 0 }">checked</c:if> />否 
										</label> 
									</div>
								</div>
								
								</div>
							</div>
							
								<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0">
									<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
										
										<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div
												class="font-size-12 line-height-18">描述</div>
											<div class="font-size-9 line-height-18">Description</div></label>
										<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
											<textarea class="form-control" id="txyj" name="txyj"  maxlength="300" disabled="disabled">${erayFns:escapeStr(engineering.txyj) }</textarea>
										</div>
									</div>
								</div>
								
								<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0">
									<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
										
										<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div
												class="font-size-12 line-height-18">颁发本工程指令理由</div>
											<div class="font-size-9 line-height-18">Reason</div></label>
										<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
											<textarea class="form-control" id="bbly" name="bbly"  maxlength="300" disabled="disabled">${erayFns:escapeStr(engineering.bbly) }</textarea>
										</div>
									</div>
								</div>
								
								<div class="col-lg-12 col-sm-12 col-xs-12">
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
												<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">影响飞机电气负载数据</div>
													<div class="font-size-9 line-height-18">Impact Data</div>
												</label>
												<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
													<label style="margin-right: 20px;font-weight: normal">
														<input name="isYxfjdqfzsj" type="radio" value="1" disabled="disabled" <c:if test="${engineering.isYxfjdqfzsj eq 1 }">checked</c:if> />是
													</label> 
													
													<label style="font-weight: normal">
														<input name="isYxfjdqfzsj" type="radio" value="0" disabled="disabled" <c:if test="${engineering.isYxfjdqfzsj eq 0 }">checked</c:if> />否 
													</label> 
												</div>
									</div>
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
												<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">影响出版物</div>
													<div class="font-size-9 line-height-18">Impact Data</div>
												</label>
												<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
													<label style="margin-right: 20px;font-weight: normal">
														<input name="isYxcbw" type="radio" value="1" disabled="disabled" <c:if test="${engineering.isYxcbw eq 1 }">checked</c:if> />是
													</label> 
													
													<label style="font-weight: normal">
														<input name="isYxcbw" type="radio" value="0" disabled="disabled" <c:if test="${engineering.isYxcbw eq 0 }">checked</c:if> />否 
													</label> 
												</div>
									</div>
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
												<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">是否索赔</div>
													<div class="font-size-9 line-height-18">Claim</div>
												</label>
												<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
													<label style="margin-right: 20px;font-weight: normal">
														<input name="isSp" type="radio" value="1" disabled="disabled" <c:if test="${engineering.isSp eq 1 }">checked</c:if> />是
													</label> 
													
													<label style="font-weight: normal">
														<input name="isSp" type="radio" value="0" disabled="disabled" <c:if test="${engineering.isSp eq 0 }">checked</c:if> />否 
													</label> 
												</div>
									</div>
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
												<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">需要特殊器材</div>
													<div class="font-size-9 line-height-18">Need Special Equipment</div>
												</label>
												<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
													<label style="margin-right: 20px;font-weight: normal">
														<input name="isXytsqc" type="radio" value="1" disabled="disabled" <c:if test="${engineering.isXytsqc eq 1 }">checked</c:if> />是
													</label> 
													<label style="font-weight: normal">
														<input name="isXytsqc" type="radio" value="0" disabled="disabled" <c:if test="${engineering.isXytsqc eq 0 }">checked</c:if> />否 
													</label> 
												</div>
									</div>
								</div>
								</div>
								
							<div class="col-lg-12 col-sm-12 col-xs-12">
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
											<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 line-height-18">需要特殊工具和设备</div>
												<div class="font-size-9 line-height-18">Need Special Tools</div>
											</label>
											<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
												<label style="margin-right: 20px;font-weight: normal">
													<input name="isXytsgjsb" type="radio" value="1" disabled="disabled" <c:if test="${engineering.isXytsgjsb eq 1 }">checked</c:if> />是
												</label> 
												
												<label style="font-weight: normal">
													<input name="isXytsgjsb" type="radio" value="0" disabled="disabled" <c:if test="${engineering.isXytsgjsb eq 0 }">checked</c:if> />否 
												</label> 
											</div>
										</div> 
										
										<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
											<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 line-height-18">需要颁发器材备货通知单</div>
												<div class="font-size-9 line-height-18">Need to issue notice</div>
											</label>
											<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
												<label style="margin-right: 20px;font-weight: normal">
													<input name="isXybfqcbhtzd" type="radio" value="1" disabled="disabled" <c:if test="${engineering.isXybfqcbhtzd eq 1 }">checked</c:if> />是
												</label> 
												
												<label style="font-weight: normal">
													<input name="isXybfqcbhtzd" type="radio" value="0" disabled="disabled" <c:if test="${engineering.isXybfqcbhtzd eq 0 }">checked</c:if> />否 
												</label> 
											</div>
										</div>
								</div>
								</div>
								
								<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
									<label  class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">受影响的出版物</div>
										<div class="font-size-9 line-height-18">Affected publish</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
										<input maxlength="100" name="syxcbw" id="syxcbw" class="form-control date-picker" type="text" value="${erayFns:escapeStr(engineering.syxcbw) }" disabled="disabled">
									</div>
								</div>
							</div>
							
							<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
									<label  class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">停场时间</div>
										<div class="font-size-9 line-height-18">Stop Time</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
										<input maxlength="100" name="tcsj" id="tcsj" class="form-control date-picker" type="text" value="${erayFns:escapeStr(engineering.tcsj) }" disabled="disabled">
									</div>
								</div>
							</div>
							</form>
						</div>
			
			</div>
			
			<div class="panel panel-default">
					        <div class="panel-heading">
								    <h3 class="panel-title"><span style="color: red">*</span>执行对象 Execute Object</h3>
						   </div>
			  <div class="panel-body">
			        <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x:auto;">
				        <table class="table table-thin table-bordered table-striped table-hover text-center" 	style=" min-width: 2000px !important;">
							<thead>
							<tr>
							<th rowspan="2" width="3%"><div class="font-size-12 line-height-18" >序号 </div><div class="font-size-9 line-height-18" >No.</div></th>
							<th rowspan="2" width="9%"><div class="font-size-12 line-height-18" >执行分类</div><div class="font-size-9 line-height-18" >Category</div></th>
							<th rowspan="2" width="9%"><div class="font-size-12 line-height-18" >飞机注册号<div class="font-size-9 line-height-18" >A/C REG</div>
							<th rowspan="2" width="7%"><div class="font-size-12 line-height-18" >部件名称</div><div class="font-size-9 line-height-18" >Name</div></th>
							<th rowspan="2" width="9%"><div class="font-size-12 line-height-18" >部件件号</div><div class="font-size-9 line-height-18" >P/N</div></th>
							<th rowspan="2" width="9%"><div class="font-size-12 line-height-18" >部件序列号</div><div class="font-size-9 line-height-18" >S/N</div></th>
							<th rowspan="2" width="9%"><div class="font-size-12 line-height-18" >关联EO工单</div><div class="font-size-9 line-height-18" >EO</div></th>
							<th rowspan="2" width="4%"><div class="font-size-12 line-height-18" >添加工单</div><div class="font-size-9 line-height-18" >Add</div></th>
							<th colspan="5"><div class="font-size-12 line-height-18" >计划完成时限/Derp code</div></th>
							<th rowspan="2" width="19%"><div class="font-size-12 line-height-18" >标准工时<div class="font-size-9 line-height-18" >Plan Hours</div>
							</tr> 
							<tr>
								<th width="7%"><div class="font-size-12 line-height-18" >完成日期</div><div class="font-size-9 line-height-18" >Finished Date</div></th>
								<th width="5%"><div class="font-size-12 line-height-18" >机身时间</div><div class="font-size-9 line-height-18" >Flight HRS.</div></th>
								<th width="5%"><div class="font-size-12 line-height-18" >起落循环</div><div class="font-size-9 line-height-18" >Flight CYCS.</div></th>
								<th width="5%"><div class="font-size-12 line-height-18" >绞车时间</div><div class="font-size-9 line-height-18" >Winch HRS</div></th>
								<th width="5%"><div class="font-size-12 line-height-18" >绞车循环</div><div class="font-size-9 line-height-18" >Winch CYCS</div></th>
							</tr>
							
			         		 </thead>
							<tbody id="zxdxList">
						
							</tbody>
							<tfoot>
							<tr>
								<td colspan="13" class="text-right"><div class="font-size-12 line-height-18" >总计 </div><div class="font-size-9 line-height-18" >Total</div></td>
								<td><input class='form-control date-picker' name="zj" id='zj' disabled="disabled"></td>
							</tr>
							</tfoot>
				   			</table>
						 </div>		
					</div>
				</div>	   
	     <div class="panel panel-default">
	        <div class="panel-heading">
				    <h3 class="panel-title"><span style="color: red">*</span>审批 Approval</h3>
		   </div>
			  <div class="panel-body">
			        <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" >
				       <label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "  >
						<div class="font-size-12 line-height-18">审核意见</div>
						<div class="font-size-9 line-height-18">Review Opinion</div></label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
							<textarea class="form-control" id="shyj" disabled="disabled">${erayFns:escapeStr(engineering.shyj)}</textarea>
					</div>
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18"></div>
						<div class="font-size-9 line-height-18"></div></label>
						
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8">
						　<label style="margin-right: 50px"></label>　
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">审核人　<label style="margin-right: 50px; font-weight:normal">${erayFns:escapeStr(engineering.shr_user.displayName)}</label></div>
						<div class="font-size-9 line-height-12">Reviewer</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18">审核时间 　<label style="margin-right: 50px; font-weight:normal"> <fmt:formatDate value='${engineering.shsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
						<div class="font-size-9 line-height-12">Review Time </div>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18">批准意见</div>
						<div class="font-size-9 line-height-12">Approval Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0">
							<textarea class="form-control" id="pfyj" disabled="disabled">${erayFns:escapeStr(engineering.pfyj)}</textarea>
					</div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8">
						　
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">批准人　<label style="margin-right: 50px; font-weight:normal">${erayFns:escapeStr(engineering.pfr_user.displayName)}</label></div>
						<div class="font-size-9 line-height-12">Appr. By</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18">批准时间　<label style="margin-right: 50px; font-weight:normal"> <fmt:formatDate value='${engineering.pfsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
						<div class="font-size-9 line-height-12">Approved Time</div>
						</div>
					</div>
				       
						 </div>		
					</div>
				</div>	 
		 </div>
				  <div class="text-right margin-bottom-10">
                     	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:back()"><div
										class="font-size-12">返回</div>
									<div class="font-size-9">Back</div></button>&nbsp;&nbsp;&nbsp;&nbsp;
                     </div>
				<div class="clearfix"></div>
			</div>
		</div>
		<input id="departmentId"  style="display: none;" /> 
	<!-------alert对话框 Start-------->
	<div class="modal fade" id="alertModalPgd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:70%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">新增技术评估单</div>
							<div class="font-size-9 ">Add</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
				           <div class="clearfix"></div>	     
				                
								<!-- start:table -->
								<div  style="margin-top: 20px">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set">
												<thead>
												<tr>
												<th class="colwidth-3"><div class="font-size-12 line-height-18" >选择</div><div class="font-size-9 line-height-18" >Choice</div></th>
												<th class="colwidth-15"><div class="important"><div class="font-size-12 line-height-18" >文件编号 </div><div class="font-size-9 line-height-18" >File No.</div></div></th>
												<th class="colwidth-7"><div class="important"><div class="font-size-12 line-height-18" >评估单号</div><div class="font-size-9 line-height-18" >Assessment No.</div></div></th>
												<th class="colwidth-5"><div class="font-size-12 line-height-18" >来源</div><div class="font-size-9 line-height-18" >Source</div></th>
												<th class="colwidth-3"><div class="font-size-12 line-height-18" >机型</div><div class="font-size-9 line-height-18" >Model</div></th>
												<th class="colwidth-3"><div class="font-size-12 line-height-18" >分类</div><div class="font-size-9 line-height-18" >Category</div></th>
												<th class="colwidth-3"><div class="font-size-12 line-height-18" >版本</div><div class="font-size-9 line-height-18" >Revision</div></th>
												<th class="colwidth-25"><div class="important"><div class="font-size-12 line-height-18" >文件主题</div><div class="font-size-9 line-height-18" >Subject</div></div></th>
												<th class="colwidth-7"><div class="font-size-12 line-height-18" >生效日期</div><div class="font-size-9 line-height-18" >Effective Date</div></th>
												<th class="colwidth-13"><div class="font-size-12 line-height-18" >机型工程师</div><div class="font-size-9 line-height-18" >Engineer</div></th>
												<th class="colwidth-13"><div class="font-size-12 line-height-18" >评估期限</div><div class="font-size-9 line-height-18" >Assess period</div></th>
												<th class="colwidth-5"><div class="font-size-12 line-height-18" >评估状态</div><div class="font-size-9 line-height-18" >State</div></th>
												</tr> 
								         		 </thead>
												<tbody id="Pgdlist">
												</tbody>
									</table>
									</div>
									<div class="col-xs-12 text-center"  style="margin-top: 20px;">
										<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" id="pagination">
										</ul>
									</div>
								<!-- end:table -->
			                	<div class="modal-footer">
									<button type="button" onclick="appendPgd()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
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
					     		<div class="clearfix"></div>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	<!-- ATA章节号 -->
	<div class="modal fade" id="alertModalList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalListBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">ATA章节列表</div>
							<div class="font-size-9 ">List Of ATA</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
			            	<div class="col-lg-12 col-xs-12">
			            	
			                    <div class="clearfix"></div>	
							    <div class="text-left margin-top-10">
							    	<div style="padding-right:9px!important;" class="col-xs-8 pull-left">
										<input type="text" placeholder='Please enter a keyword...' id="keyword_search_zjh" class="form-control ">
									</div>
				                    <div style="padding-left:0!important;" class="col-xs-1 pull-left">   
										<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevisionZjh();">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div>
							    
									<div class="clearfix"></div>			
				         		</div>
				                
								<!-- start:table -->
								<div class="margin-top-10 ">
								<div class="overflow-auto">
									<table class="table table-bordered table-striped table-hover" style="">
										<thead>
									   		<tr>
												<th width="50px">
													<div class="font-size-12 notwrap">选择</div>
													<div class="font-size-9 notwrap">Choice</div>
												</th>
												<th>
													<div class="font-size-12 notwrap">ATA章节号</div>
													<div class="font-size-9 notwrap">ATA</div>
												</th>
												<th>
													<div class="font-size-12 notwrap">ATA章节名称</div>
													<div class="font-size-9 notwrap">Description</div>
												</th>
									 		 </tr>
										</thead>
										<tbody id="listZjh">
										
										</tbody>
									</table>
									</div>
									<div class="col-xs-12 text-center">
										<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" id="paginationZjh">
										</ul>
									</div>
								</div>
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="setModelData()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
									
				                </div>
					     		<div class="clearfix"></div>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-------ATA章节号对话框 End-------->
	
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	 <script type="text/javascript" src="${ctx}/static/js/thjw/project/engineering/view_engineering.js"></script> 
	
</body>
</html>