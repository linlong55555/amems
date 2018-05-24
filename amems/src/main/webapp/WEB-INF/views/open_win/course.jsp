<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/course.js"></script>
<!-- 飞机注册号选择 -->
<div class="modal fade modal-new" id="open_win_course_modal" tabindex="-1" role="dialog" aria-labelledby="open_win_course_modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:60%;">
		<div class="modal-content">	
		<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >课程列表</div>
							<div class="font-size-9" >Course List</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
					</div>
			<div class="modal-body padding-bottom-0">
			<div class="col-xs-12 margin-top-8  ">
              		     <div class="input-group-border padding-leftright-8">
              		     <div class=" pull-right padding-left-0 padding-right-0 margin-top-10">	
			         		
			         			<div class="search-criteria pull-left padding-left-5 padding-right-0" style="width: 300px;">
									<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">机型</div>
										<div class="font-size-9">A/C Type</div>
									</span>
									<div class=" col-xs-8 col-sm-8">
										<select class="form-control" id="fjjx_win_search" name="fjjx_win_search" onchange="open_win_course_modal.search()"></select>
									</div>
								</div>	
			         		
								<!-- 搜索框start -->
								<div class=" pull-right padding-left-0 padding-right-0">
									<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" placeholder='类别/代码/名称/培训目标/形式/备注' id="open_win_course_modal_keyword_search" class="form-control" />
									</div>
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_course_modal.search()">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div> 
								</div>
								<!-- 搜索框end -->
							</div>
							<div class="clearfix"></div>
							<div class="margin-top-10 ">
							<div style="overflow-x:auto;">
								<table class="table table-bordered table-striped table-hover table-set" style="min-width: 1700px !important">
									<thead>
								   		<tr>
											<th class="colwidth-5" id="checkSingle">
												<div class="font-size-12 line-height-18">操作</div>
												<div class="font-size-9 line-height-18">Operation</div>
											</th>
											<th class="colwidth-7" id="checkAll" style='text-align:center;vertical-align:middle;width:50px;'>
												<a href="#" onclick="SelectUtil.performSelectAll('open_win_course_modal_list')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
												<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('open_win_course_modal_list')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
											</th>
											<th class="colwidth-10">
												<div class="important">
													<div class="font-size-12 line-height-18">课程类别</div>
													<div class="font-size-9 line-height-18">Course Type</div>
												</div>
											</th>
											<th class="colwidth-10">
												<div class="important">
													<div class="font-size-12 line-height-18">课程代码</div>
													<div class="font-size-9 line-height-18">Course No.</div>
												</div>
											</th>
											<th class="colwidth-10">
												<div class="important">
													<div class="font-size-12 line-height-18">课程名称</div>
													<div class="font-size-9 line-height-18">Course Name</div>
												</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">机型</div>
												<div class="font-size-9 line-height-18">A/C Type</div>
											</th>
											<th class="colwidth-30">
												<div class="important">
													<div class="font-size-12 line-height-18">培训目标</div>
													<div class="font-size-9 line-height-18">Objective</div>
												</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">初训学时</div>
												<div class="font-size-9 line-height-18">Hour</div>
											</th>
											<th class="colwidth-10">
												<div class="important">
													<div class="font-size-12 line-height-18">初训培训形式</div>
													<div class="font-size-9 line-height-18">Form</div>
												</div>
											</th>
											<th class="colwidth-10" >
												<div class="important">
													<div class="font-size-12 line-height-18">初训考试形式</div>
													<div class="font-size-9 line-height-18">Form</div>
												</div>
											</th>
											<th class="colwidth-9" >
												<div class="font-size-12 line-height-18">是/否复训</div>
												<div class="font-size-9 line-height-18">Whether</div>
											</th>
											<th class="colwidth-10" >
												<div class="font-size-12 line-height-18">复训间隔</div>
												<div class="font-size-9 line-height-18">Interval</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">复训学时</div>
												<div class="font-size-9 line-height-18">Hour</div>
											</th>
											<th class="colwidth-10">
												<div class="important">
													<div class="font-size-12 line-height-18">复训培训形式</div>
													<div class="font-size-9 line-height-18">Form</div>
												</div>
											</th>
											<th class="colwidth-10">
												<div class="important">
													<div class="font-size-12 line-height-18">复训考试形式</div>
													<div class="font-size-9 line-height-18">Form</div>
												</div>
											</th>
											<th class="colwidth-30">
												<div class="important">
													<div class="font-size-12 line-height-18">备注</div>
													<div class="font-size-9 line-height-18">Note</div>
												</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="open_win_course_modal_list">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="open_win_course_modal_pagination"></div>
							</div>
							<div class="clearfix"></div>
				</div>
				</div>
				<div class="clearfix"></div>
				</div>
				<div class="modal-footer">
			           	<div class="col-xs-12 padding-leftright-8" >
							<div class="input-group">
								<span class="input-group-addon modalfootertip" >
									<!-- <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p> -->
								</span>
			                    <span class="input-group-addon modalfooterbtn">
				                   <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_course_modal.save();" data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
			                    </span>
			               	</div>
						</div>
					</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>

