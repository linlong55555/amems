<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
	
	<div id="AttachmengsListView" style="display:none">		
		<div id="attachHeadView" class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
			<div class=" pull-left margin-right-10" >
				<div class="font-size-16 line-height-18">附件</div>
				<div class="font-size-9 ">Attachments</div>
			</div>	
		</div>
		
	   	<div class="col-lg-12 col-sm-12 col-xs-12 col-md-12 padding-left-0 padding-right-0 tab-height-one" style="overflow-x:auto;">
			<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:750px">
				<thead>
					<tr>
						<th class="colwidth-3">
							<div class="font-size-12 line-height-18">序号</div>
							<div class="font-size-9 line-height-18">No.</div>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">文件说明</div>
							<div class="font-size-9 line-height-18">File desc</div>
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
			    <tbody id="attachmentsListViewTbody">
					 
				</tbody>
			</table>
		</div>
	</div>	
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
<script type="text/javascript" src="${ctx}/static/js/thjw/common/attachments/attachments_list_view.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
