<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>报废申请</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script type="text/javascript">
		var id = "${param.id}";
		var pageParam = '${param.pageParam}';
		var todo_ywid = "${param.todo_ywid}";
	</script>
	<script type="text/javascript">
		$(document).ready(function(){
			//回车事件控制
			$(this).keydown(function(event) {
				e = event ? event :(window.event ? window.event : null); 
				if(e.keyCode==13){
					var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
					if(formatUndefine(winId) != ""){
						$("#"+winId+" button[name='keyword_search']").trigger('click'); //调用当前窗口的查询
					}else{
						searchRevision();//调用主列表页查询
					}
				}
			});
		});
	</script>	
</head>
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
<div class="page-content">
	<div class="panel panel-primary">
		<div class="panel-heading  ">
			<div id="NavigationBar"></div>
		</div>
		<div class=" panel-body padding-bottom-0" >
			<div class='searchContent margin-top-0 row-height' >
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" onclick="applyOpen()">
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</button>
						
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">报废日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<input type="text" class="form-control date-range-picker" name="date-range-picker" id="bfrq_search" readonly />
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<div class='pull-left'>
						<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input value="1" style="vertical-align:text-bottom;" type="checkbox" name='jy' onclick="searchRevision()" checked="checked">&nbsp;待处理&nbsp;&nbsp;
						</label>
						<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input value="2" style="vertical-align:text-bottom;" type="checkbox" name='jy' onclick="searchRevision()">&nbsp;待审核&nbsp;&nbsp;
						</label>
						<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input value="3" style="vertical-align:text-bottom;" type="checkbox" name='jy' onclick="searchRevision()" >&nbsp;已审核&nbsp;&nbsp;
						</label>
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='报废单号/说明'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button id="workCardMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="moreSearch();">
								<div class='input-group input-group-search'>
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
				  <div class='clearfix'></div>
			
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zzjg" class="form-control " name="zzjg">
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
				<div class='clearfix'></div>
				</div>
		
		   <!--  -->
		   <div  class='table_pagination'>
		   <div id="" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;" id="packing_list_table_div">
					<table id="packing_list_table" class="table table-thin table-bordered table-striped table-hover table-set" style="">
						<thead>                              
							<tr>
								<th  class='colwidth-5'>
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class='colwidth-9 sorting' onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18" >状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th class='colwidth-9 sorting' onclick="orderBy('bfdh')" id="bfdh_order">
								<div class="important">
									<div class="font-size-12 line-height-18">报废单号</div>
									<div class="font-size-9 line-height-18">Scrap No.</div></div>
								</th>
								<th class='colwidth-9 sorting' onclick="orderBy('bfrq')" id="bfrq_order" >
									    <div class="font-size-12 line-height-18">报废日期</div>
										<div class="font-size-9 line-height-18">Date</div>
							    </th>
								<th class='colwidth-9 sorting' onclick="orderBy('sqrid')" id="sqrid_order" >
									<div class="font-size-12 line-height-18">申请人</div>
									<div class="font-size-9 line-height-18">Applicant</div>
								</th>
								<th class='colwidth-20  sorting' onclick="orderBy('sm')" id="sm_order" >
								<div class="important">
										<div class="font-size-12 line-height-18">说明</div>
										<div class="font-size-9 line-height-18">Instruction</div></div>
							   </th>
							   <th class='colwidth-5' >
									<div class="font-size-12 line-height-18">附件</div>
									<div class="font-size-9 line-height-18">Enclosure</div>
							   </th>
							   <th class='colwidth-9 sorting' onclick="orderBy('sprid')" id="sprid_order" >
									<div class="font-size-12 line-height-18">审核人</div>
									<div class="font-size-9 line-height-18">Auditor</div>
							   </th>
							   <th class='colwidth-10 sorting' onclick="orderBy('spsj')" id="spsj_order" >
									<div class="font-size-12 line-height-18">审核时间</div>
									<div class="font-size-9 line-height-18">Date</div>
							   </th>
							 </tr> 
						</thead>
						<tbody id="applyList">							
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination_list"></div>
				</div>
				<div class='clearfix'></div>
				<div class='displayContent' id='bottom_hidden_content'  style="display:none;">
				<div class="panel panel-primary margin-bottom-0" >
				<!--标题 STATR -->
		 			<div class="panel-heading bg-panel-heading" style='' >
					 <div class="font-size-12 line-height-12">报废信息</div>
					 <div class="font-size-9 line-height-9">Discarded Info</div>
				</div>
				<!--标题EDG  -->
				<div class="panel-body  padding-bottom-0 padding-left-0 padding-right-0 padding-top-0 " >
					<div class='bottom-hidden-table-content padding-top-0 padding-left-0 padding-right-0 table-set' style="border:0px">
							<table id="OrderTable" class="table table-thin table-bordered table-striped table-hover table-set ">
								<thead >
									<tr>
										<th  class="colwidth-3" >
											<div class="font-size-12 line-height-18" >序号</div>
											<div class="font-size-9 line-height-18">No.</div>
										</th>
										<th  class="colwidth-5" >
											<div class="font-size-12 line-height-18" >销毁状态</div>
											<div class="font-size-9 line-height-18">Status</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 line-height-18">物料类型</div>
											<div class="font-size-9 line-height-18">Type</div>
										</th>
										<th class="colwidth-25">
											<div class="font-size-12 line-height-18">部件</div>
											<div class="font-size-9 line-height-18">PN</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 line-height-18">批次/序列号</div>
											<div class="font-size-9 line-height-18">BN/SN</div>
										</th>
										<th class="colwidth-9">
											<div class="font-size-12 line-height-18">规格/型号</div>
											<div class="font-size-9 line-height-18">Spec/Model</div>
										</th>
										<th class="colwidth-13"  >
											<div class="font-size-12 line-height-18">位置</div>
											<div class="font-size-9 line-height-18">Position</div>
										</th>
										<th class="colwidth-7"  >
											<div class="font-size-12 line-height-18">报废数量</div>
											<div class="font-size-9 line-height-18">QTY</div>
										</th>
										<th class="colwidth-15"  >
											<div class="font-size-12 line-height-18">产权</div>
											<div class="font-size-9 line-height-18">Right</div>
										</th>
										<th class="colwidth-9"  >
											<div class="font-size-12 line-height-18">成本</div>
											<div class="font-size-9 line-height-18">Cost</div>
										</th>
										<th class="colwidth-10"  >
											<div class="font-size-12 line-height-18">库存信息</div>
											<div class="font-size-9 line-height-18">Inventory info</div>
										</th>
										<th class="colwidth-15"  >
											<div class="font-size-12 line-height-18">销毁信息</div>
											<div class="font-size-9 line-height-18">Destory</div>
										</th>
									</tr>
								</thead>
								<tbody id="bottom_hidden_content_tbody">
									
								</tbody>
							</table>
							<div class='clearfix'></div>
						</div>
					</div>
	  	  		</div>
				<div class='clearfix'></div>
			</div>
	 	</div>
  	</div>
</div>
<%@ include file="/WEB-INF/views/material2/scrapped/apply/apply_open.jsp"%>
 <%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------审核人对话框 -------->
<%@ include file="/WEB-INF/views/material2/scrapped/apply/stock_choose.jsp"%>
<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
	<%@ include file="/WEB-INF/views/common/produce/material_cq.jsp" %> <!-- 产权弹出框 -->
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/scrapped/apply/apply_main.js"></script>
<script type="text/javascript" src="${ctx }/static/js/thjw/material2/scrapped/apply/apply_info.js"></script>
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 列表附件控件对话框 -->
</body>
</html>