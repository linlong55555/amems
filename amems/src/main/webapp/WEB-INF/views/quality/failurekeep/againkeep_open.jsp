<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="Againkeep_Open_Modal" tabindex="-1" role="dialog" aria-hidden="true" data-keyboard="false" aria-labelledby="Assessment_Open_Modal" data-backdrop="static" >
	<div class="modal-dialog" style='width:90%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
				<h4 class="modal-title" >
                	<div class='pull-left'>
                    	<div class="font-size-12 " id="Againkeep_Open_Modal_modalName"></div>
						<div class="font-size-9 " id="Againkeep_Open_Modal_modalEname"></div>
					</div>
				  	<div class='pull-right' >
		  		  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
		  		  	</div>
				  	<div class='clearfix'></div>
              	</h4>
            </div>
            <div class='clearfix'></div>
		    <div class="modal-body  " >
	           	<div class="col-xs-12  ">
 					<form id="againfailurekeepForm" >
	     				<!--基础信息STATR  -->
   						<div class="panel panel-default padding-right-0  margin-top-10">
					        <div class="panel-heading bg-panel-heading">
					        	<div class="font-size-12 ">基础信息</div>
								<div class="font-size-9">Basis Info</div>
						    </div>
							<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
								<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
					       			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">保留单编号</div>
											<div class="font-size-9">DD No.</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<input type="text" id="Againkeep_Open_Modal_bldh" readonly="readonly" class="noteditable form-control padding-left-3 padding-right-3" maxlength="15"/>
										</div>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red">*</label>办理人
											</div>
											<div class="font-size-9 ">Lae</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<div class="input-group" style="width: 100%">
												<input type="hidden"  name="zcSqrid"  id="zcSqrid">
												<input type="hidden"  name="zcSqrbmid"  id="zcSqrbmid" >
												<input type="text"  name="zcSqr" id="zcSqr" class="form-control noteditable readonly-style colse"  readonly="readonly" ondblclick="again_keep_open.openUser('zcSqr');">
												<span class="required input-group-btn" >
													<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="again_keep_open.openUser('zcSqr');">
														<i class="icon-search cursor-pointer" ></i>
													</button>
												</span>
						                	</div>
										</div>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red">*</label>办理日期
											</div>
											<div class="font-size-9 ">Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<input type="text"  onchange="again_keep_open.onchangebllx(2)"  id="zcSqrq" name="zcSqrq"  class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
										</div>
									</div>
									
								   <div class='clearfix'></div>
  				 		     		<div id="bllbDiv" class="col-lg-12 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-2 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 margin-topnew-2">保留类别</div>
												<div class="font-size-9 ">Type</div>
										</label>
										<div class="col-lg-11 col-md-10 col-sm-9 col-xs-10 padding-leftright-8" >
					                    	 <label class='label-input'><input type='radio' onclick="again_keep_open.onchangebllx(1);" class="noteditable" name='bllx2' value="1" checked />&nbsp;A&nbsp;</label>
					                    	 <label class='label-input'><input type='radio' onclick="again_keep_open.onchangebllx(1);" class="noteditable" name='bllx2' value="2"/>&nbsp;B&nbsp;</label>
					                    	 <label class='label-input'><input type='radio' onclick="again_keep_open.onchangebllx(1);" class="noteditable" name='bllx2' value="3" />&nbsp;C&nbsp;</label>
					                    	 <label class='label-input'><input type='radio' onclick="again_keep_open.onchangebllx(1);" class="noteditable" name='bllx2' value="4" />&nbsp;D&nbsp;</label>
					                    	 <label class='label-input'><input type='radio' onclick="again_keep_open.onchangebllx(1);" class="noteditable" name='bllx2' value="5" />&nbsp;CDL&nbsp;</label>
					                    	 <label class='label-input'><input type='radio' onclick="again_keep_open.onchangebllx(1);" class="noteditable" name='bllx2' value="9" />&nbsp;其他&nbsp;</label>
										</div>
									</div>
									
									<div class="clearfix"></div>
									
									<div class="col-lg-11 col-sm-11 col-xs-11 form-group" style="padding-left: 9%;padding-right: 55px; ">
								    	<div class="col-lg-10 col-sm-10 col-xs-10 form-group settingFd" >
								    		<p class="settingFd-p" style="width: 80px;font-weight: normal;margin-left:1px">再次保留期限</p>
								    		
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red"></label>
												日历
											</div>
											<div class="font-size-9 ">Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<input type="text"  onchange="FailureKeep_Open_Modal.clearRedColor()"  id="zcBlqx" name="zcBlqx" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
										</div>
									</div>
									
									 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="fhDiv2">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red"></label>
												飞行时间
											</div>
											<div class="font-size-9 ">FH</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										     <div class="input-group" style='width:100%;'> 
										     	<input class="form-control input-sm" maxlength="10" onkeyup="FailureKeep_Open_Modal.validateFH(this)" type="text" id="zcFhInput">
										     	<span class="input-group-addon dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FH</span>
										     </div>
										</div>
									</div>
									
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group"  id="fcDiv2">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red"></label>
												飞行循环
											</div>
											<div class="font-size-9 ">FC</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										     <div class="input-group" style='width:100%;'> 
										     <input class="form-control input-sm" maxlength="10" onkeyup="FailureKeep_Open_Modal.validateFC(this)" type="text" id="zcFcInput">
										     <span class="input-group-addon dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FC</span>
										     </div>
										</div>
									</div>
									</div>
								</div>
								
									<div class="clearfix"></div>
									
			     				 	 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">
												<label class="required" style="color: red">*</label>再次保留原因
											</div>
											<div class="font-size-9 ">Redeferred Reason</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea  style="height:55px" class="noteditable form-control padding-left-3 padding-right-3" id="zcBlyy" name="zcBlyy"   maxlength="1000"></textarea>
										</div>
									 </div>
 									 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">再次保留批准人
											</div>
											<div class="font-size-9 ">Approved By</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<div class="input-group" style="width: 100%">
												<input type="hidden"  name="zcPzrid"  id="zcPzrid">
												<input type="hidden"  name="zcPzrbmid"  id="zcPzrbmid">
												<input type="text"  name="zcPzr" id="zcPzr" class="form-control noteditable readonly-style colse"  readonly="readonly" ondblclick="again_keep_open.openUser('zcPzr');">
												<span class="required input-group-btn" >
													<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="again_keep_open.openUser('zcPzr');">
														<i class="icon-search cursor-pointer" ></i>
													</button>
												</span>
						                	</div>
										</div>
									</div>
								 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												再次保留批准日期
											</div>
											<div class="font-size-9 ">Approval Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="zcPzrq"  class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
										</div>
									</div>
		     						<div class="clearfix"></div>
								</div>
							</div>
					   	</div>
   						<div class="clearfix"></div>
   						<div class="panel panel-default padding-right-0">
					        <div class="panel-heading bg-panel-heading">
					        	<div class="font-size-12 ">局方批准</div>
								<div class="font-size-9">Board Approval</div>
						    </div>
							<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
								<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
								 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">批准人</div>
											<div class="font-size-9 ">Approved By CAAC</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="jfpzr"  maxlength="100" class="noteditable form-control padding-left-3 padding-right-3 "  />
										</div>
									</div>
								 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">批准日期</div>
											<div class="font-size-9 ">Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="jfpzrq"  class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
										</div>
									</div>
							 		<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">批准意见</div>
											<div class="font-size-9 ">Approval Advice</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea id="jfpzyj"  style="height:55px" class="noteditable form-control padding-left-3 padding-right-3"   maxlength="150"></textarea>
										</div>
									 </div>
		     						<div class="clearfix"></div>
								</div>
							</div>
					   	</div>
   						<div class="clearfix"></div>
						<div id="attachments_list_edit3" style="display:none">
							<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
						</div>
					</form>
				</div>
			</div>	
			<div class="clearfix"></div>
			<div class="modal-footer ">
				<div class="col-xs-12 padding-left-8 padding-right-0" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
			                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
						   	<button id="baocuns" type="button" onclick="again_keep_open.save();" class="btn btn-primary padding-top-1 padding-bottom-1">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/failurekeep/failurekeep_open.js"></script><!--故障保留通用弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/failurekeep/againkeep_open.js"></script><!--再次保留弹窗的js  -->
