<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改技术文件</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body>
<input id="newFileName" type="hidden"/>
<input id="relativePath" type="hidden"/>
<input id="wjdx" type="hidden"/>
<input id="fileName" type="hidden" value="${erayFns:escapeStr(technicalUpload.wbwjm)}" />
<input id="id" type="hidden" value="${technicalFile.id}" />
<input id="technicalUploadId" type="hidden" value="${technicalUpload.id}" />
<input id="oldshzltgh" type="hidden" value="${erayFns:escapeStr(technicalFile.shzltgh)}" />

<div class="page-content">

<script type="text/javascript">
var pageParam = '${param.pageParam}';
$(document).ready(function(){
//导航
Navigation(menuCode,"修改技术文件","Edit");
});
</script>
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
	<!-- 隐藏域 -->
	<input type="hidden" name="ly1" id="ly1" value="${erayFns:escapeStr(technicalFile.ly)}" >
	<input type="hidden" name="fl1" id="fl1" value="${erayFns:escapeStr(technicalFile.fl)}" >
	<input type="hidden" name="wjlx1" id="wjlx1" value="${erayFns:escapeStr(technicalFile.wjlx)}" >
	<input type="hidden" name="technicalFileJx" id="technicalFileJx" value="${erayFns:escapeStr(technicalFile.jx)}" >
	<input type="hidden" name="dprtcode" id="dprtcode" value="${technicalFile.dprtcode}" >
	<input type="hidden" name="technicalFilePgrid" id="technicalFilePgrid" value="${technicalFile.pgrid }" >
	<!-- end隐藏域 -->
			<div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-0 padding-bottom-0">
					<form id="form">
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">评估单号</div>
							<div class="font-size-9 line-height-18">Assessment NO.</div></label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<label class="form-control " style="font-weight:normal" readonly="readonly">${erayFns:escapeStr(technicalFile.pgdh)}</label>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>来源</div>
							<div class="font-size-9 line-height-18">Source</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<select id="ly" class="form-control "  name="ly">
							
							</select>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>机型</div>
							<div class="font-size-9 line-height-18">Model</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
								<select class="form-control " id="jx" onchange="jxgcs()">
							</select>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>分类</div>
							<div class="font-size-9 line-height-18">Category</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<select id="fl" class="form-control "   name="fl">
							</select>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>文件类型</div>
							<div class="font-size-9 line-height-18">File Type</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
						<select id="wjlx" class="form-control " name="wjlx">
							</select>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>版本</div>
							<div class="font-size-9 line-height-18">Revision</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							<input id="bb" class="form-control " name="bb" value="${erayFns:escapeStr(technicalFile.bb)}" maxlength="10"/>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>机型工程师</div>
							<div class="font-size-9 line-height-18">Engineer</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<select id="pgrid" class="form-control " name="pgrid">
							<c:forEach items="${users}" var="row">
							<option value="${row.id}" <c:if test="${technicalFile.pgrid eq row.id }">selected='selected'</c:if>>${erayFns:escapeStr(row.displayName)}</option>
							</c:forEach>
							</select>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>生效日期</div>
							<div class="font-size-9 line-height-18">Effective Date</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
								<input class="form-control date-picker" value="<fmt:formatDate value='${technicalFile.sxrq}' pattern='yyyy-MM-dd' />" id="sxrq" name="sxrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18"><span style="color: red">*</span>评估期限</div>
							<div class="font-size-9 line-height-18">Assess period</div>
						</label>
						<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control  date-picker" value="<fmt:formatDate value='${technicalFile.pgqx}' pattern='yyyy-MM-dd' />" id="pgqx" name="pgqx" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					
					 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div>
						</label>
						
						<div class=" col-xs-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
							<div class='input-group'>
								<input type="text"  id="wczjhName" class="form-control" disabled="disabled" value="${erayFns:escapeStr(technicalFile.zjh)} ${erayFns:escapeStr(technicalFile.zj.zwms)}"/>
								<input type="hidden"  id="wczjh" class="form-control" value="${erayFns:escapeStr(technicalFile.zjh)}"/>
							<span class='input-group-btn'>
								 <button class="btn btn-primary form-control" data-toggle="modal" onclick="openList();" type="button">
									<i class="icon-search cursor-pointer"></i>
								 </button>
							</span>
						    </div>
						</div>
					</div>
					
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>文件编号</div>
							<div class="font-size-9 line-height-18">File No.</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="shzltgh" value="${erayFns:escapeStr(technicalFile.shzltgh)}" name="shzltgh" placeholder='长度最大为30'   maxlength="30" class="form-control"  >
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label  class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>原文件</div>
								<div class="font-size-9 line-height-18">File</div>
							</label>
							<div class=" col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<div class='input-group'>
									<input type="text" id="sm" name="sm"  class="form-control" maxlength="100" value="${erayFns:escapeStr(technicalUpload.wbwjm)}" >
								<span class='input-group-btn'>
									<div id="fileuploader"  >
									</div>  
								</span>
							    </div>
							</div>
								
					</div>
					
					<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group" name="uploadFile" id="uploadFile" >
						
						<div class="col-lg-2 col-sm-2 col-xs-4  padding-top-10  input-group" >
						　	<a class="pull-right" style='color: blue;' href="javascript:downfile('${technicalUpload.id}')">附件下载</a>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					 <div class="col-lg-12 col-sm-12 col-xs-12 margin-bottom-0  padding-bottom-0 padding-left-0 padding-top-10 padding-right-0">
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-bottom-0 ">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 margin-bottom-0  padding-bottom-0 padding-left-8 padding-right-0 ">
							<textarea class="form-control" id="bz" placeholder='长度最大为300'   maxlength="300">${erayFns:escapeStr(technicalFile.bz)}</textarea>
						</div>
					</div>
					
					<div class="clearfix"></div>
					<br/>
					 </form>

                    
				</div>
				
				<div class="clearfix"></div>
				
				  <div class="col-lg-12 text-right padding-left-0 padding-right-0">
                    <button onclick="btnSave()" class="btn btn-primary padding-top-1 padding-bottom-1" ><div
									class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div></button>
                    	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()"><div
									class="font-size-12">返回</div>
								<div class="font-size-9">Back</div></button>
                    </div>
				
				
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	
	<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
	
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/technicalfile/modify_technicalfile_upload.js"></script>
</body>
</html>