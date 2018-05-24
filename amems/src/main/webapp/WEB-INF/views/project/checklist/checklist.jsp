<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>定检任务单</title>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<input maxlength="20" type="hidden" id="userId" value="" />
<input type="hidden" id="zzjgid" value="${user.jgdm}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
<div class="page-content">
			<!-- from start -->
	<div class="panel panel-primary">
		 <div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
				<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0 row-height" style="width: 250px;">
						<input type="text" placeholder='定检任务编号/维修方案编号/定检编号/飞机注册号/件号/序列号/中英文' id="keyword_search" class="form-control ">
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
				<!------------搜索框end------->
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10 margin-top-10 display-none border-cccccc search-height" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 " id='zilei'>
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">State</div>
						</span>
				     	<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='zt_search'>
								<option value="" >查看全部</option>
								<option value="3">已批准</option>
								<option value="10">完成</option>
								<option value="9">指定结束</option>
						    </select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
						       <select  id="dprtcode_search" name="dprtcode_search" class="form-control" >
					                 <c:forEach items="${accessDepartment}" var="type">
									   <option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
							     	</c:forEach>
							   </select>
						</div>
					</div>

					<div class="col-lg-2 pull-right text-right padding-left-0 padding-right-0" style="margin-bottom: 10px;">

						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1 "
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 

			</div>	
				<div class="clearfix"></div>

					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="margin-top: 10px;overflow-x:scroll ;">
						<table id="scheduled_table" class="table-set table table-thin table-bordered table-striped table-hover"
								style=" min-width: 1700px !important;"
							>
							<thead>
								<tr>
									<th class="fixed-column colwidth-5"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th class="sorting fixed-column colwidth-13" onclick="orderBy('djrwbh')" id="djrwbh_order">
										<div class="important">
										     <div class="font-size-12 line-height-18">定检任务编号</div>
											 <div class="font-size-9 line-height-18">Scheduled No.</div>
										</div>
									</th>
									<th class="sorting colwidth-13" onclick="orderBy('djbh')" id="djbh_order">
									    <div class="important">
									     <div class="font-size-12 line-height-18">定检编号</div>
										 <div class="font-size-9 line-height-18">Check No.</div>
										 </div>
									</th>
									<th class="sorting colwidth-12" onclick="orderBy('zwms')" id="zwms_order">
									   <div class="important">
									     <div class="font-size-12 line-height-18">定检名称</div>
										 <div class="font-size-9 line-height-18">Fixed Name</div>
									   </div>	 
									</th>	
									<th class="sorting colwidth-7" onclick="orderBy('bb')" id="bb_order">
									     <div class="font-size-12 line-height-18">版本号</div>
										 <div class="font-size-9 line-height-18">Version No.</div>
									</th>	
									<th class="sorting colwidth-13" onclick="orderBy('wxfabh')" id="wxfabh_order">
								     	<div class="important">
								     	 <div class="font-size-12 line-height-18">维修方案编号</div>
										 <div class="font-size-9 line-height-18">Maintenance No.</div>
										 </div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('fjzch')" id="fjzch_order">
									    <div class="important">
									     <div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div>
										</div>
									</th>	
									<th class="sorting colwidth-10" onclick="orderBy('bjh')" id="bjh_order">
									    <div class="important">
									     <div class="font-size-12 line-height-18">部件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
										</div>
									</th>	
									<th class="sorting colwidth-10" onclick="orderBy('xlh')" id="xlh_order">
									   <div class="important">
									     <div class="font-size-12 line-height-18">部件序列号</div>
										<div class="font-size-9 line-height-18">S/N</div>
										</div>
									</th>	
									<th class="colwidth-12">
									     <div class="font-size-12 line-height-18">标准工时</div>
										<div class="font-size-9 line-height-18">MHRS</div>
									</th>
									<th class="sorting colwidth-12" onclick="orderBy('jkxmbh_F')" id="jkxmbh_F_order">
									     <div class="font-size-12 line-height-18">监控项目</div>
										<div class="font-size-9 line-height-18">Monitoring project</div>
									</th>
									<th class="sorting colwidth-7" onclick="orderBy('zt')" id="zt_order">
									     <div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">State</div>
									</th>
									<th class="sorting colwidth-12" onclick="orderBy('zdrid')" id="zdrid_order">
									     <div class="font-size-12 line-height-18">制单人</div>
										<div class="font-size-9 line-height-18">Create By</div>
									</th>
									<th class="sorting colwidth-13" onclick="orderBy('zdsj')" id="zdsj_order">
									     <div class="font-size-12 line-height-18">制单时间</div>
										<div class="font-size-9 line-height-18">Create Time</div>
									</th>
									<th class="sorting colwidth-13" onclick="orderBy('dprtcode')" id="dprtcode_order">
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
					<div class="clearfix"></div>
			</div>
      </div>
</div>
    <input type="hidden" name="username" id="username" value="${user.username }">
   <!-- start指定结束提示框 -->
	<div class="modal fade" id="alertModalZdjs" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:550px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
							<div class="font-size-16 line-height-18">指定结束</div>
							<div class="font-size-9 ">Role Store Auth</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12 col-sm-12">
			            	
			            	<div class="col-xs-12 col-sm-12 col-lg-12 padding-right-0 padding-left-0 margin-top-10 margin-bottom-10 ">
								<label class="pull-left col-lg-2 col-xs-2 col-sm-2 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12">定检任务编号</div>
									<div class="font-size-9">Scheduled No.</div>
								</label>
								<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="djrwbh"  disabled="disabled"/>
									<input type="hidden" name="id" id="id">
								</div>
							</div> 
			            	<div class="clearfix"></div>
			            	<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>结束原因</div>
								<div class="font-size-9 line-height-18">Reason</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="zdjsyy" name="zdjsyy" placeholder='长度最大为150'   maxlength="150"></textarea>
							</div>
							    
					     		<div class="clearfix"></div>
					     		<div class="text-right margin-top-10 padding-buttom-10 ">
                               <button onclick="zdjsOver()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal"><div
										class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div></button>
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
		<!-- start指定结束查看提示框 -->
	<div class="modal fade" id="alertModalZdjsReson" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:550px!important;">
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
								<div class="font-size-12">定检任务编号</div>
									<div class="font-size-9">Scheduled No.</div>
									</label>
								<div class="col-lg-10 col-sm-6 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="djrwbh2" disabled="disabled"/>
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
<!-- end -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project/checklist/checklist.js"></script> 
</body>
</html>