<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>飞机状态</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
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
.td_table table tbody tr:first-child td,.td_table table thead tr:first-child th{
border-top:0px !important;
}
.td_table table tbody tr:last-child td,.td_table table thead tr:last-child th{
border-bottom:0px !important;
}

</style>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body >
	<input type="hidden" id="whbmid" value="${user.bmdm}" />
	<input type="hidden" id="whrid" value="${sessionScope.user.id}" />
	<div class="page-content" >
		<div class="panel panel-primary"  id='panel_content'>
			<div class="panel-heading " id="NavigationBar">
			</div>
            <div class="panel-body padding-bottom-0">
				<!-- 搜索框start -->
				<button type="button"class="btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 row-height pull-left"
						onclick="add();">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</button>
				<div class=" pull-right padding-left-0 padding-right-0" id='fjztSearchDiv'>
				  
				     <div class="pull-left padding-left-0 padding-right-0" style='width:300px;margin-right:10px;'>
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C Reg</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='fjzch' name="fjzch" onchange="searchFjzd();">
							 </select>
						</div>
					</div>
					
					   <div class="pull-left padding-left-0 padding-right-0  " style='width:300px;margin-right:10px;'>
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " onchange="jzfjzch()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				    
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='描述/构型' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchFjzd();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                     </div>
                     <div class="clearfix"></div> 
				</div>
				<!-- 搜索框end -->
				 <div class="clearfix"></div> 
				<!-- 表格 -->
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h table-set" id="fjzt_list_tableDiv" style="overflow-x: auto;width: 100%;">
					<table id="fjzd_list_table" class="table table-thin table-bordered" >
						<thead>
						<tr>
						    <th rowspan='3' class="fixed-column colwidth-5">
								<div class="font-size-12 line-height-18" style="width:60px">操作</div>
								<div class="font-size-9 line-height-18" >Operation</div>
							</th>
							<th colspan='13'>飞机震动数据</th>
							<th colspan='9'>飞机重心数据</th>
							<th rowspan='3' class="sorting colwidth-7" onclick="orderBy('zdrid')" id="zdrid_order">
								<div class="font-size-12 line-height-18" style='width:100px; !important' >制单人</div>
								<div class="font-size-9 line-height-18" >Creator</div>
							</th>
							<th rowspan='3' class="sorting colwidth-5" onclick="orderBy('zdsj')" id="zdsj_order">
								<div class="font-size-12 line-height-18" style='width:130px; !important'>制单时间</div>
								<div class="font-size-9 line-height-18" >Create Time</div>
							</th>
							<th rowspan='3' class="sorting colwidth-5" onclick="orderBy('dprtcode')" id="dprtcode_order">
								<div class="font-size-12 line-height-18" >组织机构</div>
								<div class="font-size-9 line-height-18" >Organization</div>
							</th>
							</tr>
							<tr>
								<th class="sorting  " onclick="orderBy('fjzch')" id="fjzch_order" rowspan='2' >
									<div class="font-size-12 line-height-18 colwidth-7">飞机注册号</div>
									<div class="font-size-9 line-height-18">A/C Reg</div>
								</th>
								<th class="sorting " onclick="orderBy('ms')" id="ms_order" rowspan='2'>
									<div class="important">
										<div class="font-size-12 colwidth-20">描述</div>
										<div class="font-size-9 line-height-18">Desc</div>
									</div>
								</th>
								<th class="sorting " onclick="orderBy('zxrq')" id="zxrq_order" rowspan='2'>
								
										<div class="font-size-12 line-height-18 colwidth-5 " style='width:100px; !important'>日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									
								</th>
								<th  colspan='9'>
									<div class="font-size-12 line-height-18">主旋翼震动</div>
									<div class="font-size-9 line-height-18">Main rotor</div>
								</th>
								<th class="sorting" onclick="orderBy('wjzd')" id="wjzd_order" rowspan='2'>
									<div class="font-size-12 line-height-18 colwidth-10">尾浆震动</div>
									<div class="font-size-9 line-height-18">Tail rotor</div>
								</th>
								<th  colspan='3' class="downward " onclick="vieworhideContentAll()" name="th_return">
									<div class="font-size-12 line-height-18 " style="width:450px">部件震动</div>
									<div class="font-size-9 line-height-18">Component</div>
								</th>
								<th class="sorting " onclick="orderBy('gx')" id="gx_order" rowspan='2'>
								<div class="important">
									<div class="font-size-12 line-height-18 colwidth-20">构型</div>
									<div class="font-size-9 line-height-18">Configuration</div>
								</div>
								</th>
								<th class="sorting" onclick="orderBy('fjzl_sz')" id="fjzl_sz_order" rowspan='2'>
										<div class="font-size-12 line-height-18  colwidth-7">飞机总量</div>
										<div class="font-size-9 line-height-18">Total plane</div>
								</th>
								<th class="sorting" onclick="orderBy('zxzx_sz')" id="zxzx_sz_order" rowspan='2'>
										<div class="font-size-12 line-height-18  colwidth-7">纵向重心</div>
										<div class="font-size-9 line-height-18">Y Gravity</div>
								</th>
								<th class="sorting " onclick="orderBy('hxzx_sz')" id="hxzx_sz_order" rowspan='2'>
										<div class="font-size-12 line-height-18  colwidth-7">横向重心</div>
										<div class="font-size-9 line-height-18">X Gravity</div>
								</th>
								<th class="sorting   " onclick="orderBy('bz')" id="bz_order" rowspan='2'>
										<div class="font-size-12 line-height-18 colwidth-20">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
								</th>
								<th class="downward " onclick="vieworhideContentAll()" name="th_return" rowspan='2'>
										<div class="font-size-12 line-height-18 colwidth-15">称重附件</div>
										<div class="font-size-9 line-height-18">Attachment</div>
								</th>
								
							</tr>
							<tr>
							<th ><div class='colwidth-3'>X1</div></th>
							<th><div  class='colwidth-3'>Y1</div></th>
							<th ><div class='colwidth-3'>Z1</div></th>
							<th ><div class='colwidth-3'>X2</div></th>
							<th ><div class='colwidth-3'>Y2</div></th>
							<th ><div class='colwidth-3'>Z2</div></th>
							<th><div  class='colwidth-3'>X3</div></th>
							<th ><div class='colwidth-3'>Y3</div></th>
							<th ><div class='colwidth-3'>Z3</div></th>
							<th style="width:230px padding-left:0px !important;padding-right:0px !important"><div class='colwidth-13' >部件名称</div></th>
							<th style="width:140px padding-left:0px !important;padding-right:0px !important"><div class='colwidth-5'>属性名</div></th>
							<th style="width:59px padding-left:0px !important;padding-right:0px !important"><div class='colwidth-5'>震动值</div></th>
							</tr>
						</thead>
						<tbody id="fjzdList">
						</tbody>

					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
                <div class='clearfix'></div>

			</div>
		</div>
	</div>
	 <script>
 	 
	</script> 
		<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/planeload/planeload_main.js"></script>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>