<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>审核通知单</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

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
					auditnoticeMain.reload();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			  <div  class='searchContent row-height margin-top-0'>
				<div class="col-lg-2 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
				   <a href="javascript:exportExcel();" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="quality:auditnoticequery:main:01" style="float: left;margin-left:8px;">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a>			
				</div>
				<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
				     <div class='pull-right'>
						<!-- 	<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' id='ydzt1' style='vertical-align:text-bottom'  checked="checked"/>&nbsp;未阅&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' id='ydzt2'  style='vertical-align:text-bottom;' checked="checked"/>&nbsp;已阅&nbsp;&nbsp;
							</label> -->
							<label class='padding-left-5' style='margin-left:5px;margin-top:6px;font-weight:normal;'>
								<input type='checkbox' id='zt1'  style='vertical-align:text-bottom'  checked="checked"/>&nbsp;未接收&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' id='zt2' style='vertical-align:text-bottom;'/>&nbsp;已接收&nbsp;&nbsp;
							</label>
						</div>
				</div>
				<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">审核类别</div>
							<div class="font-size-9">Category</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
							<select class='form-control' id="shlb" >
							    <option value="" >全部</option>
								<option value="11" >初审</option>
								<option value="12" >复审</option>
								<option value="21" >专项审核</option>
							</select>
						</div>
				</div>
				<div class="col-lg-4 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>

					<!-- 搜索框start -->
					<div class="col-xs-12 input-group input-group-search">
					         <div class="input-group-addon btn-searchnew" >
		                    	<div style='color:#333;text-align:right;padding-right:5px;'>
		                    	<div class="font-size-12">审核日期</div>
								<div class="font-size-9 " style='line-height:15px;margin-top:1px;'>Date</div>
		                    	</div>
		                    </div>
							<input type="text"  class="form-control " name='date-range-picker' id="shrq_search">
		                    <div class="input-group-addon btn-searchnew">
		                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="auditnoticeMain.reload();">
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
					<!-- <div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">审核对象</div>
							<div class="font-size-9 ">Object</div>
						</span>
						<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<div class='input-group' style='width:100%'>
								<input id="shdxmc_search" type="text"   onchange="shdxQk()" class="form-control "  />
							    <input id="shdxid_search" type="hidden" class="form-control readonly-style"  readonly/>
								<span class="input-group-btn">
									<button type="button" onclick="openzrdw(true)" class="btn btn-default" data-toggle="modal" >
										<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
							</div>
						</div>
					</div> -->
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control "  name="dprtcode" onchange="dprtChange()" >
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
				<div id="work_card_main_table_top_div" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-h table-set" style="overflow-x: auto;">
					<table id="quality_check_list_table" class="table table-thin table-bordered table-striped table-hover table-set" >
						<thead>                              
							<tr>
								
								<th class="fixed-column colwidth-5">
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="fixed-column colwidth-7 sorting" onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18" >状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<!-- <th class="colwidth-5 sorting" onclick="orderBy('ydzt')" id="ydzt_order">
									<div class="font-size-12 line-height-18">阅读</div>
									<div class="font-size-9 line-height-18">Read</div>
								</th> -->
								<th class="colwidth-8 sorting" onclick="orderBy('jh_shrq')" id="jh_shrq_order" >
									<div class="font-size-12 line-height-18">计划审核日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('jcnr')" id="jcnr_order" >
									<div class="font-size-12 line-height-18">检查内容</div>
									<div class="font-size-9 line-height-18">Content</div>
								</th>
								<th class="colwidth-5" >
									<div class="font-size-12 line-height-18">附件</div>
									<div class="font-size-9 line-height-18">Attachment</div>
								</th>
								<th class="downward  colwidth-15" onclick="auditnoticeMain.getGroupInfoAll()" name="th_return">
									<div class="font-size-12 line-height-18">审核成员</div>
									<div class="font-size-9 line-height-18">Audit member</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('shlb')" id="shlb_order" >
									<div class="font-size-12 line-height-18">审核类别</div>
									<div class="font-size-9 line-height-18">Category</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('jcdbh')" id="jcdbh_order">
									<div class="font-size-12 line-height-18">检查单号</div>
									<div class="font-size-9 line-height-18">Check No.</div>
								</th>
								<th class="colwidth-5 sorting" onclick="orderBy('lx')" id="lx_order">
									<div class="font-size-12 line-height-18">类型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('whrid')" id="whrid_order" >
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('whsj')" id="whsj_order" >
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="colwidth-15" >
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr> 
						</thead>
						<tbody id="auditnotice_list">
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="auditnotice_pagination"></div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/open_win/department.jsp"%><!-- 选择部门 -->
	<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-- 选择人员 -->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/creatingauditnotice/audit_notice.js"></script>
</body>
</html>