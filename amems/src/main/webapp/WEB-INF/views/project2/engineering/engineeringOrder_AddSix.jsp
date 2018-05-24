<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade double-pop-up" id="myModalSix" tabindex="-1" role="dialog"  aria-labelledby="myModalSix" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:65%;'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-12 ">选择部件</div>
							<div class="font-size-9">Select part</div>
						  </div>
						  <div class='pull-right' style='padding-top:10px;'>
						  	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" style='font-size:30px !important;' >&times;</span></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" style='padding-top:0px;'>
              	<div class="input-group-border" style='margin-top:8px;padding-top:5px;margin-bottom:8px;'>
                        <div class="col-xs-12 padding-left-0 padding-leftright-8 margin-top-0 modalSearch">	
									<div class="col-xs-12 input-group">
									<div class="input-group-addon" style='padding-left:0px;padding-right:10px;padding-bottom:0px;padding-top:0px;background:none;border:0px;' >
				                    	<div class="font-size-12">部件</div>
										<div class="font-size-9">Part</div>
				                    </div>
									<input type="text" placeholder='文件编号/ATA/主题/备注'  class="form-control" id="keyword_search" >
				                    <div class="input-group-addon" style='padding-left:10px;padding-right:0px;padding-bottom:0px;padding-top:0px;background:none;border:0px;'>
				                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();" style='margin-right:0px !important;'>
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
				                  		</button>
				                    </div>
				                     <div class="input-group-addon" style='padding-left:10px;padding-right:0px;padding-bottom:0px;padding-top:0px;background:none;border:0px;'>
				                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();" >
										<div class="font-size-12">重置</div>
										<div class="font-size-9">Reset</div>
				                  		</button>
				                    </div>
								</div>
							</div>
		            	
			         		<div class="clearfix"></div>
			         		
						<!-- start:table -->
						<div class="margin-left-8 margin-right-8" style='margin-top:5px;border:1px solid #d5d5d5;'>
							<div style="overflow-x:auto;border-right:1px solid #d5d5d5;" id="leftPNTable" class='col-xs-4 padding-left-0 padding-right-0' >
								<table class="table  table-striped table-hover table-set" style='margin-bottom:0px !important'>
									<thead>
								   		<tr>
								   			<th class=" colwidth-5" id="checkSingle">
												<div class="font-size-12 line-height-12">件号</div>
												<div class="font-size-9 line-height-12">PN</div>
											</th>
											<th class="colwidth-7">
													<div class="font-size-12 line-height-12">名称</div>
													<div class="font-size-9 line-height-12">Name</div>
											</th>
											
								 		 </tr>
									</thead>
									<tbody >
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									AK09810
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									</tr>
								    </tbody>
								</table>
								</div>
								<div style="overflow-x:auto" id="rightPNTable" class='col-xs-8 padding-left-0 padding-right-0' >
								<table class="table  table-striped table-hover table-set" style='margin-bottom:0px !important'>
									<thead>
								   		<tr>
								   			<th class=" colwidth-5" >
												<div class="font-size-12 line-height-12">选择</div>
												<div class="font-size-9 line-height-12">Select</div>
											</th>
											<th class="colwidth-7">
													<div class="font-size-12 line-height-12">序列号</div>
													<div class="font-size-9 line-height-12">SN</div>
												
											</th>
											<th class="colwidth-7">
													<div class="font-size-12 line-height-12">所在位置</div>
													<div class="font-size-9 line-height-12">Location</div>
											</th>
											
								 		 </tr>
									</thead>
									<tbody >
									<tr style="cursor:pointer" >
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									</tr>
									<tr style="cursor:pointer" >
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									</tr>
									<tr style="cursor:pointer" >
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									</tr>
									<tr style="cursor:pointer" >
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									</tr>
									<tr style="cursor:pointer" >
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									</tr>
									<tr style="cursor:pointer" >
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									</tr>
									<tr style="cursor:pointer" >
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									</tr>
									<tr style="cursor:pointer" >
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									</tr>
									<tr style="cursor:pointer" >
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									</tr>
									<tr style="cursor:pointer" >
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									</tr>
								    </tbody>
								</table>
								</div>
							  <div class="clearfix"></div>  
							</div>
							<div class="margin-left-8 margin-right-8" style='border:1px solid #d5d5d5;margin-top:18px;margin-bottom:8px;'>
							<p style='height:20px;margin-top:-10px;background:white;margin-bottom:0px;padding-top:0px;margin-left:8px;width:100px;text-align:center;'>本次已经选择</p>
							<div style="overflow-x:auto;" id='bottomPNTable'>
								<table class="table table-striped table-hover table-set" style='margin-bottom:0px !important;'>
									<thead>
								   		<tr>
								   			<th >
												<div class="font-size-12 line-height-12">件号</div>
												<div class="font-size-9 line-height-12">PN</div>
											</th>
											<th >
													<div class="font-size-12 line-height-12">名称</div>
													<div class="font-size-9 line-height-12">Name</div>
											</th>
											<th >
											<div class="font-size-12 line-height-12">型号</div>
													<div class="font-size-9 line-height-12">Model</div>
											</th>
											<th >
													<div class="font-size-12 line-height-12">序列号</div>
													<div class="font-size-9 line-height-12">SN</div>						
											</th>
											<th >											
													<div class="font-size-12 line-height-12">所在位置</div>
													<div class="font-size-9 line-height-12">Location</div>												
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
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									</tr>
									<tr style="cursor:pointer" onclick="open_win_evaluation_modal.rowonclick(event);" bgcolor="#f9f9f9">
									<td style="text-align:center;vertical-align:middle;">
									<input name="open_win_evaluation_modal_list_radio" value="0" onclick="SelectUtil.checkRow(this,'selectAllId','open_win_evaluation_modal_list')" type="checkbox">
									</td>
									<td title="类别1" style="text-align:center;vertical-align:middle;">类别1</td>
									<td title="5435" style="text-align:center;vertical-align:middle;">5435</td>
									<td title="546645" style="text-align:left;vertical-align:middle;">546645</td>
									<td title="5464" style="text-align:left;vertical-align:middle;">5464</td>
									</tr>
									
								    </tbody>
								</table>
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