<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"
	language="java"%>
<!-- 添加弹出框 -->
<!-- 	选择用户的模态框 -->
	<div class="modal modal-new" id="customer_choose" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width: 40%;height:50%;">
		<div class="modal-content">
		   <!-- 模态框头部 -->
		   <div class="modal-header modal-header-new" >
	               	<h4 class="modal-title" >
	           			<div class='pull-left'>
	               			<div class="font-size-12">选择客户</div>
							<div class="font-size-9 ">Choice</div>
					 	</div>
						<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class='clearfix'></div>
					</h4>
          	</div>
			<div class="modal-body padding-bottom-0">
				<div class="col-xs-12 margin-top-8 ">
					<div class="input-group-border">
						<div class="text-right padding-leftright-8">
			         			<div style="padding-left:0!important;" class="pull-right">   
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchCustomer()">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                    </div> 
								<div style="padding-right:9px!important;" class="col-xs-6 pull-right">
									<input type="text" placeholder='客户名/客户编号' id="keyword_user_search" class="form-control " />
								</div>
							</div>
							<div class="clearfix"></div>
							<!-- start:table -->
							<div class="margin-top-8 padding-leftright-8" style="overflow-y:auto;">
								<table class="table table-bordered table-striped table-hover text-left table-set">
									<thead>
								   		<tr>
											<th style="width:15%">
												<div class="font-size-12 notwrap">选择</div>
												<div class="font-size-9 notwrap">Choice</div>
											</th>
											<th style="width:40%">
												<div class="font-size-12 notwrap">客户编号</div>
												<div class="font-size-9 notwrap">Customer No.</div>
											</th>
											<th style="width:45%">
												<div class="font-size-12 notwrap">客户名称</div>
												<div class="font-size-9 notwrap">Customer Name</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="customer_choose_list">
									  <tr>
									     <td colspan="3" style="text-align: center;">数据加载中……</td>
									  </tr>
									</tbody>
								</table>
								<div class=" col-xs-12  text-center page-height padding-right-0 padding-left-0" style="margin-top: 0px; margin-bottom: 0px;" id="custpagination"></div>
							</div>
							<!-- end:table -->
			     			<div class="clearfix"></div>
						</div>
					</div>
			</div>
			<!-- 模态框尾部 -->
			<div class="modal-footer">
	          		<div class="col-xs-12 padding-leftright-8" >
						<div class="input-group">
							<span class="input-group-addon modalfootertip" >
		                      <i class='glyphicon glyphicon-info-sign alert-info'></i>
		                      <p class="alert-info-message"></p>
					        </span>
		                   	<span class="input-group-addon modalfooterbtn">
		                     	<button type="button" onclick="appendCustomer()" name="" id="appendCustomer_id"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									data-dismiss="modal">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
		                   	</span>
	              		</div><!-- /input-group -->
					</div>
				</div>
		</div>
	</div>
</div>
	<!--  添加或者删除弹出框结束-->