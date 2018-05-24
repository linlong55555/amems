<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/produce/task_maintenance_info.js"></script><!--当前界面js  -->
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<div class="panel panel-default" id="m_task_info_view">
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
					<div class="font-size-12 margin-topnew-2">任务号</div>
					<div class="font-size-9">Task No.</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input value="" class="form-control" id="rwdh" type="text" maxlength="50" readonly/>
				</div>
			</div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">参考号</div>
					<div class="font-size-9">Ref No.</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input value="" class="form-control" id="ckh" type="text" maxlength="50" readonly/>
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
					<div class="font-size-12 margin-topnew-2">飞机注册号</div>
					<div class="font-size-9">A/C REG</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input value="" class="form-control" id="fjzch" type="text" maxlength="50" readonly/>
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
					<div class="font-size-12 margin-topnew-2">工卡编号</div>
					<div class="font-size-9">Work Card No.</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input value="" class="form-control" id="gkh" type="text" maxlength="50" readonly/>
				</div>
			</div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">工卡附件</div>
					<div class="font-size-9">Attachment</div>
				</label>
				<div id="gkfj" class="tag-set col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 margin-top-5">
				</div>
			</div>
			
			<div class="clearfix"></div>
			
			<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">任务描述</div>
					<div class="font-size-9">Description</div>
				</label>
				<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
					<input value="" id="rwms" name="rwms" type="text" class="form-control" readonly />
				</div>
			</div>
			
			<div class="clearfix"></div>
			
			<div id="m_last_history" class="col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 5px;">
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