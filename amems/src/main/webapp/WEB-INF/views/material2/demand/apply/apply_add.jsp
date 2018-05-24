<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="demand_apply_add_alert" tabindex="-1" role="dialog"  aria-labelledby="demand_apply_add_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:50%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">添加物料信息</div>
							<div class="font-size-12" id="modalHeadENG">Add Material Info</div>
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
		            <form id="demand_detail_form">
		            <input type="hidden" id="info_id" />
		            <input type="hidden" id="info_rowid" />
		            <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class="identifying" style="color: red">*</span>物料类别</div>
							<div class="font-size-9 ">Type</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<select id="info_wllb" name="info_wllb" class="form-control" >
								<option value="1" selected="true">航材</option>
								<option value="2">工具设备</option>
								<option value="4">化工品</option>
								<option value="5">低值易耗品</option>
								<option value="6">松散件</option>
								<option value="0">其它</option>
							</select>
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class="identifying" style="color: red">*</span>件号</div>
							<div class="font-size-9 ">P/N</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="info_bjid" type="hidden">
							<div class="input-group" style="width: 100%;">
							    <input id="info_bjh" name="info_bjh" ondblclick="chooseChildMaterial()" onblur="loadMaterialData()" class="form-control" type="text" maxlength="50">
								<span class="input-group-btn">
									<button type="button" onclick="chooseChildMaterial()" class="btn btn-default" data-toggle="modal">
										<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
						   </div>
						</div>
				    </div>
				    <div class='clearfix'></div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">名称/描述</div>
							<div class="font-size-9 ">Name/Desc</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="info_bjmc" class="form-control" maxlength="200" type="text">
						</div>
				    </div>
				     <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">型号</div>
							<div class="font-size-9 ">Model</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="info_xingh" name="info_xingh" class="form-control" maxlength="50" type="text">
						</div>
				    </div>
				    <div class='clearfix'></div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">规格</div>
							<div class="font-size-9 ">Specifications</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="info_gg" name="info_gg" class="form-control" maxlength="50" type="text">
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">件号来源</div>
							<div class="font-size-9 ">Original</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="info_jhly" class="form-control" maxlength="100" type="text">
						</div>
				    </div>
				    <div class='clearfix'></div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">ATA章节号</div>
							<div class="font-size-9 ">ATA</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="hidden" id="info_zjh" />
							<div class="input-group" style="width: 100%;">
							    <input id="info_zjhms" ondblclick="" class="form-control" type="text" maxlength="20">
								<span class="input-group-btn">
									<button type="button" id="info_zjh_btn" onclick="chooseFixChapter()" class="btn btn-default" data-toggle="modal">
										<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
						   </div>
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">标准件号</div>
							<div class="font-size-9 ">Normal P/N</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="info_bzjh" name="info_bzjh" class="form-control" maxlength="50" type="text">
						</div>
				    </div>
				    <div class='clearfix'></div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class="identifying" style="color: red">*</span>需求数量</div>
							<div class="font-size-9 ">Demand Quantity</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group" style="width: 100%;">
							    <input id="info_xqsl" name="info_xqsl" class="form-control" maxlength="10" type="text">
								<span class="input-group-addon" style='padding-top:0px;padding-bottom:0px;background:none;border:0px;'>
									<select style='height:34px;border:1px solid #d5d5d5;border-left:0px;color:rgb(85, 85, 85);' id="info_dw">
									</select>
								</span>
						   </div>
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">序列号</div>
							<div class="font-size-9 ">S/N</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="info_xlh" name="info_xlh" class="form-control" onblur="limitXqsl()" maxlength="50" type="text">
						</div>
				    </div>
				    <div class='clearfix'></div>
				    <div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">替换件</div>
							<div class="font-size-9 ">Replace P/N</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<input id="info_thj" class="form-control" maxlength="300" type="text">
						</div>
				    </div>
				    <div class='clearfix'></div>
				    </form>
		            </div>
	            </div> 
	             <div class='clearfix'></div>          
           	</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
	                    	<button id="save_btn" type="button" onclick="saveMaterialInfo()" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="submit_btn" type="button" onclick="writeNext()"  class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">编写下一条</div>
								<div class="font-size-9">Write the next</div>
							</button>
	                    </span>
	               	</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--  弹出框结束-->

<script type="text/javascript" src="${ctx}/static/js/thjw/material2/demand/apply/apply_add.js"></script>