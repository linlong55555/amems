<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal modal-new" id="workpackage_close_modal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">     
 <div class="modal-dialog" style='width:85%;'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-14 " >工包完工关闭</div>
							<div class="font-size-12" >Close Workpackage</div> 
						  </div>
					 	  <div class='pull-right' >
					  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				  		  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
                  
            <div class="modal-body" >
   		        <div class="col-xs-12 margin-top-0 padding-left-10 padding-right-8">
 					  <div class="input-group-border margin-top-8 padding-left-0">
							
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">飞机注册号</div>
								<div class="font-size-9">A/C Reg</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<input class='form-control ' id="workpackage_close_fjzch" name="workpackage_close_fjzch" disabled="disabled">								
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">MSN</div>
								<div class="font-size-9">MSN</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<input class='form-control ' id="workpackage_close_msn" name="workpackage_close_msn" disabled="disabled">								
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">机型</div>
								<div class="font-size-9">A/C Type</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<input class='form-control ' id="workpackage_close_jx" name="workpackage_close_jx" disabled="disabled">								
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">类型</div>
								<div class="font-size-9">Type</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<input class='form-control ' id="workpackage_close_lx" name="workpackage_close_lx" value="正常完工" disabled="disabled">								
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工包编号</div>
								<div class="font-size-9">Packing No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<input class='form-control ' id="workpackage_close_gbbh" name="workpackage_close_gbbh" disabled="disabled">								
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">描述</div>
								<div class="font-size-9">Desc</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<input class='form-control ' id="workpackage_close_ms" name="workpackage_close_ms" disabled="disabled">								
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">下发单位</div>
								<div class="font-size-9">Issuing Unit</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<input text id="workpackage_close_xfdw" name="workpackage_close_xfdw" class="form-control"  maxlength="100" disabled="disabled">
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">计划开始日期</div>
								<div class="font-size-9">Date</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="workpackage_close_jhksrq" name="workpackage_close_jhksrq" disabled="disabled" />
						    </div>
						</div>	
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">计划完成日期</div>
								<div class="font-size-9">Date</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="workpackage_close_jhwcrq" name="workpackage_close_jhwcrq" disabled="disabled" />
						    </div>
						</div>
						<div id="vjsr" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2" id="gbr_name">关闭人</div>
								<div class="font-size-9" id="gbr_ename">Closer</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<input class="form-control"   id="workpackage_close_gbr" name="workpackage_close_gbr" disabled="disabled">
							</div>
						</div>
					
						<div id="vjssj" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2" id="date_name">关闭日期</div>
								<div class="font-size-9" id="date_ename">Closing Date</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<input class="form-control "  id="workpackage_close_gbrq" name="workpackage_close_gbrq" disabled="disabled">
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="panel panel-primary" id="workpackage_close_closeInfo_div">
					<form id="workpackage_close_form">
						<div class="panel-heading bg-panel-heading">
							   <div class="font-size-12 ">关闭信息</div>
							  <div class="font-size-9">Close Info</div>
						</div>
						<div class="panel-body" style="padding-bottom:0px;padding-left:0px;padding-right:0px">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color:red" id="workpackage_close_sjwcrq_remark">*</span>实际完成时间</div>
									<div class="font-size-9">Time</div>
								</label>
								<div class="col-lg-5 col-sm-5 col-xs-5 padding-left-8 padding-right-0">
									<input class="form-control date-picker" id="workpackage_close_sjwcrq" name="workpackage_close_sjwcrq" data-date-format="yyyy-mm-dd" type="text">
								</div>
								<div class="col-lg-3 col-sm-3 col-xs-3 padding-left-0 padding-right-8">
									<input class="form-control time-masked" name="workpackage_close_sjwcsj" id="workpackage_close_sjwcsj" style="border-left: 0;" type="text">
								</div>
							</div>	
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">实际开始日期</div>
									<div class="font-size-9">Date</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="workpackage_close_sjksrq" name="workpackage_close_jhksrq"  />
							    </div>
							</div>	
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">工作站点</div>
									<div class="font-size-9">Work Site</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" id="workpackage_close_gzzd" name="workpackage_close_gzzd" class="form-control" maxlength="100" >
								</div>
							</div> 
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">执行单位</div>
									<div class="font-size-9">Unit</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0 pull-left ">
									<div class='input-group ' style="min-width:100%">
										<input type="text" value="" name="workpackage_close_bm" maxlength="100" class="form-control readonly-style"
										 id="workpackage_close_bm" readonly="readonly"/>
										<span class="input-group-btn" id="workpackage_close_bmbutton">
											<button type="button" class="btn btn-default " id="gzzbtn"
											 data-toggle="modal"
											onclick="workpackage_close_modal.openzrdw('bm')">
											<i class="icon-search cursor-pointer"></i>
											</button>
										</span>
									</div>
								</div>				
								<input type="hidden" value="" name="workpackage_close_bmid" class="form-control " placeholder='' maxlength="" id="workpackage_close_bmid">
							</div>
							<div class="clearfix"></div>												
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">工作者</div>
									<div class="font-size-9">Worker</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0 ">
									<div class='input-group ' style="min-width:100%">
										<input type="text" value="" name="workpackage_close_gzzname" maxlength="100" class="form-control "
										 id="workpackage_close_gzzname" />										
									</div>
								</div>								
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">检查者</div>
									<div class="font-size-9">Examiner</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<div class='input-group ' style="min-width:100%">
										<input type="text" value="" name="workpackage_close_jczname" maxlength="100" class="form-control "
										 id="workpackage_close_jczname" />										
									</div>
								</div>								
							</div>							
							<div  class="col-xs-12 padding-left-0 padding-right-8 form-group" >
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2" >关闭详情</div>
									<div class="font-size-9" >Close the reason</div>
								</label>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
									<textarea class='form-control' style="height:75px" id="workpackage_close_gbxq" name="workpackage_close_gbxq"  maxlength="1000"></textarea>
								</div>
							</div>
	           			</div>
	           			<div class="clearfix"></div> 
	          		</form>       
	          		</div>
	          		<div id="workpackage_close_attachments_list_edit" style="display:none">							
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					</div>	
	          		<div class="clearfix"></div> 	          		   
	          	</div>
	           <div class="clearfix"></div>    
           </div>
     		  <div class="modal-footer ">
				<div class="col-xs-12 padding-left-8 padding-right-0" >
					<span class="pull-left modalfootertip" >
			               <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
					</span>
					<div class="pull-right margin-right-8">
					   <button id="workpackage_close_savebutton" type="button" onclick="workpackage_close_modal.saveData();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
						</button>
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
						</button>
					</div>
				</div>
			 </div>
           </div>
        </div>
</div>
    <script type="text/javascript" src="${ctx }/static/js/thjw/produce/workpackage/145/workpackage145_close.js"></script>