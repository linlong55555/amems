<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script src="${ctx}/static/js/thjw/material/receipt/lendReturn.js"></script>
<div class="modal fade" id="lendReturnModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">未归还统计数据列表</div>
						<div class="font-size-9 ">LendingReturn List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12 margin-top-10 padding-left-0 padding-right-0">
		            		<div class=" pull-right padding-left-0 padding-right-0">
		            		
								<div class="pull-left padding-left-0 padding-right-0" style="width: 250px;">
									<input type="text" placeholder='件号/中英文' id="lendReturn_keyword_search" class="form-control ">
								</div>
				                <div class="pull-right padding-left-5 padding-right-0 ">   
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="lendReturnModal.search()">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                  		</button>
				                </div> 
		            		</div>
			         		
			         		<div class="clearfix"></div>	
			         		
							<!-- start:table -->
							<div class="margin-top-10 ">
							<div class="overflow-auto">
								<table class="table table-bordered table-striped table-hover text-center" style="">
									<thead>
								   		<tr>
											<th width="50px">
												<div class="font-size-12 notwrap">选择</div>
												<div class="font-size-9 notwrap">Choice</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">借调对象</div>
												<div class="font-size-9 notwrap">Seconded obj</div>
											</th>
											<th>
												<div class="important">
													<div class="font-size-12 notwrap">件号</div>
													<div class="font-size-9 notwrap">P/N</div>
												</div>
											</th>
											<th>
												<div class="important">
													<div class="font-size-12 notwrap">英文名称</div>
													<div class="font-size-9 notwrap">F.Name</div>
												</div>
											</th>
											<th>
												<div class="important">
													<div class="font-size-12 notwrap">中文名称</div>
													<div class="font-size-9 notwrap">CH.Name</div>
												</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">厂家件号</div>
												<div class="font-size-9 notwrap">MP/N</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">航材类型</div>
												<div class="font-size-9 notwrap">Airmaterial type</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">管理级别</div>
												<div class="font-size-9 notwrap">Management level</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">待还数量</div>
												<div class="font-size-9 notwrap">To be return</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">单位</div>
												<div class="font-size-9 notwrap">Unit</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="lendReturnList">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="lendReturn_pagination"></div>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" onclick="lendReturnModal.setContract()"
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
