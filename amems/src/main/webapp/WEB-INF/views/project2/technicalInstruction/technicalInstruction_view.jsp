<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script type="text/javascript">
	//来源ID(评估单ID)
	var todo_lyid = "${param.todo_lyid}";
	var todo_ywid = "${param.todo_ywid}";
	
	</script>
</head>
<body class="page-header-fixed">		
	<input type="hidden" name="instructionId" id="instructionId" value='${id}' >			
		<!-------导航Start--->
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			
					<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">技术指令编号</div>
											<div class="font-size-9">TO No.</div>
										</span>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-10 padding-right-0">
												<input type="text" class="form-control "maxlength="50" disabled="disabled"   id="jszlh" name="jszlh"  />
											</div>
									</div>					
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group" >
									<span  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18">发出人</div>
									<div class="font-size-9 line-height-18">Sender</div></span>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left input-group">
											<div class='input-group ' style="min-width:100%">
												<input type="text" value="" name="fcrname" class="form-control " 
												 id="fcrname" disabled="disabled"/>												
											</div>
										</div>									
								</div>					
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group" >
									<span  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">发出日期</div>
										<div class="font-size-9 line-height-18">Send Date</div></span>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input class="form-control date-picker" id="fcrq" name="fcrq" disabled="disabled" data-date-format="yyyy-mm-dd" type="text" />
									</div>
								</div>					
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group" >
									<span  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">Model</div></span>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">							
										<input type="text" id="jx" name="jx" disabled="disabled" class="form-control">
									</div>
								</div>					
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">版本</div>
										<div class="font-size-9 line-height-18">Revision</div></span>
										<div id="bbViewHistoryDiv" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
											<div class="input-group input-group-new">
												<input type="text" class="form-control" maxlength="10" id="current_bb" name="current_bb" disabled="disabled"/>
												<span name="lastBbSpan" class="input-group-addon">
						                     		← <a id="previous_bb" href="javascript:void(0);" onclick="viewPrevious()"></a>
						                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                     		  <input type="hidden" id="previous_id" />
						                     	</span>
						                     	<span name="lastBbSpan" class='input-group-btn' title="历史列表  List">
													<i class="attachment-view2 glyphicon glyphicon glyphicon-list color-blue cursor-pointer" style="font-size:15px;float: left;text-decoration:none;position:relative; margin-left: 10px;"></i>
												</span>
										    </div>
										    
										</div>
									    <div id="bbNoViewHistoryDiv" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
											<input class="form-control" id="bb" name="bb" type="text" maxlength="10" disabled="disabled"/>
										</div>
								</div>							
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">接收人</div>
										<div class="font-size-9 line-height-18">Receiver</div></span>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left input-group">
											<div class='input-group ' style="min-width:100%">
												<input type="text" value="" name="jsrname" class="form-control " id="jsrname" disabled="disabled"/>											
											</div>
										</div>								
								</div>
								<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group">
									<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">状态</div>
										<div class="font-size-9">Status</div>
									</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input type="text"  class="form-control"  disabled="disabled" value="" id="zt_input" name="zt_input"  />
										</div>
								</div>										
								<div class="clearfix"></div>
									<%@ include file="/WEB-INF/views/open_win/evaluationList.jsp"%><!-- -评估单列表 -->
								 <div class="clearfix"></div>			
								 <div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0 margin-bottom-10 form-group ">
										
										<span class="col-lg-1 col-sm-2 col-xs-4 text-right  padding-right-0"><div class="font-size-12 line-height-18">主题</div>
											<div class="font-size-9 line-height-18">Subject</div>
											</span>
										 <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-20 padding-left-8 padding-right-0">
											<input maxlength="100"  name="zhut" id="zhut" class="form-control " disabled="disabled"  type="text">
										</div>
									</div>
								</div>				
								 <div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0 margin-bottom-10 form-group ">
										
										<span class="col-lg-1 col-sm-2 col-xs-4 text-right  padding-right-0"><div class="font-size-12 line-height-18">来源说明</div>
											<div class="font-size-9 line-height-18">Source</div>
											</span>
										 <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-20 padding-left-8 padding-right-0">
											<input maxlength="330"  name="lysm" id="lysm" class="form-control "  disabled="disabled" type="text">
										</div>
									</div>
								</div>				
								<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0 padding-left-0  form-group ">			
									<span class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">执行时限</div>
										<div class="font-size-9 line-height-18">Execute Limit</div></span>
									 <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-20 padding-left-8 padding-right-0">
										<input maxlength="100"  name="zxsx" id="zxsx" class="form-control " disabled="disabled" type="text">
									</div>
								</div>	
								<div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "  >
											<div class="font-size-12 line-height-18">颁发理由及依据</div>
											<div class="font-size-9 line-height-18">Reason For This</div>
									</span>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8  padding-right-0">
												<textarea class="form-control" id="bflyyj" disabled="disabled" maxlength="160"></textarea>
									</div>
								</div>
								<div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
									<span class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</span>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 form-group padding-right-0">
										<textarea class="form-control" id="bz" name="bz"  disabled="disabled"  maxlength="300"></textarea>
									</div>
								</div>
								<div class="clearfix"></div>
							<div class="panel panel-primary">
								<div class="panel-heading bg-panel-heading" >
									<div class="font-size-12 ">工作内容</div>
									<div class="font-size-9">Work Content</div>
								</div>
								<div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
									<%@ include file="/WEB-INF/views/common/project/work_content_list_edit.jsp"%><!-- 工作内容 -->								
									<div class='clearfix'></div>					
								</div>
						</div>				
						<div class="clearfix"></div>
						<div id="attachments_list_edit" style="display:none">							
								<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
						</div>
							<div class="clearfix"></div>																										
								<%@ include file="/WEB-INF/views/open_win/introduce_process_info.jsp" %> <!--流程信息 -->	
						</div>
						<div class="clearfix"></div>
						</div>
						
				
				<div class="clearfix"></div>    
			</div>
	<%@ include file="/WEB-INF/views/project2/technicalInstruction/history_view_win.jsp"%><!-- 历史版本 -->
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/technicalInstruction/technicalInstruction_view.js"></script>
	
</body>
</html>