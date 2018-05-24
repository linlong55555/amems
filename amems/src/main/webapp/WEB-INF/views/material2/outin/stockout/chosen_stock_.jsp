<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="chosen_stock_alert" tabindex="-1" role="dialog"  aria-labelledby="chosen_stock_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:80%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">已选择库存</div>
							<div class="font-size-12" id="modalHeadENG">Chosen Stock</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" >
	            <div class="col-xs-12">
		            <div class='col-xs-12 padding-right-0' style=' padding-top:8px;' id='modalPanelRight'>
		
				<!-- 查询条件结束 -->
				<div  class='table_pagination'>
		          <div id="work_card_main_table_top_div" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0" style="overflow-x: auto;">
					<table id="chosen_stock_alert_table" class="table table-thin table-bordered table-striped table-hover table-set" style="margin-bottom:0px !important;">
						<thead>                              
							<tr>
								<th class='colwidth-10' onclick="" name="" >
									    <div class="font-size-12 line-height-18">部件号</div>
										<div class="font-size-9 line-height-18">PN</div>
							    </th>
								<th class='colwidth-20' onclick="" name="" >
									<div class="font-size-12 line-height-18">部件名称</div>
									<div class="font-size-9 line-height-18">PN name</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
										<div class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">Batch No.</div>
							    </th>
							    <th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">SN</div>
							    </th>
							    <th class='colwidth-20'>
									<div class="font-size-12 line-height-18" >位置</div>
									<div class="font-size-9 line-height-18">Position</div>
								</th>
								
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18" >库存数量</div>
									<div class="font-size-9 line-height-18">Number</div>
								</th>
								<th class='colwidth-12' onclick="" name="">
									<div class="font-size-12 line-height-18">货架寿命</div>
									<div class="font-size-9 line-height-18">Shelf Life</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">成本</div>
									<div class="font-size-9 line-height-18">Cost</div>
							   </th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">价值</div>
									<div class="font-size-9 line-height-18">Value</div>
							   </th>
							   <th class='colwidth-20' onclick="" name="" >
									<div class="font-size-12 line-height-18">产权</div>
									<div class="font-size-9 line-height-18">Right</div>
							   </th>
							   <th class='colwidth-7' onclick="" name="" >
									<div class="font-size-12 line-height-18">GRN</div>
									<div class="font-size-9 line-height-18">GRN</div>
							   </th>
							   <th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">来源</div>
									<div class="font-size-9 line-height-18">Source</div>
							   </th>
							   <th class='colwidth-20' onclick="" name="" >
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark </div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">上架日期</div>
									<div class="font-size-9 line-height-18">Date</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">物料类别</div>
									<div class="font-size-9 line-height-18">Type</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-18">Manufacturer No.</div>
							   </th>
							    <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">型号</div>
									<div class="font-size-9 line-height-18">Model</div>
							   </th>
							    <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">规格</div>
									<div class="font-size-9 line-height-18">Specifications</div>
							   </th>
							    <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">制造厂商</div>
									<div class="font-size-9 line-height-18">Manufacturer</div>
							   </th>
							</tr> 
						</thead>
						<tbody id="chosen_stock_alert_stocklist">
							
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="chosen_stock_alert_pagination">
				</div>
				</div>
		        </div>
	            <div class='clearfix'></div>          
           	</div>
           	<div class='clearfix'></div>  
           	</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	               
	                    <span class="input-group-addon modalfooterbtn">
                    		<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
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
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/outin/stockout/chosen_stock.js"></script>
