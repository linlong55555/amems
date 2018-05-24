<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/import.js"></script>

<style type="text/css">
.ajax-file-upload-statusbar{
	display: none;
}
</style>
<div class="modal fade modal-new" id="ImportModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria- hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog">
		<div class="modal-content">
		<div class="modal-header modal-header-new">
				<h4 class="modal-title" >
                <div class='pull-left'>
                    <div class="font-size-12" >导入</div>
					<div class="font-size-9" >Import</div>
		  		</div>
		  		<div class='pull-right' >
				  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				</div>
		  		<div class='clearfix'></div>
              	</h4>
			</div>
			
			<div class="modal-body padding-bottom-0" id="ImportBody">
				<div class="col-xs-12 margin-top-8 ">
	            <div class="input-group-border padding-bottom-8">
				
					<label class="col-lg-2 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">
							<span style="color: red"></span>文件名称
						</div>
						<div class="font-size-9 line-height-18">File Name</div>
					</label>
					<div class="col-lg-8 padding-left-8 padding-right-0" >
					    <div class='input-group input-group-new'>
					    <input type="text" id="excelName" maxlength="100" class="form-control"  >
					    <div class='input-group-addon' style='padding:0px;'>
					    <div id="excelImporter" style='height:34px;'></div>
					    </div>
					    <div class='input-group-addon' id="downloadDiv">
					    <a href="javascript:void(0);">下载导入模板</a>
					    </div>
						</div>
					</div>
				<div class='clearfix'></div>
				</div>
				</div>
				<div class='clearfix'></div>
			</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<!-- <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p> -->
						</span>
	                    <span class="input-group-addon modalfooterbtn">
		                   <button  type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
	                    </span>
	               	</div>
				</div>
			</div>
		</div>
	</div>
</div>
