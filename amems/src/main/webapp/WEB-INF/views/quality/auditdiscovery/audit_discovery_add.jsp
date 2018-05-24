<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="audit_discovery_alert_Modal" tabindex="-1" role="dialog"  aria-labelledby="audit_discovery_alert_Modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:60%;'>
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
            	<input type="hidden" id="maintenanceListId">
              	<div class="col-xs-12 margin-top-8 ">
              	<div class="input-group-border">
            	<form id="auditDiscoveryAdd_form">
              	 <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">问题单号</div>
								<div class="font-size-9 ">Question No.</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" maxlength="50" placeholder="不填写则自动生成" name="shwtdbh" id="shwtdbh"/>
						</div>
				  </div>
				  <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color:red">*</span>实际审核日期</div>
								<div class="font-size-9 ">Date</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control date-picker" maxlength="10" name='sjShrq' data-date-format="yyyy-mm-dd" id="sjShrq"/>
						</div>
					</div>
					<div class="clearfix"></div>
					  <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color:red">*</span>类型</div>
								<div class="font-size-9 ">Type</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='lx' value='1' style='vertical-align:text-bottom'  checked="checked"/>&nbsp;内部&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='lx' value='2' style='vertical-align:text-bottom;'/>&nbsp;外部&nbsp;&nbsp;
							</label>
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color:red">*</span>审核类别</div>
								<div class="font-size-9 ">Category</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='shlb' value='11' style='vertical-align:text-bottom' checked="checked"/>&nbsp;初次审核&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='shlb' value='12' style='vertical-align:text-bottom;'/>&nbsp;复审&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='shlb' value='21' style='vertical-align:text-bottom;'/>&nbsp;专项审核&nbsp;&nbsp;
							</label>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color:red">*</span>审核对象</div>
								<div class="font-size-9 ">Object</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="hidden" id="shdxid"/>
							<input type="hidden" id="shdxbh"/>
							<input type="hidden" id="shdxmc"/>
							<div class='input-group' style='width:100%'>
						    <input type="text"  class="form-control readonly-style"  name="shdx" id="shdx" maxlength="20" readonly="readonly"/>
							<span class="input-group-btn" id="shdxbtn">
								<button type="button"  class="btn btn-default" data-toggle="modal" onclick="auditDiscoveryAdd.chooseDepartment()">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						</div>
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color:red" id="zrrmark">*</span>责任人</div>
								<div class="font-size-9 ">Head</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="hidden" id="zrrbmid"/>
							<input type="hidden" id="zrrid"/>
							<input type="hidden" id="zrrbh"/>
							<input type="hidden" id="zrrmc"/>
							<div class='input-group' style='width:100%'>
						    <input type="text"  class="form-control readonly-style" name="zrr" maxlength="20" id="zrr" readonly/>
							<span class="input-group-btn">
								<button type="button"  class="btn btn-default" data-toggle="modal" onclick="auditDiscoveryAdd.chooseUser()">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						</div>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核内容</div>
								<div class="font-size-9 ">Audit content</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control"  rows="2" cols="34" name="jcnr" maxlength="100" id="jcnr"></textarea>
						</div>
					</div>
					</form>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color:red">*</span>审核发现问题</div>
								<div class="font-size-9 ">Finding</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
						    <%@ include file="/WEB-INF/views/quality/auditdiscovery/audit_discovery_problems.jsp"%>
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color:red">*</span>问题类型</div>
								<div class="font-size-9 ">Category</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='wtlx' value='1' style='vertical-align:text-bottom'  checked="checked"/>&nbsp;一般&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='wtlx' value='9' style='vertical-align:text-bottom;'/>&nbsp;严重&nbsp;&nbsp;
							</label>
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color:red">*</span>要求反馈日期</div>
								<div class="font-size-9 ">Feedback Date</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" name="yqfkrq" id="yqfkrq"/>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">整改建议</div>
								<div class="font-size-9 ">Advice</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control" rows="2" cols="34" maxlength="1000" name="zgjy" id="zgjy"></textarea>
						</div>
					</div>
				  <div class="clearfix"></div>
				 
				</div>
				<div id="attachments_list_edit" >
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
	                    	<button id="save_btn" type="button" onclick="auditDiscoveryAdd.saveData(0);" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="submit_btn" type="button" onclick="auditDiscoveryAdd.saveData(1);"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
							<button type="button" data-dismiss="modal"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/auditdiscovery/audit_discovery_add.js"></script>