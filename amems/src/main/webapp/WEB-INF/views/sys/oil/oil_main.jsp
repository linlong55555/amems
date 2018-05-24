<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>油品规格</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">

<input type="hidden" id="zzjgid" name="zzjgid" value=${user.jgdm} />
<input type="hidden" id="oldypgg" name="oldypgg" value="" />
<input type="hidden" id="oilId" name="oilId"  />

		<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
<script type="text/javascript">
$(document).ready(function(){
//导航
Navigation(menuCode);
});
</script>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
		<!-- BEGIN CONTENT -->
		<div class="page-content ">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body padding-bottom-0">
				<div  class='searchContent margin-top-0 row-height'>
				<a href="#" onclick="add()" data-toggle="modal"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left form-group checkPermission"
					 permissioncode="basedata:oil:add:01"
					><div class="font-size-12">新增</div>
					<div class="font-size-9">Add</div>
				</a>
					
			<div class=" pull-right padding-left-0 padding-right-0 form-group">
				
				<div class="pull-left ">
						<span class="pull-left  text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class=" padding-left-8 padding-right-0 pull-left" style="width: 180px; margin-right:5px;">
							<select id="dprtcode" class="form-control " name="dprtcode" onchange="searchRevision()">
									<c:if test="${dprtcode==user.orgcode}">
											<option value="-1">公共</option>
									</c:if>
									<c:forEach items="${accessDepartments}" var="type">
										<option value="${type.id}" >${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
							</select>
						</div>
				</div>
				
				
				
					<div class=" pull-left padding-left-0 padding-right-0 row-height" style="width: 250px;">
						<input type="text" placeholder='油品规格/油品密度/描述/维护人' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                    </div> 
				</div>
					<div class="clearfix"></div>
                  </div>
					<div class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;width: 100%;">
						<table  id="quality_check_list_table" class="table table-thin table-bordered table-set">
							<thead>
							<tr>
							<th class=" colwidth-5"><div class="font-size-12 line-height-18" >操作</div><div class="font-size-9 line-height-18" >Operation</div></th>
							<th class="sorting colwidth-10" onclick="orderBy('ypgg')" id="ypgg_order"><div class="important"><div class="font-size-12 line-height-18" >油品规格</div><div class="font-size-9 line-height-18" >Oils</div></div></th>
							<th class="sorting colwidth-10" onclick="orderBy('ypmd')" id="ypmd_order"><div class="important"><div class="font-size-12 line-height-18" >油品密度(g/cm³)</div><div class="font-size-9 line-height-18" >Density</div></div></th>
							<th class="sorting colwidth-7" onclick="orderBy('ms')" id="ms_order"><div class="important"><div class="font-size-12 line-height-18" >描述</div><div class="font-size-9 line-height-18" >Desc</div></div></th>
							<th class="sorting colwidth-10" onclick="orderBy('whrid')" id="whrid_order"><div class="important"><div class="font-size-12 line-height-18" >维护人</div><div class="font-size-9 line-height-18" >Record keeper</div></div></th>
							<th class="sorting colwidth-10" onclick="orderBy('whbmid')" id="whbmid_order"><div class="font-size-12 line-height-18" >维护部门</div><div class="font-size-9 line-height-18" >Record Dept</div></th>
							<th class="sorting colwidth-10" onclick="orderBy('whsj')" id="whsj_order"><div class="font-size-12 line-height-18" >维护时间</div><div class="font-size-9 line-height-18" >Record time</div></th>
							<th class="sorting colwidth-10" onclick="orderBy('dprtcode')" id="dprtcode_order"><div class="font-size-12 line-height-18" >组织机构</div><div class="font-size-9 line-height-18" >Organization</div></th>
							</tr> 
			         		 </thead>
							<tbody id="OilList">
							</tbody>
							
						</table>
					</div>
					<!--  <div class="col-xs-12 text-center padding-right-0 page-height">
						<div class="col-xs-12 text-center padding-right-0 " id="pagination">
					</div>
				</div> -->
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
					</div>
					
					<div class="clearfix"></div>
				</div>
			</div>

	</div>
	<%@ include file="/WEB-INF/views/alert.jsp"%>
	</div>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
	</div>
	
		<div class="modal fade" id="alertModalAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
							<div class="font-size-16 line-height-18" id="titleName"></div>
							<div class="font-size-9 line-height-18"  id="titleEname"></div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12">
			            	<form id="form" >
			            	<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 margin-top-10 padding-right-0 form-group">
								<label class="pull-left col-lg-2 col-sm-6 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>油品规格</div>
									<div class="font-size-9">Oils</div>
									</label>
								<div class="col-lg-10 col-sm-6 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="ypgg" name="ypgg" maxlength="15" />
								</div>
							</div> 
							
							<div class="clearfix"></div>	
							<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-2 col-sm-6 col-xs-4 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12"><span style="color: red">*</span>油品密度</div>
									<div class="font-size-9 line-height-18">Density</div>
								</label>
								<div class=" col-lg-10 col-sm-6 col-xs-8 padding-left-8 padding-right-0 ">
									<div class="input-group">
										<input type="text" maxlength="11" class="form-control " id="ypmd" name="ypmd" style="border-top-right-radius: 0px; border-bottom-right-radius: 0px;"/>
										<label class="input-group-addon" style="border-top-left-radius: 0px; border-bottom-left-radius: 0px;">g/cm³</label> 
									</div>
								</div>
							</div>
							
			            	<div class="clearfix"></div>
			            	<div class="col-xs-12 col-sm-12  padding-left-0  padding-right-0 margin-bottom-10 form-group">
								<label class="pull-left col-lg-2 col-sm-6 col-xs-4 text-right padding-left-0 padding-right-0" >
								<div class="font-size-12">描述</div>
									<div class="font-size-9">Desc</div>
									</label>
								<div class="col-lg-10 col-sm-6 col-xs-8 padding-left-8 padding-right-0">
									<textarea class="form-control" id="ms" name="ms" placeholder='长度最大为150'   maxlength="150"></textarea>
								</div>
							</div> 
			            	</form>
							    
					     		<div class="clearfix"></div>
					     		<div class="text-center margin-top-10 padding-buttom-10 pull-right">
			                     <button onclick="saveUpdate()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" >
			                     		<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div></button>&nbsp;&nbsp;&nbsp;&nbsp;
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
	
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript"
		src="${ctx }/static/js/thjw/sys/oil_main.js"></script>
</body>
</html>