<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/workorder/copy_wo.js"></script> 
<div class="modal fade" id="alertCopyWO" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:65%">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
				<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">工单信息列表</div>
							<div class="font-size-9 ">List of Info for W/O</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
					            	
								   <div  class=" pull-right padding-left-0 padding-right-0 padding-top-10" >
								   
									   <div class="pull-left">
						            	    <label class="pull-left  text-right padding-left-0 padding-right-0">
												<div class="font-size-12 line-height-18">类型</div>
												<div class="font-size-9 line-height-18">Type</div>
											</label>
						            	    <div class=" padding-left-8 padding-right-0 pull-left" style="width: 180px; margin-right:5px;">
					            	            <select  id="lx_search" name="lx_search" class="form-control"  onchange="changeLx()">
						         		            <option value="GD" select="select">工单</option>
						         		            <option value="GK">工卡</option>
									            </select>
										    </div>  
									   </div>
									   
										<div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;" >
										   <input type="text" placeholder="编号" id="keyword_search_copy" class="form-control ">
				                        </div>
					                       
					                      <div class=" pull-right padding-left-5 padding-right-0 ">   
											<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision_copy();">
												<div class="font-size-12">搜索</div>
												<div class="font-size-9">Search</div>
					                   		</button>
					                     </div>
									    
					               </div>
				                <div class="clearfix"></div>	     
								<!-- start:table -->
								<div  style="overflow-x: auto;margin-top: 10px">
									<table class="table-set table table-thin table-bordered table-striped table-hover text-center" style="min-width:700px">
										<thead>
										<tr>
										<th class="colwidth-3">
										   <div class="font-size-12 line-height-18" >选择</div>
										    <div class="font-size-9 line-height-18" >Choice</div>
									    </th>
										<th class="colwidth-9">
											<div class="important">
											<div class="font-size-12 line-height-18">工单/卡编号</div>
											<div class="font-size-9 line-height-16">W/O No.</div>
											</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 line-height-18">专业</div>
											<div class="font-size-9 line-height-16">Skill</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">ATA章节号</div>
											<div class="font-size-9 line-height-16">ATA</div>
										</th>
										<th class="colwidth-3">
											<div class="font-size-12 line-height-18">工作站位</div>
											<div class="font-size-9 line-height-16">Location</div>
										</th>
										<th class="colwidth-20">
										<div class="font-size-12 line-height-18">工单主题</div>
										<div class="font-size-9 line-height-16">Subject</div>
										</th>
									 </tr> 
						         	</thead>
										<tbody id="copy_wolist">
										</tbody>
									</table>
									</div>
									<div class="col-xs-12 text-center" id="pagination_copy">
										<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" >
										</ul>
									</div>
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="appendCopyWo()"
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
		</div>
	</div>
</div>
