<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<!-- 隐藏部门的div -->
<div class='displayContent' id='bottom_hidden_content' style='display:none; ' >
	<div class="panel panel-primary margin-bottom-0" >
		<!--标题 STATR -->
 			<div class="panel-heading bg-panel-heading" style='' >
			 <div class="font-size-12 line-height-12">故障处理记录</div>
			 <div class="font-size-9 line-height-9">Fault handling record</div>
		</div>
		<!--标题EDG  -->
		
		
		<div class="panel-body  padding-bottom-0" >
			<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group " id="addBtn">
				<a href="javascript:Fault_Handling_Record_Open_Modal.show(1);"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height checkPermission" permissioncode='produce:fault:monitoring:main:04'>
					<div class="font-size-12">新增</div>
					<div class="font-size-9">Add</div>
				</a>
			</div>
			<div class='bottom-hidden-table-content padding-top-0 padding-left-0 padding-right-0 ' style="border:0px">
				<table id="OrderTable" class="table table-thin table-bordered table-striped table-hover table-set ">
					<thead >
						<tr>
							<th class="colwidth-7 ">
								<div class="font-size-12 line-height-18">操作</div>
								<div class="font-size-9 line-height-18">Operation</div>
							</th>
							<th class="colwidth-7 " >
								<div class="font-size-12 line-height-18">航班号</div>
								<div class="font-size-9 line-height-18">Flight No.</div>
							</th>
							<th class="colwidth-9 ">									
								<div class="font-size-12 line-height-18">日期</div>
								<div class="font-size-9 line-height-18">Date</div>
							</th>
							<th class="colwidth-13 " >
								<div class="font-size-12 line-height-18">排故思路</div>
								<div class="font-size-9 line-height-18">Troubleshooting</div>
							</th>
							<th class="colwidth-11 ">
								<div class="font-size-12 line-height-18">处理结果</div>
								<div class="font-size-9 line-height-18">Result</div>
							</th>
							<th class="colwidth-11 " >
								<div class="font-size-12 line-height-18">指令号</div>
								<div class="font-size-9 line-height-18">Instruction No.</div>
							</th>
							<th class="colwidth-11 " >
								<div class="font-size-12 line-height-18">拆下</div>
								<div class="font-size-9 line-height-18">Remove</div>
							</th>
							<th class="colwidth-11 " >			
								<div class="font-size-12 line-height-18">装上</div>
								<div class="font-size-9 line-height-18">Mount</div>
							</th>
							<th class="colwidth-11 " >			
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</th>
							<th class="colwidth-13 " > 
								<div class="font-size-12 line-height-18">附件</div>
								<div class="font-size-9 line-height-18">Attachment</div>
							</th>
						</tr>
					</thead>
					<tbody id="dcgzcl_list">
					</tbody>
				</table>
				<div class='clearfix'></div>
			</div>
		</div>
  	  </div>
	<div class='clearfix'></div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/planefaultmonitoring/planefaultmonitoring_mainone.js"></script>
<%@ include file="/WEB-INF/views/produce/planefaultmonitoring/planefaultmonitoring_mainone_open.jsp" %> <!--新增编辑界面  -->