<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade double-pop-up" id="myModalFive" tabindex="-1" role="dialog"  aria-labelledby="myModalFive" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" >
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-12 ">维修方案描述</div>
							<div class="font-size-9">Description</div>
						  </div>
						  <div class='pull-right' style='padding-top:10px;'>
						  	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" style='font-size:30px !important;' >&times;</span></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" style='padding-top:0px;'>
              	<div class="input-group-border" style='margin-top:8px;padding-top:5px;margin-bottom:8px;'>
                        <div class=" pull-right padding-left-0 padding-right-8 margin-top-0 modalSearch">	
			         		
								<!-- 搜索框start -->
								<div class=" pull-right padding-left-0 padding-right-0">
									<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" placeholder='评估单号' id="open_win_evaluation_modal_keyword_search" class="form-control" />
									</div>
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_evaluation_modal.search()">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div> 
								</div>
								<!-- 搜索框end -->
							</div>
		            	
			         		<div class="clearfix"></div>
			         		
						<!-- start:table -->
						<div class="margin-left-8 margin-right-8" style='margin-top:5px;'>
							<div style="overflow-x:auto;" id="searchTable">
								<table class="table table-bordered table-striped table-hover table-set" style="min-width: 1700px !important">
									<thead>
								   		<tr>
								   			<th class=" colwidth-5" id="checkSingle">
												<div class="font-size-12 line-height-18">操作</div>
												<div class="font-size-9 line-height-18">Operation</div>
											</th>
											<th class="colwidth-7" id="checkAll" style='text-align:center;vertical-align:middle;width:50px;'>
												<a href="#" onclick="SelectUtil.performSelectAll('open_win_evaluation_modal_list')" ><img src="${ctx }/static/assets/img/d1.png" alt="全选" /></a>
												<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('open_win_evaluation_modal_list')" ><img src="${ctx }/static/assets/img/d2.png" alt="不全选" /></a>
											</th>
											<th class="colwidth-7">
												<div class="important">
													<div class="font-size-12 line-height-18">评估单号</div>
													<div class="font-size-9 line-height-18">Assessment No.</div>
												</div>
											</th>
											<th class="colwidth-7">
													<div class="font-size-12 line-height-18">版本</div>
													<div class="font-size-9 line-height-18">Version</div>
											</th>
											<th class="colwidth-9">
													<div class="font-size-12 line-height-18">类型</div>
													<div class="font-size-9 line-height-18">Type</div>						
											</th>
											<th class="colwidth-13">											
													<div class="font-size-12 line-height-18">适航性资料</div>
													<div class="font-size-9 line-height-18">Airwothiness Data</div>												
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">主题</div>
												<div class="font-size-9 line-height-18">Theme</div>
											</th>
											<th class="colwidth-10">
												
													<div class="font-size-12 line-height-18">文件</div>
													<div class="font-size-9 line-height-18">File</div>
											
											</th>
											<th class="colwidth-5" >
												
													<div class="font-size-12 line-height-18">颁发日期</div>
													<div class="font-size-9 line-height-18">Award Date</div>
						
											</th>
											<th class="colwidth-5" >
												<div class="font-size-12 line-height-18">生效日期</div>
												<div class="font-size-9 line-height-18">Effective Date</div>
											</th>
											<th class="colwidth-5" >
												<div class="font-size-12 line-height-18">到期日期</div>
												<div class="font-size-9 line-height-18">Expire</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">评估工程师</div>
												<div class="font-size-9 line-height-18">Engineer</div>
											</th>
								 		 </tr>
									</thead>
									<tbody >
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									<td title="6" style="text-align:left;vertical-align:middle;">6</td>
									<td title="OJT" style="text-align:left;vertical-align:middle;">OJT</td>
									<td title="机试" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:center;vertical-align:middle;">2017-08-03</td>
									<td title="" style="text-align:left;vertical-align:middle;"></td>
									</tr>
									
									</tbody>
								</table>
								</div>
								<div id="open_win_evaluation_modal_pagination" class="col-xs-12 text-center page-height padding-right-0 padding-left-0">
											<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;">
											<li class="disabled">
											<a href="javascript:void(0)"><<</a>
											</li>
											<li class="disabled">
											<a href="javascript:void(0)"><</a>
											</li>
											<li class="active">
											<a href="javascript:void(0)">1</a>
											</li>
											<li class="disabled">
											<a href="javascript:void(0)">></a>
											</li>
											<li class="disabled">
											<a href="javascript:void(0)">>></a>
											</li>
											</ul>
											<div class="fenye pull-right padding-right-0 text-center">
											<span>
											每页
											<i>10</i>
											条
											</span>
											<span>
											共
											<i>1</i>
											页
											</span>
											<span>
											总数
											<i>9</i>
											条
											</span>
											</div>
											</div>
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
	                   <i class='glyphicon glyphicon-info-sign alert-info'></i>警告！请不要提交。
				</span>
                    <span class="input-group-addon modalfooterbtn">
                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">提交</div>
							<div class="font-size-9">Submit</div>
						</button>
	                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
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
<!--  弹出框结束-->