	<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看部件信息</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">

.multiselect{
overflow:hidden;
}

</style>
</head>
<body>
<div class="page-content ">
	<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
				<input type="hidden" value="${user.jgdm}" id="dprtcode">
				<div class="panel panel-default">
			        <div class="panel-heading">
					 <h3 class="panel-title">基本信息  Basic Info</h3>
				     <input type="hidden" id="id" value="${hcmainData.id}"/>
				    </div>
					<div class="panel-body">
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">件号</div>
								<div class="font-size-9 line-height-18">P/N</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control " id="bjh" name="bjh" value="${erayFns:escapeStr(hcmainData.bjh)}" readonly/>
								<input type="hidden" class="form-control " id="id" value="${hcmainData.id}" />
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">单位</div>
								<div class="font-size-9 line-height-18">Unit</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select id="jldw" class="form-control " name="jldw"></select>
								<input type="hidden" class="form-control " id="jldw-hx" value="${erayFns:escapeStr(hcmainData.jldw)}" />
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">厂家件号</div>
								<div class="font-size-9 line-height-18">MP/N</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control " id="cjjh" name="cjjh" maxlength="20" value="${erayFns:escapeStr(hcmainData.cjjh)}"/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">ATA章节号</div>
								<div class="font-size-9 line-height-18">ATA</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="zjhName" name="zjhName" class="form-control " readonly/>
								<input type="hidden" class="form-control" id="zjh" value="${erayFns:escapeStr(hcmainData.zjh)}"/>
							</div>
						</div>
					
						<div class="clearfix"></div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">中文名称</div>
								<div class="font-size-9 line-height-18">CH.Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control " id="zwms" name="zwms" value="${erayFns:escapeStr(hcmainData.zwms)}"/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">英文名称</div>
								<div class="font-size-9 line-height-18">F.Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control" id="ywms"  name="ywms" value="${erayFns:escapeStr(hcmainData.ywms)}"/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">MEL</div>
								<div class="font-size-9 line-height-18">MEL</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="checkbox" id="isMel" name="isMel" <c:if test="${hcmainData.isMel eq 1 }">checked="checked"</c:if>  />
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">机型</div>
								<div class="font-size-9 line-height-18">Model</div>
							</label>
						    <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control" id="jxdiv" readOnly />		
							</div>
						</div>
						
						<div class="clearfix"></div>
					
					 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="bz" name="bz" maxlength="300" >${erayFns:escapeStr(hcmainData.bz)}</textarea>
							</div>
						</div>
					</div>
				</div>
			 </div>	
			 
				<div class="panel panel-default">
				        <div class="panel-heading">
						 <h3 class="panel-title">航材报价信息  Production Cost Info</h3>
					    </div>
					<div class="panel-body">	
					 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
					   <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-8 padding-right-0 " style="overflow: auto;">
						<table class="table table-thin table-bordered table-striped table-hover text-center" style="min-width:900px">
							<thead>
								<tr>
								   <th>
								       <div class="font-size-12 line-height-18">决算成本(人民币:元)</div>
										<div class="font-size-9 line-height-18">Final Cost（RMB：YUAN）</div>
									</th>
									<th>
									<div class="font-size-12 line-height-18">结算成本(人民币:元)</div>
										<div class="font-size-9 line-height-18">Settlement Cost（RMB：YUAN）</div>
									</th>
									<th><div class="font-size-12 line-height-18">估算成本(人民币:元)</div>
										<div class="font-size-9 line-height-18">Approximate Cost（RMB：YUAN）</div>
									</th>
									<th><div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th><div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>					
								</tr>
							</thead>
						    <tbody id="list">
							</tbody>
						</table>
					</div>
				</div>
			   </div>
		   </div>	
		    </div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/cost/view_materialcost.js"></script>
</body>
</html>