<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
    <script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/135/workorder_jkx_setting.js"></script> <!-- 监控项设置 -->
    <script type="text/javascript" src="${ctx}/static/js/thjw/common/monitor/monitor_unit.js"></script> <!-- 监控项单位设置 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/135/workorder_wcsxwin.js"></script>
<div class="modal fade in modal-new" id="workorder135_wcsx_Modal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="open_win_evaluation_modal" aria-hidden="true">
	<div class="modal-dialog" style="width: 60%;">
		<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                        <div class="font-size-12 " id="modalHeadCN">EO</div>
							<div class="font-size-9" id="modalHeadENG">Evaluation List</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
			<div class="modal-body" style='padding-top:0px;'>
              	<div class="input-group-border" style='margin-top:8px;padding-top:5px;'>
                   <!-- 监控项设置 -->
              	   <div id="workorder_jkxsz_frame_package"></div>
              	   <!-- 监控项设置 -->
				</div>
				<div class="clearfix"></div>  
			</div>
			<div class="clearfix"></div>  
		   
		    <!-- start:底部按钮 -->
		    <div class="modal-footer">
		           	<div class="col-xs-12 padding-leftright-8"  >
		           		<div class="input-group">
							<span class="input-group-addon modalfootertip" >
				                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
							</span>
							<span class="input-group-addon modalfooterbtn">
		                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_evaluation_modal.save();">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
			                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
							    </button>
		                    </span>
		               	</div>
					</div>
			</div>
            <!-- end:底部按钮 -->
               	
		</div>
	</div>
</div>