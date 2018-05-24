<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
				<input type="hidden" id="manidinfo" />
				<input type="hidden" id="dprtId" />
				<input type="hidden" id="fjzchid" />
				<div class="panel panel-default col-xs-12 padding-left-0 padding-right-0">
				<div class="panel-heading">
                      <h3 class="panel-title">故障处理记录 Fault handling record</h3>
                </div>
				
				<div class="panel-body">
				<!-- 搜索框start -->
				<button type="button"class="btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 row-height pull-left checkPermission"
					permissioncode='productionplan:planefaultmonitoring:main:04'	onclick='addDcgz()'>
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</button>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" 
					style="margin-top: 10px;">
					<table
						class="table table-thin table-bordered table-set" id="flight_record_sheet_table">
						<thead>
							<tr>
								<th class="colwidth-7 ">
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div></th>

								<th class="colwidth-7 " >
									<div class="font-size-12 line-height-18">航班号</div>
									<div class="font-size-9 line-height-18">Flight No.</div></th>
								<th class="colwidth-9 ">									
										<div class="font-size-12 line-height-18">日期</div>
										<div class="font-size-9 line-height-18">Date</div>
								</th>
									
								<th class="colwidth-13 " >
									<div class="font-size-12 line-height-18">排故思路</div>
									<div class="font-size-9 line-height-18">Troubleshooting Thinking</div></th>
								<th class="colwidth-11 ">
									<div class="font-size-12 line-height-18">处理结果</div>
									<div class="font-size-9 line-height-18">Result</div></th>
								<th class="colwidth-11 " >
									<div class="font-size-12 line-height-18">指令号</div>
									<div class="font-size-9 line-height-18">Instruction No.</div></th>
								<th class="colwidth-11 " >
										<div class="font-size-12 line-height-18">拆下</div>
										<div class="font-size-9 line-height-18">Remove</div>
								</th>
								<th class="colwidth-11 " >			
										<div class="font-size-12 line-height-18">装上</div>
										<div class="font-size-9 line-height-18">Mount</div>
								</th>
								<th class="colwidth-11 " >			
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
								</th>
								<th class="colwidth-13 " > 
									<div class="font-size-12 line-height-18">附件</div>
									<div class="font-size-9 line-height-18">Attachment</div></th>
							</tr>
						</thead>
						<tbody id='dcgzcl_list'>

						</tbody>

					</table>
				</div>

				<div class=" col-xs-12  text-center page-height padding-left-0 padding-right-0"  id="fjgzjkInfo_pagination"></div>
				<div class="clearfix"></div>
           
			
			</div>
			</div>
			<!-- 新增单次故障处理 -->
			<div class="modal fade" id="alertModalDcgz" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		    <div class="modal-dialog" style="width:70%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
							<div class="font-size-16 line-height-18" id="infotitle"></div>
							<div class="font-size-9 " id="infotitlefy">New single fault handling</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12 col-sm-12 padding-left-0 padding-right-0">
			            	<input type="hidden" id="infoId" />
			            	<form id="form">
			            	<div class="col-xs-12 col-sm-6 col-lg-6 padding-right-0 padding-left-0 margin-top-10  form-group">
								<label class="pull-left col-lg-2 col-xs-2 col-sm-2 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12"><span style="color:red">*</span>航班号</div>
									<div class="font-size-9">Flight No.</div>
								</label>
								<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
									<input type='text' id="hbh" name="hbh" class='col-lg-8 form-control text-left' style="width:90%"/>
									<input type="hidden" id="fxjldid" value=""/>
									<button type="button" class="btn btn-primary form-control"  style="width:10%"
										 data-toggle="modal"
										onclick="openHb()">
										<i class="icon-search cursor-pointer"></i>
									</button>
								</div>
							</div> 
							<div class="col-xs-12 col-sm-6 col-lg-6 padding-right-0 padding-left-0 margin-top-10  form-group">
								<label class="pull-left col-lg-2 col-xs-2 col-sm-2 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12"><span style="color:red">*</span>航班日期</div>
									<div class="font-size-9">Flight Date</div>
								</label>
								<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
									<input type="text" class="form-control date-picker"
												maxlength="10" data-date-format="yyyy-mm-dd"  id="hbrq" name="hbrq"  />		
								</div>
							</div> 
							<div class="clearfix"></div>
							<div class="col-xs-12 col-sm-6 col-lg-6 padding-right-0 padding-left-0   form-group">
								<label class="pull-left col-lg-2 col-xs-2 col-sm-2 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12"><span style="color:red">*</span>指令号</div>
									<div class="font-size-9">Instruction No.</div>
								</label>
								<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
										<input type='text' id="zlh" name="zlh" maxlength="50" class='col-lg-8 form-control text-left' style="width:90%"/>
										<input type="hidden" id="zlhid" value=""/>
										<button type="button" class="btn btn-primary form-control"  style="width:10%"
											 data-toggle="modal"
											onclick="openZlh()">
											<i class="icon-search cursor-pointer"></i>
										</button>
								</div>
							</div> 
							<div class="col-xs-12 col-sm-6 col-lg-6 padding-right-0 padding-left-0  margin-bottom-10 form-group">
								<label class="pull-left col-lg-2 col-xs-2 col-sm-2 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12">拆下</div>
									<div class="font-size-9">Remove</div>
								</label>
								<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
									<input type="text" id="cxjxx" name="cxjxx" maxlength="1300" class="form-control" />
									
								</div>
							</div>
							<div class="clearfix"></div> 
							<div class="col-xs-12 col-sm-6 col-lg-6 padding-right-0 padding-left-0  margin-bottom-10 form-group">
								<label class="pull-left col-lg-2 col-xs-2 col-sm-2 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12">装上</div>
									<div class="font-size-9">Mount</div>
								</label>
								<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
									<input type="text" id="zsjxx" maxlength="1300" name="zsjxx" class="form-control" />
									
								</div>
							</div>
							 <div class="col-xs-12 col-sm-12 col-lg-12 padding-right-0 padding-left-0  margin-bottom-10 ">
								<label class="pull-left col-lg-1 col-xs-1 col-sm-1 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12">排故思路</div>
									<div class="font-size-9">Troubleshooting Thinking</div>
								</label>
								<div class="col-lg-11 col-sm-11 col-xs-11 padding-left-8 padding-right-0">
									<textarea class="form-control" id="pgsl" name="pgsl" placeholder='长度最大为1300' maxlength="1300" style="min-height:80px" ></textarea>
									
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-lg-12 padding-right-0 padding-left-0  margin-bottom-10 ">
								<label class="pull-left col-lg-1 col-xs-1 col-sm-1 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12">处理结果</div>
									<div class="font-size-9">Treatment Result</div>
								</label>
								<div class="col-lg-11 col-sm-11 col-xs-11 padding-left-8 padding-right-0">
									<textarea class="form-control" id="cljg" name="cljg" placeholder='长度最大为1300' maxlength="1300" style="min-height:80px" ></textarea>
									
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-lg-12 padding-right-0 padding-left-0  margin-bottom-10 ">
								<label class="pull-left col-lg-1 col-xs-1 col-sm-1 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12">备注</div>
									<div class="font-size-9">Remark</div>
								</label>
								<div class="col-lg-11 col-sm-11 col-xs-11 padding-left-8 padding-right-0">
									<textarea class="form-control" id="bz" name="bz" placeholder='长度最大为300' maxlength="300" style="min-height:80px" ></textarea>									
								</div>
							</div>
							<div id="wjsm"
										class="col-lg-6 col-sm-6 col-xs-6 padding-left-0 padding-top-0 padding-right-0">
										<label 
											class="col-lg-2 col-sm-3 col-xs-4 text-right padding-left-0 padding-right-0 "><div
												class="font-size-12 line-height-18">
												文件说明
											</div>
											<div class="font-size-9 line-height-18">File Desc</div> </label>
										<div
											class="col-lg-5 col-sm-5 col-xs-8 padding-left-8 padding-right-0 ">
											<div id="wj"
												class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
												<div
													class="col-lg-10 col-sm-10 col-xs-10 padding-left-0 padding-right-0">
													<input type="text" id="wbwjm" name="wbwjm" placeholder='' maxlength="90"
														class="form-control ">
												</div>
												<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-2 padding-left-3 padding-right-0"></div>
											</div>
										</div>
									</div>

									<div id="lookfile" style="overflow-x:auto;" 
										class="col-lg-12 col-sm-12 col-xs-12 margin-top-10 padding-left-0 padding-right-0">
										<table
											class="table table-thin table-bordered table-striped table-hover text-center table-set">
											<thead>
												<tr>
													<th class="colwidth-3" id="operat"><div 
															class="font-size-12 line-height-18 ">操作</div>
														<div class="font-size-9 line-height-18">Operation</div></th>
													<th class="colwidth-20">
														<div class="font-size-12 line-height-18">文件说明</div>
														<div class="font-size-9 line-height-18">File Desc</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 line-height-18">文件大小</div>
														<div class="font-size-9 line-height-18">File Size</div>
													</th>
													<th class="colwidth-13"><div class="font-size-12 line-height-18">上传人</div>
														<div class="font-size-9 line-height-18">Uploader</div></th>
													<th class="colwidth-13"><div class="font-size-12 line-height-18">上传时间</div>
														<div class="font-size-9 line-height-18">Upload Time</div></th>
												</tr>
											</thead>
											<tbody id="filelist">


											</tbody>
										</table>
									</div> 
									</form>    
					     		<div class="clearfix"></div>
					     		<div class="text-right margin-top-10 padding-buttom-0 ">
                               <button onclick="saveinfo()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-0" ><div
										class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div></button>
                     	      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-0" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
                    			 </div><br/>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- 航班 -->
		<div class="modal fade" id="alertModalHb" tabindex="-1" role="dialog" aria-labelledby="alertModalHb" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">航班信息</div>
							<div class="font-size-9 ">Flight Info</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
					       
								   <div  class=" pull-right padding-left-0 padding-right-0 padding-top-10" >
									     <div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;">
										   <input type="text" placeholder="航班号" id="keyword_search" class="form-control ">
					                       </div>
					                       
					                      <div class="pull-right padding-left-5 padding-right-0">   
											<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="hbModal.search();">
												<div class="font-size-12">搜索</div>
												<div class="font-size-9">Search</div>
					                   		</button>
					                     </div>
									    
					               </div>
					           
				                <div class="clearfix"></div>	     
								<div  style="overflow-x: auto;margin-top: 10px">
									<table class="table-set table table-thin table-bordered table-striped table-hover ">
										<thead>
											<tr>
											<th class="colwidth-3"><div class="font-size-12 line-height-18" >选择</div>
												<div class="font-size-9 line-height-18" >Choice</div></th>
											<th class="colwidth-13">
												<div class="important">
												<div class="font-size-12 line-height-18">航班号</div>
												<div class="font-size-9 line-height-16">Flight No.</div>
												</div>
											</th>
											<th class="colwidth-13">
												<div class="important">
												<div class="font-size-12 line-height-18">航班日期</div>
												<div class="font-size-9 line-height-16">Flight Date</div>
												</div>
											</th>
											</tr> 
						         		 </thead>
										<tbody id="Hblist">
										</tbody>
									</table>
									</div>
									<div class="col-xs-12 text-center" id="hbpagination">
		
									</div>
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="hbModal.setUser()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
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
		<!-- 指令 -->
		<div class="modal fade" id="alertModalZl" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" >
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">指令信息</div>
							<div class="font-size-9 ">Instruction info</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
					       
								   <div  class=" pull-right padding-left-0 padding-right-0 padding-top-10" >
									     <div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;">
										   <input type="text" placeholder="指令号" id="keyword_search1" class="form-control ">
										   <input type="hidden" id="gdid">
					                       </div>
					                       
					                      <div class="pull-right padding-left-5 padding-right-0">   
											<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="alertModalZl.search()();">
												<div class="font-size-12">搜索</div>
												<div class="font-size-9">Search</div>
					                   		</button>
					                     </div>
									    
					               </div>
					           
				                <div class="clearfix"></div>	     
								<div  style="overflow-x: auto;margin-top: 10px">
									<table class="table-set table table-thin table-bordered table-striped table-hover ">
										<thead>
											<tr>
											<th class="colwidth-3"><div class="font-size-12 line-height-18" >序号</div>
												<div class="font-size-9 line-height-18" >No.</div></th>
											<th class="colwidth-13">												
												<div class="font-size-12 line-height-18">指令号</div>
												<div class="font-size-9 line-height-16">Instruction number</div>
											</th>
											
											</tr> 
						         		 </thead>
										<tbody id="zlhlist">
										</tbody>
									</table>
									<div class="col-xs-12 text-center" id="zlhpagination">
									</div>
									</div>
									<div
										class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
										<label
											class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-12 line-height-18">指令号</div>
											<div class="font-size-9 line-height-18">Order</div>
										</label>
										<div
											class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
											<input class="form-control" id="selectzlh" type="text"  />
										</div>
									</div>
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="alertModalZl.setData()"
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
	<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/planefaultmonitoring/planefaultmonitoring_mainone.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/planefaultmonitoring/hbh.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/planefaultmonitoring/zlh.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 附件对话框 -->
		