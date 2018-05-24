<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="material_shelf" tabindex="-1" role="dialog"  aria-labelledby="toolStockIn_open_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:85%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">工具上架</div>
							<div class="font-size-12" id="modalHeadENG">Tool Stock In</div>
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
		            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">物料类别</div>
							<div class="font-size-9 ">Type</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="hclx" class="form-control" type="text" readonly>
						</div>
				    </div>
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">件号</div>
							<div class="font-size-9 ">PN</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="bjh" class="form-control" type="text" readonly>
						</div>
				    </div>
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">名称/描述</div>
							<div class="font-size-9 ">Name/Desc</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="bjmc" class="form-control" type="text" readonly>
						</div>
				    </div>
				     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">待上架数量</div>
							<div class="font-size-9 ">Quantity</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group col-xs-12">
								<input id="kcsl" class="form-control" type="text" readonly>
								<span id="dw" class="input-group-addon" style="min-width: 35px;"></span>
							</div>
						</div>
				    </div>
				     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">批次号</div>
							<div class="font-size-9 ">Quantity</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="pch" class="form-control" type="text" readonly>
						</div>
				    </div>
				     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">序列号</div>
							<div class="font-size-9 ">Quantity</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="sn" class="form-control" type="text" readonly>
						</div>
				    </div>
				     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">收货单号</div>
							<div class="font-size-9 ">Quantity</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="shdh" class="form-control" type="text" readonly>
						</div>
				    </div>
				     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">质检单</div>
							<div class="font-size-9 ">Quantity</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="jydh" class="form-control" type="text" readonly>
						</div>
				    </div>
				     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">收货类型</div>
							<div class="font-size-9 ">Quantity</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="shlx" class="form-control" type="text" readonly>
						</div>
				    </div>
				     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">收货人</div>
							<div class="font-size-9 ">Quantity</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="shr" class="form-control" type="text" readonly>
						</div>
				    </div>
				     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">收货时间</div>
							<div class="font-size-9 ">Quantity</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="shsj" class="form-control" type="text" readonly>
						</div>
				    </div>
				     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">发货单位</div>
							<div class="font-size-9 ">Quantity</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="lydw" class="form-control" type="text" readonly>
						</div>
				    </div>
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class='color-red'>*</span>上架仓库</div>
							<div class="font-size-9 ">Warehouse</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control' id="ck" onchange="materialShelf.buildStorageLocation()">
							</select>
						</div>
				    </div>
				     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">单价成本</div>
							<div class="font-size-9 ">Cost</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						   <div class="input-group" style="width: 100%;">
							    <input id="kccb" maxlength="10" class="form-control" type="text" onkeyup="materialShelf.validateDecimal(this)">
								<span class="input-group-btn">
									<select id="biz" class='form-control' style='width:70px;border-left:0px;'>
									</select>
								</span>
						   </div>
						</div>
				    </div>
<!-- 				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group"> -->
<!-- 						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0"> -->
<!-- 							<div class="font-size-12  margin-topnew-2">价值</div> -->
<!-- 							<div class="font-size-9 ">Value</div> -->
<!-- 						</label> -->
<!-- 						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8"> -->
<!-- 						   <div class="input-group" style="width: 100%;"> -->
<!-- 							    <input id="jz" maxlength="10" class="form-control" type="text" onkeyup="materialShelf.validateDecimal(this)"> -->
<!-- 								<span class="input-group-btn"> -->
<!-- 									<select id="jzbz" class='form-control' style='width:70px;border-left:0px;'> -->
<!-- 									</select> -->
<!-- 								</span> -->
<!-- 						   </div> -->
<!-- 						</div> -->
<!-- 				    </div> -->
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">批量设置库位</div>
							<div class="font-size-9 ">Library</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control' id="kw_onekey" onchange="materialShelf.setStorageLocation()">
							</select>
						</div>
				    </div>
				    <div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">库位信息</div>
							<div class="font-size-9">Repository Info</div>
						</label>
						<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<!-- 库位信息 -->
							<div id="" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0  table-set" style="overflow-x: auto;">
					    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
						<thead>                              
							<tr>
								<th  class='colwidth-3'>
									<button class="line6 line6-btn" onclick="materialShelf.addRow()" type="button">
										<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
								    </button>
								</th>
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18" >库位</div>
									<div class="font-size-9 line-height-18">Library</div>
								</th>
								<th class='colwidth-9' onclick="" name="">
									<div class="font-size-12 line-height-18">上架数量(单位)</div>
									<div class="font-size-9 line-height-18">Quantity (unit)</div>
								</th>
								<th class='colwidth-20' onclick="" name="">
										<div class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">SN</div>
								</th>
								
							</tr> 
						</thead>
						<tbody id='material_shelf_list'>
							<tr class="no-data"><td class="text-center" colspan="4" title="暂无数据 No data.">暂无数据 No data.</td></tr>
						</tbody>
					</table>
					</div>
					</div>
					</div>
				    <div class='clearfix'></div>
		            </div>
		            <!-- 附加信息 -->
		            <div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="" href="#additionalInfo" class="collapsed">
								<div class="pull-left">
									<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
								</div>
								<div class="pull-left">
									<div class="font-size-12">附加信息(选填)</div>
									<div class="font-size-9 ">Additional info(Optional)</div>
								</div>
								<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
								<div class="clearfix"></div>
							</a>
						</div>
						<div id="additionalInfo" class="panel-collapse collapse" >
							<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
							    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12  margin-topnew-2">部件来源</div>
										<div class="font-size-9 ">Component source</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<select id="hcly" class='form-control'>
										</select>
									</div>
							    </div>
							    
							    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12  margin-topnew-2">GRN</div>
										<div class="font-size-9 ">GRN</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input id="grn" maxlength="100" class="form-control" type="text" >
									</div>
							    </div>
							    
							    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12  margin-topnew-2">生产日期</div>
										<div class="font-size-9 ">Date</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input id="scrq" class="form-control date-picker" type="text" data-date-format="yyyy-mm-dd">
									</div>
							    </div>
							    
							    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12  margin-topnew-2">货架寿命</div>
										<div class="font-size-9 ">Shelf Life</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input id="hjsm" class="form-control date-picker" type="text"  data-date-format="yyyy-mm-dd">
									</div>
							    </div>
							    
							     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12  margin-topnew-2">TSN</div>
										<div class="font-size-9 ">TSN</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input id="tsn" maxlength="10" class="form-control" type="text" onkeyup="materialShelf.validateTime(this)">
									</div>
							    </div>
							    
							    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12  margin-topnew-2">TSO</div>
										<div class="font-size-9 ">TSO</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input id="tso" maxlength="10" class="form-control" type="text" onkeyup="materialShelf.validateTime(this)">
									</div>
							    </div>
							    
							    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12  margin-topnew-2">CSN</div>
										<div class="font-size-9 ">CSN</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input id="csn" maxlength="10" class="form-control" type="text" onkeyup="materialShelf.validateCycle(this)">
									</div>
							    </div>
							    
							    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12  margin-topnew-2">CSO</div>
										<div class="font-size-9 ">CSO</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input id="cso" maxlength="10" class="form-control" type="text" onkeyup="materialShelf.validateCycle(this)">
									</div>
							    </div>
							    
							     <div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">存放要求</div>
										<div class="font-size-9">Requirements</div>
									</label>
									<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea id="cfyq" maxlength="150" class='form-control' style='height:75px;'></textarea>
									</div>
								</div>
						
							</div>
						</div>
					</div>
					<!-- 证书信息(选填) -->
		            <div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" id='certificate_infoParent' data-parent="" href="#certificate_info" class="collapsed">
								<div class="pull-left">
									<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
								</div>
								<div class="pull-left">
									<div class="font-size-12">证书信息(选填)</div>
									<div class="font-size-9 ">Certificate info(Optional)</div>
								</div>
								<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
								<div class="clearfix"></div>
							</a>
						</div>
						<div id="certificate_info" class="panel-collapse collapse" >
							<div class="panel-body padding-left-0 padding-right-0" >
							    	<div id="" class="col-lg-12 col-md-12 padding-leftright-8" style="overflow-x: auto;">
					    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
						<thead>                              
							<tr>
								<th class='colwidth-3' name="common_certificate_addTh">
									<button class="line6 line6-btn" type="button" name="common_certificate_addBtn">
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
						<tbody id='material_shelf_certificate'>
						</tbody>
					</table>
					</div>
						
							</div>
						</div>
					</div>
	            </div> 
	             <div class='clearfix'></div>          
           	</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<i class='glyphicon glyphicon-info-sign alert-info' style="display: none;"></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
	                    	<button type="button" onclick="materialShelf.submitData()" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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

<script type="text/javascript" src="${ctx}/static/js/thjw/material2/outin/toolstockin/toolstockin_shelves.js"></script>