<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>部件履历</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	//var planeData = $.parseJSON('${planeData}');//飞机注册号
</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="userId" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

		<!-- BEGIN CONTENT -->
		<div class="page-content ">
			 
			<div class="panel panel-primary" >
				<div class="panel-heading  "> 
				<div id="NavigationBar"></div>
				</div>
				<div class="panel-body col-xs-12 padding-bottom-0">
               <!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
							 <span
								class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12">飞机注册号</div>
								<div class="font-size-9">A/C REG</div></span>
							<div
								class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="fjzch" class="form-control ">
								   <option value="">全部All</option>
								   <c:forEach items="${planes}" var="item">
								    <option value="${item.fjzch}" >${item.fjzch}</option>
								   </c:forEach>
								</select> 
							</div>
					</div>	
						
					<div class=" pull-left padding-left-5 padding-right-0 row-height" style="width: 250px;">
						<input placeholder="件号/序列号/中英文/厂家件号/飞机注册号" id="keyword" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="search();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                         <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="more();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!-- 搜索框end -->


				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10 margin-top-10 display-none border-cccccc search-height" id="divSearch">
					<div class="col-xs-12 col-lg-12 col-sm-12 padding-left-0 padding-right-0">
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">状态</div>
							<div class="font-size-9">State</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zt" class="form-control ">
							   <option value="">全部All</option>
							   <c:forEach items="${partsStatusEnum}" var="item">
							   <option value="${item.id }">${item.name }</option>
							   </c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  pull-left padding-left-0 padding-right-0">
						  <div class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						  <div class="font-size-12">位置</div>
							<div class="font-size-9">Location</div>
						  </div>
						<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="wz" class="form-control ">
							   <option value="">全部All</option>
							   <c:forEach items="${partsPositionEnum}" var="item">
							   <option value="${item.id }">${item.name }</option>
							   </c:forEach>
							</select> 
						</div>
					</div>
					
					<div class=" col-xs-12 col-sm-6 col-lg-3  pull-left padding-left-0 padding-right-0 margin-bottom-10" >
				        <span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
				        	<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class=" col-xs-8 col-sm-8 padding-left-5 padding-right-0">
								<select id="dprtcode" class="form-control " name="dprtcode"
								onchange="changeOrganization()">
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
						<input type="hidden" id="user_jgdm" value="${user.jgdm}"/>
					</div>
					<div
						class="col-lg-2 text-right  padding-right-0 pull-right margin-bottom-10">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1  pull-right"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
					
				</div>
				</div>

				<div class="clearfix"></div>

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="margin-top: 10px; overflow-x: scroll;">
					<table id="componenthistory"
						class="table table-thin table-bordered table-striped table-hover text-center  tableRe table-set" style="min-width: 1845px;">
						<thead>
							<tr class="nav-tabs">
								<th colspan="7" >
									<div class="font-size-12 line-height-18">部件信息</div>
									<div class="font-size-9 line-height-18">Component info</div>
								</th>
								<th colspan="4">
									<div class="font-size-12 line-height-18">当前在机信息</div>
									<div class="font-size-9 line-height-18">Current info</div>
								</th>
								<th rowspan="1"><div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>	
							</tr>
							
							<tr>
								<th class="sorting " onclick="orderBy('jh')"
									id="jh_order">
									<div class="important">
									<div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-18">P/N</div>
									</div>
								</th>
									
								<th class="sorting colwidth-20" onclick="orderBy('xlh')"
									id="xlh_order">
									<div class="important">
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">S/N</div>
									</div>
								</th>
									
								
								<th class="sorting colwidth-25" onclick="orderBy('ywms')"
									id="ywms_order">
									<div class="important">
									<div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-18">F.Name</div>
									</div>
								</th>
								
								<th class="sorting colwidth-20" onclick="orderBy('zwms')"
									id="zwms_order">
									<div class="important">
									<div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-18">CH.Name</div>
									</div>
								</th>
								
								<th class="sorting colwidth-15" onclick="orderBy('cjjh')"
									id="cjjh_order">
									<div class="important">
									<div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-18">MP/N</div>
									</div>
								</th>
									
								<th class="colwidth-10">
									<div class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div>
								</th>
									
								<th class="sorting colwidth-10" onclick="orderBy('fjzch')"
									id="fjzch_order">
									<div class="important">
									<div class="font-size-12 line-height-18">飞机注册号</div>
									<div class="font-size-9 line-height-18">A/C REG</div>
									</div>
								</th>
									
								<th class="colwidth-20">
									<div class="font-size-12 line-height-18">上级部件</div>
									<div class="font-size-9 line-height-18">Parent Parts</div>
								</th>
								<th class="colwidth-7">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</th>	
								<th class="colwidth-5">
									<div class="font-size-12 line-height-18">层级</div>
									<div class="font-size-9 line-height-18">Level</div>
								</th>
								<th class="colwidth-7">
									<div class="font-size-12 line-height-18">位置</div>
									<div class="font-size-9 line-height-18">Location</div>
								</th>
								<th rowspan="1"><div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>	
							</tr>
						</thead>
						<tbody id="list">

						</tbody>

					</table>
				</div>
				 
				<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
				</div>
				<div class="clearfix"></div>
			</div>
					<div class="clearfix"></div>
			</div>
	
	<!-- END CONTENT -->
 
	<!-----alert提示框Start--------->
	<div class="modal fade" id="alertModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body" id="alertModalText"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<!-----alert提示框End--------->
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/componenthistory/componenthistory_manage.js"></script> 
	 
	 
</body>
</html>