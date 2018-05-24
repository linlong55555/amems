<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div id="aircraft_maintenance_monitoring_main">
	<div class='searchContent margin-top-0 row-height' style='position:relative;'>
		<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" style='margin-bottom:18px;'>
			<a href="javascript:aircraft_maintenance_monitoring_main.checked();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left margin-right-10 checkPermission" permissioncode="produce:maintenance:monitoring:main:01">
				<div class="font-size-12">选中</div>
				<div class="font-size-9">Select</div>
			</a>
			
			<a href="#" onclick="aircraft_maintenance_monitoring_main.exportExcel()"  class="btn btn-primary padding-top-1 margin-left-6 padding-bottom-1 pull-left checkPermission" permissioncode="produce:maintenance:monitoring:main:07">
				<div class="font-size-12">导出</div>
				<div class="font-size-9">Export</div>
			</a>
			
		</div>
		
		<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='margin-bottom:18px;'>
			<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
				<div class="font-size-12">项目类型</div>
				<div class="font-size-9">Type</div>
			</span>
			<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0" onchange="aircraft_maintenance_monitoring_main.search()">
				<select multiple='multiple' id='xmlx_search'>
					<c:forEach items="${maintenanceProjectTypeEnum}" var="item">
						<option value="${item.id}">${item.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='margin-bottom:18px;'>
			<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
				<div class="font-size-12">维修项目大类</div>
				<div class="font-size-9">Class</div>
			</span>
			<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0" onchange="aircraft_maintenance_monitoring_main.search()">
				<select id="dlbh_search" class="form-control">
				</select>
			</div>
		</div>
		
		<!-- 搜索框start -->
		<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group"  style='padding-left:10px;margin-bottom:18px;' >
			<div class="col-xs-12 input-group input-group-search">
				<input type="text" placeholder='任务号/参考号/任务描述/件号/序列号/型号'  class="form-control" id="keyword_search" >
                   <div class="input-group-addon btn-search-nomore" >
                   	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="aircraft_maintenance_monitoring_main.search();" style='margin-right:0px !important;'>
					<div class="font-size-12">搜索</div>
					<div class="font-size-9">Search</div>
                 		</button>
                   </div>
			</div>
		</div>
		<!-- 搜索框end -->
		<div class='clearfix'></div>
		<small class="text-muted" style='position:absolute;bottom:0px;'>飞机维修监控项目总数：<span id="m_total_count"></span></small>
	</div>
	
	<div id="aircraft_maintenance_monitoring_main_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 tab-table-height" >
		<table id="aircraft_maintenance_monitoring_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1350px;">
			<thead>
				<tr>
					<th class="colwidth-7 selectAllImg">
						<a href="#" onclick="SelectUtil.performSelectAll('aircraft_maintenance_monitoring_main_top_div')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
						<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('aircraft_maintenance_monitoring_main_top_div')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
					</th>
					<th class="colwidth-5" >
						<div class="font-size-12 line-height-18">序号</div>
						<div class="font-size-9 line-height-18">No.</div>
					</th>
					<th class="colwidth-5" >
						<div class="font-size-12 line-height-18">标识</div>
						<div class="font-size-9 line-height-18">Mark</div>
					</th>
					<th class="colwidth-7 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('wxxmlx','g2012')" name="column_wxxmlx">
						<div class="font-size-12 line-height-18">项目类型</div>
						<div class="font-size-9 line-height-18">Type</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('rwh','g2012')" name="column_rwh">
						<div class="important">
							<div class="font-size-12 line-height-18">任务号</div>
							<div class="font-size-9 line-height-18">Task No.</div>
						</div>
					</th>
					<th class="colwidth-7 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('bb','g2012')" name="column_bb">
						<div class="font-size-12 line-height-18">版本</div>
						<div class="font-size-9 line-height-18">Rev.</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('ckh','g2012')" name="column_ckh">
						<div class="important">
							<div class="font-size-12 line-height-18">参考号</div>
							<div class="font-size-9 line-height-18">Ref No.</div>
						</div>
					</th>
					<th class="colwidth-15 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('rwms','g2012')" name="column_rwms">
						<div class="important">
							<div class="font-size-12 line-height-18">任务描述</div>
							<div class="font-size-9 line-height-18">Task Description</div>
						</div>
					</th>
					<th class="colwidth-30 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('bz','g2012')" name="column_bz">
						<div class="font-size-12 line-height-18">备注</div>
						<div class="font-size-9 line-height-18">Note</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('bjh','s2902')" name="column_bjh">
						<div class="important">
							<div class="font-size-12 line-height-18">件号</div>
							<div class="font-size-9 line-height-18">P/N</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('xlh','s2902')" name="column_xlh">
						<div class="important">
							<div class="font-size-12 line-height-18">序列号</div>
							<div class="font-size-9 line-height-18">S/N</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('xingh','d008')" name="column_xingh">
						<div class="important">
							<div class="font-size-12 line-height-18">型号</div>
							<div class="font-size-9 line-height-18">Model</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('gzlx','g2012')" name="column_gzlx">
						<div class="font-size-12 line-height-18">工作类别</div>
						<div class="font-size-9 line-height-18">Category</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">上次计划</div>
						<div class="font-size-9 line-height-18">Last Plan</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">上次实际</div>
						<div class="font-size-9 line-height-18">Last Actual</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">下次计划起算点</div>
						<div class="font-size-9 line-height-18">Next Plan Point</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">周期</div>
						<div class="font-size-9 line-height-18">Cycle</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">容差(-/+)</div>
						<div class="font-size-9 line-height-18">Tolerance(-/+)</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">下次计划</div>
						<div class="font-size-9 line-height-18">Next Plan</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">实际</div>
						<div class="font-size-9 line-height-18">Actual</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">剩余</div>
						<div class="font-size-9 line-height-18">Remain</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">下次计划日期</div>
						<div class="font-size-9 line-height-18">Next Plan Date</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('is_hdwz','g2012')" name="column_is_hdwz">
						<div class="font-size-12 line-height-18">后到为准</div>
						<div class="font-size-9 line-height-18">After Arrival</div>
					</th>
					<th class="colwidth-15 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('gkbh','g201102')" name="column_gkbh">
						<div class="font-size-12 line-height-18">工卡编号</div>
						<div class="font-size-9 line-height-18">Work Card No.</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">工卡附件</div>
						<div class="font-size-9 line-height-18">Attachment</div>
					</th>
					<th class="colwidth-13 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('gbbh','s2007')" name="column_gbbh">
						<div class="font-size-12 line-height-18">工包</div>
						<div class="font-size-9 line-height-18">Package</div>
					</th>
					<th class="colwidth-13 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('gdbh','s2008')" name="column_gdbh">
						<div class="font-size-12 line-height-18">工单</div>
						<div class="font-size-9 line-height-18">Work Order</div>
					</th>
					<th class="colwidth-13 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('zjh','g2012')" name="column_zjh">
						<div class="font-size-12 line-height-18">ATA章节号</div>
						<div class="font-size-9 line-height-18">ATA</div>
					</th>
					<th class="colwidth-15 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('dlbh','g201101')" name="column_dlbh">
						<div class="font-size-12 line-height-18">维修项目大类</div>
						<div class="font-size-9 line-height-18">Classification</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">组织机构</div>
						<div class="font-size-9 line-height-18">Organization</div>
					</th>
				</tr>
			</thead>
			<tbody id="aircraft_maintenance_monitoring_main_tbody">
			</tbody>
		</table>
	</div>
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenancemonitoring/aircraft_maintenance_monitoring_main.js"></script>
