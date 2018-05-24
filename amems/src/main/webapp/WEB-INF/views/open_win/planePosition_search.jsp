<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/planePosition_search.js"></script>
<link rel="stylesheet" href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css" type="text/css">
<script type="text/javascript" src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<style type="text/css">
.bootstrap-tagsinput {
  width: 100% !important;
}
</style>
<div class="modal fade in modal-new" id="PositionModal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="open_win_evaluation_modal" aria-hidden="true">
	<div class="modal-dialog" style="width:50%;">
		<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
                          	<div class="font-size-12 " id="modalHeadCN">飞机站位列表</div>
							<div class="font-size-9" id="modalHeadENG">A/C Type Position List</div>
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
									<input type="text" placeholder='飞机站位/描述'  class="form-control" id="keyword_Position_search" >
				                    <div class="input-group-addon" style='text-align:right;padding:0px;padding-left:10px;'>
				                    	<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="PositionModal.search()" style='margin-right:0px !important;'>
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
											<th class="viewCol fixed-column colwidth-5 selectAllImg">
												<a href="#" onclick="PositionModal.performSelectAll()">
													<img src="${ctx }/static/assets/img/d1.png" alt="全选">
												</a>
												<a href="#" class="margin-left-5" onclick="PositionModal.performSelectClear()">
													<img src="${ctx }/static/assets/img/d2.png" alt="不全选">
												</a>
											</th>
											<th class="colwidth-15 ">
												<div class="important">
													<div class="font-size-12 line-height-18">飞机站位</div>
													<div class="font-size-9 line-height-18">A/C Type Position</div>
												</div>
											</th>
											<th class="colwidth-25 ">
												<div class="important">
													<div class="font-size-12 line-height-18">描述</div>
													<div class="font-size-9 line-height-18">Desc</div>
												</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="PositionList">
									
									</tbody>
								</table>
								</div>
								
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="PositionPagination"></div> 
								
								<div class="clearfix"></div> 
								
								 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
									<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12 line-height-18">已选站位</div>
										<div class="font-size-9 line-height-18">Check Postion</div>
									</label>
									<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
										<input class="form-control" id="um_selectPosition" readonly="readonly" />
									</div>
								</div>
							
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
					                  			<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="PositionModal.setData()" data-dismiss="modal">
													<div class="font-size-12">确定</div>
													<div class="font-size-9">Confirm</div>
												</button>
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
