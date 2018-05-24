<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/materialRepair.js"></script>
<div class="modal fade" id="MaterialRepairModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 60%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">航材列表</div>
						<div class="font-size-9 ">Material List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
						    
			         		<div class="text-right margin-top-10">
			         			<div style="padding-left:0!important;" class="pull-right">   
									<button name="keyCodeSearch"
									type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="MaterialRepairModal.search()">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                    </div> 
								<div style="padding-right:9px!important;" class="col-xs-4 pull-right">
									<input type="text" placeholder='件号/中英文/厂家件号/序列号' id="keyword_material_repair_search" class="form-control ">
								</div>
							</div>
			         		
			         		<div class="clearfix"></div>
							<!-- start:table -->
							<div class="margin-top-10 ">
							<div style="overflow-x: auto;">
								<table class="table table-bordered table-striped table-hover text-center table-set" style="min-width: 1360px;">
									<thead>
								   		<tr>
											<th class="colwidth-5">
												<div class="font-size-12 notwrap">选择</div>
												<div class="font-size-9 notwrap">Choice</div>
											</th>
											<th class="colwidth-15">
												<div class="important">
													<div class="font-size-12 notwrap">件号</div>
													<div class="font-size-9 notwrap">P/N</div>
												</div>
											</th>
											<th class="colwidth-25">
												<div class="important">
													<div class="font-size-12 notwrap">英文名称</div>
													<div class="font-size-9 notwrap">F.Name</div>
												</div>
											</th>
											<th class="colwidth-20">
												<div class="important">
													<div class="font-size-12 notwrap">中文名称</div>
													<div class="font-size-9 notwrap">CH.Name</div>
												</div>
											</th>
											<th class="colwidth-15">
												<div class="important">
													<div class="font-size-12 notwrap">厂家件号</div>
													<div class="font-size-9 notwrap">MP/N</div>
												</div>
											</th>
											<th class="colwidth-20">
												<div class="important">
													<div class="font-size-12 notwrap">序列号</div>
													<div class="font-size-9 notwrap">S/N</div>
												</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">ATA章节号</div>
												<div class="font-size-9 notwrap">ATA</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">航材类型</div>
												<div class="font-size-9 notwrap">Airmaterial type</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 notwrap">单位</div>
												<div class="font-size-9 notwrap">Unit</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="materialRepairList">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="materialRepairPagination"></div>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" onclick="MaterialRepairModal.setData()"
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
