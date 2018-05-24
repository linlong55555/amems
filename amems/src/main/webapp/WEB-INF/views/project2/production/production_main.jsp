<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

<script type="text/javascript">

//来源ID(评估单ID)
var todo_lyid = "${param.todo_lyid}";
var todo_fjjx = "${param.todo_fjjx}";
var todo_ywid = "${param.todo_ywid}";
var todo_dprtcode = "${param.todo_dprtcode}";
var todo_jd = "${param.todo_jd}";

	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					production.reload();//调用主列表页查询
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
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				<!-- 搜索框end -->
				<div class='searchContent row-height margin-top-0'>
					<div class="row" style="margin-left: 0px; margin-right: 0px;">
						<div class="pull-left form-group">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" 
								style="margin-right:3px;" onclick="production.openAddPage()">
								<div class="font-size-12">新增</div>
								<div class="font-size-9">Add</div>
							</button>
						</div>
						
						<div class="pull-right form-group">
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="font-size-12">飞机注册号</div>
								<div class="font-size-9">A/C REG</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="padding-left-8 pull-left" style="width: 200px; margin-right:5px;">
								   <select id="fjzch" class="form-control" onchange="production.reload()">
								   </select> 
								</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
									<input name="zt" value="1,5,6" style="vertical-align:text-bottom" checked="checked" type="checkbox" onchange="production.reload()">&nbsp;待提交&nbsp;&nbsp;
								</label>
								<!-- <label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
									<input name="zt" value="2" style="vertical-align:text-bottom" checked="checked" type="checkbox" onchange="production.reload()">&nbsp;待审核&nbsp;&nbsp;
								</label>
								<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
									<input name="zt" value="3" style="vertical-align:text-bottom" checked="checked" type="checkbox" onchange="production.reload()">&nbsp;待审批&nbsp;&nbsp;
								</label> -->
								<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
									<input name="zt" value="7" style="vertical-align:text-bottom" checked="checked" type="checkbox" onchange="production.reload()">&nbsp;生效&nbsp;&nbsp;
								</label>
								<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
									<input name="zt" value="9" style="vertical-align:text-bottom" type="checkbox" onchange="production.reload()">&nbsp;关闭&nbsp;&nbsp;
								</label>
							</div>
							<div class="pull-left padding-left-0 padding-right-0">
								<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
									<input placeholder="指令编号/指令描述/工卡编号/ATA章节号" class="form-control" id="keyword" type="text">
								</div>
			                    <div class="pull-right padding-left-5 padding-right-0 ">   
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="production.reload()">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                   		<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" onclick="production.more()">
										<div class="pull-left text-center">
											<div class="font-size-12">更多</div>
											<div class="font-size-9">More</div>
										</div>
										<div class="pull-left padding-top-5">
											&nbsp;<i class="icon-caret-down font-size-15" id="icon"></i>
										</div>
									</button>
			                    </div> 
							</div>
						</div>
					</div>
				
			
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-4  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
						</span>
						<div class="col-xs-9 col-sm-9 padding-left-9 padding-right-0">
							<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input id="wglgk" style="vertical-align:text-bottom" checked="checked" type="checkbox">&nbsp;未关联工卡&nbsp;&nbsp;
							</label>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-4  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-9 col-sm-9 padding-left-9 padding-right-0">
							<select id="dprtcode" class="form-control" onchange="production.initAcReg()" >
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="production.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class="clearfix"></div>
				</div>
				<!-- 搜索框end -->
				<div class="clearfix"></div>
				<!--------------------  表格 start -------------------->
				<div id="production_table_main">
					<div id="production_table_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" c-height="55%">
						<table id="production_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 500px;">
							<thead>                              
								<tr>
									<th class="colwidth-5 fixed-column">
										<div class="font-size-12 line-height-18" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="colwidth-9 sorting" onclick="production.orderBy('jx','G2014')" name="column_jx">
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div>
									</th>
									<th class="colwidth-15">
										<div class="important">
											<div class="font-size-12 line-height-18">指令描述</div>
											<div class="font-size-9 line-height-18">Order Des</div>
										</div>
									</th>
									<th class="colwidth-5">
										<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">Status</div>
									</th>
									<th class="colwidth-5">
										<div class="font-size-12 line-height-18">版本</div>
										<div class="font-size-9 line-height-18">Rev.</div>
									</th>
									<th class="colwidth-10">
										<div class="important">
											<div class="font-size-12 line-height-18">工卡</div>
											<div class="font-size-9 line-height-18">Work Card</div>
										</div>
									</th>
									<th class="colwidth-10">
										<div class="important">
											<div class="font-size-12 line-height-18">指令编号</div>
											<div class="font-size-9 line-height-18">Order No.</div>
										</div>
									</th>
									<th class="colwidth-15">
										<div class="important">
											<div class="font-size-12 line-height-18">ATA章节号</div>
											<div class="font-size-9 line-height-18">ATA</div>
										</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18">生效时间</div>
										<div class="font-size-9 line-height-18">Effect Time</div>
									</th>
								</tr> 
							</thead>
							<tbody id="production_table_list">
							</tbody>
						</table>
					</div>
					
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="production_table_pagination">
					</div>
					<div class="clearfix"></div>
				</div>
				<!--------------------  表格 end -------------------->
				<div class="clearfix"></div>
				
				<div class='displayContent' style='display:none;' >
					<div id="hideIcon" class="pull-right" style='height:1px;padding-right:8px;margin-top:1px;'>
						<img src="${ctx}/static/images/down.png" onclick='TableUtil.hideDisplayContent()' style="width:33px;cursor:pointer;">
					</div>
					<ul class="nav nav-tabs tabNew-style bottom_hidden_Ul" style='padding-top:0px !important;'>
                      	<li class="active" >
                      		<a href='#production_process_record_tab' data-toggle="tab">
                      			<div class="font-size-12 line-height-12">流程记录</div>
                    			<div class="font-size-9 line-height-9">Process Record</div>
                      		</a>
                      	</li>
                      	<li>
                      		<a href='#production_history_version_tab' data-toggle="tab">
                      			<div class="font-size-12 line-height-12">历史版本</div>
                    			<div class="font-size-9 line-height-9">History Version</div>
                      		</a>
                      	</li>
                    </ul>
                    <div class="tab-content" style='padding:0px;'>
	                    <div id="production_process_record_tab" class="tab-pane fade active in">
	                    	<div class="panel-body padding-top-5">
		                    	<%@ include file="/WEB-INF/views/project2/production/production_process_record.jsp" %>
	                    	</div>
	                    </div>
	                    <div id="production_history_version_tab" class="tab-pane fade">
	                    	<div class="panel-body padding-top-5">
		                    	<%@ include file="/WEB-INF/views/project2/production/production_history_version.jsp" %>
	                    	</div>
	                    </div>
                    </div>
				</div>
			</div>
		</div>
	</div>

<%@ include file="/WEB-INF/views/project2/production/production_detail.jsp" %>
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/production/production_main.js"></script>
</body>
</html>