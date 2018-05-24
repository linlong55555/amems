<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script type="text/javascript">
	var pageParam = '${param.pageParam}';
	
	//来源ID(评估单ID)
	var todo_lyid = "${param.todo_lyid}";
	var todo_ywid = "${param.todo_ywid}";
	
	</script>
</head>
	<body class="page-header-fixed">
		
		<input type="hidden" name="bulletinId" id="bulletinId" value='${id}' >
		<input type="hidden" name="userId" id="userId" value='${user.id}' >
		<input type="hidden" name="zt" id="zt" value='${zt}' >		
		<!-------导航Start--->
		<!-- BEGIN HEADER -->
<script type="text/javascript">
$(document).ready(function(){
//导航
Navigation(menuCode,"查看维护提示","View Technical Bulletin");
});
</script>
	<div class="clearfix"></div>
	
	<!-- BEGIN CONTAINER -->

		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
						<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">维护提示编号</div>
								<div class="font-size-9">TB No.</div>
							</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control "maxlength="15" value=""  disabled="disabled" id="jxtgbh" name="jxtgbh"  />
								</div>
						</div>	
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-8  form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">版本</div>
								<div class="font-size-9">Rev.</div>
							</span>
							<div id="bbViewHistoryDiv" class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-10 padding-right-0">
								<div class="input-group input-group-new">
									<input type="text" class="form-control" maxlength="9" onkeyup="clearNoNumTwo(this)" id="current_bb" name="current_bb" disabled="disabled" />
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
						    <div id="bbNoViewHistoryDiv" class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-10 padding-right-0">
								<input class="form-control" id="bb" name="bb" type="text" maxlength="9" onkeyup='clearNoNumTwo(this)' disabled="disabled"/>
							</div>
						</div>
						<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">机型</div>
								<div class="font-size-9">A/C Type</div>
							</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" id="jx" value=""class="form-control" disabled="disabled">
								</div>
						</div>
						<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-0   form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">颁发日期</div>
								<div class="font-size-9">Issue Date</div>
							</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text"  class="form-control" value="" maxlength="10" disabled="disabled"  id="bfrq" name="bfrq"  />
								</div>
						</div>
						<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-0   form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">生效日期</div>
								<div class="font-size-9">Effect Date</div>
							</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control" disabled="disabled" maxlength="10" value="" id="sxrq" name="sxrq"  />
								</div>
						</div>
						<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">维护提示期限</div>
								<div class="font-size-9">Bulletin Limit</div>
							</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text"  class="form-control" maxlength="10"  disabled="disabled" value="" id="tgqx" name="tgqx"  />
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
						<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">主题</div>
								<div class="font-size-9 ">Subject</div>
							</span>
						 	<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-top-0 padding-right-0">
									<textarea class="form-control" id="zhut" name="zhut" placeholder='' style="height:55px" disabled="disabled"  maxlength="300" style="height:55px"></textarea>
							</div>
						</div>
						<div class="clearfix"></div>
						<%@ include file="/WEB-INF/views/open_win/evaluationList.jsp"%><!-- -评估单列表 -->
						<div class="clearfix"></div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">涉及</div>
								<div class="font-size-9">Related To</div>
							</span>
								<div class="col-lg-11 col-md-11  col-sm-10 col-xs-9  padding-left-8 padding-right-0">
									<c:forEach items="${compnentTypeEnum}" var="type">											
												<label style=" font-weight:normal" class="margin-right-5 label-input"><input type="radio" name="sj" disabled="disabled" value='${type.id}' checked="checked">&nbsp;${type.name}</label>																						
											</c:forEach>
								</div>
								
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0   form-group">
							<span class="col-lg-2 col-md-2 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">有效性</div>
								<div class="font-size-9">Effectivity</div>
							</span>
								<div class="col-lg-10 col-md-10 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control " disabled="disabled" maxlength="5" value=""  id="yxx" name="yxx"  />
								</div>
						</div>
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0   form-group">
							<span class="col-lg-2 col-md-2 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">来源说明</div>
								<div class="font-size-9">Desc</div>
							</span>
								<div class="col-lg-10 col-md-10 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control "maxlength="5" disabled="disabled" value=""  id="lysm" name="lysm"  />
								</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0" >
								<div class="font-size-12 ">参考资料</div>
								<div class="font-size-9 ">References</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-top-0 padding-right-0">
									<textarea class="form-control" id="ckzl" name="ckzl" placeholder='' style="height:55px" disabled="disabled"  maxlength="300"></textarea>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0" >
								<div class="font-size-12 ">背景</div>
								<div class="font-size-9 ">Background</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-top-0 padding-right-0">
									<textarea class="form-control" id="bj" name="bj" placeholder='' style="height:55px" disabled="disabled"  maxlength="300"></textarea>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0" >
								<div class="font-size-12 ">描述</div>
								<div class="font-size-9 ">Description</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-top-0 padding-right-0">
									<textarea class="form-control" id="ms" name="ms" placeholder='' style="height:55px" disabled="disabled"  maxlength="300"></textarea>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0" >
								<div class="font-size-12 ">措施</div>
								<div class="font-size-9 ">Action</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-top-0 padding-right-0">
									<textarea class="form-control" id="cs" name="cs" placeholder='' style="height:55px"  disabled="disabled" maxlength="300"></textarea>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">维护提示类别</div>
								<div class="font-size-9">T/B Category</div>
							</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
									<label style=" font-weight:normal" class="margin-right-5 label-input"><input type="radio" disabled="disabled" name="tglb"  value='1' >&nbsp;信息类</label>										
									<label style=" font-weight:normal" class="label-input"><input type="radio" disabled="disabled" name="tglb"  value='2'  >&nbsp;标准类</label>
								</div>
								<input type="hidden" id="tglb" value="">
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-8" >
								<div class="font-size-12 ">改版原因</div>
								<div class="font-size-9 ">Reason</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-top-8 padding-right-0">
									<textarea class="form-control" id="gbyy" name="gbyy" style="height:55px" placeholder='' disabled="disabled"  maxlength="300"></textarea>
							</div>
						</div>
						<div class="clearfix"></div>						
						<div  class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-top-0 padding-left-0 padding-right-0  form-group">
							<span
								class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red" id="ffmark">*</span>分发</div>
								<div class="font-size-9">Distribution</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0 pull-left input-group">								
									<div class="input-group" style="min-width:100%;">
										<div  id="ff" class='form-control base-color div-readonly-style' style='border-right:1px solid #d5d5d5;border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'>
										</div> 					                    
				                	</div>							
							</div>									
						</div>
						<div class="clearfix"></div>						
						<div class="panel panel-primary" id="fjxxpanel">
										<div class="panel-heading bg-panel-heading">
											   <div class="font-size-12 "><input type="checkbox" disabled="disabled"  name="is_fjxx" checked="checked" />&nbsp;&nbsp;附加信息</div>
											  <div class="font-size-9">Additional Information</div>
										</div>
										<div class="panel-body" id="fjxxdiv">
											<span>涉及部件禁装和部件送修时，请填写以下内容;如无关,则不需保留此项内容</span>
											<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 padding-top-8  form-group">
													<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-0" >
														<div class="input-group input-group-new">
													<span class="input-group-addon" ><input type="checkbox" checked="checked" disabled="disabled" name="is_wcfjpc" />&nbsp;&nbsp;已完成现有机翼的普装检查&nbsp;&nbsp;EO/MAO	</span>					
													<input type="text" class="form-control" maxlength="300" placeholder="" disabled="disabled"  id="eo_mao" name="eo_mao"  />
													</div>
													</div>
											</div>
											
											<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  margin-bottom-0 form-group">						
													<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
															<div class="input-group input-group-new">
														
															<span class="input-group-addon padding-left-35"><input type="radio" disabled="disabled" checked="checked" name="is_fj_syxbj" value="0" />&nbsp;&nbsp;无受影响部件</span>
															<label></label>
														</div>
													</div>
											</div>
											<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  margin-bottom-0 form-group">						
													<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
															<div class="input-group input-group-new">														
															<span class="input-group-addon padding-left-35"><input type="radio" name="is_fj_syxbj" checked="checked" disabled="disabled" value="1" />&nbsp;&nbsp;有受影响部件</span>
															<input type="text" class="form-control" maxlength="300" placeholder="" disabled="disabled" id="syxbj_fj"  name="syxbj_fj"   />
														</div>
													</div>
											</div>
											<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 padding-top-8  form-group">
													<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-0" >
														<div class="input-group input-group-new">
													<span class="input-group-addon" ><input type="checkbox" checked="checked" disabled="disabled" name="is_bjzjhs" />&nbsp;&nbsp;根据部件装机清单,核实我公司&nbsp;&nbsp;EO/MAO	</span>					
													<label></label>
													</div>
													</div>
											</div>										
											<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  margin-bottom-0 form-group">						
													<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
															<div class="input-group input-group-new">														
															<span class="input-group-addon padding-left-35"><input type="radio" name="is_bj_syxbj" disabled="disabled" value="0" />&nbsp;&nbsp;无受影响部件</span>														
															<label></label>
														</div>
													</div>
											</div>
											<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  margin-bottom-0 form-group">						
													<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
															<div class="input-group input-group-new">
														
															<span class="input-group-addon padding-left-35"><input type="radio" value="1" checked="checked" disabled="disabled" name="is_bj_syxbj" />&nbsp;&nbsp;有受影响部件</span>
														
														
															<input type="text" class="form-control" maxlength="300" disabled="disabled" placeholder="" id="syxbj_bj" value="" name="syxbj_bj"   />
														</div>
													</div>
											</div>
											<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 padding-top-8  form-group">
													<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-0" >
														<div class="input-group input-group-new">
													<span class="input-group-addon" ><input type="checkbox" disabled="disabled" name="is_wpc" />&nbsp;&nbsp;未普查,请PPC核实&nbsp;&nbsp;EO/MAO	</span>					
													<label></label>
													</div>
													</div>
											</div>
										</div>
									</div>						
									<div id="attachments_list_edit" style="display:none">							
										<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
									</div>
						<div class="clearfix"></div>						
  																											
						<div class="clearfix"></div>
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 " >
						<%@ include file="/WEB-INF/views/open_win/introduce_process_info.jsp" %> <!--流程信息 -->	
						</div>	
						<div class="clearfix"></div>																								
				</div>
			</div>
			</div>			
	<%@ include file="/WEB-INF/views/project2/bulletin/history_view_win.jsp"%><!-- 历史版本 -->
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/bulletin/bulletin_view.js"></script>
</body>
</html>