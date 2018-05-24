<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
			<input type="hidden" id="manidinfo" />
			<div class="panel panel-primary margin-bottom-0">
				<div class="panel-heading bg-panel-heading">
					<div class="font-size-12 line-height-12">人员信息</div>
					<div class="font-size-9 line-height-9">Information</div>
				</div>
				
				<div class="panel-body  padding-bottom-0 padding-left-0 padding-right-0 padding-top-0 " style="overflow-x:auto;">
		
					<div class="bottom-hidden-table-content padding-top-0 padding-left-0 padding-right-0">
						<table
							class="table table-thin table-bordered table-striped text-center table-set" id="flight_record_sheet_table">
							<thead>
								<tr>
									
									<th class="colwidth-5 " >
										<div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div>
									</th>
									<th class="colwidth-10 " >
										<div class="font-size-12 line-height-18">人员代码</div>
										<div class="font-size-9 line-height-18">Personnel Code</div>
									</th>
									<th class="colwidth-10 " >
										<div class="font-size-12 line-height-18">人员</div>
										<div class="font-size-9 line-height-18">Personnel</div>
									</th>
									<th class="colwidth-10 " >
										<div class="font-size-12 line-height-18">工作单位/部门</div>
										<div class="font-size-9 line-height-18">Work/Dept</div>
									</th>
									<th class="colwidth-5">
										<div class="font-size-12 line-height-18">应参</div>
										<div class="font-size-9 line-height-18">Should</div>
									</th>
									<th class="colwidth-5">
										<div class="font-size-12 line-height-18">实参</div>
										<div class="font-size-9 line-height-18">Real</div>
									</th>
									<th class="colwidth-7 " >
										<div class="font-size-12 line-height-18">出勤率%</div>
										<div class="font-size-9 line-height-18">Attendance%</div>
									</th>
									<th class="colwidth-10 " >
										<div class="font-size-12 line-height-18">成绩</div>
										<div class="font-size-9 line-height-18">Result</div>
									</th>
									<th class="colwidth-10 " >
										<div class="font-size-12 line-height-18">考核结果</div>
										<div class="font-size-9 line-height-18">Result</div>
									</th>
									<th class="colwidth-10 " >
										<div class="font-size-12 line-height-18">证书</div>
										<div class="font-size-9 line-height-18">Certificate</div>
									</th>
									
									<th class="colwidth-10 " >
										<div class="font-size-12 line-height-18">下次培训日期</div>
										<div class="font-size-9 line-height-18">Next Date</div>
									</th>
									<th class="colwidth-15 " >
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</th>
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
		    <div class="modal-dialog"  >
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
								<div class="font-size-16 line-height-18" id="accredit1"></div>
							<div class="font-size-9 " id="accreditrec1"></div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12 col-sm-12">
			            	<input type="hidden" id="infoId" />
			            	<form id="form">
			            	<div class="col-xs-12 col-sm-12 col-lg-12 padding-right-0 padding-left-0 margin-top-10  form-group">
								<label class="pull-left col-lg-2 col-xs-2 col-sm-2 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12"><span style="color:red">*</span>维修档案人员</div>
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


			
	
									</form>    
					     		<div class="clearfix"></div>
					     		<div class="text-right margin-top-10 padding-buttom-10 ">
                               <button onclick="saveinfo()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" ><div
										class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div></button>
                     	      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
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
	<%@ include file="/WEB-INF/views/open_win/personel_tree_multi.jsp"%><!-------用户对话框 -------->
	<!-- 新增单次故障处理 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/training/course/recordspersonnel_main.js"></script>
		