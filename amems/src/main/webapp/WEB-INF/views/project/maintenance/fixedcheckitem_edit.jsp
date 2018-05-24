<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script>
	var pageParam = '${param.pageParam}';
	</script>
	<style type="text/css">
	.multiselect{
	height:34px;
	overflow:hidden;
	}
	</style>
</head>
<body>
	<input type="hidden" id="wxfabb" value="${erayFns:escapeStr(newbb)}" />
	<input type="hidden" id="dprtcode" value="${fixedCheckItem.dprtcode}" />
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>		
			<div class="panel-body">
			
				<form id="form">
					<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class="font-size-16 line-height-18">基本信息</div>
						<div>Basic Information</div>
					</div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">维修方案编号</div>
								<div class="font-size-9 line-height-18">No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="wxfabh" name="wxfabh" value="${erayFns:escapeStr(fixedCheckItem.wxfabh)}" class="form-control " readonly />
								<input type="hidden" id="id" name="id" value="${fixedCheckItem.id}"/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">维修方案名称</div>
								<div class="font-size-9 line-height-18">Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="wxfamc" name="wxfamc" value="${erayFns:escapeStr(fixedCheckItem.maintenance.zwms)}" class="form-control " readonly />
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">版本号</div>
								<div class="font-size-9 line-height-18">Revision</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="bb" name="bb" value="${erayFns:escapeStr(fixedCheckItem.bb)}" class="form-control " maxlength="10" readonly />
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">定检编号</div>
								<div class="font-size-9 line-height-18">Fixed No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="djbh" name="djbh" value="${erayFns:escapeStr(fixedCheckItem.djbh)}" class="form-control " readonly />
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>定检名称</div>
								<div class="font-size-9 line-height-18">Fixed Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="zwms" name="zwms" value="${erayFns:escapeStr(fixedCheckItem.zwms)}" class="form-control " maxlength="100" />
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>重要系数</div>
								<div class="font-size-9 line-height-18">Coefficient</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='zyxs' name="zyxs" >
									<c:forEach items="${significantCoefficientEnum}" var="item">
									  <option value="${item.id}" <c:if test="${fixedCheckItem.zyxs == item.id }">selected="true"</c:if> >${item.name}</option>
									</c:forEach>
							    </select>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>有效性</div>
								<div class="font-size-9 line-height-18">Effectivity</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='yxx' name="yxx" >
									<c:forEach items="${effectiveEnum}" var="item">
									  <option value="${item.id}" <c:if test="${fixedCheckItem.yxx == item.id }">selected="true"</c:if> >${item.name}</option>
									</c:forEach>
							    </select>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>排序号</div>
								<div class="font-size-9 line-height-18">Order No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="pxh" name="pxh" value="${fixedCheckItem.pxh}" class="form-control" value="${pxh}" placeholder='' onkeyup='clearNoNumber(this)' maxlength="10" />
							</div>
						</div>
						
						
						<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>标准工时</div>
								<div class="font-size-9 line-height-18">MHRS</div>
							</label>
							<div class=" col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group form-group" style='margin-bottom:0px;'>
								<input type="text" maxlength="8" class="form-control " 
									id="jhgsRs" value="${fixedCheckItem.jhgsRs}" onkeyup='clearNoNumber(this)'>
								<span class='input-group-addon' style="padding-left:0px;border:0px;background:none;">人 ×</span>
								<input maxlength="6" type="text" onkeyup='clearNoNumTwo(this)' class="form-control" id="jhgsXss" value="${fixedCheckItem.jhgsXss}" sonkeyup='clearNoNumTwo(this)'>
								<span class='input-group-addon' style="padding-left:0px;padding-right:0px;border:0px;background:none;">时 ＝</span>
									<input type="text" class="form-control " id="bzgs" readonly>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">状态</div>
								<div class="font-size-9 line-height-18">State</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='shzt' name="shzt" disabled="disabled">
									<c:forEach items="${approveStatusEnum}" var="item">
									  <option value="${item.id}" <c:if test="${fixedCheckItem.spzt == item.id }">selected="true"</c:if> >${item.name}</option>
									</c:forEach>
							    </select>
							</div>
						</div>
						
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单人</div>
								<div class="font-size-9 line-height-18">Creator</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="zdr" name="zdr" value="${erayFns:escapeStr(fixedCheckItem.zdrusername)}&nbsp;${erayFns:escapeStr(fixedCheckItem.zdrrealname)}" class="form-control " readonly/>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单时间</div>
								<div class="font-size-9 line-height-18">Create Time</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control" id="zdsj" name="zdsj" value="<fmt:formatDate value='${fixedCheckItem.zdsj}' pattern='yyyy-MM-dd HH:mm:ss' />" readonly type="text" />
							</div>
						</div>
						
					
					
					 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="bz"  maxlength="300" >${erayFns:escapeStr(fixedCheckItem.bz)}</textarea>
							</div>
						</div>
					</div>
				</form>
			
				<div class="clearfix"></div>
				
				<div class="panel-heading margin-left-16 padding-top-3" style="border-bottom: 1px solid #ccc;">
					<div class="font-size-16 line-height-18">工作内容</div>
					<div>Contents</div>
				</div>
				<div class="panel-body padding-left-0 padding-right-0">
					<small class="text-muted">双击可修改工作内容</small>
	            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
						<!-- start:table -->
						<div style="overflow-x: scroll;">
							<table class="table table-bordered table-striped table-hover text-center change-line table-set" style="min-width: 2570px;">
								<thead>
							   		<tr>
										<th class="colwidth-5">
											<div class="text-center">
												<button class="line6 " onclick="openWorkContentWinAdd('rotatable')">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
												</button>
											</div>
										</th>
										<th class="colwidth-3">
											<div class="font-size-12 notwrap">序号</div>
											<div class="font-size-9 notwrap">No.</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 notwrap">ATA章节号</div>
											<div class="font-size-9 notwrap">ATA</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 notwrap">项目来源</div>
											<div class="font-size-9 notwrap">Source</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 notwrap">工作类型</div>
											<div class="font-size-9 notwrap">Work Type</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 notwrap">工作地点</div>
											<div class="font-size-9 notwrap">Workplace</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12">中文描述</div>
											<div class="font-size-9">CH.Name</div>
										</th>
										<th class="colwidth-25">
											<div class="font-size-12">英文描述</div>
											<div class="font-size-9">F.Name</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 notwrap">检查类型</div>
											<div class="font-size-9 notwrap">Check Type</div>
										</th>
										<th class="colwidth-30">
											<div class="font-size-12 notwrap">适用性</div>
											<div class="font-size-9 notwrap">Applicability</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 notwrap">工作站位</div>
											<div class="font-size-9 notwrap">Location</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12 notwrap">厂家手册及版本</div>
											<div class="font-size-9 notwrap">Manual and Revision</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12 notwrap">厂家工卡及版本</div>
											<div class="font-size-9 notwrap">W/C and Revision</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12 notwrap">关联定检工卡</div>
											<div class="font-size-9 notwrap">Related Fixed Check W/C</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 notwrap">必检</div>
											<div class="font-size-9 notwrap">Check</div>
										</th>
										<th class="colwidth-3">
											<div class="font-size-12 notwrap">MI</div>
											<div class="font-size-9 notwrap">MI</div>
										</th>
										<th class="colwidth-30">
											<div class="font-size-12 notwrap">备注</div>
											<div class="font-size-9 notwrap">Remark</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 notwrap">有效性</div>
											<div class="font-size-9 notwrap">Effectivity</div>
										</th>
							 		 </tr>
								</thead>
								<tbody id="rotatable">
								
								</tbody>
							</table>
						</div>
						<!-- end:table -->
			     		<div class="clearfix"></div>
				 	 </div>
				 </div> 
				 
				 <%@ include file="/WEB-INF/views/monitor.jsp"%>
				 
				 <div class="clearfix"></div>
				 <c:if test="${!empty fixedCheckItem.shyj || !empty fixedCheckItem.pfyj}">
				 
				 <div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
					<div class="font-size-16 line-height-18">审批</div>
					<div class="font-size-9 ">Approval</div>
				 </div>
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
				<c:if test="${!empty fixedCheckItem.shyj}">
				 	<label class="col-lg-1 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"  >
						<div class="font-size-12 line-height-18">审核意见</div>
						<div class="font-size-9 line-height-18">Review Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<textarea class="form-control" id="shyj" name="shyj" maxlength="150" readonly>${erayFns:escapeStr(fixedCheckItem.shyj)}</textarea>
					</div>
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18"></div>
						<div class="font-size-9 line-height-18"></div>
					</label>
					<div class="col-lg-11 col-sm-9 col-xs-8 padding-left-8 padding-top-8">
						<label style="margin-right: 50px"></label>　
						<div class="pull-left padding-right-10">
							<div class="font-size-12 line-height-18" style="font-weight:normal">审核人&nbsp;<label style="margin-right: 50px;font-weight:normal">${erayFns:escapeStr(fixedCheckItem.sdisplayName)}</label></div>
							<div class="font-size-9 line-height-12">Reviewer</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18" style="font-weight:normal">审核时间 &nbsp;<label style="margin-right: 50px;font-weight:normal"><fmt:formatDate value='${fixedCheckItem.shsj}' pattern='yyyy-MM-dd HH:mm:ss' /></label></div>
						<div class="font-size-9 line-height-12">Review Time</div>
						</div>
				 	</div>
				 	
				 <div class="clearfix"></div><br/>
				 </c:if>
				 <c:if test="${!empty fixedCheckItem.pfyj}">
				 	<label class="col-lg-1 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"  >
						<div class="font-size-12 line-height-18">批准意见</div>
						<div class="font-size-9 line-height-18">Approval Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<textarea class="form-control" id="pfyj" name="pfyj" maxlength="150" readonly>${erayFns:escapeStr(fixedCheckItem.pfyj)}</textarea>
					</div>
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18"></div>
						<div class="font-size-9 line-height-18"></div>
					</label>
					<div class="col-lg-11 col-sm-9 col-xs-8 padding-left-8 padding-top-8">
						<label style="margin-right: 50px"></label>　
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18" style="font-weight:normal">批准人 &nbsp;<label style="margin-right: 50px;font-weight:normal">${erayFns:escapeStr(fixedCheckItem.pdisplayName)}</label></div>
						<div class="font-size-9 line-height-12">Appr. By</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18" style="font-weight:normal">批准时间    &nbsp;<label style="margin-right: 50px;font-weight:normal"><fmt:formatDate value='${fixedCheckItem.pfsj}' pattern='yyyy-MM-dd HH:mm:ss' /></label></div>
						<div class="font-size-9 line-height-12">Approved Time</div>
						</div>
				 	</div>
					 <div class="clearfix"></div><br/>
					 </c:if>
				  </div>
				  
				  </c:if>
				 
				<div class="text-right" style="margin-bottom:10px">
                    <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="save()">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/fixedcheckitem_edit.js"></script>
<%@ include file="/WEB-INF/views/open_win/work_content.jsp"%><!-------工作内容对话框 -------->
</body>
</html>