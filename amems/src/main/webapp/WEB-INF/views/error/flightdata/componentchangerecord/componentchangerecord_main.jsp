<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>部件拆换记录</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" name="zzjgid" value=${user.jgdm} />
<input type="hidden" id="userId" name="userId" value=${user.id} />


		<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
<script type="text/javascript">
$(document).ready(function(){
//导航
Navigation(menuCode);
});
</script>

<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body padding-bottom-0">
				
				
					<!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0">
				
				<div class="pull-left ">
						<span class="pull-left  text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class=" padding-left-8 padding-right-0 pull-left" style="width: 180px; margin-right:5px;">
							<select id="zzjg" class="form-control " name="zzjg" onchange="searchRevision();">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
				</div>
				
				
				
					<div class=" pull-left padding-left-0 padding-right-0 row-height" style="width: 250px;">
						<input type="text" placeholder='件号/序列号/飞机注册号/安装记录单号/拆解记录单号' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                    </div> 
				</div>
				
					<div class="clearfix"></div>

					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="overflow-x: scroll;width: 100%;">
						<table id="quality_check_list_table" class="table table-thin table-bordered table-set" style="min-width: 2600px !important">
							<thead>
							<tr>
							<th class="sorting fixed-column colwidth-10" style='vertical-align:middle' onclick="orderBy('fjzch')" id="fjzch_order" rowspan="2"><div class="important"><div class="font-size-12 line-height-18" >飞机注册号</div><div class="font-size-9 line-height-18" >A/C REG</div></div></th>
							<th class="sorting fixed-column colwidth-10" style='vertical-align:middle' onclick="orderBy('jh')" id="jh_order" rowspan="2"><div class="important"><div class="font-size-12 line-height-18" >件号</div><div class="font-size-9 line-height-18" >P/N</div></div></th>
							<th class="sorting fixed-column colwidth-10" style='vertical-align:middle' onclick="orderBy('xlh')" id="xlh_order" rowspan="2"><div class="important"><div class="font-size-12 line-height-18" >序列号</div><div class="font-size-9 line-height-18" >S/N</div></div></th>
							<th class="colwidth-40" onclick="vieworhideWorkContentAll()" colspan="7"><div class="font-size-12 line-height-18" >装上</div><div class="font-size-9 line-height-18" >Assembly</div></th>
							
							<th class="colwidth-40" colspan="7"><div class="font-size-12 line-height-18" >拆下 </div><div class="font-size-9 line-height-18" >Disassembly</div></th>
							<th class="colwidth-15" rowspan="2" style='vertical-align:middle'><div class="font-size-12 line-height-18" >在机使用量</div><div class="font-size-9 line-height-18" >In machine usage</div></th>
							</tr> 
							<tr>
							<th class="sorting colwidth-5" onclick="orderBy('az_zxrq')" id="az_zxrq_order"><div class="font-size-12 line-height-18" >安装日期</div><div class="font-size-9 line-height-18" >Install date</div></th>
							<th class="sorting colwidth-7" onclick="orderBy('az_jld')" id="az_jld_order"><div class="important"><div class="font-size-12 line-height-18" >记录单号 </div><div class="font-size-9 line-height-18" >Record No.</div></div></th>
							<th class="sorting colwidth-9" onclick="orderBy('az_gzzid')" id="az_gzzid_order"><div class="font-size-12 line-height-18" >工作者</div><div class="font-size-9 line-height-18" >Worker</div></th>
							<th class="colwidth-20" ><div class="font-size-12 line-height-18" >规定时限</div><div class="font-size-9 line-height-18" >Prescribed limit </div></th>
							<th class="colwidth-20" ><div class="font-size-12 line-height-18" >装机前已用</div><div class="font-size-9 line-height-18" >Has been used</div></th>
							<th class="colwidth-20"><div class="font-size-12 line-height-18" >剩余寿命</div><div class="font-size-9 line-height-18" >Surplus</div></th>
							<th class="colwidth-20"><div class="font-size-12 line-height-18" >装上备注</div><div class="font-size-9 line-height-18" >Remark</div></th>
							
							<th class="sorting colwidth-5" onclick="orderBy('cj_zxrq')" id="cj_zxrq_order"><div class="font-size-12 line-height-18" >拆下日期</div><div class="font-size-9 line-height-18" >Remove date</div></th>
							<th class="sorting colwidth-7" onclick="orderBy('cj_jld')" id="cj_jld_order"><div class="important"><div class="font-size-12 line-height-18" >记录单号</div><div class="font-size-9 line-height-18" >Record No.</div></div></th>
							<th class="sorting colwidth-9" onclick="orderBy('cj_gzzid')" id="cj_gzzid_order"><div class="font-size-12 line-height-18" >工作者</div><div class="font-size-9 line-height-18" >Worker</div></th>
							<th class="colwidth-20" ><div class="font-size-12 line-height-18" >已用</div><div class="font-size-9 line-height-18" >Has been used</div></th>
							<th class=" colwidth-20" ><div class="font-size-12 line-height-18" >剩余</div><div class="font-size-9 line-height-18" >Surplus</div></th>
							<th class="colwidth-20" ><div class="font-size-12 line-height-18" >对应装上</div><div class="font-size-9 line-height-18" >Assembly part</div></th> 
							<th class="colwidth-20"><div class="font-size-12 line-height-18" >拆下原因</div><div class="font-size-9 line-height-18" >Reason</div></th>
							</tr>
			         		 </thead>
							<tbody id="Componentchangerecordlist">
							</tbody>
							
						</table>
					</div>
					<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
					</div>
					
					<div class="clearfix"></div>
				</div>
			</div>

	</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/componentchangerecord/componentchangerecord_main.js"></script> 
	
</body>
</html>