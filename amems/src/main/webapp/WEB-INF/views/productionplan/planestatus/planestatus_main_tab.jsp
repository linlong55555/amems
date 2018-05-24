<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="panel-body" style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;">
				<!-----标签导航start---->
				<div  class="nav nav-tabs" style='padding-top:0px;padding-left:0px;' id='djTab'>
				<ul class="nav nav-tabs pull-left" role="tablist" id="tablist" style='margin-bottom:0px;margin-top:0px;border:0px;'>
					<li class="active" onclick='showDj()'><a href="#djRecord" data-toggle="tab" aria-expanded="true">定检&nbsp;<span class="badge" style='background:red;' id='djspan'></span></a></li>
					<li ><a href="#skRecord" data-toggle="tab"  aria-expanded="true" onclick='showSk()'>时控/时寿&nbsp;<span class="badge" style='background:red;' id='ssspan'></span></a></li>
					<li ><a href="#qtgdRecord" data-toggle="tab"  aria-expanded="true" onclick='showQygd()'>其他工单&nbsp;<span class="badge" style='background:red;' id='qtgdspan'></span></a></li>
				</ul>
				<div class=" pull-right padding-left-0 padding-right-0" style='margin-top:2px;' id='searchTabBtn'>
				    <div class=" pull-left padding-left-0 padding-right-0" style="width: 150px;">
						<input type="text" placeholder='件号/控制/剩余/备注' id="djkeyword_search" class="form-control" style='border-top-right-radius:0px;border-bottom-right-radius:0px;'>
					</div>
                    <div class=" pull-right padding-right-0 ">   
						<button type="button" class=" btn btn-primary" style='padding-top:4px;padding-bottom:4px;padding-left:8px;padding-right:8px;border-top-left-radius:0px;border-bottom-left-radius:0px;' onclick="searchDj();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                     </div> 
			     </div>
			     <div class='clearfix'></div>
			     </div>
                 <!-----标签内容start---->
				<div class="tab-content  padding-left-0 padding-right-0" style='padding-top:0px;'>
                    <div class="tab-pane fade in active" id="djRecord" >
                       <!-- 搜索框start -->
						<!-- <div class=" pull-right padding-left-0 padding-right-0">
						    <div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
								<input type="text" placeholder='定检项目/件号/剩余/备注' id="djkeyword_search" class="form-control ">
							</div>
		                    <div class=" pull-right padding-left-5 padding-right-0 ">   
								<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchDj();">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
		                   		</button>
		                     </div> 
						</div> -->
						<!-- 搜索框end -->
                        <!-- 表格 -->
						<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" id="dj_list_tableDiv"  style="overflow-x: auto;">
							<table id="dj_list_table" class="table table-thin table-bordered table-set" >
								<thead>
									<tr>
										<th class="colwidth-7" onclick="orderBy('djxm')" id="djxm_order">
											<div class="font-size-12 line-height-18">定检项目</div>
											<div class="font-size-9 line-height-18">A/C REG</div>
										</th>
										<th class="sorting colwidth-7" onclick="orderBy('djjh')" id="djjh_order">
											<div class="important">
												<div class="font-size-12 line-height-18">件号</div>
												<div class="font-size-9 line-height-18">S/N</div>
											</div>
										</th>
										<th class="sorting colwidth-13" onclick="orderBy('djsy')" id="djsy_order">
											<div class="font-size-12 line-height-18">剩余</div>
											<div class="font-size-9 line-height-18">Seaworthiness</div>
										</th>
										<th class="sorting colwidth-13" onclick="orderBy('djbz')" id="djbz_order">
											<div class="font-size-12 line-height-18">备注</div>
											<div class="font-size-9 line-height-18">Remark</div>
										</th>
									</tr>
								</thead>
								<tbody id="djlist">
								</tbody>
		
							</table>
						</div>
                        <div class='clearfix'></div>
					</div>
					<div class="tab-pane fade" id="skRecord">
						<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" id="sk_list_tableDiv"  style="overflow-x: auto;">
							<table id="sk_list_table" class="table table-thin table-bordered table-set" >
								<thead>
									<tr>
										<th class="fixed-column colwidth-5" onclick="orderBy('skjh')" id="skjh_order">
											<div class="font-size-12 line-height-18">件号</div>
											<div class="font-size-9 line-height-18">A no.</div>
										</th>
										<th class="sorting fixed-column colwidth-5" onclick="orderBy('skkz')" id="skkz_order">
											<div class="important">
												<div class="font-size-12 line-height-18">控制</div>
												<div class="font-size-9 line-height-18">Control</div>
											</div>
										</th>
										<th class="sorting colwidth-13" onclick="orderBy('sksy')" id="sksy_order">
											<div class="font-size-12 line-height-18">剩余</div>
											<div class="font-size-9 line-height-18">Seaworthiness</div>
										</th>
										<th class="sorting colwidth-13" onclick="orderBy('skbz')" id="skbz_order">
											<div class="font-size-12 line-height-18">备注</div>
											<div class="font-size-9 line-height-18">Remark</div>
										</th>
									</tr>
								</thead>
								<tbody id="sklist">
								</tbody>
		
							</table>
						</div>
						<!-- <div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="paginationSk"></div> -->
                        <div class='clearfix'></div>
					</div>
					<div class="tab-pane fade" id="qtgdRecord">
						<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" id="qtgd_list_tableDiv"  style="overflow-x: auto;">
							<table id="qtgd_list_table" class="table table-thin table-bordered table-set" >
								<thead>
									<tr>
										<th class="colwidth-7" onclick="orderBy('qtgddh')" id="qtgddh_order">
											<div class="font-size-12 line-height-18">单号</div>
											<div class="font-size-9 line-height-18">Work order</div>
										</th>
										<th class="sorting colwidth-7" onclick="orderBy('qtgdlx')" id="qtgdlx_order">
											<div class="important">
												<div class="font-size-12 line-height-18">类型</div>
												<div class="font-size-9 line-height-18">Type</div>
											</div>
										</th>
										<th class="sorting colwidth-13" onclick="orderBy('qtgdsy')" id="qtgdsy_order">
											<div class="font-size-12 line-height-18">剩余</div>
											<div class="font-size-9 line-height-18">Seaworthiness</div>
										</th>
										<th class="sorting colwidth-13" onclick="orderBy('qtgdxfyy')" id="qtgdxfyy_order">
											<div class="font-size-12 line-height-18">下发原因</div>
											<div class="font-size-9 line-height-18">Reason</div>
										</th>
									</tr>
								</thead>
								<tbody id="qtgdlist">
								</tbody>
		
							</table>
						</div>
						<!-- <div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="paginationQtgd"></div> -->
                        <div class='clearfix'></div>
					</div>
				</div>
			</div>
			
<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/planestatus/planestatus_main_tab.js"></script>