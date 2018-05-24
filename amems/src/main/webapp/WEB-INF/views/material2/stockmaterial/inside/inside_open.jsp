<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="inside_open_alert" tabindex="-1" role="dialog"  aria-labelledby="inside_open_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:85%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">库存详情</div>
							<div class="font-size-12" id="modalHeadENG">Inventory Details</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" id="form">
           		 <div class="col-xs-12 margin-top-8 ">
		            <div class="input-group-border"> 
		           <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">部件号</div>
							<div class="font-size-9 ">PN</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						    <input id="kcid_view" class="form-control"  type="hidden" >	
							<input id="bjh_view" class="form-control" maxlength="16"  type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">名称</div>
							<div class="font-size-9 ">Name</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="ckmc_view" class="form-control" maxlength="16"  type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">批次号</div>
							<div class="font-size-9 ">Batch No.</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="pch_view" class="form-control" maxlength="16"  type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">序列号</div>
							<div class="font-size-9 ">SN</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="sn_view" class="form-control" maxlength="16"   type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">仓库</div>
							<div class="font-size-9 ">Warehouse</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select id="ck_view" class="form-control" maxlength="16" placeholder=""  ></select>
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">库位</div>
							<div class="font-size-9 ">Library</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						
						    <input type="hidden" id="kwid_view">
							<input id="kw_view" class="form-control" maxlength="16"  type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">库存数量</div>
							<div class="font-size-9 ">Quantity</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div id="" class="input-group" style="width: 100%;">
							    <input id="kcsldw_view"  class="form-control" type="text" >
							    <input id="kcsl_view" ondblclick="" class="form-control" type="hidden">
							    <input id="dw_view" class="form-control" type="hidden">
						   </div>
						</div>
				    </div>
	
				     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">冻结数量</div>
							<div class="font-size-9 ">Quantity</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div id="djno" class="input-group" style="width: 100%;">
							     <a href="javascript:inside_Modal.showFrozenHistory()" id="djsl_link"></a>
							    <input id="djsl_view" class="form-control" type="hidden">
						   </div>
						</div>
				    </div>
				    
				    <div class='clearfix'></div> 
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">成本</div>
							<div class="font-size-9 ">Cost</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div id="" class="input-group" style="width: 100%;">
							    <input id="cb_view" ondblclick="" class="form-control" type="text" name="cb_view">
								<span class="input-group-btn">
									<select class='form-control' id="cbbz_view" style='width:50px;border-left:0px;'>
									</select>
								</span>
						   </div>
						</div>
				    </div>
				    
				    
<!-- 				   <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group"> -->
<!-- 						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0"> -->
<!-- 							<div class="font-size-12  margin-topnew-2">价值</div> -->
<!-- 							<div class="font-size-9 ">Value</div> -->
<!-- 						</label> -->
<!-- 						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8"> -->
<!-- 							<div id="" class="input-group" style="width: 100%;"> -->
<!-- 							    <input id="jz_view" ondblclick="" class="form-control" type="text" name="jz_view"> -->
<!-- 								<span class="input-group-btn"> -->
<!-- 									<select class='form-control' id="jzbz_view" style='width:50px;border-left:0px;'> -->
<!-- 									</select> -->
<!-- 								</span> -->
<!-- 						   </div> -->
<!-- 						</div> -->
<!-- 				    </div> -->
				    
				    
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">产权</div>
							<div class="font-size-9 ">Right</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group col-xs-12">
							<input id="cqid_view" class="form-control"  type="hidden">
							<input id="cqbh_view" class="form-control readonly-style"  ondblclick="inside_Modal.loadCqView()" type="text" readonly="readonly">
							<span id="cqbutton" class="input-group-addon btn btn-default" onclick="inside_Modal.loadCqView()" style="display: table-cell;">
							 	<i class="icon-search"></i>
							</span>

							</div>
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">货架寿命</div>
							<div class="font-size-9 ">Life</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="hjsm_view" class="form-control" data-date-format="yyyy-mm-dd"  maxlength="16" placeholder="" type="text" name='date-picker' >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">来源</div>
							<div class="font-size-9 ">Source</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control' id="hcly_view"  name="hcly_view">
							</select>
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">GRN</div>
							<div class="font-size-9 ">GRN</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="grn_view" class="form-control" maxlength="9" placeholder="" type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">TSN</div>
							<div class="font-size-9 ">TSN</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="tsn_view" name="common_tsn" data-bv-field="common_tsn" class="form-control time-masked" maxlength="9"  type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">TSO</div>
							<div class="font-size-9 ">TSO</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="tso_view" name="common_tso" data-bv-field="common_tso" class="form-control time-masked" maxlength="9" type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">CSN</div>
							<div class="font-size-9 ">CSN</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="csn_view" name="common_csn"  class="form-control" maxlength="9" type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">CSO</div>
							<div class="font-size-9 ">CSO</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="cso_view" name="common_cso"  class="form-control" maxlength="9"  type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">生产日期</div>
							<div class="font-size-9 ">Date</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="scrq_view" class="form-control" maxlength="16" data-date-format="yyyy-mm-dd" type="text" name='date-picker'>
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="zt_view_div">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">状态</div>
							<div class="font-size-9 ">Status</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="zt_view" class="form-control" maxlength="16" type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="qczt_view_div">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">器材状态</div>
							<div class="font-size-9 ">Status</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select id="qczt_view" class="form-control"></select>
						</div>
				    </div>
				    
				    <div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">存放要求</div>
							<div class="font-size-9 ">Requirements</div>
						</label>
						<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class='form-control' style='height:55px;' maxlength="160" id='cfyq_view'></textarea>
						</div>
				    </div>
				    
				    <div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">备注</div>
							<div class="font-size-9 ">Remark</div>
						</label>
						<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class='form-control' style='height:55px;' maxlength="300"  id='bz_view'></textarea>
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">物料类别</div>
							<div class="font-size-9 ">Type</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select id="wllb_view" class="form-control" maxlength="16" >
							</select>
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">厂家件号</div>
							<div class="font-size-9 ">CJ No.</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="cjjh_view" class="form-control" maxlength="16" placeholder="" type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">规格</div>
							<div class="font-size-9 ">Specifications</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="gg_view" class="form-control" maxlength="16" placeholder="" type="text">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">型号</div>
							<div class="font-size-9 ">Model</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="xh_view" class="form-control" maxlength="16"  type="text" >
						</div>
				    </div>				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">管理级别</div>
							<div class="font-size-9 ">Level</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select id="gljb_view" class="form-control" maxlength="16" placeholder=""  ></select>
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">制造厂商</div>
							<div class="font-size-9 ">Manufacturer</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="zzcs_view" class="form-control" maxlength="16" placeholder="" type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">最大库存数</div>
							<div class="font-size-9 ">Maximum</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="maxkcs_view" class="form-control" maxlength="16" placeholder="" type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">最小库存数</div>
							<div class="font-size-9 ">Minimum </div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="minkcs_view" class="form-control" maxlength="16" type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">上架时间</div>
							<div class="font-size-9 ">Date</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="sjsj_view" class="form-control" maxlength="16" data-date-format="yyyy-mm-dd"  type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">上架人</div>
							<div class="font-size-9 ">Shelf</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="sjr_view" class="form-control" maxlength="16"  type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">最近修改时间</div>
							<div class="font-size-9 ">Date</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="update_view" class="form-control" maxlength="16" data-date-format="yyyy-MM-dd HH:mm:ss"  type="text" >
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">最近修改人</div>
							<div class="font-size-9 ">Modifier</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="update_person_view" class="form-control" maxlength="16"  type="text" >
						</div>
				    </div>
				    
				    
				    <div class='clearfix'></div>
		            </div>
		            <!-- 附加信息 -->
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
								<tbody id="store_inner_certificate_list"></tbody>


								</table>
								</div>
						
							</div>
						</div>
					</div>
					
		            <div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" id='' data-parent="" href="#resume_info" class="collapsed">
								<div class="pull-left">
									<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
								</div>
								<div class="pull-left">
									<div class="font-size-12">库存履历</div>
									<div class="font-size-9 ">Stock resume</div>
								</div>
								<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
								<div class="clearfix"></div>
							</a>
						</div>
						<div id="resume_info" class="panel-collapse collapse" >
							<div class="panel-body padding-left-0 padding-right-0" >
							    	<div id="" class="col-lg-12 col-md-12 padding-leftright-8" style="overflow-x: auto;">
								    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
									<thead>                              
										<tr>
											<th class='colwidth-9'>
												<div class="font-size-12 line-height-18" >操作时间</div>
												<div class="font-size-9 line-height-18">Date</div>
											</th>
											<th class='colwidth-9' onclick="" name="">
												<div class="font-size-12 line-height-18">操作人</div>
												<div class="font-size-9 line-height-18">Operator</div>
											</th>
											<th class='colwidth-20' onclick="" name="">
													<div class="font-size-12 line-height-18">操作说明</div>
													<div class="font-size-9 line-height-18">Description</div>
											</th>
											
										</tr> 
									</thead>
									<tbody id='hisTbody'>							
									</tbody>
								</table>
								</div>
						
							</div>
						</div>
					</div>
					<!--  -->
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
	                    	<button id="save_btn" type="button" onclick="inside_Modal.saveData()" class="btn btn-primary padding-top-1 padding-bottom-1 " >
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button id="print" type="button" onclick="inside_Modal.printInstock();" class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">打印</div>
								<div class="font-size-9">Print</div>
							</button>
							<button id="cancel" type="button" onclick="inside_Modal.closeModal();" class="btn btn-primary padding-top-1 padding-bottom-1" >
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
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/stockmaterial/inside/inside_open.js"></script>
<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>

