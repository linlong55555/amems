<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>适航性资料</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<link rel="stylesheet" href="${ctx}/static/lib/cropper/cropper.css"> <!-------上传图片插件样式 -------->
<script type="text/javascript" src="${ctx}/static/lib/cropper/cropper.js"></script><!-------上传图片插件js -------->
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


	<div class="page-content">
		<div class="panel panel-primary">
		    <!-- panel-heading -->
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<!-- 搜索框 -->
			    <div  class='searchContent row-height margin-top-0'>
			    
			    
			    <!-- 上传导出按钮 start-->
				    <div class=" col-lg-2 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0 form-group  ">
						<a href="javascript:add();" permissioncode="training:faculty:main:add" class=" btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission">
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a> 
						<a type="button" class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission" permissioncode="" style="display:none;">
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</a>
					</div>
			    <!-- 上传导出按钮end -->
			     <div class="col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group" >
					
				</div> 
				<div class="col-lg-2 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
			 		<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
						<div class="font-size-12">归属</div>
						<div class="font-size-9">Attribution</div>
					</span>
					<div class='col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div'>
						<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'><input type='checkbox' id="wbbs1" name="wbbs" style='vertical-align:text-bottom;' checked="checked" onchange="searchRevision();"/>&nbsp;内部&nbsp;&nbsp;</label>
						<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'><input type='checkbox' id="wbbs2" name="wbbs" style='vertical-align:text-bottom;' checked="checked" onchange="searchRevision();" />&nbsp;外部&nbsp;&nbsp;</label>
					</div>
				</div> 
				 <div class="col-lg-2 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group" >
					 <span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
				 			<div class="font-size-12">性别</div>
							<div class="font-size-9">Sex</div>
					 </span>
					<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div">
						<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
							<input type='checkbox' id="xb1" style='vertical-align:text-bottom;' name="xb" checked="checked" onchange="searchRevision();" />&nbsp;男&nbsp;&nbsp;
						</label>
						<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
							<input type='checkbox' id="xb2" style='vertical-align:text-bottom;' name="xb" checked="checked" onchange="searchRevision();" />&nbsp;女&nbsp;&nbsp;
						</label>
					</div>
				</div> 
				
				<!-- 关键字搜索 -->
				<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " style="padding-left:15px;">
					<div class="col-xs-12 input-group input-group-search">
						<input type="text" placeholder="教员编号/姓名/联系电话/电子邮箱" class="form-control" id="keyword_search">
	                    <div class="input-group-addon btn-searchnew">
	                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 row-height" onclick="searchRevision();" style="margin-right:0px !important;">
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
				<div class='clearfix'></div>
				<!-- 更多的搜索框 -->
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control "  name="dprtcode" onchange="searchRevision();">
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
				<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
				<!-- 搜索框End -->
				
				<div class='table_pagination'>
				<!-- 表格 -->
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0  table-height" style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-set" id="main_table">
							<thead>
								<tr>
									<th class="fixed-column colwidth-5" >
										<div class="font-size-12 line-height-18" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('rybh')" id="rybh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">教员编号</div>
											<div class="font-size-9 line-height-18">Faculty No.</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('xm')" id="xm_order">
										<div class="important">
											<div class="font-size-12 line-height-18">姓名</div>
											<div class="font-size-9 line-height-18">Name</div>
										</div>
									</th>
									<th class="colwidth-5 sorting" onclick="orderBy('wbbs')" id="wbbs_order">
										<div>
											<div class="font-size-12 line-height-18">内/外</div>
											<div class="font-size-9 line-height-18">within/outside</div>
										</div>
									</th>
									<th class="colwidth-5 sorting" onclick="orderBy('xb')" id="xb_order">
										<div class="font-size-12 line-height-18">性别</div>
										<div class="font-size-9 line-height-18">Sex</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('lxdh')" id="lxdh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">联系电话</div>
											<div class="font-size-9 line-height-18">Phone</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('yxdz')" id="yxdz_order">
										<div class="important">
											<div class="font-size-12 line-height-18">电子邮箱</div>
											<div class="font-size-9 line-height-18">Email</div>
										</div>
									</th>
									<th class="colwidth-15" onclick="orderBy('bz')" id="bz_order">
										<div class="font-size-12 line-height-18">家庭成员</div>
										<div class="font-size-9 line-height-18">Family</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('whrid')" id="whrid_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintianer</div>
									</th>
									<th class="colwidth-15 sorting" onclick="orderBy('whsj')" id="whsj_order">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
							<tbody id="faculty_list"></tbody>
					</table>
				</div>	
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
				<div class='clearfix'></div>
				</div>	
				<!-- 隐藏下拉框start -->
				<div class="displayContent" id="bottom_hidden_content" style="height: 33px;display:none">
					<ul id="myTab" class="nav nav-tabs tabNew-style">
                      	<li class="active" onclick="faculty_bottom.choose(this,0)">
                      		<a href="#profile" data-toggle="tab" aria-expanded="false">
	                      		<div>
									<div class="font-size-12 line-height-12">授权课程</div>
									<div class="font-size-9 line-height-9">Allowed Courseware</div>
								</div>
                      		</a>
                      	</li>
                      	<li onclick="faculty_bottom.choose(this,1)">
                      	     <a href="#profile" data-toggle="tab" aria-expanded="false">
	                      	     <div>
									<div class="font-size-12 line-height-12">授课记录</div>
									<div class="font-size-9 line-height-9">Courseware Information</div>
								</div>
                      	     </a>
                      	</li>
                      	<li onclick="faculty_bottom.choose(this,2)">
                      	      <a href="#profile" data-toggle="tab" aria-expanded="false">
                      	      	<div>
									<div class="font-size-12 line-height-12">附件信息 </div>
									<div class="font-size-9 line-height-9">Attachments</div>
								</div>
                      	      </a>
                      	</li>
                    </ul>
                    <div id="myTabContent" class="tab-content tabchild_content" style="overflow: auto;">
						<%@ include file="/WEB-INF/views/training/faculty/faculty_bottom.jsp"%>
                    </div>
				</div>
				<!-- 隐藏下拉框end -->
			</div>
		</div>		
	</div>
	<!--日志Start  -->
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<!--日志End  -->
	<div id="basic_div">
		<%@ include file="/WEB-INF/views/training/faculty/faculty_open.jsp" %>
	</div>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet"><!-------上传样式 -------->
	<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script><!-------上传js -------->
	<script src="${ctx}/static/js/thjw/training/faculty/faculty_main.js"></script>
	<script src="${ctx}/static/js/thjw/training/faculty/faculty_open.js"></script>
	<script src="${ctx}/static/js/thjw/training/faculty/faculty_bottom.js"></script>
	<link rel="stylesheet" href="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel.css"> <!-------上传图片样式 -------->
	<%@ include file="/WEB-INF/views/open_win/personel_tree_multi.jsp"%><!-------用户对话框 -------->
</body>
</html>
