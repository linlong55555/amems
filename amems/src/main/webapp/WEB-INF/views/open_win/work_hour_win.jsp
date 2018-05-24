<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/work_hour_win.js"></script>
<div class="modal fade in modal-new" id="Work_Hour_Win_Modal" tabindex="-1" role="dialog"  aria-labelledby="Work_Hour_Win_Modal" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width: 60%;" >
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
           			<div class='pull-left'>
               			<div class="font-size-12 ">工种工时</div>
						<div class="font-size-9">Work Hours</div>
				 	</div>
					<div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
          	</div>
           	<div class="modal-body" style='padding-top:0px;'>
             	<div class="input-group-border" style='margin-top:8px;padding-top:5px;'>
	            	
		         	<div class="clearfix"></div>
		         		
					<!-- start:table -->
					<div class="margin-left-8 margin-right-8" style='margin-top:5px;margin-bottom:5px;'>
						<div class="table-content col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;" id="searchTable">
							<table class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important'>
								<thead>
									<tr>
						   				<th class="colOptionhead" style="text-align:center;vertical-align:middle;width: 50px;">
									   		<button class="line6 line6-btn" onclick="Work_Hour_Win_Modal.add()"  type="button">
												<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
									   		</button>
								   		</th>
									   	<th class="colwidth-3">
										   	<div class="font-size-12 line-height-12">序号</div>
								           	<div class="font-size-9 line-height-12">No.</div>
									   	</th>
									   	<th class="colwidth-15">
										   	<div class="font-size-12 line-height-12">阶段</div>
								           	<div class="font-size-9 line-height-12">Stage</div>
									   	</th>
									   	<th class="colwidth-15">
										   	<div class="font-size-12 line-height-12">工种</div>
								           	<div class="font-size-9 line-height-12">Work</div>
									   	</th>
									   	<th class="colwidth-7">
										   	<div class="font-size-12 line-height-12">工时</div>
								           	<div class="font-size-9 line-height-12">HRS</div>
									   	</th>
									   	<th class="colwidth-25">
										   	<div class="font-size-12 line-height-12">任务</div>
								           	<div class="font-size-9 line-height-12">Task</div>
									   	</th>
									   	<th class="colwidth-15">
										   	<div class="font-size-12 line-height-12">备注</div>
								           	<div class="font-size-9 line-height-12">Note</div>
									   	</th>
									</tr>
								</thead>
								<tbody id="work_hour_list">
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
			                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                   	<span class="input-group-addon modalfooterbtn">
	                   		<span class="margin-right-8">
								合计&nbsp;:&nbsp;<span id="zgs" >0</span>
							</span>
	                     	<button id="save_btn" type="button" onclick="Work_Hour_Win_Modal.setData()" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
	                    	<button type="button" onclick="Work_Hour_Win_Modal.close()" class="btn btn-primary padding-top-1 padding-bottom-1">
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