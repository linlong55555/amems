<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看技术文件评估单</title>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<div class="page-content">
	 	<div class="panel panel-primary" >
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<!--导航结束  -->
	          <div class="panel-body " >
              	<div class="col-lg-12 col-sm-12 col-xs-12 col-md-12 padding-left-0 padding-right-0">
              	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核报告编号</div>
								<div class="font-size-9 ">Audit report No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" id="shbgbh" class="form-control" maxlength="10" readonly/>
						</div>
				  </div>
				  <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核日期</div>
								<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" id="shrq" class="form-control" maxlength="10" readonly/>
						</div>
					</div>
					 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">类型</div>
								<div class="font-size-9 ">Type</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" id="lx" class="form-control "  maxlength="20" value='内部' readonly/>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核类别</div>
								<div class="font-size-9 ">Category</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" id="group_type"  class="form-control"  maxlength="20"  readonly/>
							
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核对象</div>
								<div class="font-size-9 ">Object</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="hidden"  id="quality_audit_module_search_shdxid" >
							<input type="hidden"  id="quality_audit_module_search_shdxbh" >
							<input type="hidden"  id="quality_audit_module_search_shdxmc" >
						    <input type="text" id="quality_audit_module_add_shdx"  class="form-control"  maxlength="20"  readonly/>
							
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">状态</div>
								<div class="font-size-9 ">Status</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							 <input type="text" id="zt"  class="form-control"  maxlength="20"  readonly/>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">审核成员</div>
							<div class="font-size-9">Audit member</div>
						</span>
						
						 
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0"
								style="overflow-x: auto;">
								<table id="basic_education_table"
									class="table table-thin table-bordered table-striped table-hover table-set"
									style='margin-bottom: 0px !important'>
									<thead>
										<tr>
											<th class="colwidth-10 editTable"
												style="vertical-align: middle; display: table-cell;"></th>
											<th class="colwidth-20 editTable">
												<div class="font-size-12 line-height-18">姓名</div>
												<div class="font-size-9 line-height-18">Name</div>
											</th>
											<th>
												<div class="font-size-12 line-height-18">单位</div>
												<div class="font-size-9 line-height-18">Section</div>
											</th>
											<th>
												<div class="font-size-12 line-height-18">职务</div>
												<div class="font-size-9 line-height-18">TiTle</div>
											</th>
										</tr>
									</thead>
									<tbody id="dept_info_list">
										<tr id="headman_tr">
											<td style="text-align: center; vertical-align: middle;">
												组长</td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
									<!--		
									     <tr >
											<td style="text-align: center; vertical-align: middle;" >
											          成员</td>
											<td></td>
											<td></td>
											<td></td>
										</tr>									
									  -->							
									</tbody>
								</table>
							</div>
						</div>
						
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核目的</div>
								<div class="font-size-9 ">Audit purpose</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
							<textarea class="form-control " id="shmd" style="height: 55px;" maxlength="100" readonly></textarea>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核范围</div>
								<div class="font-size-9 ">Scope of audit</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
							<textarea class="form-control " id="shfw" style="height: 55px;" maxlength="100" readonly></textarea>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核依据</div>
								<div class="font-size-9 ">Audit basis</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
							<textarea class="form-control" id="shyj" style="height: 55px;" maxlength="100" readonly></textarea>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核概述</div>
								<div class="font-size-9 ">Overview</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
							<textarea class="form-control" id="shgs" style="height: 55px;" maxlength="100" readonly></textarea>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核结论</div>
								<div class="font-size-9 ">Conclusion</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
							<textarea class="form-control" id="shjl" style="height: 55px;" maxlength="100" readonly></textarea>
						</div>
					</div>
					<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">分发给</div>
								<div class="font-size-9 ">Distribute</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
								<input type="text" id="quality_audit_module_selects_ffdx"  class="form-control"  maxlength="20"  readonly/>
						</div>
					</div>
					
				  <div class="clearfix"></div>
				 
				</div>
				 <div class="clearfix"></div>
				<div id="attachments_list_edit" >
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				</div>
					<%@ include file="/WEB-INF/views/quality/reviewreformmeasures/measures_record.jsp"%><!------流程记录 -------->				
				
			
	          </div>
         </div>
	      
	</div>
<script>
$(document).ready(
		function() {
			Navigation(menuCode, "", "");//初始化导航
			$('input[name=date-range-picker]').daterangepicker().prev()
					.on("click", function() {
						$(this).next().focus();
					});
			//初始化查看数据
			initData();
		});
		
function initData(){
	var dprtcode='${dprtcode}';
	var zlshbh='${zlshbh}';
	var searchParam = {
			dprtcode : dprtcode,
			zlshbgbh : zlshbh

		};
		AjaxUtil.ajax({
			url : basePath + "/quality/qualityreviewreport/selectByQualityNum",
			type : "post",
			data : JSON.stringify(searchParam),
			dataType : "json",
			contentType : "application/json;charset=utf-8",
			success : function(data) {
				setData(data);
			}

		});

	}
function setData(data){
	  $("#auditId").val(data.id);
	  $("#shbgbh").val(data.zlshbgbh);
	  $("#shrq").val(data.shrq.substring(0, 10));
	  var lx="";
	  if("1"==data.lx){
		  lx="内部"
	  }else if("2"==data.lx){
		  lx="外部"
	  }
	  $("#lx").val(lx);
	  var shlb="";
	  if(data.shlb=='10'){
		  shlb="计划审核";
	  }else if(data.shlb=='20'){
		  shlb="非计划审核";
	  }	  
	  var zt="";
	  if("0"==data.zt){
		  zt="保存";
	  }else if("1"==data.zt){
		  zt="提交"
	  }else if("2"==data.zt){
		  zt="已审核"
	  }else if("3"==data.zt){
		  zt="已批准"
	  }else if("5"==data.zt){
		  zt="审核驳回"
	  }else if("6"==data.zt){
		  zt="审批驳回"
	  }else if("9"==data.zt){
		  zt="关闭"
	  }
	  $("#zt").val(zt);
	  $("#group_type").val(shlb);
	  $("#quality_audit_module_add_shdx").val(data.shdxmc);
	  //审核成员
	  setAuditMembers(data.id);
	  
	  $("#shmd").val(data.shmd);
	  $("#shfw").val(data.shfw);
	  $("#shyj").val(data.shyj);
	  $("#shgs").val(data.shgy);
	  $("#shjl").val(data.shjl);
	  
	  //分发部门
	  var departBhs=[];
	  var departMcs=[];
	  if(data.distributeDepartment){
    	  var distributeDepart=data.distributeDepartment.distributeDeparts;
    	 var departArrs=distributeDepart.split(",");
    	 for(var i=0;i<departArrs.length;i++){
    		   var ars=departArrs[i].split("#_#");
    		   departBhs.push(ars[1]);
    		   departMcs.push(ars[2]);
    	 }
    	  $("#quality_audit_module_selects_ffdx").val(departBhs.join(",")+" "+departMcs.join(","));
	  }
	  

	    //初始化附件
		var attachmentsObj = attachmentsUtil
				.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid : data.id,
			fileHead : true,
			colOptionhead : false,
			englishHead : 'Attachments'
		});
		

		//初始化流程记录
		measures_record.show({
			id : data.id,
			dprtcode : $("#dprtcode").val()
		}, true);
	}
	
	function setAuditMembers(ywid){
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/quality/auditMembers/selectByYwid",
			type:"post",
			data:{
				'ywid' : ywid
			},
			dataType:"json",
			success:function(data){
				if(data != null && data.rows.length > 0 ){
					var htmls="";
					$.each(data.rows,function(index,row){
						if(row.js == 1){//组长
						 $("#headman_tr").find("td:eq(1)").html(StringUtil.escapeStr(row.cybh) +" "+ StringUtil.escapeStr(row.cymc));
						 $("#headman_tr").find("td:eq(2)").html(StringUtil.escapeStr(row.bmmc));
						 $("#headman_tr").find("td:eq(3)").html(StringUtil.escapeStr(row.zw));
						}else if(row.js == 2){//成员
							htmls+="<tr>";
							htmls+="<td style='text-align: center; vertical-align: middle;'>成员</td>";
							htmls+="<td>"+StringUtil.escapeStr(row.cybh) +" "+ StringUtil.escapeStr(row.cymc)+"</td>";
							htmls+="<td>"+StringUtil.escapeStr(row.bmmc)+"</td>";
							htmls+="<td>"+StringUtil.escapeStr(row.zw)+"</td>";
							htmls+="</tr>";
						}else{
							htmls="<tr class='text-center'><td colspan=\"4\">暂无数据 No data.</td></tr>";
						}
					});
					
					$("#dept_info_list").append(htmls);
				}
			}
		});
		
		
	}
</script>
</body>
</html>
