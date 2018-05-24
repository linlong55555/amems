<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/produce/task_eo_info.js"></script><!--当前界面js  -->
<div class="panel panel-default" id="eo_task_info_view">
	<!--标题 STATR -->
	<div class="panel-heading bg-panel-heading">
		<div class="font-size-12 ">任务信息</div>
		<div class="font-size-9">Task Info</div>
	</div>
	<!--标题ENG  -->
	<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	  	 	
		<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
		
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">EO编号</div>
					<div class="font-size-9">EO No.</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input value="" class="form-control" id="eobh" type="text" maxlength="50" readonly/>
				</div>
			</div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">版本</div>
					<div class="font-size-9">Rev.</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input value="" class="form-control" id="bb" type="text" maxlength="50" readonly/>
				</div>
			</div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">ATA章节号</div>
					<div class="font-size-9">ATA</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input value="" class="form-control" id="zjh" type="text" maxlength="50" readonly/>
				</div>
			</div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">EO主题</div>
					<div class="font-size-9">EO</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input value="" class="form-control" id="eozt" type="text" maxlength="50" readonly/>
				</div>
			</div>
			
			<div class="clearfix"></div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">件号</div>
					<div class="font-size-9">P/N</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input value="" class="form-control" id="bjh" type="text" maxlength="50" readonly/>
				</div>
			</div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">序列号</div>
					<div class="font-size-9">S/N</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input value="" class="form-control" id="xlh" type="text" maxlength="50" readonly/>
				</div>
			</div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">型号</div>
					<div class="font-size-9">Model</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input value="" class="form-control" id="xh" type="text" maxlength="50" readonly/>
				</div>
			</div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">执行方式</div>
					<div class="font-size-9">Execution</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input value="" class="form-control" id="zxfs" type="text" maxlength="50" readonly/>
				</div>
			</div>
			
			<div class="clearfix"></div>
			
			<div id="eo_last_history" class="col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 5px;">
				<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">上版本执行历史</div>
					<div class="font-size-9">History</div>
				</label>
				<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
					<%@ include file="/WEB-INF/views/common/produce/last_plan.jsp" %> <!-- 上次计划 -->	
				</div>
			</div>
		
		</div>
	</div>
</div>
