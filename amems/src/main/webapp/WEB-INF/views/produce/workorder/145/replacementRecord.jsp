<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>	
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/145/replacementRecord.js"></script> <!-- 135工单管理主列表 -->
<div class="panel panel-primary" id="fjxxpanel">
		<div class="panel-heading bg-panel-heading">
			   <div class="font-size-12 ">拆换件记录</div>
			  <div class="font-size-9">Replacement Record</div>
		</div>
		<div class="panel-body">
			<div class="col-lg-12 col-md-12 col-xs-12 padding-leftright-8" style="overflow-x: auto">			
			<table class="table table-thin table-bordered table-striped table-hover table-set" style="min-width:900px" id="replacementRecord_table">
				<thead>
					<tr>
						<th class="colwidth-7" id="replacementRecord_option">
						   <button class="line6 line6-btn" onclick="replacementRecord.openReplaceWin()"  type="button">
								<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
						   </button>
			  			</th>
			  			<th class="colwidth-7" id="replacementRecord_option_view">
						   <div class="font-size-12 line-height-12" >查看</div>
							<div class="font-size-9 line-height-12">View</div>
			  			</th>
					   	<th class="colwidth-15">
							<div class="font-size-12 line-height-12" >拆下件号</div>
							<div class="font-size-9 line-height-12">Remove The Part No.</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-12">拆下件序列号</div>
							<div class="font-size-9 line-height-12">Remove Serial No.</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-12">拆下件名称</div>
							<div class="font-size-9 line-height-12">Remove Name</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-12">拆下时间</div>
							<div class="font-size-9 line-height-12">Remove Time</div>
						</th>
					   	<th class="colwidth-9">
							<div class="font-size-12 line-height-12" >装上件号</div>
							<div class="font-size-9 line-height-12">Part No.</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-12">装上件序列号</div>
							<div class="font-size-9 line-height-12">Mount Part S/N</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-12">装上件名称</div>
							<div class="font-size-9 line-height-12">Install Name</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-12">装上时间</div>
							<div class="font-size-9 line-height-12">Install Time</div>
						</th>
						<th class="colwidth-9">
							<div class="font-size-12 line-height-12">拆装位置</div>
							<div class="font-size-9 line-height-12">Location</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-12">拆装原因</div>
							<div class="font-size-9 line-height-12">Reason</div>
						</th>
					</tr>
				</thead>
		    <tbody id="replacementRecord_list">
				 
			</tbody>
		</table>
		</div>
	</div>
</div>			