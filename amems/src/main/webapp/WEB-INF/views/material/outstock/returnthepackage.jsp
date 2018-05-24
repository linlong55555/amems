<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/outstock/returnthepackage.js"></script>
<div class="modal fade" id="returnthepackage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:70%">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">归还航材列表</div>
						<div class="font-size-9 ">Aircraft Returned List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
						    <div class="text-left margin-top-10 " >
						    	<button type="button" onclick="opeen_win_returnthepackage.search()"
									class="btn btn-primary padding-top-1 padding-bottom-1 pull-right"
									>
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
								</button>
								<div style="padding-right:9px!important;" class="col-xs-4 pull-right">
									<input type="text" class="form-control " id="inventorykeyword_search" placeholder="件号/中英文"/>
									
								</div>
					
								
								<div class="clearfix"></div>			
			         		</div>
							<!-- start:table -->
							<div class="margin-top-10 ">
							<div class="overflow-auto">
								<table class="table table-bordered table-striped table-hover text-center table-set" style="min-width:900px">
									<thead>
								   		<tr>
											<th class="colwidth-5" style="vertical-align: middle;">
												<div class="font-size-12 notwrap">选择</div>
												<div class="font-size-9 notwrap">Choice</div>
											</th>
											<th class="colwidth-5">
												<div class="font-size-12 notwrap">序号</div>
												<div class="font-size-9 notwrap">No.</div>
											</th>
											<th class="colwidth-8">
												<div class="important">
												<div class="font-size-12 notwrap">件号</div>
												<div class="font-size-9 notwrap">P/N</div>
												</div>
											</th>
											<th class="colwidth-20">
												<div class="important">
												<div class="font-size-12 notwrap">中文名称</div>
												<div class="font-size-9 notwrap">CH.Name</div>
												</div>
											</th>
											<th class="colwidth-20">
												<div class="important">
												<div class="font-size-12 notwrap">英文名称</div>
												<div class="font-size-9 notwrap">F.Name</div>
												</div>
											</th>
											<th class="colwidth-8">
											<div class="important">
												<div class="font-size-12 notwrap">厂家件号</div>
												<div class="font-size-9 notwrap">MP/N</div>
												</div>
											</th>
											<th class="colwidth-8">
												<div class="font-size-12 notwrap">航材类型</div>
												<div class="font-size-9 notwrap">Airmaterial type</div>
											</th>
											<th class="colwidth-8">
												<div class="font-size-12 notwrap">单位</div>
												<div class="font-size-9 notwrap">Unit</div>
											</th>
											<th class="colwidth-8">
												<div class="font-size-12 notwrap">待还数量</div>
												<div class="font-size-9 notwrap">To be returned</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="userlist1">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center" id="pagination">
								</div>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" onclick="opeen_win_returnthepackage.setUser()"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									data-dismiss="modal">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" onclick="opeen_win_returnthepackage.clearUser()"
									class="btn btn-primary padding-top-1 padding-bottom-1" id="opeen_win_returnthepackage_clear"
									data-dismiss="modal">
									<div class="font-size-12">清除</div>
									<div class="font-size-9">Clear</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
								
			                </div>
			     			<div class="clearfix"></div>
						</div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
</div>
