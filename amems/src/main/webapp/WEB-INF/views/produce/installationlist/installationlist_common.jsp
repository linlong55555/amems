<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	
	<div name="installation_detail_common">
	<form name="installation_form">
		<div class="input-group-border">
			<input name="common_id" type="hidden">
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>件号</div>
					<div class="font-size-9">P/N</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<input class="form-control" maxlength="50" type="text" name="common_bjh">
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>英文名称</div>
					<div class="font-size-9">F.Name</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<input class="form-control" maxlength="100" type="text" name="common_ywmc">
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">中文名称</div>
					<div class="font-size-9">CH.Name</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<input class="form-control" maxlength="100" type="text" name="common_zwmc">
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">序列号</div>
					<div class="font-size-9">S/N</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<input class="form-control" maxlength="50" type="text" name="common_xlh">
				</div>
			</div>
			<div class="clearfix"></div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">批次号</div>
					<div class="font-size-9">B/N</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<input class="form-control" maxlength="50" type="text" name="common_pch">
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">厂家件号</div>
					<div class="font-size-9">MP/N</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<input class="form-control" maxlength="50" type="text" name="common_cjjh">
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">ATA章节号</div>
					<div class="font-size-9">ATA</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<input name="common_zjh_value" type="hidden">
					<div class="input-group col-xs-12">
						<input name="common_zjh_display" class="form-control readonly-style" readonly="readonly" type="text">
                    	<span name="common_zjh_btn"class="input-group-addon btn btn-default">
                    		<i class="icon-search"></i>
                    	</span>
                	</div>
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">型号</div>
					<div class="font-size-9">Model</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<input class="form-control" maxlength="50" type="text" name="common_xh">
				</div>
			</div>
			<div class="clearfix"></div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>数量</div>
					<div class="font-size-9">Quantity</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<input class="form-control" maxlength="10" type="text"name="common_zjsl">
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>单位</div>
					<div class="font-size-9">Unit</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<select class="form-control" name="common_jldw"></select>
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>分类</div>
					<div class="font-size-9">Categories</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<select class="form-control" name="common_wz">
					</select>
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>上级部件</div>
					<div class="font-size-9">Parent Part</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<input name="common_fjd_value" type="hidden">
					<div class="input-group col-xs-12">
						<input name="common_fjd_display" class="form-control readonly-style" readonly="readonly" type="text">
						<input name="common_fjd_wz" type="hidden">
						<input name="common_fjd_cj" type="hidden">
                    	<span name="common_fjd_btn" class="input-group-addon btn btn-default">
                    		<i class="icon-search"></i>
                    	</span>
                	</div>
				</div>
			</div>
			<div class="clearfix"></div>
				
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">履历卡类型</div>
					<div class="font-size-9">Career Type</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<select name="common_llklx" class="form-control">
					</select>
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">履历卡号</div>
					<div class="font-size-9">Career No.</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<input name="common_llkbh" class="form-control" maxlength="50" type="text">
				</div>
			</div>
			<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">飞机站位</div>
					<div class="font-size-9">A/C</div>
				</div>
				<div id="fjzw_div_edit" class="col-lg-10 col-sm-10 col-xs-10 padding-leftright-8">
					<div class="input-group " style="width: 100%">
                		<input id="common_fjzw" name="common_fjzw" type="text" maxlength="500" class="form-control" />
	                    <span name="common_fjzw_btn" class="input-group-addon btn btn-default" ><i class='icon-search'></i></span>
                	</div>
				</div>
			</div>
			<div class="clearfix"></div>
			
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">装机件类型</div>
					<div class="font-size-9">Installed Type</div>
				</div>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
					<select name="common_zjjlx" class="form-control">
					</select>
				</div>
			</div>
			<div class="clearfix"></div>
			
			<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
				<div class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">改装记录</div>
					<div class="font-size-9">Record</div>
				</div>
				<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
					<textarea name="common_bjgzjl" style="height: 55px;" class="form-control" maxlength="1000"></textarea>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		
		
		
		
		<div class="panel panel-primary">
			<div class="panel-heading bg-panel-heading">
				<div class="font-size-12">部件监控值初始化</div>
				<div class="font-size-9">Monitor Value Init</div>
			</div>
			<div class="panel-body padding-left-0 padding-right-0 padding-bottom-0">
			
				<div name="common_init_div" style="display: none;">
					<div class="col-lg-12 padding-left-0 padding-right-0 identifying" style="margin-bottom:5px;margin-top: -8px;">
						<div class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<small>注意：</small>
						</div>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<small>部件初始化值是飞机状态监控重要信息，请确保填写信息准确无误。</small>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<div class="text-right padding-left-0 padding-right-0 initDes">
							<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>初始化日期</div>
							<div class="font-size-9">Init Date</div>
						</div>
						<div class="padding-leftright-8 initCon">
							<input name="common_cal" jklbh="1_10" jkflbh="1D" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" type="text">
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<div class="text-right padding-left-0 padding-right-0 initDes">
							<div class="font-size-12 margin-topnew-2">初始化飞行时间</div>
							<div class="font-size-9">Init FH</div>
						</div>
						<div class="padding-leftright-8 initCon">
							<div class="input-group"> 
								<input name="common_fh" jklbh="2_10_FH" jkflbh="2T" class="form-control input-sm validate-time" maxlength="12" type="text">
								<span class="input-group-addon" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FH</span>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<div class="text-right padding-left-0 padding-right-0 initDes">
							<div class="font-size-12 margin-topnew-2">初始化飞行循环</div>
							<div class="font-size-9">Init FC</div>
						</div>
						<div class="padding-leftright-8 initCon">
							<div class="input-group"> 
								<input name="common_fc" jklbh="3_10_FC" jkflbh="3C" class="form-control input-sm validate-cycle" maxlength="12" type="text">
								<span class="input-group-addon" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FC</span>
							</div>
						</div>
					</div>
					
					<div name="common_init_eh" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" style="display: none;">
						<div class="text-right padding-left-0 padding-right-0 initDes">
							<div class="font-size-12 margin-topnew-2">初始化发动机时间</div>
							<div class="font-size-9">Init EH</div>
						</div>
						<div class="padding-leftright-8 initCon">
							<div class="input-group"> 
								<input name="common_eh" jklbh="2_30_EH" jkflbh="2T" class="form-control input-sm validate-time" maxlength="12" type="text">
								<span class="input-group-addon" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">EH</span>
							</div>
						</div>
					</div>
					<div name="common_init_ec" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" style="display: none;">
						<div class="text-right padding-left-0 padding-right-0 initDes">
							<div class="font-size-12 margin-topnew-2">初始化发动机循环</div>
							<div class="font-size-9">Init EC</div>
						</div>
						<div class="padding-leftright-8 initCon">
							<div class="input-group"> 
								<input name="common_ec" jklbh="3_30_EC" jkflbh="3C" class="form-control input-sm validate-cycle" maxlength="12" type="text">
								<span class="input-group-addon" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">EC</span>
							</div>
						</div>
					</div>
					
					<div name="common_init_apuh" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" style="display: none;">
						<div class="text-right padding-left-0 padding-right-0 initDes">
							<div class="font-size-12 margin-topnew-2">初始化APU时间</div>
							<div class="font-size-9">Init APUH</div>
						</div>
						<div class="padding-leftright-8 initCon">
							<div class="input-group"> 
								<input name="common_apuh" jklbh="2_20_AH" jkflbh="2T" class="form-control input-sm validate-time" maxlength="12" type="text">
								<span class="input-group-addon" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">APUH</span>
							</div>
						</div>
					</div>
					<div name="common_init_apuc" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" style="display: none;">
						<div class="text-right padding-left-0 padding-right-0 initDes">
							<div class="font-size-12 margin-topnew-2">初始化APU循环</div>
							<div class="font-size-9">Init APUC</div>
						</div>
						<div class="padding-leftright-8 initCon">
							<div class="input-group"> 
								<input name="common_apuc" jklbh="3_20_AC" jkflbh="3C" class="form-control input-sm validate-cycle" maxlength="12" type="text">
								<span class="input-group-addon" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">APUC</span>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<hr class="margin-left-8 margin-right-8 margin-bottom-10" style="margin-top: 3px;"/>
				</div>
				
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<div class="text-right padding-left-0 padding-right-0 initDes">
						<div class="font-size-12 margin-topnew-2">出厂日期</div>
						<div class="font-size-9">Date</div>
					</div>
					<div class="padding-leftright-8 initCon">
						<input name="common_chucrq" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" type="text">
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<div class="text-right padding-left-0 padding-right-0 initDes">
						<div class="font-size-14 margin-top-8">CAL</div>
					</div>
					<div class="padding-leftright-8 initCon">
						<div class="font-size-14 margin-top-8" name="common_ccsj"></div>
					</div>
				</div>
				<div class="clearfix"></div>
				
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<div class="text-right padding-left-0 padding-right-0 initDes">
						<div class="font-size-12 margin-topnew-2">TSN</div>
						<div class="font-size-9">TSN</div>
					</div>
					<div class="padding-leftright-8 initCon">
						<input name="common_tsn" class="form-control validate-time" maxlength="10" type="text">
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<div class="text-right padding-left-0 padding-right-0 initDes">
						<div class="font-size-12 margin-topnew-2">TSO</div>
						<div class="font-size-9">TSO</div>
					</div>
					<div class="padding-leftright-8 initCon">
						<input name="common_tso" class="form-control validate-time" maxlength="10" type="text">
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<div class="text-right padding-left-0 padding-right-0 initDes">
						<div class="font-size-12 margin-topnew-2">CSN</div>
						<div class="font-size-9">CSN</div>
					</div>
					<div class="padding-leftright-8 initCon">
						<input name="common_csn" class="form-control validate-cycle" maxlength="10" type="text">
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<div class="text-right padding-left-0 padding-right-0 initDes">
						<div class="font-size-12 margin-topnew-2">CSO</div>
						<div class="font-size-9">CSO</div>
					</div>
					<div class="padding-leftright-8 initCon">
						<input name="common_cso" class="form-control validate-cycle" maxlength="10" type="text">
					</div>
				</div>
				<div class="clearfix"></div>
				
				<hr class="margin-left-8 margin-right-8 margin-bottom-10" style="margin-top: 3px;"/>
				
				<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
				    <div class="col-lg-1  col-md-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">适用维修项目</div>
						<div class="font-size-9">Maintenance Item</div>
					</div>
					<div class="col-lg-11 col-md-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
							<table class="table table-bordered table-set table-thin">
								<thead class="nav-tabs">
							   		<tr>
							   			<th class="colwidth-10">
											<div class="font-size-12 line-height-12">任务号</div>
											<div class="font-size-9 line-height-12">Task No.</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 line-height-12">版本</div>
											<div class="font-size-9 line-height-12">Rev.</div>
										</th>
										<th class="colwidth-25">
											<div class="font-size-12 line-height-12">任务描述</div>
											<div class="font-size-9 line-height-12">Task Description</div>
										</th>
										<th class="colwidth-9">
											<div class="font-size-12 line-height-12">监控项目</div>
											<div class="font-size-9 line-height-12">Monitor Item</div>
										</th>
										<th class="colwidth-9">
												<div class="font-size-12 line-height-12">首检</div>
												<div class="font-size-9 line-height-12">INTI</div>	
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 line-height-12">周期</div>
											<div class="font-size-9 line-height-12">Cycle</div>
										</th>
										<th class="colwidth-11">
											<div class="font-size-12 line-height-12">容差(-/+)</div>
											<div class="font-size-9 line-height-12">Tolerance(-/+)</div>
										</th>
							 		</tr>
								</thead>
								<tbody name="common_maintenance_list">
									<tr><td class="text-center" colspan="7">暂无数据 No data.</td></tr>
								</tbody>
							</table>
							<input name="common_skbs" type="hidden">
							<input name="common_ssbs" type="hidden">
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	
		<div class="clearfix"></div>
		
		<div class="panel panel-primary">
			<div class="panel-heading bg-panel-heading">
				<div class="font-size-12">拆装信息及其他</div>
				<div class="font-size-9">Other</div>
			</div>
			<div class="panel-body padding-left-0 padding-right-0 padding-bottom-0">
				<div class="col-lg-2 col-md-2 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-6 col-md-6 col-sm-6 col-xs-6 text-right padding-left-0 padding-right-0">
					</span>
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 padding-leftright-8">
						<div class="pull-left">
						 <div class="font-size-12 margin-topnew-2">装上信息</div>
							<div class="font-size-9 ">Installed Info</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				<div class="col-lg-10 col-md-10 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">安装记录单号</div>
							<div class="font-size-9">Record No.</div>
						</div>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input name="common_azjldh" class="form-control" maxlength="50" type="text">
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0">
						<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>安装日期</div>
							<div class="font-size-9">Date</div>
						</div>
						<div class="col-lg-5 col-sm-5 col-xs-5 padding-left-8 padding-right-0 form-group">
							<input class="form-control date-picker" name="common_azrq" maxlength="10" data-date-format="yyyy-mm-dd" type="text">
						</div>
						<div class="col-lg-3 col-sm-3 col-xs-3 padding-left-0 padding-right-8">
							<input class="form-control time-masked" name="common_azsj" type="text" style="border-left: 0;">
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">安装人</div>
							<div class="font-size-9">Person</div>
						</div>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<div class="input-group col-xs-12">
								<input name="common_azr_display" class="form-control" type="text" maxlength="100">
								<input name="common_azr_value" type="hidden">
		                    	<span name="common_azr_btn" class="input-group-addon btn btn-default"><i class="icon-search"></i></span>
		                	</div>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				
				<div class="col-lg-2 col-md-2 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-6 col-md-6 col-sm-6 col-xs-6 text-right padding-left-0 padding-right-0">
					 	<input name="common_sfcx" value="1" type="checkbox">
					</span>
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 padding-leftright-8">
						<div class="pull-left" style="min-height:34px;">
						 <div class="font-size-12 margin-topnew-2">是否拆下</div>
							<div class="font-size-9 ">Removed</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				
				<div class="col-lg-10 col-md-10 col-sm-12 col-xs-12 padding-left-0 padding-right-0" name="common_cxxx_div" style="display: none;">
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>拆下记录单号</div>
							<div class="font-size-9">Record No.</div>
						</div>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input name="common_ccjldh" class="form-control" maxlength="50" type="text">
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0">
						<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>拆下日期</div>
							<div class="font-size-9">Date</div>
						</div>
						<div class="col-lg-5 col-sm-5 col-xs-5 padding-left-8 padding-right-0 form-group">
							<input class="form-control date-picker" name="common_cxrq" maxlength="10" data-date-format="yyyy-mm-dd" type="text">
						</div>
						<div class="col-lg-3 col-sm-3 col-xs-3 padding-left-0 padding-right-8">
							<input class="form-control time-masked" name="common_cxsj" style="border-left: 0;" type="text">
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<div class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>拆下人</div>
							<div class="font-size-9">Person</div>
						</div>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<input name="common_ccr_value" type="hidden">
							<div class="input-group col-xs-12">
								<input name="common_ccr_display" class="form-control" type="text" maxlength="100">
		                    	<span name="common_ccr_btn" class="input-group-addon btn btn-default"><i class="icon-search"></i></span>
		                	</div>
						</div>
					</div>
				</div>
				
				<div class="clearfix"></div>
				
				<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
					<div class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">备注</div>
						<div class="font-size-9">Note</div>
					</div>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<textarea name="common_bz" style="height: 75px;" class="form-control" maxlength="300"></textarea>
					</div>
				</div>
				<div class="clearfix"></div>
				
				<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
				    <div class="col-lg-1  col-md-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">证书信息</div>
						<div class="font-size-9">Certificate Info</div>
					</div>
					<div class="padding-leftright-8" name="certificate_table_div">
						<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
							<table class="table table-thin table-bordered table-striped table-hover table-set " name="installationlist_certificate_table">
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
										
								<tbody name="installationlist_certificate_list"><tr class="noData"><td class="text-center" colspan="6">暂无数据 No data.</td></tr></tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</form>	
	</div>
	
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/installationlist/installationlist_common.js"></script>