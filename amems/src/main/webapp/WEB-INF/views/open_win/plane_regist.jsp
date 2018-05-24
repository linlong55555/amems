<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/plane_regist.js"></script>
<!-- 飞机注册号选择 -->
<div class="modal fade" id="open_win_plane_regist" tabindex="-1" role="dialog" aria-labelledby="open_win_plane_regist" aria-hidden="true">
	<div class="modal-dialog" style="width:60%;">
		<div class="modal-content">	
			<div class="modal-body padding-bottom-0">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">飞机注册号列表</div>
						<div class="font-size-9 ">A/C REG List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12 padding-right-0 padding-left-0">
			         		
			         		<div class=" pull-right padding-left-0 padding-right-0 margin-top-10">	
			         		
								<!-- 搜索框start -->
								<div class=" pull-right padding-left-0 padding-right-0">
									<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" placeholder='机型/飞机注册号/序列号/备注名' id="open_win_plane_regist_keyword_search" class="form-control" />
									</div>
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_plane_regist.search()">
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
								<table id="open_win_regist_basic_table" class="table table-bordered table-striped table-hover table-set" style="min-width: 1700px !important">
									<thead>
								   		<tr>
											<th class="colwidth-5" id="checkSingle">
												<div class="font-size-12 line-height-18">操作</div>
												<div class="font-size-9 line-height-18">Operation</div>
											</th>
											<th class="colwidth-5" id="checkAll" style='text-align:center;vertical-align:middle;width:5%'>
												<a href="#" onclick="SelectUtil.performSelectAll('open_win_plane_regist_list')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
												<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('open_win_plane_regist_list')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
											</th>
											<th class="colwidth-10">
												<div class="important">
													<div class="font-size-12 line-height-18" >机型</div>
													<div class="font-size-9 line-height-18" >Model</div>
												</div>
											</th>
											<th class="colwidth-15">
												<div class="important">
													<div class="font-size-12 line-height-18" >飞机注册号</div>
													<div class="font-size-9 line-height-18" >A/C REG</div>
												</div>
											</th>
											<th class="colwidth-20">
												<div class="important">
													<div class="font-size-12 line-height-18" >序列号</div>
													<div class="font-size-9 line-height-18" >S/N</div>
												</div>
											</th>
											<th class="colwidth-30">
												<div class="important">
													<div class="font-size-12 line-height-18" >备注名</div>
													<div class="font-size-9 line-height-18" >Remark Name</div>
												</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18" >出厂日期</div>
												<div class="font-size-9 line-height-18" >PD</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18" >国籍登记证号</div>
												<div class="font-size-9 line-height-18" >Registration</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18" >适航证号</div>
												<div class="font-size-9 line-height-18" >Certificate</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18" >无线电台许可证号</div>
												<div class="font-size-9 line-height-18" >Station license</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18" >电台执照监控日期</div>
												<div class="font-size-9 line-height-18" >Exp.</div>
											</th>
											<th class="colwidth-30">
												<div class="font-size-12 line-height-18" >改装记录</div>
												<div class="font-size-9 line-height-18" >Record</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="open_win_plane_regist_list">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="open_win_plane_regist_pagination"></div>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_plane_regist.save();" data-dismiss="modal">
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
