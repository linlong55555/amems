<%@ page contentType="text/html; charset=utf-8" language="java" %>

<!-- 选择工单 -->
<div class="modal fade modal-new" id="open_win_flightlogbook_workorder_modal" tabindex="-1" role="dialog" aria-labelledby="open_win_flightlogbook_workorder_modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:80%">
		<div class="modal-content">	
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
                	<div class='pull-left'>
	                    <div class="font-size-14">选择工单</div>
						<div class="font-size-12">Choose W/O</div>
			  		</div>
				  	<div class='pull-right'>
				  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				  	</div>
			  		<div class='clearfix'></div>
               	</h4>
            </div>
			<div class="modal-body padding-bottom-0">
				<div class="col-xs-12 margin-top-8">
					<div class="input-group-border padding-leftright-8">
						
						<!-- <div class="panel-heading  padding-top-3 padding-bottom-1 pull-left">
							<div class="font-size-16 line-height-18">待选择</div>
							<div class="font-size-9 ">To Be Selected</div>
						</div>
						
						<div class="pull-left padding-left-10">
							<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="flb_workorder.select()">
								<div class="font-size-12">选中</div>
								<div class="font-size-9">Select</div>
							</button>
						</div> -->
						
						<div class="pull-right margin-top-0 modalSearch">	
						
							<div class="pull-left margin-right-10">
								<div class="pull-left text-right padding-left-0 padding-right-0">
									<div class="font-size-12">状态</div>
									<div class="font-size-9">Status</div>
								</div>
								<div class="pull-left text-right padding-left-0 padding-right-0">
									<div class="padding-left-5 pull-left" style="width: 250px; margin-right:3px;">
										<select class="form-control" id="workorder_modal_zt" onchange="flb_workorder.load()">
											<option value="">全部All</option>
											<option value="7" selected="selected">生效下发</option>
											<option value="10">完工关闭</option>
										</select>
									</div>
								</div>
							</div>
							
							<div class="pull-left margin-right-10">
								<div class="pull-left text-right padding-left-0 padding-right-0">
									<div class="font-size-12">工包</div>
									<div class="font-size-9">Package</div>
								</div>
								<div class="pull-left text-right padding-left-0 padding-right-0">
									<div class="padding-left-5 pull-left" style="width: 250px; margin-right:3px;">
										<input placeholder="工包编号/描述" class="form-control" type="text" id="workorder_modal_gb">
									</div>
								</div>
							</div>
							
							<div class="pull-left">
								<div class="pull-left text-right padding-left-0 padding-right-0">
									<div class="font-size-12">工单</div>
									<div class="font-size-9">W/O</div>
								</div>
								<div class="pull-left text-right padding-left-0 padding-right-0">
									<div class="padding-left-5 pull-left" style="width: 250px; margin-right:3px;">
										<input placeholder="工单编号/主题" class="form-control" type="text" id="workorder_modal_gd">
									</div>
								</div>
							</div>
							
		                    <div class=" pull-right padding-left-5 padding-right-0 ">   
								<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="flb_workorder.load()">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
		                   		</button>
		                    </div> 
						</div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 margin-top-5" style="overflow-x: auto;">
							<table class="table table-bordered table-thin table-hover table-set" style="min-width: 1000px !important">
								<thead>
							   		<tr>
										<th class="selectAllImg colwidth-5" style="text-align: center; vertical-align: middle;">
											<a href="#" onclick="flb_workorder.selectAll()"><img src="${ctx}/static/assets/img/d1.png" alt="全选"></a>
											<a href="#" class="margin-left-5" onclick="flb_workorder.cancelAll()"><img src="${ctx}/static/assets/img/d2.png" alt="不全选"></a>
										</th>
										<th class="colwidth-7" style=" font-weight:normal">
											<div class="font-size-12 line-height-12">来源 </div>
											<div class="font-size-9 line-height-12">Source</div>
										</th>
										<th class="colwidth-13">
											<div class="important">
												<div class="font-size-12 line-height-12">工单号 </div>
												<div class="font-size-9 line-height-12">W/O No.</div>
											</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-12">任务信息 </div>
											<div class="font-size-9 line-height-12">Task Info</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-12">故障信息</div>
											<div class="font-size-9 line-height-12">Fault</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-12">处理措施</div>
											<div class="font-size-9 line-height-12">Action</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-12">完成日期</div>
											<div class="font-size-9 line-height-12">Finish Date</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 line-height-12">完成时间</div>
											<div class="font-size-9 line-height-12">Finish Time</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 line-height-12">实际工时</div>
											<div class="font-size-9 line-height-12">MHRs</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 line-height-12">工作者</div>
											<div class="font-size-9 line-height-12">Worker</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 line-height-12">检查者</div>
											<div class="font-size-9 line-height-12">Checker</div>
										</th>
							 		 </tr>
								</thead>
								<tbody id="flightlogbook_workorder_modal_tbody_n">
								</tbody>
							</table>
						</div>
						<div class="clearfix"></div>
						
						<%-- <hr/>
						
						<div class="panel-heading  padding-top-3 padding-bottom-1 pull-left">
							<div class="font-size-16 line-height-18">已选择</div>
							<div class="font-size-9 ">Selected</div>
						</div>
						
						<div class="pull-left">
							<span class="badge" style="margin-top:7px;" id="select_count">0</span>
						</div>
						
						<div class="pull-left padding-left-5">
							<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="flb_workorder.remove()">
								<div class="font-size-12">移除</div>
								<div class="font-size-9">Remove</div>
							</button>
						</div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
							<table class="table table-bordered table-thin table-hover table-set" style="min-width: 1000px !important">
								<thead>
							   		<tr>
										<th class="selectAllImg colwidth-5" id="checkAll" style="text-align: center; vertical-align: middle;">
											<a href="#" onclick="SelectUtil.performSelectAll('flightlogbook_workorder_modal_tbody_y')"><img src="${ctx}/static/assets/img/d1.png" alt="全选"></a>
											<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('flightlogbook_workorder_modal_tbody_y')"><img src="${ctx}/static/assets/img/d2.png" alt="不全选"></a>
										</th>
										<th class="colwidth-7" style=" font-weight:normal">
											<div class="font-size-12 line-height-12">来源 </div>
											<div class="font-size-9 line-height-12">Source</div>
										</th>
										<th class="colwidth-13">
											<div class="important">
												<div class="font-size-12 line-height-12">工单号 </div>
												<div class="font-size-9 line-height-12">W/O No.</div>
											</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-12">任务信息 </div>
											<div class="font-size-9 line-height-12">Task Info</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-12">故障信息</div>
											<div class="font-size-9 line-height-12">Fault</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-12">处理措施</div>
											<div class="font-size-9 line-height-12">Action</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-12">完成日期</div>
											<div class="font-size-9 line-height-12">Finish Date</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 line-height-12">完成时间</div>
											<div class="font-size-9 line-height-12">Finish Time</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 line-height-12">实际工时</div>
											<div class="font-size-9 line-height-12">MHRs</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 line-height-12">工作者</div>
											<div class="font-size-9 line-height-12">Worker</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 line-height-12">检查者</div>
											<div class="font-size-9 line-height-12">Checker</div>
										</th>
							 		 </tr>
								</thead>
								<tbody id="flightlogbook_workorder_modal_tbody_y">
									<tr class="no-data"><td class='text-center' colspan="11">暂无数据 No data.</td></tr>
								</tbody>
							</table>
						</div>
						
						<div class="clearfix"></div> --%>
					</div>
			 	</div>
			 	<div class="clearfix"></div>
			</div>
			
			<div class="clearfix"></div>
			<div class="modal-footer">
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
			                <i class='glyphicon glyphicon-info-sign alert-info' style="display: none;"></i><p class="alert-info-message"></p>
						</span>
						<span class="input-group-addon modalfooterbtn">
							<button id="formSave" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="flb_workorder.save()">
			                   	<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
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

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/flb/flightlogbook_workorder.js"></script>