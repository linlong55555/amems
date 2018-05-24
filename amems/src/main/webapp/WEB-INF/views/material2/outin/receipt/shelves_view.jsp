<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="shelves_view_alert" tabindex="-1" role="dialog"  aria-labelledby="shelves_view_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
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
							<input id="" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">件号</div>
							<div class="font-size-9 ">PN</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">名称/描述</div>
							<div class="font-size-9 ">Name/Desc</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				     <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">收货数量</div>
							<div class="font-size-9 ">Quantity</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class='color-red'>*</span>上架仓库</div>
							<div class="font-size-9 ">Warehouse</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">单价成本</div>
							<div class="font-size-9 ">Cost</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						   <div id="" class="input-group" style="width: 100%;">
							   <input id="" class="form-control" maxlength="16" placeholder="" type="text" readonly>
								<span class="input-group-btn">
									<select class='form-control' style='width:50px;border-left:0px;' disabled>
										<option>币种</option>
									</select>
								</span>
						   </div>
						</div>
				    </div>
				    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">批量设置库位</div>
							<div class="font-size-9 ">Library</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input id="" class="form-control" maxlength="16" placeholder="" type="text" readonly>
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
						<tbody id='libraryTbody'>
							<tr>
								<td>1</td>
								<td>1</td>
								<td>1</td>
							</tr>
							<tr>
								<td>1</td>
								<td>1</td>
								<td>1</td>
							</tr>
							<tr>
								<td>1</td>
								<td>1</td>
								<td>1</td>
							</tr>
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
	                    	<button  type="button" data-dismiss="modal"  class="btn btn-primary padding-top-1 padding-bottom-1">
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
