<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<!-- 入库管理 -->
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<link >
</head>
<body class="page-header-fixed">
	<input type="hidden" id="adjustHeight" value="80" />
	<div class="clearfix"></div>
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar">
			</div>
		
			<div class="clearfix"></div>
			
			<div class="panel-body">
				<div class="margin-bottom-10">
					<form id="handwork_view_form">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">基本信息</div>
								<div class="font-size-9 ">Basic Info</div>
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">入库类型</div>
									<div class="font-size-9 line-height-18">InStock Type</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" id="rklx" value="${instock.rklx}" readonly />
									<script>
										var rklx = '${instock.rklx}';
										var rklxName = DicAndEnumUtil.getEnumName("instockTypeEnum", rklx);
										$("#rklx").val(rklxName);
									</script>
								</div>
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">入库单号</div>
									<div class="font-size-9 line-height-18">InStock No.</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" value="${instock.rkdh}" readonly />
								</div>
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" id="zt" value="${instock.zt}" readonly />
									<script>
										var zt = '${instock.zt}';
										var ztName = DicAndEnumUtil.getEnumName("instockStatusEnum", zt);
										$("#zt").val(ztName);
									</script>
								</div>
							</div>
							<div id="sqrDiv" class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">申请人</div>
									<div class="font-size-9 line-height-18">Applicant</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" name="handwork_view_sqrmc" id="handwork_view_sqrmc" value="${erayFns:escapeStr(instock.sqrUser.username)} ${erayFns:escapeStr(instock.sqrUser.realname)}" readonly />
								</div>
							</div>
							<div id="sqrqDiv" class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">申请日期</div>
									<div class="font-size-9 line-height-18">Application Date</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" name="handwork_view_sqsj" id="handwork_view_sqsj" value='<fmt:formatDate value="${instock.sqsj2}" pattern="yyyy-MM-dd"/>' readonly />
								</div>
							</div>
							<div id="sqrDiv1" class="display-none col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">接收人</div>
									<div class="font-size-9 line-height-18">Recipient</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" name="handwork_view_sqrmc" id="handwork_view_sqrmc" value="${erayFns:escapeStr(instock.sqrUser.username)} ${erayFns:escapeStr(instock.sqrUser.realname)}" readonly />
								</div>
							</div>
							<div id="sqrqDiv1" class="display-none col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">接收日期</div>
									<div class="font-size-9 line-height-18">Receiving date</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" name="handwork_view_sqsj" id="handwork_view_sqsj" value='<fmt:formatDate value="${instock.sqsj2}" pattern="yyyy-MM-dd"/>' readonly />
								</div>
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">入库人</div>
									<div class="font-size-9 line-height-18">Operator</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" value="${erayFns:escapeStr(instock.rkrUser.username)} ${erayFns:escapeStr(instock.rkrUser.realname)}" readonly />
								</div>
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">入库日期</div>
									<div class="font-size-9 line-height-18">Operate date</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" name="handwork_view_rksj" id="handwork_view_rksj" value='<fmt:formatDate value="${instock.rksj}" pattern="yyyy-MM-dd"/>' readonly />
								</div>
							</div>
							<div id="fjzchDiv" class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">飞机注册号</div>
									<div class="font-size-9 line-height-18">A/C REG</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control"  name="handwork_view_fjzch" id="handwork_view_fjzch" value="${erayFns:escapeStr(instock.fjzch =='00000'?'通用Currency':instock.fjzch)}" readonly />
								</div>
							</div>
							<div id="jddxDiv" class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">借调对象</div>
									<div class="font-size-9 line-height-18">Seconded obj</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" value="${erayFns:escapeStr(instock.secondment.jddxms)}" readonly>
									</input>							
								</div>
							</div>
							<div id="jddxfzrDiv" class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">借调负责人</div>
									<div class="font-size-9 line-height-18">Secondment Person</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" value="${erayFns:escapeStr(instock.jdfzr)}" readonly>
									</input>							
								</div>
							</div>
							<div id="jddxfzrDiv1" class="display-none col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">归还人</div>
									<div class="font-size-9 line-height-18">Return</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" value="${erayFns:escapeStr(instock.jdfzr)}" readonly>
									</input>							
								</div>
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">制单人</div>
									<div class="font-size-9 line-height-18">Creator</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" value="${erayFns:escapeStr(instock.zdrUser.username)} ${erayFns:escapeStr(instock.zdrUser.realname)}" readonly>
									</input>							
								</div>
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">制单时间</div>
									<div class="font-size-9 line-height-18">Create Time</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" value='<fmt:formatDate value="${instock.zdsj}" pattern="yyyy-MM-dd hh:mm:ss"/>' readonly>
									</input>							
								</div>
							</div>
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</label>
								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
									<textarea class="form-control" id="handwork_view_bz" name="handwork_view_bz" maxlength="300" readonly>${erayFns:escapeStr(instock.bz)}</textarea>
								</div>
							</div>
						</div>	
					</form>
					<!-- 航材列表 start -->
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
						<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class=" pull-left margin-right-10" >
								<div class="font-size-16 line-height-16">入库列表</div>
								<div class="font-size-9 ">InStock list</div>
							</div>	
						</div>
					</div>
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow-x:auto;">
						<table class="table table-bordered table-striped table-hover table-set table-thin" style="min-width:1400px">
							<thead>
								<tr>
									<th id='handwork_view_rownum' class="colwidth-3">
										<div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</th>
									<th class="colwidth-25">
										<div class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div>
									</th>
									<th class="colwidth-20">
										<div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Stock</div>
									</th>
									<th class="colwidth-20">
										<div class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage location</div>
									</th>
									<th class="colwidth-20">
										<div class="font-size-12 line-height-18">序列号/批次号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</th>
									<th class="colwidth-13">
										<div class="font-size-12 line-height-18">库存成本</div>
										<div class="font-size-9 line-height-18">Cost(RMB:YUAN)</div>
									</th>
									<th class="colwidth-7">
										<div class="font-size-12 line-height-18">数量</div>
										<div class="font-size-9 line-height-18">Num</div>
									</th>
								</tr>
							</thead>
							<tbody id="handwork_view_list">
								<c:forEach items="${details}" varStatus="status" var="detail">
									<tr>
										<td style='vertical-align: middle;' align='center'><c:out value="${status.index +1}"/></td>
										<td style='vertical-align: middle;' align='left' title="${erayFns:escapeStr(detail.materialHistory.bjh)}"><c:out value="${detail.materialHistory.bjh}"/></td>
										<td style='vertical-align: middle;' align='left' title="${erayFns:escapeStr(detail.materialHistory.ywms)}"><c:out value="${detail.materialHistory.ywms}"/></td>
										<td style='vertical-align: middle;' align='left' title="${erayFns:escapeStr(detail.materialHistory.zwms)}"><c:out value="${detail.materialHistory.zwms}"/></td>
										<td style='vertical-align: middle;' align='left'><c:out value="${detail.materialHistory.ckmc}"/></td>
										<td style='vertical-align: middle;' align='left'><c:out value="${detail.materialHistory.kwh}"/></td>
										<td style='vertical-align: middle;' align='left' title="${erayFns:escapeStr(detail.materialHistory.sn)}${erayFns:escapeStr(detail.materialHistory.pch)}">
											<c:choose>
												<c:when test="${empty detail.materialHistory.sn}"><c:out value="${detail.materialHistory.pch}"/></c:when>
												<c:otherwise><c:out value="${detail.materialHistory.sn}"/></c:otherwise>
											</c:choose>
										</td>
										<td style='vertical-align: middle;' align='right'><c:out value="${detail.materialHistory.kccb}"/></td>
										<td style='vertical-align: middle;' align='right'><c:out value="${detail.materialHistory.kcsl}"/></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="clearfix"></div>
<!-- 					<div class="text-right"> -->
<!-- 				        <button id="handwork_view_back" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:window.history.go(-1)"> -->
<!-- 				            <div class="font-size-12">返回</div> -->
<!-- 							<div class="font-size-9">Back</div> -->
<!-- 						</button> -->
<!-- 				    </div> -->
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function(){
			Navigation(menuCode, "查看入库单", "View Instock");
			
			var type = '${instock.rklx}';
			
			if(type == 0){//手工入库
				$("#jddxfzrDiv").hide();
				$("#jddxDiv").hide();
			}else if(type == 1){//采购入库
				$("#jddxfzrDiv").hide();
				$("#jddxDiv").hide();
				$("#fjzchDiv").hide();
				$("#sqrDiv").hide();
				$("#sqrqDiv").hide();
			}else if(type == 2){//送修入库
				$("#jddxfzrDiv").hide();
				$("#jddxDiv").hide();
				$("#fjzchDiv").hide();
				$("#sqrDiv").hide();
				$("#sqrqDiv").hide();
			}else if(type == 3){//借用入库
				$("#sqrqDiv1").show();
				$("#sqrDiv1").show();
				$("#sqrDiv").hide();
				$("#sqrqDiv").hide();
			}else if(type == 4){//归还入库
				$("#sqrDiv1").show();
				$("#sqrDiv").hide();
				$("#sqrqDiv").hide();
				$("#jddxfzrDiv").hide();
				$("#jddxfzrDiv1").show();
				$("#fjzchDiv").hide();
			}
		});
	</script>
<%-- 	<%@ include file="/WEB-INF/views/open_win/user.jsp"%> --%>
<%-- 	<%@ include file="/WEB-INF/views/open_win/contract_cost.jsp"%> --%>
<%-- 	<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%> --%>
<%-- 	<script type="text/javascript" src="${ctx}/static/js/thjw/material/instock/handwork_instock_view.js"></script> --%>
</body>
</html>
