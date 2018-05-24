<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal modal-new" id="AddalertModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:65%'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
		                      <div class="font-size-14 " id="modalName"></div>
							  <div class="font-size-12" id="modalEname"></div>
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
	                    <div class="col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-2 col-sm-3 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>产权编码</div>
								<div class="font-size-9">No.</div>
							</label>
							<div class="col-md-10 col-sm-9 col-xs-8 padding-leftright-8">
								<input type="text" class="form-control " id="cqbm" name="cqbm" maxlength="160" />
								<input type="hidden" class="form-control " id="propertyrightId" name="propertyrightId" />
								<input type="hidden" class="form-control " id="propertyrightDprtcode" name="propertyrightDprtcode" />
							</div>
						</div>
						
	                    <div class="col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-2 col-sm-3 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">维护人</div>
								<div class="font-size-9">Maintainer</div>
							</label>
							<div class="col-md-10 col-sm-9 col-xs-8 padding-leftright-8">
								<input type="text" class="form-control " id="whrText" name="whrText" disabled="disabled" />
							</div>
						</div>
						<div class='clearfix'></div>
						
	                    <div class="col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-2 col-sm-3 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>飞机注册号</div>
								<div class="font-size-9">A/C REG</div>
							</label>
							<div class="col-md-10 col-sm-9 col-xs-8 padding-leftright-8">
								<select class="form-control " id="fjzch" name="fjzch" onchange="fjzchChange()">
								</select>
							</div>
						</div>
						
						<div class="col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-2 col-sm-3 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">公司</div>
								<div class="font-size-9">Company</div>
							</label>
							<div class="col-md-10 col-sm-9 col-xs-8 padding-leftright-8">
								<div class="input-group">
									<input id="gsText"  name="gsText" class="form-control readonly-style"  ondblclick="openCustomer()" type="text" readonly="readonly">
									<input id="gsId" class="form-control" type="hidden">
									<input id="gsbm" class="form-control" type="hidden">
						            <span class="input-group-btn">
										<button type="button" class="btn btn-default form-control" style="height:34px;" data-toggle="modal" onclick="openCustomer()">
											<i class="icon-search cursor-pointer"></i>
										</button>
									</span>
						       </div>
							</div>
						</div>
						</form>
						
						<div class='clearfix'></div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-1 col-sm-3 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">备注</div>
								<div class="font-size-9">Note</div>
							</label>
							<div class="col-md-11 col-sm-9 col-xs-8 padding-leftright-8">
								<textarea id="bz" name="bz" class="form-control" maxlength="300" style="height:55px"></textarea>
							</div>
						</div>
						
						<div class="clearfix"></div>	
					</div>
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
		                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="saveUpdate(1)" id="baocunButton">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="saveUpdate(0)" id="baocunButton">
								<div class="font-size-12">提交并编写下一条</div>
								<div class="font-size-9">The next</div>
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