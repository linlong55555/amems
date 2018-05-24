<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/trainingRecords_win_list.js"></script>
<div class="modal fade in modal-new" id="trainingRecords_main" tabindex="-1" role="dialog"  aria-labelledby="trainingRecords_main" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width: 60%;" >
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
           			<div class='pull-left'>
               			<div class="font-size-12 ">培训计划</div>
						<div class="font-size-9">Training List</div>
				 	</div>
 					<div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
          	</div>
           	<div class="modal-body" style='padding-top:0px;'>
             	<div class="input-group-border" style='margin-top:8px;padding-top:5px;'>
                   	<div class=" padding-left-0 padding-right-8 margin-top-0 modalSearch">	
       		
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 padding-left-0 padding-right-0 margin-top-0 form-group" >
							  <span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">课程代码</div>
								<div class="font-size-9 ">Course Code</div>
							</span>  
							<div class="col-sm-9 col-xs-12 padding-leftright-8">
								<input id="kcdm" disabled="disabled" class="form-control" type="text">
						  </div>
						</div>
						
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 padding-left-0 padding-right-0 margin-top-0 form-group" >
							  <span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">维修人员</div>
								<div class="font-size-9 ">Personnel</div>
							</span>  
							<div class="col-sm-9 col-xs-12 padding-leftright-8">
								<input id="wxry" disabled="disabled" class="form-control" type="text">
						  </div>
						</div>
						
					</div>
	            	
		         	<div class="clearfix"></div>
		         		
					<!-- start:table -->
					<div id="trainingRecords_main_top_div" class="margin-left-8 margin-right-8" style='margin-top:5px;margin-bottom:5px;'>
						<div class='col-xs-12 padding-left-0 padding-right-0 table-content' id="searchTable">
							<table id="trainingRecords_table_list" class="table table-thin table-bordered table-striped table-hover table-set" >
								<thead>
								   		<tr>
											<th class="colwidth-10" >
												<div class="font-size-12 line-height-18">课程名称</div>
												<div class="font-size-9 line-height-18">Business Name</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">培训时间</div>
												<div class="font-size-9 line-height-18">Date</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">机型</div>
												<div class="font-size-9 line-height-18">A/C Type</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">专业</div>
												<div class="font-size-9 line-height-18">Professional</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">培训类别</div>
												<div class="font-size-9 line-height-18">Business Type</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">初/复训标识</div>
												<div class="font-size-9 line-height-18">Whether</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">培训形式</div>
												<div class="font-size-9 line-height-18">Business Form</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">培训机构</div>
												<div class="font-size-9 line-height-18">Business Name</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">培训地点</div>
												<div class="font-size-9 line-height-18">Business Location</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">讲师</div>
												<div class="font-size-9 line-height-18">Business Lecturer</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">出勤率</div>
												<div class="font-size-9 line-height-18">Attendance</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">成绩</div>
												<div class="font-size-9 line-height-18">Desc</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">证书</div>
												<div class="font-size-9 line-height-18">Certificate</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">考核结果</div>
												<div class="font-size-9 line-height-18">Results</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">备注</div>
												<div class="font-size-9 line-height-18">Desc</div>
											</th>
											<th class=" colwidth-10 ">
												<div class="font-size-12 line-height-18">附件</div>
												<div class="font-size-9 line-height-18">Attachment</div>
											</th>
												
							 		 </tr>
									</thead>
									<tbody id="trainingRecords_tbody_list">
									
									</tbody>
							</table>
						</div>
						<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="trainingRecords_Pagination"></div>
						
						<div class="clearfix"></div>  
					</div>
				</div>
               <div class="clearfix"></div>              
          </div>
          <div class="clearfix"></div>  
          <div class="modal-footer">
          		<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
						</span>
	                   	<span class="input-group-addon modalfooterbtn">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
	                   	</span>
              		</div><!-- /input-group -->
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
<!--  弹出框结束-->