<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>质量安全审核项目单</title>
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

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px">
					<table class="table table-thin table-bordered table-set">
						<thead>
							<tr>
								<th class="colwidth-7" >	
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-13" >
									<div class="font-size-12 line-height-18">单号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="colwidth-13" >
									<div class="font-size-12 line-height-18">制表人</div>
									<div class="font-size-9 line-height-18">Greator</div>
								</th>
								<th class="colwidth-13" >
									<div class="font-size-12 line-height-18">制表日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Reamrk</div>
								</th>
							</tr>
						</thead>
						<tbody>
						   <tr>
						       <td class='text-center'>
						       <i class='icon-edit color-blue cursor-pointer' onClick="openEdit(this)" title='编辑 Edit'>&nbsp;&nbsp;</i>
						       <i class='icon-trash color-blue cursor-pointer' onClick="del()" title='作废 Invalid'></i> 
		                       </td>
						       <td class='text-left' name="shxm">DH-2113</td>
						       <td class='text-left' name="zbr">admin mzl</td>
						       <td class='text-left' name="zbrq">2017-09-21</td>
						       <td class='text-left' name="bz">审核送检初级</td>
						   </tr>
						   <tr>
						       <td class='text-center'>
						       <i class='icon-edit color-blue cursor-pointer' onClick="openEdit(this)" title='编辑 Edit'>&nbsp;&nbsp;</i>
						       <i class='icon-trash color-blue cursor-pointer' onClick="del()" title='作废 Invalid'></i> 
		                       </td>
						       <td class='text-left' name="shxm">DH-3213</td>
						       <td class='text-left' name="zbr">user mei</td>
						       <td class='text-left' name="zbrq">2016-06-11</td>
						       <td class='text-left' name="bz">业务部门负责人对调查资料进行审查,并移交审核岗位进行审</td>
						   </tr>
						   <tr>
						       <td class='text-center'>
						       <i class='icon-edit color-blue cursor-pointer' onClick="openEdit(this)" title='编辑 Edit'>&nbsp;&nbsp;</i>
						       <i class='icon-trash color-blue cursor-pointer' onClick="del()" title='作废 Invalid'></i> 
		                       </td>
						       <td class='text-left' name="shxm">DH-001</td>
						       <td class='text-left' name="zbr">user tomas</td>
						       <td class='text-left' name="zbrq">2017-02-14</td>
						       <td class='text-left' name="bz"> 审核表 记录所有的审核流程的数据</td>
						   </tr>
						</tbody>
					</table>
				</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>

<!------新增 Start-------->
<div class="modal fade" id="AddAlert" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false >
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
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核项目单号</div>
										<div class="font-size-9 line-height-18">SafetyAudit</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="add_shxm" name="add_shxm" />
									</div>
								</div>
							
								<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制表人</div>
										<div class="font-size-9 line-height-18">Grteator</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="add_zbr" name="add_zbr" maxlength="50" />
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制表日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class='form-control datepicker' data-date-format="yyyy-mm-dd"  id="add_zbrq" name="add_zbrq" />
									</div>
								</div>
							</div>
							<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div></label>
									<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-7 padding-right-0 form-group">
										<textarea class="form-control" id="add_bz" name="add_bz"></textarea>
									</div>
								</div>
							</div>
							<div class="clearfix"></div>
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">项目信息</div>
								<div class="font-size-9 ">Project Information</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 " >
								<table class="table table-bordered table-striped table-hover table-set">
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
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">审核项目</div>
												<div class="font-size-9 notwrap">Audit Project</div>
											</th>
											<th class="colwidth-15" >
												<div class="font-size-12 notwrap">审核依据</div>
												<div class="font-size-9 notwrap">Audit Basis</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">符合性</div>
												<div class="font-size-9 notwrap">Is Audit</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 notwrap">备注</div>
												<div class="font-size-9 notwrap">Remark</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="rotatable">
									
									</tbody>
								</table>
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
<!-------新增对话框 End-------->
<!------修改 Start-------->
<div class="modal fade" id="EditAlert" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false >
	<div class="modal-dialog" style="width:60%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertHousingfacilitiesFormBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18" id="headChina" >修改</div>
						<div class="font-size-9 " id="headEnglish">Edit</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" >
						
						<form id="form">
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">基本信息</div>
								<div class="font-size-9 ">Basic Information</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核项目单号</div>
										<div class="font-size-9 line-height-18">SafetyAudit</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="edit_shxm" name="edit_shxm" />
									</div>
								</div>
							
								<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制表人</div>
										<div class="font-size-9 line-height-18">Grteator</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="edit_zbr" name="edit_zbr" />
									</div>
								</div>
								
								<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制表日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class='form-control datepicker' data-date-format="yyyy-mm-dd"  id="edit_zbrq" name="edit_zbrq" />
									</div>
								</div>
							</div>
							<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div></label>
									<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-7 padding-right-0 form-group">
										<textarea class="form-control" id="edit_bz" name="edit_bz"></textarea>
									</div>
								</div>
							</div>
							<div class="clearfix"></div>
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">项目信息</div>
								<div class="font-size-9 ">Project Information</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 " >
								<table class="table table-bordered table-striped table-hover table-set">
									<thead>
								   		<tr>
											<th class="colwidth-5" >
												<button type="button" class="line6" onclick="addRotatableCol2()">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
												</button>
											</th>
											<th class="colwidth-3" >
												<div class="font-size-12 notwrap">序号</div>
												<div class="font-size-9 notwrap">No.</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">审核项目</div>
												<div class="font-size-9 notwrap">Audit Project</div>
											</th>
											<th class="colwidth-15" >
												<div class="font-size-12 notwrap">审核依据</div>
												<div class="font-size-9 notwrap">Audit Basis</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">符合性</div>
												<div class="font-size-9 notwrap">Is Audit</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 notwrap">备注</div>
												<div class="font-size-9 notwrap">Remark</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="rotatable2">
									
									</tbody>
								</table>
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
<!-------修改对话框 End-------->

<script type="text/javascript">

	var list = [];
	var xh = 1;
	var mainId = '';
	
	$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		
		$('.datepicker').datepicker({
			 autoclose: true,
			 clearBtn:true
	    });
	});
	
	//打开新增弹出框
	function openAdd(){
		$("#AddAlert").modal("show");
		$("#add_shxm").val("");
		$("#add_zbr").val("");
		$("#add_zbrq").val("");
		$("#add_bz").val("");
		$("#rotatable").empty();
		$("#rotatable").append("<tr><td colspan='6' class='text-center'>暂无数据 No data.</td></tr>");
	}
	
	//打开修改航材航材弹出框
	function openEdit(obj){
		$("#EditAlert").modal("show");
	    $(obj).parent().parent().find("td").each(function(){
	        var name=$(this).attr("name");
	    	$("#edit_"+name).val($(this).text());
	    });
	    initEdit();
	}
	//新增数据
	function save(){
		AlertUtil.showMessage('保存成功!');
		$("#AddAlert").modal("hide");
		$("#EditAlert").modal("hide");
	}
	//移除一行
	function del(id){
		AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		    AlertUtil.showMessage('作废成功!');
		}});
	}
	
	//向table新增一行
	function addRotatableCol() {
		// 先移除暂无数据一行
		var len = $("#rotatable").length;
		if (len == 1) {
			if ($("#rotatable").find("td").length == 1) {
				$("#rotatable").empty();
			}
		}
		var obj = {};
		obj.id = "";
		obj.xh = xh++;
		$("#rotatable").append(addRow(obj));
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
		
		tr += "<td>";
		tr += "<input name='whzq' type='text' class='form-control' />";
		tr += "</td>";
		
		tr += "<td>";
		tr += "<input name='whzq' type='text' class='form-control' />";
		tr += "</td>";
		
		tr += "<td class='text-center' style='vertical-align:middle'>";
		tr += "<input type='radio' name='ischeck_"+obj.xh+"' value='1' checked/>是";
		tr += "<input type='radio' name='ischeck_"+obj.xh+"' value='0' />否";
		tr += "<input type='radio' name='ischeck_"+obj.xh+"' value='' />N/A";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<input name='whgzdh' type='text'  class='form-control'  />";
		tr += "</td>";
 
		tr += "</tr>";
		return tr;
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
		var len = $("#rotatable").find("tr").length;
		if (len < 1) {
			$("#rotatable").append("<tr><td colspan='6' class='text-center'>暂无数据 No data.</td></tr>");
		}
	}
	function resXh() {
		var len = $("#rotatable").find("tr").length;
		if (len == 1) {
			if ($("#rotatable").find("td").length == 1) {
				return false;
			}
		}
		var xh = 1;
		if (len > 0) {
			$("#rotatable").find("tr").each(function() {
				var index = $(this).index(); // 当前行
				$("span[name='xh']").eq(index).html(xh++);
			});
		}
	}
	function initEdit(){
		var obj = {};
		obj.id = "";
		obj.xh = xh++;
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
		
		tr += "<td>";
		tr += "<input name='whzq' type='text' class='form-control' value='机务航行仪表 检查审核' readOnly/>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "<input name='whzq' type='text' class='form-control' value='厂家维修手册' />";
		tr += "</td>";
		
		tr += "<td class='text-center' style='vertical-align:middle'>";
		tr += "<input type='radio' name='ischeck_"+obj.xh+"' value='1' />是";
		tr += "<input type='radio' name='ischeck_"+obj.xh+"' value='0' checked/>否";
		tr += "<input type='radio' name='ischeck_"+obj.xh+"' value='' />N/A";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<input name='whgzdh' type='text'  class='form-control' value='维修使用注意航材耗材使用情况' />";
		tr += "</td>";

		tr += "</tr>";
		$("#rotatable2").html(tr);
	}
	
	//向table新增一行
	function addRotatableCol2() {
		// 先移除暂无数据一行
		var len = $("#rotatable2").length;
		if (len == 1) {
			if ($("#rotatable2").find("td").length == 1) {
				$("#rotatable2").empty();
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
		$("#rotatable2").append(addRow(obj));
	}
</script>
</body>
</html>