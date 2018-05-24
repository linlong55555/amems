<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
	<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true" >
					<div class="modal-dialog" style="width: 500px;">
							<div class="modal-content" >
								<div class="modal-body padding-bottom-0" id="confirmModalBody" >
									<div class="panel panel-primary" >
										<div class="panel-heading  padding-top-3 padding-bottom-1 ">
											<div class="font-size-16 line-height-18">确认</div>
											<div class="font-size-9 ">Borrow Confirm</div>
										</div>
										<div class="panel-body padding-top-0 padding-bottom-0" >
											 <form id="confirmfrom">
											    <input type="hidden" id="id" />
											    <input type="hidden" id="kcid" />
												<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 margin-top-10  form-group" >
														<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																class="font-size-12 line-height-18"><span style="color: red">*</span>借用备注</div>
															<div class="font-size-9 line-height-18">Borrow Remarks</div>
														</label>
														<div class=" col-xs-8 padding-left-8 padding-right-0">
															<textarea type="text" id="jcBz" name="jcBz"  maxlength="100" class="form-control"  >
															</textarea>
														</div>
												</div>
												<div class="text-right margin-top-10 margin-bottom-10">
													<button id='borrowConfirmationBtn' type="button"  
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
										<button type="button" onclick="doBorrowConfirmation()"
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
 				