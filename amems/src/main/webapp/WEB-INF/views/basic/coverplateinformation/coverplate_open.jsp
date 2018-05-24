<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"
	language="java"%>
	<!-- 弹出框begin-->

	<div class="modal modal-new" id="alertModalView" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog" style="width: 75%">
			<div class="modal-content">
				<div class="modal-header modal-header-new">
					<h4 class="modal-title">
						<div class='pull-left'>
							<div class="font-size-14 " id="modalName"></div>
							<div class="font-size-12" id="modalEname"></div>
						</div>
						<div class='pull-right'>
							<button type="button" class="icon-remove modal-close"
								data-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class='clearfix'></div>
					</h4>
				</div>
				<div class="modal-body">
				<form id="form">
					<input type="hidden" id="id" name="id" value="" />
					<input type="hidden" id="dprtcode1" name="dprtcode1"/>
					<div class="col-xs-12 margin-top-8 ">
						<div class="input-group-border">
							<div
								class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label
									class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										<span style="color: red">*</span>机型
									</div>
									<div class="font-size-9">A/C Type</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" type="text" id="fjjx2" name="fjjx2" readonly/>
								</div>
							</div>
							<div
								class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label
									class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										<span style="color: red">*</span>盖板编号
									</div>
									<div class="font-size-9">Cover Plate No.</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control " id="gbbh" name="gbbh"
										maxlength="50" />
								</div>
							</div>
							<div
								class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label
									class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										盖板名称
									</div>
									<div class="font-size-9">Cover Plate Name</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control " id="gbmc" name="gbmc"
										maxlength="100" />
								</div>
							</div>
							<div class="clearfix"></div>
							<div
								class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label
									class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										所在区域位置
									</div>
									<div class="font-size-9">Zone Location</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control " id="szqywz"
										name="szqywz" maxlength="600" />
								</div>
							</div>
							<div
								class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label
									class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										开盖工时
									</div>
									<div class="font-size-9">Open Hours</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control " id="kggs" name="kggs"
										maxlength="15" />
								</div>
							</div>
							<div
								class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label
									class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										合盖工时
									</div>
									<div class="font-size-9">Close Hours</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control " id="hggs" name="hggs"
										maxlength="15" />
								</div>
							</div>
							<div class="clearfix"></div>
							<div
								class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label
									class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										区域
									</div>
									<div class="font-size-9">Zone</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class="form-control" id="qy_select_win" name="qy_select_win">
								</select>
									
								</div>
							</div>
							<div
								class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label
									class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										燃料盖板标识
									</div>
									<div class="font-size-9">Identification</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control " id="rlgbbs"
										name="rlgbbs" maxlength="100" />
								</div>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
					</form>
				</div>
				<div class="modal-footer">
	           		<div class="col-xs-12 padding-leftright-8" >
						<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							   <i class="glyphicon glyphicon-info-sign alert-info" style="display: none;"></i>
			                   <p class="alert-info-message"></p>
						</span>
		                    <span class="input-group-addon modalfooterbtn">
		                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="saveUpdate()" id="baocunButton">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
			                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
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
	<!-- 弹出框end -->
