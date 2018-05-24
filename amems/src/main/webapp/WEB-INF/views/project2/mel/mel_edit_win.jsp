<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="mel_edit_alert_Modal" tabindex="-1" role="dialog"  aria-labelledby="mel_edit_alert_Modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:90%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadChina">MEL更改单</div>
							<div class="font-size-12" id="modalHeadEnglish">MEL</div>
				  		</div>
				 		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" >
            <input type="hidden" value="${user.jgdm}" id="dprtcode">
            <input type="hidden" value="" id="melDprtcode">
            <input type="hidden" value="" id="melId">
            
              	<div class="col-xs-12 margin-top-8 ">
              		<div class="input-group-border">
              		<form id="form">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>文件编号</div>
								<div class="font-size-9">File No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="ggdbh" name="ggdbh" type="text" maxlength="50" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>机型</div>
								<div class="font-size-9">A/C Type</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="jx" name="jx" class="form-control">
								</select>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">项目号</div>
								<div class="font-size-9">Project No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="xmh" name="xmh" type="text" maxlength="50" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">所属部分</div>
								<div class="font-size-9">Owned Part</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="ssbf" name="ssbf" type="text" maxlength="100" >
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">所属章节</div>
								<div class="font-size-9">Chapter</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="sszj" name="sszj" type="text" maxlength="100" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">中/英</div>
								<div class="font-size-9">Chinese/English</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="zy" name="zy" type="text" maxlength="100" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修改前版本</div>
								<div class="font-size-9">Old Rev.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="xgqBb" name="xgqBb" type="text" maxlength="16" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>修改后版本</div>
								<div class="font-size-9">New Rev.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="xghBb" name="xghBb" type="text" maxlength="16" >
							</div>
						</div>
						
						<div class="clearfix"></div>
						<%@ include file="/WEB-INF/views/open_win/evaluationList.jsp"%><!-- -评估单列表 -->
						 <div class="clearfix"></div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修改依据</div>
								<div class="font-size-9">Basis</div>
							</label>
							<div id="xgyjDiv" class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修订页</div>
								<div class="font-size-9">Revision page</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="xdqx" name="xdqx" type="text" maxlength="100" >
							</div>
						</div>
						
						<div class="col-lg-6 col-md-8 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-3 col-sm-4 col-xs-5 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修改标记</div>
								<div class="font-size-9">Modify Mark</div>
							</label>
							<div class="col-lg-10 col-md-9 col-sm-8 col-xs-7 padding-leftright-8 label-input-div" >
								<label class='margin-right-5 label-input' ><input type='checkbox' name='xgbj' value="A" />&nbsp;A新增</label>
								<label class='margin-right-10 label-input'><input type='checkbox' name='xgbj' value="R" />&nbsp;R修订</label>
								<label class='margin-right-10 label-input'><input type='checkbox' name='xgbj' value="D" />&nbsp;D删除</label>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修改内容</div>
								<div class="font-size-9">Content</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea style="height: 75px;" id="xdnr" class='form-control' >
								</textarea>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修改原因</div>
								<div class="font-size-9">Reason</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea style="height: 75px;" id="xgyy"  class='form-control' >
								</textarea>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<%-- <div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">MEL清单</div>
								<div class="font-size-9">Mel Detailed</div>
								<input type="hidden" id="melqdfjid" value="" />
							</label>
							<div id="mel_attachments_single_edit" class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group">
								    <span class="input-group-addon inputgroupbordernone" style=''>
								    	<label class='margin-left-0 label-input' ><input id="melqdfjBox" type='checkbox' name='radio'  onclick="mel_edit_alert_Modal.showOrHideAttach('melqdfjBox','mel_attachments_single_edit')" /></label>
								    </span>
								    <%@ include file="/WEB-INF/views/common/attachments/attachments_single_edit.jsp"%><!-- 加载附件信息 -->
			                	</div>
							</div>
						</div> --%>
						
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">MEL清单</div>
								<div class="font-size-9">Mel Detailed</div>
							</label>
							<input id="melqdfjid" name="melqdfjid" type="hidden" />
							<div id="mel_attachments_single_edit" class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<%@ include file="/WEB-INF/views/common/attachments/attachments_single_edit.jsp"%><!-- 加载附件信息 -->
							</div>
						</div>
						
						
						
					</form>
						<div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>
					
					<div id="attachments_list_edit" style="display:none">
					
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					
					</div>
					
					<%-- <%@ include file="/WEB-INF/views/common/approve/approvel.jsp" %> --%> <!--审核批准信息 -->	
					<%@ include file="/WEB-INF/views/open_win/introduce_process_info.jsp" %> <!--流程信息 -->	
					
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
	                    	<button type="button" id="save" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="mel_edit_alert_Modal.save()" >
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
	                      	<button type="button" id="submit" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="mel_edit_alert_Modal.submit()"  >
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
							<button type="button" id="audited" onclick="agreedAudit()"
								class="btn btn-primary padding-top-1 padding-bottom-1"  >
								<div class="font-size-12">审核通过</div>
								<div class="font-size-9">Audited</div>
							</button>
							<button type="button" id="reject" onclick="rejectedAudit()"
								class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">审核驳回</div>
								<div class="font-size-9">Audit Reject</div>
							</button>
							<button type="button" id="approve" onclick="agreedApprove()"
								class="btn btn-primary padding-top-1 padding-bottom-1"  >
								<div class="font-size-12">批准通过</div>
								<div class="font-size-9">Approved</div>
							</button>
							<button type="button" id="rejected" onclick="rejectedApprove()"
								class="btn btn-primary padding-top-1 padding-bottom-1 "  >
								<div class="font-size-12">批准驳回</div>
								<div class="font-size-9">Rejected</div>
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
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/mel/mel_edit_win.js"></script>