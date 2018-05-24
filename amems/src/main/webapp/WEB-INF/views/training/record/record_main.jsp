<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>维修人员培训记录</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script type="text/javascript">
		var id = "${param.id}";
		var pageParam = '${param.pageParam}';
	</script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			//回车事件控制
			$(this).keydown(function(event) {
				e = event ? event :(window.event ? window.event : null); 
				if(e.keyCode==13){
					var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
					if(formatUndefine(winId) != ""){
						$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
					}else{
						searchRevision();//调用主列表页查询
					}
				}
			});
		});
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
			  <div class='row-height searchContent margin-top-0'>
				<div class="col-xs-2 col-md-3 padding-left-0 form-group">
				   	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode='training:record:main:01'  onclick="addinto()">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
               		</button>
               		<a href="#" onclick="exportExcel()"  class="btn btn-primary padding-top-1 margin-left-6 padding-bottom-1  checkPermission"  permissioncode='training:record:main:04'>
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a> 
				</div>
				<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0 form-group" >
					<div class="pull-left ">
						<span class="pull-left  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">人员</div>
							<div class="font-size-9">Personel</div>
						</span>
						<div class=" padding-left-8 padding-right-0 pull-left" style="width: 150px; margin-right:5px;">
							<input placeholder="人员编号/人员" id="ry" name="ry" class="form-control " onchange="searchRevision()" />
						</div>
					</div>
					<div class="pull-left ">
						<span class="pull-left  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">课程</div>
							<div class="font-size-9">Course</div>
						</span>
						<div class=" padding-left-8 padding-right-0 pull-left" style="width: 150px; margin-right:5px;">
							<input placeholder="课程编号/课程名称" id="kc" name="kc" class="form-control " onchange="searchRevision()" />
						</div>
					</div>
					<div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;" >
						<input placeholder="机型/专业/形式/培训机构/地点/讲师/证书/备注" id="keyword_search" class="form-control " type="text">
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
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  display-none border-cccccc" id="divSearch">
				<div class="clearfix"></div>
				<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 margin-bottom-10 ">
					<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">培训日期</div>
						<div class="font-size-9">Training Date</div>
					</span>
					<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
						<input type="text" class="form-control " name="date-range-picker" id="pxDate_search" readonly />
					</div>
				</div>
				<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 margin-bottom-10 ">
					<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">培训类别</div>
						<div class="font-size-9">Training Type</div>
					</span>
					<div id="pxlbs1" class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
						<select type="text" class="form-control "  id="pxlbs"  />
						<option value="">显示全部 All</option>
						</select>
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
			</div>
			<div  class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height"  style="overflow-x:scroll">
				<table id="fjjyd" class="table table-thin table-bordered table-set" id="borrow_return_outstock_table" style="min-width:2500px!important;">
					<thead>
						<tr>
							<th class="fixed-column colwidth-5"  style="vertical-align: middle;">
								<div class="font-size-12 line-height-18">操作</div>
								<div class="font-size-9 line-height-18">Operation</div>
							</th>
							<th class="fixed-column colwidth-5" >
								<div class="font-size-12 line-height-18">序号</div>
								<div class="font-size-9 line-height-18">No.</div>
							</th>
							<th class="fixed-column sorting colwidth-15"  name="column_rybh" onclick="orderBy('rybh', this)" >
								<div class="font-size-12 line-height-18">人员编号</div>
								<div class="font-size-9 line-height-18">Personnel No.</div>
							</th>
							<th  class="fixed-column sorting colwidth-15" name="column_xm" onclick="orderBy('xm', this)" >
								<div class="font-size-12 line-height-18">人员</div>
								<div class="font-size-9 line-height-18">Personnel</div>
							</th>
							<th  class="sorting colwidth-15"  name="column_gzdw" onclick="orderBy('gzdw', this)" >
								<div class="font-size-12 line-height-18">工作单位/部门</div>
								<div class="font-size-9 line-height-18">Work Unit/department</div>
							</th>
							<th  class="sorting colwidth-15" name="column_kcbm" onclick="orderBy('kcbm', this)">
								<div class="font-size-12 line-height-18">课程代码</div>
								<div class="font-size-9 line-height-18">Course Code</div>
							</th>
							<th  class="sorting colwidth-15" name="column_kcmc" onclick="orderBy('kcmc', this)">
								<div class="font-size-12 line-height-18">课程名称</div>
								<div class="font-size-9 line-height-18">Course Name</div>
							</th>
							<th  class="sorting colwidth-22" name="column_xcpxrq" onclick="orderBy('xcpxrq', this)">
								<div class="font-size-12 line-height-18">培训时间</div>
								<div class="font-size-9 line-height-18">Training Time</div>
							</th>
							<th  class="colwidth-15 sorting"  name="column_sj_ksrq" onclick="orderBy('sj_ksrq', this)">
									<div class="font-size-12 line-height-18">下次培训日期</div>
									<div class="font-size-9 line-height-18">Next Training Date</div>
							</th>
							<th  class="sorting colwidth-15" name="column_fjjx" onclick="orderBy('fjjx', this)">
								<div class="important">
								<div class="font-size-12 line-height-18">机型</div>
								<div class="font-size-9 line-height-18">A/C Type</div>
								</div>
							</th>
							<th  class="sorting colwidth-15" name="column_zy" onclick="orderBy('zy', this)">
								<div class="important">
								<div class="font-size-12 line-height-18">专业</div>
								<div class="font-size-9 line-height-18">Major</div>
								</div>
							</th>
							<th   class="sorting colwidth-15"  name="column_pxlb" onclick="orderBy('pxlb', this)">
								<div class="font-size-12 line-height-18">培训类别</div>
								<div class="font-size-9 line-height-18">Training Type</div>
							</th>
							<th   class="sorting colwidth-15" name="column_fxbs" onclick="orderBy('fxbs', this)">
									<div class="font-size-12 line-height-18">初/复训标识</div>
									<div class="font-size-9 line-height-18">Whether</div>
							</th>
							<th  class="sorting colwidth-15" name="column_pxxs" onclick="orderBy('pxxs', this)">
								<div class="important">
								<div class="font-size-12 line-height-18">培训形式</div>
								<div class="font-size-9 line-height-18">Training Form</div>
								</div>
							</th>
							<th class="sorting colwidth-15" name="column_ksxs" onclick="orderBy('ksxs', this)">
								<div class="important">
								<div class="font-size-12 line-height-18">考核形式</div>
								<div class="font-size-9 line-height-18">Assessment Form</div>
								</div>
							</th>
							<th  class="sorting colwidth-15" name="column_pxgh" onclick="orderBy('pxgh', this)">
								<div class="important">
								<div class="font-size-12 line-height-18">培训机构</div>
								<div class="font-size-9 line-height-18">Training Institution</div>
								</div>
							</th>
							<th  class="sorting colwidth-15" name="column_kcdd" onclick="orderBy('kcdd', this)">
								<div class="important">
								<div class="font-size-12 line-height-18">培训地点</div>
								<div class="font-size-9 line-height-18">Training Location</div>
								</div>
							</th>
							<th  class="sorting colwidth-15" name="column_jsxm" onclick="orderBy('jsxm', this)">
								<div class="important">
								<div class="font-size-12 line-height-18">讲师</div>
								<div class="font-size-9 line-height-18">Lecturer</div>
								</div>
							</th>
							<th  class="sorting colwidth-15" name="column_cql" onclick="orderBy('cql', this)">
								<div class="font-size-12 line-height-18">出勤率(%)</div>
								<div class="font-size-9 line-height-18">Attendance Rate(%)</div>
							</th>
							<th  class="sorting colwidth-15" name="column_cj" onclick="orderBy('cj', this)">
								<div class="font-size-12 line-height-18">成绩</div>
								<div class="font-size-9 line-height-18">Results</div>
							</th>
							<th  class="sorting colwidth-15" name="column_zs" onclick="orderBy('zs', this)">
								<div class="important">
								<div class="font-size-12 line-height-18">证书</div>
								<div class="font-size-9 line-height-18">Certificate</div>
								</div>
							</th>
							<th  class="sorting colwidth-15" name="column_khjg" onclick="orderBy('khjg', this)">
								<div class="font-size-12 line-height-18">考核结果</div>
								<div class="font-size-9 line-height-18">Assessment Result</div>
							</th>
							<th  class="sorting colwidth-15" name="column_bz" onclick="orderBy('bz', this)">
								<div class="important">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
								</div>
							</th>
							<th class="colwidth-10"  >
								<div class="font-size-12 line-height-18">附件</div>
								<div class="font-size-9 line-height-18">Attachment</div>
							</th>
							<th   class="sorting colwidth-15" name="column_whrid" onclick="orderBy('whrid', this)">
								<div class="font-size-12 line-height-18">维护人</div>
								<div class="font-size-9 line-height-18">Maintainer</div>
							</th>
							<th  class="sorting colwidth-15" name="column_whsj" onclick="orderBy('whsj', this)" >
								<div class="font-size-12 line-height-18">维护时间</div>
								<div class="font-size-9 line-height-18">Maintainer time</div>
								</div>
							</th>
							<th  class="sorting colwidth-15" name="column_dprtcode" onclick="orderBy('dprtcode', this)">
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
	<input type="hidden" class="form-control " id="id" name="id" />
	<!-------培训计划对话框 Start-------->
<div class="modal fade in modal-new active" id="alertModaladdupdate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-dialog" style="width:85%;">
		<div class="modal-content">
			<div class="modal-header modal-header-new">
			 	<h4 class="modal-title">
					<div class="pull-left">
						<div class="font-size-14 "><span id="modalName">新增</span></div>
						<div class="font-size-12"><span id="modalEname">Add</span></div>
					 </div>
					 <div class="pull-right">
					 	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					 </div>
					 <div class="clearfix"></div>
	             </h4>
           	</div>
			<div class="modal-body padding-bottom-0" id="alertBody">
				<div class="panel panel-primary">
					<div class="panel-body padding-top-0 padding-bottom-0" >
						<form id="form1">
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-top-10 padding-right-0 margin-bottom-10">
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>人员</div>
										<div class="font-size-9">Personnel</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="hidden" class="form-control " id="wxrydaid" />
										<div class="input-group">
											<input id="xm" name="xm" class="form-control readonly-style" readonly="readonly" type="text" ondblclick="openPersonelWin()">
								            <span class="input-group-btn">
												<button type="button" class="btn btn-default form-control" style="height:34px;" data-toggle="modal" onclick="openPersonelWin()">
													<i class="icon-search cursor-pointer"></i>
												</button>
											</span>
								       </div>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">工作单位/部门</div>
										<div class="font-size-9">Work/Dept</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="gzdw" name="gzdw" maxlength="100"  readonly/>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>课程代码</div>
										<div class="font-size-9">Course Code</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="hidden" class="form-control " id="kcid" />
										<div class="input-group">
											<input type="text" class="form-control " id="kcbm" name="kcbm" maxlength="50" onchange="onKcbmChanged();" />
								            <span class="input-group-btn">
												<button type="button" class="btn btn-default form-control" style="height:34px;" data-toggle="modal" onclick="openCourseWin()">
													<i class="icon-search cursor-pointer"></i>
												</button>
											</span>
								       </div>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>课程名称</div>
										<div class="font-size-9">Course Name</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="kcmc" name="kcmc" maxlength="100"  />
										<input type="hidden" class="form-control " id="kcnr"  />
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">机型</div>
										<div class="font-size-9">A/C Type</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input class="form-control" id="jx" name="jx" maxlength="50" >
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red"></span>讲师</div>
										<div class="font-size-9">Lecturer</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="hidden" class="form-control " id="jsid" />
										<div class="input-group">
											<input id="jsxm" name="jsxm" class="form-control "  type="text" >
								            <span class="input-group-btn">
												<button type="button" class="btn btn-default form-control" style="height:34px;" data-toggle="modal" onclick="openLecturerWin()">
													<i class="icon-search cursor-pointer"></i>
												</button>
											</span>
								       </div>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>课程开始时间</div>
										<div class="font-size-9">Start Time</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class='input-group' style="position:relative;z-index:1;">
										  	<input type="text" class='form-control datetimepicker' id="sjKsrq" name="sjKsrq" onchange="changedate()" />
										  	<span class='input-group-btn'>
										  		<input class='form-control datetimepicker readonly-style' type='text' style='width:45px;' id='sjKssj' name='sjKssj' readonly/>
										  	</span>
										</div>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">课程结束时间</div>
										<div class="font-size-9">End Time</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class='input-group' style="position:relative;z-index:1;">
										  	<input type="text" class='form-control datetimepicker' id="sjJsrq" name="sjJsrq"/>
										  	<span class='input-group-btn'>
										  		<input class='form-control datetimepicker readonly-style' type='text' style='width:45px;' id='sjJssj' name='sjJssj' readonly/>
										  	</span>
										</div>
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group ">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>实际课时</div>
										<div class="font-size-9">Practical Hours</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
										<div class="input-group">
											<input type="text" class="form-control " id="sjks" name="sjks" onkeyup='clearNoNumTwo(this)' maxlength="10" />
											<span name="xh" class="input-group-addon dw"  style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">时</span>
										</div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">初/复训标识</div>
										<div class="font-size-9">Whether</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<label style="margin-right: 20px;font-weight: normal" >
									    	<input name="fxbs" type="radio" value="1" checked="checked"/>初训
									    </label> 
										<label style="font-weight: normal" >
											<input name="fxbs" type="radio" value="2" />复训
										</label> 
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">培训形式</div>
										<div class="font-size-9">Form</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<select class='form-control' id='pxxs' name="pxxs" ></select>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">考试形式</div>
										<div class="font-size-9">Form</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<select class='form-control' id='ksxs' name="ksxs" ></select>
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">培训类别</div>
										<div class="font-size-9">Training Type</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<select class='form-control' id='pxlb' name="pxlb" ></select>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">培训机构</div>
										<div class="font-size-9">Institution</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="pxgh" name="pxgh" maxlength="100" />
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">培训地点</div>
										<div class="font-size-9">Location</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input class='form-control' id='kcdd' name="kcdd"   maxlength="100"/>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">出勤率</div>
										<div class="font-size-9">Attendance</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group">
											<input type="text" class="form-control " id="cql" name="cql"  maxlength="10" />
											<span name="xh" class="input-group-addon dw"  style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">%</span>
										</div>
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">成绩</div>
										<div class="font-size-9">Result</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="cj" name="cj" maxlength="12"  />
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">证书</div>
										<div class="font-size-9">Certificate</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="zs" name="zs" maxlength="100"  />
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">考核结果</div>
										<div class="font-size-9">Result</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<label style="margin-right: 20px;font-weight: normal" >
									    	<input name="khjg" type="radio" value="1" checked="checked"/>通过
									    </label> 
										<label style="font-weight: normal" >
											<input name="khjg" type="radio" value="0" />未通过
										</label>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">下次培训日期</div>
										<div class="font-size-9">Next Date</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control datetimepicker" id="xcpxrq" name="xcpxrq" maxlength="100"  />
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">专业</div>
										<div class="font-size-9">Skill</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input id="zy" class="form-control" name="zy" maxlength="15">
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">备注</div>
										<div class="font-size-9">Remark</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="form-control" id="bz" name="bz" maxlength="300" style="height:55px" ></textarea>
									</div>
								</div>
							<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit.jsp"%><!-- 加载附件信息 -->
							</div>
							<div class="clearfix"></div>
						</form>
					</div>
					</div>
			 <div class="clearfix"></div> 
			 </div>	
				<div class="modal-footer ">
					<div class="col-xs-12 padding-leftright-8" >
						<div class="input-group">
							<span class="input-group-addon modalfootertip" >
				                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
							</span>
		                    <span class="input-group-addon modalfooterbtn">
		                        <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="saveUpdate()" id="baocunButton">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
			                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
							    </button>
		                    </span>
		               	</div><!-- /input-group -->
					</div>
	             </div>
		</div>
	</div>
</div>
<!-------培训计划对话框 End-------->
    <%@ include file="/WEB-INF/views/open_win/course.jsp"%><!-------课程对话框 --------> 
    <%@ include file="/WEB-INF/views/open_win/personel_tree_multi.jsp"%><!-------用户对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/training/record/record_main.js"></script>
	<%@ include file="/WEB-INF/views/open_win/attachments_list_edit.jsp"%><!-------附件对话框 -------->
	<%@ include file="/WEB-INF/views/training/plan/teacher_user.jsp" %><!-- 讲师列表 -->
</body>
</html>