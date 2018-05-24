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
<input type="hidden" value="${borrowMain.jddxms}" id="jddxms" >
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
								<input class="date-picker form-control" id="sqsj" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${borrowMain.sqsj}"/>" data-date-format="yyyy-MM-dd HH:mm:ss"   disabled="disabled"/>
							</div>
						</div>
					
					
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>借调对象类型</div>
								<div class="font-size-9 line-height-18">S/O Type</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class='form-control' id='jddxlx'
										name="jddxlx" onchange="changeSelected(this)"  disabled="disabled">
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
						
							<select class='form-control' id='jddx' name="jddx"   disabled="disabled">
								<option value="">请选择 Chioce</option>
							</select>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 margin-bottom-0 ">
						<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</label>
							<input type="hidden" value="${erayFns:escapeStr(borrowMain.fjzch)}" id="fjzchs">
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
								<select class='form-control' id='fjzch' name="fjzch"  disabled="disabled" >
							
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
								<textarea  disabled="disabled" class="form-control" id="bz" name="bz" maxlength="300" >${erayFns:escapeStr(borrowMain.bz)}</textarea>
							</div>
						</div>
					</div>
				</form>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
					<div class="clearfix"></div>
					
					<div class="panel-heading padding-left-16 padding-top-3  col-xs-12  " style="border-bottom: 1px solid #ccc;">
						<div class=" pull-left margin-right-10" >
							<div class="font-size-16 line-height-18 ">借入航材</div>
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
               		<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:back()">
               			<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
					&nbsp;&nbsp;
            	</div>
			</div>
		</div>

		<!-- 基本信息 End -->
	
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/borrow/borrow_view.js"></script>
</body>
</html>