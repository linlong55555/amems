<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="work_hour_config_modal" tabindex="-1" role="dialog"  aria-labelledby="work_hour_config_modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:50%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">设置工时费用</div>
							<div class="font-size-12" id="modalHeadENG">Set Work Hour Payment</div>
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
		            <form id="demand_detail_form">
		            <input type="hidden" id="info_id" />
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">飞机注册号</div>
							<div class="font-size-9 ">A/C REG</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="config_fjzch" class="form-control" type="text" disabled="disabled">
						</div>
				    </div>
				     <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">月份</div>
							<div class="font-size-9 ">Month</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="config_yf" class="form-control" type="text" disabled="disabled">
						</div>
				    </div>
				    <div class='clearfix'></div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">航前工时</div>
							<div class="font-size-9 ">Preflight MHRs</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="config_hqgs" class="form-control" maxlength="10" type="text" onkeyup="work_hour_config_modal.validateDecimal(this)">
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">过站工时</div>
							<div class="font-size-9 ">Station MHRs</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="config_gzgs" class="form-control" maxlength="10" type="text" onkeyup="work_hour_config_modal.validateDecimal(this)">
						</div>
				    </div>
				    <div class='clearfix'></div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">航后工时</div>
							<div class="font-size-9 ">Postflight MHRs</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="config_hhgs" class="form-control" maxlength="10" type="text" onkeyup="work_hour_config_modal.validateDecimal(this)">
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">工时费用</div>
							<div class="font-size-9 ">MHRs payment</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="config_gsdj" class="form-control" maxlength="10" type="text" onkeyup="work_hour_config_modal.validateDecimal(this)">
						</div>
				    </div>
				    <div class='clearfix'></div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">常用耗材费用比例</div>
							<div class="font-size-9 ">Payment Proportion</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group" style="width:100%">
							  <input id="config_cyhcfybl" type="text" class="form-control" maxlength="10" onkeyup="work_hour_config_modal.validateDecimal(this)">
							  <span class="input-group-addon" style="width:35px;">%</span>
							</div>
						</div>
				    </div>
				    <div class='clearfix'></div>
				    </form>
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
	                    	<button id="save_btn" type="button" onclick="work_hour_config_modal.saveData()" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
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
<!--  弹出框结束-->

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenancemgnt/workhourconfig.js"></script>