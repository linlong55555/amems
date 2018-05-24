<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>维修方案信息</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
<style type="text/css">
.ztree [class^="icon-"]  {
    font-family: FontAwesome;
}
</style>
</head>
<body >
	<input type="hidden" id="dprtcode" value="${maintenance.dprtcode}" />
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
								<div class="font-size-12 line-height-18">维修方案编号</div>
								<div class="font-size-9 line-height-18">Maintenance No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="wxfabh"  name="wxfabh" value="${erayFns:escapeStr(maintenance.wxfabh)}" class="form-control " readonly/>
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">维修方案名称</div>
								<div class="font-size-9 line-height-18">Maintenance</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control " value="${erayFns:escapeStr(maintenance.zwms)}" id="zwms" name="zwms" readonly/>
								<input type="hidden" id="wxfaid" value="${maintenance.id}" />
							</div>
						</div>
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">版本</div>
								<div class="font-size-9 line-height-18">Revision</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control " value="${erayFns:escapeStr(maintenance.bb)}" id="bb" name="bb" readonly/>
							</div>
						</div>
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">生效日期</div>
								<div class="font-size-9 line-height-18">Effective date</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control" id="sjSxrq" name="sjSxrq" value="<fmt:formatDate value='${maintenance.sjSxrq}' pattern='yyyy-MM-dd' />" readonly type="text" />
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</form>
				
				<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
					<div class="font-size-16 line-height-18">定检项目</div>
					<div class="font-size-9 ">Fixed Check Item</div>
				</div>
				
				<div id="left_div" class="col-sm-2" style="border-right: 1px solid #ccc">
					<div style="overflow: auto;height: 500px;">
						<div class="list-group"  id="fixedCheckItem">
						</div>
					</div>
				</div>
				<div id="right_div" class="col-sm-10">
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">定检编号</div>
								<div class="font-size-9 line-height-18">Fixed No.</div>
							</label>
							<div class=" col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<input type="text" id="fixCode" name="fixCode" class="form-control " readonly/>
								<input type="hidden" id="fixId" name="fixId" class="form-control " readonly/>
							</div>
						</div>
						<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 " >
							<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">定检名称</div>
								<div class="font-size-9 line-height-18">Fixed Name</div>
							</label>
							<div class=" col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<input type="text" id="fixName" name="fixName" class="form-control " readonly/>
							</div>
						</div>
						<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 " >
							<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">版本</div>
								<div class="font-size-9 line-height-18">Revision</div>
							</label>
							<div class=" col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
								<div  class=" col-lg-11 col-sm-11 col-xs-11 padding-left-0 padding-right-0">
									<input type="text" id="fixBb" name="fixBb" class="form-control " readonly/>
								</div>	
								<div class=" col-lg-1 col-sm-1 col-xs-1 padding-left-5 padding-right-0 " >
									<button type="button" id="btn" onclick="vieworhide();">
										<div class="pull-left padding-top-5">
									 	<i class="icon-caret-down font-size-16" id="icon"></i>
										</div>
						   			</button>
								</div>
							</div>
						</div>

						<div class="clearfix"></div>
						<div class="display-none" id="divSearch">
							<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">重要系数</div>
									<div class="font-size-9 line-height-18">Coefficient</div>
								</label>
								<div class=" col-lg-9 col-sm-8 col-xs-9  padding-left-8 padding-right-0">
									<select class='form-control' id='fixZyxs' name="fixZyxs" >
									<c:forEach items="${significantCoefficientEnum}" var="item">
									  <option value="${item.id}" >${item.name}</option>
									</c:forEach>
							    </select>
								</div>
							</div>
							<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">有效性</div>
									<div class="font-size-9 line-height-18">Effectivity</div>
								</label>
								<div class=" col-lg-9 col-sm-8 col-xs-9  padding-left-8 padding-right-0">
									<select class='form-control' id='fixYxx' name="fixYxx" >
									<c:forEach items="${effectiveEnum}" var="item">
									  	<option value="${item.id}" >${item.name}</option>
									</c:forEach>
							    </select>
								</div>
							</div>
							
							<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">排序号</div>
									<div class="font-size-9 line-height-18">Order No.</div>
								</label>
								<div class=" col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" id="fixPxh" name="fixPxh" class="form-control" />
								</div>
							</div>
							
							<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">标准工时</div>
									<div class="font-size-9 line-height-18">MHRS</div>
								</label>
								<div class="col-xs-2 padding-left-8 padding-right-0">
								<input type="text" class="form-control" id="jhgsRs" name="jhgsRs" value="${erayFns:escapeStr(fixedCheckItem.jhgsRs)}" >
								</div>
								<div class="col-xs-1 padding-left-0 padding-right-0" style="padding: 6px 0; height: 34px; line-height: 24px;">人x</div>
								<div class="col-xs-2 padding-left-0 padding-right-0">
									<input type="text" class="form-control" id="jhgsXss" name="jhgsXss" value="${erayFns:escapeStr(fixedCheckItem.jhgsXss)}" >
								</div>
								<div class="col-xs-1 padding-left-0 padding-right-0" 	style="padding: 6px 0; height: 34px; line-height: 24px;">时=</div>
								<div class="col-xs-3 padding-left-0 padding-right-0">
									<input type="text" class="form-control " id="bzgs" readonly>
								</div>
								
							</div>
							
							<div class="clearfix"></div>
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								<label class="col-lg-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">备注:</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</label>
								<div class="col-lg-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
									<textarea class="form-control" id="fixBz" name="fixBz"></textarea>
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class="font-size-16 line-height-18">工作内容</div>
						<div class="font-size-9 ">Contents</div>
					</div>
		            <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
						<!-- start:table -->
						<div style="overflow-x: scroll;">
							<table class="table table-bordered table-striped table-hover text-center table-set" style="min-width: 1800px;">
								<thead>
							   		<tr>
										<th class="colwidth-5">
											<div class="font-size-12 notwrap"></div>
											<div class="font-size-9 notwrap"></div>
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
				 	</div>
				 	
				 	<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class="font-size-16 line-height-18">循环周期监控设置</div>
						<div class="font-size-9 ">Monitor Setting</div>
					</div>
					<div id="monitoring" style="padding-left: 15px;" class="col-sm-12">
					</div>
					<div class="clearfix"></div>
					<!-- <div class=" col-lg-10 "></div>
					<div class=" col-lg-2 ">
			           	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()"  style="float: left; margin-left: 100px;">
				           	<div class="font-size-12">返回</div>
							<div class="font-size-9">Back</div>
						</button>
			        </div> -->
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/confirmationofrevision_view.js"></script>	
</body>
</html>