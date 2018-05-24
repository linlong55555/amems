<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看需求</title>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-content">
<input type="hidden" id="dprtId" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="userType" value="${user.userType}" />
	<div class="panel panel-primary" id="materialList_find_modal">
		<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0 padding-left-8 padding-right-8  padding-top-0">	 
			<input type="hidden" id="id" value="${param.id}"/>
			<div class="col-lg-12 col-sm-12 col-xs-12 col-md-12 padding-left-0 padding-right-0">
			<form id="demand_form">
			<div style='padding-top:10px;padding-left:8px;padding-right:8px;' id=''>
 				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">需求编号</div>
						<div class="font-size-9">Demand No.</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="bh" class="form-control" readonly="readonly" type="text">
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">状态</div>
						<div class="font-size-9">Status</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="ztms" type="text" readonly/>
						<input id="zt" type="hidden"/>
					</div>
				</div>
				<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:36px;'>
				    <div class='pull-right'>
				    <label class="padding-left-5" style="margin-top:6px;font-weight:normal;" >
							<input id="jjcd" style="vertical-align:text-bottom;" type="checkbox" disabled="disabled">&nbsp;紧急&nbsp;&nbsp;
					</label>
					<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
							<input id="isYxfx" style="vertical-align:text-bottom;" type="checkbox" disabled="disabled">&nbsp;影响放行&nbsp;&nbsp;
					</label>
					<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
							<input id="isFjhtc" style="vertical-align:text-bottom;" type="checkbox" disabled="disabled">&nbsp;非计划停场&nbsp;&nbsp;
					</label>
				    </div>
				</div>
				<div class='clearfix'></div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">需求类别</div>
						<div class="font-size-9">Demand Type</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input type="text" class="form-control" id="xqlb" name="xqlb" readonly>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">计划使用日期</div>
						<div class="font-size-9">Date</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="jhsyrq" type="text"  readonly/>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">上级件号</div>
						<div class="font-size-9">Superior P/N</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input id="sjbjh" name="sjbjh" maxlength="50" class="form-control" type="text" readonly="readonly">
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">上级件名称</div>
						<div class="font-size-9">Name</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="sjbjmc" maxlength="200" type="text" readonly/>
					</div>
				</div>
				<div class='clearfix'></div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">A/C注册号</div>
						<div class="font-size-9">A/C Register</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="fjzch" name="fjzch"  readonly>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">A/C S/N</div>
						<div class="font-size-9">A/C S/N</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="xlh" type="text"  readonly="readonly"/>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">A/C FH</div>
						<div class="font-size-9">A/C FH</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="fxsj" name="fxsj" maxlength="12" type="text" readonly/>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">A/C FC</div>
						<div class="font-size-9">A/C FC</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="fxxh" name="fxxh" maxlength="12" type="text" readonly/>
					</div>
				</div>
				<div class='clearfix'></div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<input id="lylx" type="hidden" />
					<input id="lyid" type="hidden" />
					<input id="bs145" type="hidden" />
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">相关单据</div>
						<div class="font-size-9">Relevant Documents</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						
						<div class="input-group col-xs-12">
							<input id="lybh" class="form-control" readonly="readonly" type="text">
	                	</div>
					</div>
				</div>
				<div class='clearfix'></div>
				<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">需求原因/故障描述</div>
						<div class="font-size-9">Demand Cause/Fault Description</div>
					</span>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
						<textarea style="height: 75px;" class="form-control" id="xqyy" maxlength="1000" readonly></textarea>
					</div>
				</div>
				<div class='clearfix'></div>
				<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">购买建议/其他说明</div>
						<div class="font-size-9">Purchasing Advice/Other Instructions</div>
					</span>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
						<textarea style="height: 75px;" class="form-control" id="gmjy" maxlength="1000" readonly></textarea>
					</div>
				</div>
				<div class='clearfix'></div>
				<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">物料信息</div>
						<div class="font-size-9">Material info</div>
					</span>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
						<div id="" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0  table-set" style="overflow-x: auto;">
					    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
						<thead>                              
							<tr>
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18" >物料类别</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class='colwidth-9' onclick="" name="">
									<div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-18">PN</div>
								</th>
								<th class='colwidth-20' onclick="" name="">
										<div class="font-size-12 line-height-18">名称/描述</div>
										<div class="font-size-9 line-height-18">Name/Desc</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">型号</div>
									<div class="font-size-9 line-height-18">Model</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">规格</div>
									<div class="font-size-9 line-height-18">Specifications</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">SN</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">需求数量</div>
									<div class="font-size-9 line-height-18">Demand quantity</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">件号来源</div>
									<div class="font-size-9 line-height-18">Original</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">标准件号</div>
									<div class="font-size-9 line-height-18">Standard PN</div>
								</th>
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18">替换件</div>
									<div class="font-size-9 line-height-18">Replace PN</div>
								</th>
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18">保障状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18">保障备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</th>
							</tr> 
						</thead>
						<tbody id="material_info_list">
							<tr class="no-data"><td class="text-center" colspan="13" title="暂无数据 No data.">暂无数据 No data.</td></tr>
						</tbody>
					</table>
					</div>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
				<div class='clearfix'></div>
				<div style='padding-top:5px;padding-left:8px;padding-right:8px;' id=''>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">提报人</div>
						<div class="font-size-9">Submitter</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="sqr" type="text" readonly/>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">提报时间</div>
						<div class="font-size-9">Time</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="sqsj" type="text" readonly/>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">批准人</div>
						<div class="font-size-9">Approver</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
					    <input id="pzr" ondblclick="" class="form-control " type="text" readonly="readonly">
								
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">批准时间</div>
						<div class="font-size-9">Time</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="pzsj" type="text" readonly/>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
			</form>	
			</div>
			</div>
		<div class="clearfix"></div>
	</div>	
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/demand/apply/apply_find.js"></script>
<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
</body>
</html>