<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
     <div class="panel panel-primary">
	<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
		<a data-toggle="collapse" data-parent="#accordion" href="#weightCollapsed" class="collapsed">
		<div class="pull-left">
		<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
		</div>
		<div class="pull-left">
		<div class="font-size-12">重量与平衡 </div>
		<div class="font-size-9 ">Weight & Balance</div>
		</div>
		<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
		<div class="clearfix"></div>
		</a>
	</div>
	<div id="weightCollapsed" class="panel-collapse collapse" >
		<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;padding-right:0px;'>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">载重平衡变化</div>
								<div class="font-size-9 ">Weight & Balance</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<label class='margin-right-5 label-input' >
									<input  checked="checked" name="is_zzphbh_public" type="radio" maxlength="8" value="1" >&nbsp;是&nbsp;&nbsp;
								</label>
								<label class=' label-input' >
									<input name="is_zzphbh_public" type="radio" maxlength="8" value="0" >&nbsp;否
								</label>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">Mt Change</div>
								<div class="font-size-9 ">(±Lbs.in)</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control"  id="mtc_public" type="text" maxlength="100">
							</div>
						</div>
						 
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">							
								<div class="font-size-12 margin-topnew-2">Wt Change</div>
								<div class="font-size-9 ">(±Lbs)</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="wtc_public" type="text" maxlength="100">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">ARM</div>
								<div class="font-size-9 ">ARM</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="arm_public"  name="" type="text" maxlength="100">
							</div>
						</div>
						<div class="clearfix"></div>
						
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
								<div class="font-size-12 ">变化内容</div>
								<div class="font-size-9 ">Content</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
									<textarea class="form-control" id="bhnr_public"  maxlength="600" style='height:55px;'></textarea>
							</div>
						</div>	 
			<div class='clearfix'></div>
		</div>
	</div>
	</div>	 						
						 
					 
					
<!--  弹出框结束-->
<script type="text/javascript">

	$(function(){
		
	})

</script>
