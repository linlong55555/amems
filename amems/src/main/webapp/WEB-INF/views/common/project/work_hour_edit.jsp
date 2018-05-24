<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
	
<span id="work_hour_edit" class="input-group-addon padding-top-0 padding-bottom-0">
	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="work_hour_edit.openWinAdd();" id="gzgsbt_public">
		<div class="font-size-12">工种工时</div>
		<div class="font-size-9">Work Hours</div>
    </button>
	<span id="workHoursCount" class="badge" style="position: absolute; background:red ! important; color:; margin: -3px -8px 0px ! important;">0</span>
</span>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/project/work_hour_edit.js"></script>
