<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>问题整改措施</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var id = "${param.id}";
var pageParam = '${param.pageParam}';
var todo_ywid = "${param.todo_ywid}";
var todo_jd = "${param.todo_jd}";
</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" name="zzjgid" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="zdbmid" name="zdbmid" value="${user.bmdm}" />
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0" >
			  <div  class='searchContent row-height margin-top-0'>
				<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
				   <a href="javascript:void(0);" onclick="batchApproveWin()" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="training:plan:main:01" >
						<div class="font-size-12">反馈</div>
						<div class="font-size-9">Feedback</div>
					</a>
				   <a href="javascript:exportExcel();" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="training:plan:main:06" style="float: left;margin-left:8px;">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a>			
				</div>
				<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
				     <div class='pull-right'>
						<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' id="issued_radio" name='issued_radio' value='1' style='vertical-align:text-bottom'  checked="checked"/>&nbsp;未指派执行人&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' name='type_radio' value='2' style='vertical-align:text-bottom;'/>&nbsp;未填写&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-left:5px;margin-top:6px;font-weight:normal;'>
								<input type='checkbox' name='type_radio' value='1' style='vertical-align:text-bottom'  checked="checked"/>&nbsp;已填写&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' name='zt_radio' value='3' style='vertical-align:text-bottom;'/>&nbsp;已反馈&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' name='zt_radio' value='4' style='vertical-align:text-bottom;'/>&nbsp;已评估&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' name='zt_radio' value='5' style='vertical-align:text-bottom;'/>&nbsp;已验证&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' name='zt_radio' value='9' style='vertical-align:text-bottom;'/>&nbsp;已关闭&nbsp;&nbsp;
							</label>
						</div>
				</div>
				<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>

					<!-- 搜索框start -->
					<div class="col-xs-12 input-group input-group-search">
					         
							<input type="text"  class="form-control " placeholder='问题编号/问题描述'>
		                    <div class="input-group-addon btn-searchnew">
		                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   			</button>
		                    </div>
		                     <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"  id="btn" onclick="search();">
								<div class='input-group'>
								<div class="input-group-addon">
								<div class="font-size-12">更多</div>
								<div class="font-size-9 margin-top-5" >More</div>
								</div>
								<div class="input-group-addon">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
								</div>
								</div>
					   			</button>
		                	</div>
						</div>
					<!-- 搜索框end -->
				
				</div>
				<div class='clearfix'></div>
				<!-- 更多的搜索框 -->
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-bottom-10 display-none border-cccccc" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">审核日期</div>
							<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class='form-control' id="sjShrq_search" name='date-range-picker'/>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">问题类型</div>
							<div class="font-size-9 ">Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="wtlx_search" >
								<option value="">显示全部 All</option>
								<c:forEach items="${auditDiscoverProblemTypeEnum}" var="type">
										<option value="${type.id}">${type.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">类型</div>
							<div class="font-size-9 ">Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="lx_search">
							    <option value="">显示全部 All</option>
								<c:forEach items="${auditDiscoverTypeEnum}" var="type">
										<option value="${type.id}">${type.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">审核类别</div>
							<div class="font-size-9 ">Category</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="shlb_search">
							    <option value="">显示全部 All</option>
								<c:forEach items="${auditnoticeTyepEnum}" var="type">
										<option value="${type.id}">${type.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control "  name="dprtcode" onchange="dprtChange()" >
								<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
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
				<div class='table_pagination'>
				<div id="review_table_div" class='col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set' c-height="45%" style="overflow-x: auto;">
					<table id="review_table" class="table table-thin table-bordered table-striped table-hover table-set" >
						<thead>                              
							<tr>
								<th  class="fixed-column selectAllImg" id="checkAll" style='text-align:center;vertical-align:middle;width:60px;'>
									<a href="#" onclick="performSelectAll()" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
									<a href="#" class="margin-left-5" onclick="performSelectClear()" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
								</th>
								<th class="fixed-column colwidth-7">
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class=" colwidth-7 sorting" onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18" >状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('shwtdbh')" id="shwtdbh_order">
									<div class="font-size-12 line-height-18">问题清单编号</div>
									<div class="font-size-9 line-height-18">Problems No.</div>
								</th>
								<th class="colwidth-5 sorting" onclick="orderBy('lx')" id="lx_order">									
										<div class="font-size-12 line-height-18">类型</div>
										<div class="font-size-9 line-height-18">Type</div>									
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('shdxmc')" id="shdxmc_order" >
									<div class="font-size-12 line-height-18">审核对象</div>
									<div class="font-size-9 line-height-18">Object</div>
								</th>
								<th class="colwidth-9 sorting"  onclick="orderBy('shlb')" id="shlb_order" >
									<div class="font-size-12 line-height-18">审核类别</div>
									<div class="font-size-9 line-height-18">Category</div>
								</th>
								<th class="colwidth-8 sorting" onclick="orderBy('sj_shrq')" id="sj_shrq_order" >									
										<div class="font-size-12 line-height-18">实际审核日期</div>
										<div class="font-size-9 line-height-18">Date</div>									
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('jcnr')" id="jcnr_order" >
									<div class="font-size-12 line-height-18">审核内容</div>
									<div class="font-size-9 line-height-18">Content</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('wtlx')" id="wtlx_order" >
									    <div class="font-size-12 line-height-18">问题类型</div>
										<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('zgjy')" id="zgjy_order" >
									    <div class="font-size-12 line-height-18">整改建议(要求)</div>
										<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('yqfkrq')" id="yqfkrq_order" >
									    <div class="font-size-12 line-height-18">要求反馈日期</div>
										<div class="font-size-9 line-height-18">Date</div>
								</th>								
								<th class="colwidth-7" >
									<div class="font-size-12 line-height-18">附件</div>
									<div class="font-size-9 line-height-18">Attachment</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('whrid')" id="whrid_order">
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('whsj')" id="whsj_order" >
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="colwidth-13" >
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr> 
						</thead>
						<tbody id="table_list">
						   
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
				<!-- <div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="work_card_main_table_pagination"></div> -->
				<div class="clearfix"></div>
				</div>
				<div id='bottom_hidden_content' class='displayContent col-xs-12  padding-left-0 padding-right-0 padding-top-0' style='display:none;'>
			
				<!-- 流程记录 -->
				<%@ include file="/WEB-INF/views/quality/reviewreformmeasures/measures_record.jsp"%>
				 <div class='clearfix'></div>
				</div>
			</div>
		</div>
	</div>
	
	<%@ include file="/WEB-INF/views/quality/reviewreformmeasures/review_reform_measures_add.jsp"%><!-------新增对话框对话框 -------->
	<%@ include file="/WEB-INF/views/quality/reviewreformmeasures/assign_executor.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/reviewreformmeasures/review_reform_measures_main.js"></script>
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
		<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 ------>
</body>
</html>