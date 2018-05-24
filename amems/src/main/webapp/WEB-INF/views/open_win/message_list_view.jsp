<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/message_list_view.js"></script>

<div class="modal fade" id="MessageListViewModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog"  style="width: 80%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="MessageListViewModalBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">留言列表</div>
						<div class="font-size-9 ">Message List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12 padding-right-0 padding-left-0">
							<!-- start:table -->
							<div class="margin-top-10 ">
							<div style="overflow-x: auto;">
								<table class="table table-bordered table-striped table-hover text-left table-set" style="min-width: 950px;">
									<thead>
								   		<tr>
								   			<th class="colwidth-3">
												<div class="font-size-12 notwrap">序号</div>
												<div class="font-size-9 notwrap">No.</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">留言时间</div>
												<div class="font-size-9 notwrap">Message time</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">留言人</div>
												<div class="font-size-9 notwrap">Message by</div>
											</th>
											<th class="colwidth-30">
												<div class="font-size-12 notwrap">留言内容</div>
												<div class="font-size-9 notwrap">Message</div>
											</th>
											<th class="colwidth-30">
												<div class="font-size-12 notwrap">提醒人</div>
												<div class="font-size-9 notwrap">Remind by</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="open_messageListTable">
									
									</tbody>
								</table>
								</div>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-bottom-10" style="margin-top: 5px;">
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
			                </div>
			     			<div class="clearfix"></div>
						</div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
</div>
