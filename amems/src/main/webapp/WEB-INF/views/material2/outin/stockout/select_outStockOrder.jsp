<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/outin/stockout/select_outStockOrder.js"></script>
<div class="modal fade in modal-new" id="outStockModel" tabindex="-1" role="dialog"  aria-labelledby="outStockModel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width: 80%;" >
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
           			<div class='pull-left'>
               			<div class="font-size-12 ">出库单</div>
						<div class="font-size-9">Outbound Order</div>
				 	</div>
 					<div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
          	</div>
           	<div class="modal-body" style='padding-top:0px;'>
             	<div class="input-group-border" style='margin-top:8px;padding-top:5px;'>
                   	<div class=" pull-right padding-left-0 padding-right-8 margin-top-0 modalSearch">	
      					  		<div class="pull-left text-right padding-left-0 padding-right-0">
									<div class="font-size-12">出库类型</div>
									<div class="font-size-9">Demand Type</div>
								</div>	
								<div class="pull-left text-right padding-left-0 padding-right-0">
									<div class="padding-left-8 pull-left" style="width: 150px; margin-right:5px;">
									   <select id="outStockModel_shlx" class="form-control" onchange="outStockModel.search()">
									   		<option value='' >显示全部</option>
									   		<option value='70' >发料</option>
										    <option value='20'>修理</option>
											<option value='31'>租进</option>
											<option value='32'>租出</option>
											<option value='40'>交换</option>
											<option value='50'>出售</option>
											<option value='10'>采购</option>
											<option value='90'>其他</option>
									   </select> 
									</div>
								</div>
								
								<div class="pull-left text-right padding-left-0 padding-right-0">
									<div class="font-size-12">A/C注册号</div>
									<div class="font-size-9">A/C Register</div>
								</div>	
								<div class="pull-left text-right padding-left-0 padding-right-0">
									<div class="padding-left-8 pull-left" style="width: 150px; margin-right:5px;">
									   <select id="outStockModel_fjzch" class="form-control" onchange="outStockModel.search()">
									   </select> 
									</div>
								</div>
       		
       		
						<!-- 搜索框start -->
						<div class=" pull-right padding-left-0 padding-right-0">
							<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
								<input type="text" placeholder='出库单号/备注' id="outStockModel_keyword" class="form-control" />
							</div>
		                   	<div class=" pull-right padding-left-5 padding-right-0 ">   
							<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="outStockModel.search()">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                  	</div> 
						</div>
					<!-- 搜索框end -->
					</div>
	            	
		         	<div class="clearfix"></div>
		         		
					<!-- start:table -->
					<div class="margin-left-8 margin-right-8" style='margin-top:5px;margin-bottom:5px;'>
						<div class='table-content col-xs-12 padding-left-0 padding-right-0' id="searchTable">
							<table id="outStockModel_table" class="table table-thin table-bordered table-striped table-hover table-set" >
								<thead>
							   		<tr>
							   			<th class="colwidth-5" >
											<div class="font-size-12 line-height-18">选择</div>
											<div class="font-size-9 line-height-18">Choice</div>
										</th>
										<th class="colwidth-12">
											<div class="important">
												<div class="font-size-12 line-height-18">出库单号</div>
												<div class="font-size-9 line-height-18">Outbound No.</div>
											</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">出库类型</div>
											<div class="font-size-9 line-height-18">Type</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">状态</div>
											<div class="font-size-9 line-height-18">Status</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">出库日期</div>
											<div class="font-size-9 line-height-18">Date</div>
										</th>
										<th class="colwidth-12">
											<div class="font-size-12 line-height-18">接收单位</div>
											<div class="font-size-9 line-height-18">Receiving</div>
										</th>
										<th class="colwidth-12">
											<div class="font-size-12 line-height-18">飞机注册号</div>
											<div class="font-size-9 line-height-18">A/C Reg</div>
										</th>
										<th class="colwidth-20">
											<div class="important">
											<div class="font-size-12 line-height-18">备注</div>
											<div class="font-size-9 line-height-18">Remarks</div>
											</div>
										</th>
							 		 </tr>
								</thead>
								<tbody id="outStockModel_List">
								</tbody>
							</table>
						</div>
						<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="outStockModel_Pagination">
						</div>
						<div class="clearfix"></div>  
					</div>
				</div>
               <div class="clearfix"></div>              
          </div>
          <div class="clearfix"></div>  
          <div class="modal-footer">
          		<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
						</span>
	                   	<span class="input-group-addon modalfooterbtn">
	                     	<button type="button" onclick="outStockModel.setData()" class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button id="zjh_btn_clear" type="button" onclick="outStockModel.clearZjh()" class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">清空</div>
								<div class="font-size-9">Clear</div>
							</button>
	                    	<button type="button" onclick="outStockModel.close()" class="btn btn-primary padding-top-1 padding-bottom-1" >
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
<!--  弹出框结束-->