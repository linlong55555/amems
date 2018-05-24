<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div id="outfield_main">
	<div id="outfield_main_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 tab-table-height" style='overflow:auto;'>
		<table id="outfield_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1350px;">
			<thead>
		   		<tr>
					<th style="width:50px" id="checkSingle">
						<div class="font-size-12 line-height-18">操作</div>
						<div class="font-size-9 line-height-18">Operation</div>
					</th>
					<th style="width:50px" id="checkAll" class="selectAllImg">
						<a href="#" onclick="SelectUtil.performSelectAll('open_win_installationlist_removed_list')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
						<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('open_win_installationlist_removed_list')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
					</th>
					<th class="colwidth-10">
						<div class="important">
							<div class="font-size-12 line-height-18">件号</div>
							<div class="font-size-9 line-height-18">P/N</div>
						</div>
					</th>
					<th class="colwidth-15">
						<div class="important">
							<div class="font-size-12 line-height-18">序列号</div>
							<div class="font-size-9 line-height-18">S/N</div>
						</div>
					</th>
					<th class="colwidth-15">
						<div class="font-size-12 line-height-18">批次号</div>
						<div class="font-size-9 line-height-18">B/N</div>
					</th>
					<th class="colwidth-20">
						<div class="important">
							<div class="font-size-12 line-height-18">名称</div>
							<div class="font-size-9 line-height-18">Name</div>
						</div>
					</th>
					<th class="colwidth-10">
						<div class="important">
							<div class="font-size-12 line-height-18">型号</div>
							<div class="font-size-9 line-height-18">Model</div>
						</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">规格</div>
						<div class="font-size-9 line-height-18">Spec</div>
					</th>
					<th class="colwidth-10">
						<div class="important">
							<div class="font-size-12 line-height-18">厂家件号</div>
							<div class="font-size-9 line-height-18">MP/N</div>
						</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">适用机型</div>
						<div class="font-size-9 line-height-18">A/C Type</div>
					</th>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-18">器材状态</div>
						<div class="font-size-9 line-height-18">Status</div>
					</th>
		 		 </tr>
			</thead>
			<tbody id="outfield_main_list"></tbody>
		</table>
	</div>
	<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="outfield_main_pagination"></div>
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/outfield_modal_main.js"></script>
