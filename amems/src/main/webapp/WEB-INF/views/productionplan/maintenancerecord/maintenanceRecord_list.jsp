<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="tab-pane fade in active" style="padding-left:15px;padding-right:15px;" >
	<div class=" col-xs-12 padding-left-0 padding-right-0 " >
				
		<div class="col-xs-2 col-md-3 padding-left-0">
			<a href="javascript:void(0);" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left"  onclick="openSummaryAdd()" >
				<div class="font-size-12">新增</div>
				<div class="font-size-9">Add</div>
			</a> 
		</div>
				
		<!-- 搜索框start -->
		<div class=" pull-right padding-left-0 padding-right-0 row-height">
			<div class=" pull-left padding-left-0 padding-right-0" style="width: 400px;">
				<input type="text" placeholder='关键字/件号/飞机机型/飞机注册号/ATA章节号/故障描述/经验总结' id="keyword_search" class="form-control ">
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
				
		<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-10 display-none search-height border-cccccc" id="divSearch">
					
			<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
				<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12">维护日期</div>
					<div class="font-size-9">Date</div>
				</span>
				<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
					<input type="text" class="form-control" name="date-range-picker" id="zdrq_search" readonly />
				</div>
			</div>	
			
			<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
				<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12">可见范围</div>
					<div class="font-size-9">Visible Range</div>
				</span>
				<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
					<select class='form-control' id='kjfw_search'>
						<option value="" selected="true">显示全部All</option>
						<c:forEach items="${visibleRangeEnum}" var="item">
						  	<option value="${item.id}" >${item.name}</option>
						</c:forEach>
				    </select>
				</div>
			</div>
			
			<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
				<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12">专业</div>
					<div class="font-size-9">Skill</div>
				</span>
				<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
					<select id="zy_search" class="form-control" >
						<option value="" selected="true">显示全部All</option>
					</select>
				</div>
			</div>	
			
			<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
				<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12">基地</div>
					<div class="font-size-9">Base</div>
				</span>
				<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
					<select class="form-control " id="jd_search" name="jd_search">
						<option value="" selected="true">显示全部All</option>
						<c:forEach items="${baseList}" var="baseList">
							<option value="${baseList.id}">${erayFns:escapeStr(baseList.jdms)}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="clearfix"></div>	
			
			<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
				<button type="button"
					class="btn btn-primary padding-top-1 padding-bottom-1"
					onclick="searchreset();">
					<div class="font-size-12">重置</div>
					<div class="font-size-9">Reset</div>
				</button>
			</div>
		</div>
	</div>
	<div  class=" col-xs-12 text-center margin-bottom-5 padding-left-0 padding-right-0 table-h" style="overflow-x:auto;"> 
				
		<table id="record_list_table" class="table table-thin table-bordered text-center table-set" style="min-width: 1700px !important">
			<thead>
				<tr>
					<th class="fixed-column colwidth-7">	
						<div class="font-size-12 line-height-18 " >操作</div>
						<div class="font-size-9 line-height-18">Operation</div>
					</th>
					<th class="colwidth-9 sorting" onclick="orderBy('KJFW')" id="KJFW_order">
						<div class="font-size-12 line-height-18">可见范围</div>
						<div class="font-size-9 line-height-18">Visible Range</div>
					</th>
					<th class="colwidth-25 sorting" onclick="orderBy('GJC')" id="GJC_order">
						<div class="important">
							<div class="font-size-12 line-height-18">关键字</div>
							<div class="font-size-9 line-height-18">Keyword</div>
						</div>
					</th>
					<th class="colwidth-15 sorting" onclick="orderBy('BJH')" id="BJH_order">
						<div class="important">
							<div class="font-size-12 line-height-18">件号</div>
							<div class="font-size-9 line-height-18">P/N</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="orderBy('FJJX')" id="FJJX_order">
						<div class="important">
							<div class="font-size-12 line-height-18">飞机机型</div>
							<div class="font-size-9 line-height-18">Model</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="orderBy('FJZCH')" id="FJZCH_order">
						<div class="important">
							<div class="font-size-12 line-height-18">飞机注册号</div>
							<div class="font-size-9 line-height-18">A/C REG</div>
						</div>
					</th>
					<th class="colwidth-7 sorting" onclick="orderBy('JD')" id="JD_order">
							<div class="font-size-12 line-height-18">基地</div>
							<div class="font-size-9 line-height-18">Base</div>
					</th>
					<th class="colwidth-15 sorting" onclick="orderBy('ZJH')" id="ZJH_order">
						<div class="important">
							<div class="font-size-12 line-height-18">ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div>
						</div>
					</th>
					<th class="colwidth-7 sorting" onclick="orderBy('ZY')" id="ZY_order">
						<div class="font-size-12 line-height-18">专业</div>
						<div class="font-size-9 line-height-18">Skill</div>
					</th>
					<th class="colwidth-30 sorting" onclick="orderBy('GZMS')" id="GZMS_order">
						<div class="important">
							<div class="font-size-12 line-height-18">故障描述</div>
							<div class="font-size-9 line-height-18">Fault Desc</div>
						</div>
					</th>
					<th class="colwidth-30 sorting" onclick="orderBy('JYZJ')" id="JYZJ_order">
						<div class="important">
							<div class="font-size-12 line-height-18">经验总结</div>
							<div class="font-size-9 line-height-18">Summary</div>
						</div>
					</th>
					<th class="colwidth-13 sorting" onclick="orderBy('ZDSJ')" id="ZDSJ_order">
						<div class="font-size-12 line-height-18">维护时间</div>
						<div class="font-size-9 line-height-18">Maintenance Time</div>
					</th>
					<th class="colwidth-15 sorting" onclick="orderBy('DPRTCODE')" id="DPRTCODE_order">
						<div class="font-size-12 line-height-18">组织机构</div>
						<div class="font-size-9 line-height-18">Organization</div>
					</th>
				</tr>
			</thead>
			<tbody id="summaryList"></tbody>
		</table>
	</div>
	<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
	
</div>

<!-------故障总结对话框 Start-------->
	
<div class="modal fade" id="alertRecordForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false >
	<div class="modal-dialog" style="width:85%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertRecordFormBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18" id="recordHeadChina" >新增档案</div>
						<div class="font-size-9 " id="recorHeadEnglish">Add</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" >
						
						<form id="form">
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">基本信息</div>
								<div class="font-size-9 ">Basic Information</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
										<input type="text" class="form-control " id="bjh" name="bjh" maxlength="50"/>
										<input type="hidden" class="form-control " id="id" />
										<span class="input-group-btn">
											<button type="button" id="bjhbtn" class="btn btn-primary form-control" data-toggle="modal" onclick="openMaterialWin()">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</span>
									</div>
								</div>
							
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">Model</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
										<input type="text" class="form-control " id="fjjx" name="fjjx" maxlength="20"/>
										<span class="input-group-btn">
											<button type="button" id="fjjxbtn" class="btn btn-primary form-control" data-toggle="modal" onclick="openPlaneModelWin()">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</span>
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
										<input type="text" class="form-control " id="fjzch" name="fjzch" maxlength="20"/>
										<span class="input-group-btn">
											<button type="button" id="fjzchbtn" class="btn btn-primary form-control" data-toggle="modal" onclick="openPlaneRegWin()">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</span>
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">ATA章节号</div>
										<div class="font-size-9 line-height-18">ATA</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left input-group">
										<input type="text" id="zjhName" name="zjhName" class="form-control " readonly />
										<input type="hidden" class="form-control" id="zjh" name="zjh"  />
										<span class="input-group-btn">
											<button type="button" id="zjhbtn" class="btn btn-primary form-control" data-toggle="modal" onclick="openChapterWin()">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</span>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">关键字</div>
										<div class="font-size-9 line-height-18">Keyword</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control" id="gjc" name="gjc" maxlength="16" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">可见范围</div>
										<div class="font-size-9 line-height-18">Visible Range</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select class='form-control' id='kjfw' name="kjfw" >
											<c:forEach items="${visibleRangeEnum}" var="item">
											  	<option value="${item.id}" <c:if test="${1 == item.id }">selected="true"</c:if> >${item.name}</option>
											</c:forEach>
									    </select>
									</div>
								</div>
								
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">基地</div>
										<div class="font-size-9 line-height-18">Base</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select class="form-control " id="jd" name="jd">
											<c:forEach items="${baseList}" var="baseList">
												<option value="${baseList.id}">${erayFns:escapeStr(baseList.jdms)}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">专业</div>
										<div class="font-size-9 line-height-18">Skill</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select id="zy" class="form-control" name="zy"></select>
									</div>
								</div>		
	
								<div class="clearfix"></div>
							
							 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>故障描述</div>
										<div class="font-size-9 line-height-18">Fault Desc</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
										<textarea class="form-control" id="gzms" name="gzms" maxlength="600" ></textarea>
									</div>
								</div>
								
								<div class="clearfix"></div>
							
							 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12 line-height-18">经验总结</div>
										<div class="font-size-9 line-height-18">Summary</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
										<textarea class="form-control" id="jyzj" name="jyzj" maxlength="600" ></textarea>
									</div>
								</div>
							</div>
						</form>
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
							
							<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class=" pull-left margin-right-10" >
									<div class="font-size-16 line-height-18">排故思路及经过</div>
									<div class="font-size-9 ">Reason of Arrangement</div>
								</div>	
							</div>
							
			            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
		
								<!-- start:table -->
								<div style="overflow-x: auto;">
									<table class="table table-bordered table-striped table-hover table-set" style="min-width: 650px;">
										<thead>
									   		<tr>
												<th id="addDetailBtn" class="colwidth-5">
													<button class="line6 " onclick="openThoughtWinAdd()">
														<i class="icon-plus color-blue cursor-pointer" ></i>
													</button>
												</th>
												<th class="colwidth-3">
													<div class="font-size-12 notwrap">序号</div>
													<div class="font-size-9 notwrap">No.</div>
												</th>
												<th class="colwidth-13">
													<div class="font-size-12 notwrap">处理人</div>
													<div class="font-size-9 notwrap">Operator</div>
												</th>
												<th class="colwidth-9">
													<div class="font-size-12 notwrap">处理日期</div>
													<div class="font-size-9 notwrap">Operate Date</div>
												</th>
												<th>
													<div class="font-size-12 notwrap">排故经过</div>
													<div class="font-size-9 notwrap">Reason of Arrangement</div>
												</th>
									 		 </tr>
										</thead>
										<tbody id="detailTable">
										
										</tbody>
									</table>
									</div>
								<!-- end:table -->
					     		<div class="clearfix"></div>
							 </div> 
						</div>
						
						<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class=" pull-left margin-right-10" >
								<div class="font-size-16 line-height-18">附件</div>
								<div class="font-size-9 ">Attachments</div>
							</div>	
						</div>
				
						<div class="col-xs-12 col-lg-12 col-sm-12 padding-left-0 padding-right-0" >
                      		
                     			<div id="fileHead" class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10" >
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
									<div class="font-size-9 line-height-18">File Desc</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
									<div class="col-lg-10 col-sm-10 col-xs-10 text-right padding-left-0 padding-right-0">
										<input type="text" id="wbwjm" name="wbwjm" placeholder='' class="form-control "  >
									</div>
									<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-2 " style="margin-left: 0;padding-left: 0"></div>
								</div>
								
							</div>
                     		<div class="col-xs-12 col-lg-12 col-sm-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
							<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width: 750px;">
								<thead>
									<tr>
										<th id="colhead" class="colwidth-5" >
											<div class="font-size-12 line-height-18" >操作</div>
											<div class="font-size-9 line-height-18">Operation</div>
										</th>
										<th class="colwidth-3">
											<div class="font-size-12 line-height-18">序号</div>
											<div class="font-size-9 line-height-18">No.</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12 line-height-18">文件说明</div>
											<div class="font-size-9 line-height-18">File desc</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">文件大小</div>
											<div class="font-size-9 line-height-18">File size</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 line-height-18">上传人</div>
											<div class="font-size-9 line-height-18">Operator</div></th>
										<th class="colwidth-13">
											<div class="font-size-12 line-height-18">上传时间</div>
											<div class="font-size-9 line-height-18">Operate Time</div>
										</th>
									</tr>
								</thead>
							    <tbody id="profileList">
									 
								</tbody>
							</table>
							</div>
						</div>
						
						
					 	 <div class="text-right margin-top-10 margin-right-0">
							<button id="summarySave" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:save();">
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

<!-------故障总结对话框 End-------->

<!-------经过思路对话框 Begin-------->
<div class="modal fade" id="alertThoughtForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:500px!important;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertThoughtFormBody">
			  	<div class="panel panel-primary ">
					<div class="panel-heading  padding-top-3 padding-bottom-1  margin-bottom-10">
						<div class="font-size-16 line-height-18" id="thoughtHeadChina">排故思路及经过</div>
						<div class="font-size-9 " id="thoughtHeadEnglish">After Thought</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
					
		            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						
							<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>处理人</div>
									<div class="font-size-9 line-height-18">Operator</div>
								</label>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0 input-group">
									<input type="text" id="clr" class="form-control"  maxlength="16" />
									<input type="hidden" id="clrid" class="form-control" readonly/>
									<span class="input-group-btn">
										<button type="button" id="clrbtn" class="btn btn-primary form-control" data-toggle="modal" onclick="openUserWin()">
											<i class="icon-search cursor-pointer"></i>
										</button>
									</span>
								</div>
							</div>
							
							<div class="clearfix"></div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>处理日期</div>
									<div class="font-size-9 line-height-18">Operate Date</div>
								</label>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
									<input class="form-control date-picker" id="clsj" data-date-format="yyyy-mm-dd" type="text" />
								</div>
							</div>
							
							<div class="clearfix"></div>
						
						 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0 ">
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>排故经过</div>
									<div class="font-size-9 line-height-18">Reason of Arrangement</div>
								</label>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0 ">
									<textarea class="form-control" id="pgjg" maxlength="600" ></textarea>
								</div>
							</div>
						</div>
						
						<div class="clearfix"></div>
						<div class="text-right margin-top-10 margin-bottom-10">
							<button type="button" onclick="alertThoughtForm.setData()" class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
		                </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-------经过思路对话框 End-------->
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/maintenancerecord/maintenanceRecord_list.js"></script>
<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%><!-------航材对话框 -------->
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%><!-- ATA章节弹出框 -->
<%@ include file="/WEB-INF/views/open_win/user.jsp"%><!-------用户对话框 -------->
<%@ include file="/WEB-INF/views/open_win/plane_model.jsp"%><!-- 机型弹出框 -->
<%@ include file="/WEB-INF/views/open_win/plane_regist.jsp"%><!-- 飞机注册号弹出框 -->
