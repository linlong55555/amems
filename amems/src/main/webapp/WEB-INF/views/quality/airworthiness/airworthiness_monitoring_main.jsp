<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>适航性资料监控</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
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
			    <div class='searchContent margin-top-0 row-height'>
			    <!-- 上传按钮  -->
				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-4 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">文件类型</div>
							<div class="font-size-9">File Type</div>
						</span>
						<div class="col-lg-8 col-md-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" id="wjlxHtml">
						</div>
				</div>
				
				 <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-4 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-lg-8 col-md-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0" >
							<select class='form-control' id="zt">
								<option value="">全部</option>
								<c:forEach items="${ztEnum}" var="type">
									<option value="${type.id}">${type.name}</option>
								</c:forEach>
						    </select>
						</div>
				</div>
				<div class="col-xs-12 col-sm-6 col-lg-3 col-md-3 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class="form-control" id="fjzch"></select>
						</div>
					</div>	 
				<!-- 关键字搜索 -->
				<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='适航性资料编号/修正案号/EO编号'  class="form-control" id="keyword_search" >
                    <div class="input-group-addon btn-searchnew" >
                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();" style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                    </div>
                    <div class="input-group-addon btn-searchnew-more">
	                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="search();">
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
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-0 margin-bottom-10 display-none border-cccccc" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">生效日期</div>
							<div class="font-size-9">Effect date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly id="sxrq" />
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">ATA章节号</div>
							<div class="font-size-9">ATA</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group" style="width: 100%">
								<input type="hidden"  name="zjhMax"  id="zjhMax">
								<input type="text"  name="zjhmsMax" class="form-control  readonly-style " id="zjhmsMax" readonly="readonly" ondblclick='initFixChapter();'>
								<span class=" input-group-btn" >
									<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="initFixChapter()">
										<i class="icon-search cursor-pointer" ></i>
									</button>
								</span>
		                	</div>
						</div>
					</div>				
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control "  name="dprtcode">
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
				<div class="col-lg-12 col-md-12 padding-left-0  padding-right-0 margin-top-0 table-h table-set" style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-striped table-hover table-set" id="airworthiness_list_table">
							<thead>
								<tr>
									<th class="fixed-column colwidth-7" >
										<div class="font-size-12 line-height-18" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="colwidth-9 sorting" onclick="orderBy('JSWJLX')" id="JSWJLX_order">
										<div class="font-size-12 line-height-18">文件类型</div>
										<div class="font-size-9 line-height-18">Doc. Type</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('JSWJLY')" id="JSWJLY_order">
										<div class="font-size-12 line-height-18">来源</div>
										<div class="font-size-9 line-height-18">Issued By</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('JSWJBH')" id="JSWJBH_order">
										<div class="important">
											<div class="font-size-12 line-height-18">适航性资料编号</div>
											<div class="font-size-9 line-height-18">Doc. No.</div>
										</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('XZAH')" id="XZAH_order">
										<div class="important">
											<div class="font-size-12 line-height-18">修正案号</div>
											<div class="font-size-9 line-height-18">Amendment </div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('SXRQ')" id="SXRQ_order">
										<div class="font-size-12 line-height-18">生效日期</div>
										<div class="font-size-9 line-height-18">Effect Date</div>
									</th>									
									<th class="colwidth-15 sorting" onclick="orderBy('JSWJZT')" id="JSWJZT_order">
										
											<div class="font-size-12 line-height-18">主题</div>
											<div class="font-size-9 line-height-18">Subject</div>
										
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-18">文件</div>
										<div class="font-size-9 line-height-18">File</div>
									</th>
									<th class="colwidth-13" >
										<div class="font-size-12 line-height-18">关联适航性资料</div>
										<div class="font-size-9 line-height-18">Related Doc.</div>
									</th>
									<th class="colwidth-15" >
										<div class="font-size-12 line-height-18">关联适航性资料文件</div>
										<div class="font-size-9 line-height-18">Related Doc. File</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-18">预计颁发指令</div>
										<div class="font-size-9 line-height-18">Order</div>
									</th>
									<th class="colwidth-9" >
										<div class="font-size-12 line-height-18">下达指令类型</div>
										<div class="font-size-9 line-height-18">Odery Type</div>
									</th>
									<th class="colwidth-10" >
										<div class="important">
											<div class="font-size-12 line-height-18">指令编号</div>
											<div class="font-size-9 line-height-18">Order No.</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('GZGS')" id="GZGS_order">									
											<div class="font-size-12 line-height-18">工作概述</div>
											<div class="font-size-9 line-height-18">Summary</div>										
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('JX')" id="JX_order">
										<div class="font-size-12 line-height-18">飞机机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('FJZCH')" id="FJZCH_order">
										<div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18">MSN</div>
										<div class="font-size-9 line-height-18">MSN</div>
									</th>
									<th class="colwidth-9 sorting" onclick="orderBy('SYLB')" id="SYLB_order">										
											<div class="font-size-12 line-height-18">适用类别</div>
											<div class="font-size-9 line-height-18">Applicability</div>										
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('BJH')" id="BJH_order">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('BJXLH')" id="BJXLH_order">
										<div class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</th>
									<th class="colwidth-7 " >
										<div class="font-size-12 line-height-18">首次</div>
										<div class="font-size-9 line-height-18">Initial</div>
									</th>
									<th class="colwidth-7" >										
											<div class="font-size-12 line-height-18">周期</div>
											<div class="font-size-9 line-height-18">Cycle</div>										
									</th>
									<th class="colwidth-7" >
										<div class="font-size-12 line-height-18">上次执行值</div>
										<div class="font-size-9 line-height-18">Value</div>
									</th>
									<th class="colwidth-7" >
										<div class="font-size-12 line-height-18">下次计划值</div>
										<div class="font-size-9 line-height-18">Value</div>
									</th>
									<th class="colwidth-15" >
										<div class="font-size-12 line-height-18">指令单终止条件</div>
										<div class="font-size-9 line-height-18">Condition</div>
									</th>
									<th class="colwidth-13" >
										<div class="font-size-12 line-height-18">重量与平衡</div>
										<div class="font-size-9 line-height-18">Weight & Balance</div>
									</th>
									<th class="colwidth-7" >
										<div class="font-size-12 line-height-18">指令单状态</div>
										<div class="font-size-9 line-height-18">Status</div>
									</th>
									<th class="colwidth-13" >
										<div class="font-size-12 line-height-18">不适用原因</div>
										<div class="font-size-9 line-height-18">Reason</div>
									</th>
									<th class="colwidth-7" >
										<div class="font-size-12 line-height-18">ATA章节号</div>
										<div class="font-size-9 line-height-18">ATA</div>
									</th>
									<th class="colwidth-9" >
										<div class="font-size-12 line-height-18">质量监控状态</div>
										<div class="font-size-9 line-height-18">Status</div>
									</th>
									<th class="colwidth-10 ">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>							
								</tr>
							</thead>
							<tbody id="airworthiness_list">
							</tbody>
					</table>
				</div>	
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
				<div class='clearfix'></div>
				</div>	
			</div>
		</div>		
	</div>
	<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/airworthiness/airworthiness_monitoring_main.js"></script>
	<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%><!--章节号弹窗-->
		<script type="text/javascript" src="${ctx}/static/js/thjw/common/monitor/monitor_unit.js"></script> <!-- 监控项转换js -->
		<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>
