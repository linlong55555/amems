<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
<title>Home</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body >
	<input type="hidden" id="djid" value="${planeFaule.id}" />
	<input type="hidden" id="zddwid" value="${user.bmdm}" />
	<input type="hidden" id="zdrid" value="${sessionScope.user.id}" />
	<input type="hidden" id="dprtcode" value="${planeFaule.dprtcode}" />
		<div class="page-content ">
			<div class="panel panel-primary" >
				<div class="panel-heading  "> 
				<div id="NavigationBar"></div>
				</div>
				<div class="panel-body col-xs-12">
				    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">飞机注册号</div>
							<div class="font-size-9 line-height-18">A/C REG</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							  <select class='form-control' id='fjzch' name="fjzch" disabled="disabled">
							 </select>
							 <input type="hidden" id="fjzchid" value="${erayFns:escapeStr(planeFaule.fjzch)}">
						</div>
					  </div>
					   <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">故障次数</div>
							<div class="font-size-9 line-height-18">Failure times</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-10 padding-right-0">
							<span><input type="text" class="form-control" disabled="disabled" value="${erayFns:escapeStr(planeFaule.gzcs)}" /></span>
						</div>
					  </div>
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">最近发生日期</div>
							<div class="font-size-9 line-height-18">Latest date</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-10 padding-right-0">					
							<span><input type="text" class="form-control" disabled="disabled" value="<fmt:formatDate value='${planeFaule.zjfsrq}' pattern='yyyy-MM-dd ' />" />
							</span>
						</div>
					  </div>
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">状态</div>
							<div class="font-size-9 line-height-18">Status</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-10 padding-right-0">							
							<span><input type="text" class="form-control" disabled="disabled" value="${erayFns:escapeStr(planeFaule.gbzt) eq 0?"未关闭":"关闭"}" /></span>
						</div>
					  </div>
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">维护人</div>
							<div class="font-size-9 line-height-18">Maintainer</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-10 padding-right-0">
							<span><input type="text" class="form-control" disabled="disabled" value="${erayFns:escapeStr(planeFaule.zdrname)}" /></span>
						</div>
					  </div>
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">维护时间</div>
							<div class="font-size-9 line-height-18">Maintenace Time</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-10 padding-right-0">
							<span><input type="text" class="form-control" disabled="disabled" value="<fmt:formatDate value='${planeFaule.zdsj}' pattern='yyyy-MM-dd HH:mm:ss' />" /></span>
						</div>
					  </div>
					   
					  <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">故障信息</div>
							<div class="font-size-9 line-height-18">Fault info</div></label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8  padding-right-0 " >
									<textarea class="form-control" id="gzxx" name="gzxx" placeholder='长度最大为1300' maxlength="1300" style="min-height:80px" >${erayFns:escapeStr(planeFaule.gzxx) }</textarea>
							</div>
					  </div>
					 <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 margin-bottom-10 padding-right-0 form-group">
								<label class="col-lg-1 col-sm-1 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</label>
								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8  padding-right-0 " >
									<textarea class="form-control" id="bz" name="bz" placeholder='长度最大为300' maxlength="300" style="min-height:80px" >${erayFns:escapeStr(planeFaule.bz) }</textarea>
					</div>
					</div>
					 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Enclosure</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<div class='input-group' >
								<input class="form-control"  id="wbwjm" name="wbwjm" type="text" maxlength="90" />
								<span class='input-group-btn'>
								 <div id="fileuploader"></div>
								</span>
						    </div>
						</div>
					  </div>
					  <div class="clearfix"></div>
					  <div class='col-lg-12 col-sm-12 col-xs-12 padding-left-0 margin-bottom-10 padding-right-0  '>
					  <table class="table table-thin table-bordered table-striped table-hover text-center table-set">
						<thead>
							<tr>
								<th class="colwidth-3" id="operat"><div 
										class="font-size-12 line-height-18 ">操作</div>
									<div class="font-size-9 line-height-18">Operation</div></th>
								<th class="colwidth-20">
									<div class="font-size-12 line-height-18">文件说明</div>
									<div class="font-size-9 line-height-18">File Desc</div>
								</th>
								<th class="colwidth-10">
									<div class="font-size-12 line-height-18">文件大小</div>
									<div class="font-size-9 line-height-18">File Size</div>
								</th>
								<th class="colwidth-13"><div class="font-size-12 line-height-18">上传人</div>
									<div class="font-size-9 line-height-18">Uploader</div></th>
								<th class="colwidth-13"><div class="font-size-12 line-height-18">上传时间</div>
									<div class="font-size-9 line-height-18">Upload Time</div></th>
							</tr>
						</thead>
						<tbody id="filelist">
                                       
						</tbody>
					</table>
					</div>
					  <div class="pull-right ">
						<button type="button" id="save" onclick="update()"
							class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
						<button type="button" onclick="back()"
								class="btn btn-primary padding-top-1 padding-bottom-1" data-toggle="modal">
								<div class="font-size-12">返回</div>
								<div class="font-size-9">Back</div>
						</button>
					</div>
				</div>
				<div class="clearfix"></div>
				</div>
			</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/planefaultmonitoring/planefaultmonitoring_edit.js"></script>
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>