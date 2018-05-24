<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="ShutDownModal" tabindex="-1" role="dialog"  aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style='width:60%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
   				<h4 class="modal-title" >
             		<div class='pull-left'>
	          			<div class="font-size-14 " id="titleName">关闭</div>
						<div class="font-size-12" id="titleEname">Close</div> 
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
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2" id="chinaHead">保留单号</div>
								<div class="font-size-9" id="englishHead">Evalu No.</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<input class="form-control"   id="ejsdh" name="ejsdh" disabled="disabled">
							</div>
						</div>
						<div  class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span id="vjsyy" style="color: red">*</span>关闭保留缺陷措施</div>
								<div class="font-size-9">Measure</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class='form-control disabledgb' style="height:75px" id="ezdjsyy" name="ezdjsyy"  maxlength="1000">
								</textarea>
							</div>
						</div>
	           			<div  class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工作者</div>
								<div class="font-size-9">Worker</div>
							</label>
							<div class="col-xs-10 padding-leftright-8">
								<input class="form-control disabledgb"   id="ezdjsr" name="ezdjsr"  maxlength="100">
							</div>
						</div>
						<div  class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">完成日期</div>
								<div class="font-size-9">Date</div>
							</label>
							<div class="col-xs-10 padding-leftright-8">
								<input class="form-control disabledgb date-picker"  id="ezdjsrq" name="ezdjsrq" >
							</div>
						</div>
						<div class='clearfix'></div>
						<div class='table_pagination padding-leftright-8'>
						</div>
					</div>
					<!--参考文件END  -->		
					<div id="attachments_list_edit2" style="display:none">
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
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
					   <button id="baocun" type="button" onclick="ShutDownModal.setData();" class="btn btn-primary padding-top-1 padding-bottom-1">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/defectkeep/shutDown.js"></script>