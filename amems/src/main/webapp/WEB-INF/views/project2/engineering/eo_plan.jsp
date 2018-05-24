<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
 <div class="panel panel-primary">
	<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
		<a data-toggle="collapse" data-parent="#accordion" href="#planningCollapsed" class="collapsed">
		<div class="pull-left">
		<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
		</div>
		<div class="pull-left">
		<div class="font-size-12">计划 </div>
		<div class="font-size-9 ">Planning</div>
		</div>
		<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
		<div class="clearfix"></div>
		</a>
	</div>
	<div id="planningCollapsed" class="panel-collapse collapse" >
		<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
		<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
								<div class="font-size-12 ">工程建议</div>
								<div class="font-size-9 ">Project proposal</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								<textarea class="form-control" id="gcjy_add"  name=""  maxlength="1000" style='height:55px;'></textarea>
							</div>
						</div>	 
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
								<div class="font-size-12 ">生产安排</div>
								<div class="font-size-9 ">Production schedule</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								<textarea class="form-control" id="scap_add"  name=""  maxlength="1000" style='height:55px;'></textarea>
							</div>
						</div>
					 
					<div class="clearfix"></div>
			
					<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
							<div class="font-size-12 ">原因及描述</div>
							<div class="font-size-9 ">Reason and Description</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
							<textarea class="form-control" id="yyjms_add"  name=""  maxlength="1000" style='height:55px;'></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
							<div class="font-size-12 ">处理措施</div>
							<div class="font-size-9 ">Action</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								<textarea class="form-control" id="clcs_add"  name=""  maxlength="1000" style='height:55px;'></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
		</div>
</div>
</div>