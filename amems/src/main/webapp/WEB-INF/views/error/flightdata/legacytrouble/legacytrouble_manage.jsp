<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>计划任务管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = '${param.id}';
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="userId" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading ">
				<div id="NavigationBar"></div>
			</div>
			<div class="panel-body">
				<div class="col-xs-2 padding-left-0 row-height">
						 
						<a href="#" onclick="add()" data-toggle="modal"
							class="btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height"
							checkPermission" permissioncode="flightdata:legacytrouble:manage:01,flightdata:legacytrouble:manage1:01">
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a>
						
						<button type="button"  onclick="legacyTroubleOutExcel();"
										class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-5 ">
										<div class="font-size-12">导出</div>
										<div class="font-size-9">Export</div>
						</button>
				</div>
               <!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0">
					
					
				  <div class=" pull-left padding-left-0 padding-right-0" style="width: 200px;">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div></span>
						<div
							class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="fjzch" class="form-control ">
								<option value="">显示全部 All</option>
							</select>
						</div>
					</div>  
					
					<div class=" pull-left padding-left-5 padding-right-0" style="width: 200px;">
						<input placeholder="故障保留单号/飞机注册号/飞行记录单号" id="keyword" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="search();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                         <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight"  onclick="more();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
				   		</button>
				   		
                    </div> 
				</div>
				<!-- 搜索框end -->
				
				<input type="hidden" id="user_jgdm" value="${user.jgdm}"> 	
				<div
					class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-top-10 display-none border-cccccc search-height "
					id="divSearch">
					 
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">执行M程序</div>
							<div class="font-size-9">Execute M Program</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="isM" class="form-control ">
								<option value="">全部 All</option>
								<c:forEach items="${binaryEnum}" var="item">
									<option value="${item.id}">${item.name}</option>
								</c:forEach>
							</select>
							
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">MEL类型</div>
							<div class="font-size-9">MEL Type</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="mel" class="form-control ">
								<option value="">全部 All</option>
								<c:forEach items="${melTypeEnum}" var="item">
									<option value="${item.name}">${item.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">状态</div>
							<div class="font-size-9">State</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zt" class="form-control ">
								<option value="">全部 All</option>
								<c:forEach items="${legacyTroubleStatusEnum}" var="item">
									
									<c:if test="${item.id ne 8 and item.id ne 10}">
									<option value="${item.id}">${item.name}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">申请日期</div>
							<div class="font-size-9">Application Date</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control date-range-picker"
								id="sqrq" readonly="readonly"/>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">到期日期</div>
							<div class="font-size-9">Expire</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control date-range-picker"
								id="dqrq" readonly="readonly"/>
						</div>
					</div>

					<div
						class="col-lg-3 col-sm-6 col-xs-12  pull-left padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">关闭日期</div>
							<div class="font-size-9">Close date</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control date-range-picker"
								id="gbrq" readonly="readonly"/>
						</div>
					</div>
					
					
					<div
						class="col-lg-3 col-sm-6 col-xs-12 pull-left padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
							</span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " name="dprtcode">
									<c:choose>
										<c:when test="${accessDepartment!=null && fn:length(accessDepartment) > 0}">
										<c:forEach items="${accessDepartment}" var="type">
											<option value="${type.id}"
												<%-- <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname} --%>
												<option value="${type.id}" <c:if test="${ dprtcode eq type.id or (user.jgdm eq type.id and  empty dprtcode)   }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
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
					 
					<div
						class="col-lg-2  text-right  pull-right padding-right-0 margin-bottom-10">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1  pull-right"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h " style="overflow-x: scroll;">
					<table  id="legacytrouble_list_table" class="table table-thin table-bordered text-center table-set" style="min-width: 1900px;">
						<thead>
							<tr>
								<th class="fixed-column colwidth-7" >
									<div
										class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th   class=" sorting colwidth-13"
									onclick="orderBy('gzbldh')" id="gzbldh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">故障保留单号</div>
										<div class="font-size-9 line-height-18">Retain No.</div>
									</div>
								</th>
								<th   class="sorting colwidth-10"
									onclick="orderBy('fjzch')" id="fjzch_order">
									<div class="important">
										<div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div>
									</div>
									</th>
								<th  class=" colwidth-10"><div
										class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div></th>
								<th  class=" colwidth-15">
									<div class="important">
										<div class="font-size-12 line-height-18">飞行记录单号/页码</div>
										<div class="font-size-9 line-height-18">Flight Record No.</div>
									</div>
								</th>
								<th  class=" colwidth-20"><div
										class="font-size-12 line-height-18">故障描述</div>
									<div class="font-size-9 line-height-18">Fault Desc</div></th>
								<th  class=" colwidth-10"><div
										class="font-size-12 line-height-18 ">MEL类型</div>
									<div class="font-size-9 line-height-18">MEL Type</div></th>
								<th  class=" colwidth-10">
									<div
										class="font-size-12 line-height-18 ">M执行程序</div>
									<div class="font-size-9 line-height-18">M Program</div>
								</th>
										
								<th   class="sorting colwidth-10"
									onclick="orderBy('sc_blrid')" id="sc_blrid_order"><div
										class="font-size-12 line-height-18">保留人</div>
									<div class="font-size-9 line-height-18">First Retain</div></th>

								<th   class="sorting colwidth-13"
									onclick="orderBy('sc_sqrq')" id="sc_sqrq_order">
									<div
										class="font-size-12 line-height-18">申请日期</div>
									<div class="font-size-9 line-height-18">Application Date</div>
								</th>
								<th   class="sorting colwidth-9"
									onclick="orderBy('sc_dqrq')" id="sc_dqrq_order"><div
										class="font-size-12 line-height-18">到期日期</div>
									<div class="font-size-9 line-height-18">Expire</div></th>
								<th  class="colwidth-30"><div
										class="font-size-12 line-height-18">工单信息</div>
									<div class="font-size-9 line-height-18">W/O Info</div></th>
								<th  class="sorting colwidth-13"
									onclick="orderBy('zc_blrid')" id="zc_blrid_order"><div
										class="font-size-12 line-height-18">再次保留人</div>
									<div class="font-size-9 line-height-18">Again Retain</div></th>
								<th  class="sorting colwidth-15"
									onclick="orderBy('zc_sqrq')" id="zc_sqrq_order">
									<div
										class="font-size-12 line-height-18">再次申请日期</div>
									<div class="font-size-9 line-height-18">Again Application Date</div>
									</th>
								<th   class="sorting colwidth-13"
									onclick="orderBy('zc_dqrq')" id="zc_dqrq_order"><div
										class="font-size-12 line-height-18">再次到期日期</div>
									<div class="font-size-9 line-height-18">Again Expire</div></th>

								<th  class="colwidth-13"><div
										class="font-size-12 line-height-18">关闭人</div>
									<div class="font-size-9 line-height-18">Closer</div></th>
								<th  class="sorting colwidth-10"
									onclick="orderBy('zt')" id="zt_order"><div
										class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div></th>
									
								<th  class="sorting colwidth-13" onclick="orderBy('whrid')" id="whrid_order"   ><div
										class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div></th>
								<th  class="sorting colwidth-13" onclick="orderBy('whsj')" id="whsj_order"   ><div
										class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div></th>
											
								<th  class=" colwidth-15"><div
										class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div></th>
							</tr>
						</thead>
						<tbody id="list">
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center padding-right-0 page-height" id="pagination">
				</div>
				
				<div class="clearfix"></div>
			</div>
		</div>

		<!-- END CONTENT -->
	</div>

	<!-- 指定结束 -->
	<div class="modal fade" id="endModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="endModalBody">
					<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">关闭</div>
						<div class="font-size-9 ">Close</div>
					</div>
					
					<form id="endform" >
						<div class="panel-body padding-top-0 padding-bottom-10" >
						<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
							<input type="hidden" name="id" id="id">
						</div> 
						<div class="clearfix"></div>
						<div
							class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">
									故障保留单号
								</div>
								<div class="font-size-9 line-height-18">Retain No</div></label>
							<div class="col-xs-9 padding-left-8 padding-right-0">
								 <input id="gzbldh"  disabled="disabled" class="form-control "/>
							</div>
						</div>
						<div id="gbrqBlock"
							class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">
									关闭时间
								</div>
								<div class="font-size-9 line-height-18">Close Date</div>
							</label>
							<div class="col-xs-9 padding-left-8 padding-right-0">
								 <input id="gbrq"  disabled="disabled" class="form-control "/>
							</div>
						</div>
						<div
							class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">
									<span style="color: red">*</span>关闭人
								</div>
								<div class="font-size-9 line-height-18">Closer</div></label>
							<div class="col-xs-9 padding-left-8 padding-right-0">
								<select id="gbrid" class="form-control " name="gbrid">
									<c:forEach items="${users}" var="item">
										<option value="${item.id}">${erayFns:escapeStr(item.displayName)}</option>
									</c:forEach>
								</select>
							</div>
						</div>                  
						<div class="clearfix"></div>
						<div
							class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label
								class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>关闭原因</div>
								<div class="font-size-9 line-height-18">Contents</div>
							</label>
							<div
								class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="gbsm" name="gbsm"
									placeholder='长度最大为300' maxlength="300"></textarea>
							</div>
						</div>
						<div class="clearfix"></div>
						
						<div class="text-right margin-top-0 padding-buttom-10 margin-buttom-10 ">
							<button id="endbtn" type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-0">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
	                   		</button>&nbsp;
                   			<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-0" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
                   		</div>
						
						</div> 
					</form>
					</div>
					
				</div>
				
			</div>
		</div>
	</div>

	<!-- 生成工单 -->
	<div class="modal fade" id="orderModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog" style="width: 400px;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="orderModalBody">
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="font-size-16 line-height-18">工单</div>
						<div class="font-size-9 ">W/O</div>
					</div>
					<div class="panel-body">	
					<form id="orderform">
						<input type="hidden" id="id" />
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group">
										<label
											class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-10 padding-right-0 form-group"><div
												class="font-size-12">故障保留单编号</div>
											<div class="font-size-9">Retain No.</div></label>
										<div
											class="col-lg-8 col-sm-8 col-xs-8 padding-left-10 padding-right-0 ">
												<input type="text" class="form-control"
												 id="gdgzbldh" name="gdgzbldh" 
												readonly />
										</div>
						</div>	
						<div
							class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">
									<span style="color: red">*</span>工单类型
								</div>
								<div class="font-size-9 line-height-18">W/O Type</div></label>
							<div class="col-xs-8 padding-left-8 padding-right-0">
								<select id="gdlx" class="form-control " name="gdlx">
									<option value="ATTACHED">附加工单 Attached</option>
									<option value="WORK_ORDER">排故工单 Troubleshooting</option>
									<!-- <option value="EO_BILL">EO工单任务 EO</option> -->
								</select>
							</div>
						</div>
						<div class="clearfix"></div>
					</form>
					</div>
					</div>
					<div class="text-right margin-top-10 margin-bottom-10">
						<button id="generateWorkOrderBtn" type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							data-dismiss="modal">
							<div class="font-size-12">保存</div>
							<div class="font-size-9">Save</div>
						</button>
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							data-dismiss="modal">
							<div class="font-size-12">取消</div>
							<div class="font-size-9">Cancel</div>
						</button>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<script type="text/javascript"
		src="${ctx}/static/js/thjw/flightdata/legacytrouble/legacytrouble_manage.js"></script>

</body>
</html>