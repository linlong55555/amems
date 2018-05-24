<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>报废清单</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" name="zzjgid" value=${user.jgdm} />
<input type="hidden" id="userId" name="userId" value=${user.id} />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
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
	
	//导航
	Navigation(menuCode);
});
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body padding-bottom-0">
				 <div  class='searchContent row-height' >
				<a href="#" onclick="exportExcel()"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left form-group checkPermission"
					 permissioncode="aerialmaterial:scrappedlist:main:01"
					><div class="font-size-12">导出</div>
					<div class="font-size-9">export</div>
				</a>

					<!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0 form-group ">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='件号/序列号/批次号/报废单号' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                        <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!-- 搜索框end -->
				
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">报废日期</div>
							<div class="font-size-9">Scrap Date</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="spsj" readonly />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">航材类型</div>
							<div class="font-size-9">Airmaterial type</div></span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="hclx" class="form-control "  name="hclx">
								<option value="">显示全部All</option>
								<c:forEach items="${materialTypeEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">状态</div>
							<div class="font-size-9">State</div></span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="zt" class="form-control "  name="zt">
									<option value="">显示全部</option>
									<option value="3">审核通过</option>
									<option value="11">撤销</option>
									<option value="9">指定结束</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="zzjg" class="form-control " name="zzjg">
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
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" style="overflow-x: auto;width: 100%;">
						<table id="quality_check_list_table" class="table table-thin table-bordered table-set" style="min-width: 2600px !important">
							<thead>
							<tr>
							<th class="fixed-column colwidth-5"  >
								<div class="font-size-12 line-height-18" >序号</div>
								<div class="font-size-9 line-height-18" >No.</div>
							</th>
							<th class="sorting fixed-column colwidth-10" onclick="orderBy('bjh')" id="bjh_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >件号</div>
									<div class="font-size-9 line-height-18" >P/N</div>
								</div>
							</th>
							<th class="sorting colwidth-13" onclick="orderBy('ywms')" id="ywms_order">
									<div class="font-size-12 line-height-18" >英文名称</div>
									<div class="font-size-9 line-height-18" >F.Name</div>
							</th>
							<th class="sorting colwidth-13" onclick="orderBy('zwms')" id="zwms_order">
									<div class="font-size-12 line-height-18" >中文名称</div>
									<div class="font-size-9 line-height-18" >CH.Name</div>
							</th>
							<th class="sorting colwidth-7" onclick="orderBy('kcsl')" id="kcsl_order">
									<div class="font-size-12 line-height-18" >报废数量</div>
									<div class="font-size-9 line-height-18" >Num</div>
							</th>
							<th class="sorting colwidth-7" onclick="orderBy('jldw')" id="jldw_order">
									<div class="font-size-12 line-height-18" >计量单位</div>
									<div class="font-size-9 line-height-18" >Unit</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('gljb')" id="gljb_order">
									<div class="font-size-12 line-height-18" >管理级别</div>
									<div class="font-size-9 line-height-18" >Level</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('sn')" id="sn_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >序列号</div>
									<div class="font-size-9 line-height-18" >S/N</div>
								</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('pch')" id="pch_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >批次号</div>
									<div class="font-size-9 line-height-18" >S/N</div>
								</div>
							</th>
							<th class="sorting colwidth-7" onclick="orderBy('hcjz')" id="hcjz_order">
									<div class="font-size-12 line-height-18" >航材价值</div>
									<div class="font-size-9 line-height-18" >Material Value</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('kccb')" id="kccb_order">
									<div class="font-size-12 line-height-18" >库存成本(人民币:元)</div>
									<div class="font-size-9 line-height-18" >Stock Cost(RMB:YUAN)</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('xhdh')" id="xhdh_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >报废单号</div>
									<div class="font-size-9 line-height-18" >No.</div>
								</div>
							</th>
							<th class="sorting colwidth-5" onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18" >状态</div>
									<div class="font-size-9 line-height-18" >Applicant</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('bfsj')" id="bfsj_order">
									<div class="font-size-12 line-height-18" >报废时间</div>
									<div class="font-size-9 line-height-18" >Destruction Date</div>
							</th>
							<th class="sorting colwidth-13" onclick="orderBy('cjjh')" id="cjjh_order">
									<div class="font-size-12 line-height-18" >厂家件号</div>
									<div class="font-size-9 line-height-18" >MP/N</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('hclx')" id="hclx_order">
									<div class="font-size-12 line-height-18" >航材类型</div>
									<div class="font-size-9 line-height-18" >Airmaterial type</div>
							</th>					
							<th class="sorting colwidth-7" onclick="orderBy('ckmc')" id="ck_order">
									<div class="font-size-12 line-height-18" >仓库</div>
									<div class="font-size-9 line-height-18" >Store</div>
							</th>
							<th class="sorting colwidth-13" onclick="orderBy('kwh')" id="kw_order">
									<div class="font-size-12 line-height-18" >库位</div>
									<div class="font-size-9 line-height-18" >Storage Location</div>
							</th>
							<th class="sorting colwidth-13" onclick="orderBy('dprtcode')" id="dprtcode_order">
									<div class="font-size-12 line-height-18" >组织机构</div>
									<div class="font-size-9 line-height-18" >Organization</div>
							</th>
							
							</tr> 
			         		 </thead>
							<tbody id="hasScrappedListlist">
							</tbody>
							
						</table>
					</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
					</div>
					
					<div class="clearfix"></div>
				</div>
			</div>

	</div>
	
	
	</div>
		<script type="text/javascript"
		src="${ctx}/static/js/thjw/material/scrappedList/scrappedList_main.js"></script>
	<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
</body>
</html>