<%@ page contentType="text/html; charset=utf-8" language="java" %>

<!-- 飞行记录本明细 -->
<div class="modal fade modal-new" id="open_win_flightlogbook_detail_modal" tabindex="-1" role="dialog" aria-labelledby="open_win_flightlogbook_detail_modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:95%">
			<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                	<h4 class="modal-title" >
                  	<div class='pull-left'>
	                    <div class="font-size-14" id="chinaHead">飞行记录本</div>
						<div class="font-size-12" id="englishHead">Flight Log Book</div>
				  	</div>
				  	<div class='pull-right'>
				  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				  	</div>
				  	<div class='clearfix'></div>
                	</h4>
              </div>
			<div class="modal-body padding-bottom-0">
				<div class="col-xs-12 margin-top-8">
			 		<%@ include file="/WEB-INF/views/produce/flb2/flightlogbook_common.jsp"%>
			 	</div>
			 	<div class="clearfix"></div>
			</div>
			
			<div class="clearfix"></div>
			<div class="modal-footer">
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" style="width: 30%;">
			                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
						<span id="detail_close_page" style="text-align: center;position;position: absolute;left: 45%;">
							<i class="icon-caret-left cursor-pointer" id="detail_last_page" title="上一页" style="vertical-align:middle;color: #39b3d7;"></i>
							<button class="btn btn-info padding-top-1 padding-bottom-1 margin-left-5 margin-right-5" onclick="flb_detail.loadData()">
			                   	<div class="font-size-12">新增FLB</div>
								<div class="font-size-9">Add FLB</div>
							</button>
							<i class="icon-caret-right cursor-pointer" id="detail_next_page" title="下一页" style="vertical-align:middle;color: #39b3d7;"></i>
						</span>
						<span class="input-group-addon modalfooterbtn">
							<button class="btn btn-primary padding-top-1 padding-bottom-1 btn-save checkPermission" permissioncode='produce:flb2:main:01,produce:flb2:main:02' onclick="flb_detail.save()">
			                   	<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button class="btn btn-primary padding-top-1 padding-bottom-1 btn-submit checkPermission" permissioncode='produce:flb2:main:03' onclick="flb_detail.submit()">
			                   	<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
							<button class="btn btn-primary padding-top-1 padding-bottom-1 btn-revision checkPermission" permissioncode='produce:flb2:main:03' onclick="flb_detail.revision()">
			                   	<div class="font-size-12">修订</div>
								<div class="font-size-9">Revision</div>
							</button>
							<button class="btn btn-primary padding-top-1 padding-bottom-1 btn-delete checkPermission" permissioncode='produce:flb2:main:04' onclick="flb_detail.del()">
			                   	<div class="font-size-12">删除</div>
								<div class="font-size-9">Delete</div>
							</button>
							<button class="btn btn-primary padding-top-1 padding-bottom-1 btn-scrap checkPermission" permissioncode='produce:flb2:main:05' onclick="flb_detail.discard()">
			                   	<div class="font-size-12">作废</div>
								<div class="font-size-9">Scrap</div>
							</button>
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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

<%@ include file="/WEB-INF/views/produce/flb2/flightlogbook_workorder.jsp"%>
<%@ include file="/WEB-INF/views/produce/flb2/flightlogbook_manual.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/flb2/flightlogbook_detail.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/flb2/flightlogbook_work.js"></script>