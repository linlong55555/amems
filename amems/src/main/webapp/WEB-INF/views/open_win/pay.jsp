<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/pay.js"></script>
<div class="modal fade" id="PayModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:450px!important;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
			  	<div class="panel panel-primary ">
					<div class="panel-heading  padding-top-3 padding-bottom-1  margin-bottom-10">
						<div class="font-size-16 line-height-18">付款</div>
						<div class="font-size-9 ">Pay</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
					
		            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							
							<div class=" col-lg-12 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>付款日期</div>
									<div class="font-size-9 line-height-18">Payment date</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input class="form-control date-picker" id="wfkrq" data-date-format="yyyy-mm-dd" type="text" readonly/>
								</div>
							</div>
							
							<div class="clearfix"></div>
							
							<div class=" col-lg-12 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>付款金额</div>
									<div class="font-size-9 line-height-18">Payment amount</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" id="wfkje" class="form-control " onkeyup='PayModal.clearNoNumTwo(this)' placeholder=''/>
								</div>
							</div>
							
							<div class="clearfix"></div>
						
							<div class=" col-lg-12 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>付款方式</div>
									<div class="font-size-9 line-height-18">Payment method</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<select class='form-control' id='wfkfs' onchange="PayModal.changePayMethod()" >
										<c:forEach items="${payTypeEnum}" var="item">
										  	<option value="${item.id}" >${item.name}</option>
										</c:forEach>
								    </select>
								</div>
							</div>
							
							<div class="clearfix"></div>
			
						 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
								<label class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
									<div class="font-size-12 line-height-18"><span id="wfkfssmspan" style="color: red">*</span>付款方式说明</div>
									<div class="font-size-9 line-height-18">Payment desc</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
									<input type="text" id="wfkfssm" class="form-control " maxlength="10" />
								</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								<label class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
									<textarea class="form-control" id="wbz" maxlength="300" ></textarea>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="text-right margin-top-10 margin-bottom-10">
							<button type="button" onclick="PayModal.setData()" class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
		                </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
