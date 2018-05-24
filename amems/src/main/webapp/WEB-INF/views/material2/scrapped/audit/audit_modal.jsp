<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="audit_modal" tabindex="-1" role="dialog"  aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style='width:45%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
				<h4 class="modal-title" >
					<div class='pull-left'>
						<div class="font-size-14 " id="audit_modal_titleName">关闭</div>
						<div class="font-size-12" id="audit_modal_titleEname">Close</div> 
					</div>
					<div class='pull-right' >
				  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
			  		<div class='clearfix'></div>
				</h4>
			</div>
            <div class="modal-body" id="AssignEndModalBody">
   		        <div class="col-xs-12 margin-top-0 padding-left-10 padding-right-8">
 					<div class="input-group-border margin-top-8 padding-left-0">
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2" id="chinaHead">报废单号</div>
								<div class="font-size-9" id="englishHead">Scrap No.</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<input class="form-control"   id="audit_modal_bfdh" name="audit_modal_bfdh" disabled="disabled">
							</div>
						</div>
						<div  class="col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span id="audit_modal_spyjRemark" style="color: red">*</span>审核建议</div>
								<div class="font-size-9">Audit Advice</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<textarea class='form-control' style="height:75px" id="audit_modal_spyj" name="audit_modal_spyj"  maxlength="150">
								</textarea>
							</div>
						</div>
						<div class='clearfix'></div>
						<div class='table_pagination padding-leftright-8'>
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
					   <button id="baocun" type="button" onclick="audit_modal.setData();" class="btn btn-primary padding-top-1 padding-bottom-1">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/scrapped/audit/audit_modal.js"></script>