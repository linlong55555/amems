<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>查看修订通知书</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<script type="text/javascript">
$(document).ready(function(){
	 var zt=$('#revisionNoticeBookZt').val();
	//比较接收是否为当前查看人
	if(zt==1 || zt==2 || zt==3){
		var xdrid=$('#xdrid').val();
		var userid=$('#userId').val();
		var id=$('#revisionNoticeBookId').val();
		 if(xdrid==userid){
			var obj={};
			obj.id=id;
			obj.jszt=1;
			//修改接收状态
			updateJszt(obj);
		} 
	} 
});
</script>	
		<!-------导航Start--->
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading">
			<div id="NavigationBar"></div>
				</div>
			<div class="panel-body">
					<input type="hidden" id="zdrid" value="${user.id}" />
					<input type="hidden" id="tzslx" value="${revisionNoticeBook.tzslx}" />
					 <input id="jg" type="hidden" value="1" />
				    <input class="form-control " value="${revisionNoticeBook.id}"  id="revisionNoticeBookId" type="hidden" />  
				    <input id="dprtcode" type="hidden" value="${user.jgdm}" />
				    <input type="hidden" id="userId" name="userId" value="${user.id}" />
				     <input type="hidden" name="jgdm" id="jgdm" value="${user.jgdm}" />
					<input type="hidden" name="dprtcode1" id="dprtcode1" value="${revisionNoticeBook.dprtcode}" />
				    <input id="revisionNoticeBookZt" type="hidden" name="revisionNoticeBookZt" value="${revisionNoticeBook.zt}" />
				    
				    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">修订编号</div>
							<div class="font-size-9 line-height-18">A/N No.</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="xdtzsbh"  name="xdtzsbh" class="form-control datepicker " disabled="disabled" value="${erayFns:escapeStr(revisionNoticeBook.jszlh) }" />
						</div>
					</div>
					
				    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">制单人</div>
							<div class="font-size-9 line-height-18">Creator</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="zdrid"  name="zdrid" class="form-control datepicker " disabled="disabled" value="${revisionNoticeBook.zdr_user.displayName }" />
						</div>
					</div>
					
				    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">制单时间</div>
							<div class="font-size-9 line-height-18">Create Time</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="zdsj"  name="zdsj" class="form-control datepicker " disabled="disabled" 
							 value="<fmt:formatDate value='${revisionNoticeBook.zdsj}' pattern='yyyy-MM-dd HH:mm:ss' />" />
						</div>
					</div>
					
				    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">制单部门</div>
							<div class="font-size-9 line-height-18">Department</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="xdtzsbh"  name="xdtzsbh" class="form-control datepicker " disabled="disabled" value="${erayFns:escapeStr(revisionNoticeBook.bm_dprt.dprtname) }" />
						</div>
					</div>
					
				    <div class="clearfix"></div>
				    
				    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>机型</div>
							<div class="font-size-9 line-height-18">Model</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						    <input type="text" id="jx"  name="jx"  maxlength="30" class="form-control" disabled="disabled" value="${erayFns:escapeStr(revisionNoticeBook.jx)}" >
						</div>
					</div>
					
					
				    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>修订人</div>
							<div class="font-size-9 line-height-18">Revised By</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class='form-control' id='xdrid' name="xdrid" disabled="disabled">
									<option value=''>请选择Choice</option>
									<c:forEach items="${xdrList}" var="item">
									  	  <option value="${item.id}"
									  	  <c:if test="${revisionNoticeBook.xdrid == item.id }">selected="true"</c:if>
									  	  >${item.displayName}</option>
									</c:forEach>
						    </select>
						</div>
					</div>
					
				    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>修订期限</div>
							<div class="font-size-9 line-height-18">Revision Period</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="xdqx"  name="xdqx" class="form-control datepicker " style="width: 100%"  data-date-format="yyyy-mm-dd"  placeholder="请选择日期" disabled="disabled"
							  value="<fmt:formatDate value='${revisionNoticeBook.xdqx}' pattern='yyyy-MM-dd' />"  />
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">版本</div>
							<div class="font-size-9 line-height-18">Revision</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="bb"  name="bb" class="form-control datepicker " style="width: 100%" disabled="disabled" value="${erayFns:escapeStr(revisionNoticeBook.bb) }" />
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">状态</div>
							<div class="font-size-9 line-height-18">State</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker"  id="ztText" name="ztText" type="text" disabled="disabled" value="${revisionNoticeBook.ztText }" >
						</div>
					</div>
					
				    <div class="clearfix"></div>
				    
				<form id="form" >
				
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 form-group ">
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>
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
					<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0 padding-left-0 form-group ">
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right  padding-right-0"><div class="font-size-12 line-height-18"><span style="color: red">*</span>修订主题</div>
							<div class="font-size-9 line-height-18">Subject</div>
						</label>
						 <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-20 padding-left-8 padding-right-0">
							<input maxlength="100" placeholder="长度最大为100" name="xdzt" id="xdzt" class="form-control date-picker" data-bv-field="xdzt" type="text" value="${erayFns:escapeStr(revisionNoticeBook.xdzt)}" disabled="disabled">
							<small style="display: none;" class="help-block" data-bv-validator="stringLength" data-bv-for="xdzt" data-bv-result="NOT_VALIDATED">Please enter a value with valid length</small>
						</div>
					</div>
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
							<div class="font-size-12 line-height-18">修订内容</div>
							<div class="font-size-9 line-height-18">Revision Contents</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 form-group padding-right-0">
							<textarea class="form-control" id="xdnr" name="xdnr"  maxlength="300" disabled="disabled" >${erayFns:escapeStr(revisionNoticeBook.xdnr)}</textarea>
					</div>
				</div>	
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 form-group padding-right-0">
							<textarea class="form-control" id="bz" name="bz"  maxlength="150" disabled="disabled" >${erayFns:escapeStr(revisionNoticeBook.bz)}</textarea>
					</div>
				</div>
					
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 form-group">
									<div class="font-size-12 line-height-18">
										文件列表</div>
									<div class="font-size-9 line-height-18">List of Files</div>
								</label>
								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group" style="overflow-x:auto;">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:900px">
										<thead>
											<tr>
												<th class="colwidth-30">
													<div class="font-size-12 line-height-18">文件说明</div>
													<div class="font-size-9 line-height-18">File Explanation</div>
												</th>
												<th class="colwidth-5">
													<div class="font-size-12 line-height-18">文件大小</div>
													<div class="font-size-9 line-height-18">File Size</div>
												</th>
												<th class="colwidth-10">
													<div class="font-size-12 line-height-18">上传人</div>
													<div class="font-size-9 line-height-18">Uploader</div>
												</th>
												<th class="colwidth-13">
													<div class="font-size-12 line-height-18">上传时间</div>
													<div class="font-size-9 line-height-18">Upload Time</div>
												</th>					
											</tr>
										</thead>
									    <tbody id="filelist">
											 
										</tbody>
									</table>
							</div>
						</div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "  >
						<div class="font-size-12 line-height-18">审核意见</div>
						<div class="font-size-9 line-height-18">Review Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8  padding-right-0">
							<textarea class="form-control" id="shyj" disabled="disabled">${erayFns:escapeStr(revisionNoticeBook.shyj)}</textarea>
					</div>
					
					<div class="clearfix"></div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18"></div>
						<div class="font-size-9 line-height-18"></div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0">
						　<label style="margin-right: 50px"></label>　
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">审核人　<label style="margin-right: 50px; font-weight:normal">${revisionNoticeBook.shr_user.displayName}</label></div>
						<div class="font-size-9 line-height-12">Reviewer</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18">审核时间 　<label style="margin-right: 50px; font-weight:normal"><fmt:formatDate value='${revisionNoticeBook.shsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
						<div class="font-size-9 line-height-12">Review Time </div>
					</div>
					
					</div>
					
					<div class="clearfix"></div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18">批准意见</div>
						<div class="font-size-9 line-height-12">Approval Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0">
							<textarea class="form-control" id="pfyj" disabled="disabled">${erayFns:escapeStr(revisionNoticeBook.pfyj)}</textarea>
					</div>
					
					<div class="clearfix"></div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8">
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">批准人　<label style="margin-right: 50px; font-weight:normal">${revisionNoticeBook.pfr_user.displayName}</label></div>
						<div class="font-size-9 line-height-12">Appr. By</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18">批准时间　<label style="margin-right: 50px; font-weight:normal"><fmt:formatDate value='${revisionNoticeBook.pfsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></div>
						<div class="font-size-9 line-height-12">Approved Time</div>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="padding-top-8" id='wc' style="display:none">
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">实际完成日期</div>
							<div class="font-size-9 line-height-18">Finished Date</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8">
							<input type="text" id="wcrq"  name="wcrq" class="form-control datepicker " style="width: 100%"  data-date-format="yyyy-mm-dd"  placeholder="请选择日期" disabled="disabled"
							  value="<fmt:formatDate value='${revisionNoticeBook.wcrq}' pattern='yyyy-MM-dd' />"  />
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">说明</div>
							<div class="font-size-9 line-height-18">Explanation</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8">
							<input type="text" id="zdjsyy"  name="zdjsyy" class="form-control datepicker " style="width: 100%" disabled="disabled"
							  value='${erayFns:escapeStr(revisionNoticeBook.zdjsyy)}' />
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">关闭人</div>
							<div class="font-size-9 line-height-18">Close Person</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8">
							<input type="text" id="xdqx"  name="xdqx" class="form-control datepicker " style="width: 100%" disabled="disabled"
							  value='${erayFns:escapeStr(revisionNoticeBook.zdjs_user.displayName)}' />
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">关闭时间</div>
							<div class="font-size-9 line-height-18">Close Time</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8">
							<input type="text" id="xdqx"  name="xdqx" class="form-control datepicker " style="width: 100%"  data-date-format="yyyy-mm-dd"  placeholder="请选择日期" disabled="disabled"
							  value="<fmt:formatDate value='${revisionNoticeBook.zdjsrq}' pattern='yyyy-MM-dd HH:mm:ss ' />"  />
						</div>
					</div>
					
					</div>
				  <div class="text-right margin-bottom-10">
                  </div>
				<div class="clearfix"></div>
				</form>
			</div>
		</div>

		<!-- 编辑维修方案End -->

		<!-------alert对话框 Start-------->
		<div class="modal fade" id="alertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="alertModalBody"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-------alert对话框 End-------->

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
										<div class="font-size-9">OK</div>
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
		<!-------alert对话框 End-------->
	</div>
<script type="text/javascript">
Navigation(menuCode,"查看修订通知书","View");
</script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<script type="text/javascript" src="${ctx}/static/js/thjw/project/revisionNoticeBook/view_revisionNoticeBook.js"></script>

</body>
</html>