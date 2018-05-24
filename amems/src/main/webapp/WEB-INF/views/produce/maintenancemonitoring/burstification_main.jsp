<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div id="burstification_main">
	<div class='margin-top-0 row-height' >
		<div class='clearfix'></div>
	</div>
	<div id="burstification_main_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 tab-table-height">
		<table id="burstification_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1350px;">
			<thead>
				<tr>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-18" >操作</div>
						<div class="font-size-9 line-height-18">Operation</div>
					</th>
					<th class="colwidth-13 sorting" onclick="burstification_main.orderBy('gbbh','b')" name="column_gbbh">
						<div class="font-size-12 line-height-18">工包编号</div>
						<div class="font-size-9 line-height-18">No.</div>
					</th>
					<th class="colwidth-15 sorting" onclick="burstification_main.orderBy('zt','b')" name="column_zt">
						<div class="font-size-12 line-height-18">状态</div>
						<div class="font-size-9 line-height-18">Status</div>
					</th>
					<th class="colwidth-15 sorting" onclick="burstification_main.orderBy('gbmc','b')" name="column_gbmc">
						<div class="font-size-12 line-height-18">描述</div>
						<div class="font-size-9 line-height-18">Description</div>
					</th>
					<th class="colwidth-10 sorting" onclick="burstification_main.orderBy('wxlx','b')" name="column_wxlx">
						<div class="font-size-12 line-height-18">维修类型</div>
						<div class="font-size-9 line-height-18">Type</div>
					</th>
					<th class="colwidth-10 sorting" onclick="burstification_main.orderBy('JH_KSRQ','b')" name="column_JH_KSRQ">
						<div class="font-size-12 line-height-18">计划开始日期</div>
						<div class="font-size-9 line-height-18">Date</div>
					</th>
					<th class="colwidth-10 sorting" onclick="burstification_main.orderBy('JH_JSRQ','b')" name="column_JH_JSRQ">
						<div class="font-size-12 line-height-18">计划完成日期</div>
						<div class="font-size-9 line-height-18">Date</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">下发单位</div>
						<div class="font-size-9 line-height-18">Unit</div>
					</th>
					<th class="colwidth-10 sorting" onclick="burstification_main.orderBy('zdrq','b')" name="column_zdrq">
						<div class="font-size-12 line-height-18">制单日期</div>
						<div class="font-size-9 line-height-18">Date</div>
					</th>
					<th class="colwidth-10 sorting" onclick="burstification_main.orderBy('JH_ZXDW','b')" name="column_JH_ZXDW">
						<div class="font-size-12 line-height-18">预计执行单位</div>
						<div class="font-size-9 line-height-18">Unit</div>
					</th>
					<th class="colwidth-10 sorting" onclick="burstification_main.orderBy('gzyq','b')" name="column_gzyq">
						<div class="font-size-12 line-height-18">工作要求</div>
						<div class="font-size-9 line-height-18">Work Request</div>
					</th>
					<th class="colwidth-13 sorting" onclick="burstification_main.orderBy('whrid','b')" name="column_whrid">
						<div class="font-size-12 line-height-18">维护人</div>
						<div class="font-size-9 line-height-18">Maintainer</div>
					</th>
					<th class="colwidth-13 sorting" onclick="burstification_main.orderBy('whsj','b')" name="column_whsj">
						<div class="font-size-12 line-height-18">维护时间</div>
						<div class="font-size-9 line-height-18">Maintenance Time</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">组织机构</div>
						<div class="font-size-9 line-height-18">Organization</div>
					</th>
				</tr>
			</thead>
			<tbody id="burstification_main_tbody">
			</tbody>
		</table>
	</div>
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenancemonitoring/burstification_main.js"></script>
