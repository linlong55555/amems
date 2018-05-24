<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<div  class="panel panel-primary" style='margin-bottom:0px;'>
	<div class="panel-heading bg-panel-heading" >
		<div class="font-size-12 ">流程记录</div>
		<div class="font-size-9">Process record</div>
	</div>
	<div class="panel-body padding-left-0 padding-right-0">
	 <div class="col-lg-12 col-md-12 padding-leftright-8" style="overflow-x: auto;">
	<table id="processRecord_table" class="table table-thin table-bordered table-set" style='margin-bottom:0px !important;'>
		<thead>
			<tr>
				<th class="colwidth-8" >
					<div class="font-size-12 line-height-18" >操作人</div>
					<div class="font-size-9 line-height-18">Operation</div>
				</th>
				
				<th class="colwidth-13">
					<div class="font-size-12 line-height-18">操作时间</div>
					<div class="font-size-9 line-height-18">Time</div>
				</th>
				<th class="colwidth-7" >
					<div class="font-size-12 line-height-18">操作说明</div>
					<div class="font-size-9 line-height-18">Type</div>
				</th>				
			</tr>
		</thead>
		<tbody id="processRecord_list">
		</tbody>
	</table>
</div>
<div class="col-xs-12 text-center padding-right-0 padding-left-0" id="processRecord_pagination"></div>
	 </div>
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/reviewreformmeasures/measures_record.js"></script>