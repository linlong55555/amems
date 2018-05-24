<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/installationlist_replace.js"></script>

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
								<input id="replace_cxbjh" class="form-control readonly-style" readonly="" maxlength="50" ondblclick="open_win_installationlist_replace.openRemovedWin()" type="text">
			                    <span class="input-group-addon btn btn-default" onclick="open_win_installationlist_replace.openRemovedWin()">
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
							<input class="form-control" id="replace_cxxlh" maxlength="50" disabled="disabled">
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">批次号</div>
							<div class="font-size-9">B/N</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input class="form-control" id="replace_cxpch" maxlength="50" readonly="readonly">
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">数量</div>
							<div class="font-size-9">Quantity</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input class="form-control" id="replace_cxsl" maxlength="10" readonly="readonly">
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">英文名称</div>
							<div class="font-size-9">F.Name</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input type="text"  id="replace_cxywmc" class="form-control" readonly="readonly">
						</div>
					</div>
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">中文名称</div>
							<div class="font-size-9">CH.Name</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input type="text"  id="replace_cxzwmc" class="form-control" readonly="readonly">
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
					<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group">
						<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">数量</div>
							<div class="font-size-9">Quantity</div>
						</div>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input class="form-control" maxlength="10" type="text"name="replace_zjsl" id="replace_zjsl">
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
							<input  type="text" class="form-control" id="replace_zszwmc" maxlength="100">
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
					
					<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 form-group">
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
			 	 
			 	 
			 	 <div class="panel panel-primary" id="replace_detail_div">
					<div class="panel-heading bg-panel-heading">
						<div class="font-size-12">装上件初始化信息</div>
						<div class="font-size-9">Installed Init Info</div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0 padding-bottom-0">
						
						<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">厂家件号</div>
								<div class="font-size-9">MP/N</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input class="form-control" maxlength="50" type="text" name="replace_cjjh" id="replace_cjjh">
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
						<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">型号</div>
								<div class="font-size-9">Model</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input class="form-control" maxlength="50" type="text" name="replace_xh" id="replace_xh">
							</div>
						</div>
						<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>单位</div>
								<div class="font-size-9">Unit</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<select class="form-control" name="replace_jldw" id="replace_jldw"></select>
							</div>
						</div>
						<div class="clearfix"></div>	
						
						<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">履历卡类型</div>
								<div class="font-size-9">Career Type</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<select name="replace_llklx" class="form-control" id="replace_llklx">
								</select>
							</div>
						</div>
						<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">履历卡号</div>
								<div class="font-size-9">Career No.</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input name="replace_llkbh" id="replace_llkbh" class="form-control" maxlength="50" type="text">
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">装机件类型</div>
								<div class="font-size-9">Installed Type</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<select name="replace_zjjlx" id="replace_zjjlx" class="form-control">
								</select>
							</div>
						</div>
						<div class="clearfix"></div>
						
						<hr class="margin-left-8 margin-right-8 margin-bottom-10" style="margin-top: 3px;">
						<div class="clearfix"></div>
						
				 	 	<div id="replace_init_div">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>初始化日期</div>
								<div class="font-size-9">Init Date</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input class="form-control date-picker" id="replace_cal" name="replace_cal" jklbh="1_10" jkflbh="1D" maxlength="10" type="text" data-date-format="yyyy-mm-dd">
							</div>
						</div>
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">初始化飞行时间</div>
								<div class="font-size-9">Init FH</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<div class="input-group"> 
									<input class="form-control input-sm validate-time" maxlength="8" id="replace_fh" name="replace_fh" jklbh="2_10_FH" jkflbh="2T" type="text">
									<span class="input-group-addon" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FH</span>
								</div>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">初始化飞行循环</div>
								<div class="font-size-9">Init FC</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<div class="input-group"> 
									<input class="form-control input-sm validate-cycle" maxlength="8" id="replace_fc" name="replace_fc" jklbh="3_10_FC" jkflbh="3C" type="text">
									<span class="input-group-addon" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FC</span>
								</div>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="replace_init_eh">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">初始化发动机时间</div>
								<div class="font-size-9">Init EH</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<div class="input-group"> 
									<input class="form-control input-sm validate-time" maxlength="8" id="replace_eh" name="replace_eh" jklbh="2_30_EH" jkflbh="2T" type="text">
									<span class="input-group-addon" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">EH</span>
								</div>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="replace_init_ec">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">初始化发动机循环</div>
								<div class="font-size-9">Init EC</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<div class="input-group"> 
									<input class="form-control input-sm validate-cycle" maxlength="8" id="replace_ec" name="replace_ec" jklbh="3_30_EC" jkflbh="3C" type="text">
									<span class="input-group-addon" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">EC</span>
								</div>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="replace_init_apuh">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">初始化APU时间</div>
								<div class="font-size-9">Init APUH</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<div class="input-group"> 
									<input class="form-control input-sm validate-time" maxlength="8" id="replace_apuh" name="replace_apuh" jklbh="2_20_AH" jkflbh="2T" type="text">
									<span class="input-group-addon" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">APUH</span>
								</div>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="replace_init_apuc">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">初始化APU循环</div>
								<div class="font-size-9">Init APUC</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<div class="input-group"> 
									<input class="form-control input-sm validate-cycle" maxlength="8" id="replace_apuc" name="replace_apuc" jklbh="3_20_AC" jkflbh="3C" type="text">
									<span class="input-group-addon" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">APUC</span>
								</div>
							</div>
						</div>
						
						<div class="clearfix"></div>
						<hr class="margin-left-8 margin-right-8 margin-bottom-10" style="margin-top: 3px;">
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">出厂日期</div>
								<div class="font-size-9">Production Date</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input class="form-control date-picker" id="replace_chucrq" maxlength="10" type="text" data-date-format="yyyy-mm-dd" onchange="open_win_installationlist_replace.computeCal()">
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-14 margin-top-8">CAL</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<div class="font-size-14 margin-top-8" id="replace_ccsj"></div>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">TSN</div>
								<div class="font-size-9">TSN</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input class="form-control validate-time" id="replace_tsn" name="replace_tsn" maxlength="10" type="text">
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">TSO</div>
								<div class="font-size-9">TSO</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input class="form-control validate-time" id="replace_tso" name="replace_tso" maxlength="10" type="text">
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">CSN</div>
								<div class="font-size-9">CSN</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input class="form-control validate-cycle" id="replace_csn" name="replace_csn" maxlength="10" type="text">
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">CSO</div>
								<div class="font-size-9">CSO</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input class="form-control validate-cycle" id="replace_cso" name="replace_cso" maxlength="10" type="text">
							</div>
						</div>
						
						<div class="clearfix"></div>
						<hr class="margin-left-8 margin-right-8 margin-bottom-10" style="margin-top: 3px;">
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">改装记录</div>
								<div class="font-size-9">Record</div>
							</div>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea style="height: 55px;" class="form-control" id="replace_bjgzjl"  maxlength="1000"></textarea>
							</div>
						</div>
						
						<div class="clearfix"></div>
						<hr class="margin-left-8 margin-right-8 margin-bottom-10" style="margin-top: 3px;">
						<div class="clearfix"></div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						    <div class="col-lg-1  col-md-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">证书信息</div>
								<div class="font-size-9">Certificate Info</div>
							</div>
							<div class="padding-leftright-8 col-lg-11 col-md-11 col-sm-10" name="certificate_table_div">
								<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
									<table class="table table-thin table-bordered table-striped table-hover table-set" name="installationlist_certificate_table">
										<thead>
											<tr>
												<th class="colwidth-7" name="common_certificate_addTh">
													<button class="line6 line6-btn" name="common_certificate_addBtn" type="button">
														<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
													</button>
											    </th>
												<th class="colwidth-20">
												   <div class="font-size-12 line-height-12">证书类型</div>
											        <div class="font-size-9 line-height-12">Certificate Type</div>
												</th>
												<th class="colwidth-10">
												   <div class="font-size-12 line-height-12">证书编号</div>
											        <div class="font-size-9 line-height-12">Certificate No.</div>
												</th>
												<th class="colwidth-10">
												   <div class="font-size-12 line-height-12">存放位置</div>
											        <div class="font-size-9 line-height-12">Certificate Location</div>
												</th>
												<th class="colwidth-10">
												   <div class="font-size-12 line-height-12">签署日期</div>
											        <div class="font-size-9 line-height-12">Sign Date</div>
												</th>
												<th class="colwidth-7">
												   <div class="font-size-12 line-height-12">附件</div>
											        <div class="font-size-9 line-height-12">Attachment</div>
												</th>
											</tr>
										</thead>
												
										<tbody id="replace_certificate_list"><tr><td class="text-center" colspan="6">暂无数据 No data.</td></tr></tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
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
