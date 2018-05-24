<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/materialAndOutfield_modal.js"></script>
<!-- 飞机注册号选择 -->
<div class="modal fade modal-new" id="open_win_materialAndOutfield" tabindex="-1" role="dialog" aria-labelledby="open_win_installationlist_removed" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog" style="width:60%;">
		<div class="modal-content">	
	                <div class="modal-header modal-header-new">
                       <h4 class="modal-title">
                          <div class="pull-left">
	                       <div class="font-size-12 ">航材查询</div>
							<div class="font-size-9">Material List</div>
						  </div>
						  <div class='pull-right'>
					  		  <button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					  	  </div>
						  <div class="clearfix"></div>
                       </h4>
                    </div>
					<div class="modal-body padding-bottom-0" style="overflow: auto;margin-top:0px;">
		            	 <div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0 margin-top-10" style='margin-bottom:10px;'>
		                	<ul id="myTab" class="nav nav-tabs tabNew-style">
		                      	<li class="active">
		                      	<a href="#outfield"  onclick="materialAndOutfield.yqChange(true)" data-toggle="tab" aria-expanded="false">
		                      		<div class="font-size-12 line-height-12">外场列表</div>
					                <div class="font-size-9 line-height-9">Outfield List</div>
		                      	</a>
		                      	</li>
		                      	<li class="">
		                      	<a href="#material" onclick="materialAndOutfield.yqChange(false)" data-toggle="tab" aria-expanded="false">
		                      		<div class="font-size-12 line-height-12">航材列表</div>
					                <div class="font-size-9 line-height-9">Material List</div>
					             </a>
		                      	</li>
		                      <div id="status_main_rightSecondDiv_block" class="pull-right" style='height:1px;padding-right:8px;margin-top:0px;display:none;'>
								<img src="${ctx}/static/images/up.png" onclick='aircraftstatus.blockBottom()' style="width:33px;cursor:pointer;"></div>
		                    </ul>
		                    <div id="myTabContent" class="tab-content" style='padding-bottom:5px;'>
		                    	<div class="pull-right padding-left-0 padding-right-8 margin-top-0 modalSearch" style='padding-bottom:10px;'>	
									<!-- 搜索框start -->
									<div class=" pull-right padding-left-0 padding-right-0">
										<div class=" pull-left padding-left-5 padding-right-0" style="width: 300px;">
											<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<div class="font-size-12">器材状态</div>
												<div class="font-size-9">Status</div>
											</span>
											<div class=" col-xs-8 col-sm-8">
												<select class='form-control' id='materialAndOutfield_qczt_search' onchange="materialAndOutfield.search()">
											    </select>
											</div>
										</div>	
										<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
											<input type="text" fl="true" placeholder='件号/名称/序列号/型号/厂家件号' id="materialAndOutfield_keyword_search" class="form-control" />
										</div>
							                  <div class=" pull-right padding-left-5 padding-right-0 ">   
											<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="materialAndOutfield.search()">
												<div class="font-size-12">搜索</div>
												<div class="font-size-9">Search</div>
							                 		</button>
							                  </div> 
									</div>
									<!-- 搜索框end -->
								</div>
								<div class='clearfix'></div>
		                      	<div class="tab-pane fade in active" id="outfield" style="width: 100%;">
									<%@ include file="/WEB-INF/views/open_win/outfield_modal_main.jsp"%>
		                      	</div>
		                      	
		                      	<div class="tab-pane fade" id="material" style="width: 100%;">
		                      		<%@ include file="/WEB-INF/views/open_win/material_modal_main.jsp"%>
		                      	</div>
				            </div>
				          </div>
				          <div class='clearfix'></div>
					 </div> 
					 <div class='clearfix'></div>
					 <div class="modal-footer">
			           	<div class="col-xs-12 padding-leftright-8">
							<div class="input-group">
							<span class="input-group-addon modalfootertip">
							</span>
			                    <span class="input-group-addon modalfooterbtn">
			                        <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="materialAndOutfield.save();" data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<!-- <button id="project_btn_clear" type="button" onclick="materialAndOutfield.clearProject()" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">清空</div>
										<div class="font-size-9">Clear</div>
									</button> -->
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
			                    </span>
			               	</div><!-- /input-group -->
			               	<div class="clearfix"></div>
						</div>
						
					</div>
					<div class="clearfix"></div>
		</div>
	</div>
</div>
