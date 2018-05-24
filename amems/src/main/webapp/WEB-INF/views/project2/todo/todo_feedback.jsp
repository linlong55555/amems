<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"
	language="java"%>
<div class="modal fade modal-new" id="FeedBackModal" tabindex="-1" role="dialog"
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
												<div class="font-size-12" id="chinaHead">来源</div>
												<div class="font-size-9" id="englishHead">Source</div>
											</label>
											<div
												class=" col-xs-10 padding-left-8 padding-right-0 form-group">
												<input type="text" class="form-control" id="lybh2"
													name="lybh2" readonly="readonly" />
												<input type="hidden" class="form-control" id="toDoId"
													name="toDoId" readonly="readonly" />
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
												<input type="text" class="form-control " id="fjjx2"
													name="fjjx2" readonly="readonly" />
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
												<input type="text" class="form-control " id="dbgzlxName2"
													name="dbgzlxName2" readonly="readonly" />
											</div>
										</div>



										<div
											class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
											<label
												class="col-xs-2 text-right padding-left-0 padding-right-0">
												<div class="font-size-12">
													<span id="vjsyy" style="color: red"></span>反馈说明
												</div>
												<div class="font-size-9">Description</div>
											</label>
											<div
												class=" col-xs-10 padding-left-8 padding-right-0 form-group">
												<textarea class="form-control" id="fkyj2" name="fkyj2"  rows="6" cols="55"
													 placeholder='长度最大为300' maxlength="300"></textarea>
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

			<div class="modal-footer ">
				<div class="col-xs-12 padding-leftright-8">
					<div class="input-group">
						<span class="input-group-addon modalfootertip"> <i
							class='glyphicon glyphicon-info-sign alert-info'></i>
							<p class="alert-info-message"></p>
						</span> <span class="input-group-addon modalfooterbtn">
							<button type="button" id="save" onclick="saveFeedBack();"
								class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button type="button" data-dismiss="modal"
								class="btn btn-primary padding-top-1 padding-bottom-1 margin-right-5">
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
