<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
	
	//来源ID(评估单ID)
	var todo_lyid = "${param.todo_lyid}";
	var todo_fjjx = "${param.todo_fjjx}";
	var todo_ywid = "${param.todo_ywid}";
	var todo_dprtcode = "${param.todo_dprtcode}";
	var todo_jd = "${param.todo_jd}";
	
	$(document).ready(function(){
	//导航
	Navigation(menuCode);
});
</script>

<script type="text/javascript">
	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					searchRevision();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" name="zzjgid" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="zdbmid" name="zdbmid" value="${user.bmdm}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
		<div class="page-content ">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body padding-bottom-0">
				<div class='margin-top-0 searchContent row-height'>
				<a href="#" onclick="add()" data-toggle="modal"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission form-group"
					permissioncode="project2:instruction:main:01"
					><div class="font-size-12">新增</div>
					<div class="font-size-9">Add</div>
				</a>
				
				<button id="batchReview" type="button" onclick="batchApproveWin(false);"  style="margin-left:10px"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission form-group" 
					permissioncode="project2:instruction:main:05">
					<div class="font-size-12">批量审核</div>
					<div class="font-size-9">Review</div>
				</button>
				<button id="batchApprove" type="button" onclick="batchApproveWin(true);"  style="margin-left:10px"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission form-group" 
					permissioncode="project2:instruction:main:06">
					<div class="font-size-12">批量批准</div>
					<div class="font-size-9">Approve</div>
				</button>
				
				<div class="pull-right padding-left-0 padding-right-0 margin-top-0" >
					<span class="pull-left text-right padding-left-0 padding-right-0">
						<div class="font-size-12">版本</div>
						<div class="font-size-9">Rev.</div>
					</span>
					<div class="pull-left padding-left-8 padding-right-0 label-input-div" >
						<select id="bb_search" class="form-control" name="bb_search" style="width: 200px; margin-right:5px;" onchange="searchRevision()">
							<option value="current">当前</option>
							<option value="history">历史</option>
						</select>
					</div>
					
					<!-- 搜索框start -->
					<div class="pull-left padding-left-0 padding-right-0 form-group">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input type="text" placeholder='指令编号/主题/制单人' id="keyword_search" class="form-control ">
						</div>
	                    <div class=" pull-right padding-left-5 padding-right-0 ">   
							<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 row-height" onclick="searchRevision();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   		</button>
	                        <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search();">
								<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
								<div class="pull-left padding-top-5">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
								</div>
					   		</button>
	                    </div> 
					</div>
					<!-- 搜索框end -->
				</div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10  display-none border-cccccc margin-bottom-10 " id="divSearch">
				
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">状态</div>
							<div class="font-size-9">State</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zt_search" class="form-control "  name="zt_search">
								<option value="">显示全部</option>
								<c:forEach items="${technicalInstructionStatusEnum}" var="type">
										<option value="${type.id}">${type.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">接收状态</div>
							<div class="font-size-9">State</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="jszt_search" class="form-control "  name="jszt_search">
									<option value="">显示全部</option>
									<option value="0">未接收</option>
									<option value="1">已接收</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zzjg" class="form-control " name="zzjg">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					

					
					<div class="col-lg-3  text-right  pull-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>
				<div class="clearfix"></div>
				</div>
					<div class="clearfix"></div>

					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" c-height="55%" style="overflow-x: auto;">
						<table id="quality_check_list_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1700px !important">
							<thead>
								<tr>
									<th class="fixed-column " style='text-align:center;vertical-align:middle;width:60px;'>
										<a href="#" onclick="performSelectAll('instructionlist')" ><img src="${ctx}/static/assets/img/d1.png" title="全选" /></a>
										<a href="#" class="margin-left-5" onclick="performSelectClear('instructionlist')" ><img src="${ctx}/static/assets/img/d2.png" title="不全选" /></a>
									</th>
								
									<th class="fixed-column colwidth-7" ><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
										
									<th  class="fixed-column sorting colwidth-13" onclick="orderBy('jszlh')" id="jszlh_order">
									<div class="important">
									<div class="font-size-12 line-height-18">技术指令编号</div>
										<div class="font-size-9 line-height-18">Technical Order No.</div></div></th>
									<th class="sorting  colwidth-5" onclick="orderBy('bb')" id="bb_order">
									<div class="font-size-12 line-height-18">版本</div>
										<div class="font-size-9 line-height-18">Rev.</div></div></th>	
									<th  class="downward colwidth-13" onclick="vieworhidePgd()" name="pgd">
									<div class="font-size-12 line-height-18">关联评估单号</div>
										<div class="font-size-9 line-height-18">Assessment NO.</div>
									</th>																
									<th  class="sorting colwidth-30" onclick="orderBy('zhut')" id="zhut_order">
									<div class="important">
									<div class="font-size-12 line-height-18">主题</div>
										<div class="font-size-9 line-height-18">Subject</div></div></th>
										
									<th  class="sorting colwidth-7" onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div></th>
										
									<th class="sorting colwidth-10" onclick="orderBy('jsrid')" id="jsrid_order">
									<div class="font-size-12 line-height-18">接收人</div>
										<div class="font-size-9 line-height-18">Receiver</div></th>
										
									<th class="sorting colwidth-10" onclick="orderBy('jszt')" id="jszt_order">
									<div class="font-size-12 line-height-18">接收人是否接收</div>
										<div class="font-size-9 line-height-18">Receive</div></th>
									<th class="colwidth-9" ><div class="font-size-12 line-height-18" >附件</div>
										<div class="font-size-9 line-height-18" >Attachment</div></th>	
									<th class="sorting colwidth-13" onclick="orderBy('zdrid')" id="zdrid_order">
									<div class="important">
									<div class="font-size-12 line-height-18">制单人</div>
										<div class="font-size-9 line-height-18">Creator</div></div></th>
										
									<th class="sorting colwidth-13" onclick="orderBy('zdsj')" id="zdsj_order">
									<div class="font-size-12 line-height-18">制单时间</div>
										<div class="font-size-9 line-height-18">Create Time</div></th>
										
									<th class="sorting colwidth-13" onclick="orderBy('dprtcode')" id="dprtcode_order">
									<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div></th>
								</tr>
							</thead>
							<tbody id="instructionlist">
							</tbody>
							
						</table>
					</div>
						<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
					</div>
				
					<div class="clearfix"></div>
				</div>
			</div>
 	
	</div>
	<!-------添加模态框 start-------->
	<div class="modal fade modal-new" id="addModal" tabindex="-1" role="dialog" data-backdrop="static"
		aria-labelledby="myModalLabel" aria- hidden="true"  >
		<div class="modal-dialog" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-14 "><span id="titleName"></span></div>
							<div class="font-size-12"><span id="titleEname"></span></div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
				<div class="modal-body " >
						<div class="col-xs-12 margin-top-8">
							<div class="input-group-border">
							<form id="form">
								<input type="hidden" id="id" />	
								<input type="hidden" id="fBbid" />	
								<input type="hidden" id="zt" />
								<input type="hidden" id="dprt" />
								<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-8 margin-bottom-0 form-group" id="jszlhDiv" style="display: none;">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">技术指令编号</div>
											<div class="font-size-9">TO No.</div>
										</span>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-10 padding-right-0">
												<input type="text" class="form-control "maxlength="50" disabled="disabled"   id="jszlh" name="jszlh"  />
											</div>
									</div>					
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-8  form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18"><span style="color: red" id="fcr_remark">*</span>发出人</div>
									<div class="font-size-9 line-height-18">Sender</div></label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left input-group">
											<div class='input-group ' style="min-width:100%">
												<input type="text" value="" name="fcrname" class="form-control readonly-style"
												 id="fcrname" readonly="readonly"/>
												<span class="input-group-btn" id="fcrbutton">
													<button type="button" class="btn btn-default " id="fcrbtn"
													 data-toggle="modal"
													onclick="openWinUser('fcr')">
													<i class="icon-search cursor-pointer"></i>
													</button>
												</span>
											</div>
										</div>
										<div style="display: none;">
											<input type="text" value="" name="fcrid" id="fcrid">											
										</div>
								</div>					
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-8  form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18"><span style="color: red" id="fcrq_remark">*</span>发出日期</div>
										<div class="font-size-9 line-height-18">Send Date</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input class="form-control date-picker" id="fcrq" name="fcrq" data-date-format="yyyy-mm-dd" type="text" />
									</div>
								</div>					
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-8  form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18"><span style="color: red" id="jx_remark">*</span>机型</div>
										<div class="font-size-9 line-height-18">Model</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select class="form-control " id="jx" name="jx" >
										</select>
									</div>
								</div>					
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-8 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18"><span style="color: red" id="bb_remark">*</span>版本</div>
										<div class="font-size-9 line-height-18">Revision</div></label>
										<div id="bbViewHistoryDiv" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
											<div class="input-group input-group-new">
												<input type="text" class="form-control" maxlength="10" id="current_bb" name="current_bb"  />
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
											<input class="form-control" id="bb" name="bb" type="text" maxlength="10" />
										</div>
								</div>							
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-8 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18"><span style="color: red" id="jsr_remark">*</span>接收人</div>
										<div class="font-size-9 line-height-18">Receiver</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left input-group">
											<div class='input-group ' style="min-width:100%">
												<input type="text" value="" name="jsrname" class="form-control readonly-style"
												 id="jsrname" readonly="readonly"/>
												<span class="input-group-btn" id="jsrbutton">
													<button type="button" class="btn btn-default " id="rbtn"
													 data-toggle="modal"
													onclick="openWinUser('jsr')">
													<i class="icon-search cursor-pointer"></i>
													</button>
												</span>
											</div>
										</div>
										<div style="display: none;">
											<input type="text" value="" name="jsrid" id="jsrid">					
										</div>
								</div>										
								<div class="clearfix"></div>
									<%@ include file="/WEB-INF/views/open_win/evaluationList.jsp"%><!-- -评估单列表 -->
								 <div class="clearfix"></div>			
								 <div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0 margin-bottom-10 form-group ">
										
										<label class="col-lg-1 col-sm-2 col-xs-4 text-right  padding-right-0"><div class="font-size-12 line-height-18"><span style="color: red" id="zhut_remark">*</span>主题</div>
											<div class="font-size-9 line-height-18">Subject</div>
											</label>
										 <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-20 padding-left-8 padding-right-8">
											<input maxlength="100"  name="zhut" id="zhut" class="form-control "  type="text">
										</div>
									</div>
								</div>				
								 <div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0 margin-bottom-10 form-group ">
										
										<label class="col-lg-1 col-sm-2 col-xs-4 text-right  padding-right-0"><div class="font-size-12 line-height-18">来源说明</div>
											<div class="font-size-9 line-height-18">Source</div>
											</label>
										 <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-20 padding-left-8 padding-right-8">
											<input maxlength="330"  name="lysm" id="lysm" class="form-control "  type="text">
										</div>
									</div>
								</div>				
								<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-8 padding-left-0  form-group ">			
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right  padding-right-0"><div class="font-size-12 line-height-18"><span style="color: red" id="zxsx_remark">*</span>执行时限</div>
										<div class="font-size-9 line-height-18">Execute Limit</div></label>
									 <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-20 padding-left-8 padding-right-0">
										<input maxlength="100"  name="zxsx" id="zxsx" class="form-control "  type="text">
									</div>
								</div>	
								<div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8 form-group" >
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "  >
											<div class="font-size-12 line-height-18">颁发理由及依据</div>
											<div class="font-size-9 line-height-18">Reason For This</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8  padding-right-0">
												<textarea class="form-control" id="bflyyj" name='bflyyj'  maxlength="160"></textarea>
									</div>
								</div>
								<div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8 form-group" >
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8  padding-right-0">
										<textarea class="form-control" id="bz" name="bz"    maxlength="300"></textarea>
									</div>
								</div>
								<div class="clearfix"></div>
								</form>
						</div>
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
						<div class="modal-footer ">
							<div class="col-xs-12 padding-leftright-8" >
								<div class="input-group">
									<span class="input-group-addon modalfootertip" >
						                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
									</span>
									<span class="input-group-addon modalfooterbtn">
										<button type="button" id="save" onclick="saveData()"
											class="btn btn-primary padding-top-1 padding-bottom-1">
											<div class="font-size-12">保存</div>
											<div class="font-size-9">Save</div>
										</button>
										<button type="button" id="submit" onclick="submitData()"
											class="btn btn-primary padding-top-1 padding-bottom-1">
											<div class="font-size-12">提交</div>
											<div class="font-size-9">Submit</div>
										</button>
										<button type="button" id="audited" onclick="auditedData('3')"
											class="btn btn-primary padding-top-1 padding-bottom-1">
											<div class="font-size-12">审核通过</div>
											<div class="font-size-9">Audited</div>
										</button>
										<button type="button" id="reject" onclick="auditedData('5')"
											class="btn btn-primary padding-top-1 padding-bottom-1">
											<div class="font-size-12">审核驳回</div>
											<div class="font-size-9">Audit Reject</div>
										</button>
										<button type="button" id="approve" onclick="approvedData('4')"
											class="btn btn-primary padding-top-1 padding-bottom-1">
											<div class="font-size-12">批准通过</div>
											<div class="font-size-9">Approved</div>
										</button>
										<button type="button" id="rejected" onclick="approvedData('6')"
											class="btn btn-primary padding-top-1 padding-bottom-1 ">
											<div class="font-size-12">批准驳回</div>
											<div class="font-size-9">Rejected</div>
										</button>
										<button type="button" id="revision" onclick="revisionData()"
											class="btn btn-primary padding-top-1 padding-bottom-1 ">
											<div class="font-size-12">改版</div>
											<div class="font-size-9">Revision</div>
										</button>
										<button type="button" data-dismiss="modal"
											class="btn btn-primary padding-top-1 padding-bottom-1 ">
											<div class="font-size-12">关闭</div>
											<div class="font-size-9">Close</div>
										</button>
									</span>
								</div>
						</div>
					</div>
				</div>
			</div>
	</div>
	<!-------添加模态框end-------->
	
	<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
	<%@ include file="/WEB-INF/views/open_win/batchApprovel.jsp"%><!-------批量审批对话框 -------->
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/selectEvaluation.jsp"%><!-- -选择评估单列表 --> 
    <%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 -------->
	<script type="text/javascript" src="${ctx }/static/js/thjw/project2/technicalInstruction/technicalInstruction_main.js"></script>
	<%@ include file="/WEB-INF/views/project2/technicalInstruction/history_view_win.jsp"%><!-- 历史版本 -->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>