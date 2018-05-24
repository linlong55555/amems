<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/produce/project_modal.js"></script>
<div class="modal fade in modal-new" id="project_modal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="project_modal" aria-hidden="true">
	<div class="modal-dialog" style="width: 60%;">
		<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-12 ">项目列表</div>
							<div class="font-size-9">Project List</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
			<div class="modal-body" style='padding-top:0px;'>
              
              	<div class="input-group-border" style='margin-top:8px;padding-top:5px;'>
			         		
			         		<div class=" pull-right padding-left-0 padding-right-8 margin-top-0 modalSearch">	
			         		
								<!-- 搜索框start -->
								<div class=" pull-right padding-left-0 padding-right-0">
									<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" placeholder='项目编号/项目名称' id="project_modal_keyword_search" class="form-control" />
									</div>
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="project_modal.search()">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div> 
								</div>
								<!-- 搜索框end -->
							</div>
		            	
			         		<div class="clearfix"></div>
			         		
						<!-- start:table -->
						<div class="margin-left-8 margin-right-8" style='margin-top:5px;'>
							<div style="overflow-x:auto;" class='table-content'>
								<table class="table table-bordered table-thin table-striped table-hover table-set" style="min-width: 500px !important">
									<thead>
								   		<tr>
											<th class="colwidth-5 ">
												<div class="font-size-12 line-height-18">选择</div>
												<div class="font-size-9 line-height-18">Choose</div>
											</th>
											<th class="colwidth-10 ">
												<div class="important">
													<div class="font-size-12 line-height-18">项目编码</div>
													<div class="font-size-9 line-height-18">Project No.</div>
												</div>
											</th>
											<th class="colwidth-10 ">
												<div class="important">
													<div class="font-size-12 line-height-18">项目名称</div>
													<div class="font-size-9 line-height-18">Project Name</div>
												</div>
											</th>
											 
								 		 </tr>
									</thead>
									<tbody id="project_modal_list">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="project_modal_pagination"></div>
							
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
							                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
										</span>
										<span class="input-group-addon modalfooterbtn">
					                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="project_modal.setData();">
												<div class="font-size-12">确定</div>
												<div class="font-size-9">Confirm</div>
											</button>
					                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" id="project_modal_clearbtn" onclick="project_modal.clearData();">
												<div class="font-size-12">清空</div>
												<div class="font-size-9">Clear</div>
											</button>
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
