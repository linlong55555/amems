<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="Defectkeep_Open_Modal" tabindex="-1" role="dialog" aria-hidden="true" data-keyboard="false" aria-labelledby="Assessment_Open_Modal" data-backdrop="static" >
	<div class="modal-dialog" style='width:90%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
				<h4 class="modal-title" > 
                	<div class='pull-left'>
                    	<div class="font-size-12 " id="modalName"></div>
						<div class="font-size-9 " id="modalEname"></div>
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
 					<form id="defectkeepForm" >
						<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 
							<div class="input-group-border margin-top-8 padding-left-0">
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group " >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">保留单编号</div>
										<div class="font-size-9 ">DD No.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" id="bldh" name="bldh" class="form-control padding-left-3 padding-right-3 bldh" placeholder='不填写则自动生成'  maxlength="50"/>
									</div>
								</div>
			           			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">
											<label class="required" style="color: red">*</label>飞机注册号
										</div>
										<div class="font-size-9 ">A/C REG</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<select class="form-control noteditable"  id="Defectkeep_Open_Modal_fjzch" name="fjzch" onchange="Defectkeep_Open_Modal.onchangeFjzch(this)">
										</select>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">机型</div>
										<div class="font-size-9 ">A/C Type</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
										<input type="text"   id="jx" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group div-hide">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">状态</div>
										<div class="font-size-9 ">Status</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
										<input type="text"  id="ztms" class="form-control padding-left-3 padding-right-3 "  disabled="disabled"/>
									</div>
								</div>
				      			<div id="lybhdiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group " >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">来源工单</div>
										<div class="font-size-9 ">W/O</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group" style="width: 100%">
											<input type="hidden" id="addupdatebs" value="1">
											<input type="hidden" id="bs145">
											<input type="hidden"  name="gdid"  id="gdid">
											<input type="text"   id="gd" name='gd' class="form-control noteditable readonly-style colse" readonly="readonly" >
											<span class="required input-group-btn checkPermission" id="wxrybtn"  permissioncode='produce:reservation:defect:main:052'>
												<button type="button" class="btn btn-default form-control"  title="135工单" style='height:34px;' data-toggle="modal" onclick="Defectkeep_Open_Modal.initSourceData(135)">
													<i class="icon-search cursor-pointer" ></i>
												</button>
											</span>
											<span class="required input-group-btn checkPermission" id="wxrybtn"  permissioncode='produce:reservation:defect:main:053'>
												<button type="button" class="btn btn-default form-control"  title="145工单" style='height:34px;' data-toggle="modal" onclick="Defectkeep_Open_Modal.initSourceData(145)">
													<i class="icon-search cursor-pointer" ></i>
												</button>
											</span>
					                	</div>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">
											<label class="required" style="color: red">*</label>申请人
										</div>
										<div class="font-size-9 ">Applicant</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group" style="width: 100%">
											<input type="hidden"  name="sqrid"  id="sqrid">
											<input type="hidden"  name="sqrbmdm"  id="scSqrbmdm">
											<input type="text"  name="sqr" id="sqr" class="form-control noteditable readonly-style colse"  readonly="readonly" ondblclick="Defectkeep_Open_Modal.openUser('sqr');">
											<span class="required input-group-btn" >
												<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Defectkeep_Open_Modal.openUser('sqr');">
													<i class="icon-search cursor-pointer" ></i>
												</button>
											</span>
					                	</div>
									</div>
								</div>
							 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">
											<label class="required" style="color: red">*</label>申请日期
										</div>
										<div class="font-size-9 ">Application Date</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text"  id="sqrq"  name="sqrq" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
									</div>
								</div>
							 	<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">缺陷描述</div>
											<div class="font-size-9 ">Desc</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="noteditable form-control padding-left-3 padding-right-3" id="qxms"  
											style="height:55px" maxlength="1000"></textarea>
									</div>
							 	</div>
								<div class='clearfix'></div>
							</div>
						</div>
	     				<div class="clearfix"></div>
						<!--参考文件END  -->		
						<div id="attachments_list_edit" style="display:none">
							<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
						</div>
						<div class="clearfix"></div>
						<%@ include file="/WEB-INF/views/produce/defectkeep/approval_info.jsp" %> <!--批准流程 -->
					</form>
				</div>
			</div>	
			<div class='clearfix'></div>
			<div class="modal-footer ">
				<div class="col-xs-12 padding-left-8 padding-right-0" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
			                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
						    <button id="sptg" type="button" onclick="Defectkeep_Approval_Modal.passed();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">批准通过</div>
								<div class="font-size-9">Approved</div>
							</button>
							<button id="spbh" type="button" onclick="Defectkeep_Approval_Modal.turnDown();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">批准驳回</div>
								<div class="font-size-9">Rejected</div>
							</button>
						   	<button id="baocuns" type="button" onclick="Defectkeep_Add_Modal.save();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="tijiao" type="button" onclick="Defectkeep_Add_Modal.submit();" class="btn btn-primary padding-top-1 padding-bottom-1">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/defectkeep/defectkeep_open.js"></script><!--项目保留弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/defectkeep/defectkeep_add.js"></script><!--新增项目保留弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/defectkeep/defectkeep_update.js"></script><!--修改项目保留弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/defectkeep/defectkeep_approval.js"></script><!--批准项目保留弹窗的js  -->
