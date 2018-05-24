<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/customer_search.js"></script>
<style type="text/css">
.bootstrap-tagsinput {
  width: 100% !important;
}
</style>
<div class="modal fade in modal-new" id="AddalertWorkModal" tabindex="-1" role="dialog" aria-labelledby="open_win_evaluation_modal" aria-hidden="true"
	data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width:50%;">
		<div class="modal-content">
			
				<div class="modal-header modal-header-new">
					<h4 class="modal-title">
						<div class="pull-left" id="headChina">
							<div class="font-size-14 " id="modalName">新增岗位要求</div>
							<div class="font-size-12" id="modalEname"></div>
						</div>
						<div class="pull-right">
							<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class="clearfix"></div>
					</h4>
				</div>

			<div class="clearfix"></div>
			<div class="modal-body">
				<div class="col-xs-12">
					
						<form id="form">
						<div class="panel panel-default padding-right-0 margin-top-8" >
							<div class="panel-body padding-bottom-0 padding-left-8 padding-right-8  padding-top-0">
								<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0">

									<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group">
										<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-14 line-height-18"><span class="identifying" style="color: red">*</span>岗位要求</div>
											<div class="font-size-9 line-height-18">WorkRequire</div>
										</label>
										<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0 ">
											<textarea class="form-control" id="gwxq_add" maxlength="600" ></textarea>
										</div>
									</div>
									
									<div class="clearfix"></div>
									
									<div class="viewfix col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" style="display: none">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">状态</div>
											<div class="font-size-9 line-height-18">Status</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0" >
												<input name="isFx_add" type="radio" value="0" disabled="disabled" />无效
												<input name="isFx_add" type="radio" value="1" checked="checked" disabled="disabled" />有效
										
										</div>
									</div>
									 <div class="clearfix"></div>
									<div class="viewfix col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" style="display: none">
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">维护人</div>
											<div class="font-size-9 line-height-18">Maintainer</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input type="text" id="whr_add" class="form-control" />
										</div>
									</div>
								    
								    <div class="clearfix"></div>
								    
								   <div class="viewfix col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" style="display: none">
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">维护部门</div>
											<div class="font-size-9 line-height-18">Maintainer</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input type="text" id="whbm_add" class="form-control" />
										</div>
									</div>
									
									<!--
									
									<div class="clearfix"></div>
								    
									<div class="viewfix col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">维护时间</div>
											<div class="font-size-9 line-height-18">Time</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input class="form-control" id="whsj_add"  type="text" />
										</div>
									</div>
									
									
									  -->
							
								
								</div>
							</div>
						
							<div class="clearfix"></div>
							</div> 
						</form>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="modal-footer">
           		<div class="col-xs-12 padding-leftright-8">
					<div class="input-group">
					<span class="input-group-addon modalfootertip">
						   <i class="glyphicon glyphicon-info-sign alert-info" style="display: none;"></i>
		                   <p class="alert-info-message" title=""></p>
					</span>
	                    <span class="input-group-addon modalfooterbtn">
	                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="saveWorkRequires();">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
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

<div class="modal fade in modal-new" id="EditalertWorkModal" tabindex="-1" role="dialog" aria-labelledby="open_win_evaluation_modal" aria-hidden="true"
	data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width:50%;">
		<div class="modal-content">
			
				<div class="modal-header modal-header-new">
					<h4 class="modal-title">
						<div class="pull-left" id="headChina">
							<div class="font-size-14 " id="modalName">修改岗位要求</div>
							<div class="font-size-12" id="modalEname"></div>
						</div>
						<div class="pull-right">
							<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class="clearfix"></div>
					</h4>
				</div>

			<div class="clearfix"></div>
			<div class="modal-body">
				<div class="col-xs-12">
					
						<form id="form">
						<div class="panel panel-default padding-right-0 margin-top-8" >
							<div class="panel-body padding-bottom-0 padding-left-8 padding-right-8  padding-top-0">
								<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0">

									<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group">
										<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-14 line-height-18"><span class="identifying" style="color: red">*</span>岗位要求</div>
											<div class="font-size-9 line-height-18">WorkRequire</div>
										</label>
										<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0 ">
											<textarea class="form-control" id="gwyq_update"  maxlength="600" ></textarea>
											<input type="hidden" id="id_update" >
											<input type="hidden" id="mainid_update" >
											<input type="hidden" id="xc_update">
										</div>
									</div>
									
									<div class="clearfix"></div>
									
									<div class="viewfix col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" style="display: none">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">状态</div>
											<div class="font-size-9 line-height-18">Status</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0" >
												<input name="isFx_update" type="radio" value="0" disabled="disabled" />无效
												<input name="isFx_update" type="radio" value="1" checked="checked" disabled="disabled" />有效
										
										</div>
									</div>
									 <div class="clearfix"></div>
									<div class="viewfix col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" style="display: none">
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">维护人</div>
											<div class="font-size-9 line-height-18">Maintainer</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input type="text" id="whr_update" class="form-control" />
										</div>
									</div>
								    
								    <div class="clearfix"></div>
								    
								   <div class="viewfix col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" style="display: none">
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">维护部门</div>
											<div class="font-size-9 line-height-18">Maintainer</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input type="text" id="whbm_update" class="form-control" />
										</div>
									</div>
									
									<!--
									
												
									<div class="clearfix"></div>
								    
									<div class="viewfix col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">维护时间</div>
											<div class="font-size-9 line-height-18">Time</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input class="form-control" id="whsj_update"  type="text" />
										</div>
									</div>
									
									
									  -->
						
								
								</div>
							</div>
						
							<div class="clearfix"></div>
							</div> 
						</form>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="modal-footer">
           		<div class="col-xs-12 padding-leftright-8">
					<div class="input-group">
					<span class="input-group-addon modalfootertip">
						   <i class="glyphicon glyphicon-info-sign alert-info" style="display: none;"></i>
		                   <p class="alert-info-message" title=""></p>
					</span>
	                    <span class="input-group-addon modalfooterbtn">
	                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="editSaveWorkRequires();">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
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







