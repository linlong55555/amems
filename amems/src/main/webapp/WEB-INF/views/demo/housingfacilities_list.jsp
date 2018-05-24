<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工具/设备监控设置</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">

	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			
				<div class="col-xs-2 col-md-3 padding-left-0">
					<a href="javascript:void(0);" class="btn btn-primary padding-top-1 padding-bottom-1 margin-right-10 pull-left" onclick="openAdd()" >
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
				</div>
				
				<div class="clearfix"></div>

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x: auto;">
					<table class="table table-thin table-bordered table-set" style="min-width: 1000px;">
						<thead>
							<tr>
								<th class="colwidth-7" >	
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-3">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 line-height-18">设备名称</div>
									<div class="font-size-9 line-height-18">Name</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 line-height-18">设备编号</div>
									<div class="font-size-9 line-height-18">S/N</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 line-height-18">适用机型</div>
									<div class="font-size-9 line-height-18">Applicability</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 line-height-18">依据文件</div>
									<div class="font-size-9 line-height-18">Document Basis</div>
								</th>
								<th class="colwidth-10" >
									<div class="font-size-12 line-height-18">目前状态</div>
									<div class="font-size-9 line-height-18">Current Atate</div>
								</th>
								<th class="colwidth-15" >
									<div class="font-size-12 line-height-18">管理部门</div>
									<div class="font-size-9 line-height-18">Department</div>
								</th>
							</tr>
						</thead>
						<tbody id="housingfacilities_list"></tbody>
					</table>
				</div>
				<div class="clearfix"></div>
			<div id="selectTools"></div>	
			<div id="toolsDetailId" class="col-lg-12 col-md-12 padding-left-0 padding-right-0" >
				<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
					<div class="col-lg-1 col-sm-2 col-xs-4 padding-left-0 padding-right-0" >
						<div class="font-size-16 line-height-18">维护内容</div>
						<div class="font-size-9 ">Maintenance Content</div>
					</div>
               		<button id="save" type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" onclick="saveAll()" >
						<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
					</button>
					
					<div class="clearfix"></div>
				</div>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x: auto;">
					<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width: 1400px;">
						<thead>
							<tr>
								<th class="colwidth-5" >	
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-3" >
									<div class="font-size-12 notwrap">序号</div>
									<div class="font-size-9 notwrap">No.</div>
								</th>
								<th class="colwidth-13" >
									<div class="font-size-12 notwrap">维护类型</div>
									<div class="font-size-9 notwrap">Maintenance Type</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 notwrap">维护周期(天)</div>
									<div class="font-size-9 notwrap">Maintenance Location(Day)</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 notwrap">上次维护日期</div>
									<div class="font-size-9 notwrap">Last Date</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 notwrap">下次到期日期</div>
									<div class="font-size-9 notwrap">Next Date</div>
								</th>
								<th class="colwidth-7">
									<div class="font-size-12 notwrap">剩余天数</div>
									<div class="font-size-9 notwrap">Indate</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 notwrap">维护工作单号</div>
									<div class="font-size-9 notwrap">Maintenance Work No.</div>
								</th>
							</tr>
						</thead>
						<tbody id="detailList">
						
						</tbody>
						
					</table>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>

<!-------详情对话框 Start-------->
	
<div class="modal fade" id="alertHousingfacilitiesForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false >
	<div class="modal-dialog" style="width:60%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertHousingfacilitiesFormBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18" id="headChina" >新增</div>
						<div class="font-size-9 " id="headEnglish">Add</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" >
						
						<form id="form">
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">基本信息</div>
								<div class="font-size-9 ">Basic Information</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
								<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">设备名称</div>
										<div class="font-size-9 line-height-18">Name</div>
									</label>
									<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="sbmc" name="Name" maxlength="50" />
										<input type="hidden" class="form-control " id="id" />
									</div>
								</div>
							
								<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">设备编号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</label>
									<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="sbbh" name="sbbh" maxlength="50" />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">适用机型</div>
										<div class="font-size-9 line-height-18">Applicability</div>
									</label>
									<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="syjx" name="syjx" maxlength="50" />
									</div>
								</div>
							
								<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">依据文件</div>
										<div class="font-size-9 line-height-18">Document Basis</div>
									</label>
									<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="yjwj" name="yjwj" maxlength="50" />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">目前状态</div>
										<div class="font-size-9 line-height-18">Current Atate</div>
									</label>
									<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<select class='form-control' id='mqzt' name="mqzt" >
											<option value="可用" >可用</option>
											<option value="不可用" >不可用</option>
									    </select>
									</div>
								</div>
								
								<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">管理部门</div>
										<div class="font-size-9 line-height-18">Department</div>
									</label>
									<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="glbm" name="glbm" maxlength="50" />
									</div>
								</div>
								
								<div class="clearfix"></div>
							
							</div>
						
							<div class="clearfix"></div>
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">维护内容</div>
								<div class="font-size-9 ">Maintenance Content</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 " style="overflow-x: auto;">

								<!-- start:table -->
								<table class="table table-bordered table-striped table-hover table-set" style="min-width: 400px;">
									<thead>
								   		<tr>
											<th class="colwidth-5" >
												<button type="button" class="line6" onclick="addRotatableCol()">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
												</button>
											</th>
											<th class="colwidth-3" >
												<div class="font-size-12 notwrap">序号</div>
												<div class="font-size-9 notwrap">No.</div>
											</th>
											<th class="colwidth-13" >
												<div class="font-size-12 notwrap">维护类型</div>
												<div class="font-size-9 notwrap">Maintenance Type</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">维护周期(天)</div>
												<div class="font-size-9 notwrap">Maintenance Location(Day)</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">上次维护日期</div>
												<div class="font-size-9 notwrap">Last Date</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">下次到期日期</div>
												<div class="font-size-9 notwrap">Next Date</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 notwrap">剩余天数</div>
												<div class="font-size-9 notwrap">Indate</div>
											</th>
											<th class="colwidth-20" >
												<div class="font-size-12 notwrap">维护工作单号</div>
												<div class="font-size-9 notwrap">Maintenance Work No.</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="rotatable">
									
									</tbody>
								</table>
								<!-- end:table -->
					     		<div class="clearfix"></div>
							 </div> 
							
							<div class="clearfix"></div>
						</form>
						
					 	 <div class="text-right margin-top-10 margin-right-0">
							<button id="save" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:save();">
			                   	<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			              		<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
			           	</div>
                  		<br/>
					 </div> 
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div>

<!------- 详情对话框 End-------->

<!-------维护历史对话框 Start-------->
	
<div class="modal fade" id="alertHistoryForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:80%;height:50%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertHistoryFormBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">维护历史</div>
						<div class="font-size-9 ">Maintenance History</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" style="overflow-y:auto;height:600px;">
			           	
            			<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x:auto;">
							<table class="table table-thin table-bordered table-striped table-hover text-center table-set"  style="min-width: 800px;">
								<thead>
									<tr>
										<th class="colwidth-3" >
											<div class="font-size-12 notwrap">序号</div>
											<div class="font-size-9 notwrap">No.</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12 notwrap">维护工作单号</div>
											<div class="font-size-9 notwrap">Maintenance Work No.</div>
										</th>
										<th class="colwidth-13" >
											<div class="font-size-12 notwrap">维护类型</div>
											<div class="font-size-9 notwrap">Maintenance Type</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 notwrap">维护周期(天)</div>
											<div class="font-size-9 notwrap">Maintenance Location(Day)</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 notwrap">上次维护日期</div>
											<div class="font-size-9 notwrap">Last Date</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 notwrap">下次到期日期</div>
											<div class="font-size-9 notwrap">Next Date</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 notwrap">剩余天数</div>
											<div class="font-size-9 notwrap">Indate</div>
										</th>
									</tr>
								</thead>
								<tbody id="historyDetailList"></tbody>
							</table>
						</div>
						<div class="clearfix"></div>
						<div class="text-right margin-top-10 margin-right-0">
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			              		<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
			           	</div>
			            <br/>
					 </div> 
				</div>
			</div>
		</div>
	</div>
</div>

<!-------维护历史对话框 End-------->

<script type="text/javascript">

	var alertFormId = 'alertHousingfacilitiesForm';
	var headChinaId = 'headChina';
	var headEnglishId = 'headEnglish';
	var list = [];
	var xh = 1;
	var mainId = '';
	
	$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		initData();
		loadContentHtml();
		$("#detailList").append("<tr><td colspan='8' class='text-center'>暂无数据 No data.</td></tr>");
	});
	
	function loadContentHtml(){
		var htmlContent = '';
		$.each(list,function(index,row){
	   		if (index % 2 == 0) {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick=selectRow('"+row.id+"')>";
	  		} else {
		   		htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick=selectRow('"+row.id+"')>";
	  		}
	   		
	   	 	htmlContent += "<td class='text-center'>";
		   	htmlContent += "<i class='icon-edit color-blue cursor-pointer' onClick=\"openEdit('"+ row.id + "')\" title='编辑 Edit'>&nbsp;&nbsp;</i>";
		   	htmlContent += "<i class='icon-trash color-blue cursor-pointer' onClick=\"del('"+ row.id + "')\" title='作废 Invalid'></i>";  
		   	htmlContent += "</td>";  
	   		
	  	 	htmlContent += "<td class='text-center'>" + (index+1) + "</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.sbmc)+"' class='text-left' >"+StringUtil.escapeStr(row.sbmc)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.sbbh)+"' class='text-left' >"+StringUtil.escapeStr(row.sbbh)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.syjx)+"' class='text-left' >"+StringUtil.escapeStr(row.syjx)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.yjwj)+"' class='text-left' >"+StringUtil.escapeStr(row.yjwj)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.mqzt)+"' class='text-left' >"+StringUtil.escapeStr(row.mqzt)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.glbm)+"' class='text-left' >"+StringUtil.escapeStr(row.glbm)+"</td>";
	  		htmlContent += "</tr>";  
	    
	   });
	   $("#housingfacilities_list").empty();
	   if(htmlContent == ''){
		   htmlContent = "<tr><td colspan=\"8\" class='text-center'>暂无数据 No data.</td></tr>";
	   }
	   $("#housingfacilities_list").html(htmlContent);
 	}
	
	//打开新增弹出框
	function openAdd(){
		$("#"+headChinaId, $("#"+alertFormId)).html('新增');
		$("#"+headEnglishId, $("#"+alertFormId)).html('Add');
		var obj = {};
		obj.id = '';
		obj.sbmc = '';
		obj.sbbh = '';
		obj.syjx = '';
		obj.yjwj = '';
		obj.mqzt = '可用';
		obj.glbm = '';
		setData(obj);
		$("#"+alertFormId).modal("show");
		$("#rotatable", $("#"+alertFormId)).empty();
		$("#rotatable", $("#"+alertFormId)).append("<tr><td colspan='8' class='text-center'>暂无数据 No data.</td></tr>");
	}
	
	//打开修改航材航材弹出框
	function openEdit(id){
		$("#"+headChinaId, $("#"+alertFormId)).html('修改');
		$("#"+headEnglishId, $("#"+alertFormId)).html('Edit');
		var obj = getRowData(id);
		setData(obj);
		initTableCol(obj.detailList);
		$("#"+alertFormId).modal("show");
	}
	//设置表单数据
	function setData(obj){
		$("#id", $("#"+alertFormId)).val(obj.id);
		$("#sbmc", $("#"+alertFormId)).val(obj.sbmc);
		$("#sbbh", $("#"+alertFormId)).val(obj.sbbh);
		$("#syjx", $("#"+alertFormId)).val(obj.syjx);
		$("#yjwj", $("#"+alertFormId)).val(obj.yjwj);
		$("#mqzt", $("#"+alertFormId)).val(obj.mqzt);
		$("#glbm", $("#"+alertFormId)).val(obj.glbm);
	}
	
	//新增数据
	function save(){
		AlertUtil.showMessage('保存成功!');
		$("#"+alertFormId).modal("hide");
	}
	//批量保存
	function saveAll(){
		AlertUtil.showMessage('保存成功!');
	}
	
	//移除一行
	function del(id){
		
		AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		
			$.each(list,function(index,row){
		    	if(row.id == id){
		    		list.pop(row);
		    		refreshPage();
		    		return false;
		    	}  
			});
		
		}});
	}
	
	// 初始化维护内容
	function initTableCol(data) {

		// 先移除暂无数据一行
		$("#rotatable", $("#"+alertFormId)).empty();

		if (JSON.stringify(data) == '[]') {
			$("#rotatable").append("<tr><td colspan='4' class='text-center'>暂无数据 No data.</td></tr>");
			return;
		}
		$.each(data, function(i, obj) {
			obj.xh = xh++;
			addRow(obj);
		});
	}
	
	//向table新增一行
	function addRotatableCol() {

		// 先移除暂无数据一行
		var len = $("#rotatable", $("#"+alertFormId)).length;
		if (len == 1) {
			if ($("#rotatable", $("#"+alertFormId)).find("td").length == 1) {
				$("#rotatable", $("#"+alertFormId)).empty();
			}
		}
		var obj = {};
		obj.id = "";
		obj.xh = xh++;
		obj.whlx = "";
		obj.whzq = "";
		obj.scwhrq = "";
		obj.xcwhrq = "";
		obj.syts = "";
		obj.whgzdh = "";
		addRow(obj);
	}
	
	//向table新增一行
	function addRow(obj) {

		var tr = "";
		tr += "<tr>";

		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += "<button type='button' class='line6' onclick='removeCol(this)'>";
		tr += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
		tr += "</button>";
		tr += "<input type='hidden' class='form-control' name='hiddenid' value='"+ obj.id + "'/>";
		tr += "</td>";

		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += "	<span name='xh'>" + obj.xh + "</span>";
		tr += "</td>";

		var whlxSelect = "<select class='form-control' name='whlx'>";
		whlxSelect += "<option value='检查' "+(obj.whlx == '检查'?"selected=true":"")+">检查</option>";
		whlxSelect += "<option value='润滑' "+(obj.whlx == '润滑'?"selected=true":"")+">润滑</option>";
		whlxSelect += "<option value='保养' "+(obj.whlx == '保养'?"selected=true":"")+">保养</option>";
		whlxSelect += "</select>";
		
		tr += "<td>";
		tr += whlxSelect;
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<input name='whzq' type='text' value='"+ StringUtil.escapeStr(obj.whzq)+ "' class='form-control' onkeyup='clearNoNumber(this)' maxlength='10' />";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<input name='scwhrq' type='text' value='"+ StringUtil.escapeStr(obj.scwhrq)+ "' class='form-control datepicker' data-date-format='yyyy-mm-dd' onchange=loadNextDate(this) />";
		tr += "</td>";
		
		tr += "<td style='text-align:center;vertical-align:middle;' name='xcwhrq' >"+StringUtil.escapeStr(obj.xcwhrq)+"</td>";
		tr += "<td style='text-align:right;vertical-align:middle;' name='syts'>"+StringUtil.escapeStr(obj.syts)+"</td>";
		
		tr += "<td>";
		tr += "	<input name='whgzdh' type='text' value='"+ StringUtil.escapeStr(obj.whgzdh)+ "' class='form-control' maxlength='50' />";
		tr += "</td>";

		tr += "</tr>";

		$("#rotatable", $("#"+alertFormId)).append(tr);
		$('.datepicker').datepicker({
		 	autoclose: true,
		 	clearBtn:false
		});
	}
	
	// 移除一行
	function removeCol(obj) {
		var id = $(obj).next().val();
		if ('' != id && null != id) {
			
			AlertUtil.showConfirmMessage("确定删除此行吗？", {callback: function(){
				
				$(obj).parent().parent().remove();
				resXh();
				xh--;
				
			}});
			
		} else {
			$(obj).parent().parent().remove();
			resXh();
			xh--;
		}
		var len = $("#rotatable", $("#"+alertFormId)).find("tr").length;
		if (len < 1) {
			$("#rotatable", $("#"+alertFormId)).append("<tr><td colspan='8' class='text-center'>暂无数据 No data.</td></tr>");
		}
	}

	function resXh() {

		var len = $("#rotatable", $("#"+alertFormId)).find("tr").length;
		if (len == 1) {
			if ($("#rotatable", $("#"+alertFormId)).find("td").length == 1) {
				return false;
			}
		}
		var xh = 1;
		if (len > 0) {
			$("#rotatable", $("#"+alertFormId)).find("tr").each(function() {
				var index = $(this).index(); // 当前行
				$("span[name='xh']").eq(index).html(xh++);
			});
		}
	}
	//选中一行
	function selectRow(id){
		mainId = id;
		var obj = getRowData(id);
		loadDetailList(obj.detailList);
	}
	
	//加载维护内容
	function loadDetailList(data) {
		var htmlContent = '';
		$.each(data,function(index,row){
	   		if (index % 2 == 0) {
				htmlContent += "<tr id='"+row.id+"' bgcolor=\"#f9f9f9\" >";
	  		} else {
		   		htmlContent += "<tr id='"+row.id+"' bgcolor=\"#fefefe\" >";
	  		}
	   		htmlContent += "<td class='text-center'>";
		   	htmlContent += "<i class='icon-eye-open color-blue cursor-pointer' onClick=\"viewHistory('"+ row.id + "')\" title='查看历史 History'></i>";  
		   	htmlContent += "</td>";
	  	 	htmlContent += "<td class='text-center'>" + (index+1) + "</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.whlx)+"' style='text-align:center;vertical-align:middle;' >"+StringUtil.escapeStr(row.whlx)+"</td>";
	   		htmlContent += "<td style='text-align:right;vertical-align:middle;' >";
	   		htmlContent += StringUtil.escapeStr(row.whzq);
	   		htmlContent += "<input type='hidden' name='whzq' value='"+StringUtil.escapeStr(row.whzq)+"' />";
	   		htmlContent += "</td>";
			htmlContent += "<td>";
			htmlContent += "<input type='text' class='form-control datepicker' name='scwhrq' value="+formatUndefine(row.scwhrq)+" data-date-format='yyyy-mm-dd' onchange=changeDate('"+row.id+"') />";
			htmlContent += "</td>";
			
	   		htmlContent += "<td style='text-align:center;vertical-align:middle;' name='xcwhrq' >"+StringUtil.escapeStr(row.xcwhrq)+"</td>";
	   		htmlContent += "<td style='text-align:right;vertical-align:middle;' name='syts'>"+StringUtil.escapeStr(row.syts)+"</td>";
		  	
		  	htmlContent += "<td>";
			htmlContent += "<input type='text' class='form-control' name='whgzdh' value="+formatUndefine(row.whgzdh)+" />";
			htmlContent += "</td>";
	   		
	   		htmlContent += "</tr>";  
	   	});
		$("#detailList").empty();
	  	if(htmlContent == ''){
			htmlContent = "<tr><td colspan=\"8\" class='text-center'>暂无数据 No data.</td></tr>";
	   	}
	   	$("#detailList").html(htmlContent);
		$('.datepicker').datepicker({
		 	autoclose: true,
		 	clearBtn:false
		});
	}
	
	function viewHistory(id){
		var htmlContent = '';
		var obj = getRowData(mainId);
		var data = [];
		$.each(obj.detailList, function(i, row) {
			if(id == row.id){
				data.push(row);
			}
		});
		$.each(data,function(index,row){
	   		if (index % 2 == 0) {
				htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
	  		} else {
		   		htmlContent += "<tr bgcolor=\"#fefefe\" >";
	  		}
	  	 	htmlContent += "<td class='text-center'>" + (index+1) + "</td>";
		  	htmlContent += "<td title='"+StringUtil.escapeStr(row.whgzdh)+"' style='text-align:center;vertical-align:middle;' >"+StringUtil.escapeStr(row.whgzdh)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.whlx)+"' style='text-align:center;vertical-align:middle;' >"+StringUtil.escapeStr(row.whlx)+"</td>";
	   		htmlContent += "<td style='text-align:right;vertical-align:middle;' >"+StringUtil.escapeStr(row.whzq)+"</td>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+StringUtil.escapeStr(row.scwhrq)+"</td>";
	   		htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+StringUtil.escapeStr(row.xcwhrq)+"</td>";
	   		htmlContent += "<td style='text-align:right;vertical-align:middle;' >"+StringUtil.escapeStr(row.syts)+"</td>";
	   		htmlContent += "</tr>";  
	   });
	   $("#historyDetailList").empty();
	   if(htmlContent == ''){
		   htmlContent = "<tr><td colspan=\"7\" class='text-center'>暂无数据 No data.</td></tr>";
	   }
	   $("#historyDetailList").html(htmlContent);
		$("#alertHistoryForm").modal("show");
	}
	
	//获取行数据
	function getRowData(id){
		var result = {};
		$.each(list, function(i, row) {
			if(id == row.id){
				result = row;
				return false
			}
		});
		return result;
	}
	
	//改变周期或上次维护日期时加载下次到期日期
	function loadNextDate(obj){
		var whzq = $(obj).parent().parent().find("input[name='whzq']").val();
		var scwhrq = $(obj).parent().parent().find("input[name='scwhrq']").val();
		var xcwhrq = TimeUtil.dateOperator(scwhrq,whzq);
		$(obj).parent().parent().find("td[name='xcwhrq']").html(xcwhrq);
		var result = DateDiff(xcwhrq,  getNowFormatDate());
		$(obj).parent().parent().find("td[name='syts']").html(result);
	}
	
	//改变上次维护日期时加载下次到期日期
	function changeDate(id){
		var whzq = $("#"+id, $("#detailList")).find("input[name='whzq']").val();
		var scwhrq = $("#"+id, $("#detailList")).find("input[name='scwhrq']").val();
		var xcwhrq = TimeUtil.dateOperator(scwhrq,whzq);
		$("#"+id, $("#detailList")).find("td[name='xcwhrq']").html(xcwhrq);
		var result = DateDiff(xcwhrq,  getNowFormatDate());
		$("#"+id, $("#detailList")).find("td[name='syts']").html(result);
	}
	
	//计算天数差的函数，通用  
	function DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
		var aDate,  oDate1,  oDate2,  iDays;  
       	aDate  =  sDate1.split("-");  
       	oDate1  =  new  Date(aDate[0]  +  '-'  +  aDate[1]  +  '-'  +  aDate[2]);    //转换为12-18-2006格式  
       	aDate  =  sDate2.split("-");  
       	oDate2  =  new  Date(aDate[0]  +  '-'  +  aDate[1]  +  '-'  +  aDate[2]);  
       	iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);    //把相差的毫秒数转换为天数  
       	if(oDate1.getTime() < oDate2.getTime()){
       		iDays = "-" + iDays;
       	}
       	return iDays;
 	}    
	
	function getNowFormatDate() {
	    var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
	    return currentdate;
	} 
	
	//刷新页面
	function refreshPage(){
		loadContentHtml();
	}
	
	//初始化数据
	function initData(){
		var obj1 = getMainData("整体梯架","ASB-001","CRJ200","厂家说明书","可用","东一飞");
		var detailList1 = [];
		var detailObj1 = getDetailData("检查","7","2017-04-18","2017-04-25","7","A00987");
		detailList1.push(detailObj1);
		var detailObj2 = getDetailData("润滑","30","2017-04-18","2017-05-18","30","A00988");
		detailList1.push(detailObj2);
		obj1.detailList = detailList1;
		list.push(obj1);
		
		var obj2 = getMainData("工具拖车","BSB-002","S76D","厂家说明书","可用","东一飞");
		var detailList2 = [];
		var detailObj3 = getDetailData("检查","3","2017-04-16","2017-04-25","9","A00911");
		detailList2.push(detailObj3);
		var detailObj4 = getDetailData("润滑","33","2017-04-16","2017-05-28","46","A00912");
		detailList2.push(detailObj4);
		var detailObj5 = getDetailData("保养","25","2017-04-16","2017-06-18","56","A00913");
		detailList2.push(detailObj5);
		obj2.detailList = detailList2;
		list.push(obj2);
	}
	//生成主数据
	function getMainData(sbmc,sbbh,syjx,yjwj,mqzt,glbm){
		var obj = {};
		obj.id = getUuid();
		obj.sbmc = sbmc;
		obj.sbbh = sbbh;
		obj.syjx = syjx;
		obj.yjwj = yjwj;
		obj.mqzt = mqzt;
		obj.glbm = glbm;
		return obj;
	}
	
	//生成详情数据
	function getDetailData(whlx,whzq,scwhrq,xcwhrq_,syts_,whgzdh){
		var xcwhrq = TimeUtil.dateOperator(scwhrq,whzq);
		var syts = DateDiff(xcwhrq,  getNowFormatDate());
		var obj = {};
		obj.id = getUuid();
		obj.whlx = whlx;
		obj.whzq = whzq;
		obj.scwhrq = scwhrq;
		obj.xcwhrq = xcwhrq;
		obj.syts = syts;
		obj.whgzdh = whgzdh;
		return obj;
	}
	
	//只能输入数字
	function clearNoNumber(obj){
	     //先把非数字的都替换掉，除了数字
	     obj.value = obj.value.replace(/[^\d]/g,"");
	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	  		obj.value = 0;
	  	 }
	     loadNextDate(obj);
	}
	
	//获取uuid
	function getUuid() {
	    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	        return v.toString(16);
	    });
	}
	
</script>
</body>
</html>