<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
<input maxlength="20" type="hidden" id="userId" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
<div class="page-content">
			<!-- from start -->
	<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			    <input type="hidden" id="zzjgid" name="zzjgid" value="${user.jgdm}" />

				<div class="col-xs-12  padding-left-0 padding-right-0" >

					<a href="#" data-toggle="modal" class="row-height btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" 
						permissioncode="project:workorder:main:01,project:workorder:main1:01" onclick="addNon()">
						<div class="font-size-12">新增非例行工单</div>
						<div class="font-size-9">Add Non-Routine W/O</div>
					</a> 
			
			
					<a href="#" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission margin-left-10 checkPermission"  
					permissioncode='project:workorder:main:08,project:workorder:main1:08' onclick="addEo()">
						<div class="font-size-12">新增EO工单</div>
						<div class="font-size-9">Add EO W/O</div>
					</a> 
		
				<button type="button" onclick="batchApproveWin(false);"  style="margin-left:10px"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission"
					permissioncode='project:workorder:main:05,project:workorder:main1:05' >
					<div class="font-size-12">批量审核</div>
					<div class="font-size-9">Review</div>
				</button>
				<button type="button" onclick="batchApproveWin(true);"  style="margin-left:10px"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission"
					permissioncode='project:workorder:main:05,project:workorder:main1:05'>
					<div class="font-size-12">批量批准</div>
					<div class="font-size-9">Approve</div>
				</button>
				<!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='编号/评估单号/飞机注册号/部件号/序列号/ATA章节号/用户名' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
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
				<div class="col-xs-12 col-sm-3  padding-left-0 padding-right-0 pull-right" style="margin-right:10px;" >
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">工单类型</div>
							<div class="font-size-9">W/O Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='gddlx_search' onchange="changeType()">
								<option value="" selected="true" >查看全部</option>
								<option value="1" >定检工单</option>
								<option value="2" >非例行工单</option>
								<option value="3" >EO工单</option>
						    </select>
						</div>
					</div>
				<!-- 搜索框end -->
				
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10  margin-top-10 display-none border-cccccc search-height" id="divSearch">
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " id='zilei'>
						<span class="pull-left col-xs-4  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">工单子类</div>
							<div class="font-size-9">W/O Sub Type</div>
						</span>
						<div class="col-xs-8  padding-left-8 padding-right-0">
							<select class='form-control' id='gdlx_search'>
								<option value="" selected="true" >查看全部</option>
								<option value="1"  >时控件工单</option>
								<option value="2"  >附加工单</option>
								<option value="3"  >排故工单</option>
						    </select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 " id='zilei'>
						<span class="pull-left col-xs-4  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">执行分类</div>
							<div class="font-size-9">Category</div>
						</span>
						<div class="col-xs-8  padding-left-8 padding-right-0">
							<select class='form-control' id='zxfl_search'>
							   <option value="" selected="selected" >查看全部</option>
							   <option value="FJ" >机身 </option>
         		               <option value="ZJJ" >飞机部件</option>
         		               <option value="FZJJ" >非装机件</option>
						    </select>
						</div>
					</div>

					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " id='zilei'>
						<span class="pull-left col-xs-4  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">专业</div>
							<div class="font-size-9">Skill</div>
						</span>
						<div class="col-xs-8  padding-left-8 padding-right-0">
							<select class='form-control' id='zhuanye_search'>
								<option value="all" selected="true" >查看全部</option>
								<option value="" >无</option>
						    </select>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " id='zilei'>
						<span class="pull-left col-xs-4  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">基地</div>
							<div class="font-size-9">Base</div>
						</span>
						<div class="col-xs-8  padding-left-8 padding-right-0">
							<select class='form-control' id='jd_search'>
								<option value="" selected="true" >查看全部</option>
								 <c:forEach items="${baseList}" var="baseList">
									  <option value="${baseList.id}"  <c:if test="${woResult.jd ==baseList.id }">selected="true"</c:if>>${erayFns:escapeStr(baseList.jdms)}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " id='gdzt'>
						<span class="pull-left col-xs-4  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">工单状态</div>
							<div class="font-size-9">State</div>
						</span>
						<div class="col-xs-8  padding-left-8 padding-right-0">
						    <select class="form-control" id="gdzt_search">
							  <option value="">查看全部</option>
							  <option value="0">保存</option>
							  <option value="1">提交</option>
							  <option value="2">已审核</option>
							  <option value="3">已批准</option>
							  <option value="4">中止（关闭）</option>
							  <option value="5">审核驳回</option>
							  <option value="6">批准驳回</option>
							  <option value="9">指定结束</option>
							  <option value="10">完成</option>
						    </select>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">制单日期</div>
							<div class="font-size-9">Create Time</div></span>
						<div class="col-xs-8 padding-left-8 padding-right-0">
						  <input type="text" class="form-control " name="date-range-picker" id="zdrq_search" readonly />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 " id='gdzt'>
						<span class="pull-left col-xs-4  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 padding-left-8 padding-right-0">
							 <select  id="dprtcode_search"  class="form-control" onchange="changeOrg()">
				                 <c:forEach items="${accessDepartment}" var="type">
								   <option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
						     	</c:forEach>
						    </select>
						</div>
					</div>

					<div class="col-lg-3 col-sm-6 col-xs-12 pull-right text-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1 "
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
			   </div>	
				
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="overflow-x:scroll ; ">
						<table id="workorder_list_table" class="table-set table table-thin table-bordered table-striped table-hover"
								style=" min-width: 2500px !important;">
							<thead>
								<tr>
								    <th class="fixed-column colwidth-5"><!-- <input type='checkbox' name='CheckAll' id='CheckAll'  /> -->
								        <a href="javascript:checkAll();" class="pull-left margin-left-10 margin-bottom-10" id='CancelAll' ><img src="${ctx}/static/assets/img/d1.png" title="全选" /></a>
								    	<a href="javascript:notCheckAll();" class="pull-left margin-left-10 margin-bottom-10" id='CancelAll'><img src="${ctx}/static/assets/img/d2.png" title="不全选" /></a> 
								    </th>	
									
									<th class="fixed-column colwidth-10"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th class="sorting colwidth-10" onclick="orderBy('gdlx')" id="gdlx_order">
									     <div class="font-size-12 line-height-18">工单类型</div>
										 <div class="font-size-9 line-height-18">W/O Type</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('gdbh')" id="gdbh_order">
									  <div class="important">
								     	 <div class="font-size-12 line-height-18">工单编号</div>
										 <div class="font-size-9 line-height-18">W/O No.</div>
									  </div>	 
									</th>
									<th class="sorting colwidth-25" onclick="orderBy('zhut')" id="zhut_order">
									    <div class="important">
									     <div class="font-size-12 line-height-18">主题</div>
										<div class="font-size-9 line-height-18">Subject</div>
										</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('xfgdyy')" id="xfgdyy_order">
									    <div class="important">
									     <div class="font-size-12 line-height-18">下发工单原因</div>
										<div class="font-size-9 line-height-18">Reason for W/O</div>
										</div>
									</th>
									<th class="downward colwidth-10" onclick="vieworhideWorkContentAll()" name="glpgdh">
									   <div class="important">
									     <div class="font-size-12 line-height-18">关联评估单号</div>
										 <div class="font-size-9 line-height-18">Assessment NO.</div>
								       </div>	 
									</th>	
									<th class="sorting colwidth-7" onclick="orderBy('zxfl')" id="zxfl_order">
									     <div class="font-size-12 line-height-18">执行分类</div>
										<div class="font-size-9 line-height-18">Category</div>
									</th>	
									<th class="sorting colwidth-7" onclick="orderBy('fjzch')" id="fjzch_order">
									   <div class="important">
									     <div class="font-size-12 line-height-18">飞机注册号</div>
										 <div class="font-size-9 line-height-18">A/C REG</div>
									   </div>	 
									</th>	
									<th class="sorting colwidth-10" onclick="orderBy('bjh')" id="bjh_order">
									   <div class="important">
									     <div class="font-size-12 line-height-18">部件件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
										</div>
									</th>	
									<th class="sorting colwidth-10" onclick="orderBy('bjxlh')" id="bjxlh_order">
									   <div class="important">
									     <div class="font-size-12 line-height-18">部件序列号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									   </div>	
									</th>	
									<th class="sorting colwidth-5" onclick="orderBy('zhuanye')" id="zhuanye_order">
									     <div class="font-size-12 line-height-18">专业</div>
										 <div class="font-size-9 line-height-18">Skill</div>
									</th>	
									<th class="sorting colwidth-13" onclick="orderBy('zjh')" id="zjh_order">
									  <div class="important">
									     <div class="font-size-12 line-height-18">ATA章节号</div>
										 <div class="font-size-9 line-height-18">ATA</div>
									  </div>	 
									</th>	
									<th class="sorting colwidth-7" onclick="orderBy('jd')" id="jd_order">
									     <div class="font-size-12 line-height-18">基地</div>
										 <div class="font-size-9 line-height-18">Base</div>
									</th>	
									<th class="sorting colwidth-7" onclick="orderBy('zt')" id="zt_order">
									     <div class="font-size-12 line-height-18">工单状态</div>
										<div class="font-size-9 line-height-18">State</div>
									</th>	
									<th class="sorting colwidth-10" onclick="orderBy('gzzid')" id="gzzid_order">
									     <div class="font-size-12 line-height-18">工作组</div>
										<div class="font-size-9 line-height-18">WorkGroup</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('zdrid')" id="zdrid_order">
									   <div class="important">
									     <div class="font-size-12 line-height-18">制单人</div>
										<div class="font-size-9 line-height-18">Creator</div>
									  </div>	
									</th>	
									<th class="sorting colwidth-13" onclick="orderBy('zdsj')" id="zdsj_order">
									     <div class="font-size-12 line-height-18">制单时间</div>
										<div class="font-size-9 line-height-18">Create Time</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('dprtcode')" id="dprtcode_order">
									     <div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
							<tbody id="list">
							</tbody>
						</table>
					</div>
					
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
					</div>
			</div>
	</div>
		<!-- start指定结束提示框 -->
	<div class="modal fade" id="alertModalZdjs" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
							<div class="font-size-16 line-height-18">指定结束</div>
							<div class="font-size-9 ">Specified End</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12">
			            	
			            	<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
								<label class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">工单编号</div>
									<div class="font-size-9">W/O No.</div>
									</label>
								<div class="col-lg-10 col-sm-6 padding-left-8 padding-right-0">
									<input type="hidden" name="username" id="username" value="${user.username }">
									
									<input type="hidden" name="id" id="id">
									<input type="hidden" name="gddlx" id="gddlx">
									<input type="hidden" name="gdlx" id="gdlx">
									<input type="text" class="form-control " id="gdbh" name="gdbh" disabled="disabled"/>
								</div>
							</div> 
			            	<div class="clearfix"></div>
			            	<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>结束原因</div>
								<div class="font-size-9 line-height-18">Reason</div>
							</label>
							<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="zdjsyy" name="zdjsyy" placeholder='长度最大为150'   maxlength="150"></textarea>
							</div>
							    
					     		<div class="clearfix"></div>
					     		<div class="text-right margin-top-10 padding-buttom-0 ">
                               <button onclick="zdjsOver()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-0" data-dismiss="modal"><div
										class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div></button>&nbsp;&nbsp;&nbsp;&nbsp;
                     	      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-0" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
                    			 </div><br/>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
<!-- 	指定结束编辑END -->
		<!-- start指定结束查看提示框 -->
	<div class="modal fade" id="alertModalZdjsReson" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:470px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
							<div class="font-size-16 line-height-18">指定结束</div>
							<div class="font-size-9 ">Specified End</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12">
			            	
			            	<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
								<label class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">工单编号</div>
									<div class="font-size-9">W/O No.</div>
									</label>
								<div class="col-lg-10 col-sm-6 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="gdbh2" disabled="disabled"/>
								</div>
							</div> 
							<div class="clearfix"></div>
							<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 margin-bottom-10 ">
								<label class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">指定结束人</div>
									<div class="font-size-9">Operator</div>
									</label>
								<div class="col-lg-10 col-sm-6 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="zdjsr" disabled="disabled"/>
								</div>
							</div> 
			            	<div class="clearfix"></div>
			            	<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 margin-bottom-10 ">
								<label class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">结束时间</div>
									<div class="font-size-9">End Time</div>
									</label>
								<div class="col-lg-10 col-sm-6 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="zdjsrq" disabled="disabled"/>
								</div>
							</div> 
							<div class="clearfix"></div>
			            	<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18">结束原因</div>
								<div class="font-size-9 line-height-18">Reason</div>
							</label>
							<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="zdjsyy2"  disabled="disabled"></textarea>
							</div>
							    
					     		<div class="clearfix"></div>
					     		<div class="text-right margin-top-10 padding-buttom-10 ">
                     	        <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
                    			 </div><br/>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	</div>
    <%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<%@ include file="/WEB-INF/views/open_win/batchApprovel.jsp"%><!-------批量审批对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/project/workorder/workorderlist.js"></script>
</body>
</html>