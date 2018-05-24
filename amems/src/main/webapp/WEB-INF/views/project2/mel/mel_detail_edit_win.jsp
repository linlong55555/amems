<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="mel_detail_edit_alert_Modal" tabindex="-1" role="dialog"  aria-labelledby="mel_detail_edit_alert_Modal" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:45%;'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
							<div class="font-size-14" id="modalHeadChina">MEL清单</div>
							<div class="font-size-12" id="modalHeadEnglish">MEL</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
              	<div class="col-xs-12 margin-top-8 ">
              	<div class="input-group-border">
              	
              		<form id="form">
                    <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>机型</div>
							<div class="font-size-9">Model</div>
						</label>
						<div class="col-xs-9 padding-leftright-8">
							<select class='form-control' id='jx' name="jx">
			    			</select>
							<input type="hidden" id="melqdfjid" value="" />
							<input type="hidden" id="melId" value="" />
						</div>
					</div>
					<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>版本</div>
							<div class="font-size-9">Version</div>
						</label>
						<div class="col-xs-9 padding-leftright-8" >
							<input class="form-control" id="bb" name="bb" type="text" maxlength="8" >
						</div>
					</div>
					</form>
					<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">MEL清单</div>
							<div class="font-size-9">Mel Detailed</div>
						</label>
						<input id="melqdfjid" name="melqdfjid" type="hidden" />
						<div id="mel_attachments_single_edit" class="col-xs-9 padding-leftright-8">
							<%@ include file="/WEB-INF/views/common/attachments/attachments_single_edit.jsp"%><!-- 加载附件信息 -->
						</div>
					</div>
					
					<div class="clearfix"></div>
					</div>
						 
				</div>
                <div class="clearfix"></div>              
           	</div>	
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
		                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
	                    	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:save();">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
		                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
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
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/mel/mel_detail_edit_win.js"></script>