<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx }/static/js/thjw/produce/workpackage/135/workpackage_end.js"></script>
<div class="modal modal-new" id="workpackage_end_modal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">     
 <div class="modal-dialog" style='width:70%;'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-14 " >工包指定结束</div>
							<div class="font-size-12" >Close Workpackage</div> 
						  </div>
					 	  <div class='pull-right' >
					  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				  		  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
                  
            <div class="modal-body" >
   		        <div class="col-xs-12 margin-top-0 padding-left-10 padding-right-8">
 					  <div class="input-group-border margin-top-8 padding-left-0">
						<form id="workpackage_end_form">	
						<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">飞机注册号</div>
								<div class="font-size-9">A/C Reg</div>
							</label>
							<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class='form-control ' id="workpackage_end_fjzch" name="workpackage_end_fjzch" disabled="disabled">								
							</div>
						</div>
						<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">MSN</div>
								<div class="font-size-9">MSN</div>
							</label>
							<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class='form-control ' id="workpackage_end_msn" name="workpackage_end_msn" disabled="disabled">								
							</div>
						</div>
						<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">机型</div>
								<div class="font-size-9">A/C Type</div>
							</label>
							<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class='form-control ' id="workpackage_end_jx" name="workpackage_end_jx" disabled="disabled">								
							</div>
						</div>
						<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工包编号</div>
								<div class="font-size-9">Packing No.</div>
							</label>
							<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class='form-control ' id="workpackage_end_gbbh" name="workpackage_end_gbbh" disabled="disabled">								
							</div>
						</div>
						<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">描述</div>
								<div class="font-size-9">Desc</div>
							</label>
							<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class='form-control ' id="workpackage_end_ms" name="workpackage_end_ms" disabled="disabled">								
							</div>
						</div>
						<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">下发单位</div>
								<div class="font-size-9">Issuing Unit</div>
							</label>
							<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input text id="workpackage_end_xfdw" name="workpackage_end_xfdw" class="form-control"  maxlength="100" disabled="disabled">
							</div>
						</div>
						<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">计划开始日期</div>
								<div class="font-size-9">Date</div>
							</label>
							<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="workpackage_end_jhksrq" name="workpackage_end_jhksrq" disabled="disabled" />
						    </div>
						</div>	
						<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">计划完成日期</div>
								<div class="font-size-9">Date</div>
							</label>
							<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="workpackage_end_jhwcrq" name="workpackage_end_jhwcrq" disabled="disabled" />
						    </div>
						</div>
						<div  class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2" >指定结束人</div>
								<div class="font-size-9" id="gbr_ename">Closer</div>
							</label>
							<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control"   id="workpackage_end_gbr" name="workpackage_end_gbr" disabled="disabled">
							</div>
						</div>
					
						<div  class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2" >指定结束时间</div>
								<div class="font-size-9" >Closing Time</div>
							</label>
							<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control "  id="workpackage_end_gbrq" name="workpackage_end_gbrq" disabled="disabled">
							</div>
						</div>
						<div  class="col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2" ><span style="color:red" id="workpackage_end_zdjsyy_remark">*</span>指定结束原因</div>
								<div class="font-size-9" >reason</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class='form-control' style="height:75px" id="workpackage_end_zdjsyy" name="workpackage_end_zdjsyy"  maxlength="150"></textarea>
							</div>
						</div>	          		    	          		    
	          		<div class="clearfix"></div>
	          		</form> 	          		   
	          	</div>
	           <div class="clearfix"></div>    
          	 </div>
           </div>
           <div class="clearfix"></div> 
   		  <div class="modal-footer ">
			<div class="col-xs-12 padding-left-8 padding-right-0" >
				<span class="pull-left modalfootertip" >
		               <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
				</span>
				<div class="pull-right margin-right-8">
				   <button  type="button" onclick="workpackage_end_modal.saveData();" id="workpackage_end_savebutton" class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">保存</div>
							<div class="font-size-9">Save</div>
					</button>
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
					</button>
				</div>
			</div>
		 </div>
       </div>
    </div>
</div>
