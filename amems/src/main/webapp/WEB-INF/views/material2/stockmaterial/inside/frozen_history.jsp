<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="alertModalFrozen" tabindex="-1" role="dialog"  aria-labelledby="alertModalHb" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width:60%">
		<div class="modal-content">
			<div class="modal-header modal-header-new">
				<h4 class="modal-title">
	               	<div class="pull-left">
	                   	<div class="font-size-12 ">冻结履历信息</div>
						<div class="font-size-9 ">Frozen Info</div>
					</div>
				  	<div class="pull-right">
		  		  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
		  		  	</div>
				  	<div class="clearfix"></div>
	             </h4>
		    </div>
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
				<!-- <div class="panel panel-primary margin-top-15">
					<div class="panel-body padding-top-0 padding-bottom-0"> -->
				<div class="col-xs-12 margin-top-8">
				<div class="input-group-border margin-top-15" style="padding-bottom: 5px;">
						<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">件号</div>
								<div class="font-size-9">Bjh</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
							<input type="text" class="form-control" id="bjh_modal" disabled="disabled" maxlength="50"/>
							</div>
						</div>
						<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">名称</div>
								<div class="font-size-9">Name</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
								<input type="text" class="form-control" disabled="disabled" id="mc_modal" maxlength="100"/>
							</div>
						</div>
						<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">批次号</div>
								<div class="font-size-9">Pch</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
								<input type="text" class="form-control" disabled="disabled" id="pch_modal" maxlength="100"/>
							</div>
						</div>
						<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">序列号</div>
								<div class="font-size-9">Sequence</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
								<input type="text" class="form-control" disabled="disabled" id="sequence_modal" maxlength="100"/>
							</div>
						</div>
							<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">数量</div>
								<div class="font-size-9">Num</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
								<input type="text" class="form-control" disabled="disabled" id="num_modal" maxlength="100"/>
							</div>
						</div>					
						<div class="clearfix"></div>					
					</div>
					</div>
					<div class="clearfix"></div>	     
						<div  class="col-xs-12 margin-top-10">
							<table class="table-set table table-thin table-bordered table-striped table-hover ">
								<thead>
									<tr>
										<th class="colwidth-10">
												<div class="font-size-12 line-height-18">业务类型</div>
												<div class="font-size-9 line-height-16">Business Type</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">业务编号</div>
											<div class="font-size-9 line-height-16">Business Num</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">冻结数量</div>
											<div class="font-size-9 line-height-16">Frozen Num.</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">操作时间</div>
											<div class="font-size-9 line-height-16">Time</div>
										</th>
									</tr> 
				         		 </thead>
								 <tbody id="frozenlist">
								 </tbody>
							</table>
						</div>
						<div class="col-xs-12 text-center" id="frozenpagination">
						</div>
						<!-- end:table -->
			     		<div class="clearfix"></div>
				 	<!--  </div> -->
				 <!-- </div>  -->
			</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8">
					<div class="input-group">
						<span class="input-group-addon modalfootertip">
							<!-- <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p> -->
						</span>
	                    <span class="input-group-addon modalfooterbtn">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/stockmaterial/inside/frozen_history.js"></script><!--冻结履历  -->