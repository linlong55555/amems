<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
    <div class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0">
                	<ul id="myTab" class="nav nav-tabs tabNew-style">
                      	<li class="active"><a id="aaa" href="#Dropdown" data-toggle="tab" aria-expanded="false" >课程信息</a></li>
                        <li class=""><a id="bbb" href="#Dropdown2" data-toggle="tab" aria-expanded="false" >岗位要求</a></li>                     	
                      	<li class=""><a href="#profile" data-toggle="tab" aria-expanded="false" >人员信息</a></li>
                    </ul>
                    <div id="myTabContent" class="tab-content" >
                      	
                      	<div class="tab-pane active" id="Dropdown"  >
                      		<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 row-height">
						<div class="col-xs-5  padding-left-0 row-height margin-bottom-10">
					<button type="button" onclick="add()"  
						class=" col-lg-2 btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height checkPermission"
						permissioncode="training:programme:main:01">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</button>	
					</div>
						</div>
                      	
                      	
							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 pull-right margin-bottom-10" style="overflow-x: auto;width: 100%;">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set " id="quality_check_list_table" >
									<thead>
								   		<tr>
											<th  class=" colwidth-5">
												<div class="font-size-12 line-height-18">操作</div>
												<div class="font-size-9 line-height-18">Operation</div>
											</th>
											<th  class=" colwidth-7">
												<div class="font-size-12 line-height-18">课程代码</div>
												<div class="font-size-9 line-height-18">Course Code</div>
											</th>
											<th class="colwidth-10" >
													<div class="font-size-12 line-height-18">课程名称</div>
													<div class="font-size-9 line-height-18">Course Name</div>
											</th>
											<!-- <th class="colwidth-10">
													<div class="font-size-12 line-height-18">课程类别</div>
													<div class="font-size-9 line-height-18">Course Type</div>
											</th>
											<th class="colwidth-10" >
													<div class="font-size-12 line-height-18">课程代码</div>
													<div class="font-size-9 line-height-18">Course Code</div>
											</th>
											<th class="colwidth-30">
													<div class="font-size-12 line-height-18">培训目标</div>
													<div class="font-size-9 line-height-18">Training Objective</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">初训学时</div>
												<div class="font-size-9 line-height-18">Hour</div>
											</th>
											<th class="colwidth-10">
													<div class="font-size-12 line-height-18">初训培训形式</div>
													<div class="font-size-9 line-height-18">Form</div>
											</th>
											<th class="colwidth-10">
													<div class="font-size-12 line-height-18">初训考试形式</div>
													<div class="font-size-9 line-height-18">Form</div>
												</div>
											</th>
											<th class="colwidth-9">
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
													<div class="font-size-12 line-height-18">复训培训形式</div>
													<div class="font-size-9 line-height-18">Form</div>
											</th>
											<th class="colwidth-10">
													<div class="font-size-12 line-height-18">复训考试形式</div>
													<div class="font-size-9 line-height-18">Form</div>
											</th>
											<th class="colwidth-30">
													<div class="font-size-12 line-height-18">备注</div>
													<div class="font-size-9 line-height-18">Remark</div>
											</th> -->
											<th class=" colwidth-10">
													<div class="font-size-12 line-height-18">教员要求</div>
													<div class="font-size-9 line-height-18">Faculty</div>
											</th>
											<th class=" colwidth-10 ">
													<div class="font-size-12 line-height-18">培训机构</div>
													<div class="font-size-9 line-height-18">Training</div>
											</th>
											<th class=" colwidth-10 ">
													<div class="font-size-12 line-height-18">来源</div>
													<div class="font-size-9 line-height-18">Source</div>
											</th>
											<th class=" colwidth-10 ">
													<div class="font-size-12 line-height-18">教材/提纲</div>
													<div class="font-size-9 line-height-18">Outline</div>
											</th>
												
							 		 </tr>
									</thead>
									<tbody id="kcList">
									
									</tbody>
								</table>
							</div>
                      	</div>
                    <div class="tab-pane fade" id="Dropdown2"  >
                      		<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 row-height">
						<div class="col-xs-5  padding-left-0 row-height margin-bottom-10">
					<button type="button" onclick="addWorkRequires()"  
						class=" col-lg-2 btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height checkPermission"
						permissioncode="training:programme:main:04">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</button>	
					</div>
						</div>
                      	
                      		                  	
							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 pull-right margin-bottom-10" style="overflow-x: auto;width: 100%;">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set " id="work_require_list_table" style="min-width: 800px !important">
									<thead>
								   		<tr>
											<th  class="colwidth-3">
												<div class="font-size-12 line-height-18">操作</div>
												<div class="font-size-9 line-height-18">Operation</div>
											</th>
											<th class="colwidth-35">
													<div class="font-size-12 line-height-18">岗位要求</div>
													<div class="font-size-9 line-height-18">Require</div>
											</th>
											
							 		 </tr>
									</thead>
									<tbody id="gxList">
									
									</tbody>
								</table>
							</div>
                      	</div>
                      	<div class="tab-pane fade" id="profile" style="overflow-x: auto;width: 100%;">
                      		<table class="table table-thin table-bordered table-striped table-hover text-center table-set" id="quality_check_list_table" >
							<thead>
						   		<tr>
						   			<th  class="colwidth-5" >
										<div class="font-size-12 line-height-18">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="colwidth-5">
										<div class="font-size-12 line-height-18">人员编号</div>
										<div class="font-size-9 line-height-18">No.</div>
									</th > 
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-18">名称</div>
										<div class="font-size-9 line-height-18">Name</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-18">类别</div>
										<div class="font-size-9 line-height-18">Type</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-18">开始有效期</div>
										<div class="font-size-9 line-height-18">Start Validity</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-18">截止有效期</div>
										<div class="font-size-9 line-height-18">End Validity</div>
									</th>
									<th class="colwidth-5" >
										<div class="font-size-12 line-height-18">部门</div>
										<div class="font-size-9 line-height-18">Department</div>
									</th>
									
					 		 </tr>
							</thead>
							<tbody id="ryList">
					
					</tbody>
				</table>
                      	</div>
		                  </div>
		              </div>
