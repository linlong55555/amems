<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="alertModalTool" tabindex="-1" role="dialog"  aria-labelledby="alertModalHb" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" >
		<div class="modal-content">
			<div class="modal-header modal-header-new">
				<h4 class="modal-title">
	               	<div class="pull-left">
	                   	<div class="font-size-12 ">工具借用/归还记录</div>
						<div class="font-size-9 ">ToolUse Info</div>
					</div>
				  	<div class="pull-right">
		  		  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
		  		  	</div>
				  	<div class="clearfix"></div>
	             </h4>
		    </div>
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
				<div class="panel panel-primary margin-top-10">
					<div class="panel-body padding-top-0 padding-bottom-0">				
					   	<div class="clearfix"></div>	     
						<div  style="overflow-x: auto;margin-top: 10px">
							<table class="table-set table table-thin table-bordered table-striped table-hover ">
								<thead>
									<tr>

										<th class="colwidth-8">
										
											<div class="font-size-12 line-height-18">部件号</div>
											<div class="font-size-9 line-height-16">Bj No.</div>
										
										</th>
										<th class="colwidth-10">
												<div class="font-size-12 line-height-18">部件序列号</div>
												<div class="font-size-9 line-height-16">No.</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">记录类型</div>
											<div class="font-size-9 line-height-16">Record Type.</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">借用人名称</div>
											<div class="font-size-9 line-height-16">Borrow Name.</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">借用时间</div>
											<div class="font-size-9 line-height-16">Borrow Time.</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">借用数量</div>
											<div class="font-size-9 line-height-16">Borrow Quantity.</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">借用备注</div>
											<div class="font-size-9 line-height-16">Borrow Bz.</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">长期借用标识</div>
											<div class="font-size-9 line-height-16">Borrow Sign.</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">操作人</div>
											<div class="font-size-9 line-height-16">Operator.</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">组织机构</div>
											<div class="font-size-9 line-height-16">Organization.</div>
										</th>
									</tr> 
				         		 </thead>
								 <tbody id="toolusehistory">
								 </tbody>
							</table>
						</div>
						<div class="col-xs-12 text-center" id="toolusepagination">
						</div>
						<!-- end:table -->
			     		<div class="clearfix"></div>
				 	 </div>
				 </div> 
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
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/stockmaterial/inside/tool_use_history.js"></script><!--冻结履历  -->