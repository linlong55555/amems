<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="SurplusWinModal" tabindex="-1" role="dialog"  aria-labelledby="SurplusWinModal" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:30%;'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
							<div class="font-size-14" id="modalHeadCN">剩余</div>
							<div class="font-size-12" id="modalHeadENG">Remain</div>
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
              	
                    <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">日历</div>
							<div class="font-size-9">Calendar</div>
						</label>
						<div id="sy_e" class="col-xs-9 padding-leftright-8">
							<div class="input-group col-xs-12">
								<input class="form-control" onkeyup='SurplusWinModal.vilidateMonitorData(this)' id="rlsy_e" type="text" />
								<div class="input-group-btn" style="min-width:45px;">
								<button type="button" class="btn btn-default" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="min-width:45px;height:34px;padding:0 0 0 0;color: #555!important;background-color:white;"><span id="rl_btn_v">D</span>&nbsp;<span class="caret"></span></button>
							    <ul class="dropdown-menu dropdown-menu-right" style="min-width: 46px;">
								    <li><a href="#" value="11" onclick="SurplusWinModal.checkedRl(this)" class="calendarUnit">D</a></li>
								    <li><a href="#" value="12" onclick="SurplusWinModal.checkedRl(this)" class="calendarUnit">M</a></li>
							    </ul>
							    </div>
							    <span class="input-group-addon inputgroupbordernone" style=''>
							    	<label class='margin-left-5 label-input' >
							    		<input id="rlsy_e_cb" type='checkbox' />
							    		&nbsp;含空
							    	</label>
							    </span>
						    </div>
						</div>
						
						<div id="jh_e" class="col-xs-9 padding-leftright-8">
							<div class="input-group col-xs-12">
								<input class="form-control datetimepicker" onchange='SurplusWinModal.vilidateDate(this)' id="rljh_e" type="text" />
							    <span class="input-group-addon inputgroupbordernone" style=''>
							    	<label class='margin-left-5 label-input' >
							    		<input id="rljh_e_cb" type='checkbox' />
							    		&nbsp;含空
							    	</label>
							    </span>
						    </div>
						</div>
						
					</div>
					<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">飞行时间</div>
							<div class="font-size-9">Flight Time</div>
						</label>
						<div class="col-xs-9 padding-leftright-8" >
							<div class="input-group col-xs-12">
								<input class="form-control" onkeyup='SurplusWinModal.vilidateMonitorData(this)' id="fxsj_e" type="text" />
								<span id="fxsj_e_dw" class='input-group-addon dw' style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>FH</span>
							    <span class="input-group-addon inputgroupbordernone" style=''>
							    	<label class='margin-left-5 label-input' >
							    		<input id="fxsj_e_cb" type='checkbox' />
							    		&nbsp;含空
							    	</label>
							    </span>
						    </div>
						</div>
					</div>
					<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">飞行循环</div>
							<div class="font-size-9">Flight Cycle</div>
						</label>
						<div class="col-xs-9 padding-leftright-8" >
							<div class="input-group col-xs-12">
								<input class="form-control" onkeyup='SurplusWinModal.vilidateMonitorData(this)' id="fxxh_e" type="text" />
								<span id="fxxh_e_dw" class='input-group-addon dw' style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>FC</span>
							    <span class="input-group-addon inputgroupbordernone" style=''>
							    	<label class='margin-left-5 label-input' >
							    		<input id="fxxh_e_cb" type='checkbox' />
							    		&nbsp;含空
							    	</label>
							    </span>
						    </div>
						</div>
					</div>
					<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">APU时间</div>
							<div class="font-size-9">APU Time</div>
						</label>
						<div class="col-xs-9 padding-leftright-8" >
							<div class="input-group col-xs-12">
								<input class="form-control" onkeyup='SurplusWinModal.vilidateMonitorData(this)' id="apusj_e" type="text" />
								<span id="apusj_e_dw" class='input-group-addon dw' style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>APUH</span>
							    <span class="input-group-addon inputgroupbordernone" style=''>
							    	<label class='margin-left-5 label-input' >
							    		<input id="apusj_e_cb" type='checkbox' />
							    		&nbsp;含空
							    	</label>
							    </span>
						    </div>
						</div>
					</div>
					<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">APU循环</div>
							<div class="font-size-9">APU Cycle</div>
						</label>
						<div class="col-xs-9 padding-leftright-8" >
							<div class="input-group col-xs-12">
								<input class="form-control" onkeyup='SurplusWinModal.vilidateMonitorData(this)' id="apuxh_e" type="text" />
								<span id="apuxh_e_dw" class='input-group-addon dw' style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>APUC</span>
							    <span class="input-group-addon inputgroupbordernone" style=''>
							    	<label class='margin-left-5 label-input' >
							    		<input id="apuxh_e_cb" type='checkbox' />
							    		&nbsp;含空
							    	</label>
							    </span>
						    </div>
						</div>
					</div>
					<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">发动机时间</div>
							<div class="font-size-9">Engine Time</div>
						</label>
						<div class="col-xs-9 padding-leftright-8" >
							<div class="input-group col-xs-12">
								<input class="form-control" onkeyup='SurplusWinModal.vilidateMonitorData(this)' id="fdjsj_e" type="text" />
								<span id="fdjsj_e_dw" class='input-group-addon dw' style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>EH</span>
							    <span class="input-group-addon inputgroupbordernone" style=''>
							    	<label class='margin-left-5 label-input' >
							    		<input id="fdjsj_e_cb" type='checkbox' />
							    		&nbsp;含空
							    	</label>
							    </span>
						    </div>
						</div>
					</div>
					<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">发动机循环</div>
							<div class="font-size-9">Engine Cycle</div>
						</label>
						<div class="col-xs-9 padding-leftright-8" >
							<div class="input-group col-xs-12">
								<input class="form-control" onkeyup='SurplusWinModal.vilidateMonitorData(this)' id="fdjxh_e" type="text" />
								<span id="fdjxh_e_dw" class='input-group-addon dw' style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>EC</span>
							    <span class="input-group-addon inputgroupbordernone" style=''>
							    	<label class='margin-left-5 label-input' >
							    		<input id="fdjxh_e_cb" type='checkbox' />
							    		&nbsp;含空
							    	</label>
							    </span>
						    </div>
						</div>
					</div>
					<div class="clearfix"></div>
					</div>
						 
				</div>
                <div class="clearfix"></div>              
           	</div>	
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
		                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
	                    	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="SurplusWinModal.setData()">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="SurplusWinModal.clearSurplus()">
								<div class="font-size-12">清空</div>
								<div class="font-size-9">Clear</div>
							</button>
		                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="SurplusWinModal.close()" >
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
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/surplus.js"></script>