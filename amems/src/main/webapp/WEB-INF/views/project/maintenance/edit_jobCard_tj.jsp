<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
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
Navigation(menuCode,"修改工卡","Edit");
});
var pageParam = '${param.pageParam}';
</script>
<style type="text/css">

.multiselect{
overflow:hidden;
}
</style>
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
		<input type="hidden" id="jobCardDprtcode" name="jobCardDprtcode" value="${jobCard.dprtcode }"/>
		<input type="hidden" id="jobCardJx" name="jobCardJx" value="${jobCard.jx }"/>
		<input type="hidden" id="gzzId" name="gzzId" value="${jobCard.gzzId }"/>
		<input type="hidden" id="workgroupboolean" name="workgroupboolean" value="${workgroup}"/>
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
					<form id="form">
					
				       <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>工卡编号</div>
							<div class="font-size-9 line-height-18">W/C No.</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="gkbh" name="gkbh" type="text" maxlength="20" value="${erayFns:escapeStr(jobCard.gdbh)}" />
						</div>
					  </div>
					  
				       <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">版本</div>
							<div class="font-size-9 line-height-18">Revision</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="bb" name="bb" type="text" maxlength="12" onkeyup='clearNoNum(this)' value="${jobCard.bb }" />
						</div>
					  </div>	
					  
					   <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">专业</div>
							<div class="font-size-9 line-height-18">Skill</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="zy" class="form-control " name="zy"></select>
						</div>
					  </div>	
					  
					   <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">频次</div>
							<div class="font-size-9 line-height-18">Frequencies</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="pc" name="pc" type="text" maxlength="50"  value="${erayFns:escapeStr(jobCard.pc) }" />
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
							<select id="gzgroup" class="form-control " name="gzgroup" >
							</select>
						</div>
					  </div>
					  
						
					   <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>机型</div>
							<div class="font-size-9 line-height-18">Model
							</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class="form-control " id="jx" name="jx" onchange="openWxfa()">
										<option value="">please choose</option>
							</select>
						</div>
					  </div>
					  
					  <div id="lxSelect">
					  
				       <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">维修方案</div>
							<div class="font-size-9 line-height-18">Maintenance No.</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="xzwxfa" name="xzwxfa" type="text" disabled="disabled" value="${jobCard.wxfabh } ${erayFns:escapeStr(jobCard.wxfazwms) }" />
						</div>
					  </div>
					  <div class="clearfix"></div>
					  
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">关联定检项目</div>
								<div class="font-size-9 line-height-18">Fixed Check Item</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left">
								<div class="col-lg-9 col-sm-10 col-xs-10 padding-left-0 padding-right-0 ">
									<input class="form-control date-picker" value="${jobCard.djbh } ${erayFns:escapeStr(jobCard.djxmzwms) }"  id="gldjxm" name="gldjxm" type="text" disabled="disabled" />
								</div>
								<div class="col-lg-3 col-sm-2 col-xs-2 padding-left-0 padding-right-0 ">
									<button class="btn btn-primary form-control" data-toggle="modal" onclick="openDjxm();" type="button">
										<i class="icon-search cursor-pointer"></i>
									</button>
								</div>
							</div>       
							
						</div>
				       
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">定检工作内容</div>
								<div class="font-size-9 line-height-18">Contents</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left">
								<div class="col-lg-9 col-sm-10 col-xs-10 padding-left-0 padding-right-0 ">
									<input class="form-control date-picker" id="djgznr" name="djgznr" type="text" value="第${jobCard.nbxh }条" disabled="disabled" />
								</div>
								<div class="col-lg-3 col-sm-2 col-xs-2 padding-left-0 padding-right-0 ">
								<button class="btn btn-primary form-control" data-toggle="modal" onclick="openDjgznr();" type="button">
									<i class="icon-search cursor-pointer"></i>
								</button>
								</div>
							</div>
						</div>
						 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
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
							<input class="form-control date-picker" id="cksc" name="cksc" type="text"  maxlength="100" value="${erayFns:escapeStr(jobCard.cksc) }" />
						</div>
					  </div>	
						<div class="clearfix"></div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">厂家工卡及版本</div>
							<div class="font-size-9 line-height-18">W/C and Revision</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="ckgk" name="ckgk" type="text"  maxlength="100" value="${erayFns:escapeStr(jobCard.ckgk) }" />
						</div>
					  </div>	
					  
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">工作地点</div>
							<div class="font-size-9 line-height-18">Workplace</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" id="gzdd" name="gzdd" type="text" maxlength="50" value="${erayFns:escapeStr(jobCard.gzdd) }" />
						</div>
					  </div>
					  
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">工作站位</div>
							<div class="font-size-9 line-height-18">Location</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							 <select id="gzzw" class="form-control " name="gzzw" >
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
								<input name="yxx" type="radio" value="1" <c:if test="${jobCard.yxx eq 1 }">checked</c:if> />有效
							</label> 
							
							<label style="font-weight: normal">
								<input name="yxx" type="radio" value="0" <c:if test="${jobCard.yxx eq 0 }">checked</c:if> />无效
							</label> 
						</div>
					</div>
					  	
					  <div class="clearfix"></div>
					  
					<%--  <div class=" col-lg-6 col-sm-6 col-xs-12   padding-left-0 padding-right-0  form-group" style="margin-bottom:0px;">
							<label  class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>标准工时</div>
								<div class="font-size-9 line-height-18">MHRS</div>
							</label>
							<div class=" col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group form-group">
								<div class="input-group">
									<input type="text" maxlength="8" class="form-control " 
										id="jhgsRs" value="${jobCard.jhgsRs}" onkeyup='clearNoNumTwo(this)'>
									<span class='input-group-addon' style="padding-left:0px;border:0px;background:none;">人 ×</span>
									<input maxlength="6" type="text" onkeyup='clearNoNumTwo(this)' class="form-control" id="jhgsXss" value="${jobCard.jhgsXss}" sonkeyup='clearNoNumTwo(this)'>
									<span class='input-group-addon' style="padding-left:0px;padding-right:0px;border:0px;background:none;">时 ＝</span>
										<input type="text" class="form-control " id="bzgs" readonly>
								</div>
							</div>
						</div> --%>
						
						<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>标准工时</div>
								<div class="font-size-9 line-height-18">MHRS</div>
							</label>
							
							<div class=" col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group form-group" style='margin-bottom:0px;'>
								<div class="input-group">
									<input type="text" maxlength="8" class="form-control " name="jhgsRs" 
										id="jhgsRs" value="${jobCard.jhgsRs}" onkeyup='clearNoNumTwo(this)' >
									<span class='input-group-addon' style="padding-left:0px;border:0px;background:none;">人 ×</span>
									<input maxlength="6" type="text" onkeyup='clearNoNumTwo(this)' class="form-control" id="jhgsXss" name="jhgsXss" value="${jobCard.jhgsXss}" sonkeyup='clearNoNumTwo(this)'>
									<span class='input-group-addon' style="padding-left:0px;padding-right:0px;border:0px;background:none;">时 ＝</span>
										<input type="text" class="form-control " id="bzgs" readonly>
								</div>
							</div>
						
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">修订工卡</div>
								<div class="font-size-9 line-height-18">Notice</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" id="xdtzsDiv">
								<select class=" " id="xdtzsid" name="xdtzsid" multiple="multiple">
								
								</select>
							</div>
						</div>
						
						
						 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">状态</div>
							<div class="font-size-9 line-height-18">State</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							 <input class="form-control date-picker" id="zt" name="zt" type="text" maxlength="50" disabled="disabled" value="${jobCard.ztText }" />
						</div>
					  </div>	
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">制单人</div>
							<div class="font-size-9 line-height-18">Creator</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker"  id="zdr" name="zdr" data-date-format="yyyy-mm-dd" type="text" disabled="disabled" value="${erayFns:escapeStr(jobCard.zdr_user.displayName)}" />
						</div>
					  </div>
					  <div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group"   >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">制单时间</div>
							<div class="font-size-9 line-height-18">Create Time</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0"  style='margin-bottom:0px;'>
							 <input class="form-control"  name="zdsj" value="<fmt:formatDate value='${jobCard.zdsj}' pattern='yyyy-MM-dd HH:mm:ss' />" id="zdsj" disabled="disabled"/>
						</div>
					  </div>
					
						 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">ATA章节号</div>
								<div class="font-size-9 line-height-18">ATA</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left">
								<div class="col-lg-9 col-sm-10 col-xs-10 padding-left-0 padding-right-0 ">
									<input type="text"  id="fdjgkZjhName" class="form-control" disabled="disabled" value="${erayFns:escapeStr(jobCard.zjh)} ${erayFns:escapeStr(jobCard.zjhZwms)}" />
								    <input type="hidden"  id="fdjgkZjh" class="form-control" value="${erayFns:escapeStr(jobCard.zjh) }"  />
								</div>
								<div class="col-lg-3 col-sm-2 col-xs-2 padding-left-0 padding-right-0 ">
									<button class="btn btn-primary form-control" data-toggle="modal" onclick="openChapterWin();" type="button">
										<i class="icon-search cursor-pointer"></i>
									</button>
								</div>
							</div>                   
						</div>
							
						<div class="clearfix"></div>
						
							<div class=" col-lg-6 col-sm-6 col-xs-6  padding-left-0 margin-bottom-10 padding-right-0 form-group">
								<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>主题</div>
									<div class="font-size-9 line-height-18">Subject</div>
								</label>
								<div class="col-lg-10 col-sm-4 col-xs-2 padding-left-8 form-group padding-right-0 " style='margin-bottom:0px;'>
									<textarea class="form-control" id="zhut" name="zhut" placeholder='长度最大为600' maxlength="600" style="min-height:80px" >${erayFns:escapeStr(jobCard.gzzt) }</textarea>
								</div>
							</div>
							
							<div class=" col-lg-6 col-sm-6 col-xs-6  padding-left-0 margin-bottom-10 padding-right-0 form-group">
								<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
									<div class="font-size-12 line-height-18">补充文件</div>
									<div class="font-size-9 line-height-18">Files</div>
								</label>
								<div class="col-lg-10 col-sm-4 col-xs-2 padding-left-8 form-group padding-right-0 " style='margin-bottom:0px;'>
									<textarea class="form-control" id="bcwj" name="bcwj" placeholder='长度最大为100' maxlength="100" style="min-height:80px" >${erayFns:escapeStr(jobCard.bcwj) }</textarea>
								</div>
							</div>	
						<div class="clearfix"></div>
							
						 <label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 margin-bottom-10" >
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 margin-bottom-10 form-group padding-right-0" >
								<textarea class="form-control" id="bz" name="bz" placeholder='长度最大为300' maxlength="300" style="min-height:60px">${erayFns:escapeStr(jobCard.bz)}</textarea>
							</div>
					      </form>
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
					    <div class="col-lg-12 col-md-12 padding-left-2"  style="overflow-x: auto;">
						<table class="table table-thin table-bordered table-striped table-hover text-center" id="addtr" style="min-width:900px">
							<thead>
								<tr>
								   <th style="width: 40px;">
								         <button id="addTable"  onclick="addTr()">
									     <i class="icon-plus cursor-pointer color-blue cursor-pointer'" ></i>
								        </button>
		 						   </th>	
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
					     <div class="col-lg-12 col-md-12 padding-left-2 " style="overflow-x: auto;">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set" id="relatedWorkOrder" style="min-width:900px">
									<thead>
										<tr>
											<th class="colwidth-5">
											<a href="javascript:openXggk();"  >
												<button><i class="icon-search cursor-pointer" ></i></button>
											</a>
										</th >
										<th class="colwidth-3"><div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-16">NO.</div></th>
										<th class="colwidth-12"><div class="font-size-12 line-height-18" >工单(卡)编号 </div><div class="font-size-9 line-height-18" >No.</div></th>
										<th class="colwidth-13"><div class="font-size-12 line-height-18" >工单(卡)类型</div><div class="font-size-9 line-height-18" >Work Order Type</div></th>
										<th class="colwidth-30"><div class="font-size-12 line-height-18" >主题</div><div class="font-size-9 line-height-18" >Subject</div></th>
										<th class="colwidth-10"><div class="font-size-12 line-height-18" >专业</div><div class="font-size-9 line-height-18" >Skill</div></th>
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
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set" id="airMaterialTools" style="min-width:900px">
									<thead>
										<tr>
											<th class="colwidth-5">
											<a href="javascript:openHcxxList();"  >
												<button><i class="icon-search cursor-pointer" ></i></button>
											</a>
											</th>
											<th class="colwidth-3"><div class="font-size-12 line-height-18">序号</div>
											<div class="font-size-9 line-height-16">NO.</div></th>
											<th class="colwidth-10" ><div class="font-size-12 line-height-18">件号来源</div>
											<div class="font-size-9 line-height-16">Source</div></th>
											<th class="colwidth-10"><div class="font-size-12 line-height-18">中文名称</div>
											<div class="font-size-9 line-height-16">CH.Name</div></th>
											<th class="colwidth-10"><div class="font-size-12 line-height-18">英文名称</div>
											<div class="font-size-9 line-height-16">F.Name</div></th>
											<th class="colwidth-10"><div class="font-size-12 line-height-18">件号</div>
											<div class="font-size-9 line-height-16">P/N</div></th>
											<th class="colwidth-10"><div class="font-size-12 line-height-18">在库数量</div>
											<div class="font-size-9 line-height-16">Stock Quantity</div></th>
											<th class="colwidth-5"><div class="font-size-12 line-height-18">使用数量</div>
											<div class="font-size-9 line-height-16">Use Quantity</div></th>
											<th class=" colwidth-5"><div class="font-size-12 line-height-18">类型</div>
											<div class="font-size-9 line-height-16">Type</div></th>
											<th class="colwidth-15"><div class="font-size-12 line-height-18">备注</div>
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
					
					 <div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class=" col-lg-2 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
							<div class="font-size-9 line-height-18">File Explanation</div>
						</label>
						<div class=" col-lg-10 col-sm-10 col-xs-9 padding-left-8 padding-right-0 input-group">
							<div class='input-group'>
							
							<input type="text" id="wbwjm" name="wbwjm" maxlength="100" class="form-control "  >
								<span class='input-group-btn'>
									<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-12 "  style="margin-left: 0px ;padding-left: 0"></div>
								</span>
					    	</div>
						</div>
						
					</div>
					<div class="clearfix"></div>
					<div class="col-lg-12 col-md-12 padding-left-0 " style="overflow-x: auto;">
						<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:900px">
							<thead>
								<tr>
									<th class=" colwidth-3"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th class=" colwidth-20">
									<div class="font-size-12 line-height-18">文件说明</div>
										<div class="font-size-9 line-height-18">File Explanation</div>
									</th>
									
									<th class=" colwidth-5">
									<div class="font-size-12 line-height-18">文件大小</div>
										<div class="font-size-9 line-height-18">File Size</div>
									</th>
									<th class=" colwidth-7"><div class="font-size-12 line-height-18">上传人</div>
										<div class="font-size-9 line-height-18">Uploader </div></th>
									<th class=" colwidth-7"><div class="font-size-12 line-height-18">上传时间</div>
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
			
			<div class="clearfix"></div>
			<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0  padding-right-0" >
					<div class="panel panel-default">
					        <div class="panel-heading">
								    <h3 class="panel-title">审批 Approval</h3>
						   </div>
					 <div class="panel-body">
			        <div class="col-lg-12  padding-left-0 padding-right-0">
				       <label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18">审核意见</div>
						<div class="font-size-9 line-height-18">Review Opinion</div></label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0 ">
							<textarea class="form-control" id="shyj" disabled="disabled">${erayFns:escapeStr(jobCard.shyj)}</textarea>
					</div>
					<div class="clearfix"></div>
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18"></div>
						<div class="font-size-9 line-height-18"></div></label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0 ">
						　<label style="margin-right: 50px"></label>　
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">审核人　<label style="margin-right: 50px; font-weight:normal">${erayFns:escapeStr(jobCard.shr_user.displayName)}</label></div>
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
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0 ">
							<textarea class="form-control" id="pfyj" disabled="disabled">${erayFns:escapeStr(jobCard.pfyj)}</textarea>
					</div>
					<div class="clearfix"></div>
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0 ">
						　
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">批准人　<label style="margin-right: 50px; font-weight:normal">${erayFns:escapeStr(jobCard.pfr_user.displayName)}</label></div>
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
				
				<div class=" pull-right margin-bottom-10">
                        <button id="edit" name="edit" class="btn btn-primary padding-top-1 padding-bottom-1" type="button">
                    	 <div class="font-size-12">保存</div>
					 	 <div class="font-size-9">Save</div>
					 </button>
					 	 
                     <button class="btn btn-primary padding-top-1 padding-bottom-1" id="submit" name="submit" type="button">
                     	 <div class="font-size-12">提交</div>
						 <div class="font-size-9">Submit</div>
					 </button>
                      
                     	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:back()"><div
										class="font-size-12">返回</div>
						<div class="font-size-9">Back</div></button>
                 </div>
				
		 </div>  
		 
			</div>
		
		

<!-- 	航材耗材的模态框 -->

	<div class="modal fade" id="alertModalHcxx" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:60%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">航材耗材工具信息</div>
							<div class="font-size-9 ">Material Info</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
                              <div class="col-lg-9 pull-right padding-right-0">
					            	<div class="col-lg-6 padding-left-0 padding-top-10">
					            	    <label class="col-xs-4 text-right padding-left-0 padding-right-2">
											<div class="font-size-12 line-height-18">类型</div>
											<div class="font-size-9 line-height-18">Type</div>
										</label>
					            	    <div class="col-lg-8 pull-left padding-right-0 padding-left-0 ">
				            	            <select  id="hclx" name="hclx" class="form-control"  onchange="changeHclx()">
					         		            <option value="1,4,5" select="select">航材耗材</option>
					         		            <option value="2,3">工具设备</option>
								            </select>
									    </div>  
									    </div>
										   <div  class="col-lg-6 padding-right-0 padding-left-0   padding-top-10 pull-right" >
										    <div class="col-lg-10  padding-right-8">
										   <input type="text" placeholder="中文名称/英文名称/部件号" id="keyword_search" class="form-control ">
					                       </div>
					                       <div class="col-lg-2 pull-right padding-left-0 padding-right-0">   
											<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();">
												<div class="font-size-12">搜索</div>
												<div class="font-size-9">Search</div>
					                   		</button>
					                       </div>
									    
					            </div>
					            </div>
					            
				           <div class="clearfix"></div>	     
				                
								<!-- start:table -->
								<div  style="margin-top: 20px">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set">
												<thead>
												<tr>
												<th class="colwidth-3"><div class="font-size-12 line-height-18" >操作</div>
												<div class="font-size-9 line-height-18" >Operation</div></th>
										     	                <!-- <th><div class="font-size-12 line-height-18">序号</div>
																		<div class="font-size-9 line-height-16">NO.</div></th>   -->
																<th class="colwidth-10"><div class="important"><div class="font-size-12 line-height-18">英文名称</div>
																<div class="font-size-9 line-height-16">F.Name</div></div></th>
																<th class="colwidth-10"><div class="important"><div class="font-size-12 line-height-18">中文名称</div>
																<div class="font-size-9 line-height-16">CH.Name</div></div></th>
																<th class="colwidth-13"><div class="important"><div class="font-size-12 line-height-18">部件号</div>
																<div class="font-size-9 line-height-16">P/N</div></div></th>
																<th class="colwidth-5"><div class="font-size-12 line-height-18">在库数量</div>
																<div class="font-size-9 line-height-16">Stock Quantity</div></th>
																<th class="colwidth-5"><div class="font-size-12 line-height-18">计量单位</div>
																<div class="font-size-9 line-height-16">Unit</div></th>
																<th class="colwidth-5"><div class="font-size-12 line-height-18">航材类型</div>
																<div class="font-size-9 line-height-16">Airmaterial type</div></th>
												</tr> 
								         		 </thead>
												<tbody id="Hcxxlist">
												</tbody>
									</table>
									</div>
									<!-- <div class="col-xs-12 text-center">
										<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" id="pagination">
										</ul>
									</div> -->
								<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
									</div>
								<!-- end:table -->
			                	<div class="modal-footer" style="border-top: medium none ! important;">
									<button type="button" onclick="appendHcxx()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
									
				                </div>
					     		<div class="clearfix"></div>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-------航材耗材 End-------->
	
	<!-- 	工单的模态宽 模态框 -->
	<div class="modal fade" id="alertModalGlgd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">工单信息列表</div>
							<div class="font-size-9 ">Word Order List</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
                               <div class="col-lg-6 pull-right padding-right-0">
				            	<div class="col-lg-12 padding-left-0" style="margin-top: 20px">
								    <div class="col-lg-9 pull-left padding-right-0 padding-left-0 ">
									<input type="text" placeholder="Please enter a keyword..." id="keyword_search2" class="form-control ">
								</div>
			                    <div class="col-lg-3 pull-right">   
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision2();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                       </div>
					         	</div>
					            </div>
					            
				           <div class="clearfix"></div>	     
				                
								<!-- start:table -->
								<div  style="margin-top: 20px">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set">
												<thead>
												<tr>
												<th class="colwidth-5"><div class="font-size-12 line-height-18" >选择</div>
												<div class="font-size-9 line-height-18" >Choice</div></th>
												<th class="colwidth-7"><div class="font-size-12 line-height-18">工单编号</div>
												<div class="font-size-9 line-height-16">SerialNumber</div></th>
												<th class="colwidth-5"><div class="font-size-12 line-height-18">工单类型</div>
												<div class="font-size-9 line-height-16">WorkOrder Type</div></th>
												<th class="colwidth-5"><div class="font-size-12 line-height-18">专业</div>
												<div class="font-size-9 line-height-16">Major</div></th>
												<th class="colwidth-30"><div class="font-size-12 line-height-18">备注</div>
												<div class="font-size-9 line-height-16">Remark</div></th>
												</tr>  
								         		 </thead>
												<tbody id="glgdCardlist2">
												</tbody>
									</table>
									</div>
									<div class="col-xs-12 text-center">
										<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" id="pagination2">
										</ul>
									</div>
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="appendGlgd()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
									
				                </div>
					     		<div class="clearfix"></div>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	<!-------工单的模态宽 End-------->
	
	
	<!-- 	维修方案的模态宽 模态框 -->
	<div class="modal fade" id="alertModalWxfa" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">维修方案列表</div>
							<div class="font-size-9 ">Word Order List</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
                               <div class="col-lg-6 pull-right padding-right-0">
				            	<div class="col-lg-12 padding-left-0" style="margin-top: 20px">
								    <div class="col-lg-9 pull-left padding-right-0 padding-left-0 ">
									<input type="text" placeholder="Please enter a keyword..." id="keyword_search_wxfa" class="form-control ">
								</div>
			                    <div class="col-lg-3 pull-right">   
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchWxfa();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                       </div>
					         	</div>
					            </div>
					            
				           <div class="clearfix"></div>	     
				                
								<!-- start:table -->
								<div  style="margin-top: 20px">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set">
												<thead>
												<tr>
													<th>
														<div class="font-size-12 line-height-18 " >操作</div>
														<div class="font-size-9 line-height-18">Operation</div>
													</th>
													<th class="sorting" onclick="orderBy('wxfabh')" id="wxfabh_order">
														<div class="font-size-12 line-height-18">维修方案编号</div>
														<div class="font-size-9 line-height-18">Maintenance Code</div>
													</th>
													<th class="sorting" onclick="orderBy('jx')" id="jx_order">
														<div class="font-size-12 line-height-18">机型</div>
														<div class="font-size-9 line-height-18">Model</div>
													</th>
													<th class="sorting" onclick="orderBy('zwms')" id="zwms_order">
														<div class="font-size-12 line-height-18">维修方案名称</div>
														<div class="font-size-9 line-height-18">Maintenance Name</div>
													</th>
													<th class="sorting" onclick="orderBy('bb')" id="bb_order">
														<div class="font-size-12 line-height-18">版本</div>
														<div class="font-size-9 line-height-18">Version</div>
													</th>
													<th class="sorting" onclick="orderBy('jh_sxrq')" id="jh_sxrq_order">
														<div class="font-size-12 line-height-18">计划生效日期</div>
														<div class="font-size-9 line-height-18">Plan Effective</div>
													</th>
													<th class="sorting" onclick="orderBy('sj_sxrq')" id="sj_sxrq_order">
														<div class="font-size-12 line-height-18">实际生效日期</div>
														<div class="font-size-9 line-height-18">Actual Effective</div>
													</th>
													
												</tr>
								         		 </thead>
												<tbody id="listWxfa">
												</tbody>
									</table>
									</div>
									<div class="col-xs-12 text-center">
										<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" id="paginationWxfa">
										</ul>
									</div>
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="appendWxfa()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
									
				                </div>
					     		<div class="clearfix"></div>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	<!-------维修方案的模态宽 End-------->
	
	
	
	<!-- 	定检项目的模态宽 模态框 -->
	<div class="modal fade" id="alertModalDjxm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:80%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">定检项目列表</div>
							<div class="font-size-9 ">Word Order List</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
                              <div class="col-lg-4 pull-right padding-right-0">
				            	<div class="col-lg-12 padding-left-0" style="margin-top: 20px">
								    <div class="col-lg-10 pull-left padding-right-0 padding-left-0 ">
									<input type="text" placeholder="定检编号/定检名称/版本/备注" id="keyword_search_Djxm" class="form-control ">
								</div>
			                    <div class="col-lg-2 pull-right">   
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchDjxm();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                       </div>
					         	</div>
					            </div>
					            
				           <div class="clearfix"></div>	     
				                
								<!-- start:table -->
								<div  style="margin-top: 20px;">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set"  style="min-width: 80% !important">
												<thead>
												<tr>
													<th class=" colwidth-3">
														<div class="font-size-12 line-height-18 " >操作</div>
														<div class="font-size-9 line-height-18">Operation</div>
													</th>
													<!-- <th class="sorting" onclick="orderBy('pxh')" id="pxh_order">
														<div class="font-size-12 line-height-18">排序号</div>
														<div class="font-size-9 line-height-18">Order No</div>
													</th> -->
													<th class=" colwidth-7">
														<div class="important">
														<div class="font-size-12 line-height-18">定检编号</div>
														<div class="font-size-9 line-height-18">Fixed Check No.</div>
														</div>
													</th>
													<th class=" colwidth-7">
														<div class="important">
														<div class="font-size-12 line-height-18">定检名称</div>
														<div class="font-size-9 line-height-18">Fixed Check Name</div>
														</div>
													</th>
													
													<!-- <th class="sorting" onclick="orderBy('bz')" id="bz_order">
														<div class="font-size-12 line-height-18">审批状态</div>
														<div class="font-size-9 line-height-18">State</div>
													</th> -->
													<th class=" colwidth-5">
														<div class="font-size-12 line-height-18">有效性</div>
														<div class="font-size-9 line-height-18">Effectivity</div>
													</th>
													<th class=" colwidth-5">
														<div class="important">
														<div class="font-size-12 line-height-18">版本</div>
														<div class="font-size-9 line-height-18">Revision</div>
														</div>
													</th>
													<th class=" colwidth-10">
														<div class="font-size-12 line-height-18">监控条件</div>
														<div class="font-size-9 line-height-18">Condition</div>
													</th>
													<th class=" colwidth-7">
														<div class="font-size-12 line-height-18">实际生效日期</div>
														<div class="font-size-9 line-height-18">Actual Effective Date</div>
													</th>
													<th class=" colwidth-20">
														<div class="important">
														<div class="font-size-12 line-height-18">备注</div>
														<div class="font-size-9 line-height-18">Remark</div>
														</div>
													</th>
													
												</tr>
								         		 </thead>
												<tbody id="listDjxm">
												</tbody>
									</table>
									</div>
									<!-- <div class="col-xs-12 text-center">
										<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" id="paginationDjxm">
										</ul>
									</div> -->
								<div class="col-xs-12 text-center padding-right-0 page-height " id="paginationDjxm">
									</div>
								<!-- end:table -->
			                	<div class="modal-footer" style="border-top: medium none ! important;">
									<button type="button" onclick="appendDjxm()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
									
				                </div>
					     		<div class="clearfix"></div>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	<!-------定检项目的模态宽 End-------->
	
	<!-- 	定检工作内容的模态宽 模态框 -->
	<div class="modal fade" id="alertModalDjgznr" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:60%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">定检工作内容列表</div>
							<div class="font-size-9 ">List of Fixed Check Contents</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
					            
								<!-- start:table -->
								<div  style="margin-top: 20px;">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set"  style="min-width: 80% !important">
												<thead>
												<tr>
													<th class=" colwidth-3">
														<div class="font-size-12 line-height-18 " >操作</div>
														<div class="font-size-9 line-height-18">Operation</div>
													</th>
													<th class=" colwidth-3">
														<div class="font-size-12 line-height-18">序号</div>
														<div class="font-size-9 line-height-18">No.</div>
													</th>
													<th class=" colwidth-5">
														<div class="font-size-12 line-height-18">ATA章节号</div>
														<div class="font-size-9 line-height-18">ATA</div>
													</th>
												<!-- 	<th >
														<div class="font-size-12 line-height-18">工作地点</div>
														<div class="font-size-9 line-height-18">Fixed Name</div>
													</th> -->
													<th class=" colwidth-10">
														<div class="font-size-12 line-height-18">英文描述</div>
														<div class="font-size-9 line-height-18">F.Name</div>
													</th>
													<th class=" colwidth-10">
														<div class="font-size-12 line-height-18">中文描述</div>
														<div class="font-size-9 line-height-18">CH.Name</div>
													</th>
													 <th class=" colwidth-5">
														<div class="font-size-12 line-height-18">检查类型</div>
														<div class="font-size-9 line-height-18">Check Type</div>
													</th> 
													<!--  <th>
														<div class="font-size-12 line-height-18">适用性</div>
														<div class="font-size-9 line-height-18">Applicability</div>
													</th>  -->
													<th class=" colwidth-5">
														<div class="font-size-12 line-height-18">必检</div>
														<div class="font-size-9 line-height-18">Check</div>
													</th>
													<th class=" colwidth-5">
														<div class="font-size-12 line-height-18">MI</div>
														<div class="font-size-9 line-height-18">MI</div>
													</th>
													<!-- <th>
														<div class="font-size-12 line-height-18">备注</div>
														<div class="font-size-9 line-height-18">Remark</div>
													</th> -->
												</tr>
								         		 </thead>
												<tbody id="listDjgznr">
												</tbody>
									</table>
									</div>
									<div class="col-xs-12 text-center">
										<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" id="paginationDjxm">
										</ul>
									</div>
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="appendDjgznr()" 
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
									
				                </div>
					     		<div class="clearfix"></div>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	<!-------定检工作内容的模态宽 End-------->
	
	<!-- 	相关工卡的模态宽 模态框 -->
	<div class="modal fade" id="alertModalXggk" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:70%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">工单(卡)列表</div>
							<div class="font-size-9 ">Word Order List</div>
						</div>
						  <div class="panel-body padding-top-0 padding-bottom-0">
                              <div class="col-lg-9 pull-right padding-right-0">
								<div class=" pull-right padding-left-0 padding-right-0 padding-top-10">
									<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" placeholder="工单(卡)编号" id="keyword_search_xggk" class="form-control ">
									</div>
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchXggk();">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div> 
								</div>
					           </div>
					            
				           <div class="clearfix"></div>	 
								<!-- start:table -->
								<div  style="margin-top: 10px">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set">
												<thead>
												<tr>
													<th class="colwidth-3">
														<div class="font-size-12 line-height-18" >操作</div>
														<div class="font-size-9 line-height-18" >Operation</div>
													</th >
													<th class="colwidth-10">
														<div class="important">
														<div class="font-size-12 line-height-18" >工单（卡）编号</div>
														<div class="font-size-9 line-height-18" >No.</div>
														</div>
													</th>
													
													<th class="colwidth-5">
														<div class="font-size-12 line-height-18" >专业 </div>
														<div class="font-size-9 line-height-18" >Skill</div>
													</th>
													<th class="colwidth-5" onclick="orderBy('zdbmid')" id="zdbmid_order">
														<div class="font-size-12 line-height-18" >工单类型</div>
														<div class="font-size-9 line-height-18" >Type</div>
													</th>
													<th class="colwidth-5">
														<div class="font-size-12 line-height-18" >工单子类型</div>
														<div class="font-size-9 line-height-18" >Sub Type</div>
													</th>
													<th class="colwidth-30">
														<div class="font-size-12 line-height-18" >主题</div>
														<div class="font-size-9 line-height-18" >Subject</div>
													</th>
													
												</tr>
								         		 </thead>
												<tbody id="listXggk">
												</tbody>
									</table>
									</div>
									<!-- <div class="col-xs-12 text-center">
										<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" id="paginationXggk">
										</ul>
									</div> -->
								<div class="col-xs-12 text-center padding-right-0 page-height " id="paginationXggk">
									</div>
								<!-- end:table -->
			                	<div class="modal-footer" style="border-top: medium none ! important;">
									<button type="button" onclick="appendXggk()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
									
				                </div>
					     		<div class="clearfix"></div>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	<!-------定检工作内容的模态宽 End-------->
	
	



	<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%><!-- 章节号对话框 -->
 <script src="${ctx}/static/js/thjw/common/preview.js"></script>
 <script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/edit_jobCard_tj.js"></script> 
</body>
</html>
