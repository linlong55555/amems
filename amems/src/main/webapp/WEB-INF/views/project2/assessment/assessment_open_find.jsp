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
	<div class="clearfix"></div>
	<div class="page-content">
	<input type="hidden" id="viewid" value="${viewid}">
	<input type="hidden" id="id" value="${viewid}">
	<input type="hidden" id="dprtcode" >
		<div class="panel panel-primary" >
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<!--导航结束  -->
	          <div class="panel-body panel-body-new" id="Assessment_Open_Modal">
					<form id="form" >
  							<!--评估单号数据  -->
  							<div class=" padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 
    							<div class="col-xs-12 input-group-border margin-top-8 padding-left-0">
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">技术评估单编号</div>
											<div class="font-size-9 ">Evaluation No.</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<input type="text" placeholder='不填写则自动生成'  maxlength="50" id="pgdh" name="pgdh" class="form-control padding-left-3 padding-right-3"  />
										</div>
									</div>
			            			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">版本</div>
											<div class="font-size-9 ">Rev.</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 padding-top-10">
											<div class='input-group'>
												<span id="newbb" ></span> <span id="bbstate" >  ←  </span>
												<a href='javascript:void(0);'  onclick="Assessment_Open_Modal.findAssessment()" id="oldbb"></a>
												<input type="hidden" id="oldbbid" >
												<span class='input-group-btn' id="historyList">
													<i class="attachment-view2 glyphicon glyphicon glyphicon-list color-blue cursor-pointer"  style="font-size:15px"  style="float: left;text-decoration:none;position:relative; margin-left: 10px;"></i>
												</span>
										    </div>
										</div>
									</div>
									<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">来源</div>
											<div class="font-size-9 ">Issued By</div>
										</span>
										<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
											<div class="input-group input-group-new">
							                    <span class="input-group-addon">
							                    	<span><input type='radio' name='lylx'  value="1"/>&nbsp;适航性资料&nbsp;</span>
							                    </span>
							                     <span class="input-group-addon">
							                    	 <span><input type='radio' name='lylx' checked="checked" value="9"/>&nbsp;其他&nbsp;</span>
												 </span>
							                     <input type="text" id="lysm" class="form-control" maxlength="300">
						                	</div>
										</div>
									</div>
									<div class='clearfix'></div>
									<div class='table_pagination padding-leftright-8'>
									</div>
									</div>
								</div>
								<!--评估单号数据  -->
								<div class="clearfix"></div>
								<!--适应性资料STATR  -->
     							<div id="airworthinessDiv" class="panel panel-default padding-right-0 ">
							        <div class="panel-heading bg-panel-heading">
						        		<div class="font-size-12 ">适航性资料</div>
										<div class="font-size-9">Airworthiness</div>
								    </div>
								<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
										<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
							   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">文件编号</div>
													<div class="font-size-9">File No.</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<div class="input-group" style="width: 100%">
														<input id="shxzljswjbh"  type="text" class="form-control"  maxlength="50" disabled="disabled">
														<input type="hidden"  id="shxzlid" class="form-control" />
								                    	<input type="hidden"  id="gljswjid" class="form-control" />
								                    	<span class="required input-group-btn" id="wxrybtn" >
															<button type="button" id="wjbhbtn" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Assessment_Open_Modal.openTech()">
																<i class="icon-search cursor-pointer" ></i>
															</button>
														</span>
								                    	
								                	</div>
												</div>
											</div>
							   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
												<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">文件版本</div>
													<div class="font-size-9 ">File Rev.</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div margin-top-8">
													<span >
														<a href='javascript:void(0);'  onclick="Assessment_Open_Modal.findAirworthiness()" id="shxzlbb"></a>
													</span>
												</div>
											</div>
							   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">修正案号</div>
													<div class="font-size-9 ">Amendment</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<input type="text"  id="shxzlxzah" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
												</div>
											</div>
							   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">文件来源</div>
													<div class="font-size-9 ">Issued By</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<input type="text"   id="shxzljswjly" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
												</div>
											</div>
							   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">文件类型</div>
													<div class="font-size-9 ">Type</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<input type="text"  id="shxzljswjlx" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
												</div>
											</div>
							   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">上传日期</div>
													<div class="font-size-9 ">Upload Date</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<input type="text"  id="shxzlzdsj"  class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
												</div>
											</div>
											<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
												<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">文件主题</div>
													<div class="font-size-9">Subject</div>
												</span>
												<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
													<textarea class="form-control padding-left-3 padding-right-3"  style="height:55px" maxlength="300" readonly="readonly" id="shxzljswjzt"></textarea>
												</div>
											</div>
							   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">颁发日期</div>
													<div class="font-size-9 ">Issue Date</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<input type="text"  id="shxzlbfrq" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
												</div>
											</div>
							   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">生效日期</div>
													<div class="font-size-9 ">Effect Date</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<input type="text"  id="shxzlsxrq" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
												</div>
											</div>
											<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">到期日期</div>
													<div class="font-size-9 ">Due Date</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<input type="text"  id="shxzldqrq" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
												</div>
											</div>
											<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">源文件</div>
													<div class="font-size-9 ">File</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
													<label class='margin-top-8 ' style='width:100%;'>
														<input type="hidden"  id="ywjid" />
														<a href='javascript:void(0);' style="word-wrap:break-word"  onclick="Assessment_Open_Modal.onclikfile()" id="ywjurl" ></a>
													</label>
												</div>
											</div>
											<div class="clearfix"></div>
										</div>
									</div>
			     				</div>	
			     				<!--适应性资料END  -->
			     				<div class="clearfix"></div>
			     				<!--关联技术评估单STATR  -->
      							<div id="evaluationDiv" class="panel panel-default padding-right-0">
							        <div class="panel-heading bg-panel-heading">
							        	<div class="font-size-12 ">关联技术评估单</div>
										<div class="font-size-9">Related Evaluation</div>
								    </div>
										<div class="panel-body padding-bottom-0 padding-left-0  padding-top-0 padding-right-0">	
										<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
							   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">关联技术评估单</div>
													<div class="font-size-9 ">Related Evaluation</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<!-- 			<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
													<div class="input-group" style="width: 100%">
														<input type="text"  placeholder='适航性资料下的相关评估单' name="glpgd" readonly="readonly" class="form-control noteditable readonly-style colse" id="glpgd" ondblclick="Assessment_Open_Modal.updateGlpgdOption()" >
														<span class="required input-group-btn"  >
															<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Assessment_Open_Modal.updateGlpgdOption()">
																<i class="icon-search cursor-pointer" ></i>
															</button>
														</span>
								                	</div>
												</div> -->
														<input type="hidden"  name="glpgdid"  id="glpgdid" >
													<input type="text"  name="glpgd" class="form-control " id="glpgd" readonly="readonly">
<!-- 													<select placeholder='适航性资料下的相关评估单' id="glpgdid" class="noteditable form-control" onchange="Assessment_Open_Modal.changeGljspgd()"> -->
<!-- 													</select> -->
												</div>
											</div>
							   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group"  >
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">关联评估单版本</div>
													<div class="font-size-9 ">Rev.</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<span class='margin-top-8'>
														<a href='javascript:void(0);'  onclick="Assessment_Open_Modal.findGlAssessment()"id="glbb"></a>
													</span>
												</div>
											</div>
							   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">评估时间</div>
													<div class="font-size-9 ">Assess Time</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<input type="text" value="" id="glpgsj"  class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
												</div>
											</div>
											<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
												<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">关联技术评估单标题</div>
													<div class="font-size-9">Title</div>
												</span>
												<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
													<textarea class="form-control padding-left-3 padding-right-3"  style="height:55px" id="glpgdzt" maxlength="300" readonly="readonly"></textarea>
												</div>
											</div>	
											<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
												<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">关联适航性资料</div>
													<div class="font-size-9">Related Doc.</div>
												</span>
												<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
													<input class="form-control padding-left-3 padding-right-3"  value="" id="glshxzl" maxlength="300" readonly="readonly">
												</div>
											</div>	
										</div>
									</div>
			     				</div>	
			     				<!--关联技术评估单END  -->
			     				<div class="clearfix"></div>
			     				 <!--基础信息STATR  -->
      							<div class="panel panel-default padding-right-0">
							        <div class="panel-heading bg-panel-heading">
							        	<div class="font-size-12 ">基础信息</div>
										<div class="font-size-9">Basic Data</div>
								    </div>
									<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
										<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
											 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">机型</div>
													<div class="font-size-9 ">A/C Type</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<input type="text"  name="Assessment_Open_Modal_jx" class="form-control " id="Assessment_Open_Modal_jx" readonly="readonly">
												</div>
											</div>
					            			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">ATA章节号</div>
													<div class="font-size-9">ATA</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
													<input type="text"  name="zjhms" class="form-control " id="zjhms" readonly="readonly">
												</div>
											</div>
											 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">评估期限</div>
													<div class="font-size-9 ">Assess Limit</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<input type="text"  id="pgqx"  class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
												</div>
											</div>
											<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">类型</div>
													<div class="font-size-9 ">Type</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<input type="text" id="jjcd" class="noteditable form-control padding-left-3 padding-right-3" maxlength="15"/>
												</div>
											</div>
					            
					 	     			    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
														<div class="font-size-12 margin-topnew-2">等级</div>
														<div class="font-size-9 ">Level</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<input type="text"  id="dj" class="noteditable form-control padding-left-3 padding-right-3" maxlength="15" />
												</div>
											</div>
					            
					            			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
												<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">状态</div>
													<div class="font-size-9 ">Status</div>
												</span>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
													<input type="text" id="zt" class="noteditable form-control padding-left-3 padding-right-3" />
												</div>
											</div>
					     				 	<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
												<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 ">标题</div>
													<div class="font-size-9 ">Title</div>
												</span>
												<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
													<textarea  style="height:55px" class="noteditable form-control padding-left-3 padding-right-3" id="pgdzt" name="pgdzt"   maxlength="300"></textarea>
												</div>
											</div>
											<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
												<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 ">涉及改装</div>
													<div class="font-size-9 ">Refit</div>
												</span>
												<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
													<textarea class="noteditable form-control padding-left-3 padding-right-3" id="sjgz"  
														style="height:55px" maxlength="1000"></textarea>
												</div>
											</div>
											<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
												<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 ">文件摘要</div>
													<div class="font-size-9 ">Summary</div>
												</span>
												<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
													<textarea class="noteditable form-control padding-left-3 padding-right-3" id="wjzy" 
														style="height:55px"  maxlength="1000"></textarea>
												</div>
											</div>
											<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
												<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 ">备注</div>
													<div class="font-size-9 ">Note</div>
												</span>
												<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
													<textarea class="noteditable form-control padding-left-3 padding-right-3" id="bz"   
														style="height:55px" maxlength="300"></textarea>
												</div>
											</div>
					     					<div class="clearfix"></div>
										</div>
		     					<!-- 参考文件列表 -->  
	     							<%@ include file="/WEB-INF/views/project2/assessment/reference_list.jsp"%>
	     						<!-- 参考文件 -->
					     		<div class="clearfix"></div>
				     			<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">源文件范围</div>
										<div class="font-size-9 ">Range</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="noteditable form-control padding-left-3 padding-right-3" id="syfw_ywj" 
											style="height:55px"  maxlength="1000"></textarea>
									</div>
								</div>
			     				<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">本单位适用性</div>
										<div class="font-size-9 ">Applicability</div>
									</span>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
										<div class="input-group input-group-new">
						                    <span class="input-group-addon" style="padding-left:0px">
						                    	<span><input type='radio' class="noteditable" onclick="Assessment_Open_Modal.onchangesyx()" name='syx' value="1"checked />&nbsp;适用&nbsp;</span>
						                    </span>
						                     <span class="input-group-addon">
						                    	 <span><input type='radio' class="noteditable"  onclick="Assessment_Open_Modal.onchangesyx()" name='syx' value="0"/>&nbsp;不适用&nbsp;</span>
											 </span>
						                     <input type="text" class="noteditable form-control" id="fsyyy" maxlength="1000">
					                	</div>
									</div>
								</div>
								
							<!-- 适用性设置开始 -->
							<%@ include file="/WEB-INF/views/project2/assessment/applicable.jsp"%>
							<!-- 适用性设置结束 -->
								
		<!-- 			     		<div id="sylbDiv" class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">本单位适用类别</div>
										<div class="font-size-9 ">Type</div>
									</span>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
										<div class="input-group input-group-new">
						                    <span class="input-group-addon" style="padding-left:0px">
						                    	<span><input type='radio' class="noteditable" name='sylb' value="1" checked/>&nbsp;飞机&nbsp;</span>
						                    </span>
						                     <span class="input-group-addon">
						                    	 <span><input type='radio' class="noteditable" name='sylb' value="2"/>&nbsp;发动机&nbsp;</span>
											 </span>
						                     <span class="input-group-addon">
						                    	 <span><input type='radio' class="noteditable" name='sylb' value="3" />&nbsp;APU&nbsp;</span>
											 </span>
						                     <span class="input-group-addon">
						                    	 <span><input type='radio' class="noteditable" name='sylb' value="99" />&nbsp;其他部件&nbsp;</span>
											 </span>
											<input type="text" class="form-control" id="biaoshi">
					                	</div>
									</div>
								</div> -->
					     		<div id="syfwDiv" class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">本单位适用范围</div>
										<div class="font-size-9 ">Range</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="noteditable form-control padding-left-3 padding-right-3" id="syfw_bdw" 
											style="height:55px"  maxlength="1000"></textarea>
									</div>
								</div>
				     		<div id="orderDiv" class="col-xs-12 margin-top-0 padding-left-0  padding-right-0" >
			     				<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">下达指令</div>
										<div class="font-size-9">Order</div>
									</span>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
										<div class="input-group input-group-new">
											<span class="input-group-addon" style='padding-left:0px!important;'>
						                    	<span><input class="noteditable" type='checkbox' onclick="Assessment_Open_Modal.onchangeOrder('eo')"  name='eo' />&nbsp;</span>
						                    </span>
					                    <div class="input-group-addon fixed-width-style " >
						                     <div class="font-size-12 margin-topnew-2">工程指令EO</div>
											<div class="font-size-9 margin-top-4">EO</div>
										 </div>
										    <input type="hidden"  id="eocode" value="6">
										    <input type="hidden"  id="eoid" >
										    <input type="text"  id="eo" class="form-control"  disabled="disabled">
						                    <div class="required input-group-addon input-group-addon-btn" id="eor" style='padding:0px;'>
		                                       <button type="button" class='btn btn-default' style='height:34px;border-left:0px;'>
		                                       	<i class='icon-search' onclick='Assessment_Open_Modal.openUserWin("eo");'></i>
		                                       </button>
						                    </div>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0" >
									</span>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new">
											<span class="input-group-addon" style='padding-left:0px!important;'>
						                    	<span><input class="noteditable" type='checkbox' name='tb' onclick="Assessment_Open_Modal.onchangeOrder('tb')" />&nbsp;</span>
						                    </span>
						                    <div class="input-group-addon fixed-width-style">
							                    <div class="font-size-12 margin-topnew-2">维护提示</div>
												<div class="font-size-9 margin-top-4">MT</div>
											 </div>
											    <input type="hidden"  id="tbcode" value="1">
											 	<input type="hidden"  id="tbid"  >
						                      	<input type="text"  class="form-control" id="tb" disabled="disabled">
							                    <div class="required input-group-addon input-group-addon-btn" style='padding:0px;' id="tbr" >
			                                       <button type="button" class='btn btn-default' style='height:34px;border-left:0px;'><i class='icon-search' onclick='Assessment_Open_Modal.openUserWin("tb");'></i></button>
							                    </div>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									</span>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new">
											<span class="input-group-addon" style='padding-left:0px!important;'>
						                   	 	<span><input class="noteditable" type='checkbox' name='maintenance' onclick="Assessment_Open_Modal.onchangeOrder('maintenance')" />&nbsp;</span>
						                    </span>
						                    <div class="input-group-addon fixed-width-style">
						                     	<div class="font-size-12 margin-topnew-2">修订维修方案</div>
												<div class="font-size-9 margin-top-4">Maintenance</div>
											 </div>
										 	<input type="hidden"  id="maintenancecode" value="3">
										 	<input type="hidden"  id="maintenanceid" >
					                   		<input type="text"  class="form-control" id="maintenance" disabled="disabled">
						                    <div class="required input-group-addon input-group-addon-btn" style='padding:0px;' id="maintenancer">
	                                       		<button type="button" class='btn btn-default' style='height:34px;border-left:0px;'><i class='icon-search' onclick='Assessment_Open_Modal.openUserWin("maintenance");'></i></button>
					                    	</div>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									</span>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new">
											<span class="input-group-addon" style='padding-left:0px!important;'>
						                    	<span><input class="noteditable" type='checkbox' name='technicalOrder' onclick="Assessment_Open_Modal.onchangeOrder('technicalOrder')" />&nbsp;</span>
						                    </span>
						                    <div class="input-group-addon fixed-width-style">
						                     	<div class="font-size-12 margin-topnew-2">技术指令</div>
												<div class="font-size-9 margin-top-4">Technical Order</div>
											 </div>
											 <input type="hidden"  id="technicalOrdercode" value="2">
											 <input type="hidden"  id="technicalOrderid"  >
						                     <input type="text"  class="form-control" id="technicalOrder" disabled="disabled">
							                 <div class="required input-group-addon input-group-addon-btn" style='padding:0px;' id="technicalOrderr">
				                                     <button type="button" class='btn btn-default' style='height:34px;border-left:0px;'><i class='icon-search' onclick='Assessment_Open_Modal.openUserWin("technicalOrder");'></i></button>
						                     </div>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									</span>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new">
											<span class="input-group-addon" style='padding-left:0px!important;'>
						                    	<span><input class="noteditable" type='checkbox'  name='mel' onclick="Assessment_Open_Modal.onchangeOrder('mel')" />&nbsp;</span>
						                    </span>
						                    <div class="input-group-addon fixed-width-style">
						                     	<div class="font-size-12 margin-topnew-2">MEL更改</div>
												<div class="font-size-9 margin-top-4">MEL</div>
											 </div>
											 <input type="hidden"  id="melcode" value="7">
											 <input type="hidden"  id="melid" >
						                     <input type="text"  class="form-control" id="mel" disabled="disabled">
							                 <div class="required input-group-addon input-group-addon-btn" style='padding:0px;' id="melr">
			                                     <button type="button" class='btn btn-default' style='height:34px;border-left:0px;'><i class='icon-search' onclick='Assessment_Open_Modal.openUserWin("mel");'></i></button>
						                     </div>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									</span>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new">
											<span class="input-group-addon" style='padding-left:0px!important;'>
						                   	 	<span><input class="noteditable" type='checkbox' name='workCard'  onclick="Assessment_Open_Modal.onchangeOrder('workCard')" />&nbsp;</span>
						                    </span>
						                    <div class="input-group-addon fixed-width-style">
						                     	<div class="font-size-12 margin-topnew-2">修订工卡</div>
												<div class="font-size-9 margin-top-4">Work Card</div>
											 </div>
											 <input type="hidden"  id="workCardcode" value="8">
											 <input type="hidden"  id="workCardid" >
						                     <input type="text"  class="form-control" id="workCard" disabled="disabled">
							                 <div class="required input-group-addon input-group-addon-btn" style='padding:0px;' id="workCardr">
			                                     <button type="button" class='btn btn-default' style='height:34px;border-left:0px;'><i class='icon-search' onclick='Assessment_Open_Modal.openUserWin("workCard");'></i></button>
						                     </div>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									</span>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new">
											<span class="input-group-addon" style='padding-left:0px!important;'>
						                   	 <span><input class="noteditable" type='checkbox' name='nrc'  onclick="Assessment_Open_Modal.onchangeOrder('nrc')" />&nbsp;</span>
						                    </span>
						                    <div class="input-group-addon fixed-width-style">
							                    <div class="font-size-12 margin-topnew-2">下达工单（维修指令）</div>
												<div class="font-size-9 margin-top-4">W/O</div>
											</div>
											<input type="hidden"  id="nrccode" value="4">
											<input type="hidden"  id="nrcid" >
						                    <input type="text"  class="form-control" id="nrc" disabled="disabled">
							                <div class="required input-group-addon input-group-addon-btn" style='padding:0px;' id="nrcr">
			                                     <button type="button" class='btn btn-default' style='height:34px;border-left:0px;'><i class='icon-search' onclick='Assessment_Open_Modal.openUserWin("nrc");'></i></button>
						                    </div>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									</span>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new">
											<span class="input-group-addon" style='padding-left:0px!important;'>
					                   			<span><input class="noteditable" type='checkbox' name='other'  onclick="Assessment_Open_Modal.onchangeOrder('other')" />&nbsp;</span>
						                    </span>
						                    <div class="input-group-addon fixed-width-style">
					                     		<div class="font-size-12 margin-topnew-2">其他</div>
												<div class="font-size-9 margin-top-4">Other</div>
											 </div>
											 <input type="hidden"  id="othercode" value="99">
						                     <input type="text"  class="noteditable form-control" id="other">
					                	</div>
									</div>
								</div>
							</div>
							<div class="clearfix"></div>
							<div id="zlsjDiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">办理期限</div>
									<div class="font-size-9 ">For Duration</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input type="text"  id="yjbfzlsj" class="noteditable form-control date-picker"  />
								</div>
							</div>
							<div id="yjbfzlDiv" class="col-lg-9 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">预计颁发指令</div>
									<div class="font-size-9 ">Directive</div>
								</span>
								<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 label-input-div">
									<input type="text"  id="yjbfzl" class="noteditable form-control "  maxlength="300"/>
								</div>
							</div>
							<div  id="repeatid">
							<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">时限要求</div>
									<div class="font-size-9 ">Limit Request</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control" id="sxyq"  
										style="height:55px" maxlength="1000"></textarea>
								</div>
							</div>
		     				<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">关闭条件</div>
									<div class="font-size-9 ">Condition</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control" id="gbtj" 
									style="height:55px"  maxlength="300"></textarea>
								</div>
							</div>	
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">重复检查</div>
									<div class="font-size-9 ">Repeat Check</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<span class='margin-top-8' style="margin-right: 20px; font-weight: normal">
										<input name="is_cfjc" type="radio" value="1" class="noteditable" checked="checked"/>是
									</span> 
									<span class='margin-top-8' style=" font-weight: normal">
										<input name="is_cfjc" type="radio" value="0" class="noteditable"/>否
									</span> 
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">最终措施</div>
									<div class="font-size-9 ">Final Measure</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<span class='margin-top-8' style="margin-right: 20px;font-weight: normal">
										<input name="is_zzcs" type="radio" value="1" checked="checked" class="noteditable"/>是
									</span> 
									<span class='margin-top-8' style=" font-weight: normal">
										<input name="is_zzcs" type="radio" value="0" class="noteditable"/>否
									</span> 
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">影响重量平衡</div>
									<div class="font-size-9 ">Affect Balance</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<span class='margin-top-8' style="margin-right: 20px;font-weight: normal">
										<input name="is_yxzlph" type="radio" value="1" checked="checked" class="noteditable"/>是
									</span> 
									<span class='margin-top-8' style=" font-weight: normal">
										<input name="is_yxzlph" type="radio" value="0" class="noteditable"/>否
									</span> 
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">免费航材</div>
									<div class="font-size-9 ">Free Material</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<span class='margin-top-8' style="margin-right: 20px;font-weight: normal">
										<input name="is_mfhc" type="radio" value="1" checked="checked" class="noteditable"/>是
									</span> 
									<span class='margin-top-8' style=" font-weight: normal">
										<input name="is_mfhc" type="radio" value="0" class="noteditable"/>否
									</span> 
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">自备航材</div>
									<div class="font-size-9 ">Own Material</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<span class='margin-top-8' style="margin-right: 20px;font-weight: normal">
										<input name="is_zbhc" type="radio" value="1" checked="checked" class="noteditable"/>是
									</span> 
									<span class='margin-top-8' style=" font-weight: normal">
										<input name="is_zbhc" type="radio" value="0" class="noteditable"/>否
									</span> 
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">需特殊工具</div>
									<div class="font-size-9 ">Special Tools</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<span class='margin-top-8' style="margin-right: 20px;font-weight: normal">
										<input name="is_tsgj" type="radio" value="1" checked="checked" class="noteditable"/>是
									</span> 
									<span class='margin-top-8' style=" font-weight: normal">
										<input name="is_tsgj" type="radio" value="0" class="noteditable"/>否
									</span> 
								</div>
							</div>
							<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">源文件内容</div>
									<div class="font-size-9 ">Source Content</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control padding-left-3 padding-right-3" id="ywjnr" style="height:55px"  maxlength="1000"></textarea>
								</div>
							</div>
		     				<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group"  >
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">背景</div>
									<div class="font-size-9 ">Background</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control padding-left-3 padding-right-3" id="bj"  style="height:55px" maxlength="1000"></textarea>
								</div>
							</div>		
							<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">文件关系解析</div>
									<div class="font-size-9 ">Analysis</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control padding-left-3 padding-right-3" id="wjgxjx" style="height:55px"  maxlength="1000"></textarea>
								</div>
							</div>
								
		     				<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">相关文件执行状态调查</div>
									<div class="font-size-9 "> Investigation</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control padding-left-3 padding-right-3" id="xgwjzxztdc" style="height:55px" maxlength="1000"></textarea>
								</div>
							</div>		
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-top-0 padding-left-0 padding-right-8  margin-bottom-10">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">涉及部门</div>
									<div class="font-size-9">Department</div>
								</span>
								<div  class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8 ">
									<div class="input-group" style="min-width:17%;min-height:17%;">
										<div   id="sjbmms" class='form-control base-color div-readonly-style' >
										</div>
				                	</div>
								</div>	
								<input type="hidden" name="sjbmid" id="sjbmid">
							</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">完成方式</div>
									<div class="font-size-9 ">Type</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<select class="noteditable form-control padding-left-3 padding-right-3" id="wclx">
									</select>
								</div>
							</div>
							</div>
							<div class="clearfix"></div>
							<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">工程评估结论</div>
									<div class="font-size-9 "> Conclusion</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control padding-left-3 padding-right-3" id="gcpgjl" style="height:55px"  maxlength="1000"></textarea>
								</div>
							</div>	
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">结论附件</div>
									<div class="font-size-9">Attachment</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<div class="input-group">
									<span class="input-group-addon inputgroupbordernone" style="">
								    	<span class="margin-left-0 span-input">
								    		<input id="scfjId" name="scfjId" value="" type="hidden">
								    		<input class="noteditable" id="attachmentcheckbox" name="attachmentcheckbox" onclick="Assessment_Open_Modal.onchangeAttachment(this)" type="checkbox" >&nbsp;
								    	</span>
								    </span>
								    <span id="fileuploaderSingle" class="singlefile input-group-btn" style="display: none">
									</span>
									<div class="font-size-12 line-height-30" id="scwjWbwjm" style="display: none"></div>
								   	 </div>
							    </div>
							</div>
				     		</form>
				
							</div>
						</div>
						<div class='clearfix'></div>
						<%@ include file="/WEB-INF/views/open_win/introduce_process_info.jsp" %> <!--流程信息 -->	
						<div class='clearfix'></div>
						<div id="attachments_list_edit" style="display:none">
								<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
						</div>
				</div>
		</div>	
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessment/assessment_open.js"></script><!--评估单弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessment/assessment_update.js"></script><!--新增评估单弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessment/assessment_open_find.js"></script><!--当前页面JS  -->
<%@ include file="/WEB-INF/views/open_win/department.jsp"%><!-- 选择部门 -->
<%@ include file="/WEB-INF/views/open_win/user_tree_multi.jsp"%><!-- 选择人员 -->
<%@ include file="/WEB-INF/views/project2/assessment/assessment_history_view_win.jsp"%><!------历史版本对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 附件对话框 -->
</body>
</html>
