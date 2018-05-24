<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>课程管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<%@ include file="/WEB-INF/views/open_win/import.jsp"%> 
<script>
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
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-content table-tab-type">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body  padding-bottom-0">
			<!-- 搜索框  -->
			    <div class='searchContent' >
					<div class="margin-top-0">
						<div class=" col-lg-2 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
							<a href="javascript:void(0);" onclick="openWinAdd()" class="btn btn-primary margin-right-10 padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="training:course:main:01" >
								<div class="font-size-12">新增</div>
								<div class="font-size-9">Add</div>
							</a>
							<button type="button"
								class="btn btn-primary padding-top-1 padding-bottom-1  checkPermission" permissioncode="training:course:main:04"
								onclick="showImportModal()">
								<div class="font-size-12">导入</div>
								<div class="font-size-9">Import</div>
							</button>  
							<button type="button"
								class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode='training:course:main:export'
								onclick="exportExcel()">
								<div class="font-size-12">导出</div>
								<div class="font-size-9">Export</div>
							</button>  
						</div>
						
						<div class="col-lg-2 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
							<span class="col-lg-4 col-md-4 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">是/否复训</div>
								<div class="font-size-9">Whether</div>
							</span>
							<div class=" col-lg-8 col-md-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0">
								<input name="isFx_search" style=" vertical-align: middle;   margin-top: -1px;" type="checkbox" value="1" checked="checked" onclick="searchRevision()"/>&nbsp;是
								&nbsp;
								<input name="isFx_search" style=" vertical-align: middle;   margin-top: -1px;" type="checkbox" value="0"  checked="checked" onclick="searchRevision()" />&nbsp;否 
							</div>
						</div>	
						
						<div class="col-lg-2 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
							<span class="col-lg-4 col-md-4 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">机型</div>
								<div class="font-size-9">A/C Type</div>
							</span>
							<div class="col-lg-8 col-md-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0">
								<select class="form-control" id="fjjx_search" name="fjjx_search" onchange="changeJx()"></select>
							</div>
						</div>	
						
						<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
							<span class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</span>
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
								<select id="dprtcode_search" class="form-control " name="dprtcode_search" onchange="changeOrganization()">
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>	
					
						<!-- 搜索框start -->
						<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style="padding-left:15px;">
							<div class="col-xs-12 input-group input-group-search">
								<input type="text" placeholder='类别/代码/名称/目标/形式/备注' id="keyword_search" class="form-control ">
								<div class=" input-group-addon btn-searchnew">   
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
									</button>
								</div> 
							</div>
						</div>
							<!-- 搜索框end -->
						<div class="clearfix"></div>
					</div>
				</div>
				
                <div>
				<div id="course_list_table_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-tab-type_table" style="overflow-x: auto;">
					<table id="course_main_table" class="table table-thin table-bordered table-set" style="min-width: 1000px;">
						<thead>
							<tr>
								<th class="fixed-column colwidth-5" >
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('kclb')" id="kclb_order">
									<div class="important">
										<div class="font-size-12 line-height-18">课程类别</div>
										<div class="font-size-9 line-height-18">Course Type</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('kcbh')" id="kcbh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">课程代码</div>
										<div class="font-size-9 line-height-18">Course No.</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('kcmc')" id="kcmc_order">
									<div class="important">
										<div class="font-size-12 line-height-18">课程名称</div>
										<div class="font-size-9 line-height-18">Course Name</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('fjjx')" id="fjjx_order">
									<div class="font-size-12 line-height-18">机型</div>
									<div class="font-size-9 line-height-18">A/C Type</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('pxmb')" id="pxmb_order">
									<div class="important">
										<div class="font-size-12 line-height-18">培训目标</div>
										<div class="font-size-9 line-height-18">Objective</div>
									</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('ks')" id="ks_order">
									<div class="font-size-12 line-height-18">初训学时</div>
									<div class="font-size-9 line-height-18">Hour</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('pxxs')" id="pxxs_order">
									<div class="important">
										<div class="font-size-12 line-height-18">初训培训形式</div>
										<div class="font-size-9 line-height-18">Form</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('ksxs')" id="ksxs_order">
									<div class="important">
										<div class="font-size-12 line-height-18">初训考试形式</div>
										<div class="font-size-9 line-height-18">Form</div>
									</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('IS_FX')" id="IS_FX_order">
									<div class="font-size-12 line-height-18">是/否复训</div>
									<div class="font-size-9 line-height-18">Whether</div>
								</th>
								<th class="colwidth-10" >
									<div class="font-size-12 line-height-18">复训间隔</div>
									<div class="font-size-9 line-height-18">Interval</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('fxks')" id="fxks_order">
									<div class="font-size-12 line-height-18">复训学时</div>
									<div class="font-size-9 line-height-18">Hour</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('fxpxxs')" id="fxpxxs_order">
									<div class="important">
										<div class="font-size-12 line-height-18">复训培训形式</div>
										<div class="font-size-9 line-height-18">Form</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('fxksxs')" id="fxksxs_order">
									<div class="important">
										<div class="font-size-12 line-height-18">复训考试形式</div>
										<div class="font-size-9 line-height-18">Form</div>
									</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('bz')" id="bz_order">
									<div class="important">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('username')" id="username_order">
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('whsj')" id="whsj_order">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
					<div class="clearfix"></div>
			    </div>
				
				<!-- 点击后显示的内容 -->
				<div id="selectCourse" ></div>
				<div id='fileDiv' class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0" style='display:none;'>
                	<ul id="myTab" class="nav nav-tabs tabNew-style">
                      	<li class="active"><a href="#profile" data-toggle="tab" aria-expanded="false">课件信息 Courseware Information</a></li>
                      	<li class="" ><a id="postTab" href="#PostInfo" data-toggle="tab" aria-expanded="false">涉及岗位 Related To Post</a></li>
                    </ul>
                    <div id="myTabContent" class="tab-content ">
                    	<div class="tab-pane fade active in" id="profile">
							<div id="attachments_list_crud_course">
								<%@ include file="/WEB-INF/views/common/attachments/attachments_list_crud.jsp"%>
							</div>
						</div>
						<div class="tab-pane fade" id="PostInfo">
                      		<%@ include file="/WEB-INF/views/training/course/post_list_view.jsp" %><!-- 岗位列表 -->
                      	</div>
                  </div>
              </div>
		</div>
	</div>	
</div>
<!-------课程对话框 Start-------->
<div class="modal modal-new active in" id="alertForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
	data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width:85%;">
		<div class="modal-content">
			
				<div class="modal-header modal-header-new">
					<h4 class="modal-title">
						<div class="pull-left" id="headChina">
							<div class="font-size-14 " id="modalName">新增</div>
							<div class="font-size-12" id="modalEname">Add</div>
						</div>
						<div class="pull-right">
							<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class="clearfix"></div>
					</h4>
				</div>

			<div class="clearfix"></div>
			<div class="modal-body">
				<div class="col-xs-12">
					
						<form id="form">
						<div class="panel panel-default padding-right-0 margin-top-8" >
							<div class="panel-heading bg-panel-heading">
					        	<div class="font-size-12 ">基础信息</div>
								<div class="font-size-9">Basic Data</div>
						    </div>
							<div class="panel-body padding-bottom-0 padding-left-8 padding-right-8  padding-top-0">
								<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0">
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>课程代码</div>
											<div class="font-size-9 line-height-18">Course No.</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input type="text" class="form-control " id="kcbh" name="kcbh" maxlength="50" />
											<input type="hidden" class="form-control " id="id" />
										</div>
									</div>
								
									<div class=" col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>课程名称</div>
											<div class="font-size-9 line-height-18">Course Name</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input type="text" class="form-control " id="kcmc" name="kcmc" maxlength="100" />
										</div>
									</div>
									
									<div class=" col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>课程类别</div>
											<div class="font-size-9 line-height-18">Course Type</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<select class='form-control' id='kclb' name="kclb" ></select>
										</div>
									</div>
									
									<div class=" col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">机型</div>
											<div class="font-size-9 line-height-18">A/C Type</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<select class="form-control" id="fjjx" name="fjjx"></select>
										</div>
									</div>
									
									<div class="clearfix"></div>
									
									<div class=" col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>初训学时</div>
											<div class="font-size-9 line-height-18">HRS</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0 input-group">
											<div class='input-group' style="position:relative;z-index:1;">
												<input type="text" class="form-control " id="ks" name="ks" onkeyup='clearNoNumTwo(this)' maxlength="10" />
												<span class='input-group-btn'></span>
												<label class='input-group-addon ' style="padding-left:0px;padding-right:0px;border:0px;color:black;background:none;">时</label>
											</div>
										</div>
									</div>
									
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>初训培训形式</div>
											<div class="font-size-9 line-height-18">Training Form</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<select class='form-control' id='pxxs' name="pxxs" ></select>
										</div>
									</div>
									
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3  text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>初训考试形式</div>
											<div class="font-size-9 line-height-18">Form Of Exam</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<select class='form-control' id='ksxs' name="ksxs" ></select>
										</div>
									</div>
								
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>是/否复训</div>
											<div class="font-size-9 line-height-18">Recurrent training</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<label style="margin-right: 20px;font-weight: normal" onclick="checkFx()" >
												<input name="isFx" type="radio" value="1" />是
											</label> 
											<label style="font-weight: normal" onclick="checkFx()">
												<input name="isFx" type="radio" value="0" />否 
											</label> 
										</div>
									</div>
									<div class="clearfix"></div>
									<div id="fxksDiv" class=" col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>复训学时</div>
											<div class="font-size-9 line-height-18">HRS</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0 input-group">
											<input type="text" class="form-control " id="fxks" name="fxks" onkeyup='clearNoNumTwo(this)' maxlength="10" />
											<span class='input-group-addon' style="padding-left:0px;padding-right:0px;border:0px;background:none;">时</span>
										</div>
									</div>
									
									<div id="fxpxxsDiv" class=" col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>复训培训形式</div>
											<div class="font-size-9 line-height-18">Training Form</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<select class='form-control' id='fxpxxs' name="fxpxxs" ></select>
										</div>
									</div>
									
									<div id="fxksxsDiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>复训考试形式</div>
											<div class="font-size-9 line-height-18">Form Of Exam</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<select class='form-control' id='fxksxs' name="fxksxs" ></select>
										</div>
									</div>
									
									<div id="fxjgDiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>复训间隔</div>
											<div class="font-size-9 line-height-18">Interval</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										
										
											<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-0 padding-right-0">
												<input type="text" class="form-control " id="zqz" name="zqz" placeholder='' onkeyup='clearNoNumber(this)' maxlength="3" />
											</div>
											<div class="col-lg-4 col-sm-4 col-xs-4 padding-left-2 padding-right-0">
												<select id='zqdw' name="zqdw" class="form-control">
													<c:forEach items="${cycleEnum}" var="item">
													  <option value="${item.id}" <c:if test="${3 == item.id }">selected="true"</c:if> >${item.name}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
									
									<div class="viewfix col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">维护人</div>
											<div class="font-size-9 line-height-18">Maintainer</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input type="text" id="whr" class="form-control" readonly/>
										</div>
									</div>
								
									<div class="viewfix col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">维护时间</div>
											<div class="font-size-9 line-height-18">Time</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input class="form-control" id="whsj" readonly type="text" />
										</div>
									</div>
									
									<div class="clearfix"></div>
									
									<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group">
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>课程内容</div>
											<div class="font-size-9 line-height-18">Content</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0 ">
											<textarea class="form-control" id="kcnr" name="kcnr" maxlength="600" ></textarea>
										</div>
									</div>
									
									<div class="clearfix"></div>
									
									<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group">
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>培训目标</div>
											<div class="font-size-9 line-height-18">Objective</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0 ">
											<textarea class="form-control" id="pxmb" name="pxmb" maxlength="600" ></textarea>
										</div>
									</div>
									
									<div class="clearfix"></div>
									
									<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-12 line-height-18">教员要求</div>
											<div class="font-size-9 line-height-18">Requirement</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0 ">
											<textarea class="form-control" id="jyyq" name="jyyq" maxlength="600" ></textarea>
										</div>
									</div>
									
									<div class="clearfix"></div>
									
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">培训机构</div>
											<div class="font-size-9 line-height-18">Training</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input type="text" class="form-control " id="pxjg" name="pxjg" maxlength="100" />
										</div>
									</div>
								
									<div class=" col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">来源</div>
											<div class="font-size-9 line-height-18">Source</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input type="text" class="form-control " id="ly" name="ly" maxlength="16" />
										</div>
									</div>
									
									<div class=" col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10" >
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">教材/提纲</div>
											<div class="font-size-9 line-height-18">Outline</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
											<input type="text" class="form-control " id="jctg" name="jctg" maxlength="100" />
										</div>
									</div>
									
									<div class="clearfix"></div>
									
									<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-12 line-height-18">备注</div>
											<div class="font-size-9 line-height-18">Remark</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0 ">
											<textarea class="form-control" id="bz" name="bz" maxlength="300" ></textarea>
										</div>
									</div>
									
								</div>
							</div>
						
							<div class="clearfix"></div>
							</div> 
						</form>
					
					<div class="clearfix"></div>
					<div id="attachments_list_edit">
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="modal-footer">
           		<div class="col-xs-12 padding-leftright-8">
					<div class="input-group">
					<span class="input-group-addon modalfootertip">
						   <i class="glyphicon glyphicon-info-sign alert-info" style="display: none;"></i>
		                   <p class="alert-info-message" title=""></p>
					</span>
	                    <span class="input-group-addon modalfooterbtn">
	                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:save();">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
		                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
						    </button>
	                    </span>
	               	</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-------课程对话框 End-------->

<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<script type="text/javascript" src="${ctx}/static/js/thjw/training/course/course_main.js"></script>
</body>
</html>