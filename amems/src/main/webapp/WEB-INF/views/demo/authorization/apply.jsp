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
			<div class="panel-heading" id="NavigationBar"></div>
            <div class="panel-body">
				<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">类别</div>
							<div class="font-size-9 line-height-18">Type</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='jd' onchange='chageselect(this)' >
								    <option value='0'>内部</option>
									<option value='1'>外部</option>
								</select>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">姓名</div>
							<div class="font-size-9 line-height-18">Name</div></label>
							 <div class="input-group  col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" id='nameInput'>
					            <input type="text" class="form-control">
					            <span class="input-group-addon" style='border-left-top-radius:0px;border-left-bottom-radius:0px;' onclick='showDialog()'><i class='icon-plus'></i></span>
					        </div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						    <div class="font-size-12 line-height-18">性别</div>
							<div class="font-size-9 line-height-18">Sex</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<label><input type='radio' name='sex' checked/>&nbsp;&nbsp;男</label>&nbsp;&nbsp;
								<label><input type='radio' name='sex'/>&nbsp;&nbsp;女</label>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">工号</div>
							<div class="font-size-9 line-height-18">Employee No.</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control'/>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">处室</div>
							<div class="font-size-9 line-height-18">Office</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control'/>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">出生年月</div>
							<div class="font-size-9 line-height-18">Date</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control date-picker' data-date-format="yyyy-mm-dd"/>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">参加工作日期</div>
							<div class="font-size-9 line-height-18">Date</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control date-picker' data-date-format="yyyy-mm-dd"/>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">加入公司日期</div>
							<div class="font-size-9 line-height-18">Date</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control date-picker' data-date-format="yyyy-mm-dd"/>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">维修人员执照</div>
							<div class="font-size-9 line-height-18">License</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control'/>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">专业</div>
							<div class="font-size-9 line-height-18">Professional</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control'/>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">有效期</div>
							<div class="font-size-9 line-height-18">Validity</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type='text' class='form-control'/>
						</div>
					</div>
					<div  class='col-sm-12' style='border-bottom: 1px solid #ccc;margin-bottom:10px;'>
					 </div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">申请类型</div>
							<div class="font-size-9 line-height-18">Type</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<label><input type='radio' name='type' checked/>&nbsp;&nbsp;初次</label>&nbsp;&nbsp;
								<label><input type='radio' name='type'/>&nbsp;&nbsp;延期</label>&nbsp;&nbsp;
								<label><input type='radio' name='type'/>&nbsp;&nbsp;增加</label>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">申请授权岗位</div>
							<div class="font-size-9 line-height-18">Authorized</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='sqgw' multiple="multiple">
								    <option>项目1</option>
								    <option>项目2</option>
								    <option>项目3</option>
								</select>&nbsp;<label class='color-red'>选择项目，多选</label>
						    </div>
					</div>
					<!-- <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 color-red">选择项目，多选</label>
					</div> -->
					<div class='col-sm-12 margin-bottom-10 col-sm-12 col-xs-12 padding-left-0 padding-right-0'>
					<label  class="col-sm-1 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">申请内容</div>
							<div class="font-size-9 line-height-18">Content</div></label>
							<div class=" col-xs-11 padding-left-8 padding-right-0">
								<textarea  class='form-control'></textarea>
						</div>
					</div>
					<div class='margin-bottom-10 col-sm-12 col-xs-12 padding-left-0 padding-right-0'>
					<label  class="col-sm-1 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div></label>
							<div class=" col-xs-11 padding-left-8 padding-right-0">
								<textarea  class='form-control'></textarea>
						</div>
					</div>
					<div  class='col-sm-12 margin-bottom-10' style='border-bottom: 1px solid #ccc;padding-bottom:5px;'>
					   <label style='font-size:16px;font-weight:400;'>提交材料</label>
					   
					</div>
					<div class='col-sm-6 margin-bottom-10'>
					<input type='checkbox' class='pull-left' style='margin-top:8px;margin-right:10px;'/>
					<label  class="padding-left-0 padding-right-0 pull-left" style='width:110px;'><div
								class="font-size-12 line-height-18">学历证书复印件</div>
							<div class="font-size-9 line-height-18">Diploma certificate</div></label>
							<div class='col-sm-7' style='border:1px solid #d5d5d5;height:35px;line-height:35px;margin-left:10px;border-radius:5px;'>
							   <a href="#">高中毕业证.jpg</a>,<a href="#">英语六级证书.jpg</a>
							</div>
							<button class='btn btn-primary' style='margin-left:8px;'>	
							     上传
							</button> 
							<div class='clearfix'></div>		
					</div>
					<div class='col-sm-6 margin-bottom-10'>
					<input type='checkbox' class='pull-left' style='margin-top:8px;margin-right:10px;'/>
					<label  class=" padding-left-0 padding-right-0 pull-left" style='width:110px;'><div
								class="font-size-12 line-height-18">基础执照复印件</div>
							<div class="font-size-9 line-height-18">Basic license</div></label>
							<div class='col-sm-7' style='border:1px solid #d5d5d5;height:35px;line-height:35px;margin-left:10px;border-radius:5px;'>
							   
							</div>
							<button class='btn btn-primary' style='margin-left:8px;'>	
							     上传
							</button>
							<div class='clearfix'></div>	 		
					</div>
					<div class='col-sm-6 margin-bottom-10'>
					<input type='checkbox' class='pull-left' style='margin-top:8px;margin-right:10px;'/>
					<label  class="padding-left-0 padding-right-0 pull-left" style='width:110px;'><div
								class="font-size-12 line-height-18">其他证书</div>
							<div class="font-size-9 line-height-18">Other certificates</div></label>
							<div class='col-sm-7' style='border:1px solid #d5d5d5;height:35px;line-height:35px;margin-left:10px;border-radius:5px;'>
							 
							</div>
							<button class='btn btn-primary' style='margin-left:8px;'>	
							     上传
							</button> 	
							<div class='clearfix'></div>		
					</div>
					<div class='col-sm-6 margin-bottom-10'>
					<input type='checkbox' class='pull-left' style='margin-top:8px;margin-right:10px;'/>
					<label  class="padding-left-0 padding-right-0 pull-left" style='width:110px;'><div
								class="font-size-12 line-height-18">机型培训证书复印件</div>
							<div class="font-size-9 line-height-18">Model training</div></label>
							<div class='col-sm-7' style='border:1px solid #d5d5d5;height:35px;line-height:35px;margin-left:10px;border-radius:5px;'>
							  
							</div>
							<button class='btn btn-primary' style='margin-left:8px;'>	
							     上传
							</button> 
							<div class='clearfix'></div>			
					</div>
					<div class='col-sm-6 margin-bottom-10'>
					<input type='checkbox' class='pull-left' style='margin-top:8px;margin-right:10px;'/>
					<label  class="padding-left-0 padding-right-0 pull-left" style='width:110px;'><div
								class="font-size-12 line-height-18">公司培训记录</div>
							<div class="font-size-9 line-height-18">Company training</div></label>
							<div class='col-sm-7' style='border:1px solid #d5d5d5;height:35px;line-height:35px;margin-left:10px;border-radius:5px;'>
							 
							</div>
							<button class='btn btn-primary' style='margin-left:8px;'>	
							     上传
							</button>
							<div class='clearfix'></div>	 		
					</div>
					<div class='col-sm-6 margin-bottom-10'>
					<input type='checkbox' class='pull-left' style='margin-top:8px;margin-right:10px;'/>
					<label  class="padding-left-0 padding-right-0 pull-left" style='width:110px;'><div
								class="font-size-12 line-height-18">其他培训证书</div>
							<div class="font-size-9 line-height-18">Other training</div></label>
							<div class='col-sm-7' style='border:1px solid #d5d5d5;height:35px;line-height:35px;margin-left:10px;border-radius:5px;'>
							
							</div>
							<button class='btn btn-primary' style='margin-left:8px;'>	
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
					<th width='50'><button onclick='addWork("worklist")' class='btn btn-primary' style='margin-left:10px;padding-top:4px;padding-right:8px;padding-bottom:4px;padding-left:8px;'><i class='icon-plus cursor-pointer'></i></button></th>
					<th>起始</th><th>结束</th><th>工作经历</th></tr>
					</thead>
					<tbody id='worklist'>
						<tr>
						<td class='text-center'>
						<button class="btn btn-primary" onclick="del_tr(this)" style='margin-left:10px;padding-top:4px;padding-right:8px;padding-bottom:4px;padding-left:8px;'><i class="fa glyphicon glyphicon-minus cursor-pointer"></i></button></td>
						<td width='200'><input type='text' class='form-control' style='width:200px;'/></td>
						<td width='200'><input type='text' class='form-control' style='width:200px;'/></td>
						<td><textarea class='form-control' style='height:35px;'></textarea></td></tr>
					</tbody>
					</table>
					</div>
					<div  class='col-sm-12 margin-bottom-10' style='border-bottom: 1px solid #ccc;padding-bottom:5px;'>
					   <label style='font-size:16px;font-weight:400;'>培训经历</label>
					   
					</div>
					<div  class='col-sm-12 margin-bottom-10 padding-left-0 padding-right-0'>
					<table class='table table-bordered table-striped table-hover'>
					<thead>
					<tr><th width='50'><button class='btn btn-primary' onclick='addWork("trainlist")' style='margin-left:10px;padding-top:4px;padding-right:8px;padding-bottom:4px;padding-left:8px;'><i class='icon-plus cursor-pointer'></i></button></th><th>起始</th><th>结束</th><th>培训经历</th></tr>
					</thead>
					<tbody id='trainlist'>
						<tr>
						<td class='text-center'>
						<button class="btn btn-primary" onclick="del_tr(this)" style='margin-left:10px;padding-top:4px;padding-right:8px;padding-bottom:4px;padding-left:8px;'><i class="fa glyphicon glyphicon-minus cursor-pointer"></i></button></td>
						<td width='200'><input type='text' class='form-control' style='width:200px;'/></td>
						<td width='200'><input type='text' class='form-control' style='width:200px;'/></td>
						<td><textarea class='form-control' style='height:35px;'></textarea></td></tr>
						
					</tbody>
					</table>
					</div>
					<div  class='col-sm-12 margin-bottom-10' style='border-bottom:1px solid #ccc;padding-bottom:8px;'>
					<p style='font-weight:bold;color:red;'>声明：我声明所填写/提交的资料真实有效</p>
					<p style='font-weight:bold'><label class='pull-left'>申请人签名：Admin</label><label class='pull-right'>日期：2017-05-03</label></p>
					</div>
					<div class='col-sm-12 margin-bottom-10 padding-left-0 padding-right-0 text-right' >
					 <button class='btn btn-primary'>保存</button>
					 <button class='btn btn-primary'>提交</button>
					 <button class='btn btn-primary'>取消</button>
					</div>
			</div>
		</div>
	</div>
	<div id="userModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false >
<div class="modal-dialog">
<div class="modal-content">
<div id="alertModalUserBody" class="modal-body padding-bottom-0">
<div class="panel panel-primary">
<div class="panel-heading padding-top-3 padding-bottom-1">
<div class="font-size-16 line-height-18">用户列表</div>
<div class="font-size-9 ">User List</div>
</div>
<div class="panel-body padding-top-0 padding-bottom-0">
<div class="col-lg-12 col-xs-12">
<div class=" pull-right padding-left-0 padding-right-0 margin-top-10">
<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
<input id="u_realname_search" class="form-control " placeholder="用户名称" type="text">
</div>
<div class=" pull-right padding-left-5 padding-right-0 ">
<button class=" btn btn-primary padding-top-1 padding-bottom-1 " type="button" onclick="userModal.search()">
<div class="font-size-12">搜索</div>
<div class="font-size-9">Search</div>
</button>
</div>
</div>
<div class="clearfix"></div>
<div class="margin-top-10 ">
<div class="overflow-auto">
<table class="table table-bordered table-striped table-hover text-center" style="">
<thead>
<tr>
<th width="50px">
<div class="font-size-12 notwrap">选择</div>
<div class="font-size-9 notwrap">Choice</div>
</th>
<th>
<div class="important">
<div class="font-size-12 notwrap">用户名称</div>
<div class="font-size-9 notwrap">User Name</div>
</div>
</th>
<th>
<div class="font-size-12 notwrap">机构部门</div>
<div class="font-size-9 notwrap">Department</div>
</th>
</tr>
</thead>
<tbody id="userlist">
<tr style="cursor:pointer"  bgcolor="#f9f9f9">
<td style="text-align:center;vertical-align:middle;">
<input name="name" index="0" type="radio">
</td>
<td style="text-align:left;vertical-align:middle;">peixiu peixiu11</td>
<td style="text-align:left;vertical-align:middle;"></td>
</tr>
<tr style="cursor:pointer"  bgcolor="#fefefe">
<td style="text-align:center;vertical-align:middle;">
<input name="name" index="1" type="radio">
</td>
<td style="text-align:left;vertical-align:middle;">admin admin</td>
<td style="text-align:left;vertical-align:middle;"></td>
</tr>
<tr style="cursor:pointer"  bgcolor="#f9f9f9">
<td style="text-align:center;vertical-align:middle;">
<input name="name" index="2" type="radio">
</td>
<td style="text-align:left;vertical-align:middle;">mzl 方法</td>
<td style="text-align:left;vertical-align:middle;"></td>
</tr>
</tbody>
</table>
</div>
<div id="user_pagination" class="col-xs-12 text-center page-height padding-right-0 padding-left-0">
<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;">
<li class="disabled">
<a href="javascript:void(0)"><<</a>
</li>
<li class="disabled">
<a href="javascript:void(0)"><</a>
</li>
<li class="active">
<a href="javascript:void(0)">1</a>
</li>
<li class="disabled">
<a href="javascript:void(0)">></a>
</li>
<li class="disabled">
<a href="javascript:void(0)">>></a>
</li>
</ul>
<div class="fenye pull-right padding-right-0 text-center">
<span>
每页
<i>10</i>
条
</span>
<span>
共
<i>1</i>
页
</span>
<span>
总数
<i>3</i>
条
</span>
</div>
</div>
</div>
<div class="text-right margin-top-10 margin-bottom-10">
<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="userModal.setUser()" data-dismiss="modal">
<div class="font-size-12">确定</div>
<div class="font-size-9">Confirm</div>
</button>
<button id="userModal_btn_clear" class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="userModal.clearUser()">
<div class="font-size-12">清空</div>
<div class="font-size-9">Clear</div>
</button>
<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" data-dismiss="modal">
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
     <script>
     function addWork(obj){
    	 
    	 var str="	<tr><td class='text-center'>"
    	         +"<button class='btn btn-primary' onclick='del_tr(this)' style='margin-left:10px;padding-top:4px;padding-right:8px;padding-bottom:4px;padding-left:8px;'><i class='fa glyphicon glyphicon-minus cursor-pointer'></i></button></td>"
    	         +"<td width='200'><input type='text' class='form-control' style='width:200px;'/></td>"
    	         +"<td width='200'><input type='text' class='form-control' style='width:200px;'/></td>"
    	         +"<td><textarea class='form-control' style='height:35px;'></textarea></td><tr>";
    	     $("#"+obj).append(str) ;   
     }
     function del_tr(obj){
    	 $(obj).parent("td").parent("tr").remove();
     }
     function chageselect(obj){
    	if($(obj).val()=='1'){
    		$("#nameInput").find("span").remove();
    	}else{
    		var str='<span class="input-group-addon" style="border-left-top-radius:0px;border-left-bottom-radius:0px;" onclick="showDialog()"><i class="icon-plus"></i></span>'
    		if($("#nameInput").find("span").length==0){
    			$("#nameInput").append(str)
    		}
    	}
     }
     $(function(){
    	 Navigation(menuCode,"","");//初始化导航
    	 $(".date-picker").datepicker({});
    	 $("#sqgw").multiselect({});
     })
     function showDialog(){
    	 $("#userModal").modal("show");
     }
     </script>
</body>
</html>