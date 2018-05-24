<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<div class="modal fade in modal-new" id="hc_list_modal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="open_win_evaluation_modal" aria-hidden="true">
	<div class="modal-dialog" style="width: 60%;">
		<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-12 ">航材工具需求清单列表</div>
							<div class="font-size-9">Evaluation List</div>
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
						<div class="margin-left-8 margin-right-8" style='margin-top:5px;'>
							<div style="overflow-x:auto;" class='table-content'>
									<table class="table table-bordered table-striped table-hover  text-left table-set" style="min-width: 950px;">
											<thead>
										   		<tr>
										   			<th class="colwidth-5">
														<div class="font-size-12 ">类型</div>
														<div class="font-size-9 ">Type</div>
													</th>
													<th class="colwidth-13">
														<div class="font-size-12 ">件号</div>
														<div class="font-size-9 ">P/N</div>
													</th>
													<th class="colwidth-13">
														<div class="font-size-12 ">型号</div>
														<div class="font-size-9 ">Model</div>
													</th>
													<th class="colwidth-15">
														<div class="font-size-12 ">名称</div>
														<div class="font-size-9 ">Name</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 ">需求数量</div>
														<div class="font-size-9 ">Demand</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 ">单位</div>
														<div class="font-size-9 ">Company</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 ">库存数</div>
														<div class="font-size-9 ">Inventory</div>
													</th>
													<th class="colwidth-13">
														<div class="font-size-12 ">库存可用数</div>
														<div class="font-size-9 ">Inventory Availability</div>
													</th>
													<th class="colwidth-15">
														<div class="font-size-12 ">互换信息</div>
														<div class="font-size-9 ">Swap Info</div>
													</th>
													<th class="colwidth-15">
														<div class="font-size-12 ">件号来源</div>
														<div class="font-size-9 ">P/N Source</div>
													</th>
													<th class="colwidth-15">
														<div class="font-size-12 ">说明</div>
														<div class="font-size-9 ">Desc</div>
													</th>
										 		 </tr>
											</thead>
											<tbody id="hc_List_view_tbody">
											<tr class='text-center'><td colspan='11'>暂无数据 No data.</td></tr>
											</tbody>
										</table>
									</div>
								</div>
						     	<div class="clearfix"></div>						
							</div>
						<div class="clearfix"></div>						
					</div>
					<div class="clearfix"></div>
					<!-- end:table -->
                	<div class="modal-footer">
			           	<div class="col-xs-12 padding-leftright-8"  >
			           		<div class="input-group">
								<span class="input-group-addon modalfootertip" >
					                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
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