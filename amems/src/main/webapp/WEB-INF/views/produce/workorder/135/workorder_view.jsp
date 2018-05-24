<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工单管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style>
label{
font-weight:normal;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	//导航
	Navigation(menuCode,"查看工单","View Workorder");
});
var todo_lyid = "${param.todo_lyid}";
</script>

</head>
<body class="page-header-fixed">
	 <input type="hidden" id="gdid" value="${gdid}" >
	 <div class="page-content"  id="WOViewModal">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><!-- <span style="color: red">*</span> -->工单编号</div>
								<div class="font-size-9">Work Order</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="wo135add_gdbh" name="wo135add_gdbh" type="text" maxlength="15" disabled="disabled"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><!-- <span style="color: red">*</span> -->飞机注册号</div>
								<div class="font-size-9">A/C REG.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="wo135add_fjzch" name="wo135add_fjzch" class="form-control" disabled="disabled" >
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><!-- <span style="color: red">*</span> -->ATA章节号</div>
								<div class="font-size-9">ATA</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input id="wo135add_zjhName" type="text" class="form-control div-readonly-style" disabled="disabled" />
									<input id="wo135add_zjhid" type="hidden" class="form-control" />
			                	</div><!-- /input-group -->
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">必检</div>
								<div class="font-size-9">RII</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<label class='margin-right-5 label-input' ><input id="wo135add_bjbs" type="checkbox" disabled="disabled"/></label>
								<label class='label-input  margin-left-10' ><span>打印次数:</span><span id="wo135add_dycs" style='margin-left:10px'></span></label>
							</div>
						</div>
						
						
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">来源</div>
								<div class="font-size-9">Originating</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
		                       <input id="wo135add_gdlx" type="text" class="form-control " disabled="disabled"  />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">来源任务号</div>
								<div class="font-size-9">Task</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input id="wo135add_lyrwh" type="text" class="form-control div-readonly-style" disabled="disabled"  />
									<input id="wo135add_lyrwid" type="hidden" class="form-control" />
			                	</div><!-- /input-group -->
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">来源工卡</div>
								<div class="font-size-9">W/C</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input id="wo135add_gkbh" type="text" class="form-control  div-readonly-style" disabled="disabled" />
									<input id="wo135add_gkid" type="hidden" class="form-control" />
			                	</div><!-- /input-group -->
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><!-- <span style="color: red">*</span> -->状态</div>
								<div class="font-size-9">Status</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="wo135add_zt" name="" type="text" maxlength="100" disabled="disabled"/>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2" id="gdbtName">工单标题</div>
								<div class="font-size-9" id="gdbtEname">Title</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="wo135add_gdbt" name="wo135add_gdbt" style="height: 55px;" class="form-control" maxlength="1300" disabled="disabled" ></textarea>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工包号</div>
								<div class="font-size-9">Package No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input class="form-control" id="wo135add_gbid" name="" type="hidden" disabled="disabled"/>
									<input class="form-control" id="wo135add_gbbh" name="" type="text" disabled="disabled"/>
								</div>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">制单日期</div>
								<div class="font-size-9">Create Date</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="wo135add_kdrq" name="" type="text" class="form-control date-picker " maxlength="10" data-date-format="yyyy-mm-dd" disabled="disabled"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><!-- <span style="color: red">*</span> -->计划开始日期</div>
								<div class="font-size-9">Plan Start Date</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="wo135add_jh_ksrq" name="wo135add_jh_ksrq" type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" disabled="disabled"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><!-- <span style="color: red">*</span> -->计划结束日期</div>
								<div class="font-size-9">Plan End Date</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="wo135add_jh_jsrq" name="wo135add_jh_jsrq" type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" disabled="disabled"/>
							</div>
						</div>
						
						
						<div class="clearfix"></div>
					
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">报告人</div>
								<div class="font-size-9">Reporter</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input id="wo135add_bgr" type="text" class="form-control div-readonly-style" disabled="disabled" />
									<input id="wo135add_bgrid" type="hidden" class="form-control" />
			                	</div>
							</div>
						</div>
  							
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">专业</div>
								<div class="font-size-9">Trade</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="wo135add_zy" class="form-control" disabled="disabled">
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><!-- <span style="color: red">*</span> -->站点</div>
								<div class="font-size-9">Station</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="wo135add_jh_zd" name="" type="text" maxlength="100" disabled="disabled"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工作类别</div>
								<div class="font-size-9">Job Type</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="wo135add_gzlb" class="form-control" disabled="disabled">
								</select>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">区域</div>
								<div class="font-size-9">Zone</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group" style="min-width:17.2%;">
									<div id="eqy" class='form-control base-color div-readonly-style' style='border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'></div> 
			                	</div>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">接近</div>
								<div class="font-size-9">Access</div>
							</label>
							<div id="jj_div_edit" class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group" style="min-width:17.2%;">
									<div id="jj_e" class='form-control base-color div-readonly-style' style='border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'></div> 
			                	</div>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">飞机站位</div>
								<div class="font-size-9">Station</div>
							</label>
							<div id="fjzw_div_edit" class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group" style="min-width:17.2%;">
									<div id="efjzw" class='form-control base-color div-readonly-style' style='border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'></div> 
			                	</div>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">计划工时</div>
								<div class="font-size-9">MHRs</div>
							</label>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-9 padding-leftright-8">
							<!-- <div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" > -->
								<div class="input-group input-group-new" >
									<input id="wo135add_jhgs_rs" name="" type="text" class="form-control" disabled="disabled" style="min-width: 80px;"/>
			                    	<span class="input-group-addon">人&nbsp;x</span>
			                    	<input id="wo135add_jhgs_xss" name="jhgsXss" type="text" class="form-control" disabled="disabled" style="min-width: 80px;" />
			                     	<span class="input-group-addon">时=<span id="wo135add_bzgs">0</span>时</span>
			                     	<%@ include file="/WEB-INF/views/common/project/work_hour_edit.jsp"%><!-- 工种工时 --> 
			                	</div>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">完成时限</div>
								<div class="font-size-9">Completion Time Limit</div>
							</label>
							<div class="col-lg-10 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								 <input id="wo135add_wcsx" type="text" class="form-control" disabled="disabled"  />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">部件件号</div>
								<div class="font-size-9">P/N</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="wo135add_bjh" name="" type="text" maxlength="100" disabled="disabled"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">部件序列号</div>
								<div class="font-size-9">S/N</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="wo135add_xlh" name="" type="text" maxlength="100" disabled="disabled"/>
							</div>
						</div>
						
						<div class="clearfix"></div>
						<div id="wo135add_jspgddiv">
						 	<%@ include file="/WEB-INF/views/open_win/evaluationList.jsp"%><!-- -评估单列表 -->
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">一般概述</div>
								<div class="font-size-9">General Overview</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea style="height: 95px;" class="form-control" id="wo135add_ybgs" name="" maxlength="1000" disabled="disabled"></textarea>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group" id="qxmsDiv">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">缺陷描述</div>
								<div class="font-size-9">Defect Desc</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea style="height: 95px;" class="form-control" id="wo135add_qxms" name="" maxlength="1000" disabled="disabled"></textarea>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">建议处理方案</div>
								<div class="font-size-9">Treatment Scheme</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea style="height: 75px;" class="form-control" id="wo135add_jyclfa" name="" maxlength="1000" disabled="disabled"></textarea>
							</div>
						</div>
						
						<div class="clearfix"></div>
						<!-- 完工反馈 -->
						<div class="panel panel-primary" id="wo135wgfkdiv" style="display: none;">
							<div class="panel-heading bg-panel-heading" >
								<div class="font-size-12 " id="cName">完工反馈</div>
								<div class="font-size-9" id="eName">Feedback</div>
							</div>
							<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">实际执行单位</div>
										<div class="font-size-9">Department</div>
									</span>					    						
							    <div class="col-lg-8 col-md-9 col-sm-8 col-xs-9  padding-leftright-8 ">
										<input type="text" class="form-control " id="wo135feedbacktab_sjZxdw" disabled="disabled" />						
								</div>
								</div>	
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">实际完成日期</div>
										<div class="font-size-9">Actual Date</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="wo135feedbacktab_sjJsrq" disabled="disabled" />
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">工作者</div>
										<div class="font-size-9">Worker</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="wo135feedbacktab_sjgzz" disabled="disabled" />
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">检查者</div>
										<div class="font-size-9">Checker</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="wo135feedbacktab_sjjcz" disabled="disabled" />
									</div>
								</div>			
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">实际工时</div>
										<div class="font-size-9">Actual Time</div>
									</span>
									<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 padding-leftright-8">
										<input type="text" class="form-control " id="wo135feedbacktab_sjgs" disabled="disabled" />
									</div>
									<div class="col-xs-4 col-sm-4 padding-left-9 padding-right-0">
										<label class="pull-right" style="margin-top:6px;font-weight:normal;">
											<input id="wo135feedbacktab_hsgs" value="1" style="vertical-align:text-bottom" type="checkbox" disabled="disabled">&nbsp;核算工时&nbsp;&nbsp;
										</label>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">工作站点station</div>
										<div class="font-size-9">Work Site</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="wo135feedbacktab_sjzd" disabled="disabled" />
									</div>
								</div>								
								<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">故障信息</div>
										<div class="font-size-9">Fault</div>
									</span>
									<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea id="wo135feedbacktab_gzxx" name="wo135feedbacktab_gzxx" class="form-control" disabled="disabled" maxlength="100" style="height:55px"></textarea>
									</div>
								</div>
								<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">处理措施</div>
										<div class="font-size-9">Measures</div>
									</span>
									<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea id="wo135feedbacktab_clcs" name="wo135feedbacktab_clcs" class="form-control" disabled="disabled" maxlength="100" style="height:55px"></textarea>
									</div>
								</div>								
						    </div>
						</div>
						<!-- 完工关闭 -->
						<div class="panel panel-primary" id="wo135wggbdiv" style="display: none;">
							<div class="panel-heading bg-panel-heading" >
								<div class="font-size-12 " id="cName">完工关闭</div>
								<div class="font-size-9" id="eName">Close</div>
							</div>
							<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">类型</div>
									<div class="font-size-9">Type</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo135wgclose_gdlx" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">下发单位</div>
									<div class="font-size-9">Issuing unit</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo135wgclose_xfdw" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">计划开始日期</div>
									<div class="font-size-9">Date</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo135wgclose_jhksrq" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">计划完成日期</div>
									<div class="font-size-9">Date</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo135wgclose_jhjsrq" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">关闭人</div>
									<div class="font-size-9">Closer</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo135wgclose_gbr" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">关闭日期</div>
									<div class="font-size-9">Close Date</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="wo135wgclose_gbrq" name="" type="text" class="form-control" disabled="disabled"/>
								</div>
							</div>   											   
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">实际完成时间</div>
									<div class="font-size-9">Finished Time</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-8">
									<input class="form-control date-picker" id="wo135wgclose_sjJsrq" disabled="disabled" name="wo135wgclose_sjJsrq" data-date-format="yyyy-mm-dd" type="text">
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">实际开始日期</div>
									<div class="font-size-9">Date</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text" class="form-control date-picker" disabled="disabled" maxlength="10" data-date-format="yyyy-mm-dd" id="wo135wgclose_sjksrq" />
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">实际工时</div>
									<div class="font-size-9">Working Hours</div>
								</label>
								<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 padding-leftright-8">
									<input type="text" id="wo135wgclose_sjgs" disabled="disabled" name="wo135wgclose_sjgs" class="form-control" maxlength="10" >
								</div>
								<div class="col-xs-4 col-sm-4 padding-left-9 padding-right-0">
									<label class="pull-right" style="margin-top:6px;font-weight:normal;">
										<input id="wo135wgclose_hsgs" value="1" style="vertical-align:text-bottom" type="checkbox" disabled="disabled">&nbsp;核算工时&nbsp;&nbsp;
									</label>
								</div>
							</div>               									
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">工作者</div>
									<div class="font-size-9">Worker</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left">
									<div class="input-group col-xs-12">
										<input id="wo135wgclose_sjgzz" type="text" class="form-control "  disabled="disabled"/>						                    
				                	</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3  text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">检查者</div>
									<div class="font-size-9">Examiner</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left">
									<div class="input-group col-xs-12">
										<input id="wo135wgclose_sjjcz" type="text" class="form-control" disabled="disabled"/>
				                	</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">工作站点</div>
									<div class="font-size-9">Work Site</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text" id="wo135wgclose_sjzd" name="wo135wgclose_sjzd" class="form-control" maxlength="100" disabled="disabled">
								</div>
							</div>
							<div class="col-lg-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">故障信息</div>
									<div class="font-size-9 ">Fault Info</div>
								</span>
							 	<div class="col-lg-11 col-md-11  col-sm-10 col-xs-9 padding-left-8 padding-right-0">
										<textarea class="form-control" id="wo135wgclose_gzxx" name="wo135wgclose_gzxx" disabled="disabled" placeholder=''   maxlength="1000" style="height:55px"></textarea>
								</div>
							</div>
							<div class="col-lg-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">处理措施</div>
									<div class="font-size-9 ">Treatment Measures</div>
								</span>
							 	<div class="col-lg-11 col-md-11  col-sm-10 col-xs-9 padding-left-8 padding-right-0">
										<textarea class="form-control" id="wo135wgclose_clcs" name="wo135wgclose_clcs" placeholder=''  disabled="disabled" maxlength="1000" style="height:55px"></textarea>
								</div>
							</div>
							<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group"  id="wo135gbxqdiv">
									<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">关闭详情</div>
										<div class="font-size-9">Close Detail</div>
									</span>
									<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea id="wo135wgclose_gbxq" name="wo135feedbacktab_clcs" class="form-control" disabled="disabled" maxlength="100" style="height:55px"></textarea>
									</div>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<!-- Start:引用页面 -->
						<div id="replacementRecord_wo135wgclose_div" style="display:none">
							<%@ include file="/WEB-INF/views/produce/maintenanceissuedplan/replacementRecord.jsp"%><!-- 拆换件记录 -->
						</div>
						<div class="clearfix"></div>
						<!-- 指定结束 -->
						<div class="panel panel-primary" id="wo135zdjsdiv" style="display: none;">
							<div class="panel-heading bg-panel-heading" >
								<div class="font-size-12 ">指定结束</div>
								<div class="font-size-9">End</div>
							</div>
							<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 ">关闭人</div>
										<div class="font-size-9">Close man</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input id="wo135zdclose_gbr" name="" type="text" class="form-control" disabled="disabled"/>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 ">关闭日期</div>
										<div class="font-size-9">Close Date</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input id="wo135zdclose_gbrq" name="" type="text" class="form-control" disabled="disabled"/>
									</div>
								</div>   
								<div class="clearfix"></div>
								            									
								<div class="col-lg-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">指定结束原因</div>
										<div class="font-size-9 ">Reason</div>
									</span>
								 	<div class="col-lg-11 col-md-11  col-sm-10 col-xs-9 padding-left-8 padding-right-0">
										<textarea class="form-control" id="wo135zdclose_gbyy" name="wo135zdclose_gbyy" disabled="disabled" placeholder=''   maxlength="1000" style="height:55px"></textarea>
									</div>
								</div>
							</div>
						</div>
					
					<!-- 参考文件开始 -->
					<div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading" >
							<div class="font-size-12 ">参考文件</div>
							<div class="font-size-9">Reference</div>
						</div>
						<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						<%@ include file="/WEB-INF/views/common/project/reference_list_edit.jsp"%>
					    </div>
					</div>
					<!-- 参考文件结束 -->
					
					<%@ include file="/WEB-INF/views/common/project/equipment_list_edit.jsp"%><!-- 器材清单列表 -->
					<%@ include file="/WEB-INF/views/common/project/tools_list_edit.jsp"%><!-- 工具设备列表 -->
					
					<!-- 工作内容 -->
					<div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading" >
							<div class="font-size-12 ">工作内容</div>
							<div class="font-size-9">Work Content</div>
						</div>
						<div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
							
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
									<div class="font-size-12 margin-topnew-2">工作内容附件</div>
									<div class="font-size-9">Attachment</div>
								</label>
								<div id="work_content_attachments_single_edit" class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0">
									<input id="gznrfjid" name="gznrfjid" type="hidden" />
									<%@ include file="/WEB-INF/views/common/attachments/attachments_single_edit.jsp"%><!-- 加载附件信息 -->
								</div>
							</div>
							
							<div class='clearfix'></div>
							
							<%@ include file="/WEB-INF/views/common/project/work_content_list_edit.jsp"%><!-- 工作内容 -->
							
							<div class='clearfix'></div>
				
						</div>
					</div>
					<!-- 工作内容 -->
					
					<!-- 加载附件信息 -->
					<div id="attachments_list_edit" style="display:none">
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%>
					</div>
					<!-- 加载附件信息 -->
					
				
				
                <div class="clearfix"></div>
              
             
	   			
               </div> 
			</div>    		
		</div>
	 </div>
	 <%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	 <script type="text/javascript" src="${ctx}/static/js/thjw/common/monitor/monitor_unit.js"></script> <!-- 监控项单位设置 -->
	 <script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/135/workorder_view.js"></script>
	 <script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/135/workorder_main_tab.js"></script>
	 <script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 附件对话框 -->
  	 <script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
	 <%@ include file="/WEB-INF/views/open_win/work_hour_win.jsp"%><!-------工种工时对话框 -------->
    <%@ include file="/WEB-INF/views/open_win/inventory_distribution_details_view.jsp" %>  <!-- 航材工具库存弹窗 -->
  	<%@ include file="/WEB-INF/views/open_win/equivalent_substitution_view.jsp"%><!-------等效替代 -------->
  	<%@ include file="/WEB-INF/views/open_win/installationlist_replace.jsp"%><!-------拆换记录-------->
  	<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_certificate.jsp"%><!-------证书-------->
</body>

</html>
