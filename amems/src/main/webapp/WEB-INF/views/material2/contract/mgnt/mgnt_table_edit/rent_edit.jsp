<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!-- 合同类型 -->
<div id="rentAddData" >
	<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
		<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
			<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
				<div class="font-size-12 margin-topnew-2" id="zccolHeadCN">租出</div>
				<div class="font-size-9" id="zccolHeadENG">Rent</div>
			</label>
			<div id="rent_table_div" class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
			    <div style='overflow:auto;'>
			   
				<!-- 租入 -->
				<table  class="table table-thin table-bordered table-striped table-set" style='margin-bottom:0px !important;'>
				<thead>                              
					<tr>
						<th class="colOptionhead" style="text-align:center;vertical-align:middle;width: 30px;">
							<button class="line6 line6-btn" onclick="rentAddData.addTrShow()" type="button">
								<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
						    </button>
						</th>
						<th class='colwidth-3'>
							<div class="font-size-12 line-height-18" >序号</div>
							<div class="font-size-9 line-height-18">No.</div>
						</th>
						<th class='colwidth-13'>
							<div class="font-size-12 line-height-18" >件号</div>
							<div class="font-size-9 line-height-18">PN</div>
						</th>
						<th class='colwidth-15' onclick="" name="">
							<div class="font-size-12 line-height-18">名称</div>
							<div class="font-size-9 line-height-18">Name</div>
						</th>
						<th class='colwidth-9' onclick="" name="" id='addContractxlh'>
							<div class="font-size-12 line-height-18">序列号</div>
							<div class="font-size-9 line-height-18">SN</div>
						</th>
						<th class='colwidth-7' onclick="" name="" id='addContractsl'>
								<div class="font-size-12 line-height-18">数量</div>
								<div class="font-size-9 line-height-18">QTY</div>
						</th>
						<th class='colwidth-7' onclick="" name=""  id='addContractdj'>
							<div class="font-size-12 line-height-18">单价</div>
							<div class="font-size-9 line-height-18">Price</div>
						</th>
						<th class='colwidth-7' onclick="" name="" id='addContractzt'>
								<div class="font-size-12 line-height-18">状态</div>
								<div class="font-size-9 line-height-18">Status</div>
						</th>
						<th class='colwidth-13' onclick="" name="" >
							<a href="#" onclick="mgnt_add_alert_Modal.openCqWin()" style="float: right;">批量设置</a>
							<div class="font-size-12 line-height-18">产权</div>
							<div class="font-size-9 line-height-18">Right</div>
						</th>
						<th class='colwidth-9' onclick="" name="" id='addContractxj'>
								<div class="font-size-12 line-height-18">小计</div>
								<div class="font-size-9 line-height-18">Sub Total</div>
						</th>
						<th class='colwidth-13' onclick="" name="" >
							<div class="font-size-12 line-height-18">交货期</div>
							<div class="font-size-9 line-height-18">Date</div>
						</th>
						
					</tr> 
				</thead>
				<tbody id='rentAddTbody'>
				</tbody>
				<tfooter style='border:0px !important;'>
					<tr style='border:0px !important;'>
						<td colspan='11' class='text-right' style='background:none;border:0px;'>总计： <span id="zczj">0</span></td>
					</tr>
				</tfooter>
				</table>
				
					</div>
				</div>
			</div>
	   	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/contract/mgnt/mgnt_table_edit/rent_edit.js"></script>
