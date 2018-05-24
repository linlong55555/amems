<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增借调对象</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
<div class="page-content ">
		<input maxlength="30" type="hidden" id="isScheduler" value="${isScheduler }"/>
		<input maxlength="30" type="hidden" id="isDuration" value="${isDuration }"/>
		<input maxlength="30" type="hidden" id="isActualhours" value="${isActualhours }"/>
		<input type="hidden"  id="zzjgId" name="zzjgId" value="${user.jgdm}"/>
		<input type="hidden"  id="jddxbh" name="jddxbh">
		<!-------导航Start--->
		<div class="panel panel-primary">
		 <div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			 <div class="panel panel-default">
		        <div class="panel-heading">
					    <h3 class="panel-title">借调对象基本信息</h3>
			   </div>
				<div class="panel-body">
				<form id="form"> 	
				<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0" >
					<!-- <div class="col-lg-3 col-sm-4 col-xs-12 padding-left-0 padding-right-0"  >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>借调对象编号</div>
							<div class="font-size-9 line-height-18">S/O No.</div></label>
						<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
							<input type="text"  id="jddxbh" name="jddxbh"class="form-control" maxlength="50">
						</div>
					</div> -->
					
					 <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0"  >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">借调对象类型</div>
							<div class="font-size-9 line-height-18">S/O Type</div></label>
						<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
							<select  id="jddxlx" name="jddxlx" class="form-control" onchange="jddxlxChange()">
								  <!--  <option value="1" selected="selected">飞行队</option> -->
								   <option value="1" >飞行队</option>
								   <option value="2" >航空公司</option>
								   <option value="0" >其他</option>
						       </select>
						</div>
					</div> 
			 		 <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0"  id="fxd" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18"><span style="color: red">*</span>选择飞行队</div>
							<div class="font-size-9 line-height-18">S/O Type</div></label>
						<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
							<select  id="qtfxd" name="qtfxd"  class="form-control " onchange="qtfxdChange()">
							<option value="">请选择</option>
									<c:choose>
											<c:when test="${departments!=null && fn:length(departments) > 0}">
											<c:forEach items="${departments}" var="type">
												<option value="${type.id}">${type.dprtname}
												</option>
											</c:forEach>
											</c:when>
											<c:otherwise>
												<option value="">暂无飞行队 No Organization</option>
											</c:otherwise>
										</c:choose>
						    </select>
						</div>
					</div>  
				</div>
				
			 <div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0" >
			    <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0" >
						<label class="col-lg-1  col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "  >
						<div class="font-size-12 line-height-18"><span style="color: red">*</span>借调对象描述</div>
						<div class="font-size-9 line-height-18">S/O Desc</div></label>
						<div class="col-lg-11  col-sm-10 col-xs-8 padding-left-8  padding-right-0 form-group">
							<input type="text" class="form-control "  id="jddxms" name="jddxms" maxlength="150">
						</div>
				</div>
			  </div>
		       <div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0" >
				    <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0" >
							<label class="col-lg-1  col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "  >
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div></label>
							<div class="col-lg-11  col-sm-10 col-xs-8 padding-left-8  padding-right-0 form-group">
								<textarea class="form-control"  id="bz" name="bz" maxlength="300" placeholder='最大长度不超过300'></textarea>
							</div>
					</div>
			  </div>
			  </form>	
			</div>
		 </div> 
		<div class=" pull-right" style="margin-bottom: 10px;">
	       <a href="javascript:void()" data-toggle="modal"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left"
			onclick="saveSecondment()"  style="float: left; margin-left: 10px;"><div class="font-size-12">保存</div>
			<div class="font-size-9">Save</div></a>     
              	<button class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-5" onclick="back()"><div
			class="font-size-12">返回</div>
			<div class="font-size-9">Back</div></button>
           </div>
		   </div>
		</div>
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/secondment/add_secondment.js"></script>
		
</body>
</html>
