<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看课程信息</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
	<input type="hidden" id="dprtcode" value="${course.dprtcode}" />
	<input type="hidden" id="id" value="${course.id}" />
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>	
			<div class="panel-body ">
				<form id="form">
					<div class="panel panel-default padding-right-0">
						<div class="panel-heading bg-panel-heading">
				        	<div class="font-size-12 ">基础信息</div>
							<div class="font-size-9">Basic Data</div>
					    </div>
					    <div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0 margin-top-8">
					    
							<div id="courseForm" class="col-xs-12 margin-top-8 padding-left-0  padding-right-0">
							
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red"></span>课程代码</div>
										<div class="font-size-9 line-height-18">Course Code</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="kcbh" name="kcbh" value="${course.kcbh}" maxlength="50" />
									</div>
								</div>
							
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red"></span>课程名称</div>
										<div class="font-size-9 line-height-18">Course Name</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="kcmc" name="kcmc" value="${course.kcmc}" maxlength="100" />
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red"></span>课程类别</div>
										<div class="font-size-9 line-height-18">Course Type</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class='form-control' id='kclb' name="kclb" value="${course.kclb}" />
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class='form-control' id='fjjx' name="fjjx" value="${course.fjjx}" />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red"></span>初训学时</div>
										<div class="font-size-9 line-height-18">Hour</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="ks" name="ks" value="${course.ks}时" maxlength="30" />
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red"></span>初训培训形式</div>
										<div class="font-size-9 line-height-18">Form</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class='form-control' id='pxxs' name="pxxs" value="${course.pxxs}" />
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red"></span>初训考试形式</div>
										<div class="font-size-9 line-height-18">Form</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class='form-control' id='ksxs' name="ksxs" value="${course.ksxs}" />
									</div>
								</div>
							
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red"></span>是/否复训</div>
										<div class="font-size-9 line-height-18">Whether</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<span style="margin-right: 20px;font-weight: normal" >
									    	<input name="isFx" type="radio" value="1" <c:if test="${course.isFx == 1}">checked="checked"</c:if> />是
									    </span> 
										<span style="font-weight: normal" >
											<input name="isFx" type="radio" value="0" <c:if test="${course.isFx == 0}">checked="checked"</c:if>/>否 
										</span> 
									</div>
								</div>
								
								<div class="isfx col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">复训学时</div>
										<div class="font-size-9 line-height-18">Hour</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control "  id="fxks" name="fxks" value="${course.fxks}时" maxlength="30" />
									</div>
								</div>
								
								<div class="isfx col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">复训培训形式</div>
										<div class="font-size-9 line-height-18">Form</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class='form-control' id='fxpxxs' name="fxpxxs" value="${course.fxpxxs}" />
									</div>
								</div>
								
								<div class="isfx col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">复训考试形式</div>
										<div class="font-size-9 line-height-18">Form</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class='form-control' id='fxksxs' name="fxksxs" value="${course.fxksxs}" />
									</div>
								</div>
								
								<div class="isfx col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">复训间隔</div>
										<div class="font-size-9 line-height-18">Cycle</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="zqz" name="zqz" value="${course.zqz}<c:forEach items="${cycleEnum}" var="item"><c:if test="${course.zqdw == item.id }">${item.name}</c:if></c:forEach>" maxlength="10" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" id="zdr" name="zdr" value="${erayFns:escapeStr(course.zdr.displayName)}" class="form-control " readonly/>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input class="form-control" id="whsj" name="whsj" value="<fmt:formatDate value='${course.whsj}' pattern='yyyy-MM-dd HH:mm:ss' />" readonly type="text" />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">课程内容</div>
										<div class="font-size-9 line-height-18">Course Content</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="form-control" id="kcnr" name="kcnr" maxlength="600" style="height:55px">${course.kcnr}</textarea>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">培训目标</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="form-control" id="pxmb" name="pxmb" maxlength="600" style="height:55px">${course.pxmb}</textarea>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">教员要求</div>
										<div class="font-size-9 line-height-18">Requirement</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="form-control" id="jyyq" name="jyyq" maxlength="600" >${course.jyyq}</textarea>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">培训机构</div>
										<div class="font-size-9 line-height-18">Training</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="pxjg" name="pxjg" value="${course.pxjg}" maxlength="100" />
									</div>
								</div>
							
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">来源</div>
										<div class="font-size-9 line-height-18">Source</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="ly" name="ly" value="${course.ly}" maxlength="16" />
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">教材/提纲</div>
										<div class="font-size-9 line-height-18">Outline</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="jctg" name="jctg" value="${course.jctg}" maxlength="100" />
									</div>
								</div>
								
								
								<div class="clearfix"></div>
								
								<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="form-control" id="bz" name="bz" maxlength="300" style="height:55px">${course.bz}</textarea>
									</div>
								</div>
							</div>
					    </div>
					</div>
				</form>
				<div class="clearfix"></div>
				<div class="panel panel-default padding-right-0">
					<div class="panel-heading bg-panel-heading">
			        	<div class="font-size-12 ">涉及岗位</div>
						<div class="font-size-9">Related To Post</div>
				    </div>
				    <div class="panel-body padding-bottom-0 padding-left-8 padding-right-8  padding-top-0 margin-top-8">
						<%@ include file="/WEB-INF/views/training/course/post_list_view.jsp" %><!-- 岗位列表 -->
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="panel panel-default padding-right-0">
					<div class="panel-heading bg-panel-heading">
			        	<div class="font-size-12 ">课件信息</div>
						<div class="font-size-9">Courseware Information</div>
				    </div>
				    <div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0 margin-top-8">
				<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit.jsp"%><!-- 加载附件信息 -->
				</div>
				</div>
			</div>
		</div>
	</div>
		<!-- 基本信息 End -->
<script type="text/javascript" src="${ctx}/static/js/thjw/training/course/course_view.js"></script>
</body>
</html>