<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0"
					>
					<table
						class="table table-thin table-bordered table-striped text-center table-set" id="flight_record_sheet_table">
						<thead>
							<tr>
								<th class="colwidth-5 fixed-column">
									<div class="font-size-12 line-height-18">类型</div>
									<div class="font-size-9 line-height-18">Type</div></th>

								<th class="colwidth-15 sorting" onclick="orderBy('hbh')"
									id="hbh_order">
									<div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-18">Flight number</div></th>
								<th class="colwidth-10 sorting" onclick="orderBy('rq')"
									id="rq_order">
									<div class="important">
										<div class="font-size-12 line-height-18">名称</div>
										<div class="font-size-9 line-height-18">Name</div>
									</div>
								</th>
									
								<th class="colwidth-13 sorting" onclick="orderBy('cs')"
									id="cs_order">
									<div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-18">Manufacturer no.</div></th>
								<th class="colwidth-13 sorting" onclick="orderBy('cljg')"
									id="cljg_order">
									<div class="font-size-12 line-height-18">需求数量</div>
									<div class="font-size-9 line-height-18">Demand quantity</div></th>
								<th class="colwidth-10 sorting" onclick="orderBy('zlh')"
									id="zlh_order">
									<div class="font-size-12 line-height-18">在库数量</div>
									<div class="font-size-9 line-height-18">Number of Libraries in</div></th>
								<th class="colwidth-13 sorting" onclick="orderBy('cx')"
									id="cx_order">
										<div class="font-size-12 line-height-18">可用数量</div>
										<div class="font-size-9 line-height-18">Available quantity</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('zs')"
									id="zs_order">
									<div class="important">
										<div class="font-size-12 line-height-18">替换件可用数量</div>
										<div class="font-size-9 line-height-18">Quantity</div>
									</div>
								</th>
							</tr>
						</thead>
						<tbody id='zygl_list'>

						</tbody>

					</table>
				</div>
                <div class="modal fade" id="alertModalThjkysl" tabindex="-1" role="dialog" aria-labelledby="alertModalThjkysl" aria-hidden="true">
		<div class="modal-dialog" style='width:1000px;'>
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" >
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">替换件信息</div>
							<div class="font-size-9 ">Replacement Info</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
					       
								   <div  class="col-xs-12 padding-left-0 padding-right-0 padding-top-10" >
									  <label>件号：<span class='color-red'>HNMM</span></label>
									  <label style='margin-left:20px;'>名称：<span class='color-red'>HNMM</span></label>
					               </div>
					           
				                <div class="clearfix"></div>	     
								<div  style="overflow-x: auto;margin-top: 10px">
									<table class="table-set table table-thin table-bordered table-striped table-hover ">
										<thead>
											<tr>
											<th class="colwidth-13"><div class="font-size-12 line-height-18" >替换件号</div>
												<div class="font-size-9 line-height-18" >Replacement part no.</div></th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">替换件名称</div>
												<div class="font-size-9 line-height-16">Replacement name</div>
											
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">适用性</div>
												<div class="font-size-9 line-height-16">Applicability</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">可逆性</div>
												<div class="font-size-9 line-height-16">Reversible</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">在库数量</div>
												<div class="font-size-9 line-height-16">Number of Libraries</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">可用数量</div>
												<div class="font-size-9 line-height-16">Available quantity</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">附件</div>
												<div class="font-size-9 line-height-16">Attachment</div>
											</th>
											</tr> 
						         		 </thead>
										<tbody id="Thjxx_list">
										</tbody>
									</table>
									</div>
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="appendHcxx()"
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
			
			
			<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/plantask/plantask_managedetail.js"></script> 