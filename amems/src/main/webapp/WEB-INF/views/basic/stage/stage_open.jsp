<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"
	language="java"%>
<div class="modal modal-new" id="AddEditAlertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 50%;">
			<div class="modal-content">
			   <div class="modal-header modal-header-new" >
	               	<h4 class="modal-title" >
	           			<div class='pull-left'>
	               			<div class="font-size-12" id="modalName"></div>
							<div class="font-size-9 " id="modalEname"></div>
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
							<form id="form">
								   <div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<span style="color: red" id="hideOrShow">*</span>阶段编号
											</div>
											<div class="font-size-9">Stage No.</div>
										</span>
										<div class="col-sm-9 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="jdbh" name="jdbh" maxlength="50"/>
										<input type="hidden" id="stageid" value=""/>
										</div>
									</div>
									<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												阶段名称
											</div>
											<div class="font-size-9">Name</div>
										</span>
										<div class="col-sm-9 col-xs-9 padding-leftright-8">
											<input type="text" class="form-control " id="jdmc"
												name="jdmc" maxlength="50" />
										</div>
									</div>
									<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												项次
											</div>
											<div class="font-size-9">Item No.</div>
										</span>
										<div class="col-sm-9 col-xs-9 padding-leftright-8">
											<input type="text" class="form-control " id="xc" name="xc" maxlength="10" />
										</div>
									</div>
									<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">描述</div>
											<div class="font-size-9">Description</div>
										</span>
										<div class="col-sm-9 col-xs-9 padding-leftright-8">
											<textarea class="form-control" id="jdms" name="jdms" rows="3" cols="34" maxlength="100"></textarea>
										</div>
										
									</div>
									<div class='clearfix'></div>
								</form>	
							</div>
						</div>	
				</div>
				<div class="modal-footer">
	          		<div class="col-xs-12 padding-leftright-8" >
						<div class="input-group">
							<span class="input-group-addon modalfootertip" >
		                      <i class='glyphicon glyphicon-info-sign alert-info'></i>
		                      <p class="alert-info-message"></p>
					        </span>
		                   	<span class="input-group-addon modalfooterbtn">
		                     	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="saveUpdate()" 
		                     	      name="" id="commitButton">
									<div class="font-size-12">提交</div>
									<div class="font-size-9">Submit</div>
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