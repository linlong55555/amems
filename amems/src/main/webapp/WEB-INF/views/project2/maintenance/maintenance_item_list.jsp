<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="panel panel-primary">
		    <!-- panel-heading -->
			<div class="panel-heading">
				<div class="font-size-12 line-height-12">维修项目清单
					<div style="position:absolute;right:10px;top:10px;">
						<span name="info_wxfabh"></span> <span name='info_rev'></span> <i id="creator_info_btn" class="icon-info-sign cursor-pointer"></i>
					</div>
				</div>
				<div class="font-size-9 line-height-9">Maintenance Item List</div>
			</div>
			<div class="panel-body" id="item_list_div_id">
			<!-- 搜索框 -->
			    <div  class='searchContent row-height'>
				 <div class="pull-left padding-left-0 padding-right-0 margin-bottom-10">
				    <!-- 上传按钮  -->
					<div class="pull-left padding-left-0" style='margin-right:8px;' id="add_project_btn">
						<a href="javascript:void(0);"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" 
							onclick="open_win_modal.openProjectWinAdd()" permissioncode='project2:maintenanceproject:main:06'>
							<div class="font-size-12">新增维修项目</div>
							<div class="font-size-9">Add Item</div>
						</a> 
					</div>
					<div class="pull-left" style='margin-right:8px;' id="add_package_btn">
						<a href="javascript:void(0);"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" 
							onclick="open_win_modal.openCheckWinAdd()" permissioncode='project2:maintenanceproject:main:06'>
							<div class="font-size-12">新增定检包</div>
							<div class="font-size-9">Add Package</div>
						</a> 
					</div>
					<div class="pull-left padding-left-0" style='margin-right:8px;' id="import_btn">
						<a href="javascript:void(0);"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" 
						    permissioncode='project2:maintenanceproject:main:11'
							onclick="maintenanceItemList.showImportModal();">
							<div class="font-size-12">导入</div>
							<div class="font-size-9">Import</div>
						</a> 
					</div>
					<!-- <div class="btn-group pull-left" role="group">
					    <button type="button" class="btn btn-primary  dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					      	新增
					      <span class="caret"></span>
					    </button>
					    <ul class="dropdown-menu">
					      <li>
					      	  <a href="#">
					      		维修项目
					      	  </a>
				      	  </li>
					      <li>
					      	<a href="#">
					      		定检包
					      	</a>
					      </li>
					    </ul>
					</div> -->
					<div class="pull-left" style='margin-right:8px;' id="export_btn">
						<a href="javascript:void(0);"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" 
							onclick="maintenanceItemList.exportExcel()" permissioncode='project2:maintenanceproject:main:10'>
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</a> 
					</div>
					
					<div class="pull-left padding-left-5 padding-right-0" >
						<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
							<input type='radio' name='group_type' value='1' style='vertical-align:text-bottom' onchange="maintenanceItemList.reload()"/>&nbsp;按ATA&nbsp;&nbsp;
						</label>
						<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
							<input type='radio' name='group_type' value='2' style='vertical-align:text-bottom;' onchange="maintenanceItemList.reload()" checked="checked"/>&nbsp;按大类&nbsp;&nbsp;
						</label>
					</div>
			    </div>
				
				<!-- 关键字搜索 -->
				<div class=" pull-right padding-left-0 padding-right-0 margin-bottom-10">
					<div class="pull-left text-right padding-left-0 padding-right-0">
						<div class="font-size-12">适用性</div>
						<div class="font-size-9">Applicability</div>
					</div>
					<div class="pull-left text-right padding-left-0 padding-right-0">
						<div class="padding-left-8 pull-left" style="width: 120px; margin-right:5px;">
						   <select id="fjzch" class="form-control" onchange="maintenanceItemList.reload()"></select> 
						</div>
					</div>
					<div class="pull-left text-right padding-left-0 padding-right-0">
						<div class="font-size-12">状态</div>
						<div class="font-size-9">Status</div>
					</div>
					<div class="pull-left text-right padding-left-0 padding-right-0">
						<div class="padding-left-8 pull-left" style="width: 80px; margin-right:5px;">
						   <select id="yxbs" class="form-control" onchange="maintenanceItemList.reload()">
						   		<option value="">全部</option>
						   		<option value="1">生效</option>
						   		<option value="0">失效</option>
						   </select> 
						</div>
					</div>
					<div class="pull-left padding-left-5 padding-right-0" >
						<label class='' style='margin-top:6px;font-weight:normal;' onchange="maintenanceItemList.reload()">
							<input type='checkbox' name='wxxmlx' value='4' style='vertical-align:text-bottom' checked="checked"/>&nbsp;定检&nbsp;&nbsp;
						</label>
						<label class='' style='margin-top:6px;font-weight:normal;' onchange="maintenanceItemList.reload()">
							<input type='checkbox' name='wxxmlx' value='1' style='vertical-align:text-bottom;' checked="checked"/>&nbsp;一般&nbsp;&nbsp;
						</label>
						<label class='' style='margin-top:6px;font-weight:normal;' onchange="maintenanceItemList.reload()">
							<input type='checkbox' name='wxxmlx' value='2' style='vertical-align:text-bottom;' checked="checked"/>&nbsp;时控&nbsp;&nbsp;
						</label>
						<label class='' style='margin-top:6px;font-weight:normal;' onchange="maintenanceItemList.reload()">
							<input type='checkbox' name='wxxmlx' value='3' style='vertical-align:text-bottom;' checked="checked"/>&nbsp;时寿&nbsp;&nbsp;
						</label>
					</div>
					<div class="pull-right padding-left-10 padding-right-0">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 210px;">
							<input type="text" placeholder='任务号/参考号/描述/参考文件/部件号'  class="form-control" id="item_keyword_search">
						</div>
	                    <div class=" pull-right padding-left-5 padding-right-0 ">   
							<button id="wxxmItemSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="maintenanceItemList.reload()">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   		</button>
	                   		<!-- <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="maintenanceItemList.search()">
								<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
								<div class="pull-left padding-top-5">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
								</div>
					   		</button> -->
	                    </div> 
					</div>
				</div>
				<div class='clearfix'></div>
				<!-- 更多的搜索框 -->
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-10 display-none search-height border-cccccc" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">来源</div>
							<div class="font-size-9">Source</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control'>
								<option value="" selected="true">显示全部All</option>
						    </select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">ATA章节号</div>
							<div class="font-size-9">ATA</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" />
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">生效日期</div>
							<div class="font-size-9">Effective Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly />
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
				<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
				<div class='table_pagination'>
				<!-- 表格 -->
				<div id="maintenance_item_table_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-height" c-height="45%" style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-hover table-set" id="maintenance_item_table">
							<thead>
								<tr>
									<th class="colwidth-7 downward" onclick="maintenanceItemList.toggleAll()" name="ope_td">
										<div class="font-size-12 line-height-12" >操作</div>
										<div class="font-size-9 line-height-12">Operation</div>
									</th>
									<th class="colwidth-18" >
										<div class="important">
											<div class="font-size-12 line-height-12">任务号</div>
											<div class="font-size-9 line-height-12">Task No.</div>
										</div>
									</th>
									<th width='80px'>
										<div class="font-size-12 line-height-12">版本</div>
										<div class="font-size-9 line-height-12">Rev.</div>
									</th>
									<th class="colwidth-10" >
										<div class="important">
											<div class="font-size-12 line-height-12">参考号</div>
											<div class="font-size-9 line-height-12">Ref No.</div>
										</div>
									</th>
									<th class="colwidth-30">
										<div class="important">
											<div class="font-size-12 line-height-12">任务描述</div>
											<div class="font-size-9 line-height-12">Task Description</div>
										</div>
									</th>
									<th class="colwidth-15" >
										<div class="font-size-12 line-height-12">适用性</div>
										<div class="font-size-9 line-height-12">Applicability</div>
									</th>
									<th class="colwidth-7" >
										<div class="font-size-12 line-height-12">分类</div>
										<div class="font-size-9 line-height-12">category</div>
									</th>
									<th class="colwidth-9" >
										<div class="font-size-12 line-height-12">监控项目</div>
										<div class="font-size-9 line-height-12">Monitor Item</div>
									</th>
									
									<th class="colwidth-9" >
										<div class="font-size-12 line-height-12">首检</div>
										<div class="font-size-9 line-height-12">Inti</div>
									</th>
									<th class="colwidth-7" >
										<div class="font-size-12 line-height-12">周期</div>
										<div class="font-size-9 line-height-12">Cycle</div>
									</th>
									<th class="colwidth-15 downward" onclick="maintenanceItemList.vieworhideContentAll('maintenance_item')" name="th_maintenance_item">
										<div class="font-size-12 line-height-12">容差(-/+)</div>
										<div class="font-size-9 line-height-12">Tolerance(-/+)</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-12">工卡编号</div>
										<div class="font-size-9 line-height-12">Work Card No.</div>
									</th>
									<th class="colwidth-10">
										<div class="important">
											<div class="font-size-12 line-height-12">参考文件</div>
											<div class="font-size-9 line-height-12">Ref Document</div>
										</div>
									</th>
									<th class="colwidth-9" >
										<div class="font-size-12 line-height-12">参考文件附件</div>
										<div class="font-size-9 line-height-12">Attachment</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-12">工作类别</div>
										<div class="font-size-9 line-height-12">Type</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-12">项目类别</div>
										<div class="font-size-9 line-height-12">Type</div>
									</th>
									<th class="colwidth-7" >
										<div class="font-size-12 line-height-12">必检</div>
										<div class="font-size-9 line-height-12">Check</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-12">区域</div>
										<div class="font-size-9 line-height-12">Zone</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-12">接近</div>
										<div class="font-size-9 line-height-12">Access</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-12">ATA章节号</div>
										<div class="font-size-9 line-height-12">ATA</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-12">维修项目大类</div>
										<div class="font-size-9 line-height-12">Classification</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-12">飞机站位</div>
										<div class="font-size-9 line-height-12">Aircraft Stations</div>
									</th>
									<th class="colwidth-5">
										<div class="font-size-12 line-height-12">ALI</div>
										<div class="font-size-9 line-height-12">ALI</div>
									</th>
									<th class="colwidth-7">
										<div class="font-size-12 line-height-12">工时</div>
										<div class="font-size-9 line-height-12">MHRs</div>
									</th>
									<th class="colwidth-20">
										<div class="font-size-12 line-height-12">备注</div>
										<div class="font-size-9 line-height-12">Note</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-12">维护人</div>
										<div class="font-size-9 line-height-12">Maintainer</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-12">维护时间</div>
										<div class="font-size-9 line-height-12">Maintenance Time</div>
									</th>
									
								</tr>
							</thead>
							<tbody id='maintenance_item_list'>
							</tbody>
					</table>
				</div>	
				<!-- <div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div> -->
				<div class='clearfix'></div>
				</div>	
				<div  class="displayContent col-xs-12  clearfix padding-left-0 padding-right-0 padding-top-10 " style='display:none;'>
				    <div id="hideIcon" class="pull-right" style='height:1px;padding-right:8px;'>
						<img src="${ctx}/static/images/down.png" onclick='maintenanceItemList.hideBottom()' style="width:33px;cursor:pointer;">
					</div>
                	<ul id="myChildTab" class="nav nav-tabs tabNew-style bottom_hidden_Ul" style='padding-top:0px !important;'>
                      	<li class="active" ><a id="personTab" href="#Dropdown" data-toggle="tab" aria-expanded="false" style='border-top:0px;'>相关维修项目Maintenance Item</a></li>
                      	<li class="" ><a id="versionTab" href="#profile" data-toggle="tab" aria-expanded="false" style='border-top:0px;'>监控项目版本Monitor Item Rev.</a></li>
                      	<li class="" ><a id="cardTab" href="#card" data-toggle="tab" aria-expanded="false" style='border-top:0px;'>工卡Work Card</a></li>
                    </ul>
                    <div class="tab-content bottom_hidden_Div">
                    	<div class="tab-pane fade active in" id='Dropdown' >
							<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
								<div style="overflow-x: auto;" class='bottom_hidden_table'>
									<!-- start:table -->
									<table class="table table-bordered table-striped table-hover table-set table-thin" style="min-width:510px;border-bottom:1px solid #d5d5d5;">
										<thead>
									   		<tr>
									 <th class="colwidth-7">
										<div class="font-size-12 line-height-12">关联</div>
										<div class="font-size-9 line-height-12">Associated</div>
									</th>
									<th class="colwidth-10 sorting" onclick="maintenanceItemList.orderBy('rwh')" id="rwh_order">
										<div class="font-size-12 line-height-12">任务号</div>
										<div class="font-size-9 line-height-12">Task No.</div>
									</th>
									<th class="colwidth-7 sorting" onclick="maintenanceItemList.orderBy('bb')" id="bb_order">
										<div class="font-size-12 line-height-12">版本</div>
										<div class="font-size-9 line-height-12">Rev.</div>
									</th>
									<th class="colwidth-10 sorting" onclick="maintenanceItemList.orderBy('ckh')" id="ckh_order">
										<div class="font-size-12 line-height-12">参考号</div>
										<div class="font-size-9 line-height-12">Ref No.</div>
									</th>
									<th class="colwidth-10 sorting" onclick="maintenanceItemList.orderBy('rwms')" id="rwms_order">
										<div class="font-size-12 line-height-12">任务描述</div>
										<div class="font-size-9 line-height-12">Task Description</div>
									</th>
									<th class="colwidth-15" >
										<div class="font-size-12 line-height-12">适用性</div>
										<div class="font-size-9 line-height-12">Applicability</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-12">参考文件</div>
										<div class="font-size-9 line-height-12">Ref Document</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-12">工作类别</div>
										<div class="font-size-9 line-height-12">Type</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-12">项目类别</div>
										<div class="font-size-9 line-height-12">Type</div>
									</th>
									<th class="colwidth-7" >
										<div class="font-size-12 line-height-12">必检</div>
										<div class="font-size-9 line-height-12">Check</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-12">区域</div>
										<div class="font-size-9 line-height-12">Zone</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-12">接近</div>
										<div class="font-size-9 line-height-12">Access</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-12">飞机站位</div>
										<div class="font-size-9 line-height-12">Aircraft Station</div>
									</th>
									<th class="colwidth-5">
										<div class="font-size-12 line-height-12">ALI</div>
										<div class="font-size-9 line-height-12">ALI</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-12">工时</div>
										<div class="font-size-9 line-height-12">MHRs</div>
									</th>
									 </tr>
										</thead>
										<tbody id="maintenance_relate_list">
										</tbody>
									</table>
									<!-- end:table -->
								</div>
								<div class="clearfix"></div>
							</div>
                      	</div>
                      	<div class="tab-pane fade" id="profile">
                      		<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0" >
								<div style="overflow-x: auto;" class='bottom_hidden_table'>
									<!-- start:table -->
									<table class="table table-bordered table-striped table-hover text-center table-set table-thin" style="min-width:510px;border-bottom:1px solid #d5d5d5;" id="maintenance_version_table">
										<thead>
									   		<tr>
									 
									<th class="colwidth-10">
										<div class="font-size-12 line-height-12">版本</div>
										<div class="font-size-9 line-height-12">Rev.</div>
									</th>
									<th class="colwidth-7" id="maintenance_version_bj">
										<div class="font-size-12 line-height-12">部件</div>
										<div class="font-size-9 line-height-12">Component</div>
									</th>
									<th class="colwidth-9" id="maintenance_version_jkxm">
										<div class="font-size-12 line-height-12">监控项目</div>
										<div class="font-size-9 line-height-12">Monitor Item</div>
									</th>
									<th class="colwidth-9" id="maintenance_version_sj">
											<div class="font-size-12 line-height-12">首检</div>
											<div class="font-size-9 line-height-12">INTI</div>	
									</th>
									<th class="colwidth-7" id="maintenance_version_zq">
										<div class="font-size-12 line-height-12">周期</div>
										<div class="font-size-9 line-height-12">Cycle</div>
									</th>
									<th class="colwidth-15 upward" id="maintenance_version_rc" onclick="maintenanceItemList.vieworhideContentAll('maintenance_version')" name="th_maintenance_version">
										<div class="font-size-12 line-height-12">容差(-/+)</div>
										<div class="font-size-9 line-height-12">Tolerance(-/+)</div>
									</th>
									 </tr>
										</thead>
										<tbody id="maintenance_version_list">
										</tbody>
									</table>
									<!-- end:table -->
								</div>
								<div class="clearfix"></div>
							</div>
                      	</div>
                      	<div class="tab-pane fade" id="card">
                      		<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0" >
								<div style="overflow-x: auto;" class='bottom_hidden_table'>
									<!-- start:table -->
									<table class="table table-bordered table-striped table-hover text-center table-set table-thin" style="min-width:510px;border-bottom:1px solid #d5d5d5;" id="maintenance_card_table">
										<thead>
									   		<tr>
									 
									<th class="colwidth-10">
										<div class="font-size-12 line-height-12">工卡编号</div>
										<div class="font-size-9 line-height-12">Work Card No.</div>
									</th>
									<th class="colwidth-5">
										<div class="font-size-12 line-height-12">版本</div>
										<div class="font-size-9 line-height-12">Rev.</div>
									</th>
									<th class="colwidth-30">
										<div class="font-size-12 line-height-12">标题</div>
										<div class="font-size-9 line-height-12">Title</div>
									</th>
									<th class="colwidth-10">
											<div class="font-size-12 line-height-12">工作组</div>
											<div class="font-size-9 line-height-12">Work Group</div>	
									</th>
									<th class="colwidth-5">
											<div class="font-size-12 line-height-12">附件</div>
											<div class="font-size-9 line-height-12">Attachment</div>	
									</th>
									 </tr>
										</thead>
										<tbody id="maintenance_card_list">
										</tbody>
									</table>
									<!-- end:table -->
								</div>
								<div class="clearfix"></div>
							</div>
                      	</div>
                  </div>
              </div>
			</div>
    </div>
    <script type="text/javascript" src="${ctx}/static/js/thjw/project2/maintenance/maintenance_item_list.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/maintenance/maintenance_project_edit.js"></script>
    <%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
    <%@ include file="/WEB-INF/views/open_win/import.jsp"%>
	<%@ include file="/WEB-INF/views/project2/maintenance/maintenance_project_edit_win.jsp"%><!-------工作内容对话框 -------->