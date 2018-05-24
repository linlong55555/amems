<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	<div id="StoppingUtil" class="panel panel-primary" >
	<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
		<a data-toggle="collapse" data-parent="#accordion" href="#warrantyCollapsed" class="collapsed">
		<div class="pull-left">
		<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
		</div>
		<div class="pull-left">
		<div class="font-size-12">索赔</div>
		<div class="font-size-9 ">Warranty</div>
		</div>
		<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
		<div class="clearfix"></div>
		</a>
	</div>
	<div id="warrantyCollapsed" class="panel-collapse collapse" >
		<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
				<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 form-group" > 
	               	<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="col-xs-12 input-group input-group-new">
			                 <div class="input-group-addon" style='padding-top:0px;padding-left:10px;text-align:left;'>
			                 	<input type='checkbox' id='is_sp_qc_public' onchange="eo_main.setRedonly('sp_qcsm_public')" />
			                 </div>
		                      <div class="input-group-addon" style='text-align:right;color:#333;padding-top:0px;'>
			                      <div class="font-size-12 margin-topnew-2">器材</div>
								  <div class="font-size-9" style='margin-top:4px;'>Material</div>
		                      </div>
		                 </div>
					</label>
					<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<input class="form-control"  id="sp_qcsm_public"  name="" type="text" maxlength="100"  style="visibility:hidden;">
					</div>
				</div>
				<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 form-group"> 
	                	<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						 	<div class="font-size-12 margin-topnew-2">索赔信息</div>
							<div class="font-size-9">Warrant info</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<input class="form-control"  id="sp_jgxx_public"  name="" type="text" maxlength="100">
						</div>
				</div>
				<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 form-group"> 
	               	<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="col-xs-12 input-group input-group-new">
							  <div class="input-group-addon" style='padding-top:0px;padding-left:10px;text-align:left;'>
			                 	<input type='checkbox' id="is_sp_rg_public" onchange="eo_main.setRedonly('sp_rgsm_public')"/>
			                 </div>
		                      <div class="input-group-addon" style='text-align:right;color:#333;padding-top:0px;'>
			                     <div class="font-size-12 margin-topnew-2">人工</div>
								  <div class="font-size-9" style='margin-top:4px;'>Man-hours</div>
		                      </div>
		                    
		                 </div>
					</label>
					<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<input class="form-control" id="sp_rgsm_public"  name="" type="text" maxlength="100"  style="visibility: hidden">
					</div>
				</div>
				<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 form-group"> 
	               	<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">索赔期限</div>
						<div class="font-size-9">Warranty</div>
					</label>
					<div class="col-lg-4 col-md-6 col-sm-10 col-xs-9 padding-leftright-8">
						<input class="form-control datepicker" id="spqx_public"  data-date-format="yyyy-mm-dd" type="text" maxlength="10" >
					</div>
				</div>
	           	<div class='clearfix'></div>
		</div>
	</div>
	</div>				
<!--  弹出框结束-->
<script type="text/javascript">
	$(function(){
		
	});
</script>
