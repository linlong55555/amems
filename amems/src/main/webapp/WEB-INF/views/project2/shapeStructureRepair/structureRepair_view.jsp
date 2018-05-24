<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>外形缺损</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	 
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<input type="hidden" value="${shapeStructureRepair.sjlx}" id="sjlx">
	<div class="page-content">
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body" >
		 		<div class="col-xs-12 " style='padding:0px;'>
					<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">飞机注册号</div>
								<div class="font-size-9">A/C REG</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control date-picker"
								 id="fjzch" name="fjzch" value="${erayFns:escapeStr(shapeStructureRepair.fjzch)}" />
							</div>
						</div>
						
                    	<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">发现日期</div>
								<div class="font-size-9">Discovery Date</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control date-picker"
								maxlength="10" data-date-format="yyyy-mm-dd" id="fxrq" name="fxrq" 
								value="<fmt:formatDate value='${shapeStructureRepair.fxrq}' pattern='yyyy-MM-dd' />" />
							</div>
						</div>
						
                    	<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">ATA章节号</div>
								<div class="font-size-9">Maint Date</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control date-picker"
								maxlength="10" data-date-format="yyyy-mm-dd" id="fxrq" name="fxrq" 
								 value="${erayFns:escapeStr(shapeStructureRepair.fixChapter.zjh)} ${erayFns:escapeStr(shapeStructureRepair.fixChapter.ywms)}"/>
							</div>
						</div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">位置</div>
								<div class="font-size-9">Positon</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="wz" name="wz" class="form-control" disabled="disabled" maxlength="160" style="height:55px">${erayFns:escapeStr(shapeStructureRepair.wz)}</textarea>
							</div>
						</div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">缺陷描述</div>
								<div class="font-size-9">Positon</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="qxms" name="qxms" class="form-control" disabled="disabled" maxlength="1300" style="height:55px">${erayFns:escapeStr(shapeStructureRepair.qxms)}</textarea>
							</div>
						</div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">照片列表</div>
								<div class="font-size-9">Version</div>
							</span>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 " style="overflow-x: auto;">
										<table  class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important'>
											<thead>
												<tr>
													<th class="colwidth-20">
														<div class="font-size-12 line-height-18">文件说明</div>
														<div class="font-size-9 line-height-18">File Desc</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 line-height-18">文件大小</div>
														<div class="font-size-9 line-height-18">File Size</div>
													</th>
													<th class="colwidth-13">
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
											<c:forEach items="${shapeStructureRepair.attachments}" var="attachments" varStatus="status">
												<tr>
													<td title="${attachments.wbwjm}" class="text-left"><a key="${attachments.id}" href="javascript:void(0)" onclick="openAttachmentWinEdit(this)">${attachments.wbwjm}.${attachments.hzm}</a></td>
													<td class="text-left">${attachments.wjdxStr}</td>
													<td class="text-left">${attachments.czrname}</td>
													<td class="text-center"><fmt:formatDate value='${attachments.czsj}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
												</tr>
											</c:forEach>	
											</tbody>
									</table>
								</div>
							</div>
						</div>
						
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修理日期</div>
								<div class="font-size-9">Maint Date</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control date-picker"
									maxlength="10" data-date-format="yyyy-mm-dd" id="xlrq" name="xlrq"
								value="<fmt:formatDate value='${shapeStructureRepair.xlrq}' pattern='yyyy-MM-dd' />"	 />
							</div>
						</div>
						
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">关闭</div>
								<div class="font-size-9">Close</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<span style=" font-weight:normal" class="pull-left">
									<input type="radio" name="is_gb" id="is_gb" value='1' <c:if test="${shapeStructureRepair.is_gb eq 1 }">checked</c:if> >是
								</span>										
								<span style=" font-weight:normal" class="pull-left padding-left-10">
									<input type="radio" name="is_gb"  value='0' id="is_gb" <c:if test="${shapeStructureRepair.is_gb eq 0 }">checked</c:if> >否
								</span>
							</div>
						</div>
						
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">加入维修方案</div>
								<div class="font-size-9">Join Maintenanc</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<span style=" font-weight:normal" class="pull-left">
									<input type="radio" name="is_jrwxfq" id="is_jrwxfq" value='1' <c:if test="${shapeStructureRepair.is_jrwxfq eq 1 }">checked</c:if> >是
								</span>										
								<span style=" font-weight:normal" class="pull-left padding-left-10">
									<input type="radio" name="is_jrwxfq"  value='0' id="is_jrwxfq" <c:if test="${shapeStructureRepair.is_jrwxfq eq 0 }">checked</c:if> >否
								</span>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修理方式</div>
								<div class="font-size-9">Repair model</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<span style=" font-weight:normal" class="pull-left">
									<input type="radio" name="xlfs" id="xlfs" value='1'  <c:if test="${shapeStructureRepair.xlfs eq 1 }">checked</c:if> >永久修理
								</span>										
								<span style=" font-weight:normal" class="pull-left padding-left-10">
									<input type="radio" name="xlfs"  value='2' id="xlfs" <c:if test="${shapeStructureRepair.xlfs eq 2 }">checked</c:if> >临时修理
								</span>
							</div>
						</div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修理依据</div>
								<div class="font-size-9">Repair Basis</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="xlyj" name="xlyj" class="form-control" disabled="disabled" maxlength="1300" style="height:55px">${erayFns:escapeStr(shapeStructureRepair.xlyj)}</textarea>
							</div>
						</div>
						<div  id="xlyjlookfile"  class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修理依据附件</div>
								<div class="font-size-9">Repair Basis Files</div>
							</span>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 " style="overflow-x: auto;">
										<table  class="table table-thin table-bordered table-striped table-hover table-set " style='margin-bottom:0px !important'>
											<thead>
												<tr>
													<th class="colwidth-20">
														<div class="font-size-12 line-height-18">文件说明</div>
														<div class="font-size-9 line-height-18">File Desc</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 line-height-18">文件大小</div>
														<div class="font-size-9 line-height-18">File Size</div>
													</th>
													<th class="colwidth-13">
														<div class="font-size-12 line-height-18">上传人</div>
														<div class="font-size-9 line-height-18">Uploader</div>
													</th>
													<th class="colwidth-13">
														<div class="font-size-12 line-height-18">上传时间</div>
														<div class="font-size-9 line-height-18">Upload Time</div>
													</th>
												</tr>
											</thead>
											<tbody id="xlyjfilelist">
												<c:forEach items="${shapeStructureRepair.xlyjAttachment}" var="attachments" varStatus="status">
													<tr>
														<td title="${attachments.wbwjm}" class="text-left"><a key="${attachments.id}" href="javascript:void(0)" onclick="openAttachmentWinEdit(this)">${attachments.wbwjm}.${attachments.hzm}</a></td>
														<td class="text-left">${attachments.wjdxStr}</td>
														<td class="text-left">${attachments.czrname}</td>
														<td class="text-center"><fmt:formatDate value='${attachments.czsj}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
													</tr>
												</c:forEach>
											</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">例行检查</div>
								<div class="font-size-9">Inspection</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<span style=" font-weight:normal" class="pull-left">
								<input type="radio" name="is_xlxjc" id="is_xlxjc" value='1' <c:if test="${shapeStructureRepair.is_xlxjc eq 1 }">checked</c:if> >是
								</span>										
								<span style=" font-weight:normal" class="pull-left padding-left-10">
								<input type="radio" name="is_xlxjc"  value='0' id="is_xlxjc" <c:if test="${shapeStructureRepair.is_xlxjc eq 0 }">checked</c:if> >否
								</span>
							</div>
						</div>
						
						<div id="lxjcjg-div" class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">例行检查间隔</div>
								<div class="font-size-9">Inspec Interval</div>
							</span>
							<div id="div-wxsb" class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control date-picker"
								maxlength="33" id="lxjcjg" name="lxjcjg" value="${erayFns:escapeStr(shapeStructureRepair.lxjcjg)}" />
							</div>
						</div>
						<div class="clearfix"></div>	
						<div  id="lookfile"  class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">照片列表</div>
								<div class="font-size-9">Version</div>
							</span>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 " style="overflow-x: auto;">
										<table  class="table table-thin table-bordered table-striped table-hover table-set " style='margin-bottom:0px !important'>
											<thead>
												<tr>
													<th class="colwidth-20">
														<div class="font-size-12 line-height-18">文件说明</div>
														<div class="font-size-9 line-height-18">File Desc</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 line-height-18">文件大小</div>
														<div class="font-size-9 line-height-18">File Size</div>
													</th>
													<th class="colwidth-13">
														<div class="font-size-12 line-height-18">上传人</div>
														<div class="font-size-9 line-height-18">Uploader</div>
													</th>
													<th class="colwidth-13">
														<div class="font-size-12 line-height-18">上传时间</div>
														<div class="font-size-9 line-height-18">Upload Time</div>
													</th>
												</tr>
											</thead>
											<tbody id="xlhfilelist">
												<c:forEach items="${shapeStructureRepair.xlhAttachment}" var="attachments" varStatus="status">
													<tr>
														<td title="${attachments.wbwjm}" class="text-left"><a key="${attachments.id}" href="javascript:void(0)" onclick="openAttachmentWinEdit(this)">${attachments.wbwjm}.${attachments.hzm}</a></td>
														<td class="text-left">${attachments.wjdxStr}</td>
														<td class="text-left">${attachments.czrname}</td>
														<td class="text-center"><fmt:formatDate value='${attachments.czsj}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
													</tr>
												</c:forEach>
											</tbody>
									</table>
								</div>
							</div>
						</div>
							<div class="clearfix"></div>	
						
						
        	</div>
		</div>		
	</div>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript" src="${ctx }/static/js/thjw/project2/shapeStructureRepair/structureRepair_view.js"></script>
</body>
</html>
