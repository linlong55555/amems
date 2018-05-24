<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx }/static/js/thjw/material2/scrapped/apply/apply_open.js"></script>
<div class="modal fade in modal-new" id="apply_open_modal" tabindex="-1" role="dialog"  aria-labelledby="apply_open_modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:90%'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">报废登记</div>
							<div class="font-size-12" id="modalHeadENG">Discarded registration</div>
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
		            <form id="apply_open_form">
		            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class="color-red"></span>报废单编号</div>
							<div class="font-size-9 ">Scrap No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" id="apply_open_bfdh" placeholder="不填写则自动生成" maxlength="50" name="apply_open_bfdh" class="form-control"/>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class="color-red">*</span>报废日期</div>
							<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" id="apply_open_bfrq" name="apply_open_bfrq" class="form-control date-picker"  data-date-format="yyyy-mm-dd"/>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">单据状态</div>
							<div class="font-size-9 ">Status</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="apply_open_zt" name="apply_open_zt" class="form-control" type="text" readonly >							
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">申请人</div>
							<div class="font-size-9 ">Applicant</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="hidden" name="apply_open_sqbmid" id="apply_open_sqbmid" class="form-control" readonly/>
							<input type="hidden" name="apply_open_sqrid" id="apply_open_sqrid" class="form-control" readonly/>
							<input type="text" name="apply_open_sqr" id="apply_open_sqr" class="form-control" readonly/>
						</div> 
                 	</div>
                 	<div class="clear"></div>
                 	 <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">说明</div>
							<div class="font-size-9 ">Desc</div>
						</span>
						<div class="ol-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea rows="" cols="" id="apply_open_sm" maxlength="300" name="apply_open_sm" style="height:55px;" class="form-control"></textarea>
						</div> 
                 	</div>
                 	</form>
                 	<div class='clearfix'></div>
	            </div> 
	            <div class="panel panel-primary" >
					<div class="panel-heading bg-panel-heading">
						   <div class="font-size-12 ">报废部件信息</div>
						  <div class="font-size-9">Scrapped Info</div>
					</div>
					<div class="panel-body" >
						<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group ">
							<span class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-8">
									<div class="font-size-12  margin-topnew-2"><span class="color-red">*</span>审核人</div>
									<div class="font-size-9 ">Auditor</div>
								</span>
								<div class="col-lg-6 col-md-9 col-sm-8 col-xs-9 padding-left-0 padding-right-8">
									<div class=" input-group col-xs-12" style="width:100%">
								    <input id="apply_open_shrid" class="form-control" type="hidden" >
								    <input id="apply_open_shr" class="form-control readonly-style" type="text" readonly="readonly">
									<span  class="input-group-addon btn btn-default" onclick="apply_open.openUser()">
				                    		<i class="icon-search"></i>
				                    </span>
									</div>
								</div> 
	                 	</div>
	                 	 <div class="col-xs-12 padding-left-0 padding-right-8 form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
								<div class="font-size-12  margin-topnew-2"><span class="color-red">*</span>部件列表</div>
								<div class="font-size-9 ">List</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0">
								<table id="apply_open_table" class="table table-thin table-bordered table-striped table-hover table-set ">
									<thead >
										<tr>
											<th style="width: 50px;font-weight:normal"  >
											   <button class="line6 line6-btn" onclick="apply_info.openStock()"  type="button">
												<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
											   </button>
										   </th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">物料类型</div>
												<div class="font-size-9 line-height-18">Type</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">部件</div>
												<div class="font-size-9 line-height-18">PN</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18">批次/序列号</div>
												<div class="font-size-9 line-height-18">BN/SN</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18">规格/型号</div>
												<div class="font-size-9 line-height-18">Spec/Model</div>
											</th>
											<th class="colwidth-9"  >
												<div class="font-size-12 line-height-18">位置</div>
												<div class="font-size-9 line-height-18">Position</div>
											</th>
											<th class="colwidth-7"  >
												<div class="font-size-12 line-height-18">报废数量</div>
												<div class="font-size-9 line-height-18">QTY</div>
											</th>
											<th class="colwidth-9"  >
												<div class="font-size-12 line-height-18">产权</div>
												<div class="font-size-9 line-height-18">Right</div>
											</th>
											<th class="colwidth-9"  >
												<div class="font-size-12 line-height-18">成本</div>
												<div class="font-size-9 line-height-18">Cost</div>
											</th>
											<th class="colwidth-15"  >
												<div class="font-size-12 line-height-18">库存信息</div>
												<div class="font-size-9 line-height-18">Inventory info</div>
											</th>									
										</tr>
									</thead>
									<tbody id="apply_open_detail">
									</tbody>
								</table>
							</div> 
	                 	</div>
                 	</div>
	                <div class='clearfix'></div>  
	               </div> 
	               <div id="apply_open_attachments_list_edit" >
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					</div>
					<%@ include file="/WEB-INF/views/quality/reviewreformmeasures/measures_record.jsp"%>
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
	                    	<button id="save_btn" type="button" onclick="apply_open.saveData(1)" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
	                    	<button id="save_btn" type="button" onclick="apply_open.saveData(2)" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
	                    	<button id="save_btn" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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