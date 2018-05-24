<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

<div class="modal fade modal-new" id="installation_certificate_modal" tabindex="-1" role="dialog" aria-labelledby="installation_certificate_modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:60%">
			<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                	<h4 class="modal-title" >
                  	<div class='pull-left'>
	                    <div class="font-size-14">证书信息</div>
						<div class="font-size-12">Certificate Info</div>
				  	</div>
				  	<div class='pull-right'>
				  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				  	</div>
				  	<div class='clearfix'></div>
                	</h4>
              </div>
			<div class="modal-body padding-bottom-0">
				<div class="col-xs-12 margin-top-8">
					<input type="hidden" id="certificate_modal_rowid"/>
					<div class="input-group-border" style="padding-top: 15px;padding-bottom: 5px;">
						<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">证书类型</div>
								<div class="font-size-9">Certificate Type</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
								<select class="form-control" id="certificate_modal_zjlx">
								</select>
							</div>
						</div>
						<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">证书编号</div>
								<div class="font-size-9">Certificate No.</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
								<input type="text" class="form-control" id="certificate_modal_zsbh" maxlength="50"/>
							</div>
						</div>
						<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">存放位置</div>
								<div class="font-size-9">Location</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
								<input type="text" class="form-control" id="certificate_modal_zscfwz" maxlength="100"/>
							</div>
						</div>
						<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">签署日期</div>
								<div class="font-size-9">Sign Date</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
								<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="certificate_modal_qsrq"/>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
					</div>
					
					<div id="attachments_list_edit">
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
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
							<button id="common_certificate_saveBtn" class="btn btn-primary padding-top-1 padding-bottom-1">
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
	
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/installationlist/installationlist_certificate.js"></script>