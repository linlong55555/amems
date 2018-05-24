<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<style type="text/css">
	.spacing {
		margin-left:3px;
		margin-right:3px;
	}
</style>
<div id="aircraft_maintenance_monitoring_main">
	<div class='margin-top-0 row-height' >
	
		<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
		</div>
	
		<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
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
		
		<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
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
		<div class="pull-right col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-10 padding-right-0 margin-top-0 form-group" >
			<div class="col-xs-12 input-group input-group-search">
				<input type="text" placeholder='任务号/参考号/任务描述/件号/型号/序列号/ATA章节号'  class="form-control" id="keyword_search" >
         		<div class="input-group-addon btn-search-nomore" >
                   	<button id="aircraft_maintenance_monitoring_mainSearch"  type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="aircraft_maintenance_monitoring_main.search();" >
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                 	</button>
            	</div>
			</div>
		</div>
		<!-- 搜索框end -->
		<div class='clearfix'></div>
	</div>
	<div id="aircraft_maintenance_monitoring_main_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 tab-table-height">
		<table id="aircraft_maintenance_monitoring_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1350px;">
			<thead>
				<tr>
					<th class="fixed-column colwidth-7">
						<div class="font-size-12 line-height-18">操作</div>
						<div class="font-size-9 line-height-18">Operation</div>
					</th>
					<th class="fixed-column colwidth-15 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('rwh','g2012')" name="column_rwh">
						<div class="important">
							<div class="font-size-12 line-height-18">任务号</div>
							<div class="font-size-9 line-height-18">Task No.</div>
						</div>
					</th>
					<th class="colwidth-5 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('bb','g2012')" name="column_bb">
						<div class="font-size-12 line-height-18">版本</div>
						<div class="font-size-9 line-height-18">Rev.</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('ckh','g2012')" name="column_ckh">
						<div class="important">
							<div class="font-size-12 line-height-18">参考号</div>
							<div class="font-size-9 line-height-18">Ref No.</div>
						</div>
					</th>
					<th class="colwidth-7 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('wxxmlx','g2012')" name="column_wxxmlx">
						<div class="font-size-12 line-height-18">项目类型</div>
						<div class="font-size-9 line-height-18">Type</div>
					</th>
					<th class="colwidth-15 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('rwms','g2012')" name="column_rwms">
						<div class="important">
							<div class="font-size-12 line-height-18">任务描述</div>
							<div class="font-size-9 line-height-18">Task Description</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('bjh','s2902')" name="column_bjh">
						<div class="important">
							<div class="font-size-12 line-height-18">件号</div>
							<div class="font-size-9 line-height-18">P/N</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('xingh','d008')" name="column_xingh">
						<div class="important">
							<div class="font-size-12 line-height-18">型号</div>
							<div class="font-size-9 line-height-18">Model</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('xlh','s2902')" name="column_xlh">
						<div class="important">
							<div class="font-size-12 line-height-18">序列号</div>
							<div class="font-size-9 line-height-18">S/N</div>
						</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">上次计划</div>
						<div class="font-size-9 line-height-18">Last Plan</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">上次实际</div>
						<div class="font-size-9 line-height-18">Last Actual</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">下次计划起算点</div>
						<div class="font-size-9 line-height-18">T0</div>
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
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">下次计划日期</div>
						<div class="font-size-9 line-height-18">Next Plan Date</div>
					</th>
					<th class="colwidth-9 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('is_hdwz','g2012')" name="column_is_hdwz">
						<div class="font-size-12 line-height-18">后到为准</div>
						<div class="font-size-9 line-height-18">After Arrival</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">工卡附件</div>
						<div class="font-size-9 line-height-18">Attachment</div>
					</th>
					<th class="colwidth-13 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('zjh','g2012')" name="column_zjh">
						<div class="important">
							<div class="font-size-12 line-height-18">ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('dlbh','g201101')" name="column_dlbh">
						<div class="font-size-12 line-height-18">维修项目大类</div>
						<div class="font-size-9 line-height-18">Classification</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('USERNAME','U')" name="column_USERNAME">
						<div class="font-size-12 line-height-18">维护人</div>
						<div class="font-size-9 line-height-18">Maintainer</div>
					</th>
					<th class="colwidth-13 sorting" onclick="aircraft_maintenance_monitoring_main.orderBy('whsj','s2902')" name="column_whsj">
						<div class="font-size-12 line-height-18">维护时间</div>
						<div class="font-size-9 line-height-18">Maintenance Time</div>
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
	<div class="col-xs-12 text-center page-height" id="aircraft_maintenance_monitoring_main_Pagination"></div>
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenanceinitialization/aircraft_maintenance_monitoring_main.js"></script>
