<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal modal-new" id="workpackage_feedback_modal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:80%'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
		                      <div class="font-size-14 " id="modalName">工包完工反馈</div>
							  <div class="font-size-12" id="modalEname">Workpackage Complete feedback</div>
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
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">飞机注册号</div>
							<div class="font-size-9">A/C Reg</div>
						</label>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class='form-control ' name="workpackage_feedback_fjzch" id="workpackage_feedback_fjzch" type='text' disabled="disabled">							
						</div>
					</div>
					
					 <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">MSN</div>
							<div class="font-size-9">MSN</div>
						</label>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" name="workpackage_feedback_msn" id='workpackage_feedback_msn' disabled="disabled">
						</div>
					</div>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">机型</div>
							<div class="font-size-9">A/C Type</div>
						</label>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control "  id="workpackage_feedback_jx" name="workpackage_feedback_jx" maxlength="50" disabled="disabled"/>
						</div>
					</div>
					
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">工包编号</div>
							<div class="font-size-9">Package No.</div>
						</label>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control " id="workpackage_feedback_gbbh" name="workpackage_feedback_gbbh" maxlength="10" disabled="disabled"/>
						</div>
					</div>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">描述</div>
							<div class="font-size-9">Desc</div>
						</label>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control " id="workpackage_feedback_ms" name="workpackage_feedback_ms" maxlength="15" disabled="disabled" />
						</div>
					</div>					
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">下发单位</div>
							<div class="font-size-9">Issuing Unit</div>
						</label>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input text id="workpackage_feedback_xfdw" name="workpackage_feedback_xfdw" class="form-control" maxlength="100" disabled="disabled">
						</div>
					</div>
					<form id="workpackage_feedback_form">
					<div id="workpackage_feedback_doDiv">
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 "><span style="color:red" id="workpackage_feedback_sjwcrq_remark">*</span>实际完成时间</div>
							<div class="font-size-9">Finished Time</div>
						</label>
						<div class="col-lg-6 col-sm-5 col-xs-5 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="workpackage_feedback_sjwcrq" name="workpackage_feedback_sjwcrq" data-date-format="yyyy-mm-dd" type="text">
						</div>
						<div class="col-lg-3 col-sm-3 col-xs-3 padding-left-0 padding-right-8">
							<input class="form-control time-masked" name="workpackage_feedback_sjwcsj" id="workpackage_feedback_sjwcsj" style="border-left: 0;" type="text">
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">实际开始日期</div>
							<div class="font-size-9">Date</div>
						</label>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="workpackage_feedback_sjksrq" name="workpackage_feedback_sjksrq"  />
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">工作站点</div>
							<div class="font-size-9">Work Site</div>
						</label>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" id="workpackage_feedback_gzzd" name="workpackage_feedback_gzzd" class="form-control" maxlength="100" >
						</div>
					</div> 
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">实际执行单位</div>
							<div class="font-size-9">Execution Unit</div>
						</label>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-8">
							<span class='margin-right-15 label-input'>	<input type="radio" name="workpackage_feedback_sjzxdw_radio" value="0" checked="checked" />&nbsp;内部</span>
							<span class='label-input margin-left-15'>	<input type="radio" name="workpackage_feedback_sjzxdw_radio" value="1"  />&nbsp;外委</span>
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="workpackage_feedback_sjzxdw_bmdiv">
						<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">部门</div>
							<div class="font-size-9">Department</div>
						</label>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left ">
							<div class='input-group ' style="min-width:100%">
								<input type="text" value="" name="workpackage_feedback_bm" class="form-control readonly-style"
								 id="workpackage_feedback_bm" readonly="readonly" ondblclick="workpackage_feedback_modal.openzrdw('bm')" />
								<span class="input-group-btn" id="workpackage_feedback_bmbutton">
									<button type="button" class="btn btn-default " 
									 data-toggle="modal"
									onclick="workpackage_feedback_modal.openzrdw('bm')">
									<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
							</div>
						</div>				
						<input type="hidden" value="" name="workpackage_feedback_bmid" class="form-control " placeholder='' maxlength="" id="workpackage_feedback_bmid">
					</div>
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="workpackage_feedback_sjzxdw_gysdiv">
						<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">供应商</div>
							<div class="font-size-9">Supplier</div>
						</label>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left ">
							<div class='input-group ' style="min-width:100%">
								<input type="text" value="" name="workpackage_feedback_gys" class="form-control readonly-style" 
								 id="workpackage_feedback_gys" readonly="readonly" ondblclick="workpackage_feedback_modal.openGys()"/>
								<span class="input-group-btn" id="workpackage_feedback_gysbutton">
									<button type="button" class="btn btn-default " 
									 data-toggle="modal"
									onclick="workpackage_feedback_modal.openGys()">
									<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
							</div>
						</div>					
						<input type="hidden" value="" name="workpackage_feedback_gysid" class="form-control " placeholder='' maxlength="" id="workpackage_feedback_gysid"/>
					</div> 
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">工作者</div>
							<div class="font-size-9">Worker</div>
						</label>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left ">
							<div class='input-group ' style="min-width:100%">
								<input type="text" value="" name="workpackage_feedback_gzzname" class="form-control " maxlength="100"
								 id="workpackage_feedback_gzzname" />								
							</div>
						</div>						
					</div>
					<div class="clearfix"></div>
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">检查者</div>
							<div class="font-size-9">Checker</div>
						</label>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left">
							<div class='input-group ' style="min-width:100%">
								<input type="text" value="" name="workpackage_feedback_jczname" class="form-control " maxlength="100"
								 id="workpackage_feedback_jczname" />								
							</div>
						</div>					
					</div>
					</div>
					</form>
					<div class="clearfix"></div>
					</div>
					<div id="workpackage_feedback_attachments_list_edit" style="display:none">							
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
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
	                      <button type="button" id="workpackage_feedback_savebutton" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="workpackage_feedback_modal.saveData()" >
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
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
<!--  弹出框结束-->
 <script type="text/javascript" src="${ctx }/static/js/thjw/produce/workpackage/135/workpackage_feedback.js"></script>