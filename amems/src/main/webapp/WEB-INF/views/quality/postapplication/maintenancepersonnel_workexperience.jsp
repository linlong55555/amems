<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	<div class="panel-body padding-left-0 padding-right-0 padding-bottom-0" >
	   <div class="col-xs-12 padding-leftright-8">
	   		<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
				<div class="font-size-12">列表信息</div>
				<div class="font-size-9 ">List Info</div>
			</label>
		   <div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" style="overflow-x:auto ;">
				<table class="table table-thin table-bordered table-striped table-hover table-set" >
					<thead>
						<tr>
						   <th class="colwidth-10">
						   		<div class="font-size-12 line-height-12">起始</div>
				           		<div class="font-size-9 line-height-12">Start</div>
					   		</th>
					   		<th class="colwidth-10">
						   		<div class="font-size-12 line-height-12">结束</div>
				           		<div class="font-size-9 line-height-12">End</div>
					   		</th>
				   			<th >
						   		<div class="font-size-12 line-height-12">工作经历</div>
				           		<div class="font-size-9 line-height-12">Work Experience</div>
					   		</th>
						</tr>
					</thead>
					<tbody id="maintenance_workexperience_list">
						<tr class="non-choice"><td class="text-center" colspan="3">暂无数据 No data.</td></tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/postapplication/maintenancepersonnel_workexperience.js"></script>