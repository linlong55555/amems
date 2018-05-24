<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/work_material_view.js"></script>
<div class="modal fade" id="Work_Material_View_Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 65%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="Work_Material_View_UserBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">替代部件</div>
						<div class="font-size-9 ">Substitution Material</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12">
		            	
		            		<div class=" pull-right padding-left-0 padding-right-0 margin-top-10">	
					
								<%-- <div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
									<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">航材类型</div>
										<div class="font-size-9">Airmaterial type</div>
									</span>
									<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
										<select class='form-control' id='hclx_search' onchange="MaterialReserveModal.changeType();">
											<option value="" >显示全部All</option>
											<c:forEach items="${materialTypeEnum}" var="item">
												<option value="${item.id}" <c:if test="${100 == item.id }">selected=true</c:if> >${item.name}</option>
											</c:forEach>
									    </select>
									</div>
								</div>	 --%>
								
								
								<!-- 搜索框start -->
								<%-- <div class=" pull-right padding-left-5 padding-right-0">
									<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" placeholder='件号/中英文/厂家件号' id="keyword_material_reserve_search" class="form-control ">
										<input type="hidden" id="dprtId" value="${user.jgdm}" />
									</div>
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="AlertModalWinAddModal.search()">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div> 
								</div> --%>
								<!-- 搜索框end -->
							</div>
		            	
			         		<div class="clearfix"></div>
							<!-- start:table -->
							<div class="margin-top-10 overflow-auto">
								<table class="table table-bordered table-striped table-hover text-center table-set" style="min-width: 500px;">
									<thead>
										<tr>
											<th class='colwidth-5' >
												<div class="font-size-12 line-height-18">序号</div>
												<div class="font-size-9 line-height-18">No.</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">英文名称</div>
												<div class="font-size-9 line-height-16">F.Name</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">中文名称</div>
												<div class="font-size-9 line-height-16">CH.Name</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">部件号</div>
												<div class="font-size-9 line-height-16">P/N</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18">在库数量</div>
												<div class="font-size-9 line-height-16">Stock Quantity</div>
											</th>
											<th class="colwidth-5">
												<div class="font-size-12 line-height-18">计量单位</div>
												<div class="font-size-9 line-height-16">Unit</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">航材类型</div>
												<div class="font-size-9 line-height-16">Airmaterial type</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="Work_Material_View_List">
									</tbody>
								</table>
							</div>
							<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="Work_Material_View_Pagination"></div>
							<!-- end:table -->
		                	<div class="text-right margin-bottom-10">
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
