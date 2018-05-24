<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
	
			<div class="panel-body padding-left-0 padding-right-8 padding-bottom-0" >
				<div  class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
					<span id="left_column" class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
						<div class="font-size-12 margin-topnew-2">附件列表</div>
						<div class="font-size-9">File List</div>
					</span>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0" style="overflow-x: auto;">
						<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:600px">
							<thead>
								<tr>
									<th id="colOptionhead" style="width: 45px;">
										<div id="fileuploader" ></div>
									</th>
									<th class="colwidth-3">
										<div class="font-size-12 line-height-12">序号</div>
										<div class="font-size-9 line-height-12">No.</div>
									</th>
								   	<th>
										<div class="font-size-12 line-height-12" id="chinaColFileTitle">文件说明</div>
										<div class="font-size-9 line-height-12" id="englishColFileTitle">File desc</div>
									</th>
									<th class="colwidth-13">
										<div class="font-size-12 line-height-12">文件大小</div>
										<div class="font-size-9 line-height-12">File size</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-12">上传人</div>
										<div class="font-size-9 line-height-12">Operator</div></th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-12">上传时间</div>
										<div class="font-size-9 line-height-12">Operate Time</div>
									</th>			
								</tr>
							</thead>
						    <tbody id="attachments_list_tbody">
								 
							</tbody>
						</table>
						</div>
					</div>
			</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/attachments/attachments_list_edit_common.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
