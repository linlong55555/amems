<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

	<div id="maintenance_div">
		<div name="personnelInfo" class='margin-top-8'>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="pull-left text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18">人员编号</div>
					<div class="font-size-9 line-height-18">Staff No.</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<span style="font-size: 16px;line-height: 30px;" name="rybh_feedback"></span>
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="pull-left text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18">姓名</div>
					<div class="font-size-9 line-height-18">Name</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<span style="font-size: 16px;line-height: 30px;" name="xm_feedback"></span>
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="pull-left text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18">单位/部门</div>
					<div class="font-size-9 line-height-18">Department</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<span style="font-size: 16px;line-height: 30px;" name="szdw_feedback"></span>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		<!-- 维修技术等级升级记录 -->
		<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">技术等级升级记录</div>
				<div class="font-size-9 ">Tech. Promotion Record</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
	     <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="maintenance_level_table" class="table table-thin table-bordered table-striped table-hover table-set">
				<thead>
					<tr>
						<th class="colwidth-7 editTable" style="vertical-align: middle;">
							<button class="line6" onclick="addMaintenanceLevel()" style="padding:0px 6px;">
						    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
					        </button>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">级别</div>
							<div class="font-size-9 line-height-18">Level</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-18">日期</div>
							<div class="font-size-9 line-height-18">Date</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-18">专业</div>
							<div class="font-size-9 line-height-18">Profession</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">机型</div>
							<div class="font-size-9 line-height-18">Type</div>
						</th>
						<th class="colwidth-7">
							<div class="font-size-12 line-height-18">考核成绩</div>
							<div class="font-size-9 line-height-18">Result</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">批准人</div>
							<div class="font-size-9 line-height-18">Approver</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</th>
						<th class="colwidth-15 downward cursor-pointer" onclick="vieworhideFj('wxjssjjl')" name="th_wxjssjjl">
							<div class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Attachment</div>
						</th>
					</tr>
				</thead>
				<tbody id="maintenance_level_list">
					<tr class="non-choice"><td class="text-center" colspan="8">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
		
		<!-- 维修技术等级升级记录模态框Start -->
		<div class="modal fade modal-new" id="maintenance_level_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
				<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >维修技术等级升级记录</div>
							<div class="font-size-9" >Maintenance Level Upgrade Record</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
					</div>
				<!-- 	<div class="modal-header padding-bottom-5">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<div class="font-size-16 line-height-18">维修技术等级升级记录</div>
						<div class="font-size-9 ">Maintenance Level Upgrade Record</div>
					</div> -->
					<div class="modal-body">
					    <div class="col-xs-12 margin-top-8 ">
              		     <div class="input-group-border">
					
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>级别</div>
									<div class="font-size-9 line-height-18">Level</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="maintenance_level_modal_dj" type="text" maxlength="100">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="maintenance_level_modal_rq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
						
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">专业</div>
									<div class="font-size-9 line-height-18">Profession</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="maintenance_level_modal_zy" type="text" maxlength="15">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">机型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="maintenance_level_modal_jx" type="text" maxlength="50">
								</div>
							</div>
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">考核成绩</div>
									<div class="font-size-9 line-height-18">Result</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="maintenance_level_modal_khcj" type="text" maxlength="15">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">批准人</div>
									<div class="font-size-9 line-height-18">Approver</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="maintenance_level_modal_pzr" type="text" maxlength="100">
								</div>
							</div>
					
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="maintenance_level_modal_bz" rows="5" style='height:55px;' maxlength="300"></textarea>
							</div>
						</div>
						<!----------------------------------- 附件begin ---------------------------------->
						<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_attachment_common.jsp"%>
						<!----------------------------------- 附件begin ---------------------------------->
					<div class='clearfix'></div>
					</div>
					</div>
					<div class='clearfix'></div>
					</div>
					<div class="modal-footer">
			           	<div class="col-xs-12 padding-leftright-8" >
							<div class="input-group">
								<span class="input-group-addon modalfootertip" >
									<!-- <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p> -->
								</span>
			                    <span class="input-group-addon modalfooterbtn">
				                   <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmMaintenanceLevel()">
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
					<!-- <div class="modal-footer" style="margin-right:20px">
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmMaintenanceLevel()">
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div> -->
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		<!-- 维修技术等级升级记录模态框End -->
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel_maintenance.js"></script>