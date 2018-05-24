	<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/taskhistory/taskhistory_record.js"></script>		
			<div class="panel-body  padding-bottom-0  padding-top-0" id="taskhistoryRecordMain">
			     <div style='overflow:auto;' class='margin-top-10'>
			 		<table class='table table-striped table-thin table-hover table-bordered text-center table-set' style='border-bottom:1px solid #dddddd;margin-bottom:0px !important;'>
						<thead>
							<tr>
								<th class="fixed-column colwidth-5" >
									<div class="font-size-12 line-height-18" >序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th  class="fixed-column colwidth-10" >
									<div class="font-size-12 line-height-18">工单编号</div>
									<div class="font-size-9 line-height-18">Order No</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('jswjlx')" id="jswjlx_order">
									<div class="font-size-12 line-height-18">装上件号</div>
									<div class="font-size-9 line-height-18">Mount P/N</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('jswjly')" id="jswjly_order">
									<div class="font-size-12 line-height-18">装上序列号</div>
									<div class="font-size-9 line-height-18">Mount S/N</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('jswjbh')" id="jswjbh_order">
									<div class="font-size-12 line-height-18">装上件名称</div>
									<div class="font-size-9 line-height-18">Mount Name</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">装上时间</div>
									<div class="font-size-9 line-height-18">Mount Time</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('bb')" id="bb_order">
									<div class="font-size-12 line-height-18">拆下件号</div>
									<div class="font-size-9 line-height-18">Remove P/N</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('xzah')" id="xzah_order">
									<div class="font-size-12 line-height-18">拆下序列号</div>
									<div class="font-size-9 line-height-18">Remove S/N</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('jswjzt')" id="jswjzt_order">
									<div class="font-size-12 line-height-18">拆下件名称</div>
									<div class="font-size-9 line-height-18">Remove Name</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">拆下时间</div>
									<div class="font-size-9 line-height-18">Remove Time</div>
								</th>
								<th class="colwidth-25 sorting" onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18">拆装原因</div>
									<div class="font-size-9 line-height-18">Dismantling Reason</div>
								</th>
							</tr>
						</thead>
						<tbody id="taskhistory_chjjl_list" ></tbody>
					</table>
			 	 </div>
			 	
			</div>
