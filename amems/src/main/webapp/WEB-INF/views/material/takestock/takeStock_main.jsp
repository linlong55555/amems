<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>盘点</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body >
	<input type="hidden" id="userId" value="${user.id}" />
	<input type="hidden" id="username" value="${erayFns:escapeStr(user.displayName)}" />
	<input type="hidden" id="adjustHeight" value="270">
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>		
			<div class="panel-body padding-bottom-0">
				<form id="form">
					
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 row-height">
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">仓库</div>
								<div class="font-size-9 line-height-18">Warehouse</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='ckid' onchange="changeStore()" >
							    </select>
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">盘点单</div>
								<div class="font-size-9 line-height-18">Take Form</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='takeStockSelect' onchange="changeTakeStock()" ></select>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="dprtcode_search" name="dprtcode_search" class="form-control " onchange="initStoreSelect()">
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="clearfix"></div>
					</div>
				</form>

				<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
					<div class="font-size-16 line-height-18">盘点信息</div>
					<div class="font-size-9 ">Basic Information</div>
				</div>
				
				<div class="col-xs-12 col-lg-2 col-sm-12 padding-left-0 border-r" >
			        <span id="editTakeStockButton" class="pull-right font-size-12"><i title='修改 Edit' class='icon-edit color-blue cursor-pointer' style="position: absolute; right: 6px; height: 15px; display: block; width: 20px; top: 10px;" onClick="openEditTakeStockWin()"></i></span>
					<ul class="pandian">
                      	<li class="">盘点单号：<span id="vpddh"></span></li>
                      	<li class="">盘点人：<span id="vpdrname"></span><input type="hidden" id="vpdrid" ><input type="hidden" id="vpdbmid" ></li>
                      	<li class="">盘点日期：<span id="vksrq"></span><input type="hidden" id="vpdzt" ></li>
                      	<li class="">仓库：<span id="vckname"></span></li>
                      	<li class="">备注：<span id="vbz" title=""></span><input type="hidden" id="vbzValue" ></li>
                    </ul>
						
					<div class="text-center" style="border-top: 1px solid #ccc;">
						<span class="font-size-14">库位/部件</span>
						<span id="editTakeScopeButton" class="pull-right font-size-12">
							<ul style="list-style-type:none">
								<li class="dropdown user">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
										<i title='新增 Add' class='icon-plus color-blue cursor-pointer' ></i>
									</a>
									<ul class="dropdown-menu pull-right" style='text-align:left;vertical-align:middle;'>
										<li >
											<a href="#" data-toggle="modal" onclick="openTakeScopeWin(1)"> 
												新增库位Add Storage
											</a>
										</li>
										<li>
											<a href="#" data-toggle="modal" onclick="openTakeScopeWin(2)"> 
												新增部件Add Parts
											</a>
										</li>
									</ul>
								</li>
							</ul>
						</span>
					</div>
					<div class="list-group table-h"  id="takeScope"  style="overflow: auto;max-height:100px;min-height:50px;">
					</div>
				</div>
				
				<div class="col-xs-12 col-lg-10 col-sm-12">
						
					<div class="pull-left padding-left-0 padding-right-0 margin-bottom-10" >   
                   		<label style=' font-weight:normal'><input type="radio" style=" vertical-align: middle; margin-top: -3px;" name="viewType" value="1" checked=true onclick="searchRevision()"/>
                   		全部</label>
                   		&nbsp;
                   		&nbsp;
                   		<label style=' font-weight:normal'><input type="radio" style=" vertical-align: middle; margin-top: -3px;" name="viewType" value="2"  onclick="searchRevision()" />
                   		盈亏</label>
                   		&nbsp;
                   		&nbsp;
                   		<label style=' font-weight:normal'><input type="radio" style=" vertical-align: middle; margin-top: -3px;" name="viewType" value="3" onclick="searchRevision()" />
                   		盈</label>
                   		&nbsp;
                   		&nbsp;
                   		<label style=' font-weight:normal'><input type="radio" style=" vertical-align: middle; margin-top: -3px;" name="viewType" value="4" onclick="searchRevision()" />
                   		亏</label>
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
		                        <button id="cywh" type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight checkPermission" permissioncode="aerialmaterial:takestock:main:04" onclick="openDiffWin()">
									<div class="font-size-12">差异维护</div>
									<div class="font-size-9">Diff</div>
						   		</button>
						   		<button id="submit" type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight checkPermission" permissioncode="aerialmaterial:takestock:main:05" onclick="submitTakeStock()">
									<div class="font-size-12">提交</div>
									<div class="font-size-9">Submit</div>
						   		</button>
		                    </div> 
						</div>
						<!-- 搜索框end -->
					
					</div>
					
					<div class="col-xs-12 col-lg-12 col-sm-12 table-m  padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x: scroll;" >
						<table id="take_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1400px;">
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
											<div class="font-size-12 line-height-18">序列号</div>
											<div class="font-size-9 line-height-18">S/N</div>
										</div>
									</th>
									<th class="colwidth-20">
										<div class="important">
											<div class="font-size-12 line-height-18">批次号</div>
											<div class="font-size-9 line-height-18">B/N</div>
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
									<th class="colwidth-5">
										<div class="font-size-12 line-height-18">差异</div>
										<div class="font-size-9 line-height-18">Differ</div>
									</th>
								</tr>
							</thead>
							<tbody id="takeStockDetailList" ></tbody>
						</table>
					</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="takeStockDetailPagination"></div>
				</div>
			</div>
		</div>
	</div>
	
	<!-------盘点单对话框 Start-------->
	
	<div class="modal fade" id="alertModalForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalFormBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">盘点单</div>
							<div class="font-size-9 ">Take Stock</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12">
				            	<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
									<span class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">仓库</div>
										<div class="font-size-9">Warehouse</div>
									</span>
									<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
										<input type="hidden" class="form-control " id="eckid" />
										<input type="text" class="form-control " id="eckname" readonly/>
									</div>
								</div> 
							</div>
							
							<div class="col-xs-12 col-sm-12">
								<span class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>盘点人</div>
									<div class="font-size-9">taker</div>
								</span>
								<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8  padding-right-0">
								  <div class="pull-left col-lg-10 padding-left-0 padding-right-0">
									<input type="text" id="epdrname" class="form-control " value="${erayFns:escapeStr(user.displayName)}"  readonly>
									<input type="hidden" id="epdrid" >
									<input type="hidden" id="epdbmid" >
									<input type="hidden" id="epdid" />
									</div>
									<a href="#" onclick="openUserModalWin()" data-toggle="modal" class="btn btn-primary padding-top-4 padding-bottom-4 pull-right" style="float: left;">
										<i class="icon-search cursor-pointer"></i>
									</a>
								</div>
						 	 </div>
						 	 
						 	 <div class="col-lg-12 col-xs-12">
				            	<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
									<span class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12"><span style="color: red">*</span>盘点日期</div>
										<div class="font-size-9">Take Date</div>
									</span>
									<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
										<input class="form-control date-picker" id="eksrq" readonly data-date-format="yyyy-mm-dd" type="text" />
									</div>
								</div> 
							</div>
							<div class="col-lg-12 col-xs-12">
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
									<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</label>
									<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
										<textarea class="form-control" id="ebz" maxlength="300"></textarea>
									</div>
								</div>
							</div>
							
						 	 <div class="clearfix"></div>
						 	 <div class="text-center margin-top-10 padding-buttom-10">
				     			<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="saveTakeStock()">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">save</div>
		                   		</button>
		                   		&nbsp;&nbsp;&nbsp;&nbsp;
                    				<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>&nbsp;&nbsp;&nbsp;&nbsp;
                   			 </div>
                   			 <br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	
	<!-------盘点单对话框 End-------->
	
	<!-------库位对话框 Start-------->
	
	<div class="modal fade" id="alertStorageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertStorageModalBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">库位</div>
							<div class="font-size-9 ">Storage</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12">
				            	<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
									<span class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">库位</div>
										<div class="font-size-9">Storage</div>
									</span>
									<div id="StorageDiv" class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
									</div>
								</div> 
							</div>
							
						 	 <div class="clearfix"></div>
						 	 <div class="text-center margin-top-10 padding-buttom-10">
				     			<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="saveTakeScope(1,this)" data-dismiss="modal">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">save</div>
		                   		</button>
		                   		&nbsp;&nbsp;&nbsp;&nbsp;
                    				<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>&nbsp;&nbsp;&nbsp;&nbsp;
                   			 </div>
                   			 <br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	
	<!-------库位对话框 End-------->
	
		<!-------盘点差异对话框 Start-------->
	
	<div class="modal fade" id="alertDiffModalForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:70%;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertDiffModalFormBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">差异维护</div>
							<div class="font-size-9 ">Diff</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 margin-top-10">
								<div class=" col-lg-6 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10" >
									<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
				                   		库存
										<input type="radio" name="DiffType" value="1" checked=true onclick="checkDiff()"/>
				                   		&nbsp;
				                   		&nbsp;
				                   		新增
				                   		<input type="radio" name="DiffType" value="2" onclick="checkDiff()" />
				                   		&nbsp;
				                   		&nbsp;
									</div>
								</div>
								
								<div class="clearfix"></div>
					
								<div class=" col-lg-6 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control" id="search_value" placeholder='件号/英文名称/序列号/批次号/适航证号' data-provide="typeahead" onkeyup='checkValue(this)' />
									</div>
									<div style="padding-left:0!important;" class="col-xs-1 pull-left">   
										<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchMaterial()">
											<div class="font-size-12">查询</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div>
								</div>
						
								<div id="kc_sl" class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>盘点数量</div>
										<div class="font-size-9 line-height-18">Take Count</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="hidden" id="kcid" />
										<input type="hidden" id="kcgljb" />
										<input type="text" class="form-control" id="kcpdsl" placeholder='' onkeyup='clearFromDiff(this)' maxlength="10"/>
									</div>
								</div>
								<div class="clearfix"></div>
							</div>	
							
							<div id="kc_form" class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</label>
									<div id="kcbjh" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
					
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
									</label>
									<div id="kczwms" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div>
									</label>
									<div id="kcywms" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
						
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Warehouse No.</div>
									</label>
									<div id="kcck" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">管理级别</div>
										<div class="font-size-9 line-height-18">Level</div>
									</label>
									<div id="kcgljbName" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">库存数量</div>
										<div class="font-size-9 line-height-18">Stock Count</div>
									</label>
									<div id="kckcsl" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage No.</div>
									</label>
									<div id="kckwh" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">序列号/批次号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</label>
									<div id="kcsnpch" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">航材来源</div>
										<div class="font-size-9 line-height-18">Material Source</div>
									</label>
									<div id="kchcly" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">许可证号</div>
										<div class="font-size-9 line-height-18">License No.</div>
									</label>
									<div id="kcxkzh" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">适航证号</div>
										<div class="font-size-9 line-height-18">Airworthiness No.</div>
									</label>
									<div id="kcshzh" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">适航证位置</div>
										<div class="font-size-9 line-height-18">AC Position</div>
									</label>
									<div id="kcshzwz" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-Life</div>
									</label>
									<div id="kchjsm" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">TSN</div>
										<div class="font-size-9 line-height-18">TSN</div>
									</label>
									<div id="kctsn" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">TSO</div>
										<div class="font-size-9 line-height-18">TSO</div>
									</label>
									<div id="kctso" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">存放要求</div>
										<div class="font-size-9 line-height-18">Storage Must</div>
									</label>
									<div id="kccfyq" class="col-lg-11  padding-left-8 padding-right-0 form-group">
										
									</div>
								</div>
								
							</div>
							
							<div id="hc_form" class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</label>
									<input type="hidden" id="hcbjid" />
									<input type="hidden" id="hcgljb" />
									<div id="hcbjh" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
					
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
									</label>
									<div id="hczwms" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div>
									</label>
									<div id="hcywms" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
						
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Warehouse No.</div>
									</label>
									<div id="hcck" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">管理级别</div>
										<div class="font-size-9 line-height-18">Level</div>
									</label>
									<div id="hcgljbName" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>盘点数量</div>
										<div class="font-size-9 line-height-18">Take Count</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control" id="hcpdsl" placeholder='' onkeyup='clearFromDiff(this)' maxlength="10"/>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage No.</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select class="form-control" id="hckw"></select>
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">序列号/批次号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control" id="hcsnpch" maxlength="16"/>
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">航材来源</div>
										<div class="font-size-9 line-height-18">Material Source</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select id="hchcly"  name="hchcly" class="form-control"  >
									     </select>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">许可证号</div>
										<div class="font-size-9 line-height-18">License No.</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control" id="hcxkzh" maxlength="16"/>
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">适航证号</div>
										<div class="font-size-9 line-height-18">Airworthiness No.</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" id="hcshzh" class="form-control " maxlength="16" />
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">适航证位置</div>
										<div class="font-size-9 line-height-18">AC Position</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" id="hcshzwz" class="form-control " maxlength="30"/>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-Life</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" placeholder="请选择日期" id="hchjsm" class='form-control datepicker'  data-date-format="yyyy-mm-dd"  />
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">TSN</div>
										<div class="font-size-9 line-height-18">TSN</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" id="hctsn" class="form-control " onkeyup='clearNoNumber(this)' maxlength="16" />
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">TSO</div>
										<div class="font-size-9 line-height-18">TSO</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" id="hctso" class="form-control " onkeyup='clearNoNumber(this)' maxlength="16" />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">存放要求</div>
										<div class="font-size-9 line-height-18">Storage Must</div>
									</label>
									<div class="col-lg-11  padding-left-8 padding-right-0 form-group">
										<textarea class="form-control" id="hccfyq" maxlength="150" placeholder="最大长度不超过150"></textarea>
									</div>
								</div>
								
							</div>
							
						 	 <div class="clearfix"></div>
						 	 <div class="text-right margin-top-10 margin-right-10">
				                <button class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode="aerialmaterial:takestock:main:03" onclick="javascript:saveTakeStockDetail(1)">
				                   	<div class="font-size-12">保存/下一条</div>
									<div class="font-size-9">Save/Next</div>
								</button>
								<button class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode="aerialmaterial:takestock:main:03" onclick="javascript:saveTakeStockDetail(2)">
				                   	<div class="font-size-12">保存/关闭</div>
									<div class="font-size-9">Save/Close</div>
								</button>
				              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
				              		<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
				           	</div>
                   			 <br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	
	<!-------盘点差异对话框 End-------->
	
	<!-------盘点差异对话框(回显)库存 Start-------->
	
	<div class="modal fade" id="alertVDiffKCModalForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:70%;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertVDiffKCModalFormBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">差异维护</div>
							<div class="font-size-9 ">Diff</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 margin-top-10">
								
								<div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>盘点数量</div>
										<div class="font-size-9 line-height-18">Take Count</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="hidden" id="vkcid" />
										<input type="hidden" id="vkcgljb" />
										<input type="text" class="form-control" id="vkcpdsl" placeholder='' onkeyup='clearStockDiffNum(this)' maxlength="10"/>
									</div>
								</div>
								<div class="clearfix"></div>
							</div>	
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</label>
									<div id="vkcbjh" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
					
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
									</label>
									<div id="vkczwms" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div>
									</label>
									<div id="vkcywms" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
						
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Warehouse No.</div>
									</label>
									<div id="vkcck" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">管理级别</div>
										<div class="font-size-9 line-height-18">Level</div>
									</label>
									<div id="vkcgljbName" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">库存数量</div>
										<div class="font-size-9 line-height-18">Stock Count</div>
									</label>
									<div id="vkckcsl" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage No.</div>
									</label>
									<div id="vkckwh" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">序列号/批次号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</label>
									<div id="vkcsnpch" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">航材来源</div>
										<div class="font-size-9 line-height-18">Material Source</div>
									</label>
									<div id="vkchcly" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">许可证号</div>
										<div class="font-size-9 line-height-18">License No.</div>
									</label>
									<div id="vkcxkzh" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">适航证号</div>
										<div class="font-size-9 line-height-18">Airworthiness No.</div>
									</label>
									<div id="vkcshzh" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">适航证位置</div>
										<div class="font-size-9 line-height-18">AC Position</div>
									</label>
									<div id="vkcshzwz" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-Life</div>
									</label>
									<div id="vkchjsm" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">TSN</div>
										<div class="font-size-9 line-height-18">TSN</div>
									</label>
									<div id="vkctsn" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">TSO</div>
										<div class="font-size-9 line-height-18">TSO</div>
									</label>
									<div id="vkctso" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">存放要求</div>
										<div class="font-size-9 line-height-18">Storage Must</div>
									</label>
									<div id="vkccfyq" class="col-lg-11  padding-left-8 padding-right-0 form-group">
										
									</div>
								</div>
								
							</div>
							
						 	 <div class="clearfix"></div>
						 	 <div class="text-right margin-top-10 margin-right-10">
				                <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:editTakeStockDetail(1)">
				                   	<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
				              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
				              		<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
				           	</div>
                   			 <br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	
	<!-------盘点差异对话框(回显)库存 End-------->
	
	<!-------盘点差异对话框(回显)航材begin-------->
		<div class="modal fade" id="alertVDiffHCModalForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:70%;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertVDiffHCModalFormBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">差异维护</div>
							<div class="font-size-9 ">Diff</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >

							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 margin-top-10">
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</label>
									<input type="hidden" id="pdcyid" />
									<input type="hidden" id="kcllid" />
									<input type="hidden" id="vhcgljb" />
									<div id="vhcbjh" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
					
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
									</label>
									<div id="vhczwms" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div>
									</label>
									<div id="vhcywms" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
						
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Warehouse No.</div>
									</label>
									<div id="vhcck" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">管理级别</div>
										<div class="font-size-9 line-height-18">Level</div>
									</label>
									<div id="vhcgljbName" class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>盘点数量</div>
										<div class="font-size-9 line-height-18">Take Count</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control" id="vhcpdsl" placeholder='' onkeyup='clearMaterialDiffNum(this)' maxlength="10"/>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage No.</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select class="form-control" id="vhckw"></select>
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">序列号/批次号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control" id="vhcsnpch" maxlength="16"/>
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">航材来源</div>
										<div class="font-size-9 line-height-18">Material Source</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select id="vhchcly" name="vhchcly" class="form-control"  >
										
									     </select>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">许可证号</div>
										<div class="font-size-9 line-height-18">License No.</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control" id="vhcxkzh" maxlength="16"/>
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">适航证号</div>
										<div class="font-size-9 line-height-18">Airworthiness No.</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" id="vhcshzh" class="form-control " maxlength="16" />
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">适航证位置</div>
										<div class="font-size-9 line-height-18">AC Position</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" id="vhcshzwz" class="form-control " maxlength="30"/>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-Life</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" placeholder="请选择日期" id="vhchjsm" class='form-control datepicker'  data-date-format="yyyy-mm-dd"  />
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">TSN</div>
										<div class="font-size-9 line-height-18">TSN</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" id="vhctsn" class="form-control " onkeyup='clearNoNumber(this)' maxlength="16" />
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">TSO</div>
										<div class="font-size-9 line-height-18">TSO</div>
									</label>
									<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" id="vhctso" class="form-control " onkeyup='clearNoNumber(this)' maxlength="16" />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">存放要求</div>
										<div class="font-size-9 line-height-18">Storage Must</div>
									</label>
									<div class="col-lg-11  padding-left-8 padding-right-0 form-group">
										<textarea class="form-control" id="vhccfyq" maxlength="150" placeholder="最大长度不超过150"></textarea>
									</div>
								</div>
								
							</div>
							
						 	 <div class="clearfix"></div>
						 	 <div class="text-right margin-top-10 margin-right-10">
								<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:editTakeStockDetail(2)">
				                   	<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
				              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
				              		<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
				           	</div>
                   			 <br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	
	<!-------盘点差异对话框(回显)航材 End-------->
	
	
<script type="text/javascript" src="${ctx}/static/js/thjw/material/takestock/takeStock_main.js"></script>	
<%@ include file="/WEB-INF/views/open_win/user.jsp"%><!-------用户对话框 -------->
<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%><!-------航材对话框 -------->
</body>
</html>