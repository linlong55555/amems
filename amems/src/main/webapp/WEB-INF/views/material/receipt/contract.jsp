<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script src="${ctx}/static/js/thjw/material/receipt/contract.js"></script>
<div class="modal fade" id="contractModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">合同列表</div>
						<div class="font-size-9 ">Contract List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
			         		
			         		<!-- 搜索框start -->
							<div class=" pull-right padding-left-0 padding-right-0 margin-top-10">
								<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
									<input type="text" placeholder='合同号/合同编号/供应商编号/供应商名称' id="contract_keyword_search" class="form-control ">
								</div>
				                <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="contractModal.search()">
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
								<table class="table table-bordered table-striped table-hover text-center" style="">
									<thead>
								   		<tr>
											<th width="50px">
												<div class="font-size-12 notwrap">选择</div>
												<div class="font-size-9 notwrap">Choice</div>
											</th>
											<th>
												<div class="important">
													<div class="font-size-12 notwrap">合同号</div>
													<div class="font-size-9 notwrap">Contract No.</div>
												</div>
											</th>
											<th>
												<div class="important">
													<div class="font-size-12 notwrap">合同编号</div>
													<div class="font-size-9 notwrap">Order No.</div>
												</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">紧急程度</div>
												<div class="font-size-9 notwrap">Emergency</div>
											</th>
											<th>
												<div class="important">
													<div class="font-size-12 notwrap">供应商编号</div>
													<div class="font-size-9 notwrap">Supplier No.</div>
												</div>
											</th>
											<th>
												<div class="important">
													<div class="font-size-12 notwrap">供应商名称</div>
													<div class="font-size-9 notwrap">Supplier name</div>
												</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">合同签订日期</div>
												<div class="font-size-9 notwrap">Date of signing</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">合同到货状态</div>
												<div class="font-size-9 notwrap">Arrival state</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="contractlist">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="contract_pagination"></div>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" onclick="contractModal.setContract()"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									data-dismiss="modal">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" onclick="contractModal.clearUser()" id="contractModal_btn_clear"
										class="btn btn-primary padding-top-1 padding-bottom-1">
										<div class="font-size-12">清空</div>
										<div class="font-size-9">Clear</div>
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
