<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common_new.jsp"%>

<script>
var pageParam = '${param.pageParam}';
</script>

<title>新增故障保留单</title>
<style>

.ajax-file-upload_ext {
    margin-left: 10px;
}
.ajax-file-upload_ext div{ }

</style>

</head>
<body>
<input id="newFileName" type="hidden"/>
<input id="relativePath" type="hidden"/>
<input id="fileName" type="hidden"/>
 
	<div class="page-content " >
		<!-------导航Start--->
		
		<!-------导航End--->
		<!-- 查看工单Start -->
		<div class="panel panel-primary">
			<div class="panel-heading  padding-top-3 padding-bottom-1">
			<div id="NavigationBar"></div>
			</div>

			<div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
				<div id="form">
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group" >
							<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18"><span style="color: red">*</span>故障保留单号</div>
								<div class="font-size-9 line-height-18">Retain No.</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="gzbldh" name="gzbldh" placeholder='长度最大为20'   maxlength="20" class="form-control"  >
								<input type="hidden" value="1"/>
							</div>
							
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group " >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>飞机注册号</div>
							<div class="font-size-9 line-height-18">A/C REG</div>
						</label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							<select id="fjzch" class="form-control " name="fjzch">
								 
							</select>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>飞行记录单</div>
							<div class="font-size-9 line-height-18">Flight Record No.</div>
						</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<select id="fxjldh" class="form-control " name="fxjldh">
							</select> 
							<input type="hidden" id="jlbym" name="jlbym"/>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group " >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div></label>
						 
						<div class=" padding-left-8 padding-right-0 input-group">
							<input type="text" id="zjhandname" name="zjhandname" class="form-control"  readonly="true" /> 
							<input type="hidden" id="zjh" />
							<span class="input-group-btn">
								<button onclick="javascript:openChapterWin();" class="btn btn-primary">
									<i class="icon-search" ></i>
								</button>
							</span>	
						</div>
					</div>
					<div class="clearfix"></div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>执行M程序</div>
							<div class="font-size-9 line-height-18">Execute M Program</div>
						</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							 <input type="checkbox" id='isM' name='isM'/>
						</div>
					</div>
					<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group" >
						<label class="col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>MEL类型</div>
							<div class="font-size-9 line-height-18">MEL Type</div>
						</label>
						<div class="col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_A' name='mel' value='A' checked="checked"/>A</label>
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_B' name='mel' value='B'/>B</label>
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_C' name='mel' value='C'/>C</label>
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_D' name='mel' value='D'/>D</label>
								&nbsp;<label style=' font-weight:normal'><input type="radio" id='mel_NA' name='mel' value='NA'/>NA</label>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18"><span style="color: red">*</span>故障描述/措施采取</div>
							<div class="font-size-9 line-height-18">Fault Desc</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea class="form-control" id="gzms" style="height:60px" name="gzms" placeholder='长度最大为300'   maxlength="300"></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18"><span style="color: red">*</span>保留内容</div>
							<div class="font-size-9 line-height-18">Contents</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea class="form-control" id="scNr" name="scNr" placeholder='长度最大为300'   maxlength="300"></textarea>
						</div>	
					</div>
					
					<div class="clearfix"></div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18"><span style="color: red">*</span>保留依据</div>
							<div class="font-size-9 line-height-18">Basis</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea class="form-control" id="blyj" name="blyj" placeholder='长度最大为300'   maxlength="300"></textarea>
						</div>	
					</div>
					
					<div class="clearfix"></div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>保留人</div>
							<div class="font-size-9 line-height-18">First Retain</div>
						</label>
						<div class="padding-left-8 padding-right-0 input-group">
							<input type="text" class="form-control" name="scBlrmc" id="scBlrmc"  readonly />
							<input type="hidden" id="scBlrid" />
							<span class="input-group-btn">
								<button onclick="javascript:scBlr.selectUser1();" class="btn btn-primary">
									<i class="icon-search" ></i>
								</button>
							</span>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>执照号</div>
							<div class="font-size-9 line-height-18">Licence</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
							 <input type="text" id="scZzh" name="scZzh"  maxlength="20" placeholder='长度最大为20' class="form-control">
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>批准人</div>
							<div class="font-size-9 line-height-18">Approved</div>
						</label>
						<div class="padding-left-8 padding-right-0 input-group">
							<input type="text" class="form-control" name="scPzrmc" id="scPzrmc"  readonly />
							<input type="hidden" id="scPzrid" />
							<span class="input-group-btn">
								<button onclick="javascript:scPzr.selectUser1();" class="btn btn-primary">
									<i class="icon-search" ></i>
								</button>
							</span>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18"><span style="color: red">*</span>申请日期</div>
								<div class="font-size-9 line-height-18">App Date</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0" >
								<input type="text" id="scSqrq"  name="scSqrq" class="form-control datepicker " style="width: 100%"  data-date-format="yyyy-mm-dd"  placeholder="请选择日期"   />
							</div>
						</div>
					<div class="clearfix">
						
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							 <label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18"><span style="color: red">*</span>到期日期</div>
								<div class="font-size-9 line-height-18">Expire</div>
								</label>
							 <div class="col-xs-8 padding-left-8 padding-right-0" >
								<input type="text" id="scDqrq"  name="scDqrq" class="form-control datepicker " style="width: 100%"  data-date-format="yyyy-mm-dd"  placeholder="请选择日期"   />
							</div>
						</div>
					   <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						 <label class="col-xs-4 text-right padding-left-0 padding-right-0">
						        <div class="font-size-12 line-height-18">文件说明</div>
								<div class="font-size-9 line-height-18">File Desc</div>
						 </label>
						 <div class="col-xs-6 padding-left-8 padding-right-0" >
							<input type="text" id="wbwjm" name="wbwjm" placeholder='' class="form-control"  >
						 </div>
						 <div id="fileuploader" class="col-xs-2 "  style="margin-left: 0;padding-left:0; display:block; "></div> 
					</div>
					</div>
					 <div class="clearfix">
					</div>
						
						 
					</div>
					<div class="clearfix">
					</div>
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow: auto;" >
						<table class="table table-thin table-bordered table-striped table-hover text-center" style="min-width: 900px">
							<thead>
								<tr>
									<th style="width:110px;"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th >
									<div class="font-size-12 line-height-18">文件说明</div>
										<div class="font-size-9 line-height-18">File desc</div>
									</th>
									
									<th >
									<div class="font-size-12 line-height-18">文件大小</div>
										<div class="font-size-9 line-height-18">File Size</div>
									</th>
									
									
									<th  ><div class="font-size-12 line-height-18">上传人</div>
										<div class="font-size-9 line-height-18">Operator</div></th>
									<th  ><div class="font-size-12 line-height-18">上传时间</div>
										<div class="font-size-9 line-height-18">Operate time</div></th>					
								</tr>
							</thead>
							    <tbody id="filelist">
									 
									 
								</tbody>
						</table>
					</div>
					
					<div class="clearfix">
					</div>
					 
					<br/>
                   </div>
				</div>
				  <div class="text-right">
                        <button onclick="save()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10 checkPermission"
                          permissioncode='flightdata:legacytrouble:add'>
                          <div class="font-size-12">保存</div>
						  <div class="font-size-9">Save</div>
						</button>
						<button onclick="committed()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10 " 
						 >
						<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
						</button>
                     	<button id="revert" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" ><div
										class="font-size-12">返回</div>
									<div class="font-size-9">Back</div></button>
                     </div>
				<div class="clearfix"></div>
				
			</div>
		</div>
	</div>
	</div>
	 
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	 
<script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/legacytrouble/legacytrouble_add.js"></script>
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
</body>
</html>