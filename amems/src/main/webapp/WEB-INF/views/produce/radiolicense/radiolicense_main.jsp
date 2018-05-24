<%@ page contentType="text/html; charset=utf-8"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>飞机三证监控</title>
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
					aircraftinfoMain.reload(false,null,null);//调用主列表页查询
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
				     
					<div class='searchContent margin-top-0 row-height'>
						<div class=" col-lg-6 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
						</div>
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
							<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">机型</div>
								<div class="font-size-9 ">A/C Type</div>
							</span>
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
								<select id="jx_search" class="form-control" onchange="aircraftinfoMain.reload();">
									<option value="" selected="selected">显示全部All</option>
								</select>
							</div>
						</div>
						
						<!-- 搜索框start -->
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
							<div class="col-xs-12 input-group input-group-search">
								<input type="text" placeholder='飞机注册号/MSN/备注名/基地'  class="form-control" id="keyword_search" >
			                    <div class="input-group-addon btn-searchnew" >
			                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="aircraftinfoMain.reload(false,null,null);" style='margin-right:0px !important;'>
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
					<!-- 搜索框end -->
				
					<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">购买日期</div>
								<div class="font-size-9">PFD</div></span>
							<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="date-range-picker" readonly id="gmrq_search" />
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">生产日期</div>
								<div class="font-size-9">PD</div></span>
							<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="date-range-picker" readonly id="scrq_search" />
							</div>
						</div>
					
						<div class="col-lg-3 col-sm-6 col-xs-12	  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">归属</div>
								<div class="font-size-9">Attribution</div></span>
							<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="gs_search" class="form-control " name="gs_search" >
									<option value="ALL">显示全部</option>
									<option value="BDW">本单位</option>
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12	  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div></span>
							<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="dprtcode_search" class="form-control " name="dprtcode_search" onchange="dprtChange()" >
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					
					<div class="col-lg-3 col-sm-3 col-xs-12  text-right  pull-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>
					<div class="clearfix"></div>
                 </div>
					<div id="aircraftinfo_main_table_top_div" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-h table-set" style="overflow-x: auto;width: 100%;">
						<table id="quality_check_list_table" class="table table-thin table-striped table-bordered table-set table-hover" style="min-width: 2600px !important">
							<thead>
								<tr>
									<th class="fixed-column colwidth-3">
										<div class="font-size-12 line-height-18 ">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th  class="colwidth-3" >
										<div class="font-size-12 line-height-18">标识</div>
										<div class="font-size-9 line-height-18">Mark</div>
									</th>
									<th class="sorting colwidth-13" onclick="orderBy('fjjx')" id="fjjx_order">
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('fjzch')" id="fjzch_order">
										<div class="important">
											<div class="font-size-12 line-height-18">飞机注册号</div>
											<div class="font-size-9 line-height-18">A/C REG</div>
										</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('xlh')" id="xlh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">MSN </div>
											<div class="font-size-9 line-height-18">MSN</div>
										</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('bzm')" id="bzm_order">
										<div class="font-size-12 line-height-18">备注名</div>
										<div class="font-size-9 line-height-18">Note Name</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('ssdwid')" id="ssdwid_order">
										<div class="font-size-12 line-height-18">归属</div>
										<div class="font-size-9 line-height-18">Attribution</div>
									</th>
									<th class="sorting colwidth-15" onclick="orderBy('gjdjzh')" id="gjdjzh_order">
										<div class="font-size-12 line-height-18">国籍登记证号</div>
										<div class="font-size-9 line-height-18">Nationality No.</div>
									</th>
									<th class="sorting colwidth-15" onclick="orderBy('shzh')" id="shzh_order">
										<div class="font-size-12 line-height-18">适航证号</div>
										<div class="font-size-9 line-height-18">Airworthiness No.</div>
									</th>
									<th class="sorting colwidth-15" onclick="orderBy('wxdtxkzh')" id="wxdtxkzh_order">
										<div class="font-size-12 line-height-18">无线电台执照号</div>
										<div class="font-size-9 line-height-18">Radio No.</div>
									</th>
									<th class="colwidth-5">
										<div class="font-size-12 line-height-18">附件列表</div>
										<div class="font-size-9 line-height-18">Flies</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('jd')" id="jd_order">
										<div class="important">
											<div class="font-size-12 line-height-18">基地</div>
											<div class="font-size-9 line-height-18">Base</div>
										</div>
									</th>
									<th class="sorting colwidth-7" onclick="orderBy('scrq')" id="scrq_order">
										<div class="font-size-12 line-height-18">生产日期</div>
										<div class="font-size-9 line-height-18">PD</div>
									</th>
									<th class="sorting colwidth-7" onclick="orderBy('ccrq')" id="ccrq_order">
										<div class="font-size-12 line-height-18">出厂日期</div>
										<div class="font-size-9 line-height-18">MFD</div>
									</th>
									<th class="sorting colwidth-7" onclick="orderBy('gmrq')" id="gmrq_order">
										<div class="font-size-12 line-height-18">购买日期</div>
										<div class="font-size-9 line-height-18">PFD</div>
									</th>
									<th class="sorting colwidth-20" onclick="orderBy('gjdjzyxq')" id="gjdjzyxq_order">
										<div class="font-size-12 line-height-18">有效期</div>
										<div class="font-size-9 line-height-18">Valid Period</div>
									</th>
									<th class="sorting colwidth-7" onclick="" id="">
										<div class="font-size-12 line-height-18">剩余时间</div>
										<div class="font-size-9 line-height-18">Remains(天)</div>
									</th>
									<th class="sorting colwidth-5" onclick="orderBy('zt')" id="zt_order">
										<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">Status</div>
									</th>
									<th class="colwidth-13" onclick="orderBy('whrid')" id="whrid_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th class="sorting colwidth-13" onclick="orderBy('whsj')" id="whsj_order">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th class="sorting colwidth-13" onclick="orderBy('dprtcode')" id="dprtcode_order">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
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
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
	<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
    <%@ include file="/WEB-INF/views/produce/radiolicense/radiolicense_open.jsp" %> 
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/radiolicense/radiolicense_main.js"></script>
    <script src="${ctx}/static/js/thjw/common/preview.js"></script><!-- 下载 -->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>