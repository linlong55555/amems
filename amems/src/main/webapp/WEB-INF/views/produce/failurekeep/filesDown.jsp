<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="FilesDownModal" tabindex="-1" role="dialog"  aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style='width:60%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
   				<h4 class="modal-title" >
             		<div class='pull-left'>
	          			<div class="font-size-14 " id="titleName">附件列表</div>
						<div class="font-size-12" id="titleEname">Files</div> 
					</div>
			 	  	<div class='pull-right' >
			  			<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
		  		  	</div>
					<div class='clearfix'></div>
  				</h4>
			</div>
            <div class="modal-body" id="AssignEndModalBody">
 		    <div class="col-xs-12 margin-top-8 padding-left-10 padding-right-8">
				<div id="attachments_list_edit1" style="display:none">
			<div id="attach_div" class="panel panel-primary">
			<div class="panel-body padding-left-8 padding-right-8" style='padding-bottom:0px;'>
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
						<div class="clearfix"></div>
					</div>
				</div>
				</div>
				</div>
			    <div class="clearfix"></div>              
			</div>
        	 <div class="modal-footer ">
				<div class="col-xs-12 padding-left-8 padding-right-0" >
					<span class="pull-left modalfootertip" >
		                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
					</span>
					<div class="pull-right margin-right-8">
					   <button id="baocun" type="button" onclick="FilesDownModal.setData();" class="fjbtn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
						</button>
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
						</button>
					</div>
				</div>
			 </div>
   		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/failurekeep/filesDown.js"></script>