<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="check_open_alert" tabindex="-1" role="dialog"  aria-labelledby="check_open_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:90%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">新建库存</div>
							<div class="font-size-12" id="modalHeadENG">Add</div>
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
		            	<form id="check_open_form">
		            	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color: red">*</span>部件号</div>
								<div class="font-size-9 ">PN</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class=" input-group col-xs-12">
									<input id="bjid_o" type="hidden" />
								    <input id="bjh_o" class="form-control" onBlur="check_open_alert.loadMaterialRel(this)" onkeyup="check_open_alert.changeBjh(this)" type="text">
									<span id="bj_btn_o" class="input-group-addon btn btn-default" onclick="check_open_alert.openMaterialWin()">
			                    		<i class="icon-search"></i>
				                    </span>
								</div>
							</div> 
	                 	</div> 
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">名称</div>
								<div class="font-size-9 ">Name</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="zywms_o" class="form-control" type="text" readonly>
							</div> 
	                 	</div> 
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">批次号</div>
								<div class="font-size-9 ">Batch No.</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="pch_o" class="form-control" type="text" maxlength="50" placeholder='不填写则自动生成' onkeyup="check_open_alert.changeBjh(this)" />
							</div> 
	                 	</div> 
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">序列号</div>
								<div class="font-size-9 ">SN</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="xlh_o" class="form-control" type="text" onblur="check_open_alert.changeKcsl(this)" onkeyup="check_open_alert.changeXlh(this)" />
							</div> 
	                 	</div> 
	                 	
	                 	<div class="clearfix"></div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">仓库</div>
								<div class="font-size-9 ">Warehouse</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="ck_o" class="form-control" onchange="check_open_alert.changeStore();">
								</select>
							</div> 
	                 	</div> 
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">库位</div>
								<div class="font-size-9 ">Library</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class='form-control selectpicker' data-live-search="true" 
								data-container="#check_open_alert" data-size="10" id="kw_o"></select>
								
							</div> 
	                 	</div> 
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span style="color: red">*</span>库存数量</div>
								<div class="font-size-9 ">QTY</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class=" input-group col-xs-12">
							    <input id="kcsl_o" class="form-control" type="text" onkeyup="check_open_alert.changeNum2(this)">
								<span id="jldw_o" class="input-group-addon"  style="background:none;border:0px;padding-left:5px;">
			                    	单位
			                    </span>
								</div>
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">库存成本</div>
								<div class="font-size-9 ">Cost</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class=" input-group col-xs-12">
							    <input  id="kccb_o" class="form-control" type="text" onkeyup="check_open_alert.changeNum2(this)">
								<span class="input-group-addon"  style="background:none;border:0px;padding:0px;">
		                    		<select id="biz_o" class="form-control" style="width:70px;border-left:0px;">
		                    			<option>币种</option>
		                    		</select>
			                    </span>
								</div>
							</div> 
	                 	</div>
	                 	
	                 	<div class="clearfix"></div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">产权</div>
								<div class="font-size-9 ">Right</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class=" input-group col-xs-12">
								    <input id="cqbh_o" class="form-control readonly-style" type="text" ondblclick="check_open_alert.openCqWin()" readonly />
								    <input id="cqid_o" type="hidden" />
									<span id="cq_btn_o" class="input-group-addon btn btn-default" onclick="check_open_alert.openCqWin()">
				                    		<i class="icon-search"></i>
				                    </span>
								</div>
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">货架寿命</div>
								<div class="font-size-9 ">Life</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class=" input-group col-xs-12">
							    <input id="hjsm_o" class="form-control" type="text" name="date-picker" data-date-format="yyyy-mm-dd" onchange="check_open_alert.changeHjsm()">
								<span id="syts_o" class="input-group-addon"  style="background:none;border:0px;padding-left:5px;">
			                    		
			                    </span>
								</div>
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">来源</div>
								<div class="font-size-9 ">Source</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="hcly_o" name="hcly_o" class="form-control"  >
							     </select>
							</div> 
	                 	</div>
	                 	
<!-- 	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group"> -->
<!-- 							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0"> -->
<!-- 								<div class="font-size-12  margin-topnew-2">价值</div> -->
<!-- 								<div class="font-size-9 ">Cost</div> -->
<!-- 							</span> -->
<!-- 							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8"> -->
<!-- 								<div class=" input-group col-xs-12"> -->
<!-- 							    <input  id="jz_o" class="form-control" type="text" onkeyup="check_open_alert.changeNum2(this)"> -->
<!-- 								<span class="input-group-addon"  style="background:none;border:0px;padding:0px;"> -->
<!-- 		                    		<select id="jzbz_o" class="form-control" style="width:70px;border-left:0px;"> -->
<!-- 		                    			<option>币种</option> -->
<!-- 		                    		</select> -->
<!-- 			                    </span> -->
<!-- 								</div> -->
<!-- 							</div>  -->
<!-- 	                 	</div> -->
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">GRN</div>
								<div class="font-size-9 ">GRN</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="grn_o" class="form-control" type="text" maxlength="100" >
							</div> 
	                 	</div>
	                 	<div class="clearfix"></div>
	                 	
	                 	
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">TSN</div>
								<div class="font-size-9 ">TSN</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="tsn_o" class="form-control" type="text" maxlength="16" onkeyup="check_open_alert.changeNumRel(this)">
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">TSO</div>
								<div class="font-size-9 ">TSO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="tso_o" class="form-control" type="text" maxlength="16" onkeyup="check_open_alert.changeNumRel(this)">
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">CSN</div>
								<div class="font-size-9 ">CSN</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="csn_o" class="form-control" type="text" maxlength="16" onkeyup="check_open_alert.changeNum(this)">
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">CSO</div>
								<div class="font-size-9 ">CSO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="cso_o" class="form-control" type="text" maxlength="16" onkeyup="check_open_alert.changeNum(this)" />
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">生产日期</div>
								<div class="font-size-9 ">Date</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="scrq_o" class="form-control" type="text" name="date-picker" data-date-format="yyyy-mm-dd">
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">器材状态</div>
								<div class="font-size-9 ">Status</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="qczt_o" class="form-control"></select>
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">存放要求</div>
								<div class="font-size-9">requirements</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="cfyq_o" name="" style="height: 55px;" class="form-control" maxlength="150"></textarea>
							</div>
						</div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">备注</div>
								<div class="font-size-9">Note</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="bz_o" name="" style="height: 55px;" class="form-control" maxlength="300" ></textarea>
							</div>
						</div>
						</form>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">物料类别</div>
								<div class="font-size-9 ">Type</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="hclx_o" class="form-control" type="text" readonly>
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">厂家件号</div>
								<div class="font-size-9 ">MP/N</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="cjjh_o" class="form-control" type="text" readonly>
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">规格</div>
								<div class="font-size-9 ">Spec</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="gg_o" class="form-control" type="text" readonly>
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">型号</div>
								<div class="font-size-9 ">Model</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="xingh_o" class="form-control" type="text" readonly>
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">管理级别</div>
								<div class="font-size-9 ">Level</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="gljb_o" class="form-control" type="text" readonly>
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">制造厂商</div>
								<div class="font-size-9 ">Manufacturer</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="zzcs_o" class="form-control" type="text" readonly>
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">最大库存数</div>
								<div class="font-size-9 ">Maximum</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="maxKcl_o" class="form-control" type="text" readonly>
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">最小库存数</div>
								<div class="font-size-9 ">Minimum</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="minKcl_o" class="form-control" type="text" readonly>
							</div> 
	                 	</div>
	                 	
	                 	<!-- <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">上架时间</div>
								<div class="font-size-9 ">Date</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" type="text" readonly>
							</div> 
	                 	</div>
	                 	
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">上架人</div>
								<div class="font-size-9 ">Shelf</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" type="text" readonly>
							</div> 
	                 	</div> -->
					
	                 	<div class="clearfix"></div>
	                 </div>
	            	 <div class='clearfix'></div>  
				     <!-- 证书信息 -->
		            <div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="" href="#certificate_info" class="collapsed">
								<div class="pull-left">
									<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
								</div>
								<div class="pull-left">
									<div class="font-size-12">证书信息</div>
									<div class="font-size-9 ">Certificate info</div>
								</div>
								<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
								<div class="clearfix"></div>
							</a>
						</div>
						<div id="certificate_info" class="panel-collapse collapse">
							<div class="panel-body padding-left-0 padding-right-0">
							    <div id="" class="col-lg-12 col-md-12 padding-leftright-8" style="overflow-x: auto;">
								    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
									<thead>                              
										<tr>
											<th  class='colwidth-3'>
												<button id="common_certificate_addBtn" class="line6 line6-btn" type="button">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
											    </button>
											</th>
											<th class='colwidth-9'>
												<div class="font-size-12 line-height-18" >证书类型</div>
												<div class="font-size-9 line-height-18">Type</div>
											</th>
											<th class='colwidth-9' onclick="" name="">
												<div class="font-size-12 line-height-18">证书编号</div>
												<div class="font-size-9 line-height-18">Certificate No.</div>
											</th>
											<th class='colwidth-20' onclick="" name="">
													<div class="font-size-12 line-height-18">存放位置</div>
													<div class="font-size-9 line-height-18">Position</div>
											</th>
											<th class='colwidth-9' onclick="" name="">
													<div class="font-size-12 line-height-18">签署日期</div>
													<div class="font-size-9 line-height-18">Date</div>
											</th>
											<th class='colwidth-9' onclick="" name="">
													<div class="font-size-12 line-height-18">附件</div>
													<div class="font-size-9 line-height-18">Attachment</div>
											</th>
											
										</tr> 
									</thead>
								<tbody id="check_open_certificate_list"></tbody>
								</table>
								</div>
						
							</div>
						</div>
					</div>
				    <div class='clearfix'></div>       
           	</div>
           	<div class='clearfix'></div>  
           	</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
	                    	<button id="save_btn" type="button" onclick="check_open_alert.setData();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">确认</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
						    </button>
	                    </span>
	               	</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/store/check/check_open.js"></script>
<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%><!-- 部件弹出框 -->
<!--  弹出框结束-->