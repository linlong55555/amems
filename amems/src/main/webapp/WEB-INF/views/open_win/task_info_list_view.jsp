<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/task_info_list_view.js"></script>
<div class="modal fade in modal-new" id="task_info_list_view" tabindex="-1" role="dialog"  aria-labelledby="task_info_list_view" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width: 60%;" >
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
           			<div class='pull-left'>
               			<div class="font-size-12 ">任务信息</div>
						<div class="font-size-9">Task Info</div>
				 	</div>
					<div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
          	</div>
           	<div class="modal-body" style='padding-top:0px;'>
             	<div class="input-group-border" style='margin-top:8px;padding-top:5px;'>
	            	
					<!-- start:table -->
					<div class="margin-left-8 margin-right-8" style='margin-top:5px;margin-bottom:5px;'>
						<div class='table-content col-xs-12 padding-left-0 padding-right-0' id="task_info_list_view_top_div">
							<table id="task_info_list_view_table" class="table table-thin table-bordered table-striped table-hover table-set">
								<thead>
							   		<tr>
							   			<th class="colwidth-7">
											<div class="font-size-12 line-height-12">类型</div>
											<div class="font-size-9 line-height-12">Type</div>
										</th>
										<th>
											<div class="font-size-12 line-height-12">任务号</div>
											<div class="font-size-9 line-height-12">Task No.</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-12">工卡编号</div>
											<div class="font-size-9 line-height-12">Work Card No.</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 line-height-12">器材代号/IPC No.</div>
											<div class="font-size-9 line-height-12">Part No./IPC No.</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 line-height-12">需求数量</div>
											<div class="font-size-9 line-height-12">QTY</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-12">必需性</div>
											<div class="font-size-9 line-height-12">Necessity</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-12">备注</div>
											<div class="font-size-9 line-height-12">Remark</div>
										</th>
							 		 </tr>
								</thead>
								<tbody id="task_info_list_view_tbody">
								</tbody>
							</table>
						</div>
						<div class="clearfix"></div>  
					</div>
				</div>
               <div class="clearfix"></div>              
          </div>
          <div class="clearfix"></div>  
          <div class="modal-footer">
          		<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
						</span>
	                   	<span class="input-group-addon modalfooterbtn">
	                    	<button onclick="task_info_list_view.close();" type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
					    	</button>
	                   	</span>
              		</div><!-- /input-group -->
				</div>
			</div>
		</div>
	</div>
</div>
<!--  弹出框结束-->