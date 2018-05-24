<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/workorder/alertbjh.js"></script>
<div class="modal fade" id="bjhModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:80%" >
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">部件列表</div>
						<div class="font-size-9 ">P/N List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
				         <div class="col-lg-9 pull-right padding-right-0">
				              <div class="col-lg-7 padding-left-0 padding-top-10" id="demo1_div">
				            	    <label class="col-xs-6 text-right padding-left-0 padding-right-2">
										<div class="font-size-12 line-height-18">位置</div>
										<div class="font-size-9 line-height-18">Position</div>
									</label>
				            	    <div class="col-lg-6 pull-left padding-right-0 padding-left-0 ">
			            	            <select  id="wz" name="wz" class="form-control"  onchange="changeWz()">
				         		            <option value="" selected="selected">查看全部</option>
											<option value="0">机身 Fuselage</option>
											<option value="1">1#左发 L/Engine</option>
											<option value="2">2#右发 R/Engine</option>
											<option value="5">外吊挂 E/S</option>
											<option value="4">搜索灯 Search</option>
											<option value="3">绞车 Winch</option>
							            </select>
								    </div>  
							   </div>
							   <div  class="col-lg-5 padding-right-0 padding-left-0   padding-top-10 pull-right" >
									   <div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;">
									   <input type="text" placeholder="ATA章节号/部件号/序列号/中英文/厂家件号" id="keyword_search_alert" class="form-control ">
				                       </div>
				                      <div class="pull-right padding-left-5 padding-right-0">   
										<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision_alert();">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                     </div>
								    
				               </div>
				         </div>
							<!-- start:table -->
					        <div class="clearfix"></div>	     
							<div  style="margin-top: 10px">
								<table class="table-set table table-bordered table-striped table-hover text-center" style="">
									<thead>
								   		<tr>
											<th width="50px">
												<div class="font-size-12 notwrap">选择</div>
												<div class="font-size-9 notwrap">Choice</div>
											</th>
											<th>
											  <div class="important">
												<div class="font-size-12 notwrap">ATA章节号</div>
												<div class="font-size-9 notwrap">ATA</div>
												</div>
											</th>
											<th>
												<div class="important">
													<div class="font-size-12 notwrap">部件号</div>
													<div class="font-size-9 notwrap">P/N</div>
											    </div>
											</th>
											<th>
											  <div class="important">
											 	<div class="font-size-12 notwrap">序列号</div>
												<div class="font-size-9 notwrap">S/N</div>
											  </div>	
											</th>
											<th>
											  <div class="important">
												<div class="font-size-12 notwrap">英文名称</div>
												<div class="font-size-9 notwrap">English Name</div>
											  </div>	
											</th>
											<th>
											   <div class="important">
												<div class="font-size-12 notwrap">中文名称</div>
												<div class="font-size-9 notwrap">Chinese Name</div>
												</div>
											</th>
											<th>
											  <div class="important">
												<div class="font-size-12 notwrap">厂家件号</div>
												<div class="font-size-9 notwrap">MP/N</div>
											  </div>	
											</th>
											<th id="demo2_div">
												<div class="font-size-12 notwrap">位置</div>
												<div class="font-size-9 notwrap">Position</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="userlist">
									
								   </tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="bjh_pagination"></div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10 margin-right-9">
								<button type="button" onclick="getBjhAndXlh()"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									data-dismiss="modal">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>&nbsp;&nbsp;
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
			                </div>
			     			<div class="clearfix"></div>
						 </div>
				  </div>
			</div> 
		</div>
	</div>
</div> 
