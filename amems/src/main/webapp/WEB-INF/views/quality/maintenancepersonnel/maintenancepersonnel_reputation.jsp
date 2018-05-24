<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

	<div id="reputation_div">
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
		<!-- 负有责任的不安全事件 -->
		<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">负有责任的不安全事件</div>
				<div class="font-size-9 ">Responsible Unsafe Incident</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
		<!-- <div class="panel-heading margin-left-16 padding-top-1 margin-bottom-10 " style="border-bottom: 1px solid #ccc; margin-top: 0px;">
			<div class="col-lg-4  padding-left-0 padding-right-0">
				<div class="font-size-16 line-height-18">负有责任的不安全事件</div>
				<div class="font-size-9 ">Responsible Unsafe Incident</div>
			</div>
			<div class="clearfix"></div>
	    </div> -->
	    
	    <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="reputation_unsafe_table" class="table table-thin table-bordered table-striped table-hover table-set">
				<thead>
					<tr>
						<th class="colwidth-7 editTable" style="vertical-align: middle;">
							<button class="line6" onclick="addReputationUnsafe()" style="padding:0px 6px;">
						    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
					        </button>
						</th>
						<th class="colwidth-10">
							<div class="font-size-12 line-height-18">日期</div>
							<div class="font-size-9 line-height-18">Date</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">地点</div>
							<div class="font-size-9 line-height-18">Site</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">涉及人员</div>
							<div class="font-size-9 line-height-18">Personnel Involved</div>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">事件经过</div>
							<div class="font-size-9 line-height-18">Event Course</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">后果</div>
							<div class="font-size-9 line-height-18">Result</div>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">调查结论</div>
							<div class="font-size-9 line-height-18">Research Conclusion</div>
						</th>
						<th class="colwidth-7">
							<div class="font-size-12 line-height-18">扣分</div>
							<div class="font-size-9 line-height-18">Deduct Marks</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</th>
						<th class="colwidth-15 downward cursor-pointer" onclick="vieworhideFj('baqsj')" name="th_baqsj">
							<div class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Attachment</div>
						</th>
					</tr>
				</thead>
				<tbody id="reputation_unsafe_list">
					<tr class="non-choice"><td class="text-center" colspan="9">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
		<div class="clearfix"></div>
		<!-- 不诚信行为 -->
		<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">不诚信行为</div>
				<div class="font-size-9 ">Dishonest Behavior</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
		<!-- <div class="panel-heading margin-left-16 padding-top-1 margin-bottom-10 " style="border-bottom: 1px solid #ccc; margin-top: 0px;">
			<div class="col-lg-4  padding-left-0 padding-right-0 padding-top-10">
				<div class="font-size-16 line-height-18">不诚信行为</div>
				<div class="font-size-9 ">Dishonest Behavior</div>
			</div>
			<div class="clearfix"></div>
	    </div> -->
	    
	    <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="reputation_dishonest_table" class="table table-thin table-bordered table-striped table-hover table-set">
				<thead>
					<tr>
						<th class="colwidth-7 editTable" style="vertical-align: middle;">
							<button class="line6" onclick="addReputationDishonest()" style="padding:0px 6px;">
						    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
					        </button>
						</th>
						<th class="colwidth-10">
							<div class="font-size-12 line-height-18">日期</div>
							<div class="font-size-9 line-height-18">Date</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">地点</div>
							<div class="font-size-9 line-height-18">Site</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">涉及人员</div>
							<div class="font-size-9 line-height-18">Personnel Involved</div>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">事件经过</div>
							<div class="font-size-9 line-height-18">Event Course</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">后果</div>
							<div class="font-size-9 line-height-18">Result</div>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">调查结论</div>
							<div class="font-size-9 line-height-18">Research Conclusion</div>
						</th>
						<th class="colwidth-7">
							<div class="font-size-12 line-height-18">扣分</div>
							<div class="font-size-9 line-height-18">Deduct Marks</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</th>
						<th class="colwidth-15 downward cursor-pointer" onclick="vieworhideFj('bcxxw')" name="th_bcxxw">
							<div class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Attachment</div>
						</th>
					</tr>
				</thead>
				<tbody id="reputation_dishonest_list">
					<tr class="non-choice"><td class="text-center" colspan="9">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
		
		<!-- 负有责任的不安全事件模态框Start -->
		<div class="modal fade modal-new" id="reputation_unsafe_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
				<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >负有责任的不安全事件</div>
							<div class="font-size-9" >Responsible Unsafe Incident</div>
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
						<div class="font-size-16 line-height-18">负有责任的不安全事件</div>
						<div class="font-size-9 ">Responsible Unsafe Incident</div>
					</div> -->
					<div class="modal-body">
						<div class="col-xs-12 margin-top-8 ">
              		     <div class="input-group-border">
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="reputation_unsafe_modal_rq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">地点</div>
									<div class="font-size-9 line-height-18">Site</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="reputation_unsafe_modal_dd" type="text" maxlength="100">
								</div>
							</div>
					
					
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">涉及人员</div>
									<div class="font-size-9 line-height-18">Personnel Involved</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="reputation_unsafe_modal_sjry" type="text" maxlength="100">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">扣分</div>
									<div class="font-size-9 line-height-18">Deduct Marks</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" maxlength="8" id="reputation_unsafe_modal_kf" type="text" maxlength="8">
								</div>
							</div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">事件经过</div>
								<div class="font-size-9 line-height-18">Event Course</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="reputation_unsafe_modal_sjjg" rows="5" style='height:55px;' maxlength="1000"></textarea>
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">后果</div>
								<div class="font-size-9 line-height-18">Result</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="reputation_unsafe_modal_hg" rows="5" style='height:55px;' maxlength="1000"></textarea>
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>调查结论</div>
								<div class="font-size-9 line-height-18">Research Conclusion</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="reputation_unsafe_modal_dcjl" rows="5" style='height:55px;' maxlength="1000"></textarea>
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="reputation_unsafe_modal_bz" rows="5" style='height:55px;' maxlength="300"></textarea>
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
				                  <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmReputationUnsafe()">
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
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmReputationUnsafe()">
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
		<!-- 负有责任的不安全事件模态框End -->
		
		<!-- 不诚信行为模态框Start -->
		<div class="modal fade modal-new" id="reputation_dishonest_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
				<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >不诚信行为</div>
							<div class="font-size-9" >Dishonest Behavior</div>
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
						<div class="font-size-16 line-height-18">不诚信行为</div>
						<div class="font-size-9 ">Dishonest Behavior</div>
					</div> -->
					<div class="modal-body">
					<div class="col-xs-12 margin-top-8 ">
              		     <div class="input-group-border">
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="reputation_dishonest_modal_rq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">地点</div>
									<div class="font-size-9 line-height-18">Site</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="reputation_dishonest_modal_dd" type="text" maxlength="100">
								</div>
							</div>
					
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">涉及人员</div>
									<div class="font-size-9 line-height-18">Personnel Involved</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8 ">
									<input class="form-control" id="reputation_dishonest_modal_sjry" type="text" maxlength="100">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">扣分</div>
									<div class="font-size-9 line-height-18">Deduct Marks</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="reputation_dishonest_modal_kf" type="text" maxlength="8">
								</div>
							</div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">事件经过</div>
								<div class="font-size-9 line-height-18">Event Course</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="reputation_dishonest_modal_sjjg" rows="5" style='height:55px;' maxlength="1000"></textarea>
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">后果</div>
								<div class="font-size-9 line-height-18">Result</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="reputation_dishonest_modal_hg" rows="5" style='height:55px;' maxlength="1000"></textarea>
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>调查结论</div>
								<div class="font-size-9 line-height-18">Research Conclusion</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="reputation_dishonest_modal_dcjl" rows="5" style='height:55px;' maxlength="1000"></textarea>
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="reputation_dishonest_modal_bz" rows="5" style='height:55px;' maxlength="300"></textarea>
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
				                   <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmReputationDishonest()">
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
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmReputationDishonest()">
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
		<!-- 不诚信行为模态框End -->
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel_reputation.js"></script>