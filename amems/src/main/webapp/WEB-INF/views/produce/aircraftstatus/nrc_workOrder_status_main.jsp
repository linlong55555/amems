<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div id="nrc_workOrder_status_main">
	<div id="nrc_workOrder_status_main_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 tab-table-height">
		<table id="nrc_workOrder_status_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1350px;">
			<thead>
				<tr>
					<th class="colwidth-7 sorting" onclick="nrc_workOrder_status_main.orderBy('gdlx','B')" name="column_gdlx">
						<div class="font-size-12 line-height-18">类型</div>
						<div class="font-size-9 line-height-18">Type</div>
					</th>
					<th class="colwidth-30 sorting" onclick="nrc_workOrder_status_main.orderBy('gdbt','B')" name="column_gdbt">
						<div class="font-size-12 line-height-18">工单标题</div>
						<div class="font-size-9 line-height-18">Title</div>
					</th>
					<th class="colwidth-10 sorting" style="border-right:0px !important;" onclick="nrc_workOrder_status_main.orderBy('gkbh','B')" name="column_gkbh">
						<div class="font-size-12 line-height-18">来源工卡</div>
						<div class="font-size-9 line-height-18">Source</div>
					</th>
					<th style="width:20px;border-left:0px !important;" >
					</th>
					<th class="colwidth-10" >
						<div class="font-size-12 line-height-18">计划工时</div>
						<div class="font-size-9 line-height-18">HRS</div>
					</th>
					<th class="colwidth-10 sorting" onclick="nrc_workOrder_status_main.orderBy('JH_KSRQ','B')" name="column_JH_KSRQ">
						<div class="font-size-12 line-height-18">计划开始日期</div>
						<div class="font-size-9 line-height-18">Date</div>
					</th>
					<th class="colwidth-10 sorting" onclick="nrc_workOrder_status_main.orderBy('JH_JSRQ','B')" name="column_JH_JSRQ">
						<div class="font-size-12 line-height-18">计划完成日期</div>
						<div class="font-size-9 line-height-18">Date</div>
					</th>
					<th class="colwidth-10 sorting" onclick="nrc_workOrder_status_main.orderBy('jh_zxdw','s2007')" name="column_jh_zxdw">
						<div class="font-size-12 line-height-18">预计执行单位</div>
						<div class="font-size-9 line-height-18">Unit</div>
					</th>
					<th class="colwidth-13 sorting" onclick="nrc_workOrder_status_main.orderBy('gbbh','s2007')" name="column_gbbh">
						<div class="font-size-12 line-height-18">工包</div>
						<div class="font-size-9 line-height-18">Package</div>
					</th>
					
					<!-- <th class="colwidth-13 sorting" onclick="nrc_workOrder_status_main.orderBy('zjh','B')" name="column_zjh">
						<div class="font-size-12 line-height-18">ATA章节号</div>
						<div class="font-size-9 line-height-18">ATA</div>
					</th>
					<th class="colwidth-15 sorting" onclick="nrc_workOrder_status_main.orderBy('gdbh','B')" name="column_gdbh">
						<div class="font-size-12 line-height-18">工单编号</div>
						<div class="font-size-9 line-height-18">W/C No.</div>
					</th>
					<th class="colwidth-7 sorting" onclick="nrc_workOrder_status_main.orderBy('zt','B')" name="column_zt">
						<div class="font-size-12 line-height-18">状态</div>
						<div class="font-size-9 line-height-18">Status</div>
					</th>
					<th class="colwidth-9 sorting" onclick="nrc_workOrder_status_main.orderBy('whsj','B')" name="column_whsj">
						<div class="font-size-12 line-height-18">制单日期</div>
						<div class="font-size-9 line-height-18">Date</div>
					</th>
					<th class="colwidth-10 sorting" onclick="nrc_workOrder_status_main.orderBy('gzlb','B')" name="column_gzlb">
						<div class="font-size-12 line-height-18">工作类别</div>
						<div class="font-size-9 line-height-18">Category</div>
					</th>
					<th class="colwidth-10 sorting" onclick="nrc_workOrder_status_main.orderBy('bgr','B')" name="column_bgr">
						<div class="font-size-12 line-height-18">报告人</div>
						<div class="font-size-9 line-height-18">Reporter</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">工单附件</div>
						<div class="font-size-9 line-height-18">Attachment</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">组织机构</div>
						<div class="font-size-9 line-height-18">Organization</div>
					</th> -->
				</tr>
			</thead>
			<tbody id="nrc_workOrder_status_main_tbody">
			</tbody>
		</table>
	</div>
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/aircraftstatus/nrc_workOrder_status_main.js"></script>
