<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in" id="CloseModal" tabindex="-1" role="dialog"  aria-labelledby="EOCloseModal" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:40%;'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-14 ">关闭</div>
							<div class="font-size-12">Close</div> 
						  </div>
						  <div class='pull-right' style='padding-top:10px;'>
						  	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" style='font-size:30px !important;' >&times;</span></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
        		            	<div class="col-xs-12 margin-top-0 padding-left-10 padding-right-8">
       												<!--评估单号数据  -->
    							<div class="input-group-border margin-top-8 padding-left-0">
								
	           <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">关闭人</div>
								<div class="font-size-9">Close the person</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<input class="form-control"  maxlength="100">
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">关闭时间</div>
								<div class="font-size-9">Closing time</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<input class="form-control date-picker"  maxlength="100" id='gbsj'>
							</div>
						</div>
						 <div class="col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">关闭原因</div>
								<div class="font-size-9">Close the reason</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<textarea class='form-control' style="height:75px">
								</textarea>
							</div>
						</div>
				
									<div class='clearfix'></div>
									<div class='table_pagination padding-leftright-8'>
									</div>
								</div>
								<!--评估单号数据  -->
				
	           </div>
            
                <div class="clearfix"></div>              
           </div>
                             <div class="modal-footer">
		           	<div class="col-xs-12 padding-leftright-8" >
						<div class="input-group">
						<span class="input-group-addon modalfootertip" >
			                   <i class='glyphicon glyphicon-info-sign alert-info'></i>警告！请不要提交。
						</span>
		                    <span class="input-group-addon modalfooterbtn">
							   <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">提交</div>
									<div class="font-size-9">Submit</div>
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