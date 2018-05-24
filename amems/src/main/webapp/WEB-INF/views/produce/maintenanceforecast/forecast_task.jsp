<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<!-- 隐藏部门的div -->
<!-- <div class='displayContent' id='bottom_hidden_content'   > -->
	<div class="panel panel-primary margin-bottom-0"  id='forecast_list_panel'>
			<!--标题 STATR -->
		<!-- <div class="panel-heading bg-panel-heading" style='' >
			 <div class="font-size-12 line-height-12">下达指令</div>
			 <div class="font-size-9 line-height-9">An Order</div>
		</div> -->
		<!--标题EDG  -->
		<div class="panel-body " >
			<div  class='searchContent' >
					<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
					</div>
					<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
					</div>
					<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">任务类型</div>
							<div class="font-size-9">Task type</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" id="acregHtml">
							<select id="lylx" class="form-control " onchange="maintenance_forecast.search(this);">
							<option value="">显示全部All</option>
							<option value="1">维修项目</option>
							<option value="2">EO</option>
							<option value="3">生产指令</option>
							</select>
						</div>
					</div>
		
						<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
						<input type="text" placeholder='任务号/参考号/任务描述/件号/型号/序列号' title='任务号/参考号/任务描述/件号/型号/序列号'  class="form-control" id="keyword_search" >
	                    <div class="input-group-addon btn-search-nomore" >
	                    	<button type="button" id="loadlistBtn" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="maintenance_forecast.search(this);" style='margin-right:0px !important;'>
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
	                  		</button>
	                    </div>
						</div>
					</div>
					<div class="clearfix"></div>
				
				</div>
				 
				<div class='col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0' style="overflow-x: auto;">
				<table id="production_forecast_list_table" class="table table-thin table-bordered table-vcenter table-striped table-hover table-set ">
					<thead>
								<tr>
									<th class="fixed-column colwidth-7" >
										<div class="font-size-12 line-height-18" ></div>
										<div class="font-size-9 line-height-18"></div>
									</th>
									<th class="colwidth-10  "  >
										<div class=" ">
											<div class="font-size-12 line-height-18">计划执行日期</div>
											<div class="font-size-9 line-height-18">Planned Exec Date</div>
										</div>
									</th>
									<th  class="colwidth-7" >
										<div class="font-size-12 line-height-18">ATA</div>
										<div class="font-size-9 line-height-18">ATA</div>
									</th>
									<th class="colwidth-7  "  >
										<div class="font-size-12 line-height-18">任务类型</div>
										<div class="font-size-9 line-height-18">Task type</div>
									</th>
									
									<th class="colwidth-10  "  >
										<div class="important">
											<div class="font-size-12 line-height-18">任务号</div>
											<div class="font-size-9 line-height-18">Task No.</div>
										</div>
									</th>
									<th class="colwidth-10  "  >
										 <div class="important">
											<div class="font-size-12 line-height-18">参考号</div>
											<div class="font-size-9 line-height-18">Ref No.</div>
										 </div>
									</th>
									<!-- <th class="colwidth-5  "  >
										<div class="font-size-12 line-height-18">版本</div>
										<div class="font-size-9 line-height-18">Rev.</div>
									</th>
									 -->
									<th class="colwidth-15  "  >
										<div class="important">
											<div class="font-size-12 line-height-18">任务描述</div>
											<div class="font-size-9 line-height-18">Description</div>
										</div>
									</th>
									<th class="colwidth-15 "  >
											<div class="font-size-12 line-height-18">飞机注册号/MSN</div>
											<div class="font-size-9 line-height-18">A/C Reg/MSN</div>
									</th>
									<th class="colwidth-10" >
										<div class="important">
											<div class="font-size-12 line-height-18">件号</div>
											<div class="font-size-9 line-height-18">P/N</div>
										</div>
									</th>
									
									<th class="colwidth-10  "  >
										<div class="important">
											<div class="font-size-12 line-height-18">型号</div>
											<div class="font-size-9 line-height-18">Model</div>
										 </div>
									</th>
									<th class="colwidth-10  "  >
										<div class="important">
											<div class="font-size-12 line-height-18">序列号</div>
											<div class="font-size-9 line-height-18">S/N</div>
										</div>
									</th>
									<th class="colwidth-7"  >
										<div class="font-size-12 line-height-18">工作类别</div>
										<div class="font-size-9 line-height-18">Work Type</div>
									</th>
									 
									<th class="colwidth-10  "  >
										<div class="font-size-12 line-height-18">项目类别</div>
										<div class="font-size-9 line-height-18">Project Type</div>
									</th>
									<th class="colwidth-12"  >
										<div class="font-size-12 line-height-18">计划</div>
										<div class="font-size-9 line-height-18">Planned</div>
									</th>
									<th class="colwidth-12">
										<div class="font-size-12 line-height-18">剩余</div>
										<div class="font-size-9 line-height-18">Surplus</div>
									</th>
									<th class="colwidth-10  "  >
										<div class="font-size-12 line-height-18">工卡编号</div>
										<div class="font-size-9 line-height-18">Work Card No.</div>
									</th>
									<th class="colwidth-10  "  >
										<div class="font-size-12 line-height-18">工卡附件</div>
										<div class="font-size-9 line-height-18">Attachment</div>
									</th>
									
								</tr>
							</thead>
							<tbody id="production_forecast_list"></tbody>
				</table>
				<div class='clearfix'></div>
			</div>
		</div>
  	  </div>
	<div class='clearfix'></div>
<!-- </div> -->

<%-- <script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessment/assessment_give_order.js"></script>
<%@ include file="/WEB-INF/views/project2/assessment/todo_feedback.jsp"%><!-- 反馈对话框 --> --%>