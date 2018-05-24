<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class='displayContent' id='bottom_hidden_content' style='display:none; ' >
	<div class="panel panel-primary margin-bottom-0" >
 		<div class="panel-heading bg-panel-heading" style='' >
 		    <div class='pull-left'>
			 <div class="font-size-12 line-height-12">监控设置</div>
			 <div class="font-size-9 line-height-9">Monitor Settings</div>
			 </div>
			 <div class='pull-right'>
			  <label class='padding-left-8' style='font-weight:normal;padding-right:8px;'>
			 	<input type='radio'  value="1" name='classification1' onclick="classificationType('percentage')" style='vertical-align:-3px'/>&nbsp;按百分比
			 </label>
			 <label  style='font-weight:normal;border-left:1px solid #d5d5d5;padding-left:8px;'>
			 	<input type='radio' value="2"  name='classification1' onclick="classificationType()"  style='vertical-align:-3px'/>&nbsp;按数值
			 </label>
			
			 </div>
			 <div class='clearfix'></div> 
		</div>
		
		<div class="panel-body  padding-bottom-0 padding-top-0 " id="bottom_hidden_content_input" >
			<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-5">	 
				<div style="height:25px ;" >
				<small class="text-muted" >	
					<span >说明：以下项为监控数据中剩余值的预警设置</span>
				</small>
				</div>
				<div class="input-group-border margin-top-0 padding-left-0" id='monitor_group_border'>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">
									日历
								</div>
								<div class="font-size-9 ">Calendar</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
							<div class="input-group"> 
							<input type="text"  id="1_10"  class=" form-control"  onkeyup='bottom_hidden_content.clearNoNum(this)'/>
								<input class="form-control" type="hidden" />
								<span class="input-group-addon dw" name="zq_dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">D</span>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">飞行时间</div>
							<div class="font-size-9 line-height-18">Flight Time</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
							<div class="input-group"> 
								<input class="form-control" id="2_10_FH" type="text" onkeyup='bottom_hidden_content.onkeyup4Num(this)'>
								<input class="form-control" type="hidden" />
								<span class="input-group-addon dw" name="zq_dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FH</span>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">飞行循环</div>
							<div class="font-size-9 line-height-18">Flight Cycle</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group"> 
								<input class="form-control "  id="3_10_FC" type="text" onkeyup='bottom_hidden_content.clearNoNum(this)'>
								<input class="form-control" type="hidden" />
								<span class="input-group-addon dw" name="zq_dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FC</span>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">APU时间</div>
							<div class="font-size-9 line-height-18">APU Time</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group"> 
								<input class="form-control" id="2_20_AH" type="text" onkeyup='bottom_hidden_content.onkeyup4Num(this)'>
								<input class="form-control" type="hidden" />
								<span class="input-group-addon dw" name="zq_dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">AH</span>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">APU循环</div>
							<div class="font-size-9 line-height-18">APU Cycle</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group"> 
								<input class="form-control "  id="3_20_AC" type="text" onkeyup='bottom_hidden_content.clearNoNum(this)'>
								<input class="form-control" type="hidden" />
								<span class="input-group-addon dw" name="zq_dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">AC</span>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">发动机时间</div>
							<div class="font-size-9 line-height-18">Engine Time</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group"> 
								<input class="form-control "  id="2_30_EH" type="text" onkeyup='bottom_hidden_content.onkeyup4Num(this)'>
								<input class="form-control" type="hidden" />
								<span class="input-group-addon dw" name="zq_dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">EH</span>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">发动机循环</div>
							<div class="font-size-9 line-height-18">Engine Cycle</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group"> 
								<input class="form-control "  id="3_30_EC" type="text" onkeyup='bottom_hidden_content.clearNoNum(this)'>
								<input class="form-control" type="hidden" />
								<span class="input-group-addon dw" name="zq_dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">EC</span>
							</div>
						</div>
					</div>
					<div class='clearfix'></div>
				</div>
		 	 	<div class="text-right margin-top-0 margin-right-0 ">
					<button id="planSave" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:bottom_hidden_content.save();">
	                   	<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
					</button>
	           	</div>
			</div>
		</div>
  	  </div>
	<div class='clearfix'></div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/aircraftmonitorsetting/aircraftmonitor_info.js"></script>
