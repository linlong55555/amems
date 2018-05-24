<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<div class="modal modal-new " id="viewMarkupStatus_modal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="viewMarkupStatus_modal" aria-hidden="true">
	<div class="modal-dialog" style="width: 40%;">
		<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-12 line-height-18">查看圈阅情况</div>
							<div class="font-size-9 ">View Markup Status</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
			<div class="modal-body" style='padding-top:0px;'>
              
              	<div class="input-group-border" style='margin-top:8px;padding-top:5px;'>
			         	<div class="clearfix"></div>
			         		
						<!-- start:table -->
						<div class="margin-left-8 margin-right-8" style='margin-top:5px;'>
							<div style="overflow-x:auto;">
								<table class="table table-bordered table-thin table-striped table-hover table-set">
									<thead>
								   		<tr>
											<th><div class="font-size-12 line-height-18" >接收部门</div><div class="font-size-9 line-height-18" >Department</div></th>
											<th><div class="font-size-12 line-height-18" >状态</div><div class="font-size-9 line-height-18" >State</div></th>
											<th><div class="font-size-12 line-height-18" >接收时间</div><div class="font-size-9 line-height-18" >Receive Time</div></th>
								 		 </tr>
									</thead>
									<tbody id="viewMarkupStatus_List">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination1"></div>
							
							</div>
							
							</div>
							
							<div class="clearfix"></div>  
							</div>
							<div class="clearfix"></div>  
							<!-- end:table -->
		                	<div class="modal-footer">
					           	<div class="col-xs-12 padding-leftright-8"  >
					           		<div class="input-group">
										<span class="input-group-addon modalfootertip" >
							                
										</span>
										<span class="input-group-addon modalfooterbtn">					              
						                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
												<div class="font-size-12">关闭</div>
												<div class="font-size-9">Close</div>
										    </button>
					                    </span>
					               	</div>
								</div>
							</div>
		</div>
	</div>
</div>
