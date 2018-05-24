<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>

<div id="tracking_log" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0  table-set" style="overflow-x: auto;">
    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
	<thead>                              
		<tr>
		<th  class="   colwidth-10" >
			<div class="font-size-12 line-height-18">编号</div>
			<div class="font-size-9 line-height-18">No.</div>
		</th>
		<th class="  colwidth-14" >
			<div class="font-size-12 line-height-18">名称</div>
			<div class="font-size-9 line-height-18">Name</div>
		</th>
		<th class="  colwidth-12" >
			<div class="font-size-12 line-height-18">规格</div>
			<div class="font-size-9 line-height-18">Spec</div>
		</th>
		<th  class="  colwidth-13" >
			<div class="font-size-12 line-height-18">型号</div>
			<div class="font-size-9 line-height-18">Model</div>
		</th>	
		<th  class="  colwidth-13" >
			<div class="font-size-12 line-height-18">计量方式</div>
			<div class="font-size-9 line-height-18">Mode</div>
		</th>	
		<th  class="  colwidth-8" >
			<div class="font-size-12 line-height-18">计量等级</div>
			<div class="font-size-9 line-height-18">Grade</div>
		</th>	
		<th  class="  colwidth-8">
			<div class="font-size-12 line-height-18">周期/单位</div>
			<div class="font-size-9 line-height-18">Cycle/Unit</div>
		</th>	
		<th  class="  colwidth-10" >
			<div class="font-size-12 line-height-18">最近校验日期</div>
			<div class="font-size-9 line-height-18">Date</div>
		</th>	
		<th  class="  colwidth-10" >
			<div class="font-size-12 line-height-18">下次校验日期</div>
			<div class="font-size-9 line-height-18">Date</div>
		</th>	
		<th  class="  colwidth-8" >
			<div class="font-size-12 line-height-18">剩余天数</div>
			<div class="font-size-9 line-height-18">Remain</div>
		</th>	
		<th  class="  colwidth-15" >
			<div class="font-size-12 line-height-18">备注</div>
			<div class="font-size-9 line-height-18">Desc</div>
		</th>	
		<th  class="  colwidth-13" >
			<div class="font-size-12 line-height-18">维护人</div>
			<div class="font-size-9 line-height-18">Maintainer</div>
		</th>
		<th  class="  colwidth-15" >
			<div class="font-size-12 line-height-18">维护时间</div>
			<div class="font-size-9 line-height-18">Maintenance Time</div>
		</th>
								
		</tr> 
	</thead>
	<tbody id="tracking_log_list">
	</tbody>
</table>
</div>
<script type="text/javascript" src="${ctx }/static/js/thjw/material2/toolcheck/toolcheck_log.js"></script>