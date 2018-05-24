<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<div id="mgnt_payment_main" >
	<div id="" class="col-lg-12 col-md-12 col-xs-12 padding-left-0 padding-right-0 tab-table-content"
		style="overflow-x: auto">
				<table class="table table-bordered table-striped table-thin table-hover table-set" style="margin-bottom:0px !important" id="">
					<thead>
				   		<tr>				   			
				   			<th class="colOptionhead" style="text-align:center;vertical-align:middle;width: 30px;">
								<button class="line6 line6-btn checkPermission" permissioncode='material:contract:mgnt:07' onclick="mgnt_payment_main.openWinAdd()" type="button">
								    <i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
						        </button>
							</th>
							<th class='colwidth-3'>
								<div class="font-size-12 line-height-18" >序号</div>
								<div class="font-size-9 line-height-18">No.</div>
							</th>
				   			<th class="colwidth-5">
								<div class="font-size-12 line-height-18">收/付</div>
								<div class="font-size-9 line-height-18">Collect/pay</div>
							</th>
							<th class="colwidth-5">
								<div class="font-size-12 line-height-18">金额</div>
								<div class="font-size-9 line-height-18">Sum</div>
							</th>
							<th class="colwidth-9">
								<div class="font-size-12 line-height-18">支付方式</div>
								<div class="font-size-9 line-height-18">Method</div>
							</th>
							<th class="colwidth-20">
								<div class="font-size-12 line-height-18">发票</div>
								<div class="font-size-9 line-height-18">Invoice</div>
							</th>
							<th class="colwidth-30">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</th>
							
				 		 </tr>
					</thead>
					<tbody id="mgnt_payment_main_tbody">
					</tbody>
					<tfooter style='border:0px !important;'>
						<tr style='border:0px !important;'>
							<td id="contract_main_payment_tfooter_td" colspan='7' class='text-right' style='background:none;border:0px;'>总计：  <span id="htfkzj">0</span></td>
						</tr>
					</tfooter>
				</table>
				</div>
				<div class='clearfix'></div>
			
</div>
<%@ include file="/WEB-INF/views/material2/contract/mgnt/payment_open.jsp"%>
<%@ include file="/WEB-INF/views/open_win/contract_info_win_list.jsp" %> <!-- 合同明细弹出框 -->
 <script type="text/javascript" src="${ctx }/static/js/thjw/material2/contract/mgnt/mgnt_payment.js"></script>