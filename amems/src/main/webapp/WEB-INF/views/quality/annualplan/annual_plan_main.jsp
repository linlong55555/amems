<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
 <div id="course_list_table_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 tab-first-height" style="margin-top: 10px;overflow-x: auto;">
	<table id="annualplanTable" class="table table-thin table-bordered table-set">
		<thead>
			<tr>
				<th class="fixed-column colwidth-7 operation_td" style='vertical-align:middle;' >
					<button class="line6 checkPermission" permissioncode="quality:annualplan:main:01" onclick="javascript:annual_plan_alert_Add.open();" style="padding:0px 6px;">
						<i class="icon-plus cursor-pointer color-blue cursor-pointer "  ></i>
					</button>
				</th>
				<th class="colwidth-5 sorting" onclick="course_list_table_div.orderBy('yf')" id="yf_order">
					<div class="font-size-12 line-height-18">月份</div>
					<div class="font-size-9 line-height-18">Month</div>
				</th>
				<th class="colwidth-5 sorting" onclick="course_list_table_div.orderBy('lx')" id="lx_order">
					<div class="font-size-12 line-height-18">类型</div>
					<div class="font-size-9 line-height-18">Type</div>
				</th>
				<th class="colwidth-15 sorting" onclick="course_list_table_div.orderBy('shdxbh')" id="shdxbh_order">
					<div class="important">
						<div class="font-size-12 line-height-18">审核对象</div>
						<div class="font-size-9 line-height-18">Audit object</div>
					</div>
				</th>
				<th class="colwidth-15 " >
					<div class="important">
						<div class="font-size-12 line-height-18">责任审核人</div>
						<div class="font-size-9 line-height-18">Auditor</div>
					</div>
				</th>
				<th class="colwidth-15 sorting" onclick="course_list_table_div.orderBy('bz')" id="bz_order">
				    <div class="font-size-12 line-height-18">备注</div>
					<div class="font-size-9 line-height-18">Remark</div>
				</th>
				<th class="colwidth-9 " >
				    <div class="font-size-12 line-height-18">附件</div>
					<div class="font-size-9 line-height-18">Attachment</div>
				</th>
				<th class="colwidth-13 sorting" onclick="course_list_table_div.orderBy('whrid')" id="whrid_order" >
				<div class="font-size-12 line-height-18">维护人</div>
				<div class="font-size-9 line-height-18">Maintainer</div>
			</th>
			<th class="colwidth-13 sorting" onclick="course_list_table_div.orderBy('whsj')" id="whsj_order" >
				<div class="font-size-12 line-height-18">维护时间</div>
				<div class="font-size-9 line-height-18">Maintenance Time</div>
			</th>
			<th class="colwidth-13" >
				<div class="font-size-12 line-height-18">组织机构</div>
				<div class="font-size-9 line-height-18">Organization</div>
			</th>
			</tr>
		</thead>
		<tbody id="course_list_table_div_list">
		</tbody>
	</table>
</div>
<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="course_list_table_div_pagination">
</div>
                 