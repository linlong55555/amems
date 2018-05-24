<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看仓库信息</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">

		<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<div class="page-content">
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0" >
		 		<div class="col-xs-12 " style='padding:0px;'>
					<div class="input-group-border">
					
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">仓库编号</div>
								<div class="font-size-9">Warehouse No.</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control " id="ckh" name="ckh" value="${erayFns:escapeStr(store.ckh)}" readonly />
								<input type="hidden" id="id" name="id" value="${store.id}"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">仓库名称</div>
								<div class="font-size-9">Warehouse Name</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" id="ckmc" name="ckmc" value="${erayFns:escapeStr(store.ckmc)}" class="form-control " />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">仓库类别</div>
								<div class="font-size-9">Warehouse Type</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							
										<%
							     if(request.getAttribute("isStoreData").toString().equals("true")){//仓库数据管理%>	    	 
							    		<select class='form-control' id='cklb' name="cklb" >
										<c:forEach items="${storeType2Enum}" var="item">
										  <option value="${item.id}" <c:if test="${store.cklb  == item.id }">selected="true"</c:if> >${item.name}</option>
										</c:forEach>
								    </select>							    	 
							     <%}else if(request.getAttribute("isStoreData").toString().equals("false")){//仓库主数据%>			    	 
							    		<select class='form-control' id='cklb' name="cklb" >
										<c:forEach items="${storeTypeEnum}" var="item">
										  <option value="${item.id}" <c:if test="${store.cklb  == item.id }">selected="true"</c:if> >${item.name}</option>
										</c:forEach>
								    </select>
							     <%}%>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">库管员</div>
								<div class="font-size-9">Manager</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="kgyn" name="kgyn" value="${erayFns:escapeStr(store.displayName)}" readonly/>
								<input type="hidden" class="form-control" id="kgyid" value="${store.kgyid}" maxlength="33" readonly/>
							</div>
						</div>
					
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">仓库地址</div>
								<div class="font-size-9">Address</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="ckdz" name="ckdz" value="${erayFns:escapeStr(store.ckdz)}" />
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">基地</div>
								<div class="font-size-9">Base</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class="form-control " id="jd" name="jd" disabled = "disabled">
								</select>
								<input type="hidden" value="${erayFns:escapeStr(store.jd)}" id="jdid">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">维护人</div>
								<div class="font-size-9">Maintainer</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" id="whr" class="form-control" value="${erayFns:escapeStr(store.zdr.username)}&nbsp;${erayFns:escapeStr(store.zdr.realname)}" readonly/>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">维护时间</div>
								<div class="font-size-9">Time</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="whsj"  value="<fmt:formatDate value='${store.cjsj}' pattern='yyyy-MM-dd HH:mm:ss' />" readonly type="text" />
							</div>
						</div>
				
						<div class="clearfix"></div>
					
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">备注</div>
								<div class="font-size-9">Note</div>
							</span>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="bz" name="bz" class="form-control" maxlength="300" style="height:55px">${erayFns:escapeStr(store.bz)}</textarea>
							</div>
						</div>	
						
	            	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-8">
						<span id="left_column" class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
							<div class="font-size-12 margin-topnew-2">库位</div>
							<div class="font-size-9">Location</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0" style="overflow-x: auto;">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:600px">
									<thead>
								   		<tr>
											<th class="colwidth-5">
												<div class="font-size-12 notwrap">序号</div>
												<div class="font-size-9 notwrap">No.</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">库位号</div>
												<div class="font-size-9 notwrap">Storage location</div>
											</th>
											<th class="colwidth-40">
												<div class="font-size-12 notwrap">备注</div>
												<div class="font-size-9 notwrap">Remark</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="rotatable">
									
									</tbody>
								</table>
							</div>
						</div>
						<div class="clearfix"></div>	
					</div>
					<div class="clearfix"></div>   

				<!-- <div class="text-right">
               		<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back();">
               			<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
            	</div> -->
			</div>
		</div>

		<!-- 基本信息 End -->
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/config/store_view.js"></script>
</body>
</html>