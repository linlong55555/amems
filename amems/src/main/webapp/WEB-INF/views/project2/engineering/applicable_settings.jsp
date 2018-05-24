<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="panel panel-primary">
	<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
		<a data-toggle="collapse" data-parent="#accordion" href="#appSettingCollapsed" class="collapsed">
		<div class="pull-left">
		<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
		</div>
		<div class="pull-left">
		
		<div class="font-size-12">适用性设置</div>
		<div class="font-size-9 ">Applicability setting</div>
		</div>
		<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
		<div class="clearfix"></div>
		</a>
	</div>
	<div id="appSettingCollapsed" class="panel-collapse collapse" >
	
		<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;padding-top:8px;'>

			<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
				<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
					<div class="font-size-12 margin-topnew-2">适用类别</div>
					<div class="font-size-9">Category</div>
				</span>
				<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<label class='margin-right-5 label-input'><input  checked="checked"  name="sylb_public" type="radio" maxlength="8" value="1" onchange="eo_add_alert_Modal.initJkxszWinBySylb();">&nbsp;飞机&nbsp;&nbsp;</label>
						<label class='label-input'><input   name="sylb_public" type="radio" maxlength="8"  value="2" onchange="eo_add_alert_Modal.initJkxszWinBySylb();">&nbsp;发动机&nbsp;&nbsp;</label>
						<label class='label-input'><input   name="sylb_public" type="radio" maxlength="8" value="3"  onchange="eo_add_alert_Modal.initJkxszWinBySylb();">&nbsp;APU&nbsp;&nbsp;</label>
						<label class='label-input'><input   name="sylb_public" type="radio" maxlength="8" value="99" onchange="eo_add_alert_Modal.initJkxszWinBySylb();">&nbsp;部件</label>&nbsp;&nbsp;
				 	    <label class='label-input' id="qbsyLable"><input type='checkbox' name='qbsy' id="qbsyInput" onclick="applicable_settings.loadAllFj();"/>&nbsp;全部适用</label>
				</div>
			</div>
			<div class="clearfix"></div>
			
			<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
	    <label class="col-lg-1  col-md-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2"><span style="color: red" class='control-important' id="syxlbredspan">*</span>适用列表</div>
			<div class="font-size-9">List</div>
		</label>
		<div class="col-lg-11 col-md-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
		  <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
			
						<table  class="table table-thin table-bordered table-striped table-hover table-set " id="applicable_settings_table">
							<thead>
								<tr>
								    <th width="50" id="applicable_settings_operationTd">
									   <button class="line6 line6-btn" onclick="eo_add_alert_Modal.selectsyx()" type="button">
										<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
									   </button>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-12">飞机注册号/件号</div>
							           <div class="font-size-9 line-height-12">A/C Reg./PN</div>
								   </th>
								   <!-- class='sorting' -->
								   <th >
									   <div class="font-size-12 line-height-12">序列号</div>
							           <div class="font-size-9 line-height-12">SN</div>
								   </th>
								    
								   <th >
									   <div class="font-size-12 line-height-12">型号</div>
							           <div class="font-size-9 line-height-12">Model</div>
								   </th>
								   <!-- <th>
									   <div class="font-size-12 line-height-12">下发状态</div>
							           <div class="font-size-9 line-height-12">Status</div>
								   </th> -->
								</tr>
							</thead>
							
							<tbody id="applicable_settings_list">

							</tbody>
					</table>
				</div>
				</div>
				</div>
			<div class='clearfix'></div>
			<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
					<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
					<div class="font-size-12 ">源文件使用范围</div>
					<div class="font-size-9 ">Usage Scope</div>
				</span>
				<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 padding-right-0">
					<textarea class="form-control" id="ywjsyfw_public" placeholder="" maxlength="900" style='height:55px;'></textarea>
				</div>
			</div>
			<div class="clearfix"></div>
			
		</div>
	</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineering/applicable_settings.js"></script> <!-- 当前页脚本 -->				
<!--  弹出框结束-->