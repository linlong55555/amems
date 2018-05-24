<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java"%>
<div id="contract_main_detail" >
	<div class="col-lg-12 col-md-12 col-xs-12 padding-left-0 padding-right-0 tab-table-content" style="overflow-x: auto">
		<table id="" class='table table-bordered table-thin table-striped table-hover table-set' style='margin-bottom:0px !important'>
			<thead>
				<tr>
				<th class='colwidth-13'>
					<div class="font-size-12 line-height-18" >件号</div>
					<div class="font-size-9 line-height-18">PN</div>
				</th>
				<th class='colwidth-15' onclick="" name="">
					<div class="font-size-12 line-height-18">名称</div>
					<div class="font-size-9 line-height-18">Name</div>
				</th>
				<th id="xlh_d" class='colwidth-9' onclick="" name="">
					<div class="font-size-12 line-height-18">序列号</div>
					<div class="font-size-9 line-height-18">SN</div>
				</th>
				<th id="htsl_d" class='colwidth-7' onclick="" name="" >
					<div class="font-size-12 line-height-18">合同数量</div>
					<div class="font-size-9 line-height-18">QTY</div>
				</th>
				<th id="cksl_d" class='colwidth-7' onclick="" name="" >
					<div class="font-size-12 line-height-18">出库数量</div>
					<div class="font-size-9 line-height-18">QTY</div>
				</th>
				<th id="jfsl_d" class='colwidth-7' onclick="" name="" >
					<div class="font-size-12 line-height-18">交付数量</div>
					<div class="font-size-9 line-height-18">QTY</div>
				</th>
				<th id="rksl_d" class='colwidth-7' onclick="" name="" >
					<div class="font-size-12 line-height-18">入库数量</div>
					<div class="font-size-9 line-height-18">QTY</div>
				</th>
				<th id="ghsl_d" class='colwidth-7' onclick="" name="" >
					<div class="font-size-12 line-height-18">归还数量</div>
					<div class="font-size-9 line-height-18">QTY</div>
				</th>
				<th id="sxyy_d" class='colwidth-15' onclick="" name="" >
					<div class="font-size-12 line-height-18">原因</div>
					<div class="font-size-9 line-height-18">Reason</div>
				</th>
				<th id="dj_d" class='colwidth-7' onclick="" name="" >
					<div class="font-size-12 line-height-18">单价</div>
					<div class="font-size-9 line-height-18">Price</div>
				</th>
				<th id="wlzt_d" class='colwidth-9' onclick="" name="" >
					<div class="font-size-12 line-height-18">状态</div>
					<div class="font-size-9 line-height-18">Status</div>
				</th>
				<th class='colwidth-9' onclick="" name="" >
					<div class="font-size-12 line-height-18">产权</div>
					<div class="font-size-9 line-height-18">Right</div>
				</th>
				<th class='colwidth-9' onclick="" name="" >
					<div class="font-size-12 line-height-18">交货期</div>
					<div class="font-size-9 line-height-18">Date</div>
				</th>
				<th id="xj_d" class='colwidth-9' onclick="" name="" >
					<div class="font-size-12 line-height-18">小计</div>
					<div class="font-size-9 line-height-18">Subtotal</div>
				</th>
				
			</tr> 
			</thead>
			<tbody id="contract_main_detail_list"> 
			</tbody>
			 <tfooter style='border:0px !important;'>
				<tr style='border:0px !important;'>
					<td id="contract_main_detail_tfooter_td" class='text-right' style='background:none;border:0px;' colspan="14" >总计：  <span id="htmxzj">0</span></td>
				</tr>
			</tfooter>
		</table>
	</div>
	<div class="clearfix"></div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/contract/mgnt/contract_detail.js"></script>