<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="toolcheck_open_modal" tabindex="-1" role="dialog" aria-hidden="true" data-keyboard="false" aria-labelledby="toolcheck_open_modal" data-backdrop="static" >
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
 					<form id="toolcheckForm" >
						<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 
							<div class="input-group-border margin-top-8 padding-left-0">
								<div  class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><label class="required" style="color: red">*</label>工具编号</div>
										<div class="font-size-9 ">Tool No.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group" style="width: 100%">
											<input type="hidden" id="bjid">
											<input type="text"   id="bjxlh"  name="zbjxlh" class="form-control noteditable readonly-style colse" readonly="readonly" ondblclick='toolcheck_open_modal.selecttoolequipment();'>
											<span class="required input-group-btn"  >
												<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="toolcheck_open_modal.selecttoolequipment()">
													<i class="icon-search cursor-pointer" ></i>
												</button>
											</span>
					                	</div>
									</div>
								</div>
			           			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">部件号</div>
										<div class="font-size-9 ">P/N</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text"   id="bjh" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">部件名称</div>
										<div class="font-size-9 ">Name</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
										<input type="text"   id="bjmc" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group div-hide">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">维护人</div>
										<div class="font-size-9 ">Maintainer</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
										<input type="text"  id="whr" class="form-control padding-left-3 padding-right-3 "  disabled="disabled"/>
									</div>
								</div>
								<div class='clearfix'></div>
				      			<div id="lybhdiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">位置</div>
										<div class="font-size-9 ">Position</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
										<input type="text"  id="wz" class="form-control padding-left-3 padding-right-3 "  disabled="disabled"/>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">入库日期</div>
										<div class="font-size-9 ">Date</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
										<input type="text"  id="rksj" class="form-control padding-left-3 padding-right-3 "  disabled="disabled"/>
									</div>
								</div>
							 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">批次号</div>
										<div class="font-size-9 ">B/N</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
										<input type="text"  id="pch" class="form-control padding-left-3 padding-right-3 "  disabled="disabled"/>
									</div>
								</div>
							 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">数量</div>
										<div class="font-size-9 ">QTY</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
										<input type="text"  id="kcsl" class="form-control padding-left-3 padding-right-3 "  disabled="disabled"/>
									</div>
								</div>
								<div class='clearfix'></div>
							</div>
							<!--**********************  -->
							<div id="toolcheck_open_modal_data_list">
							<div  name="zbjs" class="input-group-border padding-left-0" style='margin-top:15px;border:1px dashed #d5d5d5;'>
							   <input type="hidden" name="zid" id="zid" />
							   <input type="hidden"  id="bs" value="2"/>
							    <div style='width:80px;background:white;height:20px;margin-top:-20px;margin-left:20px;text-align:center'>
							     <button class="line6 line6-btn button" onclick="toolcheck_open_modal.remove(this)"  type="button"><i class="icon-minus cursor-pointer color-blue cursor-pointer"></i></button>
					   			</div>
			           			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-8 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><label class="required" style="color: red">*</label>编号</div>
										<div class="font-size-9 ">No.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text"     id="zbjxlh" class="form-control"  maxlength="50" disabled="disabled"/>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-8 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">中文名称</div>
										<div class="font-size-9 ">Chinese name</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text"   id="zzwms"  class="form-control"  maxlength="100" disabled="disabled"/>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-8 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">英文名称</div>
										<div class="font-size-9 ">English name</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text"   id="zywms" class="form-control"   maxlength="100" disabled="disabled"/>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-8 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">型号</div>
										<div class="font-size-9 ">Model</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text"    id="zxh" class="form-control noteditable"   maxlength="50"/>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">规格</div>
										<div class="font-size-9 ">Specifications</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text"    id="zgg" class="form-control noteditable"  maxlength="50"/>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><label class="required" style="color: red">*</label>周期</div>
										<div class="font-size-9 ">Cycle</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 padding-right-0">
										<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-0 padding-right-0">
											<input type="text" class="form-control" id="zjyZq"  placeholder='' onkeyup='clearNoNum(this)' maxlength="3"  />
										</div>
										<div class="col-lg-3 col-sm-3 col-xs-3 padding-left-2 padding-right-0">
											<select id='zjyZqdw'  class="form-control" onchange="toolcheck_open_modal.loadNextDate()">
												<option value="11" >天</option>
												<option value="12" >月</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><label class="required" style="color: red">*</label>校验日期</div>
										<div class="font-size-9 ">Date</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text"  id="zjyScrq"   class="form-control date-picker"  onchange="toolcheck_open_modal.loadNextDate()"/>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><label class="required" style="color: red">*</label>下次校验日期</div>
										<div class="font-size-9 ">Date</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text"   id="zjyXcrq"  class="form-control date-picker"  />
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">计量方式</div>
										<div class="font-size-9 ">Mode</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<select class="form-control "   id="jlfsSelect" >
										</select>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">计量等级</div>
										<div class="font-size-9 ">Grade</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text"  id="zjldj" class="form-control"  />
									</div>
								</div>
								<div id="fileDiv" class="div-hide col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group ">
									<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">附件</div>
										<div class="font-size-9">Attachment</div>
									</label>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<button type="button" onClick="toolcheck_open_modal.filseDown()" class="btn btn-xs btn-default padding-top-1 padding-bottom-1 "style="height:30px;line-height:30px;" title="上传">
											<i class="fa fa-upload" style="font-size:16px;"></i>
										</button>
									</div>
								</div>
								<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">备注</div>
										<div class="font-size-9">Remark</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
										<textarea style="height: 75px;" class="form-control" id="zbz"  maxlength="300"></textarea>
									</div>
								</div>
								<div class='clearfix'></div>
							</div>
							</div>
							<div id="toolcheck_open_modal_list">
					
							</div>
						
							<div class='clearfix'></div>
							<div style='padding-bottom:8px;' class='button'>
							     <button class="line6 line6-btn" onclick="toolcheck_open_modal.addRow()"  type="button">
								<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
					   			</button>
					   		</div>
							
						</div>
	     				<div class="clearfix"></div>
	     				<!-- <div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	
	     					<div class="input-group-border margin-top-8 padding-left-0">
	     						<button class="line6 line6-btn" onclick="ReferenceUtil.add()"  type="button">
								<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
					   			</button>
	     					<div class='clearfix'></div>
	     					</div>
	     				</div> -->
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
							<button id="tijiao" type="button" onclick="toolcheck_add_modal.submit();" class="btn btn-primary padding-top-1 padding-bottom-1">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/toolcheck/toolcheck_open.js"></script><!--计量工具的通用js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/toolcheck/toolcheck_add.js"></script><!--新增计量工具弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/toolcheck/toolcheck_update.js"></script><!--修改计量工具弹窗的js  -->
<%@ include file="/WEB-INF/views/open_win/select_toolequipment.jsp"%><!--工具设备选择-->
<%@ include file="/WEB-INF/views/material2/toolcheck/toolcheck_filesDown.jsp" %> <!--附件列表界面  -->
	<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>

