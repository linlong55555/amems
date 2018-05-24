<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>盘点审核</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body >
	<input type="hidden" id="adjustHeight" value="235">
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>		
			<div class="panel-body padding-bottom-0">
				<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
					<div class="font-size-16 line-height-18">盘点信息</div>
					<div class="font-size-9 ">Basic Information</div>
				</div>
				
				<div class="col-xs-12 col-lg-2 col-sm-12 padding-left-0 border-r">
					<ul class="pandian">
                      	<li class="">盘点单号：<span id="vpddh"></span><input type="hidden" id="pdid" value="${id}" ></li>
                      	<li class="">盘点人：<span id="vpdrname"></span><input type="hidden" id="pdzt"></li>
                      	<li class="">盘点日期：<span id="vksrq"></span></li>
                      	<li class="">仓库：<span id="vckname"></span></li>
                      	<li class="">备注：<span id="vbz" title=""></span></li>
                    </ul>
                    
                    <div class="text-center" style="border-top: 1px solid #ccc;">
						<span class="font-size-14">库位/部件</span>
					</div>
					<div class="list-group table-h"  id="takeScope"  style="overflow: auto;max-height:100px;min-height:50px;">
					</div>
	                	
				</div>
				
				<div class="col-xs-12 col-lg-10 col-sm-12">
				
					<div class="pull-left padding-left-0 padding-right-0 margin-bottom-10" >   
                   		<input type="radio" name="viewType" value="1" checked=true onclick="searchRevision()"/>
                   		<span id="viewAll">
                   		显示全部
                   		&nbsp;
                   		&nbsp;
                   		</span>
                   		<input type="radio" name="viewType" value="2"  onclick="searchRevision()" />
                   		盈亏
                   		&nbsp;
                   		&nbsp;
                   		<input type="radio" name="viewType" value="3" onclick="searchRevision()" />
                   		盈
                   		&nbsp;
                   		&nbsp;
                   		<input type="radio" name="viewType" value="4" onclick="searchRevision()" />
                   		亏
                   		&nbsp;
                   		&nbsp;
                   		&nbsp;
                   		&nbsp;
                   		盘点类型：<span id="pdpz">0</span>
                   		&nbsp;
                   		&nbsp;
						 盘点数：<span id="pdjls">0</span>
						&nbsp;
                   		&nbsp;
						差异数：<span id="czcyjls">0</span>
                    </div>
                    
                    <div class="pull-left padding-left-10 padding-right-0" >   
							
                    </div>
                    
                    <div class="pull-right padding-left-0 padding-right-0 margin-bottom-10">	
					
						<!-- 搜索框start -->
						<div class=" pull-right padding-left-0 padding-right-0 ">
							<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
								<input type="text" placeholder='库位号/部件号/中英文/序列号/批次号' id="keyword_search" class="form-control ">
							</div>
		                    <div class=" pull-right padding-left-5 padding-right-0 ">   
								<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision()">
									<div class="font-size-12">查询</div>
									<div class="font-size-9">Search</div>
		                   		</button>
		                    </div> 
						</div>
						<!-- 搜索框end -->
					
					</div>
				
					<div class="col-xs-12 col-lg-12 col-sm-12 table-m  padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x: scroll;">
						<table id="take_view_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1400px;">
							<thead>
								<tr>
									<th class="colwidth-3" >
										<div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">NO.</div>
									</th>
									<th class="colwidth-20 sorting" onclick="orderBy('KCKWH')" id="KCKWH_order">
										<div class="important">
											<div class="font-size-12 line-height-18">库位号</div>
											<div class="font-size-9 line-height-18">Stock Location</div>
										</div>
									</th>
									<th class="colwidth-15 sorting" onclick="orderBy('HCBJH')" id="HCBJH_order">
										<div class="important">
											<div class="font-size-12 line-height-18">部件号</div>
											<div class="font-size-9 line-height-18">P/N</div>
										</div>
									</th>
									<th class="colwidth-25 sorting" onclick="orderBy('HCYWMS')" id="HCYWMS_order">
										<div class="important">
											<div class="font-size-12 line-height-18">英文名称</div>
											<div class="font-size-9 line-height-18">F.Name</div>
										</div>
									</th>
									<th class="colwidth-20 sorting" onclick="orderBy('HCZWMS')" id="HCZWMS_order">
										<div class="important">
											<div class="font-size-12 line-height-18">中文名称</div>
											<div class="font-size-9 line-height-18">CH.Name</div>
										</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('HCGLJB')" id="HCGLJB_order">
										<div class="font-size-12 line-height-18">管理级别</div>
										<div class="font-size-9 line-height-18">Management Level</div>
									</th>
									<th class="colwidth-20">
										<div class="important">
											<div class="font-size-12 line-height-18">序列号/批次号</div>
											<div class="font-size-9 line-height-18">S/N</div>
										</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('KCKCSL')" id="KCKCSL_order">
										<div class="font-size-12 line-height-18">库存数量</div>
										<div class="font-size-9 line-height-18">Stock Count</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('CKSL')" id="CKSL_order">
										<div class="font-size-12 line-height-18">盘点数量</div>
										<div class="font-size-9 line-height-18">Take Count</div>
									</th>
									<th  class="colwidth-5">
										<div class="font-size-12 line-height-18">差异</div>
										<div class="font-size-9 line-height-18">Differ</div>
									</th>
								</tr>
							</thead>
							<tbody id="takeStockDetailList"></tbody>
						</table>
					</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="takeStockDetailPagination"></div>
				</div>
			</div>
		</div>
	</div>
	
<script type="text/javascript" src="${ctx}/static/js/thjw/material/takestock/takeStock_view.js"></script>	
</body>
</html>