<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="open_win_demand_source" tabindex="-1" role="dialog"  aria-labelledby="receipt_add_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:80%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">需求来源</div>
							<div class="font-size-12" id="modalHeadENG">Demand Source</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" >
           		<div class="col-xs-12 margin-top-8 margin-bottom-8">
            	<ul class="nav nav-tabs tabNew-style" role="tablist" style="border-radius:0px;padding-top:0px !important;">
	            	<li role="presentation" class="active">
	            		<a href="#source_wp" data-toggle="tab" id="source_tab_wp"></a>
	            	</li>
	            	<li role="presentation">
	            		<a href="#source_wo" data-toggle="tab"  id="source_tab_wo"></a>
	            	</li>
            	</ul>
            	<div class="tab-content " style="border-radius:0px">
            	<div class="tab-pane fade in active" id="source_wp">
            	<div style='margin-bottom:8px;'>
            	 <div class=" pull-right padding-left-0 padding-right-0">
					 <div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
					 	<input placeholder="工包编号/描述/预计执行单位" id="open_win_demand_source_keyword_search" class="form-control" type="text">
					 </div>
		             <div class=" pull-right padding-left-5 padding-right-0 ">   
						 <button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_demand_source.search()">
							 <div class="font-size-12">搜索</div>
							 <div class="font-size-9">Search</div>
			              </button>
		              </div> 
	              </div>
	              <div class='clearfix'></div>
	              </div>
            	  <div id="" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 " style="overflow-x: auto;margin-bottom:8px;">
					    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
						<thead>                              
							<tr>
							    <th class='colwidth-5'>
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class='colwidth-9'>
									<div class="important">
										<div class="font-size-12 line-height-18" >工包编号</div>
										<div class="font-size-9 line-height-18">Package No.</div>
									</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="important">
										<div class="font-size-12 line-height-18">描述</div>
										<div class="font-size-9 line-height-18">Desc</div>
									</div>
								</th>
								<th class='colwidth-13' onclick="" name="">
									<div class="font-size-12 line-height-18">维修类型</div>
									<div class="font-size-9 line-height-18">Maintenance Type</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">计划开始日期</div>
									<div class="font-size-9 line-height-18">Plan Start Date</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">计划完成日期</div>
									<div class="font-size-9 line-height-18">Plan End Date</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">下发单位</div>
									<div class="font-size-9 line-height-18">Issuing Unit</div>
								</th>
								<th class='colwidth-20' onclick="" name="" >
									<div class="important">
										<div class="font-size-12 line-height-18">预计执行单位</div>
										<div class="font-size-9 line-height-18">Estimated Execution Unit</div>
									</div>
								</th>
								<th class='colwidth-20' onclick="" name="" >
									<div class="font-size-12 line-height-18">工作要求</div>
									<div class="font-size-9 line-height-18">Work Request</div>
								</th>
							</tr> 
						</thead>
						<tbody id="open_win_demand_source_list">
						</tbody>
					</table>
					</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="open_win_demand_source_pagination">
					</div>
            	</div>
            	<div class="tab-pane fade" id="source_wo">
            	<div style='margin-bottom:8px;'>
            	 <div class=" pull-right padding-left-0 padding-right-0">
            	 	 <div class="pull-left padding-left-5 padding-right-0">
						<label class="defaultChecked" style="margin-top:6px;font-weight:normal;" onchange="open_win_demand_source_wo.search()">
							<input name="source_gdlx" value="1" style="vertical-align:text-bottom" checked="checked" type="checkbox">&nbsp;RTN&nbsp;&nbsp;
						</label>
						<label class="defaultChecked" style="margin-top:6px;font-weight:normal;" onchange="open_win_demand_source_wo.search()">
							<input name="source_gdlx" value="2" style="vertical-align:text-bottom;" checked="checked" type="checkbox">&nbsp;EO&nbsp;&nbsp;
						</label>
						<label class="defaultChecked" style="margin-top:6px;font-weight:normal;" onchange="open_win_demand_source_wo.search()">
							<input name="source_gdlx" value="3" style="vertical-align:text-bottom;" checked="checked" type="checkbox">&nbsp;NRC&nbsp;&nbsp;
						</label>
						<label class="defaultChecked" style="margin-top:6px;font-weight:normal;" onchange="open_win_demand_source_wo.search()">
							<input name="source_gdlx" value="4" style="vertical-align:text-bottom;" checked="checked" type="checkbox">&nbsp;MO/MCC&nbsp;&nbsp;
						</label>
						<label class="defaultChecked" style="margin-top:6px;font-weight:normal;" onchange="open_win_demand_source_wo.search()">
							<input name="source_gdlx" value="5" style="vertical-align:text-bottom;" checked="checked" type="checkbox">&nbsp;其它指令&nbsp;&nbsp;
						</label>
						<label class="defaultNotChecked" style="margin-top:6px;font-weight:normal;" onchange="open_win_demand_source_wo.search()">
							<input name="source_gdlx" value="9" style="vertical-align:text-bottom;" type="checkbox">&nbsp;FLB&nbsp;&nbsp;
						</label>
					</div>
					 <div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
					 	<input placeholder="工单编号/工单标题" id="open_win_demand_source_wo_keyword_search" class="form-control" type="text">
					 </div>
		             <div class=" pull-right padding-left-5 padding-right-0 ">   
						 <button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_demand_source_wo.search()">
							 <div class="font-size-12">搜索</div>
							 <div class="font-size-9">Search</div>
			              </button>
		              </div> 
	              </div>
	              <div class='clearfix'></div>
	              </div>
            	 <div id="" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 " style="overflow-x: auto;margin-bottom:8px;">
					    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
						<thead>                              
							<tr>
							    <th class='colwidth-5'>
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class='colwidth-15' onclick="" name="" >
									<div class="important">
										<div class="font-size-12 line-height-18">工单编号</div>
										<div class="font-size-9 line-height-18">Work Order</div>
									</div>
								</th>
								<th class='colwidth-15'>
									<div class="font-size-12 line-height-18" >任务号</div>
									<div class="font-size-9 line-height-18">Task No.</div>
								</th>
								<th class='colwidth-25' onclick="" name="">
									<div class="important">
										<div class="font-size-12 line-height-18">工单标题</div>
										<div class="font-size-9 line-height-18">Title</div>
									</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">计划开始日期</div>
									<div class="font-size-9 line-height-18">Plan Start Date</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">计划完成日期</div>
									<div class="font-size-9 line-height-18">Plan End Date</div>
								</th>
								<th class='colwidth-15' onclick="" name="" >
									<div class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">来源分类</div>
									<div class="font-size-9 line-height-18">Originating Type</div>
								</th>
							</tr> 
						</thead>
						<tbody id="open_win_demand_source_wo_list">
						</tbody>
					</table>
					</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="open_win_demand_source_wo_pagination">
					</div>
            	</div>
            	</div>
            	</div>
	             <div class='clearfix'></div>          
           	</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<i class='glyphicon glyphicon-info-sign alert-info' style="display: none;"></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
	                    	<button type="button" onclick="open_win_demand_source.save()" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button id="source_btn_clear" type="button" onclick="open_win_demand_source.clearProject()" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">清空</div>
								<div class="font-size-9">Clear</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
	                    </span>
	               	</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/demand/apply/apply_source_wo.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/demand/apply/apply_source_wp.js"></script>