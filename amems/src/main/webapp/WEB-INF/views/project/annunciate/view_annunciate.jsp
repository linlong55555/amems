<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script type="text/javascript">
	var pageParam = '${param.pageParam}';
	</script>
</head>
<body>
	<body class="page-header-fixed">
		<input type="hidden" name="annunciatezlbh" id="annunciatezlbh" value=${erayFns:escapeStr(annunciate.jstgh)} >
		<input type="hidden" name="annunciateId" id="annunciateId" value=${annunciate.id} >
		<input type="hidden" name="userId" id="userId" value=${user.id} >
		<input type="hidden" name="annunciateZt" id="annunciateZt" value=${annunciate.zt} >
		<input type="hidden" name="jgdm" id="jgdm" value=${user.jgdm}>
		<input type="hidden" name="dprtcode" id="dprtcode" value=${annunciate.dprtcode}>
		<!-------导航Start--->
		<!-- BEGIN HEADER -->
<script type="text/javascript">
$(document).ready(function(){
//导航
Navigation(menuCode,"查看维护提示","View");
});
</script>
	<div class="clearfix"></div>
	
	<!-- BEGIN CONTAINER -->

		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">维护提示编号</div>
							<div class="font-size-9 line-height-18">T/B No.</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="tgzlbh" name="tgzlbh" type="text" value=${erayFns:escapeStr(annunciate.jstgh)} disabled="disabled" />
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">制单人</div>
							<div class="font-size-9 line-height-18">Creator</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker"  id="zdr" name="zdr" data-date-format="yyyy-mm-dd" type="text" disabled="disabled" value="${annunciate.zdr_user.displayName}" />
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">制单时间</div>
							<div class="font-size-9 line-height-18">Create Time</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="zdsj"  name="zdsj" class="form-control datepicker " disabled="disabled" 
							 value="<fmt:formatDate value='${annunciate.zdsj}' pattern='yyyy-MM-dd HH:mm:ss' />" />
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">制单部门</div>
							<div class="font-size-9 line-height-18">Department</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker"  id="zdbm" name="zdbm" data-date-format="yyyy-mm-dd" type="text" disabled="disabled" value="${erayFns:escapeStr(annunciate.bm_dprt.dprtname) }" />
						</div>
					</div> 
					
			<form id="form">
			
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
					<div class="col-lg-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0">
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 form-group ">
									<div class="font-size-12 line-height-18">
										关联评估单</div>
									<div class="font-size-9 line-height-18">Evaluation</div>
								</label>

								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group" style="overflow-x:auto;">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set"  style="min-width:900px">
											<thead>
											<tr>
											</th> 
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
					<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0 padding-left-0 margin-bottom-10 form-group ">
						
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right  padding-right-0"><div class="font-size-12 line-height-18">主题</div>
							<div class="font-size-9 line-height-18">Subject</div>
						</label>
						 <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-20 padding-left-8 padding-right-0">
								<input maxlength="100" placeholder="长度最大为100" name="zhut" id="zhut" class="form-control date-picker" data-bv-field="zhut" type="text" disabled="disabled" value="${erayFns:escapeStr(annunciate.zhut) }">
							<small style="display: none;" class="help-block" data-bv-validator="stringLength" data-bv-for="zhut" data-bv-result="NOT_VALIDATED">Please enter a value with valid length</small>
						</div>
					</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">通告期限</div>
							<div class="font-size-9 line-height-18">Bulletin Period</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker"  id="tgqx" name="tgqx" data-date-format="yyyy-mm-dd" type="text" disabled="disabled" value=<fmt:formatDate value='${annunciate.tgqx }' pattern='yyyy-MM-dd' /> />
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">生效日期</div>
							<div class="font-size-9 line-height-18">Effective Date</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker"  id="sxrq" name="sxrq" data-date-format="yyyy-mm-dd" type="text" disabled="disabled" value=<fmt:formatDate value='${annunciate.sxrq }' pattern='yyyy-MM-dd' /> >
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">版本</div>
							<div class="font-size-9 line-height-18">Revision</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker"  id="bb" name="bb" type="text" disabled="disabled" value="${erayFns:escapeStr(annunciate.bb) }" >
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">状态</div>
							<div class="font-size-9 line-height-18">State</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker"  id="ztText" name="ztText" type="text" disabled="disabled" value="${annunciate.ztText }" >
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group"  >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">圈阅人员</div>
							<div class="font-size-9 line-height-18">Markup Person</div></label>
						 <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0" >
							<select class=" " id="userids" name="userids" multiple="multiple" disabled="disabled">
									<c:forEach items="${userListYes}" var="type">
										<option value="${type.id},${type.bmdm}" selected="selected">${type.username} ${type.realname}</option>
									</c:forEach>
									 <c:forEach items="${userListNo}" var="type">
										<option value="${type.id},${type.bmdm}">${type.username} ${type.realname}</option>
									</c:forEach> 
							</select>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 " >
						<div class="font-size-12 line-height-18">内容</div>
						<div class="font-size-9 line-height-18">Contents</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8  form-group padding-right-0">
						<textarea class="form-control" id="nr" name="nr" maxlength="300" disabled="disabled">${erayFns:escapeStr(annunciate.nr)}</textarea>
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
							
					<div class="clearfix"></div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "  >
						<div class="font-size-12 line-height-18">审核意见</div>
						<div class="font-size-9 line-height-18">Review Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
							<textarea class="form-control" id="shyj" disabled="disabled" >${erayFns:escapeStr(annunciate.shyj)}</textarea>
					</div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18"></div>
						<div class="font-size-9 line-height-18"></div></label>
						
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8">
						　<label style="margin-right: 50px"></label>　
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">审核人　<label style="margin-right: 50px; font-weight:normal">${annunciate.shr_user.displayName}</label></div>
						<div class="font-size-9 line-height-12">Reviewer</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18">审核时间 　<label style="margin-right: 50px; font-weight:normal"> <fmt:formatDate value='${annunciate.shsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
						<div class="font-size-9 line-height-12">Review Time </div>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18">批准意见</div>
						<div class="font-size-9 line-height-12">Approval Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0">
							<textarea class="form-control" id="pfyj" disabled="disabled">${erayFns:escapeStr(annunciate.pfyj)}</textarea>
					</div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0">
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">批准人　<label style="margin-right: 50px; font-weight:normal">${annunciate.pfr_user.displayName}</label></div>
						<div class="font-size-9 line-height-12">Appr. By</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18">批准时间　<label style="margin-right: 50px; font-weight:normal"> <fmt:formatDate value='${annunciate.pfsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
						<div class="font-size-9 line-height-12">Approved Time</div>
						</div>
					</div>
				</div>
				
				<div class="clearfix"></div>
				
				<div class="col-lg-12 text-right padding-left-0 padding-right-0">
                    <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="printAnnunciate()">
                     		<div class="font-size-12">打印</div>
							<div class="font-size-9">Print</div>
					</button>
					<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()">
                    		<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
                    </div>
                     
				<div class="clearfix"></div>
				</form>
			</div>
		</div>
		<input id="departmentId"  style="display: none;" /> 
	
	</div>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<script type="text/javascript" src="${ctx}/static/js/thjw/project/annunciate/view_annunciate.js"></script>
</body>
</html>