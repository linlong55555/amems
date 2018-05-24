<%@ page contentType="text/html; charset=utf-8" language="java" %>

<!-- 手工输入 -->
<div class="modal fade modal-new" id="open_win_flightlogbook_manual_modal" tabindex="-1" role="dialog" aria-labelledby="open_win_flightlogbook_manual_modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:60%">
		<div class="modal-content">	
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
                	<div class='pull-left'>
	                    <div class="font-size-14">完成工作</div>
						<div class="font-size-12">Finished Task</div>
			  		</div>
				  	<div class='pull-right'>
				  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				  	</div>
			  		<div class='clearfix'></div>
               	</h4>
            </div>
			<div class="modal-body padding-bottom-0">
				<div class="col-xs-12 margin-top-8">
					<form id="open_win_flightlogbook_manual_modal_form">
					<div class="input-group-border">
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">来源类型</div>
								<div class="font-size-9">Source Type</div>
							</label>
							<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10 padding-leftright-8">
								<input class="form-control" id="manual_modal_gdlx" disabled="disabled" type="text">
							</div>
						</div>
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="manual_modal_gdbh_div">
							<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工单编号</div>
								<div class="font-size-9">W/O No.</div>
							</label>
							<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10 padding-leftright-8">
								<input class="form-control" id="manual_modal_gdbh" disabled="disabled" type="text">
							</div>
						</div>
						<div class="clearfix"></div>
						
						
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0">
							<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>完成时间</div>
								<div class="font-size-9">Finish Time</div>
							</label>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 padding-left-8 padding-right-0 form-group">
								<input class="form-control date-picker" data-date-format="yyyy-mm-dd" id="manual_modal_wcrq" name="wcrq" maxlength="10" type="text">
							</div>
							<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 padding-left-0 padding-right-8">
								<input class="form-control time-masked" id="manual_modal_wcsj" name="wcsj" type="text" style="border-left: 0;">
							</div>
						</div>
						
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">实际工时</div>
								<div class="font-size-9">MHRs</div>
							</label>
							<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7 padding-leftright-8">
								<!-- <div class="input-group input-group-new">
									<input maxlength="8" class="form-control " id="manual_modal_sjgs" onkeyup="flb_manual.clearNoNumber(this)" type="text">
			                    	<span class="input-group-addon">人&nbsp;x</span>
			                    	<input maxlength="6" class="form-control" id="manual_modal_sjrs" onkeyup="flb_manual.clearNoNumTwo(this)" type="text">
			                        <span class="input-group-addon">时=
			                        	<span id="manual_modal_total">0</span>时
			                        </span>
			                	</div> -->
			                	<input maxlength="8" class="form-control" id="manual_modal_sjgs" type="text" onkeyup="flb_manual.clearNoNumTwo(this)">
			                	<input class="form-control" id="manual_modal_sjrs" type="hidden">
							</div>
							<div class="col-xs-3 col-sm-3 padding-left-9 padding-right-0">
								<label class="pull-right" style="margin-top:6px;font-weight:normal;">
									<input id="manual_modal_hsgs" value="1" style="vertical-align:text-bottom" type="checkbox">&nbsp;核算工时&nbsp;&nbsp;
								</label>
							</div>
						</div>
						<div class="clearfix"></div>
						
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工作者</div>
								<div class="font-size-9">Worker</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
								<input class="form-control" id="manual_modal_gzzid" type="hidden">
								<div class="input-group">
									<input id="manual_modal_gzz" class="form-control readonly-style" type="text" readonly="readonly" maxlength="100">
				                    <span class="input-group-addon btn btn-default" id="manual_modal_gzz_btn" onclick="flb_manual.openGzzWin()">
				                    	<i class="icon-search cursor-pointer"></i>
				                    </span>
			                	</div>
							</div>
						</div>
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">检查者</div>
								<div class="font-size-9">Checker</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
								<input class="form-control" id="manual_modal_jczid" type="hidden">
								<div class="input-group">
									<input id="manual_modal_jcz" class="form-control" type="text" maxlength="100">
				                    <span class="input-group-addon btn btn-default" id="manual_modal_jcz_btn" onclick="flb_manual.openJczWin()">
				                    	<i class="icon-search cursor-pointer"></i>
				                    </span>
			                	</div>
							</div>
						</div>
						<div class="clearfix"></div>
						
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工作站点</div>
								<div class="font-size-9">Station</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
								<input class="form-control" id="manual_modal_sjZd" type="text" maxlength="16">
							</div>
						</div>
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">故障报告</div>
								<div class="font-size-9">PIREP.</div>
							</label>
							<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10 padding-leftright-8">
								<select class="form-control" id="manual_modal_gzbg">
									<option value=""></option>
									<option value="机组">机组</option>
									<option value="地面">地面</option>
								</select>
							</div>
						</div>
						<div class="clearfix"></div>
						
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" id="manual_modal_gdbt_div">
							<label class="col-lg-1 col-md-1 col-sm-1 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">任务信息</div>
								<div class="font-size-9">Task Info</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 padding-leftright-8">
								<textarea class="form-control" id="manual_modal_gdbt" maxlength="1000" style="height: 55px;"></textarea>
							</div>
						</div>
						<div class="clearfix"></div>
						
						
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-1 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">故障信息</div>
								<div class="font-size-9">Fault</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 padding-leftright-8">
								<textarea class="form-control" id="manual_modal_gzxx" maxlength="1000" style="height: 55px;"></textarea>
							</div>
						</div>
						<div class="clearfix"></div>
						
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-1 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">处理措施</div>
								<div class="font-size-9">Action</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 padding-leftright-8">
								<textarea class="form-control" id="manual_modal_clcs" maxlength="1000" style="height: 55px;"></textarea>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
					</form>
					
					<div id="task_list_edit">
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					</div>
				 	<div class="clearfix"></div>
			 	</div>
			 	<div class="clearfix"></div>
			</div>
			
			<div class="clearfix"></div>
			<div class="modal-footer">
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
			                <i class='glyphicon glyphicon-info-sign alert-info' style="display: none;"></i><p class="alert-info-message"></p>
						</span>
						<span class="input-group-addon modalfooterbtn">
							<button id="formSave" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="flb_manual.save()">
			                   	<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
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

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/flb2/flightlogbook_manual.js"></script>