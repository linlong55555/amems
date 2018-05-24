<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/equivalent_substitution_view.js"></script>
<div class="modal fade in modal-new" id="open_win_equivalent_substitution" tabindex="-1" role="dialog"  aria-labelledby="open_win_access" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width: 60%;" >
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
           			<div class='pull-left'>
               			<div class="font-size-12 ">等效替代查看</div>
						<div class="font-size-9">Equivalent Substitution View</div>
				 	</div>
					<div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
          	</div>
           	<div class="modal-body" style='padding-top:0px;'>
             	<div class="input-group-border" style='margin-top:8px;padding-top:5px;'>
                   	
                   	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">件号</div>
							<div class="font-size-9">P/N</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
							<input class="form-control" id="subjh" name="subjh" type="text" readonly="readonly"/>
						</div>
					</div>
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">替代件号</div>
							<div class="font-size-9">T/B No.</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
							<input class="form-control" id="sutdjh" name="sutdjh" type="text" readonly="readonly"/>
						</div>
					</div>
					<div class="clearfix"></div>
	            	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">描述</div>
							<div class="font-size-9">Desc</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
							<textarea class="form-control" id="sums" name="sums" maxlength="300" disabled="disabled" ></textarea>
						</div>
					</div>
	            	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">替代描述</div>
							<div class="font-size-9">Replace Desc</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
							<textarea class="form-control" id="sutdjms" name="sutdjms" maxlength="300" disabled="disabled" ></textarea>
						</div>
					</div>
	            	
		         	<div class="clearfix"></div>
		         	
		         	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">机型适用性</div>
							<div class="font-size-9">Start Date</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
							<input class="form-control" id="sujxbs" name="sujxbs" type="text" readonly="readonly"/>
						</div>
					</div>
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">工卡适用性</div>
							<div class="font-size-9">Start Date</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
							<input class="form-control" id="sugkbs" name="sugkbs" type="text" readonly="readonly"/>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">是否可逆</div>
							<div class="font-size-9">Replace</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
							<label style='margin-top:6px;font-weight:normal;' >
								<input class="pgjl" type='radio' name="knxbs" value='2' style='vertical-align:text-bottom' disabled="disabled"/>&nbsp;是
							</label>
							<label style='margin-top:6px;font-weight:normal;' >
								<input class="pgjl" type='radio' name="knxbs" value='1' style='vertical-align:text-bottom' disabled="disabled" />&nbsp;否
							</label>
						</div>
					</div>
					
					<div class="clearfix"></div>
		         		
			
				</div>
				
				<div id="attachments_list_view" style="display:none">
					
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					
					</div>
				
               <div class="clearfix"></div>              
          </div>
          <div class="clearfix"></div>  
          <div class="modal-footer">
          		<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
						</span>
	                   	<span class="input-group-addon modalfooterbtn">
	                    	<button onclick="open_win_equivalent_substitution.close();" type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
					    	</button>
	                   	</span>
              		</div><!-- /input-group -->
				</div>
			</div>
		</div>
	</div>
</div>
<!--  弹出框结束-->