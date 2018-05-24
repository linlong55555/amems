<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<link >

</head>
<body class="page-header-fixed">
	<input type="hidden" id="fazhi" value="${threshold}" />
	<input type="hidden" id="adjustHeight" value="70" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="clearfix"></div>
			<div class="panel-body padding-bottom-0">
				<!-----标签导航start---->
				<ul class="nav nav-tabs" role="tablist" id="tablist">
				  <li role="presentation"   class="active"><a href="#requisition" data-toggle="tab">工具/设备 Tools& Equipment</a></li>
				  <li role="presentation"   ><a id="loadhis" onclick="loadHistory();" href="#requisitionHistory" data-toggle="tab">借用记录 Borrow Record</a></li>
				</ul>
				<!-----标签内容start---->
		      	<div class="tab-content">
					<div class="tab-pane fade in active"  id="requisition">
						<div class="margin-bottom-0 col-xs-12 padding-left-0 padding-right-0">
							<div class="col-xs-3 col-md-3 padding-left-0 ">
								 <div class="pull-left padding-left-0 padding-right-0" >  
			                   		<label style=' font-weight:normal'><input type="radio" style=" vertical-align: middle; margin-top: -3px;"  name="brrowStatus" value="" checked=true onclick="search()"/>
			                   		全部</label>
			                   		&nbsp;
			                   		&nbsp;
			                   		<label style=' font-weight:normal'><input type="radio" style=" vertical-align: middle; margin-top: -3px;"  name="brrowStatus" value="1"  onclick="search()" />
			                   		申请</label>
			                   		&nbsp;
			                   		&nbsp;
			                   		<label style=' font-weight:normal'><input type="radio" style=" vertical-align: middle; margin-top: -3px;"  name="brrowStatus" value="2" onclick="search()" />
			                   		借用</label>
			                   		&nbsp;
			                   		&nbsp;
			                   		<label style=' font-weight:normal'><input type="radio" style=" vertical-align: middle; margin-top: -3px;"  name="brrowStatus" value="3" onclick="search()" />
			                   		空闲</label>
			                   		&nbsp;
			                   		&nbsp;
			                    </div>
							</div>
							
							<div class="alert alert-success col-xs-2 col-md-3 text-center" style="padding-top: 5px;padding-bottom: 5px; margin-bottom:0px;vertical-align: middle;display:none;" role="alert" id="requisiton_message"></div>
							<div class="pull-right padding-left-0 padding-right-0">
							
							
							     <div class=" pull-left padding-left-5 padding-right-0" style="width: 200px;">
							        <span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							        	<div class="font-size-12">组织机构</div>
										<div class="font-size-9">Organization</div>
									</span>
									<div class=" col-xs-8 col-sm-8 padding-left-5 padding-right-0">
											<select id="dprtcode" class="form-control " name="dprtcode">
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
								<div class="pull-left padding-left-5 padding-right-0" style="width:250px;">
									<input type="text" class="form-control row-height" id="keyword_search" placeholder="部件号/中英文/序列号"/>
								</div>
			                    <div class="pull-right padding-left-5 padding-right-0">   
			                   		<button id="requisitionSearch" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="search();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
									</button>
			                    </div>    
							</div>
							 
						</div>
						<div  class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h"  style="overflow-x:scroll;">
							<table id="toolUse" class="table table-thin table-bordered text-center table-set" style="min-width:800px">
								<thead>
									<tr>
										<th  class="colwidth-7" >
											<div class="font-size-12 line-height-18">操作</div>
											<div class="font-size-9 line-height-18">Operation</div>
										</th>
										<th   class="sorting colwidth-15" onclick="orderBy('bjh',this)" name="column_bjh" >
											<div class="important">
												<div class="font-size-12 line-height-18">件号</div>
												<div class="font-size-9 line-height-18">P/N</div>
											</div>
										</th>
										<th   class="sorting colwidth-25" onclick="orderBy('D008.YWMS',this)" name="column_D008.YWMS" >
											<div class="important">
												<div class="font-size-12 line-height-18">英文名称</div>
												<div class="font-size-9 line-height-18">F.Name</div>
											</div>
											
										</th>
										<th   class="sorting colwidth-20"  onclick="orderBy('D008.ZWMS',this)" name="column_D008.ZWMS" >
											<div class="important">
												<div class="font-size-12 line-height-18">中文名称</div>
											</div>
											<div class="font-size-9 line-height-18">CH.Name</div>
										</th>
										<th   class="sorting colwidth-20" onclick="orderBy('sn',this)" name="column_sn" >
											<div class="important">
											<div class="font-size-12 line-height-18">序列号</div></div>
											<div class="font-size-9 line-height-18">S/N</div>
										</th>
										
										<th class="colwidth-10"  >
											<div class="font-size-12 line-height-18">型号</div>
											<div class="font-size-9 line-height-18">Model</div>
										</th>
										
										<th  class="colwidth-10"  >
										<div class="font-size-12 line-height-18">规格</div>
										<div class="font-size-9 line-height-18">Specifications</div>
										</th>
										
										<th  class="colwidth-7"  class="sorting" onclick="orderBy('kcsl',this)" name="column_kcsl" >
										<div class="font-size-12 line-height-18">数量</div>
										<div class="font-size-9 line-height-18">Num</div>
										</th>
										<th   class="sorting colwidth-13" onclick="orderBy('hclx',this)" name="column_hclx" >
										<div class="font-size-12 line-height-18">航材类型</div>
										<div class="font-size-9 line-height-18">Airmaterial Type</div>
										</th>
										
										<th    class="sorting colwidth-7" onclick="orderBy('BW002.ZT', this)" name="column_BW002.ZT" >
										<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">Status</div>
										</th>
										<th  class="colwidth-20" >
										<div class="font-size-12 line-height-18">位置</div>
										<div class="font-size-9 line-height-18">Position</div>
										</th>
										<th  class="colwidth-15" >
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
										</th>
									 
									</tr>
								</thead>
								<tbody id="list">
								</tbody>
							</table>
						</div>
						<div class=" col-xs-12 text-center page-height padding-left-0 padding-right-0" id="pagination">
						</div>
					</div>
			        <div class="tab-pane fade" id="requisitionHistory">
		          		<%@ include file="/WEB-INF/views/outfield/toolsuse/toolsuse_history.jsp"%>
			        </div>
				</div>
				<%@ include file="/WEB-INF/views/outfield/toolsuse/toolsuse_apply.jsp"%>
					<%@ include file="/WEB-INF/views/outfield/toolsuse/toolsuse_confirm_fast.jsp"%>
					<%@ include file="/WEB-INF/views/outfield/toolsuse/toolsuse_confirm.jsp"%>
					<%@ include file="/WEB-INF/views/outfield/toolsuse/toolsuse_revert.jsp"%>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/thjw/outfield/toolsuse/toolsuse_main.js"></script>
	 
</body>
</html>