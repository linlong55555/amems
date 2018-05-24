<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增航材移库记录</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
<div class="page-content">
			<!-- from start -->
	<div class="panel panel-primary">
	 <div class="panel-heading" id="NavigationBar"></div>
	     <div class="panel-body">
	          <!--    基本信息 start -->
				 <div class="panel panel-default">
				        <div class="panel-heading">
						  <h3 class="panel-title">移库基本信息 Transfer Information</h3>
					   </div>
				 <div class="panel-body">
				 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">移库人</div>
								<div class="font-size-9 line-height-18"> Transferer</div>
							</label>
							 
							  <div class=" col-xs-8 padding-left-8 padding-right-0 input-group">
								<div class='input-group'>
							    <input type="text" id="ykr"  value="${erayFns:escapeStr(user.username)}  ${erayFns:escapeStr(user.realname)}" class="form-control "/>
							 	<input type="hidden" class="form-control" id="ykrid" value="${user.id}" name="ykrid"/>
									<span class='input-group-btn'>
									  <button onclick='openUserList()' class='btn btn-primary'><i class='icon-search'></i>
									</button></span>
							    </div>
							</div>
							 
							 
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">移库日期</div>
								<div class="font-size-9 line-height-18">Transfer Date</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class='form-control datepicker'  id="ykrq"  value="<fmt:formatDate pattern="yyyy-MM-dd" value="${queryQuality.jyrq}"/>" data-date-format="yyyy-mm-dd" 
			         		       placeholder="请选择日期"   />
							</div>
						</div>
					</div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
							<label class=" col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
							   <div class="font-size-12 line-height-18">移库原因</div>
								<div class="font-size-9 line-height-18">Transfer Reason</div></label>
							   <div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group" >
									<textarea class="form-control" id="ykyy" name="ykyy" maxlength="300" placeholder="最大长度不超过300" ></textarea>
							  </div>
						</div>	 	
				 </div>
			     </div>
					<!-- end -->
					
				    <!-- 执行对象start	 -->
				  <div class="panel panel-default">
				        <div class="panel-heading">
						   <h3 class="panel-title">移库航材 Transfer Material</h3>
					    </div>
					<div class="panel-body">
					 <div class="col-lg-12 col-md-12 padding-left-2 padding-right-0" style="overflow-x:scroll ; ">
						 <table class="table-set table table-thin table-bordered table-striped table-hover text-center" style=" min-width: 1400px !important;" >
							<thead>
								<tr>
								    <th width="4%"> <div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-16">Opreation</div></th>
									<th><div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-16">P/N</div></th>
									<th><div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-16">CH.Name</div></th>
									<th><div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-16">F.Name</div></th>
									<th><div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-16">MP/N</div></th>
									<th width="4%"><div class="font-size-12 line-height-18">单位 </div>
									<div class="font-size-9 line-height-16">Unit</div></th>
									<th><div class="font-size-12 line-height-18">仓库</div>
									<div class="font-size-9 line-height-16">Store</div></th>
									<th><div class="font-size-12 line-height-18">库位</div>
									<div class="font-size-9 line-height-16">Storage</div></th>
									<th width="4%"><div class="font-size-12 line-height-18">库存数量</div>
									<div class="font-size-9 line-height-16">O-Num</div></th>
									<th><div class="font-size-12 line-height-18">目标仓库</div>
									<div class="font-size-9 line-height-16">T-Store</div></th>
									<th><div class="font-size-12 line-height-18">目标库位</div>
									<div class="font-size-9 line-height-16">T-Storage</div></th>									
								</tr>
							</thead>     
							<tbody id="CKList">
							<c:forEach items="${stock}" var="stock">
								<tr>
						          <td style='vertical-align: middle;'class='text-center' title="删除  Delete">
						           <i class="icon-trash color-blue cursor-pointer" onclick="delTr(this)"></i> 
						          <input type="hidden" name='id'  value="${stock.id}">
						          <input type="hidden" name='ykid'  value="${stock.bjid}">
						          </td>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(stock.bjh)}'><c:out value="${stock.bjh}" /></td>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(stock.zwms)}'><c:out value="${stock.zwms}" /></td>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(stock.ywms)}'><c:out value="${stock.ywms}" /></td>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(stock.cjjh)}'><c:out value="${stock.cjjh}" /></td>
						          <td style='vertical-align: middle;'class='text-left'><c:out value="${stock.jldw}" /></td>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(stock.ckmc)}'>
						          <input type="hidden" name='ckid'  value="${stock.ckid}">
						          <c:out value="${stock.ckmc}" /></td>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(stock.kwh)}'>
						          <input type="hidden" name='kwid'  value="${stock.kwid}">
						          <c:out value="${stock.kwh}" /></td>
						          <td style='vertical-align: middle;'class='text-right'>
						          <input type="text" name='kcsl'  value="${stock.kcsl}" class=' form-control'  onkeyup='clearNoNum(this)' >
						          <input type="hidden" name='kykcsl'  value="${stock.kcsl}"  >
						         <%--  <c:out value="${stock.kcsl}" /> --%></td>
						          <td style='vertical-align: middle;'class='text-center'>
						             <select name='mbCkmc' class='form-control'></select>
						          </td>
						          <td style='vertical-align: middle;' class='text-center'>
						             <select name='mbKwh' class='form-control'></select>
						          </td>						         
								</tr>
							</c:forEach>
							</tbody>	
						</table>
					  </div>
			        </div>
			      </div>
			      
			      
			      <div class=" pull-right" style="margin-bottom: 10px;">
                       <a href="javascript:void()" data-toggle="modal"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left"
						onclick="submitSave()"   style="float: left; margin-left: 10px;"><div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div></a>
                         &nbsp;&nbsp;
                     	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()"><div
						class="font-size-12">返回</div>
						<div class="font-size-9">Back</div></button>
                 </div>			 	
	     </div>
	     
	</div>
</div>
	<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/transfer/add_transfer.js"></script>
</body>
</html>