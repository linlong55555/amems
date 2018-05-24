<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

	<div id="license_div">
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
		
		<!-- 维修执照 -->
		<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">技术执照</div>
				<div class="font-size-9 ">License-holding</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
	    <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="maintenance_license_table" class="table table-thin table-bordered table-striped table-hover table-set">
				<thead>
					<tr>
						<th class="colwidth-7 editTable" style="vertical-align: middle;">
							<button class="line6" onclick="addMaintenanceLicense()" style="padding:0px 6px;">
						    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
					        </button>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">颁发单位</div>
							<div class="font-size-9 line-height-18">Issuing Unit</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">专业</div>
							<div class="font-size-9 line-height-18">Profession</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">执照号</div>
							<div class="font-size-9 line-height-18">License No.</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">颁发日期</div>
							<div class="font-size-9 line-height-18">Date Of Issue</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">有效期</div>
							<div class="font-size-9 line-height-18">Period Of Validity</div>
						</th>
						<th class="colwidth-15 downward cursor-pointer" onclick="vieworhideFj('wxzz')" name="th_wxzz">
							<div class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Attachment</div>
						</th>
					</tr>
				</thead>
				<tbody id="maintenance_license_list">
					<tr class="non-choice"><td class="text-center" colspan="6">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
		<!--  机型信息-->
		<div class="clearfix"></div>
			<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">机型信息</div>
				<div class="font-size-9 ">A/C Type License</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
	    <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="type_license_table" class="table table-thin table-bordered table-striped table-hover table-set">
				<thead>
					<tr>
						<th class="colwidth-7 editTable" style="vertical-align: middle;">
							<button class="line6" onclick="addTypeLicense()" style="padding:0px 6px;">
						    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
					        </button>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">颁发单位</div>
							<div class="font-size-9 line-height-18">Issuing Unit</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">机型</div>
							<div class="font-size-9 line-height-18">Type</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">专业</div>
							<div class="font-size-9 line-height-18">Profession</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">维护级别</div>
							<div class="font-size-9 line-height-18">Level</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">颁发日期</div>
							<div class="font-size-9 line-height-18">Date Of Issue</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">有效期</div>
							<div class="font-size-9 line-height-18">Period Of Validity</div>
						</th>
						<th class="colwidth-15 downward cursor-pointer" onclick="vieworhideFj('jxzz')" name="th_jxzz">
							<div class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Attachment</div>
						</th>
					</tr>
				</thead>
				<tbody id="type_license_list">
					<tr class="non-choice"><td class="text-center" colspan="7">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
		
		<!-- 维修执照模态框Start -->
		<div class="modal modal-new" id="maintenance_license_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
				<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >维修执照</div>
							<div class="font-size-9" >Maintenance License</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
					</div>
					<!-- <div class="modal-header padding-bottom-5">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<div class="font-size-16 line-height-18">维修执照</div>
						<div class="font-size-9 ">Maintenance License</div>
					</div> -->
					<div class="modal-body">
					<div class="col-xs-12 margin-top-8 ">
              		     <div class="input-group-border">
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>颁发单位</div>
									<div class="font-size-9 line-height-18">Issuing Unit</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="maintenance_license_modal_bfdw" type="text" maxlength="100">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">专业</div>
									<div class="font-size-9 line-height-18">Profession</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="maintenance_license_modal_zy" type="text" maxlength="15">
								</div>
							</div>
						
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>执照号</div>
									<div class="font-size-9 line-height-18">License No.</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="maintenance_license_modal_zjbh" type="text" maxlength="20">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">颁发日期</div>
									<div class="font-size-9 line-height-18">Date Of Issue</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="maintenance_license_modal_bfrq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
						
					
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">有效期开始</div>
									<div class="font-size-9 line-height-18">Start Validity</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="maintenance_license_modal_yxq_ks" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>有效期截止</div>
									<div class="font-size-9 line-height-18">End Validity</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="maintenance_license_modal_yxq_js" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
						<!----------------------------------- 附件begin ---------------------------------->
						<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_attachment_common.jsp"%>
						<!----------------------------------- 附件begin ---------------------------------->
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
				                  <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmMaintenanceLicense()">
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
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmMaintenanceLicense()">
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
		<!-- 维修执照模态框End -->
		
		<!-- 机型信息模态框Start -->
		<div class="modal fade modal-new " id="type_license_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >机型信息</div>
							<div class="font-size-9" >Type License</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
					</div>
					<!-- <div class="modal-header padding-bottom-5">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<div class="font-size-16 line-height-18">机型信息</div>
						<div class="font-size-9 ">Type License</div>
					</div> -->
					<div class="modal-body">
					<div class="col-xs-12 margin-top-8 ">
              		     <div class="input-group-border">
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>颁发单位</div>
									<div class="font-size-9 line-height-18">Issuing Unit</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="type_license_modal_bfdw" type="text" maxlength="100">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">专业</div>
									<div class="font-size-9 line-height-18">Profession</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="type_license_modal_zy" type="text" maxlength="15">
								</div>
							</div>
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">维护级别</div>
									<div class="font-size-9 line-height-18">Level</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="type_license_modal_whjb" type="text" maxlength="15">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">颁发日期</div>
									<div class="font-size-9 line-height-18">Date Of Issue</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="type_license_modal_bfrq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">有效期开始</div>
									<div class="font-size-9 line-height-18">Start Validity</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="type_license_modal_yxq_ks" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">有效期截止</div>
									<div class="font-size-9 line-height-18">End Validity</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="type_license_modal_yxq_js" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>机型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="type_license_modal_fjjx" type="text" maxlength="50">
								</div>
							</div>
						
						<!----------------------------------- 附件begin ---------------------------------->
						<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_attachment_common.jsp"%>
						<!----------------------------------- 附件begin ---------------------------------->
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
				                   <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmTypeLicense()">
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
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmTypeLicense()">
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
		<!-- 机型信息模态框End -->
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel_license.js"></script>