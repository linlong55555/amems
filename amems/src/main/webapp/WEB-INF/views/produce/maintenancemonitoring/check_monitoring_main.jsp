<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div id="check_monitoring_main">
	<div class='searchContent margin-top-0 row-height' >
		<div class=" col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
			<a href="javascript:check_monitoring_main.removeChecked();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left margin-right-10 checkPermission"  permissioncode="produce:maintenance:monitoring:main:02">
				<div class="font-size-12">移除</div>
				<div class="font-size-9">Remove</div>
			</a>
			<a href="javascript:check_monitoring_main.openMaterialToolDetail();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left margin-right-10 " >
				<div class="font-size-12">航材工具需求清单</div>
				<div class="font-size-9">Detail</div>
			</a>
			<button class="btn btn-primary padding-top-1 padding-bottom-1 pull-left burstification-view checkPermission"  permissioncode="produce:maintenance:monitoring:main:03" >
				<div class="font-size-12">组包</div>
				<div class="font-size-9">Burstification</div>
			</button> 
			<!-- <span title="组包  Burstification">
				<i class="burstification-view fa fa-briefcase color-blue cursor-pointer" style="font-size:30px;float: left;margin-top: 13px;" ></i>
			</span>  -->
		</div>
		<!-- 搜索框start -->
		<div class="pull-right col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
			<div class="col-xs-12 input-group input-group-search">
				<input type="text" placeholder='任务号/描述/件号/序列号/型号/ATA'  class="form-control" id="keyword_search" >
                   <div class="input-group-addon btn-search-nomore" >
                   	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="check_monitoring_main.search();" style='margin-right:0px !important;'>
					<div class="font-size-12">搜索</div>
					<div class="font-size-9">Search</div>
                 		</button>
                   </div>
			</div>
		</div>
		<!-- 搜索框end -->
		<div class='clearfix'></div>
	</div>
	<div id="check_monitoring_main_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 tab-table-height">
		<table id="check_monitoring_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1350px;">
			<thead>
				<tr>
					<th class="colwidth-7 selectAllImg">
						<a href="#" onclick="SelectUtil.performSelectAll('check_monitoring_main_top_div')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
						<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('check_monitoring_main_top_div')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
					</th>
					<th class="colwidth-7 sorting" onclick="check_monitoring_main.orderBy('jksjlx','s2901')" name="column_jksjlx">
						<div class="font-size-12 line-height-18">类型</div>
						<div class="font-size-9 line-height-18">Type</div>
					</th>
					<th class="colwidth-10 sorting" onclick="check_monitoring_main.orderBy('jksjbm','s2901')" name="column_jksjbm">
						<div class="important">
							<div class="font-size-12 line-height-18">任务号</div>
							<div class="font-size-9 line-height-18">Task No.</div>
						</div>
					</th>
					<th class="colwidth-7 sorting" onclick="check_monitoring_main.orderBy('bb','s2901')" name="column_bb">
						<div class="font-size-12 line-height-18">版本</div>
						<div class="font-size-9 line-height-18">Rev.</div>
					</th>
					<th class="colwidth-15 sorting" onclick="check_monitoring_main.orderBy('rwms','s2901')" name="column_rwms">
						<div class="important">
							<div class="font-size-12 line-height-18">描述</div>
							<div class="font-size-9 line-height-18">Description</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="check_monitoring_main.orderBy('bjh','s2901')" name="column_bjh">
						<div class="important">
							<div class="font-size-12 line-height-18">件号</div>
							<div class="font-size-9 line-height-18">P/N</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="check_monitoring_main.orderBy('xlh','s2901')" name="column_xlh">
						<div class="important">
							<div class="font-size-12 line-height-18">序列号</div>
							<div class="font-size-9 line-height-18">S/N</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="check_monitoring_main.orderBy('xingh','d008')" name="column_xingh">
						<div class="important">
							<div class="font-size-12 line-height-18">型号</div>
							<div class="font-size-9 line-height-18">Model</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="check_monitoring_main.orderBy('gzlx','s2901')" name="column_gzlx">
						<div class="font-size-12 line-height-18">工作类别</div>
						<div class="font-size-9 line-height-18">Category</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">下次计划</div>
						<div class="font-size-9 line-height-18">Next Plan</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">容差(-/+)</div>
						<div class="font-size-9 line-height-18">Tolerance(-/+)</div>
					</th>
					<th class="colwidth-10 sorting" onclick="check_monitoring_main.orderBy('is_hdwz','s2901')" name="column_is_hdwz">
						<div class="font-size-12 line-height-18">后到为准</div>
						<div class="font-size-9 line-height-18">After Arrival</div>
					</th>
					<th class="colwidth-13 sorting" onclick="check_monitoring_main.orderBy('zjh','s2901')" name="column_zjh">
						<div class="font-size-12 line-height-18">ATA章节号</div>
						<div class="font-size-9 line-height-18">ATA</div>
					</th>
					<th class="colwidth-13 sorting" onclick="check_monitoring_main.orderBy('gbbh','s2007')" name="column_gbbh">
						<div class="font-size-12 line-height-18">工包</div>
						<div class="font-size-9 line-height-18">Package</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">组织机构</div>
						<div class="font-size-9 line-height-18">Organization</div>
					</th>
				</tr>
			</thead>
			<tbody id="check_monitoring_main_tbody">
			</tbody>
		</table>
	</div>
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenancemonitoring/check_monitoring_main.js"></script>
<%@ include file="/WEB-INF/views/produce/maintenancemonitoring/burstification_view_win.jsp"%><!------组包对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
