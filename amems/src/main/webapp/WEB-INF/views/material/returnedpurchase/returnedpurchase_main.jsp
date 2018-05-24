<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" value="${user.jgdm}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->


	<div class="page-content">
		<div class="panel panel-primary ">
		<div class="panel-heading" id="NavigationBar" ></div>
			
				<div class="panel-body padding-bottom-0">
				<div  class='searchContent row-height' >
					<div class="col-xs-2 col-md-3 padding-left-0  form-group">
						<a onclick="add()" data-toggle="modal" permissioncode='aerialmaterial:returnedpurchase:manage:01' class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" >
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a> 
					</div>
				
					<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0 form-group" >
		
					<div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;" >
						<input placeholder="退货单号/退货人/出库单号" id="keyword_search" class="form-control " type="text">
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
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">退货日期</div>
							<div class="font-size-9">Application Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="flightDate_search" readonly />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
							<div
								class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12">状态</div>
								<div class="font-size-9">State</div>
							</div>
							<div
								class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="zt" class="form-control " name="zt">
									<option value="">显示全部All</option>
									<option value="1">保存</option>
									<option value="2">提交</option>
									<option value="11">撤销</option>
								</select>
							</div>
						</div>
		
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" name="dprtcode" class="form-control" onchange="changeOrganization()">
								<c:choose>
											<c:when test="${accessDepartment!=null && fn:length(accessDepartment) > 0}">
											<c:forEach items="${accessDepartment}" var="type">
												<option value="${type.id}"
													<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}
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
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				
				<div class="clearfix"></div>
                </div>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" style="overflow-x: scroll;">
					<table id="jrsq" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width:1000px">
						<thead>
							<tr>
								<th  class="colwidth-5" style="vertical-align: middle;">
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-5">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
							
								<th name="column_thdh"  class="sorting colwidth-10" onclick="orderBy('thdh',this)">
									<div class="important"><div class="font-size-12 line-height-18">退货单号</div></div>
									<div class="font-size-9 line-height-18">Returned No.</div>
								</th>
								<th name="column_ckdh"  class="sorting colwidth-10" onclick="orderBy('ckdh',this)">
									<div class="important"><div class="font-size-12 line-height-18">出库单号</div></div>
									<div class="font-size-9 line-height-18">Delivery No.</div>
								</th>
								<th name="column_fhdw" class="sorting colwidth-10" onclick="orderBy('fhdw',this)" >
									<div class="important">
									<div class="font-size-12 line-height-18">发货单位</div>
									<div class="font-size-9 line-height-18">Ship Dprt</div>
									</div>
								</th>
								<th name="column_thrid"  class="sorting colwidth-10" onclick="orderBy('thrid',this)" >
									<div class="important">
									<div class="font-size-12 line-height-18">退货人</div>
									<div class="font-size-9 line-height-18">Returnee</div>
									</div>
								</th>
								<th name="column_thrq"  class="sorting colwidth-10" onclick="orderBy('thrq',this)" >
									<div class="font-size-12 line-height-18">退货日期</div>
									<div class="font-size-9 line-height-18">Returned Date</div>
								</th>
								<th name="column_zt"  class="sorting colwidth-7" onclick="orderBy('zt',this)" >
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</th>
								<th name="column_bz"  class="sorting colwidth-10" onclick="orderBy('bz',this)" >
									<div class="important">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
									</div>
								</th>
								<th  name="column_zdrid" class="sorting colwidth-10" onclick="orderBy('zdrid',this)" >
									<div class="important">
									<div class="font-size-12 line-height-18">制单人</div>
									<div class="font-size-9 line-height-18">Creator</div>
									</div>
								</th>
								<th  name="column_zdsj" class="sorting colwidth-15" onclick="orderBy('zdsj',this)" >
									<div class="font-size-12 line-height-18">制单时间</div>
									<div class="font-size-9 line-height-18">Create Time</div>
								</th>
								<th name="column_dprtname"  class="sorting colwidth-15" onclick="orderBy('dprtname',this)">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
					
				</div>
				<div class="clearfix"></div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/returnedpurchase/returnedpurchase_main.js"></script>
</body>
</html>