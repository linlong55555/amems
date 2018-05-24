<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/materialEnquiry.js"></script>
<div class="modal fade" id="MaterialEnquiryModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 74%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="MaterialEnquiryModalBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">询价列表</div>
						<div class="font-size-9 ">List of Enquiry</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
						    
							<!-- start:table -->
							<div style="margin-top: 10px;overflow-x: auto;">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width: 750px;">
									<thead id="enquiryHead">
										<tr>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">序号</div>
												<div class="font-size-9 line-height-18">No.</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">供应商</div>
												<div class="font-size-9 line-height-18">Supplier</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">单价(人民币：元)</div>
												<div class="font-size-9 line-height-18">Price(RMB:YUAN)</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">材料费(人民币：元)</div>
												<div class="font-size-9 line-height-18">Material cost(RMB:YUAN)</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">工时费(人民币：元)</div>
												<div class="font-size-9 line-height-18">Time cost(RMB:YUAN)</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">其它费(人民币：元)</div>
												<div class="font-size-9 line-height-18">Other cost(RMB:YUAN)</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">总价(人民币：元)</div>
												<div class="font-size-9 line-height-18">Total(RMB:YUAN)</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18">预计到货日期</div>
												<div class="font-size-9 line-height-18">Expected arrival</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">询价人</div>
												<div class="font-size-9 line-height-18">Enquiry</div>
											</th>
										</tr>
									</thead>
									<tbody id="enquiryList">
									</tbody>
								</table>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
			                </div>
			     			<div class="clearfix"></div>
						</div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
</div>
