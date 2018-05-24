<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<div class="modal fade" id="open_win_account_select_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">账号列表</div>
						<div class="font-size-9 ">Account List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12">
			         		
			         		<!-- 搜索框start -->
							<div class=" pull-right padding-left-0 padding-right-10 margin-top-10">
							
							
								<div class=" pull-left padding-left-10 padding-right-0" style="width: 250px;">
									<input type="text" placeholder='账号' id="open_win_account_select_username_search" class="form-control ">
								</div>
				                <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button id="loginAccountSelectSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_account_select.search()">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                  		</button>
				                </div> 
							</div>
							<!-- 搜索框end -->
			         		
			         		<div class="clearfix"></div>	
			         		
							<!-- start:table -->
							<div class="margin-top-10 ">
							<div class="overflow-auto">
								<table class="table table-bordered table-striped table-hover table-set text-center" style="">
									<thead>
								   		<tr>
											<th width="50px">
												<div class="font-size-12 notwrap">选择</div>
												<div class="font-size-9 notwrap">Choice</div>
											</th>
											<th width="50px">
												<div class="font-size-12 notwrap">序号</div>
												<div class="font-size-9 notwrap">No.</div>
											</th>
											<th>
												<div class="important">
													<div class="font-size-12 notwrap">账号</div>
													<div class="font-size-9 notwrap">Account</div>
												</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">组织机构</div>
												<div class="font-size-9 notwrap">Organization</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="open_win_account_select_list">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="open_win_account_select_pagination"></div>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" onclick="open_win_account_select.save()"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									data-dismiss="modal">
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
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/account_select.js"></script>
<script type="text/javascript">
	//回车事件控制
	document.onkeydown = function(event) {
		e = event ? event : (window.event ? window.event : null);
		if (e.keyCode == 13) {
			$('#loginAccountSelectSearch').trigger('click');
		}
	};
</script>