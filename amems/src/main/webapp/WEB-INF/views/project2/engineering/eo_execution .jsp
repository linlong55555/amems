<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in" id="EOexecutionModal" tabindex="-1" role="dialog"  aria-labelledby="EOexecutionModal" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" >
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-14 ">EO执行对象监控关闭</div>
							<div class="font-size-12">EO execution close</div> 
						  </div>
						  <div class='pull-right' style='padding-top:10px;'>
						  	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" style='font-size:30px !important;' >&times;</span></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
              	<div class="col-xs-12">
                    <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">指定结束人</div>
								<div class="font-size-9">End of appointment</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<input class="form-control"  maxlength="100">
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">指定结束时间</div>
								<div class="font-size-9">Designated end time</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<input class="form-control date-picker"  maxlength="100" id='zdjssj'>
							</div>
						</div>
						 <div class="col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">指定结束原因</div>
								<div class="font-size-9">End cause</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<textarea class='form-control' rows='2'>
								</textarea>
							</div>
						</div>
								 
				</div>
                <div class="clearfix"></div>              
           </div>
           <div class="modal-footer">
              <div class="col-xs-8 alert-style">
		            
		             <i class='glyphicon glyphicon-info-sign alert-info'></i>警告！请不要提交。
		        </div>
		        <div class='col-xs-4'>
			   <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
						<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
				</button>
				<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
						<div class="font-size-12">关闭</div>
						<div class="font-size-9">Close</div>
				</button>
				</div>
				<div class="clearfix"></div> 
				
			</div>
            </div>
          </div>
	</div>
<!--  弹出框结束-->