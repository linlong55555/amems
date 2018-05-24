<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/config/store_open.js"></script>
<div class="modal fade in modal-new" id="alertMaterialForm" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:80%'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
		                      <div class="font-size-14 " id="materialHeadChina"></div>
							  <div class="font-size-12" id="materialHeadEnglish"></div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
              	<div class="col-xs-12 margin-top-8 ">
              
						<!-- start隐藏input -->
						<!-- end隐藏input -->
				<div class="panel panel-primary">
					<div id="attachHead" class="panel-heading bg-panel-heading" >
						<div class="font-size-12" id="chinaHead">基本信息</div>
						<div class="font-size-9" id="englishHead">Basic Information</div>
					</div>
					
					<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						<form id="form">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>件号</div>
								<div class="font-size-9">P/N</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control " id="bjh" name="bjh" maxlength="50" />
								<input type="hidden" class="form-control " id="id" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">中文名称</div>
								<div class="font-size-9">CH.Name</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control " id="zwms" name="zwms" maxlength="100" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>英文名称</div>
								<div class="font-size-9">F.Name</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="ywms" name="ywms" maxlength="100" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">件号来源</div>
								<div class="font-size-9">P/N Source</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="jhly" name="jhly" maxlength="100" />
							</div>
						</div>
						
						<div class="clearfix"></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>单位</div>
								<div class="font-size-9">Unit</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="jldw" class="form-control " name="jldw"></select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">厂家件号</div>
								<div class="font-size-9">MP/N</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control " id="cjjh" name="cjjh" maxlength="50" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="gkSelect">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">ATA章节号</div>
								<div class="font-size-9">ATA</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style="height:34px">
								<div class='input-group ' style="min-width:100%" id="ata_click">
								<input type="text" id="zjhName" name="zjhName" class="form-control readonly-style"  ondblclick="openChapterWin()" placeholder='' maxlength="100" readonly="readonly" />
										<input type="hidden" class="form-control" id="zjh" name="zjh"  />
										<span class="input-group-btn" id="ffbmid" >
											<button type="button" class="btn btn-default " id="zjhbtn" data-toggle="modal" onclick="openChapterWin()">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</span>
								</div>
								<input type="text" id="zjhName_text" name="zjhName_text" class="form-control " style="display:none" disabled="disabled"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">型号</div>
								<div class="font-size-9">Model</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control " id="xingh" name="xingh" maxlength="50" />
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">规格</div>
								<div class="font-size-9">Spec</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="gg" name="gg" maxlength="50" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">MEL</div>
								<div class="font-size-9">MEL</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="checkbox" id="isMel" name="isMel" checked="checked" />
							</div>
						</div>
						
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">适用机型</div>
								<div class="font-size-9">Model</div>
							</label>
							<div id="jxdiv" class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">标准件号</div>
								<div class="font-size-9">P/N</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="bzjh" name="bzjh" maxlength="50" />
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">GSE</div>
								<div class="font-size-9">GSE</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="gse" name="gse" maxlength="100" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">自制件</div>
								<div class="font-size-9">P/N Self</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<label style="margin-right: 20px;font-weight: normal">
							    	<input name="zzjbs" type="radio" id="zzjbs" value="1" />是
							    </label> 
								<label style="font-weight: normal">
									<input name="zzjbs" type="radio" value="0" />否 
								</label> 
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">制造厂商</div>
								<div class="font-size-9">Manufacturer</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="zzcs" name="zzcs" maxlength="100" />
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
					</div>
				</div>
					
					
				<div class="panel panel-primary">
					<div id="attachHead" class="panel-heading bg-panel-heading" >
						<div class="font-size-12" id="chinaHead">生产信息</div>
						<div class="font-size-9" id="englishHead">Production Information</div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>	
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">建议仓库</div>
								<div class="font-size-9">Warehouse</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="hidden" class="form-control " id="msId" />
								<select class='form-control' id='ckh' name="ckh" onchange="changeStore()" >
							    </select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">建议库位</div>
								<div class="font-size-9">Stock Location</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class='form-control' id='kwh' name="kwh" ></select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">航材价值</div>
								<div class="font-size-9">Aircraft Value</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class='form-control' id='hcjz' name="hcjz" >
									<option value="" ></option>
									<c:forEach items="${materialPriceEnum}" var="item">
									  <option value="${item.id}" <c:if test="${1 == item.id }">selected="true"</c:if> >${item.name}</option>
									</c:forEach>
							    </select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">管理级别</div>
								<div class="font-size-9">Level</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class='form-control' id='gljb' name="gljb" >
									<c:forEach items="${supervisoryLevelEnum}" var="item">
									  <option value="${item.id}" <c:if test="${3 == item.id }">selected="true"</c:if> >${item.name}</option>
									</c:forEach>
							    </select>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">最低库存量</div>
								<div class="font-size-9">Minimum</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" id="minKcl" name="minKcl" class="form-control " value="${material.minKcl}"  placeholder='' onkeyup='clearNoNumTwo(this)' maxlength="10" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">最高库存量</div>
								<div class="font-size-9">Maximum</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" id="maxKcl" name="maxKcl" class="form-control " value="${material.maxKcl}"  placeholder='' onkeyup='clearNoNumTwo(this)' maxlength="10" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">类型</div>
								<div class="font-size-9">Type</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class='form-control' id='hclx' name="hclx" onchange="changeMaterialType()">
									<c:forEach items="${materialTypeEnum}" var="item">
									  <option value="${item.id}" <c:if test="${1 == item.id }">selected="true"</c:if> >${item.name}</option>
									</c:forEach>
							    </select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">时效控制</div>
								<div class="font-size-9">Control</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								 <label style="margin-right: 20px;font-weight: normal">
							    	<input name="sxkz" type="radio" value="1" />是
							    </label> 
								<label style="font-weight: normal">
									<input name="sxkz" type="radio" value="0" />否 
								</label> 
							</div>
						</div>
						<div class="clearfix"></div>
						<div id="materialType" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">航材二级类型</div>
								<div class="font-size-9">Second Type</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class='form-control' id='hclxEj' name="hclxEj" >
									<c:forEach items="${materialSecondTypeEnum}" var="item">
									  <option value="${item.id}" <c:if test="${100 == item.id }">selected="true"</c:if> >${item.name}</option>
									</c:forEach>
							    </select>
							</div>
						</div>
						
					</div>
				</div>
				
				<div class="panel panel-primary">
					<div id="attachHead" class="panel-heading bg-panel-heading" >
						<div class="font-size-12" id="chinaHead">附件列表</div>
						<div class="font-size-9" id="englishHead">File List</div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>	
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit.jsp"%><!-- 加载附件信息 -->
					</div>
				</div>
				
				<div id="subListView" style="display:none" class="panel panel-primary">
					<div id="attachHead" class="panel-heading bg-panel-heading" >
						<div class="font-size-12" id="chinaHead">等效替代</div>
						<div class="font-size-9" id="englishHead">Equivalent Substitution</div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>	
					    <div class='padding-leftright-8'>
						<table class="table table-bordered table-striped table-hover text-center table-set" style="min-width:910px">
							<thead>
						   		<tr>
									<th class="colwidth-3" >
										<div class="font-size-12 notwrap">序号</div>
										<div class="font-size-9 notwrap">No.</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18" >件号</div>
										<div class="font-size-9 line-height-18" >T/B No.</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18" >件号描述</div>
										<div class="font-size-9 line-height-18" >T/B No.</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18" >替代件号</div>
										<div class="font-size-9 line-height-18" >T/B No.</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18" >替代描述</div>
										<div class="font-size-9 line-height-18" >T/B No.</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18" >机型适用性</div>
										<div class="font-size-9 line-height-18" >T/B No.</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18" >工卡适用性</div>
										<div class="font-size-9 line-height-18" >T/B No.</div>
									</th>
									<th class="colwidth-7">
										<div class="font-size-12 line-height-18" >是否可逆性</div>
										<div class="font-size-9 line-height-18" >T/B No.</div>
									</th>
								</tr>
							</thead>
							<tbody id="subListViewTable">
							
							</tbody>
						</table>
						</div>
					</div>
				</div>
						
				</div>
              <div class="clearfix"></div>              
            <!--  </div> -->
			
			 </div>
           <div class="modal-footer">
           		<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
					<span class="input-group-addon modalfootertip" >
		                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
					</span>
	                    <span class="input-group-addon modalfooterbtn">
	                        <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="save();" >
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
