	<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>维护航材成本信息</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">

.multiselect{
   overflow:hidden;
}
</style>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
<input type="hidden" value="${user.jgdm}" id="dprtcode">
	<div class="page-content">
		<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
		      <div class="panel panel-default">
			        <div class="panel-heading">
					   <h3 class="panel-title">基本信息  Basic Info</h3>
				    </div>
				    <div class="panel-body">
				    <input type="hidden" id="id" value="${hcmainData.id}"/>
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
								<input type="text" class="form-control " id="cjjh" name="cjjh"  readOnly maxlength="20" value="${erayFns:escapeStr(hcmainData.cjjh)}"/>
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
								<input type="text" class="form-control " id="zwms" name="zwms" readOnly value="${erayFns:escapeStr(hcmainData.zwms)}"/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">英文名称</div>
								<div class="font-size-9 line-height-18">F.Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control" id="ywms"  name="ywms" readOnly value="${erayFns:escapeStr(hcmainData.ywms)}"/>
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
							<div  class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
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
					   <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-8 padding-right-0 " style="overflow: auto;" >
						<table class="table table-thin table-bordered table-striped table-hover text-center" style="min-width:900px">
							<thead>
								<tr>
								    <th width="4%">
									     <a href="javascript:addHcCost();" >
											<button style="height: 35px;" class="line6 " >
											<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i></button>
										 </a>
	 								    </th>	
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
				  <div class="pull-right" style="margin-bottom: 10px">
               		<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()">
               			<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
				 </div>
     </div>
   </div>
		<!-- 基本信息 End -->
		
		 <!-- start指定结束提示框 -->
	<div class="modal fade" id="alertModalAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:550px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
							<div class="font-size-16 line-height-18">新增航材成本报价</div>
							<div class="font-size-9 ">Add AirMaterial Cost</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12">
			            	<form id="form">
			            	<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0">
								<label class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12">决算成本</div>
									<div class="font-size-9">Final Cost</div>
								</label>
								<div class="col-lg-10 col-sm-6 padding-left-8 padding-right-0 form-group">
									<input type="text" maxlength="9" class="form-control " id="juescb" name="juescb" />
								</div>
							</div> 
			            	<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0">
								<label class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12">结算成本</div>
									<div class="font-size-9">Settlement Cost</div>
								</label>
								<div class="col-lg-10 col-sm-6 padding-left-8 padding-right-0 form-group">
									<input type="text" maxlength="9" class="form-control " id="jiescb" name="jiescb" />
								</div>
							</div> 
					        <div class="col-xs-12 col-sm-12  padding-left-0  padding-right-0 ">
								<label class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12">估算成本</div>
									<div class="font-size-9">Appro Cost</div>
								</label>
								<div class="col-lg-10 col-sm-6 padding-left-8 padding-right-0 form-group">
									<input type="text" maxlength="9" class="form-control " id="gscb" name="gscb" />
								</div>
							</div>
							</form> 
				     		<div class=" margin-top-10 text-right">
                    	         <button type="button" onclick="AddCost()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
                    	         &nbsp;&nbsp;
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
	</div>
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/material/cost/edit_materialcost.js"></script>
</body>
</html>