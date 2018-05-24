<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>拆装记录</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	
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
						componentiohistoryMain.reload();//调用主列表页查询
					}
				}
			});
		});
	</script>

</head>
<body class="page-header-fixed">
<input type="hidden" id="dprtId" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="userType" value="${user.userType}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading  ">
					<div id="NavigationBar"></div>
				</div>
				<div class="panel-body padding-bottom-0" >
				
					<div class='searchContent margin-top-0  row-height'>
						<div class=" col-lg-3 col-md-3 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
							<a href="javascript:showImportModal();" permissioncode='produce:componentiohistory:main:01' class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission">
								<div class="font-size-12">导入</div>
								<div class="font-size-9">Import</div>
							</a>
						</div>
						<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
							<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">导入状态</div>
								<div class="font-size-9 ">Import Option</div>
							</span>
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
								<select id="drbs" class="form-control " name="drbs" onchange="componentiohistoryMain.reload()" >
									<option value="" >显示全部  All</option>
									<option value="0">非导入</option>
									<option value="1">导入</option>
								</select>
							</div>
						</div>
						<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
							<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">飞机注册号</div>
								<div class="font-size-9 ">A/C REG</div>
							</span>
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
								<select id="fjzch" class="form-control" onchange="componentiohistoryMain.reload()">
									<option value="" selected="selected">查看全部</option>
								</select>
							</div>
						</div>
						
						<!-- 搜索框start -->
						<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " style='padding-left:15px;'>
							<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='件号/序列号/名称/章节号'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="componentiohistoryMain.reload();" style='margin-right:0px !important;'>
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
						
						<!-- 更多的搜索框 -->
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-bottom-10 display-none border-cccccc" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">安装时间开始</div>
							<div class="font-size-9">Installed Time</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" data-date-format="yyyy-mm-dd" id="azsjBegin" type="text">
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">安装时间截止</div>
							<div class="font-size-9">Installed Time</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" data-date-format="yyyy-mm-dd" id="azsjEnd" type="text">
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">拆下时间开始</div>
							<div class="font-size-9">Removed Time</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" data-date-format="yyyy-mm-dd" id="ccsjBegin" type="text">
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">拆下时间结束</div>
							<div class="font-size-9">Removed Time</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" data-date-format="yyyy-mm-dd" id="ccsjEnd" type="text">
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control " name="dprtcode_search" onchange="dprtChange()" >
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
						
						
						
						<div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>

					<div id="mel_main_table_top_div" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-h" style="overflow-x: auto;width: 100%;">
						<table id="main_list_table" class="table table-thin table-set table-striped table-bordered table-hover tableRe" style="min-width:3100px!important">
							<thead>
								<tr class="nav-tabs">
									<th colspan="8" >
									</th>
									<th colspan="9">
										<div class="font-size-12 line-height-18">装上</div>
										<div class="font-size-9 line-height-18">Mount</div>
									</th>
									<th colspan="7">
										<div class="font-size-12 line-height-18">拆下</div>
										<div class="font-size-9 line-height-18">Remove</div>
									</th>
								</tr>
							
								<tr>
									<th class="fixed-column colwidth-5 "  style="min-width:100px">
										<div class="font-size-12 line-height-18 ">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="fixed-column colwidth-13 sorting"  onclick="orderBy('bjh')" id="bjh_order" style="min-width:100px">
										<div class="important">
											<div class="font-size-12 line-height-18 ">件号</div>
											<div class="font-size-9 line-height-18">P/N</div>
										</div>
									</th>
									<th  class="fixed-column colwidth-7 sorting"  onclick="orderBy('xlh')" id="xlh_order" style="min-width:100px">
										<div class="important">
											<div class="font-size-12 line-height-18">序列号</div>
											<div class="font-size-9 line-height-18">S/N</div>
										</div>
									</th>
									<!--  
									<th  class="fixed-column colwidth-7 sorting"  onclick="orderBy('pch')" id="pch_order" style="min-width:100px">
										<div class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">Batch No.</div>
									</th>
									
									-->
							
									<th class="sorting colwidth-17" onclick="orderBy('bjywms')" id="bjywms_order" colspan='2'>
										<div class="important">
											<div class="font-size-12 line-height-18">名称</div>
											<div class="font-size-9 line-height-18">Name</div>
										</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('zjh')" id="zjh_order">
										<div class="font-size-12 line-height-18">ATA章节号</div>
										<div class="font-size-9 line-height-18">ATA</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('fjzch')" id="fjzch_order">
										<div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-18">在机使用量</div>
										<div class="font-size-9 line-height-18">Machine Use</div>
									</th>
									<th class="sorting colwidth-18" onclick="orderBy('azsj')" id="azsj_order">
										<div class="font-size-12 line-height-18">安装时间</div>
										<div class="font-size-9 line-height-18">Installation Time</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('azjldh')" id="azjldh_order">
										<div class="font-size-12 line-height-18">拆装记录单号</div>
										<div class="font-size-9 line-height-18">Disassembling Record</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('azr')" id="azr_order">
										<div class="font-size-12 line-height-18">工作者</div>
										<div class="font-size-9 line-height-18">Workers</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('zjsl')" id="zjsl_order">
										<div class="font-size-12 line-height-18">装机数量</div>
										<div class="font-size-9 line-height-18">Number</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('fjzw')" id="fjzw_order">
										<div class="font-size-12 line-height-18">飞机站位</div>
										<div class="font-size-9 line-height-18">Position</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('zjjlx')" id="zjjlx_order">
										<div class="font-size-12 line-height-18">装机件类型</div>
										<div class="font-size-9 line-height-18">Type</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-18">附件</div>
										<div class="font-size-9 line-height-18">Attachment</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-18">装机前已用</div>
										<div class="font-size-9 line-height-18">Machine used</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('azbz')" id="azbz_order">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Note</div>
									</th>
									<th class="sorting colwidth-13" onclick="orderBy('ccsj')" id="ccsj_order">
										<div class="font-size-12 line-height-18">拆下时间</div>
										<div class="font-size-9 line-height-18">Remove Time</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('ccjldh')" id="ccjldh_order">
										<div class="font-size-12 line-height-18">拆装记录单号</div>
										<div class="font-size-9 line-height-18">Disassembling Record</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('ccr')" id="ccr_order">
										<div class="font-size-12 line-height-18">工作者</div>
										<div class="font-size-9 line-height-18">Workers</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-18">附件</div>
										<div class="font-size-9 line-height-18">Attachment</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-18">拆下已用</div>
										<div class="font-size-9 line-height-18">Remove Used</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('cj_zsjjh')" id="cj_zsjjh_order">
										<div class="font-size-12 line-height-18">对应装上部件</div>
										<div class="font-size-9 line-height-18">Corresponding</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('ccbz')" id="ccbz_order">
										<div class="font-size-12 line-height-18">拆装原因</div>
										<div class="font-size-9 line-height-18">Dismantling</div>
									</th>
								</tr>
							</thead>
						<tbody id="list">
						</tbody>
							
						</table>
					</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
					<div class="clearfix"></div>
				</div>
			</div>

	</div>
	<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/componentiohistory/componentiohistory_main.js"></script>
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>