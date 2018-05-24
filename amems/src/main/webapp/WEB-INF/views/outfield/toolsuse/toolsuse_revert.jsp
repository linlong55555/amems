<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
	<div class="modal fade" id="revertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true" >
					<div class="modal-dialog" style="width: 500px;">
							<div class="modal-content" >
								<div class="modal-body padding-bottom-0" id="revertModalBody" >
									<div class="panel panel-primary" >
										<div class="panel-heading  padding-top-3 padding-bottom-1 ">
											<div class="font-size-16 line-height-18">归还</div>
											<div class="font-size-9 ">Borrow Revert</div>
										</div>
										<div class="panel-body padding-top-0 padding-bottom-0" >
											 <form id="revertform">
											    <input type="hidden" id="id" />
											    <input type="hidden" id="kcid" />
												<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 margin-top-10 form-group" >
														<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																class="font-size-12 line-height-18"><span style="color: red">*</span>归还人</div>
															<div class="font-size-9 line-height-18">Revert People</div>
														</label>
														
														<div class="col-lg-7 col-sm-7 col-xs-7 padding-left-8 padding-right-0">
															<input type="text" class="form-control" name="ghZrrmc" id="ghZrrmc"  />
															<input type="hidden" id="ghZrrid" />
															 
														</div>
														<div class="col-lg-1 col-sm-1 col-xs-1 padding-left-2 padding-right-0">
															<a href="javascript:toolUseRevert.selectUser1();" class="btn btn-primary" >
																<i class="icon-search cursor-pointer" ></i>
															</a>
														</div>
														
												</div>
												<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
														<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																class="font-size-12 line-height-18"><span style="color: red">*</span>归还备注</div>
															<div class="font-size-9 line-height-18">Revert Remarks</div>
														</label>
														<div class=" col-xs-8 padding-left-8 padding-right-0">
															<textarea type="text" id="ghBz" name="ghBz"  maxlength="100" class="form-control"  >
															</textarea>
														</div>
												</div>
												<div class="text-right margin-top-10 margin-bottom-10">
													<button type="button" id="revertBtn"
														class="btn btn-primary padding-top-1 padding-bottom-1"
														data-dismiss="modal">
														<div class="font-size-12">确定</div>
														<div class="font-size-9">Confirm</div>
													</button>
													<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
														<div class="font-size-12">关闭</div>
														<div class="font-size-9">Close</div>
													</button>
													
								                </div>
											</form>
										</div>
									</div>
									<!-- <div class="text-right margin-top-10 margin-bottom-10">
										<button type="button" onclick="doRevert()"
											class="btn btn-primary padding-top-1 padding-bottom-1"
											data-dismiss="modal">
											<div class="font-size-12">确定</div>
											<div class="font-size-9">Confirm</div>
										</button>
										<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
											<div class="font-size-12">关闭</div>
											<div class="font-size-9">Close</div>
										</button>
										
					                </div> -->
					     			<div class="clearfix"></div>
								</div>
								
							</div>
						</div>
					</div>
<script type="text/javascript">
 
toolUseRevert = {
		selectUser1:function (){
			var this_ = this; 
			//调用用户选择弹窗
			userModal.show({
				selected:$("#"+"ghZrrid").val(),//原值，在弹窗中默认勾选
				dprtcode:$("#dprtcode").val(),
				callback: function(data){//回调函数
					$("#"+"ghZrrid").val(formatUndefine(data.id));
					$("#"+"ghZrrmc").val(formatUndefine(data.displayName));
					
					$('#revertform').data('bootstrapValidator')
					.updateStatus('ghZrrmc',
							'NOT_VALIDATED', null)
					.validateField('ghZrrmc');
				}
			}) 
		}
}

$('#revertform #ghZrrmc').on('change',function(){
	$('#revertform #ghZrrid').val('')
});
 
</script>					