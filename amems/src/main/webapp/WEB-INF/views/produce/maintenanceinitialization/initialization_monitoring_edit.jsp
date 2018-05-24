<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="initialization_monitoring_edit_alert_Modal" tabindex="-1" role="dialog"  aria-labelledby="initialization_monitoring_edit_alert_Modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:90%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">飞机维修项目监控</div>
							<div class="font-size-12" id="modalHeadENG">Aircraft Maintenance Monitoring</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" >
              	<div class="col-xs-12 margin-top-8 ">
					
					<div id="m_task_info">
						<%@ include file="/WEB-INF/views/common/produce/task_maintenance_info.jsp" %> <!-- 维修任务信息 -->
					</div>
					<div id="eo_task_info">
						<%@ include file="/WEB-INF/views/common/produce/task_eo_info.jsp" %> <!-- EO任务信息 -->	
					</div>
					<div id="po_task_info">
						<%@ include file="/WEB-INF/views/common/produce/task_po_info.jsp" %> <!-- 生产指令任务信息 -->	
					</div>
					
					<%@ include file="/WEB-INF/views/common/produce/next_plan_starting_point.jsp" %> <!-- 下次计划起算点 -->	
					
					<%-- <%@ include file="/WEB-INF/views/common/produce/next_plan.jsp" %> --%> <!-- 下次计划 -->	
					
				</div>
                <div class="clearfix"></div>
                           
           	</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
	                    	<button id="save_btn" type="button" onclick="javascript:initialization_monitoring_edit_alert_Modal.setData();" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
		                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
						    </button>
	                    </span>
	               	</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenanceinitialization/initialization_monitoring_edit.js"></script>
