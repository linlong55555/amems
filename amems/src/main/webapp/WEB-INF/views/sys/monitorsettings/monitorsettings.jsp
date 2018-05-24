<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body>
	<input type="hidden" id="zzjgid" name="zzjgid" value=${user.jgdm} />
	<div class="page-content">
		<div class="panel panel-primary">
			<form action="updatethreshold" method="post" name="fo">
				<div class="pane panel-primary">
					<div class="panel-heading" id="NavigationBar"></div>
					<input type="hidden" id="adjustHeight" value="10">
					<div class="panel-body">
							<div class=" pull-left padding-left-5 padding-right-0 margin-bottom-10" style="width: 250px;">
								<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
								</span>
								<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<select id="dprtcode" class="form-control " name="dprtcode" onchange="gopage()">
											<c:if test="${dprtcode==user.orgcode}">
													<option value="-1">公共</option>
											</c:if>
											<c:forEach items="${accessDepartments}" var="type">
												<option value="${type.id}" >${erayFns:escapeStr(type.dprtname)}</option>
											</c:forEach>
										</select>
							</div>
						</div>	
					<!-- <div class=" pull-left padding-left-10 padding-right-0 margin-bottom-10">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="gopage();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                    </div>  -->
						<div
							class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">监控设置</h4>
								</div>
								<div class="panel-body">
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">
													<div class="font-size-12" id="JSWJDiv">适航性资料</div></label>&nbsp;&nbsp;&nbsp;&nbsp;<span>剩余评估期限&le;</span>
											<span><input type="text" style="width: 30px" id="JSWJfirst" name="JSWJfirst"></span>
											<span>天</span> <span> <input class="ys_club" disabled="disabled";type="text">
											</span> <span>标注</span>
										</div>
										<div class="col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0">
											<span id="first"></span> <span id="JSWJfirstcopy"></span><span>天&lt;剩余评估期限&le;</span>
											<input name="JSWJsecond" id="JSWJsecond" type="text" style="width: 25px"> <span>天</span>
											<span><input type="text" class="ye_club" disabled="disabled">
											</span> <span>标注</span>
											<img src="${ctx}/static/images/down.png" id="moreImge" onclick='showHideSubType()' style="width:20px;height:20px;cursor:pointer;">
										</div>
									</div>
								
								<div class="clearfix"></div>
								
								<!-- 子类 -->
								<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10" id="divSubType" style="display: none;">
									
								</div>
								<!-- 子类 -->
								
								<div class="clearfix"></div>
								
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">
													<div class="font-size-12" id="TGZLDiv">维护提示</div></label>&nbsp;&nbsp;&nbsp;&nbsp;<span>剩余通告期限&le;</span>
											<span><input type="text" style="width: 30px" id="TGZLfirst" name="TGZLfirst"></span>
											<span>天</span> <span> <input type="text" class="ys_club"disabled="disabled">
											</span> <span>标注</span>
										</div>

										<div
											class="col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0">
											<span id="first"></span> <span id="TGZLfirstcopy"></span><span>天&lt;剩余通告期限&le;</span>
											<input type="text" style="width: 25px" name="TGZLsecond" id="TGZLsecond">
											<span>天</span> <span><input type="text" class="ye_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
									</div>
								
								<div class="clearfix"></div>
								
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">
													<div class="font-size-12" id="GZBLDDiv">保留控制</div></label>&nbsp;&nbsp;&nbsp;&nbsp;<span>剩余保留期限&le;</span>
											<span><input type="text" style="width: 30px" id="GZBLDfirst" name="GZBLDfirst"></span>
											<span>天</span> <span> <input type="text" class="ys_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
										<div
											class="col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0">
											<span id="first"></span> <span id="GZBLDfirstcopy"></span><span>天&lt;剩余保留期限&le;</span>
											<input type="text" style="width: 25px" name="GZBLDsecond" id="GZBLDsecond"> <span>天</span> <span><input
												type="text" class="ye_club" disabled="disabled"> </span> <span>标注</span>
										</div>
									</div>
								
								<div class="clearfix"></div>
								
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">
												<div class="font-size-12" id="HCSMDiv">货架寿命</div></label>&nbsp;&nbsp;&nbsp;&nbsp;<span>剩余货架寿命&le;</span>
											<span><input type="text" style="width: 30px"
												id="HCSMfirst" name="HCSMfirst"></span>
											<span>天</span> <span> <input type="text"
												class="ys_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
										<div
											class="col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0">
											<span id="first"></span> <span id="HCSMfirstcopy"></span><span>天&lt;剩余货架寿命&le;</span>
											<input type="text" style="width: 25px" name="HCSMsecond" id="HCSMsecond"> <span>天</span> <span><input
												type="text" class="ye_club"
												disabled="disabled"> </span> <span>标注</span>
										</div>
									</div>
							
								<div class="clearfix"></div>
								
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">
													<div class="font-size-12" id="SQZZDiv">维修执照</div></label>&nbsp;&nbsp;&nbsp;<span>剩余期限&le;</span>
											<span><input type="text" style="width: 30px"
												id="SQZZfirst" name="SQZZfirst" ></span>
											<span>天</span> <span> <input type="text"
												class="ys_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
										<div
											class="col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0">
											<span id="SQZZfirst"></span> <span id="SQZZfirstcopy"></span><span>天&lt;剩余期限&le;</span>
											<input type="text" style="width: 25px" name="SQZZsecond" id="SQZZsecond"> <span>天</span> <span><input
												type="text" class="ye_club"
												disabled="disabled"> </span> <span>标注</span>
										</div>
									</div>
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">
													<div class="font-size-12" id="JXDQJKDiv">机型执照</div></label>&nbsp;&nbsp;&nbsp;<span>剩余期限&le;</span>
											<span><input type="text" style="width: 30px"
												id="JXDQJKfirst" name="JXDQJKfirst" ></span>
											<span>天</span> <span> <input type="text"
												class="ys_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
										<div
											class="col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0">
											<span id="JXDQJKfirst"></span> <span id="JXDQJKfirstcopy"></span><span>天&lt;剩余期限&le;</span>
											<input type="text" style="width: 25px" name="JXDQJKsecond" id="JXDQJKsecond"> <span>天</span> <span><input
												type="text" class="ye_club"
												disabled="disabled"> </span> <span>标注</span>
										</div>
									</div>
							
								<div class="clearfix"></div>
								
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">
													<div class="font-size-12" id="DTZZDiv">飞机三证</div></label>&nbsp;&nbsp;&nbsp;<span>剩余期限&le;</span>
											<span><input type="text" style="width: 30px"
												id="DTZZfirst" name="DTZZfirst"></span>
											<span>天</span> <span> <input type="text"
												class="ys_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
										<div
											class="col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0">
											<span id="DTZZfirst"></span> <span id="DTZZfirstcopy"></span><span>天&lt;剩余期限&le;</span>
											<input type="text" style="width: 25px" name="DTZZsecond" id="DTZZsecond"> <span>天</span> <span><input
												type="text" class="ye_club"
												disabled="disabled"> </span> <span>标注</span>
										</div>
									</div>
								
								<div class="clearfix"></div>
								
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">
												<div class="font-size-12" id="TDYQDiv">航材提订要求</div></label>&nbsp;&nbsp;&nbsp;<span>剩余期限&le;</span>
											<span><input type="text" style="width: 30px"
												id="TDYQfirst" name="TDYQfirst"></span>
											<span>天</span> <span> <input type="text"
												class="ys_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
										<div
											class="col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0">
											<span id="TDYQfirst"></span> <span id="TDYQfirstcopy"></span><span>天&lt;剩余期限&le;</span>
											<input type="text" style="width: 25px" name="TDYQsecond" id="TDYQsecond">
											<span>天</span> <span><input type="text"
												class="ye_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
									</div>
							
								<div class="clearfix"></div>
								
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">
													<div class="font-size-12" id="GJJKDiv">工具/设备监控</div></label>&nbsp;&nbsp;&nbsp;<span>剩余期限&le;</span>
											
											<span><input type="text" style="width: 30px" id="GJJKfirst" name="GJJKfirst" ></span>
											<span>天</span> <span> <input type="text"
												class="ys_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
										<div class="col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0">
											
											<span id="GJJKfirstcopy"></span> <span id="GJJKfirstcopy"></span><span>天&lt;剩余期限&le;</span>
												
											<input type="text" style="width: 25px"  name="GJJKsecond" id="GJJKsecond">
											<span>天</span> <span><input type="text"
												class="ye_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
									</div>
									
								<div class="clearfix"></div>
								
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">
													<div class="font-size-12" id="GYSYXQDiv">供应商授权有效期</div></label>&nbsp;&nbsp;&nbsp;<span>剩余期限&le;</span>
											
											<span><input type="text" style="width: 30px" id="GYSYXQfirst" name="GYSYXQfirst" ></span>
											<span>天</span> <span> <input type="text"
												class="ys_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
										<div class="col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0">
											
											<span id="GYSYXQfirstcopy"></span> <span id="GYSYXQfirstcopy"></span><span>天&lt;剩余期限&le;</span>
												
											<input type="text" style="width: 25px"  name="GYSYXQsecond" id="GYSYXQsecond">
											<span>天</span> <span><input type="text"
												class="ye_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
									</div>
									
								<div class="clearfix"></div>
								
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">
													<div class="font-size-12" id="ZWZLSCQXDiv">自我质量审查期限</div></label>&nbsp;&nbsp;&nbsp;<span>剩余期限&le;</span>
											
											<span><input type="text" style="width: 30px" id="ZWZLSCQXfirst" name="ZWZLSCQXfirst" ></span>
											<span>天</span> <span> <input type="text"
												class="ys_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
										<div class="col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0">
											
											<span id="ZWZLSCQXfirstcopy"></span> <span id="GJJKfirstcopy"></span><span>天&lt;剩余期限&le;</span>
												
											<input type="text" style="width: 25px"  name="ZWZLSCQXsecond" id="ZWZLSCQXsecond">
											<span>天</span> <span><input type="text"
												class="ye_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
									</div>
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">
													<div class="font-size-12" id="RYKCPXDiv">课程培训监控</div></label>&nbsp;&nbsp;&nbsp;<span>剩余期限&le;</span>
											
											<span><input type="text" style="width: 30px" id="RYKCPXfirst" name="RYKCPXfirst" ></span>
											<span>天</span> <span> <input type="text"
												class="ys_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
										<div class="col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0">
											
											<span id="RYKCPXfirstcopy"></span> <span id="RYKCPXfirstcopy"></span><span>天&lt;剩余期限&le;</span>
												
											<input type="text" style="width: 25px"  name="RYKCPXsecond" id="RYKCPXsecond">
											<span>天</span> <span><input type="text"
												class="ye_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
									</div>
									
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">
													<div class="font-size-12" id="GWDQJKDiv">岗位到期监控</div></label>&nbsp;&nbsp;&nbsp;<span>剩余期限&le;</span>
											
											<span><input type="text" style="width: 30px" id="GWDQJKfirst" name="GWDQJKfirst" ></span>
											<span>天</span> <span> <input type="text"
												class="ys_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
										<div class="col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0">
											
											<span id="GWDQJKfirstcopy"></span> <span id="GWDQJKfirstcopy"></span><span>天&lt;剩余期限&le;</span>
												
											<input type="text" style="width: 25px"  name="GWDQJKsecond" id="GWDQJKsecond">
											<span>天</span> <span><input type="text"
												class="ye_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
									</div>
									<div class="clearfix"></div>
									
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">
													<div class="font-size-12" id="ZLWTFKDiv">质量问题反馈</div></label>&nbsp;&nbsp;&nbsp;<span>剩余期限&le;</span>
											
											<span><input type="text" style="width: 30px" id="ZLWTFKfirst" name="ZLWTFKfirst" ></span>
											<span>天</span> <span> <input type="text"
												class="ys_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
										<div class="col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0">
											
											<span id="ZLWTFKfirstcopy"></span> <span id="ZLWTFKfirstcopy"></span><span>天&lt;剩余期限&le;</span>
												
											<input type="text" style="width: 25px"  name="ZLWTFKsecond" id="ZLWTFKsecond">
											<span>天</span> <span><input type="text"
												class="ye_club" disabled="disabled">
											</span> <span>标注</span>
										</div>
									</div>
														
									
								<div class="clearfix"></div>
							
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10">
										<div
											class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">
											<label
												class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height"><div
													class="font-size-12">生产监控：按单架飞机在《生产监控预警设置》功能中维护</div>
										</div>
										
									</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">提醒设置</h4>
							</div>
							<div class="panel-body">
								<label
									class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height"><div
										class="font-size-12">培训提醒设置</div></label>&nbsp;&nbsp;&nbsp;&nbsp; 
								
								<input type="checkbox" name="txsz" id="txsz" > <span>培训前,提前</span>
									
								<input name="pxtx" type="text" style="width: 35px" id="pxts">
								
								<select id="txlx" name="txlx">
									<option value="11">天</option>
									<option value="12">月</option>
									<option value="13">年</option>
								</select> <span>对培训人进行提醒</span>
							</div>
						</div>
						
						</div>
						<div class="pull-right">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" data-dismiss="modal"
								onclick="update()"  permissioncode="sys:setting:monitorsettings:01">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctx }/static/js/thjw/sys/monitorsettings.js"></script>
	<script type="text/javascript" src="${ctx }/static/js/tool/jsMap/jsMap.js"></script><!-- jsMap工具类 -->
	
</body>
</html>
