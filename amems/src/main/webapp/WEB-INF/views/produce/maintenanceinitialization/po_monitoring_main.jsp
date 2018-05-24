<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<style type="text/css">
	.spacing {
		margin-left:3px;
		margin-right:3px;
	}
</style>
<div id="po_monitoring_main">
	<div class='margin-top-0  row-height' >
		<!-- 搜索框start -->
		<div class="pull-right col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
			<div class="col-xs-12 input-group input-group-search">
				<input type="text" placeholder='指令编号/指令描述/ATA章节号'  class="form-control" id="keyword_search" >
                   <div class="input-group-addon btn-search-nomore" >
                   	<button id="po_monitoring_mainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="po_monitoring_main.search();" >
					<div class="font-size-12">搜索</div>
					<div class="font-size-9">Search</div>
                 		</button>
                   </div>
			</div>
		</div>
		<!-- 搜索框end -->
		<div class='clearfix'></div>
	</div>
	<div id="po_monitoring_main_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 tab-table-height" style="overflow-x: auto;">
		<table id="po_monitoring_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1350px;">
			<thead>
				<tr>
					<th class="fixed-column colwidth-7">
						<div class="font-size-12 line-height-18">操作</div>
						<div class="font-size-9 line-height-18">Operation</div>
					</th>
					<th class="fixed-column colwidth-15 sorting" onclick="po_monitoring_main.orderBy('zlbh','g2014')" name="column_zlbh">
						<div class="important">
							<div class="font-size-12 line-height-18">指令编号</div>
							<div class="font-size-9 line-height-18">Order No.</div>
						</div>
					</th>
					<th class="colwidth-5 sorting" onclick="po_monitoring_main.orderBy('bb','g2014')" name="column_bb">
						<div class="font-size-12 line-height-18">版本</div>
						<div class="font-size-9 line-height-18">Rev.</div>
					</th>
					<th class="colwidth-20 sorting" onclick="po_monitoring_main.orderBy('zlms','g2014')" name="column_zlms">
						<div class="important">
							<div class="font-size-12 line-height-18">指令描述</div>
							<div class="font-size-9 line-height-18">Order Des</div>
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
					<th class="colwidth-15">
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
					<th class="colwidth-10 sorting" onclick="po_monitoring_main.orderBy('IS_HDWZ','g2014')" name="column_IS_HDWZ">
						<div class="font-size-12 line-height-18">后到为准</div>
						<div class="font-size-9 line-height-18">After Arrival</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">工卡附件</div>
						<div class="font-size-9 line-height-18">Attachment</div>
					</th>
					<th class="colwidth-13 sorting" onclick="po_monitoring_main.orderBy('zjh','g2014')" name="column_zjh">
						<div class="important">
							<div class="font-size-12 line-height-18">ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="po_monitoring_main.orderBy('USERNAME','U')" name="column_USERNAME">
						<div class="font-size-12 line-height-18">维护人</div>
						<div class="font-size-9 line-height-18">Maintainer</div>
					</th>
					<th class="colwidth-13 sorting" onclick="po_monitoring_main.orderBy('whsj','s2902')" name="column_whsj">
						<div class="font-size-12 line-height-18">维护时间</div>
						<div class="font-size-9 line-height-18">Maintenance Time</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">组织机构</div>
						<div class="font-size-9 line-height-18">Organization</div>
					</th>
				</tr>
			</thead>
			<tbody id="po_monitoring_main_tbody">
			</tbody>
		</table>
	</div>
	<div class="col-xs-12 text-center page-height" id="po_monitoring_main_Pagination"></div>
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenanceinitialization/po_monitoring_main.js"></script>
