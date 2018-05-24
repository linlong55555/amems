<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript">
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
</script>
					<!-----标签内容start---->
					<div class="tab-pane fade in active" style="padding-left:15px;padding-right:15px;"  id="senda">
					
				<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0" >
		
					<div class=" pull-left padding-left-0 padding-right-0" style="width:250px;" >
						<input placeholder="送修单号/申请人/件号/GRN/英文名称/中文名称/序列号" id="sendakeyword_search" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button id="RepairOutStock" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchsenda();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                         <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search1();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon1"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!------------搜索框end------->
						
						
				
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-0 margin-top-10  display-none border-cccccc" id="divSearch1">
				
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
						<div
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12">申请日期</div>
							<div class="font-size-9">Application Date</div></div>
						<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " name="date-range-picker" id="flightDate_search1" readonly />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 margin-bottom-10 padding-left-0 padding-right-0">
						<div class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">仓库</div>
							<div class="font-size-9">Store</div></div>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='ckh1' name="ckh1" >
							
								
								</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10  ">
						<div class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></div>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode1" class="form-control " name="dprtcode1" onchange="changeSelectedPlane()">
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
							onclick="searchresetsenda();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
		
					</div>
					
				</div>
				<div  class="col-xs-12 text-center margin-top-10 margin-bottom-5 padding-left-0 padding-right-0 padding-top-0 "  style="overflow-x:scroll">
			          	<table id="sxck" class="table table-thin table-bordered table-set" style="min-width:1000px!important;">
							<thead>
								<tr>
									<th class=" colwidth-5"  style="vertical-align: middle;">
										<div class="font-size-12 line-height-18">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class=" colwidth-5" style="vertical-align: middle;"><div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div></th>
									<th class=" sorting colwidth-10" style="vertical-align: middle;" name="column_sqdh" onclick="orderBysenda('sqdh',this)"><div class="important">送修单号</div>
									<div class="font-size-9 line-height-18">Repair No.</div></th>
									<th class="colwidth-10" style="vertical-align: middle;"><div class="font-size-12 line-height-18">合同号</div>
									<div class="font-size-9 line-height-18">Order No.</div></th>
									<th class="colwidth-10" style="vertical-align: middle;"><div class="font-size-12 line-height-18"><div class="important">申请人</div></div>
									<div class="font-size-9 line-height-18">Applicant</div></th>
									<th class="colwidth-10" style="vertical-align: middle;"><div class="font-size-12 line-height-18">申请日期</div>
									<div class="font-size-9 line-height-18">Application Date</div></th>
									<th class="colwidth-13" style="vertical-align: middle;"><div class="font-size-12 line-height-18"><div class="important">件号</div></div>
									<div class="font-size-9 line-height-18">P/N</div></th>
									<th class="colwidth-13" style="vertical-align: middle;"><div class="font-size-12 line-height-18"><div class="important">GRN</div></div>
									<div class="font-size-9 line-height-18">GRN</div></th>
								    <th  class="colwidth-20" style="vertical-align: middle;"><div class="font-size-12 line-height-18"><div class="important">英文名称</div></div>
									<div class="font-size-9 line-height-18">F.Name</div></th>
									<th class="colwidth-15" style="vertical-align: middle;"><div class="font-size-12 line-height-18"><div class="important">中文名称</div></div>
									<div class="font-size-9 line-height-18">CH.Name</div></th>
									<th class="colwidth-15" style="vertical-align: middle;"><div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-18">MP/N</div></th>
									<th class="colwidth-13" style="vertical-align: middle;"><div class="font-size-12 line-height-18"><div class="important">序列号</div></div>
									<div class="font-size-9 line-height-18">S/N</div></th>
									<th class="colwidth-15" style="vertical-align: middle;"><div class="font-size-12 line-height-18">适航证号</div>
									<div class="font-size-9 line-height-18">Certificate</div></th>
									<th class="colwidth-10" style="vertical-align: middle;"><div class="font-size-12 line-height-18">仓库</div>
									<div class="font-size-9 line-height-18">Storage location</div></th>
									<th class="colwidth-10" style="vertical-align: middle;"><div class="font-size-12 line-height-18">库位</div>
									<div class="font-size-9 line-height-18">Storage location</div></th>
									<th class="colwidth-10" style="vertical-align: middle;"><div class="font-size-12 line-height-18">数量</div>
									<div class="font-size-9 line-height-18">Num</div></th>
									<th class="colwidth-15" style="vertical-align: middle;"><div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div></th>
								</tr>
							</thead>
									<tbody id="sendalist">
									</tbody>
						</table>
						</div>
							<div class=" col-xs-12  text-center" id="sendapagination">
								
						</div>
						<div class="panel-heading padding-left-16 padding-top-3  col-xs-12  " style="border-bottom: 1px solid #ccc;">
					<div class=" pull-left margin-right-10" >
							<div class="font-size-16 line-height-18">基本信息</div>
							<div class="font-size-9 ">Basic Info</div>
						</div>	
					 	<div class="pull-left ">
							<button id="btnGoAdd" style="display:none" class="btn btn-primary " onclick="javascript:window.history.go(-1)">
								<i class="icon-plus cursor-pointer" ></i>
							</button>
			          	</div>
					</div>	
						<div class="col-xs-12 line4 widget-body clearfix padding-top-10 margin-top-10 ">
							<div class="clearfix"></div>
								<form id="sendaform">
								
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">出库人</div>
								<div class="font-size-9 line-height-18"> Operator</div>
							</label>
										<div class=" col-xs-8 padding-left-8 padding-right-0 input-group">
							<div class='input-group'>
					<input type="text" class="form-control" value="${user.username} ${user.realname}" name="username1" id="username1"  readonly />
								<input type="hidden" name="sendamckmid" id="sendackbmid" value="${user.bmdm}"/>
								<input class="form-control " type="hidden" value="${user.id}" type="text" id="userid1" name="userid1" readonly="readonly"/>
							
								<span class='input-group-btn'>
								  <button type="button" onclick='selectUser1()' class='btn btn-primary'><i class='icon-search'></i>
								</button>
								</span>
						    </div>
					</div>
							
						</div>
						
							<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " >
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								   <div class="font-size-12 line-height-18">出库日期</div>
									<div class="font-size-9 line-height-18">Outstock Date</div>
								</label>
								 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group" >
										<input class="form-control date-picker" id="ckrq1"  name="ckrq1" data-date-format="yyyy-mm-dd" type="text" />
								</div>
							</div>
					
							<div class=" col-lg-6 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " >
							<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
							   <div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							 <div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group" >
								<input class="form-control "  type="text" id="bz1" name="bz1" placeholder='长度最大为300'   maxlength="300"/>
							</div>
							</div>
						</form>
							<div class="text-right margin-top-10 margin-bottom-10">
									  <button onclick="putoutstorage1()" permissioncode='aerialmaterial:outstock:main:02' class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10 checkPermission" ><div
										class="font-size-12">出库</div>
									<div class="font-size-9">Outstock </div></button>
				                </div>
					</div>
			          	
				
					</div>	
					
							
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/outstock/materialrepair_main.js"></script>
