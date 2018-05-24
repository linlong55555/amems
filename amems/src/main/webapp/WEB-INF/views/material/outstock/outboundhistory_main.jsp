<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

					<!-----标签内容start---->
					<div class="tab-pane fade in active" style="padding-left:15px;padding-right:15px;"  id="history">
					
						
							<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0" >
						<div class="pull-left ">
						<span class="pull-left  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">State</div>
						</span>
							<div id="fjzch_search_zt" class="padding-left-8 padding-right-0 pull-left">
									<select class='form-control' id='zthistory' name="zthistory" onchange="searchhistory1();">
											<option value="">显示全部</option>
											<option value="2" selected="selected">提交</option>
											<option value="11">撤销</option>
									</select>
							</div>
						</div>
					
					<div class=" pull-left padding-left-10 padding-right-0" style="width:250px;" >
						<input placeholder="出库单号" id="historykeyword_search" class="form-control " type="text">
					</div>
					
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button id="OutStockhistory" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchhistory1();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                         <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="searchhistory();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="iconhistory"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!------------搜索框end------->
				
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-top-10 margin-bottom-0  display-none border-cccccc" id="divSearchhistory">
				
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
						<div
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12">申请日期</div>
							<div class="font-size-9">Application Date</div></div>
						<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " name="date-range-picker" id="flightDate_searchhistory" readonly />
						</div>
					</div>
					
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
						<div
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12">出库类型</div>
							<div class="font-size-9">Outbound Type</div></div>
						<div id="fjzch_search_zt" class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select class='form-control' id='cklx' name="cklx" >
										<option value="">显示全部</option>
										<option value="1">领用出库</option>
										<option value="2">送修出库</option>
										<option value="3">归还出库</option>
										<option value="4">借出出库</option>
										<option value="5">报废出库</option>
										<option value="6">退货出库</option>
										<option value="0">其它</option>
								</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0">
						  <span class="pull-left col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
							</span>
						<div id="fjzch_search_history" class="form-group col-xs-8  padding-left-8 padding-right-0">
								<select class='form-control' id='fjzch_searchhistory' name="fjzch_searchhistory" onchange="changeSelectedPlane()">
									
								</select>
						</div>
					</div>
					
					
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10  ">
						<div class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></div>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode3" class="form-control " name="dprtcode3" onchange="changeSelectedPlane()">
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
					<div class="clearfix"></div>
					<div class="col-lg-2 pull-right text-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchresethistory();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
		
					</div>
					
				</div>
							<div  class="col-xs-12 text-center  margin-top-10 margin-bottom-5 padding-left-0 padding-right-0 padding-top-0"  style="overflow-x:scroll">
							<table id="ckls" class="table table-thin table-bordered table-set" style="min-width:1800px">
							<thead>
								<tr>
<!-- 									<th  class="fixed-column colwidth-5"  style="vertical-align: middle;"><div class="font-size-12 line-height-18">选择</div> -->
<!-- 									<div class="font-size-9 line-height-18">Choice</div></th> -->
									<th  class="fixed-column colwidth-5"  style="vertical-align: middle;"><div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div></th>
									<th  class="fixed-column colwidth-5"   style="vertical-align: middle;"><div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div></th>
									<th  name="column_ckdh" class="sorting colwidth-10" onclick="orderByhistory('ckdh',this)" style="vertical-align: middle;"><div class="important">出库单号</div>
									<div class="font-size-9 line-height-18">Outbound  No.</div></th>
									<th  name="column_cklx" class="sorting colwidth-10" onclick="orderByhistory('cklx',this)" style="vertical-align: middle;">出库类型</div>
									<div class="font-size-9 line-height-18">Outstock Type</th>
									<th  class="sorting colwidth-10" name="column_sqr" onclick="orderByhistory('sqr',this)" style="vertical-align: middle;">申请人</div>
									<div class="font-size-9 line-height-18">Applicant</th>
									<th  class="sorting colwidth-10" name="column_sqsj" onclick="orderByhistory('sqsj',this)" style="vertical-align: middle;">申请日期
									<div class="font-size-9 line-height-18">Application Date</div></th>
									<th  name="column_fjzch" class="sorting colwidth-10" onclick="orderByhistory('fjzch',this)" style="vertical-align: middle;">飞机注册号
									<div class="font-size-9 line-height-18">A/C REG</div></th>
									<th  class="sorting colwidth-10" name="column_jddxms" onclick="orderByhistory('jddxms',this)" style="vertical-align: middle;">借调对象
									<div class="font-size-9 line-height-18">Seconded Obj</div></th>
									<th  class="sorting colwidth-15" name="column_jdfzr" onclick="orderByhistory('jdfzr',this)" style="vertical-align: middle;">借调对象负责人
									<div class="font-size-9 line-height-18">Seconded Operator</div></th>
									<th  class="sorting colwidth-10" name="column_sgbs"  onclick="orderByhistory('sgbs',this)" style="vertical-align: middle;">标识
									<div class="font-size-9 line-height-18">Identify</div></th>
									<th  class="sorting colwidth-10" name="column_zt"  onclick="orderByhistory('zt',this)" style="vertical-align: middle;">状态
									<div class="font-size-9 line-height-18">State</div></th>
									<th  class="sorting colwidth-15" name="column_ckr"  onclick="orderByhistory('ckr',this)" style="vertical-align: middle;">出库人
									<div class="font-size-9 line-height-18">Outstock Operator</div></th>
									<th  class="sorting colwidth-10" name="column_cksj"  onclick="orderByhistory('cksj',this)" style="vertical-align: middle;">出库日期
									<div class="font-size-9 line-height-18">Outstock Date</div></th>
									<th  class="sorting colwidth-10" name="column_zdri"  onclick="orderByhistory('zdri',this)" style="vertical-align: middle;">制单人
									<div class="font-size-9 line-height-18">Creator</div></th>
									<th  class="sorting colwidth-15" name="column_zdsj"  onclick="orderByhistory('zdsj',this)" style="vertical-align: middle;">制单时间
									<div class="font-size-9 line-height-18">Create Time</div></th>
									<th  class="sorting colwidth-30" name="column_bz"  onclick="orderByhistory('bz',this)" style="vertical-align: middle;">备注
									<div class="font-size-9 line-height-18">Remark</div></th>
									<th  class="colwidth-10"><div class="font-size-12 line-height-18" style="vertical-align: middle;">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div></th>
								</tr>
							</thead>
									<tbody id="historylist">
									</tbody>
						</table>
						</div>
							<div class=" col-xs-12  text-center" id="historypagination">
								
							</div>
						
					<div class="panel-heading padding-left-16 padding-top-3  col-xs-12  margin-bottom-10" style="border-bottom: 1px solid #ccc;">
					<div class=" pull-left margin-right-10" >
							<div class="font-size-16 line-height-18">航材信息</div>
							<div class="font-size-9 ">Aircraft info</div>
						</div>	
					</div>	
						
						<div  class="col-xs-12 text-center  margin-top-10 margin-bottom-5 padding-left-0 padding-right-0 padding-top-0"  style="overflow-x:scroll">
							<table class="table table-thin table-bordered table-set" style="min-width:1100px">
							<thead>
								<tr>
									<th class="colwidth-5" style="vertical-align: middle;"><div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div></th>
									<th class="colwidth-5"><div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">部件号</div>
									<div class="font-size-9 line-height-18">P/N</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-18">F.Name</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-18">CH.Name</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-18">MP/N</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">航材类型</div>
									<div class="font-size-9 line-height-18">Airmaterial type</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">S/N</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">批次号</div>
									<div class="font-size-9 line-height-18">P/N</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">GRN</div>
									<div class="font-size-9 line-height-18">GRN</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">适航证号</div>
									<div class="font-size-9 line-height-18">Certificate</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">货架寿命</div>
									<div class="font-size-9 line-height-18">Shelf-Life</div></th>
									<th  class="colwidth-15"><div class="font-size-12 line-height-18">出库数量</div>
									<div class="font-size-9 line-height-18">Outstock Num</div></th>
									<th  class="colwidth-15"><div class="font-size-12 line-height-18">已退数量</div>
									<div class="font-size-9 line-height-18">Returned Num</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">单位</div>
										<div class="font-size-9 line-height-18">Unit</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Store</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage location</div></th>
								</tr>
							</thead>
									<tbody id="historyaviationlist">
									</tbody>
						</table>
						</div>
							
					</div>	
					
			<!-- start 修改文件夹 -->
	<div class="modal fade" id="updtaeuploading" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
							<div class="font-size-16 line-height-18">出库</div>
							<div class="font-size-9 ">Outstock</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
							<form id="form1">
			            	<div class="col-lg-12 col-xs-12">
							<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 form-group">
								<span class="pull-left col-lg-3 col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>退库数量</div>
									<div class="font-size-9">Return Num</div>
									</span>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								<input type="hidden" id="detailid" >
								<input type="hidden" id="ckid" >
								<input type="text" class="form-control " id="tksl" name="tksl"  onkeyup="clearNoNum1(this)" placeholder="长度最大为10"   maxlength="10"/>
								</div>
							</div> 
							
					     		<div class="text-center margin-top-10 padding-buttom-10 ">
					     		     	<button type="button" class="pull-right btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>&nbsp;&nbsp;
                     <button onclick="cancellingStock()" type="button" class="pull-right btn margin-right-10 btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" ><div
										class="font-size-12">退库</div>
									<div class="font-size-9">Return</div></button>&nbsp;&nbsp;
                
                    			 </div><br/>
						 	 </div>
						 	 </form>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/outstock/outboundhistory_main.js"></script>
