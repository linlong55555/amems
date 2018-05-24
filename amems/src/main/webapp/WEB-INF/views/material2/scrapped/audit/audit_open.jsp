<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="apply_open_alert" tabindex="-1" role="dialog"  aria-labelledby="apply_open_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
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
		            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class="color-red"></span>报废单编号</div>
							<div class="font-size-9 ">Scrap No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control"/>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class="color-red"></span>报废日期</div>
							<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" name="date-picker" data-date-format="yyyy-mm-dd"/>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">单据状态</div>
							<div class="font-size-9 ">Status</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input id="" style="vertical-align:text-bottom;" type="radio" name='zt'>&nbsp;审核驳回&nbsp;&nbsp;
							</label>
							<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
									<input id="" style="vertical-align:text-bottom;" type="radio" name='zt'>&nbsp;审批意见&nbsp;&nbsp;
							</label>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">申请人</div>
							<div class="font-size-9 ">Applicant</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" readonly/>
						</div> 
                 	</div>
                 	
                 	 <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">说明</div>
							<div class="font-size-9 ">Desc</div>
						</span>
						<div class="ol-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea rows="" cols="" style="height:55px;" class="form-control"></textarea>
						</div> 
                 	</div>
                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class="color-red">*</span>报废信息</div>
							<div class="font-size-9 ">Discarded info</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" onclick="applyOpen()">
								<div class="font-size-12">选择</div>
								<div class="font-size-9">Choice</div>
							</button>
						</div> 
                 	</div>
                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group pull-right">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class="color-red">*</span>审核人</div>
							<div class="font-size-9 ">Auditor</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class=" input-group col-xs-12">
						    <input id="" class="form-control" type="text">
							<span id="" class="input-group-addon btn btn-default" onclick="">
		                    		<i class="icon-search"></i>
		                    </span>
							</div>
						</div> 
                 	</div>
                 	 <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<table id="OrderTable" class="table table-thin table-bordered table-striped table-hover table-set ">
								<thead >
									<tr>
										<th  class="colwidth-5" >
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">物料类型</div>
											<div class="font-size-9 line-height-18">Type</div>
										</th>
										<th class="colwidth-7">
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
										<th class="colwidth-9"  >
											<div class="font-size-12 line-height-18">报废数量</div>
											<div class="font-size-9 line-height-18">Qty</div>
										</th>
										<th class="colwidth-9"  >
											<div class="font-size-12 line-height-18">产权</div>
											<div class="font-size-9 line-height-18">Right</div>
										</th>
										<th class="colwidth-9"  >
											<div class="font-size-12 line-height-18">价值</div>
											<div class="font-size-9 line-height-18">Value</div>
										</th>
										<th class="colwidth-15"  >
											<div class="font-size-12 line-height-18">库存信息</div>
											<div class="font-size-9 line-height-18">Inventory info</div>
										</th>
										<th class="colwidth-15"  >
											<div class="font-size-12 line-height-18">销毁信息</div>
											<div class="font-size-9 line-height-18">Destory</div>
										</th>
									</tr>
								</thead>
								<tbody id="">
									<tr>
										<td style="vertical-align:middle;" class="text-center">
										<button type="button" class="line6 line6-btn" onclick="removeTr(this)">    
										<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>
										</button>
										</td>
										<td>
											<p class="margin-bottom-0">航材</p>
											<p class="margin-bottom-0">二级类型</p>
										</td>
										<td>
											<p class="margin-bottom-0">部件号</p>
											<p class="margin-bottom-0">部件名称</p>
										</td>
										<td>
											<p class="margin-bottom-0">批次号</p>
											<p class="margin-bottom-0">序列号</p>
										</td>
										<td>
											<p class="margin-bottom-0">规格</p>
											<p class="margin-bottom-0">型号</p>
										</td>
										<td style="vertical-align:middle;" class="text-center">仓库+库位</td>
										<td style="vertical-align:middle;" class="text-center">
										<div class="input-group col-xs-12">
											<input id="" class="form-control" ondblclick="" type="text">
											<span class="input-group-addon" style="border:0px;background:none;padding-left:5px;">
												EA
											</span>
										</div>
										</td>
										<td></td>
										<td style="vertical-align:middle;" class="text-center">2700 RMB</td>
										<td>
											<p class="margin-bottom-0">入库日期</p>
											<p class="margin-bottom-0">货架寿命/剩余天数</p>
										</td>
										<td>
											<p class="margin-bottom-0">销毁人</p>
											<p class="margin-bottom-0">销毁时间</p>
										</td>
									</tr>
								</tbody>
							</table>
						</div> 
                 	</div>
                 	<div class='clearfix'></div>
	            </div> 
	                <div class='clearfix'></div>   
	               <div id="attachments_list_edit" >
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					</div>
					<%@ include file="/WEB-INF/views/open_win/introduce_process_info.jsp" %> <!--流程信息 -->	
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
	                    	<button id="save_btn" type="button" onclick=";" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
	                    	<button id="save_btn" type="button" onclick=";" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
	                    </span>
	               	</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--  弹出框结束-->
<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_certificate.jsp"%>