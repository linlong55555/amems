<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<style>
.input-addon-style{
background:none;
border:0px;
}
.table-timeprice tbody tr:first-child td{
border-top:0px;
}
.table-timeprice tbody tr:last-child td{
border-bottom:1px solid #d5d5d5;
}
.timeprice-p{
width:120px;
margin-left:8px;
margin-top:-13px;
padding-bottom:0px;
background:white;
text-align:center;
}
</style>
<div class="modal fade in modal-new" id="settingtimeprice_open_alert" tabindex="-1" role="dialog"  aria-labelledby="settingtimeprice_open_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:40%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">设置工时单价</div>
							<div class="font-size-12" id="modalHeadENG">Set time unit price</div>
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
							<div class="font-size-12  margin-topnew-2"><span class="color-red">*</span>币种</div>
							<div class="font-size-9 ">Currency</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control' id="settingtimeprice_open_alert_biz">
							</select>
						</div>
				    </div>
				    <div class='clearfix'></div>
	            	</div> 
	                <div class='clearfix'></div>
	                <div style="border:1px dashed #d5d5d5;" class="margin-top-10">
	                <p class="timeprice-p">
	                	维修工时单价&nbsp;
	                	<button class="line6 line6-btn" onclick="settingtimeprice_open.addWx()" type="button">
	                		<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
	                	</button>
	                </p>
	                <div class="padding-leftright-8 margin-bottom-10" >
	                <table class="table table-thin  text-center table-set table-timeprice" id="settingtimeprice_open_alert_wxTable" style="margin-bottom:0px !important;">
		                <tbody id="settingtimeprice_open_alert_wxTbody">	                	
		                	<tr id="settingtimeprice_open_alert_wxGd">
		                	<td class="colwidth-5">
			                	
							</td>
		                	<td class="text-left" id="settingtimeprice_open_alert_autoWx">
		                	0工时以后
		                	</td>
		                	<td>
		                	<div class="input-group col-xs-12">
									<span id="" class="input-group-addon input-addon-style padding-right-8" >
									 	单价
									</span>
									<input type="text" id="settingtimeprice_open_alert_wxdj" onkeyup='settingtimeprice_open.changedj(this)'  class="form-control" />
							</div>
		                	</td>
		                	</tr>
		                </tbody>
	                </table>
	                </div>
	                </div> 
	                <div class="clearfix"></div>
	                <!-- 清洁工时单价 -->
	                 <div style="border:1px dashed #d5d5d5;" class="margin-top-20 margin-bottom-10">
	                <p class="timeprice-p">
	                	清洁工时单价&nbsp;
	                	<button class="line6 line6-btn" onclick="settingtimeprice_open.addQj()"  type="button">
	                		<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
	                	</button>
	                </p>
	                <div class="padding-leftright-8 margin-bottom-10" >
	                <table class="table table-thin  text-center table-set table-timeprice" id="settingtimeprice_open_alert_qjTable" style="margin-bottom:0px !important;">
		                <tbody >

		                	<tr id="settingtimeprice_open_alert_qjGd">
		                	<td class="colwidth-5">
			                	
							</td>
		                	<td class="text-left" id="settingtimeprice_open_alert_autoQj">
		                	0工时以后
		                	</td>
		                	<td>
		                	<div class="input-group col-xs-12">
									<span id="" class="input-group-addon input-addon-style padding-right-8" >
									 	单价
									</span>
									<input type="text" id="settingtimeprice_open_alert_qjdj" onkeyup='settingtimeprice_open.changedj(this)' class="form-control" />
							</div>
		                	</td>
		                	</tr>
		                </tbody>
	                </table>
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
	                    	<button id="save_btn" type="button" onclick="settingtimeprice_open.saveData()" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">确认</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" data-dismiss="modal"
								class="btn btn-primary padding-top-1 padding-bottom-1 ">
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
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/worktimesubsidy/settingtimeprice.js"></script>
<!--  弹出框结束-->