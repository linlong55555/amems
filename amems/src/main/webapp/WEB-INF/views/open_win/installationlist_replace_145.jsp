<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/installationlist_replace_145.js"></script>

<div class="modal fade modal-new" id="open_win_installationlist_replace" tabindex="-1" role="dialog" aria-labelledby="open_win_installationlist_replace" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog" style="width:80%;">
		<div class="modal-content">	
        	<div class="modal-header modal-header-new">
            	<h4 class="modal-title">
                	<div class="pull-left">
                    	<div class="font-size-12 ">拆换记录</div>
						<div class="font-size-9">Record</div>
					</div>
					<div class='pull-right'>
			  			<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
			  	    </div>
				  	<div class="clearfix"></div>
             	</h4>
             </div>
			 <div class="modal-body padding-bottom-0" style="overflow: auto;margin-top:0px;">
			 	<form id="open_win_installationlist_replace_form">
			 	<input type="hidden" id="replace_id" />
			 	<input type="hidden" id="replace_zszjqdid" />
			 	<input type="hidden" id="replace_rowid" />
			 	<input type="hidden" id="replace_gdczjlid" />
			 	
			 	<input type="hidden" id="replace_cxWckcid" />
			 	<input type="hidden" id="replace_cxKclvid" />
			 	<input type="hidden" id="replace_zsWckcid" />
			 	<input type="hidden" id="replace_zsKclvid" />
            	<div class="input-group-border" style="margin-top:8px;padding-top:5px;">
            	
            		<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">拆下件号</div>
							<div class="font-size-9 line-height-18">P/N</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input class="form-control" id="replace_cxbjid" type="hidden">
							<div class="input-group" style="width:100%">
								<input id="replace_cxbjh" name="replace_cxbjh" class="form-control" readonly="" maxlength="50" ondblclick="open_win_installationlist_replace.openRemovedWin()" type="text">
			                    <span id="replace_cxbjh_btn" class="input-group-addon btn btn-default" onclick="open_win_installationlist_replace.openRemovedWin()">
			                    	<i class="icon-search cursor-pointer"></i>
			                    </span>
		                	</div>
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">拆下序号</div>
							<div class="font-size-9">S/N</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input class="form-control" id="replace_cxxlh" name="replace_cxxlh" maxlength="50" onblur="open_win_installationlist_replace.limitCxsl()">
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">批次号</div>
							<div class="font-size-9">B/N</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input class="form-control" id="replace_cxpch" name="replace_cxpch" maxlength="50">
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">数量</div>
							<div class="font-size-9">Quantity</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input class="form-control" id="replace_cxsl" name="replace_cxsl" maxlength="10">
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">英文名称</div>
							<div class="font-size-9">F.Name</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input type="text"  id="replace_cxywmc" name="replace_cxywmc" class="form-control">
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">中文名称</div>
							<div class="font-size-9">CH.Name</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input type="text"  id="replace_cxzwmc" name="replace_cxzwmc" class="form-control">
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">拆下时间</div>
							<div class="font-size-9 line-height-18">Time</div>
						</label>
						<div class="col-lg-5 col-sm-5 col-xs-5 padding-left-8 padding-right-0 form-group">
							<input class="form-control date-picker" id="replace_cxrq" name="cxrq" maxlength="10" data-date-format="yyyy-mm-dd" type="text">
						</div>
						<div class="col-lg-3 col-sm-3 col-xs-3 padding-left-0 padding-right-8">
							<input class="form-control time-masked" id="replace_cxsj" name="cxsj" style="border-left: 0;">
						</div>
					</div>
	         		<div class="clearfix"></div>
	         		
	         		<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">装上件号</div>
							<div class="font-size-9 line-height-18">P/N</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<!-- <input class="form-control" id="replace_zsbjid" type="hidden"> -->
							<div class="input-group" style="width:100%">
								<input id="replace_zsbjh" name="replace_zsbjh" class="form-control" maxlength="50" 
									onkeyup="open_win_installationlist_replace.manualInputInstalled()" 
									onblur="open_win_installationlist_replace.finishInputInstalled()" type="text">
			                    <span id="replace_zsbjh_btn" class="input-group-addon btn btn-default" onclick="open_win_installationlist_replace.openInstalledWin()">
			                    	<i class="icon-search cursor-pointer"></i>
			                    </span>
		                	</div>
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">装上序号</div>
							<div class="font-size-9">S/N</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input class="form-control" id="replace_zsxlh" name="replace_zsxlh" maxlength="50" onblur="open_win_installationlist_replace.onblurXlh()">
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">批次号</div>
							<div class="font-size-9">B/N</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input class="form-control" id="replace_zspch" name="replace_zspch" maxlength="50">
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">数量</div>
							<div class="font-size-9">Quantity</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input class="form-control" id="replace_zssl" name="replace_zssl" maxlength="10">
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">英文名称</div>
							<div class="font-size-9">F.Name</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input  type="text" class="form-control" id="replace_zsywmc" name="replace_zsywmc" maxlength="100">
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">中文名称</div>
							<div class="font-size-9">CH.Name</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input  type="text" class="form-control" id="replace_zszwmc" name="replace_zszwmc" maxlength="100">
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">装上时间</div>
							<div class="font-size-9 line-height-18">Time</div>
						</label>
						<div class="col-lg-5 col-sm-5 col-xs-5 padding-left-8 padding-right-0 form-group">
							<input class="form-control date-picker" id="replace_zsrq" name="zsrq" maxlength="10" data-date-format="yyyy-mm-dd" type="text">
						</div>
						<div class="col-lg-3 col-sm-3 col-xs-3 padding-left-0 padding-right-8">
							<input class="form-control time-masked" id="replace_zssj" name="zssj" style="border-left: 0;">
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">ATA章节号</div>
								<div class="font-size-9">ATA</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input name="replace_zjh_value" type="hidden" id="replace_zjh_value">
								<div class="input-group " style="width:100%">
									<input name="replace_zjh_display" id="replace_zjh_display" ondblclick="open_win_installationlist_replace.openChapterWin()" class="form-control readonly-style" readonly="readonly" type="text">
				                   	<span name="replace_zjh_btn"class="input-group-addon btn btn-default" onclick="open_win_installationlist_replace.openChapterWin()">
				                   		<i class="icon-search"></i>
				                   	</span>
				               	</div>
							</div>
						</div>
	         		<div class="clearfix"></div>
	         		
	         		<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>拆装位置</div>
							<div class="font-size-9 line-height-18">Position</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8 pull-left">
							<select id="replace_wz" class="form-control" onchange="open_win_installationlist_replace.switchInitInput()">
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group" id="replace_parent_div">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>上级部件</div>
							<div class="font-size-9 line-height-18">Parent Part</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8 pull-left">
							<input id="replace_fjdid" class="form-control" type="hidden">
							<input id="replace_fjd_wz" class="form-control" type="hidden">
							<input id="replace_fjd_cj" class="form-control" type="hidden">
							<div class="input-group " style="width:100%">
								<input id="replace_fjd_name" name="replace_fjd_name" class="form-control readonly-style" ondblclick="open_win_installationlist_replace.openParentWin()" readonly="" type="text">
		                    	<span id="replace_fjd_btn" class="input-group-addon btn btn-default" onclick="open_win_installationlist_replace.openParentWin()"><i class="icon-search"></i></span>
		                	</div>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group">
						<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">操作人</div>
							<div class="font-size-9">Operator</div>
						</div>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<div class="input-group " style="width:100%">
								<input name="replace_azr_display" id="replace_azr_display" class="form-control" ondblclick="open_win_installationlist_replace.openUser()"  type="text" maxlength="100">
								<input name="replace_azr_value" id="replace_azr_value" type="hidden">
		                    	<span name="replace_azr_btn" id="replace_azr_btn" class="input-group-addon btn btn-default" onclick="open_win_installationlist_replace.openUser()"><i class="icon-search"></i></span>
		                	</div>
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">单位</div>
								<div class="font-size-9">Unit</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<select class="form-control" name="replace_jldw" id="replace_jldw"></select>
							</div>
						</div>
	         		<div class="clearfix"></div>
	         		
	         		<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<div class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">飞机站位</div>
							<div class="font-size-9">A/C</div>
						</div>
						<div id="replace_fjzw_div_edit" class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
							<div class="input-group cursor-pointer" style="min-width:38%;width:100%;">
		                		<input id="replace_fjzw" name="replace_fjzw" type="text" maxlength="500" class="form-control" />
	                    		<span name="replace_fjzw_btn" class="input-group-addon btn btn-default" onclick="open_win_installationlist_replace.openStationWin()"><i class='icon-search'></i></span>
		                	</div>
						</div>
					</div>
					<div class="clearfix"></div>
	         		
					<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<div class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">拆装原因</div>
							<div class="font-size-9">Reason</div>
						</div>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea style="height: 55px;" class="form-control" id="replace_czyy" maxlength="300"></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
			 	 </div>
				</form>
			 </div> 
			 <div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8">
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
			                <i class='glyphicon glyphicon-info-sign alert-info' style="display: none;"></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
	                        <button type="button" id="replace_confirm_btn" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_installationlist_replace.save()">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
	                    </span>
	               	</div><!-- /input-group -->
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
