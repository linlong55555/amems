<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>需求分析</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	
	<script type="text/javascript">
		$(document).ready(function(){
			//回车事件控制
			$(this).keydown(function(event) {
				e = event ? event :(window.event ? window.event : null); 
				if(e.keyCode==13){
					var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
					if(formatUndefine(winId) != ""){
						$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
					}else{
						$('#aircraftinfoMainSearch').trigger('click'); //调用主列表页查询
					}
				}
			});
		});
	</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="dprtId" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="userType" value="${user.userType}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
<div class="page-content">
	<div class="panel panel-primary">
		<div class="panel-heading  ">
			<div id="NavigationBar"></div>
		</div>
		<div class=" panel-body padding-bottom-0" >
			<div class='searchContent margin-top-0 row-height' >
			<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
				<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 ">飞机注册号</div>
					<div class="font-size-9 ">A/C Reg</div>
				</span>
				<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
					<select id="" class="form-control" onchange="">
						<option value="" selected="selected">显示全部All</option>
					</select>
				</div>
	        </div>
	         <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
	            <div class='padding-leftright-8 pull-right'>
				<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
						<input id="" style="vertical-align:text-bottom;" type="checkbox">&nbsp;缺件&nbsp;&nbsp;
				</label>
				<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
						<input id="" style="vertical-align:text-bottom;" type="checkbox">&nbsp;库存满足&nbsp;&nbsp;
				</label>
				</div>
	        </div>
	         <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group ">
				<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='部件号/部件名称'  class="form-control" id="" >
                    <div class="input-group-addon btn-searchnew-more" style='padding-left:5px !important;'>
	                   <button  type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" >
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                	</div>
				</div>
	        </div>
			<div class='padding-leftright-8 margin-bottom-5'>
			            需求分析的样本数：<span class='color-red margin-right-5'>1</span>
			     A/C数量：<span class='color-red margin-right-5'>2</span>
			             部件数：<span class='color-red margin-right-5'>22</span>
			</div>
			<div class='clearfix'></div>
			</div>
		
		   <!-- 需求追踪 -->
		   <div id="" class="modal-body col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;">
					<table id="" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1400px;">
						<thead>                              
							<tr>
								<th  style="width: 70px; ">
									<div class="font-size-12 line-height-18" >状态 </div>
									<div class="font-size-9 line-height-18">Choice</div>
								</th>
								<th class="">
									<div class="font-size-12 line-height-18" >飞机注册号</div>
									<div class="font-size-9 line-height-18">A/C Reg</div>
								</th>
								<th class="" onclick="" name="">
									<div class="font-size-12 line-height-18">部件号</div>
									<div class="font-size-9 line-height-18">PN</div>
								</th>
								<th class="" onclick="" name="">
										<div class="font-size-12 line-height-18">部件名称</div>
										<div class="font-size-9 line-height-18">PN name</div>
								</th>
								<th class="" onclick="" name="" >
									<div class="font-size-12 line-height-18">需求数量</div>
									<div class="font-size-9 line-height-18">Demand quantity</div>
								</th>
								<th class="" onclick="" name="" >
									    <div class="font-size-12 line-height-18">库存可用数量</div>
										<div class="font-size-9 line-height-18">Available num</div>
							    </th>
								<th class="" onclick="" name="" >
									<div class="font-size-12 line-height-18">替换件号</div>
									<div class="font-size-9 line-height-18">Replacement parts</div>
								</th>
								<th class="" onclick="" name="" >
										<div class="font-size-12 line-height-18">替换件库存数量</div>
										<div class="font-size-9 line-height-18">Replacement num</div>
							   </th>
							</tr> 
						</thead>
						<tbody id="">
							<tr>
							<td>缺件</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							
							</tr>
							
						</tbody>
					</table>
				</div>
		
</div>
</div>
</div>
<!-- 已关闭需求 -->

<script>
$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
})
function moreSearch(){
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
}
function customResizeHeight(bodyHeight, tableHeight){
	
}

</script>
</body>
</html>