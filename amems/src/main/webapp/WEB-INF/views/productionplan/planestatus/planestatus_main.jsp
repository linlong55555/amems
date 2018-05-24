<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>飞机状态</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style>
.fxzt_bottom_left{
	border-right:1px solid #d5d5d5;
	padding-bottom:0px;
	padding-left:0px;
}
.fxzt_bottom_right{
	padding-bottom:0px; 
	padding-right:0px;
}
#fxzt_bottom{
	/* border-top:1px solid #d5d5d5; */
}
</style>
</head>
<body >
	<div class="page-content" >
		<div class="panel panel-primary"  id='panel_content'>
			<div class="panel-heading " id="NavigationBar">
			</div>
            <div class="panel-body padding-bottom-0">
				<!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0" id='fjztSearchDiv'>
				    <div class="pull-left padding-left-0 padding-right-0 margin-bottom-10 " style='width:300px;margin-right:10px;'>
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">机构代码</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="jgdm" class="form-control " onchange="changeOrganization()">
								<option  selected="selected">易瑞航空</option>
							</select>
						</div>
					</div>
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='机型/飞机注册号/序列号/备注名' id="fjzt_keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchFjzt();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                     </div>
                     <div class="clearfix"></div> 
				</div>
				<!-- 搜索框end -->
				 <div class="clearfix"></div> 
				<!-- 表格 -->
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" id="fjzt_list_tableDiv" style="overflow-x: auto;">
					<table id="fjzt_list_table" class="table table-thin table-bordered table-set" >
						<thead>
							<tr>
								<th class="fixed-column colwidth-9" onclick="orderBy('zch')" id="zch_order">
									<div class="font-size-12 line-height-18 notwrap">注册号</div>
									<div class="font-size-9 line-height-18 notwrap">A/C REG</div>
								</th>
								<th class="sorting fixed-column colwidth-7" onclick="orderBy('xlh')" id="xlh_order">
									<div class="important">
										<div class="font-size-12 line-height-18 notwrap">序列号</div>
										<div class="font-size-9 line-height-18 notwrap">S/N</div>
									</div>
								</th>
								<th class="sorting colwidth-10 colwidth-9" onclick="orderBy('shzt')" id="shzt_order">
									<div class="font-size-12 line-height-18 notwrap">适航状态</div>
									<div class="font-size-9 line-height-18 notwrap">Seaworthiness</div>
								</th>
								<th class="sorting colwidth-5 colwidth-7" onclick="orderBy('jx')" id="jx_order">
									<div class="font-size-12 line-height-18 notwrap">机型</div>
									<div class="font-size-9 line-height-18 notwrap">Model</div>
								</th>
								<th class="sorting colwidth-9" onclick="orderBy('fxrq')" id="fxrq_order">
									<div class="font-size-12 line-height-18 notwrap">飞行本日期</div>
									<div class="font-size-9 line-height-18 notwrap">The flight date</div>
								</th>
								<th class="sorting colwidth-9" onclick="orderBy('fxsj')" id="fxsj_order">
									<div class="font-size-12 line-height-18 notwrap">飞行时间</div>
									<div class="font-size-9 line-height-18 notwrap">Time of flight</div>
								</th>
								<th class="sorting colwidth-15" onclick="orderBy('qlxh')" id="qlxh_order">
									<div class="important">
										<div class="font-size-12 line-height-18 notwrap">起落循环</div>
										<div class="font-size-9 line-height-18 notwrap">Ups and downs cycle</div>
									</div>
								</th>
								<th class="sorting colwidth-25" onclick="orderBy('fxjl')" id="fxjl_order">
									<div class="important">
										<div class="font-size-12 line-height-18 notwrap">飞行记录</div>
										<div class="font-size-9 line-height-18 notwrap">Flight record</div>
									</div>
								</th>
							</tr>
						</thead>
						<tbody id="list">
						</tbody>

					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
                <div class='clearfix'></div>
                <div style='display:none;' id='fxzt_bottom'>
	                <div class=' col-sm-6 col-xs-12 fxzt_bottom_left'  >
	                 <%@ include file="/WEB-INF/views/productionplan/planestatus/planestatus_main_tab.jsp"%> 
	                </div>
	                <div class='col-sm-6 col-xs-12 fxzt_bottom_right'  >
	                 <%@ include file="/WEB-INF/views/productionplan/planestatus/planestatus_main_progress.jsp"%>
	                </div>
                    <div class='clearfix'>
                    </div>
                </div>

			</div>
		</div>
	</div>
	 <script>
 	 
	</script> 
	<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/planestatus/planestatus_main.js"></script>
</body>
</html>