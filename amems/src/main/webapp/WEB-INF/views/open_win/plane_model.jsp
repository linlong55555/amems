<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/plane_model.js"></script>
<!-- 机型选择 -->
<div class="modal fade" id="open_win_plane_model" tabindex="-1" role="dialog" aria-labelledby="open_win_plane_model" aria-hidden="true">
	<div class="modal-dialog" style="width:60%;">
		<div class="modal-content">	
			<div class="modal-body padding-bottom-0">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">机型列表</div>
						<div class="font-size-9 ">Model List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12 padding-right-0 padding-left-0">
			         		
			         		<div class=" pull-right padding-left-0 padding-right-0 margin-top-10">	
					
								<!-- 搜索框start -->
								<div class=" pull-right padding-left-0 padding-right-0">
									<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" placeholder='飞机机型' id="open_win_plane_model_keyword_search" class="form-control ">
									</div>
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_plane_model.search()">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div> 
								</div>
								<!-- 搜索框end -->
							</div>
		            	
			         		<div class="clearfix"></div>
			         		
						<!-- start:table -->
						<div class="margin-top-10 ">
							<div style="overflow-x:auto;">
								<table class="table table-bordered table-striped table-hover table-set" style="min-width: 1450px !important">
									<thead>
								   		<tr>
											<th class="colwidth-5" id="checkSingle" >
												<div class="font-size-12 line-height-18"">操作</div>
												<div class="font-size-9 line-height-18">Operation</div>
											</th>
											<th class="colwidth-5" id="checkAll" style='text-align:center;vertical-align:middle;'>
												<a href="#" onclick="SelectUtil.performSelectAll('open_win_plane_model_list')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
												<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('open_win_plane_model_list')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
											</th>
											<th class="colwidth-10">
											  <div class="important">
												<div class="font-size-12 line-height-18">飞机机型</div>
												<div class="font-size-9 line-height-18">Model</div>
											  </div>	
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">机身飞行时间</div>
												<div class="font-size-9 line-height-18">Flight HRS.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">搜索灯时间</div>
												<div class="font-size-9 line-height-18">SearchLight Time</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">绞车时间</div>
												<div class="font-size-9 line-height-18">Winch Time</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">起落循环</div>
												<div class="font-size-9 line-height-18">Flight CYCS.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">绞车循环</div>
												<div class="font-size-9 line-height-18">Winch CYCS.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">外吊挂循环</div>
												<div class="font-size-9 line-height-18">E/S CYCS.</div>
											</th>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">N1</div>
												<div class="font-size-9 line-height-18">N1</div>
											</th>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">N2</div>
												<div class="font-size-9 line-height-18">N2</div>
											</th>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">N3</div>
												<div class="font-size-9 line-height-18">N3</div>
											</th>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">N4</div>
												<div class="font-size-9 line-height-18">N4</div>
											</th>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">N5</div>
												<div class="font-size-9 line-height-18">N5</div>
											</th>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">N6</div>
												<div class="font-size-9 line-height-18">N6</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">特殊监控1</div>
												<div class="font-size-9 line-height-18">TS1</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">特殊监控2</div>
												<div class="font-size-9 line-height-18">TS2</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">定检计划公式</div>
												<div class="font-size-9 line-height-18">Calculation Methods</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">特殊情况</div>
												<div class="font-size-9 line-height-18">Special Circumstances</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="open_win_plane_model_list">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="open_win_plane_model_pagination"></div>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_plane_model.save();" data-dismiss="modal">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
			                </div>
				     		<div class="clearfix"></div>
					 	 </div>
					 </div> 
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div>
