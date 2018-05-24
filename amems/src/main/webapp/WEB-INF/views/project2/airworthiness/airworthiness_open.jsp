<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal modal-new" id="AddalertModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:85%'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
		                      <div class="font-size-14 " id="modalName"></div>
							  <div class="font-size-12" id="modalEname"></div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
              	<div class="col-xs-12 margin-top-8 ">
              	<div class="input-group-border">
					<form id="form">
					
                    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>文件类型</div>
							<div class="font-size-9">Doc. type</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control ' id="jswjlx" >
									<option value="" >请选择</option>
							</select>
						</div>
					</div>
					
					 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>来源</div>
							<div class="font-size-9">Issued By</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control ' id="jswjly">
								<option value="" >请选择</option>
							</select>
						</div>
					</div>
					<div class='clearfix'></div>
                    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>文件编号</div>
							<div class="font-size-9">Doc. No.</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control " id="jswjwjbh" name="jswjwjbh" maxlength="50" />
							<input type="hidden" class="form-control " id="airworthinessId" name="airworthinessId" />
							<input type="hidden" class="form-control " id="airworthinessDprtcode" name="airworthinessDprtcode" />
						</div>
					</div>
					
                    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>版本</div>
							<div class="font-size-9">Rev</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control " id="jswjbb" name="jswjbb" maxlength="10"/>
						</div>
					</div>
					
					
                    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">修正案号</div>
							<div class="font-size-9">Amendment</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control " id="xzah" name="xzah" maxlength="15" />
						</div>
					</div>
					
					<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">主题</div>
							<div class="font-size-9">Subject</div>
						</label>
						<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea id="zhut" name="zhut" class="form-control" maxlength="300" style="height:55px"></textarea>
						</div>
					</div>
					
					<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">ATA章节号</div>
							<div class="font-size-9">ATA</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group">
								<input type="text"  name="zjhs"  id="zjhs" class="form-control" value="##" style="display: none;">
								<input id="wczjh"  name="zjh" class="form-control "  type="text" >
								<input id="shxzlid" class="form-control" type="hidden">
					            <span class="input-group-btn">
									<button type="button" class="btn btn-default form-control" style="height:34px;" data-toggle="modal" onclick="openList()">
										<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
					       </div>
						</div>
					</div>
					
					</form>
					
					<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">ATA英文描述</div>
							<div class="font-size-9">English Desc</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text"  id="ataywmc" class="form-control" disabled="disabled"/>
						</div>
					</div>
					
					<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">ATA中文描述</div>
							<div class="font-size-9">Chinese Desc</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text"  id="atazwmc" class="form-control" disabled="disabled"/>
						</div>
					</div>
					
					<div class='clearfix'></div>
					<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">关联适航性资料</div>
							<div class="font-size-9">Related Doc.</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
			                <div class="input-group">
								<input type="text"  id="glshxzlBh" class="form-control readonly-style" readonly="readonly"  ondblclick="openTech()" />
								<input type="hidden"  id="glshxzlid" class="form-control" />
					            <span class="input-group-btn" >
									<button type="button" class="btn btn-default form-control" style="height:34px;" data-toggle="modal" onclick="openTech()">
										<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
					       </div>
						</div>
					</div>
					
					<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">关联主题</div>
							<div class="font-size-9">Related Subject</div>
						</label>
						<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea id="gljspgdzhut" name="gljspgdzhut" class="form-control" disabled="disabled" maxlength="100" style="height:55px"></textarea>
						</div>
					</div>
					
					<div class='clearfix'></div>
					 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">颁发日期</div>
							<div class="font-size-9">Issue Date</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="jswjbfrq" name="jswjbfrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					
					 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">生效日期</div>
							<div class="font-size-9">Effect Date</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						 	<input class="form-control date-picker" id="jswjsxrq" name="jswjsxrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					
					 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">到期日期</div>
							<div class="font-size-9">Due Date</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="jswjdqrq" name="jswjdqrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					
					<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">备注</div>
							<div class="font-size-9">Note</div>
						</label>
						<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea id="bz" name="bz" class="form-control" maxlength="300" style="height:55px"></textarea>
						</div>
					</div>
					
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">源文件</div>
							<div class="font-size-9">File</div>
						</label>
						<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<div class="input-group">
								<span class="input-group-addon inputgroupbordernone" style="">
							    	<label class="margin-left-0 label-input">
							    		<input id="scfjId" name="scfjId" value="" type="hidden" >
							    		<input id="scfjBox" name="scfjBox" onclick="scfjBox(this)" type="checkbox" > &nbsp;
							    	</label>
							    </span>
							    <span id="fileuploaderSingle" class="singlefile input-group-btn" style="display: none">
								</span>
								<div class="font-size-12 line-height-30" id="scwjWbwjm" style="display: none"></div>
						    </div>
					    </div>
					</div>
					<div class="clearfix"></div>
					
					
					 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">是否评估</div>
							<div class="font-size-9">Is Estimate</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
							<label class='margin-right-5 label-input' >
								<input name="Ispg" type="radio" value="1" checked onclick="syjxshowAndhide()" />&nbsp;需评估
							</label>
							<label class='label-input'>
								<input name="Ispg" type="radio" value="0"  onclick="syjxshowAndhide()" />&nbsp;无需评估
							</label>
						</div>
					</div>
					
					<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="pgqxDiv">
						<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>评估期限</div>
							<div class="font-size-9">Assess Limit</div>
						</label>
						<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="jswjpgqx" name="jswjpgqx" data-date-format="yyyy-mm-dd" type="text"/>
						</div>
					</div>
					<div class="clearfix"></div>
					<div id="jxchecked">
					</div>
					
					<div class="clearfix"></div>	
						</div>
						 
				</div>
                <div class="clearfix"></div>              
           </div>
           <div class="modal-footer">
           		<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
					<span class="input-group-addon modalfootertip" >
		                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
					</span>
	                    <span class="input-group-addon modalfooterbtn">
	                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="saveUpdate(0)" id="baocunButton">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
	                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="saveUpdate(1)">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
		                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
						    </button>
	                    </span>
	               	</div><!-- /input-group -->
				</div>
           
				<div class="clearfix"></div> 
				
			</div>
            </div>
          </div>
	</div>
<!--  弹出框结束-->