<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<!-- 航材主数据选择	 -->
<div class="modal fade" id="open_win_contract_cost_his" tabindex="-1" role="dialog" aria-labelledby="open_win_contract_cost_his" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:60%;">
		<div class="modal-content">	
			<div class="modal-body padding-bottom-0">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">航材历史采购价格</div>
						<div class="font-size-9 ">Historical purchase price of aircraft</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
						<!-- start:table -->
						<div class="margin-top-10 ">
							<div class="overflow-auto">
								<table class="table table-bordered table-striped table-hover" style="">
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
												<div class="font-size-12 notwrap">合同日期</div>
												<div class="font-size-9 notwrap">Contract date</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">合同号</div>
												<div class="font-size-9 notwrap">Contract No.</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">供应商名称</div>
												<div class="font-size-9 notwrap">Supplier</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">价格（人民币：元）</div>
												<div class="font-size-9 notwrap">Price(RMB:YUAN)</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="open_win_contract_cost_his_list">
									
									</tbody>
								</table>
							</div>
							<div class="col-xs-12 text-center">
								<ul class="pagination" style="margin-top: 0px; margin-bottom: 0px;" id="open_win_contract_cost_his_pagination">
								</ul>
							</div>
						</div>
						<!-- end:table -->
	                	<div class="text-right margin-top-10 margin-bottom-10">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_contract_cost_his.save();" >
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1"  onclick="open_win_contract_cost_his.close();">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
		                </div>
				 	 </div>
				 </div> 
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/contract_cost.js"></script>