<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="Assessment_Open_Modal" tabindex="-1" role="dialog" aria-hidden="true" data-keyboard="false" aria-labelledby="Assessment_Open_Modal" data-backdrop="static" >
	<div class="modal-dialog" id="xgpgrids" style='width:90%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
				<h4 class="modal-title" >
                	<div class='pull-left'>
                    	<div class="font-size-12 " id="modalName"></div>
						<div class="font-size-9 " id="modalEname"></div>
					</div>
				  	<div class='pull-right' >
		  		  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
		  		  	</div>
				  	<div class='clearfix'></div>
              	</h4>
            </div>
            <div class='clearfix'></div>
		    <div class="modal-body  " >
				<form id="assessmentForm" >	 
	           	<div class="col-xs-12  ">
						<!--隐藏域start -->
						
						<!--隐藏域 end-->
	           		<input type="hidden" id="pgd_dprtcode"  />
						<!--评估单号数据  -->
						<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 
							<div class="input-group-border margin-top-8 padding-left-0">
								<div id="xgpgrid1" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">技术评估单编号</div>
										<div class="font-size-9 ">Evaluation No.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" placeholder='不填写则自动生成'  maxlength="50" id="pgdh" name="pgdh" class="form-control padding-left-3 padding-right-3"  />
									</div>
								</div>
			           			<div id="xgpgrid2" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">版本</div>
										<div class="font-size-9 ">Rev.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 padding-top-10">
										<div class='input-group'>
											<span id="newbb" class='base-color' ></span> <span id="bbstate" >  ←  </span>
											<a href='javascript:void(0);'  onclick="Assessment_Open_Modal.findAssessment()" id="oldbb"></a>
											<input type="hidden" id="oldbbid" >
											<span class='input-group-btn' id="historyList">
												<i class="attachment-view2 glyphicon glyphicon glyphicon-list color-blue cursor-pointer"  style="font-size:15px"  style="float: left;text-decoration:none;position:relative; margin-left: 10px;"></i>
											</span>
									    </div>
									</div>
								</div>
								<div  class="xgpgrid col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">来源</div>
										<div class="font-size-9 ">Issued By</div>
									</label>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
										<div class="input-group input-group-new">
						                    <span class="input-group-addon">
						                    	<label><input type='radio' name='lylx'  value="1"/>&nbsp;适航性资料&nbsp;</label>
						                    </span>
						                     <span class="input-group-addon">
						                    	 <label><input type='radio' name='lylx' checked="checked" value="9"/>&nbsp;其他&nbsp;</label>
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
						<div class="clearfix"></div>
						<!--适应性资料STATR  -->
						<div class="xgpgrid">
   						<div id="airworthinessDiv" class="panel panel-default padding-right-0 ">
					        <div class="panel-heading bg-panel-heading">
				        		<div class="font-size-12 ">适航性资料</div>
								<div class="font-size-9">Airworthiness</div>
						    </div>
							<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
								<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
					   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">文件编号</div>
											<div class="font-size-9">File No.</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
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
										<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">文件版本</div>
											<div class="font-size-9 ">File Rev.</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<label class='margin-top-8 '>
												<a href='javascript:void(0);'  onclick="Assessment_Open_Modal.findAirworthiness()" id="shxzlbb"></a>
											</label>
										</div>
									</div>
					   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">修正案号</div>
											<div class="font-size-9 ">Amendment</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="shxzlxzah" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
										</div>
									</div>
					   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 margin-topnew-2">文件来源</div>
												<div class="font-size-9 ">Issued By</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"   id="shxzljswjly" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
										</div>
									</div>
					   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">文件类型</div>
											<div class="font-size-9 ">Type</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="shxzljswjlx" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
										</div>
									</div>
					   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 margin-topnew-2">上传日期</div>
												<div class="font-size-9 ">Upload Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="shxzlzdsj"  class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
										</div>
									</div>
							<!-- 		<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">文件主题</div>
											<div class="font-size-9">Subject</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea class="form-control padding-left-3 padding-right-3"  style="height:55px" maxlength="300" disabled="disabled" id="shxzljswjzt"></textarea>
										</div>
									</div> -->
					   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">颁发日期</div>
											<div class="font-size-9 ">Issue Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="shxzlbfrq" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
										</div>
									</div>
					   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 margin-topnew-2">生效日期</div>
												<div class="font-size-9 ">Effect Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="shxzlsxrq" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
										</div>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">到期日期</div>
											<div class="font-size-9 ">Due Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="shxzldqrq" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
										</div>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">源文件</div>
											<div class="font-size-9 ">File</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<label class='margin-top-8 '>
												<input type="hidden"  id="ywjid" />
												<a href='javascript:void(0);'  onclick="Assessment_Open_Modal.onclikfile()" id="ywjurl"></a>
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
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">关联技术评估单</div>
											<div class="font-size-9 ">Related Evaluation</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<div class="input-group" style="width: 100%">
												<input type="hidden"  name="glpgdid"  id="glpgdid" >
												<input type="text"   name="glpgd" readonly="readonly" class="form-control noteditable readonly-style colse" id="glpgd" ondblclick="Assessment_Open_Modal.updateGlpgdOption()" >
												<span class="required input-group-btn"  >
													<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Assessment_Open_Modal.updateGlpgdOption()">
														<i class="icon-search cursor-pointer" ></i>
													</button>
												</span>
						                	</div>
										</div>
<!-- 											<select placeholder='适航性资料下的相关评估单' id="glpgdid" class="noteditable form-control" onchange="Assessment_Open_Modal.changeGljspgd()"> -->
<!-- 											</select> -->
<!-- 										</div> -->
									</div>
					   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group"  >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">关联评估单版本</div>
											<div class="font-size-9 ">Rev.</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<label class='margin-top-8'>
												<a href='javascript:void(0);'  onclick="Assessment_Open_Modal.findGlAssessment()" id="glbb"></a>
											</label>
										</div>
									</div>
					   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">评估时间</div>
											<div class="font-size-9 ">Assess Time</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text" value="" id="glpgsj"  class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
										</div>
									</div>
									<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">关联技术评估单标题</div>
											<div class="font-size-9">Title</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea class="form-control padding-left-3 padding-right-3"  style="height:55px" id="glpgdzt" maxlength="300" readonly="readonly"></textarea>
										</div>
									</div>	
									<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">关联适航性资料</div>
											<div class="font-size-9">Related Doc.</div>
										</label>
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
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red">*</label>机型
											</div>
											<div class="font-size-9 ">A/C Type</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<select class="form-control" name="jx" id="Assessment_Open_Modal_jx">
											</select>
										</div>
									</div>
			            			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red">*</label>ATA章节号
											</div>
											<div class="font-size-9">ATA</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<div class="input-group" style="width: 100%">
												<input type="text"  name="zjh"  id="zjh" class="form-control" style="display: none;">
												<input type="text"  name="zjhms" class="form-control noteditable" id="zjhms"  >
												<span class="required input-group-btn" id="wxrybtn" >
													<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Assessment_Open_Modal.initFixChapter()">
														<i class="icon-search cursor-pointer" ></i>
													</button>
												</span>
						                	</div>
										</div>
									</div>
									 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">评估期限</div>
											<div class="font-size-9 ">Assess Limit</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="pgqx"  class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
										</div>
									</div>
			        
			 	     	<!-- 		     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">等级</div>
											<div class="font-size-9 ">Level</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="dj" class="noteditable form-control padding-left-3 padding-right-3" maxlength="15" />
										</div>
									 </div> -->
			            			 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">类型</div>
											<div class="font-size-9 ">Type</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<select class="noteditable form-control padding-left-3 padding-right-3" id="jjcd">
											</select>
											
<!-- 											<input type="text" id="jjcd" class="noteditable form-control padding-left-3 padding-right-3" maxlength="15"/> -->
										</div>
									 </div>
	     				 			 <div class="clearfix"></div>
			     				 	 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">
												<label class="required" style="color: red">*</label>标题
											</div>
											<div class="font-size-9 ">Title</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea  style="height:55px" class="noteditable form-control padding-left-3 padding-right-3" id="pgdzt" name="pgdzt"   maxlength="300"></textarea>
										</div>
									 </div>
					 				<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group"  >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">背景</div>
											<div class="font-size-9 ">Background</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea class="noteditable form-control padding-left-3 padding-right-3" id="bj"  
												style="height:55px" maxlength="1000"></textarea>
										</div>
									</div>	
									<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">源文件内容</div>
											<div class="font-size-9 ">Source Content</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea class="noteditable form-control padding-left-3 padding-right-3" id="ywjnr" 
												style="height:55px"  maxlength="1000"></textarea>
										</div>
									</div>
									
						 
									<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">时限要求</div>
											<div class="font-size-9 ">Limit Request</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea class="noteditable form-control" id="sxyq"  
												style="height:55px" maxlength="1000"></textarea>
										</div>
									</div>
					<!-- 				 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">涉及改装</div>
											<div class="font-size-9 ">Refit</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea class="noteditable form-control padding-left-3 padding-right-3" id="sjgz"  
												style="height:55px" maxlength="1000"></textarea>
										</div>
									 </div>
									 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">文件摘要</div>
											<div class="font-size-9 ">Summary</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea class="noteditable form-control padding-left-3 padding-right-3" id="wjzy" 
												style="height:55px"  maxlength="1000"></textarea>
										</div>
									 </div> -->
									 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">备注</div>
											<div class="font-size-9 ">Note</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea class="noteditable form-control padding-left-3 padding-right-3" id="bz"   
												style="height:55px" maxlength="300"></textarea>
										</div>
									 </div>
			     					 <div class="clearfix"></div>
								</div>
							</div>
					
	     					<!-- 参考文件列表 -->  
   							<%@ include file="/WEB-INF/views/project2/assessmenttwo/reference_list_two.jsp"%>
   							<%@ include file="/WEB-INF/views/project2/assessmenttwo/reference_list_view_two.jsp"%>
     						<!-- 参考文件 -->
			     			<div class="clearfix"></div>
		     				<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">源文件范围</div>
										<div class="font-size-9 ">Range</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="noteditable form-control padding-left-3 padding-right-3" id="syfw_ywj" 
											style="height:55px"  maxlength="1000"></textarea>
									</div>
							</div> 
			     	
		     				<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">本单位适用性</div>
									<div class="font-size-9 ">Applicability</div>
								</label>
								<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
									<div class="input-group input-group-new">
					                    <span class="input-group-addon" style="padding-left:0px">
					                    	<label><input type='radio' class="noteditable" onclick="Assessment_Open_Modal.onchangesyx()" name='syx' value="1"checked />&nbsp;适用&nbsp;</label>
					                    </span>
					                     <span class="input-group-addon">
					                    	 <label><input type='radio' class="noteditable"  onclick="Assessment_Open_Modal.onchangesyx()" name='syx' value="0"/>&nbsp;不适用&nbsp;</label>
										 </span>
					                     <input type="text" class="noteditable form-control" id="fsyyy" maxlength="300">
				                	</div>
								</div>
							</div>
								<!-- 适用性设置开始 -->
							<%@ include file="/WEB-INF/views/project2/assessment/applicable.jsp"%>
							<!-- 适用性设置结束 -->
							
		<!-- 		     		<div id="sylbDiv" class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">本单位适用类别</div>
									<div class="font-size-9 ">Type</div>
								</label>
								<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
									<div class="input-group input-group-new">
					                    <span class="input-group-addon" style="padding-left:0px">
					                    	<label><input type='radio' class="noteditable" name='sylb' value="1" checked/>&nbsp;飞机&nbsp;</label>
					                    </span>
					                     <span class="input-group-addon">
					                    	 <label><input type='radio' class="noteditable" name='sylb' value="2"/>&nbsp;发动机&nbsp;</label>
										 </span>
					                     <span class="input-group-addon">
					                    	 <label><input type='radio' class="noteditable" name='sylb' value="3" />&nbsp;APU&nbsp;</label>
										 </span>
					                     <span class="input-group-addon">
					                    	 <label><input type='radio' class="noteditable" name='sylb' value="99" />&nbsp;其他部件&nbsp;</label>
										 </span>
										<input type="text" class="form-control" id="biaoshi">
				                	</div>
								</div>
							</div> -->
				     		<div id="syfwDiv" class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">本单位适用范围</div>
									<div class="font-size-9 ">Range</div>
								</label>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control padding-left-3 padding-right-3" id="syfw_bdw" 
										style="height:55px"  maxlength="1000"></textarea>
								</div>
							</div>
		     				<div id="orderDiv" class="col-xs-12 margin-top-0 padding-left-0  padding-right-0" >
			     				<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">下达指令</div>
										<div class="font-size-9">Order</div>
									</label>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
										<div class="input-group input-group-new">
											<span class="input-group-addon" style='padding-left:0px!important;'>
						                    	<label><input class="noteditable" type='checkbox' onclick="Assessment_Open_Modal.onchangeOrder('eo')"  name='eo' />&nbsp;</label>
						                    </span>
					                    <div class="input-group-addon fixed-width-style " >
						                     <div class="font-size-12 margin-topnew-2">工程指令EO</div>
											<div class="font-size-9 margin-top-4">EO</div>
										 </div>
										    <input type="hidden"  id="eocode" value="6">
										    <input type="hidden"  id="eoid" >
										    <input type="text"  id="eo" class="form-control noteditable readonly-style colse"   ondblclick='Assessment_Open_Modal.openUserWin("eo");' readonly="readonly">
						                    <div class="required input-group-addon input-group-addon-btn" id="eor" style='padding:0px;'>
		                                       <button type="button" onclick='Assessment_Open_Modal.openUserWin("eo");' class='btn btn-default' style='height:34px;border-left:0px;'>
		                                       		<i class='icon-search' ></i>
		                                       </button>
						                    </div>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0" >
									</label>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new">
										<span class="input-group-addon" style='padding-left:0px!important;'>
					                    <label><input class="noteditable" type='checkbox' name='tb' onclick="Assessment_Open_Modal.onchangeOrder('tb')" />&nbsp;</label>
					                    </span>
					                    <div class="input-group-addon fixed-width-style">
					                     <div class="font-size-12 margin-topnew-2">维护提示</div>
										<div class="font-size-9 margin-top-4">MT</div>
										 </div>
										    <input type="hidden"  id="tbcode" value="1">
										 	<input type="hidden"  id="tbid"  >
					                      	<input type="text"  class="form-control noteditable readonly-style colse" id="tb" readonly="readonly" ondblclick='Assessment_Open_Modal.openUserWin("tb");'>
						                    <div class="required input-group-addon input-group-addon-btn" style='padding:0px;' id="tbr" >
		                                       <button type="button" class='btn btn-default' style='height:34px;border-left:0px;' onclick='Assessment_Open_Modal.openUserWin("tb");'>
		                                       	<i class='icon-search' ></i>
		                                       </button>
						                    </div>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									</label>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new">
										<span class="input-group-addon" style='padding-left:0px!important;'>
					                    <label><input class="noteditable" type='checkbox' name='maintenance' onclick="Assessment_Open_Modal.onchangeOrder('maintenance')" />&nbsp;</label>
					                    </span>
					                    <div class="input-group-addon fixed-width-style">
					                     	<div class="font-size-12 margin-topnew-2">修订维修方案</div>
											<div class="font-size-9 margin-top-4">Maintenance</div>
										 </div>
										 	<input type="hidden"  id="maintenancecode" value="3">
										 	<input type="hidden"  id="maintenanceid" >
					                   		<input type="text"  class="form-control noteditable readonly-style colse" id="maintenance" readonly="readonly" ondblclick='Assessment_Open_Modal.openUserWin("maintenance");'>
						                    <div class="required input-group-addon input-group-addon-btn" style='padding:0px;' id="maintenancer">
	                                       		<button onclick='Assessment_Open_Modal.openUserWin("maintenance");' type="button" class='btn btn-default' style='height:34px;border-left:0px;'>
	                                       			<i class='icon-search' ></i>
	                                       		</button>
					                    	</div>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									</label>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new">
										<span class="input-group-addon" style='padding-left:0px!important;'>
					                    <label><input class="noteditable" type='checkbox' name='technicalOrder' onclick="Assessment_Open_Modal.onchangeOrder('technicalOrder')" />&nbsp;</label>
					                    </span>
					                    <div class="input-group-addon fixed-width-style">
					                     <div class="font-size-12 margin-topnew-2">技术指令</div>
										<div class="font-size-9 margin-top-4">Technical Order</div>
										 </div>
											 <input type="hidden"  id="technicalOrdercode" value="2">
											 <input type="hidden"  id="technicalOrderid"  >
						                     <input type="text"  class="form-control noteditable readonly-style colse" id="technicalOrder" readonly="readonly" ondblclick='Assessment_Open_Modal.openUserWin("technicalOrder");'>
							                 <div class="required input-group-addon input-group-addon-btn" style='padding:0px;' id="technicalOrderr">
			                                     <button type="button" class='btn btn-default' style='height:34px;border-left:0px;' onclick='Assessment_Open_Modal.openUserWin("technicalOrder");'>
			                                   	  <i class='icon-search' ></i>
			                                     </button>
						                     </div>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									</label>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new">
										<span class="input-group-addon" style='padding-left:0px!important;'>
					                    <label><input class="noteditable" type='checkbox'  name='mel' onclick="Assessment_Open_Modal.onchangeOrder('mel')" />&nbsp;</label>
					                    </span>
					                    <div class="input-group-addon fixed-width-style">
					                     <div class="font-size-12 margin-topnew-2">MEL更改</div>
										<div class="font-size-9 margin-top-4">MEL</div>
										 </div>
										 <input type="hidden"  id="melcode" value="7">
										 <input type="hidden"  id="melid" >
					                     <input type="text"  class="form-control noteditable readonly-style colse" id="mel" readonly="readonly" ondblclick='Assessment_Open_Modal.openUserWin("mel");'>
						                 <div class="required input-group-addon input-group-addon-btn" style='padding:0px;' id="melr" >
		                                     <button type="button" class='btn btn-default' style='height:34px;border-left:0px;' onclick='Assessment_Open_Modal.openUserWin("mel");'>
		                                     	<i class='icon-search' ></i>
		                                     </button>
					                     </div>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									</label>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new">
										<span class="input-group-addon" style='padding-left:0px!important;'>
					                    <label><input class="noteditable" type='checkbox' name='workCard'  onclick="Assessment_Open_Modal.onchangeOrder('workCard')" />&nbsp;</label>
					                    </span>
					                    <div class="input-group-addon fixed-width-style">
					                     <div class="font-size-12 margin-topnew-2">修订工卡</div>
										<div class="font-size-9 margin-top-4">Work Card</div>
										 </div>
										 <input type="hidden"  id="workCardcode" value="8">
										 <input type="hidden"  id="workCardid" >
					                     <input type="text"  class="form-control noteditable readonly-style colse" id="workCard" readonly="readonly" ondblclick='Assessment_Open_Modal.openUserWin("workCard");'>
						                 <div class="required input-group-addon input-group-addon-btn" style='padding:0px;' id="workCardr" >
		                                     <button type="button" class='btn btn-default' style='height:34px;border-left:0px;' onclick='Assessment_Open_Modal.openUserWin("workCard");'>
		                                     	<i class='icon-search' ></i>
		                                     </button>
					                     </div>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									</label>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new">
										<span class="input-group-addon" style='padding-left:0px!important;'>
					                    <label><input class="noteditable" type='checkbox' name='nrc'  onclick="Assessment_Open_Modal.onchangeOrder('nrc')" />&nbsp;</label>
					                    </span>
					                    <div class="input-group-addon fixed-width-style">
					                     <div class="font-size-12 margin-topnew-2">下达工单（维修指令）</div>
										<div class="font-size-9 margin-top-4">W/O</div>
										</div>
										<input type="hidden"  id="nrccode" value="4">
										<input type="hidden"  id="nrcid" >
					                    <input type="text"  class="form-control noteditable readonly-style colse" id="nrc" readonly="readonly" ondblclick='Assessment_Open_Modal.openUserWin("nrc");'>
						                <div class="required input-group-addon input-group-addon-btn" style='padding:0px;' id="nrcr" >
		                                     <button type="button" class='btn btn-default' style='height:34px;border-left:0px;' onclick='Assessment_Open_Modal.openUserWin("nrc");'>
		                                     	<i class='icon-search' ></i>
		                                     </button>
					                    </div>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
									<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									</label>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new">
										<span class="input-group-addon" style='padding-left:0px!important;'>
					                    <label><input class="noteditable" type='checkbox' name='other'  onclick="Assessment_Open_Modal.onchangeOrder('other')" />&nbsp;</label>
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
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">办理期限</div>
									<div class="font-size-9 ">For Duration</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
									<input type="text"  id="yjbfzlsj" class="noteditable form-control date-picker"  />
								</div>
							</div>
							<div id="yjbfzlDiv" class="col-lg-9 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">预计颁发指令</div>
									<div class="font-size-9 ">Directive</div>
								</label>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 label-input-div">
									<input type="text"  id="yjbfzl" class="noteditable form-control "  maxlength="300"/>
								</div>
							</div>
						
		     		<!-- 		<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">关闭条件</div>
									<div class="font-size-9 ">Condition</div>
								</label>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control" id="gbtj" 
									style="height:55px"  maxlength="300"></textarea>
								</div>
							</div>	 -->
							<div  id="repeatid">
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">重复检查</div>
									<div class="font-size-9 ">Repeat Check</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
									<label class='margin-top-8' style="margin-right: 20px; font-weight: normal">
										<input name="is_cfjc" type="radio" value="1" class="noteditable" checked="checked"/>
									是</label> 
									<label class='margin-top-8' style=" font-weight: normal">
										<input name="is_cfjc" type="radio" value="0" class="noteditable"/>
									否</label> 
								</div>
							</div>
							<!-- <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">最终措施</div>
									<div class="font-size-9 ">Final Measure</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
									<label class='margin-top-8' style="margin-right: 20px;font-weight: normal">
										<input name="is_zzcs" type="radio" value="1" checked="checked" class="noteditable"/>
										是
									</label> 
									<label class='margin-top-8' style=" font-weight: normal">
										<input name="is_zzcs" type="radio" value="0" class="noteditable"/>
										否
									</label> 
								</div>
							</div> -->
							
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">免费航材</div>
									<div class="font-size-9 ">Free Material</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
									<label class='margin-top-8' style="margin-right: 20px;font-weight: normal">
										<input name="is_mfhc" type="radio" value="1" checked="checked" class="noteditable"/>
										是
									</label> 
									<label class='margin-top-8' style=" font-weight: normal">
										<input name="is_mfhc" type="radio" value="0" class="noteditable"/>
										否
									</label> 
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">自备航材</div>
									<div class="font-size-9 ">Own Material</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
									<label class='margin-top-8' style="margin-right: 20px;font-weight: normal">
										<input name="is_zbhc" type="radio" value="1" checked="checked" class="noteditable"/>
										是
									</label> 
									<label class='margin-top-8' style=" font-weight: normal">
										<input name="is_zbhc" type="radio" value="0" class="noteditable"/>
										否
									</label> 
								</div>
							</div>
							 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">影响重量平衡</div>
									<div class="font-size-9 ">Affect Balance</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
									<label class='margin-top-8' style="margin-right: 20px;font-weight: normal">
										<input name="is_yxzlph" type="radio" value="1" checked="checked" class="noteditable"/>
										是
									</label> 
									<label class='margin-top-8' style=" font-weight: normal">
										<input name="is_yxzlph" type="radio" value="0" class="noteditable"/>
										否
									</label> 
								</div>
							</div> 
				<!-- 			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">需特殊工具</div>
									<div class="font-size-9 ">Special Tools</div>
								</label>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
									<label class='margin-top-8' style="margin-right: 20px;font-weight: normal">
										<input name="is_tsgj" type="radio" value="1" checked="checked" class="noteditable"/>
										是
									</label> 
									<label class='margin-top-8' style=" font-weight: normal">
										<input name="is_tsgj" type="radio" value="0" class="noteditable"/>
										否
									</label> 
								</div>
							</div> -->
					
		     		
				<!-- 			<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">文件关系解析</div>
										<div class="font-size-9 ">Analysis</div>
								</label>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control padding-left-3 padding-right-3" id="wjgxjx" 
										style="height:55px"  maxlength="1000"></textarea>
								</div>
							</div>
							-->
		     				<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">相关文件执行状态调查</div>
									<div class="font-size-9 "> Investigation</div>
								</label>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control padding-left-3 padding-right-3" id="xgwjzxztdc"  
										style="height:55px" maxlength="1000"></textarea>
								</div>
							</div>	 
							
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-top-0 padding-left-0 padding-right-8  margin-bottom-10">
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">涉及部门</div>
									<div class="font-size-9">Department</div>
								</label>
								<div  class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
									<div class="input-group" style="min-width:17%;min-height:17%;">
										<div ondblclick="Assessment_Open_Modal.openzrdw()"  id="sjbmms" class='form-control base-color  div-readonly-style colse-div' style='border-right:1px solid #d5d5d5;border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'>
										</div> 
					                    <div class="required input-group-addon btn btn-default " style='padding-left:0px;padding-right:0px;width:38px;' onclick="Assessment_Open_Modal.openzrdw()"><i class='icon-search'></i></div>
				                	</div>
								</div>	
								<input type="hidden" name="sjbmid" id="sjbmid">
							</div>	
			    			 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">完成方式</div>
											<div class="font-size-9 ">Type</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<select class="noteditable form-control padding-left-3 padding-right-3" id="wclx">
											</select>
										</div>
									 </div>
							</div>
							<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">工程评估结论</div>
									<div class="font-size-9 "> Conclusion</div>
								</label>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control padding-left-3 padding-right-3" id="gcpgjl" 
									style="height:55px"  maxlength="1000"></textarea>
								</div>
							</div>	
					
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">结论附件</div>
									<div class="font-size-9">Attachment</div>
								</label>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<div class="input-group">
									<span class="input-group-addon inputgroupbordernone" style="">
								    	<label class="margin-left-0 label-input">
								    		<input id="scfjId" name="scfjId" value="" type="hidden">
								    		<input class="noteditable" id="attachmentcheckbox" name="attachmentcheckbox" onclick="Assessment_Open_Modal.onchangeAttachment(this)" type="checkbox">&nbsp;
								    	</label>
								    </span>
								    <span id="fileuploaderSingle" class="singlefile input-group-btn" style="display: none">
									</span>
									<div class="font-size-12 line-height-30" id="scwjWbwjm" style="display: none"></div>
							   	 </div>
							    </div>
							</div>
							<div class="clearfix"></div>
					   	</div>
						<!--参考文件END  -->		
						<div class='clearfix'></div>
						<%@ include file="/WEB-INF/views/open_win/introduce_process_info.jsp" %> <!--流程信息 -->	
						<div class='clearfix'></div>
						<div id="attachments_list_edit" style="display:none">
							<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
						</div>
						</div>
					<div class="pgclass input-group-border margin-top-0 padding-left-0">
		            	<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">原评估人</div>
								<div class="font-size-9">Assessor</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
								<input type="text"  name="ypgr" id="ypgr"  class="form-control   "  readonly="readonly" >
							</div>
						</div>
		            	<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>评估人</div>
								<div class="font-size-9">Assessor</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
							  <div class='input-group'>
									<input type="hidden"  name="pgrid"  id="pgrid">
										<input type="hidden"  name="pgrbmdm"  id="pgrbmdm">
										<input type="text"  name="pgr" id="pgr"  class="form-control  readonly-style "  readonly="readonly" ondblclick="Assessment_Open_Modal.openUserWin('pgr');">
										<span class=" input-group-btn" >
										<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Assessment_Open_Modal.openUserWin('pgr');">
											<i class="icon-search cursor-pointer" ></i>
										</button>
									</span>
								</div>
							</div>
						</div>
						<div class='clearfix'></div>
					</div>
				</div>
			</form>
			</div>	
		 	<div class="clearfix"></div>
			<div class="modal-footer ">
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="pull-left modalfootertip" >
				               <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
					 	<span class="input-group-addon modalfooterbtn">
							<button id="shtg" type="button" onclick="Assessment_Audit_Modal.passed();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">审核通过</div>
								<div class="font-size-9">Audited</div>
							</button>
			        		<button id="sptg" type="button" onclick="Assessment_Approval_Modal.passed();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">批准通过</div>
								<div class="font-size-9">Approved</div>
							</button>
			         		<button id="shbh" type="button" onclick="Assessment_Audit_Modal.turnDown();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">审核驳回</div>
								<div class="font-size-9">Audit Reject</div>
							</button>
						   	<button id="spbh" type="button" onclick="Assessment_Approval_Modal.turnDown();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">批准驳回</div>
								<div class="font-size-9">Rejected</div>
							</button>
						   	<button id="baocuns" type="button" onclick="Assessment_Add_Modal.save();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
					   		<button id="tijiao" type="button" onclick="Assessment_Add_Modal.submit();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
						 	<button id="xiugaipgr" type="button" onclick="Assessment_Update_Assessor_Modal.save();" class="btn btn-primary padding-top-1 padding-bottom-1">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessmenttwo/assessment_open_two.js"></script><!--评估单弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessmenttwo/assessment_add_two.js"></script><!--新增评估单弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessmenttwo/assessment_update_two.js"></script><!--新增评估单弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessmenttwo/assessment_revision_two.js"></script><!--改版评估单弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessmenttwo/assessment_update_assessor_two.js"></script><!--修改评估人弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessmenttwo/audit/assessment_audit_two.js"></script><!--审核评估单弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessmenttwo/approval/assessment_approval_two.js"></script><!--批准评估单弹窗的js  -->
<%@ include file="/WEB-INF/views/project2/assessmenttwo/selectAssociatedEvaluation_two.jsp"%><!-- -选择评估单列表 -->
