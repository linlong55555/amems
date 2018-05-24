<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<div  class="panel panel-primary" id='measure_assessment_div'>
	<div class="panel-heading bg-panel-heading" >
		<div class="font-size-12 ">纠正及预防措施评估</div>
		<div class="font-size-9">Corrective & preventive measures</div>
	</div>
	<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
	<div class="col-xs-12 margin-top-0 padding-left-0 padding-right-0">
	<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
		<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
		<label  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
			
		</label>
		<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
			<label style='width:200px;text-align:left;'>上述纠正及预防是否切实有效？</label>
			<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
				<input type='radio' name='isyx' value='1' style='vertical-align:text-bottom'  checked="checked" />&nbsp;是&nbsp;&nbsp;
			</label>
			<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
				<input type='radio' name='isyx' value='0' style='vertical-align:text-bottom;' />&nbsp;否&nbsp;&nbsp;
			</label>
		</div>
	</div>
	<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
		<label  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
			
		</label>
		<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
			 <label style='width:200px;text-align:left;'>上述纠正及预防是否具有可操作性？</label>
			<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
				<input type='radio' name='iscz' value='1' style='vertical-align:text-bottom'  checked="checked" />&nbsp;是&nbsp;&nbsp;
			</label>
			<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
				<input type='radio' name='iscz' value='0' style='vertical-align:text-bottom;' />&nbsp;否&nbsp;&nbsp;
			</label>
		</div>
	</div>
	<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
		<label  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
			
		</label>
		<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
			<label style='width:200px;text-align:left;'>上述纠正及预防是否具经济可行？</label>
			<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
				<input type='radio' name='iskx' value='1' style='vertical-align:text-bottom'  checked="checked" />&nbsp;是&nbsp;&nbsp;
			</label>
			<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
				<input type='radio' name='iskx' value='0' style='vertical-align:text-bottom;' />&nbsp;否&nbsp;&nbsp;
			</label>
		</div>
	</div>
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="pgrdiv">
		<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
				<div class="font-size-12  margin-topnew-2">评估人</div>
				<div class="font-size-9 ">Evaluator</div>
		</label>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
			<input type="text" class="form-control" maxlength="15" id="pgr" readonly/>
		</div>
	</div>
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="pgsjdiv">
		<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
				<div class="font-size-12  margin-topnew-2">评估时间</div>
				<div class="font-size-9 ">Date</div>
		</label>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
			<input type="text" class="form-control" maxlength="16" id="pgsj" readonly/>
		</div>
	</div>
	</div>
	<div class='clearfix'></div>
	 </div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/reviewreformmeasures/measures_assessment.js"></script>