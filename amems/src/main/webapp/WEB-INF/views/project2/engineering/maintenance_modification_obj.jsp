<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	  <div class="panel panel-primary" id="open_win_maintenance_modification_modal">
	<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
		<a data-toggle="collapse" data-parent="#accordion" href="#objectCollapsed" class="collapsed">
		<div class="pull-left">
		<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
		</div>
		<div class="pull-left">
		<div class="font-size-12">维修改装/对象 </div>
		<div class="font-size-9 ">Object</div>
		</div>
		<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
		<div class="clearfix"></div>
		</a>
	</div>
	<div id="objectCollapsed" class="panel-collapse collapse" >
		<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;padding-right:0px;' >
					 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>部件</div>
								<div class="font-size-9">Component </div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								 <div class="input-group col-xs-12" style='margin-top:0px !important;'>
									<input type="hidden" id="wxgzdxid_public" class="form-control">
									<input type="text" id="wxgzdx_public" class="form-control readonly-style" readonly="readonly"  ondblclick="openMaterialWinAdd()" >
									<span  id="wxgzdxbt_public" class="input-group-addon btn btn-default"  onclick="openMaterialWinAdd()">
										<i class="icon-search"></i>
									</span>
							    </div>  
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">名称</div>
								<div class="font-size-9">Name</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="wxgzdxName_public" type="text"  class="form-control" disabled='disabled'>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">ATA章节号</div>
								<div class="font-size-9">ATA</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input id="zjh_public"  type="hidden" readonly="readonly" class="form-control">
									<input id="zjhName_public" type="text" readonly="readonly" class="form-control">
							</div>
						</div>
						
						<!-- <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">区域</div>
								<div class="font-size-9">Zone</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" id='qy_publicDiv'>
								<select multiple='multiple' id='qy_public' style="width: 100%">
									<option value=''></option>
								</select>
							</div>
						</div> -->
						
						<div class='clearfix'></div>
						
						<div id="eqydiv_edit" class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">区域</div>
								<div class="font-size-9">Zone</div>
							</label>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								<div class="input-group col-xs-12">
									<input id="eqy_public" type="text" class="form-control" maxlength="4000" />
			                    	<span id="eqy_public_btn" class="input-group-addon btn btn-default" onclick="openEOZoneWin()"><i class='icon-search'></i></span>
			                	</div>
							</div>
						</div>
						
						<!-- <div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">飞机站位</div>
								<div class="font-size-9">Aircraft Stations</div>
							</label>
							<div id="fjzw_div_edit" class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group " style="min-width:17.3%;">
									<div id="efjzw_public" ondblclick="openEOStationWin();" class='form-control base-color' style='border-right:0px;border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;border-right:1px solid #d5d5d5;'>
									</div> 
				                    <div onclick="openEOStationWin();" id="efjzw_ico_public" class="input-group-addon btn btn-default" style='padding-left:0px;padding-right:0px;width:38px;'><i class='icon-search'></i></div>
			                	</div>
							</div>
						</div> -->
						
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">飞机站位</div>
								<div class="font-size-9">Aircraft Stations</div>
							</label>
							<div id="fjzw_div_edit" class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								<div class="input-group col-xs-12">
									<input id="efjzw_public" type="text" class="form-control"  maxlength="4000" />
			                    	<span id="efjzw_ico_public" class="input-group-addon btn btn-default" onclick="openEOStationWin()"><i class='icon-search'></i></span>
			                	</div>
							</div>
						</div>

						
						<div class='clearfix'></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">标识</div>
								<div class="font-size-9 ">Tags</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<label >
								 	<input class='pull-left margin-right-5' name="wxgzbs_public" value="A" type="checkbox" maxlength="8" >&nbsp;
									<div class='pull-left'>
									 <div class="font-size-12 ">故障/功能失效</div>
										<div class="font-size-9 ">Fault /Function Fail </div>
									</div>
									<div class='clearfix'></div>
								</label>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<label >
								 	<input class='pull-left margin-right-5'  name="wxgzbs_public" value="B" type="checkbox" maxlength="8"   >&nbsp;
									<div class='pull-left'>
									 <div class="font-size-12 ">机体完整 </div>
										<div class="font-size-9 ">Integration</div>
									</div>
									<div class='clearfix'></div>
								</label>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<label >
								 	<input class='pull-left margin-right-5' name="wxgzbs_public" value="C" type="checkbox" maxlength="8"   >&nbsp;
									<div class='pull-left'>
									 <div class="font-size-12 ">性能改进 </div>
										<div class="font-size-9 ">Improvement </div>
									</div>
									<div class='clearfix'></div>
								</label>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<label >
								 	<input class='pull-left margin-right-5' name="wxgzbs_public" value="D"  type="checkbox" maxlength="8"  >&nbsp;
									<div class='pull-left'>
									 <div class="font-size-12 ">腐蚀 </div>
										<div class="font-size-9 ">Corrosion</div>
									</div>
									<div class='clearfix'></div>
								</label>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<label >
								 	<input class='pull-left margin-right-5'  name="wxgzbs_public" value="E"  type="checkbox" maxlength="8"  >&nbsp;
									<div class='pull-left'>
									 <div class="font-size-12 ">疲劳裂纹 </div>
										<div class="font-size-9 ">Crack</div>
									</div>
									<div class='clearfix'></div>
								</label>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<label >
								 	<input class='pull-left margin-right-5' name="wxgzbs_public" value="F" type="checkbox" maxlength="8"   >&nbsp;
									<div class='pull-left'>
									 <div class="font-size-12 ">脱胶/分层</div>
										<div class="font-size-9 ">Disbond/Delamination</div>
									</div>
									<div class='clearfix'></div>
								</label>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<label >
									<input class='pull-left margin-right-5' name="wxgzbs_public" value="G"  type="checkbox" maxlength="8"   >&nbsp;
									<div class='pull-left'>
									 <div class="font-size-12 ">管道/线路老化 </div>
										<div class="font-size-9 ">Aging</div>
									</div>
									<div class='clearfix'></div>
								</label>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							</span>
							<div class='col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8'>
								<div class="input-group input-group-new">
								<div class="input-group-addon" style='text-align:left;padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;'>
									<input class='pull-left margin-right-5'  name="wxgzbs_public" id="showthers" value="H" type="checkbox" maxlength="8" onchange="showHideOther()" >&nbsp;
								</div>
			                    <div class="input-group-addon" style='text-align:left;padding-left:0px;padding-right:5px;padding-top:0px;padding-bottom:0px;color:#333;'>
				                    <div class="font-size-12 ">其他</div>
									<div class="font-size-9 " style='margin-top:4px;'>Others</div>
                                </div>
			                    	<input class="form-control" id="wxgzbs_qt_public" type="text" maxlength="100" style="visibility: hidden">
			                	</div>
							</div>
						</div>
						
						<div class="clearfix"></div>			  
			<div class='clearfix'></div>
		</div>
	</div>
	</div>
<script type="text/javascript">
	$('#qy_public').multiselect({
		buttonClass : 'btn btn-default',
		buttonWidth : 'auto',
		numberDisplayed : 3,
		includeSelectAllOption : true,
	});

	var fjzwList = []; //飞机站位数据集合
	var positionPublicList = [];
	
	//打开飞机站位弹窗
	function openEOStationWin() {
		var this_ = this;
		PositionModal.show({
			/* selected:positionPublicList,//原值，在弹窗中默认勾选 */
			selected:[],
			parentid : 'EOAddModal',
			dprtcode:eo_add_alert_Modal.param.dprtcode,//默认登录人当前机构代码
			jx :$("#jx_add").val(),
			lx:3,
			callback : function(data) {//回调函数
				positionPublicList = [];
				fjzwList = data; 
				var str = '';
				if (data != null && data.length > 0) {
					$.each(data, function(index, row) {
						positionPublicList.push(row);
						str += formatUndefine(row.sz) + ",";
					});
				}
				if (str != '') {
					str = str.substring(0, str.length - 1);
				}
				$("#efjzw_public").val(str);
			}
		});
	}
	
	/**
	 * 打开区域弹窗
	 */
	function openEOZoneWin(){
		var this_ = this;
		PositionModal.show({
			selected:[],
			dprtcode:eo_add_alert_Modal.param.dprtcode,//默认登录人当前机构代码
			modalHeadCN : '区域列表',
			modalHeadENG : 'Zone List',
			jx:$("#jx_add").val(),
			lx:1,
			callback: function(data){//回调函数
				var str = '';
				if(data != null && data.length > 0){
					$.each(data,function(index,row){
						str += formatUndefine(row.sz) + ",";
					});
				}
				if(str != ''){
					str = str.substring(0,str.length - 1);
				}
				$("#eqy_public").val(str);
			}
		});
	}
	
	/**
	 * 打开部件对话框
	 * @returns
	 */
	function openMaterialWinAdd(){
		open_win_material_basic.show({
			multi : false,
			dprtcode:eo_add_alert_Modal.param.dprtcode,
			selected:$("#wxgzdxid_public").val(),
			callback: function(data){//回调函数
				if(data != null){
					$("#wxgzdxid_public").val(data.id);
					$("#wxgzdx_public").val(data.bjh);
					if(null != data.fixChapter){
						$("#zjh_public").val(data.fixChapter.zjh);
						$("#zjhName_public").val(data.fixChapter.displayName);
					}else{
						$("#zjh_public").val("");
						$("#zjhName_public").val("");
					}
					
					$("#wxgzdxName_public").val(data.ywms+" "+data.zwms);
				}else{
					$("#wxgzdxid_public").val("");
					$("#wxgzdx_public").val("");
					$("#zjh_public").val("");
					$("#zjhName_public").val("");
					$("#wxgzdxName_public").val("");
				}
			}
		},true);
	}
	
	function showHideOther(){
		if($('#showthers').is(':checked')){
			$("#wxgzbs_qt_public").css("visibility", "visible");
		}else{
			$("#wxgzbs_qt_public").css("visibility", "hidden");
		}
	}
</script>


 
						
						
					 
 
