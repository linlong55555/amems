<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>航材证书</title>
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
						certificateMain.reload();//调用主列表页查询
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
				<div class="panel-body searchContent" >			
					<div class='margin-top-0'>
						<div class=" col-lg-6 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
							<!-- <a href="javascript:add();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left ">
								<div class="font-size-12">导入</div>
								<div class="font-size-9">Ismport</div>
							</a> -->
						</div>
						
						<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
							<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">组织机构</div>
								<div class="font-size-9 ">Organization</div>
							</span>
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
								<select id="dprtcode_search" class="form-control " name="dprtcode_search" onchange="dprtChange()" >
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<!-- 搜索框start -->
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
							<div class="col-xs-12 input-group input-group-search">
								<input type="text" placeholder='件号/序列号/批次号/名称/厂家件号'  class="form-control" id="keyword_search" >
			                    <div class="input-group-addon btn-searchnew" >
			                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 row-height" onclick="certificateMain.reload();" style='margin-right:0px !important;'>
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
			                  		</button>
			                    </div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>

					<div id="mel_main_table_top_div" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-h table-set" style="overflow-x: auto;width: 100%;">
						<table id="main_list_table" class="table table-thin table-striped table-bordered table-set table-hover" style="min-width:auto !important">
							<thead>
								<tr>
									<th class=" fixed-column colwidth-5" >
										<div class="font-size-12 line-height-18 ">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="sorting fixed-column colwidth-7" onclick="orderBy('bjh')" id="bjh_order" >
										<div class="important">
											<div class="font-size-12 line-height-18 ">件号</div>
											<div class="font-size-9 line-height-18">P/N</div>
										</div>
									</th>
									<th class="sorting fixed-column colwidth-7" onclick="orderBy('xingh')" id="xingh_order" >
										<div class="font-size-12 line-height-18 ">型号</div>
										<div class="font-size-9 line-height-18">Model</div>
									</th>
									<th  class="sorting fixed-column colwidth-7" onclick="orderBy('xlh')" id="xlh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">序列号</div>
											<div class="font-size-9 line-height-18">S/N</div>
										</div>
									</th>
									<th  class="sorting fixed-column colwidth-7" onclick="orderBy('pch')" id="pch_order">
										<div class="important">
											<div class="font-size-12 line-height-18">批次号</div>
											<div class="font-size-9 line-height-18">Batch No.</div>
										</div>
									</th>
									<th class="sorting colwidth-20" onclick="orderBy('ywms')" id="ywms_order">
										<div class="important">
											<div class="font-size-12 line-height-18">名称</div>
											<div class="font-size-9 line-height-18">Name</div>
										</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('cjjh')" id="cjjh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">厂家件号</div>
											<div class="font-size-9 line-height-18">Manufacturer P/N</div>
										</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('jhly')" id="jhly_order">
										<div class="font-size-12 line-height-18">件号来源</div>
										<div class="font-size-9 line-height-18">P/N Source</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('zjh')" id="zjh_order">
										<div class="font-size-12 line-height-18">ATA章节号</div>
										<div class="font-size-9 line-height-18">ATA</div>
									</th>
									<th class=" colwidth-10">
										<div class="font-size-12 line-height-18">证书信息</div>
										<div class="font-size-9 line-height-18">Certificate Info</div>
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
    <%@ include file="/WEB-INF/views/demo/certificate/certificate_open.jsp"%>
    <%@ include file="/WEB-INF/views/produce/installationlist/installationlist_certificate.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
    <%@ include file="/WEB-INF/views/open_win/history_certificate_win.jsp"%><!-----证书对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/demo/certificate/certificate_main.js"></script>
</body>
</html>