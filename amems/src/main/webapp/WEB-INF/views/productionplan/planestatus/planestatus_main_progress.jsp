<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
       <div class="panel-body" style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;">
				<!-----标签导航start---->
				<p class="nav nav-tabs" style="padding-bottom:10px;padding-top:10px;" id='wcsTab'>
					完工数/未关闭任务数
				</p>
                 <!-----标签内容start---->
				<div class="tab-content padding-left-0 padding-right-0">
                   <div class="progress progress-striped" style='margin-bottom:10px;margin-left:8px;margin-right:8px;'id='wcsTabProgress' >
					    <div class="progress-bar progress-bar-success text-left" role="progressbar" aria-valuenow="60" 
					        aria-valuemin="0" aria-valuemax="100"  id='progressbar'>
					        <p style='text-align:left;margin-left:10px;' id='wcspan1'></p>
					     </div>
					     <p style='text-align:right;color:red;margin-right:10px;' id='wcspan2'></p>
					</div>
					<!-- 表格 -->
						<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" id='wcs_list_tableDiv' style="overflow-x: auto;">
							<table id="wcs_list_table" class="table table-thin table-bordered table-set" >
								<thead>
									<tr>
										<th class="colwidth-3" onclick="orderBy('wcswg')" id="wcswg_order">
											<div class="font-size-12 line-height-18">完工</div>
											<div class="font-size-9 line-height-18">Complete</div>
										</th>
										<th class="sorting colwidth-13" onclick="orderBy('wcsrw')" id="wcsrw_order">
											<div class="important">
												<div class="font-size-12 line-height-18">任务</div>
												<div class="font-size-9 line-height-18">Task</div>
											</div>
										</th>
										<th class="sorting colwidth-7" onclick="orderBy('wcsly')" id="wcsly_order">
											<div class="font-size-12 line-height-18">来源</div>
											<div class="font-size-9 line-height-18">Original</div>
										</th>
										<th class="sorting colwidth-13" onclick="orderBy('wcsly')" id="wcsbz_order">
											<div class="font-size-12 line-height-18">备注</div>
											<div class="font-size-9 line-height-18">Remark</div>
										</th>
									</tr>
								</thead>
								<tbody id="wgslist">
								</tbody>
		
							</table>
						</div>
						<!-- <div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="paginationWcs"></div> -->
                        <div class='clearfix'></div>
				</div>
		</div>
        <script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/planestatus/planestatus_main_progress.js"></script>