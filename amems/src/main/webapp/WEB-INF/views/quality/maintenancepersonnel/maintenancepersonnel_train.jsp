<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

	<div id="train_div">
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
		<div class='clearfix'></div>
		<!-- 培训记录 -->
		<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">培训记录</div>
				<div class="font-size-9 ">A/C Type Training Record</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
	    <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="training_records_table" class="table table-thin table-bordered table-striped table-hover table-set">
				<thead>
					<tr>
						<th class="colwidth-7 editTable" style="vertical-align: middle;">
							<button class="line6" onclick="addTrainingRecords()" style="padding:0px 6px;">
						    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
					        </button>
						</th>
						<th class="colwidth-10">
							<div class="font-size-12 line-height-18">培训类别</div>
							<div class="font-size-9 line-height-18">Training Type</div>
						</th>
						<th class="colwidth-10">
							<div class="font-size-12 line-height-18">机型</div>
							<div class="font-size-9 line-height-18">Type</div>
						</th>
						<th class="colwidth-10">
							<div class="font-size-12 line-height-18">专业</div>
							<div class="font-size-9 line-height-18">Profession</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">日期</div>
							<div class="font-size-9 line-height-18">Date</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">培训机构</div>
							<div class="font-size-9 line-height-18">Organization</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">培训地点</div>
							<div class="font-size-9 line-height-18">Address</div>
						</th>
						<th class="colwidth-10">
							<div class="font-size-12 line-height-18">课程</div>
							<div class="font-size-9 line-height-18">Course</div>
						</th>
						<th class="colwidth-10">
							<div class="font-size-12 line-height-18">讲师</div>
							<div class="font-size-9 line-height-18">Teacher</div>
						</th>
						<th class="colwidth-10">
							<div class="font-size-12 line-height-18">课时</div>
							<div class="font-size-9 line-height-18">Hour</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">培训形式</div>
							<div class="font-size-9 line-height-18">Training Form</div>
						</th>
						<th class="colwidth-7">
							<div class="font-size-12 line-height-18">成绩</div>
							<div class="font-size-9 line-height-18">Score</div>
						</th>
						<th class="colwidth-10">
							<div class="font-size-12 line-height-18">考核结果</div>
							<div class="font-size-9 line-height-18">Examination Result</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">证书编号</div>
							<div class="font-size-9 line-height-18">Certificate No.</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</th>
						<th class="colwidth-15 downward cursor-pointer" onclick="vieworhideFj('pxjl')" name="th_pxjl">
							<div class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Attachment</div>
						</th>
					</tr>
				</thead>
				<tbody id="training_records_list">
					<tr class="non-choice"><td class="text-center" colspan="15">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
		
		<!-------培训计划对话框 Start-------->
	
<div class="modal fade modal-new" id="training_records_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"  data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog modal-lg" style="width:85%;">
		<div class="modal-content">
		<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >培训记录</div>
							<div class="font-size-9" >A/C Type Training Record</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
					</div>
			<div class="modal-body padding-bottom-0" id="alertBody">
			    <div class="col-xs-12 margin-top-8 ">
              	<div class="input-group-border">
				
						
						<form id="training_form">
							<!-- <div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">培训记录</div>
								<div class="font-size-9 ">A/C Type Training Record</div>
							</div> -->
						
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">课程代码</div>
										<div class="font-size-9 line-height-18">Course Code</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8 ">
									<div class='input-group'>
										<input type="hidden" class="form-control " id="training_records_modal_kcid" />
										<input type="text" class="form-control" id="training_records_modal_kcbm" name="kcbm" maxlength="50" onchange="onKcbmChanged();" />
										<div class='input-group-btn'>
										  <button type="button"  class="btn btn-default" data-toggle="modal" onclick="openCourseWin()">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</div>
									</div>
								</div>
							    </div>
								<div class=" col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>课程名称</div>
										<div class="font-size-9 line-height-18">Course Name</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<input type="text" class="form-control " id="training_records_modal_kcmc" name="kcmc" maxlength="100"  />
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">Type</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<input type="text" class="form-control " id="training_records_modal_fjjx" name="fjjx" maxlength="50"  />
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">专业</div>
										<div class="font-size-9 line-height-18">Profession</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<input type="text" class="form-control " id="training_records_modal_zy" name="zy" maxlength="15"  />
									</div>
								</div>
								<div class='clearfix'></div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">讲师</div>
										<div class="font-size-9 line-height-18">Lecturer</div>
									</label>
									
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
											<input type="hidden" class="form-control " id="training_records_modal_jsid" />
											<div class="input-group">
												<input id="training_records_modal_jsxm" name="jsxm" class="form-control "  type="text" >
									            <span class="input-group-btn">
													<button type="button" class="btn btn-default form-control" style="height:34px;" data-toggle="modal" onclick="openLecturerWin()">
														<i class="icon-search cursor-pointer"></i>
													</button>
												</span>
									       </div>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>课程开始日期</div>
										<div class="font-size-9 line-height-18">Start Date</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<div class='input-group' >
										  <input type="text" class='form-control datetimepicker' id="training_records_modal_sjKsrq" name="sjKsrq" onchange="calcNextTrainingDate()" />
										  <span class='input-group-btn'>
										  <input class='form-control readonly-style' type='text' style='width:60px;border-left:0px;' id='training_records_modal_sjKssj' name='sjKssj' readonly/>
										  </span>
										  
										</div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">课程结束日期</div>
										<div class="font-size-9 line-height-18">End Date</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<div class='input-group' >
										  <input type="text" class='form-control datetimepicker' id="training_records_modal_sjJsrq" name="sjJsrq" />
										  <span class='input-group-btn'> 
										   <input class='form-control readonly-style' type='text' style='width:60px;border-left:0px;' id='training_records_modal_sjJssj' name='sjJssj' readonly/>
										  </span>
										 
										</div>
									</div>
								</div>
							
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">实际课时</div>
										<div class="font-size-9 line-height-18">Practical Hours</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<div class='input-group' style="position:relative;z-index:1;">
											<input type="text" class="form-control " id="training_records_modal_sjks" name="sjks" onkeyup='clearNoNumOne(this)' maxlength="10" />
										  <span class='input-group-btn'> </span>
										  	<label class='input-group-addon ' style="padding-left:0px;padding-right:0px;border:0px;background:none;color:black !important">时</label>
										</div>
									</div>
								</div>
							 <div class='clearfix'></div>
								
								
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">初/复训标识</div>
										<div class="font-size-9 line-height-18">Whether</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<label class="margin-right-5 label-input"  style='vertical-align:-3px;'>
									    	<input name="training_records_modal_fxbs" type="radio" value="1" checked="checked"/>&nbsp;初训
									    </label> 
										<label class="margin-right-5 label-input" style='vertical-align:-3px;'>
											<input name="training_records_modal_fxbs" type="radio" value="2" />&nbsp;复训
										</label> 
									</div>
								</div>
								
								<div class=" col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">培训形式</div>
										<div class="font-size-9 line-height-18">Form</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<select class='form-control' id='training_records_modal_pxxs' name="pxxs" ></select>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">考试形式</div>
										<div class="font-size-9 line-height-18">Form</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<select class='form-control' id='training_records_modal_ksxs' name="ksxs" ></select>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">培训类别</div>
										<div class="font-size-9 line-height-18">Training Type</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<select class='form-control' id='training_records_modal_pxlb' name="pxlb" >
										</select>
									</div>
								</div>
							<div class='clearfix'></div>
								
								
								<div class=" col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">培训机构</div>
										<div class="font-size-9 line-height-18">Institution</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<input type="text" class="form-control " id="training_records_modal_pxgh" name="pxgh" maxlength="100" />
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">培训地点</div>
										<div class="font-size-9 line-height-18">Location</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<input class='form-control' id='training_records_modal_kcdd' name="kcdd" maxlength="100"/>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">出勤率</div>
										<div class="font-size-9 line-height-18">Attendance</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<div class='input-group' style="position:relative;z-index:1;">
											<input type="text" class="form-control " id="training_records_modal_cql" name="cql"  maxlength="10" />
										  <span class='input-group-btn'> </span>
										  	<span class='input-group-addon' style="padding-left:0px;padding-right:0px;border:0px;background:none;color:black !important">%</span>
										</div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">成绩</div>
										<div class="font-size-9 line-height-18">Result</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<input type="text" class="form-control " id="training_records_modal_cj" name="cj" maxlength="15"/>
									</div>
								</div>
							<div class='clearfix'></div>
								
						
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">证书</div>
										<div class="font-size-9 line-height-18">Certificate</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<input type="text" class="form-control " id="training_records_modal_zs" name="zs" maxlength="100"  />
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">考核结果</div>
										<div class="font-size-9 line-height-18">Result</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<label class="margin-right-5 label-input" style='vertical-align:-3px;' >
									    	<input name="training_records_modal_khjg" type="radio" value="1" checked="checked"/>&nbsp;通过
									    </label> 
										<label class="margin-right-5 label-input" style='vertical-align:-3px;'>
											<input name="training_records_modal_khjg" type="radio" value="0" />&nbsp;未通过
										</label> 
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">下次培训日期</div>
										<div class="font-size-9 line-height-18">Next Date</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 padding-leftright-8">
										<input type="text" class="form-control datetimepicker" id="training_records_modal_xcpxrq" name="xcpxrq" maxlength="100"  />
									</div>
								</div>
								
								<div class="clearfix"></div>
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
										<textarea class="form-control" id="training_records_modal_bz" name="bz" maxlength="300" style='height:55px;'></textarea>
									</div>
								</div>
								
							
							
							<!----------------------------------- 附件begin ---------------------------------->
							<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_attachment_common.jsp"%>
							<!----------------------------------- 附件begin ---------------------------------->
						</form>
						
					 </div>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<!-- <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p> -->
						</span>
	                    <span class="input-group-addon modalfooterbtn">
		                 	<button id="planSave" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmTrainingRecords();">
			                   	<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
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

<!-------培训计划对话框 End-------->
	</div>
<%@ include file="/WEB-INF/views/open_win/course.jsp"%><!-------课程对话框 --------> 
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel_train.js"></script>
<%@ include file="/WEB-INF/views/training/plan/teacher_user.jsp" %><!-- 讲师列表 -->