<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>审核工卡</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
	<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
</head>
<body>
<input id="newFileName" type="hidden"/>
<input id="relativePath" type="hidden"/>
<input id="fileName" type="hidden"/>
<script type="text/javascript">
$(document).ready(function(){
//导航
Navigation(menuCode,"审核工卡","Review");
});
</script>
<script>
var pageParam = '${param.pageParam}';
</script>
	<div class="page-content ">
		<input type="hidden" id="dprtcode" value="${user.jgdm }"/>
		<input type="hidden" id="wxfabh" name="wxfabh"value="${jobCard.wxfabh }"/>
		<input type="hidden" id="djxmid" name="djxmid" value="${jobCard.djxmid }"/>
		<input type="hidden" id="djbh" name=djbh value="${erayFns:escapeStr(jobCard.djbh) }"/>
		<input type="hidden" id="djgznrid" name="djgznrid" value=""/>
		<input type="hidden" id="djgznrckgk" name="djgznrckgk" value=""/>
		<input type="hidden" id="djgznbid" name="djgznbid" value="${erayFns:escapeStr(jobCard.nbxh) }"/>
		<input type="hidden" id="gdjcid" name="gdjcid" value="${jobCard.gdjcid }"/>
		<input type="hidden" id="jobCardZy" name="jobCardZy" value="${erayFns:escapeStr(jobCard.zy) }"/>
		<input type="hidden" id="jobCardGzzw" name="jobCardGzzw" value="${erayFns:escapeStr(jobCard.gzzw) }"/>
		<input type="hidden" id="oldgkbh" name="oldgkbh" value="${erayFns:escapeStr(jobCard.gdbh) }"/>
		<input type="hidden" id="wxfabb" name="wxfabb" value="${jobCard.wxfabb }"/>
		<input type="hidden" id="jobCardId" name="jobCardId" value="${jobCard.id }"/>
		<input type="hidden" id="gzzId" name="gzzId" value="${jobCard.gzzId }"/>		
		<input type="hidden" id="jg" name="jg" value="0"/>
		<!-------导航Start--->

		<!-------导航End--->
		<!-- 查看工单Start -->
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>

			<div class="panel-body">
				    <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0  padding-right-0" >
					<div class="panel panel-default">
					        <div class="panel-heading">
								    <h3 class="panel-title">工卡基本信息</h3>
						   </div>
					<div class="panel-body">
					
				       <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>工卡编号</div>
							<div class="font-size-9 line-height-18">W/C No.</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="gkbh" name="gkbh" type="text" maxlength="20"  disabled="disabled"  value="${erayFns:escapeStr(jobCard.gdbh)}" />
						</div>
					  </div>
					  
				       <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">版本</div>
							<div class="font-size-9 line-height-18">Revision</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="bb" name="bb" type="text" maxlength="12" onkeyup='clearNoNum(this)'  disabled="disabled"  value="${jobCard.bb }" />
						</div>
					  </div>	
					  
					   <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">专业</div>
							<div class="font-size-9 line-height-18">Skill</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="zy" class="form-control " name="zy"  disabled="disabled" ></select>
						</div>
					  </div>	
					  
					   <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">频次</div>
							<div class="font-size-9 line-height-18">Frequencies</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="pc" name="pc" type="text" maxlength="50"   disabled="disabled"  value="${erayFns:escapeStr(jobCard.pc) }" />
						</div>
					  </div>
					  
					   <div class="clearfix"></div>
					  
					   <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>工卡类型</div>
							<div class="font-size-9 line-height-18">JobCard Type</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="gklx" class="form-control " name="gklx" onchange="gklxSelect()" disabled="disabled">
							<option value="">please choose</option>
							<option value="1" <c:if test="${jobCard.gklx eq 1 }">selected='selected'</c:if>>定检工卡</option>
							<option value="2" <c:if test="${jobCard.gklx eq 2 }">selected='selected'</c:if>>非定检工卡</option>
							</select>
						</div>
					  </div>
					  
					   <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">工作组</div>
							<div class="font-size-9 line-height-18">Work Group</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="gzgroup" class="form-control " name="gzgroup" disabled="disabled">
							</select>
						</div>
					  </div>
					  
					   <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>机型</div>
							<div class="font-size-9 line-height-18">Model
							</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 padding-right-0">
							<input class="form-control date-picker" id="jx" name="jx" type="text" disabled="disabled" value="${erayFns:escapeStr(jobCard.jx) }" />
						</div>
					  </div>
					  
					  <div id="lxSelect">
					  
				       <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">维修方案</div>
							<div class="font-size-9 line-height-18">Maintenance No.</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="xzwxfa" name="xzwxfa" type="text" disabled="disabled" value="${erayFns:escapeStr(jobCard.wxfabh) } ${erayFns:escapeStr(jobCard.wxfazwms) }" />
						</div>
					  </div>
					  
					  <div class="clearfix"></div>
					  
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">关联定检项目</div>
							<div class="font-size-9 line-height-18">Fixed Check Item</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="gldjxm" name="gldjxm" type="text" disabled="disabled" value="${erayFns:escapeStr(jobCard.djbh)} ${erayFns:escapeStr(jobCard.djxmzwms)}" />
						</div>
					  </div>
				       
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">定检工作内容</div>
							<div class="font-size-9 line-height-18">Contents</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="djgznr" name="djgznr" type="text" disabled="disabled" value="第${jobCard.nbxh }条" />
						</div>
					  </div>
						 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="zjhAndMc" name="zjhAndMc" type="text" disabled="disabled" value="${erayFns:escapeStr(jobCard.zjh)} ${erayFns:escapeStr(jobCard.zjhZwms)}" />
							<input class="form-control date-picker" id="zjh" name="zjh" type="hidden" disabled="disabled" value="${erayFns:escapeStr(jobCard.zjh) }" />
						</div>
					  </div>
					  </div>
					  
					  
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">厂家手册及版本</div>
							<div class="font-size-9 line-height-18">M and Revision</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="cksc" name="cksc" type="text"  maxlength="100" disabled="disabled" value="${erayFns:escapeStr(jobCard.cksc) }" />
						</div>
					  </div>	
					  
						<div class="clearfix"></div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">厂家工卡及版本</div>
							<div class="font-size-9 line-height-18">W/C and Revision</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="ckgk" name="ckgk" type="text"  maxlength="100" disabled="disabled" value="${erayFns:escapeStr(jobCard.ckgk) }" />
						</div>
					  </div>	
					  
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">工作地点</div>
							<div class="font-size-9 line-height-18">Workplace</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="gzdd" name="gzdd" type="text" maxlength="50" disabled="disabled" value="${erayFns:escapeStr(jobCard.gzdd) }" />
						</div>
					  </div>
					  
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">工作站位</div>
							<div class="font-size-9 line-height-18">Location</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							 <select id="gzzw" class="form-control " name="gzzw" disabled="disabled" >
							 	<option vlaue="-">-</option>
							 </select>
						</div>
					  </div>
					  
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">有效性</div>
							<div class="font-size-9 line-height-18">Effectivity</div>
						</label>
						<div class="col-xs-8  padding-left-8 padding-right-0 form-group">
							<label style="margin-right: 20px;font-weight: normal">
								<input name="yxx" type="radio" value="1" disabled="disabled" <c:if test="${jobCard.yxx eq 1 }">checked</c:if> />有效
							</label> 
							
							<label style="font-weight: normal">
								<input name="yxx" type="radio" value="0" disabled="disabled" <c:if test="${jobCard.yxx eq 0 }">checked</c:if> />无效
							</label> 
						</div>
					</div>
					
					  <div class="clearfix"></div>
					  
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">状态</div>
							<div class="font-size-9 line-height-18">State</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
							 <input class="form-control date-picker" id="zt" name="zt" type="text" maxlength="50" disabled="disabled" value="${jobCard.ztText }" />
						</div>
					  </div>	
					  
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">制单人</div>
							<div class="font-size-9 line-height-18">Creator</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker"  id="zdr" name="zdr" data-date-format="yyyy-mm-dd" type="text" disabled="disabled" value="${jobCard.zdr_user.displayName}" />
						</div>
					  </div>
					  
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">制单时间</div>
							<div class="font-size-9 line-height-18">Create Time</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							 <input class="form-control"  name="zdsj" value="<fmt:formatDate value='${jobCard.zdsj}' pattern='yyyy-MM-dd HH:mm:ss' />" id="zdsj" disabled="disabled"/>
						</div>
					  </div>
					  
					 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" id="lxSelectzjh">
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" style='margin-bottom:0px;'>
							<input class="form-control date-picker" id="fdjgkZjhName" name="fdjgkZjhName" type="text" disabled="disabled" value="${erayFns:escapeStr(jobCard.zjh)} ${erayFns:escapeStr(jobCard.zjhZwms)}" />
							<input class="form-control date-picker" id="fdjgkZjh" name="fdjgkZjh" type="hidden" disabled="disabled" value="${erayFns:escapeStr(jobCard.zjh) }" />
						</div>
					  </div>
					  
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">修订工卡</div>
								<div class="font-size-9 line-height-18">Notice</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" id="xdtzsDiv">
								<select class=" " id="xdtzsid" name="xdtzsid" multiple="multiple" disabled="disabled">
								
								</select>
							</div>
						</div>
						
						 <div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" style='margin-bottom:0px;' >
							<label  class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">标准工时</div>
								<div class="font-size-9 line-height-18">MHRS</div>
							</label>
							
							<div class=" col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group form-group" >
								<input type="text" maxlength="8" class="form-control " 
									id="jhgsRs" value="${jobCard.jhgsRs}" onkeyup='clearNoNumTwo(this)' readonly>
								<span class='input-group-addon' style="padding-left:0px;border:0px;background:none;">人 ×</span>
								<input maxlength="6" type="text" onkeyup='clearNoNumTwo(this)' class="form-control" id="jhgsXss" value="${jobCard.jhgsXss}" readonly sonkeyup='clearNoNumTwo(this)'>
								<span class='input-group-addon' style="padding-left:0px;padding-right:0px;border:0px;background:none;">时 ＝</span>
									<input type="text" class="form-control " id="bzgs" readonly>
							</div>
						</div>
						
						<div class="clearfix"></div>
							
							<div class=" col-lg-6 col-sm-6 col-xs-6  padding-left-0 margin-bottom-10 padding-right-0 form-group">
								<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>主题</div>
									<div class="font-size-9 line-height-18">Subject</div>
								</label>
								<div class="col-lg-10 col-sm-4 col-xs-2 padding-left-8 form-group padding-right-0 " style='margin-bottom:0px;'>
									<textarea class="form-control" id="zhut" name="zhut" placeholder='长度最大为600' maxlength="600" style="min-height:80px" disabled="disabled" >${erayFns:escapeStr(jobCard.gzzt) }</textarea>
								</div>
							</div>
							
							<div class=" col-lg-6 col-sm-6 col-xs-6  padding-left-0 margin-bottom-10 padding-right-0 form-group">
								<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
									<div class="font-size-12 line-height-18">补充文件</div>
									<div class="font-size-9 line-height-18">Files</div>
								</label>
								<div class="col-lg-10 col-sm-4 col-xs-2 padding-left-8 form-group padding-right-0 " style='margin-bottom:0px;'>
									<textarea class="form-control" id="bcwj" name="bcwj" placeholder='长度最大为100' maxlength="100" style="min-height:80px" disabled="disabled" >${erayFns:escapeStr(jobCard.bcwj) }</textarea>
								</div>
							</div>	
							<div class="clearfix"></div>
						 <label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 margin-bottom-10" >
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 margin-bottom-10 form-group padding-right-0">
								<textarea class="form-control" id="bz" name="bz" disabled="disabled"  maxlength="300" style="min-height:60px" >${erayFns:escapeStr(jobCard.bz)}</textarea>
							</div>
					     
				</div>		
			</div>
			</div>
				<!-- 航材，耗材 -->
			<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0  padding-right-0" >
					<div class="panel panel-default">
					        <div class="panel-heading">
								    <h3 class="panel-title">工作内容</h3>
						   </div>
					<div class="panel-body">
					    <div class="col-lg-12 col-md-12 padding-left-2" style="overflow-x: auto;">
						<table class="table table-thin table-bordered table-striped table-hover text-center" id="addtr" style="min-width:900px">
							<thead>
								<tr>
									<th width="7%"><div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div></th>
									<th><div class="font-size-12 line-height-18">工作内容</div>
										<div class="font-size-9 line-height-18">Contents</div></th>
									<th width="10%"><div class="font-size-12 line-height-18">工作人</div>
										<div class="font-size-9 line-height-18">Worker</div></th>
									<th width="8%"><div class="font-size-12 line-height-18">是否必检</div>
										<div class="font-size-9 line-height-18">Check</div></th>
								</tr>
							</thead>
							<tbody id="gznrList">
							</tbody>
							
						</table>
					</div>
					     
                             
				</div>		
			</div>
			</div>
			<!-- end 工作内容 -->
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0  padding-right-0" >
					<div class="panel panel-default">
					        <div class="panel-heading">
								    <h3 class="panel-title">相关工单（卡）</h3>
						   </div>
					<div class="panel-body">
					     <div class="col-lg-12 col-md-12 padding-left-2 "  style="overflow-x: auto;">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:900px" >
									<thead>
										<tr>
										<th width="3%"><div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-16">NO.</div></th>
										<th width="20%"><div class="font-size-12 line-height-18" >工单(卡)编号 </div><div class="font-size-9 line-height-18" >No.</div></th>
										<th><div class="font-size-12 line-height-18" >工单(卡)类型</div><div class="font-size-9 line-height-18" >Work Order Type</div></th>
										<th width="40%"><div class="font-size-12 line-height-18" >主题</div><div class="font-size-9 line-height-18" >Subject</div></th>
										<th width="10%"><div class="font-size-12 line-height-18" >专业</div><div class="font-size-9 line-height-18" >Skill</div></th>
						         	</tr></thead>
									     <tbody id="appendlistXggk">
									</tbody>	
								</table>
				   </div>
					     
                             
				</div>		
			</div>
			</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0  padding-right-0" >
					<div class="panel panel-default" >
					        <div class="panel-heading">
								    <h3 class="panel-title">航材耗材工具信息</h3>
						   </div>
					<div class="panel-body">
					     <div class="col-lg-12 col-md-12 padding-left-2"  style="overflow-x: auto;">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:900px" >
									<thead>
										<tr>
											<th width="5%"><div class="font-size-12 line-height-18">序号</div>
											<div class="font-size-9 line-height-16">NO.</div></th>
											<th width="15%"><div class="font-size-12 line-height-18">件号来源</div>
											<div class="font-size-9 line-height-16">Source</div></th>
											<th><div class="font-size-12 line-height-18">中文名称</div>
											<div class="font-size-9 line-height-16">CH.Name</div></th>
											<th><div class="font-size-12 line-height-18">英文名称</div>
											<div class="font-size-9 line-height-16">F.Name</div></th>
											<th><div class="font-size-12 line-height-18">件号</div>
											<div class="font-size-9 line-height-16">P/N</div></th>
											<th width="8%"><div class="font-size-12 line-height-18">在库数量</div>
											<div class="font-size-9 line-height-16">Stock Quantity</div></th>
											<th width="8%"><div class="font-size-12 line-height-18">使用数量</div>
											<div class="font-size-9 line-height-16">Use Quantity</div></th>
											<th><div class="font-size-12 line-height-18">类型</div>
											<div class="font-size-9 line-height-16">Type</div></th>
											<th width="30%"><div class="font-size-12 line-height-18">备注</div>
											<div class="font-size-9 line-height-16">Remark</div></th>
										</tr>
									</thead>     
									<tbody id="CKlist"></tbody>	
								</table>
				   </div>
					     
                             
				</div>		
			</div>
			</div>
			
			<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0  padding-right-0" >
					<div class="panel panel-default">
					        <div class="panel-heading">
								    <h3 class="panel-title">附件上传</h3>
						   </div>
					<div class="panel-body">	 	
					
					<div class="col-lg-12 col-md-12 padding-left-0 " style="overflow-x: auto;">
						<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:900px">
							<thead>
								<tr>
									<th  class=" colwidth-20">
									<div class="font-size-12 line-height-18">文件说明</div>
										<div class="font-size-9 line-height-18">File Explanation</div>
									</th>
									
									<th  class=" colwidth-5">
									 <div class="font-size-12 line-height-18">文件大小</div>
										<div class="font-size-9 line-height-18">File Size</div>
									</th>
									<th  class=" colwidth-7"><div class="font-size-12 line-height-18">上传人</div>
										<div class="font-size-9 line-height-18">Uploader </div></th>
									<th  class=" colwidth-7"><div class="font-size-12 line-height-18">上传时间</div>
										<div class="font-size-9 line-height-18">Upload Time</div></th> 				
								</tr>
							</thead>
							    <tbody id="filelist">
									 
								</tbody>
						</table>
					</div>
					</div>	
			</div>
			</div>
			
			
			<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0  padding-right-0" >
					<div class="panel panel-default">
					        <div class="panel-heading">
								    <h3 class="panel-title">审批 Approval</h3>
						   </div>
					 <div class="panel-body">
			        <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" >
				        <label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18">审核意见</div>
						<div class="font-size-9 line-height-18">Review Opinion</div></label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0">
							<textarea class="form-control" id="shyj" maxlength="150" >${erayFns:escapeStr(jobCard.shyj)}</textarea>
					</div>
					<div class="clearfix"></div>
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18"></div>
						<div class="font-size-9 line-height-18"></div></label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0">
						　<label style="margin-right: 50px"></label>　
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">审核人　<label style="margin-right: 50px; font-weight:normal">${jobCard.shr_user.displayName}</label></div>
						<div class="font-size-9 line-height-12">Reviewer</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18">审核时间 　<label style="margin-right: 50px; font-weight:normal"> <fmt:formatDate value='${jobCard.shsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
						<div class="font-size-9 line-height-12">Review Time </div>
						</div>
					
					
					</div>
					<div class="clearfix"></div>
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18">批准意见</div>
						<div class="font-size-9 line-height-12">Approval Opinion</div></label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0" >
							<textarea class="form-control" id="pfyj" disabled="disabled" maxlength="150" >${erayFns:escapeStr(jobCard.pfyj)}</textarea>
					</div>
					<div class="clearfix"></div>
						<label class="col-lg-1 col-sm-1 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						</label>
						<div class="col-lg-11 col-sm-11 col-xs-8 padding-left-8 padding-top-8 padding-right-0">
						　
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">批准人　<label style="margin-right: 50px; font-weight:normal">${jobCard.pfr_user.displayName}</label></div>
						<div class="font-size-9 line-height-12">Appr. By</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18">批准时间　<label style="margin-right: 50px; font-weight:normal"> <fmt:formatDate value='${jobCard.pfsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
						<div class="font-size-9 line-height-12">Approved Time</div>
						</div>
					</div>
				       
						 </div>		
					</div>	
			</div>
			</div>
			
			
				<div class=" col-lg-10 "></div>
				<div  class="col-lg-12 text-right padding-left-0 padding-right-0">
                     	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:agreedMain()"><div
										class="font-size-12">审核通过</div>
									<div class="font-size-9">Approved</div></button>
                     <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:rejectedMain()"><div
										class="font-size-12">审核驳回</div>
									<div class="font-size-9">Rejected</div></button>
                     	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:back()" type="button"><div
										class="font-size-12">返回</div>
									<div class="font-size-9">Back</div></button>
                 </div>
				
		 </div>  
		 
			</div>
		


	 <script src="${ctx}/static/js/thjw/common/preview.js"></script>
 <script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/view_jobCard.js"></script> 
</body>
</html>
