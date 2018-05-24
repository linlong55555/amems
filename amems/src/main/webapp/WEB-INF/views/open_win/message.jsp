<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/message.js"></script>
<div class="modal fade" id="MessageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:450px!important;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="MessageModalBody">
			  	<div class="panel panel-primary ">
					<div class="panel-heading  padding-top-3 padding-bottom-1  margin-bottom-10">
						<div class="font-size-16 line-height-18">留言</div>
						<div class="font-size-9 ">Message</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
					
		            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						
							<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">留言人</div>
									<div class="font-size-9 line-height-18">Message by</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" id="myswjm" class="form-control " value="${user.displayName}" readonly/>
								</div>
							</div>
							
							<div class="clearfix"></div>
							
							<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>提醒人</div>
									<div class="font-size-9 line-height-18">Remind by</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-0 padding-right-0">
										<textarea class="form-control" id="mjsrName" maxlength="300" readonly></textarea>
									</div>
									<button name="keyCodeSearch"
									 class=" btn btn-primary form-control pull-right"  style=" width:39px;"data-toggle="modal" onclick="MessageModal.openUserModalWin()">
										<i class="icon-search cursor-pointer"></i>
									</button>
								</div>
							</div>	
		
							<div class="clearfix"></div>
						
						 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								<label class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>留言内容</div>
									<div class="font-size-9 line-height-18">Message</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
									<textarea class="form-control" id="mnbwjm" maxlength="300" ></textarea>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="text-right margin-top-10 margin-bottom-10">
							<button type="button" onclick="MessageModal.setData()" class="btn btn-primary padding-top-1 padding-bottom-1" >
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
<%@ include file="/WEB-INF/views/open_win/user_multi.jsp"%><!-------用户对话框 -------->
