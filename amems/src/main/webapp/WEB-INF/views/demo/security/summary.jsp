<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>质量安全审核发现问题汇总单</title>
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
								<th class="colwidth-9" >
									<div class="font-size-12 line-height-18">制单日期</div>
									<div class="font-size-9 line-height-18">Create Date</div>
								</th>
								<th class="colwidth-13" >
									<div class="font-size-12 line-height-18">制单人</div>
									<div class="font-size-9 line-height-18">Creator</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 line-height-18">审核项目</div>
									<div class="font-size-9 line-height-18">Audit items</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 line-height-18">审核编号</div>
									<div class="font-size-9 line-height-18">Audit No.</div>
								</th>
								<th class="colwidth-10" >
									<div class="font-size-12 line-height-18">审核类别</div>
									<div class="font-size-9 line-height-18">Audit Type</div>
								</th>
								<th class="colwidth-30" >
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</th>
							</tr>
						</thead>
						<tbody id="summary_list"></tbody>
					</table>
				</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>

<!-------详情对话框 Start-------->
	
<div class="modal fade" id="alertsummaryForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false >
	<div class="modal-dialog" style="width:50%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertsummaryFormBody">
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
									<label  class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核项目</div>
										<div class="font-size-9 line-height-18">Audit items</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="shxm" name="shxm" maxlength="50" />
										<input type="hidden" class="form-control " id="id" />
									</div>
								</div>
							
								<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核编号</div>
										<div class="font-size-9 line-height-18">Audit No.</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="shbh" name="shbh" maxlength="50" />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制单日期</div>
										<div class="font-size-9 line-height-18">Create Date</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="zdrq" name="zdrq" value="2017-04-18" readonly/>
									</div>
								</div>
							
								<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制单人</div>
										<div class="font-size-9 line-height-18">Creator</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="zdr" name="zdr" value="小张" readonly />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核类别</div>
										<div class="font-size-9 line-height-18">Audit Type</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
										<label style="margin-right: 20px;font-weight: normal">
									    	<input name="shlb" type="radio" value="系统审核" />系统审核
									    </label> 
										<label style="margin-right: 20px;font-weight: normal">
											<input name="shlb" type="radio" value="生产审核" />生产审核
										</label> 
										<label style="font-weight: normal">
											<input name="shlb" type="radio" value="专项审核" />专项审核
										</label> 
									</div>
								</div>
								
								<div class="clearfix"></div>
							
							 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
										<textarea class="form-control" id="bz" name="bz" maxlength="300" ></textarea>
									</div>
								</div>
								
								<div class="clearfix"></div>
							
							</div>
						
							<div class="clearfix"></div>
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">问题详情</div>
								<div class="font-size-9 ">Problem details</div>
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
												<div class="font-size-12 notwrap">编号</div>
												<div class="font-size-9 notwrap">number</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">问题描述</div>
												<div class="font-size-9 notwrap">Problem description</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">依据</div>
												<div class="font-size-9 notwrap">Basis</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">问题类型</div>
												<div class="font-size-9 notwrap">Problem type</div>
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
							<button id="submit" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:submit();">
			                   	<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
							<button id="audit" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:audit();">
			                   	<div class="font-size-12">审核</div>
								<div class="font-size-9">Audit</div>
							</button>
							<button id="approve" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:approve();">
			                   	<div class="font-size-12">批准</div>
								<div class="font-size-9">Approve</div>
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

<script type="text/javascript">

	var alertFormId = 'alertsummaryForm';
	var headChinaId = 'headChina';
	var headEnglishId = 'headEnglish';
	var list = [];
	var xh = 1;
	
	$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		initData();
		loadContentHtml();
	});
	
	function loadContentHtml(){
		var htmlContent = '';
		$.each(list,function(index,row){
	   		if (index % 2 == 0) {
				htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
	  		} else {
		   		htmlContent += "<tr bgcolor=\"#fefefe\" >";
	  		}
	   		
	   	 	htmlContent += "<td class='text-center'>";
	   		htmlContent += "<i class='icon-foursquare color-blue cursor-pointer' onClick=\"openAudit('"+ row.id + "')\" title='审核 Review'>&nbsp;&nbsp;</i>";
	   		htmlContent += "<i class='icon-check color-blue cursor-pointer' onClick=\"openApprove('"+ row.id + "')\" title='批准 Approved'>&nbsp;&nbsp;</i>";
	   		htmlContent += "<i class='icon-edit color-blue cursor-pointer' onClick=\"openEdit('"+ row.id + "')\" title='编辑 Edit'>&nbsp;&nbsp;</i>";
		   	htmlContent += "<i class='icon-trash color-blue cursor-pointer' onClick=\"del('"+ row.id + "')\" title='作废 Invalid'></i>";  
		   	htmlContent += "</td>";  

	   		htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.zdrq)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr)+"' class='text-left' >"+StringUtil.escapeStr(row.zdr)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.shxm)+"' class='text-left' >"+StringUtil.escapeStr(row.shxm)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.shbh)+"' class='text-left' >"+StringUtil.escapeStr(row.shbh)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.shlb)+"' class='text-left' >"+StringUtil.escapeStr(row.shlb)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left' >"+StringUtil.escapeStr(row.bz)+"</td>";
	  		htmlContent += "</tr>";  
	    
	   });
	   $("#summary_list").empty();
	   if(htmlContent == ''){
		   htmlContent = "<tr><td colspan=\"7\" class='text-center'>暂无数据 No data.</td></tr>";
	   }
	   $("#summary_list").html(htmlContent);
 	}
	
	//打开新增弹出框
	function openAdd(){
		$("#save", $("#"+alertFormId)).show();
		$("#submit", $("#"+alertFormId)).show();
		$("#audit", $("#"+alertFormId)).hide();
		$("#approve", $("#"+alertFormId)).hide();
		$("#"+headChinaId, $("#"+alertFormId)).html('新增');
		$("#"+headEnglishId, $("#"+alertFormId)).html('Add');
		var obj = {};
		obj.id = '';
		obj.shxm = '';
		obj.shbh = '';
		obj.zdrq = '2017-04-18';
		obj.zdr = '小张';
		obj.shlb = '系统审核';
		obj.bz = '';
		setData(obj);
		$("#"+alertFormId).modal("show");
		$("#rotatable", $("#"+alertFormId)).empty();
		$("#rotatable", $("#"+alertFormId)).append("<tr><td colspan='6' class='text-center'>暂无数据 No data.</td></tr>");
	}
	
	//打开修改弹出框
	function openEdit(id){
		$("#save", $("#"+alertFormId)).show();
		$("#submit", $("#"+alertFormId)).show();
		$("#audit", $("#"+alertFormId)).hide();
		$("#approve", $("#"+alertFormId)).hide();
		$("#"+headChinaId, $("#"+alertFormId)).html('修改');
		$("#"+headEnglishId, $("#"+alertFormId)).html('Edit');
		var obj = getRowData(id);
		setData(obj);
		initTableCol(obj.detailList);
		$("#"+alertFormId).modal("show");
	}
	
	//打开审核弹出框
	function openAudit(id){
		$("#save", $("#"+alertFormId)).hide();
		$("#submit", $("#"+alertFormId)).hide();
		$("#audit", $("#"+alertFormId)).show();
		$("#approve", $("#"+alertFormId)).hide();
		$("#"+headChinaId, $("#"+alertFormId)).html('审核');
		$("#"+headEnglishId, $("#"+alertFormId)).html('Audit');
		var obj = getRowData(id);
		setData(obj);
		initTableCol(obj.detailList);
		$("#"+alertFormId).modal("show");
	}
	
	//打开批准弹出框
	function openApprove(id){
		$("#save", $("#"+alertFormId)).hide();
		$("#submit", $("#"+alertFormId)).hide();
		$("#audit", $("#"+alertFormId)).hide();
		$("#approve", $("#"+alertFormId)).show();
		$("#"+headChinaId, $("#"+alertFormId)).html('批准');
		$("#"+headEnglishId, $("#"+alertFormId)).html('Approve');
		var obj = getRowData(id);
		setData(obj);
		initTableCol(obj.detailList);
		$("#"+alertFormId).modal("show");
	}
	
	//设置表单数据
	function setData(obj){
		$("#id", $("#"+alertFormId)).val(obj.id);
		$("#shxm", $("#"+alertFormId)).val(obj.shxm);
		$("#shbh", $("#"+alertFormId)).val(obj.shbh);
		$("#zdrq", $("#"+alertFormId)).val(obj.zdrq);
		$("#zdr", $("#"+alertFormId)).val(obj.zdr);
		$("input:radio[name='shlb'][value='"+obj.shlb+"']", $("#"+alertFormId)).attr("checked",true); 
		$("#bz", $("#"+alertFormId)).val(obj.bz);
	}
	
	//新增数据
	function save(){
		AlertUtil.showMessage('保存成功!');
		$("#"+alertFormId).modal("hide");
	}
	
	//提交数据
	function submit(){
		AlertUtil.showMessage('提交成功!');
		$("#"+alertFormId).modal("hide");
	}
	
	//审核数据
	function audit(){
		AlertUtil.showMessage('审核通过!');
		$("#"+alertFormId).modal("hide");
	}
	
	//批准数据
	function approve(){
		AlertUtil.showMessage('批准通过!');
		$("#"+alertFormId).modal("hide");
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
			$("#rotatable").append("<tr><td colspan='6' class='text-center'>暂无数据 No data.</td></tr>");
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
		obj.bh = "";
		obj.wtms = "";
		obj.yj = "";
		obj.wtlx = "事实性问题";
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

		var wtlxSelect = "<select class='form-control' name='wtlx'>";
		wtlxSelect += "<option value='事实性问题' "+(obj.wtlx == '事实性问题'?"selected=true":"")+">事实性问题</option>";
		wtlxSelect += "<option value='偏好性问题' "+(obj.wtlx == '偏好性问题'?"selected=true":"")+">偏好性问题</option>";
		wtlxSelect += "<option value='判断性问题' "+(obj.wtlx == '判断性问题'?"selected=true":"")+">判断性问题</option>";
		wtlxSelect += "</select>";
		
		tr += "<td>";
		tr += "	<input name='bh' type='text' value='"+ StringUtil.escapeStr(obj.bh)+ "' class='form-control' maxlength='50' />";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<input name='wtms' type='text' value='"+ StringUtil.escapeStr(obj.wtms)+ "' class='form-control' />";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<input name='yj' type='text' value='"+ StringUtil.escapeStr(obj.yj)+ "' class='form-control' />";
		tr += "</td>";
		
		tr += "<td>";
		tr += wtlxSelect;
		tr += "</td>";
		
		tr += "</tr>";

		$("#rotatable", $("#"+alertFormId)).append(tr);
	}
	
	// 移除一行
	function removeCol(obj) {
		var id = $(obj).next().val();
		if ('' != id && null != id) {
			
			AlertUtil.showConfirmMessage("确定删除此行吗？", {callback: function(){
				
				actionRemoveCol(obj);
				
			}});
			
		} else {
			actionRemoveCol(obj);
		}
	}
	
	function actionRemoveCol(obj){
		$(obj).parent().parent().remove();
		resXh();
		xh--;
		var len = $("#rotatable", $("#"+alertFormId)).find("tr").length;
		if (len < 1) {
			$("#rotatable", $("#"+alertFormId)).append("<tr><td colspan='6' class='text-center'>暂无数据 No data.</td></tr>");
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
	
	//刷新页面
	function refreshPage(){
		loadContentHtml();
	}
	
	//初始化数据
	function initData(){
		var obj1 = getMainData("2017-04-18","小张","设立农药生产企业","SH-001","生产审核","审核通过");
		var detailList1 = [];
		var detailObj1 = getDetailData("SQ001","需要重新检查","质量手册","判断性问题");
		detailList1.push(detailObj1);
		var detailObj2 = getDetailData("SQ002","过期","ISO9001：2008","偏好性问题");
		detailList1.push(detailObj2);
		obj1.detailList = detailList1;
		list.push(obj1);
		
	}
	//生成主数据
	function getMainData(zdrq,zdr,shxm,shbh,shlb,bz){
		var obj = {};
		obj.id = getUuid();
		obj.zdrq = zdrq;
		obj.zdr = zdr;
		obj.shxm = shxm;
		obj.shbh = shbh;
		obj.shlb = shlb;
		obj.bz = bz;
		return obj;
	}
	
	//生成详情数据
	function getDetailData(bh,wtms,yj,wtlx){
		var obj = {};
		obj.id = getUuid();
		obj.bh = bh;
		obj.wtms = wtms;
		obj.yj = yj;
		obj.wtlx = wtlx;
		return obj;
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