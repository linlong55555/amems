<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>历史任务</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
.multiselect{
overflow:hidden;
}
</style>
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
					taskhistoryMain.reload();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtment" value="${user.bmdm}" />
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<input type="hidden" id="userDisplayName" value="${user.displayName}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->


	<div class="page-content" id='airworthiness_mainpage'>
		<div class="panel panel-primary">
		  <!-- panel-heading -->
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<!-- 搜索框 -->
			    <div  class='searchContent margin-top-0  row-height'  >
			    <!-- 上传按钮  -->
			    <div class=" col-lg-3 col-md-3 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
					<a href="javascript:exportExcel();" permissioncode='produce:taskhistory:main:01'  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a> 
				</div>
			    
				<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">机号/MSN</div>
							<div class="font-size-9">Reg/MSN</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
							<select class='form-control' id="fjzch" onchange="taskhistoryMain.reload()">
								<option value="" >显示全部All</option>
						    </select>
						</div>
				</div>
				<!-- <div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">来源类型</div>
							<div class="font-size-9">Source Type</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
							<select class='form-control' id="gdlx" onchange="taskhistoryMain.reload()">
								<option value="" >显示全部All</option>
								<option value="1" >维修项目</option>
								<option value="2" >EO</option>
								<option value="3" >NRC</option>
								<option value="4" >MO/MCC</option>
								<option value="5" >其它指令</option>
								<option value="9" >FLB</option>
						    </select>
						</div>
				</div> -->
				<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">ATA章节号</div>
							<div class="font-size-9">ATA</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10  padding-leftright-8">
							<div class="input-group">
								<input id="zjh"  class="form-control"  type="text" ondblclick="openList()">
					            <span class="input-group-btn">
									<button type="button" class="btn btn-default form-control" style="height:34px;" data-toggle="modal" onclick="openList()">
										<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
					       </div>
						</div>
					</div> 
				<!-- 关键字搜索 -->
				<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='工单编号/工单标题/任务号/飞行记录本'  class="form-control" id="keyword_search" >
                    <div class="input-group-addon btn-searchnew" >
                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="taskhistoryMain.reload();" style='margin-right:0px !important;'>
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
				
				<div class='clearfix'></div>
				<!-- 更多的搜索框 -->
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-bottom-10 display-none border-cccccc" id="divSearch">
					<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10  padding-leftright-8">
							<label class='margin-left-3' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' name='zt_search' value='10' style='vertical-align:text-bottom;' checked="checked"/>&nbsp;完工关闭&nbsp;
							</label>
							<label class='margin-left-3' style='margin-top:6px;font-weight:normal;'>
								<input type='checkbox' name='zt_search' value='9' style='vertical-align:text-bottom;' />&nbsp;指定结束&nbsp;
							</label>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">任务号</div>
							<div class="font-size-9">Task No.</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10  padding-leftright-8">
							<input id="rwh" class="form-control "  name="rwh" maxlength="100">
						</div>
					</div>
					
					<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">来源类型</div>
							<div class="font-size-9">Source Type</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10  padding-leftright-8">
							<select class='form-control' id="gdlx" onchange="taskhistoryMain.reload()">
								<option value="" >显示全部All</option>
								<option value="1" >维修项目</option>
								<option value="2" >EO</option>
								<option value="3" >NRC</option>
								<option value="4" >MO/MCC</option>
								<option value="5" >其它指令</option>
								<option value="9" >FLB</option>
						    </select>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10  padding-leftright-8">
							<select id="dprtcode" class="form-control "  name="dprtcode" onchange="dprtChange()" >
								<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-1  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
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
				<!-- 搜索框End -->
				
				<div class='table_pagination'>
				<!-- 表格 -->
				<div class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" c-height="45%" id="tableId" style="overflow-x: auto;width: 100%;">
						<table  id="quality_check_list_table" class="table table-thin table-bordered table-set table-hover table-striped" style="min-width: 2600px !important">
							<thead>
								<tr>
									<th class="fixed-column colwidth-5" >
										<div class="font-size-12 line-height-18" >序号</div>
										<div class="font-size-9 line-height-18">No.</div>
									</th>
									<th  class="fixed-column colwidth-10 sorting" onclick="orderBy('sj_jsrq')" id="sj_jsrq_order">
										<div class="font-size-12 line-height-18">完成日期</div>
										<div class="font-size-9 line-height-18">Complete Date</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('zjh')" id="zjh_order">
										<div class="font-size-12 line-height-18">ATA章节号</div>
										<div class="font-size-9 line-height-18">ATA</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('gdbh')" id="gdbh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">工单编号</div>
											<div class="font-size-9 line-height-18">Order No.</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('fjzch')" id="fjzch_order">
										<div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div>
									</th>
									<th class="colwidth-25 sorting" onclick="orderBy('gdbt')" id="gdbt_order">
										<div class="important">
											<div class="font-size-12 line-height-18">工单标题</div>
											<div class="font-size-9 line-height-18">Order Title</div>
										</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('zt')" id="zt_order">
											<div class="font-size-12 line-height-18">工单状态</div>
											<div class="font-size-9 line-height-18">Status</div>
									</th>
									<th class="colwidth-15 sorting" onclick="orderBy('gbbh')" id="gbbh_order">
										<div class="font-size-12 line-height-18">工包编号</div>
										<div class="font-size-9 line-height-18">Work Packages No.</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('gdlx')" id="gdlx_order">
										<div class="font-size-12 line-height-18">来源类型</div>
										<div class="font-size-9 line-height-18">Source Type</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('rwh')" id="rwh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">任务号（含版本）</div>
											<div class="font-size-9 line-height-18">Task No.</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('ckh')" id="ckh_order">
										<div class="font-size-12 line-height-18">参考号</div>
										<div class="font-size-9 line-height-18">Reference No.</div>
									</th>
									<th class="colwidth-10" onclick="orderBy('sj_gzz')" id="sj_gzz_order">
										<div class="font-size-12 line-height-18">工作者</div>
										<div class="font-size-9 line-height-18">Workers</div>
									</th>
									
									<th class="colwidth-25 sorting" onclick="orderBy('gzxx')" id="gzxx_order">
										<div class="font-size-12 line-height-18">故障信息</div>
										<div class="font-size-9 line-height-18">Fault</div>
									</th>
									<th class="colwidth-25 sorting" onclick="orderBy('clcs')" id="clcs_order">
										<div class="font-size-12 line-height-18">处理措施</div>
										<div class="font-size-9 line-height-18">Measures</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-18">工单附件</div>
										<div class="font-size-9 line-height-18">Order File</div>
									</th>
									<th class="colwidth-13 ">
										<div class="important">
											<div class="font-size-12 line-height-18">飞行记录本</div>
											<div class="font-size-9 line-height-18">Flight Record</div>
										</div>
									</th>
									<th class="colwidth-10 ">
										<div class="font-size-12 line-height-18">记录本页码</div>
										<div class="font-size-9 line-height-18">Record Number</div>
									</th>
									<!-- <th class="colwidth-13 ">
										<div class="font-size-12 line-height-18">飞行记录本附件</div>
										<div class="font-size-9 line-height-18">Flight Record File</div>
									</th> -->
									<th class="colwidth-10 ">
										<div class="font-size-12 line-height-18">计划值</div>
										<div class="font-size-9 line-height-18">Planned Value</div>
									</th>
									<th class="colwidth-10 ">
										<div class="font-size-12 line-height-18">实际值</div>
										<div class="font-size-9 line-height-18">Actual Value</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('jhgs')" id="jhgs_order">
										<div class="font-size-12 line-height-18">计划工时</div>
										<div class="font-size-9 line-height-18">Plan Time</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('sj_gs')" id="sj_gs_order">
										<div class="font-size-12 line-height-18">实际工时</div>
										<div class="font-size-9 line-height-18">Actual Time</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('zzjg')" id="zzjg_order">
										<div class="font-size-12 line-height-18" >组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
									
								</tr>
							</thead>
							<tbody id="taskhistory_list"></tbody>
					</table>
				</div>	
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="taskhistory_pagination"></div>
				<div class='clearfix'></div>
				</div>	
				<!-- 隐藏的div -->
				<div class='displayContent '  style='display:none;'>
				    <!-- <div class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0 padding-top-10"> -->
	                	<ul id="myTab" class="nav nav-tabs tabNew-style">
	                      	<li class="active"><a id="aaa" href="#Dropdown" data-toggle="tab" aria-expanded="false" >完工反馈 Complete Feedback</a></li>
	                      	<li class=""><a href="#profile" data-toggle="tab" aria-expanded="false" >拆换件记录 Record</a></li>
	                    </ul>
	                    <div id="myTabContent" class="tab-content padding-top-0 padding-left-0 padding-right-0" >
                      	
	                      	<div class="tab-pane active" id="Dropdown"  >
								  <%@ include file="/WEB-INF/views/produce/taskhistory/taskhistory_feedback.jsp" %>  
	                      	</div>
	                     	
	                      	<div class="tab-pane fade " id="profile">
	                      		<%@ include file="/WEB-INF/views/produce/taskhistory/taskhistory_record.jsp" %>
	                      	</div>
	                  </div>
	                    
                    <!-- </div> -->
				   <div class='clearfix'></div>
				</div>
        	</div>
		</div>		
	</div>
    <%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
    <%@ include file="/WEB-INF/views/produce/workorder/135/workorder_wg_close.jsp"%><!-- -工单完工关闭 -->
    <%@ include file="/WEB-INF/views/produce/workorder/135/workorder_zd_close.jsp"%><!-- -工单指定结束 -->
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
	<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/taskhistory/taskhistory_main.js"></script>
	<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>
