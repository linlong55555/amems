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
			    <div class='searchContent margin-top-0 row-height' >
			    <!-- 上传按钮  -->
				   <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-4 col-sm-4 col-xs-2 text-left padding-left-0 padding-right-0">
						<a href="javascript:exportExcel();" type="button" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left ">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
			           	</a>
						</span>
				   </div>
				   
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
							<div class="font-size-12">技术文件关闭状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-lg-8 col-md-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0" >
							<select class='form-control' id="zt">
								<option value="">显示全部 All</option>
								<option value="0">开启</option>
								<option value="9">关闭</option>
						    </select>
						</div>
				</div>
				<!-- 关键字搜索 -->
				<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='适航性资料编号/修正案号/指令编号'  class="form-control" id="keyword_search" >
                    <div class="input-group-addon btn-searchnew" >
                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();" style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                    </div>
                    <div class="input-group-addon btn-searchnew-more">
	                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 "  id="btn" onclick="search();">
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
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="fjzch" class="form-control"  name="fjzch">
							</select>
						</div>
					</div>
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
								  <!-- <th class="colwidth-5" >
										<div class="font-size-12 line-height-18">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th> -->
									 <th class="colwidth-5" >
										<div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div>
									</th>	
									<th class="colwidth-10 sorting" onclick="orderBy('JSWJ_JKZT')" id="JSWJ_JKZT_order">										
											<div class="font-size-12 line-height-18">技术文件关闭状态</div>
											<div class="font-size-9 line-height-18">Status</div>									
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('SYX_JKZT')" id="SYX_JKZT_order">	
										<div class="font-size-12 line-height-18">关闭状态</div>
										<div class="font-size-9 line-height-18">Status</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('JSWJLX')" id="JSWJLX_order">
										<div class="font-size-12 line-height-18">文件类型</div>
										<div class="font-size-9 line-height-18">Doc. Type</div>
									</th>
									
									<th class="colwidth-10 sorting" onclick="orderBy('XZAH')" id="XZAH_order">
										<div class="important">
											<div class="font-size-12 line-height-18">修正案号</div>
											<div class="font-size-9 line-height-18">Amendment </div>
										</div>
									</th>
									
									<th class="colwidth-13 sorting" onclick="orderBy('JSWJBH')" id="JSWJBH_order">
										<div class="important">
											<div class="font-size-12 line-height-18">文件编号</div>
											<div class="font-size-9 line-height-18">Doc. No.</div>
										</div>
									</th>
																
									<!-- <th class="colwidth-25 sorting" onclick="orderBy('JSWJZT')" id="JSWJZT_order">
										<div class="font-size-12 line-height-18">主题</div>
										<div class="font-size-9 line-height-18">Subject</div>
									</th> -->
									
									<th class="colwidth-25" style="border-right:0px !important;" onclick="orderBy('JSWJZT')" id="JSWJZT_order">
										<div class="font-size-12 line-height-18">主题</div>
										<div class="font-size-9 line-height-18">Subject</div>
									</th>
									<th style="width:20px;border-left:0px !important;" >
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('SXRQ')" id="SXRQ_order">
										<div class="font-size-12 line-height-18">生效日期</div>
										<div class="font-size-9 line-height-18">Effect Date</div>
									</th>
									<th class="colwidth-25" style="border-right:0px !important;">
										<div class="font-size-12 line-height-18">关联文件</div>
										<div class="font-size-9 line-height-18">Related Doc.</div>
									</th>
									<th style="width:20px;border-left:0px !important;" >
									</th>
									<th class="colwidth-15 sorting" onclick="orderBy('bdwsyx')" id="bdwsyx_order">										
											<div class="font-size-12 line-height-18">本单位适用性</div>
											<div class="font-size-9 line-height-18">Applicability</div>										
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('PGDH')" id="SYLB_order">
										<div class="font-size-12 line-height-18">评估单号</div>
										<div class="font-size-9 line-height-18">Technical No.</div>
									</th>
										
									<th class="colwidth-10 sorting" onclick="orderBy('JX')" id="JX_order">
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</th>
									<th class="colwidth-10" onclick="orderBy('FJBJ')" id="FJBJ_order">
										<div class="font-size-12 line-height-18">飞机/部件</div>
										<div class="font-size-9 line-height-18">A/C Type/PN</div>
									</th>
									<th class="colwidth-15" onclick="orderBy('zllx')" id="zllx_order">
										<div class="font-size-12 line-height-18">下达指令类型</div>
										<div class="font-size-9 line-height-18">Type</div>
									</th>
									<th class="colwidth-15" onclick="orderBy('zlbh')" id="zlbh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">指令编号</div>
											<div class="font-size-9 line-height-18">No.</div>
										</div>
									</th>
									<th class="colwidth-9" onclick="orderBy('xfrq')" id="xfrq_order">
										<div class="font-size-12 line-height-18">下发日期</div>
										<div class="font-size-9 line-height-18">Issued Date</div>
									</th>
									<th class="colwidth-10" onclick="orderBy('dqrq')" id="dqrq_order">
										<div class="font-size-12 line-height-18">到期日期</div>
										<div class="font-size-9 line-height-18">Expire Date</div>
									</th>
									<th class="colwidth-7">
										<div class="font-size-12 line-height-18">上次执行</div>
										<div class="font-size-9 line-height-18">Last Execute</div>
									</th>
									<th class="colwidth-7">
										<div class="font-size-12 line-height-18">下次到期</div>
										<div class="font-size-9 line-height-18">Next Expire</div>
									</th>
									<th class="colwidth-15 downward"  onclick="vieworhideWorkContentAll()" name="th_return">
										<div class="font-size-12 line-height-18">执行记录</div>
										<div class="font-size-9 line-height-18">Executive Logging</div>
									</th>
									<th class="colwidth-15" onclick="orderBy('syx_jkbz')" id="syx_jkbz_order">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Note</div>
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
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/airworthinessAdmonitoring/airworthiness_admonitoring_main.js"></script>
	<%@ include file="/WEB-INF/views/quality/airworthinessAdmonitoring/airworthiness_admonitoring_close.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%><!--章节号弹窗-->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/monitor/monitor_unit.js"></script> <!-- 监控项转换js -->
		<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>
