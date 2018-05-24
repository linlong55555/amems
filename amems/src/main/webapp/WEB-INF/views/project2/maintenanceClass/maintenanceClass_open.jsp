<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="AddalertModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:50%;'>
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
              	<div class="col-xs-12  ">
              	<div class="input-group-border">
                    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">机型</div>
							<div class="font-size-9">A/C Type</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control " id="jx" name="jx" maxlength="50" disabled="disabled" />
						</div>
					</div>
					<form id="form">
						<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>编号</div>
								<div class="font-size-9">No.</div>
							</label>
							<div class="col-sm-8 col-xs-9 padding-leftright-8" >
								<input type="text" class="form-control " id="dlbh" name="dlbh" maxlength="50" />
								<input type="hidden" class="form-control " id="oldDlbh" name="oldDlbh" />
								<input type="hidden" class="form-control " id="dlid" name="dlid" />
							</div>
						</div>
						<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">项次</div>
									<div class="font-size-9">Item No.</div>
								</label>
								<div class="col-sm-8 col-xs-9 padding-leftright-8" >
									<input type="text" class="form-control " id="xc" name="xc"  />
								</div>
						</div>
					</form>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">英文描述</div>
							<div class="font-size-9">English Desc</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<input class="form-control" id="dlywms" name="dlywms" type="text" maxlength="100" />
						</div>
					</div>
					
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">中文描述</div>
							<div class="font-size-9">Chinese Desc</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<input class="form-control" id="dlzwms" name="dlzwms" type="text" maxlength="100" />
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
	                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="saveUpdate()">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
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