<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>待办事宜</title>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
var param_lybh = "${param.lybh}";
var param_fjjx = "${param.fjjx}";
var param_dbgzlx = "${param.dbgzlx}";
var param_todoId = "${param.todoId}";
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">

	<div class="clearfix"></div>
	<div class="page-content">
	<input type="hidden" id="userid" value="${user.id}">
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<!-- <input type="hidden" id="adjustHeight" value="0"> -->
			<!--导航结束  -->
			<div class="panel-body padding-bottom-0">
			   <div class='margin-top-0 searchContent row-height'>
			   <div class=" col-lg-3 col-md-3 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
				<!-- 这个div用来右对齐 -->
				</div>
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div id="jxdiv" class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<label style=' font-weight:normal' class='margin-right-5 label-input'><input type='radio' id="zt1" onclick="search();" name='radio99' checked="checked" value="1"/>&nbsp;待处理</label>
							<label style=' font-weight:normal' class='label-input'><input type='radio' name='radio99' id="zt2" onclick="search();" value="2"/>&nbsp;已处理</label>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">待办工作</div>
							<div class="font-size-9">To Do Work</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
							<select id="daibangongzuo" class="form-control "  name="daibangongzuo" onchange="search();">
							</select>
						</div>
				   </div>
					<!-- 搜索框start -->
				   <div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>	
						<!-- 搜索框start -->
						<div class=" col-xs-12 input-group input-group-search" >
						    <input type="text" placeholder='来源'  id="keyword_search" class="form-control">
                            <div class="input-group-addon btn-searchnew " style="padding-right: 0px !important;">
			                    <button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="search();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                	</div>
						</div>
				   </div>
				<!-- 搜索框end -->
				<div class="clearfix"></div>
				</div>
			<div class="clearfix"></div>
				
			<div class='table_pagination'>
				<!-- 表格 -->
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-h" >
						<table id="quality_check_list_table"  class="table table-thin table-bordered table-striped table-hover table-set" style="min-width:1200px">
							<thead>
								<tr>
									<th id="cz_main_table" class="colwidth-5">
										<div class="font-size-12 line-height-18 ">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th  class="sorting colwidth-13" onclick="orderBy('lybh')" id="lybh_order">
										<div class="important"><div class="font-size-12 line-height-18">来源</div>
										<div class="font-size-9 line-height-18">Source No.</div></div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('fjjx')" id="fjjx_order">
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</th>
									<th  class="sorting colwidth-13" onclick="orderBy('dbgzlx')" id="dbgzlx_order">
										<div class="font-size-12 line-height-18">待办工作</div>
										<div class="font-size-9 line-height-18">To Do Work</div>
									</th>	
									<th  class="sorting" onclick="orderBy('sm')" id="sm_order">
										<div class="font-size-12 line-height-18">说明</div>
										<div class="font-size-9 line-height-18">Description</div>
									</th>	
									<th  class="sorting  colwidth-9" onclick="orderBy('blqx')" id="blqx_order">
										<div class="font-size-12 line-height-18">期限</div>
										<div class="font-size-9 line-height-18">Limit</div>
									</th>	
									<th  class="downward colwidth-20" onclick="vieworhideWorkContentAll()" name="th_return">
										<div class="font-size-12 line-height-18">处理结果</div>
										<div class="font-size-9 line-height-18">Result</div>
									</th>
									<th class="sorting  colwidth-13" onclick="orderBy('fksj')" id="fksj_order">
										<div class="font-size-12 line-height-18" id="fksj_main_ch" >接收时间</div>
										<div class="font-size-9 line-height-18">Date</div>
									</th>
									<th class="sorting colwidth-20 fksmMainTable" onclick="orderBy('fkyj')" id="fkyj_order">
										<div class="font-size-12 line-height-18">反馈说明</div>
										<div class="font-size-9 line-height-18">FeedBack Description</div>
									</th>
								</tr>
							</thead>
							<tbody id="to_do_list">
							</tbody>
					</table>
				</div>	
				
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
				</div>
				<div class='clearfix'></div>
			</div>	
		</div>
	</div>
</div>	
<!--日志END  -->
<%@ include file="/WEB-INF/views/project2/todo/todo_feedback.jsp"%><!-- 反馈对话框 -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/todo/todo_main.js"></script><!--当前界面js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/todo/todo_option.js"></script><!-- 处理结果js  -->
</body>
</html>
