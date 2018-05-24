<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/config/store_open.js"></script>
<div class="modal fade in modal-new" id="storefoModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:80%'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
		                      <div class="font-size-14 " id="modalHeadChina"></div>
							  <div class="font-size-12" id="modalHeadEnglish"></div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
              	<div class="col-xs-12 margin-top-8 ">
              
              	<div class="input-group-border">
						<!-- start隐藏input -->
						<input type="hidden" class="form-control " id="storeId" name="storeId"  />
						<!-- end隐藏input -->
						<form id="form">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>仓库编号</div>
								<div class="font-size-9">Warehouse No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control " id="ckh" name="ckh" maxlength="50" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>仓库名称</div>
								<div class="font-size-9">Warehouse Name</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" id="ckmc" name="ckmc" class="form-control " maxlength="100" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">仓库类别</div>
								<div class="font-size-9">Warehouse Type</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<%
							     if(request.getAttribute("isStoreData").toString().equals("true")){//仓库数据管理%>	    	 
							    		<select class='form-control' id='cklb' name="cklb" >
										<c:forEach items="${storeType2Enum}" var="item">
										  <option value="${item.id}" <c:if test="${14 == item.id }">selected="selected"</c:if> >${item.name}</option>
										</c:forEach>
								    </select>							    	 
							     <%}else if(request.getAttribute("isStoreData").toString().equals("false")){//仓库主数据%>			    	 
							    		<select class='form-control' id='cklb' name="cklb" >
										<c:forEach items="${storeTypeEnum}" var="item">
										  <option value="${item.id}" <c:if test="${6 == item.id }">selected="selected"</c:if> >${item.name}</option>
										</c:forEach>
								    </select>
							     <%}%>
							
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="gkSelect">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">库管员</div>
								<div class="font-size-9">Manager</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style="height:34px">
								<div class='input-group ' style="min-width:100%">
									<input type="text"  value="" name="kgyn" id="kgyn" class="form-control readonly-style" ondblclick="openUserList()" placeholder='' maxlength="100" readonly="readonly" id="khText">
										<span class="input-group-btn" id="ffbmid" >
											<button type="button" class="btn btn-default " id="wxrybtn" data-toggle="modal" onclick="openUserList()">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</span>
									<input type="hidden" class="form-control" id="kgyid" name="kgyid"/>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">仓库地址</div>
								<div class="font-size-9">Address</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="ckdz" name="ckdz" maxlength="100"/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">基地</div>
								<div class="font-size-9 line-height-18">Base</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-leftright-8">
								<select class="form-control " id="jd" name="jd">
								</select>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">备注</div>
								<div class="font-size-9">Note</div>
							</label>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="bz" name="bz" class="form-control" maxlength="100" style="height:55px"></textarea>
							</div>
						</div>	
						 </form>
						<div class="clearfix"></div>
			            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-8">
							<label id="left_column" class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
								<div class="font-size-12 margin-topnew-2">库位</div>
								<div class="font-size-9">Location</div>
							</label>
						    <div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0" style="overflow-x: auto;">
							
						
						   
				<%
			    if("true".equals(request.getAttribute("isStoreData").toString())){//如果是仓库数据管理%>
			 	<button type="button"
						     id="button1"
							 class="btn btn-primary padding-top-1 padding-bottom-1 form-group checkPermission"
							 permissioncode="material:store2:main:04"
							 style="display: none"
							 onclick="showImportModal()">
							<div class="font-size-12">导入</div>
							<div class="font-size-9">Import</div>   
						   </button>
			    	
			   <% }else{//如果是仓库主数据%>			    	
				<button type="button"
						     id="button1"
							 class="btn btn-primary padding-top-1 padding-bottom-1 form-group checkPermission"
							 permissioncode="material:store:main:04"
							 style="display: none"
							 onclick="showImportModal()">
							<div class="font-size-12">导入</div>
							<div class="font-size-9">Import</div>   
						   </button>
			    <%}			
			%>
						   
						   
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:600px">
									<thead>
										<tr>
											<th class="colwidth-3" >
												<button class="line6 line6-btn" onclick="addRotatableCol()" type="button">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
											    </button>
												<!-- <button class="line6 " onclick="addRotatableCol()">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
												</button> -->
											</th>
											<th class="colwidth-3" >
												<div class="font-size-12 line-height-12">序号</div>
												<div class="font-size-9 line-height-12">No.</div>
											</th>
											<th class="colwidth-20" >
												<div class="font-size-12 line-height-12">库位号</div>
												<div class="font-size-9 line-height-12">Storage location</div>
											</th>
											<th>
												<div class="font-size-12 line-height-12">备注</div>
												<div class="font-size-9 line-height-12">Remark</div>
											</th>
								 		 </tr>
								</thead>
							   <tbody id="rotatable">
							   		 
								</tbody>
							</table>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
              <div class="clearfix"></div>              
            <!--  </div> -->
			 </div>	
			 <div class="clearfix"></div> 
			
			 </div>
           <div class="modal-footer">
           		<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
					<span class="input-group-addon modalfootertip" >
		                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
					</span>
	                    <span class="input-group-addon modalfooterbtn">
	                        <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="storefoModal.setData()" id="baocunButton">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
		                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
						    </button>
	                    </span>
	               	</div><!-- /input-group -->
				</div>
           
				<div class="clearfix"></div> 
				
			</div>
           
          </div>
	</div>
	</div>
<%@ include file="/WEB-INF/views/open_win/import.jsp"%>