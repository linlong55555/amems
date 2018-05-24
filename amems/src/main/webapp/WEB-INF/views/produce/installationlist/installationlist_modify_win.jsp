<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>

<div class="modal fade modal-new" id="installation_modify_modal" tabindex="-1" role="dialog" aria-labelledby="installation_modify_modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:90%">
			<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                	<h4 class="modal-title" >
                  	<div class='pull-left'>
	                    <div class="font-size-14" id="chinaHead">装机清单</div>
						<div class="font-size-12" id="englishHead">InstallationList</div>
				  	</div>
				  	<div class='pull-right'>
				  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				  	</div>
				  	<div class='clearfix'></div>
                	</h4>
              </div>
			<div class="modal-body padding-bottom-0">
				<div class="col-xs-12 margin-top-8">
					<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_common.jsp"%>
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
							<button id="formSave" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="installationlist_modify_win.save();">
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

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/installationlist/installationlist_modify_win.js"></script>
