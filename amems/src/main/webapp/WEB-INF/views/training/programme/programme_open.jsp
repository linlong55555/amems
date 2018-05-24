<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<div class="modal fade in modal-new" id="alertModalOpen" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="open_win_evaluation_modal" aria-hidden="true">
	<div class="modal-dialog" style="width:70%;">
		<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
                          	<div class="font-size-12 ">人员岗位要求</div>
							<div class="font-size-9">Personnel Requirements</div>
						  </div>
						   <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
			  <div class="modal-body" style='padding-top:0px;'>
              	<div class="input-group-border" style='margin-top:8px;padding-top:5px;margin-bottom:8px;'>
			         		
						<!-- start:table -->
						<div class="margin-left-8 margin-right-8" style='margin-top:5px;'>
							<div class="table-content" style="overflow-x:auto;" id="searchTable">
								<table class="table table-bordered table-striped table-hover table-set table-thin" style="min-width: 1600px !important">
									<thead>
								   		<tr>
											<th class="colwidth-15"><div  class="font-size-12 line-height-18">岗位代码/名称</div><div class="font-size-9 line-height-18" >Post Code/Name</div></th>
											<th class="colwidth-8"><div class="font-size-12 line-height-18">状态</div><div class="font-size-9 line-height-18" >State</div></th>
											<th class="colwidth-10"><div class="font-size-12 line-height-18" >课程代码</div><div class="font-size-9 line-height-18" >Course Code</div></th>
											<th class="colwidth-10"><div  class="font-size-12 line-height-18">课程名称</div><div class="font-size-9 line-height-18" >Course Name</div></th>
											<th class="colwidth-10"><div  class="font-size-12 line-height-18">飞机机型</div><div class="font-size-9 line-height-18" >A/C Type</div></th>
											<th class="colwidth-10"><div  class="font-size-12 line-height-18">类别</div><div class="font-size-9 line-height-18" >Type</div></th>
											<th class="colwidth-10"><div  class="font-size-12 line-height-18">人员编号</div><div class="font-size-9 line-height-18" >Personel No</div></th>
											<th class="colwidth-10"><div  class="font-size-12 line-height-18">人员名称</div><div class="font-size-9 line-height-18" >Personel Name</div></th>
											<th class="colwidth-8"><div  class="font-size-12 line-height-18">是否复训</div><div class="font-size-9 line-height-18" >YES/NO</div></th>
											<th class="colwidth-10"><div class="font-size-12 line-height-18" >复训周期</div><div class="font-size-9 line-height-18" >Periodic</div></th>
											<th class="colwidth-10"><div class="font-size-12 line-height-18">下次培训日期</div><div class="font-size-9 line-height-18" > Date</div></th>
											<th class="colwidth-10"><div  class="font-size-12 line-height-18">上次培训日期</div><div class="font-size-9 line-height-18" > Date</div></th>
											<th class="colwidth-10"><div  class="font-size-12 line-height-18">培训计划</div><div class="font-size-9 line-height-18" > Plan</div></th>
											<th class="colwidth-10"><div  class="font-size-12 line-height-18">培训机构</div><div class="font-size-9 line-height-18" > Institution</div></th>
											<th class="colwidth-10"><div  class="font-size-12 line-height-18">培训地点</div><div class="font-size-9 line-height-18" > Location</div></th>
											<th class="colwidth-10"><div class="font-size-12 line-height-18">培训讲师</div><div class="font-size-9 line-height-18" >Trainer</div></th>
											<th class="colwidth-10"><div  class="font-size-12 line-height-18">出勤率(%)</div><div class="font-size-9 line-height-18" >Attendance(%)</div></th>
											<th class="colwidth-10"><div class="font-size-12 line-height-18" >成绩</div><div class="font-size-9 line-height-18" >Achievement</div></th>
											<th class="colwidth-10"><div  class="font-size-12 line-height-18">证书</div><div class="font-size-9 line-height-18" >Certificate</div></th>
											<th class="colwidth-10"><div  class="font-size-12 line-height-18">考核结果</div><div class="font-size-9 line-height-18" >Result</div></th>
										</tr>
									</thead>
									<tbody id="matlist">
									</tbody>
								</table>
								</div>
							 <div class="clearfix"></div> 
							</div>
							</div>
							<div class="clearfix"></div>  
							</div>
							<!-- end:table -->
							 <div class="modal-footer">
				           		<div class="col-xs-12 padding-leftright-8" >
									<div class="input-group">
					                    <span class="input-group-addon modalfooterbtn">
												<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
													<div class="font-size-12">关闭</div>
													<div class="font-size-9">Close</div>
												</button>
					                    </span>
					               	</div><!-- /input-group -->
								</div>
				           
								<div class="clearfix"></div> 
								
							</div>
		</div>
	</div>
</div>
