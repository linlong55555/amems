<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="shelf_open_win" tabindex="-1" role="dialog"  aria-labelledby="shelf_open_win" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:75%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">上架</div>
							<div class="font-size-12" id="modalHeadENG">The shelves</div>
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
		            <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class='color-red'>*</span>物料类别</div>
							<div class="font-size-9 ">Type</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="shelf_wllb" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">件号</div>
							<div class="font-size-9 ">PN</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="shelf_bjh" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">名称/描述</div>
							<div class="font-size-9 ">Name/Desc</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="shelf_bjmc" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				     <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">收货数量</div>
							<div class="font-size-9 ">Quantity</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="shelf_shsl" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class='color-red'>*</span>上架仓库</div>
							<div class="font-size-9 ">Warehouse</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select id="shelf_ck" onchange="shelf.buildStorageLocation()" class='form-control'>
								<option></option>
							</select>
						</div>
				    </div>
				    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">单价成本</div>
							<div class="font-size-9 ">Cost</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						   <div id="" class="input-group" style="width: 100%;">
							    <input id="shelf_kccb" onkeyup="shelf.validateDecimal(this)" maxlength="10" class="form-control" type="text">
								<span class="input-group-btn">
									<select id="shelf_biz" class="form-control" style="width:70px;border-left:0px;">
									</select>
								</span>
						   </div>
						</div>
				    </div>
				    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">价值</div>
							<div class="font-size-9 ">Value</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						   <div id="" class="input-group" style="width: 100%;">
							    <input id="shelf_jz" onkeyup="shelf.validateDecimal(this)"  maxlength="10" class="form-control" type="text">
								<span class="input-group-btn">
									<select id="shelf_jzbz" class="form-control" style="width:70px;border-left:0px;">
									</select>
								</span>
						   </div>
						</div>
				    </div>
				    <div class="col-md-4 col-sm-6 col-xs-6 padding-left-0 padding-right-0 form-group notView">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">批量设置库位</div>
							<div class="font-size-9 ">Library</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					        <select id="shelf_kw_onekey" class="form-control selectpicker" data-live-search="true" data-container="#shelf_open_win" data-size="10" onchange="shelf.setStorageLocation()"></select>
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
					    <table class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
						<thead>                              
							<tr>
								<th class='colwidth-3 notView'>
									<button class="line6 line6-btn" onclick="shelf.addRow()" type="button">
										<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
								    </button>
								</th>
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18" >库位</div>
									<div class="font-size-9 line-height-18">Library</div>
								</th>
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18">上架数量(<span id="shelf_unit"></span>)</div>
									<div class="font-size-9 line-height-18">Quantity (unit)</div>
								</th>
								<th class='colwidth-20'>
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">SN</div>
								</th>
							</tr> 
						</thead>
						<tbody id='shelf_tbody'>
							<tr class="no-data"><td class="text-center" colspan="4" title="暂无数据 No data.">暂无数据 No data.</td></tr>
						</tbody>
					</table>
					</div>
					</div>
					</div>
				    <div class='clearfix'></div>
		            </div>
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
	                    	<button type="button" onclick="shelf.save()" class="btn btn-primary padding-top-1 padding-bottom-1 notView">
								<div class="font-size-12">确认</div>
								<div class="font-size-9">Confirm</div>
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
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/outin/receipt/shelves_open.js"></script>