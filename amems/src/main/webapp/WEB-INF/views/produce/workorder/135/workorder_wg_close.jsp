<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal modal-new" id="workorder135_wgclose_Modal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
      <div class="modal-dialog" style='width:85%'>
          <div class="modal-content">
            <!--  Start:窗口标题 -->
            <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
		                      <div class="font-size-14 " id="modalHeadCN">工单完工关闭</div>
							  <div class="font-size-12" id="modalHeadENG">Close</div>
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
						<form id="wo135wgclose_from">
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
											<div class="font-size-9 line-height-18" >WO No.</div>
										</th>
										<th class=" colwidth-7" >
											<div class="font-size-12 line-height-18" >ATA章节号</div>
											<div class="font-size-9 line-height-18" >ATA</div>
										</th>
										<th class="colwidth-10" >
											<div class="font-size-12 line-height-18" >工单标题</div>
											<div class="font-size-9 line-height-18" >WO Title</div>
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
											<div class="font-size-12 line-height-18" >部件件号序列号</div>
											<div class="font-size-9 line-height-18" >S/N</div>
										</th>							
										</tr> 
					         		 </thead>
									 <tbody id="wo135wgclose_list">
									 		<tr class='text-center'><td colspan='7'>暂无数据 No data.</td></tr>
									 </tbody>
									
								</table>
							</div>
							
							<div class="clearfix"></div>   
							
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">状态</div>
									<div class="font-size-9">Status</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo135wgclose_gdlx" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">下发单位</div>
									<div class="font-size-9">Issuing Unit</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo135wgclose_xfdw" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">计划开始日期</div>
									<div class="font-size-9">Date</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo135wgclose_jhksrq" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">计划完成日期</div>
									<div class="font-size-9">Date</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo135wgclose_jhjsrq" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">关闭人</div>
									<div class="font-size-9">Closer</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo135wgclose_gbr" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">关闭日期</div>
									<div class="font-size-9">Close Date</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo135wgclose_gbrq" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>   
							<div class="clearfix"></div>
							<div id="workorder135_wgclose_Modal_optionDiv">						   
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color:red" id="workorder135_wgclose_Modal_sjwcrq_remark">*</span>实际完成时间</div>
									<div class="font-size-9">Finished Time</div>
								</label>
								<div class="col-lg-6 col-sm-6 col-xs-6 padding-left-8 padding-right-0">
									<input class="form-control date-picker" id="wo135wgclose_sjJsrq" name="wo135wgclose_sjJsrq" data-date-format="yyyy-mm-dd" type="text">
								</div>
								<div class="col-lg-3 col-sm-3 col-xs-3 padding-left-0 padding-right-8">
									<input class="form-control time-masked" name="wo135wgclose_sjJssj" id=wo135wgclose_sjJssj style="border-left: 0;" type="text">
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">实际开始日期</div>
									<div class="font-size-9">Date</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="wo135wgclose_sjksrq" />
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">实际工时</div>
									<div class="font-size-9">Working Hours</div>
								</label>
								<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 padding-leftright-8">
									<input type="text" id="wo135wgclose_sjgs" name="wo135wgclose_sjgs" class="form-control" maxlength="10" >
								</div>
								<div class="col-xs-3 col-sm-3 padding-left-9 padding-right-0">
									<label class="pull-right" style="margin-top:6px;font-weight:normal;">
										<input id="wo135wgclose_hsgs" value="1" style="vertical-align:text-bottom" checked="checked" type="checkbox">&nbsp;核算工时&nbsp;&nbsp;
									</label>
								</div>
							</div> 
							<div class="clearfix"></div>              									
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">工作者</div>
									<div class="font-size-9">Worker</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left">
									<input class="form-control" id="wo135wgclose_sjgzzid" type="hidden">
									<div class="input-group col-xs-12">
										<input id="wo135wgclose_sjgzz" type="text" name="wo135wgclose_sjgzz" class="form-control readonly-style" readonly="readonly"/>						                    
				                		<span class="input-group-addon btn btn-default" id="wo135wgclose_sjgzz_btn" onclick="Workorder135WGCloseWin.openGzzWin()">
					                    	<i class="icon-search cursor-pointer"></i>
					                    </span>
				                	</div>
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">检查者</div>
									<div class="font-size-9">Checker</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left">
									<input class="form-control" id="wo135wgclose_sjjczid" type="hidden">
									<div class="input-group col-xs-12">
										<input id="wo135wgclose_sjjcz" type="text" name="wo135wgclose_sjjcz" class="form-control" />
										<span class="input-group-addon btn btn-default" id="wo135wgclose_sjjcz_btn" onclick="Workorder135WGCloseWin.openJczWin()">
					                    	<i class="icon-search cursor-pointer"></i>
					                    </span>		
				                	</div>
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">工作站点</div>
									<div class="font-size-9">Work Site</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text" id="wo135wgclose_sjzd" name="wo135wgclose_sjzd" class="form-control" maxlength="100" >
								</div>
							</div>
							<div class="col-lg-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">故障信息</div>
									<div class="font-size-9 ">Fault Info</div>
								</span>
							 	<div class="col-lg-11 col-md-11  col-sm-10 col-xs-9 padding-left-8 padding-right-0">
										<textarea class="form-control" id="wo135wgclose_gzxx" name="wo135wgclose_gzxx" placeholder=''   maxlength="1000" style="height:55px"></textarea>
								</div>
							</div>
							<div class="col-lg-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">处理措施</div>
									<div class="font-size-9 ">Treatment Measures</div>
								</span>
							 	<div class="col-lg-11 col-md-11  col-sm-10 col-xs-9 padding-left-8 padding-right-0">
										<textarea class="form-control" id="wo135wgclose_clcs" name="wo135wgclose_clcs" placeholder=''   maxlength="1000" style="height:55px"></textarea>
								</div>
							</div>
							<div class="col-lg-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">关闭详情</div>
									<div class="font-size-9 ">Close Detail</div>
								</span>
							 	<div class="col-lg-11 col-md-11  col-sm-10 col-xs-9 padding-left-8 padding-right-0">
									<textarea class="form-control" id="wo135wgclose_gbyy" name="wo135wgclose_gbyy" placeholder=''   maxlength="1000" style="height:55px"></textarea>
								</div>
							</div>
							</div>	
							<div class="clearfix"></div>	
						</form>
						<div class="clearfix"></div>
					</div>
					<!-- End:基本字段 -->
					
					<div class="clearfix"></div>
					<!-- Start:引用页面 -->
					<div id="replacementRecord_wo135wgclose_div" style="display:none">
						<%@ include file="/WEB-INF/views/produce/maintenanceissuedplan/replacementRecord.jsp"%><!-- 拆换件记录 -->
					</div>	
					
					<div class="clearfix"></div>
					<div id="attachments_list_workorderedit_wo135wgclose" style="display:none">							
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
	                      <button type="button" id="workorder135_wgclose_Modal_save" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="Workorder135WGCloseWin.submitFeedback()" >
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
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
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/135/workorder_wg_close.js"></script><!-- 135工单完工反馈 -->