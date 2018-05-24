<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade" id="fixedMonitorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg" style="width: 1350px;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<div class="panel-heading col-xs-12 margin-bottom-10 padding-right-0" style="padding-top:2px!important; padding-bottom:10px!important;">
								<h3 class="panel-title padding-top-10"> 部件信息 Part info</h3>
							</div>
							<div class="panel-body padding-bottom-0">
								<div class="row">
									<input id="zjqdid_fixedMonitor" type="hidden">
									<input id="bjlx_fixedMonitor" type="hidden">
									<input id="nbsbm_fixedMonitor" type="hidden">
									<input id="cj_fixedMonitor" type="hidden">
									<div class="col-sm-4 padding-left-0 padding-right-0 form-group">
										<label class="col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">件号</div>
											<div class="font-size-9 line-height-18">P/N</div>
										</label>
										<div class=" col-xs-8 padding-left-8 padding-right-0">
											<input id="jh_fixedMonitor" class="form-control" type="text" disabled="disabled" name="jh_fixedMonitor">
										</div>
									</div>
									<div class="col-sm-4 padding-left-0 padding-right-0 form-group">
										<label class="col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">序列号</div>
											<div class="font-size-9 line-height-18">S/N</div>
										</label>
										<div class=" col-xs-8 padding-left-8 padding-right-0">
											<input id="xlh_fixedMonitor" class="form-control" type="text" disabled="disabled" name="xlh_fixedMonitor">
										</div>
									</div>
									<div class="col-sm-4 padding-left-0 padding-right-0 form-group">
										<label class="col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">中文名称</div>
											<div class="font-size-9 line-height-18">CH.Name</div>
										</label>
										<div class=" col-xs-8 padding-left-8 padding-right-0">
											<input id="zwmc_fixedMonitor" class="form-control" type="text" disabled="disabled" name="zwmc_fixedMonitor">
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-4 padding-left-0 padding-right-0 form-group">
										<label class="col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">英文名称</div>
											<div class="font-size-9 line-height-18">F.Name</div>
										</label>
										<div class=" col-xs-8 padding-left-8 padding-right-0">
											<input id="ywmc_fixedMonitor" class="form-control" type="text" disabled="disabled" name="ywmc_fixedMonitor">
										</div>
									</div>
									<div class="col-sm-4 padding-left-0 padding-right-0 ">
										<label class="col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">位置</div>
											<div class="font-size-9 line-height-18">Location</div>
										</label>
										<div class=" col-xs-8 padding-left-8 padding-right-0">
											<select class='form-control' id='wz_fixedMonitor' disabled="disabled">
												<option value="0">机身 Fuselage</option>
												<option value="1">1#左发 L/Engine</option>
												<option value="2">2#右发 R/Engine</option>
												<option value="5">外吊挂 E/S</option>
												<option value="4">搜索灯 Search</option>
												<option value="3">绞车 Winch</option>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<div class="panel-heading col-xs-12 margin-bottom-10 padding-right-0" style="padding-top:2px!important; padding-bottom:10px!important;">
								<h3 class="panel-title padding-top-10"> 定检监控信息 P/I Monitor info</h3>
							</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-md-6 padding-10">
										<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="showfixedMonitorModalFlight()">
											<div class="font-size-12">新增</div>
											<div class="font-size-9">Add</div>
										</button>
										&nbsp;&nbsp;
										<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="showUnmatchModalFlight()">
											<div class="font-size-12">未匹配定检项目</div>
											<div class="font-size-9">No matching items</div>
										</button>
									</div>
								</div>
								<div class="row" style="overflow: auto;height: 260px;">
									<div  class="col-xs-12 text-center margin-bottom-5 padding-left-10 padding-right-10 padding-top-10">
										<table class="table table-thin table-bordered table-striped table-hover text-center tableRe table-set">
											<thead>
												<tr>
												<th style="width: 70px;"><div class="font-size-12 line-height-18" >操作</div><div class="font-size-9 line-height-18" >Operation</div></th>
												<th style="width: 80px;"><div class="font-size-12 line-height-18" >定检编号</div><div class="font-size-9 line-height-18" >P/I No.</div></th>
												<th style="width: 120px;"><div class="font-size-12 line-height-18" >定检名称</div><div class="font-size-9 line-height-18" >P/I Name</div></th>
												<th style="width: 60px;"><div class="font-size-12 line-height-18" >版本</div><div class="font-size-9 line-height-18" >Version</div></th>
												<th style="width: 450px;"><div class="font-size-12 line-height-18" >修改后首次定检计划执行时间</div><div class="font-size-9 line-height-18" >First Execute Time After Modify</div></th>
												</tr>
								          </thead>
											<tbody id="fixedMonitorList">
											    <!------ 定检监控信息列表展示 ------>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer padding-top-0">
				<input type="hidden" id="hidden_fixedMonitor">
				<input type="hidden" id="fjzch_search">
				<button type="button" onclick="setFixedMonitor()"
					class="btn btn-primary padding-top-1 padding-bottom-1">
					<div class="font-size-12">确定</div>
					<div class="font-size-9">Confirm</div>
				</button>
				<button type="button"
					class="btn btn-primary padding-top-1 padding-bottom-1"
					data-dismiss="modal">
					<div class="font-size-12">关闭</div>
					<div class="font-size-9">Close</div>
				</button>
			</div>
		</div>
		</div>
	</div>
</div>


	<!-------维护定检项目Start-------->
	<div class="modal fade" id="fixedMonitor_remoteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg"  style="width: 1000px;margin-top:80px;" id="fixedMonitor_width">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
					</button>
					<h4>设置定检项目 Set up P/I</h4>
					<hr class="margin-top-0" />
				
					<form>
						<div class="row">
							<input type="hidden" id="jdxmid_hidden_fixedMonitor" />
							<div class="col-sm-3 padding-left-0 padding-right-0 form-group">
								<label class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">机型</div>
									<div class="font-size-9 line-height-18">Model</div>
								</label>
								<div class=" col-xs-8 padding-left-8 padding-right-0">
									<input id="fjjx_fixedMonitor" class="form-control" type="text" disabled="disabled" name="fjjx_fixedMonitor">
								</div>
							</div>
							<div class="col-sm-3 padding-left-0 padding-right-0 form-group">
								<label class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">飞机注册号</div>
									<div class="font-size-9 line-height-18">A/C REG</div>
								</label>
								<div class=" col-xs-8 padding-left-8 padding-right-0">
									<input id="fjzch_fixedMonitor" class="form-control" type="text" disabled="disabled" name="fjzch_fixedMonitor">
								</div>
							</div>
							<div class="col-sm-3 padding-left-0 padding-right-0 form-group">
								<label class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-18">P/N</div>
								</label>
								<div class=" col-xs-8 padding-left-8 padding-right-0">
									<input id="modal_jh_fixedMonitor" class="form-control" type="text" disabled="disabled" name="modal_jh_fixedMonitor">
								</div>
							</div>
							<div class="col-sm-3 padding-left-0 padding-right-0 form-group">
								<label class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">S/N</div>
								</label>
								<div class=" col-xs-8 padding-left-8 padding-right-0">
									<input id="modal_xlh_fixedMonitor" class="form-control" type="text" disabled="disabled" name="modal_xlh_fixedMonitor">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-3 padding-left-0 padding-right-0 form-group">
								<label class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">位置</div>
									<div class="font-size-9 line-height-18">Location</div>
								</label>
								<div class=" col-xs-8 padding-left-8 padding-right-0">
									<select class='form-control' id='modal_wz_fixedMonitor' disabled="disabled">
										<option value="0">机身 Fuselage</option>
										<option value="1">1#左发 L/Engine</option>
										<option value="2">2#右发 R/Engine</option>
										<option value="5">外吊挂 E/S</option>
										<option value="4">搜索灯 Search</option>
										<option value="3">绞车 Winch</option>
									</select>
								</div>
							</div>
							<div class="col-sm-3 padding-left-0 padding-right-0 form-group">
								<label class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">维修方案</div>
									<div class="font-size-9 line-height-18">Maintenance</div>
								</label>
								<div class=" col-xs-8 padding-left-8 padding-right-0">
									<input id="wxfamc_fixedMonitor" class="form-control" type="text" disabled="disabled">
									<input id="wxfabh_fixedMonitor" type="hidden">
								</div>
							</div>
							<div class="col-sm-3 padding-left-0 padding-right-0 form-group">
								<label class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">定检编号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</label>
								<div class=" col-xs-8 padding-left-8 padding-right-0">
									<input id="djbh_fixedMonitor" class="form-control" type="text" disabled="disabled" name="djbh_fixedMonitor">
								</div>
							</div>
							<div class="col-sm-3 padding-left-0 padding-right-0 form-group">
								<label class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">定检名称</div>
									<div class="font-size-9 line-height-18">Name</div>
								</label>
								<div class=" col-xs-8 padding-left-8 padding-right-0">
									<input id="djmc_fixedMonitor" class="form-control" type="text" disabled="disabled" name="djmc_fixedMonitor">
								</div>
							</div>
						</div>
						<br/>
						<div class="row">
							<div class="col-sm-5 padding-left-0" id="fixedMonitor_col_5">
								<div id="modal_left_fixedMonitor">
									<div class="row">
										 <div class="col-sm-11 margin-bottom-10 col-sm-offset-1">
										    <div class="input-group">
											  <input type="text" class="form-control " id="keyword_djxm_fixedMonitor" placeholder="定检编号/名称/说明"/>
										      <span class="input-group-btn">
										        <button class="btn btn-default  padding-top-1 padding-bottom-1 pull-right" type="button" onclick="searchDjxm()">
										        	<div class="font-size-12">搜索</div>
													<div class="font-size-9">Search</div>
												</button>
										      </span>
										    </div>
										 </div>
									</div>
									<div class="col-xs-11 padding-left-8 padding-right-0 col-sm-offset-1"  style="height: 300px;overflow: auto;">
										<table class="table table-thin table-bordered table-striped table-hover text-center table-set">
											<thead>
												<tr>
													<th width="10%">
														<div class="font-size-12 line-height-18" >选择</div>
														<div class="font-size-9 line-height-18" >Choice</div>
													</th>
													<th width="20%">
														<div class="important">
															<div class="font-size-12 line-height-18" >定检编号 </div>
															<div class="font-size-9 line-height-18" >P/I No.</div>
														</div>
													</th>
													<th width="22%">
														<div class="important">
															<div class="font-size-12 line-height-18" >定检名称</div>
															<div class="font-size-9 line-height-18" >P/I Name</div>
														</div>
													</th>
													<th width="48%">
														<div class="important">
															<div class="font-size-12 line-height-18" >说明</div>
															<div class="font-size-9 line-height-18" >Desc</div>
														</div>
													</th>
												</tr> 
							         		 </thead>
											<tbody id="fixedCheckedItem_fixedMonitor">
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="col-sm-7 padding-left-0" id="fixedMonitor_col_7">
								<form id="fixedMonitorForm">
									<div id="modal_right_fixedMonitor">
										<h4 style="margin-bottom: 3px;">设置监控项 Set up Monitor</h4>
										<small class="text-muted">剩余值仅供参考，数据以生产监控界面为准。</small>
										<hr class="margin-top-0" />
										<div class="col-xs-12 padding-left-0">
											<table class="table table-thin table-hover text-center">
												<thead>
													<tr>
													<th style="min-width: 80px;"><div class="font-size-12 line-height-18" >监控项</div><div class="font-size-9 line-height-18" >Monitor Item</div></th>
													<th style="min-width: 70px;"><div class="font-size-12 line-height-18" >周期</div><div class="font-size-9 line-height-18" >Cycle</div></th>
													<th style="min-width: 170px;"><div class="font-size-12 line-height-18" >修改后首次定检计划执行时间</div><div class="font-size-9 line-height-18" >First Execute Time After Modify</div></th>
													<th style="min-width: 150px;"><div class="font-size-12 line-height-18" >该部件装上前，该部件已用</div><div class="font-size-9 line-height-18" >Assembly before used</div></th>
													<th style="min-width: 130px;"><div class="font-size-12 line-height-18" >装机后,进入系统前</div><div class="font-size-9 line-height-18" >Assembly after used</div></th>
													<th style="min-width: 100px;"><div class="font-size-12 line-height-18" >当前飞机数据</div><div class="font-size-9 line-height-18" >Init Value</div></th>
													<th style="min-width: 80px;"><div class="font-size-12 line-height-18" >剩余</div><div class="font-size-9 line-height-18" >Surplus</div></th>
													</tr> 
								         		 </thead>
												<tbody id="monitorItem_fixedMonitor">
												</tbody>
											</table>
										</div>
									</div>
								</form>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer padding-top-0">
					<button type="button" onclick="saveFixedMonitorFlight()" id="save_btn_fixedMonitor"
						class="btn btn-primary padding-top-1 padding-bottom-1">
						<div class="font-size-12">确定</div>
						<div class="font-size-9">Confirm</div>
					</button>
					<button type="button"
						class="btn btn-primary padding-top-1 padding-bottom-1"
						data-dismiss="modal">
						<div class="font-size-12">关闭</div>
						<div class="font-size-9">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-------维护定检项目 End-------->
	
	<!-------删除消息框 Start-------->
	<div class="modal fade" id="deleteFixedMonitorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="deleteFixedMonitorModal_body">
					
				</div>
				<div class="modal-footer">
					<input type="hidden" id="hidden_delete_fixedMonitor"/>
					<input type="hidden" id="hidden_delete_fixedMonitor_rn"/>
					<button type="button" class="btn btn-danger padding-top-1 padding-bottom-1" data-dismiss="modal" onclick="deleteFixedMonitorFlight()">
						<div class="font-size-12">确定</div>
						<div class="font-size-9">Close</div>
						<input type="hidden" id="delete_self_hid"/>
					</button>
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
						<div class="font-size-12">关闭</div>
						<div class="font-size-9">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-------删除消息框 Start-------->
	
	
	
	<!-------未匹配的定检项目 Start-------->
	<div class="modal fade" id="unmathchModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="unmathchModal_body">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
					</button>
					<h4>未匹配的定检项目 No matching items</h4>
					<hr class="margin-top-0" />
					<table class="table table-thin table-bordered table-striped table-hover text-center table-set">
							<thead>
							<tr>
							<th style="width: 5%"><div class="font-size-12 line-height-18" >序号</div><div class="font-size-9 line-height-18" >No. </div></th>
							<th style="width: 15%"><div class="font-size-12 line-height-18" >定检编号</div><div class="font-size-9 line-height-18" >P/I No.</div></th>
							<th style="width: 15%"><div class="font-size-12 line-height-18" >定检名称</div><div class="font-size-9 line-height-18" >P/I Name</div></th>
							<th style="width: 65%"><div class="font-size-12 line-height-18" >说明</div><div class="font-size-9 line-height-18" >Desc</div></th>
							</tr> 
			         		 </thead>
							<tbody id="unmatchList">
							</tbody>
					</table>
				</div>
				<div class="modal-footer padding-top-0">
					<input type="hidden" id="hidden_delete_fixedMonitor"/>
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
						<div class="font-size-12">关闭</div>
						<div class="font-size-9">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-------未匹配的定检项目 Start-------->

<script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/flightrecordsheet/fixedMonitor.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/loadingList/loadingList_fixedMonitor.js"></script>