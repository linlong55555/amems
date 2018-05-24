<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/business_win_list.js"></script>
<div class="modal fade in modal-new" id="business_main" tabindex="-1" role="dialog"  aria-labelledby="business_main" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width: 60%;" >
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
           			<div class='pull-left'>
               			<div class="font-size-12 ">岗位列表</div>
						<div class="font-size-9"> List</div>
				 	</div>
 					<div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
          	</div>
           	<div class="modal-body" style='padding-top:0px;'>
             	<div class="input-group-border" style='margin-top:8px;padding-top:5px;'>
                   	<div class=" pull-right padding-left-0 padding-right-8 margin-top-0 modalSearch">	
       		
						<!-- 搜索框start -->
						<div class=" pull-right padding-left-0 padding-right-0">
							<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
								<input type="text" placeholder='岗位编号/岗位名称/备注'  class="form-control" id="keyword_business_search" >
							</div>
		                   	<div class=" pull-right padding-left-5 padding-right-0 ">   
							<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="business_main.search()">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                  	</div> 
						</div>
					<!-- 搜索框end -->
					</div>
	            	
		         	<div class="clearfix"></div>
		         		
					<!-- start:table -->
					<div id="business_main_top_div" class="margin-left-8 margin-right-8" style='margin-top:5px;margin-bottom:5px;'>
						<div class='col-xs-12 padding-left-0 padding-right-0 table-content' id="searchTable">
							<table id="business_table_list" class="table table-thin table-bordered table-striped table-hover table-set" >
								<thead>
								   		<tr>
											<th class="viewCol fixed-column colwidth-5 selectAllImg" style="width: 75px;">
												<a href="#" onclick="SelectUtil.performSelectAll('business_main_top_div')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
												<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('business_main_top_div')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
											</th> 
											<th class="colwidth-10" >
												<div class="important">
													<div class="font-size-12 line-height-18">岗位编号</div>
													<div class="font-size-9 line-height-18">No.</div>
												</div>
											</th>
											<th class=" colwidth-20 ">
												<div class="important">
													<div class="font-size-12 line-height-18">岗位名称</div>
													<div class="font-size-9 line-height-18">Name</div>
												</div>
											</th>
											<th class=" colwidth-20 ">
												<div class="important">
													<div class="font-size-12 line-height-18">备注</div>
													<div class="font-size-9 line-height-18">Desc</div>
												</div>
											</th>
												
							 		 </tr>
									</thead>
									<tbody id="business_tbody_list">
									
									</tbody>
							</table>
						</div>
						<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="business_Pagination"></div>
						
						<div class="clearfix"></div>  
					</div>
				</div>
               <div class="clearfix"></div>              
          </div>
          <div class="clearfix"></div>  
          <div class="modal-footer">
          		<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
						</span>
	                   	<span class="input-group-addon modalfooterbtn">
	                     	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="business_main.setData()" data-dismiss="modal">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
	                   	</span>
              		</div><!-- /input-group -->
				</div>
			</div>
		</div>
	</div>
</div>
<!--  弹出框结束-->