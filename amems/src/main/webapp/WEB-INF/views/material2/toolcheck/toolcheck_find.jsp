<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看工具/设备校验</title>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-content">
<input type="hidden" id="viewid" value="${viewid}">
<input type="hidden" id="id" value="${viewid}">
<input type="hidden" id="dprtcode" >
	<div class="panel panel-primary" id="toolcheck_find_modal">
		<div class="panel-heading" id="NavigationBar"></div>
					<div class="panel-body padding-bottom-0 padding-left-8 padding-right-8  padding-top-0">	 
				<div class="input-group-border margin-top-8 padding-left-0">
					<div  class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span class="required" style="color: red">*</span>工具编号</div>
							<div class="font-size-9 ">Tool No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group" style="width: 100%">
								<input type="hidden" id="bjid">
								<input type="text"   id="bjxlh"  name="bjxlh" class="form-control noteditable readonly-style colse" readonly="readonly" ondblclick='toolcheck_open_modal.selecttoolequipment();'>
								<span class="required input-group-btn"  >
									<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="toolcheck_open_modal.selecttoolequipment()">
										<i class="icon-search cursor-pointer" ></i>
									</button>
								</span>
		                	</div>
						</div>
					</div>
           			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">部件号</div>
							<div class="font-size-9 ">P/N</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
							<input type="text"   id="bjh" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">部件名称</div>
							<div class="font-size-9 ">Name</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
							<input type="text"   id="bjmc" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group div-hide">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">维护人</div>
							<div class="font-size-9 ">Maintainer</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
							<input type="text"  id="whr" class="form-control padding-left-3 padding-right-3 "  disabled="disabled"/>
						</div>
					</div>
					<div class='clearfix'></div>
	      			<div id="lybhdiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">位置</div>
							<div class="font-size-9 ">Position</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
							<input type="text"  id="wz" class="form-control padding-left-3 padding-right-3 "  disabled="disabled"/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">入库日期</div>
							<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
							<input type="text"  id="rksj" class="form-control padding-left-3 padding-right-3 "  disabled="disabled"/>
						</div>
					</div>
				 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">批次号</div>
							<div class="font-size-9 ">B/N</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
							<input type="text"  id="pch" class="form-control padding-left-3 padding-right-3 "  disabled="disabled"/>
						</div>
					</div>
				 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">数量</div>
							<div class="font-size-9 ">QTY</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
							<input type="text"  id="kcsl" class="form-control padding-left-3 padding-right-3 "  disabled="disabled"/>
						</div>
					</div>
					<div class='clearfix'></div>
				</div>
				<!--**********************  -->
				<div id="toolcheck_open_modal_data_list">
				<div name="zbjs" class="input-group-border padding-left-0" style='margin-top:15px;border:1px dashed #d5d5d5;'>
				   <input type="hidden" value="1" name="bjbs"/>
				   <input type="hidden" name="zid" id="zid" />
				   <input type="hidden"  id="bs" value="2"/>
				    <div style='width:80px;background:white;height:20px;margin-top:-20px;margin-left:20px;text-align:center'>
				     <button class="line6 line6-btn button" onclick="toolcheck_open_modal.remove(this)"  type="button"><i class="icon-minus cursor-pointer color-blue cursor-pointer"></i></button>
		   			</div>
           			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-8 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span class="required" style="color: red">*</span>编号</div>
							<div class="font-size-9 ">No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
							<input type="text"     id="zbjxlh" class="form-control"  maxlength="50" disabled="disabled"/>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-8 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">中文名称</div>
							<div class="font-size-9 ">Chinese name</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
							<input type="text"   name="zwms" id="zzwms"  class="form-control"  maxlength="100" disabled="disabled"/>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-8 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">英文名称</div>
							<div class="font-size-9 ">English name</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
							<input type="text"   name="ywms" id="zywms" class="form-control"   maxlength="100" disabled="disabled"/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-8 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">型号</div>
							<div class="font-size-9 ">Model</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
							<input type="text"   name="xh" id="zxh" class="form-control "   disabled="disabled" maxlength="50"/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">规格</div>
							<div class="font-size-9 ">Specifications</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
							<input type="text"   name="gg" id="zgg" class="form-control " disabled="disabled"  maxlength="50"/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span class="required" style="color: red">*</span>周期</div>
							<div class="font-size-9 ">Cycle</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 padding-right-0">
							<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-0 padding-right-0">
								<input type="text" class="form-control" id="zjyZq" name="jyZq" placeholder='' onkeyup='clearNoNum(this)' maxlength="3" disabled="disabled" />
							</div>
							<div class="col-lg-3 col-sm-3 col-xs-3 padding-left-2 padding-right-0">
								<select id='zjyZqdw' name="jyZqdw" class="form-control" onchange="toolcheck_open_modal.loadNextDate()" disabled="disabled">
									<option value="11" >天</option>
									<option value="12" >月</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span class="required" style="color: red">*</span>校验日期</div>
							<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
							<input type="text"  id="zjyScrq"  name="jyScrq" class="form-control date-picker"  disabled="disabled" onchange="toolcheck_open_modal.loadNextDate()"/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span class="required" style="color: red">*</span>下次校验日期</div>
							<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
							<input type="text"   id="zjyXcrq" name="jyXcrq" class="form-control date-picker" disabled="disabled" />
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">计量方式</div>
							<div class="font-size-9 ">Mode</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
							<select class="form-control "  name="jlfs" id="jlfsSelect" disabled="disabled">
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">计量等级</div>
							<div class="font-size-9 ">Grade</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
							<input type="text"   name="jldj" id="zjldj" class="form-control"  disabled="disabled"/>
						</div>
					</div>
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">附件</div>
							<div class="font-size-9">Attachment</div>
						</span>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<button type="button" onClick="toolcheck_open_modal.filseDown()" class="btn btn-xs btn-default padding-top-1 padding-bottom-1 "style="height:30px;line-height:30px;" title="上传">
								<i class="fa fa-upload" style="font-size:16px;"></i>
							</button>
						</div>
					</div>
					<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">备注</div>
							<div class="font-size-9">Remark</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
							<textarea style="height: 75px;" class="form-control" id="zbz" name="bz" maxlength="300" disabled="disabled"></textarea>
						</div>
					</div>
					<div class='clearfix'></div>
				</div>
				</div>
				<div id="toolcheck_open_modal_list">
		
				</div>
			
				<div class='clearfix'></div>
				<div style='padding-bottom:8px;' class='button'>
				     <button class="line6 line6-btn" onclick="toolcheck_open_modal.addRow()"  type="button">
					<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
		   			</button>
		   		</div>
				
			</div>
		<div class="clearfix"></div>
	</div>	
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/toolcheck/toolcheck_open.js"></script><!--计量工具的通用js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/toolcheck/toolcheck_find.js"></script><!--计量工具的查看js  -->
<%@ include file="/WEB-INF/views/material2/toolcheck/toolcheck_filesDown.jsp" %> <!--附件列表界面  -->
</body>
</html>