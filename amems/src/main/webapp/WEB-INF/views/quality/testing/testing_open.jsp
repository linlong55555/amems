<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="testing_open_alert" tabindex="-1" role="dialog"  aria-labelledby="testing_open_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:90%'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">质检</div>
							<div class="font-size-12" id="modalHeadENG">Quality testing</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" id="open_win_installationlist_replace">
	            <div class="col-xs-12 margin-top-8 ">
		            <div class="input-group-border"> 
		            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">检验单号</div>
							<div class="font-size-9 ">Check No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="testing_open_alert_jydh" readonly  />
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">物料类别</div>
							<div class="font-size-9 ">Type</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="testing_open_alert_hclx"  readonly/>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">部件号</div>
							<div class="font-size-9 ">PN</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="testing_open_alert_bjh" readonly/>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">部件名称</div>
							<div class="font-size-9 ">Name</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="testing_open_alert_bjmc" readonly/>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">管理级别</div>
							<div class="font-size-9 ">Level</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="testing_open_alert_gljb"  readonly/>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">序列号</div>
							<div class="font-size-9 ">SN</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="testing_open_alert_sn" readonly/>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">批次号</div>
							<div class="font-size-9 ">Batch No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="testing_open_alert_pch" readonly/>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">数量</div>
							<div class="font-size-9 ">Qty</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="testing_open_alert_kysldw" readonly/>
							<input type="hidden" id="testing_open_alert_kysl" />
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">收货单号</div>
							<div class="font-size-9 ">Receipt No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="hidden" id="testing_open_alert_id" />
							<input type="hidden" id="testing_open_alert_gljbzt" />
							<input type="hidden" id="testing_open_alert_djzt" />
							<input type="hidden" id="testing_open_alert_shdid" />
							<input type="hidden" id="testing_open_alert_dprtcode" />
							<a href='#' style="line-height:34px;" id="testing_open_alert_shdh"  onclick="testing_open_alert.onclickshd()"></a>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">发货单位</div>
							<div class="font-size-9 ">Unit</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="testing_open_alert_lydw" readonly/>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">收货日期</div>
							<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="testing_open_alert_shrq" readonly/>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">位置</div>
							<div class="font-size-9 ">Position</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="testing_open_alert_wz" readonly/>
						</div> 
                 	</div>
                 	
                    <div class='clearfix'></div>
	            </div> 
	             <div class='clearfix'></div>   
	              <form id="form1">
				    <div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading">
							<div class="font-size-12 ">检验信息</div>
							<div class="font-size-9">Inspection Info</div>
						</div>
						<div class="panel-body padding-left-0 padding-right-0" >
						 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span class="color-red">*</span>部件来源</div>
								<div class="font-size-9 ">Source</div>
							</span>
							<div id="hclyDiv" class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="testing_open_alert_hcly" name="hcly" class="form-control"  >
							     </select>
							</div> 
	                 	</div>
	                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">GRN</div>
								<div class="font-size-9 ">GRN</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="testing_open_alert_grn" maxlength="50"/>
							</div> 
	                 	</div>
	                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">生产日期</div>
								<div class="font-size-9 ">Date</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control date-picker" id="testing_open_alert_scrq"/>
							</div> 
	                 	</div>
	                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">货架寿命</div>
								<div class="font-size-9 ">Life</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control date-picker" id="testing_open_alert_hjsm"/>
							</div> 
	                 	</div>
                 		<div class='clearfix'></div>
	                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">TSN</div>
								<div class="font-size-9 ">TSN</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control time-masked" id="testing_open_alert_tsn" name="tsn" maxlength="50"/>
							</div> 
	                 	</div>
	                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">CSN</div>
								<div class="font-size-9 ">CSN</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="testing_open_alert_csn" maxlength="50" name="csn"/>
							</div> 
	                 	</div>
	                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">TSO</div>
								<div class="font-size-9 ">TSO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control time-masked" id="testing_open_alert_tso" name="tso" maxlength="50"/>
							</div> 
	                 	</div>
	                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">CSO</div>
								<div class="font-size-9 ">CSO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="testing_open_alert_cso" maxlength="50" name="cso"/>
							</div> 
	                 	</div>
	                 		<div class='clearfix'></div>
	                 	<div class="col-lg-6 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">存放要求</div>
								<div class="font-size-9 ">Requirements</div>
							</span>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" style="height:55px;" id="testing_open_alert_cfyq" maxlength="150"></textarea>
							</div> 
	                 	</div>
	                 	<div class="col-lg-6 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">备注</div>
								<div class="font-size-9 ">Remark</div>
							</span>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" style="height:55px;" id="testing_open_alert_bz" maxlength="300"></textarea>
							</div> 
	                 	</div>
	                 		<div class='clearfix'></div>
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span class="color-red">*</span>检验日期</div>
								<div class="font-size-9 ">Date</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control date-picker" name="jyrq" id="testing_open_alert_jyrq"/>
							</div> 
	                 	</div>
	                 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span class="color-red">*</span>检验结果</div>
								<div class="font-size-9 ">Result</div>
							</span>
							<div id="jyjgDiv" class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control' id='testing_open_alert_jyjg' name="jyjg">
								<option value="1" >合格Qualified</option>
								<option value="2">不合格Unqualified</option>
								<option value="3">让步接收Compromise</option>
						    </select>
							</div> 
	                 	</div>
	                 	<div class="col-lg-6 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2"><span class="color-red">*</span>结果说明</div>
								<div class="font-size-9 ">Remark</div>
							</span>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" name="jgsm" id="testing_open_alert_jgsm" maxlength="300"/>
							</div> 
	                 	</div>
						<div class='clearfix'></div>
						</div>
					</div>
					</form>
					
					<%@ include file="/WEB-INF/views/quality/testing/maintenance_project_view.jsp"%><!-- 适用维修项目  Maintenance Item -->
					
					<!-- 证书信息 -->
			 		<div class="panel panel-default">
			        <div class="panel-heading">
					  <h3 class="panel-title">证书信息  Certificate Info</h3>
				    </div>
					<div class="panel-body">	
				      	<div  class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow: auto;" >
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
						
				<div id="attachments_list_edit1" >
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
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
	                    	<button  onclick="testing_open_alert.save()" type="button" onclick=";" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
	                    	<button  type="button" onclick="testing_open_alert.submit()" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
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
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/testing/testing_open.js"></script><!--当前js  -->
<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_certificate.jsp"%><!--证书弹出框  -->
<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script><!--生成uuid  -->
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
