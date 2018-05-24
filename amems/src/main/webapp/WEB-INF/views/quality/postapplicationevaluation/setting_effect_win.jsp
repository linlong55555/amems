<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="setting_effect_alert_Modal" tabindex="-1" role="dialog"  aria-labelledby="mel_detail_edit_alert_Modal" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:50%;'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
							<div class="font-size-12">设置有效期</div>
							<div class="font-size-9">Setting</div>
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
                    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">授权单号</div>
							<div class="font-size-9">Authorized No.</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="sqsqdh_view" type="text" readonly="readonly">
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">申请人</div>
							<div class="font-size-9">Applicant</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="sqr_view" type="text" readonly="readonly">
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">申请类型</div>
							<div class="font-size-9">Type</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="sqlx_view" type="text" readonly="readonly">
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">申请岗位</div>
							<div class="font-size-9">Post</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="sqgw_view" type="text" readonly="readonly">
						</div>
					</div>
					<div class="sqrqwin col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>授权开始日期</div>
							<div class="font-size-9">Start date</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8" >
							<input id="sqksrq_edit" class="form-control date-picker"  type="text" onchange="setting_effect_alert_Modal.changeDate(this)"/>
						</div>
					</div>
					<div class="sqrqwin col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>授权截止日期</div>
							<div class="font-size-9">Closing date</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8" >
							<input id="sqjzrq_edit" class="form-control date-picker"  type="text" onchange="setting_effect_alert_Modal.changeDate(this)" />
						</div>
					</div>
					
					<div class='clearfix'></div>
			        <div id="setting_plane_model" class="jslbwin">
             		<%@ include file="/WEB-INF/views/quality/postapplicationevaluation/plane_model_list_edit.jsp"%><!--机型  -->
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
	                    	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="setting_effect_alert_Modal.setData();">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
		                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="setting_effect_alert_Modal.close();">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/postapplicationevaluation/setting_effect_win.js"></script><!--当前界面js  -->
