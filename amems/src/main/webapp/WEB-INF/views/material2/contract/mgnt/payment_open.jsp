<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="payment_open_alert" tabindex="-1" role="dialog"  aria-labelledby="payment_open_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:50%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">新增收付款</div>
							<div class="font-size-12" id="modalHeadENG">Add</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" >
	            <div class="col-xs-12 margin-top-8 ">
		            <div class="input-group-border"> 
		           <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">合同号</div>
							<div class="font-size-9 ">Contract No.</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type='text' id="p_hth" class='form-control' readonly/>
						</div>
				    </div>
				    
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">合同日期</div>
							<div class="font-size-9 ">Date</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type='text' id="p_htrq" class='form-control' readonly/>
						</div>
				    </div>
				    
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">合同类型</div>
							<div class="font-size-9 ">Type</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type='text' id="p_htlx" class='form-control' readonly/>
						</div>
				    </div>
				    
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">编制人</div>
							<div class="font-size-9 ">Compactor</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type='text' id="p_bzr" class='form-control' readonly/>
						</div>
				    </div>
				    
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">币种</div>
							<div class="font-size-9 ">Currency</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type='text' id="p_biz" class='form-control' readonly/>
						</div>
				    </div>
				    
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">交付方式</div>
							<div class="font-size-9 ">Delivery</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type='text' id="p_jffs" class='form-control' readonly/>
						</div>
				    </div>
				    
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">供应商</div>
							<div class="font-size-9 ">Supplier</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type='text' id="p_xgfms" class='form-control' readonly/>
						</div>
				    </div>
				    
				    <div class='clearfix'></div>
				    
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class='color-red'>*</span>类型</div>
							<div class="font-size-9 ">Type</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8" style='height:34px;'>
							<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input value="1" style="vertical-align:text-bottom;" type="radio" name="paymentType">&nbsp;收&nbsp;&nbsp;
							</label>
							<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input value="2" style="vertical-align:text-bottom;" type="radio" name="paymentType">&nbsp;付&nbsp;&nbsp;
							</label>
						</div>
				    </div>
				    
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">明细科目</div>
							<div class="font-size-9 ">Detail subject</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group col-xs-12">
							    <input id="p_htmxid" class="form-control" type="hidden">
								<input id="p_htmxName" class="form-control readonly-style" type="text" ondblclick="payment_open_alert.openContractInfoWin()" readonly >
		                    	<span id="" class="input-group-addon btn btn-default" onclick="payment_open_alert.openContractInfoWin()">
		                    		<i class="icon-search"></i>
		                    	</span>
		                	</div>
						</div>
				    </div>
				    
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class='color-red'>*</span>金额</div>
							<div class="font-size-9 ">Amount</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type='text' id="p_je" class='form-control' onkeyup="payment_open_alert.changeJe(this)" />
						</div>
				    </div>
				    
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class='color-red'>*</span>支付方式</div>
							<div class="font-size-9 ">Method</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<select id="p_sfkfs" class='form-control'>
							</select>
						</div>
				    </div>
				    
				    <div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">发票</div>
							<div class="font-size-9 ">Invoice</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<input type='text' id="p_fphm" class='form-control' maxlength="100"/>
						</div>
				    </div>
				    
				    <div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">备注</div>
							<div class="font-size-9 ">Remark</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea id="p_bz" class='form-control' style='height:55px;' maxlength="300"></textarea>
						</div>
				    </div>
				    
				    <div class='clearfix'></div>
		            </div>
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
							<button id="save_btn" type="button" onclick="javascript:payment_open_alert.setData()" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
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
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/contract/mgnt/payment_open.js"></script>