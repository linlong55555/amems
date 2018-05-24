<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/scrap/scrap_choosestock.js"></script>
<!-- 库存选择	 -->
<div class="modal fade" id="open_win_scrap_stock" tabindex="-1" role="dialog" aria-labelledby="open_win_scrap_stock" aria-hidden="true">
	<div class="modal-dialog" style="width:85%;">
		<div class="modal-content">	
			<div class="modal-body padding-bottom-0">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">库存列表</div>
						<div class="font-size-9 ">Stock List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
			         		
			         		<div class=" pull-right padding-left-0 padding-right-0 margin-top-10">	
			         		
			         			
			         			<div class=" pull-left padding-left-5 padding-right-0" style="width: 300px;">
									<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">航材类型</div>
										<div class="font-size-9">Airmaterial type</div>
									</span>
									<div class=" col-xs-8 col-sm-8">
										<select class='form-control' id='open_win_scrap_stock_hclx_search' onchange="open_win_scrap_stock.search()">
											<option value="" >显示全部All</option>
									    </select>
									</div>
								</div>
					
								<!-- 搜索框start -->
								<div class=" pull-right padding-left-0 padding-right-0">
									<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" placeholder='件号/中英文/序列号/批次号/厂家件号' id="open_win_scrap_stock_keyword_search" class="form-control ">
										<input type="hidden" id="dprtId" value="${user.jgdm}" />
									</div>
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_scrap_stock.search()">
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
							<div class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0" style="overflow-x:auto;">
								<table id="open_win_scrap_stock_table" class="table table-bordered table-striped table-hover table-set" style="min-width:500px">
									<thead>
								   		<tr>
											<th style="width:50px">
												<div id="checkSingle">
													<div class="font-size-12 notwrap">选择</div>
													<div class="font-size-9 notwrap">Choice</div>
												</div>
												<div id="checkAll">
													<a href="#" onclick="open_win_scrap_stock.performSelectAll(true);" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
													<a href="#" class="margin-left-5" onclick="open_win_scrap_stock.performSelectAll(false);" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
												</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">件号</div>
												<div class="font-size-9 line-height-18">P/N</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">英文名称</div>
												<div class="font-size-9 line-height-18">F.Name</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">中文名称</div>
												<div class="font-size-9 line-height-18">CH.Name</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">航材价值</div>
												<div class="font-size-9 line-height-18">Value</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">库存成本(人民币:元)</div>
												<div class="font-size-9 line-height-18">Cost(RMB:YUAN)</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">仓库名称</div>
												<div class="font-size-9 line-height-18">Warehouse Name</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">库位</div>
												<div class="font-size-9 line-height-18">Storage Location</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">航材类型</div>
												<div class="font-size-9 line-height-18">Type</div>
											</th>	
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">管理级别</div>
												<div class="font-size-9 line-height-18">Level</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">序列号</div>
												<div class="font-size-9 line-height-18">S/N</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">批次号</div>
												<div class="font-size-9 line-height-18">B/N</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">厂家件号</div>
												<div class="font-size-9 line-height-18">MP/N</div>
											</th>																					
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">库存数量</div>
												<div class="font-size-9 line-height-18">Stock Num</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">可用数量</div>
												<div class="font-size-9 line-height-18">Available Num</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="open_win_scrap_stock_list">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="open_win_scrap_stock_pagination"></div>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_scrap_stock.save();" data-dismiss="modal">
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
