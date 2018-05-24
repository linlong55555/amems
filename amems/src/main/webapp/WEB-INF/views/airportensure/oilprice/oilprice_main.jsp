<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>油品价格维护</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body >
<style type="text/css">
.sel{
  background: #dbe2f7;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
//导航
Navigation(menuCode);
});
</script>
<input type="hidden" id="zzjgid" name="zzjgid" value=${user.jgdm} />
<input type="hidden" id="sz" name="sz"  />
<input type="hidden" id="ypmc" name="ypmc"  />
<input type="hidden" id="ypggid" name="ypggid"  />
<input type="hidden" id="oilpriceId" name="oilpriceId"  />
<input type="hidden" id="adjustHeight" value="90" />
	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>		
			<div class="panel-body ">
				
				
				
				<div class="col-sm-3" style="border-right: 1px solid #ccc">
				
					<div class=" pull-left padding-left-5 padding-right-0 margin-bottom-10" style="width: 250px;">
									<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">组织机构</div>
									<div class="font-size-9">Organization</div>
									</span>
									<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
										<select id="dprtcode" class="form-control " name="dprtcode" onchange="ypggChange()">
												<c:if test="${dprtcode==user.orgcode}">
														<option value="-1">公共</option>
												</c:if>
												<c:forEach items="${accessDepartments}" var="type">
													<option value="${type.id}" >${erayFns:escapeStr(type.dprtname)}</option>
												</c:forEach>
											</select>
								</div>
					</div>
										<div class="clearfix"></div>
				
					<div style="overflow: auto;height: 500px;">
						<div class="list-group" >
						<table class="table table-set" >
							<thead>
						   		<tr>
									<th class="colwidth-7">
										<div class="font-size-12 notwrap">油品</div>
										<div class="font-size-9 notwrap">Oils</div>
									</th > 
									<th class="colwidth-3" >
										<div class="font-size-12 notwrap">当前价格</div>
										<div class="font-size-9 notwrap">Current price</div>
									</th>
									
					 		 </tr>
							</thead>
							<tbody id="oilList">
					
					</tbody>
				</table>
						</div>
					</div>
				</div>
				
				<div id="right_div1" class="col-sm-9 " >
						<input type="hidden" id="fixId1" name="fixId1" />
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<div class="col-xs-7  padding-left-0 row-height">
						<!-- <div class="font-size-12" id="szName">上传文件</div> -->
					
					<button type="button" onclick="add()"  
						class=" col-lg-2 btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height checkPermission"
						permissioncode="airportensure:oilprice:main:01">
						<div class="font-size-12">新增价格</div>
						<div class="font-size-9">Add</div>
					</button>
					
					<div class="col-lg-6 col-sm-6 col-xs-6 padding-left-10 padding-right-0  margin-top-10 pull-left" id="szName">
					
					</div>
					</div>
					
					<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='价格/维护人' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                    </div> 
				</div>
						</div>
						
		            	<div id="workContent1" class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
							<!-- start:table -->
							<!-- <div class="margin-top-0 table-h" style="height:727px !important;overflow-x:auto;"> -->
							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 padding-top-10" style="height:500px !important;overflow-x:auto;width: 100%;">
								<table class="table table-bordered table-striped table-hover text-center " id="quality_check_list_table" >
									<thead>
								   		<tr>
											<th  class=" colwidth-5">
												<div class="font-size-12 notwrap">操作</div>
												<div class="font-size-9 notwrap">Operation</div>
											</th>
											<th  class=" colwidth-5 sorting" onclick="orderBy('ksrq')" id="ksrq_order">
												<div class="font-size-12 notwrap">开始日期</div>
												<div class="font-size-9 notwrap">Start date</div>
											</th>
											<th class=" colwidth-7 sorting" onclick="orderBy('ypjg')" id="ypjg_order">
											<div class="important">
												<div class="font-size-12 notwrap">价格(人民币)/吨</div>
												<div class="font-size-9 notwrap">RMB:Yuan</div>
											</div>
											</th>
											<th class=" colwidth-13 sorting" onclick="orderBy('whrid')" id="whrid_order">
											<div class="important">
												<div class="font-size-12 notwrap">维护人</div>
												<div class="font-size-9 notwrap">Maintainer</div>
											</div>
											</th>
											<th class=" colwidth-13 sorting" onclick="orderBy('whsj')" id="whsj_order">
												<div class="font-size-12 notwrap">维护时间</div>
												<div class="font-size-9 notwrap">Maintenance Time</div>
											</th>
							 		 </tr>
									</thead>
									<tbody id="oilpriceList">
									
									</tbody>
								</table>
								</div>
							<!-- end:table -->
					 	</div>
						<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
						</div>
				
					
					<div class="clearfix"></div>
					<div class=" col-lg-10 "></div>
					
				</div>
			</div>
		</div>
	</div>
		<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
		<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
	<div class="modal fade" id="alertModalAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
							<div class="font-size-16 line-height-18">维护价格时间阶段</div>
							<div class="font-size-9 line-height-18">Record Current price</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12 col-sm-12">
			            	<form id="form" >
			            	<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 margin-top-10 padding-right-0 ">
								<span class="pull-left col-xs-4 col-sm-6 col-lg-2 text-right padding-left-0 padding-right-0" style="width:30%;font-weight:bold">
								<div class="font-size-12">油品名称</div>
									<div class="font-size-9">Oils</div>
									</span>
								<div class="col-lg-10 col-sm-6 col-xs-8 padding-left-8 padding-right-0" style="width:70%">
										<input type="text" class="form-control " id="ypName" name="ypName" disabled="disabled"/>
								</div>
							</div> 
			            	<div class="clearfix"></div>
			            	<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 margin-top-10 padding-right-0 form-group">
								<span class="pull-left col-xs-4 col-sm-6 col-lg-2 text-right padding-left-0 padding-right-0" style="width:30%;font-weight:bold">
								<div class="font-size-12"><span style="color: red">*</span>开始日期</div>
									<div class="font-size-9">Start Date</div>
									</span>
								<div class="col-lg-10 col-sm-6 col-xs-8 padding-left-8 padding-right-0" style="width:70%">
									<input class="form-control date-picker"  id="ksrq" name="ksrq" data-date-format="yyyy-mm-dd" type="text"/>
								</div>
							</div> 
			            	<div class="clearfix"></div>
			            	<div class="col-xs-12 col-sm-12 col-lg-12  padding-left-0  padding-right-0 margin-bottom-10 form-group">
								<span class="pull-left col-xs-4 col-sm-6 col-lg-2 text-right padding-left-0 padding-right-0" style="width:30%;font-weight:bold">
								<div class="font-size-12"><span style="color: red">*</span>价格</div>
									<div class="font-size-9">RMB:Yuan</div>
									</span>
								<div class="col-lg-10 col-sm-6 col-xs-8 padding-left-8 padding-right-0" style="width:70%">
									<input type="text" class="form-control " id="ypjg" name="ypjg" />
								</div>
							</div> 
			            	</form>
							    
					     		<div class="clearfix"></div>
					     		<div class="text-center margin-top-10 padding-buttom-10 pull-right">
			                     <button onclick="saveUpdate()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" >
			                     		<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div></button>&nbsp;&nbsp;&nbsp;&nbsp;
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
		<%@ include file="/WEB-INF/views/alert.jsp"%>
		<script type="text/javascript" src="${ctx }/static/js/thjw/airportensure/oilprice/oilprice_main.js"></script>
</body>
</html>