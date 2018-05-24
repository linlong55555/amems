<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
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
<input type="hidden"  id="engineeringId" class="form-control" value="${engineering.id}" />
<input type="hidden"  id="engineeringGczlbh" class="form-control" value="${erayFns:escapeStr(engineering.gczlbh)}" />
<input type="hidden"  id="engineeringDprtcode" class="form-control" value="${erayFns:escapeStr(engineering.dprtcode)}" />
<input type="hidden" name="jgdm" id="jgdm" value="${user.jgdm}" />
<input type="hidden" name="dprtcode" id="dprtcode" value="${engineering.dprtcode}" />
<input type="hidden" name="engineeringFjjx" id="engineeringFjjx" value="${erayFns:escapeStr(engineering.fjjx)}" />
<!-- end隐藏域 -->
<script type="text/javascript">
var pageParam = '${param.pageParam}';
$(document).ready(function(){
//导航
Navigation(menuCode,"修改EO指令","Edit");
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
						<form id="form">
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
										<select class="form-control " id="jx" name="jx">
										</select>
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10">
										<label class="col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">ATA章节号</div>
											<div class="font-size-9 line-height-18">ATA</div>
										</label>
										
										<div class=" col-xs-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group ">
										<div class='input-group'>
											<input type="text"  id="zjhName" class="form-control" disabled="disabled"/>
											<input type="hidden"  id="zjh" class="form-control" value="${erayFns:escapeStr(engineering.zjh)}" />
											<span class='input-group-btn'>
												<a href="#" onclick="openChapterWin()" data-toggle="modal"
													class="btn btn-primary padding-top-4 padding-bottom-4" > <i class="icon-search cursor-pointer"></i>
												</a>
											</span>
									    </div>
										</div>
								</div>			
									
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
												class="font-size-12 line-height-18">版本</div>
											<div class="font-size-9 line-height-18">Revision</div></label>
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input class="form-control date-picker" id="bb" name="bb" type="text" value="${erayFns:escapeStr(engineering.bb)}" maxlength="5" />
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
											<input type="hidden" name="engineeringEolx" id="engineeringEolx" value="${erayFns:escapeStr(engineering.eolx) }" >
											<select class=" " id="eolx" name="eolx" multiple="multiple">
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
											<input class="form-control date-picker" id="zt" name="zt" type="text" maxlength="50" disabled="disabled" value="${erayFns:escapeStr(engineering.ztText) }" />
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
										选择评估单
									</div>
									<div class="font-size-9 line-height-18">Evaluation</div>
								</label>

								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group" style="overflow-x:auto;">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:900px">
										<thead>
											<tr>
												<th class="colwidth-3">
												<button class="line6 " onclick="openPgd()" type="button">
													<i class="icon-search cursor-pointer color-blue cursor-pointer" ></i>
												</button>
												</th>
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
							
								<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
									
									<label  class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>主题</div>
										<div class="font-size-9 line-height-18">Subject</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
										<input maxlength="100" name="zhut" id="zhut" class="form-control date-picker" data-bv-field="zhut" type="text" value="${erayFns:escapeStr(engineering.zhut)}">
									</div>
								</div>
							</div>
							
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-right-0">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						
							<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">相关改装号</div>
								<div class="font-size-9 line-height-18">MOD No.</div></label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control date-picker" id="xggzh" name="xggzh" type="text" value="${erayFns:escapeStr(engineering.xggzh) }" maxlength="50" />
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">重检</div>
							<div class="font-size-9 line-height-18">Reinspection</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text"  id="cj" name="cj" maxlength="100" class="form-control" value="${erayFns:escapeStr(engineering.cj)}" />
							</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">重量与平衡影响</div>
							<div class="font-size-9 line-height-18">Weight And Balance</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
							<label style="margin-right: 20px;font-weight: normal">
								<input name="isZlphyx" type="radio" value="1" <c:if test="${engineering.isZlphyx eq 1 }">checked</c:if> />是
							</label> 
							<label style="font-weight: normal">
								<input name="isZlphyx" type="radio" value="0" <c:if test="${engineering.isZlphyx eq 0 }">checked</c:if> />否
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
								<input name="isCfzxsx" type="radio" value="1" <c:if test="${engineering.isCfzxsx eq 1 }">checked</c:if> />是
							</label> 
							<label style="font-weight: normal">
								<input name="isCfzxsx" type="radio" value="0" <c:if test="${engineering.isCfzxsx eq 0 }">checked</c:if> />否 
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
											<textarea class="form-control" id="txyj" name="txyj"  maxlength="300">${erayFns:escapeStr(engineering.txyj) }</textarea>
										</div>
									</div>
								</div>
								
								<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0">
									<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div
												class="font-size-12 line-height-18">颁发本工程指令理由</div>
											<div class="font-size-9 line-height-18">Reason</div></label>
										<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
											<textarea class="form-control" id="bbly" name="bbly"  maxlength="300">${erayFns:escapeStr(engineering.bbly) }</textarea>
										</div>
									</div>
								</div>
								
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								
											<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
												<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">影响飞机电气负载数据</div>
													<div class="font-size-9 line-height-18">Impact Data</div>
												</label>
												<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
													<label style="margin-right: 20px;font-weight: normal">
														<input name="isYxfjdqfzsj" type="radio" value="1" <c:if test="${engineering.isYxfjdqfzsj eq 1 }">checked</c:if> />是
													</label> 
													
													<label style="font-weight: normal">
														<input name="isYxfjdqfzsj" type="radio" value="0" <c:if test="${engineering.isYxfjdqfzsj eq 0 }">checked</c:if> />否 
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
														<input name="isYxcbw" type="radio" value="1" <c:if test="${engineering.isYxcbw eq 1 }">checked</c:if> />是
													</label> 
													
													<label style="font-weight: normal">
														<input name="isYxcbw" type="radio" value="0" <c:if test="${engineering.isYxcbw eq 0 }">checked</c:if> />否 
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
														<input name="isSp" type="radio" value="1" <c:if test="${engineering.isSp eq 1 }">checked</c:if> />是
													</label> 
													
													<label style="font-weight: normal">
														<input name="isSp" type="radio" value="0" <c:if test="${engineering.isSp eq 0 }">checked</c:if> />否 
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
														<input name="isXytsqc" type="radio" value="1" <c:if test="${engineering.isXytsqc eq 1 }">checked</c:if> />是
													</label> 
													
													<label style="font-weight: normal">
														<input name="isXytsqc" type="radio" value="0" <c:if test="${engineering.isXytsqc eq 0 }">checked</c:if> />否 
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
													<input name="isXytsgjsb" type="radio" value="1" <c:if test="${engineering.isXytsgjsb eq 1 }">checked</c:if> />是
												</label> 
												
												<label style="font-weight: normal">
													<input name="isXytsgjsb" type="radio" value="0" <c:if test="${engineering.isXytsgjsb eq 0 }">checked</c:if> />否 
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
													<input name="isXybfqcbhtzd" type="radio" value="1" <c:if test="${engineering.isXybfqcbhtzd eq 1 }">checked</c:if> />是
												</label> 
												
												<label style="font-weight: normal">
													<input name="isXybfqcbhtzd" type="radio" value="0" <c:if test="${engineering.isXybfqcbhtzd eq 0 }">checked</c:if> />否 
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
										<input maxlength="100" name="syxcbw" id="syxcbw" class="form-control date-picker" type="text" value="${erayFns:escapeStr(engineering.syxcbw) }" >
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
										<input maxlength="100" name="tcsj" id="tcsj" class="form-control date-picker" type="text" value="${erayFns:escapeStr(engineering.tcsj) }">
									</div>
								</div>
							</div>
							</form>
						</div>
			</div>
			
			<div class="panel panel-default">
					        <div class="panel-heading">
								  <h3 class="panel-title"><span style="color: red">*</span>执行对象 Execute Object
									 <a href="javascript:copyTechncialfilezxdx();"  >
									 <i id="copy_wo" class='icon-copy color-blue cursor-pointer pull-right' title="引用评估单执行对象"></i>
									 </a>
								 </h3>
						   </div>
			  <div class="panel-body">
			        <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x:auto ;">
				        <table class="table table-thin table-bordered table-striped table-hover text-center" 	style=" min-width: 2000px !important;" id="addtr">
							<thead>
							<tr>
							 <th rowspan="2">
								<button class="line6 " onclick="addRotatableCol()" type="button">
									<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
								</button>
							</th>
							<th rowspan="2" width="3%"><div class="font-size-12 line-height-18" >序号 </div><div class="font-size-9 line-height-18" >No.</div></th>
							<th rowspan="2" width="10%"><div class="font-size-12 line-height-18" >执行分类</div><div class="font-size-9 line-height-18" >Category</div></th>
							<th rowspan="2" width="10%"><div class="font-size-12 line-height-18" >飞机注册号<div class="font-size-9 line-height-18" >A/C REG</div>
							<th rowspan="2" width="8%"><div class="font-size-12 line-height-18" >部件名称</div><div class="font-size-9 line-height-18" >Name</div></th>
							<th rowspan="2" width="10%"><div class="font-size-12 line-height-18" >部件件号</div><div class="font-size-9 line-height-18" >P/N</div></th>
							<th rowspan="2" width="10%"><div class="font-size-12 line-height-18" >部件序列号</div><div class="font-size-9 line-height-18" >S/N</div></th>
							<th colspan="5"><div class="font-size-12 line-height-18" >计划完成时限/Derp code</div></th>
							<th rowspan="2" width="20%"><div class="font-size-12 line-height-18" >标准工时<div class="font-size-9 line-height-18" >Plan Hours</div>
							</tr> 
							<tr>
								<th width="10%"><div class="font-size-12 line-height-18" >完成日期</div><div class="font-size-9 line-height-18" >Finished Date</div></th>
								<th width="6%"><div class="font-size-12 line-height-18" >机身时间</div><div class="font-size-9 line-height-18" >Flight HRS.</div></th>
								<th width="6%"><div class="font-size-12 line-height-18" >起落循环</div><div class="font-size-9 line-height-18" >Flight CYCS.</div></th>
								<th width="6%"><div class="font-size-12 line-height-18" >绞车时间</div><div class="font-size-9 line-height-18" >Winch HRS</div></th>
								<th width="6%"><div class="font-size-12 line-height-18" >绞车循环</div><div class="font-size-9 line-height-18" >Winch CYCS</div></th>
							</tr>
							
			         		 </thead>
							<tbody id="zxdxList">
						
							</tbody>
							<tfoot>
							<tr>
								<td colspan="12" class="text-right"><div class="font-size-13 line-height-18" >总计 </div><div class="font-size-9 line-height-18" >Total</div></td>
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
			        <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0">
				       <label class="col-lg-1 col-sm-1 col-xs-4 text-right padding-left-0 padding-right-0 "  >
						<div class="font-size-12 line-height-18">审核意见</div>
						<div class="font-size-9 line-height-18">Review Opinion</div></label>
						<div class="col-lg-11 col-sm-11 col-xs-8 padding-left-8 padding-right-0" >
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
						<label class="col-lg-1 col-sm-1 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18">批准意见</div>
						<div class="font-size-9 line-height-12">Approval Opinion</div></label>
						<div class="col-lg-11 col-sm-11 col-xs-8 padding-left-8 padding-top-8 padding-right-0">
							<textarea class="form-control" id="pfyj" disabled="disabled">${erayFns:escapeStr(engineering.pfyj)}</textarea>
					</div>
					
						<label class="col-lg-1 col-sm-1 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						</label>
						<div class="col-lg-11 col-sm-11 col-xs-8 padding-left-8 padding-top-8 padding-right-0">
						　
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
		 	<div class="clearfix"></div>
		 <div class="col-lg-12 text-right padding-left-0 padding-right-0">
                    <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:save()"><div
										class="font-size-12">保存</div>
									<div class="font-size-9">Save</div></button>
                    	 <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:submit()"><div
										class="font-size-12">提交</div>
									<div class="font-size-9">Submit</div></button>
									<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:back()"><div
										class="font-size-12">返回</div>
									<div class="font-size-9">Back</div></button>
                    </div>
				<div class="clearfix"></div>
			</div>
		</div>
		<input id="departmentId"  style="display: none;" /> 
	</div>
	<!-- 	选择评估单的模态框 -->
	<div class="modal fade" id="alertModalPgd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:70%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">选择技术评估单</div>
							<div class="font-size-9 ">Choice</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
								<div class="col-lg-9 pull-right padding-right-0">
									<div class=" pull-right padding-left-0 padding-right-0 padding-top-10">
										<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
											<input type="text" placeholder="文件编号/评估单号/文件主题" id="keyword_search" class="form-control ">
										</div>
					                    <div class=" pull-right padding-left-5 padding-right-0 ">   
											<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
												<div class="font-size-12">搜索</div>
												<div class="font-size-9">Search</div>
					                   		</button>
					                    </div> 
									</div>
					            </div>
					            
					            
					            
					            <div class="clearfix"></div>
					            
				                
								<!-- start:table -->
								<div style="margin-top: 10px " >
								<div style="overflow-x: auto;width: 100%;">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width: 1600px !important">
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
									<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
									</div>
								<!-- end:table -->
								
			                	<div class="modal-footer padding-right-0 padding-top-0" style="border-top: medium none ! important;">
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
	</div>
	<!-------评估单 End-------->
	
	
	
	<%@ include file="/WEB-INF/views/open_win/openPerformObject.jsp"%>	
	 <script type="text/javascript" src="${ctx}/static/js/thjw/project/engineering/edit_engineering_bc.js"></script> 
	<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
</body>
</html>