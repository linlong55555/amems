<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>等效替代</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" name="zzjgid" value=${user.jgdm} />
<input type="hidden" id="userId" name="userId" value=${user.id} />
<input type="hidden" id="substitutionId" name="substitutionId"  />
<input type="hidden" id="gkcount" name="gkcount"  />
<input type="hidden" id="jxcount" name="jxcount"  />
<input type="hidden" id="substitutionDprtcode" name="substitutionDprtcode"  />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
<script type="text/javascript">
$(document).ready(function(){
//导航
Navigation(menuCode);
});
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
<style type="text/css">

.multiselect{
overflow:hidden;
}
</style>
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body padding-bottom-0 searchContent">
				<div class="searchContent row-height">
				<a href="#" onclick="add()"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left form-group checkPermission"
					  permissioncode="basic:substitution:main:01"><div class="font-size-12">新增</div>
					<div class="font-size-9">Add</div>
				</a>

					<!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0 form-group">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='件号/描述/替代件号/替代描述' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                        <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!-- 搜索框end -->
				
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">是否可逆性</div>
							<div class="font-size-9">State</div></span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class="form-control"  id="knxbs_search" >
									<option value="" style="color: black;">显示全部</option>
									<option value="2" style="color: black;">是</option>
									<option value="1" style="color: black;">否</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="zzjg" class="form-control " name="zzjg">
								<c:forEach items="${accessDepartment}" var="type">
									<option style="color: black;" value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					
					<div class="col-lg-3  text-right  pull-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>
				
					<div class="clearfix"></div>
                   </div>
					<div id="substitution__main_table_top_div" class="modal-body col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-h table-set" style="overflow-x: auto;">
						<table id="quality_check_list_table" class="table table-thin table-bordered table-set" style="min-width: 1700px !important">
							<thead>
							<tr>
							<th class="fixed-column colwidth-8"  >
								<div class="font-size-12 line-height-18" >操作</div>
								<div class="font-size-9 line-height-18" >Operation</div>
							</th>
							<th class="sorting  colwidth-10" onclick="orderBy('bjh')" id="bjh_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >件号</div>
									<div class="font-size-9 line-height-18" >P/N</div>
								</div>
							</th>
							<th class="sorting  colwidth-25" onclick="orderBy('ms')" id="ms_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >件号描述</div>
									<div class="font-size-9 line-height-18" >P/N Desc</div>
								</div>
							</th>
							
							<th class=" colwidth-12" >
									<div class="font-size-12 line-height-18">等效替代评估单</div>
									<div class="font-size-9 line-height-18">Attachment</div>
							</th>
							
							
							<th class="sorting  colwidth-10" onclick="orderBy('tdjh')" id="tdjh_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >替代件号</div>
									<div class="font-size-9 line-height-18" >Replace P/N</div>
								</div>
							</th>
							<th class="sorting  colwidth-25" onclick="orderBy('tdjms')" id="tdjms_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >替代描述</div>
									<div class="font-size-9 line-height-18" >Replace Desc</div>
								</div>
							</th>
							<th class="sorting  colwidth-15" onclick="orderBy('jxbs')" id="jxbs_order">
								<div class="font-size-12 line-height-18" >机型适用性</div>
								<div class="font-size-9 line-height-18" >A/C Type Applicability</div>
							</th>
							<th class="sorting  colwidth-15" onclick="orderBy('gkbs')" id="gkbs_order">
								<div class="font-size-12 line-height-18" >工卡适用性</div>
								<div class="font-size-9 line-height-18" >W/C Applicability</div>
							</th>
							<th class="sorting  colwidth-10" onclick="orderBy('knxbs')" id="knxbs_order">
								<div class="font-size-12 line-height-18" >是否可逆性</div>
								<div class="font-size-9 line-height-18" >Reversibility</div>
							</th>
							<th class="sorting colwidth-13" onclick="orderBy('whrid')" id="whrid_order">
								<div class="font-size-12 line-height-18" >维护人</div>
								<div class="font-size-9 line-height-18" >Creator </div>
							</th>
							<th class="sorting colwidth-13" onclick="orderBy('whsj')" id="whsj_order">
								<div class="font-size-12 line-height-18" >维护时间</div>
								<div class="font-size-9 line-height-18" >Create Time</div>
							</th>
							<th class="sorting colwidth-13" onclick="orderBy('whbmid')" id="whbmid_order">
								<div class="font-size-12 line-height-18" >维护部门</div>
								<div class="font-size-9 line-height-18" >Department</div>
							</th>
							<th class=" colwidth-13" onclick="orderBy('dprtcode')" id="dprtcode_order">
								<div class="font-size-12 line-height-18" >组织机构 </div>
								<div class="font-size-9 line-height-18" >Organization</div>
							</th>
							</tr> 
			         		 </thead>
							<tbody id="list">
							</tbody>
							
						</table>
					</div>
					<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
					</div>
					
					<div class="clearfix"></div>
				</div>
			</div>

	</div>
	
	
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
   
    
    <div class="modal fade in modal-new" id="alertModalAdd" tabindex="-1" role="dialog" aria-hidden="true" data-keyboard="false" aria-labelledby="Assessment_Open_Modal" data-backdrop="static" >
	<div class="modal-dialog" style='width:60%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
				<h4 class="modal-title" >
                	<div class='pull-left'>
                    	<div class="font-size-12 " id="titleName"></div>
						<div class="font-size-9 " id="titleEname"></div>
					</div>
				  	<div class='pull-right' >
		  		  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
		  		  	</div>
				  	<div class='clearfix'></div>
              	</h4>
            </div>
		    <div class="modal-body  " >
				<form id="form" >	 
	           	<div class="input-group-border margin-top-8 ">
  					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12"><span style="color: red">*</span>件号</div>
							<div class="font-size-9">P/N</div>
						</span>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<div class='input-group' style='width:100%;'>
						    <input class="form-control"  name="bjh" id="bjh" type="text" maxlength="100" />
							<span class="input-group-btn">
								<button onclick="replace()" class='btn btn-default form-control' type="button" style='border-left:0px;'><i class='fa fa-exchange'></i></button> 
							</span>
							<div class='clearfix'></div>
						</div>
							<!-- <div class="col-lg-10 col-sm-10 col-xs-10 padding-left-0 padding-right-0">
							<input class="form-control"  name="bjh" id="bjh" type="text" maxlength="100" />
							</div>
							<div class="col-lg-2 col-sm-2 col-xs-2 padding-left-2 padding-right-0">
							<button onclick="replace()" class='btn btn-primary form-control' type="button">替换</button> 
							</div> -->
						</div>
					</div> 
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0" >
							<div class="font-size-12"><span style="color: red">*</span>替代件号</div>
							<div class="font-size-9">Replaced P/N</div>
						</span>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<input class="form-control"  id="tdjh" name="tdjh"  type="text" maxlength="100" />
						</div>
					</div> 
					<div class='clearfix'></div>
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0" >
							<div class="font-size-12 line-height-18">描述</div>
							<div class="font-size-9 line-height-18">Description</div>
						</span>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="ms" name="ms" placeholder='长度最大为300'   maxlength="300"></textarea>
						</div>
					</div> 
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0" >
							<div class="font-size-12 line-height-18">替代描述</div>
							<div class="font-size-9 line-height-18">Description</div>
						</span>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="tdjms" name="tdjms" placeholder='长度最大为300'   maxlength="300"></textarea>
						</div>
					</div> 
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0" >
							<div class="font-size-12"><span style="color: red">*</span>机型适用性</div>
							<div class="font-size-9">Applicability</div>
						</span>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" id="jxbsDiv">
							<select class=" " id="jxbs" name="jxbs" multiple="multiple">
							</select>
						</div>
					</div> 
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0" >
							<div class="font-size-12"><span style="color: red">*</span>工卡适用性</div>
							<div class="font-size-9">Start Date</div>
						</span>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" id="gkbsDiv">
							<select class=" " id="gkbs" name="gkbs" multiple="multiple">
							</select>
						</div>
					</div> 
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0" >
							<div class="font-size-12"><span style="color: red">*</span>是否可逆</div>
							<div class="font-size-9">Replace</div>
						</span>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						    <label  style='margin-top:4px;font-weight:normal;'>
								<input name="knxbs" type="radio" value="2" checked style='vertical-align:-2px'/>是
							</label>
							<label class='padding-left-5' style='margin-top:4px;font-weight:normal;'>
								<input name="knxbs" type="radio" value="1" style='vertical-align:-2px'/>否 
							</label>
							
						</div>
					</div> 
					<div class='clearfix'></div>
					</div>
					
					<div id="dxtd_modal_attachments_list_edit" style="display:none">							
						        <%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					</div>		
					
			</form>
			<div class="clearfix"></div>
			
			<div class="clearfix"></div>
			</div>	
		 	
			<div class="modal-footer ">
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
					 	<span class="input-group-addon modalfooterbtn">
							
						   	<button id="baocuns" type="button" onclick="saveUpdate();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
						</span>
					</div>
				</div>
		  	</div>
	 	</div>
	</div>
</div>
<div class="modal fade in modal-new" id="alertModalShow"
	tabindex="-1" role="dialog"
	aria-labelledby="alertModalShow" aria-hidden="true"
	data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width: 60%;">
		<div class="modal-content">

			<div class="modal-header modal-header-new">
				<h4 class="modal-title">
					<div class='pull-left'>
						<div class="font-size-12 ">等效替代数据详情</div>
						<div class="font-size-9">Detail</div>
					</div>
					<div class='pull-right'>
						<button type="button" class="icon-remove modal-close"
							data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
			</div>
            <div class="modal-body">
            <div class="input-group-border margin-top-8">
			<div
				class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span
					class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">件号</div>
					<div class="font-size-9">P/N</div>
				</span>
				<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
					<input class="form-control" name="bjh" id="bjh_show" type="text"
						maxlength="100" readonly="readonly" />
				</div>
			</div>

			<div
				class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span
					class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">替代件号</div>
					<div class="font-size-9">Replaced P/N</div>
				</span>
				<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
					<input class="form-control" id="tdjh_show" name="tdjh" type="text"
						maxlength="100" readonly="readonly" />
				</div>
			</div>

			<div class="clearfix"></div>

			<div
				class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span
					class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">描述</div>
					<div class="font-size-9">Description</div>
				</span>
				<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
					<textarea class="form-control" id="ms_show" name="ms" maxlength="300"
						readonly="readonly"></textarea>

				</div>
			</div>

			<div
				class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span
					class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">替代描述</div>
					<div class="font-size-9">Description</div>
				</span>
				<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
					<textarea class="form-control" id="tdjms_show" name="tdjms"
						maxlength="300" readonly="readonly"></textarea>

				</div>
			</div>

			<div class="clearfix"></div>


			<div
				class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span
					class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">机型适用性</div>
					<div class="font-size-9">Applicability</div>
				</span>
				<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
					<input class="form-control" id="jxbs_show" name="jxbs" type="text"
						readonly="readonly" />
				</div>
			</div>
			<div
				class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span
					class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">工卡适用性</div>
					<div class="font-size-9">Applicability</div>
				</span>
				<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">

					<input class="form-control" id="gkbs_show" name="gkbs" type="text"
						readonly="readonly" />
				</div>
			</div>

			<div class="clearfix"></div>

			<div
				class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span
					class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">是否可逆</div>
					<div class="font-size-9">Maintainer</div>
				</span>
				<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
					<input type="text" id="knxbs_show" class="form-control"
						readonly="readonly" />
				</div>
			</div>

			<div class="clearfix"></div>
			</div>
			<div id="dxtd_modal_attachments_list_show">
				<%@ include
					file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
			</div>
			<div class="clearfix"></div>
			</div>
			<div class="modal-footer">
				<div class="col-xs-12 padding-leftright-8">
					<div class="input-group">
						<span class="input-group-addon modalfootertip"> </span> <span
							class="input-group-addon modalfooterbtn">
							<button type="button"
								class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
						</span>
					</div>
					<!-- /input-group -->
				</div>
			</div>
		</div>
	</div>
</div>    
    <!-- <div class="modal fade" id="alertModalAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:65%!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
							<div class="font-size-16 line-height-18" id="titleName"></div>
							<div class="font-size-9 line-height-18" id="titleEname"></div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12 col-sm-12">
			            	<form id="form" >
			            	<div class="col-lg-4 col-sm-6 col-xs-12 padding-left-0 margin-top-10 padding-right-0 form-group">
								<span class="pull-left col-xs-4 col-sm-6 col-lg-2 text-right padding-left-0 padding-right-0" style="width:30%;font-weight:bold">
								<div class="font-size-12"><span style="color: red">*</span>件号</div>
									<div class="font-size-9">Start Date</div>
									</span>
								<div class="col-lg-10 col-sm-6 col-xs-8 padding-left-8 padding-right-0" style="width:70%">
									<input class="form-control"  name="bjh" id="bjh" type="text"/>
									 <button onclick="replace()" class='btn btn-primary margin-top-8' type="button">替换</button> 
								</div>
							</div> 
							
							<div class=" col-lg-8 col-sm-6 col-xs-12  padding-left-0 margin-top-10 padding-right-0 form-group" >
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
										<div class="font-size-12 line-height-18">描述</div>
										<div class="font-size-9 line-height-18">Desc</div>
								</label>
								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8  padding-right-0">
										<textarea class="form-control" id="ms" name="ms" placeholder='长度最大为300'   maxlength="300"></textarea>
								</div>
							</div>
			            	<div class="clearfix"></div>
			            	
			            	<div class="col-lg-4 col-sm-6 col-xs-12 padding-left-0 margin-top-10 padding-right-0 form-group">
								<span class="pull-left col-xs-4 col-sm-6 col-lg-2 text-right padding-left-0 padding-right-0" style="width:30%;font-weight:bold">
								<div class="font-size-12"><span style="color: red">*</span>替代件号</div>
									<div class="font-size-9">Start Date</div>
									</span>
								<div class="col-lg-10 col-sm-6 col-xs-8 padding-left-8 padding-right-0" style="width:70%">
									<input class="form-control"  id="tdjh" name="tdjh"  type="text"/>
								</div>
							</div> 
							
							<div class=" col-lg-8 col-sm-6 col-xs-12  padding-left-0 margin-top-10 padding-right-0 form-group" >
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
										<div class="font-size-12 line-height-18">替代描述</div>
										<div class="font-size-9 line-height-18">Replace Desc</div>
								</label>
								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8  padding-right-0">
										<textarea class="form-control" id="tdjms" name="tdjms" placeholder='长度最大为300'   maxlength="300"></textarea>
								</div>
							</div>
							
							
			            	<div class="clearfix"></div>
			            	
			            	<div class="col-lg-4 col-sm-6 col-xs-12 padding-left-0 margin-top-10 padding-right-0 form-group">
								<span class="pull-left col-xs-4 col-sm-6 col-lg-2 text-right padding-left-0 padding-right-0" style="width:30%;font-weight:bold">
								<div class="font-size-12"><span style="color: red">*</span>机型适用性</div>
									<div class="font-size-9">Start Date</div>
									</span>
								<div class="col-lg-10 col-sm-6 col-xs-8 padding-left-8 padding-right-0" style="width:70%" id="jxbsDiv">
									<select class=" " id="jxbs" name="jxbs" multiple="multiple">
									</select>
								</div>
							</div> 
			            	<div class="col-lg-4 col-sm-6 col-xs-12 padding-left-0 margin-top-10 padding-right-0 form-group">
								<span class="pull-left col-xs-4 col-sm-6 col-lg-2 text-right padding-left-0 padding-right-0" style="width:30%;font-weight:bold">
								<div class="font-size-12"><span style="color: red">*</span>工卡适用性</div>
									<div class="font-size-9">Start Date</div>
									</span>
								<div class="col-lg-10 col-sm-6 col-xs-8 padding-left-8 padding-right-0" style="width:70%" id="gkbsDiv">
									<select class=" " id="gkbs" name="gkbs" multiple="multiple">
									</select>
								</div>
							</div> 
							
			            	<div class="col-lg-4 col-sm-6 col-xs-12 padding-left-0 margin-top-10 padding-right-0 form-group">
								<span class="pull-left col-xs-4 col-sm-6 col-lg-2 text-right padding-left-0 padding-right-0" style="width:30%;font-weight:bold">
								<div class="font-size-12"><span style="color: red">*</span>是否可逆</div>
									<div class="font-size-9">Start Date</div>
									</span>
								<div class="col-lg-10 col-sm-6 col-xs-8 padding-left-8 padding-right-0" style="width:70%">
									<select id="knxbs" class="form-control "  name="knxbs">
											<option value="2">是</option>
											<option value="1">否</option>
									</select>
									
									<label style="margin-right: 20px;font-weight: normal">
										<input name="knxbs" type="radio" value="2" checked/>是
									</label> 
									
									<label style="font-weight: normal">
										<input name="knxbs" type="radio" value="1" />否 
									</label> 
								</div>
							</div> 
			            	</form>
							    
					     		<div class="clearfix"></div>
					     		<div class="text-center margin-top-10 padding-buttom-10 pull-right">
			                     <button onclick="saveUpdate()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" >
			                     		<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div></button>&nbsp;&nbsp;&nbsp;&nbsp;
			                     	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
                    			 </div><br/>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div> -->
    
    
	<script type="text/javascript" src="${ctx }/static/js/thjw/basic/substitution/substitution_main.js"></script>
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
	
</body>
</html>