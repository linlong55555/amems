<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/batchApprovel.js"></script>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="BatchApprovelModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>

      <div class="modal-dialog" style="width:45%;">
            <div class="modal-content" >
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          	<div class='pull-left'>
		                 		<div class="font-size-12" id="modelHeadChina">批量审核</div>
								<div class="font-size-9" id="modelHeadEnglish">Checked by</div>
							</div>
			 				<div class='pull-right' >
								<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
							</div>
							<div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
              	<div class="col-lg-12 col-sm-12 col-xs-12 ">
              	          <div style='border-bottom:1px solid #d5d5d5; margin-bottom:8px;padding-bottom:8px;margin-top:8px;'>
							<div class="col-lg-6 col-xs-12 col-sm-12  padding-left-0 padding-right-0 ">
								<div class="col-lg-12 col-sm-6 padding-left-8 padding-right-0" style="width:100%">
									<div id="isApprovel_li" style="list-style:none;padding-left: 24px;">下列单据状态为提交状态，可以进行审核：</div>
									<ul>
										<div id="approve_list">暂无数据</div>
									</ul>
								</div>
							</div>
							
			            	<div class="col-lg-6 col-xs-12 col-sm-12  padding-left-0 padding-right-0 ">
								<div class="col-lg-12 col-sm-6 padding-left-8 padding-right-0" style="width:100%">
									<div id="isNotApprovel_li" style="list-style:none;padding-left: 24px;">下列单据状态不是提交状态，不可进行审核：</div>
									<ul >
									<!-- <li id="isNotApprovel_li" style="list-style:none;">下列单据状态不是提交状态，不可进行审核：</li> -->
										<div id="approve_not_list">暂无数据</div>
									</ul>
								</div>
							</div>
			            	<div class="clearfix"></div>
			            	</div>
							<div style='border-bottom:1px solid #d5d5d5; margin-bottom:8px;padding-bottom:8px;'>
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"  >
								<div class="font-size-12 line-height-18" style="font-weight:normal">意见</div>
								<div class="font-size-9 line-height-18" style="font-weight:normal">Opinion</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
							    <div class='col-xs-12'>
							    	<textarea class="form-control" id="yj" name="yj" maxlength="150" style="height: 85px;outline:none;resize:none;"></textarea>
							    </div>
							    <%-- <div class='col-xs-12 margin-top-8'>
							       	<div class='col-xs-6 padding-left-0'>
								       <div class='pull-left'>
								       		<div class="font-size-12" id="personChina">审核人</div>
										    <div class="font-size-9" id="personEnglish">Checked by</div>
								       </div>
							       		<div class='pull-left' style='margin-top:8px;margin-left:5px;'>${user.displayName}</div>
								  		<div class="clearfix"></div>
				                	</div>
				                	<div class='col-xs-6'>
				                		<div class='pull-left'>
								       		<div class="font-size-12" id="timeChina">审核时间</div>
									        <div class="font-size-9" id="timeEnglish">Date</div>
							       		</div>
							       		<div id="optionTime" class='pull-left' style='margin-top:8px;margin-left:5px;'>XXXXXXX</div>
								  		<div class="clearfix"></div>
				                	</div>
							    </div> --%>
							    </div>
								 <div class="clearfix"></div>
							</div>
							<div class="clearfix"></div>
							</div>
							<!-- <div class='col-xs-12' style='margin-bottom:8px;'>是否继续操作？</div> -->
		
						<div class="clearfix"></div>
						</div>
			 <div class='clearfix'></div>
          
           <div class="modal-footer">
           	<div class="col-xs-12 padding-leftright-8" >
				<div class="input-group">
				<span class="input-group-addon modalfootertip" >
	                  <!--  <i class='glyphicon glyphicon-info-sign alert-info'></i>警告！请不要提交 -->
				</span>
                    <span class="input-group-addon modalfooterbtn">
                      <button type="button" onclick="BatchApprovelModal.setData()" class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
                    </span>
               	</div><!-- /input-group -->
			</div>
			</div>
			 </div>
            </div>
          </div>
	
<!--  弹出框结束-->



<!-- <div class="modal fade" id="BatchApprovelModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:600px!important;">
		<div class="modal-content">
		
			<div class="modal-header modal-header-new" >
         		<h4 class="modal-title" >
             		<div id="batchReviewHead" class='pull-left'>
                 		<div class="font-size-14 ">批量审核</div>
						<div class="font-size-12">Review</div>
					</div>
					<div id="batchApprovalHead" class='pull-left'>
                 		<div class="font-size-14 ">批量批准</div>
						<div class="font-size-12">Approval</div>
					</div>
	 				<div class='pull-right' style='padding-top:10px;'>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" style='font-size:30px !important;' >&times;</span></button>
					</div>
					<div class='clearfix'></div>
               	</h4>
          	</div>
		
			<div class="modal-body padding-bottom-0" id="BatchApprovelModalBody">
			  	<div class="panel panel-primary ">
					<div class="panel-body padding-top-0 padding-bottom-0">
					
		            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							<div class="col-lg-6 col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
								<div class="col-lg-12 col-sm-6 padding-left-8 padding-right-0" style="width:70%">
									<ul>
									<li>可操作的数据</li>
										<div id="approve_list">暂无数据</div>
									</ul>
								</div>
							</div>
							
			            	<div class="col-lg-6 col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
								<div class="col-lg-12 col-sm-6 padding-left-8 padding-right-0" style="width:70%">
									<ul>
									<li>不可操作的数据</li>
										<div id="approve_not_list">暂无数据</div>
									</ul>
								</div>
							</div>
			            	<div class="clearfix"></div>
							<hr width="500px" />
							
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"  >
								<div class="font-size-12 line-height-18" style="font-weight:normal">意见</div>
								<div class="font-size-9 line-height-18" style="font-weight:normal">Opinion</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
								<textarea class="form-control" id="yj" name="yj" maxlength="150" style="height: 85px;outline:none;resize:none;"></textarea>
							</div>
							<div class="clearfix"></div>
							
							<hr width="500px" />
							<div >是否继续操作？</div>
		
						
						</div>
						<div class="clearfix"></div>
						<div class="text-right margin-top-10 margin-bottom-10">
							<button type="button" onclick="BatchApprovelModal.setData()" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
		                </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
 -->