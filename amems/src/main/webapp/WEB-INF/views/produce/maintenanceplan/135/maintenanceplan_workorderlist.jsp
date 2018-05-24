<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<style type="text/css">
	.spacing {
		margin-left:3px;
		margin-right:3px;
	}
</style>
<div class="panel panel-primary" id="gd_panel_div">
				<div class="panel-heading">
					<div class="font-size-12 line-height-12">工单列表</div>
                    <div class="font-size-9 line-height-9">Work Order list</div>
				</div>
				<div class="panel-body padding-bottom-0" id='gd_panel_div_body'>
				<!-- 搜索 -->
				<div class='row-height margin-top-0 searchContent' >
			   
				 <!-- 关闭状态 -->
				<!--  <div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
					<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
						<div class="font-size-12">关闭状态</div>
						<div class="font-size-9">Status </div>
					</span>
					<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
						<select id="gbzt_search" class="form-control " >
							<option value="" selected="true">显示全部All</option>
							<option value="1,2,7" >未关闭</option>
							<option value="9,10" >已关闭</option>
						</select>
					</div>
				</div> -->
				<!-- 工单状态 -->
				<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10" >
					<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">工单状态</div>
						<div class="font-size-9">Status</div>
					</span>
					<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0" id="gdztDiv">
						<select multiple='multiple' id='gdzt_search'>
							<c:forEach items="${workorderStatusEnum}" var="item">
								<option value="${item.id}">${item.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				 <!-- 关闭状态 -->
				 <div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
					<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
						<div class="font-size-12">反馈状态</div>
						<div class="font-size-9">Status </div>
					</span>
					<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
						<select class='form-control' id="fkzt_search">
							<option value="" selected="true">显示全部All</option>
							<c:forEach items="${feedbackStatusEnum}" var="item">
								<option value="${item.id}">${item.name}</option>
							</c:forEach>
					    </select>
					</div>
				</div>
				<!-- 关键字搜索 -->
				<div class=" pull-right padding-left-0 padding-right-0 form-group">
					
					<div class="pull-right padding-left-10 padding-right-0">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input id="keyword_gd_search" type="text" placeholder='任务信息/工单标题/工单编号/工卡编号'  class="form-control" >
						</div>
	                    <div class=" pull-right padding-left-5 padding-right-0 ">   
							<button name="keyCodeSearch" onclick="maintencePlanList.searchGd()" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   		</button>
	                   		<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  onclick='maintencePlanList.search()' >
								<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
								<div class="pull-left padding-top-5">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
								</div>
					   		</button> 
	                    </div> 
					</div>
				</div>
				<div class='clearfix'></div>
				<!-- 更多的搜索框 -->
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-bottom-10  display-none border-cccccc" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">来源分类</div>
							<div class="font-size-9">Classification</div>
						</span>
						<div id="lyfl_div" class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select multiple='multiple' id='lyfl_search'>
								<c:forEach items="${workorderTypeEnum}" var="item">
									<c:if test="${item.id != 9}">
										<option value="${item.id}">${item.name}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
					
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 " style='height:34px;'>
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">ATA章节号</div>
							<div class="font-size-9">ATA</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left input-group">
							<input id="zjh_search" class="form-control readonly-style" maxlength="20" ondblclick="maintencePlanList.openChapterWin()" type="text" readonly>
							<span class="input-group-btn">
								<button id="zjh_search_btn" class="btn btn-default" type="button" data-toggle="modal" onclick="maintencePlanList.openChapterWin()">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">工作类别</div>
							<div class="font-size-9">Category</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id='gzlx_search' class='form-control'>
						    </select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">维修类型</div>
							<div class="font-size-9">Types</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id='wxlx_search' class='form-control'>
						    </select>
						</div>
					</div>
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="maintencePlanList.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
				<div>
				 <!-- 上传按钮  -->
			    <div class="pull-left form-group" style='margin-right:3px;'>
					<a href="javascript:maintencePlanList.openMaterialToolDetail();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" >
						<div class="font-size-12">航材工具需求清单</div>
						<div class="font-size-9">Material & Tools</div>
					</a> 
				</div>
				<!-- <div class="pull-left padding-left-0" style='margin-right:3px;'>
					<a   class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" >
						<div class="font-size-12">指定结束</div>
						<div class="font-size-9">End</div>
					</a> 
				</div> -->
				<div class="pull-left form-group" style='margin-right:3px;'>
					<a href="#"  onclick="maintencePlanList.exportExcel()" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="produce:maintenanceplan:main:01">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a> 
				</div>
				
				<small id="gb_sm" class='pull-left color-red form-group' style='height:34px;line-height:34px;vertical-align:middle;margin-bottom:0px;font-size:12px;'>4017工包：已反馈10,已关闭：10</small>
				<div class='clearfix'></div>
				</div>
				</div>
				<div  class='table_pagination'>
				<div  class='col-lg-12 col-md-12 padding-left-0 padding-right-0 table-height' c-height="45%" style="overflow-x: auto;" id="maintenanceplane_workorder_div">
				<table class='table table-thin table-nonbordered table-striped table-hover table-set' id='maintenanceplan_table' style='margin-bottom:0px !important;min-width:2000px;'>
					<thead>
						<tr>
							<th class=" colwidth-7 selectAllImg">
								<a href="#" onclick="SelectUtil.performSelectAll('maintenanceplane_workorder_div')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
								<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('maintenanceplane_workorder_div')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
							</th>
							<th class='colwidth-10'>
								<div class="font-size-12 line-height-12" >操作</div>
								<div class="font-size-9 line-height-12">Operation</div>
							</th>
							<th class='colwidth-13'>
								<div class="important">
									<div class="font-size-12 line-height-12">任务信息</div>
			                        <div class="font-size-9 line-height-12">Task Info</div>
		                        </div>
							</th>
							<th class='colwidth-13'>
								<div class="important">
									<div class="font-size-12 line-height-12">工单标题</div>
			                        <div class="font-size-9 line-height-12">Title</div>
		                        </div>
							</th>
							<th class='colwidth-5'>
								<div class="font-size-12 line-height-12">关闭状态</div>
		                        <div class="font-size-9 line-height-12">Status</div>
							</th>
							<th class='colwidth-5'>
								<div class="font-size-12 line-height-12">反馈状态</div>
		                        <div class="font-size-9 line-height-12">Status</div>
							</th>
							<th class='colwidth-9'>
								<div class="font-size-12 line-height-12">工卡附件</div>
		                        <div class="font-size-9 line-height-12">Attachment </div>
							</th>
							<th class='colwidth-7'>
								<div class="font-size-12 line-height-12">来源分类</div>
		                        <div class="font-size-9 line-height-12">Classification</div>
							</th>
							<th class='colwidth-15'>
								<div class="important">
									<div class="font-size-12 line-height-12">工单编号</div>
			                        <div class="font-size-9 line-height-12">Work Order</div>
		                        </div>
							</th>
							<th class='colwidth-10'>
								<div class="font-size-12 line-height-12">ATA章节号</div>
		                        <div class="font-size-9 line-height-12">ATA</div>
							</th>
							<th class='colwidth-20'>
								<div class="font-size-12 line-height-12">执行对象</div>
		                        <div class="font-size-9 line-height-12">Perform object</div>
							</th>
							<th class='colwidth-25'>
								<div class="font-size-12 line-height-12">完成时限</div>
		                        <div class="font-size-9 line-height-12">Complete</div>
							</th>
							<th class='colwidth-7'>
								<div class="font-size-12 line-height-12">专业</div>
		                        <div class="font-size-9 line-height-12">Skill</div>
							</th>
							<th class='colwidth-9'>
								<div class="font-size-12 line-height-12">工作类别</div>
		                        <div class="font-size-9 line-height-12">Category</div>
							</th>
							<th class='colwidth-9'>
								<div class="font-size-12 line-height-12">区域</div>
		                        <div class="font-size-9 line-height-12">Zone</div>
							</th>
							<th class='colwidth-13'>
								<div class="font-size-12 line-height-12">工作地点</div>
		                        <div class="font-size-9 line-height-12">Working place</div>
							</th>
							<th class='colwidth-9'>
								<div class="important">
									<div class="font-size-12 line-height-12">工卡编号</div>
			                        <div class="font-size-9 line-height-12">WC No. </div>
		                        </div>
							</th>
							<th class='colwidth-9'>
								<div class="font-size-12 line-height-12">计划/实际工时</div>
		                        <div class="font-size-9 line-height-12">Plan/actual</div>
							</th>
							<th class='colwidth-13'>
								<div class="font-size-12 line-height-12">预计执行单位</div>
		                        <div class="font-size-9 line-height-12">Expected Unit</div>
							</th>
							<th class='colwidth-7'>
								<div class="font-size-12 line-height-12">工单附件</div>
		                        <div class="font-size-9 line-height-12">Attachment</div>
							</th>
							<th class='colwidth-13'>
								<div class="font-size-12 line-height-12">维护人</div>
		                        <div class="font-size-9 line-height-12">Maintainer</div>
							</th>
							<th class='colwidth-13'>
								<div class="font-size-12 line-height-12">维护时间</div>
		                        <div class="font-size-9 line-height-12">Maintenance Time</div>
							</th>
							<th class='colwidth-15'>
								<div class="font-size-12 line-height-12">组织机构</div>
		                        <div class="font-size-9 line-height-12">Organization</div>
							</th>
						</tr>
					</thead>
					<tbody id='maintenanceplan_list'>
					</tbody>
				</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination_gd" ></div>
				<div class='clearfix'></div>
				</div>
				
				<div id='bottom_hidden_content' class='displayContent col-xs-12  clearfix padding-top-10 padding-left-0 padding-right-0 padding-top-0' style='display:none;'>
				 <div id="hideIcon" class="pull-right" style='height:1px;padding-right:8px;margin-top:1px;'>
					<img src="${ctx}/static/images/down.png" onclick='maintencePlanList.hideBottom()' style="width:33px;cursor:pointer;">
				 </div>
				<ul id="myChildTab" class="nav nav-tabs tabNew-style" style="padding-top:0px !important;">
			
				<li class="active" >
				<a id='tool' href='#toolTab' data-toggle="tab"  >
					<div class="font-size-12 line-height-12">航材工具</div>
                    <div class="font-size-9 line-height-9">Material&Tool</div>
                 </a>
				</li>
				<li class=""  >
				<a href='#feedbackTool' data-toggle="tab"  >
					<div class="font-size-12 line-height-12">完工反馈</div>
                    <div class="font-size-9 line-height-9">Feedback</div>
                  </a>
				</li>
				<li class="" >
				<a href='#replacementTab' data-toggle="tab"  >
					<div class="font-size-12 line-height-12">拆换件记录</div>
                    <div class="font-size-9 line-height-9">Replacement</div>
                </a>
				</li>
				<li class="" >
				<a href='#historyTab' data-toggle="tab" >
					<div class="font-size-12 line-height-12">执行历史</div>
                    <div class="font-size-9 line-height-9">Execution history</div>
				</a>
				</li>
				
				</ul>
				<div id="plan_feedback_replace_tab" class="tab-content" style='padding:0px;'>
					<div id="toolTab" class="tab-pane fade active in" >
						<%@ include file="/WEB-INF/views/common/produce/material_tool_list.jsp" %> <!-- 航材工具 -->
					</div>
					<div id="feedbackTool" class="tab-pane fade">
						<%@ include file="/WEB-INF/views/produce/taskhistory/taskhistory_feedback.jsp" %> <!-- 完工反馈 -->
					</div>
					<div id="replacementTab" class="tab-pane fade">
						<%@ include file="/WEB-INF/views/produce/taskhistory/taskhistory_record.jsp" %> <!-- 拆换件记录 -->
					</div>
					<div id="historyTab" class="tab-pane fade">
						<%@ include file="/WEB-INF/views/common/produce/executionHistory.jsp" %>  <!-- 执行历史 -->
					</div>
				</div>
				</div>
				
				</div>
		    </div>