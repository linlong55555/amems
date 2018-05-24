<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="mgnt_add_alert_Modal" tabindex="-1" role="dialog"  aria-labelledby="mgnt_add_alert_Modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:90%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">新增合同</div>
							<div class="font-size-12" id="modalHeadENG">New contract</div>
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
		            <form id="mgnt_form">
		           <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span style="color: red">*</span>合同号</div>
							<div class="font-size-9 ">Contract No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="hth_e" name="hth_e" type="text" maxlength="50" placeholder='不填写则自动生成'/>
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span style="color: red">*</span>合同日期</div>
							<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type='text' class='form-control' id="htrq_e" name="htrq_e" data-date-format="yyyy-mm-dd"/>
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">合同类型</div>
							<div class="font-size-9 ">Type</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
							<input type='text' class='form-control' id="htlx_e" name="htlx_e" readonly />
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">编制人</div>
							<div class="font-size-9 ">Compactor</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type='text' class='form-control' id="bzrstr_e" name="bzrstr_e" readonly />
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span style="color: red">*</span>币种</div>
							<div class="font-size-9 ">Currency</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control' id="biz_e" name="biz_e" >
							</select>
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">交付方式</div>
							<div class="font-size-9 ">Delivery Mode</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control' id="jffs_e" name="jffs_e">
							</select>
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">相关方</div>
							<div class="font-size-9 ">Relevant Party</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group col-xs-12">
								<input type="hidden" class="form-control" id="xgfid_e" name="xgfid_e">
								<input type="hidden" class="form-control" id="xgflx_e" name="xgflx_e">
								<input type="text" class="form-control" id="xgfms_e" name="xgfms_e" maxlength="100">
		                    	<span id="xgfBtn" class="input-group-addon btn btn-default" onclick="mgnt_add_alert_Modal.openXgfWin()"><i class="icon-search"></i></span>
			                </div>
						</div>
				    </div>
				    
				    <!-- <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">产权</div>
							<div class="font-size-9 ">Right</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group col-xs-12">
								<input type="hidden" class="form-control" id="cqid_e" name="cqid_e">
								<input id="cq_e" class="form-control readonly-style" type="text" ondblclick="mgnt_add_alert_Modal.openCqWin()" readonly >
		                    	<span id="" class="input-group-addon btn btn-default" onclick="mgnt_add_alert_Modal.openCqWin()"><i class="icon-search"></i></span>
			                </div>
						</div>
				    </div> -->
				    
				    <div class="col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
						<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">备注</div>
							<div class="font-size-9">Note</div>
						</label>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea id="bz_e" name="bz_e" style="height: 55px;" class="form-control" maxlength="1300"></textarea>
						</div>
					</div>
				    </form>
				    	<div id="purchase_include_edit" >
			            	<%@ include file="/WEB-INF/views/material2/contract/mgnt/mgnt_table_edit/purchase_edit.jsp"%><!-- 加载采购信息 -->
			            </div>
			            <div id="repair_include_edit" >
			            	<%@ include file="/WEB-INF/views/material2/contract/mgnt/mgnt_table_edit/repair_edit.jsp"%><!-- 加载修理信息 -->
			            </div>
			            <div id="rent_include_edit" >
			            	<%@ include file="/WEB-INF/views/material2/contract/mgnt/mgnt_table_edit/rent_edit.jsp"%><!-- 加载租出信息 -->
			            </div>
				    
		            </div>
		            <div class='clearfix'></div>
		            <!-- 附件 -->
		            <div id="attachments_list_edit" >
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
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
							<button id="export_btn" type="button" class="operation-btn btn btn-primary mgnt-export-type padding-top-1 padding-bottom-1">
								<div class="font-size-12">导出</div>
								<div class="font-size-9">Export</div>
							</button>
							<button id="exportSingle_btn" type="button" onclick="javascript:mgnt_add_alert_Modal.exportWord(2);" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">导出</div>
								<div class="font-size-9">Export</div>
							</button>
							<button id="save_btn" type="button" onclick="javascript:mgnt_add_alert_Modal.setData('');" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="submit_btn" type="button" onclick="javascript:mgnt_add_alert_Modal.setData('submit');"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/contract/mgnt/mgnt_add.js"></script>
<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%><!-- 部件弹出框 -->
<%@ include file="/WEB-INF/views/common/produce/material_cq.jsp" %> <!-- 产权弹出框 -->
<%@ include file="/WEB-INF/views/open_win/firm_win_list.jsp" %> <!-- 供应商弹出框 -->
<!--  弹出框结束-->