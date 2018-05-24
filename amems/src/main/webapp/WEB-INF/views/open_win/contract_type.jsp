<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/contract_type.js"></script>
<div class="modal fade in modal-new" id="contract_type" tabindex="-1" role="dialog"  aria-labelledby="contract_type" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:550px;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14">合同类型</div>
							<div class="font-size-12">Type</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" >
            	<div class="input-group-border" style="margin-top:8px;padding-top:5px;">
            	
				<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">合同类型</div>
						<div class="font-size-9">Type</div>
					</span>
					<div id="htlx_radio_div" class="col-sm-9 col-xs-9 padding-leftright-8">
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
	                    	<button id="contract_confirm_btn" class="btn btn-primary padding-top-1 padding-bottom-1 edit-contract">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button id="contract_confirm_single_btn" onclick="contract_type.operationContract('')" class="btn btn-primary padding-top-1 padding-bottom-1 ">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button onclick="contract_type.close()" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" >
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
<div id="contract_view_Modal" style='display: none;padding:0px;width:100px;' >
	<!-- 表格 -->
	<div class="padding-left-0 padding-right-0 padding-bottom-0 padding-top-0" style="margin:0px;width:100%;">
		<ul style="list-style:none;" class='burstification_style'>
			<li class='bottom-line' ><a href="javascript:contract_type.operationContract('');"><i class='fa fa-plus'></i>创建新合同</a></li>
			<li id="contract_li" class='packageslist' style="background:white;border-top:1px solid #d5d5d5;">添加到现有合同:</li>
			<div id="exists_contract_div" style="overflow-y: auto;max-height: 150px;">
			</div>
		</ul>
	</div>
	<div class='clearfix'></div>
</div>
<%@ include file="/WEB-INF/views/material2/contract/mgnt/mgnt_add.jsp"%><!-- 新增合同弹出框 -->
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
<!--  弹出框结束-->