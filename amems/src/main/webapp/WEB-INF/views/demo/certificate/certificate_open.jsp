<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/demo/certificate/certificate_open.js"></script>
<div class="modal fade in modal-new" id="CertificateModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
	<div class="modal-dialog" style="width:70%">
			<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                	<h4 class="modal-title" >
                  	<div class='pull-left'>
	                    <div class="font-size-14 " id="modalHeadChina"></div>
						<div class="font-size-12" id="modalHeadEnglish"></div>
				  	</div>
				  	<div class='pull-right'>
				  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				  	</div>
				  	<div class='clearfix'></div>
                	</h4>
              </div>
			<div class="modal-body padding-bottom-0">
				<div class="col-xs-12 margin-top-8">
					<div class="input-group-border" style="padding-top: 15px;padding-bottom: 5px;">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">件号</div>
								<div class="font-size-9">P/N</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="bjh" name="bjh" type="text" disabled="disabled">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">型号</div>
								<div class="font-size-9">Model</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="xingh" name="xingh" type="text" disabled="disabled">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">序列号</div>
								<div class="font-size-9">S/N</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="xlh" name="xlh" type="text" disabled="disabled">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">批次号</div>
								<div class="font-size-9">Batch No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="pch" name="pch" type="text" disabled="disabled">
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">名称</div>
								<div class="font-size-9">Name</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="mc" name="mc" type="text" disabled="disabled">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">厂家件号</div>
								<div class="font-size-9">Manufacturer P/N</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="cjjh" name="cjjh" type="text" disabled="disabled">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">件号来源</div>
								<div class="font-size-9">P/N Source</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="jhly" name="jhly" type="text" disabled="disabled">
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">ATA章节号</div>
								<div class="font-size-9">ATA</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="zjh" name="zjh" type="text" disabled="disabled">
							</div>
						</div>
						
						<div class="clearfix"></div>
						
					</div>
					
					<div class="panel panel-primary ">
				<div id="attachHead" class="panel-heading bg-panel-heading" >
					<div class="font-size-12" >证书信息</div>
					<div class="font-size-9" >Certificate Info</div>
				</div>
				<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
			
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-8" style="overflow-x: auto;">
						<table class="table table-thin table-bordered table-striped table-hover table-set" style="min-width:600px">
							<thead>
								<tr>
									<th class="colwidth-7" name="common_certificate_addTh">
										<button class="line6 line6-btn" name="common_certificate_addBtn" type="button">
											<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
										</button>
								    </th>
									<th class="colwidth-20">
									   <div class="font-size-12 line-height-12">证书类型</div>
								        <div class="font-size-9 line-height-12">Certificate Type</div>
									</th>
									<th class="colwidth-10">
									   <div class="font-size-12 line-height-12">证书编号</div>
								        <div class="font-size-9 line-height-12">Certificate No.</div>
									</th>
									<th class="colwidth-10">
									   <div class="font-size-12 line-height-12">存放位置</div>
								        <div class="font-size-9 line-height-12">Certificate Location</div>
									</th>
									<th class="colwidth-10">
									   <div class="font-size-12 line-height-12">签署日期</div>
								        <div class="font-size-9 line-height-12">Sign Date</div>
									</th>
									<th class="colwidth-7">
									   <div class="font-size-12 line-height-12">附件</div>
								        <div class="font-size-9 line-height-12">Attachment</div>
									</th>
								</tr>
							</thead>
						   <tbody id="installationlist_certificate_list"><tr class="noData"><td class="text-center" colspan="6">暂无数据 No data.</td></tr></tbody>
							
						</table>
					</div>
					<div class="clearfix"></div> 
				</div>
				
			</div>
				 	<div class="clearfix"></div>
				</div>
			</div>
			
			<div class="clearfix"></div>
			<div class="modal-footer">
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
			                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
						<span class="input-group-addon modalfooterbtn">
							<button  class="btn btn-primary padding-top-1 padding-bottom-1" onclick="CertificateModal.setData()">
			                   	<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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