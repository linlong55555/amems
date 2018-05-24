<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/store/transfer/transfer_open.js"></script>
<div class="modal fade in modal-new" id="transfer_open_alert" tabindex="-1" role="dialog"  aria-labelledby="transfer_open_alert" aria-hidden="true" data-keyboard="false" data-backdrop="static">
      <div class="modal-dialog" style='width:80%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">移库操作</div>
							<div class="font-size-12" id="modalHeadENG">Transfer operation</div>
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
		            <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">批量目标仓库</div>
							<div class="font-size-9 ">Batch Warehouse</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<select id="ck" onchange="transfer_open.selectCkChange(this)" class='form-control'>
							</select>
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">批量目标库位</div>
							<div class="font-size-9 ">Batch Library</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<select id="kw" onchange="transfer_open.selectKwChange(this)" data-live-search="true"  data-size="10" class='form-control selectpicker'>
							</select>
						</div>
				    </div>
				    
				    <div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">移库说明</div>
							<div class="font-size-9 ">Instructions</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea id="yksm" class="form-control" style="height:75px;"></textarea>
						</div>
				    </div>
				   
				    
				  <!--   <div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">待移库物料清单</div>
							<div class="font-size-9 ">Material List</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<div  class="col-xs-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
							<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="margin-bottom:0px !important;">
								<thead>
									<tr>
										<th class='colwidth-3' >
											<div class="font-size-12 line-height-12">操作</div>
											<div class="font-size-9 line-height-12">Operation</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 line-height-12">部件</div>
											<div class="font-size-9 line-height-12">PN</div>
										</th>
									   	<th class="colwidth-5">
											<div class="font-size-12 line-height-12">批次号/序列号</div>
											<div class="font-size-9 line-height-12">Batch NO./SN</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 line-height-12">库存数量</div>
											<div class="font-size-9 line-height-12">QTY</div>
										</th>
										<th class="colwidth-9">
											<div class="font-size-12 line-height-12">移动冻结</div>
											<div class="font-size-9 line-height-12">Freezing</div></th>
										<th class="colwidth-9">
											<div class="font-size-12 line-height-12">移出数量</div>
											<div class="font-size-9 line-height-12">Operate Time</div>
										</th>
										<th class="colwidth-9">
											<div class="font-size-12 line-height-12">目标仓库</div>
											<div class="font-size-9 line-height-12">Warehouse</div>
										</th>
										<th class="colwidth-9">
											<div class="font-size-12 line-height-12">当前位置</div>
											<div class="font-size-9 line-height-12">Location</div>
										</th>				
									</tr>
								</thead>
							    <tbody id="">
							    <tr>
							    <td colspan="8" style="text-align:center;vertical-align:middle;">暂无数据 No data.</td>
							    </tr>
							    </tbody>
							</table>
							</div>
						</div>
				    </div> -->
				     <div class='clearfix'></div>
	            </div> 
	             <div class='clearfix'></div>   
	              
				    <div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading">
							<div class="font-size-12 ">待移库物料清单</div>
							<div class="font-size-9">Material List</div>
						</div>
						<div class="panel-body padding-leftright-8" >
							<div  class="col-xs-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="margin-bottom:0px !important;">
									<thead>
										<tr>
											<th class='colwidth-5' >
												<div class="font-size-12 line-height-12">操作</div>
												<div class="font-size-9 line-height-12">Operation</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-12">部件</div>
												<div class="font-size-9 line-height-12">PN</div>
											</th>
										   	<th class="colwidth-13">
												<div class="font-size-12 line-height-12">批次号/序列号</div>
												<div class="font-size-9 line-height-12">Batch NO./SN</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-12">库存数量</div>
												<div class="font-size-9 line-height-12">QTY</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-12">移动冻结</div>
												<div class="font-size-9 line-height-12">Freezing</div></th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-12">移出数量</div>
												<div class="font-size-9 line-height-12">Operate Time</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-12">目标仓库</div>
												<div class="font-size-9 line-height-12">Warehouse</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-12">目标库位</div>
												<div class="font-size-9 line-height-12">Library</div>
											</th>
											<th class="colwidth-18">
												<div class="font-size-12 line-height-12">当前位置</div>
												<div class="font-size-9 line-height-12">Location</div>
											</th>				
										</tr>
									</thead>
								    <tbody id="transfer_tbody">
								    <tr >
								    <td style='vertical-align:middle;'>
									    <button class="line6 line6-btn"  type="button" onclick='deletRow(this)'>
									    	<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>
									    </button>
                                    </td>
                                    <td>
                                    	<p class='margin-bottom-0'>部件号</p>
                                    	<p class='margin-bottom-0'>部件名称</p>
                                    </td>
                                    <td>
                                    	<p class='margin-bottom-0'>批次号</p>
                                    	<p class='margin-bottom-0'>序列号</p>
                                    </td>
                                    <td style='vertical-align:middle;'>	
                                    	<p class='margin-bottom-0'>12 / 34</p>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    <input type='checkbox'/>
                                    </td>
                                    <td style='vertical-align:middle;' >
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
								    </tr>
								    <tr >
								    <td style='vertical-align:middle;'>
									    <button class="line6 line6-btn"  type="button" onclick='deletRow(this)'>
									    	<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>
									    </button>
                                    </td>
                                    <td>
                                    	<p class='margin-bottom-0'>部件号</p>
                                    	<p class='margin-bottom-0'>部件名称</p>
                                    </td>
                                    <td>
                                    	<p class='margin-bottom-0'>批次号</p>
                                    	<p class='margin-bottom-0'>序列号</p>
                                    </td>
                                    <td style='vertical-align:middle;'>	
                                    	<p class='margin-bottom-0'>12 / 34</p>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    <input type='checkbox'/>
                                    </td>
                                    <td style='vertical-align:middle;' >
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
								    </tr>
								    <tr>
								    <td style='vertical-align:middle;'>
									    <button class="line6 line6-btn"  type="button" onclick='deletRow(this)'>
									    	<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>
									    </button>
                                    </td>
                                    <td>
                                    	<p class='margin-bottom-0'>部件号</p>
                                    	<p class='margin-bottom-0'>部件名称</p>
                                    </td>
                                    <td>
                                    	<p class='margin-bottom-0'>批次号</p>
                                    	<p class='margin-bottom-0'>序列号</p>
                                    </td>
                                    <td style='vertical-align:middle;'>	
                                    	<p class='margin-bottom-0'>12 / 34</p>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    <input type='checkbox'/>
                                    </td>
                                    <td style='vertical-align:middle;' >
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
								    </tr>
								    <tr>
								    <td style='vertical-align:middle;'>
									    <button class="line6 line6-btn"  type="button" onclick='deletRow(this)'>
									    	<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>
									    </button>
                                    </td>
                                    <td>
                                    	<p class='margin-bottom-0'>部件号</p>
                                    	<p class='margin-bottom-0'>部件名称</p>
                                    </td>
                                    <td>
                                    	<p class='margin-bottom-0'>批次号</p>
                                    	<p class='margin-bottom-0'>序列号</p>
                                    </td>
                                    <td style='vertical-align:middle;'>	
                                    	<p class='margin-bottom-0'>12 / 34</p>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    <input type='checkbox'/>
                                    </td>
                                    <td style='vertical-align:middle;' >
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
								    </tr>
								    <tr>
								    <td style='vertical-align:middle;'>
									    <button class="line6 line6-btn"  type="button" onclick='deletRow(this)'>
									    	<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>
									    </button>
                                    </td>
                                    <td>
                                    	<p class='margin-bottom-0'>部件号</p>
                                    	<p class='margin-bottom-0'>部件名称</p>
                                    </td>
                                    <td>
                                    	<p class='margin-bottom-0'>批次号</p>
                                    	<p class='margin-bottom-0'>序列号</p>
                                    </td>
                                    <td style='vertical-align:middle;'>	
                                    	<p class='margin-bottom-0'>12 / 34</p>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    <input type='checkbox'/>
                                    </td>
                                    <td style='vertical-align:middle;' >
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
								    </tr>
								    <tr>
								    <td style='vertical-align:middle;'>
									    <button class="line6 line6-btn" type="button" onclick='deletRow(this)'>
									    	<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>
									    </button>
                                    </td>
                                    <td>
                                    	<p class='margin-bottom-0'>部件号</p>
                                    	<p class='margin-bottom-0'>部件名称</p>
                                    </td>
                                    <td>
                                    	<p class='margin-bottom-0'>批次号</p>
                                    	<p class='margin-bottom-0'>序列号</p>
                                    </td>
                                    <td style='vertical-align:middle;'>	
                                    	<p class='margin-bottom-0'>12 / 34</p>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    <input type='checkbox'/>
                                    </td>
                                    <td style='vertical-align:middle;' >
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
                                    <td style='vertical-align:middle;'>
                                    	<input type='text' class='form-control'/>
                                    </td>
								    </tr>
								    </tbody>
								</table>
								</div>
								<div class='clearfix'></div>
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
	                    	<button id="save_btn" type="button" onclick="transfer_open.save();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">确认</div>
								<div class="font-size-9">Confirm</div>
							</button>
	                    </span>
	               	</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--  弹出框结束-->