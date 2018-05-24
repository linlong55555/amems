<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增巡检记录单</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
 <style type="text/css">
/* .multiselect{
width:678px;
overflow:hidden;
}
.multiselect-container.dropdown-menu{
width:678px;
} */

</style> 
</head>

<body>
	<body class="page-header-fixed">
		<!-------导航Start--->
		<input type="hidden" id="zzjgid" name="zzjgid" value=${user.jgdm} />
		<!-- BEGIN CONTENT -->
<script type="text/javascript">
var pageParam = '${param.pageParam}';
$(document).ready(function(){
	//导航
	Navigation(menuCode,"新增巡检记录单","Add");
});
</script>
		<div class="page-content ">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			<form id="form">
			
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">巡视人</div>
								<div class="font-size-9 line-height-18">Patrol man</div>
							</label>
							<div class=" col-xs-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<div class='input-group'>
								<input class="form-control date-picker" id="xsrmc" name="xsrmc" type="text" onchange="usernameChange()" />
								<input class="form-control date-picker" id="xsrmcOld" name="xsrmcOld" type="hidden" />
								<input class="form-control date-picker" id="xsrid" name="xsrid" type="hidden"/>
									<span class='input-group-btn'>
									  <button class="btn btn-primary form-control" data-toggle="modal" onclick="openUserList();" type="button">
										<i class="icon-search cursor-pointer"></i>
									</button>
									</span>
							    </div>
							</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>巡视日期</div>
							<div class="font-size-9 line-height-18">Patrol date</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker"  id="xsrq" name="xsrq" data-date-format="yyyy-mm-dd" type="text"/>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>开始时间</div>
							<div class="font-size-9 line-height-18">Start time</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker"  id="kssj" name="kssj" type="text" maxlength="5" />
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>结束时间</div>
							<div class="font-size-9 line-height-18">End time</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker"  id="jssj" name="jssj" type="text" maxlength="5" />
						</div>
					</div>
					<div class="clearfix"></div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">是否夜航巡检</div>
							<div class="font-size-9 line-height-18">Night patrol</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select id="isYhxs" class="form-control "  name="isYhxs">
								<option value="1">是</option>
								<option value="0">否</option>
								</select>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 form-group ">
									<div class="font-size-12 line-height-18">
										巡视记录明细</div>
									<div class="font-size-9 line-height-18">Patrol records</div>
								</label>

								<div class="col-lg-11 col-sm-10 col-xs-4 padding-left-8 padding-right-0 form-group" >
									<table class="table table-thin table-bordered table-striped table-hover text-center" >
											<thead>
											<tr>
											<th colspan="3" width="30%"><div class="font-size-12 line-height-18" >检查项目</div><div class="font-size-9 line-height-18" >Inspection items</div></th>
											<th width="40%"><div class="font-size-12 line-height-18" >备注 </div><div class="font-size-9 line-height-18" >Remarks</div></th>
											<th width="15%"><div class="font-size-12 line-height-18" >文件 </div><div class="font-size-9 line-height-18" >File</div></th>
											<th width="5%"><div class="font-size-12 line-height-18" >操作</div><div class="font-size-9 line-height-18" >Operation</div></th>
											</tr> 
							         		 </thead>
											<tbody id="detailList">
											<tr> 
											<td rowspan="5">机</br>坪</br>、</br>跑</br>道</br>和</br>滑</br>行</br>道</br></td>
											<td width="12%" class='text-right' >道面表面情况</td>
											<td width="11%" class='text-left'>
												<select id="detail0101" class="form-control "  name="detail0101">
												<option value="1">无剥落物</option>
												<option value="2">有剥落物</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0101" name="detailBz0101" maxlength="300" rows="1"></textarea>
											</td>
											<td name='detailPxfj0101' class='text-left'></td>
											<td style='vertical-align:middle' >
												<div class="uploaderDiv" class="col-lg-2 col-sm-2 col-xs-12"  style="margin-left: 5px ;padding-left: 0"></div>
											</td>
											</tr> 
											
											<tr> 
											<td class='text-right'>嵌缝料情况</td>
											<td class='text-left'>
												<select id="detail0102" class="form-control "  name="detail0102">
												<option value="1">无剥落物</option>
												<option value="2">有剥落物</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0102" name="detailBz0102" maxlength="300" rows="1"></textarea>
											</td>
											<td name='detailPxfj0102' class='text-left'></td>
											<td style='vertical-align:middle' >
												<div class="uploaderDiv" class="col-lg-2 col-sm-2 col-xs-12"  style="margin-left: 5px ;padding-left: 0"></div>
											</td>
											</tr>
											
											<tr> 
											<td class='text-right'>标志线、标识情况</td>
											<td class='text-left'>
												<select id="detail0103" class="form-control "  name="detail0103">
												<option value="1">标志清晰</option>
												<option value="2">部分模糊</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0103" name="detailBz0103" maxlength="300" rows="1"></textarea>
											</td>
											<td name='detailPxfj0103' class='text-left'></td>
											<td style='vertical-align:middle' >
												<div class="uploaderDiv" class="col-lg-2 col-sm-2 col-xs-12"  style="margin-left: 5px ;padding-left: 0"></div>
											</td>
											</tr>
											
											<tr> 
											<td class='text-right'>污染物情况</td>
											<td class='text-left'>
												<select id="detail0104" class="form-control "  name="detail0104">
												<option value="1">道面清洁</option>
												<option value="2">部分模糊</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0104" name="detailBz0104" maxlength="300" rows="1"></textarea>
											</td>
											<td name='detailPxfj0104' class='text-left'></td>
											<td style='vertical-align:middle' >
												<div class="uploaderDiv" class="col-lg-2 col-sm-2 col-xs-12"  style="margin-left: 5px ;padding-left: 0"></div>
											</td>
											</tr>
											
											<tr> 
											<td class='text-right'>FOD情况</td>
											<td class='text-left'>
												<select id="detail0105" class="form-control "  name="detail0105">
												<option value="1">无</option>
												<option value="2">有</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0105" name="detailBz0105" maxlength="300" rows="1"></textarea>
											</td>
											<td name='detailPxfj0105' class='text-left'></td>
											<td style='vertical-align:middle'>
												<div class="uploaderDiv" class="col-lg-2 col-sm-2 col-xs-12"  style="margin-left: 5px ;padding-left: 0"></div>
											</td>
											</tr >
											
											<tr> 
											<td >土质地带</td>
											<td class='text-right'>目视平整度</td>
											<td class='text-left'>
												<select id="detail0201" class="form-control "  name="detail0201">
												<option value="1">平整</option>
												<option value="2">异常</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0201" name="detailBz0201" maxlength="300" rows="1"></textarea>
											</td>
											<td name='detailPxfj0201' class='text-left'></td>
											<td style='vertical-align:middle' >
												<div class="uploaderDiv" class="col-lg-2 col-sm-2 col-xs-12"  style="margin-left: 5px ;padding-left: 0"></div>
											</td>
											</tr> 
											
											<tr> 
											<td rowspan="2">场内障碍物</td>
											<td class='text-right'>已有障碍物标识和灯光</td>
											<td class='text-left'>
												<select id="detail0301" class="form-control "  name="detail0301">
												<option value="1">正常</option>
												<option value="2">异常</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0301" name="detailBz0301" maxlength="300" rows="1"></textarea>
											</td>
											<td name='detailPxfj0301' class='text-left'></td>
											<td style='vertical-align:middle' >
												<div class="uploaderDiv" class="col-lg-2 col-sm-2 col-xs-12"  style="margin-left: 5px ;padding-left: 0"></div>
											</td>
											</tr> 
											
											<tr> 
											<td class='text-right'>疑似新增超高物</td>
											<td class='text-left'>
												<select id="detail0302" class="form-control "  name="detail0302">
												<option value="1">无</option>
												<option value="2">有</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0302" name="detailBz0302" maxlength="300" rows="1"></textarea>
											</td>
											<td name='detailPxfj0302' class='text-left'></td>
											<td style='vertical-align:middle' >
												<div class="uploaderDiv" class="col-lg-2 col-sm-2 col-xs-12"  style="margin-left: 5px ;padding-left: 0"></div>
											</td>
											</tr> 
											
											<tr> 
											<td>场外障碍物</td>
											<td class='text-right'>疑似新增超高物</td>
											<td class='text-left'>
												<select id="detail0401" class="form-control "  name="detail0401">
												<option value="1">无</option>
												<option value="2">有</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0401" name="detailBz0401" maxlength="300" rows="1"></textarea>
											</td>
											<td name='detailPxfj0401' class='text-left'></td>
											<td style='vertical-align:middle' >
												<div class="uploaderDiv" class="col-lg-2 col-sm-2 col-xs-12"  style="margin-left: 5px ;padding-left: 0"></div>
											</td>
											</tr> 
											
											<tr> 
											<td>机场围界</td>
											<td class='text-right'>目视完整度</td>
											<td class='text-left'>
												<select id="detail0501" class="form-control "  name="detail0501">
												<option value="1">完整</option>
												<option value="2">异常</option>
												</select>
											</td>
											<td  class='text-center' class='text-left'>
											<textarea class="form-control" id="detailBz0501" name="detailBz0501" maxlength="300" rows="1"></textarea>
											</td>
											<td name='detailPxfj0501'></td>
											<td style='vertical-align:middle' >
												<div class="uploaderDiv" class="col-lg-2 col-sm-2 col-xs-12"  style="margin-left: 5px ;padding-left: 0"></div>
											</td>
											</tr> 
											
											<tr> 
											<td>排水渠</td>
											<td class='text-right'>是否畅通</td>
											<td class='text-left'>
												<select id="detail0601" class="form-control "  name="detail0601">
												<option value="1">是</option>
												<option value="2">否</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0601" name="detailBz0601" maxlength="300" rows="1"></textarea>
											</td>
											<td name='detailPxfj0601' class='text-left'></td>
											<td style='vertical-align:middle' width='8%'>
												<div class="uploaderDiv" class="col-lg-2 col-sm-2 col-xs-12"  style="margin-left: 5px ;padding-left: 0"></div>
											</td>
											</tr> 
											
											<tr> 
											<td>风向带</td>
											<td class='text-right'>是否正常</td>
											<td class='text-left'>
												<select id="detail0701" class="form-control "  name="detail0701">
												<option value="1">是</option>
												<option value="2">否</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0701" name="detailBz0701" maxlength="300" rows="1"></textarea>
											</td>
											<td name='detailPxfj0701' class='text-left'></td>
											<td style='vertical-align:middle' width='8%'>
												<div class="uploaderDiv" class="col-lg-2 col-sm-2 col-xs-12"  style="margin-left: 5px ;padding-left: 0"></div>
											</td>
											</tr> 
											
											<tr> 
											<td>灯光</td>
											<td class='text-right'>目视有无损坏</td>
											<td class='text-left'>
												<select id="detail0801" class="form-control "  name="detail0801">
												<option value="1">正常</option>
												<option value="2">异常</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0801" name="detailBz0801" maxlength="300" rows="1"></textarea>
											</td>
											<td name='detailPxfj0801' class='text-left'></td>
											<td style='vertical-align:middle' width='8%'>
												<div class="uploaderDiv" class="col-lg-2 col-sm-2 col-xs-12"  style="margin-left: 5px ;padding-left: 0"></div>
											</td>
											</tr> 
							         		 </thead>
											
								</table>
								</div>
							</div>
				
                   	<div class="clearfix"></div>
				 <div class=" pull-right margin-bottom-10 margin-top-10">
                       <a href="javascript:void()" data-toggle="modal"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left"
						onclick="submit()"   style="float: left; margin-left: 10px;"><div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div></a>
                      &nbsp;
                     	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()" type="button"><div
										class="font-size-12">返回</div>
						<div class="font-size-9">Back</div></button>
                 </div>
				</form>
			</div>
		</div>
		<input id="departmentId"  style="display: none;" /> 
	
	<%@ include file="/WEB-INF/views/alert.jsp"%>
	
	<!-- 	选择用户的模态框 -->
	<div class="modal fade" id="alertModalUser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 40%;height:50%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="FixChapterBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">选择用户</div>
						<div class="font-size-9 ">Choice</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12">
		            	
			         		<div class="text-right margin-top-10">
			         			<div style="padding-left:0!important;" class="pull-right">   
									<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchUser()">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                    </div> 
								<div style="padding-right:9px!important;" class="col-xs-6 pull-right">
									<input type="text" placeholder='用户名称' id="keyword_user_search" class="form-control " />
								</div>
							</div>
							
							<div class="clearfix"></div>
						    
							<!-- start:table -->
							<div class="margin-top-10 " style="overflow-y:auto;max-height:400px;">
								<table class="table table-bordered table-striped table-hover text-left table-set">
									<thead>
								   		<tr>
											<th style="width:15%">
												<div class="font-size-12 notwrap">选择</div>
												<div class="font-size-9 notwrap">Choice</div>
											</th>
											<th style="width:40%">
											<div class="important">
												<div class="font-size-12 notwrap">用户名称</div>
												<div class="font-size-9 notwrap">User Name</div>
											</div>	
											</th>
											<th style="width:45%">
												<div class="font-size-12 notwrap">机构部门</div>
												<div class="font-size-9 notwrap">Deprtment</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="userList">
									
									</tbody>
								</table>
								<!-- <div class=" col-xs-12  text-center " style="margin-top: 0px; margin-bottom: 0px;" id="fixChapterPagination"></div> -->
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" onclick="appendUser()"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									data-dismiss="modal">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" onclick="clearUser()" id="userModal_btn_clear"
										class="btn btn-primary padding-top-1 padding-bottom-1">
										<div class="font-size-12">清空</div>
										<div class="font-size-9">Clear</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
								
			                </div>
			     			<div class="clearfix"></div>
						</div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
</div>
	<!------选择用户 End-------->
	
	
	
	<script src="${ctx}/static/js/workdetail.js"></script>
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
	<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/airportensure/routinginspectionrecord/routinginspectionrecord_add.js"></script>
	
</body>
</html>