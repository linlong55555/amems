<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<!-- 隐藏部门的div -->
<div class='displayContent' id='bottom_hidden_content' style='display:none; ' >
	<div class="panel panel-primary margin-bottom-0" >
		<!--标题 STATR -->
 			<div class="panel-heading bg-panel-heading" style='' >
			 <div class="font-size-12 line-height-12">下达指令</div>
			 <div class="font-size-9 line-height-9">An Order</div>
		</div>
		<!--标题EDG  -->
		<div class="panel-body  padding-bottom-0 padding-left-0 padding-right-0 padding-top-0 " >
			<div class='bottom-hidden-table-content padding-top-0 padding-left-0 padding-right-0 ' style="border:0px">
				<table id="OrderTable" class="table table-thin table-bordered table-striped table-hover table-set ">
					<thead >
						<tr>
							<th  class=" colwidth-10" >
								<div class="font-size-12 line-height-18" >技术评估单编号</div>
								<div class="font-size-9 line-height-18">Evaluation No.</div>
							</th>
							<th  class=" colwidth-7" >
								<div class="font-size-12 line-height-18" >指令类型</div>
								<div class="font-size-9 line-height-18">Order Type</div>
							</th>
							<th class=" colwidth-8">
								<div class="font-size-12 line-height-18">指派人</div>
								<div class="font-size-9 line-height-18">Receiver</div>
							</th>
							<th class=" colwidth-7">
								<div class="font-size-12 line-height-18">办理期限</div>
								<div class="font-size-9 line-height-18">For Duration</div>
							</th>
							<th class=" colwidth-5">
								<div class="font-size-12 line-height-18">反馈状态</div>
								<div class="font-size-9 line-height-18">Status</div>
							</th>
							<th  class="downward colwidth-15"  onclick="vieworhideOutputAll()" name="output_return">
								<div class="font-size-12 line-height-18">指令编号</div>
								<div class="font-size-9 line-height-18">Instruction No.</div>
							</th>
							<th  class=" colwidth-25"  >
								<div class="font-size-12 line-height-18">内容</div>
								<div class="font-size-9 line-height-18">Content</div>
							</th>
						</tr>
					</thead>
					<tbody id="give_order_list">
					</tbody>
				</table>
				<div class='clearfix'></div>
			</div>
		</div>
  	  </div>
	<div class='clearfix'></div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessment/assessment_give_order.js"></script>
<%@ include file="/WEB-INF/views/project2/assessment/todo_feedback.jsp"%><!-- 反馈对话框 -->