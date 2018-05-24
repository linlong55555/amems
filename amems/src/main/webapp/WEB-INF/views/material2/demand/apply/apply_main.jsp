<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>需求提报</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	
	<script type="text/javascript">
		$(document).ready(function(){
			//回车事件控制
			$(this).keydown(function(event) {
				e = event ? event :(window.event ? window.event : null); 
				if(e.keyCode==13){
					var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
					if(formatUndefine(winId) != ""){
						$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
					}else{
						$('#aircraftinfoMainSearch').trigger('click'); //调用主列表页查询
					}
				}
			});
		});
		
		var jsonData = '${param.content}';
	</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="dprtId" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="userType" value="${user.userType}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
<div class="page-content">
	<div class="panel panel-primary">
		<div class="panel-heading  ">
			<div id="NavigationBar"></div>
		</div>
		<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0 padding-top-0" id='demand_apply_body'>
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
						<input id="" type="hidden">
						<div class="input-group col-xs-12">
							<input id="bh" class="form-control readonly-style" ondblclick="chooseDemand()" readonly="readonly" type="text">
	                    	<span id="" class="input-group-addon btn btn-default" onclick="chooseDemand()">
	                    		<i class="icon-search"></i>
	                    	</span>
	                	</div>
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
					<div class='pull-left'>
						<label style="margin-top:12px;font-weight:normal;padding-left:5px;color:red;display: none;" id="jhyj_label">
						驳回原因：
						<span id="jhyj"></span>
						</label>
					</div>
				    <div class='pull-right'>
				    <label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
							<input id="jjcd" style="vertical-align:text-bottom;" type="checkbox">&nbsp;紧急&nbsp;&nbsp;
					</label>
					<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
							<input id="isYxfx" style="vertical-align:text-bottom;" type="checkbox">&nbsp;影响放行&nbsp;&nbsp;
					</label>
					<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
							<input id="isFjhtc" style="vertical-align:text-bottom;" type="checkbox">&nbsp;非计划停场&nbsp;&nbsp;
					</label>
				    </div>
				</div>
				<div class='clearfix'></div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>需求类别</div>
						<div class="font-size-9">Demand Type</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<select class="form-control" id="xqlb" name="xqlb"></select>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">计划使用日期</div>
						<div class="font-size-9">Date</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="jhsyrq" type="text" name='date-picker' data-date-format="yyyy-mm-dd"/>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">上级件号</div>
						<div class="font-size-9">Superior P/N</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input id="sjbjid" name="sjbjid" type="hidden">
						<div class="input-group col-xs-12">
							<input id="sjbjh" name="sjbjh" maxlength="50" class="form-control" ondblclick="chooseParentMaterial()" type="text">
	                    	<span class="input-group-addon btn btn-default" style="border-left: 0px;" onclick="chooseParentMaterial()">
	                    		<i class="icon-search"></i>
	                    	</span>
	                	</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">上级件名称</div>
						<div class="font-size-9">Superior Name</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="sjbjmc" maxlength="200" type="text" />
					</div>
				</div>
				<div class='clearfix'></div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>A/C注册号</div>
						<div class="font-size-9">A/C REG</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<select class="form-control" id="fjzch" name="fjzch" onchange="loadAcRegData()"></select>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">A/C S/N</div>
						<div class="font-size-9">A/C S/N</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="xlh" type="text" readonly="readonly"/>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">A/C FH</div>
						<div class="font-size-9">A/C FH</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="fxsj" name="fxsj" maxlength="12" type="text" />
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">A/C FC</div>
						<div class="font-size-9">A/C FC</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input class="form-control" id="fxxh" name="fxxh" maxlength="12" type="text" />
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
							<input id="lybh" class="form-control readonly-style" readonly="readonly" type="text">
	                    	<span class="input-group-addon btn btn-default checkPermission" onclick="chooseSource135()"
	                    		permissioncode="material:demand:apply:01" style="border-left: 0px;" title="135">
	                    		<i class="icon-search"></i>
	                    	</span>
	                    	<span class="input-group-addon btn btn-default checkPermission" onclick="chooseSource145()" 
	                    		permissioncode="material:demand:apply:02" style="border-left: 0px;" title="145">
	                    		<i class="icon-search"></i>
	                    	</span>
	                	</div>
					</div>
				</div>
				
				<div class='clearfix'></div>
				<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">需求原因/故障描述</div>
						<div class="font-size-9">Reason</div>
					</span>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
						<textarea style="height: 75px;" class="form-control" id="xqyy" maxlength="1000" ></textarea>
					</div>
				</div>
				<div class='clearfix'></div>
				<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">购买建议/其他说明</div>
						<div class="font-size-9">Desc</div>
					</span>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
						<textarea style="height: 75px;" class="form-control" id="gmjy" maxlength="1000" ></textarea>
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
								<th  class='colwidth-7'>
									<button class="line6 line6-btn" onclick="addMaterialInfo()" type="button">
										<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
								    </button>
								</th>
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18" >物料类别</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class='colwidth-9' onclick="" name="">
									<div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-18">P/N</div>
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
									<div class="font-size-9 line-height-18">S/N</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">需求数量</div>
									<div class="font-size-9 line-height-18">Demand Quantity</div>
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
									<div class="font-size-9 line-height-18">Normal P/N</div>
								</th>
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18">替换件</div>
									<div class="font-size-9 line-height-18">Replace P/N</div>
								</th>
							</tr> 
						</thead>
						<tbody id="material_info_list">
							<tr class="no-data"><td class="text-center" colspan="12" title="暂无数据 No data.">暂无数据 No data.</td></tr>
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
						<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>批准人</div>
						<div class="font-size-9">Approver</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
						<input type="hidden" id="pzrid" name="pzrid"/>
						<input type="hidden" id="pzbmid" />
						<div class="input-group" style="width: 100%;">
						    <input id="pzr" ondblclick="chooseApprover()" class="form-control readonly-style" type="text" readonly="readonly">
							<span class="input-group-btn">
								<button type="button" onclick="chooseApprover()" class="btn btn-default" data-toggle="modal">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
					   </div>
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
		<div class="panel-footer" >
			<div class="col-xs-12 padding-left-0 padding-right-0" >
				<div class="input-group">
					<span class="input-group-addon modalfootertip" >
			        	<i style="display: none;" class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
					</span>
			        <span class="input-group-addon modalfooterbtn">
			            <a href='#' onclick="toMyDemand()">我的需求单</a>
				        <button type="button" id="save_btn" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="saveData()">
							<div class="font-size-12">保存</div>
							<div class="font-size-9">Save</div>
						</button>
				        <button type="button" id="submit_btn" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="submitData()">
							<div class="font-size-12">提交</div>
							<div class="font-size-9">Submit</div>
						</button>
			        </span>
		        </div>
			</div>
			<div class='clearfix'></div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/material2/demand/apply/apply_add.jsp"%>
<%@ include file="/WEB-INF/views/material2/demand/apply/apply_list.jsp"%>
<%@ include file="/WEB-INF/views/material2/demand/apply/apply_source.jsp"%>
<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%>
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/demand/apply/apply_main.js"></script>
</body>
</html>