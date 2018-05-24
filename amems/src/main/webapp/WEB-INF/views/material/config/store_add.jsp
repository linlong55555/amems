<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增仓库信息</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body>

	<input type="hidden" class="form-control " id="state" value="1"/>
	<input type="hidden" id="dprtId" value="${user.jgdm}" />

	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>	
			<div class="panel-body">
				<form id="form">
					<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class="font-size-16 line-height-18">基本信息</div>
						<div class="font-size-9 ">Basic Info</div>
					</div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>仓库编号</div>
								<div class="font-size-9 line-height-18">Warehouse No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control " id="ckh" name="ckh" maxlength="50" />
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>仓库名称</div>
								<div class="font-size-9 line-height-18">Warehouse Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="ckmc" name="ckmc" class="form-control " maxlength="100" />
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">仓库类别</div>
								<div class="font-size-9 line-height-18">Warehouse Type</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							
							<%
							     if(request.getAttribute("isStoreData").toString().equals("true")){//仓库数据管理%>	    	 
							    		<select class='form-control' id='cklb' name="cklb" >
										<c:forEach items="${storeType2Enum}" var="item">
										  <option value="${item.id}" <c:if test="${14 == item.id }">selected="selected"</c:if> >${item.name}</option>
										</c:forEach>
								    </select>							    	 
							     <%}else if(request.getAttribute("isStoreData").toString().equals("false")){//仓库主数据%>			    	 
							    		<select class='form-control' id='cklb' name="cklb" >
										<c:forEach items="${storeTypeEnum}" var="item">
										  <option  value="${item.id}" <c:if test="${6 == item.id }">selected="selected"</c:if> >${item.name}</option>
										</c:forEach>
								    </select>
							     <%}%>																				    
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">库管员</div>
								<div class="font-size-9 line-height-18">Manager</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<input class="form-control" id="kgyn" name="kgyn" readonly/>
								<input type="hidden" class="form-control" id="kgyid" name="kgyid"/>
								<span class="input-group-btn">
									<button type="button" class="btn btn-primary form-control" data-toggle="modal" onclick="openUserList();">
									<i class="icon-search "></i>
									</button>
								</span>
							</div>
						</div>
					
						<div class="clearfix"></div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">仓库地址</div>
								<div class="font-size-9 line-height-18">Address</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control" id="ckdz" name="ckdz" maxlength="100"/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">基地</div>
								<div class="font-size-9 line-height-18">Base</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								
								<select class="form-control " id="jd" name="jd">
									<option value="">please choose</option>
									<c:forEach items="${baseList}" var="baseList">
										<option value="${baseList.id}">${erayFns:escapeStr(baseList.jdms)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="clearfix"></div>
					
					 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="bz" name="bz" maxlength="300" ></textarea>
							</div>
						</div>
					</div>
				
					<div class="clearfix"></div>
				</form>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
					<div class="clearfix"></div>
					
					<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12" style="border-bottom: 1px solid #ccc;">
						<div class=" pull-left margin-right-10" >
							<div class="font-size-16 line-height-18">库位</div>
						<div class="font-size-9 ">Location</div>
						</div>	
					</div>
					
	            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">

						<!-- start:table -->
						<div class="margin-top-10 ">
							<div class="overflow-auto">
								<table class="table table-bordered table-striped table-hover table-set table-thin">
									<thead>
								   		<tr>
											<th class="colwidth-5" >
												<button class="line6 " onclick="addRotatableCol()">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
												</button>
											</th>
											<th class="colwidth-3" >
												<div class="font-size-12 notwrap">序号</div>
												<div class="font-size-9 notwrap">No.</div>
											</th>
											<th class="colwidth-20" >
												<div class="font-size-12 notwrap">库位号</div>
												<div class="font-size-9 notwrap">Storage location</div>
											</th>
											<th>
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
						<!-- end:table -->
			     		<div class="clearfix"></div>
					 </div> 
				</div>

				<div class="clearfix"></div>
				<div class="text-right">
                    <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:save()">
                    	<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
					</button>
               		<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()">
               			<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
            	</div>
			</div>
		</div>

		<!-- 基本信息 End -->

</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/config/store_add.js"></script>
<%@ include file="/WEB-INF/views/open_win/user.jsp"%><!-------用户对话框 -------->
</body>
</html>