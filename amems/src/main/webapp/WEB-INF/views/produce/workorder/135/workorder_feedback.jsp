<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal modal-new" id="workorder135_feedback_Modal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
      <div class="modal-dialog" style='width:85%'>
          <div class="modal-content">
            <!--  Start:窗口标题 -->
            <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
		                      <div class="font-size-14 " id="modalHeadCN">工单完工反馈</div>
							  <div class="font-size-12" id="modalHeadENG">W/O Complete Feedback</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
            </div>
            <!--  End:窗口标题 -->
            
            <!-- 主体内容 -->
            <div class="modal-body" >
             	<div class="col-xs-12 margin-top-8 ">
              	    <!-- Start:基本字段 -->
	              	<div class="input-group-border">
						<form id="wo135feedback_from">
							<div class="col-lg-12 col-md-12 padding-left-8 margin-top-0 padding-right-8 table-set" style="overflow-x: auto;" >
								<table id="workorder_feedback_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 900px !important">
									<thead>
										<tr>
										<th class="colwidth-5"  >
											<div class="font-size-12 line-height-18" >类型</div>
											<div class="font-size-9 line-height-18" >Type</div>
										</th>
										<th class="colwidth-7"  >
											<div class="font-size-12 line-height-18" >工单编号</div>
											<div class="font-size-9 line-height-18" >W/O No.</div>
										</th>
										<th class=" colwidth-7" >
											<div class="font-size-12 line-height-18" >ATA章节号</div>
											<div class="font-size-9 line-height-18" >ATA</div>
										</th>
										<th class="colwidth-10" >
											<div class="font-size-12 line-height-18" >工单标题</div>
											<div class="font-size-9 line-height-18" >W/O Title</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 line-height-18" >计划工时 </div>
											<div class="font-size-9 line-height-18" >Plan Hour</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18" >部件件号</div>
											<div class="font-size-9 line-height-18" >P/N</div>
										</th>							
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18" >部件序列号</div>
											<div class="font-size-9 line-height-18" >S/N</div>
										</th>							
										</tr> 
					         		 </thead>
									 <tbody id="wo135feedback_list">
									 		<tr class='text-center'><td colspan='7'>暂无数据 No data.</td></tr>
									 </tbody>
									
								</table>
							</div>
							<!-- <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">实际执行单位</div>
									<div class="font-size-9">Executive Unit</div>
								</label>
								<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 padding-left-8 padding-right-0">
									<span class='label-input'>	<input type="checkbox" name="ww" value="3" checked="checked" />&nbsp;外委</span>
								</div>
								<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 padding-leftright-8">
									<div class='input-group ' style="min-width:100%">
									    <input type="text"  value="" name="ff" class="form-control readonly-style" placeholder='' maxlength="100" readonly="readonly" id="ff">
										<span class="input-group-btn" id="ffbmid" >
											<button type="button" class="btn btn-default " id="wxrybtn" data-toggle="modal" onclick="openzrdw()">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</span>
										<input type="hidden" value="" name="ffid" class="form-control " placeholder='' maxlength="" id="ffid">
									</div>
								</div>
							</div> -->
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color:red" id="workorder135_feedback_Modal_sjwcrq_remark">*</span>实际完成时间</div>
									<div class="font-size-9">Finished Time</div>
								</label>
								<div class="col-lg-6 col-sm-6 col-xs-6 padding-left-8 padding-right-0">
									<input class="form-control date-picker" id="wo135feedback_sjJsrq" name="wo135feedback_sjJsrq" data-date-format="yyyy-mm-dd" type="text">
								</div>
								<div class="col-lg-3 col-sm-3 col-xs-3 padding-left-0 padding-right-8">
									<input class="form-control time-masked" name="wo135feedback_sjJssj" id="wo135feedback_sjJssj" style="border-left: 0;" type="text">
								</div>
							</div> 
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">实际开始日期</div>
									<div class="font-size-9">Starting Date</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="wo135feedback_sjksrq" />
								</div>
							</div> 
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">实际工时</div>
									<div class="font-size-9">Working Hours</div>
								</label>
								<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 padding-leftright-8">
									<input type="text" id="wo135feedback_sjgs" name="wo135feedback_sjgs" class="form-control" maxlength="10" >
								</div>
								<div class="col-xs-3 col-sm-3 padding-left-9 padding-right-0">
									<label class="pull-right" style="margin-top:6px;font-weight:normal;">
										<input id="wo135feedback_hsgs" value="1" style="vertical-align:text-bottom" checked="checked" type="checkbox">&nbsp;核算工时&nbsp;&nbsp;
									</label>
								</div>
							</div> 
							<div class="clearfix"></div>            									
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">工作者</div>
									<div class="font-size-9">Worker</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 padding-leftright-8 pull-left">
									<input class="form-control" id="wo135feedback_sjgzzid" type="hidden">
									<div class="input-group col-xs-12">
										<input id="wo135feedback_sjgzz" type="text" class="form-control readonly-style" name="wo135feedback_sjgzz" maxlength="100" readonly="readonly"/>
										<span class="input-group-addon btn btn-default" id="wo135feedback_sjgzz_btn" onclick="Workorder135FeedbackWin.openGzzWin()">
					                    	<i class="icon-search cursor-pointer"></i>
					                    </span>
									</div>								
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">检查者</div>
									<div class="font-size-9">Checker</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 padding-leftright-8 pull-left">	
									<input class="form-control" id="wo135feedback_sjjczid" type="hidden">
									<div class="input-group col-xs-12">
										<input id="wo135feedback_sjjcz" type="text" name="wo135feedback_sjjcz" class="form-control readonly-style" maxlength="100" readonly="readonly"/>	
										<span class="input-group-addon btn btn-default" id="wo135feedback_sjjcz_btn" onclick="Workorder135FeedbackWin.openJczWin()">
					                    	<i class="icon-search cursor-pointer"></i>
					                    </span>													                    					                	
									</div>								
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">工作站点</div>
									<div class="font-size-9">Work Site</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 padding-leftright-8">
									<input type="text" id="wo135feedback_sjzd" name="wo135feedback_sjzd" class="form-control" maxlength="100" >
								</div>
							</div>
							<div class="clearfix"></div>  
							<div class="col-lg-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">故障信息</div>
									<div class="font-size-9 ">Fault Info</div>
								</span>
							 	<div class="col-lg-11 col-md-11  col-sm-10 col-xs-9 padding-left-8 padding-right-0">
										<textarea class="form-control" id="wo135feedback_gzxx" name="wo135feedback_gzxx" placeholder=''   maxlength="1000" style="height:55px"></textarea>
								</div>
							</div>
							<div class="col-lg-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">处理措施</div>
									<div class="font-size-9 ">Treatment Measures</div>
								</span>
							 	<div class="col-lg-11 col-md-11  col-sm-10 col-xs-9 padding-left-8 padding-right-0">
										<textarea class="form-control" id="wo135feedback_clcs" name="wo135feedback_clcs" placeholder=''   maxlength="1000" style="height:55px"></textarea>
								</div>
							</div>
							<div class="clearfix"></div>	
						</form>
						<div class="clearfix"></div>
					</div>
					<!-- End:基本字段 -->
					
					<div class="clearfix"></div>
					<!-- Start:引用页面 -->
					<div id="wo135feedback_replacementRecord_div" style="display:none">
						<%@ include file="/WEB-INF/views/produce/maintenanceissuedplan/replacementRecord.jsp"%><!-- 拆换件记录 -->
					</div>	
					
					<div class="clearfix"></div>
					<div id="wo135feedback_attachments_list_workorderedit" style="display:none">							
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					</div>
					<div class="clearfix"></div>
					<!-- End:引用页面 -->
					
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
	                      <button type="button" id="workorder135_feedback_Modal_save" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="Workorder135FeedbackWin.submitFeedback()" >
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Save</div>
							</button>
		                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">取消</div>
								<div class="font-size-9">Cancel</div>
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
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/135/workorder_feedback.js"></script><!-- 135工单完工反馈 -->