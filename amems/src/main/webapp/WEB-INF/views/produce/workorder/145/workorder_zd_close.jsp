<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal modal-new" id="workorder145_zdclose_Modal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
      <div class="modal-dialog" style='width:85%'>
          <div class="modal-content">
            <!--  Start:窗口标题 -->
            <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
		                      <div class="font-size-14 " id="modalHeadCN">工单指定结束</div>
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
						<form id="wo145zdclose_from">
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
										<th class="colwidth-9">
											<div class="font-size-12 line-height-18" >计划工时 </div>
											<div class="font-size-9 line-height-18" >Plan Hour</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18" >部件件号</div>
											<div class="font-size-9 line-height-18" >PN</div>
										</th>							
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18" >部件件号序列号</div>
											<div class="font-size-9 line-height-18" >SN</div>
										</th>							
										</tr> 
					         		 </thead>
									 <tbody id="wo145zdclose_list">
									 		<tr class='text-center'><td colspan='7'>暂无数据 No data.</td></tr>
									 </tbody>
									
								</table>
							</div>
							
							<div class="clearfix"></div>   
							
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">状态</div>
									<div class="font-size-9">Status</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo145zdclose_gdlx" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">下发单位</div>
									<div class="font-size-9">Issuing unit</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo145zdclose_xfdw" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">计划开始日期</div>
									<div class="font-size-9">Date</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo145zdclose_jhksrq" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">计划完成日期</div>
									<div class="font-size-9">Date</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo145zdclose_jhjsrq" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							
							<div class="clearfix"></div>
							
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">关闭人</div>
									<div class="font-size-9">Closer</div>
								</label>
								<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10 padding-leftright-8">
									<input id="wo145zdclose_gbr" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">关闭日期</div>
									<div class="font-size-9">Close Date</div>
								</label>
								<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10 padding-leftright-8">
									<input id="wo145zdclose_gbrq" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>   
							<div class="clearfix"></div>
							            									
							<div class="col-lg-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 "><span style="color:red" id="workorder145_zdclose_Modal_zdjsyymark">*</span>指定结束原因</div>
									<div class="font-size-9 ">Reason</div>
								</span>
							 	<div class="col-lg-11 col-md-11  col-sm-10 col-xs-9 padding-left-8 padding-right-0">
									<textarea class="form-control" id="wo145zdclose_gbyy" name="wo145zdclose_gbyy" placeholder=''   maxlength="1000" style="height:55px"></textarea>
								</div>
							</div>
							<div class="clearfix"></div>	
						</form>
						<div class="clearfix"></div>
					</div>
					<!-- End:基本字段 -->
					
					<div class="clearfix"></div>
					<!-- Start:引用页面 -->
					
					
					
					<div id="attachments_list_workorderedit_wo145zdclose" style="display:none">							
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
	                      <button type="button" id="workorder145_zdclose_Modal_save" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="Workorder145ZDCloseWin.submitFeedback()" >
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
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/145/workorder_zd_close.js"></script><!-- 145工单指定结束 -->