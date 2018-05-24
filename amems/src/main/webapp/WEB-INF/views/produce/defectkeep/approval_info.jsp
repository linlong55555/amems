<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<div class="panel panel-default" id="introduce_process_info_class_lcxx">
	<div class="panel-heading bg-panel-heading">
	 	<div class="font-size-12 ">保留依据</div>
		<div class="font-size-9">Process Info</div>
    </div>
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
							<span class="pull-left col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">
									时间
								</div>
								<div class="font-size-9 ">Date</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 margin-top-8">
								<span type="text"  id="preparedDate" name="preparedDate" onchange="FailureKeep_Open_Modal.onchangebllx()" class="noteditable  padding-left-3 padding-right-3  "  ></span>
							</div>
						</div>
	 		    		<div class="col-lg-3 padding-left-0 padding-right-0 pull-left">
							<div class="pull-left padding-right-10" style="margin-right:8px">
								<div class="font-size-12 margin-topnew-2">缺陷保留由授权工程师指定修复期限</div>
								<div class="font-size-9 ">Repair Limit</div>
							</div>
					  	</div>
						<div class="col-lg-3 padding-left-0 padding-right-0 pull-left">
							<input type="text"  id="approvedDate" name="approvedDate"  class=" form-control padding-left-3 padding-right-3 date-picker"  />
						</div>
					</div>
				 </div>
			</div>
			<div class='clearfix'></div>
			<div id="introduce_process_info_class_shenpid">
	 			<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
					<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">授权工程师意见</div>
						<div class="font-size-9 ">Opinion</div>
					</span>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
						<textarea id="introduce_process_info_class_shenpi" maxlength="160" style="height:55px" class="form-control padding-left-3 padding-right-3" ></textarea>
					</div>
				</div>
	 			<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
					<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">运行限制</div>
						<div class="font-size-9 ">Limit</div>
					</span>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
						<input id="introduce_process_info_class_yxxz" maxlength="160" class="form-control padding-left-3 padding-right-3" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="clearfix"></div>
<div id='hcgj'>
<%@ include file="/WEB-INF/views/common/project/equipment_list_edit.jsp"%><!-- 器材清单列表 -->
<div class="clearfix"></div>
<%@ include file="/WEB-INF/views/common/project/tools_list_edit.jsp"%><!-- 工具设备列表 -->
</div>
<div class="clearfix"></div>	
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/defectkeep/approval_info.js"></script><!--当前界面js  -->