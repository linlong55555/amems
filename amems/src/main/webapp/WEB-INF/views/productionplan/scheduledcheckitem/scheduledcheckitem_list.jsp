<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
</script>
<!-- <style>
body{overflow-y:hidden; }
</style> -->
</head>
<body class="page-header-fixed">
	<input type="hidden" id="userId" value="" />
	<input type="hidden" id="fazhi1" value="${threshold1}" />
	<input type="hidden" id="zzjgid" value="${user.jgdm}" />
	<input type="hidden" id="fazhi2" value="${threshold2}" />
	<!-- BEGIN HEADER -->
	<div class="page-content">
		<!-- END PAGE TITLE & BREADCRUMB-->

		<div class="panel panel-primary">
		
		<div class="panel-heading" id="NavigationBar"></div>

					
		<input type="hidden" id="adjustHeight" value="107">
			<div class="clearfix"></div>

			<div  class="panel-body" >
				<div class="row row-height margin-left-0" style="margin-right: 0px;">
					<div class="pull-left margin-bottom-10 padding-left-10" >
						    <div class="pull-left  text-right padding-left-0 padding-right-0">
						    <div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
							</div>
						<div id="fjzch_search_sel" class=" padding-left-8 pull-left" style="width: 180px; margin-right:5px;">
								<select class='form-control' id='fjzch_search' name="fjzch_search" >
									
								</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-3 margin-bottom-10 padding-left-0 padding-right-0  ">
									<span
										class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">组织机构</div>
										<div class="font-size-9">Organization</div>
									</span>
									<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 ">
										<select id="dprtcode" class="form-control " name="dprtcode"
											>
											<c:choose>
												<c:when test="${accessDepartment!=null && fn:length(accessDepartment) > 0}">
												<c:forEach items="${accessDepartment}" var="type">
													<option value="${type.id}"
														<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}
													</option>
												</c:forEach>
												</c:when>
												<c:otherwise>
													<option value="">暂无组织机构 No Organization</option>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
					</div>
				</div>
				
				<!-----标签导航start---->
				<ul class="nav nav-tabs " role="tablist" id="tablist">
					<li  class="active"><a href="#planeLoadingList" data-toggle="tab">定检监控 Inspection And Inspection</a></li>
					<li ><a  href="#timeMonitor" data-toggle="tab">时控件监控 Time Control</a></li>
					<li ><a  href="#fixedMonitor" data-toggle="tab">其他工单监控 Other Work Orders</a></li>
				</ul>
				<!-----标签内容start---->
				<div class="tab-content">
			<div class="tab-pane fade in active" id="planeLoadingList">	
				<div class="col-xs-12 padding-left-0 padding-right-0 ">
					<div class=" col-md-5 padding-left-0 padding-right-0 ">
						 <div class="col-md-6 col-xs-6 padding-left-0 padding-right-0">
								<div class="col-xs-2 col-md-2 padding-left-0">
									<button type="button"  onclick="Excel();"
										class="btn btn-primary padding-top-1 padding-bottom-1 pull-left">
										<div class="font-size-12">导出</div>
										<div class="font-size-9">Export</div>
									</button>
								</div>

								<div class="col-xs-7 col-sm-7 margin-top-5 margin-left-10 padding-left-1 padding-right-0">
								&nbsp;<label style=" font-weight:normal"><input type="radio" id='jk'   name='jkfs' value='MONITOR' checked="checked"/>&nbsp; 监控</label>
								&nbsp;<label style=" font-weight:normal"><input type="radio" id='yp' name='jkfs'   value='PRE_SCHEDULING'/>&nbsp;预排</label>
								</div>
					
					       <div id="jsid" class="col-xs-2 col-md-2 padding-left-1">
									<a onclick="sub()"
										permissioncode='productionplan:scheduledcheckitem:main:01' class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-10 pull-left checkPermission">
										<div class="font-size-12">提交计划</div>
										<div class="font-size-9">Submit Production</div>
									</a>
					          </div>
							</div>  
								
							<div id="jzrqBlock" class="col-xs-12 col-md-6  padding-left-0 padding-right-0">
								<span class="pull-left col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">截至日期</div>
									<div class="font-size-9">End Date</div>
								</span>
								<div class="col-xs-9 col-sm-9 padding-left-8 padding-right-0">
									 <input type="text" id="jzrq"  name="jzrq" class="form-control datepicker " style="width: 100%"  data-date-format="yyyy-mm-dd"  placeholder="请选择日期"   />
								</div>
							</div>
					</div>
					
						
						<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0" >
					<div class=" pull-left padding-left-0 padding-right-0" style="width:240px;" >
						<input placeholder="定检项目名称编号/中英文/序列号/件号" id="keyword_search" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 resizeHeight" onclick="searchFromButton();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                        <input id="click4Btn" type="hidden"/>
                    </div> 
				</div>
			</div>
			<div class="clearfix"></div>
			
						<div id="sssss">
				<div id="jiankong" class="col-xs-12 text-center margin-bottom-5 padding-left-0 padding-right-0 table-h"  style="overflow-x:scroll">
						<table class="table table-thin table-bordered text-center table-set" id="jkPanel"  style="min-width:2500px">
							<thead>
								<tr>
								<th class="fixed-column colwidth-5" style="vertical-align: middle;">
									<a href="javascript:checkAll();" class="pull-left margin-left-10 margin-bottom-10" id='CancelAll' ><img src="${ctx}/static/assets/img/d1.png" alt="全选" title="全选" /></a>
									<a href="javascript:notCheckAll();" class="pull-left margin-left-10 margin-bottom-10" id='CancelAll'><img src="${ctx}/static/assets/img/d2.png" alt="不全选" title="不全选" /></a> 
								</th>
								<th class="fixed-column colwidth-5" ><div class="font-size-12 line-height-18">操作</div>
								<div class="font-size-9 line-height-18">Operation</div></th>
								<th class=" colwidth-5" ><div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div></th>
								<th  class=' sorting colwidth-20' name="column_zwms"  onclick="orderBy('zwms',this)" >
								<div class="important">
								<div
											class="font-size-12 line-height-18">定检项目名称</div>
											</div>
										<div class="font-size-9 line-height-18">Fixed Check Name</div></th>
								<th class='sorting colwidth-25' name="column_ywmc"   onclick="orderBy('ywmc',this)" ><div class="important"><div
									class="font-size-12 line-height-18">英文名称</div></div>
								<div class="font-size-9 line-height-18">F.Name</div></th>
								<th name="column_xlh" class="sorting colwidth-15" onclick="orderBy('xlh',this)" ><div class="important"><div
										class="font-size-12 line-height-18">序列号</div></div>
									<div class="font-size-9 line-height-18">S/N</div></th>
								<th  class="colwidth-15"  id="jhcss_order"><div
										class="font-size-12 line-height-18">计划</div>
									<div class="font-size-9 line-height-18">Plan</div></th>
								<th class="colwidth-15"  id="sys_order"><div
											class="font-size-12 line-height-18">剩余</div>
								<div class="font-size-9 line-height-18">Remain</div></th>
								<th class="sorting colwidth-9"  name="column_syts" onclick="orderBy('syts',this)" id="syts_order"><div
											class="font-size-12 line-height-18" >剩余(天)</div>
								<div class="font-size-9 line-height-18">Remain(Day)</div></th>
								<th class="colwidth-30"  id="jkbz_order"><div
											class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div></th>
								<th name="column_wz"  class="sorting colwidth-9" onclick="orderBy('wz',this)" ><div
											class="font-size-12 line-height-18">位置</div>
								<div class="font-size-9 line-height-18">Location</div></th>
								<th class="colwidth-31" id="jhrw_order"><div
									class="font-size-12 line-height-18">任务单号</div>
								<div class="font-size-9 line-height-18">Task No.</div></th>
								<th name="column_djbh"  class="sorting colwidth-10" onclick="orderBy('djbh',this)" >
									<div class="important">
									<div class="font-size-12 line-height-18">定检项目编号</div>
								<div class="font-size-9 line-height-18">Fixed Check No.</div>
								</div></th>
							<th name="column_jh"  class="sorting colwidth-15" onclick="orderBy('jh',this)" ><div class="important"><div
								class="font-size-12 line-height-18">件号</div></div>
							<div class="font-size-9 line-height-18">P/N</div></th>
							<th name="column_zwmc"  class="sorting colwidth-20" onclick="orderBy('zwmc',this)" ><div class="important"><div
									class="font-size-12 line-height-18">中文名称</div></div>
							<div class="font-size-9 line-height-18">CH.Name</div></th>
							<th class="colwidth-10"><div class="font-size-12 line-height-18">组织机构</div>
								<div class="font-size-9 line-height-18">Organization</div></th>
								</tr>
							</thead>
							<tbody id="list">
							</tbody>
						</table>
						</div>
					<!-- 	<div class="col-xs-12 text-center padding-right-0 page-height"  style="margin-top: 0px; margin-bottom: 0px;" id="yp_pagination">
						</div> -->
						</div>
						
						<div id="pre_scheduling_block">
						<div id="pre_scheduling"  class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h"  style="overflow-x:scroll; display: none; margin-top: 10px;">
							<table class="table table-thin table-bordered table-set" id="ypPanel" >
								<thead>
									<tr>
										<!-- <th><input type="checkbox" id="store"
											onclick="selectAll('store','list')" /></th> -->
										<th class="fixed-column colwidth-3">
											<div class="font-size-12 line-height-18">序号</div>
											<div class="font-size-9 line-height-18">No.</div>
										</th>
										
										
										<th class="fixed-column colwidth-13">
											<div class="important">
												<div class="font-size-12 line-height-18">定检项目编号</div>
												<div class="font-size-9 line-height-18">Fixed Check No</div>
											</div>
										</th>
										<th class="fixed-column colwidth-15">
											<div class="important">
												<div
													class="font-size-12 line-height-18">定检项目名称</div>
												<div class="font-size-9 line-height-18">Fixed Check Name</div>
											</div>
										</th>
										
										<th class=" colwidth-25">
											<div class="important">
												<div
													class="font-size-12 line-height-18">英文名称</div>
												<div class="font-size-9 line-height-18">F.Name</div>
											</div>
										</th>
										
										<th class="  colwidth-20">
											<div class="important">
												<div
													class="font-size-12 line-height-18">序列号</div>
												<div class="font-size-9 line-height-18">S/N</div>
											</div>
										</th>
										<th class=" colwidth-13">
											<div class="font-size-12 line-height-18">计划执行日期</div>
											<div class="font-size-9 line-height-18">Planned Exec Date</div>
										</th>	
										
										<th class="colwidth-20" id="jhcss_order">
											<div
												class="font-size-12 line-height-18">计划</div>
											<div class="font-size-9 line-height-18">Plan</div>
										</th>
										
										<th class="colwidth-15">
											<div class="important">
												<div
													class="font-size-12 line-height-18">件号</div>
												<div class="font-size-9 line-height-18">P/N</div>
											</div>
										</th>
										
										<th class="colwidth-20">
											<div class="important">
												<div class="font-size-12 line-height-18">中文名称</div>
												<div class="font-size-9 line-height-18">CH.Name</div>
											</div>
										</th>
										
										<th class="colwidth-15">
											<div class="font-size-12 line-height-18">组织机构</div>
											<div class="font-size-9 line-height-18">Organization</div>
										</th>
									</tr>
								</thead>
								<tbody id="list">
								</tbody>
							</table>
						</div>
<!-- 						<div class="col-xs-12 text-center padding-right-0 page-height"  style="margin-top: 0px; margin-bottom: 0px;" id="yp_pagination"> -->
<!-- 						</div> -->
						</div>
						
					</div>

					<div class="tab-pane fade" id="timeMonitor">
						<div class="row feature">
							<%@ include
								file="/WEB-INF/views/productionplan/scheduledcheckitem/timecontrollwareitem_list.jsp"%>
						</div>
					</div>

					<div class="tab-pane fade" id="fixedMonitor">
						<div class="row feature">
							<%@ include
								file="/WEB-INF/views/productionplan/scheduledcheckitem/otherworkorder_list.jsp"%>
						</div>
					</div>

				</div>

			</div>
		</div>
		
	<!-- start编辑备注提示框 -->
	<div class="modal fade" id="alertModalview" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:50%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">编辑监控备注</div>
							<div class="font-size-9 ">Edit Monitoring Remark</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
							<div class="col-xs-12 col-sm-12 col-lg-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<span class="pull-left col-lg-2 col-xs-2 col-sm-2  text-right padding-left-0 padding-right-0">
									<div class="important"><div class="font-size-12">英文名称</div></div>
									<div class="font-size-9">F.Name</div>
								</span>
								<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="ywmc" name="ywmc" disabled="disabled"/>
								</div>
							</div> 
							<div class="clearfix"></div>
							<div class="col-xs-12 col-sm-6 col-lg-6 padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<span class="pull-left col-lg-4 col-xs-2 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="important"><div class="font-size-12">序列号</div></div>
									<div class="font-size-9">S/N</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="xlh" name="xlh" disabled="disabled"/>
								</div>
							</div> 
							<div class="col-xs-12 col-sm-6 col-lg-6  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<span class="pull-left col-lg-4 col-xs-2 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="important"><div class="font-size-12">件号</div></div>
									<div class="font-size-9">P/N</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="jh" name="jh" disabled="disabled"/>
								</div>
							</div> 
							<div class="clearfix"></div>
							<div class="col-xs-12 col-sm-6 col-lg-6  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<span class="pull-left col-lg-4 col-xs-2 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="important"><div class="font-size-12">定检项目编号</div></div>
									<div class="font-size-9">Fixed Check No.</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="djbh" name="djbh" disabled="disabled"/>
								</div>
							</div> 
								<div class="col-xs-12 col-sm-6 col-lg-6  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<span class="pull-left col-lg-4 col-xs-2 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="important"><div class="font-size-12">定检项目名称</div></div>
									<div class="font-size-9">Fixed Check Name</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="zwms" name="zwms" disabled="disabled"/>
								</div>
							</div> 
						<div class="clearfix"></div>
						
							<div class="col-xs-12 col-sm-12 col-lg-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
								<span class="pull-left col-xs-2 col-sm-2 col-lg-2 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">监控备注</div>
									<div class="font-size-9">Monitoring Remark</div>
									<input  type="hidden" id="jkid" />
								</span>
								<div class="col-xs-10 col-sm-10 col-lg-10 padding-left-8 padding-right-0">
									<textarea class="form-control" id="jkbz" name="jkbz"   maxlength="300" placeholder='长度最大为300'></textarea>
								</div>
							</div> 
							
					     	<div class="text-center margin-top-10 padding-buttom-10 ">
					     		<button type="button" class="pull-right btn btn-primary margin-right-0 padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
					     		<button type="button" class="pull-right btn btn-primary margin-right-10 padding-top-1 margin-right-10 padding-bottom-1 margin-bottom-10" onclick="sbDown()">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
		                   		</button>
                     			
                    		</div><br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- end编辑备注提示框 -->
		
</div>
	<script type="text/javascript"
		src="${ctx}/static/js/thjw/common/select.js"></script>
	<script type="text/javascript"
		src="${ctx}/static/js/thjw/productionplan/scheduledcheckitem/scheduledcheckitem_list.js"></script>
	
</body>
</html>