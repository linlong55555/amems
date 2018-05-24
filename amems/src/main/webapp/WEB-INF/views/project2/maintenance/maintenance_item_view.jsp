<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>维修项目查看</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
	label {
    	font-weight: normal !important;
	}
	table thead tr th {
    	font-weight: normal;
	}
</style>
</head>
<body>
	<input type="hidden" id="wxxmid" value="${param.id}"/>
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading " id="NavigationBar"></div>
			<div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div class="col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0" >
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" id="historyDiv">  
							<label class="pull-left text-right padding-left-10 padding-right-0">
								<div class="font-size-12 margin-topnew-2">版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="pull-left padding-leftright-8">
								<label class="margin-top-8">
									<span id="bbSpan"></span>
									<span id="lastBbSpan">
										←
										<span id="lastBbData"></span>
										<a href="javascript:void(0);" title="历史列表 List">
											<i class="attachment-view2 glyphicon glyphicon glyphicon-list cursor-pointer" style="font-size:15px"></i>
										</a>
									</span>
								</label>
							</div>
							<div class="pull-left padding-left-10 padding-right-0">
								<a id="compareBtn" href="#" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height">
									<div class="font-size-12">对比</div>
									<div class="font-size-9">Contrast</div>
								</a>
							</div>
						</div>
					</div>
					
					<div class=" col-lg-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group margin-top-5" >
						<div class="pull-right padding-leftright-8" style="margin-top: 9px;">
							<span id="yxbs"></span>
						</div>
						<div class="pull-right padding-leftright-8 label-input-div" id="wxxmCheckbox">
							<label class="margin-right-5 label-input"><input id="rii" name="radio" type="checkbox" disabled="disabled">&nbsp;RII</label>
							<label class="label-input"><input id="ali" name="radio" type="checkbox" disabled="disabled">&nbsp;ALI</label>
						</div>
						<div class="pull-right padding-leftright-8" style="margin-top: 9px;">
							<span id="wxxmlx"></span>
						</div>
					</div>
				</div>
				
				<div class="col-xs-12">
              	<div class="input-group-border">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="zjh" readonly/>
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">维修项目大类</div>
							<div class="font-size-9">Class</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<select id="wxxmdl" class="form-control" disabled="disabled">
							</select>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">任务号</div>
							<div class="font-size-9">Task No.</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="rwh" maxlength="50" readonly/>
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">CMP/CAMP号</div>
							<div class="font-size-9">CMP No.</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="cmph" readonly/>
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group wxxmDiv" >
						<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">区域</div>
							<div class="font-size-9">Zone</div>
						</label>
						<div id="eqydiv" class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<input class="form-control" maxlength="300" readonly="" id="qy">
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">参考号</div>
							<div class="font-size-9">Ref No.</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="ckh" maxlength="300" style="height: 75px;" readonly></textarea>
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">参考文件</div>
							<div class="font-size-9">Ref Document</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="ckwj" maxlength="300" style="height: 75px;" readonly></textarea>
						</div>
					</div>
						
					<div class="col-lg-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">备注</div>
							<div class="font-size-9">Note</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="bz" maxlength="300" style="height: 75px;" readonly></textarea>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">任务描述</div>
							<div class="font-size-9">Task Description</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-leftright-8">
							<textarea class="form-control" id="rwms" maxlength="300" style="height: 75px;" readonly></textarea>
						</div>
					</div>
					
				</div>
				<div class="clearfix"></div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 wxxmDiv">
					
					<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">接近</div>
							<div class="font-size-9">Access</div>
						</label>
						<div id="ejjdiv" class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<input class="form-control" maxlength="300" readonly="" id="jj">
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">飞机站位</div>
							<div class="font-size-9">Aircraft Stations</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<input class="form-control" maxlength="300" readonly="" id="fjzw">
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">工作类别</div>
							<div class="font-size-9">Work Type</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<select id="gzlx" class="form-control" disabled="disabled">
							</select>
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">项目类别</div>
							<div class="font-size-9">Project Type</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<select id="xmlx" class="form-control" disabled="disabled">
							</select>
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">计划工时</div>
							<div class="font-size-9">MHRs</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
								
							<div class="input-group input-group-new">
								<input type="text" maxlength="8" class="form-control " 
									id="jhgsRs" readonly>
		                    	<span class="input-group-addon">人&nbsp;x</span>
		                    	<input maxlength="6" type="text" class="form-control" 
									id="jhgsXss" readonly>
		                        <span class="input-group-addon">时=
		                        	<span id="bzgs"></span>时
		                        </span>
		                	</div><!-- /input-group -->
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">工卡</div>
							<div class="font-size-9">Work Card</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
		                	<input class="form-control" id="gkbh" readonly="">
						</div>
					</div>
					
					<div class="clearfix"></div>
					
				</div>
				<div class="clearfix"></div>
				</div>
				
				<div class="panel panel-primary" id="component_monitor_panel">
					<div class="panel-heading bg-panel-heading" >
						<div class="font-size-12">适用性</div>
						<div class="font-size-9">Applicability</div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0">
						<label class="col-lg-1 col-md-1 col-sm-1 col-xs-1 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</label>
						<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 padding-left-8 padding-right-8">
							<div class="input-group" style="min-width:26.4%;">
								<div readonly="" id="fjzch" class="form-control" style="border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;"></div>
							</div>
						</div>
						<div class="clearfix"></div>
						
					</div>
				</div>
				
				<div class="clearfix"></div>
				
				<div class="panel panel-primary" id="plane_monitor_panel">
					<div class="panel-heading bg-panel-heading" >
						<div class="font-size-12">监控项设置</div>
						<div class="font-size-9">Monitor Setting</div>
					</div>
					<div class="panel-body padding-bottom-0">
						<label class="col-lg-1 col-md-1 col-sm-1 col-xs-1 text-right padding-left-0 padding-right-8">
							<div class="font-size-12 margin-topnew-2">监控项</div>
							<div class="font-size-9">Monitor Item</div>
						</label>
			        	<div name="monitorItemDiv" class="padding-left-0 padding-right-0 col-lg-11 col-md-11 col-sm-11 col-xs-11" style="overflow-x: auto;">
				        	<table class="table table-bordered table-striped table-hover text-center table-set table-thin" style="min-width: 500px;width: 70%">
				        		<thead>
							   		<tr>
									<th class="colwidth-7" id="maintenance_version_bj">
										<div class="font-size-12 line-height-12">部件</div>
										<div class="font-size-9 line-height-12">Component</div>
									</th>
									<th class="colwidth-9" id="maintenance_version_jkxm">
										<div class="font-size-12 line-height-12">监控项目</div>
										<div class="font-size-9 line-height-12">Monitor Item</div>
									</th>
									<th class="colwidth-9" id="maintenance_version_sj">
											<div class="font-size-12 line-height-12">首检</div>
											<div class="font-size-9 line-height-12">INTI</div>	
									</th>
									<th class="colwidth-7" id="maintenance_version_zq">
										<div class="font-size-12 line-height-12">周期</div>
										<div class="font-size-9 line-height-12">Cycle</div>
									</th>
									<th class="colwidth-15" id="maintenance_version_rc">
										<div class="font-size-12 line-height-12">容差(-/+)</div>
										<div class="font-size-9 line-height-12">Tolerance(-/+)</div>
									</th>
									 </tr>
								</thead>
				        		<tbody id="maintenance_version_list">
				           		</tbody>
			           		</table>
			           	</div>
			           	
			           	<div class="col-sm-12 padding-left-0 padding-right-0">
			           		<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="jsgs_div">
			           			<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-8">
			           				<div class="font-size-12 margin-topnew-2">计算公式</div>
			           				<div class="font-size-9">Formula</div>
			           			</label>
				           		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-0 padding-right-8">
				           			<select class="form-control" id="jsgs" disabled="disabled"></select>
				           		</div>
			           		</div>
			           		<div class="col-md-1 col-sm-2 col-xs-4 padding-left-0 padding-right-0 form-group" id="hdwz_div">
		           				<label class="margin-right-5 label-input">
		           					<input id="isHdwz" type="checkbox" disabled="disabled">&nbsp;后到为准
		           				</label>
			           		</div>
		                       <div class="clearfix"></div>
							 <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">间隔描述</div>
										<div class="font-size-9">Remark</div>
									</label>
									<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 padding-leftright-8">
									<textarea class="form-control" disabled="disabled" id="monitor_frame_component_jgms" maxlength="300" style="height: 75px;"></textarea>		
									</div>
							</div>			           		
			           		<div class="col-lg-4 col-md-6 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" id="wz_div">
			           			<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
			           				<div class="font-size-12 margin-topnew-2">发动机分类</div>
			           				<div class="font-size-9">Engine</div>
			           			</label>
			           			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 padding-leftright-8">
			           				<select class="form-control" id="wz" disabled="disabled">
			           					<option value="21">1#发</option>
			           					<option value="22">2#发</option>
			           					<option value="23">3#发</option>
			           					<option value="24">4#发</option>
			           				</select>
			           			</div>
			           		</div>
			           	</div>
			        </div>
				</div>
				
				<div class="clearfix"></div>
				
				<div class="panel panel-primary">
					<div class="panel-heading bg-panel-heading" >
						<div class="font-size-12" id="associateCnHead">维修项目关联</div>
						<div class="font-size-9">Maintenance Item</div>
					</div>
					<div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
						<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" id="ssdjbDiv">
							<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-8">
								<div class="font-size-12 margin-topnew-2">所属定检包</div>
								<div class="font-size-9">Package</div>
							</label>
							<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-0 padding-right-8">
								<input class="form-control" readonly="" id="ssdjb">
							</div>
						</div>
						<div class="clearfix"></div>
						<div id="qdeo_div">
							<div class=" col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group" >
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-8">
									<div class="font-size-12 margin-topnew-2">取代EO</div>
									<div class="font-size-9">Replace EO</div>
								</label>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-0 padding-right-8">
				                	<input class="form-control" id="eobh" readonly="">
								</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group" id="eo_title_div" style="display: none;">
								<label class="col-lg-1 col-sm-1 col-xs-1 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">EO标题</div>
									<div class="font-size-9">Title</div>
								</label>
								<div class="col-lg-11 col-sm-11 col-xs-11 padding-leftright-8">
									<label class="label-input margin-top-10" id="eo_title"></label>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<label class="col-lg-1 col-md-1 col-sm-1 col-xs-1 text-right padding-left-0 padding-right-8">
							<div class="font-size-12 margin-topnew-2" id="associateCnTitle">维修项目关联</div>
							<div class="font-size-9" id="associateEngTitle">Maintenance Item</div>
						</label>
						<div class="col-lg-11 col-xs-11 padding-left-0 padding-right-0">
							<div style="overflow-x:auto;">
								<!-- start:table -->
								<table class="table table-bordered table-striped table-hover text-center table-set table-thin" style="min-width: 500px;">
									<thead>
								   		<tr>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">版本</div>
												<div class="font-size-9 notwrap">Rev.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">任务号</div>
												<div class="font-size-9 notwrap">Task No.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">参考号</div>
												<div class="font-size-9 notwrap">Ref No.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">适用性</div>
												<div class="font-size-9 notwrap">Applicability</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">参考文件</div>
												<div class="font-size-9 notwrap">Ref Document</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">工时</div>
												<div class="font-size-9 notwrap">MHRs</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="maintenance_project_list">
									</tbody>
								</table>
								<!-- end:table -->
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				
				<div class="clearfix"></div>
				<div id="attachments_list_edit" style="display:none">
						
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				
				 </div>
			 	<div class="clearfix"></div>
			 	</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>
	<script src="${ctx}/static/js/thjw/project2/maintenance/maintenance_item_view.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/maintenance_class.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/maintenance/maintenance_project_history.js"></script>
</body>
</html>

