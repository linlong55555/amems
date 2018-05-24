<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade modal-new" id=addModal tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:80%'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-14 " id="Cname"></div>
							<div class="font-size-12" id="Ename"></div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
              	<div class="col-xs-12 margin-top-8 ">
              	<div class="input-group-border">
              		<form id="form" >
                    	<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>飞机注册号</div>
								<div class="font-size-9">A/C REG</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								 <select class='form-control' id='fjzch' name="fjzch">
								 </select>
							</div>
						</div>
						
                    	<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>发现日期</div>
								<div class="font-size-9">Discovery Date</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control date-picker"
								maxlength="10" data-date-format="yyyy-mm-dd" id="fxrq" name="fxrq" />
							</div>
						</div>
						
						 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">ATA章节号</div>
								<div class="font-size-9">ATA</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group">
								<input type="hidden" value="" name="zjhid" id="zjhid">
								<input type="text" value="" name="zjh" class="form-control readonly-style" readonly="readonly" ondblclick="openChapterWin()"
									   id="zjh" />
			                     <span class="input-group-btn">
									<button type="button" class="btn btn-default form-control" style="height:34px;" data-toggle="modal" onclick="openChapterWin()">
										<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
			                	</div>
							</div>
						</div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red" id="wzmark">*</span>位置</div>
								<div class="font-size-9">Positon</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="wz" name="wz" class="form-control" disabled="disabled" maxlength="160" style="height:55px"></textarea>
							</div>
						</div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red" id="wzmark">*</span>缺陷描述</div>
								<div class="font-size-9">Defect Desc</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="qxms" name="qxms" class="form-control" disabled="disabled" maxlength="1300" style="height:55px"></textarea>
							</div>
						</div>
						
						<!-- <div id="xlqfile-div" class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修理前照片</div>
								<div class="font-size-9">Repairt File</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div id="wj"
									class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
									<div
										class="col-lg-10 col-sm-10 col-xs-10 padding-left-0 padding-right-0">
										<input type="text" id="wbwjm" name="wbwjm" placeholder='' maxlength="90"
											class="form-control ">
									</div>
									<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-2 padding-left-3 padding-right-0"></div>
								</div>
							</div>
						</div> -->
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修理前照片列表</div>
								<div class="font-size-9">Photos Before Repairs</div>
							</label>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 " style="overflow-x: auto;">
										<table  class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important'>
											<thead>
												<tr>
													<th class="colwidth-3" id="operat">
														<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-2 padding-left-3 padding-right-0">
														</div>
													</th>
													<th class="colwidth-20">
														<div class="font-size-12 line-height-18">文件说明</div>
														<div class="font-size-9 line-height-18">File Desc</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 line-height-18">文件大小</div>
														<div class="font-size-9 line-height-18">File Size</div>
													</th>
													<th class="colwidth-13"><div class="font-size-12 line-height-18">上传人</div>
														<div class="font-size-9 line-height-18">Uploader</div></th>
													<th class="colwidth-13"><div class="font-size-12 line-height-18">上传时间</div>
														<div class="font-size-9 line-height-18">Upload Time</div></th>
												</tr>
											</thead>
											<tbody id="filelist">
												
											</tbody>
									</table>
								</div>
							</div>
						</div>
						
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red" id="xlrqmark">*</span>修理日期</div>
								<div class="font-size-9">Maint Date</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control date-picker"
									maxlength="10" data-date-format="yyyy-mm-dd" id="xlrq" name="xlrq"
									 />
							</div>
						</div>
						
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">关闭</div>
								<div class="font-size-9">Close</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<label style=" font-weight:normal" class="pull-left"><input type="radio" name="is_gb" id="is_gb" value='1' checked="checked">是</label>										
								<label style=" font-weight:normal" class="pull-left padding-left-10"><input type="radio" name="is_gb"  value='0' id="is_gb" >否</label>
							</div>
						</div>
						
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">加入维修方案</div>
								<div class="font-size-9">Join Maintenanc</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<label style=" font-weight:normal" class="pull-left"><input type="radio" name="is_jrwxfq" id="is_jrwxfq" value='1' checked="checked">是</label>										
								<label style=" font-weight:normal" class="pull-left padding-left-10"><input type="radio" name="is_jrwxfq"  value='0' id="is_jrwxfq" >否</label>
							</div>
						</div>
						<div class='clearfix'></div>
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修理方式</div>
								<div class="font-size-9">Repair model</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<label style=" font-weight:normal" class="pull-left"><input type="radio" name="xlfs" id="xlfs" value='1' checked="checked">永久修理</label>										
								<label style=" font-weight:normal" class="pull-left padding-left-10"><input type="radio" name="xlfs"  value='2' id="xlfs" >临时修理</label>
							</div>
						</div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red" id="xlyjmark">*</span>修理依据</div>
								<div class="font-size-9">Repair Basis</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="xlyj" name="xlyj" class="form-control" disabled="disabled" maxlength="1300" style="height:55px"></textarea>
							</div>
						</div>
						<div  id="xlyjlookfile"  class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修理依据附件</div>
								<div class="font-size-9">Repair Basis File </div>
							</label>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 " style="overflow-x: auto;">
										<table  class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important'>
											<thead>
												<tr>
													<th class="colwidth-3" id="operat">
														<div id="xlyjfileuploader" class="col-lg-2 col-sm-2 col-xs-2 padding-left-3 padding-right-0">
														</div>
													</th>
													<th class="colwidth-20">
														<div class="font-size-12 line-height-18">文件说明</div>
														<div class="font-size-9 line-height-18">File Desc</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 line-height-18">文件大小</div>
														<div class="font-size-9 line-height-18">File Size</div>
													</th>
													<th class="colwidth-13"><div class="font-size-12 line-height-18">上传人</div>
														<div class="font-size-9 line-height-18">Uploader</div></th>
													<th class="colwidth-13"><div class="font-size-12 line-height-18">上传时间</div>
														<div class="font-size-9 line-height-18">Upload Time</div></th>
												</tr>
											</thead>
											<tbody id="xlyjfilelist">
												
											</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">例行检查</div>
								<div class="font-size-9">Inspection</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<label style=" font-weight:normal" class="pull-left"><input type="radio" name="is_xlxjc" id="is_xlxjc" value='1' checked="checked">是</label>										
								<label style=" font-weight:normal" class="pull-left padding-left-10"><input type="radio" name="is_xlxjc"  value='0' id="is_xlxjc" >否</label>
							</div>
						</div>
						
						<div id="lxjcjg-div" class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">例行检查间隔</div>
								<div class="font-size-9">Inspec Interval</div>
							</label>
							<div id="div-wxsb" class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control date-picker"
								maxlength="33" id="lxjcjg" name="lxjcjg" />
							</div>
						</div>
						<div class="clearfix"></div>	
						<!-- <div id="xlqfile-div" class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修理后照片</div>
								<div class="font-size-9">File Des</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div id="wj"
									class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
									<div
										class="col-lg-10 col-sm-10 col-xs-10 padding-left-0 padding-right-0">
										<input type="text" id="xlhfj" name="xlhfj" placeholder='' maxlength="90"
											   class="form-control ">
									</div>
									<div id="xlhfileuploader" class="col-lg-2 col-sm-2 col-xs-2 padding-left-3 padding-right-0"></div>
								</div>
							</div>
						</div> -->
						
						<div  id="lookfile"  class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修理后照片列表</div>
								<div class="font-size-9">Repaired photos</div>
							</label>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 " style="overflow-x: auto;">
										<table  class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important'>
											<thead>
												<tr>
													<th class="colwidth-3" id="operat">
														<div id="xlhfileuploader" class="col-lg-2 col-sm-2 col-xs-2 padding-left-3 padding-right-0">
														</div>
													</th>
													<th class="colwidth-20">
														<div class="font-size-12 line-height-18">文件说明</div>
														<div class="font-size-9 line-height-18">File Desc</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 line-height-18">文件大小</div>
														<div class="font-size-9 line-height-18">File Size</div>
													</th>
													<th class="colwidth-13"><div class="font-size-12 line-height-18">上传人</div>
														<div class="font-size-9 line-height-18">Uploader</div></th>
													<th class="colwidth-13"><div class="font-size-12 line-height-18">上传时间</div>
														<div class="font-size-9 line-height-18">Upload Time</div></th>
												</tr>
											</thead>
											<tbody id="xlhfilelist">
												
											</tbody>
									</table>
								</div>
							</div>
						</div>
					</form>
							<div class="clearfix"></div>	
						
				
				</div>
			
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
	                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="saveRecord()">
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
				
				<div class="clearfix"></div> 
				
			</div>
			
			
			
            </div>
          </div>
	</div>
<!--  弹出框结束-->