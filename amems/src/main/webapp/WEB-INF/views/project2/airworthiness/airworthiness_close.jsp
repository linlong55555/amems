<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in" id="EOCloseModal" tabindex="-1" role="dialog"  aria-labelledby="EOCloseModal" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style="width:550px!important;" >
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-14 ">关闭</div>
							<div class="font-size-12">close</div> 
						  </div>
						  <div class='pull-right' style='padding-top:10px;'>
						  	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" style='font-size:30px !important;' >&times;</span></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
              	<div class="col-xs-12 margin-top-0 ">
              	<div class="input-group-border">
                    <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">关闭人</div>
								<div class="font-size-9">Operator</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<input class="form-control"  maxlength="100">
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">关闭时间</div>
								<div class="font-size-9">Time</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<input class="form-control date-picker" data-date-format="yyyy-mm-dd"  maxlength="10" id='gbsj'>
							</div>
						</div>
						 <div class="col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color:red">*</span>关闭原因</div>
								<div class="font-size-9">Reason</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="zhut" name="zhut" placeholder=''   maxlength="300" style="height:75px"></textarea>
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
	                   <i class='glyphicon glyphicon-info-sign alert-info'></i>
	                        <p class="alert-info-message">警告！请不要提交。</p>   
				</span>
                    <span class="input-group-addon modalfooterbtn">
                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
	                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
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