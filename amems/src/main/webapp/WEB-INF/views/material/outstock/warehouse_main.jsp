	<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	<script type="text/javascript">
	var id1 = "${param.id1}";
	var pageParam1 = '${param.pageParam1}';
</script>
	<!-----标签内容start---->
	<input type="hidden" id="changerepertoryid" value="${changerepertoryid}"/>
	<input type="hidden" id="parameter" value="${parameter}"/>
		<input type="hidden" id="adjustHeight" value="90">
					<div class="tab-pane fade in active" style="padding-left:15px;padding-right:15px;padding-bottom:0px;margin-bottom:0px"  id="inventory">
						<div class="col-xs-2 col-md-1 padding-left-0 row-height   ">
								<a href="#" onclick="goManual();" class="btn btn-xs btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission"  permissioncode='aerialmaterial:outstock:main:03'>
									<div class="font-size-12">手工领用出库</div>
									<div class="font-size-9">Manual Outstock</div>
								</a>
								<span id="requisiton_message" class="pull-left text-center" style="padding-left:10px; line-height:35px;"></span> 
						</div>
							
						<div class="col-xs-2 col-md-1 padding-left-5">
							<a href="#" onclick="checkout();" class="btn btn-xs btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission"  permissioncode='aerialmaterial:outstock:main:04'>
								<div class="font-size-12">借出出库</div>
								<div class="font-size-9">Lending Outstock</div>
							</a>
							<span id="requisiton_message" class="pull-left text-center" style="padding-left:10px; line-height:35px;"></span> 
						</div>
						
						<div class="col-xs-2 col-md-1 padding-left-10">
							<a href="#" onclick="givebackout();" class="btn btn-xs btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='aerialmaterial:outstock:main:05'>
								<div class="font-size-12">归还出库</div>
								<div class="font-size-9">Return Outstock</div>
							</a>
							<span id="requisiton_message" class="pull-left text-center" style="padding-left:10px; line-height:35px;"></span> 
						</div>
						
						<div class="col-xs-2 col-md-1 padding-left-10">
							<a href="#" onclick="dumping();" class="btn btn-xs btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='aerialmaterial:outstock:main:06'>
								<div class="font-size-12">报废出库</div>
								<div class="font-size-9">Scrap Outstock</div>
							</a>
							<span id="requisiton_message" class="pull-left text-center" style="padding-left:10px; line-height:35px;"></span> 
						</div>
						
						<div class="col-xs-2 col-md-1 padding-left-10">
							<a href="#" onclick="otherbackout();" class="btn btn-xs btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='aerialmaterial:outstock:main:07'>
								<div class="font-size-12">其它出库</div>
								<div class="font-size-9">Other Outstock</div>
							</a>
							<span id="requisiton_message" class="pull-left text-center" style="padding-left:10px; line-height:35px;"></span> 
						</div>
						
				<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0" >
					<div class="pull-left padding-left-0 padding-right-0" style="width:200px;">
						<div class="pull-left col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">仓库</div>
							<div class="font-size-9">Store</div></div>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0" >
							<select class='form-control' id='ckh2' name="ckh2" onclick="searchckh();">
								
							</select>
						</div>
					</div>
					
					<div class=" pull-left padding-left-0 padding-right-0 row-height " style="width:200px;" >
						<input placeholder="件号/中英文/厂家件号/序列号/GRN" id="inventorykeyword_search" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button id="OutStock" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchinventory();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                         <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search2();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon3"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!------------搜索框end------->
						
					<div class="col-xs-12 padding-left-0 padding-right-0 margin-bottom-0">
				<div class="col-xs-12 triangle  padding-top-10 margin-bottom-10 padding-bottom-10 margin-top-10 display-none border-cccccc search-height" id="divSearch2">
					<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
					
						<div class=" col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
						<div
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12">航材类型</div>
							<div class="font-size-9">Airmaterial type</div></div>
						<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="hclx2" class="form-control "  name="hclx2">
								<option value="">显示全部All</option>
								<c:forEach items="${materialTypeEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
								</select>
						</div>
					</div>
					
			
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10  ">
						<div class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></div>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode2" class="form-control " name="dprtcode2" onchange="changeSelectedPlane()">
								<c:choose>
											<c:when test="${accessDepartment!=null && fn:length(accessDepartment) > 0}">
											<c:forEach items="${accessDepartment}" var="type">
												<option value="${type.id}"
													<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}
												</option>
											</c:forEach>
											</c:when>
											<c:otherwise>
												<option value="">暂无组织机构 No Organization</option>
											</c:otherwise>
										</c:choose>
							</select>
						</div>
					</div>
					<div class="col-lg-2 pull-right text-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchresetinventory();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
					</div>
				</div>
				</div>
			
				<div  class="col-xs-12 text-center margin-bottom-5 padding-left-0 padding-right-0 padding-top-0 table-h"  style="overflow-x:scroll">
						<table id="kcck" class="table table-thin table-bordered table-set" style="min-width:1600px">
							<thead>
								<tr>
									<th  class="fixed-column colwidth-5"style="vertical-align: middle;"><div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div></th>
									<th  class="fixed-column colwidth-5" ><div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div></th>
									<th  class="fixed-column sorting colwidth-15"  name="column_bjh"  onclick="orderByinventory('bjh',this)" ><div class="important"><div class="font-size-12 line-height-18">件号</div></div>
									<div class="font-size-9 line-height-18">P/N</div></th>
									<th  class=" sorting colwidth-20"  name="column_ywms" onclick="orderByinventory('ywms',this)"><div class="important"><div class="font-size-12 line-height-18">英文名称</div></div>
									<div class="font-size-9 line-height-18">F.Name</div></th>
									<th  class=" sorting colwidth-20"  name="column_zwms" onclick="orderByinventory('zwms',this)" ><div class="important"><div class="font-size-12 line-height-18">中文名称</div></div>
									<div class="font-size-9 line-height-18">CH.Name</div></th>
								
									<th  class="sorting colwidth-15" name="column_cjjh" onclick="orderByinventory('cjjh',this)"><div class="important"><div class="font-size-12 line-height-18">厂家件号</div></div>
									<div class="font-size-9 line-height-18">MP/N</div></th>
									<th  class="sorting colwidth-10" name="column_hclx" onclick="orderByinventory('hclx',this)" ><div class="font-size-12 line-height-18">航材类型</div>
									<div class="font-size-9 line-height-18">Airmaterial type</div></th>
									<th  class="sorting colwidth-10" name="column_gljb" onclick="orderByinventory('gljb',this)" ><div class="font-size-12 line-height-18">航材管理级别</div>
									<div class="font-size-9 line-height-18">Management level</div></th>
									<th  class="colwidth-15"><div class="font-size-12 line-height-18"><div class="important">序列号</div></div>
									<div class="font-size-9 line-height-18">S/N</div></th>
									<th  class="colwidth-15"><div class="font-size-12 line-height-18">批次号</div>
									<div class="font-size-9 line-height-18">P/N</div></th>
									<th  class="colwidth-15"><div class="font-size-12 line-height-18"><div class="important">GRN</div></div>
									<div class="font-size-9 line-height-18">GRN</div></th>
									<th  class="sorting colwidth-15" name="column_shzh" onclick="orderByinventory('shzh',this)" ><div class="font-size-12 line-height-18">适航证号</div>
									<div class="font-size-9 line-height-18">Certificate</div></th>
									<th  class="sorting colwidth-7" name="column_kcsl" onclick="orderByinventory('kcsl',this)" ><div class="font-size-12 line-height-18">数量</div>
									<div class="font-size-9 line-height-18">Num</div></th>
									<th  class="sorting colwidth-7" name="column_jldw" onclick="orderByinventory('jldw',this)" ><div class="font-size-12 line-height-18">单位</div>
									<div class="font-size-9 line-height-18">Unit</div></th>
									<th  class="sorting colwidth-15" name="column_ckmc" onclick="orderByinventory('ckmc',this)" ><div class="font-size-12 line-height-18">仓库</div>
									<div class="font-size-9 line-height-18">Store</div></th>
									<th  class="sorting colwidth-15" name="column_kwh" onclick="orderByinventory('kwh',this)" ><div class="font-size-12 line-height-18">库位</div>
									<div class="font-size-9 line-height-18">Storage Location</div></th>
									<th  class="sorting colwidth-10" name="column_hjsm" onclick="orderByinventory('hjsm',this)" ><div class="font-size-12 line-height-18">货架寿命</div>
									<div class="font-size-9 line-height-18">Shelf-Life</div></th>
									<th  class="colwidth-15"><div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div></th>
								</tr>
							</thead>
									<tbody id="inventorylist">
									</tbody>
						</table>
						</div>
							<div class=" col-xs-12  text-center page-height" id="inventorypagination">
								
							</div>
						</div>
						
<script type="text/javascript" src="${ctx}/static/js/thjw/material/outstock/warehouse_main.js"></script>