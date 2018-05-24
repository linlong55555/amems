<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx }/static/js/thjw/component/monitor.js"></script>

	<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
		<div class="font-size-16 line-height-18">循环周期监控设置</div>
		<div>Monitor Setting</div>
	</div>
	
	<div id="monitor"></div>
	
	<div class="col-sm-12">
		<div class="panel-body">
			<div id="calendar_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：日历</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="11">日</option>
											<option value="12">月</option>
											<option value="13">年</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="fuselage_flight_time_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：机身飞行时间</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="20">H</option>
										</select>
								    </div>
								    
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="search_light_time_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：搜索灯时间</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="20">H</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="winch_time_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：绞车时间</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="20">H</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="landing_gear_cycle_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：起落循环</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="30">C</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="winch_cycle_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：绞车循环</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="30">C</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="ext_suspension_loop_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：外吊挂循环</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="30">C</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="search_light_cycle_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：搜索灯循环</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="30">C</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="N1_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：N1循环</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="30">C</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="N2_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：N2循环</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="30">C</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="N3_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：N3循环</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="30">C</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="N4_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：N4循环</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="30">C</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="N5_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：N5循环</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="30">C</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="N6_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：N6循环</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="30">C</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="special_first_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：特殊监控1</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="30">C</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="special_second_dlg" style="padding-left: 15px;display:none;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
				<div class="well">
					<font><strong>监控条件：特殊监控2</strong></font>
					<br/>
					<div class="row padding-top-10">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<font class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-36">周期值</div>
								<div class="font-size-9 line-height-18">Value</div>
							</font>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<form class="form-inline" role="form">
								    <div class="input-group">
										<input type="text" style="width: 60%" class="form-control" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
										<select class="form-control "  style="width: 35%" name="dw" >
											<option value="30">C</option>
										</select>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
