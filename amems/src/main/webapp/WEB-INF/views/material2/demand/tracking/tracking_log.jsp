<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>

<div id="tracking_log" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0  table-set" style="overflow-x: auto;">
    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
	<thead>                              
		<tr>
			<th  class='colwidth-9'>
				<div class="font-size-12 line-height-18" >操作时间</div>
				<div class="font-size-9 line-height-18">Date</div>
			</th>
			<th class='colwidth-9'>
				<div class="font-size-12 line-height-18" >操作人</div>
				<div class="font-size-9 line-height-18">Operator</div>
			</th>
			<th class='colwidth-9' onclick="" name="">
				<div class="font-size-12 line-height-18">部门</div>
				<div class="font-size-9 line-height-18">Department</div>
			</th>
			<th class='colwidth-25' onclick="" name="">
					<div class="font-size-12 line-height-18">操作事项</div>
					<div class="font-size-9 line-height-18">Operation matters</div>
			</th>
		</tr> 
	</thead>
	<tbody id="tracking_log_list">
	</tbody>
</table>
</div>
<script type="text/javascript" src="${ctx }/static/js/thjw/material2/demand/tracking/tracking_log.js"></script>