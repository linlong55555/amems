<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="Fault_Handling_Record_Modal" tabindex="-1" role="dialog" aria-hidden="true" data-keyboard="false" aria-labelledby="Assessment_Open_Modal" data-backdrop="static" >
	<div class="modal-dialog" style='width:85%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
				<h4 class="modal-title" >
                	<div class='pull-left'>
                    	<div class="font-size-12 " id="modalNameFault"></div>
						<div class="font-size-9 " id="modalEnameFault"></div>
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
					<input type="hidden" id="manidinfo" />
					<input type="hidden" id="dprtId" />
					<input type="hidden" id="fjzchid" />
					<input type="hidden" id="infoId"/>
 					
						<!--保留数据  -->
						<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 
							<div class="input-group-border margin-top-8 padding-left-0">
							<form id="monitoringForm_bottom" >
           						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">
											<label class="required" style="color: red">*</label>航班号
										</div>
										<div class="font-size-9">Flight No.</div>
									</label>
									<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group" style="width: 100%">
											<input type="hidden"  name="fxjldid"  id="fxjldid">
											<input type="text"  name="hbh" class="form-control noteditable" id="hbh" ondblclick='Fault_Handling_Record_Open_Modal.openHb();'>
											<span class="required input-group-btn" id="wxrybtn">
												<button id="hbbtn" type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Fault_Handling_Record_Open_Modal.openHb()">
													<i class="icon-search cursor-pointer" ></i>
												</button>
											</span>
					                	</div>
									</div>
								</div>
		           				<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">航班日期</div>
										<div class="font-size-9 ">Flight Date</div>
									</label>
									<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text"  id="hbrq" name="hbrq" class="noteditable form-control padding-left-3 padding-right-3 date-picker" data-date-format="yyyy-mm-dd" />
									</div>
								</div>
           						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">
											<label class="required" style="color: red">*</label>工单编号
										</div>
										<div class="font-size-9">Instruction No.</div>
									</label>
									<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group" style="width: 100%">
											<input type="hidden"  name="zlhid"  id="zlhid">
											<input type="text"  name="zlh" id="zlh" class="form-control noteditable "   ondblclick='Fault_Handling_Record_Open_Modal.openZlh();'>
											<span class="required input-group-btn" id="wxrybtn" >
												<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Fault_Handling_Record_Open_Modal.openZlh()">
													<i class="icon-search cursor-pointer" ></i>
												</button>
											</span>
					                	</div>
									</div>
								</div>
								<div class='clearfix'></div>
								</form>
								<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0" >
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">拆下</div>
										<div class="font-size-9 ">Remove</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea style="height:55px" class="noteditable form-control padding-left-3 padding-right-3" id="cxjxx"></textarea>
									</div>
								</div>
								
		           				<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 margin-top-8" >
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">装上</div>
										<div class="font-size-9 ">Mount</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea style="height:55px" class="noteditable form-control padding-left-3 padding-right-3" id="zsjxx" ></textarea>
									</div>
								</div>
								
						 		<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 margin-top-8" >
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 ">
											排故思路
										</div>
										<div class="font-size-9 ">Troubleshooting Thinking</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea  style="height:55px" class="noteditable form-control padding-left-3 padding-right-3" id="pgsl" name="pgsl"   maxlength="300"></textarea>
									</div>
								 </div>
								 
					 	 		 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 margin-top-8" >
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12 ">
											处理结果
										</div>
										<div class="font-size-9 ">Result</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea  style="height:55px" class="noteditable form-control padding-left-3 padding-right-3" id="cljg" name="cljg"   maxlength="300"></textarea>
									</div>
								 </div>
					 	 		 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 margin-top-8 margin-bottom-8" >
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12 ">
											备注
										</div>
										<div class="font-size-9 ">Remark</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea  style="height:55px" class="noteditable form-control padding-left-3 padding-right-3" id="bz" name="bz"   maxlength="300"></textarea>
									</div>
								 </div>
								<div class='clearfix'></div>
							</div>
						</div>
						<div class="clearfix"></div>
						<!--参考文件END  -->		
						<div id="attachments_list_edit_handing" style="display:none">
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
						   	<button id="baocuns" type="button" onclick="Fault_Handling_Record_Add_Modal.save();" class="btn btn-primary padding-top-1 padding-bottom-1">
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


<%@ include file="/WEB-INF/views/produce/planefaultmonitoring/hbh.jsp"%>  <!--航班号jsp  -->		
<%@ include file="/WEB-INF/views/produce/planefaultmonitoring/zlh.jsp"%>  <!--指令号jsp  -->		
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/planefaultmonitoring/planefaultmonitoring_mainone_open.js"></script><!--评估单弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/planefaultmonitoring/planefaultmonitoring_mainone_add.js"></script><!--新增评估单弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/planefaultmonitoring/planefaultmonitoring_mainone_update.js"></script><!--新增评估单弹窗的js  -->
