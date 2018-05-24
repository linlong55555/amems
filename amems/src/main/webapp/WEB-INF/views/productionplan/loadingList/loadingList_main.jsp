<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Home</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
.ztree [class^="icon-"] {
	font-family: FontAwesome;
}

.list-group {
	margin-bottom: 0px;
}
.line {
    border-bottom: 0px;
}
.dropdown-menu li{
	padding-left: 0 !important;
}
a.list-group-item.active, a.list-group-item.active:hover, a.list-group-item.active:focus{
	z-index: 2;
	color: #333;
	background-color: #dbe2f7;
	border-color: #dbe2f7;
}
#divSearch:before {
  content: '';
  display: inline-block;
  border-left: 7px solid transparent;
  border-right: 7px solid transparent;
  border-bottom: 7px solid #ccc;
  border-bottom-color: rgba(0, 0, 0,0.2);
  position: absolute;
  top: -8px;
  right: 95px;
}
#divSearch:after {
  content: '';
  display: inline-block;
  border-left: 6px solid transparent;
  border-right: 6px solid transparent;
  border-bottom: 6px solid #ffffff;
  position: absolute;
  top: -6px;
  right: 96px;
}
</style>
</head>
<body>

	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar">
			</div>

			<div class="panel-body padding-bottom-0">
				<div class="row" style="margin-left: 0px; margin-right: 0px;">
					<div class="col-sm-3 padding-left-0 ">
						<div class="col-sm-4 text-right">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</div>
						<div class="col-sm-8 pull-left padding-left-0">
							<select class='form-control' id='fjzch_search'
								name="fjzch_search" onchange="changeSelectedPlane()">
							</select>
						</div>

					</div>
					
					<div class="col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_head" class="form-control " onchange="changeOrganization()">
							   <c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
									<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-sm-6 margin-bottom-10 padding-right-0">
						
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1 pull-right"
							onclick="synchronizeEffective();">
							<div class="font-size-12">初始化完成</div>
							<div class="font-size-9">Synchronize</div>
						</button>
					</div>
				</div>


				<div class="clearfix"></div>


				<!-----标签导航start---->
				<ul class="nav nav-tabs" role="tablist" id="tablist">
					<li role="presentation" class="active" id="tab-1"><a
						href="#planeLoadingList">飞机装机清单</a></li>
					<li role="presentation"  id="tab-2"><a href="#timeMonitor">时控件监控设置</a></li>
					<li role="presentation"  id="tab-3"><a href="#fixedMonitor">定检监控设置</a></li>
				</ul>
				<!-----标签导航end---->

				<!-----标签内容start---->
				<div class="tab-content">
					<!---------------------------------------- sheet1  飞机装机清单 start --------------------------------------------->
					<div class="tab-pane fade in active" id="planeLoadingList">
						<div id="treeView"  style="display: none;">
							<input type="hidden" id="current_id" />
							<div class=" feature">
								<!-- 装机清单树-->
								<div class="col-sm-6" style="padding: 0 0 10px 0;" id="left_div">

									<div
										class="col-sm-11  padding-left-0 padding-right-0 margin-bottom-10 ">
										<div class="input-group">
											<input type="text" class="form-control " id="keyword_search"
												placeholder="件号/序列号/中英文名称/ATA章节号/内部识别码/厂家件号" /> <span
												class="input-group-btn">
												<button
													class="btn btn-default  padding-top-1 padding-bottom-1 pull-right"
													type="button" onclick="refreshTree('', false, true)">
													<div class="font-size-12">搜索</div>
													<div class="font-size-9">Search</div>
												</button>
											</span>
										</div>
									</div>


									<div class="col-sm-12  padding-left-0">
										<button type="button"
											class="btn btn-default padding-top-1 padding-bottom-1"
											onclick="addLoadingList()">
											<div class="font-size-12">新增</div>
											<div class="font-size-9">Add</div>
										</button>
										&nbsp;&nbsp;
										<button class="btn btn-default padding-top-1 padding-bottom-1"
											onclick="alertScrapWarnMessage()">
											<div class="font-size-12">作废</div>
											<div class="font-size-9">Invalid</div>
										</button>
										&nbsp;&nbsp;
										<button class="btn btn-default padding-top-1 padding-bottom-1"
											onclick="ajaxComponent()">
											<div class="font-size-12">维护子部件</div>
											<div class="font-size-9">Sub Parts</div>
										</button>
									</div>
									<div class="clearfix"></div>
									<div class="zTreeDemoBackground margin-right-10">
										<ul id="loadingList_tree" class="ztree"
											style="height: 430px; overflow: auto;"></ul>
									</div>
								</div>
								<div class="col-sm-6 padding-left-0 padding-right-0" id="right_div">
									<form id="componentForm">
											<div class="col-sm-12 padding-left-0 padding-right-0">
												<div class="panel panel-default">
													<div class="panel-heading col-xs-12 margin-bottom-10 padding-right-0" style="padding-top: 2px !important; padding-bottom: 2px !important;">
														<div class="pull-left">
															<h3 class="panel-title padding-top-5">部件基本信息</h3>
														</div>
														<div class="pull-right">
															<button type="button" class="btn btn-default btn-xs padding-bottom-1 resizeHeight" onclick="switchView()">
																<div class="font-size-12">切换显示</div>
																<div class="font-size-9">Toggle</div>
															</button>
															&nbsp;
														</div>
													</div>
													<div class="panel-body">
														<div class="col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0">
															<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
																<label class="col-xs-4 text-right padding-left-0 padding-right-0">
																	<div class="font-size-12 line-height-18">机型</div>
																	<div class="font-size-9 line-height-18">Model</div>
																</label>
																<div class="col-xs-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="fjjx" name="fjjx" disabled="disabled">
																</div>
															</div>
															<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
																<label class="col-xs-4 text-right padding-left-0 padding-right-0">
																	<div class="font-size-12 line-height-18">飞机注册号</div>
																	<div class="font-size-9 line-height-18">A/C REG</div>
																</label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="fjzch" name="fjzch" disabled="disabled">
																</div>
															</div>
														</div>
														<div class="clearfix"></div>
														<div class="col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0">
															<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
																<label class="col-xs-4 text-right padding-left-0 padding-right-0">
																	<div class="font-size-12 line-height-18">
																		<span style="color: red">*</span>ATA章节号
																	</div>
																	<div class="font-size-9 line-height-18">ATA</div>
																</label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																	<div class="input-group">
																		<input class="form-control" id="zjh_show" name="zjh_show" type="text" disabled="disabled">
																		<input id="zjh" name="zjh" type="hidden"> 
																		<span class="input-group-btn">
																			<button id="main_chapter_btn" class="btn btn-primary form-control" type="button" onclick="openChapterWin();">
																				<i class="icon-search"></i>
																			</button>
																		</span>
																	</div>
																</div>
															</div>
															<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
																<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">
																		<span style="color: red">*</span>件号
																	</div>
																	<div class="font-size-9 line-height-18">P/N</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="jh"
																		name="jh" maxlength="50">
																</div>
															</div>
														</div>
														<div class="clearfix"></div>
														<div class="col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0">
															<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">厂家件号</div>
																	<div class="font-size-9 line-height-18">MP/N</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="cjjh"
																		name="cjjh" maxlength="50">
																</div>
															</div>
															<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">序列号</div>
																	<div class="font-size-9 line-height-18">S/N</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="xlh"
																		name="xlh" maxlength="50" onkeyup="limitCount()">
																</div>
															</div>
														</div>
														<div class="clearfix"></div>
														<div
															class="col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0">
															<div
																class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">英文名称</div>
																	<div class="font-size-9 line-height-18">F.Name</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="ywmc"
																		name="ywmc" maxlength="300">
																</div>
															</div>
															<div
																class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">中文名称</div>
																	<div class="font-size-9 line-height-18">CH.Name</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="zwmc"
																		name="zwmc" maxlength="100">
																</div>
															</div>
														</div>
														<div class="clearfix"></div>
														<div class="col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0">
															<div
																class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">内部识别码</div>
																	<div class="font-size-9 line-height-18">Code</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="nbsbm"
																		name="nbsbm" maxlength="50">
																</div>
															</div>
															<div
																class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">位置</div>
																	<div class="font-size-9 line-height-18">Location</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																	<select class='form-control' id='wz' name="wz">
																		<option value="0">机身 Fuselage</option>
																		<option value="1">1#左发 L/Engine</option>
																		<option value="2">2#右发 R/Engine</option>
																		<option value="5">外吊挂 E/S</option>
																		<option value="4">搜索灯 Search</option>
																		<option value="3">绞车 Winch</option>
																	</select>
																</div>
															</div>
														</div>
														<div class="clearfix"></div>
														<div class="col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0">
															<div
																class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">许可证号</div>
																	<div class="font-size-9 line-height-18">Licence
																		No.</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="xkzh"
																		name="xkzh" maxlength="50">
																</div>
															</div>
															<div
																class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">
																		适航证号
																	</div>
																	<div class="font-size-9 line-height-18">Certificate
																		No.</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="shzh"
																		name="shzh" maxlength="50">
																</div>
															</div>
														</div>
														<div class="clearfix"></div>
														<div
															class="col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0">
															<div
																class="col-xs-12 col-sm-6  padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">批次号</div>
																	<div class="font-size-9 line-height-18">B/N</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="pch"
																		name="pch" maxlength="50">
																</div>
															</div>

															<div
																class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
																<label
																	class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																		class="font-size-12 line-height-18">数量</div>
																	<div class="font-size-9 line-height-18">Quantity</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																	<input type="text" class="form-control" id="zjsl"
																		name="zjsl" maxlength="6">
																</div>
															</div>
														</div>

														<div class="clearfix"></div>
														<div
															class="col-sm-12 col-xs-12  padding-left-0 padding-right-0  margin-bottom-10">
															<label
																class="col-xs-4 col-sm-2 col-lg-2 text-right padding-left-0 padding-right-0">
																<div class="font-size-12 line-height-18">改装记录</div>
																<div class="font-size-9 line-height-18">Record</div>
															</label>
															<div class="col-xs-8 col-sm-10 col-lg-10 padding-left-8 padding-right-0">
																<textarea rows="1" name="bjgzjl" class="form-control"
																	id="bjgzjl" maxlength="500"></textarea>
															</div>
														</div>


														<div class="clearfix"></div>
														<div
															class="col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0">
															<label class="col-xs-4 col-sm-2 col-lg-2 text-right padding-left-0 padding-right-0">
																<div class="font-size-12 line-height-18">备注</div>
																<div class="font-size-9 line-height-18">Remark</div>
															</label>
															<div class="col-xs-8 col-sm-10 col-lg-10 padding-left-8 padding-right-0">
																<textarea rows="1" class="form-control" name="bz" id="bz" name="bz" maxlength="300"></textarea>
															</div>
														</div>
													</div>
												</div>
											</div>

											<div class="col-sm-12 padding-left-0 padding-right-0">
												<div class="panel panel-default">
													<div class="panel-heading">
														<h3 class="panel-title margin-bottom-5">设置监控信息</h3>
													</div>
													<div class="panel-body">

														<div
															class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
															<label
																class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">生产日期</div>
																<div class="font-size-9 line-height-18">Production
																	Date</div></label>
															<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input class="form-control date-picker" name="scrq"
																	data-date-format="yyyy-mm-dd" type="text" id="scrq"
																	maxlength="10" />
															</div>
														</div>

														<div
															class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
															<label
																class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">安装日期</div>
																<div class="font-size-9 line-height-18">Installation
																	Date</div></label>
															<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input class="form-control date-picker" name="azrq"
																	data-date-format="yyyy-mm-dd" type="text" id="azrq"
																	maxlength="10" />
															</div>
														</div>
														<div class="clearfix"></div>
														<div
															class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
															<label
																class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">安装记录单号</div>
																<div class="font-size-9 line-height-18">Flight Log
																	No.</div></label>
															<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control" id="azjldh"
																	name="azjldh" maxlength="50">
															</div>
														</div>
														<div
															class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
															<label
																class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">拆除日期</div>
																<div class="font-size-9 line-height-18">Remove
																	Date</div></label>
															<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input class="form-control date-picker" name="ccrq"
																	data-date-format="yyyy-mm-dd" type="text" id="ccrq"
																	maxlength="10" />
															</div>
														</div>
														<div class="clearfix"></div>
														<div
															class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
															<label
																class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">拆除记录单号</div>
																<div class="font-size-9 line-height-18">Remove No.</div></label>
															<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control" id="ccjldh"
																	name="ccjldh" maxlength="50">
															</div>
														</div>
														<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
															<label
																class="pull-left col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
																<div class="font-size-12">控制类型</div>
																<div class="font-size-9">Monitor Type</div>
															</label>
															<div
																class="col-sm-8 col-xs-8 padding-left-8 padding-right-0">
																<select class='form-control' id='kzlx' onchange="reValidateXlh()">
																	<option value="1">时控件</option>
																	<option value="2">时寿件</option>
																	<option value="3">非控制件</option>
																</select>
															</div>
														</div>
														<div class="clearfix"></div>

														<div
															class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
															<label
																class="pull-left col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
																<div class="font-size-12">履历卡类型</div>
																<div class="font-size-9">Log Card Type</div>
															</label>
															<div
																class="col-sm-8 col-xs-8 padding-left-8 padding-right-0">
																<select class='form-control' id='llklx'>
																	<option value="1">无履历卡</option>
																	<option value="2">原装履历卡</option>
																	<option value="3">自制履历卡</option>
																</select>
															</div>
														</div>
														<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
															<label class="col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
																<div class="font-size-12 line-height-18">履历卡编号</div>
																<div class="font-size-9 line-height-18">Log Card No.</div>
															</label>
															<div class="col-sm-8 col-xs-8 padding-left-8 padding-right-0">
																<input class="form-control" name="llkbh" type="text" id="llkbh" maxlength="50" />
															</div>
														</div>
														<div class="clearfix"></div>
														<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
															<label class="col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
																<div class="font-size-12">是否定检</div>
																<div class="font-size-9">Fixed Check</div>
															</label>
															<div class="col-sm-8 col-xs-8 padding-left-8 padding-right-0">
																<select class='form-control' id='isDj' onchange="reValidateXlh()">
																	<option value="1">是Yes</option>
																	<option value="0">否No</option>
																</select>
															</div>
														</div>
														<div class="clearfix"></div>
													</div>

												</div>
											</div>
											<div class="col-sm-12 padding-left-0 padding-right-0 margin-bottom-10">
												<div class="text-right">
													<button id="main_insertOrUpdate_btn" type="button"
														class="btn btn-primary padding-top-1 padding-bottom-1"
														onclick="insertOrUpdate()">
														<div class="font-size-12">保存</div>
														<div class="font-size-9">Save</div>
													</button>
												</div>
											</div>
								</div>
								</form>
							</div>
						</div>
						<input type="hidden" id="adjustHeight" value="105">
						<div id="tableView">
							<div>
								<div class="col-xs-2 padding-left-0 row-height">
									<button type="button"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										onclick="modify_tableView();">
										<div class="font-size-12">新增</div>
										<div class="font-size-9">Add</div>
									</button>
									<button type="button"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										onclick="showImportModal()">
										<div class="font-size-12">导入</div>
										<div class="font-size-9">Import</div>
									</button>
									<button type="button" onclick="outLodingListExcel();"
										class="btn btn-primary padding-left-5 padding-top-1 padding-bottom-1">
										<div class="font-size-12">导出</div>
										<div class="font-size-9">Export</div>
									</button>
								</div>
								
								<div class="pull-right padding-left-0 padding-right-0">
									
									<div class="pull-left">
										<div class="pull-left text-right padding-left-0 padding-right-0">
											<div class="font-size-12">状态</div>
											<div class="font-size-9">Status</div>
										</div>
										<div class="pull-left text-right padding-left-0 padding-right-0">
											<div class="padding-left-8 pull-left" style="width: 150px; margin-right:5px;">
											   <select class='form-control' id='zt_search' name="zt_search" onchange="searchTableView()">
													<option value="1" selected="selected">当前</option>
													<option value="all">历史</option>
												</select>
											</div>
										</div>
									</div>
									
									<div class="pull-left">
										<div class="pull-left text-right padding-left-0 padding-right-0">
											<div class="font-size-12">位置</div>
											<div class="font-size-9">Location</div>
										</div>
										<div class="pull-left text-right padding-left-0 padding-right-0">
											<div class="padding-left-8 pull-left" style="width: 150px; margin-right:5px;">
											   <select class='form-control' id='wz_tableView' onchange="searchTableView()">
													<option value="">显示全部All</option>
													<option value="0">机身 Fuselage</option>
													<option value="1">1#左发 L/Engine</option>
													<option value="2">2#右发 R/Engine</option>
													<option value="5">外吊挂 E/S</option>
													<option value="4">搜索灯 Search</option>
													<option value="3">绞车 Winch</option>
												</select> 
											</div>
										</div>
									</div>
									
									<div class="pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" class="form-control "
											id="keyword_search_tableView"
											placeholder="ATA章节号/中英文名称/件号/序列号/内部识别码/厂家件号/安装拆除记录单号/履历卡编号/备注" />
									</div>
									
									<div class="pull-right padding-left-5 padding-right-0">
										<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchTableView();">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
										</button>
										<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" onclick="search();">
											<div class="pull-left text-center">
												<div class="font-size-12">更多</div>
												<div class="font-size-9">More</div>
											</div>
											<div class="pull-left padding-top-5">
												&nbsp;<i class="icon-caret-down font-size-15" id="icon"></i>
											</div>
										</button>
										<button type="button" class="btn btn-default padding-top-1 padding-bottom-1" onclick="switchView();">
											<div class="pull-left text-center">
												<div class="font-size-12">切换显示</div>
												<div class="font-size-9">Toggle</div>
											</div>
										</button>
										
										
									</div>

								</div>
							</div>
								<div
									class="col-xs-12 triangle  padding-top-10 margin-top-10 display-none border-cccccc search-height"
									id="divSearch">
									<span
										class="col-xs-12 col-sm-6 col-lg-3 text-right padding-left-0 padding-right-0">
										<span
										class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">生产日期</div>
											<div class="font-size-9">Production Date</div>
									</span>
										<div
											class="form-group col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<input class="form-control date-range-picker" type="text"
												id="scrq_tableView" />
										</div>
									</span>  <span
										class="col-xs-12 col-sm-6 col-lg-3 text-right padding-left-0 padding-right-0">
										<span
										class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">安装日期</div>
											<div class="font-size-9">Installation Date</div>
									</span>
										<div
											class="form-group col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<input class="form-control date-range-picker" type="text"
												id="azrq_tableView" />
										</div>
									</span>  <span
										class="col-xs-12 col-sm-6 col-lg-3 text-right padding-left-0 padding-right-0">
										<span
										class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">拆除日期</div>
											<div class="font-size-9">Remove Date</div>
									</span>
										<div
											class="form-group col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<input class="form-control date-range-picker" type="text"
												id="ccrq_tableView" />
										</div>
									</span> <span
										class="col-xs-12 col-sm-6 col-lg-3 text-right padding-left-0 padding-right-0">
										<span
										class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">履历卡类型</div>
											<div class="font-size-9">Log Card Type</div>
									</span>
										<div
											class="form-group col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<select class='form-control' id='llklx_tableView'>
												<option value="">显示全部All</option>
												<option value="1">无履历卡</option>
												<option value="2">原装履历卡</option>
												<option value="3">自制履历卡</option>
											</select>
										</div>
									</span>  <span
										class="col-xs-12 col-sm-6 col-lg-3 text-right padding-left-0 padding-right-0">
										<span
										class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">控制类型</div>
											<div class="font-size-9">Monitor Type</div>
									</span>
										<div
											class="form-group col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<select class='form-control' id='kzlx_tableView'>
												<option value="">显示全部All</option>
												<option value="1">时控件</option>
												<option value="2">时寿件</option>
												<option value="3">非控制件</option>
											</select>
										</div>
									</span> <span
										class="col-xs-12 col-sm-6 col-lg-3 text-right padding-left-0 padding-right-0">
										<span
										class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">是否定检</div>
											<div class="font-size-9">Fixed Check</div>
									</span>
										<div
											class="form-group col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<select class='form-control' id='isDj_tableView'>
												<option value="">显示全部All</option>
												<option value="1">是Yes</option>
												<option value="0">否No</option>
											</select>
										</div>
									</span>
									<span class="col-xs-12 col-sm-6 col-lg-3 text-right padding-left-0 padding-right-0">
										<span class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">装机状态</div>
											<div class="font-size-9">Install Status</div>
										</span>
										<div class="form-group col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<select class='form-control' id='zt_tableView'>
												<option value="">显示全部All</option>
												<option value="1">装上</option>
												<option value="2">拆下</option>
												<option value="3">作废</option>
											</select>
										</div>
									</span>
									<div class="col-lg-2 text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
										<button type="button"
											class="btn btn-primary padding-top-1 padding-bottom-1"
											onclick="searchreset();">
											<div class="font-size-12">重置</div>
											<div class="font-size-9">Reset</div>
										</button>
									</div>
								</div>

							<div class="clearfix"></div>
							<div
								class="col-xs-12 text-center margin-bottom-5 padding-left-0 padding-right-0 table-h"
								style="overflow-x: scroll">

								<table
									class="table table-thin table-bordered text-center table-set" id="planelist_table">
									<thead>
										<tr>
											<th style="width: 60px;" class="fixed-column"><div
													class="font-size-12 line-height-18">操作</div>
												<div class="font-size-9 line-height-18">Operation</div></th>
											<th style="width: 60px;" class="sorting"
												onclick="orderBy('zt')" id="zt_order"><div
													class="font-size-12 line-height-18">状态</div>
												<div class="font-size-9 line-height-18">State</div></th>
											<th style="width: 50px;" id="fjzch"><div
													class="font-size-12 line-height-18">节点</div>
												<div class="font-size-9 line-height-18">Node</div></th>
											<th style="width: 180px;" id="zjh">
												<div class="important">
													<div class="font-size-12 line-height-18">ATA章节号</div>
													<div class="font-size-9 line-height-18">ATA</div>
												</div>
											</th>
											<th style="width: 250px;" id="bzm">
												<div class="important">
													<div class="font-size-12 line-height-18">英文名称</div>
													<div class="font-size-9 line-height-18">F.Name</div>
												</div>
											</th>
											<th style="width: 200px;" id="ccrq">
												<div class="important">
													<div class="font-size-12 line-height-18">中文名称</div>
													<div class="font-size-9 line-height-18">CH.Name</div>
												</div>
											</th>
											<th style="width: 150px;" class="sorting" onclick="orderBy('jh')" id="jh_order">
												<div class="important">
													<div class="font-size-12 line-height-18">件号</div>
													<div class="font-size-9 line-height-18">P/N</div>
												</div>
											</th>
											<th style="width: 200px;" class="sorting" onclick="orderBy('xlh')" id="xlh_order">
												<div class="important">
													<div class="font-size-12 line-height-18">序列号</div>
													<div class="font-size-9 line-height-18">S/N</div>
												</div>
											</th>
											<th style="width: 150px;" class="sorting" onclick="orderBy('nbsbm')" id="nbsbm_order">
												<div class="important">
													<div class="font-size-12 line-height-18">内部识别码</div>
													<div class="font-size-9 line-height-18">Identification Code</div>
												</div>
											</th>
											<th style="width: 150px;" id="cjjh">
												<div class="important">
													<div class="font-size-12 line-height-18">厂家件号</div>
													<div class="font-size-9 line-height-18">MP/N</div>
												</div>
											</th>
											<th style="width: 200px;" id="pch"><div
													class="font-size-12 line-height-18">批次号</div>
												<div class="font-size-9 line-height-18">B/N</div></th>
											<th style="width: 80px;" id="init_date_rq"><div
													class="font-size-12 line-height-18">数量</div>
												<div class="font-size-9 line-height-18">Quantity</div></th>
											<th style="width: 100px;" class="sorting"
												onclick="orderBy('wz')" id="wz_order"><div
													class="font-size-12 line-height-18">部件装机位置</div>
												<div class="font-size-9 line-height-18">Location</div></th>
											<th style="width: 100px;" class="sorting"
												onclick="orderBy('scrq')" id="scrq_order"><div
													class="font-size-12 line-height-18">生产日期</div>
												<div class="font-size-9 line-height-18">Production
													Date</div></th>
											<th style="width: 100px;" class="sorting" onclick="orderBy('xkzh')" id="xkzh_order"><div
													class="font-size-12 line-height-18">许可证号</div>
												<div class="font-size-9 line-height-18">Licence No.</div></th>
											<th style="width: 100px;" class="sorting" onclick="orderBy('shzh')"  id="shzh_order"><div
													class="font-size-12 line-height-18">适航证号</div>
												<div class="font-size-9 line-height-18">Certificate
													No.</div></th>
											<th style="width: 100px;" class="sorting"
												onclick="orderBy('azrq')" id="azrq_order"><div
													class="font-size-12 line-height-18">安装日期</div>
												<div class="font-size-9 line-height-18">Installation
													Date</div></th>
											<th style="width: 100px;">
												<div class="important">
													<div class="font-size-12 line-height-18">安装记录单号</div>
													<div class="font-size-9 line-height-18">Flight Log No.</div>
												</div>
											</th>
											<th style="width: 100px;" class="sorting"
												onclick="orderBy('ccrq')" id="ccrq_order"><div
													class="font-size-12 line-height-18">拆除日期</div>
												<div class="font-size-9 line-height-18">Remove Date</div></th>
											<th style="width: 100px;">
												<div class="important">
													<div class="font-size-12 line-height-18">拆除记录单号</div>
													<div class="font-size-9 line-height-18">Remove No.</div>
												</div>
											</th>
											<th style="width: 100px;" class="sorting"
												onclick="orderBy('llklx')" id="llklx_order"><div
													class="font-size-12 line-height-18">履历卡类型</div>
												<div class="font-size-9 line-height-18">Log Card Type</div></th>
											<th style="width: 100px;" class="sorting" onclick="orderBy('llkbh')" id="llkbh_order">
												<div class="important">
													<div class="font-size-12 line-height-18">履历卡编号</div>
													<div class="font-size-9 line-height-18">Log Card No.</div>
												</div>
											</th>
											<th style="width: 100px;" class="sorting"
												onclick="orderBy('kzlx')" id="kzlx_order"><div
													class="font-size-12 line-height-18">监控类型</div>
												<div class="font-size-9 line-height-18">Monitor Type</div></th>
											<th style="width: 150px;" class="sorting"
												onclick="orderBy('timeMonitorFlag')" id="timeMonitorFlag_order"><div
													class="font-size-12 line-height-18">时控件监控配置标识</div>
												<div class="font-size-9 line-height-18">Time Monitor Setting</div></th>
											<th style="width: 100px;" class="sorting"
												onclick="orderBy('is_Dj')" id="is_Dj_order"><div
													class="font-size-12 line-height-18">是否定检</div>
												<div class="font-size-9 line-height-18">Fixed Check</div></th>
											<th style="width: 150px;" class="sorting"
												onclick="orderBy('fixMonitorFlag')" id="fixMonitorFlag_order"><div
													class="font-size-12 line-height-18">定检件监控配置标识</div>
												<div class="font-size-9 line-height-18">Fixed Monitor Setting</div></th>
											<th style="width: 200px;">
												<div class="important">
													<div class="font-size-12 line-height-18">备注</div>
													<div class="font-size-9 line-height-18">Remark</div>
												</div>
											</th>
										</tr>
									</thead>
									<tbody id="planelist">
										<!------ plane列表展示 ------>
									</tbody>
								</table>
							</div>
							<div class=" col-xs-12  text-center page-height" style="margin-top: 0px; margin-bottom: 0px;" id="mpagination"></div>
						</div>
					</div>
					<!---------------------------------------- sheet1  飞机装机清单 end --------------------------------------------->

					<!--------------------------------------- sheet2  时控件监控 start --------------------------------------------->
					<div class="tab-pane fade" id="timeMonitor">
						<div class=" feature">
							<%@ include
								file="/WEB-INF/views/productionplan/loadingList/loadingList_timeMonitor.jsp"%>
						</div>
					</div>
					<!--------------------------------------- sheet2  时控件监控 end --------------------------------------------->

					<!--------------------------------------- sheet3  定检监控 start --------------------------------------------->
					<div class="tab-pane fade" id="fixedMonitor">
						<div class=" feature">
							<%@ include
								file="/WEB-INF/views/productionplan/loadingList/loadingList_fixedMonitor.jsp"%>
						</div>
					</div>
					<!--------------------------------------- sheet3  定检监控 end --------------------------------------------->
					<!-----标签内容end---->
				</div>
			</div>
		</div>

		<!-------维护子部件关联 Start-------->
		<div class="modal fade" id="component_remoteModal" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg" style="width: 1350px;">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">当前部件</div>
							<div class="font-size-9 ">Current Part</div>
						</div>
						
						<div class="text-left">
							<div class="row">
								<div class="col-sm-3">
									<label class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-18">P/N</div>
									</label>
									<div class=" col-xs-9 padding-left-8 padding-right-0">
									<input id="jh_subModal" class="form-control" disabled="disabled" type="text">
									</div>
								</div>
								<div class="col-sm-3">
									<label class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">S/N</div>
									</label>
									<div class=" col-xs-9 padding-left-8 padding-right-0">
									<input id="xlh_subModal" class="form-control" disabled="disabled" type="text">
									</div>
								</div>
								<div class="col-sm-3">
									<label class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-18">F.Name</div>
									</label>
									<div class=" col-xs-9 padding-left-8 padding-right-0">
									<input id="ywmc_subModal" class="form-control" disabled="disabled" type="text">
									</div>
								</div>
								<div class="col-sm-3">
									<label class="col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-18">CH.Name</div>
									</label>
									<div class=" col-xs-9 padding-left-8 padding-right-0">
									<input id="zwmc_subModal" class="form-control" disabled="disabled" type="text">
									</div>
								</div>
							</div>
							
							<br/>
							
							<div class="panel-heading  padding-top-3 padding-bottom-1">
								<div class="font-size-16 line-height-18">待选择子部件</div>
								<div class="font-size-9 ">Add Sub Parts</div>
							</div>
							
							<div class="pull-right padding-right-0 margin-bottom-10 padding-top-0">
								<div class="pull-left">
									<div class="pull-left text-right padding-left-0 padding-right-0">
										<div class="font-size-12">位置</div>
										<div class="font-size-9">Location</div>
									</div>
									<div class="pull-left text-right padding-left-0 padding-right-0">
										<div class="padding-left-8 pull-left" style="width: 250px; margin-right:5px;">
										   <select id="wz_main" class="form-control" onchange="ajaxComponent()">
										   		<option value="">显示全部All</option>
												<option value="0">机身 Fuselage</option>
												<option value="1">1#左发 L/Engine</option>
												<option value="2">2#右发 R/Engine</option>
												<option value="5">外吊挂 E/S</option>
												<option value="4">搜索灯 Search</option>
												<option value="3">绞车 Winch</option>
										   </select> 
										</div>
									</div>
								</div>
								<div class="pull-left padding-left-5 padding-right-0" style="width: 250px;">
									<input type="text" class="form-control " id="subcomponent_search" placeholder="件号/序列号/中英文名称/ATA章节号/内部识别码/厂家件号"/>
								</div>
			              		<div class="pull-right padding-left-5 padding-right-0">
			              			<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="ajaxComponent();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
									</button>     
			                    </div>    
							</div>
							
						</div>
						
						<table
							class="table table-thin table-bordered table-striped table-hover text-center table-set">
							<thead>
								<tr>
									<th width="6%">
										<div class="font-size-12 line-height-18">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th width="10%">
										<div class="important">
											<div class="font-size-12 line-height-18">ATA章节号</div>
											<div class="font-size-9 line-height-18">ATA</div>
										</div>
									</th>
									<th width="24%">
										<div class="important">
											<div class="font-size-12 line-height-18">英文名称</div>
											<div class="font-size-9 line-height-18">F.Name</div>
										</div>
									</th>
									<th width="10%">
										<div class="important">
											<div class="font-size-12 line-height-18">中文名称</div>
											<div class="font-size-9 line-height-18">CH.Name</div>
										</div>
									</th>
									<th width="12%">
										<div class="important">
											<div class="font-size-12 line-height-18">件号</div>
											<div class="font-size-9 line-height-18">P/N</div>
										</div>
									</th>
									<th width="12%">
										<div class="important">
											<div class="font-size-12 line-height-18">序列号</div>
											<div class="font-size-9 line-height-18">S/N</div>
										</div>
									</th>
									<th width="12%">
										<div class="font-size-12 line-height-18">内部识别码</div>
										<div class="font-size-9 line-height-18">Identification Code</div>
									</th>
									<th width="12%">
										<div class="important">
											<div class="font-size-12 line-height-18">厂家件号</div>
											<div class="font-size-9 line-height-18">MP/N</div>
										</div>
									</th>
									<th width="12%">
										<div class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">B/N</div>
									</th>
									<th width="6%">
										<div class="font-size-12 line-height-18">数量</div>
										<div class="font-size-9 line-height-18">Quantity</div>
									</th>
									<th width="8%">
										<div class="font-size-12 line-height-18">位置</div>
										<div class="font-size-9 line-height-18">Location</div>
									</th>
								</tr>
							</thead>
							<tbody id="loadingList">
							</tbody>
						</table>
						<div class="col-xs-12  text-center padding-right-0 padding-left-0" style="margin-top: 0px; margin-bottom: 0px;" id="pagination"></div>
						<div class="clearfix"></div>
						<hr />
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">已选择子部件</div>
							<div class="font-size-9 ">Add Sub Parts</div>
						</div>
						<table
							class="table table-thin table-bordered table-striped table-hover text-center table-set">
							<thead>
								<tr>
									<th width="6%"><div class="font-size-12 line-height-18">操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th width="10%"><div class="font-size-12 line-height-18">ATA章节号
										</div>
										<div class="font-size-9 line-height-18">ATA</div></th>
									<th width="24%"><div class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div></th>
									<th width="10%"><div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div></th>
									<th width="12%"><div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div></th>
									<th width="12%"><div class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">S/N</div></th>
									<th width="12%"><div class="font-size-12 line-height-18">内部识别码</div>
										<div class="font-size-9 line-height-18">Identification Code</div></th>
									<th width="12%"><div class="font-size-12 line-height-18">厂家件号</div>
										<div class="font-size-9 line-height-18">MP/N</div></th>
									<th width="12%"><div class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">B/N</div></th>
									<th width="6%"><div class="font-size-12 line-height-18">数量</div>
										<div class="font-size-9 line-height-18">Quantity</div></th>
									<th width="8%"><div class="font-size-12 line-height-18">位置</div>
										<div class="font-size-9 line-height-18">Location</div></th>
								</tr>
							</thead>
							<tbody id="chooseList">
							</tbody>
						</table>
						<div class="modal-footer">
							<button type="button" onclick="ListChangeParent()"
								class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button"
								class="btn btn-primary padding-top-1 padding-bottom-1"
								data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-------维护子部件关联框 End-------->


		<!-------确认作废消息框 Start-------->
		<div class="modal fade" id="scrapModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria- hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="scrapModalBody">
					</div>
					<div class="modal-footer">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							data-dismiss="modal" onclick="delete_self()">
							<div class="font-size-12">作废部件</div>
							<div class="font-size-9">Invalid</div>
							<input type="hidden" id="delete_self_hid" />
						</button>
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							data-dismiss="modal" onclick="delete_cascade()">
							<div class="font-size-12">作废部件及其子部件</div>
							<div class="font-size-9">Invalid</div>
							<input type="hidden" id="delete_cascade_hid" />
						</button>
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							data-dismiss="modal">
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-------确认作废消息框 End-------->

		<!-------序列号已存在消息框 Start-------->
		<div class="modal fade" id="xlhExistModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria- hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="xlhExistModalBody">
					</div>
					<div class="modal-footer">
						<button type="button"
							class="btn btn-danger padding-top-1 padding-bottom-1"
							data-dismiss="modal" onclick="insertOrUpdate()">
							<div class="font-size-12">确认</div>
							<div class="font-size-9">Confirm</div>
							<input type="hidden" id="warn_ensure_hid" />
						</button>
						<button  type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							data-dismiss="modal">
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-------序列号已存在消息框end-------->
		
		<!-------装机清单详细 Start-------->
		<div class="modal fade" id="mountComponentModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg" style="width: 1350px;">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="mountComponentModalBody">
						<div class="panel panel-primary">
							<div class="panel-heading  padding-top-3 padding-bottom-1">
								<div class="font-size-16 line-height-18">装机清单详细信息</div>
								<div class="font-size-9 ">LoadingList Detail</div>
							</div>
							<input type="hidden" id="current_id_modal" />
							<div class="panel-body padding-top-0 padding-bottom-0">
				            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
									<div class="col-lg-12 margin-top-10">
										<div class="tab-pane fade in active" id="planeLoadingList">
								         <div id="treeView">
								          <div class="row padding-left-0 padding-right-0">
											<div class="col-sm-12 padding-left-0 padding-right-0">
											<form id="componentForm_modal">
											<div class="row">
											<div class="col-sm-12">
												<div class="panel panel-default">
												  <div class="panel-heading col-xs-12 margin-bottom-10 padding-right-0">
													    <h3 class="panel-title" >
													    部件基本信息 Base info
													    </h3>
												  </div>
												  <div class="panel-body">
												  	 
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">机型</div>
																<div class="font-size-9 line-height-18">Model</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="fjjx_zs" name="fjjx_zs" disabled="disabled">
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">飞机注册号</div>
																<div class="font-size-9 line-height-18">A/C REG</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="fjzch_zs" name="fjzch_zs" disabled="disabled">
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18"><span style="color: red">*</span>ATA章节号</div>
																<div class="font-size-9 line-height-18">ATA</div></label>
															<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<div class="input-group">
																	<input class="form-control" id="zjh_show_zs" name="zjh_show_zs" type="text" disabled="disabled">
																	<input id="zjh_zs" name="zjh_zs" type="hidden">
												                    <span class="input-group-btn">
																		<button id="main_chapter_btn_zs" class="btn btn-primary form-control" type="button" onclick="openChapterWinModal();">
																		<i class="icon-search"></i>
																		</button>
												                    </span>
												                </div>
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18"><span style="color: red">*</span>件号</div>
																<div class="font-size-9 line-height-18">P/N</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="jh_zs" name="jh_zs" maxlength="50">
															</div>
														</div>
														<div class="clearfix"></div>
														
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">厂家件号</div>
																<div class="font-size-9 line-height-18">MP/N</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="cjjh_zs" name="cjjh_zs" maxlength="50">
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">序列号</div>
																<div class="font-size-9 line-height-18">S/N</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-right-0">
																<input type="text" class="form-control"  id="xlh_zs" name="xlh_zs" maxlength="50" onkeyup="limitCount()">
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">英文名称</div>
																<div class="font-size-9 line-height-18">F.Name</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="ywms_zs" name="ywms_zs" maxlength="300">
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">中文名称</div>
																<div class="font-size-9 line-height-18">CH.Name</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="zwms_zs" name="zwms_zs" maxlength="100">
															</div>
														</div>
														<div class="clearfix"></div>
														
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">内部识别码</div>
																<div class="font-size-9 line-height-18">I/N</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="nbsbm_zs" name="nbsbm_zs" maxlength="50">
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">位置</div>
																<div class="font-size-9 line-height-18">Location</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<select class='form-control' id='wz_zs' name="wz_zs">
																	<option value="0">机身 Fuselage</option>
																	<option value="1">1#左发 L/Engine</option>
																	<option value="2">2#右发 R/Engine</option>
																	<option value="5">外吊挂 E/S</option>
																	<option value="4">搜索灯 Search</option>
																	<option value="3">绞车 Winch</option>
																</select>
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">许可证号</div>
																<div class="font-size-9 line-height-18">Licence</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="xkzh_zs" name="xkzh_zs" maxlength="50">
															</div>
														</div>
													 	<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">适航证号</div>
																<div class="font-size-9 line-height-18">Certificate</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="shzh_zs" name="shzh_zs" maxlength="50">
															</div>
														</div>
														<div class="clearfix"></div>
														
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">批次号</div>
																<div class="font-size-9 line-height-18">B/N</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="pch_zs" name="pch_zs" maxlength="50">
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">数量</div>
																<div class="font-size-9 line-height-18">Num</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="zjsl_zs" name="zjsl_zs" maxlength="6">
																<input type="hidden" id="zjsl_max">
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-6 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-2 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18"><span style="color: red">*</span>父节点</div>
																<div class="font-size-9 line-height-18">Parent</div></label>
															<div class=" col-xs-8 col-sm-8 col-lg-10 padding-left-8 padding-right-0">
																<div class="input-group">
																	<input class="form-control" id="parent_show_zs" type="text" disabled="disabled">
																	<input id="parent_zs" name="parent_zs" type="hidden">
												                    <span class="input-group-btn">
																		<button id="main_parent_btn_zs" class="btn btn-primary form-control" type="button" onclick="openChooseParentModal();">
																		<i class="icon-search"></i>
																		</button>
												                    </span>
												                </div>
															</div>
														</div>
														<div class="clearfix"></div>
														
													 	<div class="col-xs-12 col-sm-12 col-lg-12  padding-left-0 padding-right-0 form-group">
															<label class="col-xs-4 col-sm-2 col-lg-1 text-right padding-left-0 padding-right-0">
																<div class="font-size-12 line-height-18">改装记录</div>
																<div class="font-size-9 line-height-18">Record</div>
															</label>
															<div class="col-xs-8 col-sm-10 col-lg-11 padding-left-8 padding-right-0">
																<textarea rows="1" name="bjgzjl_zs" class="form-control" id="bjgzjl_zs" maxlength="500"></textarea>
															</div>
														</div>
														<div class="col-xs-12 col-sm-12 col-lg-12  padding-left-0 padding-right-0">
															<label class="col-xs-4 col-sm-2 col-lg-1 text-right padding-left-0 padding-right-0">
																<div class="font-size-12 line-height-18">备注</div>
																<div class="font-size-9 line-height-18">Remark</div>
															</label>
															<div class="col-xs-8 col-sm-10 col-lg-11 padding-left-8 padding-right-0">
																<textarea rows="1" id="bz_zs" name="bz_zs" class="form-control" maxlength="300"></textarea>
															</div>
														</div>
												  </div>
												</div>
								          </div>
								          
								          <div class="col-sm-12">
												<div class="panel panel-default">
												  <div class="panel-heading">
												    <h3 class="panel-title">设置监控信息 Set up Monitor</h3>
												  </div>
												  <div class="panel-body padding-bottom-0">
												 
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">生产日期</div>
																<div class="font-size-9 line-height-18">Manufacture date</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																	<input class="form-control date-picker" name="scrq_zs" data-date-format="yyyy-mm-dd" type="text"  id="scrq_zs" maxlength="10"/>
																</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">安装日期</div>
																<div class="font-size-9 line-height-18">Install date</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input class="form-control date-picker" name="azrq_zs" data-date-format="yyyy-mm-dd" type="text"  id="azrq_zs" maxlength="10"/>
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">安装记录单号</div>
																<div class="font-size-9 line-height-18">Assembly Record</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="azjldh_zs" name="azjldh_zs" maxlength="50">
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">拆除日期</div>
																<div class="font-size-9 line-height-18">Remove date</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input class="form-control date-picker" name="ccrq_zs" data-date-format="yyyy-mm-dd" type="text"  id="ccrq_zs" maxlength="10"/>
															</div>
														</div>
														<div class="clearfix"></div>
														
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">拆除记录单号</div>
																<div class="font-size-9 line-height-18">Disassembly Record</div></label>
																<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="ccjldh_zs" name="ccjldh_zs" maxlength="50">
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group">
															<label class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
																<div class="font-size-12 line-height-18">控制类型</div>
																<div class="font-size-9 line-height-18">Control type</div>
															</label>
															<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<select class='form-control' id='kzlx_zs'  onchange="reValidateXlhZs()">
																	<option value="1">时控件 Time</option>
																	<option value="2">时寿件 Life</option>
																	<option value="3">非控制件 Non control</option>
																</select>
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group">
															<label class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
																<div class="font-size-12">履历卡类型</div>
																<div class="font-size-9">Career type</div>
															</label>
															<div
																class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<select class='form-control' id='llklx_zs'>
																	<option value="1">无履历卡 No Career</option>
																	<option value="2">原装履历卡 Original</option>
																	<option value="3">自制履历卡 Self-control</option>
																</select>
															</div>
														</div>
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group">
															<label  class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
																class="font-size-12 line-height-18">履历卡编号</div>
															<div class="font-size-9 line-height-18">Career No.</div></label>
															<div class=" col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
															<input class="form-control" name="llkbh_zs" type="text"  id="llkbh_zs" maxlength="50"/>
															</div>
														</div>
														<div class="clearfix"></div>
												
														<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 form-group">
															<label class="col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
																<div class="font-size-12">是否定检</div>
																<div class="font-size-9">Is P/I</div>
															</label>
															<div
																class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
																<select class='form-control' id='isDj_zs'  onchange="reValidateXlhZs()">
																	<option value="1">是 Yes</option>
																	<option value="0">否 No</option>
																</select>
															</div>
														</div>
												
												  </div>
												  
												  </div>
												</div>
								          </div>
								          </div>
								          </form>
								        </div>
								        </div>
			        				</div>
									
									</div>
				                	<div class="text-right margin-top-10 margin-bottom-10">
										<button type="button" onclick="insertOrUpdateModal()" id="main_insertOrUpdate_btn_zs"
											class="btn btn-primary padding-top-1 padding-bottom-1">
											<div class="font-size-12">确定</div>
											<div class="font-size-9">Confirm</div>
										</button>
										<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
											<div class="font-size-12">关闭</div>
											<div class="font-size-9">Close</div>
										</button>
					                </div>
						     		<div class="clearfix"></div>
							 	 </div>
							 </div> 
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
		<!-------装机清单详细 End-------->
		
		<!-------选择父节点 Start-------->
		<div class="modal fade" id="chooseParentModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria- hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="chooseParentModalBody">
						<!-- 装机清单树-->
						<div class="col-sm-12"  style="padding-right: 0px;">
							<div class="panel panel-default">
								<div class="panel-heading">
								<h3 class="panel-title">选择父节点 Choose Parent</h3>
								</div>
								<div class="panel-body">
									<div class="col-sm-12  padding-left-0 padding-right-0 margin-bottom-10 ">
										<div class="input-group">
											<input class="form-control " id="keyword_search_modal" placeholder="件号/序列号/中英文名称/ATA章节号/内部识别码/厂家件号" type="text"> <span class="input-group-btn">
												<button class="btn btn-default  padding-top-1 padding-bottom-1 pull-right" type="button" onclick="refreshModalTree('', true)">
													<div class="font-size-12">搜索</div>
													<div class="font-size-9">Search</div>
												</button>
											</span>
										</div>
									</div>
									<div class="clearfix"></div>
									<div class="zTreeDemoBackground">
										<ul id="loadingList_tree_modal" class="ztree" style=" height:430px; overflow: auto; "></ul>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							data-dismiss="modal" onclick="chooseParentModal()">
							<div class="font-size-12">确认</div>
							<div class="font-size-9">Confirm</div>
							<input type="hidden" id="warn_ensure_hid" />
						</button>
						<button  type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							data-dismiss="modal">
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-------选择父节点end-------->
		

		<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
	    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
		<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
		<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
		<script type="text/javascript"
			src="${ctx}/static/js/thjw/productionplan/loadingList/loadingList_planeLoadingList.js"></script>
		<script type="text/javascript"
			src="${ctx}/static/js/thjw/productionplan/loadingList/loadingList_planeLoadingList_tableView.js"></script>
		<script type="text/javascript"
			src="${ctx}/static/js/thjw/productionplan/loadingList/loadingList_fixedMonitor.js"></script>
		<script type="text/javascript"
			src="${ctx}/static/js/thjw/productionplan/loadingList/loadingList_timeMonitor.js"></script>
		<script type="text/javascript"
			src="${ctx}/static/js/jqueryactual/jquery.actual.min.js"></script>
		
</body>
</html>