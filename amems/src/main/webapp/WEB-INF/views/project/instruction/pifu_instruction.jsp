<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<script type="text/javascript">
var pageParam = '${param.pageParam}';
$(document).ready(function(){
//导航
Navigation(menuCode,"批准技术指令","Approval");
});
</script>		
	<input type="hidden" name="instructionId" id="instructionId" value="${instruction.id}" >
			<input type="hidden" name="jgdm" id="jgdm" value="${user.jgdm}" />
		<input type="hidden" name="dprtcode" id="dprtcode" value="${instruction.dprtcode}" />
	<input type="hidden" name="jg" id="jg" value="0" >
		<!-------导航Start--->
		<!-- BEGIN CONTENT -->
		<div class="page-content ">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">技术指令编号</div>
							<div class="font-size-9 line-height-18">T/O No.</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="jszlh" name="jszlh" value="${erayFns:escapeStr(instruction.jszlh) }" disabled="disabled" />
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">发出人</div>
							<div class="font-size-9 line-height-18">Sender</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class="form-control " id="fcrid" name="fcrid" disabled="disabled">
								<option value="">please choose</option>
								<c:forEach items="${userList}" var="user">
									 <option value="${user.id}" <c:if test="${instruction.fcrid eq user.id }">selected='selected'</c:if>>${user.displayName}</option> 
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">机型</div>
							<div class="font-size-9 line-height-18">Model</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="jx"  name="jx"  maxlength="30" class="form-control" disabled="disabled" value="${erayFns:escapeStr(instruction.jx)}" >
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 "><div
								class="font-size-12 line-height-18">发出日期</div>
							<div class="font-size-9 line-height-18">Send Date</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="fcrq" name="fcrq" disabled="disabled" data-date-format="yyyy-mm-dd" type="text" value="<fmt:formatDate value='${instruction.fcrq}' pattern='yyyy-MM-dd' />" />
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">接收人</div>
							<div class="font-size-9 line-height-18">Receiver</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class="form-control " id="jsrid" name="jsrid" onchange="obtainDrptment()" disabled="disabled">
								<option value="">please choose</option>
								<c:forEach items="${userList}" var="user">
									 <option value="${user.id}" <c:if test="${instruction.jsrid eq user.id }">selected='selected'</c:if>>${user.displayName}</option> 
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">接收人部门</div>
							<div class="font-size-9 line-height-18">Receiver Dept</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="jsrbmid"  name="jsrbmid"  maxlength="30" class="form-control"  disabled="disabled">
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">版本</div>
							<div class="font-size-9 line-height-18">Revision</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="bb"  name="bb"  maxlength="30" class="form-control" disabled="disabled" value="${erayFns:escapeStr(instruction.bb)}" >
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">状态</div>
							<div class="font-size-9 line-height-18">State</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker"  id="ztText" name="ztText" type="text" disabled="disabled" value="${instruction.ztText }" >
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">制单人</div>
							<div class="font-size-9 line-height-18">Creator</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="zdr" name="zdr" value="${instruction.zdr_user.displayName }" disabled="disabled" />
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">制单时间</div>
							<div class="font-size-9 line-height-18">Create Time</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="zdsj"  name="zdsj" class="form-control datepicker " disabled="disabled" 
							 value="<fmt:formatDate value='${instruction.zdsj}' pattern='yyyy-MM-dd HH:mm:ss' />" />
						</div>
					</div>
					
				<form id="form">
				
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 form-group ">
									<div class="font-size-12 line-height-18">
										关联评估单</div>
									<div class="font-size-9 line-height-18">Evaluation</div>
								</label>

								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group" style="overflow-x:auto;">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:900px">
											<thead>
											<tr>
											<th class="colwidth-7"><div class="font-size-12 line-height-18" >评估单号 </div><div class="font-size-9 line-height-18" >Assessment No.</div></th>
											<th class="colwidth-5"><div class="font-size-12 line-height-18" >来源 </div><div class="font-size-9 line-height-18" >Source</div></th>
											<th class="colwidth-15"><div class="font-size-12 line-height-18" >参考资料</div><div class="font-size-9 line-height-18" >Reference Material</div></th>
											<th class="colwidth-13"><div class="font-size-12 line-height-18" >生效日期</div><div class="font-size-9 line-height-18" >Effective date</div></th>
											<th class="colwidth-13"><div class="font-size-12 line-height-18" >机型工程师</div><div class="font-size-9 line-height-18" >Engineer</div></th>
											<th class="colwidth-7"><div class="font-size-12 line-height-18" >评估期限</div><div class="font-size-9 line-height-18" >Assess period</div></th>
											<th class="colwidth-5"><div class="font-size-12 line-height-18" >评估状态</div><div class="font-size-9 line-height-18" >State</div></th>
											</tr> 
							         		 </thead>
											<tbody id="Annunciatelist">
						         		    </thead>
								</table>
								</div>
							</div>
							
				 <div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
					<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0 padding-left-0  form-group ">
						
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right  padding-right-0"><div class="font-size-12 line-height-18">主题</div>
							<div class="font-size-9 line-height-18">Subject</div>
						</label>
						 <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-20 padding-left-8 padding-right-0">
							<input maxlength="100"  name="zhut" id="zhut" class="form-control date-picker" data-bv-field="zhut" type="text" value="${erayFns:escapeStr(instruction.zhut) }" disabled="disabled">
							<small style="display: none;" class="help-block" data-bv-validator="stringLength" data-bv-for="zhut" data-bv-result="NOT_VALIDATED">Please enter a value with valid length</small>
						</div>
					</div>
				</div>
					
				<div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
					<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0 padding-left-0 margin-bottom-10 form-group ">
						
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right  padding-right-0"><div class="font-size-12 line-height-18">执行时限</div>
							<div class="font-size-9 line-height-18">Execute Period</div>
						</label>
						 <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-20 padding-left-8 padding-right-0">
							<input maxlength="100"  name="zxsx" id="zxsx" class="form-control date-picker" data-bv-field="zxsx" type="text"  value="${erayFns:escapeStr(instruction.zxsx) }" disabled="disabled">
						<small style="display: none;" class="help-block" data-bv-validator="stringLength" data-bv-for="zxsx" data-bv-result="NOT_VALIDATED">Please enter a value with valid length</small></div>
				</div>	
					<div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "  >
								<div class="font-size-12 line-height-18">颁发理由依据</div>
								<div class="font-size-9 line-height-18">Reason For This</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8  padding-right-0">
									<textarea class="form-control" id="bflyyj" disabled="disabled">${erayFns:escapeStr(instruction.bflyyj)}</textarea>
						</div>
					</div>
				
					<div class="col-lg-12 col-sm-12 col-xs-12  padding-top-10 padding-left-0 padding-right-0">
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 form-group ">
									<div class="font-size-12 line-height-18">
										工作内容</div>
									<div class="font-size-9 line-height-18">Contents</div>
								</label>
								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group" style="overflow-x:auto;">
									<table class="table table-thin table-bordered table-striped table-hover text-center" style="min-width:900px">
												<thead>
											<tr>
											<th ><div class="font-size-12 line-height-18" >工作内容</div><div class="font-size-9 line-height-18" >Contents</div></th>
											<th ><div class="font-size-12 line-height-18" >工作者</div><div class="font-size-9 line-height-18" >Worker</div></th>
											<th  valign="bottom"><div class="font-size-12 line-height-18" >工作时间</div><div class="font-size-9 line-height-18" >Work Time</div></th>
											</tr> 
							         		 </thead>
											<tbody id="GznrList">
											</tbody>
									</table>
								</div>
							</div>
							
					<div class="clearfix"></div>
					<div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 form-group padding-right-0">
							<textarea class="form-control" id="bz" name="bz"   maxlength="300" disabled="disabled">${erayFns:escapeStr(instruction.bz)}</textarea>
						</div>
					</div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 form-group">
									<div class="font-size-12 line-height-18">
										文件列表</div>
									<div class="font-size-9 line-height-18">List of Files</div>
								</label>
								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group" style="overflow-x:auto;">
									<table class="table table-thin table-bordered table-striped table-hover text-center" style="min-width:900px">
										<thead>
										<tr>
										<th>
										<div class="font-size-12 line-height-18">文件说明</div>
											<div class="font-size-9 line-height-18">File Explanation</div>
										</th>
										<th>
										<div class="font-size-12 line-height-18">文件大小</div>
											<div class="font-size-9 line-height-18">File Size</div>
										</th>
										<th><div class="font-size-12 line-height-18">上传人</div>
											<div class="font-size-9 line-height-18">Uploader</div></th>
										<th><div class="font-size-12 line-height-18">上传时间</div>
											<div class="font-size-9 line-height-18">Upload Time</div></th>					
										</tr>
										</thead>
								    	<tbody id="filelist">
										</tbody>
								</table>
								</div>
							</div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"  >
						<div class="font-size-12 line-height-18">审核意见</div>
						<div class="font-size-9 line-height-18">Review Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
							<textarea class="form-control" id="shyj" disabled="disabled">${erayFns:escapeStr(instruction.shyj)}</textarea>
					</div>
					
				<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18"></div>
						<div class="font-size-9 line-height-18"></div></label>
						
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8">
						　<label style="margin-right: 50px"></label>　
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">审核人　<label style="margin-right: 50px; font-weight:normal">${instruction.shr_user.displayName}</label></div>
						<div class="font-size-9 line-height-12">Reviewer</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18">审核时间 　<label style="margin-right: 50px; font-weight:normal"><fmt:formatDate value='${instruction.shsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
						<div class="font-size-9 line-height-12">Review Time</div>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18">批准意见</div>
						<div class="font-size-9 line-height-12">Approval Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0">
							<textarea class="form-control" id="pfyj" maxlength="150">${erayFns:escapeStr(instruction.pfyj)}</textarea>
					</div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0">
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">批准人　<label style="margin-right: 50px; font-weight:normal">${instruction.pfr_user.displayName}</label></div>
						<div class="font-size-9 line-height-12">Appr. By</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18">批准时间　<label style="margin-right: 50px; font-weight:normal"><fmt:formatDate value='${instruction.pfsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
						<div class="font-size-9 line-height-12">Approved Time</div>
						</div>
					</div>
				</div>
				
				<div class="clearfix"></div>
				
				<div class="col-lg-12 text-right padding-left-0 padding-right-0">
                     <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="agreedMainInstruction()" type="button">
                     	 <div class="font-size-12">批准通过</div>
						 <div class="font-size-9">Approved</div>
					 </button>
                    	  <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="rejectedMainInstruction()" type="button">
                     	 <div class="font-size-12">批准驳回</div>
						 <div class="font-size-9">Rejected</div>
					 </button>
					  <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="suspendMainInstruction()" type="button">
                     	 <div class="font-size-12">中止(关闭)</div>
						 <div class="font-size-9">Close</div>
					 </button>
					  <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:back()" type="button">
                     	<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					 </button>
                    </div>
				
				<div class="clearfix"></div>
				
				</form>
			</div>
		</div>
		
		<div class="modal fade" id="alertModalPgd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">新增技术评估单</div>
							<div class="font-size-9 ">add</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
				           <div class="clearfix"></div>	     
				                
								<!-- start:table -->
								<div  style="margin-top: 20px">
									<table class="table table-thin table-bordered table-striped table-hover text-center">
												<thead>
												<tr>
												<th width="60"><div class="font-size-12 line-height-18" >选择</div><div class="font-size-9 line-height-18" >Choice</div></th>
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
									<div class="col-xs-12 text-center">
										<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" id="pagination2">
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
	</div>
		
		
		
		
	</div>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/project/instruction/pifu_instruction.js"></script>
	
</body>
</html>