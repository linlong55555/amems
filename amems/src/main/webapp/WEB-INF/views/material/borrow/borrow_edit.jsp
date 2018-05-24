<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var pageParam = '${param.pageParam}';
</script>
</head>
<body>
<input type="hidden" value="${user.jgdm}" id="dprtcode">
<input type="hidden" value="${erayFns:escapeStr(borrowMain.jddxms)}" id="jddxms" >
<input type="hidden" value="${borrowMain.id}" id="id" >
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
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">申请单号</div>
								<div class="font-size-9 line-height-18">Apply No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control" type="text" value="${borrowMain.sqdh}" disabled="disabled"/>
							</div>
						</div>
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">申请人</div>
								<div class="font-size-9 line-height-18">Applicant</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control" type="text"  value="${erayFns:escapeStr(user.username)} ${erayFns:escapeStr(user.realname)}" readonly />
							<input class="form-control" type="hidden"  id="sqrid" value="${user.id}" readonly />
							<input class="form-control" type="hidden"  id="sqdwid" value="${user.bmdm}" readonly />
							
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">申请日期</div>
								<div class="font-size-9 line-height-18">Application Date</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="date-picker form-control" id="sqsj" value="${borrowMain.sqsj}" name="sqsj" data-date-format="yyyy-mm-dd" type="text" readonly/>
							</div>
						</div>
					
					
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>借调对象类型</div>
								<div class="font-size-9 line-height-18">S/O Type</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class='form-control' id='jddxlx'
										name="jddxlx" onchange="changeSelected(this)" disabled="disabled">
											<option value="">请选择 Choice</option>
												<c:forEach items="${secondmenttype}" var="plans">
													<option value="${plans.id}" <c:if test="${borrowMain.jddxlx == plans.id }">selected=true</c:if>>${erayFns:escapeStr(plans.name)}</option>
												</c:forEach>
									</select>
							</div>
						</div>
							
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>借调对象</div>
								<div class="font-size-9 line-height-18">Seconded Obj</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						
							<select class='form-control' id='jddx' name="jddx" onchange="changesel()" disabled="disabled">
								<option value="">请选择 Choice</option>
							</select>
							</div>
						</div>
								<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-0 ">
						<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</label>
						<input type="hidden" value="${erayFns:escapeStr(borrowMain.fjzch)}" id="fjzchs">
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
								<select class='form-control' id='fjzch' name="fjzch"  >
								
								</select>
						</div>
					</div>
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单人</div>
								<div class="font-size-9 line-height-18">Creator</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control" type="text"  value="${erayFns:escapeStr(borrowMain.zdrs)}" readonly />
							</div>
						</div>
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单时间</div>
								<div class="font-size-9 line-height-18">Create Time</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control" type="text" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${borrowMain.zdsj}"/>" data-date-format="yyyy-MM-dd HH:mm:ss"   readonly />
							</div>
						</div>
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">状态</div>
								<div class="font-size-9 line-height-18">State</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input class="form-control"  name="state" value="${borrowMain.zt}" id="state" disabled="disabled"/>
							</div>
						</div>
					
					
					 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0  form-group">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="bz" name="bz" maxlength="300" placeholder="长度最大为300">${erayFns:escapeStr(borrowMain.bz)}</textarea>
							</div>
						</div>
					</div>
				</form>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
					<div class="clearfix"></div>
					
					<div class="panel-heading padding-left-16 padding-top-3  col-xs-12  " style="border-bottom: 1px solid #ccc;">
						<button id="btnGoAdd"  class="btn btn-xs btn-primary margin-left-10 float_an2" onclick="javascript:openMaterialWinAdd()">
								<i class=" cursor-pointer">选择航材</i>
							</button>
				   		    <button  style="display: none;" id="btnGoAdd1"  class="btn btn-xs btn-primary margin-left-10 float_an3" onclick="javascript:openotherStockAdd()">
								<i class=" cursor-pointer">选择库存</i>
							</button>
						<div class=" pull-left margin-right-10" >
							<div class="font-size-16 line-height-18">借入航材</div>
							<div class="font-size-9 ">Borrowed Aircraft</div>
						</div>	
					</div>
					
	            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">

						<!-- start:table -->
						<div class="margin-top-10 ">
						<div class="overflow-auto">
							<table class="table table-bordered table-striped table-hover table-set" style="min-width:1500px">
								<thead>
							   		<tr>
										<th class="colwidth-5" style="vertical-align: middle;">
											<div class="font-size-12 notwrap">操作</div>
											<div class="font-size-9 notwrap">Operation</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 notwrap">序号</div>
											<div class="font-size-9 notwrap">NO.</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 notwrap">件号</div>
											<div class="font-size-9 notwrap">P/N</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12 notwrap">英文名称</div>
											<div class="font-size-9 notwrap">F.Name</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12 notwrap">中文名称</div>
											<div class="font-size-9 notwrap">CH.Name</div>
										</th>
										
										
										<th class="colwidth-8">
											<div class="font-size-12 notwrap">厂家件号</div>
											<div class="font-size-9 notwrap">MP/N</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 notwrap">航材类型</div>
											<div class="font-size-9 notwrap">Airmaterial type</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 notwrap">管理级别</div>
											<div class="font-size-9 notwrap">Management Level</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 notwrap">序列号</div>
											<div class="font-size-9 notwrap">S/N</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 notwrap">批次号</div>
											<div class="font-size-9 notwrap">P/N</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 notwrap">借入数量</div>
											<div class="font-size-9 notwrap">Borrowed Num</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 notwrap">单位</div>
											<div class="font-size-9 notwrap">Unit</div>
										</th >
										<th class="colwidth-15">
											<div class="font-size-12 notwrap">用途</div>
											<div class="font-size-9 notwrap">Use</div>
										</th>
							 		 </tr>
								</thead>
								<tbody id="reserveTable">
								
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
					
					<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:submit()">
                    	<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
					</button>
				
               		<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:goreturn()">
               			<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
            	</div>
			</div>
		</div>

		<!-- 基本信息 End -->
    
    <!-------其他飞行队对话框 Start-------->
	<div class="modal fade" id="alertModalotherStockAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog  modal-lg" >
			<div class="modal-content">	
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18" id="otherStockModel"></div>
							<div class="font-size-9 ">Aircraft List</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
			            	<div class="col-lg-12 col-xs-12">
							    <div class=" pull-right padding-left-0 padding-right-0 margin-top-10">
							
								<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
									<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">航材类型</div>
										<div class="font-size-9">Airmaterial type</div>
									</span>
									<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-10">
											<select class='form-control' id='other_hclx_search' onchange="searchotherStockAdd()">
												<option value="" >显示全部</option>
												<c:forEach items="${materialTypeEnum}" var="item">
													<option value="${item.id}" <c:if test="${100 == item.id }">selected=true</c:if> >${erayFns:escapeStr(item.name)}</option>
												</c:forEach>
										    </select>
									</div>
								</div>	
											<!-- 搜索框start -->
								<div class=" pull-right padding-left-0 padding-right-0">
									<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" placeholder='件号/中英文/厂家件号' id="keyword_otherStock_search" class="form-control ">
									</div>
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button name="keyCodeSearch"
										 type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchotherStockAdd()">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div> 
								</div>
								<!-- 搜索框end -->		
				         		</div>
				                	<div class="clearfix"></div>
							<!-- start:table -->
							<div class="margin-top-10 ">
								<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow:auto;">
									<table id="otherStockAddTable" class="table table-bordered table-striped table-hover table-set"  style="min-width:1500px">
										<thead>
									   		<tr>
												<th class="colwidth-5 fixed-column" style="vertical-align: middle;">
												<a href="javascript:checkAll();" class="pull-left margin-left-10 margin-bottom-10" id='CancelAll' ><img src="${ctx}/static/assets/img/d1.png" alt="全选" title="全选" /></a>
													<a href="javascript:notCheckAll();" class="pull-left margin-left-10 margin-bottom-10" id='CancelAll'><img src="${ctx}/static/assets/img/d2.png" alt="不全选" title="不全选" /></a> 
												</th>
												<th class="colwidth-5">
													<div class="font-size-12 notwrap">序号</div>
													<div class="font-size-9 notwrap">P/N</div>
												</th>
												<th class="colwidth-8">	<div class="important">
													<div class="font-size-12 notwrap">件号</div>
													<div class="font-size-9 notwrap">P/N</div></div>
												</th>
												<th class="colwidth-20">	<div class="important">
													<div class="font-size-12 notwrap">中文名称</div>
													<div class="font-size-9 notwrap">CH.Name</div></div>
												</th>
												<th class="colwidth-20">	<div class="important">
													<div class="font-size-12 notwrap">英文名称</div>
													<div class="font-size-9 notwrap">F.Name</div></div>
												</th>
												<th class="colwidth-8">	<div class="important">
													<div class="font-size-12 notwrap">厂家件号</div>
													<div class="font-size-9 notwrap">MP/N</div></div>
												</th>
												<th class="colwidth-8">
													<div class="font-size-12 notwrap">航材类型</div>
													<div class="font-size-9 notwrap">Airmaterial type</div>
												</th>
												<th class="colwidth-8">
													<div class="font-size-12 notwrap">航材管理级别</div>
													<div class="font-size-9 notwrap">Airmaterial Level</div>
												</th>
												<th class="colwidth-8">
													<div class="font-size-12 notwrap">序列号/批次号</div>
													<div class="font-size-9 notwrap">Aircraft Type</div>
												</th>
												<th class="colwidth-8">
													<div class="font-size-12 notwrap">货架寿命</div>
													<div class="font-size-9 notwrap">Stock Num</div>
												</th>
												<th class="colwidth-8">
													<div class="font-size-12 notwrap">单位</div>
													<div class="font-size-9 notwrap">Unit</div>
												</th>
									 		 </tr>
										</thead>
										<tbody id="otherStockAdd">
										
										</tbody>
									</table>
									</div>
									<div class="col-xs-12 text-center">
										<ul class="pagination" style="margin-top: 0px; margin-bottom: 0px;" id="otherStockAddPagination">
										</ul>
									</div>
								</div>
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
		                			<button type="button" onclick="setData()"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									data-dismiss="modal">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
				                </div>
					     		<div class="clearfix"></div>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-------其它飞行队对话框 End-------->     

	<%@ include file="/WEB-INF/views/alert.jsp"%>
	
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/borrow/borrow_edit.js"></script>
<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%><!-------航材对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/common/openotherStockAdd.js"></script>
</body>
</html>