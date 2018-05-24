<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/material_basic.js"></script>
<!-- 航材主数据选择	 -->
<div class="modal fade in modal-new" id="open_win_material_basic" tabindex="-1" role="dialog" aria-labelledby="open_win_material_basic" aria-hidden="true">
	<div class="modal-dialog" style="width:85%;">
		<div class="modal-content">	
		   <div class="modal-header modal-header-new" >
	           <h4 class="modal-title" >
                  <div class='pull-left'>
                    <div class="font-size-12">航材列表</div>
					<div class="font-size-9 ">Material List</div>
				  </div>
				  <div class='pull-right' >
				  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				  </div>
				  <div class='clearfix'></div>
	            </h4>
            </div>
			<div class="modal-body padding-bottom-0" style='margin-top:0px;'>
				<!-- <div class="panel panel-primary"> -->
					<!-- <div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">航材列表</div>
						<div class="font-size-9 ">Material List</div>
					</div> -->
					<!-- <div class="panel-body padding-top-0 padding-bottom-0"> -->
		            	<div class="input-group-border" style="margin-top:8px;padding-top:5px;">
			         		
			         		<div class=" pull-right padding-left-0 padding-right-8 margin-top-0">	
					
								<div class=" pull-left padding-left-5 padding-right-0" style="width: 300px;">
									<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">航材类型</div>
										<div class="font-size-9">Airmaterial type</div>
									</span>
									<div class=" col-xs-8 col-sm-8">
										<select class='form-control' id='open_win_material_basic_hclx_search' onchange="open_win_material_basic.load();">
											<option value="" >显示全部All</option>
									    </select>
									</div>
								</div>	
								
								<!-- 搜索框start -->
								<div class=" pull-right padding-left-0 padding-right-0">
									<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" placeholder='件号/中英文/厂家件号' id="open_win_material_basic_keyword_search" class="form-control ">
										<input type="hidden" id="dprtId" value="${user.jgdm}" />
									</div>
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button name="keyCodeSearch"
										type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_material_basic.search()">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div> 
								</div>
								<!-- 搜索框end -->
							</div>
		            	
			         		<div class="clearfix"></div>
			         		
						<!-- start:table -->
						<div class="margin-left-8 margin-right-8" style='margin-top:5px;margin-bottom:5px;'>
							<div class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0" style="overflow-x:auto;">
								<table id="open_win_material_basic_table" class="table table-bordered table-striped table-hover table-set" style="min-width:500px">
									<thead>
								   		<tr>
											<th style="width:50px">
												<div id="checkSingle">
													<div class="font-size-12 notwrap">选择</div>
													<div class="font-size-9 notwrap">Choice</div>
												</div>
												<div id="checkAll">
													<a href="#" onclick="open_win_material_basic.performSelectAll(true);" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
													<a href="#" class="margin-left-5" onclick="open_win_material_basic.performSelectAll(false);" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
												</div>
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
											<th class="colwidth-10">
												<div class="important">
													<div class="font-size-12 notwrap">中文名称</div>
													<div class="font-size-9 notwrap">CH.Name</div>
												</div>
											</th>
											<th class="colwidth-10">
												<div class="important">
													<div class="font-size-12 notwrap">厂家件号</div>
													<div class="font-size-9 notwrap">MP/N</div>
												</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">适用机型</div>
												<div class="font-size-9 notwrap">Model</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">ATA章节号</div>
												<div class="font-size-9 notwrap">ATA</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 notwrap">航材类型</div>
												<div class="font-size-9 notwrap">type</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 notwrap">管理级别</div>
												<div class="font-size-9 notwrap">level</div>
											</th>
											<th class="colwidth-5 stock_column">
												<div class="font-size-12 notwrap">库存</div>
												<div class="font-size-9 notwrap">Num</div>
											</th>
											<th class="colwidth-5 stock_column">
												<div class="font-size-12 notwrap">单位</div>
												<div class="font-size-9 notwrap">Unit</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="open_win_material_basic_list">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="open_win_material_basic_pagination"></div>
							</div>
							<!-- end:table -->
		                	<!-- <div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_material_basic.save();" data-dismiss="modal">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
			                </div> -->
				     		<div class="clearfix"></div>
					 	 <!-- </div> -->
					 </div> 
				<!-- </div> -->
				<div class="clearfix"></div>
			</div>
			 <div class="modal-footer">
           	<div class="col-xs-12 padding-leftright-8 text-right" >
				<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_material_basic.save();" data-dismiss="modal">
					<div class="font-size-12">确定</div>
					<div class="font-size-9">Confirm</div>
				</button>
				<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_material_basic.clear();" data-dismiss="modal">
					<div class="font-size-12">清空</div>
					<div class="font-size-9">Clear</div>
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
