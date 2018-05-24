<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<div id="work_package_detail" class="panel panel-body margin-bottom-0 padding-top-0 padding-left-0 padding-right-0" >
	<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group" id="workorder_detail_searchDiv" >
		<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0" >
			<a href="javascript:work_package_detail.removeChecked();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left margin-right-10" >
				<div class="font-size-12">移除</div>
				<div class="font-size-9">Remove</div>
			</a>
		</div>
		<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0" >
		
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-0 label-input-div text-right" >
				<label style='margin-top:6px;margin-left:5px;font-weight:normal;' >
					<input onclick="work_package_detail.search();" name='gdzlx' value="1"  type='checkbox' style='vertical-align:text-bottom' />&nbsp;一般项目
				</label>
				<label style='margin-top:6px;margin-left:5px;font-weight:normal;' >
					<input onclick="work_package_detail.search();" name='gdzlx' value="2"  type='checkbox' style='vertical-align:text-bottom' />&nbsp;时控项目
				</label>
				<label style='margin-top:6px;margin-left:5px;font-weight:normal;' >
					<input onclick="work_package_detail.search();" name='gdzlx' value="3"  type='checkbox' style='vertical-align:text-bottom' />&nbsp;时寿项目
				</label>
				<label style='margin-top:6px;margin-left:5px;font-weight:normal;' >
					<input onclick="work_package_detail.search();" name='gdzlx' value="4"  type='checkbox' style='vertical-align:text-bottom' />&nbsp;定检项目
				</label>
				<label style='margin-top:6px;margin-left:5px;font-weight:normal;' >
					<input onclick="work_package_detail.search();" name='gdlx' value="2"  type='checkbox' style='vertical-align:text-bottom' />&nbsp;EO
				</label>
				<label style='margin-top:6px;margin-left:5px;font-weight:normal;' >
					<input isGd = "0" onclick="work_package_detail.search();" name='gdlx' value="6"  type='checkbox' style='vertical-align:text-bottom' />&nbsp;生产指令
				</label>
				<!-- <label style='margin-top:6px;margin-left:5px;font-weight:normal;' >
					<input isGd = "1" onclick="work_package_detail.search();" name='gdlx' value="3"  type='checkbox' style='vertical-align:text-bottom' />&nbsp;NRC
				</label> -->
				<label style='margin-top:6px;margin-left:5px;font-weight:normal;' >
					<input onclick="work_package_detail.search();" name='gdlx' value="4"  type='checkbox' style='vertical-align:text-bottom' />&nbsp;MO/MCC
				</label>
				<label style='margin-top:6px;margin-left:5px;font-weight:normal;' >
					<input onclick="work_package_detail.search();" name='gdlx' value="5"  type='checkbox' style='vertical-align:text-bottom' />&nbsp;其它指令
				</label>
			</div>
		</div>
		<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0" style='padding-left:15px;'>
			<div class="col-xs-12 input-group input-group-search">
				<input type="text" placeholder='任务信息/执行对象'  class="form-control" id="keyword_search" >
	            <div class="input-group-addon btn-searchnew" >
	              	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="work_package_detail.search();" style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
	               	</button>
	               	<button type="button" onclick="work_package_detail.exportExcel()" class=" btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode="produce:maintenance:monitoring:main:07" style='margin-right:0px !important;'>
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
	               	</button>
	            </div>	                
			</div>
		</div>
		<!-- <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 text-right pull-right padding-left-0 padding-right-0 margin-top-0 form-group" >
		</div> -->
	</div>	
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-bottom-0 padding-left-0 padding-right-0 padding-top-0 " >
		<div id="workorder_detail_top_div" class='padding-top-0 padding-left-0 padding-right-0 ' style='overflow-x:auto;'>
				<table id="workorder_detail_table" class="table table-thin table-bordered table-striped table-hover text-left table-set" style="min-width: 950px;">
					<thead>
				   		<tr>
				   			<th class="fixed-column colwidth-7 selectAllImg">
								<a href="#" onclick="SelectUtil.performSelectAll('workorder_detail_top_div')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
								<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('workorder_detail_top_div')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
							</th>
				   			<th class="colwidth-7">
								<div class="font-size-12 ">类型</div>
								<div class="font-size-9 ">Type</div>
							</th>
							<th class="colwidth-10">
								<div class="font-size-12 ">工单编号</div>
								<div class="font-size-9 ">WO No.</div>
							</th>
							<th class="colwidth-13">
								<div class="important">
									<div class="font-size-12 ">任务信息</div>
									<div class="font-size-9 ">Task Info</div>
								</div>
							</th>
							<th class="colwidth-7">
								<div class="font-size-12 ">关闭状态</div>
								<div class="font-size-9 ">Status</div>
							</th>
							<th class="colwidth-7">
								<div class="font-size-12 ">反馈状态</div>
								<div class="font-size-9 ">Status</div>
							</th>
							<th class="colwidth-10">
								<div class="important">
									<div class="font-size-12 ">执行对象</div>
									<div class="font-size-9 ">Executed</div>
								</div>
							</th>
							<th class="colwidth-7">
								<div class="font-size-12 ">计划</div>
								<div class="font-size-9 ">Plan</div>
							</th>
							<th class="colwidth-7">
								<div class="font-size-12 ">计划工时</div>
								<div class="font-size-9 ">Planned HRS</div>
							</th>
							<th class="colwidth-7">
								<div class="font-size-12 ">实际工时</div>
								<div class="font-size-9 ">Actual HRS</div>
							</th>
							<th class="colwidth-7">
								<div class="font-size-12 ">附件</div>
								<div class="font-size-9 ">Attachment</div>
							</th>
				 		 </tr>
					</thead>
					<tbody id="workorder_detail_list">
					
					</tbody>
				</table>
				</div>
			</div>
</div>

 <script type="text/javascript" src="${ctx }/static/js/thjw/common/produce/work_package_detail.js"></script>
 <script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>
