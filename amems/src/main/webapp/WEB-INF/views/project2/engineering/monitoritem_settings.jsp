<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	<div class="panel panel-primary">
    	<!-- panel-heading -->
		<div class="panel-heading bg-panel-heading" >
			<div class="font-size-12 ">监控项设置</div>
			<div class="font-size-9">Monitor Settings</div>
		</div>
		<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;padding-right:0px;'>
 			 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">执行方式</div>
					<div class="font-size-9">Implement way</div>
				</span>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<label class='margin-right-5 label-input' ><input  checked="checked"  id="maintenance_schedule_modal_bb" name="bb" type="radio" maxlength="8"  >&nbsp;单次&nbsp;&nbsp;</label>
					<label class=' label-input' ><input   id="maintenance_schedule_modal_bb" name="bb" type="radio" maxlength="8"  >&nbsp;重复&nbsp;&nbsp;</label>
					<label class=' label-input' ><input   id="maintenance_schedule_modal_bb" name="bb" type="radio" maxlength="8"  >&nbsp;分段</label>
				</div>
			</div>
			<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								
							</span>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group input-group-new">
			                    <span class="input-group-addon">
			                     <label>
			                      <input  checked="checked"  id="maintenance_schedule_modal_bb" name="bb" type="checkbox" maxlength="8"  >&nbsp;后到为准&nbsp;&nbsp;
			                     </label>
			                    </span>
			                    <span class="input-group-addon">
			                     <label>
			                    <input  id="maintenance_schedule_modal_bb" name="bb" type="checkbox" maxlength="8"  >&nbsp;终止条件&nbsp;&nbsp;
			                     </label>
			                    </span>
			                    <input type="text" class="form-control" >
			                	</div><!-- /input-group -->
							</div>
						</div>
			 
			<div class='clearfix'></div>
			
			<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
				<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
					<div class="font-size-12 "></div>
					<div class="font-size-9 "></div>
				</span>
				<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-leftright-8 margin-bottom-8">
				<label class='margin-right-5 label-input' ><input  checked="checked"  id="maintenance_schedule_modal_bb" name="rl" type="checkbox" maxlength="8"  >&nbsp;&nbsp;日历&nbsp;&nbsp;</label>
						 <label class='label-input' ><input     id="maintenance_schedule_modal_bb" name="rl" type="checkbox" maxlength="8"  >&nbsp;&nbsp;飞行小时&nbsp;&nbsp;</label>
						 <label class='label-input' ><input     id="maintenance_schedule_modal_bb" name="rl" type="checkbox" maxlength="8"  >&nbsp;&nbsp;发动机小时&nbsp;&nbsp;</label>
						 <label class='label-input' ><input     id="maintenance_schedule_modal_bb" name="rl" type="checkbox" maxlength="8"  >&nbsp;&nbsp;APU小时&nbsp;&nbsp;</label>
						 <label class='label-input' ><input     id="maintenance_schedule_modal_bb" name="rl" type="checkbox" maxlength="8"  >&nbsp;&nbsp;飞行循环&nbsp;&nbsp;</label>
						 <label class='label-input' ><input     id="maintenance_schedule_modal_bb" name="rl" type="checkbox" maxlength="8"  >&nbsp;&nbsp;发动机循环&nbsp;&nbsp;</label>
						 <label class='label-input' ><input     id="maintenance_schedule_modal_bb" name="rl" type="checkbox" maxlength="8"  >&nbsp;&nbsp;APU循环&nbsp;&nbsp;</label>
						 
					<!-- 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label><input  checked="checked"  id="maintenance_schedule_modal_bb" name="rl" type="checkbox" maxlength="8"  >&nbsp;&nbsp;日历&nbsp;&nbsp;</label>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<input     id="maintenance_schedule_modal_bb" name="rl" type="checkbox" maxlength="8"  >&nbsp;&nbsp;飞行小时&nbsp;&nbsp;
						</div>
						
						 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<input     id="maintenance_schedule_modal_bb" name="rl" type="checkbox" maxlength="8"  >&nbsp;&nbsp;发动机小时&nbsp;&nbsp;
						</div>
						 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<input     id="maintenance_schedule_modal_bb" name="rl" type="checkbox" maxlength="8"  >&nbsp;&nbsp;APU小时&nbsp;&nbsp;
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<input     id="maintenance_schedule_modal_bb" name="rl" type="checkbox" maxlength="8"  >&nbsp;&nbsp;飞行循环&nbsp;&nbsp;
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<input     id="maintenance_schedule_modal_bb" name="rl" type="checkbox" maxlength="8"  >&nbsp;&nbsp;发动机循环&nbsp;&nbsp;
						</div>
						 
						 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<input     id="maintenance_schedule_modal_bb" name="rl" type="checkbox" maxlength="8"  >&nbsp;&nbsp;APU循环&nbsp;&nbsp;
						</div> -->
				</div>
			</div>	 
			
			<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">日历</div>
					<div class="font-size-9">TODO</div>
				</label>
				<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
					<div class="input-group" >
					    <span class="input-group-addon" style='background:none;border:0px;text-align:left;padding-left:0px;'>首次</span>
						<input type="text" id="jswjbh" class="form-control padding-left-3 padding-right-3" >
						<input type="hidden" id="jswjid" class="form-control">
						<div class="input-group-btn">
						     
							 <button type="button" style="height: 34px;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">单位 <span class="caret"></span></button>
						        <ul class="dropdown-menu dropdown-menu-right">
						          <li><a href="#">RMB</a></li>
						          <li><a href="#">RMB</a></li>
						        </ul>
						</div>
				    </div>
				</div>
			</div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">周期</div>
					<div class="font-size-9">Cycle</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<div class="input-group">
						<input type="text" id="jswjbh" class="form-control padding-left-3 padding-right-3" >
						<input type="hidden" id="jswjid" class="form-control">
						<div class="input-group-btn">
							 <button type="button" style="height: 34px;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">单位 <span class="caret"></span></button>
						        <ul class="dropdown-menu dropdown-menu-right">
						          <li><a href="#">RMB</a></li>
						          <li><a href="#">RMB</a></li>
						        </ul>
						</div>
				    </div>
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">容差</div>
					<div class="font-size-9">Tolerance</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<div class="input-group">
					  <span class="input-group-addon" id="basic-addon1" style='padding-left:2px;padding-right:2px;background:none;border:0px;'>-</span>
					  <input type="text" class="form-control"  aria-describedby="basic-addon1">
					  <span class="input-group-addon" id="basic-addon1" style='padding-left:2px;padding-right:2px;background:none;border:0px;'>+</span>
					  <input type="text" class="form-control"  aria-describedby="basic-addon1">
					  <div class="input-group-btn">
							 <button type="button" style="height: 34px;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">单位 <span class="caret"></span></button>
						        <ul class="dropdown-menu dropdown-menu-right">
						          <li><a href="#">RMB</a></li>
						          <li><a href="#">RMB</a></li>
						        </ul>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">日历</div>
					<div class="font-size-9">The calendar</div>
				</label>
				<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
					<div class="input-group">
					    <span class="input-group-addon" style='background:none;border:0px;text-align:left;padding-left:0px;'>首次</span>
						<input type="text" id="jswjbh" class="form-control padding-left-3 padding-right-3" >
						<input type="hidden" id="jswjid" class="form-control">
						<div class="input-group-btn">
						     
							 <button type="button" style="height: 34px;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">单位 <span class="caret"></span></button>
						        <ul class="dropdown-menu dropdown-menu-right">
						          <li><a href="#">RMB</a></li>
						          <li><a href="#">RMB</a></li>
						        </ul>
						</div>
				    </div>
				</div>
			</div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>周期</div>
					<div class="font-size-9">Cycle</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<div class="input-group">
						<input type="text" id="jswjbh" class="form-control padding-left-3 padding-right-3" >
						<input type="hidden" id="jswjid" class="form-control">
						<div class="input-group-btn">
							 <button type="button" style="height: 34px;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">单位 <span class="caret"></span></button>
						        <ul class="dropdown-menu dropdown-menu-right">
						          <li><a href="#">RMB</a></li>
						          <li><a href="#">RMB</a></li>
						        </ul>
						</div>
				    </div>
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>容差</div>
					<div class="font-size-9">Tolerance</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<div class="input-group">
					  <span class="input-group-addon" id="basic-addon1" style='padding-left:2px;padding-right:2px;background:none;border:0px;'>-</span>
					  <input type="text" class="form-control"  aria-describedby="basic-addon1">
					  <span class="input-group-addon" id="basic-addon1" style='padding-left:2px;padding-right:2px;background:none;border:0px;'>+</span>
					  <input type="text" class="form-control"  aria-describedby="basic-addon1">
					  <div class="input-group-btn">
							 <button type="button" style="height: 34px;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">单位 <span class="caret"></span></button>
						        <ul class="dropdown-menu dropdown-menu-right">
						          <li><a href="#">RMB</a></li>
						          <li><a href="#">RMB</a></li>
						        </ul>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">日历</div>
					<div class="font-size-9">The calendar</div>
				</label>
				<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
					<div class="input-group">
					    <span class="input-group-addon" style='background:none;border:0px;text-align:left;padding-left:0px;'>首次</span>
						<input type="text" id="jswjbh" class="form-control padding-left-3 padding-right-3" >
						<input type="hidden" id="jswjid" class="form-control">
						<div class="input-group-btn">
						     
							 <button type="button" style="height: 34px;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">单位 <span class="caret"></span></button>
						        <ul class="dropdown-menu dropdown-menu-right">
						          <li><a href="#">RMB</a></li>
						          <li><a href="#">RMB</a></li>
						        </ul>
						</div>
				    </div>
				</div>
			</div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>周期</div>
					<div class="font-size-9">Cycle</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<div class="input-group">
						<input type="text" id="jswjbh" class="form-control padding-left-3 padding-right-3" >
						<input type="hidden" id="jswjid" class="form-control">
						<div class="input-group-btn">
							 <button type="button" style="height: 34px;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">单位 <span class="caret"></span></button>
						        <ul class="dropdown-menu dropdown-menu-right">
						          <li><a href="#">RMB</a></li>
						          <li><a href="#">RMB</a></li>
						        </ul>
						</div>
				    </div>
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>容差</div>
					<div class="font-size-9">Tolerance</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<div class="input-group">
					  <span class="input-group-addon" id="basic-addon1" style='padding-left:2px;padding-right:2px;background:none;border:0px;'>-</span>
					  <input type="text" class="form-control"  aria-describedby="basic-addon1">
					  <span class="input-group-addon" id="basic-addon1" style='padding-left:2px;padding-right:2px;background:none;border:0px;'>+</span>
					  <input type="text" class="form-control"  aria-describedby="basic-addon1">
					  <div class="input-group-btn">
							 <button type="button" style="height: 34px;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">单位 <span class="caret"></span></button>
						        <ul class="dropdown-menu dropdown-menu-right">
						          <li><a href="#">RMB</a></li>
						          <li><a href="#">RMB</a></li>
						        </ul>
						</div>
					</div>
				</div>
			</div>
			<div class='clearfix'></div>
		</div>
	</div>
					
<!--  弹出框结束-->
<script type="text/javascript">

	$(function(){
		
	})

</script>
