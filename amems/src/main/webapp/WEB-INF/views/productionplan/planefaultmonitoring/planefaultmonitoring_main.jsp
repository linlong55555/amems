<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css"
	type="text/css">
		<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script type="text/javascript"
	src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Home</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%><style type="text/css">
.bootstrap-tagsinput {
  width: 100% !important;
}
</style>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body >
<input type="hidden" id="zddwid" value="${user.bmdm}" />
<input type="hidden" id="gbrid" value="${sessionScope.user.id}" />
<input type="hidden" id="username" value="${sessionScope.user.username}" />
<input type="hidden" id="realname" value="${sessionScope.user.realname}" />
		<div class="page-content ">
			<div class="panel panel-primary" >
				<div class="panel-heading  "> 
				<div id="NavigationBar"></div>
				</div>
				<div class="panel-body col-xs-12 padding-bottom-0">
				
				<div class="col-xs-2 padding-left-0">
					<button class="btn btn-primary padding-top-1 padding-bottom-1 row-height checkPermission"
					permissioncode='productionplan:planefaultmonitoring:main:01' type="button" onclick="add();">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</button>
					<button class="btn btn-primary padding-top-1 padding-bottom-1 row-height" type="button" onclick="exportExcel();">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">export</div>
					</button>
				</div>
				
				
				<div class=" pull-right padding-left-0 padding-right-0" >
					<div class="pull-left">
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</div>
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="padding-left-8 pull-left" style="width: 200px; margin-right:5px;">
							   <select class='form-control' id='fjzch' name="fjzch">
							 </select>
							</div>
						</div>
					</div>
				<div class="pull-left">
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</div>
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="padding-left-8 pull-left" style="width: 230px; margin-right:5px;">
							   <select id="dprtcode" class="form-control ">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
							</div>
						</div>
                    </div>
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='飞机注册号/故障信息' id="keyword"
							class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button"
							class=" btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchFjgzRecord();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
						</button>
                    </div> 
				</div>

				<div class="clearfix"></div>

				<div class="col-xs-12 text-center padding-left-0 padding-right-10 margin-top-10 "style="overflow-x: auto;"
					>
					<table  style="min-width: 1000px;"
						class="table table-thin table-bordered table-set" id="fjgz_record_sheet_table">
						<thead>
							<tr>
								<th class="colwidth-7 ">
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div></th>

								<th class="colwidth-7  "><div class="important">
									<div class="font-size-12 line-height-18">飞机注册号</div>
									<div class="font-size-9 line-height-18">A/C REG</div></div></th>
								<th class="colwidth-20 sorting" onclick="orderBy('gzxx')"
									id="gzxx_order"><div class="important">
									<div class="font-size-12 line-height-18">故障信息</div>
									<div class="font-size-9 line-height-18">Fault info</div></div></th>
								<th class="colwidth-7  " >
										<div class="font-size-12 line-height-18">故障次数</div>
										<div class="font-size-9 line-height-18">Failure times</div>
								</th>
								<th class="colwidth-9 " >
									<div class="font-size-12 line-height-18">最近发生日期</div>
									<div class="font-size-9 line-height-18">Latest date</div></th>
								<th class="colwidth-5 sorting" onclick="orderBy('gbzt')"
									id="gbzt_order">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div></th>
								<th class="colwidth-13 downward cursor-pointer" onclick="vieworhideZjqkContent()" name="fj">
										<div class="font-size-12 line-height-18">附件</div>
										<div class="font-size-9 line-height-18">Attachment</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('bz')"
									id="bz_order">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div></th>
								<th class="colwidth-13 sorting" onclick="orderBy('zdrid')" id="zdrid_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('zdsj')" id="zdsj_order">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="colwidth-9 ">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="fjgzjk_list">

						</tbody>

					</table>
				</div>
				
				<div class=" col-xs-12 text-center page-height padding-right-0 padding-left-0"  id="fjgzjk_pagination"></div>
				<div class="clearfix"></div>
            <div id='dcgzcl_div' style="display:none; auto;">
             <%@ include file="/WEB-INF/views/productionplan/planefaultmonitoring/planefaultmonitoring_mainone.jsp"%> 
            </div>
			</div>
			<div class="clearfix"></div>
			</div>
			<!-- 关闭对话框 -->
	<div class="modal fade" id="alertModalFjgzjk" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:550px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
							<div class="font-size-16 line-height-18">关闭</div>
							<div class="font-size-9 ">Close</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12 col-sm-12 padding-left-0 padding-right-0">
			            	<input id="gbyyid" type="hidden">
			            	<div class="col-xs-12 col-sm-12 col-lg-12 padding-right-0 padding-left-0 margin-top-10 margin-bottom-10 ">
								<label class="pull-left col-lg-2 col-xs-2 col-sm-2 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12">关闭人</div>
									<div class="font-size-9">Person</div>
								</label>
								<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
									<input type="text" id="gbrname" readonly="readonly" class="form-control " />
									
								</div>
							</div> 
							<div id="gb" class="col-xs-12 col-sm-12 col-lg-12 padding-right-0 padding-left-0  margin-bottom-10 ">
								<label class="pull-left col-lg-2 col-xs-2 col-sm-2 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12">关闭时间</div>
									<div class="font-size-9">Date</div>
								</label>
								<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
									<input type="text" id="gbsj" readonly="readonly" class="form-control" />
									
								</div>
							</div> 
			            	<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18"><span style="color: red" id="gbyymark">*</span>关闭原因</div>
								<div class="font-size-9 line-height-18">Reason</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="gbyy"    maxlength="150" ></textarea>
							</div>
							    
					     		<div class="clearfix"></div>
					     		<div class="text-right margin-top-10 padding-buttom-10 ">
                               <button onclick="zdjsOver()" id="qd" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-0" data-dismiss="modal"><div
										class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div></button>
                     	      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-0" data-dismiss="modal">
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
	</div>
	
	<!-- END CONTENT -->
</div>
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/planefaultmonitoring/planefaultmonitoring_main.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>