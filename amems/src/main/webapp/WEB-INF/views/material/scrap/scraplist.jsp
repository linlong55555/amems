<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>报废清单</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<input maxlength="20" type="hidden" id="userId" value="" />
<div class="page-content ">
	<div class="panel panel-primary">
	<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<input type="hidden" id="zzjgid" name="zzjgid" value="${user.jgdm}" />
				<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0 row-height" style="width: 250px;">
						<input type="text" placeholder='报废单号' id="keyword_search" class="form-control ">
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
				
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-top-10 display-none border-cccccc search-height" id="divSearch">
					 <div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">申请日期</div>
							<div class="font-size-9">Application Date</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
						  <input type="text" class="form-control " name="date-range-picker" id="bfsj_search" readonly />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">报废类型</div>
							<div class="font-size-9">Scrap Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
					       <select  id="bflx_search" name="bflx_search" class="form-control">
					           <option value="">查看全部</option>
					           <option value="1">库内报废</option>
					           <option value="2">外场报废</option>
					       </select>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">State</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
					       <select  id="zt_search" name="zt_search" class="form-control">
					           <option value="">查看全部</option>
					           <option value="1">保存</option>
					           <option value="10">完成</option>
					           <option value="11">撤销</option>
					       </select>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
						       <select  id="dprtcode_search" name="dprtcode_search" class="form-control">
					                 <c:forEach items="${accessDepartment}" var="type">
									   <option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
							     	 </c:forEach>
						       </select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-1 pull-right text-right padding-right-0" style="margin-bottom: 10px;">

						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1 "
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>	
				<div class="clearfix"></div>

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="overflow-x:scroll">
					<table id="scrap_list_table" class="table-set table table-thin table-bordered table-striped table-hover" style=" min-width: 1300px !important;">
						<thead>
							<tr>
								<th class="fixed-column colwidth-5">
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="sorting fixed-column colwidth-13" onclick="orderBy('bfdh')" id="bfdh_order">
									 <div class="important">
									<div class="font-size-12 line-height-18">报废单号</div>
									<div class="font-size-9 line-height-18">Scrap No.</div>
									</div>
								</th>
								<th class="sorting colwidth-30" onclick="orderBy('bfyy')" id="bfyy_order">
									<div class="font-size-12 line-height-18">报废原因</div>
									<div class="font-size-9 line-height-18">Scrap Cause</div>
								</th>
								<th class="sorting colwidth-5" onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('sprid')" id="sprid_order">
									<div class="font-size-12 line-height-18">审批人</div>
									<div class="font-size-9 line-height-18">Approvaler</div>
								</th>
								<th  class="sorting colwidth-10" onclick="orderBy('spsj')" id="spsj_order">
									<div class="font-size-12 line-height-18">审批日期</div>
									<div class="font-size-9 line-height-18">Approval Date</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('bfrid')" id="bfrid_order">
									<div class="font-size-12 line-height-18">报废人</div>
									<div class="font-size-9 line-height-18">Scraper</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('bfsj')" id="bfsj_order">
									<div class="font-size-12 line-height-18">申请日期</div>
									<div class="font-size-9 line-height-18">Application Date</div>
								</th>
								<th class="sorting colwidth-10" onclick="orderBy('bflx')" id="bflx_order">
									<div class="font-size-12 line-height-18">报废类型</div>
									<div class="font-size-9 line-height-18">Scrap Type</div>
								</th>
								<th  class="sorting colwidth-13" onclick="orderBy('zdrid')" id="zdrid_order">
									<div class="font-size-12 line-height-18">制单人</div>
									<div class="font-size-9 line-height-18">Creater</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('zdsj')" id="zdsj_order">
									<div class="font-size-12 line-height-18">制单时间</div>
									<div class="font-size-9 line-height-18">Create Time</div>
								</th>
								<th class="sorting colwidth-15" onclick="orderBy('dprtcode')" id="dprtcode_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 page-height text-center" id="pagination">
				</div>
			</div>
		</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/scrap/scraplist.js"></script>
</body>
</html>
