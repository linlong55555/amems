	<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/taskhistory/taskhistory_feedback.js"></script>		
			<div class="panel-body  padding-bottom-0 padding-top-0 padding-right-0" id="taskhistoryFeedbackMain">
			<div class='margin-top-10'>
			    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">实际执行单位</div>
						<div class="font-size-9">Department</div>
					</span>
				    <div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control " id="feedback_jhZxdw" disabled="disabled" />
					</div>
				</div>
			    
				 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">实际完成日期</div>
						<div class="font-size-9">Actual Date</div>
					</span>
					<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control " id="feedback_sjJsrq" disabled="disabled" />
					</div>
				</div>
				
				 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">工作者</div>
						<div class="font-size-9">Workers</div>
					</span>
					<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control " id="feedback_sjGzz" disabled="disabled" />
					</div>
				</div>
				<div class='clearfix'></div>
				 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">检查者</div>
						<div class="font-size-9">Inspector</div>
					</span>
					<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control " id="feedback_sjJcz" disabled="disabled" />
					</div>
				</div>
				
				 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">实际工时</div>
						<div class="font-size-9">Actual Time</div>
					</span>
					<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control " id="feedback_sjGs" disabled="disabled" />
					</div>
				</div>
				
				 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">工作站点</div>
						<div class="font-size-9">Work Site</div>
					</span>
					<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control " id="feedback_sjZd" disabled="disabled" />
					</div>
				</div>
				<div class='clearfix'></div>
				<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">故障信息</div>
						<div class="font-size-9">Fault</div>
					</span>
					<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<textarea id="feedback_gzxx" name="feedback_gzxx" class="form-control" disabled="disabled" maxlength="100" style="height:55px"></textarea>
					</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">处理措施</div>
						<div class="font-size-9">Measures</div>
					</span>
					<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<textarea id="feedback_clcs" name="feedback_clcs" class="form-control" disabled="disabled" maxlength="100" style="height:55px"></textarea>
					</div>
				</div>
				<div class='clearfix'></div>
				<div id="attachments_list_edit" >
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				</div>
				<div class='clearfix'></div>
			</div>
          </div>