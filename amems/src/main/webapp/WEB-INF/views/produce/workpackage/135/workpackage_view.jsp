<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script type="text/javascript">
	var pageParam = '${param.pageParam}';
	</script>
</head>
	<body class="page-header-fixed">
		
		<input type="hidden" name="workpackageId" id="workpackageId" value='${id}' >
		<input type="hidden" name="userId" id="userId" value='${user.id}' >
		<input type="hidden" name="zt" id="zt" value='${zt}' >		
		<!-------导航Start--->
		<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	
	<!-- BEGIN CONTAINER -->

		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
					<div class="panel-body">
						<div class="panel panel-primary" >
							<div class="panel-heading bg-panel-heading">
								   <div class="font-size-12 ">基本信息</div>
								  <div class="font-size-9">Base Info</div>
							</div>
						<div class="panel-body" style="padding-bottom:0px;padding-left:0px;padding-right:0px">	
							<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12 padding-left-0 padding-right-8 margin-bottom-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">工包编号</div>
									<div class="font-size-9">Package No.</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control "maxlength="15" value=""  disabled="disabled" id="gbbh" name="gbbh"  />
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">描述</div>
									<div class="font-size-9">Desc</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="ms" name="ms" maxlength="15" disabled="disabled" />
								</div>
							</div>	
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">飞机注册号</div>
									<div class="font-size-9">A/C REG</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input class='form-control ' id="fjzch" disabled="disabled">
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">MSN</div>
									<div class="font-size-9">MSN</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input class='form-control ' id="msn" disabled="disabled">
								</div>
							</div>
							<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-8  form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">机型</div>
									<div class="font-size-9">A/C Type</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" id="jx" value=""class="form-control" disabled="disabled">
								</div>
							</div>
							<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-8   form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">制单日期</div>
									<div class="font-size-9">Create Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text"  class="form-control" value="" maxlength="10" disabled="disabled"  id="zdrq" name="zdrq"  />
								</div>
							</div>
							<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-8   form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">下发单位</div>
									<div class="font-size-9">Issuing Unit</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text"  class="form-control" value="" maxlength="10" disabled="disabled"  id="xfdw" name="xfdw"  />
								</div>
							</div>
							<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-8   form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">计划开始日期</div>
									<div class="font-size-9">Plan Start Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text"  class="form-control" value="" maxlength="10" disabled="disabled"  id="jhksrq" name="jhksrq"  />
								</div>
							</div>
							<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-8   form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">计划完成日期</div>
									<div class="font-size-9">Plan Finish Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text"  class="form-control" value="" maxlength="10" disabled="disabled"  id="jhjsrq" name="jhjsrq"  />
								</div>
							</div>
							<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-8   form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">预计执行单位</div>
									<div class="font-size-9">Estimated Execution Unit</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text"  class="form-control" value="" maxlength="10" disabled="disabled"  id="yjzxdw" name="yjzxdw"  />
								</div>
							</div>
							<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-8   form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">状态</div>
									<div class="font-size-9">Status</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text"  class="form-control" value="" maxlength="10" disabled="disabled"  id="gbzt" name="gbzt"  />
								</div>
							</div>
							<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">工作要求</div>
									<div class="font-size-9 ">Work Request</div>
								</span>
							 	<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-top-0 padding-right-0">
										<textarea class="form-control" id="gzyq" name="gzyq" placeholder='' style="height:55px" disabled="disabled"  maxlength="300" style="height:55px"></textarea>
								</div>
							</div>
						</div>
						</div>
						<div class="panel panel-primary" id="wp145wgfkdiv" style="display: none;">
					<div class="panel-heading bg-panel-heading" >
						<div class="font-size-12 " id="cName">完工反馈</div>
						<div class="font-size-9" id="eName">Feedback</div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">实际完成时间</div>
								<div class="font-size-9">Finished Time</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-8">
								<input class="form-control date-picker" id="workpackage_feedback_sjwcrq" disabled="disabled" name="workpackage_feedback_sjwcrq" data-date-format="yyyy-mm-dd" type="text">
							</div>
						</div>
						<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">实际开始日期</div>
								<div class="font-size-9">Date</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control date-picker" maxlength="10" disabled="disabled" data-date-format="yyyy-mm-dd" id="workpackage_feedback_sjksrq" name="workpackage_feedback_sjksrq"  />
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">工作站点</div>
								<div class="font-size-9">Work Site</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" id="workpackage_feedback_gzzd" disabled="disabled" name="workpackage_feedback_gzzd" class="form-control" maxlength="100" >
							</div>
						</div> 
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">实际执行单位</div>
								<div class="font-size-9">Execution Unit</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left ">
								<div class='input-group ' style="min-width:100%">
									<input type="text" value="" name="workpackage_feedback_bm" class="form-control "
									 id="workpackage_feedback_bm"  disabled="disabled"/>									
								</div>
							</div>				
						</div> 
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">工作者</div>
								<div class="font-size-9">Worker</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left ">
								<div class='input-group ' style="min-width:100%">
									<input type="text" value="" name="workpackage_feedback_gzzname" disabled="disabled" class="form-control " maxlength="100"
									 id="workpackage_feedback_gzzname" />								
								</div>
							</div>						
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">检查者</div>
								<div class="font-size-9">Examiner</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left">
								<div class='input-group ' style="min-width:100%">
									<input type="text" value="" name="workpackage_feedback_jczname" disabled="disabled" class="form-control " maxlength="100"
									 id="workpackage_feedback_jczname" />								
								</div>
							</div>					
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="gbrdiv">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">关闭人</div>
								<div class="font-size-9">Closer</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left">
								<div class='input-group ' style="min-width:100%">
									<input type="text" value="" name="workpackage_close_gbr" disabled="disabled" class="form-control " maxlength="100"
									 id="workpackage_close_gbr" />								
								</div>
							</div>					
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="gbrqdiv">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">关闭日期</div>
								<div class="font-size-9">Closing Date</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left">
								<div class='input-group ' style="min-width:100%">
									<input type="text" value="" name="workpackage_close_gbrq" disabled="disabled" class="form-control " maxlength="100"
									 id="workpackage_close_gbrq" />								
								</div>
							</div>					
						</div>
						<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group" id="gbyydiv">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">关闭原因</div>
								<div class="font-size-9 ">Reason</div>
							</span>
						 	<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-top-0 padding-right-0">
									<textarea class="form-control" id="workpackage_close_gbyy" name="workpackage_close_gbyy" placeholder='' style="height:55px" disabled="disabled"  maxlength="300" style="height:55px"></textarea>
							</div>
						</div>
					</div>
				</div>	
				<div class="panel panel-primary" id="wp145zdjsdiv" style="display: none;">
					<div class="panel-heading bg-panel-heading" >
						<div class="font-size-12 " id="cName">指定结束</div>
						<div class="font-size-9" id="eName">End</div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="gbrdiv">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">关闭人</div>
								<div class="font-size-9">Closer</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left">
								<div class='input-group ' style="min-width:100%">
									<input type="text" value="" name="workpackage_end_gbr" disabled="disabled" class="form-control " maxlength="100"
									 id="workpackage_end_gbr" />								
								</div>
							</div>					
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="gbrqdiv">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">关闭日期</div>
								<div class="font-size-9">Closing Date</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left">
								<div class='input-group ' style="min-width:100%">
									<input type="text" value="" name="workpackage_end_gbrq" disabled="disabled" class="form-control " maxlength="100"
									 id="workpackage_end_gbrq" />								
								</div>
							</div>					
						</div>
						<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group" id="gbyydiv">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">指定结束原因</div>
								<div class="font-size-9 ">Reason</div>
							</span>
						 	<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-top-0 padding-right-0">
									<textarea class="form-control" id="workpackage_end_gbyy" name="workpackage_end_gbyy" placeholder='' style="height:55px" disabled="disabled"  maxlength="300" style="height:55px"></textarea>
							</div>
						</div>
					</div>
				</div>
						<div class="clearfix"></div>											
						<div id="workpackage_view_attachments_list_edit" style="display:none">							
							<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
						</div>
						<div class="clearfix"></div>
						<div  id='bottom_hidden_content' class='displayContent' style='overflow-x: auto;'>
						  	<ul id="myTab" class="nav nav-tabs">
		                      	<li class="active"><a href="#Dropdown" data-toggle="tab" aria-expanded="false">工包明细</a></li>
<!-- 		                      	<li class=""><a href="#profile" data-toggle="tab" aria-expanded="false">航材工具</a></li> -->
		                    </ul>
		                    <div id="myTabContent" class="tab-content">
		                      	
		                      	<div class="tab-pane fade active in" id="Dropdown">
									<%@ include file="/WEB-INF/views/produce/workpackage/135/workorder_detail.jsp"%>
		                      	</div>                   	
<!-- 		                      	<div class="tab-pane fade" id="profile"> -->
<%-- 		                      		<%@ include file="/WEB-INF/views/open_win/hc_list_view.jsp"%> --%>
<!-- 		                      	</div> -->
		                  </div>
					</div>																								
				</div>
			</div>
		</div>			
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/auth_height_util.js"></script>
	<script src="${ctx}/static/js/thjw/produce/workpackage/135/workpackage_view.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/135/workorder_main.js"></script> <!-- 135工单管理主列表 -->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 列表附件控件对话框 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/monitor/monitor_unit.js"></script> <!-- 监控项转换js -->
	<%@ include file="/WEB-INF/views/produce/workorder/135/workorder_feedback.jsp"%><!-- -工单完工反馈 -->
	<%@ include file="/WEB-INF/views/produce/workorder/135/workorder_wg_close.jsp"%><!-- -关闭工单 -->
		<%@ include file="/WEB-INF/views/produce/workorder/135/workorder_zd_close.jsp"%><!-- -完工关闭工单 -->
</body>
</html>