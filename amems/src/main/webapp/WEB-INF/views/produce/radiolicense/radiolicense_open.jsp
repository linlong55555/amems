<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/radiolicense/radiolicense_open.js"></script>
<style>
#bjcsTable > tbody > tr > td, .table > tfoot > tr > td {
	border-top : none;
}
#rsylTable > tbody > tr > td, .table > tfoot > tr > td {
	border-top : none;
}
</style>
<div class="modal fade in modal-new" id="aircraftinfoModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:90%'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
		                      <div class="font-size-14 " id="modalHeadChina"></div>
							  <div class="font-size-12" id="modalHeadEnglish"></div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
              	<div class="col-xs-12 margin-top-8 ">
              	<form id="form">
              	<div class="input-group-border">
						<!-- start隐藏input -->
						<input class="form-control" id="gjdjzfjid" name="gjdjzfjid" type="hidden" >
						<input class="form-control" id="shzfjid" name="shzfjid" type="hidden"  >
						<input class="form-control" id="wxdtzzfjid" name="wxdtzzfjid" type="hidden" >
						<input class="form-control" id="aircraftinfo_dprtcode" name="aircraftinfo_dprtcode" type="hidden" >
						<!-- end隐藏input -->
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>机型</div>
								<div class="font-size-9">A/C Type</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="fjjx" name="fjjx" class="form-control">
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>飞机注册号</div>
								<div class="font-size-9">A/C REG</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="fjzch" name="fjzch" type="text" maxlength="20" >
								<input class="form-control" id="oldFjzch" name="oldFjzch" type="hidden" maxlength="20" >
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>MSN</div>
								<div class="font-size-9">MSN</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="xlh" name="xlh" type="text" maxlength="20" >
								<input class="form-control" id="oldXlh" name="oldXlh" type="hidden" maxlength="20" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">备注名</div>
								<div class="font-size-9">Note Name</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="bzm" name="bzm" type="text" maxlength="16" >
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
              <div class="clearfix"></div>              
            <!--  </div> -->
            
			<div class="panel panel-primary">
				<div id="attachHead" class="panel-heading bg-panel-heading" >
					<div class="font-size-12" id="chinaHead">飞机重点监控信息</div>
					<div class="font-size-9" id="englishHead">A/C Type Monitoring</div>
				</div>
				<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
					
					<div  class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<label id="left_column" class=" text-right padding-left-0 padding-right-8">
							&nbsp;&nbsp;<b style="font-weight:bold">三证信息</b>
						</label>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>国籍登记证号</div>
							<div class="font-size-9">Nationality No.</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control " id="gjdjzh" name="gjdjzh" type="text" maxlength="50" />
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>颁发日期</div>
							<div class="font-size-9">Issue Date</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="gjdjzjkrq" name="gjdjzjkrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>有效期</div>
							<div class="font-size-9">Valid Period</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="gjdjzyxq" name="gjdjzyxq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group ">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">国籍登记证附件</div>
							<div class="font-size-9">File</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class='input-group' >
								<span class='input-group-btn'>
									 <div id="fileuploader"></div>
								</span>
					        </div>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>适航证号</div>
							<div class="font-size-9">Airworthiness No.</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control " id="shzh" name="shzh" type="text" maxlength="50" />
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>颁发日期</div>
							<div class="font-size-9">Issue Date</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="shzjkrq" name="shzjkrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>有效期</div>
							<div class="font-size-9">Valid Period </div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="shzzjkrq" name="shzzjkrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group ">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">适航证号附件</div>
							<div class="font-size-9">File</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class='input-group' >
								<span class='input-group-btn'>
									 <div id="fileuploaderairworthiness"></div>
								</span>
					        </div>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>无线电台执照号</div>
							<div class="font-size-9">Radio No.</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control " id="wxdtxkzh" name="wxdtxkzh" type="text" maxlength="50" />
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>颁发日期</div>
							<div class="font-size-9">Issue Date</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="wxdtbfrq" name="wxdtbfrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>有效期</div>
							<div class="font-size-9">Valid Period</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="dtzzjkrq" name="dtzzjkrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group ">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">无线电台证附件</div>
							<div class="font-size-9">File</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class='input-group' >
								<span class='input-group-btn'>
									 <div id="fileuploaderwireless"></div>
								</span>
					        </div>
						</div>
					</div>
					
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-8">
						<label id="left_column" class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
							<div class="font-size-12 margin-topnew-2">附件列表</div>
							<div class="font-size-9">File List</div>
						</label>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0" style="overflow-x: auto;">
							<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:600px">
								<thead>
									<tr>
										<th class="colwidth-5">
											<div class="font-size-12 line-height-12">操作</div>
											<div class="font-size-9 line-height-12">No.</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-12">文件类型</div>
											<div class="font-size-9 line-height-12">File Type</div>
										</th>
									   	<th class="colwidth-30">
											<div class="font-size-12 line-height-12" id="chinaColFileTitle">文件说明</div>
											<div class="font-size-9 line-height-12" id="englishColFileTitle">File desc</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 line-height-12">文件大小</div>
											<div class="font-size-9 line-height-12">File size</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-12">上传人</div>
											<div class="font-size-9 line-height-12">Operator</div></th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-12">上传时间</div>
											<div class="font-size-9 line-height-12">Operate Time</div>
										</th>			
									</tr>
								</thead>
							   <tbody id="attachments_list_tbody">
							   		 <tr class="non-choice text-center"><td colspan="6">暂无数据 No data.</td></tr>
								</tbody>
							</table>
							</div>
						</div>
				</div>
			</div>
			 </form>

			 </div>	
			 <div class="clearfix"></div> 
			
			 </div>
           <div class="modal-footer">
           		<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
					<span class="input-group-addon modalfootertip" >
		                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
					</span>
	                    <span class="input-group-addon modalfooterbtn">
	                        <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="aircraftinfoModal.setData()" id="baocunButton">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
		                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
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
