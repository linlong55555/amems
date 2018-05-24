<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/introduce_process_info.js"></script><!--当前界面js  -->
			<div class="panel panel-default" id="introduce_process_info_class_lcxx">
				<!--标题 STATR -->
					 <div class="panel-heading bg-panel-heading">
					 	<div class="font-size-12 ">流程信息</div>
						<div class="font-size-9">Process Info</div>
				     </div>
				<!--标题ENG  -->
				<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	  	 	
					<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
					
					<div id="introduce_process_info_class_bianxied">
						
					   <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
								
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								      <div class="col-lg-3 padding-left-0 padding-right-0 pull-left">
										<div class="pull-left padding-right-10" style="margin-right:8px">
											<div class="font-size-12 margin-topnew-2">编写</div>
											<div class="font-size-9 ">Prepared by</div>
										</div>
										<span class="pull-left padding-right-10 margin-top-8 "  id="prepared"></span>
									  </div>
									  
									<div class="col-lg-3 padding-left-0 padding-right-0 pull-left">
										<div class="pull-left" style="margin-right:0px">
											<div class="font-size-12 line-height-18" >时间 <label style=" font-weight:normal;margin-right:8px" > </label>
											</div>
											<div class="font-size-9 line-height-12">Date</div>
										</div>
										<span class="pull-left padding-right-10 margin-top-8 " id="preparedDate"></span>
									</div>
								</div>
								
					 </div>
						
						</div>
					<div id="introduce_process_info_class_shenhed">
						
						 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">审核意见</div>
								<div class="font-size-9 ">Opinion</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								<textarea id="introduce_process_info_class_shenhe" maxlength="160" style="height:55px" class="form-control padding-left-3 padding-right-3" ></textarea>
							</div>
						</div>
						
					   <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" id="introduce_process_info_class_shenhed_xx">
								
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								<div class="col-lg-3 padding-left-0 padding-right-0 pull-left">
										<div class="pull-left padding-right-10" style="margin-right:8px">
											<div class="font-size-12 margin-topnew-2">审核</div>
											<div class="font-size-9 ">Checked by</div>
										</div>
										<span class="pull-left padding-right-10 margin-top-8 "  id="checkedby"></span>
										</div>
										<div class="col-lg-3 padding-left-0 padding-right-0 pull-left">
										<div class="pull-left" style="margin-right:0px">
											<div class="font-size-12 line-height-18" >时间 <label style="font-weight:normal;margin-right:8px"> <fmt:formatDate value='${annunciate.shsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
											<div class="font-size-9 line-height-12">Date</div>
										</div>
										<span class="pull-left padding-right-10 margin-top-8 "  id="checkedDate"></span>
										</div>
								     <div class="col-lg-3 padding-left-0 padding-right-0 pull-left">
										<div class="pull-left">
											<div class="font-size-12 line-height-18">结论<label style=" font-weight:normal;margin-right:8px"> <fmt:formatDate value='${annunciate.shsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
											<div class="font-size-9 line-height-12">Result</div>
										</div>
										<span class="pull-left padding-right-10 margin-top-8 " id="checkedResult"></span>
							      	</div>
								</div>
							 </div>
						</div>
						<div class='clearfix'></div>
						
						<div id="introduce_process_info_class_shenpid">
						
						<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">批准意见</div>
										<div class="font-size-9 ">Opinion</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
									<textarea id="introduce_process_info_class_shenpi" maxlength="160" style="height:55px" class="form-control padding-left-3 padding-right-3" ></textarea>
								</div>
						</div>
							   <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" id="introduce_process_info_class_shenpid_xx">
								
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								     <div class="col-lg-3 padding-left-0 padding-right-0 pull-left">
										<div class="pull-left padding-right-10" style="margin-right:8px">
											<div class="font-size-12 margin-topnew-2">批准</div>
											<div class="font-size-9 ">Approved by</div>
										</div>
										<span class="pull-left padding-right-10 margin-top-8 "  id="approvedBy"></span>
										</div>
										
									 <div class="col-lg-3 padding-left-0 padding-right-0 pull-left">	
										<div class="pull-left" style="margin-right:0px">
											<div class="font-size-12 line-height-18" >时间 <label style="font-weight:normal;margin-right:8px"> <fmt:formatDate value='${annunciate.shsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
											<div class="font-size-9 line-height-12">Date</div>
										</div>
										<span class="pull-left padding-right-10 margin-top-8 "  id="approvedDate"></span>
										</div>
								 <div class="col-lg-3 padding-left-0 padding-right-0 pull-left">
										<div class="pull-left">
											<div class="font-size-12 line-height-18">结论<label style=" font-weight:normal;margin-right:8px"> <fmt:formatDate value='${annunciate.shsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
											<div class="font-size-9 line-height-12">Result</div>
										</div>
										<span class="pull-left padding-right-10 margin-top-8 "  id="approvedResult"></span>
							     	</div>
								
								</div>
								
							 </div>
		
						</div>
						
					</div>
				</div>
	</div>
