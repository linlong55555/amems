<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div id="nrc_work_order_main">
	<div class='searchContent margin-top-0 row-height' style='position:relative;'>
		<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0 form-group" style='margin-bottom:18px;'>
			<a href="javascript:nrc_work_order_main.checked();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left margin-right-10 checkPermission"  permissioncode="produce:maintenance:monitoring:main:01">
				<div class="font-size-12">选中</div>
				<div class="font-size-9">Select</div>
			</a>
			
			<a href="#" onclick="nrc_work_order_main.exportExcel()"  class="btn btn-primary padding-top-1 margin-left-6 padding-bottom-1 pull-left checkPermission" permissioncode="produce:maintenance:monitoring:main:07">
				<div class="font-size-12">导出</div>
				<div class="font-size-9">Export</div>
			</a>
			
		</div>
		<!-- 搜索框start -->
		<div class="pull-right col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='margin-bottom:18px;'>
			<div class="col-xs-12 input-group input-group-search">
				<input type="text" placeholder='工单编号/工单标题/ATA/来源工卡'  class="form-control" id="keyword_search" >
                   <div class="input-group-addon btn-search-nomore" >
                   	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="nrc_work_order_main.search();" style='margin-right:0px !important;'>
					<div class="font-size-12">搜索</div>
					<div class="font-size-9">Search</div>
                 		</button>
                   </div>
			</div>
		</div>
		<!-- 搜索框end -->
		<div class='clearfix'></div>
		<small class="text-muted" style='position:absolute;bottom:0px;'>其它指令总数：<span id="n_total_count"></span></small>
	</div>
	
	<div id="nrc_work_order_main_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 tab-table-height">
		<table id="nrc_work_order_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1350px;">
			<thead>
				<tr>
					<th class="colwidth-7 selectAllImg">
						<a href="#" onclick="SelectUtil.performSelectAll('nrc_work_order_main_top_div')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
						<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('nrc_work_order_main_top_div')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
					</th>
					<th class="colwidth-15 sorting" onclick="nrc_work_order_main.orderBy('gdbh','B')" name="column_gdbh">
						<div class="important">
							<div class="font-size-12 line-height-18">工单编号</div>
							<div class="font-size-9 line-height-18">Order No.</div>
						</div>
					</th>
					<th class="colwidth-30 sorting" onclick="nrc_work_order_main.orderBy('gdbt','B')" name="column_gdbt">
						<div class="important">
							<div class="font-size-12 line-height-18">工单标题</div>
							<div class="font-size-9 line-height-18">Title</div>
						</div>
					</th>
					<th class="colwidth-7 sorting" onclick="nrc_work_order_main.orderBy('zt','B')" name="column_zt">
						<div class="font-size-12 line-height-18">状态</div>
						<div class="font-size-9 line-height-18">Status</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">工卡附件</div>
						<div class="font-size-9 line-height-18">Attachment</div>
					</th>
					<th class="colwidth-10 sorting" onclick="nrc_work_order_main.orderBy('JH_KSRQ','B')" name="column_JH_KSRQ">
						<div class="font-size-12 line-height-18">计划开始日期</div>
						<div class="font-size-9 line-height-18">Date</div>
					</th>
					<th class="colwidth-10 sorting" onclick="nrc_work_order_main.orderBy('JH_JSRQ','B')" name="column_JH_JSRQ">
						<div class="font-size-12 line-height-18">计划完成日期</div>
						<div class="font-size-9 line-height-18">Date</div>
					</th>
					<th class="colwidth-13 sorting" onclick="nrc_work_order_main.orderBy('zjh','B')" name="column_zjh">
						<div class="important">
							<div class="font-size-12 line-height-18">ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div>
						</div>
					</th>
					<th class="colwidth-7 sorting" onclick="nrc_work_order_main.orderBy('gdlx','B')" name="column_gdlx">
						<div class="font-size-12 line-height-18">类型</div>
						<div class="font-size-9 line-height-18">Type</div>
					</th>
					<th class="colwidth-9 sorting" onclick="nrc_work_order_main.orderBy('whsj','B')" name="column_whsj">
						<div class="font-size-12 line-height-18">制单日期</div>
						<div class="font-size-9 line-height-18">Date</div>
					</th>
					<th class="colwidth-10 sorting" onclick="nrc_work_order_main.orderBy('gzlb','B')" name="column_gzlb">
						<div class="font-size-12 line-height-18">工作类别</div>
						<div class="font-size-9 line-height-18">Category</div>
					</th>
					<th class="colwidth-10 sorting" onclick="nrc_work_order_main.orderBy('bgr','B')" name="column_bgr">
						<div class="font-size-12 line-height-18">报告人</div>
						<div class="font-size-9 line-height-18">Reporter</div>
					</th>
					<th class="colwidth-10 sorting" onclick="nrc_work_order_main.orderBy('gkbh','B')" name="column_gkbh">
						<div class="important">
							<div class="font-size-12 line-height-18">来源工卡</div>
							<div class="font-size-9 line-height-18">Source</div>
						</div>
					</th>
					<th class="colwidth-10" >
						<div class="font-size-12 line-height-18">计划工时</div>
						<div class="font-size-9 line-height-18">HRS</div>
					</th>
					<th class="colwidth-10 sorting" onclick="nrc_work_order_main.orderBy('jh_zxdw','s2007')" name="column_jh_zxdw">
						<div class="font-size-12 line-height-18">预计执行单位</div>
						<div class="font-size-9 line-height-18">Unit</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">工单附件</div>
						<div class="font-size-9 line-height-18">Attachment</div>
					</th>
					<th class="colwidth-13 sorting" onclick="nrc_work_order_main.orderBy('gbbh','s2007')" name="column_gbbh">
						<div class="font-size-12 line-height-18">工包</div>
						<div class="font-size-9 line-height-18">Package</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">组织机构</div>
						<div class="font-size-9 line-height-18">Organization</div>
					</th>
				</tr>
			</thead>
			<tbody id="nrc_work_order_main_tbody">
			</tbody>
		</table>
	</div>
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenancemonitoring/nrc_work_order_main.js"></script>
