<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

	<div id="award_div">
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
		<!-- 业务考核记录 -->
		<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">业务考核记录</div>
				<div class="font-size-9 ">Business Assessment Record</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
	    
	    <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="award_business_table" class="table table-thin table-bordered table-striped table-hover table-set">
				<thead>
					<tr>
						<th class="colwidth-7 editTable" style="vertical-align: middle;">
							<button class="line6" onclick="addAwardBusiness()" style="padding:0px 6px;">
						    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
					        </button>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">日期</div>
							<div class="font-size-9 line-height-18">Date</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">机型</div>
							<div class="font-size-9 line-height-18">Type</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">专业</div>
							<div class="font-size-9 line-height-18">Profession</div>
						</th>
						<th class="colwidth-7">
							<div class="font-size-12 line-height-18">成绩</div>
							<div class="font-size-9 line-height-18">Score</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">考核人</div>
							<div class="font-size-9 line-height-18">Appraiser</div>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</th>
						<th class="colwidth-15 downward cursor-pointer" onclick="vieworhideFj('ywkhjl')" name="th_ywkhjl">
							<div class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Attachment</div>
						</th>
					</tr>
				</thead>
				<tbody id="award_business_list">
					<tr class="non-choice"><td class="text-center" colspan="7">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
		<div class="clearfix"></div>
		<!-- 学术成就 -->
		<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">学术成就</div>
				<div class="font-size-9 ">Scholarship</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
		
	    <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="award_scholarship_table" class="table table-thin table-bordered table-striped table-hover table-set">
				<thead>
					<tr>
						<th class="colwidth-7 editTable" style="vertical-align: middle;">
							<button class="line6" onclick="addAwardScholarship()" style="padding:0px 6px;">
						    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
					        </button>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">成就项目</div>
							<div class="font-size-9 line-height-18">Achievement Project</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">日期</div>
							<div class="font-size-9 line-height-18">Date</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">证明</div>
							<div class="font-size-9 line-height-18">Certification</div>
						</th>
						<th class="colwidth-15 downward cursor-pointer" onclick="vieworhideFj('xscj')" name="th_xscj">
							<div class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Attachment</div>
						</th>
					</tr>
				</thead>
				<tbody id="award_scholarship_list">
					<tr class="non-choice"><td class="text-center" colspan="4">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
		<div class="clearfix"></div>
		
		<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">嘉奖记录</div>
				<div class="font-size-9 ">Citation Record</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
		
	    <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="award_citation_table" class="table table-thin table-bordered table-striped table-hover table-set">
				<thead>
					<tr>
						<th class="colwidth-7 editTable" style="vertical-align: middle;">
							<button class="line6" onclick="addAwardCitation()" style="padding:0px 6px;">
						    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
					        </button>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">事件</div>
							<div class="font-size-9 line-height-18">Event</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">日期</div>
							<div class="font-size-9 line-height-18">Date</div>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">奖励情况</div>
							<div class="font-size-9 line-height-18">Reward</div>
						</th>
						<th class="colwidth-15 downward cursor-pointer" onclick="vieworhideFj('jjjl')" name="th_jjjl">
							<div class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Attachment</div>
						</th>
					</tr>
				</thead>
				<tbody id="award_citation_list">
					<tr class="non-choice"><td class="text-center" colspan="4">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
		<div class="clearfix"></div>
		<!-- 事故征候情况 -->
		<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">事故征候情况</div>
				<div class="font-size-9 ">Incident Situation</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
	    <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="award_incident_table" class="table table-thin table-bordered table-striped table-hover table-set">
				<thead>
					<tr>
						<th class="colwidth-7 editTable" style="vertical-align: middle;">
							<button class="line6" onclick="addAwardIncident()" style="padding:0px 6px;">
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
						<th class="colwidth-18">
							<div class="font-size-12 line-height-18">事故征候</div>
							<div class="font-size-9 line-height-18">Accident Proneness</div>
						</th>
						<th class="colwidth-18">
							<div class="font-size-12 line-height-18">造成结果</div>
							<div class="font-size-9 line-height-18">Produce Results</div>
						</th>
						<th class="colwidth-18">
							<div class="font-size-12 line-height-18">处分情况</div>
							<div class="font-size-9 line-height-18">Punishment</div>
						</th>
						<th class="colwidth-15 downward cursor-pointer" onclick="vieworhideFj('sgzhqk')" name="th_sgzhqk">
							<div class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Attachment</div>
						</th>
					</tr>
				</thead>
				<tbody id="award_incident_list">
					<tr class="non-choice"><td class="text-center" colspan="6">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
		
		<!-- 业务考核记录模态框Start -->
		<div class="modal fade modal-new" id="award_business_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
				<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >业务考核记录</div>
							<div class="font-size-9" >Business Assessment Record</div>
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
						<div class="font-size-16 line-height-18">业务考核记录</div>
						<div class="font-size-9 ">Business Assessment Record</div>
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
									<input class="form-control date-picker" id="award_business_modal_rq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>机型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="award_business_modal_jx" type="text" maxlength="50">
								</div>
							</div>
					
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">专业</div>
									<div class="font-size-9 line-height-18">Profession</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="award_business_modal_zy" type="text" maxlength="15">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>成绩</div>
									<div class="font-size-9 line-height-18">Score</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="award_business_modal_cj" type="text" maxlength="100">
								</div>
							</div>
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>考核人</div>
									<div class="font-size-9 line-height-18">Appraiser</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="award_business_modal_khr" type="text" maxlength="100">
								</div>
							</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="award_business_modal_bz" rows="5" style='height:55px;' maxlength="300"></textarea>
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
				                   <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmAwardBusiness()">
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
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmAwardBusiness()">
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
		<!-- 业务考核记录模态框End -->
		
		<!-- 学术成就模态框Start -->
		<div class="modal fade modal-new" id="award_scholarship_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
				<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >学术成就</div>
							<div class="font-size-9" >Scholarship</div>
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
						<div class="font-size-16 line-height-18">学术成就</div>
						<div class="font-size-9 ">Scholarship</div>
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
									<input class="form-control date-picker" id="award_scholarship_modal_jcrq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>成就项目</div>
								<div class="font-size-9 line-height-18">Achievement Project</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="award_scholarship_modal_cljg" rows="5" style='height:55px;' maxlength="1000"></textarea>
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">证明</div>
								<div class="font-size-9 line-height-18">Certification</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="award_scholarship_modal_sm" rows="5" style='height:55px;' maxlength="1000"></textarea>
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
				                  <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmAwardScholarship()">
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
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmAwardScholarship()">
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
		<!-- 学术成就模态框End -->
		
		<!-- 嘉奖记录模态框Start -->
		<div class="modal fade modal-new" id="award_citation_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false> 
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >嘉奖记录</div>
							<div class="font-size-9" >Citation Record</div>
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
						<div class="font-size-16 line-height-18">嘉奖记录</div>
						<div class="font-size-9 ">Citation Record</div>
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
									<input class="form-control date-picker" id="award_citation_modal_jcrq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
					
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>事件</div>
								<div class="font-size-9 line-height-18">Event</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="award_citation_modal_cljg" rows="5" style='height:55px;' maxlength="1000"></textarea>
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">奖励情况</div>
								<div class="font-size-9 line-height-18">Reward</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="award_citation_modal_sm" rows="5" style='height:55px;' maxlength="1000"></textarea>
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
				                  <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmAwardCitation()">
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
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmAwardCitation()">
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
		<!-- 嘉奖记录模态框End -->
		
		<!-- 事故征候情况模态框Start -->
		<div class="modal fade modal-new" id="award_incident_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
				<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >事故征候情况</div>
							<div class="font-size-9" >Incident Situation</div>
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
						<div class="font-size-16 line-height-18">事故征候情况</div>
						<div class="font-size-9 ">Incident Situation</div>
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
									<input class="form-control date-picker" id="award_incident_modal_rq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">地点</div>
									<div class="font-size-9 line-height-18">Site</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="award_incident_modal_dd" type="text" maxlength="100">
								</div>
							</div>
							
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>事故征候</div>
								<div class="font-size-9 line-height-18">Accident Proneness</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="award_incident_modal_sjjg" rows="5" style='height:55px;' maxlength="1000"></textarea>
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">造成结果</div>
								<div class="font-size-9 line-height-18">Produce Results</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="award_incident_modal_hg" rows="5" style='height:55px;' maxlength="1000"></textarea>
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>处分情况</div>
								<div class="font-size-9 line-height-18">Punishment</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="award_incident_modal_dcjl" rows="5" style='height:55px;' maxlength="1000"></textarea>
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
				                   <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmAwardIncident()">
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
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmAwardIncident()">
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
		<!-- 事故征候情况模态框End -->
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel_award.js"></script>