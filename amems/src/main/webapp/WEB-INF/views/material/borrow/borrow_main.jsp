<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" value="${user.jgdm}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->


	<div class="page-content">
		<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
			
				<div class="panel-body padding-bottom-0">
				<div  class='searchContent row-height' >
					<div class="col-xs-2 col-md-3 padding-left-0  form-group">
						<a onclick="addBorrow()" data-toggle="modal" permissioncode='aerialmaterial:borrow:main:01' class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" >
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a> 
					</div>
				
					<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0 form-group" >
		
					<div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;" >
						<input placeholder="申请单号/申请人" id="keyword_search" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                         <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!------------搜索框end------->
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">申请日期</div>
							<div class="font-size-9">Application Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="flightDate_search" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select class='form-control' id='fjzch_search' name="fjzch_search" >
										
								</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">借调对象类型</div>
							<div class="font-size-9">S/O Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='jddxlx'>
								<option value="" >显示全部 All</option>
								<option value="1" >飞行队 Flying team</option>
								<option value="2" >航空公司 Airline.com</option>
								<option value="0" >其它 Other</option>
								
						    </select>
						</div>
					</div>
					
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" name="dprtcode" class="form-control" onchange="changeOrganization()">
								<c:choose>
											<c:when test="${accessDepartment!=null && fn:length(accessDepartment) > 0}">
											<c:forEach items="${accessDepartment}" var="type">
												<option value="${type.id}"
													<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}
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
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				
				<div class="clearfix"></div>
                </div>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" style="overflow-x: scroll;">
					<table id="jrsq" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width:1400px">
						<thead>
							<tr>
								<th  class="colwidth-5" style="vertical-align: middle;">
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-5">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
							
								<th name="column_sqdh"  class="sorting colwidth-10" onclick="orderBy('sqdh',this)">
									<div class="important"><div class="font-size-12 line-height-18">申请单号</div></div>
									<div class="font-size-9 line-height-18">Apply No.</div>
								</th>
								<th name="column_sqr" class="sorting colwidth-10" onclick="orderBy('sqr',this)" >
									<div class="important"><div class="font-size-12 line-height-18">申请人</div>
									<div class="font-size-9 line-height-18">Applicant</div></div>
								</th>
								<th name="column_sqsj"  class="sorting colwidth-10" onclick="orderBy('sqsj',this)" >
									<div class="font-size-12 line-height-18">申请日期</div>
									<div class="font-size-9 line-height-18">Application Date</div>
								</th>
								<th name="column_fjzch"  class="sorting colwidth-10" onclick="orderBy('fjzch',this)" >
									<div class="font-size-12 line-height-18">飞机注册号</div>
									<div class="font-size-9 line-height-18">A/C REG</div>
								</th>
								<th name="column_jddxlx"  class="sorting colwidth-15" onclick="orderBy('jddxlx',this)" >
									<div class="font-size-12 line-height-18">借调对象类型</div>
									<div class="font-size-9 line-height-18">Seconded Obj Type</div>
								</th>
								<th name="column_jddxms"  class="sorting colwidth-10" onclick="orderBy('jddxms',this)">
									<div class="font-size-12 line-height-18" >借调对象</div>
									<div class="font-size-9 line-height-18">Seconded Obj</div>
								</th>
								<th name="column_zt"  class="sorting colwidth-5" onclick="orderBy('zt',this)">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</th>
								<th  name="column_bz" class="sorting colwidth-20" onclick="orderBy('bz',this)" >
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</th>
								<th  class="colwidth-10">
									<div class="font-size-12 line-height-18">制单人</div>
									<div class="font-size-9 line-height-18">Creator</div>
								</th>
								<th   class="colwidth-15">
									<div class="font-size-12 line-height-18">制单时间</div>
									<div class="font-size-9 line-height-18">Create Time</div>
								</th>
								<th name="column_dprtcode"  class="sorting" onclick="orderBy('dprtcode',this)">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
					
				</div>
				<div class="clearfix"></div>
		</div>
	</div>
	
		<%@ include file="/WEB-INF/views/alert.jsp"%>
	
	<!-- start指定结束提示框 -->
	<div class="modal fade" id="alertModalShutDown" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">指定结束</div>
							<div class="font-size-9 ">Specify The End</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
								<label class="pull-left col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">借入单号</div>
									<div class="font-size-9">Borrow No.</div>
								</label>
								<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
									<input type="hidden" name="reserveId" id="reserveId">
									<input type="hidden" name="zdjsrid" id="zdjsrid" value="${user.id}">
									<input type="text" class="form-control " id="sqdh" name="sqdh" disabled="disabled"/>
								</div>
							</div> 
			            	<div class="clearfix"></div>
			            	<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>结束原因</div>
								<div class="font-size-9 line-height-18">End Cause</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="zdjsyy" name="zdjsyy" placeholder='长度最大为150'   maxlength="150"></textarea>
							</div>
					     	<div class="clearfix"></div>
					     	<div class="text-center margin-top-10 padding-buttom-10 ">
					     			<button type="button" class="pull-right btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
								</button>&nbsp;&nbsp;
								<button type="button" class="pull-right btn btn-primary margin-right-10 padding-top-1 padding-bottom-1 margin-bottom-10" onclick="sbShutDown()">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
		                   		</button>&nbsp;&nbsp;
                     	
                    		</div><br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- end指定结束提示框 -->
	
	<!-- start指定结束提示框 -->
	<div class="modal fade" id="alertModalview" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">指定结束</div>
							<div class="font-size-9 ">Specify The End</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<label class="pull-left col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">借入单号</div>
									<div class="font-size-9">Borrow No.</div>
								</label>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="sqdhview" name="sqdhview" disabled="disabled"/>
								</div>
							</div> 
							<div class="clearfix"></div>
			            	<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<label class="pull-left col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">指定结束人</div>
									<div class="font-size-9">Operator</div>
								</label>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="zdjsr" name="zdjsr" disabled="disabled"/>
								</div>
							</div> 
			            	<div class="clearfix"></div>
            	           	<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
								<label class="pull-left col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">指定结束时间</div>
									<div class="font-size-9">End Time</div>
								</label>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="zdjsrq" name="zdjsrq" disabled="disabled"/>
								</div>
							</div> 
			            	<div class="clearfix"></div>
			            	<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18">结束原因</div>
								<div class="font-size-9 line-height-18">End Cause</div>
							</label>
							<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="zdjsyyview" name="zdjsyyview" disabled="disabled"  maxlength="150"></textarea>
							</div>
					     	<div class="clearfix"></div>
					     	<div class="text-center margin-top-10 padding-buttom-10 ">
                     			<button type="button" class="pull-right btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
                    		</div><br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- end指定结束提示框 -->
	
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/borrow/borrow_main.js"></script>
</body>
</html>