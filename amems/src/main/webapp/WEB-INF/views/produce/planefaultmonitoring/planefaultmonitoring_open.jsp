<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="Planefaultmonitoring_Open_Modal" tabindex="-1" role="dialog" aria-hidden="true" data-keyboard="false" aria-labelledby="Assessment_Open_Modal" data-backdrop="static">
	<div class="modal-dialog" style='width:75%;'>
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
 					<input type="hidden" id="djid"  />
 					<input type="hidden" id="dprtcode1" />
 					<form id="monitoringForm" >
						<!--保留数据  -->
						<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 
							<div class="input-group-border margin-top-8 padding-left-0">
			           			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">
											<label class="required" style="color: red">*</label>
											飞机注册号
										</div>
										<div class="font-size-9 ">Type</div>
									</label>
									<div id="Planefaultmonitoring_Open_Modal_fjzch_div" class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<select class="form-control" name="fjzch" id="Planefaultmonitoring_Open_Modal_fjzch">
										</select> 
									</div>
								</div>
								<div class='clearfix'></div>
									
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">
											<label class="required" style="color: red">*</label>ATA章节号
										</div>
										<div class="font-size-9">ATA</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
										<div class="input-group" style="width: 100%">
											<input id="common_zjh_value" type="hidden" name="common_zjh_value">
											<input type="text"  name="common_zjh_display" class="form-control noteditable readonly-style" id="common_zjh_display" readonly="readonly" >
											<span class="required input-group-btn" id="common_zjh_btn" >
												<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="openChapterWin();">
													<i class="icon-search cursor-pointer" ></i>
												</button>
											</span>
					                	</div>
									</div>
								</div>
						 		<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 " >
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 ">
											故障信息
										</div>
										<div class="font-size-9 ">Fault info</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea  style="height:55px" class="noteditable form-control padding-left-3 padding-right-3" id="gzxx" name="gzxx"   maxlength="1300"></textarea>
									</div>
								 </div>
					 	 		 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 " >
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 ">
											备注
										</div>
										<div class="font-size-9 ">Remark</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 margin-top-8 margin-bottom-8">
										<textarea  style="height:55px" class="noteditable form-control padding-left-3 padding-right-3" id="mbz" name="mbz"   maxlength="300"></textarea>
									</div>
								 </div>
								<div class='clearfix'></div>
							</div>
						</div>
					</form>
					<div class="clearfix"></div>
					<!--参考文件END  -->		
					<div id="attachments_list" style="display:none">
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					</div>
				</div>
			</div>	
			<div class="clearfix"></div>
			<div class="modal-footer ">
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
			                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
						   	<button id="baocuns" type="button" onclick="Planefaultmonitoring_Add_Modal.save();" class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
							</button>
					   	</span>
	               	</div><!-- /input-group -->
				</div>
				<div class="clearfix"></div> 
			</div>
	  	</div>
 	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/planefaultmonitoring/planefaultmonitoring_open.js"></script><!--飞机故障公用的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/planefaultmonitoring/planefaultmonitoring_add.js"></script><!--新增飞机故障弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/planefaultmonitoring/planefaultmonitoring_update.js"></script><!--修改飞机故障弹窗的js  -->
