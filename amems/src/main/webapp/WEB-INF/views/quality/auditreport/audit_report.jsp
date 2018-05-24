<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>质量审核报告</title>
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
					auditReportItemList.decodePageParam();//调用主列表页查询
				}
			}
		});
	});
</script>


</head>
<body class="page-header-fixed">
	<div class="page-content">
		<div class="panel panel-primary" id="panel">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			  <div  class='searchContent row-height margin-top-0'>
				<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
				    <a href="javascript:void(0);" onclick="auditReportItemList.openAddView(this,'adds',event);" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="quality:qualityreviewreport:main:01" >
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a>&nbsp;&nbsp;
				   
				   <a href="javascript:void(0);" onclick="openWinView()" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left margin-left-8 checkPermission" permissioncode="quality:qualityreviewreport:main:06" >
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a>
					<div class="pull-right padding-left-0 padding-right-0 margin-top-0" >
				    
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' onchange="auditReportItemList.decodePageParam();" checked="checked" name='type_radio' value='0' style='vertical-align:text-bottom;'/>&nbsp;保存&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' onchange="auditReportItemList.decodePageParam();" name='type_radio' checked="checked" value='1' style='vertical-align:text-bottom;'/>&nbsp;提交&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' onchange="auditReportItemList.decodePageParam();" name='type_radio' checked="checked" value='5&6' style='vertical-align:text-bottom;'/>&nbsp;驳回&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' onchange="auditReportItemList.decodePageParam();" name='type_radio' checked="checked" value='2' style='vertical-align:text-bottom;'/>&nbsp;已审核&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' onchange="auditReportItemList.decodePageParam();" name='type_radio' checked="checked" value='3' style='vertical-align:text-bottom;'/>&nbsp;已审批&nbsp;&nbsp;
							</label>
						
				 </div>		
				</div>
				
				<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >						
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核对象</div>
								<div class="font-size-9 ">Object</div>
						</span>				
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
							<div class='input-group' style='width:100%'>
							<input type="hidden"  id="quality_audit_module_search_shdxid" >
							<input type="hidden"  id="quality_audit_module_search_shdxbh" >
							<input type="hidden"  id="quality_audit_module_search_shdxmc" >
						    <input type="text"    id="quality_audit_module_search_shdx" class="form-control"   maxlength="20"  />
							<span class="input-group-btn">
								<button type="button"  class="btn btn-default" data-toggle="modal" onclick="auditReportItemList.openzrdw('shdx',null,'search',null)">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						    </div>
						</div>		
				</div>
				<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>

					<!-- 搜索框start -->
					<div class="col-xs-12 input-group input-group-search">
					         
							<input type="text" id="keyword_search" class="form-control " placeholder='审核报告编号/审核概述'>
		                    <div class="input-group-addon btn-searchnew">
		                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="auditReportItemList.decodePageParam();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   			</button>
		                    </div>
		                     <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"  id="btn" onclick="auditReportItemList.getMore();">
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
							<input class='form-control' id="shrq_search" name='date-range-picker'/>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">类型</div>
							<div class="font-size-9 ">Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="lx_search" onchange="auditReportItemList.decodePageParam();">
							    <option value="">全部</option>
								<option value="1">内部</option>
								<option value="2">外部</option>
							</select>
						</div>
					</div>
					
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">审核类别</div>
							<div class="font-size-9 ">Category</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="shlb_search" onchange="auditReportItemList.decodePageParam();">
							    <option value="">全部</option>
								<option value="10">计划审核</option>
								<option value="20">非计划审核</option>
							</select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control "  name="dprtcode" onchange="auditReportItemList.decodePageParam();" >
								<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="auditReportItemList.resert();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class="clearfix"></div>
				</div>
				<div id="audit_report_main_table_top_div" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-h table-set" style="overflow-x: auto;">
					<table id="audit_report_main_table" class="table table-thin table-bordered table-striped table-hover table-set" >
						<thead>                              
							<tr>
								
								<th class="fixed-column colwidth-7">
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="fixed-column colwidth-7" onclick="auditReportItemList.orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18" >状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th class="colwidth-13 sorting" onclick="auditReportItemList.orderBy('zlshbgbh')" id="zlshbgbh_order" name="column_jx">
									<div class="important">
									<div class="font-size-12 line-height-18">审核报告编号</div>
									<div class="font-size-9 line-height-18">Audit report No.</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="auditReportItemList.orderBy('shgy')" id="shgy_order" name="column_jx">
									<div class="important">
									<div class="font-size-12 line-height-18">审核概述</div>
									<div class="font-size-9 line-height-18">Overview</div>
									</div>
								</th>
								<th class="colwidth-5 sorting" onclick="auditReportItemList.orderBy('lx')" id="lx_order" name="column_gkh">
									
										<div class="font-size-12 line-height-18">类型</div>
										<div class="font-size-9 line-height-18">Type</div>
									
								</th>
								<th class="colwidth-9 sorting" onclick="auditReportItemList.orderBy('shdxmc')" id="shdxmc_order" name="column_bb" >
									<div class="font-size-12 line-height-18">审核对象</div>
									<div class="font-size-9 line-height-18">Object</div>
								</th>
								<th class="colwidth-9 sorting" onclick="auditReportItemList.orderBy('shlb')" id="shlb_order" name="column_bb" >
									<div class="font-size-12 line-height-18">审核类别</div>
									<div class="font-size-9 line-height-18">Category</div>
								</th>
								<th class="colwidth-8 sorting" onclick="auditReportItemList.orderBy('shrq')" id="shrq_order" name="column_gzbt" >
									    <div class="font-size-12 line-height-18">审核日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									
								</th>
								<th class="colwidth-10 sorting" onclick="auditReportItemList.orderBy('shcy')" id="shcy_order" name="column_wxxmbh" >
									    <div class="font-size-12 line-height-18">审核成员</div>
										<div class="font-size-9 line-height-18">Audit member</div>
								</th>
								<th class="colwidth-5" >
									<div class="font-size-12 line-height-18">附件</div>
									<div class="font-size-9 line-height-18">Attachment</div>
								</th>
								<th class="colwidth-10 sorting" onclick="auditReportItemList.orderBy('realname')" id="realname_order" name="column_username" >
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="colwidth-10 sorting" onclick="auditReportItemList.orderBy('whsj')" id="whsj_order" name="column_whsj" >
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="colwidth-15" >
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr> 
						</thead>
						<tbody id="audit_report_main_table_list">
	
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="auditdreport_main_table_pagination"></div>
				<div class="clearfix"></div>
				<div id='bottom_hidden_content' class='displayContent col-xs-12  padding-left-0 padding-right-0 padding-top-0'  style="display: none">
				<div class="panel panel-primary" style='margin-bottom: 0px;'>
					<div class="panel-heading bg-panel-heading">
						<div class="font-size-12 ">流程记录</div>
						<div class="font-size-9">Process record</div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0">
						<div class="col-lg-12 col-md-12 padding-leftright-8"
							style="overflow-x: auto;" id="course_list_table_div">
							<table id="change_record_table"
								class="table table-thin table-bordered table-striped table-hover table-set">
								<thead>
									<tr>
										<th class="colwidth-6"
											style="vertical-align: middle; display: table-cell;">
											<div class="font-size-12 line-height-18">操作人</div>
											<div class="font-size-9 line-height-18">Operation</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">操作时间</div>
											<div class="font-size-9 line-height-18">Times</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">操作说明</div>
											<div class="font-size-9 line-height-18">Description</div>
										</th>						

									</tr>
								</thead>
								<tbody id="processRecord_list">
								</tbody>
							</table>
						</div>
					</div>
				</div>



			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/quality/auditreport/audit_report_add.jsp"%><!-------新增对话框对话框 -------->
	<%@ include file="/WEB-INF/views/open_win/department.jsp"%><!-- 选择部门 -->
	<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 -------->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
    <script type="text/javascript" src="${ctx}/static/js/thjw/quality/auditreport/audit_main.js"></script><!--当前js  -->

</body>
</html>