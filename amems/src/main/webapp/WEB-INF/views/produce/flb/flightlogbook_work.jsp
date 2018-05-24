<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

<div>
	<div class="pull-left padding-left-10 padding-right-10">
		<div class="font-size-16 line-height-18">完成工作</div>
		<div class="font-size-9 margin-bottom-5 ">Finished Task</div>
	</div>
	
	<div class="pull-left padding-left-10 padding-right-10">
		<button class="btn btn-primary padding-top-1 padding-bottom-1 notView" type="button" onclick="flb_work.chooseWorkOrder(this)">
			<div class="font-size-12">选择</div>
			<div class="font-size-9">Choose</div>
		</button>
		
		<button class="btn btn-primary padding-top-1 padding-bottom-1 notView" type="button" onclick="flb_work.manual(this)">
			<div class="font-size-12">手工输入</div>
			<div class="font-size-9">Manual</div>
		</button>
	</div>
	
	<div class="col-lg-3 padding-left-10 padding-right-10 padding-bottom-5">
		<div class="col-lg-4 padding-left-0 padding-right-0 text-right">
			<div class="font-size-12 line-height-18">航站</div>
			<div class="font-size-9 line-height-18">Station</div>
			</div>
		<div class="col-lg-8 padding-left-8 padding-right-0">
			<input class="form-control" name="work_hz" maxlength="100" type="text">
		</div>
	</div>
	<div class="col-lg-3 padding-left-10 padding-right-10">
		<div class="col-lg-4 padding-left-0 padding-right-0 text-right">
			<div class="font-size-12 line-height-18">放行人</div>
			<div class="font-size-9 line-height-18">Release</div>
			</div>
		<div class="col-lg-8 padding-left-8 padding-right-0">
			<input class="form-control" name="work_jcrid" type="hidden">
			<div class="input-group">
				<input name="work_jcr" class="form-control" ondblclick="flb_work.openFxrWin(this)" type="text" maxlength="100">
	            <span class="input-group-addon btn btn-default" name="work_jcr_btn" onclick="flb_work.openFxrWin(this)">
	            	<i class="icon-search cursor-pointer"></i>
	            </span>
          	</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<small class="notView">双击完成工作可以进行编辑,附件需要保存后才能预览</small>
	<div class="col-xs-12 padding-left-0 padding-right-0"style="overflow-x: auto;" id="flb_work_wcgz_table_div">
		<table class="table table-thin table-bordered table-striped table-set" ">
			<thead class="nav-tabs">                              
				<tr>
					<th class="fixed-column colwidth-10">
						<div class="font-size-12 line-height-18">操作</div>
						<div class="font-size-9 line-height-18">Operation</div>
					</th>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-18">来源</div>
						<div class="font-size-9 line-height-18">Source</div>
					</th>
					<th class="colwidth-15">
						<div class="font-size-12 line-height-18">工单编号</div>
						<div class="font-size-9 line-height-18">W/O No.</div>
					</th>
					<th class="colwidth-30">
						<div class="font-size-12 line-height-18">任务信息</div>
						<div class="font-size-9 line-height-18">Task Info</div>
					</th>
					<th class="colwidth-25">
						<div class="font-size-12 line-height-18">机组/地面故障报告</div>
						<div class="font-size-9 line-height-18">PIREP.</div>
					</th>
					<th class="colwidth-25">
						<div class="font-size-12 line-height-18">故障信息</div>
						<div class="font-size-9 line-height-18">Fault</div>
					</th>
					<th class="colwidth-25">
						<div class="font-size-12 line-height-18">处理措施</div>
						<div class="font-size-9 line-height-18">Action</div>
					</th>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-18">实际工时</div>
						<div class="font-size-9 line-height-18">MHRs</div>
					</th>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-18">工作站点</div>
						<div class="font-size-9 line-height-18">Station</div>
					</th>
					<th class="colwidth-11">
						<div class="font-size-12 line-height-18">工作者</div>
						<div class="font-size-9 line-height-18">Worker</div>
					</th>
					<th class="colwidth-11">
						<div class="font-size-12 line-height-18">检查者</div>
						<div class="font-size-9 line-height-18">Checker</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">完成日期</div>
						<div class="font-size-9 line-height-18">Finish Date</div>
					</th>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-18">完成时间</div>
						<div class="font-size-9 line-height-18">Finish Time</div>
					</th>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-18">附件</div>
						<div class="font-size-9 line-height-18">Attachment</div>
					</th>
				</tr> 
			</thead>
			<tbody name="flb_work_table_list">
				<tr class="no-data"><td class="text-center" colspan="14">暂无数据 No data.</td></tr>
			</tbody>
		</table>
	</div>
	
	<div class="pull-left padding-left-10 padding-right-10 padding-top-10">
		<div class="font-size-16 line-height-18">防冰液记录</div>
		<div class="font-size-9 margin-bottom-5 ">Anti-ice Record</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-xs-8 padding-left-0 padding-right-0"style="overflow-x: auto;">
		<table class="table table-thin table-bordered table-striped table-set table-hover">
			<thead>
		  		<tr>
		  			<th class="colwidth-5 text-center notView">
						<button class="line6 line6-btn" onclick="flb_work.addAntiIceRow(this)" name="flb_anti_ice_add_btn">
							<i class="icon-plus cursor-pointer color-blue"></i>
						</button>
					</th>
					<th class="colwidth-20">
						<div class="font-size-12 line-height-12">防冰液类型 </div>
						<div class="font-size-9 line-height-12">Type</div>
					</th>
					<th class="colwidth-20">
						<div class="font-size-12 line-height-12">防冰代码</div>
						<div class="font-size-9 line-height-12">Code</div>
					</th>
					<th class="colwidth-15">
						<div class="font-size-12 line-height-12">开始时间</div>
						<div class="font-size-9 line-height-12">Start Time</div>
					</th>
					<th class="colwidth-15">
						<div class="font-size-12 line-height-12">保持时间</div>
						<div class="font-size-9 line-height-12">Keep</div>
					</th>
				 </tr>
			</thead>
			<tbody name="flb_anti_ice_table_list">
				<tr class="no-data"><td class="text-center" colspan="5">暂无数据 No data.</td></tr>
			</tbody>
		</table>
	</div>
</div>
