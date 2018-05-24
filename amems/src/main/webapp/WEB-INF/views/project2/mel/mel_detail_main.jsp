<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>供应商主数据</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<%@ include file="/WEB-INF/views/open_win/import.jsp"%> 

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
	<input type="hidden" id="userId" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body  padding-bottom-0">
			
				<div class=' margin-top-0 searchContent row-height'>
					<div class=" col-lg-9 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
						<a href="javascript:openWinAdd();"  permissioncode=project:meldetail:main:01  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission">
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a>
					</div>
					
<!-- 					<div style="visibility:hidden;" class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">机型</div>
							<div class="font-size-9 ">A/C Type</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
							<select id="jx_search" class="form-control" onchange="searchRevision()">
								<option value="" selected="selected">显示全部All</option>
							</select>
						</div>
					</div>
					<div style="visibility:hidden;" class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">版本</div>
							<div class="font-size-9">Rev.</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0 label-input-div" >
						</div>
					</div>
 -->					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='机型/版本'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();" style='margin-right:0px !important;'>
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
					</div>
				
				<!-- 搜索框end -->
				
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zt_search" name="zt_search" class="form-control \">
								<option value="" selected="true">显示全部All</option>
								<option value="1" >有效</option>
								<option value="0" >无效</option>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" name="dprtcode_search" class="form-control ">
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
				<div class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-h table-set" >
					<table id="main_list_table" class="table table-thin table-bordered table-set" >
						<thead>                              
							<tr>
								<th class="fixed-column colwidth-5">
									<div class="font-size-12 line-height-18 ">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('jx')" id="jx_order">
									<div class="important">
										<div class="font-size-12 line-height-18">机型 </div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('bb')" id="bb_order">
									<div class="important">
										<div class="font-size-12 line-height-18">版本</div>
										<div class="font-size-9 line-height-18">Rev.</div>
									</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">MEL清单</div>
									<div class="font-size-9 line-height-18">MEL</div>
								</th>
								<th class="colwidth-5">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('username')" id="username_order">
										<div class="font-size-12 line-height-18">审核人</div>
										<div class="font-size-9 line-height-18">Checked By</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">审核时间</div>
									<div class="font-size-9 line-height-18">Check Time</div>
								</th>
								<th class="colwidth-13">
										<div class="font-size-12 line-height-18">批准人</div>
										<div class="font-size-9 line-height-18">Approved By</div>
								</th>
								<th class="colwidth-13" >
									<div class="font-size-12 line-height-18">批准时间</div>
									<div class="font-size-9 line-height-18">Approve Time</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('WHRID')" id="WHRID_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('WHSJ')" id="WHSJ_order">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
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
		</div>
	</div>

	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
	<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/mel/mel_detail_main.js"></script>
	<%@ include file="/WEB-INF/views/project2/mel/mel_detail_edit_win.jsp"%><!-------编辑对话框 -------->
</body>
</html>