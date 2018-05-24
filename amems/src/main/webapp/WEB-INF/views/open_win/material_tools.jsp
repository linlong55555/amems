<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/material_tools.js"></script>
<div class="modal fade in modal-new" id="open_win_material_tools" tabindex="-1" role="dialog"  aria-labelledby="open_win_material_tools" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width:70%;">
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
       			<h4 class="modal-title" >
                   	<div class='pull-left'>
                 		<div class="font-size-12 " id="modalHeadChina">航材列表</div>
						<div class="font-size-9" id="modalHeadEnglish">Material List</div>
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
			         		
		         		<div class="search-criteria pull-left padding-left-5 padding-right-0" style="width: 300px;">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">类型</div>
								<div class="font-size-9">Type</div>
							</span>
							<div class=" col-xs-8 col-sm-8">
								<select class='form-control' id='open_win_material_tools_hclx_search' onchange="open_win_material_tools.search()">
									<option value="" >显示全部All</option>
									<option value="1" >航材</option>
									<option value="4" >化工品</option>
							    </select>
							</div>
						</div>	
			         		
						<!-- 搜索框start -->
						<div class=" pull-right padding-left-0 padding-right-0">
							<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
								<input type="text" placeholder='件号/英文名称/中文名称' id="open_win_material_tools_keyword_search" class="form-control" />
							</div>
	                    	<div class=" pull-right padding-left-5 padding-right-0 ">   
								<button name="keyCodeSearch"
								type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_material_tools.search()">
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
											<a href="#" onclick="SelectUtil.performSelectAll('open_win_material_tools_list')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
											<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('open_win_material_tools_list')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 line-height-18">类型</div>
											<div class="font-size-9 line-height-18">Type</div>
										</th>
										<th class="colwidth-15">
											<div class="important">
												<div class="font-size-12 line-height-18">件号</div>
												<div class="font-size-9 line-height-18">P/N</div>
											</div>
										</th>
										<th class="colwidth-20">
											<div class="important">
												<div class="font-size-12 line-height-18">英文名称</div>
												<div class="font-size-9 line-height-18">English Desc</div>
											</div>
										</th>
										<th class="colwidth-15">
											<div class="important">
												<div class="font-size-12 line-height-18">中文名称</div>
												<div class="font-size-9 line-height-18">Chinese Desc</div>
											</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">型号</div>
											<div class="font-size-9 line-height-18">Model</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">件号来源</div>
											<div class="font-size-9 line-height-18">Source</div>
										</th>
										<th class="colwidth-5 stock_column">
											<div class="font-size-12 line-height-18">在库数量</div>
											<div class="font-size-9 line-height-18">QTY</div>
										</th>
										<th class="colwidth-5 stock_column">
											<div class="font-size-12 line-height-18">单位</div>
											<div class="font-size-9 line-height-18">Unit</div>
										</th>
							 		 </tr>
								</thead>
								<tbody id="open_win_material_tools_list">
								</tbody>
							</table>
						</div>
						<div id="open_win_material_tools_pagination" class="col-xs-12 text-center page-height padding-right-0 padding-left-0"></div>
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
	                  	<button onclick="open_win_material_tools.save();" type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
	               		<button onclick="open_win_material_tools.close();" type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
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