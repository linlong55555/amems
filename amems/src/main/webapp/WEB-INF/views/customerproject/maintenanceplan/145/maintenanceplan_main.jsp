<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript" src="${ctx}/static/lib/echarts/echarts.min.js"></script>
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
<input type="hidden" id="zzjgid" name="zzjgid" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="zdbmid" name="zdbmid" value="${user.bmdm}" />
<input type="hidden" id="hcgjid" name="hcgjid"  />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body padding-bottom-0">
				 <div  class='searchContent row-height' >
				 	<div class='margin-top-0'>
						<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
							<a href="javascript:ToHcList();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left ">
								<div class="font-size-12">航材工具需求清单</div>
								<div class="font-size-9">Requirements & Tools</div>
							</a>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 form-group ">
							<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">飞机注册号</div>
								<div class="font-size-9">A/C Reg</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class="form-control" id="fjzch_search">									
							</div>
						</div>
						<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
								<span  class="col-lg-4 col-md-4 col-sm-4 col-xs-1 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">下发单位</div>
									<div class="font-size-9 ">Issued Unit</div>
								</span>
								<div class="col-lg-8 col-md-8 col-sm-8 col-xs-11 padding-left-8 padding-right-0">
									<select id="xfdw_search" class="form-control ">
										<option value="">选择全部 All</option>
										<option value="0">内部</option>
										<option value="1">外部</option>
									</select>
								</div>
						</div>
							<!-- 搜索框start -->
						<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
							<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='工包编号/描述/项目编号'  class="form-control" id="keyword_search" >
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
					</div>
					<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
						<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
								<span  class="col-lg-4 col-md-4 col-sm-4 col-xs-1 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">执行单位</div>
									<div class="font-size-9 ">Executive Unit</div>
								</span>
								<div class="col-lg-8 col-md-8 col-sm-8 col-xs-11 padding-left-8 padding-right-0">
									<select id="zxdw_search" class="form-control ">
										<option value="">选择全部 All</option>
										<c:forEach items="${departmentList}" var="type">
											<option value="${type.id}" >${erayFns:escapeStr(type.dprtname)}</option>
										</c:forEach>
									</select>
								</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 form-group ">
							<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">维修类型</div>
								<div class="font-size-9">Type</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class="form-control" id="wxlx_search">
									<option value=''>选择全部 All</option>
								</select>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 form-group ">
							<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">状态</div>
								<div class="font-size-9">Status</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class="form-control" id="zt_search">
									<option value="">选择全部 All</option>
									<c:forEach items="${workpackageStatusEnum}" var="type">
										<option value="${type.id}" >${erayFns:escapeStr(type.name)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 form-group ">
							<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">项目编号</div>
								<div class="font-size-9">Project No.</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class="form-control" id="xmbh_search">
									<option value=''>选择全部 All</option>
									<c:forEach items="${xmList}" var="type">
											<option value="${type.id}" >${erayFns:escapeStr(type.xmmc)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 form-group ">
							<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">客户</div>
								<div class="font-size-9">Customer</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class="form-control" id="kh_search">
									<option value=''>选择全部 All</option>
									<c:forEach items="${khList}" var="type">
											<option value="${type.id}" >${erayFns:escapeStr(type.khmc)}</option>
									</c:forEach>									
								</select>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 form-group ">
							<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select id="zzjg" class="form-control " name="zzjg">
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>										
						<div class="col-lg-1  text-right  pull-right padding-right-0" style="margin-bottom: 10px;">
							<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
								<div class="font-size-12">重置</div>
								<div class="font-size-9">Reset</div>
							</button>
						</div>					 
					</div>
					<div class="clearfix"></div>
				</div>
				<div class='table_pagination'>				
					<div class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" c-height="35%" style="overflow-x: auto;" id="packing_list_table_div">
						<table id="packing_list_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1700px !important">
							<thead>
							<tr>
							<th  class="fixed-column selectAllImg" id="checkAll" style='text-align:center;vertical-align:middle;width:60px;'>
								<a href="#" onclick="performSelectAll()" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
								<a href="#" class="margin-left-5" onclick="performSelectClear()" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
							</th>
							<th class="sorting colwidth-9" onclick="orderBy('gbbh')" id="gbbh_order" >
								<div class="important">
									<div class="font-size-12 line-height-18" >工包编号</div>
									<div class="font-size-9 line-height-18" >Package No.</div>
								</div>
							</th>
							<th class="sorting  colwidth-7" onclick="orderBy('zt')" id="zt_order">
								<div class="font-size-12 line-height-18" >状态</div>
								<div class="font-size-9 line-height-18" >Status</div>
							</th>
							<th class="sorting  colwidth-9" onclick="orderBy('fjzch')" id="fjzch_order">
								<div class="font-size-12 line-height-18" >飞机注册号</div>
								<div class="font-size-9 line-height-18" >A/C Reg</div>
							</th>
							<th class="sorting  colwidth-10" onclick="orderBy('fjxlh')" id="fjxlh_order">
								<div class="font-size-12 line-height-18" >MSN</div>
								<div class="font-size-9 line-height-18" >MSN</div>
							</th>
							<th class="sorting colwidth-9" onclick="orderBy('gbmc')" id="gbmc_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >描述 </div>
									<div class="font-size-9 line-height-18" >Desc</div>
								</div>
							</th>
							<th class="sorting colwidth-13"  onclick="orderBy('wxlx')" id="wxlx_order">
								<div class="font-size-12 line-height-18" >维修类型</div>
								<div class="font-size-9 line-height-18" >Maintenance Type</div>
							</th>							
							<th class="sorting colwidth-9" onclick="orderBy('jh_ksrq')" id="jh_ksrq_order">
								<div class="font-size-12 line-height-18" >计划开始日期</div>
								<div class="font-size-9 line-height-18" >Date</div>
							</th>
							<th class="sorting colwidth-9" onclick="orderBy('jh_jsrq')" id="jh_jsrq_order">
								<div class="font-size-12 line-height-18" >计划完成日期</div>
								<div class="font-size-9 line-height-18" >Date</div>
							</th>							
							<th class="sorting colwidth-20" onclick="orderBy('xmmc')" id="xmmc_order">
							<div class="important">
								<div class="font-size-12 line-height-18" >项目编号</div>
								<div class="font-size-9 line-height-18" >Project No.</div>
							</div>
							</th>							
							<th class="sorting colwidth-10" onclick="orderBy('wxlx')" id="wxlx_order">
								<div class="font-size-12 line-height-18" >来源类型</div>
								<div class="font-size-9 line-height-18" >Source Type</div>
							</th>							
							<th class="sorting colwidth-20" onclick="orderBy('xfdw')" id="xfdw_order">
								<div class="font-size-12 line-height-18" >下发单位/客户信息</div>
								<div class="font-size-9 line-height-18" >Issuing Unit/Custom Info</div>
							</th>
							<th class="sorting colwidth-9" onclick="orderBy('xfsj')" id="xfsj_order">
								<div class="font-size-12 line-height-18" >下发日期</div>
								<div class="font-size-9 line-height-18" >Date Of Issue</div>
							</th>
							<th class="sorting colwidth-20" onclick="orderBy('zxdw')" id="zxdw_order">
						
									<div class="font-size-12 line-height-18" >预计执行单位</div>
									<div class="font-size-9 line-height-18" >Estimated Execution Unit</div>
								
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('gzyq')" id="gzyq_order">
								<div class="font-size-12 line-height-18" >工作要求</div>
								<div class="font-size-9 line-height-18" >Work Request</div>
							</th>						
							<th class="sorting colwidth-7" onclick="orderBy('wgbs')" id="wgbs_order">
								<div class="font-size-12 line-height-18" >反馈状态</div>
								<div class="font-size-9 line-height-18" >Status</div>
							</th>
							<th class="colwidth-7" >
								<div class="font-size-12 line-height-18" >附件</div>
								<div class="font-size-9 line-height-18" >Attachment</div>
							</th>
							<th class="sorting colwidth-13" onclick="orderBy('whrid')" id="whrid_order">
								<div class="font-size-12 line-height-18" >维护人</div>
								<div class="font-size-9 line-height-18" >Maintainer</div>
							</th>
							<th class="sorting colwidth-13" onclick="orderBy('whsj')" id="whsj_order">
								<div class="font-size-12 line-height-18" >维护时间</div>
								<div class="font-size-9 line-height-18" >Maintenance Time</div>
							</th>
							<th class="sorting colwidth-13" onclick="orderBy('dprtcode')" id="dprtcode_order">
								<div class="font-size-12 line-height-18" >组织机构 </div>
								<div class="font-size-9 line-height-18" >Organization</div>
							</th>
							</tr> 
			         		 </thead>
							<tbody id="packaginglist">
							</tbody>
							
						</table>
					</div>
					<div class="col-xs-12 text-center padding-left-0 padding-right-0 page-height " id="pagination_list">
					</div>				
					<div class="clearfix"></div>
					</div>
					<!-- 隐藏的div -->
					<div  class='displayContent col-xs-12  clearfix padding-top-10 padding-left-0 padding-right-0 padding-top-0' style='display:none;'>
						<div class='col-xs-6 padding-left-0 padding-right-0' style='padding-right:5px;'>
							<%@ include file="/WEB-INF/views/customerproject/maintenanceplan/145/maintenanceplan_bar.jsp"%>
						</div>
						<div class='col-xs-6 padding-left-0 padding-right-0' style='padidng-left:5px;'>
						    <%@ include file="/WEB-INF/views/customerproject/maintenanceplan/145/maintenanceplan_gannt.jsp"%>
						</div>
						<div class='clearfix'></div>
					</div>
				</div>
			</div>
	</div>
	<script>
	$(function(){
		Navigation(menuCode);
		//绘制甘特图 
	})
	</script>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
    <script type="text/javascript" src="${ctx}/static/js/thjw/common/auth_height_util.js"></script>
   	<script src='${ctx}/static/js/thjw/customerproject/maintenanceplan/145/maintenanceplan_main.js'></script>
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<%@ include file="/WEB-INF/views/common/produce/customer_modal.jsp"%><!-- -客户 -->
	<%@ include file="/WEB-INF/views/common/produce/project_modal.jsp"%><!-- -项目 -->
	<%@ include file="/WEB-INF/views/produce/workpackage/145/workpackage145_close.jsp"%><!-- -关闭工包 -->
	<%@ include file="/WEB-INF/views/produce/workpackage/145/workpackage145_end.jsp"%><!-- -关闭工包 -->
	<%@ include file="/WEB-INF/views/produce/workpackage/145/workpackage145_feedback.jsp"%><!-- -关闭工包 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
		<%@ include file="/WEB-INF/views/open_win/department.jsp"%><!-- 选择部门 -->
			 <%@ include file="/WEB-INF/views/open_win/attachments_list_edit.jsp"%><!------工包明细附件对话框 -------->
		<%@ include file="/WEB-INF/views/common/produce/workorder_modal.jsp"%><!------选择工单对话框 -------->
</body>
</html>