<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

	<div id="left_div_timeMonitor" class="col-sm-3" style="padding:0 0 10px 0;">
		<div class="row">
			 <div class="col-sm-12 margin-bottom-10 ">
			    <div class="input-group">
				  <input type="text" class="form-control " id="keyword_search_timeMonitor" placeholder="件号/序列号/中英文名称/ATA章节号/内部识别码/厂家件号"/>
			      <span class="input-group-btn">
			        <button class="btn btn-default  padding-top-1 padding-bottom-1 pull-right" type="button" onclick="getTimeMonitorList()">
			        	<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
					</button>
			      </span>
			    </div>
			 </div>
		</div>
		<div style="overflow: auto;height: 485px;">
			<div class="list-group" id="component_timeMonitor">
				<p id="component_timeMonitor_0" style="border:1px solid #ddd;border-bottom:0px;margin-bottom:0px;padding-top:5px;padding-bottom:5px;padding-left:15px;background:#ececec;font-weight:bold;border-top-left-radius:5px;border-top-right-radius:5px;">
				 	机身 &nbsp;&nbsp;&nbsp;&nbsp;<span class="badge" id="timeMonitor_js_count">0</span><!-- <i class="icon-angle-left" style="right:20px;position:absolute"></i> -->
				</p>
				<div id="component_timeMonitor_0_part"></div>
				<p id="component_timeMonitor_1" style="border:1px solid #ddd;border-bottom:0px;margin-bottom:0px;padding-top:5px;padding-bottom:5px;padding-left:15px;background:#ececec;font-weight:bold;">
					1#左发 <span class="badge" id="timeMonitor_zf_count">0</span><!-- <i class="icon-angle-left" style="right:20px;position:absolute"></i> -->
				</p>
				<div id="component_timeMonitor_1_part"></div>
				<p id="component_timeMonitor_2" style="border:1px solid #ddd;border-bottom:0px;margin-bottom:0px;padding-top:5px;padding-bottom:5px;padding-left:15px;background:#ececec;font-weight:bold;">
					2#右发 <span class="badge" id="timeMonitor_yf_count">0</span><!-- <i class="icon-angle-left" style="right:20px;position:absolute"></i> -->
				</p>
				<div id="component_timeMonitor_2_part"></div>
				<p id="component_timeMonitor_5" style="border:1px solid #ddd;border-bottom:0px;margin-bottom:0px;padding-top:5px;padding-bottom:5px;padding-left:15px;background:#ececec;font-weight:bold;">
					外吊挂 &nbsp;<span class="badge" id="timeMonitor_wdg_count">0</span><!-- <i class="icon-angle-left" style="right:20px;position:absolute"></i> -->
				</p>
				<div id="component_timeMonitor_5_part"></div>
				<p id="component_timeMonitor_4" style="border:1px solid #ddd;border-bottom:0px;margin-bottom:0px;padding-top:5px;padding-bottom:5px;padding-left:15px;background:#ececec;font-weight:bold;">
					搜索灯 &nbsp;<span class="badge" id="timeMonitor_ssd_count">0</span><!-- <i class="icon-angle-left" style="right:20px;position:absolute"></i> -->
				</p>
				<div id="component_timeMonitor_4_part"></div>
				<p id="component_timeMonitor_3" style="border:1px solid #ddd;border-bottom:0px;margin-bottom:0px;padding-top:5px;padding-bottom:5px;padding-left:15px;background:#ececec;font-weight:bold;">
					绞车 &nbsp;&nbsp;&nbsp;&nbsp;<span class="badge" id="timeMonitor_jc_count">0</span><!-- <i class="icon-angle-left" style="right:20px;position:absolute"></i> -->
				</p>
				<div id="component_timeMonitor_3_part"></div>
			</div>
		</div>
	</div>
	<div id="right_div_timeMonitor" class="col-sm-9">
		<form id="form_timeMonitor">
			<div class="row">
				<div class="col-sm-12 padding-right-0">
					<div class="panel panel-default">
						<div class="panel-heading col-xs-12 margin-bottom-10 padding-right-0" style="padding-top:2px!important; padding-bottom:10px!important;">
							<h3 class="panel-title padding-top-10"> 时控件监控设置 </h3>
						</div>
						<div class="panel-body padding-bottom-0">
						
							<div class="row">
								<input id="zjqdid_timeMonitor" type="hidden">
								<input id="bjlx_timeMonitor" type="hidden">
								<div class="col-xs-12 col-sm-6 col-lg-4 padding-left-0 padding-right-0 form-group">
									<label class="col-xs-3 col-sm-3 col-lg-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</label>
									<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
										<input id="jh_timeMonitor" class="form-control" type="text" disabled="disabled" name="jh_timeMonitor">
									</div>
								</div>
								<div class="col-xs-12 col-sm-6 col-lg-4 padding-left-0 padding-right-0 form-group">
									<label class="col-xs-3 col-sm-3 col-lg-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</label>
									<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
										<input id="xlh_timeMonitor" class="form-control" type="text" disabled="disabled" name="xlh_timeMonitor">
									</div>
								</div>
								<div class="col-xs-12 col-sm-6 col-lg-4 padding-left-0 padding-right-0 form-group">
									<label class="col-xs-3 col-sm-3 col-lg-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
									</label>
									<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
										<input id="zwmc_timeMonitor" class="form-control" type="text" disabled="disabled" name="zwmc_timeMonitor">
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-xs-12 col-sm-6 col-lg-4 padding-left-0 padding-right-0 form-group">
									<label class="col-xs-3 col-sm-3 col-lg-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div>
									</label>
									<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
										<input id="ywmc_timeMonitor" class="form-control" type="text" disabled="disabled" name="ywmc_timeMonitor">
									</div>
								</div>
								<div class="col-xs-12 col-sm-6 col-lg-4 padding-left-0 padding-right-0 form-group">
									<label class="col-xs-3 col-sm-3 col-lg-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">类型</div>
										<div class="font-size-9 line-height-18">Type</div>
									</label>
									<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
										<input id="kzlx_timeMonitor" class="form-control" type="text" disabled="disabled">
									</div>
								</div>
								<div class="col-xs-12 col-sm-6 col-lg-4 padding-left-0 padding-right-0 form-group">
									<label class="col-xs-3 col-sm-3 col-lg-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">位置</div>
										<div class="font-size-9 line-height-18">Location</div>
									</label>
									<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
										<input id="wz_timeMonitor" class="form-control" type="text" disabled="disabled">
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-xs-12 col-sm-6 col-lg-4  padding-left-0 padding-right-0 form-group">
									<label class="col-xs-3 col-sm-3 col-lg-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">TSN</div>
										<div class="font-size-9 line-height-18">TSN</div>
									</label>
									<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
										<input id="tsn_timeMonitor" class="form-control" type="text" name="tsn_timeMonitor" maxlength="10">
									</div>
								</div>
								<div class="col-xs-12 col-sm-6 col-lg-4 padding-left-0 padding-right-0 form-group margin-bottom-0">
									<label class="col-xs-3 col-sm-3 col-lg-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">TSO</div>
										<div class="font-size-9 line-height-18">TSO</div>
									</label>
									<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
										<input id="tso_timeMonitor" class="form-control" type="text" name="tso_timeMonitor" maxlength="10">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-lg-12  padding-right-0">
					<div class="panel panel-default">
						<div class="panel-heading col-xs-12 col-sm-12 col-lg-12 margin-bottom-10 padding-right-0" style="padding-top:2px!important; padding-bottom:10px!important;">
							<h3 class="panel-title padding-top-10"> 监控项选择（选择1~3项） </h3>
						</div>
						<div class="panel-body padding-bottom-0">
						
							<div class="row">
								<div class="col-xs-4 col-sm-2 col-lg-1 padding-top-2 text-right  padding-right-0 padding-left-0">
								<label>日历：</label>
								</div>
								<div class="col-xs-6 col-sm-10 col-lg-11 padding-left-0">
								<label class="checkbox-inline" id="calendar_label">
								  <input type="checkbox" value="option1" name="item_timeMonitor" onclick="showTimeMonitorWell('date_rq_well_timeMonitor')"> 日历
								</label>
								</div>
							</div>
							
							<div class="row">
								<div class="col-xs-4 col-sm-2 col-lg-1 padding-top-2 text-right  padding-right-0 padding-left-0">
								<label>飞行时间：</label>
								</div>
								<div class="col-xs-6 col-sm-10 col-lg-11 padding-left-0">
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
								<div class="col-xs-4 col-sm-2 col-lg-1 padding-top-2 text-right  padding-right-0 padding-left-0">
								<label>循环：</label>
								</div>
								<div class="col-xs-6 col-sm-10 col-lg-11 padding-left-0">
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
								<div id="date_rq_well_timeMonitor" style="padding-left: 15px; display: none;" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10">
									<div class="well" id="calendar_div">
										<font><strong>监控条件：日历</strong></font>
										<input type="hidden" name="jklbh" value="calendar"></input>
										<input type="hidden" name="jkflbh" value="calendar"></input>
										<input type="hidden" name="pxh" value="1"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">日历限制</div>
													<!-- <div class="font-size-9 line-height-18">Date Period</div> -->
												</font>
												<div class=" col-xs-7 padding-left-8 padding-right-0">
													<form class="form-inline" role="form">
													    <div class="input-group">
															<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor" maxlength="15"/>
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
													<div class="font-size-12 line-height-18">起始日期</div>
													<!-- <div class="font-size-9 line-height-18">Start Date</div> -->
												</font>
												<div class=" col-xs-7 padding-left-8 padding-right-0">
													<input class="form-control date-picker input-sm" data-date-format="yyyy-mm-dd" type="text" name="nowUsed_timeMonitor" id="calendar_nowUsed_timeMonitor"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">预拆日期</div>
													<!-- <div class="font-size-9 line-height-18">Remove Date</div> -->
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
								
								<div id="time_jsfx_well_timeMonitor" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10" style="display: none;">
									<div class="well"  style="padding-left: 15px; " id="fuselage_flight_time_div">
										<font><strong>监控条件：机身飞行时间</strong></font>
										<input type="hidden" name="jklbh" value="fuselage_flight_time"></input>
										<input type="hidden" name="jkflbh" value="flight_time"></input>
										<input type="hidden" name="pxh" value="1"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定时限</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="timeLimit_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装机时，飞机时间</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="nowUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装机前，该部件已用时间</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="beforeUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该部件从装机后-->进入系统前，已用时间</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">预拆机身飞行时间</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="expectedRemoval_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，飞机飞行时间</div>
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
								
								<div id="time_ssd_well_timeMonitor" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10" style="display: none;">
									<div class="well"  style="padding-left: 15px; " id="search_light_time_div">
										<font><strong>监控条件：搜索灯时间</strong></font>
										<input type="hidden" name="jklbh" value="search_light_time"></input>
										<input type="hidden" name="jkflbh" value="flight_time"></input>
										<input type="hidden" name="pxh" value="2"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定时限</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装机时，搜索灯时间</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装机前，该部件已用搜索灯时间</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该部件从装机后-->进入系统前，已用搜索灯时间</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，搜索灯时间</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，飞机搜索灯时间</div>
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
								
								<div id="time_jc_well_timeMonitor" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10" style="display: none;">
									<div class="well"  style="padding-left: 15px; " id="winch_time_div">
										<font><strong>监控条件：绞车时间</strong></font>
										<input type="hidden" name="jklbh" value="winch_time"></input>
										<input type="hidden" name="jkflbh" value="flight_time"></input>
										<input type="hidden" name="pxh" value="3"></input>
										<br/>
										<div class="row padding-top-10">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件规定时限</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装机时，绞车时间</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装机前，该部件已用绞车时间</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="beforeUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该绞车从装机后-->进入系统前，已用绞车时间</div>
												<!-- 	<div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，绞车时间</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="expectedRemoval_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">系统初始化时，飞机绞车时间</div>
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
								
								<div id="loop_qlj_well_timeMonitor" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10" style="display: none;">
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
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="timeLimit_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装机时，飞机起落循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text"  name="nowUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装机前，该部件已用循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">装机后-->进入系统前:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">预拆该部件时，飞机起落循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor" maxlength="15"/>
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
								
								<div id="loop_jc_well_timeMonitor" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10" style="display: none;">
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
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装机时，绞车循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装机前，该部件已用绞车循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该部件从装机后-->进入系统前，已用绞车循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，绞车循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor" maxlength="15"/>
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
								
								
								<div id="loop_wdg_well_timeMonitor" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10" style="display: none;">
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
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装机时，外吊挂循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装机前，该部件已用外吊挂循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该部件从装机后-->进入系统前，已用外吊挂循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，外吊挂循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor" maxlength="15"/>
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
								
								<div id="loop_N1_well_timeMonitor" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10" style="display: none;">
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
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装上发动机时，发动机N1循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上发动机前，该部件已用N1循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该发动机从装机后-->进入系统前，已用N1循环:</div>
												<!-- 	<div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，发动机N1循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor" maxlength="15"/>
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
								
								<div id="loop_N2_well_timeMonitor" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10" style="display: none;">
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
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装上发动机时，发动机N2循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上发动机前，该部件已用N2循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该发动机从装机后-->进入系统前，已用N2循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，发动机N2循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor" maxlength="15"/>
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
								
								<div id="loop_N3_well_timeMonitor" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10" style="display: none;">
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
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装上发动机时，发动机N3循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上发动机前，该部件已用N3循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该发动机从装机后-->进入系统前，已用N3循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，发动机N3循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor" maxlength="15"/>
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
								
								<div id="loop_N4_well_timeMonitor" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10" style="display: none;">
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
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装上发动机时，发动机N4循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上发动机前，该部件已用N4循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该发动机从装机后-->进入系统前，已用N4循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，发动机N4循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor" maxlength="15"/>
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
								
								<div id="loop_N5_well_timeMonitor" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10" style="display: none;">
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
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36"  name="tbDescription">该部件装上发动机时，发动机N5循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上发动机前，该部件已用N5循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该发动机从装机后-->进入系统前，已用N5循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，发动机N5循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor" maxlength="15"/>
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
											<div class="col-sm-12 padding-left-0 padding-right-0"  name="remain_row">
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
								
								<div id="loop_N6_well_timeMonitor" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10" style="display: none;">
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
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tbDescription">该部件装上发动机时，发动机N6循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;" name="tc_div">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上发动机前，该部件已用N6循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tvDescription">该发动机从装机后-->进入系统前，已用N6循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36" name="tyDescription">预拆该部件时，发动机N6循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor" maxlength="15"/>
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
											<div class="col-sm-12 padding-left-0 padding-right-0"  name="remain_row">
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
								
								<div id="loop_ts1_well_timeMonitor" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10" style="display: none;">
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
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上飞机时，TS1循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上飞机前，该部件已用TS1循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件从装机后-->进入系统前，已用TS1循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">预拆该部件时，飞机TS1循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor" maxlength="15"/>
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
								
								<div id="loop_ts2_well_timeMonitor" class="col-xs-12 col-sm-6 col-lg-4 margin-bottom-10" style="display: none;">
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
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="timeLimit_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上飞机时，TS2循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="nowUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件装上飞机前，该部件已用TS2循环：</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="beforeUsed_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">该部件从装机后-->进入系统前，已用TS2循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="between_timeMonitor" maxlength="15"/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
												<font class="col-xs-8 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-36">预拆该部件时，飞机TS2循环:</div>
													<!-- <div class="font-size-9 line-height-18">Menu Code:</div> -->
												</font>
												<div class=" col-xs-3 padding-left-8 padding-right-0">
													<input class="form-control input-sm" type="text" name="expectedRemoval_timeMonitor" maxlength="15"/>
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
				<div class="col-sm-12  padding-right-0">
					<div class="panel panel-default">
						<div class="panel-heading col-xs-12 margin-bottom-10 padding-right-0" style="padding-top:2px!important; padding-bottom:10px!important;">
							<h3 class="panel-title padding-top-10"> 特殊情况设置 </h3>
						</div>
						<div class="panel-body">
						
							<div class="row">
								<table id="" class="table-bordered table-striped text-center tableRe" style="width:98%; margin:0 auto;">
									<thead>
										<tr>
										<th style="width: 60px;"><div class="font-size-12 line-height-18" >No.</div><div class="font-size-9 line-height-18" >No.</div></th>
										<th style="width: 100px;"><div class="font-size-12 line-height-18" >编号</div><div class="font-size-9 line-height-18" >Code</div></th>
										<th style="width: 250px;"><div class="font-size-12 line-height-18" >描述</div><div class="font-size-9 line-height-18" >Desc</div></th>
										<th style="width: 120px;"><div class="font-size-12 line-height-18" >系数值</div><div class="font-size-9 line-height-18" >Value</div></th>
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
			
			<div class="row">
				<div class="col-sm-12 margin-bottom-10">
					<div class="text-right">
	                    	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="insertOrUpdateTimeMonitor()">
	                    		<div class="font-size-12">保存</div>
							<div class="font-size-9">Save</div>
						</button>
					</div>
				</div>
			</div>
		</form>
	</div>