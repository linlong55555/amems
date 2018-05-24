<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>岗位授权申请</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

</head>
<body >
	<div class="page-content" >
		<div class="panel panel-primary"  id='panel_content'>
			<div class="panel-heading " id="NavigationBar">
			</div>
            <div class="panel-body">
				<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">类别</div>
							<div class="font-size-9 line-height-18">Type</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='jd' disabled>
								    <option value='0'>内部</option>
									<option value='1'>外部</option>
								</select>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">姓名</div>
							<div class="font-size-9 line-height-18">Name</div></label>
							 <div class="input-group  col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					            <input type="text" class="form-control" disabled>
					            <span class="input-group-addon" style='border-left-top-radius:0px;border-left-bottom-radius:0px;'><i class='icon-plus'></i></span>
					        </div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						    <div class="font-size-12 line-height-18">性别</div>
							<div class="font-size-9 line-height-18">Sex</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<label><input type='radio' name='sex' checked disabled/>&nbsp;&nbsp;男</label>&nbsp;&nbsp;
								<label><input type='radio' name='sex' disabled/>&nbsp;&nbsp;女</label>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">工号</div>
							<div class="font-size-9 line-height-18">Employee No.</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control' disabled/>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">处室</div>
							<div class="font-size-9 line-height-18">Office</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control' disabled/>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">出生年月</div>
							<div class="font-size-9 line-height-18">Date</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control' disabled/>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">参加工作日期</div>
							<div class="font-size-9 line-height-18">Date</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control' disabled/>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">加入公司日期</div>
							<div class="font-size-9 line-height-18">Date</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control' disabled/>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">维修人员执照</div>
							<div class="font-size-9 line-height-18">License</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control' disabled/>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">专业</div>
							<div class="font-size-9 line-height-18">Professional</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control' disabled/>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">有效期</div>
							<div class="font-size-9 line-height-18">Validity</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control' disabled/>
						</div>
					</div>
					<div  class='col-sm-12' style='border-bottom: 1px solid #ccc;margin-bottom:10px;'>
					 </div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">申请类型</div>
							<div class="font-size-9 line-height-18">Type</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<label><input type='radio' name='type' checked disabled/>&nbsp;&nbsp;初次</label>&nbsp;&nbsp;
								<label><input type='radio' name='type' disabled/>&nbsp;&nbsp;延期</label>&nbsp;&nbsp;
								<label><input type='radio' name='type' disabled/>&nbsp;&nbsp;增加</label>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">申请授权岗位</div>
							<div class="font-size-9 line-height-18">Authorized</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='sqgw' multiple="multiple" disabled>
								    <option>项目1</option>
								    <option>项目2</option>
								    <option>项目3</option>
								</select>&nbsp;<label class='color-red'>选择项目，多选</label>
						    </div>
						</div>
					</div>
					<div class='col-sm-12 margin-bottom-10 col-sm-12 col-xs-12 padding-left-0 padding-right-0'>
					<label  class="col-sm-1 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">申请内容</div>
							<div class="font-size-9 line-height-18">Content</div></label>
							<div class=" col-xs-11 padding-left-8 padding-right-0">
								<textarea  class='form-control' disabled></textarea>
						</div>
					</div>
					<div class='margin-bottom-10 col-sm-12 col-xs-12 padding-left-0 padding-right-0'>
					<label  class="col-sm-1 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div></label>
							<div class=" col-xs-11 padding-left-8 padding-right-0">
								<textarea  class='form-control' disabled></textarea>
						</div>
					</div>
					<div  class='col-sm-12 margin-bottom-10' style='border-bottom: 1px solid #ccc;padding-bottom:5px;'>
					   <label style='font-size:16px;font-weight:400;'>提交材料</label>
					   
					</div>
					<div class='col-sm-6 margin-bottom-10'>
					<input type='checkbox' class='pull-left' style='margin-top:8px;margin-right:10px;' disabled/>
					<label  class="padding-left-0 padding-right-0 pull-left" style='width:110px;'><div
								class="font-size-12 line-height-18">学历证书复印件</div>
							<div class="font-size-9 line-height-18">Diploma certificate</div></label>
							<div class='col-sm-7' style='border:1px solid #d5d5d5;height:35px;line-height:35px;margin-left:10px;border-radius:5px;'>
							   <a href="#">高中毕业证.jpg</a>,<a href="#">英语六级证书.jpg</a>
							</div>
							<button class='btn btn-primary' style='margin-left:8px;' disabled>	
							     上传
							</button> 
							<div class='clearfix'></div>		
					</div>
					<div class='col-sm-6 margin-bottom-10'>
					<input type='checkbox' class='pull-left' style='margin-top:8px;margin-right:10px;' disabled/>
					<label  class=" padding-left-0 padding-right-0 pull-left" style='width:110px;'><div
								class="font-size-12 line-height-18">基础执照复印件</div>
							<div class="font-size-9 line-height-18">Basic license</div></label>
							<div class='col-sm-7' style='border:1px solid #d5d5d5;height:35px;line-height:35px;margin-left:10px;border-radius:5px;'>
							   
							</div>
							<button class='btn btn-primary' style='margin-left:8px;' disabled>	
							     上传
							</button>
							<div class='clearfix'></div>	 		
					</div>
					<div class='col-sm-6 margin-bottom-10'>
					<input type='checkbox' class='pull-left' style='margin-top:8px;margin-right:10px;' disabled/>
					<label  class="padding-left-0 padding-right-0 pull-left" style='width:110px;'><div
								class="font-size-12 line-height-18">其他证书</div>
							<div class="font-size-9 line-height-18">Other certificates</div></label>
							<div class='col-sm-7' style='border:1px solid #d5d5d5;height:35px;line-height:35px;margin-left:10px;border-radius:5px;'>
							 
							</div>
							<button class='btn btn-primary' style='margin-left:8px;' disabled>	
							     上传
							</button> 	
							<div class='clearfix'></div>		
					</div>
					<div class='col-sm-6 margin-bottom-10'>
					<input type='checkbox' class='pull-left' style='margin-top:8px;margin-right:10px;' disabled/>
					<label  class="padding-left-0 padding-right-0 pull-left" style='width:110px;'><div
								class="font-size-12 line-height-18">机型培训证书复印件</div>
							<div class="font-size-9 line-height-18">Model training</div></label>
							<div class='col-sm-7' style='border:1px solid #d5d5d5;height:35px;line-height:35px;margin-left:10px;border-radius:5px;'>
							  
							</div>
							<button class='btn btn-primary' style='margin-left:8px;' disabled>	
							     上传
							</button> 
							<div class='clearfix'></div>			
					</div>
					<div class='col-sm-6 margin-bottom-10'>
					<input type='checkbox' class='pull-left' style='margin-top:8px;margin-right:10px;' disabled/>
					<label  class="padding-left-0 padding-right-0 pull-left" style='width:110px;'><div
								class="font-size-12 line-height-18">公司培训记录</div>
							<div class="font-size-9 line-height-18">Company training</div></label>
							<div class='col-sm-7' style='border:1px solid #d5d5d5;height:35px;line-height:35px;margin-left:10px;border-radius:5px;'>
							 
							</div>
							<button class='btn btn-primary' style='margin-left:8px;' disabled>	
							     上传
							</button>
							<div class='clearfix'></div>	 		
					</div>
					<div class='col-sm-6 margin-bottom-10'>
					<input type='checkbox' class='pull-left' style='margin-top:8px;margin-right:10px;' disabled/>
					<label  class="padding-left-0 padding-right-0 pull-left" style='width:110px;'><div
								class="font-size-12 line-height-18">其他培训证书</div>
							<div class="font-size-9 line-height-18">Other training</div></label>
							<div class='col-sm-7' style='border:1px solid #d5d5d5;height:35px;line-height:35px;margin-left:10px;border-radius:5px;'>
							
							</div>
							<button class='btn btn-primary' style='margin-left:8px;' disabled>	
							     上传
							</button> 
							<div class='clearfix'></div>			
					</div>
					
					<div  class='col-sm-12 margin-bottom-10' style='border-bottom: 1px solid #ccc;padding-bottom:5px;'>
					   <label style='font-size:16px;font-weight:400;'>工作经历</label>
					  
					</div>
					<div  class='col-sm-12 margin-top-10 padding-left-0 padding-right-0'>
					<table class='table table-bordered'>
					<thead>
					<tr>
					<th width='50'><button onclick='addWork("worklist")' class='btn btn-primary' style='margin-left:10px;padding-top:4px;padding-right:8px;padding-bottom:4px;padding-left:8px;' disabled><i class='icon-plus cursor-pointer'></i></button></th>
					<th>起始</th><th>结束</th><th>工作经历</th></tr>
					</thead>
					<tbody id='worklist'>
						<tr>
						<td class='text-center'>
						<button class="btn btn-primary" onclick="del_tr(this)" style='margin-left:10px;padding-top:4px;padding-right:8px;padding-bottom:4px;padding-left:8px;' disabled><i class="fa glyphicon glyphicon-minus cursor-pointer"></i></button></td>
						<td width='200'><input type='text' class='form-control' style='width:200px;' disabled/></td>
						<td width='200'><input type='text' class='form-control' style='width:200px;' disabled/></td>
						<td><textarea class='form-control' style='height:35px;' disabled></textarea></td></tr>
					</tbody>
					</table>
					</div>
					<div  class='col-sm-12 margin-bottom-10' style='border-bottom: 1px solid #ccc;padding-bottom:5px;'>
					   <label style='font-size:16px;font-weight:400;'>培训经历</label>
					   
					</div>
					<div  class='col-sm-12 margin-bottom-10 padding-left-0 padding-right-0'>
					<table class='table table-bordered table-striped table-hover'>
					<thead>
					<tr><th width='50'><button class='btn btn-primary' onclick='addWork("trainlist")' style='margin-left:10px;padding-top:4px;padding-right:8px;padding-bottom:4px;padding-left:8px;' disabled><i class='icon-plus cursor-pointer'></i></button></th><th>起始</th><th>结束</th><th>培训经历</th></tr>
					</thead>
					<tbody id='trainlist'>
						<tr>
						<td class='text-center'>
						<button class="btn btn-primary" onclick="del_tr(this)" style='margin-left:10px;padding-top:4px;padding-right:8px;padding-bottom:4px;padding-left:8px;' disabled><i class="fa glyphicon glyphicon-minus cursor-pointer"></i></button></td>
						<td width='200'><input type='text' class='form-control' style='width:200px;' disabled/></td>
						<td width='200'><input type='text' class='form-control' style='width:200px;' disabled/></td>
						<td><textarea class='form-control' style='height:35px;' disabled></textarea></td></tr>
						
					</tbody>
					</table>
					</div>
					<div  class='col-sm-12 margin-bottom-10' style='border-bottom: 1px solid #ccc;padding-bottom:5px;'>
					   <label style='font-size:16px;font-weight:400;'>评估意见</label>
					   
					</div>
					<div class='col-sm-12 margin-bottom-10 col-sm-12 col-xs-12 padding-left-0 padding-right-0'>
					     <div class="col-xs-12 padding-left-0 padding-right-0">
								<textarea  class='form-control' disabled></textarea>
						</div>
						<div class='clearfix'></div>
						<p style='font-weight:bold;margin-top:10px;text-align:right;color:red;'><label >处室经理：Admin</label>&nbsp;&nbsp;<label >日期：2017-05-03</label></p>
					</div>
					<div  class='col-sm-12 margin-bottom-10' style='border-bottom: 1px solid #ccc;padding-bottom:5px;'>
					   <label style='font-size:16px;font-weight:400;'>培训评估</label>
					   
					</div>
					<div  class='col-sm-12 margin-bottom-10 padding-left-0 padding-right-0'>
					<table class='table table-bordered '>
					
					<tbody>
					    <tr>
						<th rowspan='4'>授权项目培训</th>
						<th>课程代码</th>
						<th>是否已完成培训</th>
						<th>课程代码</th>
						<th>是否已完成培训</th>
						</tr>
						<tr>
						<td ><input type='text' class='form-control'  disabled/></td>
						<td><input type='text' class='form-control'  disabled/></td>
						<td ><input type='text' class='form-control' disabled /></td>
						<td><input type='text' class='form-control' disabled/></td></tr>
						<tr>
						<td ><input type='text' class='form-control'  disabled/></td>
						<td><input type='text' class='form-control'  disabled/></td>
						<td ><input type='text' class='form-control'  disabled/></td>
						<td><input type='text' class='form-control' disabled/></td></tr>
						<tr>
						<td ><input type='text' class='form-control'  disabled/></td>
						<td><input type='text' class='form-control'  disabled/></td>
						<td ><input type='text' class='form-control'  disabled/></td>
						<td><input type='text' class='form-control' disabled/></td></tr>
						<tr>
						<th rowspan='3'>复训科目要求</th>
						<td ><input type='text' class='form-control'  disabled/></td>
						<td><input type='text' class='form-control'  disabled/></td>
						<td ><input type='text' class='form-control' disabled /></td>
						<td><input type='text' class='form-control' disabled/></td></tr>
						<tr>
						<td ><input type='text' class='form-control'  disabled/></td>
						<td><input type='text' class='form-control'  disabled/></td>
						<td ><input type='text' class='form-control'  disabled/></td>
						<td><input type='text' class='form-control' disabled/></td></tr>
						<tr>
						<td ><input type='text' class='form-control'  disabled/></td>
						<td><input type='text' class='form-control'  disabled/></td>
						<td ><input type='text' class='form-control'  disabled/></td>
						<td><input type='text' class='form-control' disabled/></td></tr>
					</tbody>
					</table>
					</div>
					<div  class='col-sm-12 margin-bottom-10' style='border-bottom: 1px solid #ccc;padding-bottom:5px;'>
					   <label style='font-size:16px;font-weight:400;'>人力资源评估</label>
					   
					</div>
					<div  class='col-sm-12 margin-bottom-10 padding-left-0 padding-right-0'>
					<table class='table table-bordered '>
					<tbody>
					<tr>
						<td width='300'><label style='margin-left:10px;'>1.学历要素：</label></td>
						<td><label style='margin-left:10px;font-weight:400;'><input type='radio' name='1' disabled/>&nbsp;合格</label>&nbsp;&nbsp;&nbsp;<label style='font-weight:400;'><input type='radio' name='1' disabled/>&nbsp;不合格</label></td>
					</tr>
					<tr>
						<td ><label style='margin-left:10px;'>2.经验要素：</label></td>
						<td><label style='margin-left:10px;font-weight:400;'><input type='radio'name='2' disabled/>&nbsp;合格</label>&nbsp;&nbsp;&nbsp;<label style='font-weight:400;'><input type='radio' name='2' disabled/>&nbsp;不合格</label></td>
					</tr>
					<tr>
						<td ><label style='margin-left:10px;'>3.证书要求</label></td>
						<td><label style='margin-left:10px;font-weight:400;'><input type='radio' name='3' disabled/>&nbsp;合格</label>&nbsp;&nbsp;&nbsp;<label style='font-weight:400;'><input type='radio' name='3' disabled/>&nbsp;不合格</label></td>
					</tr>
					<tr>
						<td ><label style='margin-left:10px;'>4.人员培训满足《维修人员培训大纲》的要求</label></td>
						<td><label style='margin-left:10px;font-weight:400;'><input type='radio' name='4' disabled/>&nbsp;合格</label>&nbsp;&nbsp;&nbsp;<label style='font-weight:400;'><input type='radio' name='4' disabled/>&nbsp;不合格</label></td>
					</tr>
					<tr>
						<td ><label style='margin-left:10px;'>5.身体健康状况：是否通过公司年度健康体检</label></td>
						<td><label style='margin-left:10px;font-weight:400;'><input type='radio' name='5' disabled/>&nbsp;合格</label>&nbsp;&nbsp;&nbsp;<label style='font-weight:400;'><input type='radio' name='5' disabled/>&nbsp;不合格</label></td>
					</tr>
					<tr>
						<td ><label style='margin-left:10px;'>6.以往奖惩记录</label></td>
						<td><label style='margin-left:10px;font-weight:400;'><input type='radio' name='6' disabled/>&nbsp;合格</label>&nbsp;&nbsp;&nbsp;<label style='font-weight:400;'><input type='radio' name='6' disabled/>&nbsp;不合格</label></td>
					</tr>
					</tbody>
					</table>
					</div>
					<div  class='col-sm-12 margin-bottom-10' style='border-bottom: 1px solid #ccc;padding-bottom:5px;'>
					   <label style='font-size:16px;font-weight:400;'>评估结论</label>
					</div>
					<div  class='col-sm-12 margin-bottom-10 padding-left-0 padding-right-0'>
					<table class='table table-bordered '>
					<tbody>
					<tr><th width='100'>
					<label style='margin-left:10px;'>评估结论</label></th><td><div class='pull-left'><label style='margin-left:10px;font-weight:400;'><input type='radio' name='7' disabled/>&nbsp;合格</label>&nbsp;&nbsp;&nbsp;<label style='font-weight:400;'><input type='radio' name='7' disabled/>&nbsp;不合格</label></div>
					<div class='pull-right text-right'><p style='font-weight:bold'><label >评估人：<input type='text' disabled/></label><label style='margin-left:15px;margin-right:10px;'>日期：2017-05-03</label></p></div></td></tr>
					</tbody>
					</table>
					</div>
					<div  class='col-sm-12 margin-bottom-10' style='border-bottom: 1px solid #ccc;padding-bottom:5px;'>
					   <label style='font-size:16px;font-weight:400;'>责任经理/质量经理授权</label>
					</div>
					<div  class='col-sm-12 margin-bottom-10 padding-left-0 padding-right-0'>
					<table class='table table-bordered '>
					<thead><tr><th>适航当局</th><th>机型/发动机</th><th>授权(岗位/工作项目)</th><th>限定</th></tr></thead>
					<tbody>
					<tr>
					<td><input type='text' class='form-control'/></td>
					<td><input type='text' class='form-control'/></td>
					<td><input type='text' class='form-control'/></td>
					<td><input type='text' class='form-control'/></td></tr>
					
					<tr>
					<td colspan='4'><label class='pull-left'>备注：</label>&nbsp;<div class='col-sm-11'><textarea  class='form-control ' ></textarea></div></td></tr>
					<tr>
					<td colspan='2'><label class='pull-left'>授权人签名：</label>&nbsp;<div class='col-sm-8'><input type='text' class='form-control ' /></div></td>
					<td><label class='pull-left'>职务：</label>&nbsp;<div class='col-sm-8'><input type='text' class='form-control ' /></div></td>
					<td>日期：2017-05-03</td></tr>
					
					</tbody>
					</table>
					</div>
					
					<div class='col-sm-12 margin-bottom-10 padding-left-0 padding-right-0 text-right' >
					 <button class='btn btn-primary'>通过</button>
					 <button class='btn btn-primary'>驳回</button>
					 <button class='btn btn-primary'>拒绝</button>
					 <button class='btn btn-primary'>取消</button>
					</div>
			</div>
		</div>
	</div>
<script>
$(function(){
	 Navigation(menuCode,"","");//初始化导航 
	 $("#sqgw").multiselect({});
})
</script>
</body>
</html>