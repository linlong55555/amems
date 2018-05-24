<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<script>
	var dprtcode = "${param.dprtcode}";
	var pageParam = '${param.pageParam}';
	</script>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<input maxlength="20" type="hidden" id="userId" value="" />
	<input type ="hidden" id="fjjx" value='${erayFns:escapeStr(param.fjjx)}'>
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
<div class="page-content">
	<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			  	 <div class="col-xs-6 padding-left-0 row-height">
					<a href="#" onclick="add()" data-toggle="modal" permissioncode='project:planemodeldata:main:01'
						class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission"
						><div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
				</div>
				<input type="hidden" id="zzjgid" name="zzjgid" value="${user.jgdm}" />
               <!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input placeholder="飞机机型/创建人" id="keyword_search" class="form-control " type="text">
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
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " id='zilei'>
						<span class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">定检计划公式</div>
							<div class="font-size-9">Calculation Methods</div>
						</span>
						<div class="col-xs-8  padding-left-8 padding-right-0">
					       <select  id="gs_djjh" name="gs_djjh" class="form-control">
			                  <option value="" selected="true" >查看全部</option>
							  <option value="1" >计划与实际取小+周期</option>
							  <option value="2" >实际+周期</option>
							  <option value="3" >计划+周期</option>
						   </select>
						</div>
					</div>
				
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " id='zilei'>
						<span class=" col-xs-4  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">特殊情况</div>
							<div class="font-size-9">Special</div>
						</span>
						<div class="col-xs-8  padding-left-8 padding-right-0">
					       <select  id="is_tsqk" name="is_tsqk" class="form-control">
				               <option value="" selected="true" >查看全部</option>
							   <option value="0" >无</option>
							   <option value="1" >有</option>
						   </select>
						</div>
					</div>
					
			     <div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " id='zilei'>
					<span class=" col-xs-4  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
					</span>
					<div class="col-xs-8 padding-left-8 padding-right-0">
					       <select  id="dprtcode" name="dprtcode" class="form-control">
				                 <c:forEach items="${accessDepartment}" var="type">
								   <option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
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
				<div class="clearfix"></div>

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="margin-top: 10px;overflow-x:scroll ;">
					<table  id="ckTb" class="table-set table table-thin table-bordered table-striped table-hover text-right"
						style=" min-width: 1800px !important;">
						<thead>
							<tr>
								<th class="fixed-column colwidth-5">
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="fixed-column colwidth-7 sorting" onclick="orderBy('fjjx')" id="fjjx_order">
								  <div class="important">
									<div class="font-size-12 line-height-18">飞机机型</div>
									<div class="font-size-9 line-height-18">Model</div>
								  </div>	
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('r_jsfxsj')" id="r_jsfxsj_order">
									<div class="font-size-12 line-height-18">机身飞行时间</div>
									<div class="font-size-9 line-height-18">Flight HRS.</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('r_ssdsj')" id="r_ssdsj_order">
									<div class="font-size-12 line-height-18">搜索灯时间</div>
									<div class="font-size-9 line-height-18">Search Time</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('r_jcsj')" id="r_jcsj_order">
									<div class="font-size-12 line-height-18">绞车时间</div>
									<div class="font-size-9 line-height-18">Winch Time</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('r_qljxh')" id="r_qljxh_order">
									<div class="font-size-12 line-height-18">起落循环</div>
									<div class="font-size-9 line-height-18">Flight CYCS.</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('r_jcxh')" id="r_jcxh_order">
									<div class="font-size-12 line-height-18">绞车循环</div>
									<div class="font-size-9 line-height-18">Winch CYCS.</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('r_wdgxh')" id="r_wdgxh_order">
									<div class="font-size-12 line-height-18">外吊挂循环</div>
									<div class="font-size-9 line-height-18">E/S CYCS.</div>
								</th>
								<th class="colwidth-3 sorting" onclick="orderBy('r_n1')" id="r_n1_order">
									<div class="font-size-12 line-height-18">N1</div>
									<div class="font-size-9 line-height-18">N1</div>
								</th>
								<th class="colwidth-3 sorting" onclick="orderBy('r_n2')" id="r_n2_order">
									<div class="font-size-12 line-height-18">N2</div>
									<div class="font-size-9 line-height-18">N2</div>
								</th>
								<th class="colwidth-3 sorting" onclick="orderBy('r_n3')" id="r_n3_order">
									<div class="font-size-12 line-height-18">N3</div>
									<div class="font-size-9 line-height-18">N3</div>
								</th>
								<th class="colwidth-3 sorting" onclick="orderBy('r_n4')" id="r_n4_order">
									<div class="font-size-12 line-height-18">N4</div>
									<div class="font-size-9 line-height-18">N4</div>
								</th>
								<th class="colwidth-3 sorting" onclick="orderBy('r_n5')" id="r_n5_order">
									<div class="font-size-12 line-height-18">N5</div>
									<div class="font-size-9 line-height-18">N5</div>
								</th>
								<th class="colwidth-3 sorting" onclick="orderBy('r_n6')" id="r_n6_order">
									<div class="font-size-12 line-height-18">N6</div>
									<div class="font-size-9 line-height-18">N6</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('r_tsjk1')" id="r_tsjk1_order">
									<div class="font-size-12 line-height-18">特殊监控1</div>
									<div class="font-size-9 line-height-18">TS1</div>
								</th>
								
								<th class="colwidth-7 sorting" onclick="orderBy('r_tsjk2')" id="r_tsjk2_order">
									<div class="font-size-12 line-height-18">特殊监控2</div>
									<div class="font-size-9 line-height-18">TS2</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('gs_djjh')" id="gs_djjh_order">
									<div class="font-size-12 line-height-18">定检计划公式</div>
									<div class="font-size-9 line-height-18">Calculation Methods</div>
								</th>
								<th class="colwidth-5 sorting" onclick="orderBy('is_tsqk')" id="is_tsqk_order">
									<div class="font-size-12 line-height-18">特殊情况</div>
									<div class="font-size-9 line-height-18">Special</div>
								</th>
								<th class="colwidth-5" onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('cjrid')" id="cjrid_order">
								  <div class="important">
									<div class="font-size-12 line-height-18">创建人</div>
									<div class="font-size-9 line-height-18">Creator</div>
								   </div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('cjsj')" id="cjsj_order">
									<div class="font-size-12 line-height-18">创建时间</div>
									<div class="font-size-9 line-height-18">Create Time</div>
								</th>
								<!-- <th class="colwidth-13 sorting" onclick="orderBy('bmid')" id="bmid_order">
									<div class="font-size-12 line-height-18">部门名称</div>
									<div class="font-size-9 line-height-18">Dept Name</div>
								</th> -->
								<th class="colwidth-15 sorting" onclick="orderBy('dprtCode')" id="dprtCode_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						 <tbody id="list"></tbody>
					</table>
				</div>
			
				<div class="col-xs-12 text-center page-height" id="pagination">
					<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" >
					</ul>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	  <%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/planemodellist.js"></script>
</body>
</html>
