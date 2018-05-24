<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="audit_report_alert_Modal" tabindex="-1" role="dialog"  aria-labelledby="audit_report_alert_Modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:50%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">维护问题清单</div>
							<div class="font-size-12" id="modalHeadENG">Maintenance list</div>
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
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核报告编号</div>
								<div class="font-size-9 ">Audit report No.</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
						     <input type="hidden" id="auditId">
							<input type="text" id="shbgbh" class="form-control" maxlength="10" placeholder="不填写时系统生成编号"/>
						</div>
				  </div>
				  <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color:red">*</span>审核日期</div>
								<div class="font-size-9 ">Date</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="shrq"  maxlength="10" name='date-picker' data-date-format="yyyy-mm-dd"/>
						</div>
					</div>
					  <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">类型</div>
								<div class="font-size-9 ">Type</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='lx' value='1' style='vertical-align:text-bottom'  checked="checked" onchange="setShdx(this)"/>&nbsp;内部&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='lx' value='2' style='vertical-align:text-bottom;' onchange="setShdx(this)"/>&nbsp;外部&nbsp;&nbsp;
							</label>
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核类别</div>
								<div class="font-size-9 ">Category</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='group_type' value='10' style='vertical-align:text-bottom'  checked="checked"/>&nbsp;计划审核&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='group_type' value='20' style='vertical-align:text-bottom;'/>&nbsp;非计划审核&nbsp;&nbsp;
							</label>

						</div>
					</div>
					
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color:red">*</span>审核对象</div>
								<div class="font-size-9 ">Object</div>
						</label>				
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<div class='input-group' style='width:100%'>
							<input type="hidden"  id="quality_audit_module_add_shdxid" >
							<input type="hidden"  id="quality_audit_module_add_shdxbh">
							<input type="hidden"  id="quality_audit_module_add_shdxmc">
						    <input type="text"    id="quality_audit_module_add_shdx" class="form-control readonly-style"   maxlength="20"  placeholder="部门编号/名称" readonly/>
							<span class="input-group-btn">
								<button type="button" id="button1"  class="btn btn-default" data-toggle="modal" onclick="auditReportItemList.openzrdw('shdx',null,'add')">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						    </div>
						</div>
					</div>
					<div id="zt" class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">状态</div>
								<div class="font-size-9 ">Status</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							 <input type="text" id="zt"  class="form-control"  maxlength="20"  readonly/>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">审核成员</div>
							<div class="font-size-9">Audit member</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8" id="audit_item_member">
							<%@ include file="/WEB-INF/views/quality/creatingauditnotice/creating_audit_member.jsp"%>
						</div>	
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核目的</div>
								<div class="font-size-9 ">Audit purpose</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="shmd" style="height: 55px;" maxlength="100"></textarea>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核范围</div>
								<div class="font-size-9 ">Scope of audit</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="shfw" style="height: 55px;" maxlength="100"></textarea>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核依据</div>
								<div class="font-size-9 ">Audit basis</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="shyj" style="height: 55px;" maxlength="100"></textarea>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核概述</div>
								<div class="font-size-9 ">Overview</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="shgs" style="height: 55px;" maxlength="100"></textarea>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核结论</div>
								<div class="font-size-9 ">Conclusion</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="shjl" style="height: 55px;" maxlength="100"></textarea>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">分发给</div>
								<div class="font-size-9 ">Distribute</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<div class='input-group' style='width:100%'>
								    <input type="hidden"  class="form-control readonly-style"  maxlength="20"  readonly/>
								    <input type="hidden"  id="quality_audit_module_selects_ffdxid" >
							        <input type="hidden"  id="quality_audit_module_selects_ffdxbh">
							        <input type="hidden"  id="quality_audit_module_selects_ffdxmc">
						            <input type="text"    id="quality_audit_module_selects_ffdx" class="form-control"   maxlength="20"  placeholder="部门编号/名称;可多选"/>
									<span class="input-group-btn">
										<button type="button" id="button2" class="btn btn-default" data-toggle="modal" onclick="auditReportItemList.openzrdw('ffdx',null,'selects','1')">
											<i class="icon-search cursor-pointer"></i>
										</button>
									</span>
								</div>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" id="czsm_area">
						<label  class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2" id="operationDescription">操作说明</div>
								<div class="font-size-9 ">Operation</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="czsm" style="height: 55px;" maxlength="300"></textarea>
						</div>
					</div>
					
				  <div class="clearfix"></div>
				 
				</div>
				<div id="attachments_list_edit" >
				<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				</div>

				<!-- 流程记录 -->
                  <div class="panel panel-primary" style='margin-bottom: 0px;'>
					<div class="panel-heading bg-panel-heading">
						<div class="font-size-12 ">流程记录</div>
						<div class="font-size-9">Process record</div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0">
						<div class="col-lg-12 col-md-12 padding-leftright-8"
							style="overflow-x: auto;" id="course_list_table_div">
							<table id="change_record_table"
								class="table table-thin table-bordered table-striped table-hover table-set">
								<thead>
									<tr>
										<th class="colwidth-6"
											style="vertical-align: middle; display: table-cell;">
											<div class="font-size-12 line-height-18">操作人</div>
											<div class="font-size-9 line-height-18">Operation</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">操作时间</div>
											<div class="font-size-9 line-height-18">Times</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">操作说明</div>
											<div class="font-size-9 line-height-18">Description</div>
										</th>						

									</tr>
								</thead>
								<tbody id="processRecord_list_add">
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>			
				</div>
				<div class="clearfix"></div>
            </div>
			<div class="modal-footer" >
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip">
							<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    
	                    <span class="input-group-addon modalfooterbtn" id="adds"  name="buttons">
	                    	<button id="add_save_btn" type="button" onclick="auditReportItemList.save('addSave')" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="add_submit_btn" type="button" onclick="auditReportItemList.save('addSubmit');"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
							<button id="add_close_btn" type="button" onclick="auditReportItemList.close();"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
	                   
	                    </span>	  
	                   
	                    
	                     <span class="input-group-addon modalfooterbtn" id="edits"  name="buttons">
	                    	<button id="edit_save_btn" type="button" onclick="auditReportItemList.save('editSave')" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="edit_submit_btn" type="button" onclick="auditReportItemList.save('editSubmit');"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button> 
							<button id="edit_close_btn" type="button" onclick="auditReportItemList.close();"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>       
	                    </span>
	                     <span class="input-group-addon modalfooterbtn" id="audits"  name="buttons">
	                    	<button id="audit_reject_btn" type="button" onclick="auditReportItemList.save('auditRejectSave')" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">审核驳回</div>
								<div class="font-size-9">AuditReject</div>
							</button>
							<button id="audit_pass_btn" type="button" onclick="auditReportItemList.save('auditPassSave');"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">审核通过</div>
								<div class="font-size-9">AuditPass</div>
							</button> 
							<button id="audit_close_btn" type="button" onclick="auditReportItemList.close();"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>       
	                    </span>
	                    
	                    <span class="input-group-addon modalfooterbtn" id="approves"  name="buttons">
	                    	<button id="approve_pass_btn" type="button" onclick="auditReportItemList.save('approveRejectSave')" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">审批驳回</div>
								<div class="font-size-9">AuditReject</div>
							</button>
							<button id="approve_pass_btn" type="button" onclick="auditReportItemList.save('approvePassSave');"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">审批通过</div>
								<div class="font-size-9">AuditPass</div>
							</button> 
							<button id="approve_close_btn" type="button" onclick="auditReportItemList.close();"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
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

<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 -------->

