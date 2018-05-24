<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="panel panel-primary">
		    <!-- panel-heading -->
			<div class="panel-heading" >
				<div class="font-size-12 ">年度计划</div>
				<div class="font-size-9">Annual plan</div>
			</div>
			<div class="panel-body" id="item_list_div_id">
			<!-- 搜索框 -->
			    <div  class='searchContent row-height'>
				<!-- 关键字搜索 -->
				
				<div class="col-lg-4 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>

					<!-- 搜索框start -->
					<div class="col-xs-12 input-group input-group-search">
					        
							<input type="text" id="keyword_search" class="form-control "  placeholder='审核对象/责任审核人'>
		                    <div class="input-group-addon btn-searchnew">
		                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
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
					<!-- 搜索框end -->
				
				</div>
				<div class='clearfix'></div>
				<!-- 更多的搜索框 -->
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-bottom-10 display-none border-cccccc" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">审核对象</div>
							<div class="font-size-9">Object</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<div class='input-group' style='width:100%'>
							<input type="hidden"  id="annual_plan_module_shdx_searchid">
							<input type="hidden"  id="annual_plan_module_shdx_search">
						    <input type="text" id="annual_plan_module_shdx_searchbh" class="form-control"   maxlength="20"  />
							<span class="input-group-btn">
								<button type="button"  class="btn btn-default" data-toggle="modal" onclick="annual_plan_approval_main.openzrdw('shdx_search',null)">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						    </div>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">责任审核人</div>
							<div class="font-size-9">Auditor</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<div class='input-group' style='width:100%'>
							<input type="hidden"   id="annual_plan_module_shzrr_searchid">
						    <input type="text" id="annual_plan_module_shzrr_search" class="form-control "  maxlength="20"  />
							<span class="input-group-btn">
								<button type="button" id="zjh_search_btn" class="btn btn-default" data-toggle="modal" onclick="annual_plan_approval_main.openUser('shzrr_search')">
									<i class="icon-search cursor-pointer"></i>
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
							<select id="dprtcode" class="form-control "  name="dprtcode" onchange="dprtChange(this)" >
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
				<div class="col-xs-12 padding-left-0 padding-right-0 margin-top-0 first-tab-style"  >
				    <ul id="myTab" class="nav nav-tabs tabNew-style ">
                    	<li class="active" id="pxjhExport">
	                    	<a href="#payStatistics" data-toggle="tab" aria-expanded="true">
		                    	<div class="font-size-12 line-height-12" style='width:90px;'>年度审核计划 </div>
							    <div class="font-size-9 line-height-9">Annual Plan</div>
							    <span id="ndjhnums"  class="badge" style="position: absolute; background:red ! important;right:3px;top:5px ">0</span>
	                    	</a>
	                    	
                    	</li>
                      	<li class="" id="ndstExport">
	                      	<a href="#paymentDetail" data-toggle="tab" aria-expanded="false">
		                      	<div class="font-size-12 line-height-12">年度视图 </div>
						        <div class="font-size-9 line-height-9">Year View</div>
	                      	</a>
                      	</li>
                    </ul>
                    <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade active in" id="payStatistics">
                     	<%@ include file="/WEB-INF/views/quality/annualplan/annual_plan_main.jsp"%>
                    </div>
                    <div class="tab-pane fade" id="paymentDetail" style="margin-top: -5px;">
                      	 <%@ include file="/WEB-INF/views/quality/annualplan/annual_view.jsp"%>
                    </div>
                  </div>
              </div>
			</div>
    </div>