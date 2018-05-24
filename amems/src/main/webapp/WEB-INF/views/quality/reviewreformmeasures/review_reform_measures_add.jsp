<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="review_reform_measures_alert_Modal" tabindex="-1" role="dialog"  aria-labelledby="review_reform_measures_alert_Modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:75%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">问题整改措施</div>
							<div class="font-size-12" id="modalHeadENG">Corrective measures for problems</div>
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
              	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">问题编号</div>
								<div class="font-size-9 ">Audited unit</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id='shwtbh'  maxlength="20"  readonly/>
						</div>
				  </div>
				  <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">实际审核日期</div>
								<div class="font-size-9 ">Date</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="sjShrq" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">类型</div>
								<div class="font-size-9 ">Type</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="lx" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核类别</div>
								<div class="font-size-9 ">Status</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="shlb" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核对象</div>
								<div class="font-size-9 ">Status</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="shdx" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">责任人</div>
								<div class="font-size-9 ">Status</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="zrr" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">问题等级</div>
								<div class="font-size-9 ">Problem level</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="wtdj" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">问题分类</div>
								<div class="font-size-9 ">Classification</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="wtfl" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">要求反馈日期</div>
								<div class="font-size-9 ">Status</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="yqfkrq" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">附件</div>
								<div class="font-size-9 ">Status</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<span class='input-group-btn' >
								<i class="attachment-view2 glyphicon glyphicon glyphicon-list color-blue cursor-pointer"  style="font-size:15px"  style="float: left;text-decoration:none;position:relative; margin-left: 10px;"></i>
							</span>
						</div>
					</div>
					<div class='clearfix'></div>
						<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">问题描述及依据</div>
								<div class="font-size-9 ">Audit Findings & Reference</div>
						</label>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control" rows="2" cols="34" id="wtms" maxlength="100" readonly></textarea>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">整改建议</div>
								<div class="font-size-9 ">Advice (requirements)</div>
						</label>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="zgjy" rows="2" cols="34" maxlength="100" readonly></textarea>
						</div>
					</div>
				  <div class="clearfix"></div>
				</div>
				 <%@ include file="/WEB-INF/views/quality/reviewreformmeasures/preventive_measure.jsp"%>
				 <!-- 附件列表 -->
				 <div id="attachments_list_edit" >
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				</div>
				<!-- 流程记录 -->
				<div style='margin-bottom:10px;'>
				<%@ include file="/WEB-INF/views/quality/reviewreformmeasures/measures_record.jsp"%>
				</div>
				<div class="clearfix"></div>
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
	                        <button id="assign_btn" type="button" onclick="reviewReformMeasures.assignExecutor();" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode='quality:correctivemeasures:main:02'>
								<div class="font-size-12">指派执行人</div>
								<div class="font-size-9">Executor</div>
							</button>
	                    	<button id="save_btn" type="button" onclick="reviewReformMeasures.saveData();" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode='quality:correctivemeasures:main:01'>
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="submit_btn" type="button" onclick="javascript:reviewReformMeasures.submitData('submit');"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode='quality:correctivemeasures:main:02'>
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
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/reviewreformmeasures/review_reform_measures_add.js"></script>