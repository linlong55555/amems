<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看岗位授权</title>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-content">
<input type="hidden" id="viewid" value="${viewid}">
<input type="hidden" id="id" value="${viewid}">
<input type="hidden" id="dprtcode" >
	<div class="panel panel-primary" >
		<div class="panel-heading" id="NavigationBar"></div>
		<form id="Post_Open_Modal" >
			<div class="panel-body padding-bottom-0"> 
	            <div class="input-group-border margin-top-0 padding-left-0 input-group-border margin-top-0 padding-left-0 fixedModalHeader">
	            <!-- 授权申请单号 -->
	            <div class="div-hide col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">授权单号</div>
							<div class="font-size-9">Authorization No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="sqsqdh" name="sqsqdh" type="text" readonly="readonly"/>
						</div>
				</div>
				<div class="div-hide pull-right col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="ztms" name="ztms" type="text" readonly="readonly"/>
						</div>
				</div>
				<div class='clearfix'></div>
				<!-- 姓名 -->
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">
							<span class="required" style="color: red">*</span>姓名
						</div>
						<div class="font-size-9 ">Name</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<div class="input-group" style="width: 100%">
							<input class="form-control" id="wxryid" type="hidden"/>
							<input class="form-control" id="wxrydaid" type="hidden"/>
							<input type="text"  name="xm" id="xm"  class="form-control noteditable readonly-style colse"  readonly="readonly" ondblclick="Post_Open_Modal.openPersonelWin();">
							<span class="required input-group-btn" >
								<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Post_Open_Modal.openPersonelWin();">
									<i class="icon-search cursor-pointer" ></i>
								</button>
							</span>
	                	</div>
					</div>
				</div>
				<!-- 性别 -->
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">性别</div>
							<div class="font-size-9">Sex</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control"  type="text" id="sex" readonly/>
						</div>
				</div>
				<!-- 工号 -->
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">人员编号</div>
							<div class="font-size-9">Staff No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control"  type="text" id="rybh" readonly/>
						</div>
				</div>
				<!-- 出生年月 -->
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group ">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">出生日期</div>
						<div class="font-size-9 ">Birthdate</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input class="form-control "  type="text" id="sr" disabled="disabled"/>
					</div>
				</div>
				<!-- 参加工作日期 -->
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">参加工作日期</div>
							<div class="font-size-9 ">Work Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control "  type="text" id="cjgzrq" disabled="disabled"/>
						</div>
				</div>
				<!-- 加入公司日期 -->
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">入职日期</div>
							<div class="font-size-9">Employment Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control "  type="text" id="rzrq" disabled="disabled"/>
						</div>
				</div>
				<!-- 部门 -->
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">单位/部门</div>
							<div class="font-size-9">Department</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control"  id="szdw" type="text" readonly/>
						</div>
				</div>
				<!-- 现任职务 -->
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">现任职务</div>
							<div class="font-size-9">Current Position</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="gwzw" name="gwzw" type="text" readonly/>
						</div>
				</div>
				<!-- 学历/专业 -->
				<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
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
				
				<div class='nonFixedContent'>
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
							<span  style='margin-top:6px;font-weight:normal;' >
								<input type='radio' class="noteditable" style='vertical-align:text-bottom' checked="checked" name="sqlx" value="1"/>&nbsp;初次
							</span>
							<span class='padding-left-5' style='margin-top:6px;font-weight:normal;' >
								<input type='radio'  class="noteditable" style='vertical-align:text-bottom' name="sqlx" value="2"/>&nbsp;延期
							</span>
							<span class='padding-left-5' style='margin-top:6px;font-weight:normal;' >
								<input type='radio'  class="noteditable" style='vertical-align:text-bottom' name="sqlx" value="3"/>&nbsp;增加
							</span>
						</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>申请授权项目</div>
							<div class="font-size-9">Project</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class='input-group' style="width: 100%">
							<input class="form-control" id="sqgwid" type="hidden"/>
							<input type="text" class="form-control readonly-style colse"   id="sqgwms" name="sqgwms"   readonly="readonly" ondblclick="Post_Open_Modal.openProjectWin();"/>
							<span class="required input-group-btn" >
								<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Post_Open_Modal.openProjectWin();">
									<i class="icon-search cursor-pointer" ></i>
								</button>
							</span>
							</div>
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
             		<%@ include file="/WEB-INF/views/quality/postapplication/plane_model_list_edit.jsp"%><!--机型  -->
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
				<div  class="panel panel-primary">
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
				<div  class="panel panel-primary">
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
				<div  class="panel panel-primary">
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
						<div id="attachments_list_edit" style="display:none">
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
						<input class="form-control "    disabled="disabled" id="smsm" value=""/>
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
							<div class="font-size-9">Application Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control "  type="text" id="sqsj" disabled="disabled"/>
						</div>
				</div>
				<div class='clearfix'></div>
				</div>
				<div class="input-group-border margin-top-8 padding-left-0">
	            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">审核领导</div>
							<div class="font-size-9">Leader</div>
						</span>
						
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
						  <div class='input-group' style="width: 100%">
								<input type="hidden"  name="shrid"  id="shrid">
									<input type="hidden"  name="shrbmdm"  id="shrbmdm">
									<input type="text"  name="shr" id="shr"  class="form-control  readonly-style colse"  readonly="readonly" ondblclick="Post_Open_Modal.openUser('shr');">
									<span class="required input-group-btn" >
									<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Post_Open_Modal.openUser('shr');">
										<i class="icon-search cursor-pointer" ></i>
									</button>
								</span>
							</div>
						</div>
				</div>
				<div class='clearfix'></div>
		            <div class="col-lg-12 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-1 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">审核意见</div>
							<div class="font-size-9">Audit Opinion</div>
						</span>
						<div class="col-lg-11 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
						 	<textarea class="noteditable form-control padding-left-3 padding-right-3" id="shyj" style="height:55px" maxlength="300"></textarea>
						</div>
					</div>
				<div class='clearfix'></div>
				</div>
				</div>
			</div>
		</form>
		<div class="clearfix"></div>
	</div>	
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/postapplication/postapplication_open.js"></script><!--岗位申请通用弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/postapplication/postapplication_view.js"></script><!--查看岗位申请弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 附件对话框 -->
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
</body>
</html>