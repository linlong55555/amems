<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<div class="panel-body" >
    <div class='row-height'>
	<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0 form-group " id="workorder_detail_operationDiv">
		<a href="javascript:workorder_detail.addWorkorder();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='produce:workpackage:main:09'>
				<div class="font-size-12">新增例行工单</div>
				<div class="font-size-9">Add RTN</div>
		</a>
		<a href="javascript:workorder_detail.addNRC();"  class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission" permissioncode='produce:workpackage:main:10'>
				<div class="font-size-12">新增非例行工单</div>
				<div class="font-size-9">Add NRC</div>
		</a>
		<a href="javascript:workorder_detail.chooseWorkorder(); "  class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left form-group">
				<div class="font-size-12">选择已有工单</div>
				<div class="font-size-9">Choose W/O</div>
		</a>
		<a href="javascript:workorder_detail.exportExcel();"  class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left form-group checkPermission" permissioncode='produce:workpackage:main:12' >
				<div class="font-size-12">导出</div>
				<div class="font-size-9">Export</div>
		</a>
	</div>
	<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  " id="workorder_detail_searchDiv" style="display: none;">
		<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
			<span id="workorder_detail_jhgshj" style="display: none;"></span>
		</div>
		<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 text-right  padding-left-0 padding-right-0 margin-top-0 form-group" >
			<span class='margin-right-5 label-input ' >	<input type="checkbox"  name="workorder_detail_cy" value="1" checked="checked" />&nbsp;一般项目</span>
			<span class='margin-right-5 label-input ' >	<input type="checkbox"  name="workorder_detail_cy" value="2" checked="checked" />&nbsp;时控项目</span>
			<span class='margin-right-5 label-input ' >	<input type="checkbox"  name="workorder_detail_cy" value="3" checked="checked" />&nbsp;时寿项目</span>
			<span class='margin-right-5 label-input ' >	<input type="checkbox"  name="workorder_detail_cy" value="4" checked="checked" />&nbsp;定检包</span>
			<span class='margin-right-5 label-input ' >	<input type="checkbox"  name="workorder_detail_cy" value="5" checked="checked" />&nbsp;EO</span>
			<span class='label-input'>	<input type="checkbox" name="workorder_detail_cy" value="6" checked="checked" />&nbsp;NRC</span>
			<span class='label-input'>	<input type="checkbox" name="workorder_detail_cy" value="7" checked="checked" />&nbsp;MO/MCC</span>
			<span class='label-input'>	<input type="checkbox" name="workorder_detail_cy" value="8" checked="checked" />&nbsp;其它指令</span>
		</div>		
		<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
			<div class="col-xs-12 input-group input-group-search">
				<input type="text" placeholder='来源任务信息/标题/部件件号/部件序列号'  class="form-control" id="workorder_detail_keyword_search" >
	            <div class="input-group-addon btn-searchnew" >
	              	<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="workorder_detail.searchWorkOder();" style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
	               	</button>
	               	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1"  style='margin-right:0px !important;' onclick="javascript:workorder_detail.exportExcel();">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
	               	</button>
	            </div>	                
			</div>
		</div>
	</div>	
	<div class='clearfix'></div>
	</div>
	<div class="col-lg-12 col-md-12 col-xs-12 padding-left-0 padding-right-0 tab-table-content"
		style="overflow-x: auto" id="workorder_detail_table_div">
				<table class="table table-bordered table-striped table-thin table-hover table-set" style="min-width: 950px;margin-bottom:0px !important" id="workorder_detail_table">
					<thead>
				   		<tr>				   			
				   			<th class="colwidth-13" id="workorder_detail_operation">
								<div class="font-size-12 line-height-18">操作</div>
								<div class="font-size-9 line-height-18">Operation</div>
							</th>
							<th  class="selectAllImg" id="workorder_detail_checkAll" style='text-align:center;vertical-align:middle;width:60px;'>
								<a href="#" onclick="workorder_detail.performSelectAll()" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
								<a href="#" class="margin-left-5" onclick="workorder_detail.performSelectClear()" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
							</th>
							<th class="colwidth-10">
							<div class="important">
								<div class="font-size-12 line-height-18">来源任务信息</div>
								<div class="font-size-9 line-height-18">Task Info</div></div>
							</th>
							<th class="colwidth-15">
							<div class="important">
								<div class="font-size-12 line-height-18">标题</div>
								<div class="font-size-9 line-height-18">Title</div></div>
							</th>
							<th class="colwidth-7">
								<div class="font-size-12 line-height-18">工单状态</div>
								<div class="font-size-9 line-height-18">Status</div>
							</th>
							<th class="colwidth-9">
								<div class="font-size-12 line-height-18">工卡附件</div>
								<div class="font-size-9 line-height-18">Attachment</div>
							</th>
				   			<th class="colwidth-7">
								<div class="font-size-12 line-height-18">类型</div>
								<div class="font-size-9 line-height-18">Type</div>
							</th>
							<th class="colwidth-13">
								<div class="font-size-12 line-height-18">工单编号</div>
								<div class="font-size-9 line-height-18">W/O No.</div>
							</th>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-18">参考号</div>
								<div class="font-size-9 line-height-18">No.</div>
							</th>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-18">工作类别</div>
								<div class="font-size-9 line-height-18">Type</div>
							</th>
							<th class="colwidth-7">
								<div class="font-size-12 line-height-18">反馈状态</div>
								<div class="font-size-9 line-height-18">Status</div>
							</th>
							<th class="colwidth-10">
							<div class="important">
								<div class="font-size-12 line-height-18">部件件号</div>
								<div class="font-size-9 line-height-18">P/N</div></div>
							</th>
							<th class="colwidth-10">
							<div class="important">
								<div class="font-size-12 line-height-18">部件序列号</div>
								<div class="font-size-9 line-height-18">S/N</div></div>
							</th>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-18">完成时限</div>
								<div class="font-size-9 line-height-18">Limit</div>
							</th>
							<th class="colwidth-9">
								<div class="font-size-12 line-height-18">计划工时</div>
								<div class="font-size-9 line-height-18">Planned Hour</div>
							</th>
							<th class="colwidth-9">
								<div class="font-size-12 line-height-18">实际工时</div>
								<div class="font-size-9 line-height-18">Actual Hours</div>
							</th>
							<th class="colwidth-9">
								<div class="font-size-12 line-height-18">工卡编号</div>
								<div class="font-size-9 line-height-18">W/C No.</div>
							</th>
							<th class="colwidth-7">
								<div class="font-size-12 line-height-18">工单附件</div>
								<div class="font-size-9 line-height-18">Attachment</div>
							</th>
				 		 </tr>
					</thead>
					<tbody id="workorder_detail_list">
					
					</tbody>
				</table>
				</div>
				<div class='clearfix'></div>
			
</div>

 <script type="text/javascript" src="${ctx }/static/js/thjw/produce/workpackage/135/workorder_detail.js"></script>