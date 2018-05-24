<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var pageParam = '${param.pageParam}';
</script>
</head>
<body>

	<div class="page-content">
		<div class="panel panel-primary">
	<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			
				
				<input type="hidden" value="${godownEntry.receiptSheetDetail.bjid}" id="bjid">
				<input type="hidden" value="${godownEntry.hcmainData.gg}" id="gg">
				<input type="hidden" value="${godownEntry.hcmainData.xh}" id="xh">
				<input type="hidden" value="${godownEntry.dprtcode}" id="dprtcode" >
				<input type="hidden" value="${godownEntry.jydid}" id="jydid" >
				<input type="hidden" value="${godownEntry.id}" id="id" >
				 <input type="hidden" value="${GodownEntryDetails[0].materialHistory.sn}" id="xlh" >
				<input type="hidden" value="${GodownEntryDetails[0].materialHistory.pch}" id="pch" > 
				<input type="hidden" value="${GodownEntryDetails[0].materialHistory.hcly}" id="hclyone" > 
				<c:choose>
					<c:when test="${GodownEntryDetails[0].materialHistory==null}">
						<input type="hidden" value="1" id="cklb" >
					</c:when>
					<c:otherwise>
						<input type="hidden" value="${GodownEntryDetails[0].materialHistory.cklb}" id="cklb" >
					</c:otherwise>
				</c:choose>
				
				
					  <div class="panel panel-default">
				        <div class="panel-heading">
						 <h3 class="panel-title">基本信息  Basic Information</h3>
					    </div>
					    
					<div class="panel-body">	
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库单号</div>
								<div class="font-size-9 line-height-18">Receipt No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control" type="text"  value="${erayFns:escapeStr(godownEntry.rkdh)} " readonly />
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库类型</div>
								<div class="font-size-9 line-height-18">Storage Type</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							
							<c:choose>
								<c:when test="${godownEntry.rklx==99}">
									<input class="form-control" id="rklx" value="5" name="rklx"  type="text" readonly/>
								</c:when>
								<c:otherwise>
									<input class="form-control" id="rklx" value="${godownEntry.rklx}" name="rklx"  type="text" readonly/>
								</c:otherwise>
							</c:choose>
							
								
							</div>
						</div>
					
					
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">件号</div>
								<div class="font-size-9 line-height-18">Part No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control" type="text" id="bjh" value="${erayFns:escapeStr(godownEntry.hcmainData.bjh)} " readonly />
							</div>
						</div>
							
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">中文名称</div>
								<div class="font-size-9 line-height-18">Chinese Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control" type="text"  id="zwms" value="${erayFns:escapeStr(godownEntry.hcmainData.zwms)} " readonly />
							</div>
						</div>
						
						<div class="clearfix"></div>
							
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-0 ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">英文名称</div>
								<div class="font-size-9">English Name</div>
							</label>
							<input type="hidden" value="${erayFns:escapeStr(borrowMain.fjzch)}" id="fjzchs">
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
									<input class="form-control" type="text"  id="ywms" value="${erayFns:escapeStr(godownEntry.hcmainData.ywms)} " readonly />
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-0 ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">航材类型</div>
								<div class="font-size-9">Material Type</div>
							</label>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
									<input class="form-control" type="text" id="hclx" name="hclx" value="${godownEntry.hcmainData.hclx}" readonly />
							</div>
						</div>
						
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-0 ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">管理级别</div>
								<div class="font-size-9">Management Level</div>
							</label>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
									<input class="form-control" type="text" id="gljb"  value="${godownEntry.hcmainData.gljb}" readonly />
									<input  type="hidden" id="gljbbase"  value="${godownEntry.hcmainData.gljb}" readonly />
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-0 ">
						<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">计量单位</div>
							<div class="font-size-9">Unit</div>
						</label>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
								<input class="form-control" type="text"  id="jldw" value="${erayFns:escapeStr(godownEntry.hcmainData.jldw)}" readonly />
						</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-0 ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">收货数量</div>
								<div class="font-size-9">Receipt Quantity</div>
							</label>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
									<input class="form-control" type="text"  value="${erayFns:escapeStr(godownEntry.receiptSheetDetail.sl)} " readonly />
							</div>
						</div>
				
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-0 ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">入库数量</div>
								<div class="font-size-9">Number</div>
							</label>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
							<c:choose>
								<c:when test="${godownEntry.inspection.id==null}">
									<input class="form-control" type="text" id="rksl" value="${godownEntry.receiptSheetDetail.sl-(godownEntry.receiptSheetDetail.thsl==null?0:godownEntry.receiptSheetDetail.thsl)}" readonly />
								</c:when>
								<c:otherwise>
									<input class="form-control" type="text"   id="rksl" value="${godownEntry.inspection.kysl}" readonly />
								</c:otherwise>
							</c:choose>
							
							</div>
							
							
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单人</div>
								<div class="font-size-9 line-height-18">Creator</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control" type="text"  value="${erayFns:escapeStr(godownEntry.zdrid)}" readonly />
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单时间</div>
								<div class="font-size-9 line-height-18">Create Time</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control" type="text" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${godownEntry.zdsj}"/>" data-date-format="yyyy-MM-dd HH:mm:ss"   readonly />
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">状态</div>
								<div class="font-size-9 line-height-18">State</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input class="form-control"  name="state" value="${godownEntry.zt}" id="state" disabled="disabled"/>
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-0 ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">库存成本(元)</div>
								<div class="font-size-9">Costs(YUAN)</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<c:choose>
									<c:when test="${GodownEntryDetails[0].materialHistory==null}">
										<input type="text" class="form-control" name="bccb" value="" onkeyup="clearNoNum(this);" id="kccb"/>
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control" name="bccb" value="${GodownEntryDetails[0].materialHistory.kccb}" onkeyup="clearNoNum(this);" id="kccb"/>
									</c:otherwise>
								</c:choose>
								<span class="input-group-btn">
									<button onclick="showContractCost();" class="btn btn-primary">
										<i class="icon-list"></i>
									</button>
								</span>
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-0 ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">入库人</div>
								<div class="font-size-9">Warehousing People</div>
							</label>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
								<div class='input-group'>
									<input type="text" class="form-control" value="${erayFns:escapeStr(user.username)} ${erayFns:escapeStr(user.realname)}" name="username" id="username" readonly />
									<input class="form-control " type="hidden" value="${user.id}" type="text" id="userid" name="userid" readonly="readonly" />
									<input class="form-control " type="hidden" value="${user.bmdm}" type="text" id="rkbmid" name="rkbmid" readonly="readonly" />
									<span class='input-group-btn'>
									  <button type="button" onclick='selectUser()' class='btn btn-primary'><i class='icon-search'></i>
									</button>
									</span>
					  		  </div>
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-0 ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">入库日期</div>
								<div class="font-size-9">Storage Date</div>
							</label>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 ">
									<input class="form-control date-picker" id="rkrq" name="rkrq"
							data-date-format="yyyy-mm-dd" type="text" />
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">发货单位</div>
								<div class="font-size-9">Delivery</div>
							</label>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 ">
									<input class="form-control " id="fhdw" name="fhdw" value="${erayFns:escapeStr(godownEntry.fhdw)}" type="text" />
							</div>
						</div>
						
						<div class="clearfix"></div>
						
					 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 margin-left-50 form-group">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="bz" name="bz" maxlength="300" placeholder="长度最大为300">${erayFns:escapeStr(godownEntry.bz)}</textarea>
							</div>
						</div>
						</div>
					</div>
			</div>
				<form id="form">
  			  <div class="panel panel-default">
				        <div class="panel-heading">
						  <h3 class="panel-title">航材检验  Material Checking</h3>
					    </div>
				<div class="panel-body" id="checkingId">
				   <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						 <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 margin-bottom-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">部件来源</div>
								<div class="font-size-9 line-height-18">Source</div>
							</label>
							<div id="hclyDiv"  class=" col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
								<select id="hcly" name="hcly" class="form-control"  >
							     </select>
							</div> 
	                 	</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 margin-bottom-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">GRN</div>
								<div class="font-size-9 line-height-18">GRN</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
							<input type="text" class="form-control" id="grn" maxlength="50"  value="${erayFns:escapeStr(GodownEntryDetails[0].materialHistory.grn)}" />
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 margin-bottom-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">生产日期</div>
								<div class="font-size-9 line-height-18">Date</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="scrq"  name="scrq" class='form-control datepicker'  value="<fmt:formatDate pattern="yyyy-MM-dd" value="${GodownEntryDetails[0].materialHistory.hjsm}"/>" data-date-format="yyyy-mm-dd"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 margin-bottom-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">货架寿命</div>
								<div class="font-size-9 line-height-18">Shelf-Life</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="hjsm"  name="hjsm" class='form-control datepicker'  value="<fmt:formatDate pattern="yyyy-MM-dd" value="${GodownEntryDetails[0].materialHistory.hjsm}"/>" data-date-format="yyyy-mm-dd"/>
							</div>
						</div>
						
					</div>
					
				   <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
				
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 margin-bottom-0 padding-right-0 form-group">
						
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">TSN</div>
								<div class="font-size-9 line-height-18">TSN</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group">
								<input type="text" value="${erayFns:escapeStr(GodownEntryDetails[0].materialHistory.tsn)}" id="tsn"  name="tsn" maxlength="50" class="form-control " />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 margin-bottom-0 padding-right-0 form-group">
						
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">CSN</div>
								<div class="font-size-9 line-height-18">CSN</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group">
								<input type="text" value="${erayFns:escapeStr(GodownEntryDetails[0].materialHistory.csn)}" id="csn"  name="csn" maxlength="50" class="form-control " />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 margin-bottom-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">TSO</div>
								<div class="font-size-9 line-height-18">TSO</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group">
								<input type="text" value="${erayFns:escapeStr(GodownEntryDetails[0].materialHistory.tso)}" id="tso"  name="tso"  maxlength="50" class="form-control "  />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 margin-bottom-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">CSO</div>
								<div class="font-size-9 line-height-18">CSO</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group">
								<input type="text" value="${erayFns:escapeStr(GodownEntryDetails[0].materialHistory.cso)}" id="cso"  name="cso"  maxlength="50" class="form-control "  />
							</div>
						</div>
					</div>
				     <div class="col-lg-6 col-sm-12 col-xs-12 padding-left-0 margin-bottom-0 padding-right-0 form-group">
						<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">存放要求</div>
							<div class="font-size-9 line-height-18">Storage Must</div>
						</label>
						<div class="col-lg-10 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
							<textarea class="form-control" id="cfyq" name="cfyq" maxlength="150" placeholder="">${GodownEntryDetails[0].materialHistory.cfyq}</textarea>
						</div>
					</div>
				     <div class="col-lg-6 col-sm-12 col-xs-12 padding-left-0 margin-bottom-0 padding-right-0 form-group">
						<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</label>
						<div class="col-lg-10 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
							<textarea class="form-control" id="jybz" name="jybz" maxlength="300" placeholder="">${GodownEntryDetails[0].materialHistory.bz}</textarea>
						</div>
					</div>
		
				   
				</div> 
			</div> 
			</form>
			
			<!-- 证书信息 -->
			 		<div class="panel panel-default" id="zsDiv">
			        <div class="panel-heading">
					  <h3 class="panel-title">证书信息  Certificate Info</h3>
				    </div>
					<div class="panel-body">	
				      	<div  class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow: auto;" >
							<table class="table table-thin table-bordered table-striped table-hover table-set" name="installationlist_certificate_table">
								<thead>
									<tr>
										<th class="colwidth-7" name="common_certificate_addTh">
											<button class="line6 line6-btn" name="common_certificate_addBtn" type="button">
												<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
											</button>
									    </th>
										<th class="colwidth-20">
										   <div class="font-size-12 line-height-12">证书类型</div>
									        <div class="font-size-9 line-height-12">Certificate Type</div>
										</th>
										<th class="colwidth-10">
										   <div class="font-size-12 line-height-12">证书编号</div>
									        <div class="font-size-9 line-height-12">Certificate No.</div>
										</th>
										<th class="colwidth-10">
										   <div class="font-size-12 line-height-12">存放位置</div>
									        <div class="font-size-9 line-height-12">Certificate Location</div>
										</th>
										<th class="colwidth-10">
										   <div class="font-size-12 line-height-12">签署日期</div>
									        <div class="font-size-9 line-height-12">Sign Date</div>
										</th>
										<th class="colwidth-7">
										   <div class="font-size-12 line-height-12">附件</div>
									        <div class="font-size-9 line-height-12">Attachment</div>
										</th>
									</tr>
								</thead>
										
								<tbody id="replace_certificate_list"><tr><td class="text-center" colspan="6">暂无数据 No data.</td></tr></tbody>
							</table>
						</div>
					</div>	
				</div>	
			
			
			 <div class="panel panel-default">
			        <div class="panel-heading">
					 <h3 class="panel-title">入库信息  Basic Information</h3>
				    </div>
						    
					<div class="panel-body">	
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
					<div class="clearfix"></div>
					
					<div class="panel-heading padding-left-0 padding-top-3  col-xs-12  " >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">仓库</div>
								<div class="font-size-9 line-height-18">Warehouse</div>
						</label>
						<div class="padding-left-10 col-lg-3 col-sm-3 col-xs-3" >
						<c:choose>
							<c:when test="${GodownEntryDetails[0].materialHistory==null}">
								<input type="hidden" id='ckhs'  />
							</c:when>
							<c:otherwise>
								<input type="hidden" id='ckhs'  value="${GodownEntryDetails[0].materialHistory.ckid}" />
							</c:otherwise>
							</c:choose>
						
							<select class="form-control" id="ckh" name="ckh"  onchange="onchage();">
							</select>
						</div>
						<c:choose>
							<c:when test="${godownEntry.hcmainData.gljb!=3}">
								<button id="btnGoAdd"  class="btn btn-primary " onclick="javascript:add()" >
									<i class="icon-plus cursor-pointer"></i>
								</button>
							</c:when>
						</c:choose>
						
						
					</div>
					
	            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">

						<!-- start:table -->
						<div class="margin-top-10 ">
						<div class="overflow-auto">
							<table class="table table-bordered table-striped table-hover table-set" style="min-width:900px">
								<thead>
							   		<tr>
										<th class="colwidth-5" style="vertical-align: middle;">
											<div class="font-size-12 notwrap">操作</div>
											<div class="font-size-9 notwrap">Operation</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 notwrap">库位</div>
											<div class="font-size-9 notwrap">NO.</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12 notwrap">序列号/批次号</div>
											<div class="font-size-9 notwrap">F.Name</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12 notwrap">数量</div>
											<div class="font-size-9 notwrap">CH.Name</div>
										</th>
							 		 </tr>
								</thead>
								<c:choose>
								<c:when test="${godownEntry.hcmainData.gljb==2}">
									<input type="hidden"  id='pchs'  value="${GodownEntryDetails[0].materialHistory.pch}"  readonly>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
								<tbody id="reserveTable">
								
								
								
								<c:forEach items="${GodownEntryDetails}" var="item" varStatus="status">
									<tr   style="cursor:pointer" >
										<input type="hidden"  name='godownEntryDetailsId'  value="${item.id}"  >
										<td style='vertical-align: middle;' class='text-center'>
										<c:choose>
											<c:when test="${godownEntry.hcmainData.gljb!=3}">
												<div>
												<i class='icon-trash color-blue cursor-pointer'  onClick="remove('', event)" title='删除 Delete'></i>
											</div>
											</c:when>
										</c:choose>
										</td>
										<input type="hidden"  name='kwhs'   value='${item.materialHistory.kwid}'  readonly>
										<input type="hidden"  name='kcllid'   value='${item.materialHistory.id}'  readonly>
										<td style='vertical-align: middle; ' align='center'>
											<select class='form-control' name='kwh' >
											</select>
										</td>
									
										<td style='vertical-align: middle; ' align='center'>
												<c:choose>
													<c:when test="${godownEntry.hcmainData.gljb==2}">
														<input type='text' class='form-control' name='pch'  value="${item.materialHistory.pch}"  readonly>
													</c:when>
													<c:otherwise>
														<input type='text' class='form-control' name='sn' value='${item.materialHistory.sn}'  readonly>
													</c:otherwise>
												</c:choose>
										</td>
										<td style='vertical-align: middle; ' align='center'>
											<input type='text' class='form-control' name='kcsl' value='${item.materialHistory.kcsl}' onkeyup='clearNoNum(this);'>
										</td>
									</tr>
								</c:forEach>
								
								</tbody>
							</table>
							</div>
						</div>
						<!-- end:table -->
			     		<div class="clearfix"></div>
					 </div> 
				</div>
				<div class="clearfix"></div>
				
			</div>
		</div>
		<div class="text-right margin-bottom-10">
                    <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:save()">
                    	<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
					</button>
					
					<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:submit()">
                    	<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
					</button>
				
               		<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:goreturn()">
               			<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
            	</div>
		<!-- 基本信息 End -->
</div>
<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
<%@ include file="/WEB-INF/views/open_win/contract_cost.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/godown/godown_stoctin.js"></script>

<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_certificate.jsp"%><!--证书弹出框  -->
<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script><!--生成uuid  -->
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>