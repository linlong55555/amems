<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/work_card_win.js"></script>
<div class="modal fade in modal-new" id="open_win_work_card" tabindex="-1" role="dialog"  aria-labelledby="open_win_work_card" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width:70%;">
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
       			<h4 class="modal-title" >
                   	<div class='pull-left'>
                 		<div class="font-size-12">工卡列表</div>
						<div class="font-size-9">Work Card List</div>
 					</div>
 					<div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
 					<div class='clearfix'></div>
                </h4>
           	</div>
            <div class="modal-body" style='padding-top:0px;'>
              	<div class="input-group-border" style='margin-top:8px;padding-top:5px;margin-bottom:8px;'>
              		<div class=" pull-right padding-left-0 padding-right-8 margin-top-0 modalSearch">	
			         		
						<!-- 搜索框start -->
						<div class=" pull-right padding-left-0 padding-right-0">
							<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
								<input type="text" placeholder='工卡编号/工卡标题/维修项目' id="open_win_work_card_keyword_search" class="form-control" />
							</div>
	                    	<div class=" pull-right padding-left-5 padding-right-0 ">   
								<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_work_card.search()">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
	                   			</button>
	                    	</div> 
						</div>
						<!-- 搜索框end -->
					</div>
		            	
			       	<div class="clearfix"></div>
			         		
					<!-- start:table -->
					<div class="margin-left-8 margin-right-8" style='margin-top:5px;'>
						<div class="table-content" style="overflow-x:auto;" id="searchTable">
							<table class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 500px !important">
								<thead>
							   		<tr>
							   			<th style="width:50px" id="checkSingle">
											<div class="font-size-12 line-height-18">操作</div>
											<div class="font-size-9 line-height-18">Operation</div>
										</th>
										<th id="checkAll" style='text-align:center;vertical-align:middle;width:70px;' class="selectAllImg">
											<a href="#" onclick="SelectUtil.performSelectAll('open_win_work_card_list')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
											<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('open_win_work_card_list')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
										</th>
										<th class="colwidth-15">
											<div class="important">
												<div class="font-size-12 line-height-18">工卡编号</div>
												<div class="font-size-9 line-height-18">Work Card No.</div>
											</div>
										</th>
										<th class="colwidth-30">
											<div class="important">
												<div class="font-size-12 line-height-18">工卡标题</div>
												<div class="font-size-9 line-height-18">Title</div>
											</div>
										</th>
										<th class="colwidth-10">
											<div class="important">
												<div class="font-size-12 line-height-18">维修项目</div>
												<div class="font-size-9 line-height-18">Project</div>
											</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 line-height-18">ATA章节号</div>
											<div class="font-size-9 line-height-18">ATA</div>
										</th>
										<th class="colwidth-9">
											<div class="font-size-12 line-height-18">工作类别</div>
											<div class="font-size-9 line-height-18">Category</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-18">参考工时</div>
											<div class="font-size-9 line-height-18">MHRs</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 line-height-18">专业</div>
											<div class="font-size-9 line-height-18">Skill</div>
										</th>
							 		 </tr>
								</thead>
								<tbody id="open_win_work_card_list">
								</tbody>
							</table>
						</div>
						<div id="open_win_work_card_pagination" class="col-xs-12 text-center page-height padding-right-0 padding-left-0"></div>
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
	                   <!-- <i class='glyphicon glyphicon-info-sign alert-info'></i>警告！请不要提交。 -->
					</span>
	                <span class="input-group-addon modalfooterbtn">
	                  	<button onclick="open_win_work_card.save();" type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
						<button type="button" onclick="open_win_work_card.clearData()" id="open_win_work_card_clear_btn"
								class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">清空</div>
							<div class="font-size-9">Clear</div>
						</button>
	               		<button onclick="open_win_work_card.close();" type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
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