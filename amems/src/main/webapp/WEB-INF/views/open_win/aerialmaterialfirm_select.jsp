<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<div class="modal fade" id="open_win_account_select_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">供应商列表</div>
						<div class="font-size-9 ">Account List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12">
			         		
			         		<!-- 搜索框start -->
							<div class=" pull-right padding-left-0 padding-right-10 margin-top-10">
							
										<div
						class=" pull-left padding-left-5 padding-right-0" style="width: 280px;">					
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">供应商类型</div>
							<div class="font-size-9">Supplier Type</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="gyslb" class="form-control ">
									<option value="1">航材供应商</option>
									<option value="2">外围供应商</option>
							</select>
						</div>					
					</div>
								<div class=" pull-left padding-left-10 padding-right-0" style="width: 250px;">
									<input type="text" placeholder='账号' id="open_win_account_select_username_search" class="form-control ">
								</div>
				                <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_account_select.search()">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                  		</button>
				                </div> 
							</div>
							<!-- 搜索框end -->
			         		
			         		<div class="clearfix"></div>	
			         		
							<!-- start:table -->
							<div class="margin-top-10 ">
							<div class="overflow-auto">
								<table class="table table-bordered table-striped table-hover table-set text-center" style="">
									<thead>
								   		<tr>
											<th width="50px">
												<div class="font-size-12 notwrap">选择</div>
												<div class="font-size-9 notwrap">Choice</div>
											</th>
											<th class="colwidth-15 sorting" onclick="orderBy('gysbm')" id="gysbm_order">
									<div class="important">
										<div class="font-size-12 line-height-18">供应商编号</div>
										<div class="font-size-9 line-height-18">Supplier No.</div>
									</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('gysmc')" id="gysmc_order">
									<div class="important">
										<div class="font-size-12 line-height-18">供应商名称</div>
										<div class="font-size-9 line-height-18">Supplier Name</div>
									</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('pzh')" id="pzh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">批准号</div>
										<div class="font-size-9 line-height-18">APR No.</div>
									</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('zssm')" id="zssm_order">
									<div class="important">
										<div class="font-size-12 line-height-18">证书说明</div>
										<div class="font-size-9 line-height-18">Document Remark</div>
									</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('sqkssj')" id="sqkssj_order">
									<div class="font-size-12 line-height-18">批准日期</div>
									<div class="font-size-9 line-height-18">APR Date</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('sqjssj')" id="sqjssj_order">
									<div class="font-size-12 line-height-18">有效期</div>
									<div class="font-size-9 line-height-18">EXPY Date</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('SYTS')" id="SYTS_order">
									<div class="font-size-12 line-height-18">剩余天数</div>
									<div class="font-size-9 line-height-18">Remain(Day)</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('bz')" id="bz_order">
									<div class="important">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('username')" id="username_order">
									<div class="important">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('cjsj')" id="cjsj_order">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
								 		 </tr>
									</thead>
									<tbody id="open_win_account_select_list">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="open_win_account_select_pagination"></div>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" onclick="open_win_account_select.save()"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									>
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
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
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/aerialmaterialfirm_select.js"></script>
