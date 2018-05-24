<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="panel-body  padding-bottom-0  padding-top-10 overflow-auto padding-leftright-8" >		
				<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group">
					<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">实际执行单位</div>
						<div class="font-size-9">Department</div>
					</span>
				    
				    <div class="col-md-9 col-sm-8 col-xs-9 padding-left-8  padding-right-0 pull-left input-group">
							<input type="text" class="form-control " id="wo145feedbacktab_sjZxdw" disabled="disabled" />						
					</div>
				</div>
				<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">实际完成日期</div>
						<div class="font-size-9">Actual Date</div>
					</span>
					<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control " id="wo145feedbacktab_sjJsrq" disabled="disabled" />
					</div>
				</div>
				<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">工作者</div>
						<div class="font-size-9">Worker</div>
					</span>
					<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control " id="wo145feedbacktab_sjgzz" disabled="disabled" />
					</div>
				</div>
				<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">检查者</div>
						<div class="font-size-9">inspectors</div>
					</span>
					<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control " id="wo145feedbacktab_sjjcz" disabled="disabled" />
					</div>
				</div>			
				<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">实际工时</div>
						<div class="font-size-9">Actual Time</div>
					</span>
					<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control " id="wo145feedbacktab_sjgs" disabled="disabled" />
					</div>
				</div>
				<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">工作站点station</div>
						<div class="font-size-9">Work Site</div>
					</span>
					<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control " id="wo145feedbacktab_sjzd" disabled="disabled" />
					</div>
				</div>								
				<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">故障信息</div>
						<div class="font-size-9">Fault</div>
					</span>
					<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<textarea id="wo145feedbacktab_gzxx" name="wo145feedbacktab_gzxx" class="form-control" disabled="disabled" maxlength="100" style="height:55px"></textarea>
					</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">处理措施</div>
						<div class="font-size-9">Measures</div>
					</span>
					<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<textarea id="wo145feedbacktab_clcs" name="wo145feedbacktab_clcs" class="form-control" disabled="disabled" maxlength="100" style="height:55px"></textarea>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<!-- 加载附件信息 -->
					<div id="wo145feedbacktab_attachments_list_edit" style="display:none">
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%>
					</div>
					<!-- 加载附件信息 -->
					<div class='clearfix'></div>
				</div>
		</div>