<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<!-- 航材主数据选择	 -->
<div class="modal fade in modal-new" id="open_win_attachments_list_edit" tabindex="-1" role="dialog" aria-labelledby="open_win_attachments_list_edit" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:65%;">
		<div class="modal-content">	
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
           			<div class='pull-left'>
               			<div class="font-size-12 " id="chinaHead">附件</div>
						<div class="font-size-9" id="englishHead">Attachments</div>
				 	</div>
					<div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
          	</div>
			<div class="modal-body" style='padding-top:0px;'>
				<div class="input-group-border" style='margin-top:8px;padding-top:5px;margin-bottom:8px;'>
	            	<div class="margin-left-8 margin-right-8" style='margin-top:5px;margin-bottom:5px;'>
		         		
		         		<!-- <div id="fileHead" class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-top-10" >
							<label class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18" id="chinaFileHead"><span style="color: red"></span>文件说明</div>
								<div class="font-size-9 line-height-18" id="englishFileHead">File Desc</div>
							</label>
							<div class="col-lg-7 col-sm-7 col-xs-6 padding-left-8 padding-right-0" >
								<input type="text" id="awbwjm" name="awbwjm" placeholder='' class="form-control "  maxlength="50" > 
							</div>
							<div id="fileuploader" class="col-lg-2 col-sm-1 col-xs-2 "  style="margin-left: 0;padding-left: 0"></div>
						</div> -->
	            	
		         		<div class="clearfix"></div>
		         		
						<!-- start:table -->
					   	<div class="table-content col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;" id="searchTable">
							<table class="table table-thin table-bordered table-striped table-hover text-center table-set" >
								<thead>
									<tr>
										<th id="colOptionhead" style="width:40px">
											<div id="fileuploader"  style="margin-left: 0;padding-left: 0"></div>
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
			     		<div class="clearfix"></div>
				 	 </div>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="clearfix"></div>
          	<div class="modal-footer">
          		<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
			                 <!-- <i class='glyphicon glyphicon-info-sign alert-info'></i>警告！请不要提交。 -->
						</span>
	                   	<span class="input-group-addon modalfooterbtn">
	                     	<button id="saveBtn" type="button" onclick="open_win_attachments_list_edit.save();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
	                    	<button type="button" onclick="open_win_attachments_list_edit.close()" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
					    	</button>
	                   	</span>
              		</div><!-- /input-group -->
				</div>
			</div>
		</div>
	</div>
</div>
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/attachments_list_edit.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
