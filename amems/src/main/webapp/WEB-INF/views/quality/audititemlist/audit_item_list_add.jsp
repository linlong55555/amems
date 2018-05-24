<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/audititemlist/audit_item_list_add.js"></script>
<div class="modal fade in modal-new" id="audit_item_list_alert_Modal" tabindex="-1" role="dialog"  aria-labelledby="audit_item_list_alert_Modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:70%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadChina">维护审核项目单</div>
							<div class="font-size-12" id="modalHeadEnglish">Maintenance audit list</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
        	<input id="auditItemId" type="hidden" class="form-control" />
            <div class="modal-body" >
              	<div class="col-xs-12 margin-top-8 ">
              	<div class="input-group-border">
              	<form id="form">
              	 <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">审核项目单号</div>
							<div class="font-size-9 ">Audit item No.</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="shxmdbh" type="text"  class="form-control" maxlength="16" placeholder='不填写时系统生成编号'/>
						</div>
				  </div>
				  <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span style="color: red">*</span>实际审核日期</div>
							<div class="font-size-9 ">Date</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="sjShrq" type="text" class="form-control" maxlength="10" name='sjShrq' data-date-format="yyyy-mm-dd"/>
						</div>
					</div>
				  <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color: red">*</span>类型</div>
								<div class="font-size-9 ">Type</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input name="lx" type='radio' onchange="shdxChange(true)" value='1' style='vertical-align:text-bottom'  checked="checked"/>&nbsp;内部&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input name="lx" type='radio'  onchange="shdxChange(false)" value='2' style='vertical-align:text-bottom;'/>&nbsp;外部&nbsp;&nbsp;
							</label>
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color: red">*</span>审核类别</div>
								<div class="font-size-9 ">Category</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input name="shlb" type='radio' value='11' style='vertical-align:text-bottom'  checked="checked"/>&nbsp;初次审核&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input name="shlb" type='radio' ' value='12' style='vertical-align:text-bottom;'/>&nbsp;复审&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input name="shlb" type='radio'  value='21' style='vertical-align:text-bottom;'/>&nbsp;专项审核&nbsp;&nbsp;
							</label>
						</div>
					</div>
					</form>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" style="height:34px;">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color: red">*</span>审核对象</div>
								<div class="font-size-9 ">Object</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<div id="shdx1" class='input-group' style='width:100%'>
							    <input id="shdxmcText" type="text" ondblclick="openzrdw()" class="form-control readonly-style"  readonly/>
							    <input id="shdxmc" type="hidden" class="form-control readonly-style"  readonly/>
							    <input id="shdxbh" type="hidden" class="form-control readonly-style"  readonly/>
							    <input id="shdxid" type="hidden" class="form-control readonly-style"  readonly/>
								<span class="input-group-btn">
									<button type="button" onclick="openzrdw(false)" class="btn btn-default" data-toggle="modal" >
										<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
						   </div>
						   
						   <div id="shdx2" class='input-group' style='width:100%'>
							    <input id="shdxmc2" type="text" ondblclick="openzrdw(false)" class="form-control " />
						   </div>
						   
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
					
				  <div class="clearfix"></div>
				 
				</div>
				<div id="attachments_list_auditItem" style="display:none">
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
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
	                    	<button id="save_btn" type="button" onclick="javascript:audit_item_add.setData(0);" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="submit_btn" type="button" onclick="javascript:audit_item_add.setData(1);"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
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
