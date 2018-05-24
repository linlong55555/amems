<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/produce/executionHistory.js"></script><!--当前界面js  -->
<div id="executionHistory" class="panel-body">
	<div id="executionHistory_top_div" class="col-lg-12 col-md-12 col-xs-12  padding-left-0 padding-right-0" style="overflow-x: auto;">			
		<table id="executionHistory_table" class='table table-thin table-striped table-bordered table-set'>
	        <thead>
	        	<tr>
				    <th class="colwidth-3">
				   		<div class="font-size-12 line-height-12">序号</div>
		           		<div class="font-size-9 line-height-12">No.</div>
			   		</th>
			   		<th class="colwidth-15">
				   		<div class="font-size-12 line-height-12">工单编号</div>
		           		<div class="font-size-9 line-height-12">W/O No.</div>
			   		</th>
			   		<th class="colwidth-20">
				   		<div class="font-size-12 line-height-12">工单标题</div>
		           		<div class="font-size-9 line-height-12">W/O Title</div>
			   		</th>
		   			<th class="colwidth-10">
				   		<div class="font-size-12 line-height-12">工单附件</div>
		           		<div class="font-size-9 line-height-12">W/O Attachment</div>
			   		</th>
			   		<th class="colwidth-10">
				   		<div class="font-size-12 line-height-12">飞行记录本</div>
		           		<div class="font-size-9 line-height-12">Flight Log Book</div>
			   		</th>
		   			<th class="colwidth-10">
			   			<div class="font-size-12 line-height-12">记录本页码</div>
	           			<div class="font-size-9 line-height-12">Record Number</div>
		   			</th>
		   			<th class="colwidth-9">
			   			<div class="font-size-12 line-height-12">执行日期</div>
	           			<div class="font-size-9 line-height-12">Date</div>
		   			</th>
		   			<th class="colwidth-9">
			   			<div class="font-size-12 line-height-12">实际值</div>
	           			<div class="font-size-9 line-height-12">Actual Value</div>
		   			</th>
		   			<th class="colwidth-9">
			   			<div class="font-size-12 line-height-12">计划值</div>
	           			<div class="font-size-9 line-height-12">Plan Value</div>
		   			</th>
		   			<th class="colwidth-7">
			   			<div class="font-size-12 line-height-12">反馈</div>
	           			<div class="font-size-9 line-height-12">Feedback</div>
		   			</th>
		   			<th class="colwidth-7">
			   			<div class="font-size-12 line-height-12">拆换件记录</div>
	           			<div class="font-size-9 line-height-12">Record</div>
		   			</th>
				</tr>
	        </thead>
	        <tbody id="executionHistory_tbody">
	        </tbody>
        </table>
         <%-- <%@ include file="/WEB-INF/views/common/produce/feedback_replacement_tab_view.jsp"%> --%>
        
	</div>
</div>
<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>
