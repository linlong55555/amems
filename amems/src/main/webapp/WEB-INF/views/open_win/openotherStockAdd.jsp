<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/openotherStockAdd.js"></script>

<div class="modal fade" id="OpenotherStockModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 60%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">航材列表</div>
						<div class="font-size-9 ">Material List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12">
		            	
		            		<div class=" pull-right padding-left-0 padding-right-0 margin-top-10">	
					
								<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
									<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">航材类型</div>
										<div class="font-size-9">Aircraft Type</div>
									</span>
									<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
										<select class='form-control' id='hclx_search' >
											<option value="" ></option>
											<c:forEach items="${materialTypeEnum}" var="item">
												<option value="${item.id}" <c:if test="${100 == item.id }">selected=true</c:if> >${item.name}</option>
											</c:forEach>
									    </select>
									</div>
								</div>	
								
								
								<!-- 搜索框start -->
								<div class=" pull-right padding-left-0 padding-right-0">
									<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" placeholder='件号/中英文/厂家件号' id="keyword_material_reserve_search" class="form-control ">
										<input type="hidden" id="dprtId" value="${user.jgdm}" />
									</div>
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button name="keyCodeSearch"
										type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="MaterialReserveModal.search()">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div> 
								</div>
								<!-- 搜索框end -->
							</div>
		            	
			         		<div class="clearfix"></div>
							<!-- start:table -->
							<div class="margin-top-10 overflow-auto">
								<table class="table table-bordered table-striped table-hover text-center">
									<thead>
										<tr>
											<th style='text-align:center;vertical-align:middle;width:4%'>
												<a href="#" onclick="SelectUtil.performSelectAll('otherStockAdd')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
												<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('otherStockAdd')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
												<!-- <input id="selectAllId" type='checkbox' onclick="SelectUtil.selectAll('selectAllId','otherStockAdd')" /> -->
											</th>
											<th style="width:5%">
												<div class="important">
													<div class="font-size-12 notwrap">件号</div>
													<div class="font-size-9 notwrap">P/N</div>
												</div>
											</th>
											<th style="width:5%">
												<div class="important">
													<div class="font-size-12 notwrap">英文名称</div>
													<div class="font-size-9 notwrap">F.Name</div>
												</div>
											</th>
											<th style="width:5%">
												<div class="important">
													<div class="font-size-12 notwrap">中文名称</div>
													<div class="font-size-9 notwrap">CH.Name</div>
												</div>
											</th>
											<th style="width:5%">
												<div class="important">
													<div class="font-size-12 notwrap">厂家件号</div>
													<div class="font-size-9 notwrap">MP/N</div>
												</div>
											</th>
											<th style="width:5%">
												<div class="font-size-12 notwrap">适用机型</div>
												<div class="font-size-9 notwrap">Model</div>
											</th>
											<th style="width:9%">
												<div class="font-size-12 notwrap">ATA章节号</div>
												<div class="font-size-9 notwrap">ATA</div>
											</th>
											<th style="width:3%">
												<div class="font-size-12 notwrap">航材类型</div>
												<div class="font-size-9 notwrap">Aircraft Type</div>
											</th>
											<th style="width:3%">
												<div class="font-size-12 notwrap">库存</div>
												<div class="font-size-9 notwrap">Num</div>
											</th>
											<th style="width:3%">
												<div class="font-size-12 notwrap">单位</div>
												<div class="font-size-9 notwrap">Unit</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="otherStockAdd">
									</tbody>
								</table>
							</div>
							<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="otherStockAddPagination"></div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" onclick="MaterialReserveModal.setData()"
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
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
