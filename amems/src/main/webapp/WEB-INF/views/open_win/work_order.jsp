<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<!-- 工单选择	 -->
<div class="modal fade in modal-new" id="open_win_work_order" tabindex="-1" role="dialog" aria-labelledby="open_win_work_order" aria-hidden="true">
	<div class="modal-dialog" style="width:60%;">
		<div class="modal-content">	
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
           			<div class='pull-left'>
               			<div class="font-size-12 ">工单</div>
						<div class="font-size-9">Work Order</div>
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
						
				  		<div class="pull-left padding-left-0 padding-top-0" style="width:220px;">
		            	    <label class="col-xs-4 text-right padding-left-0 padding-right-2">
								<div class="font-size-12 line-height-18">工单类型</div>
								<div class="font-size-9 line-height-18">Type</div>
							</label>
		            	    <div id="lyfl_div" class="col-lg-8 pull-left padding-right-0 padding-left-0 ">
	            	            <select multiple='multiple' id='lyfl_search'>
								</select>
						    </div>  
					   </div>
							
				        <div class="pull-left padding-left-0 padding-top-0" style="width:220px;">
		            	    <label class="col-xs-4 text-right padding-left-0 padding-right-2">
								<div class="font-size-12 line-height-18">专业</div>
								<div class="font-size-9 line-height-18">Skill</div>
							</label>
		            	    <div class="col-lg-8 pull-left padding-right-0 padding-left-0 ">
	            	            <select id="zy2_search" class="form-control " name="zy2_search" onchange="open_win_work_order.load();">
	             	                <option value="" select="select">查看全部</option>
	            	            </select>
						    </div>  
					   </div>
					
						<div class="pull-left padding-left-10 padding-right-0" style="width:200px;">
							<input type="text" class="form-control " id="open_win_work_order_keyword_search" placeholder="工单编号/主题"/>
						</div>
	                    <div class="pull-right padding-left-5 padding-right-0">   
	                   		<button name="keyCodeSearch"
	                   		 type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_work_order.load();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
							</button>
	                    </div>    
					</div>
		            	
			       	<div class="clearfix"></div>
					
					<!-- start:table -->
					<div class="margin-left-8 margin-right-8" style='margin-top:5px;'>
						<div class="table-content" style="overflow-x:auto;" id="searchTable">
							<table class="table table-bordered table-striped table-hover table-set">
								<thead>
									<tr>
										<th id="open_win_work_order_single_choice" width="30px">
											<div class="font-size-12 line-height-18" >选择</div>
											<div class="font-size-9 line-height-18" >Choice</div>
										</th>
										<th id="open_win_work_order_multi_choice" style='vertical-align: middle;' width="70px" >
											<a href="#" onclick="SelectUtil.performSelectAll('open_win_work_order_list')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
											<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('open_win_work_order_list')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
											<!-- <input type="checkbox" id="open_win_work_order_checkall" onclick="open_win_work_order.checkAll();"/> -->
										</th>
										<th width="50px">
											<div class="font-size-12 line-height-18">序号</div>
											<div class="font-size-9 line-height-16">No.</div>
										</th>
										<th width="150px">
											<div class="important"><div class="font-size-12 line-height-18">工单编号</div></div>
											<div class="font-size-9 line-height-16">W/O No.</div>
										</th>
										<th width="120px">
											<div class="font-size-12 line-height-18">工单类型</div>
											<div class="font-size-9 line-height-16">W/O Type</div>
										</th>
										<th width="70px">
											<div class="font-size-12 line-height-18">专业</div>
											<div class="font-size-9 line-height-16">Skill</div>
										</th>
										<th>
											<div class="important"><div class="font-size-12 line-height-18">主题</div></div>
											<div class="font-size-9 line-height-16">Subject</div>
										</th>
									</tr> 
				         		</thead>
								<tbody id="open_win_work_order_list">
								
								</tbody>
							</table>
							<div id="open_win_work_order_pagination" class="col-xs-12 text-center page-height padding-right-0 padding-left-0"></div>
						</div>
						<!-- end:table -->
				 	 </div>
				 </div> 
				 <div class="clearfix"></div>              
			</div>
			<div class="clearfix"></div>  
           <div class="modal-footer">
	           <div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
					<span class="input-group-addon modalfootertip" >
	                   <!-- <i class='glyphicon glyphicon-info-sign alert-info'></i>警告！请不要提交。 -->
					</span>
	                <span class="input-group-addon modalfooterbtn">
	                  	<button onclick="open_win_work_order.save();" type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
	               		<button onclick="open_win_work_order.close();" type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/work_order.js"></script>