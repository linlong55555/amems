<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
	
	<div id="AttachmengsList" style="display:none" class="padding-top-10" >	
	
		<div class='clearfix'></div>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-8">
						<span id="left_column" class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
							<div class="font-size-12 margin-topnew-2">附件列表</div>
							<div class="font-size-9">File List</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0" style="overflow-x: auto;">
							<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:900px" >
							<thead>
								<tr>
									<th id="colOptionhead" class="colwidth-3">
										<div id="fileuploader" class="col-lg-2 col-sm-1 col-xs-2 "  style="margin-left: 0;padding-left: 0">
										</div>
									</th>
									<th class="colwidth-3">
										<div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div>
									</th>
									<th class="colwidth-30">
										<div class="font-size-12 line-height-18" id="chinaColFileTitle">文件说明</div>
										<div class="font-size-9 line-height-18" id="englishColFileTitle">File desc</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-18">文件大小</div>
										<div class="font-size-9 line-height-18">File size</div>
									</th>
									<th class="colwidth-13">
										<div class="font-size-12 line-height-18">上传人</div>
										<div class="font-size-9 line-height-18">Operator</div></th>
									<th class="colwidth-13">
										<div class="font-size-12 line-height-18">上传时间</div>
										<div class="font-size-9 line-height-18">Operate Time</div>
									</th>
								</tr>
							</thead>
						    <tbody id="attachmentsListTbody">
								 
							</tbody>
						</table>
					</div>
				</div>	
			</div>
		<div class='clearfix'></div>
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
<script type="text/javascript" src="${ctx}/static/js/thjw/common/attachments/attachments_list_edit.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
