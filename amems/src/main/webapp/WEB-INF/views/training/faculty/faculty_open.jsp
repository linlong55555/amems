<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	<div class="modal fade in modal-new active" id="AddalertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:90%!important;">
			<div class="modal-content">
				<div class="modal-header modal-header-new">
					<h4 class="modal-title">
						<div class="pull-left">
							<div class="font-size-14 "><span id="modalName"></span></div>
							<div class="font-size-12"><span id="modalEname"></span></div>
						 </div>
						 <div class="pull-right">
						 	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						 </div>
						 <div class="clearfix"></div>
	                </h4>
	             </div>
	             
	             <div class="modal-body " style="max-height:517px !important;overflow: auto;margin-top:0px;">
					<div class="col-xs-12 margin-top-8 ">
						<div class="input-group-border">
			            	<form id="form_main" novalidate="novalidate" class="bv-form"><button type="submit" class="bv-hidden-submit" style="display: none; width: 0px; height: 0px;"></button>
				            		<div class="col-lg-2 col-sm-2 col-xs-12 margin-bottom-8 ">
										<div class="avatar-view" style="height:180px" title="更换照片" onclick="showChoosePhoto()">
									      <img src="${ctx }/static/images/maintenanceTest.png" alt="照片">
									    </div>
							  		</div>
							  		<div class="col-lg-10 col-sm-10 col-xs-12">

										<div class=" col-lg-4 col-sm-8 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
											<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 line-height-18">归属</div>
												<div class="font-size-9 line-height-18">Attribution</div>
											</label>
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
												<label class="checkbox-inline ">
													<input  class="noteditable" type="radio" name="wbbs" value="1"  checked="checked" onchange="onchlickradio()" >&nbsp;内部
												</label>
												<label class="checkbox-inline noteditable">
													<input   class="noteditable" type="radio" name="wbbs" value="2"  onchange="onchlickradio()" >&nbsp;外部
												</label>
											</div>
										</div>

						
							  			<div class="col-lg-4 col-sm-8 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group ">
											<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 line-height-18"><span style="color: red" id="xmmark">*</span>员工</div>
												<div class="font-size-9 line-height-18">Name</div>
											</label>
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" id="xm_div">
												<div class="input-group" style="width:100%">
													<input type="text" class="form-control  readonly-style colse-div noteditable" onchange="onchangexm()" ondblclick='openPersonelWin();' id="xm" name="xm" maxlength="100" readonly="readonly">
													<input class="form-control" id="wxryid" type="hidden"/>
													<input class="form-control" id="wxrydaid" type="hidden"/>
													<input class="form-control" id="zpdzD" type="hidden"/>
													<input class="form-control" id="zpdzX" type="hidden"/>
													<span class="required input-group-btn ">
														<button id="wxry_win_Btn" class="btn btn-default form-control" type="button" onclick="openPersonelWin()">
															<i class="icon-search"></i>
														</button>
													</span>
												</div>
											</div>
										</div>
								  		<div class="col-lg-4 col-sm-8 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group ">
											<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 line-height-18"><span style="color: red" id="rybhmark">*</span>编号</div>
												<div class="font-size-9 line-height-18">Staff No.</div>
											</label>
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
												<input type="text" class="form-control noteditable" id="rybh" name="rybh" maxlength="15" readonly="readonly">
											</div>
										</div>
										 <div class="clearfix"></div>
										<div class=" col-lg-4 col-sm-8 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
											<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 line-height-18">性别</div>
												<div class="font-size-9 line-height-18">Gender</div>
											</label>
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
												<label class="checkbox-inline">
													<input type="radio" name="xb" value="1" checked="checked">&nbsp;男
												</label>
												<label class="checkbox-inline">
													<input type="radio" name="xb" value="2">&nbsp;女
												</label>
											</div>
										</div>
										<div class="col-lg-4 col-sm-8 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group ">
											<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 line-height-18">联系电话</div>
												<div class="font-size-9 line-height-18">Phone</div>
											</label>
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
												<input type="text" class="form-control" id="lxdh" name="lxdh" maxlength="100" >
											</div>
										</div>
										<div class="col-lg-4 col-sm-8 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group ">
											<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 line-height-18">邮箱</div>
												<div class="font-size-9 line-height-18">Email</div>
											</label>
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
												<input type="text" class="form-control" id="yxdz" name="yxdz" maxlength="100" >
											</div>
										</div>
										<div class="clearfix"></div>
										<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group ">
											<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" style="width: 11%">
												<div class="font-size-12 line-height-18">家庭成员</div>
												<div class="font-size-9 line-height-18">Family</div>
											</label>
											<div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8  padding-right-0" style="width: 89%">
												<textarea class="form-control" id="jtcy" name="jtcy" style="height:55px"    maxlength="300" ></textarea>
											</div>
										</div>
										
									</div>
								    <div class="clearfix"></div>
			            	</div>

			            	<div class="panel panel-primary">
								<div id="attachHead" class="panel-heading bg-panel-heading">
									<div class="font-size-12" id="chinaHead">授权课程</div>
									<div class="font-size-9" id="englishHead">Attachments</div>
								</div>
						    
							    <div class="panel-body padding-left-8 padding-right-8">
									<span id="left_column" class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
										<div class="font-size-12 margin-topnew-2">授课列表</div>
										<div class="font-size-9">Courseware List</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0" style="overflow-x: auto;">
										<table id="basic_education_table" class="table table-thin table-bordered table-striped table-hover table-set">
										<thead>
											<tr>
												<th class="colwidth-3" style="vertical-align: middle;">
														<button class="line6" type="button" onclick="addCourse()" style="padding:0px 6px;">
															<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
														</button>
												</th>
												<th class="colwidth-10">
													<div class="font-size-12 line-height-18">课程代码</div>
													<div class="font-size-9 line-height-18">Course No.</div>
												</th>
												<th class="colwidth-13">
													<div class="font-size-12 line-height-18">课程名称</div>
													<div class="font-size-9 line-height-18">Course Name</div>
												</th>
												<th class="colwidth-13">
													<div class="font-size-12 line-height-18">飞机机型</div>
													<div class="font-size-9 line-height-18">A/C Type</div>
												</th>
												<th class="colwidth-5">
													<div class="font-size-12 line-height-18">是否复训</div>
													<div class="font-size-9 line-height-18">Whether</div>
												</th>
												<th class="colwidth-20">
													<div class="font-size-12 line-height-18">授权期限</div>
													<div class="font-size-9 line-height-18">Limit</div>
												</th>
											</tr>
										</thead>
										<tbody id="basic_education_list">
											<tr class="non-choice"><td class="text-center" colspan="5">暂无数据 No data.</td></tr>
										</tbody>
									</table>
									</div>
					
								</div>
							</div>
							<div class="clearfix"></div>
							<div id="attachments_list_edit">
								<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
							</div>
						</form>
					</div>	             
	             </div>
	             
	             <div class="modal-footer ">
					<div class="col-xs-12 padding-leftright-8">
								<div class="input-group">
									<span class="input-group-addon modalfootertip">
						                <i class="glyphicon glyphicon-info-sign alert-info" style="display: none;"></i><p class="alert-info-message"></p>
									</span>
									<span class="input-group-addon modalfooterbtn">
				                     	<button onclick="saveUpdate()" class="btn btn-primary padding-top-1 padding-bottom-1" >
				                     		<div class="font-size-12">保存</div>
											<div class="font-size-9">Save</div></button>
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
		
			<!-- 选择头像模态框Start -->
		<div class="modal fade" id="avatar-modal" tabindex="-1" role="dialog" aria-labelledby="avatar-modal-label" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
				  <form class="avatar-form" action="crop.php" enctype="multipart/form-data" method="post">
					<div class="modal-header padding-bottom-5">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<div class="font-size-16 line-height-18">选择头像</div>
						<div class="font-size-9 ">Choose Photo</div>
					</div>
					<div class="modal-body">
		              <div class="avatar-body">
		
		                <!-- Upload image and data -->
		                <div class="avatar-upload">
		                  <input type="hidden" class="avatar-src" name="avatar_src">
		                  <input type="hidden" class="avatar-data" name="avatar_data">
			              <input type="file" class="sr-only avatar-input" id="avatarInput" name="avatar_file" accept="image/jpeg,image/jpg,image/png">
			              <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="$('#avatarInput').click();">
							<div class="font-size-12">&nbsp;&nbsp;选择&nbsp;&nbsp;</div>
							<div class="font-size-9">Choose</div>
						  </button>
			       		 <!--  <label for="avatarInput">Local upload</label>
		                  <input type="file" class="avatar-input" id="avatarInput" name="avatar_file"> -->
		                </div>
		
		                <!-- Crop and preview -->
		                <div class="row">
		                  <div class="col-md-9">
		                    <div class="avatar-wrapper"></div>
		                  </div>
		                  <div class="col-md-3">
		                    <div class="avatar-preview preview-lg"></div>
		                    <div class="avatar-preview preview-md"></div>
		                    <div class="avatar-preview preview-sm"></div>
		                  </div>
		                </div>
		
		                <div class="row avatar-btns">
		                  <div class="col-md-9">
		                    <div class="btn-group">
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="-90" title="Rotate -90 degrees">左旋转</button>
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="-15">向左15度</button>
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="-30">向左30度</button>
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="-45">向左45度</button>
		                    </div>
		                    <div class="btn-group">
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="90" title="Rotate 90 degrees">右旋转</button>
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="15">向右15度</button>
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="30">向右30度</button>
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="45">向右45度</button>
		                    </div>
		                  </div>
		                  <div class="col-md-3">
		                    <button type="submit" class="btn btn-primary btn-block avatar-save">
								确定
							</button>
		                  </div>
		                </div>
		              </div>
		            </div>
		          </form>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		<!-- 选择头像模态框End -->
		
		
		<!-- 课程选择弹出框Start -->
		<div class="modal fade modal-new active in" id="course-list-modal" tabindex="-1" role="dialog" aria-labelledby="avatar-modal-label" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header modal-header-new">
						<h4 class="modal-title">
		                	<div class="pull-left">
		                    	<div class="font-size-12 ">课程列表</div>
								<div class="font-size-9 ">Course List</div>
							</div>
						  	<div class="pull-right">
				  		  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				  		  	</div>
						  	<div class="clearfix"></div>
		              	</h4>
		            </div>		
		            <div class="modal-body" style="max-height:185px !important;overflow: auto;margin-top:0px;">
						<div class="input-group-border margin-top-8 padding-left-0">
								<div class="padding-left-0 padding-right-8 margin-top-0 modalSearch">	
									<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
										<span class="col-lg-4 col-md-4 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">是/否复训</div>
											<div class="font-size-9">Whether</div>
										</span>
										<div class=" col-lg-8 col-md-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0">
											<input name="isFx_search" style=" vertical-align: middle;   margin-top: -1px;" type="checkbox" value="1" checked="checked" />&nbsp;是
											&nbsp;
											<input name="isFx_search" style=" vertical-align: middle;   margin-top: -1px;" type="checkbox" value="0"  checked="checked" />&nbsp;否 
										</div>
									</div>	
									
									<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
										<span class="col-lg-4 col-md-4 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">机型</div>
											<div class="font-size-9">A/C Type</div>
										</span>
										<div class="col-lg-8 col-md-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0">
											<select class="form-control" id="fjjx_search" name="fjjx_search"></select>
										</div>
									</div>
									
									<!-- 搜索框start -->
									 <div class=" pull-right padding-left-0 padding-right-0">
										<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
											<input type="text" placeholder='类别/代码/名称/目标/形式/备注'  class="form-control" id="faculty_open_course_keyword_search" >
										</div>
					                   	<div class=" pull-right padding-left-5 padding-right-0 ">   
										<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="faculty_open_course.searchRevision()">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
					                  		</button>
					                  	</div> 
									</div>
								<!-- 搜索框end -->
								</div>
							
							<div class="col-lg-12 col-md-12 padding-left-8 padding-right-8 " >
								<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
									<table id="faculit_table1 "class="table table-thin table-bordered table-striped table-hover table-set">
										<thead>
											<tr>
												<th class="fixed-column selectAllImg colwidth-5" id="checkAll" style="text-align: center; vertical-align: middle;">
													<a href="#" onclick="faculty_open_course.performSelectAll()"><img src="${ctx}/static/assets/img/d1.png" alt="全选"></a>
													<a href="#" class="margin-left-5" onclick="faculty_open_course.performSelectAllClear()"><img src="${ctx}/static/assets/img/d2.png" alt="不全选"></a>
												</th>
												<th class="colwidth-10 " >
													<div class="important">
														<div class="font-size-12 line-height-18">课程类别</div>
														<div class="font-size-9 line-height-18">Course Type</div>
													</div>
												</th>
												<th class="colwidth-10 " >
													<div class="important">
														<div class="font-size-12 line-height-18">课程代码</div>
														<div class="font-size-9 line-height-18">Course No.</div>
													</div>
												</th>
												<th class="colwidth-10 " >
													<div class="important">
														<div class="font-size-12 line-height-18">课程名称</div>
														<div class="font-size-9 line-height-18">Course Name</div>
													</div>
												</th>
												<th class="colwidth-10 " >
														<div class="font-size-12 line-height-18">机型</div>
														<div class="font-size-9 line-height-18">A/C Type</div>
												</th>
												<th class="colwidth-30 " >
													<div class="important">
														<div class="font-size-12 line-height-18">培训目标</div>
														<div class="font-size-9 line-height-18">Objective</div>
													</div>
												</th>
												<th class="colwidth-7 " >
													<div class="font-size-12 line-height-18">初训学时</div>
													<div class="font-size-9 line-height-18">Hour</div>
												</th>
												<th class="colwidth-10 " >
													<div class="important">
														<div class="font-size-12 line-height-18">初训培训形式</div>
														<div class="font-size-9 line-height-18">Form</div>
													</div>
												</th>
												<th class="colwidth-10 " >
													<div class="important">
														<div class="font-size-12 line-height-18">初训考试形式</div>
														<div class="font-size-9 line-height-18">Form</div>
													</div>
												</th>
												<th class="colwidth-9 " >
													<div class="font-size-12 line-height-18">是/否复训</div>
													<div class="font-size-9 line-height-18">Whether</div>
												</th>
												<th class="colwidth-10">
													<div class="font-size-12 line-height-18">复训间隔</div>
													<div class="font-size-9 line-height-18">Interval</div>
												</th>
												<th class="colwidth-7 " >
													<div class="font-size-12 line-height-18">复训学时</div>
													<div class="font-size-9 line-height-18">Hour</div>
												</th>
												<th class="colwidth-10 " >
													<div class="important">
														<div class="font-size-12 line-height-18">复训培训形式</div>
														<div class="font-size-9 line-height-18">Form</div>
													</div>
												</th>
												<th class="colwidth-10 " >	
													<div class="important">											
														<div class="font-size-12 line-height-18">复训考试形式</div>
														<div class="font-size-9 line-height-18">Form</div>												
													</div>
												</th>
												<th class="colwidth-30 " >	
													<div class="important">											
														<div class="font-size-12 line-height-18">备注</div>
														<div class="font-size-9 line-height-18">Remark</div>
													</div>
												</th>
												<th class="colwidth-13 ">
													<div class="font-size-12 line-height-18">维护人</div>
													<div class="font-size-9 line-height-18">Maintainer</div>
												</th>
												<th class="colwidth-13 ">
													<div class="font-size-12 line-height-18">维护时间</div>
													<div class="font-size-9 line-height-18">Maintenance Time</div>
												</th>
												<th class="colwidth-15 " >
													<div class="font-size-12 line-height-18">组织机构</div>
													<div class="font-size-9 line-height-18">Organization</div>
												</th>
											</tr>
											<tbody id="faculty_open_courseList"></tbody>
										</thead>
									</table>
									</div>
										<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="coursePagination"></div>
										<div class="clearfix"></div>
								    </div>
									<div class="clearfix"></div>
								</div>
							</div>
							 <!-- 底部按钮start -->
				             <div class="modal-footer ">
								<div class="col-xs-12 padding-leftright-8">
									<div class="input-group">
										<span class="input-group-addon modalfootertip">
							                <i class="glyphicon glyphicon-info-sign alert-info" style="display: none;"></i><p class="alert-info-message"></p>
										</span>
										<span class="input-group-addon modalfooterbtn">
					                     	<button onclick="faculty_open_course.save()" class="btn btn-primary padding-top-1 padding-bottom-1" >
					                     		<div class="font-size-12">确定</div>
												<div class="font-size-9">Confirm</div></button>
					                     	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
												<div class="font-size-12">关闭</div>
												<div class="font-size-9">Close</div>
											</button>
										</span>
									</div>
								</div>	             
				             </div>
				             <!-- 底部按钮end -->
							<div class="clearfix"></div>
						</div>
		            </div>

				</div>
			</div>
		</div>
		<!-- 维修技术人员档案弹出框start -->
		<%@ include file="/WEB-INF/views/training/faculty/faculty_user.jsp" %>
		<!-- 维修技术人员档案弹出框end -->