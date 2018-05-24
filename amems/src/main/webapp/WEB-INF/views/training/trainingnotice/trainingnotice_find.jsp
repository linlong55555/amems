<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看培训计划</title>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content">
	<input type="hidden" id="adjustHeight" value="10">
	<input type="hidden" id="userid" value="${user.id}">
	<input type="hidden" id="id" value="${id}">
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<!--导航结束  -->
			<div class="panel-body">
					<form id="form">
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
								<input type="hidden" class="form-control " id="id" />
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">计划类型</div>
										<div class="font-size-9">Plan Type</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input class='form-control' id='jhlx'>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">培训类别</div>
										<div class="font-size-9">Training Type</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input class='form-control' id='pxlb'>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">课程代码</div>
										<div class="font-size-9">Course Code</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<a href='javascript:void(0);'  onclick="findCourse()" id="kcbh"></a>
										<input type="hidden" class="form-control " id="kcid" />
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">课程名称</div>
										<div class="font-size-9">Course Name</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="kcmc" name="kcmc" maxlength="100" readonly />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">初/复训标识</div>
										<div class="font-size-9">Whether</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<label style="margin-right: 20px;font-weight: normal" onclick="checkFxbs()" >
									    	<input name="fxbs" type="radio" value="1" />初训
									    </label> 
										<label style="font-weight: normal" onclick="checkFxbs()">
											<input name="fxbs" type="radio" value="2" />复训
										</label> 
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">课时</div>
										<div class="font-size-9">Hour</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group">
											<input type="text" class="form-control " id="ks" name="ks" onkeyup='clearNoNumber(this)' maxlength="10" />
											<span name="xh" class="input-group-addon dw"  style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">时</span>
										</div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">培训形式</div>
										<div class="font-size-9">Form</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input class='form-control' id='pxxs'>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">考试形式</div>
										<div class="font-size-9">Form</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input class='form-control' id='ksxs'>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">计划开始日期</div>
										<div class="font-size-9">Start Date</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class='input-group' style="position:relative;z-index:1;">
											 <input type="text" class='form-control datetimepicker' id="jhKsrq" name="jhKsrq" />
											 <span class='input-group-btn'>
											  	<input class='form-control datetimepicker' type='text' style='width:45px;' id='jhKssj' name='jhKssj' readonly/>
											 </span>
										</div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">计划结束日期</div>
										<div class="font-size-9">End Date</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class='input-group' style="position:relative;z-index:1;">
											 <input type="text" class='form-control datetimepicker' id="jhJsrq" name="jhJsrq" />
											 <span class='input-group-btn'>
											  	 <input class='form-control datetimepicker' type='text' style='width:45px;' id='jhJssj' name='jhJssj' readonly/>
											 </span>
										</div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">学天</div>
										<div class="font-size-9">Day</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group">
											<input type="text" class="form-control " id="xt" name="xt" onkeyup='clearNoNumber(this)' maxlength="10" />
											<span name="xh" class="input-group-addon dw"  style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">天</span>
										</div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">主办</div>
										<div class="font-size-9">Host</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="zrr" name="zrr" maxlength="100" />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">培训机构</div>
										<div class="font-size-9">Host</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="pxjg" name="pxjg" maxlength="100" />
									</div>
								</div>
								
							
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">培训预算</div>
										<div class="font-size-9">Budget</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class='input-group' style="position:relative;z-index:1;">
											<input type="text" class="form-control " id="pxys" name="pxys" placeholder='' onkeyup='clearNoNumTwo(this)' maxlength="10" />
											 <span class='input-group-btn'>
												<input type="text" class="form-control " id="pxysBz" style='width:45px;' name="pxysBz" />
											 </span>
										</div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">培训地点</div>
										<div class="font-size-9">Address</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="kcdd" name="kcdd" maxlength="100" />
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">教材发放</div>
										<div class="font-size-9">Grant</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<label style="margin-right: 20px;font-weight: normal" >
									    	<input name="isJcff" type="radio" value="1" />是
									    </label> 
										<label style="font-weight: normal" >
											<input name="isJcff" type="radio" value="0" />否 
										</label>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">机型</div>
										<div class="font-size-9">Plan Type</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input class='form-control' id='jx'>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">专业</div>
										<div class="font-size-9">Skill</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input class='form-control' id='zy'>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">讲师</div>
										<div class="font-size-9">Lecturers</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="jsxm" name="jsxm" maxlength="100" readonly />
										<input type="hidden" class="form-control " id="jsid" />
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">是/否颁证</div>
										<div class="font-size-9">Documonts</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<label style="margin-right: 20px;font-weight: normal" >
									    	<input name="jsBz" type="radio" value="1" />是
									    </label> 
										<label style="font-weight: normal" >
											<input name="jsBz" type="radio" value="0" />否 
										</label>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
			
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">状态</div>
										<div class="font-size-9">State</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input  class="form-control " id="zt" />
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">维护人</div>
										<div class="font-size-9">Maintainer</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input  class="form-control " id="whr" />
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">维护时间</div>
										<div class="font-size-9">Time</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input  class="form-control " id="whsj" />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">课程内容</div>
										<div class="font-size-9">Course Content</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="form-control" id="kcnr" name="kcnr" maxlength="300" style="height:55px" ></textarea>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">备注</div>
										<div class="font-size-9">Note</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="form-control" id="bz" name="bz" maxlength="300" style="height:55px" ></textarea>
									</div>
								</div>
							</div>
						
							<div class="clearfix"></div>
							
					
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">实际开始日期</div>
									<div class="font-size-9">Start Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<div class='input-group' style="position:relative;z-index:1;">
										 <input type="text" class='form-control datetimepicker' id="sjKsrq" name="sjKsrq" />
										 <span class='input-group-btn'>
										  	 <input class='form-control datetimepicker' type='text' style='width:45px;' id='sjKssj' name='sjKssj' readonly/>
										 </span>
									</div>
								</div>
							</div>
							
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">实际结束日期</div>
									<div class="font-size-9">End Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<div class='input-group' style="position:relative;z-index:1;">
										 <input type="text" class='form-control datetimepicker' id="sjJsrq" name="sjJsrq" />
										 <span class='input-group-btn'>
										  	 <input class='form-control datetimepicker' type='text' style='width:45px;' id='sjJssj' name='sjJssj' readonly/>
										 </span>
									</div>
								</div>
							</div>
							
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">实际课时</div>
									<div class="font-size-9">Practical Hours</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<div class="input-group">
										<input type="text" class="form-control " id="sjks" name="sjks" onkeyup='clearNoNumTwo(this)' maxlength="10" />
										<span name="xh" class="input-group-addon dw"  style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">时</span>
									</div>
								</div>
							</div>
						<div class="clearfix"></div>
														
											
						
							<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">课程内容</div>
										<div class="font-size-9">Course Content</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="form-control" id="kcnr" name="kcnr" maxlength="300" style="height:55px" ></textarea>
									</div>
							</div>
							
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">培训对象</div>
									<div class="font-size-9">Object</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<div class='input-group' style="position:relative;z-index:1;">
									     <textarea type="text" class='form-control' id="pxdx" name="pxdx" maxlength="1000" style="width: 1220px"></textarea>
									</div>
								</div>
						  </div>
							
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-8 ">
									<span id="left_column" class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
										<div class="font-size-12 margin-topnew-2">计划培训人员</div>
										<div class="font-size-9">Personnel List</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0" style="overflow-x: auto;">
									
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set padding-right-0"  style="min-width:1250px">
									<thead>
								   		<tr>
										<th class="colwidth-5">
											<div class="font-size-12 line-height-18">序号</div>
											<div class="font-size-9 line-height-18">No.</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">人员代码</div>
											<div class="font-size-9 line-height-18">Personnel Code</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">人员</div>
											<div class="font-size-9 line-height-18">Personnel</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">工作单位/部门</div>
											<div class="font-size-9 line-height-18">Work/Dept</div></th>
										<th class="colwidth-5">
											<div class="font-size-12 line-height-18">应参</div>
											<div class="font-size-9 line-height-18">Should</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 line-height-18">实参</div>
											<div class="font-size-9 line-height-18">Real</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 line-height-18">出勤率%</div>
											<div class="font-size-9 line-height-18">Attendance%</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 line-height-18">成绩</div>
											<div class="font-size-9 line-height-18">Result</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">考核结果</div>
											<div class="font-size-9 line-height-18">Result</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">证书</div>
											<div class="font-size-9 line-height-18">Certificate</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">下次培训日期</div>
											<div class="font-size-9 line-height-18">Next Date</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 line-height-18">备注</div>
											<div class="font-size-9 line-height-18">Remark</div>
										</th>
										<th class="colwidth-30" colspan="2">
											<div class="font-size-12 line-height-18">附件</div>
											<div class="font-size-9 line-height-18">Attachment</div>
										</th>
										
								 		 </tr>
									</thead>
									<tbody id="messageListTable">
									
									</tbody>
								</table>
								</div>
							</div>
						<div class="clearfix"></div>
							<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit.jsp"%><!-- 加载附件信息 -->
						<div class="clearfix"></div>
							
						</form>
						
				
                  		<br/>
		                     
			</div>
	</div>
</div>	
<script type="text/javascript" src="${ctx}/static/js/thjw/training/trainingnotice/trainingnotice_find.js"></script><!--当前页面JS  -->
</body>
</html>
