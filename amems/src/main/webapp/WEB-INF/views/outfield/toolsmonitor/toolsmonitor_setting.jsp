<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工具/设备监控设置</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var bjxlh = "${param.bjxlh}";
	var dprtcode = "${param.dprtcode}";
</script>

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
	});
</script>

</head>
<body class="page-header-fixed">

	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
				<div class="col-xs-8 col-lg-8 padding-left-0 padding-right-0  pull-right">
				
					<!-- 搜索框start -->
					<div class=" pull-right padding-left-0 padding-right-0">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input type="text" placeholder='编号/部件号/名称/规格/型号/ID' id="keyword_search" class="form-control ">
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
				</div>
				
				<div class="col-xs-12 col-sm-12 col-lg-12  triangle  padding-top-10  margin-top-10 display-none border-cccccc" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">类型</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
							<select class='form-control' id='hclx_search'>
								<option value="" selected="selected" >显示全部All</option>
								<c:forEach items="${materialTypeEnum}" var="item">
									<c:if test="${item.id == 2 || item.id == 3}">
										<option value="${item.id}" >${item.name}</option>
									</c:if>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">计量标识</div>
							<div class="font-size-9">Measurement Mark</div>
						</span>
						<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
							<select class='form-control' id='isJl_search'>
								<option value="" selected="selected" >显示全部All</option>
								<c:forEach items="${measurementMarkEnum}" var="item">
									<option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" name="dprtcode_search" class="form-control ">
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

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x: scroll;">
					<table class="table table-thin table-bordered table-set" style="min-width: 1800px;">
						<thead>
							<tr>
								<th class="colwidth-3">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('SN')" id="SN_order">
									<div class="important">
										<div class="font-size-12 line-height-18">编号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('BJH')" id="BJH_order">
									<div class="important">
										<div class="font-size-12 line-height-18">部件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</div>
								</th>
								<th class="colwidth-30" >
									<div class="important">
										<div class="font-size-12 line-height-18">名称</div>
										<div class="font-size-9 line-height-18">Name</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('GG')" id="GG_order">
									<div class="important">
										<div class="font-size-12 line-height-18">规格</div>
										<div class="font-size-9 line-height-18">Specifications</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('XH')" id="XH_order">
									<div class="important">
										<div class="font-size-12 line-height-18">型号</div>
										<div class="font-size-9 line-height-18">Model</div>
									</div>
								</th>
								<th class="colwidth-30">
									<div class="font-size-12 line-height-18">来源</div>
									<div class="font-size-9 line-height-18">Source</div>
								</th>
								<th class="colwidth-5" >
									<div class="font-size-12 line-height-18">位置</div>
									<div class="font-size-9 line-height-18">Location</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('ISJL')" id="ISJL_order">
									<div class="font-size-12 line-height-18">计量标识</div>
									<div class="font-size-9 line-height-18">Measurement Mark</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('HCLX')" id="HCLX_order">
									<div class="font-size-12 line-height-18">类型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('RKSJ')" id="RKSJ_order">
									<div class="font-size-12 line-height-18">入库日期</div>
									<div class="font-size-9 line-height-18">Indate</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('JYSCRQ')" id="JYSCRQ_order">
									<div class="font-size-12 line-height-18">最近检查日期</div>
									<div class="font-size-9 line-height-18">Last Date</div>
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
			<div id="selectTools"></div>	
			<div id="toolsDetailId" class="col-lg-12 col-md-12 padding-left-0 padding-right-0" >
				<div class="col-lg-3 padding-left-0 padding-right-0">
              		<div class="text-right margin-top-10 padding-buttom-10 ">
							<div class="col-lg-1 col-sm-1 col-xs-1 padding-left-8 padding-right-0">
								<input type="checkbox" id="measuringTool" onchange="setEffectorDisabled()" />
							</div>
							<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">计量工具</div>
								<div class="font-size-9 line-height-18">Tools</div>
							</label>
						<button id="add" type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10 checkPermission" permissioncode="outfield:toolsmonitor:setting:01" onclick="openToolsDetailAddWin()">
							<div class="font-size-12">新增</div>
							<div class="font-size-9">ADD</div>
                   		</button>&nbsp;&nbsp;&nbsp;&nbsp;
                  		<button id="save" type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10 checkPermission" permissioncode="outfield:toolsmonitor:setting:02" onclick="saveAll()" >
							<div class="font-size-12">保存</div>
							<div class="font-size-9">Save</div>
						</button>
                  	</div>
				</div>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x: auto;">
					<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width: 1400px;">
						<thead>
							<tr>
								<th class="colwidth-7" >
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-3" >
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="colwidth-20">
									<div class="font-size-12 line-height-18">编号</div>
									<div class="font-size-9 line-height-18">S/N</div>
								</th>
								<th class="colwidth-30">
									<div class="font-size-12 line-height-18">名称</div>
									<div class="font-size-9 line-height-18">Name</div>
								</th>
								<th class="colwidth-10">
									<div class="font-size-12 line-height-18">规格</div>
									<div class="font-size-9 line-height-18">Specifications</div>
								</th>
								<th class="colwidth-10">
									<div class="font-size-12 line-height-18">型号</div>
									<div class="font-size-9 line-height-18">Model</div>
								</th>
								<th class="colwidth-7">
									<div class="font-size-12 line-height-18">周期(天)</div>
									<div class="font-size-9 line-height-18">Cycle(Day)</div>
								</th>
								<th class="colwidth-10">
									<div class="font-size-12 line-height-18">最近检查日期</div>
									<div class="font-size-9 line-height-18">Last Date</div>
								</th>
								<th class="colwidth-9">
									<div class="font-size-12 line-height-18">下次检查日期</div>
									<div class="font-size-9 line-height-18">Next Date</div>
								</th>
								<th class="colwidth-30">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</th>
							</tr>
						</thead>
						<tbody id="toolsDetailList">
						
						</tbody>
						
					</table>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>

<!-------设备监控详情对话框 Start-------->
	
<div class="modal fade" id="alertToolsDetailForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:80%;height:50%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertToolsDetailFormBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">工具/设备监控详情</div>
						<div class="font-size-9 ">Tools Monitor Detail</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" style="overflow-y:auto;height:600px;">
						<form id="form">
						
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">基本信息</div>
								<div class="font-size-9 ">Basic Info</div>
							</div>
						
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						
								<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>编号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										<input type="hidden" class="form-control" id="detailId" />
										<input type="hidden" class="form-control" id="mainid" />
										<input type="hidden" class="form-control" id="bjbs" />
										<input type="text" class="form-control" id="bjxlh" name="bjxlh" maxlength="50"/>
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" id="zwms" name="zwms" class="form-control " maxlength="100" />
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" id="ywms" name="ywms" class="form-control " maxlength="100" />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">规格</div>
										<div class="font-size-9 line-height-18">Specifications</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" id="gg" name="gg" class='form-control' maxlength="16"  />
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">型号</div>
										<div class="font-size-9 line-height-18">Model</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" id="xh" name="xh" class="form-control " maxlength="16" />
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">计量工具</div>
										<div class="font-size-9 line-height-18">Tools</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										<input type="checkbox" id="isJl" checked="checked" />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>检验周期</div>
										<div class="font-size-9 line-height-18">Check Cycles</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" id="jyZq" name="jyZq" class="form-control " placeholder='' onkeyup='clearNoNum(this)' maxlength="4" />
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>最近检验日期</div>
										<div class="font-size-9 line-height-18">Last Date</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" id="jyScrq" name="jyScrq" class='form-control date-picker' data-date-format="yyyy-mm-dd"  onchange="loadNextDate()" />
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">下次检验日期</div>
										<div class="font-size-9 line-height-18">Next Date</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" id="jyXcrq" class='form-control' readonly />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
									<label class="col-lg-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-9  padding-left-8 padding-right-0 form-group">
										<textarea class="form-control" id="bz" maxlength="300" placeholder="最大长度不超过300"></textarea>
									</div>
								</div>
								
							</div>
						</form>
						
						<div class="clearfix"></div>
				
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit.jsp"%><!-- 加载附件信息 -->
						
					 	 <div class="text-right margin-top-10">
							<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:saveDetail();">
			                   	<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			              		<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
			           	</div>
			           	
                  		<div id="historyDetail" class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0 padding-top-10">
		                	<ul id="myTab" class="nav nav-tabs">
		                    	<li class="active"><a href="#inspectionHistors" data-toggle="tab" aria-expanded="true">检验历史 Inspection History</a></li>
		                    </ul>
		                    <div id="myTabContent" class="tab-content">
		                    	<div class="tab-pane fade active in" id="inspectionHistors">
									<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x:auto;">
										<table class="table table-thin table-bordered table-striped table-hover text-center table-set"  style="min-width: 800px;">
											<thead>
												<tr>
													<th class="colwidth-7">
														<div class="font-size-12 line-height-18">操作</div>
														<div class="font-size-9 line-height-18">Operation</div>
													</th>
													<th class="colwidth-3">
														<div class="font-size-12 line-height-18">序号</div>
														<div class="font-size-9 line-height-18">No.</div>
													</th>
													<th class="colwidth-13">
														<div class="font-size-12 line-height-18">校验日期</div>
														<div class="font-size-9 line-height-18">Inspection date</div>
													</th>
													<th class="colwidth-13">
														<div class="font-size-12 line-height-18">维护人</div>
														<div class="font-size-9 line-height-18">Maintainer</div>
													</th>
													<th class="colwidth-13">
														<div class="font-size-12 line-height-18">维护时间</div>
														<div class="font-size-9 line-height-18">Maintenance Time</div>
													</th>
													<th class="colwidth-30">
														<div class="font-size-12 line-height-18">备注</div>
														<div class="font-size-9 line-height-18">Remark</div>
													</th>
												</tr>
											</thead>
											<tbody id="historyDetailList"></tbody>
										</table>
									</div>
		                      	</div>
			                  </div>
			              </div>
			              <br/>
					 </div> 
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div>

<!-------设备监控详情对话框 End-------->
	
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<script type="text/javascript" src="${ctx}/static/js/thjw/outfield/toolsmonitor/toolsmonitor_setting.js"></script>
</body>
</html>