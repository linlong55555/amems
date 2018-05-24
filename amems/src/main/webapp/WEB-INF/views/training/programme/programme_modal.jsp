<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<%-- <script type="text/javascript" src="${ctx}/static/js/thjw/open_win/customer_search.js"></script> --%>
<style type="text/css">
.bootstrap-tagsinput {
  width: 100% !important;
}
</style>
<!-- <div class="modal fade in modal-new" id="AddalertModal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="open_win_evaluation_modal" aria-hidden="true">
	<div class="modal-dialog" style="width:60%;">
		<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
		                      <div class="font-size-14 " id="gw_kc_chName">添加</div>
							  <div class="font-size-12" id="gw_kc_enName">Add</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                </div>
			  <div class="modal-body" style='padding-top:0px;'>
              	<div class="input-group-border" style='margin-top:8px;padding-top:5px;margin-bottom:8px;'>
			         		<div class="clearfix"></div>
						start:table
						<div class="margin-left-8 margin-right-8" style='margin-top:5px;'>
							<div class="table-content" style="overflow-x:auto;" id="searchTable">
								<table class="table table-bordered table-striped table-hover table-set table-thin" >
									<thead>
								   		<tr>
											<th class="fixed-column colwidth-3" style='text-align:center;vertical-align:middle'>
												<button class="line6" title="添加 Add"  onclick="addKc()">
													<i class="icon-plus color-blue cursor-pointer"></i>
												</button>
											</th>
											<th class=" colwidth-10">
												<div class="font-size-12 line-height-18">课程编号</div>
												<div class="font-size-9 line-height-18">Course No.</div>
											</th>
											<th class=" colwidth-10">
												<div class="font-size-12 line-height-18">教员要求</div>
												<div class="font-size-9 line-height-18">Faculty</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">培训机构</div>
												<div class="font-size-9 line-height-18">Training</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">来源</div>
												<div class="font-size-9 line-height-18">Source</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">教材/提纲</div>
												<div class="font-size-9 line-height-18">Outline</div>
											</th>
										</tr>
									</thead>
									<tbody id="courselist">
										
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div> 
							 <div class="clearfix"></div> 
							</div>
							</div>
							<div class="clearfix"></div>  
							</div>
							end:table
			                  <div class="modal-footer">
				           		<div class="col-xs-12 padding-leftright-8" >
									<div class="input-group">
					                    <span class="input-group-addon modalfooterbtn">
					                  			<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="save()" data-dismiss="modal">
													<div class="font-size-12">确定</div>
													<div class="font-size-9">Confirm</div>
												</button>
												<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
													<div class="font-size-12">关闭</div>
													<div class="font-size-9">Close</div>
												</button>
					                    </span>
					               	</div>/input-group
								</div>
				           
								<div class="clearfix"></div> 
								
							</div>
		</div>
	</div>
</div> -->


<div class="modal fade in modal-new" id="EditalertModal" tabindex="-1" role="dialog"  aria-labelledby="audit_item_list_alert_Modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:60%'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
		                      <div class="font-size-14 " id="gw_kc_chName">修改</div>
							  <div class="font-size-12" id="gw_kc_enName">Edit</div>
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
					<form id="form" >
					
					
							 <div class="col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">课程编号</div>
									<div class="font-size-9">Course No.</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8" id="kcbhDiv">
								</div>
								<input type="hidden" class="form-control " id="courseId" />
							</div>
							 <div class="col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">教员要求</div>
									<div class="font-size-9">Faculty</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control " id="jyyq" name="jyyq" maxlength="65" />
								</div>
							</div>
							 <div class="col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">培训机构</div>
									<div class="font-size-9">Training</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control " id="pxjg" name="pxjg" maxlength="100" />
								</div>
							</div>
							 <div class="col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">来源</div>
									<div class="font-size-9">Source</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control " id="ly" name="ly" maxlength="15" />
								</div>
							</div>
							 <div class="col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">教材/提纲</div>
									<div class="font-size-9">Outline</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control " id="jctg" name="jctg" maxlength="100" />
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
					</span>
	                    <span class="input-group-addon modalfooterbtn">
	                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="modify()"  data-dismiss="modal">
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



<div class="modal fade in modal-new" id="ViewAlertModal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="open_win_evaluation_modal" aria-hidden="true">
	<div class="modal-dialog" style="width:70%;">
		<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
                          	<div class="font-size-12 ">查看</div>
							<div class="font-size-9">View</div>
						  </div>
						   <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
			  <div class="modal-body" style='padding-top:0px;'>
              	<div class="input-group-border" style='margin-top:8px;padding-top:5px;margin-bottom:8px;'>
                        <div class="col-sm-6 col-xs-12 pull-right  padding-leftright-8 margin-top-0 modalSearch">	
								 <div class="col-lg-12 col-md-12 col-xs-12 input-group input-group-search">
									<input type="text" placeholder='类别/代码/名称/形式/初训学时/复训学时/复训间隔/内容/目标/备注'  class="form-control" id="keyword_Customer_search" >
				                    <div class="input-group-addon" style='text-align:right;padding:0px;padding-left:10px;'>
				                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision()" style='margin-right:0px !important;'>
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
				                  		</button>
				                    </div>
								</div>
							</div>
						
			         		<div class="clearfix"></div>
			         		
						<!-- start:table -->
						<div class="margin-left-8 margin-right-8" style='margin-top:5px;'>
							<div class="table-content" style="overflow-x:auto;" id="searchTable">
								<table class="table table-bordered table-striped table-hover table-set table-thin" >
									<thead>
								   		<tr>
												<th class="colwidth-10 sorting" onclick="orderBy('kclb')" id="kclb_order">
													<div class="important">
														<div class="font-size-12 line-height-18">课程类别</div>
														<div class="font-size-9 line-height-18">Course Type</div>
													</div>
												</th>
												<th class="colwidth-10 sorting" onclick="orderBy('kcbh')" id="kcbh_order">
													<div class="important">
														<div class="font-size-12 line-height-18">课程代码</div>
														<div class="font-size-9 line-height-18">Course Code</div>
													</div>
												</th>
												<th class="colwidth-10 sorting" onclick="orderBy('kcmc')" id="kcmc_order">
													<div class="important">
														<div class="font-size-12 line-height-18">课程名称</div>
														<div class="font-size-9 line-height-18">Course Name</div>
													</div>
												</th>
												<th class="colwidth-10 sorting" onclick="orderBy('fjjx')" id="fjjx_order">
													<div class="font-size-12 line-height-18">机型</div>
													<div class="font-size-9 line-height-18">A/C Type</div>
												</th>
												<th class="colwidth-30 sorting" onclick="orderBy('pxmb')" id="pxmb_order">
													<div class="important">
														<div class="font-size-12 line-height-18">培训目标</div>
														<div class="font-size-9 line-height-18">Training Objective</div>
													</div>
												</th>
												<th class="colwidth-7 sorting" onclick="orderBy('ks')" id="ks_order">
													<div class="font-size-12 line-height-18">初训学时</div>
													<div class="font-size-9 line-height-18">Hour</div>
												</th>
												<th class="colwidth-10 sorting" onclick="orderBy('pxxs')" id="pxxs_order">
													<div class="important">
														<div class="font-size-12 line-height-18">初训培训形式</div>
														<div class="font-size-9 line-height-18">Form</div>
													</div>
												</th>
												<th class="colwidth-10 sorting" onclick="orderBy('ksxs')" id="ksxs_order">
													<div class="important">
														<div class="font-size-12 line-height-18">初训考试形式</div>
														<div class="font-size-9 line-height-18">Form</div>
													</div>
												</th>
												<th class="colwidth-9 sorting" onclick="orderBy('IS_FX')" id="IS_FX_order">
													<div class="font-size-12 line-height-18">是/否复训</div>
													<div class="font-size-9 line-height-18">Whether</div>
												</th>
												<th class="colwidth-10" >
													<div class="font-size-12 line-height-18">复训间隔</div>
													<div class="font-size-9 line-height-18">Interval</div>
												</th>
												<th class="colwidth-7 sorting" onclick="orderBy('fxks')" id="fxks_order">
													<div class="font-size-12 line-height-18">复训学时</div>
													<div class="font-size-9 line-height-18">Hour</div>
												</th>
												<th class="colwidth-10 sorting" onclick="orderBy('fxpxxs')" id="fxpxxs_order">
													<div class="important">
														<div class="font-size-12 line-height-18">复训培训形式</div>
														<div class="font-size-9 line-height-18">Form</div>
													</div>
												</th>
												<th class="colwidth-10 sorting" onclick="orderBy('fxksxs')" id="fxksxs_order">
													<div class="important">
														<div class="font-size-12 line-height-18">复训考试形式</div>
														<div class="font-size-9 line-height-18">Form</div>
													</div>
												</th>
												<th class="colwidth-30 sorting" onclick="orderBy('bz')" id="bz_order">
													<div class="important">
														<div class="font-size-12 line-height-18">备注</div>
														<div class="font-size-9 line-height-18">Remark</div>
													</div>
												</th>
												<th class="colwidth-15 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
													<div class="font-size-12 line-height-18">组织机构</div>
													<div class="font-size-9 line-height-18">Organization</div>
												</th>
											</tr>
									</thead>
									<tbody id="course_list">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div> 
							 <div class="clearfix"></div> 
							</div>
							</div>
							<div class="clearfix"></div>  
							</div>
							<!-- end:table -->
							<div class="modal-footer">
				           		<div class="col-xs-12 padding-leftright-8" >
									<div class="input-group">
					                    <span class="input-group-addon modalfooterbtn">
												<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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









