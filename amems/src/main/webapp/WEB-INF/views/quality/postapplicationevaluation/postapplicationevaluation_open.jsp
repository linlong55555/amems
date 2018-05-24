<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="Post_Open_Modal" tabindex="-1" role="dialog" aria-hidden="true" data-keyboard="false" aria-labelledby="Post_Open_Modal" data-backdrop="static" >
	<div class="modal-dialog" style='width:75%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
				<h4 class="modal-title" >
                	<div class='pull-left'>
                    	<div class="font-size-12" id="modalHeadCN">岗位评估</div>
						<div class="font-size-9" id="modalHeadENG">Post</div>
					</div>
				  	<div class='pull-right' >
		  		  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
		  		  	</div>
				  	<div class='clearfix'></div>
              	</h4>
            </div>
            <div class='clearfix'></div>
		    <div class="modal-body  " >
				<form>	 
				    <div class='col-xs-12'>
		            	<div class="input-group-border margin-top-8 padding-left-0 input-group-border margin-top-8 padding-left-0 fixedModalHeader">
			            	<!-- 授权申请单号 -->
			            	<div class="div-hide col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">授权单号</div>
									<div class="font-size-9">Authorized No.</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="sqsqdh" name="sqsqdh" type="text" readonly="readonly"/>
								</div>
							</div>
							
							<!-- 姓名 -->
							<div class="div-hide col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">姓名</div>
									<div class="font-size-9 ">Name</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="xm" name="xm" type="text" readonly="readonly"/>
								</div>
							</div>
							
							<!-- 搜索效果 -->
							<div style='position:relative;' ><div style='position:absolute;width:130px;background:white;z-index:50;text-align:center;border:1px solid #d5d5d5;border-top:0px;padding-top:3px;padding-bottom:3px;cursor:pointer;left:50%;bottom:-16px;' onclick='Post_Open_Modal.toggleRyinfo(this)'>收起人员信息<i class='fa fa-angle-down margin-left-5'></i></div></div>
							<!-- 搜索效果 -->
							
							<div class='clearfix'></div>
							<!-- 性别 -->
							<div class="evaluationRyInfo col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">性别</div>
									<div class="font-size-9">Sex</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control"  type="text" id="sex" readonly/>
								</div>
							</div>
							<!-- 工号 -->
							<div class="evaluationRyInfo col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">人员编号</div>
									<div class="font-size-9">Staff No.</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control"  type="text" id="rybh" readonly/>
								</div>
							</div>
							<!-- 出生年月 -->
							<div class="evaluationRyInfo col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group ">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">出生日期</div>
									<div class="font-size-9 ">Birthdate</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control "  type="text" id="sr" disabled="disabled"/>
								</div>
							</div>
							<!-- 参加工作日期 -->
							<div class="evaluationRyInfo col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">参加工作日期</div>
									<div class="font-size-9 ">Work Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control "  type="text" id="cjgzrq" disabled="disabled"/>
								</div>
							</div>
							<!-- 加入公司日期 -->
							<div class="evaluationRyInfo col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">入职日期</div>
									<div class="font-size-9">Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control "  type="text" id="rzrq" disabled="disabled"/>
								</div>
							</div>
							<!-- 部门 -->
							<div class="evaluationRyInfo col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">单位/部门</div>
									<div class="font-size-9">Department</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control"  id="szdw" type="text" readonly/>
								</div>
							</div>
							<!-- 现任职务 -->
							<div class="evaluationRyInfo col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">现任职务</div>
									<div class="font-size-9">Current Position</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="gwzw" name="gwzw" type="text" readonly/>
								</div>
							</div>
							
							<div class="evaluationRyInfo div-hide pull-right col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">状态</div>
									<div class="font-size-9">Status</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="ztms" name="ztms" type="text" readonly="readonly"/>
								</div>
							</div>
							
							<!-- 学历/专业 -->
							<div class="evaluationRyInfo col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">学历/专业</div>
									<div class="font-size-9">Education/Major</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<input id="xl"  type="text" class="form-control" readonly />
								</div>
							</div>
							<div class='clearfix'></div>
						</div>
						<!-- 搜索效果 -->
						<div style='position:relative;' id='showReview'><div style='position:absolute;width:130px;background:white;z-index:50;text-align:center;border:1px solid #d5d5d5;border-top:0px;padding-top:3px;padding-bottom:3px;cursor:pointer;left:50%;bottom:-16px;' onclick='Post_Open_Modal.toggleBaseinfo(this)'>收起审核信息<i class='fa fa-angle-down margin-left-5'></i></div></div>
						<!-- 搜索效果 -->
						<div class='nonFixedContent' id="scroll_div">
					    	<div class='evaluationBaseInfo'>
						    	<!-- 执照信息 -->
								<div class="input-group-border margin-top-8 padding-left-0">
					            	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">其他执照</div>
											<div class="font-size-9">Leader</div>
										</span>
										<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<input class="form-control noteditable"  type="text" id="qtzz" maxlength="1300"/>
										</div>
									</div>
								 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">有效期</div>
											<div class="font-size-9">Validity</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<input class="form-control date-picker noteditable"type="text" id="qtzzyxq"/>
										</div>
									</div>
									<div class='clearfix'></div>
								 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">申请类型</div>
											<div class="font-size-9">Type</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<label  style='margin-top:6px;font-weight:normal;' >
												<input type='radio' class="noteditable" style='vertical-align:text-bottom' checked="checked" name="sqlx" value="1"/>&nbsp;初次
											</label>
											<label class='padding-left-5' style='margin-top:6px;font-weight:normal;' >
												<input type='radio'  class="noteditable" style='vertical-align:text-bottom' name="sqlx" value="2"/>&nbsp;延期
											</label>
											<label class='padding-left-5' style='margin-top:6px;font-weight:normal;' >
												<input type='radio'  class="noteditable" style='vertical-align:text-bottom' name="sqlx" value="3"/>&nbsp;增加
											</label>
										</div>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">申请授权项目</div>
											<div class="font-size-9">Project</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<input class="form-control" id="sqgwid" type="hidden"/>
											<input type="text" class="form-control" id="sqgwms" name="sqgwms" readonly="readonly" />
										 </div>
									 </div>
							 	    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">项目名称</div>
											<div class="font-size-9">Project Name</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<input class="form-control  noteditable"type="text" id="dgmc" readonly="readonly"/>
										</div>
									</div>
									 <div class='clearfix'></div>
									 <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">备注</div>
											<div class="font-size-9">Remark</div>
										</span>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea class="form-control noteditable"  type="text" id="sqbz" style="height:55px" maxlength="1300"></textarea>
										</div>
									</div>
									<div class='clearfix'></div>
								</div>
								
								<div class="input-group-border padding-left-0">
		                   			<%@ include file="/WEB-INF/views/quality/postapplication/maintenancepersonnel_license.jsp"%><!--执照信息  -->
		                   			<div class='clearfix'></div>
		                   		</div>
								
								<!-- 教育经历/外语水平 -->
								<div class="panel panel-primary">
									<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
										<a data-toggle="collapse" data-parent="#accordion" href="#educationForeign" >
										<div class="pull-left">
										<div class="font-size-12">教育经历/外语水平</div>
										<div class="font-size-9 ">Education/Foreign</div>
										</div>
										<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
										<div class="clearfix"></div>
										</a>
									</div>
									<div id="educationForeign" class="panel-collapse collapse">
										<%@ include file="/WEB-INF/views/quality/postapplication/maintenancepersonnel_educationorforeign.jsp"%><!--教育经历/外语水平  -->
									</div>
								</div>
								<!-- 工作经历 -->
								<div class="panel panel-primary">
									<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
										<a data-toggle="collapse" data-parent="#accordion" href="#workExperience" >
										<div class="pull-left">
										<div class="font-size-12">工作经历 </div>
										<div class="font-size-9 ">Work Experience</div>
										</div>
										<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
										<div class="clearfix"></div>
										</a>
									</div>
									<div id="workExperience" class="panel-collapse collapse">
										<%@ include file="/WEB-INF/views/quality/postapplication/maintenancepersonnel_workexperience.jsp"%><!--工作经历  -->
									</div>
								</div>
								<!-- 培训课程 -->
								<div  class="panel panel-primary">
									<ul id="myTab" class="nav nav-tabs tabNew-style" style='position:relative;border:0px;'>
						               	<li  onclick='showTab()'>
						               		<a id="material_tab"  href="#CourseTraining" data-toggle="tab" aria-expanded="false" >
						               			<div class="font-size-12 line-height-12">课程培训</div>
						              			<div class="font-size-9 line-height-9">Course Training </div>
						               		</a>
						               	</li>
						                <li class="" onclick='showTab()'>
											<a id='tool_tab'  href='#MeTraining' data-toggle="tab"  >
												<div class="font-size-12 line-height-12">全部培训</div>
						                   		<div class="font-size-9 line-height-9">All Training </div>
						                	</a>
										</li>
									 	<a data-toggle="collapse" data-parent="#accordion" onclick='collapseHide()' href="#trainingCourse"  style='position:absolute;right:8px;top:-5px;'>
							           		<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
											<div class="clearfix"></div>
										</a>
					           		</ul>
									<div id="trainingCourse" class="panel-collapse collapse margin-bottom-8">
									  <div id="myTabContent" class="tab-content" style='border:0px'>
						             	<div class="tab-pane" id="CourseTraining"  >
						             		<%@ include file="/WEB-INF/views/quality/postapplication/maintenancepersonnel_trainingcourse.jsp"%><!--课程培训  -->
						             	</div>
						             	<div class="tab-pane fade " id="MeTraining" >
						            		 <%@ include file="/WEB-INF/views/quality/postapplication/maintenancepersonnel_metraining.jsp"%><!--本人培训  -->
						             	</div>
					           		 </div>
					       		 	</div>
								</div>
								<!-- 证书附件信息 -->
								<div class="panel panel-primary">
									<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
										<a data-toggle="collapse" data-parent="#accordion" href="#attachmentInfo" >
										<div class="pull-left">
										<div class="font-size-12">证书附件信息</div>
										<div class="font-size-9 ">Certificate Attachment Info</div>
										</div>
										<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
										<div class="clearfix"></div>
										</a>
									</div>
									<div id="attachmentInfo" class="panel-collapse collapse">
										<div id="attachments_list_cert_view" style="display:none">
											<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common_view.jsp"%><!-- 加载附件信息 -->
										</div>
									</div>
								</div>
				    			<div class="input-group-border margin-top-8 padding-left-0">
						            <div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-2 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">声明</div>
											<div class="font-size-9">Statement</div>
										</span>
										<div class="col-lg-10 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<input class="form-control " disabled="disabled" id="smsm" value=""/>
										</div>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">申请人</div>
											<div class="font-size-9">Applicant</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<input class="form-control"  type="text" id="sqr" disabled="disabled"/>
										</div>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">申请日期</div>
											<div class="font-size-9">Date</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<input class="form-control "  type="text" id="sqsj" disabled="disabled"/>
										</div>
									</div>
									<div class='clearfix'></div>
								</div>
								<div class="input-group-border margin-top-8 padding-left-0">
									<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">审核意见</div>
											<div class="font-size-9">Review Opinion</div>
										</span>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea id="shyj" style='height:55px;' class='form-control' disabled="disabled"></textarea>
										</div>
									</div>
						            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">审核人</div>
											<div class="font-size-9">Reviewer</div>
										</span>
										
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										  <input id="shr" type='text' class='form-control'  disabled="disabled" />
										</div>
										
									</div>
									 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">审核日期</div>
											<div class="font-size-9">Audit Date</div>
										</span>
										
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										  	<input id="shrq" class="form-control date-picker"  type="text"  disabled="disabled"/>
										</div>
									</div>
									<div class='clearfix'></div>
								</div>
							</div>
					
					<div style='margin-bottom:8px;'>
					<small >以下由质量处按《维修人员培训大纲》要求审核人员资质</small>
					<!-- <label>展开审核基本信息&nbsp;<i class='fa fa-angle-down margin-left-5'></i></label> -->
					</div>
					<div class='clearfix'></div>
					<!-- 培训评估 -->
					<div  class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#qualificationAssessment" >
							<div class="pull-left">
							<div class="font-size-12">培训评估</div>
							<div class="font-size-9 ">Qualification Assessment</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="qualificationAssessment" class="panel-collapse collapse">
							<%@ include file="/WEB-INF/views/quality/postapplicationevaluation/qualification_assessment_edit.jsp" %>
						</div>
					</div>
					<!-- 人员资质评估 -->
					<div  class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#trainingEvaluation" >
							<div class="pull-left">
							<div class="font-size-12">人员资质评估</div>
							<div class="font-size-9 ">Training Evaluation</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="trainingEvaluation" class="panel-collapse collapse">
							<%@ include file="/WEB-INF/views/quality/postapplicationevaluation/training_evaluation_edit.jsp" %>
						</div>
					</div>
					<!-- 评估结论 -->
					<div  class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#appraisalConclusion" >
							<div class="pull-left">
							<div class="font-size-12">评估结论</div>
							<div class="font-size-9 ">Appraisal conclusion</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="appraisalConclusion" class="panel-collapse collapse">
						    <div class="panel-body padding-left-0 padding-right-0" >
						    <!-- 是否合格 -->
						     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<!-- <div class="font-size-12 margin-topnew-2">评估人</div>
									<div class="font-size-9 ">Evaluator</div> -->
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<label style='margin-top:6px;font-weight:normal;' >
									<input class="pgjl" type='radio' name="pgjl" value='1' style='vertical-align:text-bottom' onclick="Post_Open_Modal.checkedPgjl()" />&nbsp;合格
									</label>
									<label style='margin-top:6px;font-weight:normal;' >
									<input class="pgjl" type='radio' name="pgjl" value='0' style='vertical-align:text-bottom' onclick="Post_Open_Modal.checkedPgjl()" />&nbsp;不合格
									</label>
								</div>
					        </div>
						    <!-- 评估人 -->
						     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">评估人</div>
									<div class="font-size-9 ">Evaluator</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="pgr" class="form-control"  type="text"  readonly="readonly"/>
								</div>
					        </div>
					        <!-- 评估日期 -->
							 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">评估日期</div>
									<div class="font-size-9 ">date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="pgrq" class="form-control"  type="text"  readonly="readonly"/>
								</div>
					        </div>
					        <div class='clearfix'></div>
					        <!-- 授权开始日期 -->
					        <div class="sqrq col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">授权开始日期</div>
									<div class="font-size-9 ">Start date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="sqksrq" class="form-control date-picker"  type="text" />
								</div>
					        </div>
					        <!-- 授权截止日期 -->
					         <div class="sqrq col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">授权截止日期</div>
									<div class="font-size-9 ">Closing date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="sqjzrq" class="form-control date-picker"  type="text" />
								</div>
					        </div>
					        
					        <div class='clearfix'></div>
					        <div id="evaluat_plane_model" class="jslb">
		             		<%@ include file="/WEB-INF/views/quality/postapplicationevaluation/plane_model_list_edit.jsp"%><!--机型  -->
					        </div>
		               		<div class='clearfix'></div>
					        
						</div>
						</div>
					</div>
					<!-- 偏离评估 -->
					<div  class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#deviationEvaluation" >
							<div class="pull-left">
							<div class="font-size-12">偏离评估</div>
							<div class="font-size-9 ">Deviation evaluation</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="deviationEvaluation" class="panel-collapse collapse">
						    <div class="panel-body padding-left-0 padding-right-0" >
						    <!-- 是否合格 -->
						     <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<label  style='margin-top:6px;font-weight:normal;' >
									<input type='radio' name="plpgjg" value='1' style='vertical-align:text-bottom' />&nbsp;同意偏离&nbsp;&nbsp;&nbsp;
									</label>
									<label  style='margin-top:6px;font-weight:normal;' >
									<input type='radio' name="plpgjg" value='2' style='vertical-align:text-bottom' />&nbsp;不同意偏离&nbsp;&nbsp;&nbsp;
									</label>
									<label  style='margin-top:6px;font-weight:normal;' >
									<input type='radio' name="plpgjg" value='9' style='vertical-align:text-bottom' />&nbsp;不适用
									</label>
								</div>
								
					        </div>
						    <!-- 偏离考核成绩 -->
						     <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">偏离考核成绩</div>
									<div class="font-size-9 ">Result</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								   <div class='col-xs-6 padding-left-0 padding-right-0'>
								   <input id="plkhcj" class="form-control"  type="text"  maxlength="100"/>
								   </div>
								</div>
							</div>
						    <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									评估记录具体见后附《人员授权评估表》
								</div>
								
					        </div>
					        
						</div>
						</div>
					</div>
					
					<!-- 授权书附件 -->
					<div  class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#authorizationAttachment" >
							<div class="pull-left">
							<div class="font-size-12">授权书附件</div>
							<div class="font-size-9 ">Authorization Attachment</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="authorizationAttachment" class="panel-collapse collapse">
						    <div id="attachments_list_authorization_eidt" style="display:none">
								<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common_view.jsp"%><!-- 加载附件信息 -->
							</div>
						</div>
					</div>
					</div>
					</div>
				</form>
			</div>	
		 	<div class="clearfix"></div>
			<div class="modal-footer ">
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="pull-left modalfootertip" >
				               <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
					 	<span class="input-group-addon modalfooterbtn">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="Post_Open_Modal.setData();">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
					   		<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="Post_Open_Modal.close();">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/postapplicationevaluation/postapplicationevaluation_open.js"></script><!--当前界面js  -->