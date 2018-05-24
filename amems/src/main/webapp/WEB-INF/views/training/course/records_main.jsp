<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Home</title>
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<%@ include file="/WEB-INF/views/common_new.jsp"%>
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
					//$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
					Personel_Tree_Multi_Modal.searchRevision();
				}else{
					searchFjgzRecord();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<script src="${ctx }/static/js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
<body >
<input type="hidden" id="zddwid" value="${user.bmdm}" />
<input type="hidden" id="gbrid" value="${sessionScope.user.id}" />
<input type="hidden" id="username" value="${sessionScope.user.username}" />
<input type="hidden" id="realname" value="${sessionScope.user.realname}" />
<div class="page-content table-table-type">
		<div class="panel panel-primary" >
			<div class="panel-heading  "> 
				<div id="NavigationBar"></div>
			</div>
			<div class="panel-body padding-bottom-0">
				<div  class='searchContent row-height'>
					<div class="margin-top-0" >
						<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
							<a href="#" onclick="exportExcel()"  class="btn btn-primary padding-top-1 margin-left-0 padding-bottom-1 pull-left checkPermission" permissioncode='training:course:records:export' >
								<div class="font-size-12">导出</div>
								<div class="font-size-9">Export</div>
							</a>
						</div>
						<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
							<span class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">培训讲师</div>
								<div class="font-size-9">Lecturer</div>
							</span>
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
								<input placeholder="讲师" id="jsxms" name="jsxms" class="form-control " onchange="searchFjgzRecord()" />
							</div>
						</div>
		
						<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group" >
							 <span class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
								 <div class="font-size-12">状态</div>
								<div class="font-size-9">Status</div>
							</span>
							<div  class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
								<select type="text" class="form-control "  id="zt" onchange="searchFjgzRecord();">
									<option value="" selected="true">显示全部 All</option>
									<option value="1" >下发</option>
									<option value="10" >完成</option>
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style="padding-left:15px;">
							<div class="col-xs-12 input-group input-group-search">
								<input type="text" placeholder="课程/地点/备注" class="form-control" id="keyword" />
			                    <div class="input-group-addon btn-searchnew">
			                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchFjgzRecord();" style="margin-right:0px !important;">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                  		</button>
			                    </div>
			                    
			                    <div class="input-group-addon btn-searchnew-more">
				                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight" id="btn" onclick="search();">
									<div class="input-group">
										<div class="input-group-addon">
											<div class="font-size-12">更多</div>
											<div class="font-size-9 margin-top-5">More</div>
										</div>
										<div class="input-group-addon">
											 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
										</div>
									</div>
						   			</button>
			                	</div>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
					<!------------搜索框end------->
					<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0 search-height" id="divSearch">
					
						<div class="clearfix"></div>
			
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8">
							<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">培训日期</div>
								<div class="font-size-9">Training Date</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control " name="date-range-picker" id="pxDate_search" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
							<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								 <div class="font-size-12">培训类别</div>
								 <div class="font-size-9">Training Type</div>
							</span>
							<div  id="pxlbs" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select type="text" class="form-control "  id="selectpxlb"  />
										<option value="" selected="true">显示全部 All</option>
								</select>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
							<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">计划类型</div>
								<div class="font-size-9">Training Type</div>
							</span>
							<div id="jhlxs" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select type="text" class="form-control "  id="jhlx">
										<option value="" selected="true">显示全部All</option>
										<c:forEach items="${trainingPlanTypeEnum}" var="item">
										  <option value="${item.id}" >${item.name}</option>
										</c:forEach>
								</select>
							</div>
						</div>
		
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8">
							<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">初/复训标识</div>
								<div class="font-size-9">Whether</div>
							</span>
							<div  class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input name="fxbs_search" type="checkbox" value="1" checked="checked" />&nbsp;是
									&nbsp;
								<input name="fxbs_search" type="checkbox" value="2"  checked="checked" />&nbsp;否 
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
							<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
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
				</div>
			
				<div class="clearfix"></div>
				<div class='table_pagination'>
		
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" style="overflow-x: auto;">
						<table
							class="table table-thin table-bordered   table-set " id="fjgz_record_sheet_table">
							<thead>
								<tr>
									<th class="fixed-column colwidth-5 ">
										<div class="font-size-12 line-height-18">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="colwidth-10 sorting"  onclick="orderBy('jhlx')" id="jhlx_order">
											<div class="font-size-12 line-height-18">计划类别</div>
											<div class="font-size-9 line-height-18">Plan Type</div>
									</th>
									<th class="colwidth-10 sorting"  onclick="orderBy('zt')" id="zt_order">
											<div class="font-size-12 line-height-18">状态</div>
											<div class="font-size-9 line-height-18">Status</div>
									</th>
									<th class="colwidth-10 sorting"  onclick="orderBy('kcbh')" id="kcbh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">课程代码</div>
											<div class="font-size-9 line-height-18">Business No.</div>
										</div>
									</th>
									<th class="colwidth-15 sorting"  onclick="orderBy('kcmc')" id="kcmc_order">
										<div class="important">
											<div class="font-size-12 line-height-18">课程名称</div>
											<div class="font-size-9 line-height-18">Business Name</div>
										</div>
									</th>
									<th class="colwidth-15 sorting"  onclick="orderBy('fjjx')" id="fjjx_order">
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</th>
									<th class="colwidth-15 sorting"  onclick="orderBy('pxjg')" id="pxjg_order">
											<div class="font-size-12 line-height-18">培训机构</div>
											<div class="font-size-9 line-height-18">Business Name</div>
									</th>
									<th class="colwidth-15 sorting"  onclick="orderBy('kcdd')" id="kcdd_order">
										<div class="important">
											<div class="font-size-12 line-height-18">培训地点</div>
											<div class="font-size-9 line-height-18">Business Location</div>
										</div>
									</th>
									<th class="colwidth-10 sorting"  onclick="orderBy('fxbs')" id="fxbs_order">
										<div class="font-size-12 line-height-18">初/复训标识</div>
										<div class="font-size-9 line-height-18">Whether</div>
									</th>
									<th class="colwidth-22 sorting"  onclick="orderBy('jh_ksrq')" id="jh_ksrq_order">
										<div class="font-size-12 line-height-18">培训日期</div>
										<div class="font-size-9 line-height-18">Business Date</div>
									</th>
									<th class="colwidth-22 sorting"  onclick="orderBy('sj_ksrq')" id="sj_ksrq_order">
										<div class="font-size-12 line-height-18">实际日期</div>
										<div class="font-size-9 line-height-18">Actual Date</div>
									</th>
									<th class="colwidth-15 sorting"  onclick="orderBy('jsxm')" id="jsxm_order">
										<div class="font-size-12 line-height-18">培训讲师</div>
										<div class="font-size-9 line-height-18">Business Lecturer</div>
									</th>
									<th class="colwidth-10 sorting"  onclick="orderBy('pxlb')" id="pxlb_order">
										<div class="font-size-12 line-height-18">培训类别</div>
										<div class="font-size-9 line-height-18">Business Type</div>
									</th>
									<th class="colwidth-10 sorting"  onclick="orderBy('pxxs')" id="pxxs_order">
										<div class="font-size-12 line-height-18">培训形式</div>
										<div class="font-size-9 line-height-18">Business Form</div>
									</th>
									<th class="colwidth-10 sorting"  onclick="orderBy('is_jcff')" id="is_jcff_order">
										<div class="font-size-12 line-height-18">教材发放</div>
										<div class="font-size-9 line-height-18">Issued</div>
									</th>
									<th class="colwidth-10 sorting"  onclick="orderBy('ksxs')" id="ksxs_order">
										<div class="font-size-12 line-height-18">考核形式</div>
										<div class="font-size-9 line-height-18">From</div>
									</th>
									<th class="colwidth-15 sorting"  onclick="orderBy('kcnr')" id="kcnr_order">
										<div class="font-size-12 line-height-18">培训内容</div>
										<div class="font-size-9 line-height-18">Business Content</div>
									</th>
									<th class="colwidth-15 sorting"  onclick="orderBy('ks')" id="ks_order">
										<div class="font-size-12 line-height-18">培训课时</div>
										<div class="font-size-9 line-height-18">Business Class</div>
									</th>
									<th class="colwidth-10 sorting"  onclick="orderBy('xt')" id="xt_order">
										<div class="font-size-12 line-height-18">学天</div>
										<div class="font-size-9 line-height-18">Learn days</div>
									</th>
									 <th class="colwidth-10 sorting"  onclick="orderBy('sjks')" id="sjks_order">
										<div class="font-size-12 line-height-18">完成课时</div>
										<div class="font-size-9 line-height-18">Finish Lesson</div>
									</th> 
									<th class="colwidth-10 sorting"  onclick="orderBy('ycrs')" id="ycrs_order">
										<div class="font-size-12 line-height-18">应参培人数</div>
										<div class="font-size-9 line-height-18">Num</div>
									</th>
									<th class="colwidth-10 sorting"  onclick="orderBy('scrs')" id="scrs_order">
										<div class="font-size-12 line-height-18">实际参培人数</div>
										<div class="font-size-9 line-height-18">Num</div>
									</th>
									<th class="colwidth-15 sorting"  onclick="orderBy('bz')" id="bz_order">
										<div class="important">
											<div class="font-size-12 line-height-18">备注</div>
											<div class="font-size-9 line-height-18">Remark</div>
										</div>
									</th>
									
									<th   class="sorting colwidth-15"  onclick="orderBy('JUSERNAME')" id="JUSERNAME_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th  class="sorting colwidth-15"  onclick="orderBy('whsj')" id="whsj_order">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintainer time</div>
									</th>
									<th  class="sorting colwidth-15" id="dprtcode_order" onclick="orderBy('dprtcode')">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
							<tbody id="fjgzjk_list">

							</tbody>
						</table>
					</div>
					<div class=" col-xs-12  text-center page-height	 padding-left-0 padding-right-0"  id="fjgzjk_pagination"></div>
					<div class="clearfix"></div>
				</div>
				
				<div class="displayContent" id="bottom_hidden_content" style='height: 113px; display: none;'>
					<div id="selectCourse"></div>
					<%@ include file="/WEB-INF/views/training/course/recordspersonnel_main.jsp"%> 
				</div>
			</div>
		</div>
</div>

   <%@ include file="/WEB-INF/views/open_win/personel_tree_multi.jsp"%><!-------用户对话框 -------->
	<input type="hidden" class="form-control " id="kcid" name="kcid" />
	<input type="hidden" class="form-control " id="kcnewid" name="kcnewid" />
	<input type="hidden" class="form-control " id="kcbm" name="kcbm" />
	<input type="hidden" class="form-control " id="id" name="id" />
	<input type="hidden" class="form-control " id="fxbs" name="fxbs" />
	<input type="hidden" class="form-control " id="pxlb" name="pxlb" />
	<input type="hidden" class="form-control " id="fjjx" name="fjjx" />
	<input type="hidden" class="form-control " id="zy" name="zy" />
	<input type="hidden" class="form-control " id="pxjg" name="pxjg" />
<!-- start新增修改提示框 -->
<div class="modal fade in modal-new active" id="alertModaladdupdate"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" tabindex="-1" >
	<div class="modal-dialog" style="width:85%;">
		<div class="modal-content">
			<div class="modal-header modal-header-new">
				<h4 class="modal-title">
					<div class="pull-left">
						<div class="font-size-14 "><span id="accredit">新增</span></div>
						<div class="font-size-12"><span id="accreditrec">Add</span></div>
					 </div>
					<div class="pull-right">
						<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close" onclick="saveColse();" ></button>
					</div>
					<div class="clearfix"></div>
				</h4>
			</div>
			<div class="modal-body padding-bottom-0" id="alertBody">
				<form id="form1">
					<div class="col-xs-12">
						<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">
							<div class="input-group-border margin-top-8 padding-left-0" >
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-8 margin-top-10 padding-right-8  form-group">
									<label class="pull-left col-lg-4 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">课程代码</div>
										<div class="font-size-9">Business No.</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										<a style="font-weight: normal" href='javascript:void(0);'  onclick="findCourses()" id="dgbh"></a>
										<input type="hidden" class="form-control " id="businessid" name="businessid" />
									</div>
								</div>
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-8 margin-top-10 padding-right-8  form-group">
									<label class="pull-left col-lg-4 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">课程名称</div>
										<div class="font-size-9">Business Name</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " maxlength="50" id="kcmc" name="kcmc" disabled="disabled"/>
									</div>
								</div>
								
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-8 margin-top-10 padding-right-8  form-group">
									<label class="pull-left col-lg-4 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">机型</div>
										<div class="font-size-9">A/C Type</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " maxlength="50" id="fjjx_mod" name="fjjx_mod" disabled="disabled"/>
									</div>
								</div>
									
									<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-8 padding-right-8 margin-top-10 margin-bottom-10 form-group" >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span style="color: red">*</span>课程开始日期</div>
											<div class="font-size-9 line-height-18">Start Date</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<div class='input-group' style="position:relative;z-index:1;">
											  <input type="text" class='form-control datetimepicker' id="sjKsrq" name="sjKsrq"  onchange="changedate()"/>
											  <span class='input-group-btn'> </span>
											  <input class='form-control datetimepicker' type='text' style='width:60px;' id='sjKssj' name='sjKssj' readonly/>
											</div>
										</div>
									</div>
								<div class="clearfix"></div>	
									<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-8 padding-right-8 margin-bottom-10 form-group " >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">课程结束日期</div>
											<div class="font-size-9 line-height-18">End Date</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<div class='input-group' style="position:relative;z-index:1;">
											  <input type="text" class='form-control datetimepicker' id="sjJsrq" name="sjJsrq" />
											  <span class='input-group-btn'> </span>
											  <input class='form-control datetimepicker' type='text' style='width:60px;' id='sjJssj' name='sjJssj' readonly/>
											</div>
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
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-8 padding-right-8 margin-bottom-10 form-group " >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">培训地点</div>
											<div class="font-size-9 line-height-18">Location</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<input class='form-control' id='kcdd' name="kcdd"   maxlength="100" />
										</div>
									</div>
									
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-8 padding-right-8 margin-bottom-10 form-group " >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">培训形式</div>
											<div class="font-size-9 line-height-18">Form</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<select class='form-control' id='pxxs' name="pxxs" >
											</select>
										</div>
									</div>
									<div class="clearfix"></div>
									<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-8 padding-right-8 margin-bottom-10 form-group " >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">考试形式</div>
											<div class="font-size-9 line-height-18">Form</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
											<select class='form-control' id='ksxs' name="ksxs" >
											</select>
										</div>
									</div>
									
								
								
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-8  padding-right-8  form-group">
									<label class="pull-left col-lg-4 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">是否颁证</div>
										<div class="font-size-9">Certificate</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<label style="margin-right: 20px;font-weight: normal" >
											<input name="jsBz" type="radio" value="1" checked="checked" />是
										</label> 
										<label style="font-weight: normal" >
											<input name="jsBz" type="radio" value="0"  />否
										</label> 
									</div>
								</div>
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-8  padding-right-8  form-group">
									<label class="pull-left col-lg-4 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">教材发放</div>
										<div class="font-size-9">Material</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										<label style="margin-right: 20px;font-weight: normal" >
											<input name="isJcff" type="radio" value="1" checked="checked" />是
										</label> 
										<label style="font-weight: normal" >
											<input name="isJcff" type="radio" value="0"  />否
										</label> 
									</div>
								</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-8 padding-right-8  form-group " >
										<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span style="color: red">*</span>实际课时</div>
											<div class="font-size-9 line-height-18">Practical Hours</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
											<div class='input-group' style="position:relative;z-index:1;">
												<input type="text" class="form-control " id="sjks" name="sjks" onkeyup='clearNoNumTwo(this)' maxlength="10" />
											  <span class='input-group-btn'> </span>
												<label class='input-group-addon ' style="padding-left:0px;padding-right:0px;border:0px;color:black;background:none;">时</label>
											</div>
										</div>
									</div>
									
								<div class="clearfix"></div>
								<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-8 margin-top-0 padding-right-8  form-group">
									<label class="pull-left col-lg-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">培训内容</div>
										<div class="font-size-9">Content</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
										<textarea class="form-control" id="kcnr"   maxlength="300" style="height:55pxd"></textarea>
									</div>
								</div>
								
								<div class="clearfix"></div>
								<div class="col-xs-12 padding-left-8 padding-right-8 form-group">
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">培训人员</div>
										<div class="font-size-9">Select People</div><br/>
										<a href="#" onclick="showImportModal()" class="btn btn-primary padding-top-1 padding-bottom-1 pull-right " >
						                <div class="font-size-12">导入</div>
						                <div class="font-size-9">Import</div>
					                    </a>
					                    <div class="clearfix"></div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="col-lg-12 col-sm-12 col-xs-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto;">
											<!-- start:table -->
											<table class="table table-thin table-bordered table-striped table-hover text-center table-set padding-right-0" style="min-width:1250px">
												<thead>
													<tr>
													<th class="colwidth-5" style="z-index: 9999 ! important;">
														<button type="button"  class="line6 line6-btn" data-toggle="modal" onclick="openPersonelWin()">
																<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
														</button>
													</th>
													<th class="colwidth-5">
														<div class="font-size-12 line-height-18">序号</div>
														<div class="font-size-9 line-height-18">No.</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 line-height-18">人员代码</div>
														<div class="font-size-9 line-height-18">Personnel Code</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 line-height-18">人员</div>
														<div class="font-size-9 line-height-18">Personnel</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 line-height-18">工作单位/部门</div>
														<div class="font-size-9 line-height-18">Work/Dept</div></th>
													<th class="colwidth-5">
														<div class="font-size-12 line-height-18">应参</div>
														<div class="font-size-9 line-height-18">Should</div>
													</th>
													<th class="colwidth-5">
														<div class="font-size-12 line-height-18">实参</div>
														<div class="font-size-9 line-height-18">Real</div>
													</th>
													<th class="colwidth-7">
														<div class="font-size-12 line-height-18">出勤率%</div>
														<div class="font-size-9 line-height-18">Attendance%</div>
													</th>
													<th class="colwidth-5">
														<div class="font-size-12 line-height-18">成绩</div>
														<div class="font-size-9 line-height-18">Result</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 line-height-18">考核结果</div>
														<div class="font-size-9 line-height-18">Result</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 line-height-18">证书</div>
														<div class="font-size-9 line-height-18">Certificate</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 line-height-18">下次培训日期</div>
														<div class="font-size-9 line-height-18">Next Date</div>
													</th>
													<th class="colwidth-13">
														<div class="font-size-12 line-height-18">备注</div>
														<div class="font-size-9 line-height-18">Remark</div>
													</th>
													<th class="colwidth-30" colspan="3">
														<div class="font-size-12 line-height-18">附件</div>
														<div class="font-size-9 line-height-18">Attachment</div>
													</th>
													 </tr>
												</thead>
												<tbody id="messageListTable">
												
												</tbody>
											</table>
											<!-- end:table -->
										</div>
									</div>
								</div>
								<div class="clearfix"></div>
							 </div> 
						</div>
						<div class="clearfix"></div>
						<div id="attachments_list_edit">
							<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
						</div>
					</div>
				</form>
			</div>
			<div class="clearfix"></div>
			<div class="modal-footer ">
				<div class="col-xs-12 padding-leftright-8">
					<div class="input-group">
						<span class="pull-left modalfootertip">
				               <i class="glyphicon glyphicon-info-sign alert-info" style="display: none;"></i><p class="alert-info-message"></p>
						</span>
					 	<span class="input-group-addon modalfooterbtn">
						   	<button id="baocuns" type="button" onclick="saveUpdate();" class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
							</button>
							<button type="button" onclick="saveColse();" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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
	<!-- end新增修改提示框-->
<script type="text/javascript" src="${ctx}/static/js/thjw/training/course/records_main.js"></script>
<%@ include file="/WEB-INF/views/training/plan/teacher_user.jsp" %><!-- 讲师列表 -->
<%@ include file="/WEB-INF/views/open_win/import.jsp"%> <!-- 导入模态框 -->
</body>
</html>