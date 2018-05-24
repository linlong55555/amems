<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"
	language="java"%>
<div class="modal fade" id="FeedBackModal" tabindex="-1" role="dialog"
	aria-labelledby="open_win_maintenance_project_modal" aria-hidden="true"
	data-backdrop='static' data-keyboard=false>
	<div class="modal-dialog" style="width: 35%;">
		<div class="modal-content">
			<!-- header放在这里 -->

			<div class="modal-header modal-header-new">
				<h4 class="modal-title">
					<div class='pull-left'>
						<div class="font-size-14 ">完成工作反馈</div>
						<div class="font-size-12">Complete Work</div>
					</div>
					<div class="pull-right">
						<button type="button" class="icon-remove modal-close"
							data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
			</div>

			<div class="modal-body padding-bottom-0 margin-top-0"
				id="alertModalUserBody">
				<div class="col-xs-12 margin-top-8">
					<div class="input-group-border">
						<div class="panel-body padding-top-0 padding-bottom-0">
							<div>
								<form id="form">
									<div
										class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
										<div
											class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
											<label
												class="col-xs-2 text-right padding-left-0 padding-right-0">
												<div class="font-size-12" id="chinaHead">来源编号</div>
												<div class="font-size-9" id="englishHead">Source No.</div>
											</label>
											<div
												class=" col-xs-10 padding-left-8 padding-right-0 form-group">
												<input type="text" class="form-control" id="lybh"
													name="lybh" readonly="readonly" />
											</div>
										</div>

										<div
											class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
											<label
												class="col-xs-2 text-right padding-left-0 padding-right-0">
												<div class="font-size-12">机型</div>
												<div class="font-size-9">A/C Type</div>
											</label>
											<div
												class=" col-xs-10 padding-left-8 padding-right-0 form-group">
												<input type="text" class="form-control " id="fjjx"
													name="fjjx" readonly="readonly" />
											</div>
										</div>

										<div
											class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
											<label
												class="col-xs-2 text-right padding-left-0 padding-right-0">
												<div class="font-size-12">待办工作</div>
												<div class="font-size-9">To Do Work</div>
											</label>
											<div
												class=" col-xs-10 padding-left-8 padding-right-0 form-group">
												<input type="text" class="form-control " id="dbgzlx"
													name="dbgzlx" readonly="readonly" />
											</div>
										</div>
										<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
											<label
												class="col-xs-2 text-right padding-left-0 padding-right-0">
												<div class="font-size-12">反馈人</div>
												<div class="font-size-9">Person</div>
											</label>
											<div
												class=" col-xs-10 padding-left-8 padding-right-0 form-group">
												<input type="text" class="form-control " id="blrid"
													name="blrid" readonly="readonly" />
											</div>
										</div>
										<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
											<label
												class="col-xs-2 text-right padding-left-0 padding-right-0">
												<div class="font-size-12">反馈时间</div>
												<div class="font-size-9">Time</div>
											</label>
											<div
												class=" col-xs-10 padding-left-8 padding-right-0 form-group">
												<input type="text" class="form-control " id="fksj"
													name="fksj" readonly="readonly" />
											</div>
										</div>

										<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
											<label
												class="col-xs-2 text-right padding-left-0 padding-right-0">
												<div class="font-size-12">
													<span id="vjsyy" style="color: red"></span>反馈说明
												</div>
												<div class="font-size-9">Description</div>
											</label>
											<div
												class=" col-xs-10 padding-left-8 padding-right-0 form-group">
												<textarea class="form-control" id="fkyj" name="fkyj"  rows="6" cols="55"
													 placeholder='长度最大为300' maxlength="300" readonly="readonly"></textarea>
											</div>
										</div>

									</div>
								</form>
							</div>
						</div>
					</div>

				</div>
				<div class="clearfix"></div>
			</div>

		</div>
	</div>
</div>
