<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<input type="hidden" id="dprtcode" value="${scrap.dprtcode==null?user.jgdm:scrap.dprtcode}">
			<input type="hidden" id="scrapId" value="${scrap.id}">
			<input type="hidden" id="scrapZt" value="${scrap.zt}">
			<input type="hidden" id="type" value="${type}">
			<input type="hidden" id="dlrid" value="${sessionScope.user.id}">
			<input type="hidden" id="dlrname" value="${sessionScope.user.username} ${sessionScope.user.realname}">
			<input type="hidden" id="dlrbm" value="${sessionScope.user.jgdm}">
			<div class="panel-body">
				<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
					<div class="font-size-16 line-height-18">基本信息</div>
					<div class="font-size-9 ">Basic Info</div>
				</div>
				<form id="scrap_form">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18"><span style="color: red" class="view">*</span>申请人</div>
							<div class="font-size-9 line-height-18">Applicant</div>
						</label>
						 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" id="sqr_div">
							<div class="input-group">
								<input class="form-control" id="bfr" disabled="disabled" type="text" value="${scrap.bfr.displayName}">
								<input class="form-control" id="bfrid" name="bfrid" type="hidden" value="${scrap.bfrid}">
								<input class="form-control" id="bfbmid" type="hidden" value="${scrap.bfbmid}">
								<span class="input-group-btn">
									<button class="btn btn-primary form-control" type="button" onclick="chooseSqr()">
										<i class="icon-search"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">报废原因</div>
						<div class="font-size-9 line-height-18">Reason</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
						<textarea class="form-control" id="bfyy" name="bfyy" maxlength="300">${scrap.bfyy}</textarea>
					</div>
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">备注</div>
						<div class="font-size-9 line-height-18">Remark</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
						<textarea class="form-control" id="bz" name="bz" maxlength="300">${scrap.bz}</textarea>
					</div>
				</div>
				
				<div class="clearfix"></div>
					
				<div class="col-lg-12 padding-left-0 padding-right-0">
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="bfdh_div" style="display: none;">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">报废单号</div>
							<div class="font-size-9 line-height-18">Scrap No.</div>
						</label>
						 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
							<input type="text" class="form-control" id="bfdh" value="${scrap.bfdh}" disabled="disabled"/>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="zt_div" style="display: none;">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">状态</div>
							<div class="font-size-9 line-height-18">State</div>
						</label>
						 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
							<input type="text" class="form-control" id="zt" disabled="disabled"/>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="zdr_div" style="display: none;">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">维护人</div>
							<div class="font-size-9 line-height-18">Editor</div>
						</label>
						 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
							<input type="text" class="form-control" id="zdr" value="${scrap.zdrname}" disabled="disabled"/>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="zdsj_div" style="display: none;">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">维护时间</div>
							<div class="font-size-9 line-height-18">Edit Time</div>
						</label>
						 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
							<input type="text" class="form-control" id="zdsj" value="<fmt:formatDate value='${scrap.zdsj}' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled="disabled" data-date-format="yyyy-mm-dd"/>
						</div>
					</div>
				</form>
				<div class="clearfix"></div>
				
				<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
					<div class=" pull-left margin-right-10">
						<div class="font-size-16 line-height-18">报废部件清单</div>
						<div class="font-size-9 ">Scrap Loadinglist</div>
					</div>	
				</div>
				
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
					<table id="scrap_detail_table" class="table table-thin table-bordered table-striped table-hover table-set">
						<thead>
							<tr>
								<th class="colwidth-5 notView" style="vertical-align: middle;">
									<button class="line6" onclick="chooseJh()" style="padding:0px 6px;">
								    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
							        </button>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-18">P/N</div>
								</th>
								<th class="colwidth-20">
									<div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-18">F.Name</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-18">CH.Name</div>
								</th>
								<th class="colwidth-7">
									<div class="font-size-12 line-height-18">报废数量</div>
									<div class="font-size-9 line-height-18">Scrap Num</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">报废仓库</div>
									<div class="font-size-9 line-height-18">Store</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">报废库位</div>
									<div class="font-size-9 line-height-18">Storage Location</div>
								</th>
								<th class="colwidth-7">
									<div class="font-size-12 line-height-18">航材价值</div>
									<div class="font-size-9 line-height-18">Value</div>
								</th>
								<th class="colwidth-9">
									<div class="font-size-12 line-height-18">库存成本(元)</div>
									<div class="font-size-9 line-height-18">Cost(RMB:YUAN)</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">仓库名称</div>
									<div class="font-size-9 line-height-18">Warehouse Name</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">库位</div>
									<div class="font-size-9 line-height-18">Storage Location</div>
								</th>
								<th class="colwidth-7">
									<div class="font-size-12 line-height-18">管理级别</div>
									<div class="font-size-9 line-height-18">Level</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">S/N</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">批次号</div>
									<div class="font-size-9 line-height-18">B/N</div>
								</th>
								<th class="colwidth-10">
									<div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-18">MP/N</div>
								</th>
								<th class="colwidth-7">
									<div class="font-size-12 line-height-18">航材类型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>								
								<th class="colwidth-7">
									<div class="font-size-12 line-height-18">库存数量</div>
									<div class="font-size-9 line-height-18">Stock Num</div>
								</th>
								<th class="colwidth-7">
									<div class="font-size-12 line-height-18">可用数量</div>
									<div class="font-size-9 line-height-18">Available Num</div>
								</th>
							</tr>
						</thead>
						<tbody id="list">
							<tr class="non-choice"><td class="text-center" colspan="18">暂无数据 No data.</td></tr>
						</tbody>
					</table>
				</div>
				
				<div class="clearfix"></div>
				<div class="panel-heading margin-left-16 padding-top-1 margin-bottom-10 " style="border-bottom: 1px solid #ccc; margin-top: 20px;cursor:pointer"  id="fileUpload_title">
					<span>
						<div class="font-size-16 line-height-18">
							 附件上传
						</div>
						<div class="font-size-9">Attachment Upload</div>
					</span>
			    </div>
			   <div id="FileUploadDiv">	 	
				 <div class=" col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
					<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
							class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
						<div class="font-size-9 line-height-18">File Description</div></label>
					 <div class="col-xs-9 padding-left-8 padding-right-0" >
						<input type="text" id="wbwjm" name="wbwjm"  maxlength="90" class="form-control "  >
					</div>
				 </div>
			     <div class=" col-lg-1 col-sm-4 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
					<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-12 "  style="margin-left: 5px ;padding-left: 0"></div>
				 </div> 
				
				 <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0">
					<table class="table-set table table-bordered table-striped table-hover text-center">
						<thead>
							<tr>
								<th style="width:40px;"><div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div></th>
								<th style="width:500px;">
								<div class="font-size-12 line-height-18">文件说明</div>
									<div class="font-size-9 line-height-18">Description</div>
								</th>
								<th style="width:100px;">
								<div class="font-size-12 line-height-18">文件大小</div>
									<div class="font-size-9 line-height-18">File Size</div>
								</th>
								<th style="width:100px;"><div class="font-size-12 line-height-18">上传人</div>
									<div class="font-size-9 line-height-18">Uploader</div></th>
								<th style="width:100px;"><div class="font-size-12 line-height-18">上传时间</div>
									<div class="font-size-9 line-height-18">Upload Time</div></th>					
							</tr>
						</thead>
					    <tbody id="filelist">
							 <tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>
						</tbody>
					</table>
				  </div>
				</div>
				<div class="clearfix"></div>
				
				<div style="display: none;" class="view">
				<div class="panel-heading margin-left-16 padding-top-1 margin-bottom-10 " style="border-bottom: 1px solid #ccc; margin-top: 20px;cursor:pointer">
					<span>
						<div class="font-size-16 line-height-18">
							 审核
						</div>
						<div class="font-size-9">Audit</div>
					</span>
			    </div>
				<div class="clearfix"></div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">审核意见</div>
						<div class="font-size-9 line-height-18">Audit Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
						<textarea class="form-control" id="spyj" name="spyj" maxlength="150">${scrap.spyj}</textarea>
					</div>
				</div>
				<div class="clearfix"></div>
				</div>
				
				<div class="text-right">
					<button id="saveBtn" type="button" class="btn btn-primary padding-top-1 padding-bottom-1 notView" onclick="save()">
                    		<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
					</button>
					
                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 notView" onclick="doSubmit()">
                    		<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
					</button>
					
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 view" style="display: none;" onclick="approve()">
                    		<div class="font-size-12">通过</div>
						<div class="font-size-9">Approve</div>
					</button>
					
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 view" style="display: none;" onclick="reject()">
                    		<div class="font-size-12">驳回</div>
						<div class="font-size-9">Reject</div>
					</button>
					
                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:goToMainPage()">
                    		<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
					
                    </div>
			</div>
		</div>
	</div>
<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
<%@ include file="/WEB-INF/views/material/scrap/scrap_choosestock.jsp"%>
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
<script type="text/javascript" src="${ctx}/static/js/thjw/material/scrap/scrap_apply_modify.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>