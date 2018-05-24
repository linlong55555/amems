<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div  class="tab-pane fade in active" style="padding-left:15px;padding-right:15px;" id="fieldmaterial" >
		<div class=" col-xs-12 padding-left-0 padding-right-0 ">
				
				<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0" >
		
					<div class=" pull-left padding-left-0 padding-right-0 row-height  " style="width:250px;" >
						<input placeholder="件号/中英文/厂家件号" id="keyword_search2" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision2();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                         <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search2();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon2"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!------------搜索框end------->
				
					<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10  margin-top-10 display-none border-cccccc search-height" id="divSearch2">
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
									<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
										<div
											class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-12">航材类型</div>
											<div class="font-size-9">Airmaterial type</div>
										</div>
										<div
											class="form-group col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<select id="hclx2" class="form-control " name="hclx2">
												<option value="">显示全部All</option>
												<c:forEach items="${materialTypeEnum}" var="item">
								  					<option value="${item.id}" >${item.name}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
										<div
											class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-12">状态</div>
											<div class="font-size-9">State</div>
										</div>
										<div
											class="form-group col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<select id="zt2" class="form-control " name="zt2">
												<option value="">显示全部All</option>
												<option value="2">正常</option>
												<option value="3">冻结</option>
												<option value="4">待报废</option>
											</select>
										</div>
									</div>
									<div
										class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10  ">
										<span
											class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">组织机构</div>
											<div class="font-size-9">Organization</div>
										</span>
										<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<select id="dprtcode2" class="form-control " name="dprtcode2">
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
									<div class="col-lg-2 pull-right text-right padding-right-0"
										style="margin-bottom: 10px;">
										<button type="button"
											class="btn btn-primary padding-top-1 padding-bottom-1"
											onclick="searchreset2();">
											<div class="font-size-12">重置</div>
											<div class="font-size-9">Reset</div>
										</button>
									</div>

								</div>

							</div>
					</div>
					<div  class=" col-xs-12 text-center padding-left-0 padding-right-0 table-h" style="overflow-x:scroll "> 
				
						<table id="wchc" class="table table-thin table-bordered text-center table-set" style="min-width: 1500px !important">
							<thead>
								<tr>
								<th class='fixed-column colwidth-5' style="vertical-align: middle;"><div
											class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div></th>
									<th class='fixed-column sorting colwidth-15' 
										name="column_bjh" onclick="orderBy2('bjh',this)" ><div class="important"><div
											class="font-size-12 line-height-18">件号</div></div>
										<div class="font-size-9 line-height-18">P/N</div></th>
									<th class=' sorting colwidth-30' 
									name="column_ywms"	onclick="orderBy2('ywms',this)"><div class="important"><div
											class="font-size-12 line-height-18">英文名称</div></div>
										<div class="font-size-9 line-height-18">F.Name</div></th>
									<th class=' sorting colwidth-25' 
									name="column_zwms"	onclick="orderBy2('zwms',this)"><div class="important"><div
											class="font-size-12 line-height-18">中文名称</div></div>
										<div class="font-size-9 line-height-18">CH.Name</div></th>
									<th  class="sorting colwidth-15"
									name="column_cjjh"	onclick="orderBy2('cjjh',this)"><div class="important"><div
											class="font-size-12 line-height-18">厂家件号</div></div>
										<div class="font-size-9 line-height-18">MP/N</div></th>
									<th  class="sorting colwidth-9"
									name="column_hclx"	onclick="orderBy2('hclx',this)"><div
											class="font-size-12 line-height-18">航材类型</div>
										<div class="font-size-9 line-height-18">Airmaterial type</div></th>
									<th  class="sorting colwidth-10"
									name="column_gljb"	onclick="orderBy2('gljb',this)"><div
											class="font-size-12 line-height-18">航材管理级别</div>
										<div class="font-size-9 line-height-18">Level</div></th>
									<th  class="sorting colwidth-15" name="column_sn"
										onclick="orderBy2('sn',this)"><div
											class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">S/N</div></th>
									<th  class="sorting colwidth-15" name="column_pch"
										onclick="orderBy2('pch',this)"><div
											class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">B/N</div></th>
									<th   class="sorting colwidth-15"
									name="column_shzh"	onclick="orderBy2('shzh',this)"><div
											class="font-size-12 line-height-18">适航证号</div>
										<div class="font-size-9 line-height-18">Certificate Of No.</div></th>
									<th  class="sorting colwidth-5"
									name="column_kcsl"	onclick="orderBy2('kcsl',this)"><div
											class="font-size-12 line-height-18">数量</div>
										<div class="font-size-9 line-height-18">Num</div></th>
									<th  class="sorting colwidth-5"
									name="column_jldw"	onclick="orderBy2('jldw',this)"><div
											class="font-size-12 line-height-18">单位</div>
										<div class="font-size-9 line-height-18">Unit</div></th>
									<!-- <th   class="sorting colwidth-6"
									name="column_ckmc"	onclick="orderBy2('ckmc',this)"><div
											class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Store</div></th>
									<th   class="sorting colwidth-6"
									name="column_kwh"	onclick="orderBy2('kwh',this)"><div
											class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage Location</div></th> -->
									<th  class="sorting colwidth-9"
									name="column_hjsm"	onclick="orderBy2('hjsm',this)"><div
											class="font-size-12 line-height-18">货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-Life</div></th>
									<th   class="sorting colwidth-9"
									name="column_syts"	onclick="orderBy2('syts',this)"><div class="font-size-12 line-height-18">剩余天数</div>
										<div class="font-size-9 line-height-18">Surplus(day)</div></th>
									<th  class="sorting colwidth-5"
									name="column_zt"	onclick="orderBy2('zt',this)"><div
											class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">State</div></th>
									<th  
									name="column_dprtcode"	class="sorting colwidth-20" onclick="orderBy2('dprtcode',this)"><div
											class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div></th>
								</tr>
							</thead>
							<tbody id="list2">
							</tbody>
						</table>
						
						</div>
						<div class=" col-xs-12  text-center page-height padding-left-0 padding-right-0"  id="pagination2">
									
							</div>
					</div>
<script type="text/javascript"
		src="${ctx}/static/js/thjw/material/stock/fieldmaterial_main.js"></script>
