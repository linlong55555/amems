<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/installationlist_installed.js"></script>
<!-- 飞机注册号选择 -->
<div class="modal fade modal-new" id="open_win_installationlist_installed" tabindex="-1" role="dialog" aria-labelledby="open_win_installationlist_installed" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog" style="width:60%;">
		<div class="modal-content">	
	                <div class="modal-header modal-header-new">
                       <h4 class="modal-title">
                          <div class="pull-left">
	                       <div class="font-size-12 ">装上件查询</div>
							<div class="font-size-9">Installed</div>
						  </div>
						  <div class='pull-right'>
					  		  <button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					  	  </div>
						  <div class="clearfix"></div>
                       </h4>
                    </div>
					<div class="modal-body padding-bottom-0" style="overflow: auto;margin-top:0px;">
		            	<div class="input-group-border" style="margin-top:8px;padding-top:5px;">
			         		
			         		<div class="pull-right padding-left-0 padding-right-8 margin-top-0 modalSearch">	
			         		
								<!-- 搜索框start -->
								<div class=" pull-right padding-left-0 padding-right-0">
									<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" placeholder='件号/名称/序列号/型号/厂家件号' id="open_win_installationlist_installed_keyword_search" class="form-control" />
									</div>
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_installationlist_installed.search()">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div> 
								</div>
								<!-- 搜索框end -->
							</div>
		            	
			         		<div class="clearfix"></div>
			         		
							<!-- start:table -->
							<div class="margin-left-8 margin-right-8" style="margin-top:5px;">
							<div style="overflow: auto;" id="searchTable" class="table-content">
								<table id="open_win_regist_basic_table" class="table table-bordered table-striped table-hover table-thin table-set" >
									<thead>
								   		<tr>
											<th style="width:50px" id="checkSingle">
												<div class="font-size-12 line-height-18">操作</div>
												<div class="font-size-9 line-height-18">Operation</div>
											</th>
											<th style="width:50px" id="checkAll" class="selectAllImg">
												<a href="#" onclick="SelectUtil.performSelectAll('open_win_installationlist_installed_list')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
												<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('open_win_installationlist_installed_list')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
											</th>
											<th class="colwidth-10">
												<div class="important">
													<div class="font-size-12 line-height-18">件号</div>
													<div class="font-size-9 line-height-18">P/N</div>
												</div>
											</th>
											<th class="colwidth-15">
												<div class="important">
													<div class="font-size-12 line-height-18">序列号</div>
													<div class="font-size-9 line-height-18">S/N</div>
												</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">批次号</div>
												<div class="font-size-9 line-height-18">B/N</div>
											</th>
											<th class="colwidth-20">
												<div class="important">
													<div class="font-size-12 line-height-18">名称</div>
													<div class="font-size-9 line-height-18">Name</div>
												</div>
											</th>
											<th class="colwidth-10">
												<div class="important">
													<div class="font-size-12 line-height-18">型号</div>
													<div class="font-size-9 line-height-18">Model</div>
												</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">ATA章节号</div>
												<div class="font-size-9 line-height-18">ATA</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">规格</div>
												<div class="font-size-9 line-height-18">MHRs</div>
											</th>
											<th class="colwidth-10">
												<div class="important">
													<div class="font-size-12 line-height-18">厂家件号</div>
													<div class="font-size-9 line-height-18">MHRs</div>
												</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">适用机型</div>
												<div class="font-size-9 line-height-18">MHRs</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="open_win_installationlist_installed_list">
									
									</tbody>
								</table>
							</div>
							<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="open_win_installationlist_installed_pagination"></div>
							</div>
				     		<div class="clearfix"></div>
					 	 </div>
					 </div> 
					 <div class="modal-footer">
			           	<div class="col-xs-12 padding-leftright-8">
							<div class="input-group">
							<span class="input-group-addon modalfootertip">
							</span>
			                    <span class="input-group-addon modalfooterbtn">
			                        <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_installationlist_installed.save();" data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button id="project_btn_clear" type="button" onclick="open_win_installationlist_installed.clearProject()" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">清空</div>
										<div class="font-size-9">Clear</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
			                    </span>
			               	</div><!-- /input-group -->
						</div>
					</div>
					<div class="clearfix"></div>
		</div>
	</div>
</div>
