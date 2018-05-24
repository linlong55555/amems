<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessment/selectAssociatedEvaluation.js"></script>
<div class="modal fade in modal-new" id="open_win_evaluation_modal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="open_win_evaluation_modal" aria-hidden="true">
	<div class="modal-dialog" style="width: 60%;">
		<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-12 ">评估单列表</div>
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
			         		
			         		<div class=" pull-right padding-left-0 padding-right-8 margin-top-0 modalSearch">	
			         		
								<!-- 搜索框start -->
								<div class=" pull-right padding-left-0 padding-right-0">
									<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" placeholder='技术评估单编号' id="open_win_evaluation_modal_keyword_search" class="form-control" />
									</div>
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_evaluation_modal.search()">
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
								<table class="table table-bordered table-thin table-striped table-hover table-set" style="min-width: 1000px !important">
									<thead>
								   		<tr>
								   			<th style="width: 50px;font-weight:normal" id="checkSingle">
												<div class="font-size-12 line-height-18">操作</div>
												<div class="font-size-9 line-height-18">Operation</div>
											</th>
											<th  class="selectAllImg" id="checkAll" style='text-align:center;vertical-align:middle;width:60px;'>
												<a href="#" onclick="SelectUtil.performSelectAll('open_win_evaluation_modal_list')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
												<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('open_win_evaluation_modal_list')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
											</th>
											<th class="colwidth-10" style=' font-weight:normal'><div class="important"><div class="font-size-12 line-height-12" >技术评估单编号 </div><div class="font-size-9 line-height-12" >Evaluation No.</div></div></th>
											<th class="colwidth-3" style=' font-weight:normal'><div class="font-size-12 line-height-12" >版本 </div><div class="font-size-9 line-height-12" >Rev.</div></th>
											<th class="colwidth-5" style=' font-weight:normal'><div class="font-size-12 line-height-12" >类型 </div><div class="font-size-9 line-height-12" >Type</div></th>
											<th class="colwidth-10" style=' font-weight:normal'><div class="font-size-12 line-height-12" >适航性资料</div><div class="font-size-9 line-height-12" >Doc.</div></th>
											<th style=' font-weight:normal'><div class="font-size-12 line-height-12" >技术评估单主题</div><div class="font-size-9 line-height-12" >Subject</div></th>
<!-- 											<th class="colwidth-10" style=' font-weight:normal'><div class="font-size-12 line-height-12" >文件</div><div class="font-size-9 line-height-12" >File</div></th> -->
											<th class="colwidth-7" style=' font-weight:normal'><div class="font-size-12 line-height-12" >颁发日期</div><div class="font-size-9 line-height-12" >Issue Date</div></th>
											<th class="colwidth-7" style=' font-weight:normal'><div class="font-size-12 line-height-12" >生效日期</div><div class="font-size-9 line-height-12" >Effect Date</div></th>
											<th class="colwidth-7" style=' font-weight:normal'><div class="font-size-12 line-height-12" >到期日期</div><div class="font-size-9 line-height-12" >Due Date</div></th>
											<th class="colwidth-7" style=' font-weight:normal'><div class="font-size-12 line-height-12" >评估工程师</div><div class="font-size-9 line-height-12" >Engineer</div></th>
								 		 </tr>
									</thead>
									<tbody id="open_win_evaluation_modal_list">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="open_win_evaluation_modal_pagination"></div>
							
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
					                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_evaluation_modal.save();">
												<div class="font-size-12">确定</div>
												<div class="font-size-9">Confirm</div>
											</button>
											<button type="button" onclick="open_win_evaluation_modal.clearData()" id="userModal_btn_clear"
												class="btn btn-primary padding-top-1 padding-bottom-1">
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
