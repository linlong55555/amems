<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
 <div id="course_list_table_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 tab-first-height" style="overflow-x: auto;">
	<table id="change_record_table" class="table table-thin table-bordered table-set" style='margin-bottom:0px !important;'>
		<thead>
			<tr>
				<th class="colwidth-7" >
					<div class="font-size-12 line-height-18" >操作人</div>
					<div class="font-size-9 line-height-18">Operator</div>
				</th>
				
				<th class="colwidth-13" onclick="orderBy('jhlx')" id="jhlx_order">
					<div class="font-size-12 line-height-18">操作时间</div>
					<div class="font-size-9 line-height-18">Operating Time</div>
				</th>
				<th class="" onclick="orderBy('kcbh')" id="kcbh_order">
					<div class="font-size-12 line-height-18">操作说明</div>
					<div class="font-size-9 line-height-18">Desc</div>
				</th>
				
			</tr>
		</thead>
		<tbody id="process_record_list">
		</tbody>
	</table>
</div>
<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="change_record_pagination"></div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/annualplan/process_record.js"></script>