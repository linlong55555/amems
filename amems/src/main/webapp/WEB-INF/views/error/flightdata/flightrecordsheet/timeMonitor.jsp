<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade" id="timeMonitorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg" style="width: 1350px;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0">
				<form id="form_timeMonitor">
			<div class="row">
				<div class="col-sm-12">
					<div class="panel panel-default">
						<div class="panel-heading col-xs-12 margin-bottom-10 padding-right-0" style="padding-top:2px!important; padding-bottom:10px!important;">
							<h3 class="panel-title padding-top-10">时控件监控设置 Set up Time Monitor</h3>
						</div>
						<div class="panel-body padding-bottom-0">
						
							<div class="row">
								<input id="hidden_timeMonitor" type="hidden">
								<input id="zjqdid_timeMonitor" type="hidden">
								<input id="bjlx_timeMonitor" type="hidden">
								<div class="col-sm-4 padding-left-0 padding-right-0 form-group">
									<label class="col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</label>
									<div class=" col-xs-8 padding-left-8 padding-right-0">
										<input id="jh_timeMonitor" class="form-control" type="text" disabled="disabled" name="jh_timeMonitor">
									</div>
								</div>
								<div class="col-sm-4 padding-left-0 padding-right-0 form-group">
									<label class="col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</label>
									<div class=" col-xs-8 padding-left-8 padding-right-0">
										<input id="xlh_timeMonitor" class="form-control" type="text" disabled="disabled" name="xlh_timeMonitor">
									</div>
								</div>
								<div class="col-sm-4 padding-left-0 padding-right-0 form-group">
									<label class="col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
									</label>
									<div class=" col-xs-8 padding-left-8 padding-right-0">
										<input id="zwmc_timeMonitor" class="form-control" type="text" disabled="disabled" name="zwmc_timeMonitor">
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
										<input id="ywmc_timeMonitor" class="form-control" type="text" disabled="disabled" name="ywmc_timeMonitor">
									</div>
								</div>
								<div class="col-sm-4 padding-left-0 padding-right-0 form-group">
									<label class="col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">控制类型</div>
										<div class="font-size-9 line-height-18">Control type</div>
									</label>
									<div class=" col-xs-8 padding-left-8 padding-right-0">
										<select class='form-control' id='kzlx_timeMonitor' disabled="disabled">
											<option value="1">时控件 Time</option>
											<option value="2">时寿件 Life</option>
										</select>
									</div>
								</div>
								<div class="col-sm-4 padding-left-0 padding-right-0 form-group">
									<label class="col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">位置</div>
										<div class="font-size-9 line-height-18">Location</div>
									</label>
									<div class=" col-xs-8 padding-left-8 padding-right-0">
										<select class='form-control' id='wz_timeMonitor' disabled="disabled">
											<option value="0">机身 Fuselage</option>
											<option value="1">1#左发 L/Engine</option>
											<option value="2">2#右发 R/Engine</option>
											<option value="5">外吊挂 E/S</option>
											<option value="4">搜索灯 Search</option>
											<option value="3">绞车 Winch</option>
										</select>
									</div>
								</div>
								<div class="col-sm-4 padding-left-0 padding-right-0 form-group">
									<label class="col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">TSN</div>
										<div class="font-size-9 line-height-18">TSN</div>
									</label>
									<div class=" col-xs-8 padding-left-8 padding-right-0">
										<input id="tsn_timeMonitor" class="form-control" type="text" name="tsn_timeMonitor" maxlength="10">
									</div>
								</div>
								<div class="col-sm-4 padding-left-0 padding-right-0 form-group margin-bottom-0">
									<label class="col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">TSO</div>
										<div class="font-size-9 line-height-18">TSO</div>
									</label>
									<div class=" col-xs-8 padding-left-8 padding-right-0">
										<input id="tso_timeMonitor" class="form-control" type="text" name="tso_timeMonitor" maxlength="10">
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
							<h3 class="panel-title padding-top-10">监控项选择（选择1~3项）Choice Monitoring items</h3>
						</div>
						<div class="panel-body padding-bottom-0">
						
							<div class="row">
								<div class="col-sm-1 padding-top-2 text-right  padding-right-0 padding-left-0">
								<label>日历：</label>
								</div>
								<div class="col-sm-10 padding-left-0">
								<label class="checkbox-inline" id="calendar_label">
								  <input type="checkbox" value="option1" name="item_timeMonitor" onclick="showTimeMonitorWell('date_rq_well_timeMonitor')"> 日历
								</label>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-1 padding-top-2 text-right  padding-right-0 padding-left-0">
								<label>飞行时间：</label>
								</div>
								<div class="col-sm-10 padding-left-0">
								<label class="checkbox-inline" id="fuselage_flight_time_label">
								  <input type="checkbox" value="option1" name="item_timeMonitor" onclick="showTimeMonitorWell('time_jsfx_well_timeMonitor')"> 机身飞行时间
								</label>
								<label class="checkbox-inline" id="search_light_time_label">
								  <input type="checkbox" value="option2" name="item_timeMonitor" onclick="showTimeMonitorWell('time_ssd_well_timeMonitor')"> 搜索灯时间
								</label>
								<label class="checkbox-inline" id="winch_time_label">
								  <input type="checkbox" value="option3" name="item_timeMonitor" onclick="showTimeMonitorWell('time_jc_well_timeMonitor')"> 绞车时间
								</label>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-1 padding-top-2 text-right  padding-right-0 padding-left-0">
								<label>循环：</label>
								</div>
								<div class="col-sm-10 padding-left-0">
								<label class="checkbox-inline" id="landing_gear_cycle_label">
								  <input type="checkbox" value="option1" name="item_timeMonitor" onclick="showTimeMonitorWell('loop_qlj_well_timeMonitor')"> 起落循环&nbsp;&nbsp;&nbsp;&nbsp;
								</label>
								<label class="checkbox-inline" id="winch_cycle_label">
								  <input type="checkbox" value="option2" name="item_timeMonitor" onclick="showTimeMonitorWell('loop_jc_well_timeMonitor')"> 绞车循环&nbsp;&nbsp;&nbsp;&nbsp;
								</label>
								<label class="checkbox-inline" id="ext_suspension_loop_label">
								  <input type="checkbox" value="option3" name="item_timeMonitor" onclick="showTimeMonitorWell('loop_wdg_well_timeMonitor')"> 外吊挂循环
								</label>
								<label class="checkbox-inline" id="N1_label">
								  <input type="checkbox" value="option3" name="item_timeMonitor" onclick="showTimeMonitorWell('loop_N1_well_timeMonitor')"> &nbsp;N1
								</label>
								<label class="checkbox-inline" id="N2_label">
								  <input type="checkbox" value="option3" name="item_timeMonitor" onclick="showTimeMonitorWell('loop_N2_well_timeMonitor')"> &nbsp;N2
								</label>
								<label class="checkbox-inline" id="N3_label">
								  <input type="checkbox" value="option3" name="item_timeMonitor" onclick="showTimeMonitorWell('loop_N3_well_timeMonitor')"> &nbsp;N3
								</label>
								<label class="checkbox-inline" id="N4_label">
								  <input type="checkbox" value="option3" name="item_timeMonitor" onclick="showTimeMonitorWell('loop_N4_well_timeMonitor')"> &nbsp;N4
								</label>
								<label class="checkbox-inline" id="N5_label">
								  <input type="checkbox" value="option3" name="item_timeMonitor" onclick="showTimeMonitorWell('loop_N5_well_timeMonitor')"> &nbsp;N5
								</label>
								<label class="checkbox-inline" id="N6_label">
								  <input type="checkbox" value="option3" name="item_timeMonitor" onclick="showTimeMonitorWell('loop_N6_well_timeMonitor')"> &nbsp;N6
								</label>
								<label class="checkbox-inline" id="special_first_label">
								  <input type="checkbox" value="option3" name="item_timeMonitor" onclick="showTimeMonitorWell('loop_ts1_well_timeMonitor')"> &nbsp;TS1
								</label>
								<label class="checkbox-inline" id="special_second_label">
								  <input type="checkbox" value="option3" name="item_timeMonitor" onclick="showTimeMonitorWell('loop_ts2_well_timeMonitor')"> &nbsp;TS2
								</label>
								</div>
							</div>
							<br/>
							<div class="row" id="items_timeMonitor">
								<div id="date_rq_well_timeMonitor" style="padding-left: 15px; display: none;" class="col-sm-4">
									<div class="well" id="calendar_div">
										<font><strong>监控条件：日历</strong></font>
										<input type="hidden" name="jklbh" value="calendar"></input>
										<input type="hidden" name="jkflbh" value="calendar"></input>
										<input type="hidden" name="pxh" value="1"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">日历限制:</div>
													<!-- <div class="font-size-9 line-height-18">Date limit:</div> -->
												</font>
												<div class=" col-xs-7 padding-left-8 padding-right-0">
													<form class="form-inline" role="form">
													    <div class="input-group">
															<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor"/>
															<div class="input-group-btn">
											                     <button type="button" class="btn btn-default btn-sm
											                     dropdown-toggle" data-toggle="dropdown" value="day" id="switchYearAndMonthBtn">日
											                         <span class="caret"></span>
											                     </button>
											                     <ul class="dropdown-menu pull-right">
											                     	 <li>
											                             <a href="####" onclick="switchYearAndMonth('day')">&nbsp;&nbsp;日</a>
											                         </li>
											                         <li>
											                             <a href="####" onclick="switchYearAndMonth('month')">&nbsp;&nbsp;月</a>
											                         </li>
											                         <li>
											                             <a href="####" onclick="switchYearAndMonth('year')">&nbsp;&nbsp;年</a>
											                         </li>
											                     </ul>
											                 </div><!-- /btn-group -->
													    </div>
													</form>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">起始日期:</div>
													<!-- <div class="font-size-9 line-height-18">Date start:</div> -->
												</font>
												<div class=" col-xs-7 padding-left-8 padding-right-0">
													<input class="form-control date-picker input-sm" data-date-format="yyyy-mm-dd" type="text" name="nowUsed_timeMonitor" id="calendar_nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">预拆日期:</div>
													<!-- <div class="font-size-9 line-height-18">Remove date:</div> -->
												</font>
												<div class=" col-xs-7 padding-left-8 padding-right-0">
													<input class="form-control date-picker input-sm" data-date-format="yyyy-mm-dd" type="text" name="expectedRemoval_timeMonitor" id="calendar_expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row"  style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，飞机日期:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor"></font>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div id="time_jsfx_well_timeMonitor" class="col-sm-4" style="display: none;">
									<div class="well"  style="padding-left: 15px; " id="fuselage_flight_time_div">
										<font><strong>监控条件：机身飞行时间</strong></font>
										<input type="hidden" name="jklbh" value="fuselage_flight_time"></input>
										<input type="hidden" name="jkflbh" value="flight_time"></input>
										<input type="hidden" name="pxh" value="1"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定时限:</div>
													<!-- <div class="font-size-9 line-height-18">Prescribed limit:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="timeLimit_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装机时，飞机时间:</div>
													<!-- <div class="font-size-9 line-height-18">Flight HRS.:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装机前，该部件已用时间:</div>
													<!-- <div class="font-size-9 line-height-18">Before Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="beforeUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件从装机后、进入系统前，已用时间:</div>
													<!-- <div class="font-size-9 line-height-18">After Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">预拆机身飞行时间:</div>
													<!-- <div class="font-size-9 line-height-18">Expected removal:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，飞机飞行时间:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor">0</font>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div id="time_ssd_well_timeMonitor" class="col-sm-4" style="display: none;">
									<div class="well"  style="padding-left: 15px; " id="search_light_time_div">
										<font><strong>监控条件：搜索灯时间</strong></font>
										<input type="hidden" name="jklbh" value="search_light_time"></input>
										<input type="hidden" name="jkflbh" value="flight_time"></input>
										<input type="hidden" name="pxh" value="2"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定时限:</div>
													<!-- <div class="font-size-9 line-height-18">Prescribed limit:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装机时，搜索灯时间:</div>
													<!-- <div class="font-size-9 line-height-18">Part Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装机前，该部件已用搜索灯时间:</div>
													<!-- <div class="font-size-9 line-height-18">Before Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该部件从装机后-->进入系统前，已用搜索灯时间:</div>
													<!-- <div class="font-size-9 line-height-18">After Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，搜索灯时间:</div>
													<!-- <div class="font-size-9 line-height-18">Expected removal:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，飞机搜索灯时间:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor">0</font>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div id="time_jc_well_timeMonitor" class="col-sm-4" style="display: none;">
									<div class="well"  style="padding-left: 15px; " id="winch_time_div">
										<font><strong>监控条件：绞车时间</strong></font>
										<input type="hidden" name="jklbh" value="winch_time"></input>
										<input type="hidden" name="jkflbh" value="flight_time"></input>
										<input type="hidden" name="pxh" value="3"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定时限:</div>
													<!-- <div class="font-size-9 line-height-18">Prescribed limit:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装机时，绞车时间:</div>
													<!-- <div class="font-size-9 line-height-18">Part Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装机前，该部件已用绞车时间:</div>
													<!-- <div class="font-size-9 line-height-18">Before Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="beforeUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该绞车从装机后-->进入系统前，已用绞车时间:</div>
													<!-- <div class="font-size-9 line-height-18">After Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，绞车时间:</div>
													<!-- <div class="font-size-9 line-height-18">Expected removal:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，飞机绞车时间:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor">0</font>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div id="loop_qlj_well_timeMonitor" class="col-sm-4" style="display: none;">
									<div class="well"  style="padding-left: 15px; " id="landing_gear_cycle_div">
										<font><strong>监控条件：起落循环</strong></font>
										<input type="hidden" name="jklbh" value="landing_gear_cycle"></input>
										<input type="hidden" name="jkflbh" value="loop"></input>
										<input type="hidden" name="pxh" value="1"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定时限:</div>
													<!-- <div class="font-size-9 line-height-18">Prescribed limit:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="timeLimit_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装机时，飞机起落循环:</div>
													<!-- <div class="font-size-9 line-height-18">Part Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装机前，该部件已用循环:</div>
													<!-- <div class="font-size-9 line-height-18">Before Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">装机后，进入系统前:</div>
													<!-- <div class="font-size-9 line-height-18">After Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，飞机起落循环:</div>
													<!-- <div class="font-size-9 line-height-18">Expected removal:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row"  style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，飞机起落循环:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor">0</font>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div id="loop_jc_well_timeMonitor" class="col-sm-4" style="display: none;">
									<div class="well"  style="padding-left: 15px;" id="winch_cycle_div">
										<font><strong>监控条件：绞车循环</strong></font>
										<input type="hidden" name="jklbh" value="winch_cycle"></input>
										<input type="hidden" name="jkflbh" value="loop"></input>
										<input type="hidden" name="pxh" value="2"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定循环:</div>
													<!-- <div class="font-size-9 line-height-18">Prescribed limit:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装机时，绞车循环:</div>
													<!-- <div class="font-size-9 line-height-18">Part Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装机前，该部件已用绞车循环:</div>
													<!-- <div class="font-size-9 line-height-18">Before Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该部件从装机后、进入系统前，已用绞车循环:</div>
													<!-- <div class="font-size-9 line-height-18">After Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，绞车循环:</div>
													<!-- <div class="font-size-9 line-height-18">Expected removal:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row"  style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，飞机绞车循环:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor">0</font>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								
								<div id="loop_wdg_well_timeMonitor" class="col-sm-4" style="display: none;">
									<div class="well"  style="padding-left: 15px;" id="ext_suspension_loop_div">
										<font><strong>监控条件：外吊挂循环</strong></font>
										<input type="hidden" name="jklbh" value="ext_suspension_loop"></input>
										<input type="hidden" name="jkflbh" value="loop"></input>
										<input type="hidden" name="pxh" value="3"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定循环:</div>
													<!-- <div class="font-size-9 line-height-18">Prescribed limit:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装机时，外吊挂循环:</div>
													<!-- <div class="font-size-9 line-height-18">Part Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装机前，该部件已用外吊挂循环:</div>
													<!-- <div class="font-size-9 line-height-18">Before Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该部件从装机后、进入系统前，已用外吊挂循环:</div>
													<!-- <div class="font-size-9 line-height-18">After Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，外吊挂循环:</div>
													<!-- <div class="font-size-9 line-height-18">Expected removal:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row"  style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，飞机外吊挂循环:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor">0</font>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div id="loop_N1_well_timeMonitor" class="col-sm-4" style="display: none;">
									<div class="well"  style="padding-left: 15px;" id="N1_div">
										<font><strong>监控条件：N1循环</strong></font>
										<input type="hidden" name="jklbh" value="N1"></input>
										<input type="hidden" name="jkflbh" value="loop"></input>
										<input type="hidden" name="pxh" value="5"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定循环:</div>
													<!-- <div class="font-size-9 line-height-18">Prescribed limit:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装上发动机时，发动机N1循环:</div>
													<!-- <div class="font-size-9 line-height-18">Part Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上发动机前，该部件已用N1循环:</div>
													<!-- <div class="font-size-9 line-height-18">Before Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该发动机从装机后、进入系统前，已用N1循环:</div>
													<!-- <div class="font-size-9 line-height-18">After Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，发动机N1循环:</div>
													<!-- <div class="font-size-9 line-height-18">Expected removal:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										
										<div class="row" style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，发动机N1循环:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor">0</font>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div id="loop_N2_well_timeMonitor" class="col-sm-4" style="display: none;">
									<div class="well"  style="padding-left: 15px;" id="N2_div">
										<font><strong>监控条件：N2循环</strong></font>
										<input type="hidden" name="jklbh" value="N2"></input>
										<input type="hidden" name="jkflbh" value="loop"></input>
										<input type="hidden" name="pxh" value="6"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定循环:</div>
													<!-- <div class="font-size-9 line-height-18">Prescribed limit:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装上发动机时，发动机N2循环:</div>
													<!-- <div class="font-size-9 line-height-18">Part Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上发动机前，该部件已用N2循环:</div>
													<!-- <div class="font-size-9 line-height-18">Before Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该发动机从装机后、进入系统前，已用N2循环:</div>
													<!-- <div class="font-size-9 line-height-18">After Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，发动机N2循环:</div>
													<!-- <div class="font-size-9 line-height-18">Expected removal:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，发动机N2循环:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor">0</font>
												</div>
											</div>
										</div>
										<div class="row form-group" >
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div id="loop_N3_well_timeMonitor" class="col-sm-4" style="display: none;">
									<div class="well"  style="padding-left: 15px;" id="N3_div">
										<font><strong>监控条件：N3循环</strong></font>
										<input type="hidden" name="jklbh" value="N3"></input>
										<input type="hidden" name="jkflbh" value="loop"></input>
										<input type="hidden" name="pxh" value="7"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定循环:</div>
													<!-- <div class="font-size-9 line-height-18">Prescribed limit:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装上发动机时，发动机N3循环:</div>
													<!-- <div class="font-size-9 line-height-18">Part Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上发动机前，该部件已用N3循环:</div>
													<!-- <div class="font-size-9 line-height-18">Before Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该发动机从装机后、进入系统前，已用N3循环:</div>
													<!-- <div class="font-size-9 line-height-18">After Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，发动机N3循环:</div>
													<!-- <div class="font-size-9 line-height-18">Expected removal:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，发动机N3循环:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor">0</font>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div id="loop_N4_well_timeMonitor" class="col-sm-4" style="display: none;">
									<div class="well"  style="padding-left: 15px;" id="N4_div">
										<font><strong>监控条件：N4循环</strong></font>
										<input type="hidden" name="jklbh" value="N4"></input>
										<input type="hidden" name="jkflbh" value="loop"></input>
										<input type="hidden" name="pxh" value="8"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定循环:</div>
													<!-- <div class="font-size-9 line-height-18">Prescribed limit:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装上发动机时，发动机N4循环:</div>
													<!-- <div class="font-size-9 line-height-18">Part Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上发动机前，该部件已用N4循环:</div>
													<!-- <div class="font-size-9 line-height-18">Before Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该发动机从装机后、进入系统前，已用N4循环:</div>
													<!-- <div class="font-size-9 line-height-18">After Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，发动机N4循环:</div>
													<!-- <div class="font-size-9 line-height-18">Expected removal:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row"  style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，发动机N4循环:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor">0</font>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div id="loop_N5_well_timeMonitor" class="col-sm-4" style="display: none;">
									<div class="well"  style="padding-left: 15px;" id="N5_div">
										<font><strong>监控条件：N5循环</strong></font>
										<input type="hidden" name="jklbh" value="N5"></input>
										<input type="hidden" name="jkflbh" value="loop"></input>
										<input type="hidden" name="pxh" value="9"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定循环:</div>
													<!-- <div class="font-size-9 line-height-18">Prescribed limit:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装上发动机时，发动机N5循环:</div>
													<!-- <div class="font-size-9 line-height-18">Part Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上发动机前，该部件已用N5循环:</div>
													<!-- <div class="font-size-9 line-height-18">Before Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该发动机从装机后、进入系统前，已用N5循环:</div>
													<!-- <div class="font-size-9 line-height-18">After Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，发动机N5循环:</div>
													<!-- <div class="font-size-9 line-height-18">Expected removal:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row"  style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，发动机N5循环:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor">0</font>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div id="loop_N6_well_timeMonitor" class="col-sm-4" style="display: none;">
									<div class="well"  style="padding-left: 15px;" id="N6_div">
										<font><strong>监控条件：N6循环</strong></font>
										<input type="hidden" name="jklbh" value="N6"></input>
										<input type="hidden" name="jkflbh" value="loop"></input>
										<input type="hidden" name="pxh" value="10"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定循环:</div>
													<!-- <div class="font-size-9 line-height-18">Prescribed limit:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装上发动机时，发动机N6循环:</div>
													<!-- <div class="font-size-9 line-height-18">Part Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上发动机前，该部件已用N6循环:</div>
													<!-- <div class="font-size-9 line-height-18">Before Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该发动机从装机后、进入系统前，已用N6循环:</div>
													<!-- <div class="font-size-9 line-height-18">After Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，发动机N6循环:</div>
													<!-- <div class="font-size-9 line-height-18">Expected removal:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row"  style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，发动机N6循环:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor">0</font>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div id="loop_ts1_well_timeMonitor" class="col-sm-4" style="display: none;">
									<div class="well"  style="padding-left: 15px;" id="special_first_div">
										<font><strong>监控条件：特殊循环1</strong></font>
										<input type="hidden" name="jklbh" value="special_first"></input>
										<input type="hidden" name="jkflbh" value="loop"></input>
										<input type="hidden" name="pxh" value="11"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定循环:</div>
													<!-- <div class="font-size-9 line-height-18">Prescribed limit:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上飞机时，TS1循环:</div>
													<!-- <div class="font-size-9 line-height-18">Part Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上飞机前，该部件已用TS1循环:</div>
													<!-- <div class="font-size-9 line-height-18">Before Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该发动机从装机后、进入系统前，已用TS1循环:</div>
													<!-- <div class="font-size-9 line-height-18">After Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">预拆该部件时，飞机TS1循环:</div>
													<!-- <div class="font-size-9 line-height-18">Expected removal:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row"  style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，飞机TS1循环:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor">0</font>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div id="loop_ts2_well_timeMonitor" class="col-sm-4" style="display: none;">
									<div class="well"  style="padding-left: 15px;" id="special_second_div">
										<font><strong>监控条件：特殊循环2</strong></font>
										<input type="hidden" name="jklbh" value="special_second"></input>
										<input type="hidden" name="jkflbh" value="loop"></input>
										<input type="hidden" name="pxh" value="12"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定循环:</div>
													<!-- <div class="font-size-9 line-height-18">Prescribed limit:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上飞机时，TS2循环:</div>
													<!-- <div class="font-size-9 line-height-18">Part Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上飞机前，该部件已用TS2循环：</div>
													<!-- <div class="font-size-9 line-height-18">Before Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该发动机从装机后、进入系统前，已用TS2循环:</div>
													<!-- <div class="font-size-9 line-height-18">After Has been used:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">预拆该部件时，飞机TS2循环:</div>
													<!-- <div class="font-size-9 line-height-18">Expected removal:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row"  style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，飞机TS2循环:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="initDate_timeMonitor">0</font>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-sm-12 padding-left-0 padding-right-0" name="remain_row">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">剩余:</div>
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<font name="remain_timeMonitor">0</font>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row" id="specialList_timeMonitor_div">
				<div class="col-sm-12">
					<div class="panel panel-default">
						<div class="panel-heading col-xs-12 margin-bottom-10 padding-right-0" style="padding-top:2px!important; padding-bottom:10px!important;">
							<h3 class="panel-title padding-top-10"> 特殊情况设置 Set up Special Circumstances</h3>
						</div>
						<div class="panel-body">
						
							<div class="row">
								<table id="" class="table-thin table-bordered table-striped table-hover text-center tableRe" style="width:98%; margin:0 auto;">
									<thead>
										<tr>
										<th style="width: 60px;"><div class="font-size-12 line-height-18" >序号</div><div class="font-size-9 line-height-18" >No.</div></th>
										<th style="width: 100px;"><div class="font-size-12 line-height-18" >编号</div><div class="font-size-9 line-height-18" >SC/N</div></th>
										<th style="width: 250px;"><div class="font-size-12 line-height-18" >描述</div><div class="font-size-9 line-height-18" >Desc</div></th>
										<th style="width: 120px;"><div class="font-size-12 line-height-18" >系数值</div><div class="font-size-9 line-height-18" >Ratio</div></th>
										</tr>
						          	</thead>
									<tbody id="specialList_timeMonitor">
									    <!------ 特殊情况设置列表展示 ------>
									</tbody>
								</table>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		</form>
				<div class="modal-footer padding-top-0">
				<button type="button" onclick="setTimeMonitor()"
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

<script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/flightrecordsheet/timeMonitor.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/loadingList/loadingList_timeMonitor.js"></script>