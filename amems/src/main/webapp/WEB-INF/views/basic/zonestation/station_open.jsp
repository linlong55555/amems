<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"
	language="java"%>
<!-- 添加或者弹出框 -->
<div class="modal fade in" id="AddEditAlertModal" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
	data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style='width: 50%;'>
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
			
						<div  class="modal-body padding-bottom-0 margin-top-0">
				<div class=" col-xs-12 margin-top-8 ">
					<div class="input-group-border">
					<div class="panel-body padding-top-0 padding-bottom-0">
					
						<div>
						<form id="form">

							<div
								class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label
									class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">机型</div>
									<div class="font-size-9">A/C Type</div>
								</label>
								<div class="col-sm-10 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control " id="fjjx2"
										name="fjjx2" maxlength="50" readonly="readonly" /> 
								</div>
							</div>

							<div
								class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label
									class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										<span style="color: red" id="hideOrShow">*</span>飞机站位
									</div>
									<div class="font-size-9">Station</div>
								</label>
								<div class="col-sm-10 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control " id="zone2"
										name="zone2" maxlength="50" />
								</div>
							</div>
							<div
								class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label
									class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">描述</div>
									<div class="font-size-9">Description</div>
								</label>
								<div class="col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="form-control" id="ms2" name="ms2" rows="3" cols="34"
										maxlength="100"></textarea>
								</div>
								
							</div>
						</form>
						</div>
						</div>


						<div class="clearfix"></div>
					</div>
				</div>
			</div>
<div class="clearfix"></div>

	<div class="modal-footer">
				<div class="col-xs-12 padding-leftright-8">
					<div class="input-group">
						<span class="input-group-addon modalfootertip"> <i
							class='glyphicon glyphicon-info-sign alert-info'></i>
							<p class="alert-info-message"></p>
						</span> <span class="input-group-addon modalfooterbtn">
							<button type="button"
								class="btn btn-primary padding-top-1 padding-bottom-1"
								onclick="saveUpdate()" name="" id="commitButton">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
							<button type="button"
								class="btn btn-primary padding-top-1 padding-bottom-1"
								data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
						</span>
					</div>
					<!-- /input-group -->
				</div>
				<div class="clearfix"></div>

			</div>

		
		</div>
	</div>
	</div>
	<!--  添加或者弹出框结束-->