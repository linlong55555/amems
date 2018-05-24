<%@ page contentType="text/html; charset=utf-8" language="java" %>

			

    <div class="input-group-border">
		<form id="maintenance_project_form">
			<input id="common_id" type="hidden" />
			<input id="common_zt" type="hidden" />
			<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
			
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2"><span style="color: red" class="identifying">*</span>飞机注册号</div>
						<div class="font-size-9">A/C REG</div>
					</label>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<select id="common_fjzch" class="form-control" onchange="flb_detail.changePlane()">
					   </select>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2"><span style="color: red" class="identifying">*</span>日期</div>
						<div class="font-size-9">Date</div>
					</label>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="common_fxrq" type="text">
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2"><span style="color: red" class="identifying">*</span>记录本页码</div>
						<div class="font-size-9">Page</div>
					</label>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input class="form-control" id="common_jlbym" name="" type="text">
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="common_fxjlbh_div">
					<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">飞行记录单号</div>
						<div class="font-size-9">FLB No.</div>
					</label>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<span id="common_fxjlbh" class="font-size-18" style="line-height:1.8;"></span>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		
		</form>
		<div class="clearfix"></div>
		<div class="col-lg-12 padding-left-0 padding-right-0">
			<%@ include file="/WEB-INF/views/produce/flb2/flightlogbook_record.jsp" %> <!--飞行数据  -->
		</div>
		<div class="clearfix"></div>
	</div>
	
	
	<div class="">
		<ul class="nav nav-tabs tabNew-style" role="tablist" id="tablist">
			<li class="active">
		  		<a href="#work_hd1" onclick="" data-toggle="tab" hd="1" hdms="航前">
		  			<div class="font-size-12 line-height-12">工作故障报告</div>
	                <div class="font-size-9 line-height-9">Work Failure Report</div>
		  		</a>
		  	</li>
		</ul>
		<div class="tab-content margin-bottom-10  padding-leftright-8">
			<div class="tab-pane fade active in margin-top-0" id="work_hd1" hd="1" hdms="航前">
               	<%@ include file="/WEB-INF/views/produce/flb2/flightlogbook_work.jsp"%>
            </div>
		</div>
	</div>
	
	<div class="clearfix"></div>
	
	
	<div class="panel panel-primary">
		<div class="panel-heading bg-panel-heading">
			<div class="font-size-12">故障保留单</div>
			<div class="font-size-9">Fault Reservation</div>
		</div>
		<div class="panel-body padding-leftright-8 padding-bottom-0">
			<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0" style="overflow-x:auto;">
				<!-- start:table -->
				<table class="table table-bordered table-striped table-thin table-hover text-center table-set">
					<thead>
				   		<tr>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-12">故障保留单号</div>
								<div class="font-size-9 line-height-12">Fault Reservation No.</div>
							</th>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-12">MEL类型</div>
								<div class="font-size-9 line-height-12">MEL type</div>
							</th>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-12">保留期限</div>
								<div class="font-size-9 line-height-12">Expiry Date</div>
							</th>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-12">关闭日期</div>
								<div class="font-size-9 line-height-12">Close Date</div>
							</th>
							<th class="colwidth-20">
								<div class="font-size-12 line-height-12">首次保留内容</div>
								<div class="font-size-9 line-height-12">First Retain</div>
							</th>
							<th class="colwidth-20">
								<div class="font-size-12 line-height-12">再次保留内容</div>
								<div class="font-size-9 line-height-12">Again Retain</div>
							</th>
							<th class="colwidth-20">
								<div class="font-size-12 line-height-12">故障描述</div>
								<div class="font-size-9 line-height-12">Fault Desc</div>
							</th>
				 		 </tr>
					</thead>
					<tbody id="flb_fault_reservation_table_list"><tr style="height:35px;"><td colspan="7" style="text-align:center;vertical-align:middle;">暂无数据 No data.</td></tr></tbody>
				</table>
				<!-- end:table -->
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	
	<div id="attachments_flb_edit">
		<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
	</div>
 	<div class="clearfix"></div>
 	