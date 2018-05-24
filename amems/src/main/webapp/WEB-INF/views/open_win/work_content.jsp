<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/work_content.js"></script>
<div class="modal fade" id="WorkContentModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 50%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
			  	<div class="panel panel-primary ">
					<div class="panel-heading  padding-top-3 padding-bottom-1  margin-bottom-10">
						<div class="font-size-16 line-height-18">工作内容</div>
						<div class="font-size-9 ">Contents</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
					
		            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">项目来源</div>
									<div class="font-size-9 line-height-18">Source</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" id="wcxmly" class="form-control "  maxlength="100"/>
								</div>
							</div>
							
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
									<input type="text" id="wczjhName" class="form-control " readonly/>
									<input type="hidden" class="form-control" id="wczjh" />
									<span class="input-group-btn">
										<button type="button" class="btn btn-primary form-control" data-toggle="modal" onclick="WorkContentModal.openChapterWin()">
										<i class="icon-search "></i>
										</button>
									</span>
								</div>
							</div>
							
							<div class="clearfix"></div>
						
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">中文描述</div>
									<div class="font-size-9 line-height-18">CH.Name</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<!-- <input type="text" id="wcscmsZw" class="form-control "  maxlength="300"/> -->
									<textarea class="form-control" id="wcscmsZw" maxlength="300" ></textarea>
								</div>
							</div>
							
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">英文描述</div>
									<div class="font-size-9 line-height-18">F.Name</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<!-- <input type="text" id="wcscmsYw" class="form-control "  maxlength="300" /> -->
									<textarea class="form-control" id="wcscmsYw" maxlength="1000" ></textarea>
								</div>
							</div>
							
							<div class="clearfix"></div>
							
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">厂家手册及版本</div>
									<div class="font-size-9 line-height-18">Manual and Revision</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" id="wccksc" class="form-control "  maxlength="100" />
								</div>
							</div>
							
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">厂家工卡及版本</div>
									<div class="font-size-9 line-height-18">W/C and Revision</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" id="wcckgk" class="form-control "  maxlength="100" />
								</div>
							</div>
							
							<div class="clearfix"></div>
						
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">检查类型</div>
									<div class="font-size-9 line-height-18">Check Type</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<select id="wcjclx" class="form-control" >
										<option value=''></option>
									</select>
								</div>
							</div>
							
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">工作站位</div>
									<div class="font-size-9 line-height-18">Location</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<select id="wcgzzw" class="form-control" >
									</select>
								</div>
							</div>
						
							<div class="clearfix"></div>
							
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">有效性</div>
									<div class="font-size-9 line-height-18">Effectivity</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<select id="wczt" class="form-control" >
										<option value='1'>有效</option>
										<option value='0'>无效</option>
									</select>
								</div>
							</div>
							
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">必检</div>
									<div class="font-size-9 line-height-18">Check</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<label style="margin-right: 20px;font-weight: normal">
										<input name="wcisBj" type="radio" value="1" />是
							 		</label> 
									<label style="font-weight: normal">
										<input name="wcisBj" type="radio" value="0" checked />否 
									</label> 
								</div>
							</div>
							
							<div class="clearfix"></div>
							
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">工作地点</div>
									<div class="font-size-9 line-height-18">workplace</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" id="wwz" class="form-control "  maxlength="16" />
								</div>
							</div>
							
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">MI</div>
									<div class="font-size-9 line-height-18">MI</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="checkbox" id="wcisMi" checked="checked" />
								</div>
							</div>
							
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">工作类型</div>
									<div class="font-size-9 line-height-18">Work Type</div>
								</label>
								<div id="wcgzlxdiv" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								</div>
							</div>
							
							<div class="clearfix"></div>
							
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>适用性</div>
									<div class="font-size-9 line-height-18">Applicability</div>
								</label>
								<div id="wcfjzchdiv" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								</div>
							</div>
						
						 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</label>
								<div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
									<textarea class="form-control" id="wcbz" maxlength="300" ></textarea>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="text-right margin-top-10 margin-bottom-10">
							<button type="button" onclick="WorkContentModal.setData()" class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
		                </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
