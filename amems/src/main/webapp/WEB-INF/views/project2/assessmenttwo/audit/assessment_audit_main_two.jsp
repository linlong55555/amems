<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>技术文件评估单</title>
<script type="text/javascript">
//来源ID(评估单ID)
var todo_lyid = "${param.todo_lyid}";
var todo_fjjx = "${param.todo_fjjx}";
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
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					search();//调用主列表页查询
				}
			}
		});
	});
</script>

<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content table-table-type">
	<input type="hidden" id="userid" value="${user.id}">
	<input type="hidden" id="id"  />
	<input type="hidden" id="technicalAttachedid"  />
	<input type="hidden" id="djzt"  />
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<!--导航结束  -->
			<div class="panel-body">
			  <div  class='searchContent' >
				<div class='margin-top-0'>
				<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
					<a  id="batchReview" href="javascript:batchApproveWin(false);"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height checkPermission" permissioncode='project2:assessment:audit:main:02'>
						<div class="font-size-12">批量审核</div>
						<div class="font-size-9">Audit</div>
					</a> 
				</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">机型</div>
							<div class="font-size-9 ">A/C Type</div>
						</span>
						<div id="jxdiv" class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<select id="jx"  class="form-control " onchange="search()">
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<div  class="text-right padding-left-0 padding-right-0">
							<label class='' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' id="ztList" name="ztList" style='vertical-align:text-bottom;' onchange="search()" checked="checked" />
								&nbsp;仅显示待处理
							</label>
							    &nbsp;
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
									<input type='checkbox' id="bbList" name="bbList" style='vertical-align:text-bottom;' onchange="search()"/>
									&nbsp;全部(不勾选仅显示有效数据)
							</label>
						</div>
					</div>
	
					<!-- 搜索框start -->
				<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='技术评估单编号/标题/适航性编号/文件/ATA'  class="form-control" id="keyword_search" >
                    <div class="input-group-addon btn-searchnew" >
                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="search();" style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                    </div>
                    <div class="input-group-addon btn-searchnew-more">
	                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"  id="btn" onclick="openOrHide();">
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
				<div class="clearfix"></div>
				</div>
				<!-- 搜索框end -->
			
					
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0 search-height" id="divSearch">
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class='form-control' id="zt">
								<option value="" selected="true">显示全部All</option>
								<c:forEach items="${technicalStatusEnum}" var="item">
										<option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">来源</div>
							<div class="font-size-9">Issued By</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class='form-control' id="lylx">
								<option value="" selected="true">显示全部All</option>
								<option value="1" >适航性资料</option>
								<option value="9" >其他</option>
						    </select>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">适用类别</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class='form-control' id="sylb">
								<option value="" selected="true">显示全部All</option>
								<option value="1" >飞机</option>
								<option value="2" >发动机</option>
								<option value="3" >APU</option>
								<option value="99" >其他部件</option>
						    </select>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">维护日期</div>
							<div class="font-size-9">Maintenance Date</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly id="pgrq" />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">文件类型</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 label-input-div" id="wjlxHtml">
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">评估工程师</div>
							<div class="font-size-9">Engineer</div>
						</span>
						<div id="pgridDiv" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class='form-control' id="pgrid" type="text" >
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " name="dprtcode" onchange="onchangeDprtcode()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-lg-3  text-right  pull-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>
				<div class="clearfix"></div>
				</div>
			<div class="clearfix"></div>
				
			<div class='table_pagination'>
				<!-- 表格 -->
			<div  class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" c-height="55%" id="tableId" style="overflow-x: auto;">
						<table  id="auditTable" class="table table-thin table-bordered table-striped table-hover table-set">
							<thead>
								<tr>
								
									<th  class="fixed-column selectAllImg" id="checkAll" style='text-align:center;vertical-align:middle;width:60px;'>
										<a href="#" onclick="performSelectAll()" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
										<a href="#" class="margin-left-5" onclick="performSelectClear()" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
									</th>
									<th  class="fixed-column colwidth-5">
										<div class="font-size-12 line-height-18 ">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th width='50' >
										<div class="font-size-12 line-height-18">标识</div>
										<div class="font-size-9 line-height-18">Mark</div>
									</th>
									<th  class="sorting  colwidth-10" onclick="orderBy('pgdh')" id="pgdh_order">
										<div class="important"><div class="font-size-12 line-height-18">技术评估单编号</div>
										<div class="font-size-9 line-height-18">Evaluation No.</div></div>
									</th>
									<th  class="sorting  colwidth-5" onclick="orderBy('bb')" id="bb_order">
										<div class="font-size-12 line-height-18">版本</div>
										<div class="font-size-9 line-height-18">Rev.</div>
									</th>
									<th class="sorting  colwidth-10" onclick="orderBy('jx')" id="jx_order">
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</th>
									<th class="sorting  colwidth-20" onclick="orderBy('pgdzt')" id="pgdzt_order">
										<div class="important">
											<div class="font-size-12 line-height-18">标题</div>
											<div class="font-size-9 line-height-18">Title</div>
										</div>	
									</th>
									<th  class="sorting  colwidth-13" onclick="orderBy('pgrid')" id="pgrid_order">
										<div class="font-size-12 line-height-18">评估工程师</div>
										<div class="font-size-9 line-height-18">Engineer</div>
									</th>	
									<th  class="sorting  colwidth-13" onclick="orderBy('glpgdid')" id="glpgdid_order">
										<div class="font-size-12 line-height-18">关联技术评估单</div>
										<div class="font-size-9 line-height-18">Related Evaluation</div>
									</th>	
									<th class="downward cursor-pointer colwidth-13"  class="" onclick="vieworhideWorkContentAll()" name="th_return">
										<div class="font-size-12 line-height-18">下达指令类型</div>
										<div class="font-size-9 line-height-18">Type</div>
									</th>	
									<th  class="sorting  colwidth-7" onclick="orderBy('zt')" id="zt_order">
										<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">Status</div>
									</th>
									<th  class="sorting  colwidth-10" onclick="orderBy('pgqx')" id="pgqx_order">
										<div class="font-size-12 line-height-18">评估期限</div>
										<div class="font-size-9 line-height-18">Assess Limit</div>
									</th>
									<th  class="sorting  colwidth-10" onclick="orderBy('syts')" id="syts_order">
										<div class="font-size-12 line-height-18">剩余(天)</div>
										<div class="font-size-9 line-height-18">Remain</div>
									</th>
									<th class="sorting  colwidth-10" onclick="orderBy('lylx')" id="lylx_order">
										<div class="font-size-12 line-height-18">来源</div>
										<div class="font-size-9 line-height-18">Issued By</div>
									</th>
							 		<th class="sorting  colwidth-10" onclick="orderBy('sylb')" id="sylb_order">
							 			<div class="font-size-12 line-height-18">适用类别</div>
										<div class="font-size-9 line-height-18">Type</div>
									</th> 
									<th class="sorting  colwidth-10" onclick="orderBy('jswjlx')" id="jswjlx_order">
										<div class="font-size-12 line-height-18">文件类型</div>
										<div class="font-size-9 line-height-18">Type</div>
									</th>
							 		<th class="sorting  colwidth-15" onclick="orderBy('jswjid')" id="jswjid_order">
							 			<div class="important">
								 			<div class="font-size-12 line-height-18">适航性资料编号</div>
											<div class="font-size-9 line-height-18">Airworthines No.</div>
										</div>
									</th> 
									<th  class="colwidth-15" >
										<div class="important">
											<div class="font-size-12 line-height-18">文件</div>
											<div class="font-size-9 line-height-18">File</div>
										</div>
									</th>
									<th  class="colwidth-15" >
											<div class="font-size-12 line-height-18">关联适航性资料</div>
											<div class="font-size-9 line-height-18">Related Doc.</div>
									</th>
									<th  class="sorting  colwidth-15" onclick="orderBy('zjh')" id="zjh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">ATA章节号</div>
											<div class="font-size-9 line-height-18">ATA</div>
										</div>
									</th>
									<th  class="colwidth-10" >
											<div class="font-size-12 line-height-18">附件</div>
											<div class="font-size-9 line-height-18">Attachment</div>
									</th>
									<th  class="sorting  colwidth-10" onclick="orderBy('sxrq')" id="sxrq_order">
										<div class="font-size-12 line-height-18">生效日期</div>
										<div class="font-size-9 line-height-18">Effect Date</div>
									</th>
									<th  class="sorting  colwidth-30" onclick="orderBy('bz')" id="bz_order">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Note</div>
									</th>
									<th  class="sorting  colwidth-15" onclick="orderBy('pgsj')" id="pgsj_order">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th  class="colwidth-13">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
							<tbody id="airworthiness_list">
							</tbody>
					</table>
				</div>	
				
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
				</div>
				<div class='clearfix'></div>
			</div>	
			<%@ include file="/WEB-INF/views/project2/assessmenttwo/assessment_give_order_two.jsp" %> <!--下达指令  -->
		</div>
	</div>
</div>	
<!--日志STATR -->
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<!--日志END  -->
<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
<%@ include file="/WEB-INF/views/open_win/batchApprovel.jsp"%><!-------批量审批对话框 -------->
<%@ include file="/WEB-INF/views/project2/assessmenttwo/assessment_open_two.jsp" %> <!--新增编辑评估单界面  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessmenttwo/audit/assessment_audit_main_two.js"></script><!--当前界面js  -->

<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 附件对话框 -->
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
<%@ include file="/WEB-INF/views/project2/assessment/assessment_history_view_win.jsp"%><!------历史版本对话框 -------->
</body>
</html>
