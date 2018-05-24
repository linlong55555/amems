<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>生产指令查看</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style>
label{
font-weight:normal;
}
</style>
</head>
<body>
	<input type="hidden" id="id" value="${param.id}"/>
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading " id="NavigationBar"></div>
			<div class="panel-body" id="production_detail_modal">
              	<div class="input-group-border">
				<form id="production_detail_form">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					
					<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">生产指令编号</div>
							<div class="font-size-9 line-height-18">Order No.</div>
						</label>
						<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8 pull-left">
		                	<input id="zlbh" class="form-control" placeholder="不填写时系统自动生成"/>
						</div>
					</div>
					<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
						<input type="hidden" class="form-control " id="id" />
						<input type="hidden" class="form-control " id="zt" />
						<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">版本</div>
							<div class="font-size-9">Rev.</div>
						</label>
						<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
							<div class="input-group margin-top-8">
								R<span id="bb" class="base-color"></span> 
								<span name="lastBbSpan" style="display: inline;">  ←  </span>
								<span name="lastBbSpan" id="lastBbData"></span>
								<span name="lastBbSpan" class="input-group-btn" style="display: table-cell;">
									<i class="attachment-view3 glyphicon glyphicon glyphicon-list color-blue cursor-pointer" style="font-size:15px"></i>
								</span>
						    </div>
						</div>
					</div>
					<div class="clearfix"></div>
				
					<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div>
						</label>
						<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
							<input type="hidden" class="form-control" id="zjh" name="zjh" />
							<div class="input-group col-xs-12">
								<input id="zjhms" class="form-control" type="text" ondblclick="production_detail_modal.openChapterWin()">
			                    <span id="zjh_btn" class="input-group-addon btn btn-default" onclick="production_detail_modal.openChapterWin()" >
			                    	<i class="icon-search cursor-pointer"></i>
			                    </span>
		                	</div>
						</div>
					</div>
					
					<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>飞机机型</div>
							<div class="font-size-9 line-height-18">A/C Type</div>
						</label>
						<div class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8 pull-left">
		                	<select id="fjjx" name="fjjx" class="form-control" onchange="production_detail_modal.changeFjjx()"></select>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-1 col-sm-1 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>指令描述</div>
							<div class="font-size-9">Order Des</div>
						</label>
						<div class="col-lg-11 col-sm-11 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="zlms" name="zlms" style="height: 75px;"></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-1 col-sm-1 col-xs-1 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">工卡</div>
							<div class="font-size-9 line-height-18">Work Card</div>
						</label>
						<div class="col-lg-11 col-sm-11 col-xs-11 padding-leftright-8 pull-left">
							<input type="hidden" class="form-control" id="gkid" name="gkid" />
		                	<input type="hidden" class="form-control" id="gkbh" name="gkbh" />
							<div class="input-group col-xs-12">
								<input id="gkms" class="form-control readonly-style" readonly="" type="text" ondblclick="production_detail_modal.openWorkCardWin()">
			                    <span id="gk_btn" class="input-group-addon btn btn-default" onclick="production_detail_modal.openWorkCardWin()" >
			                    	<i class="icon-search cursor-pointer"></i>
			                    </span>
		                	</div>
						</div>
					</div>
					<div class="clearfix"></div>
					
				</div>
				<div class="clearfix"></div>
				</form>
				</div>
				<div class="clearfix"></div>
				
				<div class="panel panel-primary padding-left-0 padding-right-0" id="component_monitor_panel">
					<div class="panel-heading bg-panel-heading" >
						<div class="font-size-12">适用性及监控项设置</div>
						<div class="font-size-9">Applicability And Monitor Setting</div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</label>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<select class='form-control' multiple='multiple' id='syx'></select>
							<input class='form-control' type="text" id="syx_input" style="display: none;"/>
						</div>
						<div class="clearfix"></div>
						
						<div id="production_monitor_item" class="padding-top-5"></div>
						<div class="clearfix"></div>
						
						<div id="production_monitor_body"></div>
						<div class="clearfix"></div>
						
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">计算公式</div>
								<div class="font-size-9">Formula</div>
							</label>
							<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10 padding-leftright-8">
								<select class="form-control" id="jsgs"></select>
							</div>
						</div>
						<div class="col-lg-1 col-md-2 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="label-input">
								<input id="isHdwz" type="checkbox">&nbsp;后到为准
							</label>
						</div>
						<div class="clearfix"></div>
						
						<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" id="wz_div">
							<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">发动机分类</div>
								<div class="font-size-9">Engine</div>
							</label>
							<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10 padding-leftright-8">
								<select class="form-control" id="wz">
									<option value="21" selected="selected">1#发</option>
									<option value="22">2#发</option>
									<option value="23">3#发</option>
									<option value="24">4#发</option>
								</select>
							</div>
						</div>
						<div class="clearfix"></div>
						
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-1 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">间隔描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 padding-leftright-8">
							<textarea class="form-control" id="jgms" maxlength="300" style="height: 75px;"></textarea>		
							</div>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				
				<div id="production_attachments_list">
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				</div>
			 	<div class="clearfix"></div>
			 	
			 	<div id="production_process_record_panel" class="panel panel-primary padding-left-0 padding-right-0">
			 		<div class="panel-heading bg-panel-heading">
						<div class="font-size-12">流程记录</div>
						<div class="font-size-9">Process Record</div>
					</div>
			 		<div class="panel-body padding-leftright-8">
			 			<%@ include file="/WEB-INF/views/project2/production/production_process_record.jsp" %>
			 		</div>
			 	</div>
			 	<div class="clearfix"></div>
			 	
			 	<div id="production_history_version_panel" class="panel panel-primary padding-left-0 padding-right-0">
			 		<div class="panel-heading bg-panel-heading">
						<div class="font-size-12">历史版本</div>
						<div class="font-size-9">History Version</div>
					</div>
			 		<div class="panel-body padding-leftright-8">
			 			<%@ include file="/WEB-INF/views/project2/production/production_history_version.jsp" %>
			 		</div>
			 	</div>
			 	<div class="clearfix"></div>
			 	
			 	<div id="production_process_info_panel" class="panel panel-primary padding-left-0 padding-right-0">
			 		<div class="panel-heading bg-panel-heading">
						<div class="font-size-12">流程信息</div>
						<div class="font-size-9">Process Info</div>
					</div>
			 		<div class="panel-body padding-leftright-8">
			 			<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0">
			 				<div id="production_process_info_shyj">
								 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
										<div class="font-size-12 margin-topnew-2">审核意见</div>
										<div class="font-size-9 ">Opinion</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-8">
										<textarea id="shyj" maxlength="160" style="height:55px" class="form-control padding-left-3 padding-right-3"></textarea>
									</div>
								</div>
							</div>
							<div id="production_process_info_spyj">
								 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
										<div class="font-size-12 margin-topnew-2">审批意见</div>
										<div class="font-size-9 ">Opinion</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-8">
										<textarea id="spyj" maxlength="160" style="height:55px" class="form-control padding-left-3 padding-right-3"></textarea>
									</div>
								</div>
							</div>
			 			</div>
			 		</div>
			 	</div>
			 	<div class="clearfix"></div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/production/production_detail.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/production/production_view.js"></script>
	<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%><!-- 章节号 -->
	<%@ include file="/WEB-INF/views/open_win/work_card_win.jsp"%><!-------工卡对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/production/production_monitor.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/production/production_history.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/monitor/monitor_unit.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>

