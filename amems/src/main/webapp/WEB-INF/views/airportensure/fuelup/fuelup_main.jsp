<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
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
	<input type="hidden" id="fazhi1" value="${erayFns:escapeStr(threshold1)}" />
	<input type="hidden" id="fazhi2" value="${erayFns:escapeStr(threshold2)}" />
	<input type="hidden" id="zzjgid" value="${user.jgdm}" />
	
	<div class="clearfix"></div>
	
	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				<div class="col-xs-2 col-md-3 padding-left-0 row-height">
				   	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode='airportensure:fuelup:main:01'  onclick="addinto()">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
               		</button>
				</div>
				<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0" >
					<div class="pull-left ">
						<span class="pull-left  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</span>
						<div class=" padding-left-8 padding-right-0 pull-left" style="width: 180px; margin-right:5px;">
							<select id="fjzch_f" name="fjzch_f" class="form-control " onchange="changeSelectedPlane()">
								
							</select>
						</div>
					</div>
		
					<div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;" >
						<input placeholder="飞机加油单编号/化验单编号" id="keyword_search" class="form-control " type="text">
					</div>
					
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                         <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search();">
							<div class="pull-left text-center">
								<div class="font-size-12">更多</div>
								<div class="font-size-9">More</div>
							</div>
							<div class="pull-left padding-top-5"> &nbsp;
							<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!------------搜索框end------->
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10 margin-top-10 display-none border-cccccc search-height" id="divSearch">
					
				<div class="clearfix"></div>
			
				<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 margin-bottom-10 ">
					<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">加油日期</div>
						<div class="font-size-9">Date</div>
					</span>
					<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
						<input type="text" class="form-control " name="date-range-picker" id="jyrqs" readonly />
					</div>
				</div>
					
				<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
					<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
						<div class="font-size-9">Organization</div>
					</span>
					<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
						<select id="dprtcode" name="dprtcode" class="form-control " onchange="changeOrganization()">
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
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
						<div class="font-size-12">重置</div>
						<div class="font-size-9">Reset</div>
					</button>
				</div>
			</div>
			
			<div class="clearfix"></div>
			
			<div  class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h"  style="overflow-x:scroll">
				<table id="fjjyd" class="table table-thin table-bordered table-set" id="borrow_return_outstock_table" style="min-width:2500px!important;">
					<thead>
						<tr>
							<th class="fixed-column colwidth-5"  style="vertical-align: middle;">
								<div class="font-size-12 line-height-18">操作</div>
								<div class="font-size-9 line-height-18">Operation</div>
							</th>
							<th class=" colwidth-5" >
								<div class="font-size-12 line-height-18">序号</div>
								<div class="font-size-9 line-height-18">No.</div>
							</th>
							<th class=" sorting colwidth-15"  name="column_fjjydbh" onclick="orderBy('fjjydbh', this)" >
								<div class="important">
								<div class="font-size-12 line-height-18">飞机加油单编号</div>
								<div class="font-size-9 line-height-18">Refueling No.</div>
								</div>
							</th>
							<th  class="sorting colwidth-15" name="column_fjssdw" onclick="orderBy('fjssdw', this)" >
								<div class="font-size-12 line-height-18">飞机所属单位</div>
								<div class="font-size-9 line-height-18">Aircraft Owned</div>
							</th>
							<th  class="sorting colwidth-15"  name="column_jd" onclick="orderBy('jd', this)" >
								<div class="font-size-12 line-height-18">基地</div>
								<div class="font-size-9 line-height-18">Airport</div>
							</th>
							<th  class="sorting colwidth-15" name="column_fjjx" onclick="orderBy('fjjx', this)">
								<div class="font-size-12 line-height-18">机型</div>
								<div class="font-size-9 line-height-18">Type</div>
							</th>
							<th  class="sorting colwidth-15" name="column_hbh" onclick="orderBy('hbh', this)">
								<div class="font-size-12 line-height-18">航班号</div>
								<div class="font-size-9 line-height-18">Flight No.</div>
							</th>
							<th  class="sorting colwidth-15" name="column_fjzch" onclick="orderBy('fjzch', this)">
								<div class="font-size-12 line-height-18">飞机注册号</div>
								<div class="font-size-9 line-height-18">A/C REG</div>
							</th>
							<th  class="colwidth-15">
								<div class="important">
									<div class="font-size-12 line-height-18">化验单编号</div>
									<div class="font-size-9 line-height-18">Test No.</div>
									</div>
							</th>
							<th   class="sorting colwidth-15"  name="column_jyrq" onclick="orderBy('jyrq', this)">
							
								<div class="font-size-12 line-height-18">加油日期</div>
								<div class="font-size-9 line-height-18">Date</div>
							</th>
							<th   class="sorting colwidth-30" name="column_ypgg" onclick="orderBy('ypgg', this)">
									<div class="font-size-12 line-height-18">油品名称与规格</div>
									<div class="font-size-9 line-height-18">Oils</div>
							</th>
							<th  class="sorting colwidth-15" name="column_jsmd" onclick="orderBy('sjmd', this)">
								<div class="font-size-12 line-height-18">实际密度(g/cm³) </div>
								<div class="font-size-9 line-height-18">Density(g/cm³)</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">实际加油数量(L)</div>
								<div class="font-size-9 line-height-18">Gas volume(L)</div>
							</th>
							<th  class="colwidth-15">
								<div class="font-size-12 line-height-18">加油车车号</div>
								<div class="font-size-9 line-height-18">Tanker No.</div>
							</th>
							<th  class="sorting colwidth-15" name="column_jydjbh" onclick="orderBy('jydjbh', this)">
								<div class="font-size-12 line-height-18">加油地井编号</div>
								<div class="font-size-9 line-height-18">Gas well No.</div>
							</th>
							<th  class="sorting colwidth-15" name="column_fyr" onclick="orderBy('fyr', this)">
								<div class="font-size-12 line-height-18">加油人</div>
								<div class="font-size-9 line-height-18">Oiler</div>
							</th>
							<th  class="sorting colwidth-15" name="column_jykssj" onclick="orderBy('jykssj', this)">
								<div class="font-size-12 line-height-18">加油开始时间</div>
								<div class="font-size-9 line-height-18">Start date</div>
							</th>
							<th  class="sorting colwidth-15" name="column_jyjssj" onclick="orderBy('jyjssj', this)">
								<div class="font-size-12 line-height-18">加油结束时间</div>
								<div class="font-size-9 line-height-18">End Date</div>
							</th>
							<th  class="sorting colwidth-15" name="column_syr" onclick="orderBy('syr', this)">
								<div class="font-size-12 line-height-18">收油人</div>
								<div class="font-size-9 line-height-18">Recipient</div>
							</th>
							<th  class=" colwidth-15" >
								<div class="font-size-12 line-height-18">操作人</div>
								<div class="font-size-9 line-height-18">Operator</div>
							</th>
							<th  class=" colwidth-15" >
								<div class="font-size-12 line-height-18">操作时间</div>
								<div class="font-size-9 line-height-18">Operate time</div>
								</div>
							</th>
							<th  class="colwidth-15">
								<div class="font-size-12 line-height-18">组织机构</div>
								<div class="font-size-9 line-height-18">Organization</div>
							</th>
						</tr>
					</thead>
					<tbody id="list">
					
					</tbody>
				</table>
			</div>
			<div class="col-xs-12 text-center padding-right-0 page-height" id="pagination"></div>
		</div>
	</div>
</div>
	
	<input type="hidden" class="form-control " id="fuelupId" name="fuelupId" />
	<!-- start新增修改提示框 -->
	<div class="modal fade" id="alertModaladdupdate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:70%;">
		<input type="hidden" class="form-control " id="biaoshi"  value="1" />
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18" id="accredit"></div>
							<div class="font-size-9 " id="accreditrec"></div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
							<form id="form1">
			         			<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 margin-top-10 padding-right-0">
									<label class="pull-left col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">基地</div>
										<div class="font-size-9">Base</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										<select class='form-control' id='jd' name="jd">
										<c:choose>
											<c:when test="${baseList!=null && fn:length(baseList) > 0}">
												<c:forEach items="${baseList}" var="base">
													<option value="${base.id}">${erayFns:escapeStr(base.jdms)}</option>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<option value="">暂无基地</option>
											</c:otherwise>
										</c:choose>
									</select>
								</div>
							</div> 
			
							<div class="col-lg-4 col-sm-6 col-xs-12 padding-left-0 margin-top-10 padding-right-0  form-group">
								<label class="pull-left col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">
										<span style="color: red">*</span>飞机注册号
									</div>
									<div class="font-size-9">A/C REG</div>
								</label>
								<div id="fjzchs" class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<select id="fjzch" class="form-control" name="fjzch">
									
									</select>
									<input id="fjzchyc" class="form-control" name="fjzchyc" />
								</div>
							</div>
							
							<div class="col-lg-4 col-sm-6 col-xs-12 padding-left-0 margin-top-10 padding-right-0  form-group">
								<label class="pull-left col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">航班号</div>
									<div class="font-size-9">Flight No.</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input class=" form-control" id="hbh" name="hbh" maxlength="50"  type="text" />
								</div>
							</div>
							
							<div class="clearfix"></div>

							<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<label class="pull-left col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">化验单编号</div>
									<div class="font-size-9">Test No.</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control " maxlength="50" id="hyddh" name="hyddh" />
								</div>
							</div>
								
							<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<label class="pull-left col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">
										<span style="color: red">*</span>加油日期
									</div>
									<div class="font-size-9">Date</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input class="date-picker form-control" id="jyrq" name="jyrq" data-date-format="yyyy-mm-dd" type="text" disabled="disabled"/>
								</div>
							</div>
							
							<div class="clearfix"></div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 margin-top-0 padding-right-0 form-group">
								<label class="pull-left col-lg-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">飞机所属单位</div>
									<div class="font-size-9">Aircraft owned</div>
								</label>
								<div class="col-lg-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
									<input class="form-control" id="fjssdw"  name="fjssdw" maxlength="15" type="text"/>
								</div>
							</div> 
							
							<div class="clearfix"></div>
								
							<div class="col-lg-12 col-md-12 padding-left-0  padding-top-0 padding-bottom-10 padding-right-0 "style="overflow-x: auto" >
								<table class="table table-thin table-bordered table-striped table-hover" style="min-width:900px" >
									<thead>
										<tr class="tr_bg">
											<th  class="colwidth-15" style="vertical-align: middle;" rowspan="2">
												<div class="font-size-12 line-height-18">油品名称与规格</div>
												<div class="font-size-9 line-height-18">Oils</div>
											</th>
											<th  class="colwidth-5" rowspan="2" style="vertical-align: middle;">
												<div class="font-size-12 line-height-18">实际密度(g/cm³)</div>
												<div class="font-size-9 line-height-18">Density(g/cm³)</div>
											</th>
											<th  class="colwidth-30" colspan="2">
												<div class="font-size-12 line-height-18">实际加油数量(升) Gas volume(L)</div>
											</th>
											<th  class="colwidth-5" rowspan="2" style="vertical-align: middle;">
												<div class="font-size-12 line-height-18">千克</div>
												<div class="font-size-9 line-height-18">KG</div>
											</th>
											</tr>
											<tr class="tr_bg">
											<th  class="colwidth-5" >
												<div class="font-size-12 line-height-18">数字</div>
												<div class="font-size-9 line-height-18">Numbers</div>
											</th>
											<th  class="colwidth-15" >
												<div class="font-size-12 line-height-18">数字大写</div>
												<div class="font-size-9 line-height-18">Capitalized Numbers</div>
											</th>
										</tr>
									</thead>
									<tbody id="list1">
									</tbody>
								</table>
							</div>
							
							<div class="clearfix"></div>	
								
							<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<label class="pull-left col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">加油车车号</div>
									<div class="font-size-9">Tanker No.</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input class="form-control" id="jycch" type="text" maxlength="50" name="jycch"/>
								</div>
							</div>
							
							<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<label class="pull-left col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">加油地井编号</div>
									<div class="font-size-9">Gas well No.</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input class="form-control" id="jydjbh" type="text" maxlength="50" name="jydjbh"/>
								</div>
							</div>
							
							<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<label class="pull-left col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>加油人</div>
									<div class="font-size-9">Oiler</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input class="form-control" id="fyr" type="text" maxlength="10" name="fyr"/>
								</div>
							</div>
							
							<div class="clearfix"></div>
							
							<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<label class="pull-left col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">加油开始时间</div>
									<div class="font-size-9">Start date</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input class="form-control" id="jykssj" name="jykssj" maxlength="20" type="text"/>
								</div>
							</div>
							
							<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<label class="pull-left col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">加油结束时间</div>
									<div class="font-size-9">End date</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input class="form-control" id="jyjssj" name="jyjssj" maxlength="20" type="text"/>
								</div>
							</div>
							
							<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<label class="pull-left col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">收油人</div>
									<div class="font-size-9">Recipient</div>
								</label>
								<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
									<input class="form-control" id="syr" name="syr" maxlength="10" type="text"/>
								</div>
							</div>
					  
							<div class="clearfix"></div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<label class="pull-left col-lg-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">备注</div>
									<div class="font-size-9">Remark</div>
								</label>
								<div class="col-lg-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
									<textarea class="form-control" id="bz"   maxlength="300"></textarea>
								</div>
							</div>
							
							<div class="clearfix"></div>
							
					     	<div class="text-center margin-top-0 padding-buttom-10 ">
	                     		<button type="button" class="pull-right btn btn-primary padding-top-1 margin-right-0 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
								<button id="baocuns" type="button" class="pull-right btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 margin-bottom-10" onclick="saveUpdate()">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
		                   		</button>
                    		</div>
							</form>
						 </div> 
					</div>
					
					<div class="clearfix"></div>
					
				</div>
			</div>
		</div>
	</div>
	<!-- end新增修改提示框-->
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<script type="text/javascript" src="${ctx}/static/js/thjw/airportensure/fuelup/fuelup_main.js"></script>
</body>
</html>