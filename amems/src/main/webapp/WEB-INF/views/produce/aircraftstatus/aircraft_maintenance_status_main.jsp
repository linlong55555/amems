<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div id="aircraft_maintenance_status_main">
	<div id="aircraft_maintenance_status_main_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 tab-table-height">
		<table id="aircraft_maintenance_status_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1350px;">
			<thead>
				<tr>
					<th class="colwidth-5 " >
						<div class="font-size-12 line-height-18">标识</div>
						<div class="font-size-9 line-height-18">Mark</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_status_main.orderBy('wxxmlx','g2012')" name="column_wxxmlx">
						<div class="font-size-12 line-height-18">项目类型</div>
						<div class="font-size-9 line-height-18">Type</div>
					</th>
					<th class="colwidth-15 sorting" onclick="aircraft_maintenance_status_main.orderBy('rwms','g2012')" name="column_rwms">
						<div class="font-size-12 line-height-18">任务描述</div>
						<div class="font-size-9 line-height-18">Task Description</div>
					</th>
					<th class="colwidth-15 sorting" style="border-right:0px !important;" onclick="aircraft_maintenance_status_main.orderBy('gkbh','g201102')" name="column_gkbh">
						<div class="font-size-12 line-height-18">工卡编号</div>
						<div class="font-size-9 line-height-18">Work Card No.</div>
					</th>
					<th style="width:20px;border-left:0px !important;" >
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
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">上次计划</div>
						<div class="font-size-9 line-height-18">Last Plan</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">上次实际</div>
						<div class="font-size-9 line-height-18">Last Actual</div>
					</th>
					
					<!-- <th class="colwidth-10 sorting" onclick="aircraft_maintenance_status_main.orderBy('rwh','g2012')" name="column_rwh">
						<div class="font-size-12 line-height-18">任务号</div>
						<div class="font-size-9 line-height-18">Task No.</div>
					</th>
					<th class="colwidth-7 sorting" onclick="aircraft_maintenance_status_main.orderBy('bb','g2012')" name="column_bb">
						<div class="font-size-12 line-height-18">版本</div>
						<div class="font-size-9 line-height-18">Rev.</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_status_main.orderBy('ckh','g2012')" name="column_ckh">
						<div class="font-size-12 line-height-18">参考号</div>
						<div class="font-size-9 line-height-18">Ref No.</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_status_main.orderBy('bjh','s2902')" name="column_bjh">
						<div class="font-size-12 line-height-18">件号</div>
						<div class="font-size-9 line-height-18">P/N</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_status_main.orderBy('xlh','s2902')" name="column_xlh">
						<div class="font-size-12 line-height-18">序列号</div>
						<div class="font-size-9 line-height-18">S/N</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_status_main.orderBy('xingh','d008')" name="column_xingh">
						<div class="font-size-12 line-height-18">型号</div>
						<div class="font-size-9 line-height-18">Model</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_status_main.orderBy('gzlx','g2012')" name="column_gzlx">
						<div class="font-size-12 line-height-18">工作类别</div>
						<div class="font-size-9 line-height-18">Category</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">下次计划起算点</div>
						<div class="font-size-9 line-height-18">Next Plan Point</div>
					</th>
					<th class="colwidth-10 sorting" onclick="aircraft_maintenance_status_main.orderBy('is_hdwz','g2012')" name="column_is_hdwz">
						<div class="font-size-12 line-height-18">后到为准</div>
						<div class="font-size-9 line-height-18">After Arrival</div>
					</th>
					<th class="colwidth-13 sorting" onclick="aircraft_maintenance_status_main.orderBy('gbbh','s2007')" name="column_gbbh">
						<div class="font-size-12 line-height-18">工包</div>
						<div class="font-size-9 line-height-18">Package</div>
					</th>
					<th class="colwidth-13 sorting" onclick="aircraft_maintenance_status_main.orderBy('gdbh','s2008')" name="column_gdbh">
						<div class="font-size-12 line-height-18">工单</div>
						<div class="font-size-9 line-height-18">Work Order</div>
					</th>
					<th class="colwidth-13 sorting" onclick="aircraft_maintenance_status_main.orderBy('zjh','g2012')" name="column_zjh">
						<div class="font-size-12 line-height-18">ATA章节号</div>
						<div class="font-size-9 line-height-18">ATA</div>
					</th>
					<th class="colwidth-15 sorting" onclick="aircraft_maintenance_status_main.orderBy('dlbh','g201101')" name="column_dlbh">
						<div class="font-size-12 line-height-18">维修项目大类</div>
						<div class="font-size-9 line-height-18">Classification</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">组织机构</div>
						<div class="font-size-9 line-height-18">Organization</div>
					</th> -->
				</tr>
			</thead>
			<tbody id="aircraft_maintenance_status_main_tbody">
			</tbody>
		</table>
	</div>
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/aircraftstatus/aircraft_maintenance_status_main.js"></script>
