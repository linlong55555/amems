<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>飞机状态</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
<style>
.td_table{
padding:0px !important;
}
.td_table table{
border:0px !important;
margin-bottom:0px !important;
}
.td_table table tbody{
border:0px !important;
}
.td_table table tbody tr td:last-child,.td_table table thead tr th:last-child{
border-right:0px !important;
}
.td_table table tbody tr td:first-child,.td_table table thead tr th:first-child{
border-left:0px !important;
}

</style>
<script>
var pageParam = '${param.pageParam}';
var id = "${param.id}";
</script>
</head>
<body>
	<input type="hidden" id="dprtcode" value="${user.jgdm}" />
	<input type="hidden" id="zdrid" value="${sessionScope.user.id}" />
	<input type="hidden" id="zddwid" value="${user.bmdm}" />
	<input type="hidden" id="planeloadFjzch" value="${erayFns:escapeStr(planeLoad.fjzch)}" />
	<input type="hidden" id="planeloadId" value="${planeLoad.id}" />
	<input type="hidden" id="planeloadDprtcode" value="${planeLoad.dprtcode}" />
	<div class="page-content" >
		<div class="panel panel-primary"  id='panel_content'>
			<div class="panel-heading " id="NavigationBar">
			</div>
            <div class="panel-body">
			
				<!-- 表格 -->
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" id="fjzt_list_tableDiv" style="overflow-x: auto;">
					<table id="fjzt_list_table" class="table table-thin table-bordered table-set" >
						
						<tbody>
							<tr>
								<th  class='text-right colwidth-10' style='vertical-align:middle;'>飞机注册号</th>
								<td>
								<select class='form-control' id='fjzch' name="fjzch">
							 	</select>
								</td>
								<th class='text-right colwidth-10' style='vertical-align:middle;'>日期</th>
								<td>
									<input type='text' data-date-format="yyyy-mm-dd" id="zxrq"  class='form-control datepicker'
									value="<fmt:formatDate value='${planeLoad.zxrq}' pattern='yyyy-MM-dd' />" />
								</td>
								<th class='text-right colwidth-10' style='vertical-align:middle;'></th>
								<td></td>
							</tr>
							<tr>
								<th  class='text-right colwidth-10' style='vertical-align:middle;'>描述</th>
								<td colspan='5'>
								<textarea class='form-control' id="ms" maxlength="100">${erayFns:escapeStr(planeLoad.ms)}</textarea>
								</td>
							</tr>
							<tr>
								<th colspan='6' style='background:#ececec;cursor:pointer;' id='zxzd_tr_id' onclick='showZxzd("zxzd_tr","zxzd_tr_id")'>飞机震动数据</th>
							</tr>
							<tr class='zxzd_tr'>
							<td colspan='2' class='td_table'>
							<table class='table table-bordered'>
							        <thead><tr><th colspan='6'>主旋翼震动</th></tr></thead>
									<tbody>
										<tr>
										<th style='vertical-align:middle;'>X1</th>
										<td><input type='text' id="zxyzd_x1" class='form-control' value="${erayFns:escapeStr(planeLoad.zxyzd_x1)}" maxlength="10" /></td>
										<th style='vertical-align:middle;'>Y1</th>
										<td><input type='text' id="zxyzd_y1" class='form-control' value="${erayFns:escapeStr(planeLoad.zxyzd_y1)}" maxlength="10" /></td>
										<th style='vertical-align:middle;'>Z1</th>
										<td><input type='text' id="zxyzd_z1" class='form-control' value="${erayFns:escapeStr(planeLoad.zxyzd_z1)}" maxlength="10" /></td>
										</tr>
										<tr>
										<th style='vertical-align:middle;'>X2</th>
										<td><input type='text' id="zxyzd_x2" class='form-control' value="${erayFns:escapeStr(planeLoad.zxyzd_x2)}" maxlength="10" /></td>
										<th style='vertical-align:middle;'>Y2</th>
										<td><input type='text' id="zxyzd_y2" class='form-control' value="${erayFns:escapeStr(planeLoad.zxyzd_y2)}" maxlength="10" /></td>
										<th style='vertical-align:middle;'>Z2</th>
										<td><input type='text' id="zxyzd_z2" class='form-control' value="${erayFns:escapeStr(planeLoad.zxyzd_z2)}" maxlength="10" /></td>
										</tr>
										<tr>
										<th style='vertical-align:middle;'>X3</th>
										<td><input type='text' id="zxyzd_x3" class='form-control' value="${erayFns:escapeStr(planeLoad.zxyzd_x3)}" maxlength="10" /></td>
										<th style='vertical-align:middle;'>Y3</th>
										<td><input type='text' id="zxyzd_y3" class='form-control' value="${erayFns:escapeStr(planeLoad.zxyzd_y3)}" maxlength="10" /></td>
										<th style='vertical-align:middle;'>Z3</th>
										<td><input type='text' id="zxyzd_z3" class='form-control' value="${erayFns:escapeStr(planeLoad.zxyzd_z3)}" maxlength="10" /></td>
										</tr>
									</tbody>
								</table>
								<table class='table table-bordered'>
								<tbody>
								<tr>
								<th class='text-right colwidth-10' style='vertical-align:middle;'>尾桨震动</th>
								<td><input type='text' id="wjzd" class='form-control' value="${erayFns:escapeStr(planeLoad.wjzd)}" maxlength="10" /></td></tr>
								</tbody>
								</table>
							</td>
							
							<td colspan='4' class='td_table'>
							<table class='table table-bordered'>
							        <thead>
							        <tr><th colspan='4' >部件震动</th></tr>
							        <tr style='height:41px;'>								        
								         <th style="width:25%">
												<div class="font-size-12 line-height-18" >部件名称</div>
												<div class="font-size-9 line-height-18" >Parts Name</div>
											 </th>
											 <th style="width:25%">
												 <div class="font-size-12 line-height-18" >属性名</div>
												 <div class="font-size-9 line-height-18" >Type Name</div>
											 </th>
											 <th style="width:25%">
											     <div class="font-size-12 line-height-18" >震动值</div>
												 <div class="font-size-9 line-height-18" >Shake Value</div>
											 </th>
							        </tr>
							        </thead>
									<tbody id='bjzd_list'>
										<tr>
										
										<td>
											<input type='text' id="bjmc1" name="bjmc" class='col-lg-8 form-control text-left' style="width:99%" maxlength="100"/>
											<input type="hidden" id="zjqdid1" value=""/>
											<input type="hidden" id="bjid1" value=""/>
											
										</td>
										<td>
											<input type='text' id="sxm1" class='form-control' maxlength="100"/>
										</td>
										<td>
											<input type='text' id="zdz1" class='form-control' maxlength="10"/>
											<input type="hidden" id="bs018Id1" class='form-control' value="1" />
										</td>
										</tr>
										<tr>
										
										<td>
											<input type='text' id="bjmc2" name="bjmc" class='col-lg-8 form-control text-left' style="width:99%" maxlength="100"/>
											<input type="hidden" id="zjqdid2" value=""/>
											<input type="hidden" id="bjid2" value=""/>											
										</td>										
										<td>
											<input type='text' id="sxm2" class='form-control' maxlength="100"/>
										</td>
										<td>
											<input type='text' id="zdz2" class='form-control' maxlength="10"/>
											<input type='hidden' id="bs018Id2" class='form-control' value="1" />
										</td>
										</tr>
										<tr>
										
										<td>
											<input type='text' id="bjmc3" name="bjmc" class='col-lg-8 form-control text-left' style="width:99%" maxlength="100" />
											<input type="hidden" id="zjqdid3" value=""/>
											<input type="hidden" id="bjid3" value=""/>											
										</td>										
										<td>
											<input type='text' id="sxm3" class='form-control' maxlength="100" />
										</td>
										<td>
											<input type='text' id="zdz3" class='form-control' maxlength="10"/>
											<input type="hidden" id="bs018Id3" class='form-control' value="1" />
										</td>
										</tr>
									</tbody>
								</table>
							</td>
							</tr>
							<tr>
								<th colspan='6' style='background:#ececec;cursor:pointer;' id='zxsj_tr_id' onclick='showZxzd("zxsj_tr","zxsj_tr_id")'>飞机重心数据</th>
							</tr>
							<tr class='zxsj_tr'>
								<th class='text-right colwidth-10' style='vertical-align:middle;'>
									<div class="font-size-12 line-height-18" >构型</div>
									<div class="font-size-9 line-height-18" >Configuration</div>
								</th>
								<td colspan='5'>
								<textarea id="gx" class='form-control' maxlength="100">${erayFns:escapeStr(planeLoad.gx)}</textarea>
								</td>
							</tr>
							<tr class='zxsj_tr'>
								<th class='text-right colwidth-10' style='vertical-align:middle;'>
									<div class="font-size-12 line-height-18" >飞机总量</div>
									<div class="font-size-9 line-height-18" >Plane Amount</div>
								</th>
								<td>
									<input type='text' id="fjzl_sz" class='col-lg-8 form-control text-left' style="width:66%" value="${erayFns:escapeStr(planeLoad.fjzl_sz)}" maxlength="10"/>
									<select class=' form-control ' id='fjzl_dw' name="fjzl_dw" style="width:33%" >
										<option value="KG" <c:if test="${planeLoad.fjzl_dw == 'KG' }">selected="true"</c:if>>KG</option>
										<option value="LB" <c:if test="${planeLoad.fjzl_dw == 'LB' }">selected="true"</c:if>>LB</option>
							 		</select>
								</td>
								<th class='text-right colwidth-10' style='vertical-align:middle;'>
									<div class="font-size-12 line-height-18" >纵向重心</div>
									<div class="font-size-9 line-height-18" >Lengthways Core</div>
								</th>
								<td>
									<input type='text'  id="zxzx_sz"  class='col-lg-8 form-control text-left' style="width:66%" value="${erayFns:escapeStr(planeLoad.zxzx_sz)}" maxlength="10" />
									<select class=' form-control ' id='zxzx_dw' name="zxzx_dw" style="width:33%" >
										<option value="M" <c:if test="${planeLoad.zxzx_dw == 'M' }">selected="true"</c:if>>M</option>
										<option value="IN" <c:if test="${planeLoad.zxzx_dw == 'IN' }">selected="true"</c:if>>IN</option>
							 		</select>
								</td>
								<th class='text-right colwidth-10' style='vertical-align:middle;'>
									<div class="font-size-12 line-height-18" >横向重心</div>
									<div class="font-size-9 line-height-18" >Crosswise Core</div>
								</th>
								<td>
									<input type='text' id="hxzx_sz" class='col-lg-8 form-control text-left' style="width:66%" value="${erayFns:escapeStr(planeLoad.hxzx_sz)}" maxlength="10" />
									<select class=' form-control ' id='hxzx_dw' name="hxzx_dw" style="width:33%" >
										<option value="M" <c:if test="${planeLoad.hxzx_dw == M }">selected="true"</c:if>>M</option>
										<option value="IN" <c:if test="${planeLoad.hxzx_dw == IN }">selected="true"</c:if>>IN</option>
							 		</select>
								</td>
							</tr>
							<tr class='zxsj_tr'>
								<th class='text-right colwidth-10' style='vertical-align:middle;'>
									<div class="font-size-12 line-height-18" >备注</div>
									<div class="font-size-9 line-height-18" >Remark</div>
								</th>
								<td colspan='5'>
								<textarea id="bz" class='form-control' maxlength="100">${erayFns:escapeStr(planeLoad.bz)}</textarea>
								</td>
							</tr>
							<tr class='zxsj_tr' id="fj">
								<th class='text-right colwidth-10' style='vertical-align:middle;'>
								<div class="font-size-12 line-height-18" >附件</div>
								<div class="font-size-9 line-height-18" >Attachments</div>
							</th>
							<td colspan='5' >
							<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 padding-top-10" >
							<div class="col-xs-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0 " style='padding-left:0px !important;' >
							
							</div>
							</div>
								<table
											class="table table-thin table-bordered table-striped table-hover text-center table-set">
											<thead>
												<tr>													
													<th class="colwidth-20">
														<div class="font-size-12 line-height-18">文件说明</div>
														<div class="font-size-9 line-height-18">File Desc</div>
													</th>
													<th class="colwidth-10">
														<div class="font-size-12 line-height-18">文件大小</div>
														<div class="font-size-9 line-height-18">File Size</div>
													</th>
													<th class="colwidth-13"><div class="font-size-12 line-height-18">上传人</div>
														<div class="font-size-9 line-height-18">Uploader</div></th>
													<th class="colwidth-13"><div class="font-size-12 line-height-18">上传时间</div>
														<div class="font-size-9 line-height-18">Upload Time</div></th>
												</tr>
											</thead>
											<tbody id="filelist">


											</tbody>
										</table>
							</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="pull-right ">										
										<button type="button"
												class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()">
												<div class="font-size-12">返回</div>
												<div class="font-size-9">Back</div>
										</button>
									</div>
			</div>
		</div>
	</div>
	
	<!-------添加模态框 start-------->
		<div class="modal fade" id="partModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:70%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">部件列表</div>
							<div class="font-size-9 ">User List</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
							<div class="col-lg-12 col-xs-12">
									<div
										class=" pull-right padding-left-0 padding-right-0 margin-top-10">
										<div
											class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
											<input type="text" class="form-control "placeholder="ATA章节号/部件号/序列号/中英文/厂家件号/批次号" id="part_search" /> 							
										</div>
									 <div class=" pull-right padding-left-5 padding-right-0 "> 
									<button name="keyCodeSearch" type="button" onclick="partModal.search()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										style="float: left; margin-left: 10px;">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
									</button>
									</div>
									</div>
									<div class="clearfix"></div>
								<!-- start:table -->
								<div class="margin-top-10 ">
									<div class="overflow-auto">
										<table
											class="table table-bordered table-striped table-hover text-center"
											style="">
											<thead>
												<tr>
													<th width="50px">
														<div class="font-size-12 notwrap">选择</div>
														<div class="font-size-9 notwrap">Choice</div>
													</th>
													<th>
													  <div class="important">
														<div class="font-size-12 notwrap">ATA章节号</div>
														<div class="font-size-9 notwrap">ATA</div>
														</div>
													</th>
													<th>
														<div class="important">
															<div class="font-size-12 notwrap">部件号</div>
															<div class="font-size-9 notwrap">P/N</div>
													    </div>
													</th>
													<th>
													  <div class="important">
													 	<div class="font-size-12 notwrap">序列号</div>
														<div class="font-size-9 notwrap">S/N</div>
													  </div>	
													</th>
													<th>
													   <div class="important">
														<div class="font-size-12 notwrap">中文名称</div>
														<div class="font-size-9 notwrap">Chinese Name</div>
														</div>
													</th>
													<th>
													  <div class="important">
														<div class="font-size-12 notwrap">英文名称</div>
														<div class="font-size-9 notwrap">English Name</div>
													  </div>	
													</th>
													<th>
													  <div class="important">
														<div class="font-size-12 notwrap">厂家件号</div>
														<div class="font-size-9 notwrap">MP/N</div>
													  </div>	
													</th>
													<th>
													  <div class="important">
														<div class="font-size-12 notwrap">批次号</div>
														<div class="font-size-9 notwrap">B/N</div>
													  </div>	
													</th>
													<th>
														<div class="font-size-12 notwrap">位置</div>
														<div class="font-size-9 notwrap">Position</div>
													</th>
										 		 </tr>
											</thead>
											<tbody id="partlist">

											</tbody>
										</table>
									</div>
									<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="part_pagination"></div>
								</div>
								<!-- end:table -->
								<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="partModal.setUser()"
										class="btn btn-primary padding-top-1 padding-bottom-1">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>

								</div>
								<div class="clearfix"></div>
							</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/planeload/planeload_view.js"></script>

	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script>
	function showZxzd(obj,id){
		
		if($("."+obj).css("display")=="none"){
			$("."+obj).css("display","table-row");
			$("#"+id).find("i").removeClass("icon-chevron-left");
			$("#"+id).find("i").addClass("icon-chevron-down");
			
		}else{
			$("."+obj).css("display","none");	
			$("#"+id).find("i").removeClass("icon-chevron-down");
			$("#"+id).find("i").addClass("icon-chevron-left");
		}
		
	}
	
	</script>
</body>
</html>