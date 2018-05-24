<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			
			<div class="panel-body padding-bottom-0">
			   <div  class='searchContent row-height' >
				<div class="col-xs-2 col-md-1 padding-left-0 form-group">
					<a href="" data-toggle="modal" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" onclick="outRequisitioncostExcel()">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a> 
				</div>
				
				<div class="col-xs-9 col-md-9 padding-left-0 form-group">
				
					<div class="col-xs-4 col-sm-4 padding-left-0 padding-right-0 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">领用日期</div>
							<div class="font-size-9">Receive Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " name="date-range-picker" id="lyrq_search" readonly />
						</div>
					</div>
				
					<div class="col-xs-3 col-sm-3 padding-left-0 padding-right-0 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="fjzch_search" class="form-control" onchange="onchangeSelect()">
							</select>
						</div>
					</div>
					
					<div class="col-xs-3 col-sm-3 padding-left-0 padding-right-0 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">航材类型</div>
							<div class="font-size-9">Airmaterial type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="hclx_search" class="form-control" onchange="onchangeSelect()">
								<option value="">航材</option>
								<option value="2">工具/设备</option>
							</select>
						</div>
					</div>
					
				</div>
				
				<!-- 搜索框start -->
				<div class="pull-right padding-left-0 padding-right-0 form-group">
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
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">航材二级类型</div>
							<div class="font-size-9">Aircraft Second Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='hclxej_search' >
								<option value="" >显示全部All</option>
								<c:forEach items="${materialSecondTypeEnum}" var="item">
									<option value="${item.id}">${item.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control " name="dprtcode_search" onchange="changeOrganization()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
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
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" style="overflow: auto; height: auto;">
					<table id="requisitioncost_main_table" class="table table-thin table-bordered table-striped table-hover text-left table-set" style="min-width: 4000px;">
						<thead>
							<tr>
								<th class="colwidth-10 sorting" onclick="orderBy('HCLX')" id="HCLX_order">
									<div class="font-size-12 line-height-18">航材类型</div>
									<div class="font-size-9 line-height-18">Airmaterial type</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('HCLX_EJ')" id="HCLX_EJ_order">
									<div class="font-size-12 line-height-18">航材二级类型</div>
									<div class="font-size-9 line-height-18">Aircraft Second Type</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('BJH')" id="BJH_order">
									<div class="font-size-12 line-height-18 " >件号</div>
									<div class="font-size-9 line-height-18">P/N</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('GLJB')" id="GLJB_order">
									<div class="font-size-12 line-height-18 " >管理级别</div>
									<div class="font-size-9 line-height-18">Management Level</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('SN')" id="SN_order">
									<div class="font-size-12 line-height-18 " >序列号</div>
									<div class="font-size-9 line-height-18">S/N</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('PCH')" id="PCH_order">
									<div class="font-size-12 line-height-18 " >批次号</div>
									<div class="font-size-9 line-height-18">B/N</div>
								</th>
								<th class="colwidth-25 sorting" onclick="orderBy('YWMS')" id="YWMS_order">
									<div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-18">F.Name</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('ZWMS')" id="ZWMS_order">
									<div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-18">CH.Name</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('SYSL')" id="SYSL_order">
									<div class="font-size-12 line-height-18 " >使用数量</div>
									<div class="font-size-9 line-height-18">Use Num</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('JLDW')" id="JLDW_order">
									<div class="font-size-12 line-height-18 " >单位</div>
									<div class="font-size-9 line-height-18">Unit</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('KCCB')" id="KCCB_order">
									<div class="font-size-12 line-height-18 " >单价(人民币：元)</div>
									<div class="font-size-9 line-height-18">Price(RMB:YUAN)</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('LYCB')" id="LYCB_order">
									<div class="font-size-12 line-height-18 " >成本(人民币：元)</div>
									<div class="font-size-9 line-height-18">Cost(RMB:YUAN)</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('CKRQ')" id="CKRQ_order">
									<div class="font-size-12 line-height-18">领用日期</div>
									<div class="font-size-9 line-height-18">Receive date</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('CKDH')" id="CKDH_order">
									<div class="font-size-12 line-height-18">领用单号</div>
									<div class="font-size-9 line-height-18">Receive No.</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('KCSL')" id="KCSL_order">
									<div class="font-size-12 line-height-18">领用数量</div>
									<div class="font-size-9 line-height-18">Receive Num</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('TKSL')" id="TKSL_order">
									<div class="font-size-12 line-height-18">退库数量</div>
									<div class="font-size-9 line-height-18">Return Num</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('BZ')" id="BZ_order">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remarks</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('CJJH')" id="CJJH_order">
									<div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-18">MP/N</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('ZJH')" id="ZJH_order">
									<div class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('HCJZ')" id="HCJZ_order">
									<div class="font-size-12 line-height-18">航材价值</div>
									<div class="font-size-9 line-height-18">Aircraft Value</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('GG')" id="GG_order">
									<div class="font-size-12 line-height-18">规格</div>
									<div class="font-size-9 line-height-18">Spec</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('XINGH')" id="XINGH_order">
									<div class="font-size-12 line-height-18">型号</div>
									<div class="font-size-9 line-height-18">Model</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('CKH')" id="CKH_order">
									<div class="font-size-12 line-height-18">仓库号</div>
									<div class="font-size-9 line-height-18">Store No.</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('CKMC')" id="CKMC_order">
									<div class="font-size-12 line-height-18">仓库名称</div>
									<div class="font-size-9 line-height-18">Store</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('KWH')" id="KWH_order">
									<div class="font-size-12 line-height-18">库位号</div>
									<div class="font-size-9 line-height-18">Storage location No</div>
								</th>
<!-- 								<th class="colwidth-10 sorting" onclick="orderBy('JD')" id="JD_order"> -->
<!-- 									<div class="font-size-12 line-height-18">基地</div> -->
<!-- 									<div class="font-size-9 line-height-18">Base</div> -->
<!-- 								</th> -->
								<th class="colwidth-10 sorting" onclick="orderBy('XKZH')" id="XKZH_order">
									<div class="font-size-12 line-height-18">许可证号</div>
									<div class="font-size-9 line-height-18">Licence</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('SHZH')" id="SHZH_order">
									<div class="font-size-12 line-height-18">适航证号</div>
									<div class="font-size-9 line-height-18">Certificate</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('SHZWZ')" id="SHZWZ_order">
									<div class="font-size-12 line-height-18">适航证位置</div>
									<div class="font-size-9 line-height-18">Certificate position</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('DPRTCODE')" id="DPRTCODE_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/material/statistical/requisitioncost_main.js"></script>
</body>
</html>