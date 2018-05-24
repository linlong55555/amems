<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>航材成本列表</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content ">
		<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			    <div  class='searchContent row-height' >
				 <!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0 form-group" style="width: 250px;">
						<input type="text" placeholder='件号/厂家件号/中英文' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 form-group">   
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
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">管理级别</div>
							<div class="font-size-9">Management Level</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='gljb_search'>
								<option value="" selected="true" >查看全部All</option>
								<c:forEach items="${supervisoryLevelEnum}" var="item">
								  <option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">时效控制</div>
							<div class="font-size-9">Time Limited Control</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='sxkz_search'>
								<option value="" selected="true" >查看全部All</option>
								<c:forEach items="${agingControlEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">类型</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='hclx_search'>
								<option value="" selected="true" >查看全部All</option>
								<c:forEach items="${materialTypeEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">维护日期</div>
							<div class="font-size-9">Operate time</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="whrq_search" readonly />
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" name="dprtcode_search" class="form-control ">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
								</c:forEach>
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
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" style="overflow: auto; height: auto;">
					<table id="cost_list_table"
						class="table-set table table-thin table-bordered table-striped table-hover" style="min-width: 2350px;">
						<thead>
							<tr>
								<th class="fixed-column colwidth-5">	
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="sorting fixed-column colwidth-12" onclick="orderBy('bjh')" id="bjh_order">
									<div class="important">
									<div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-18">P/N</div>
									</div>
								</th>
								<th class="sorting colwidth-15" onclick="orderBy('juescb')" id="juescb_order">
									<div class="font-size-12 line-height-18">决算成本(人民币:元)</div>
									<div class="font-size-9 line-height-18">FinalCost（RMB：YUAN）</div>
								</th>
								<th class="sorting colwidth-20" onclick="orderBy('jiescb')" id="jiescb_order">
									<div class="font-size-12 line-height-18">结算成本(人民币:元)</div>
									<div class="font-size-9 line-height-18">SettlementCost（RMB：YUAN）</div>
								</th>
								<th class="sorting colwidth-20" onclick="orderBy('gscb')" id="gscb_order">
									<div class="font-size-12 line-height-18">估算成本(人民币:元)</div>
									<div class="font-size-9 line-height-18"> ApproximateCost（RMB：YUAN）</div>
								</th>
								<th class="sorting colwidth-12" onclick="orderBy('cjjh')" id="cjjh_order">
									<div class="important">
									<div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-18">MP/N</div>
									</div>
								</th>
								<th class="sorting colwidth-14" onclick="orderBy('ywms')" id="ywms_order">
									<div class="important">
									<div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-18">F.Name</div>
									</div>
								</th>
								<th class="sorting colwidth-14" onclick="orderBy('zwms')" id="zwms_order">
									<div class="important">
									<div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-18">CH.Name</div>
									</div>
								</th>
								<th class="colwidth-12">
									<div class="font-size-12 line-height-18">机型</div>
									<div class="font-size-9 line-height-18">Model</div>
								</th>
								<th class="sorting colwidth-7" onclick="orderBy('gljb')" id="gljb_order">
									<div class="font-size-12 line-height-18">管理级别</div>
									<div class="font-size-9 line-height-18">M/Level</div>
								</th>
								<th class="sorting colwidth-7" onclick="orderBy('sxkz')" id="sxkz_order">
									<div class="font-size-12 line-height-18">时效控制</div>
									<div class="font-size-9 line-height-18">Time Control</div>
								</th>
								<th class="sorting colwidth-7" onclick="orderBy('IS_MEL')" id="IS_MEL_order">
									<div class="font-size-12 line-height-18">是否定检</div>
									<div class="font-size-9 line-height-18">Is P/I</div>
								</th>
								<th class="sorting colwidth-10" onclick="orderBy('zjh')" id="zjh_order">
									<div class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div>
								</th>
								<th class="sorting colwidth-7" onclick="orderBy('hclx')" id="hclx_order">
									<div class="font-size-12 line-height-18">类型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class="sorting colwidth-5" onclick="orderBy('jldw')" id="jldw_order">
									<div class="font-size-12 line-height-18">单位</div>
									<div class="font-size-9 line-height-18">Unit</div>
								</th>
								<th class="sorting colwidth-30" onclick="orderBy('bz')" id="bz_order">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('username')" id="username_order">
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('cbwhsj')" id="cbwhsj_order">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('dprtcode')" id="dprtcode_order">
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
			</div>
		</div>
		
	<%@ include file="/WEB-INF/views/alert.jsp"%>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/cost/materialcostlist.js"></script>
</body>
</html>