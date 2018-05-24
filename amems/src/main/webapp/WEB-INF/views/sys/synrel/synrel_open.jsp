<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal modal-new" id="synrel_form_Modal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:35%'>
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
						
		                    <div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>关系类型</div>
									<div class="font-size-9">Relation type</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<select class='form-control ' id="gxlx" >
											
									</select>
								</div>
								<input type="hidden" id="id" name="id" />
							</div>
							<div class='clearfix'></div>
							
							<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>关联组织机构</div>
									<div class="font-size-9">Related organization</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<select class='form-control ' name="gljgdm" id="gljgdm">
										<option value="" >请选择</option>
									</select>
								</div>
							</div>
							<div class='clearfix'></div>
							
							
							 <div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>对象</div>
									<div class="font-size-9">Object</div>
								</label>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<div class="input-group">
										<input id="dxText"  name="dxText"   class="form-control readonly-style" readonly="readonly" type="text" ondblclick="synrel_form_Modal.selectObjct();">
										<input id="dxid" name="dxid" class="form-control" type="hidden">
							            <span class="input-group-btn">
											<button type="button" class="btn btn-default form-control" style="height:34px;" data-toggle="modal" onclick="synrel_form_Modal.selectObjct();">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</span>
							       </div>
								</div>
								
							</div>
							<div class='clearfix'></div>
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
		                      	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="synrel_form_Modal.saveUpdate();" id="saveButton">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
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
	<%@ include file="/WEB-INF/views/open_win/customer_search.jsp"%><!-- 选择客戶-->
	<%@ include file="/WEB-INF/views/open_win/firm_search.jsp"%><!-- 选择厂商-->
	
<!--  弹出框结束-->
<script>

//供应商组件
var supplier_open = {
		show: function(){
			
		}
};

var synrel_form_Modal = {
		 
		id:'synrel_form_Modal',
		/** */
		selectObjct: function(){
			var _this =  this;
			// 根据关系来下弹出不同控件列表，获取ID和机构代码，写入对象ID，关联机构 
			//1外委供应商、2客户
			var gxlx = $('#'+this.id +' #gxlx').val();
			var dxid = $('#'+this.id +' #dxid').val();
			var gljgdm  = $('#'+this.id +' #gljgdm').val();
			if(!gljgdm){
				AlertUtil.showErrorMessage('请先指定关联机构');
				return false;
			}
			if( gxlx =='1'){
				//关系类型：选中外委供应商-弹出供应商控件
				  ManufacturerModal.show({
					selected : dxid,// 对象ID
					parentid : "synrel_form_Modal",
					callback : function(data) {// 回调函数
						if(data){
							$('#'+_this.id +' #dxid').val(data.id);
							$('#'+_this.id +' #dxText').val(data.gysbm+" "+data.gysmc);
							$('#'+_this.id+' #form').data('bootstrapValidator').updateStatus('dxText',
									'NOT_VALIDATED', null).validateField('dxText');
						}
					}
				}); 
				
			}
			else if(gxlx =='2'){
				//关系类型：客户-弹出供应商控件
				CustomerModal.show({
					selected : dxid,// 对象ID
					parentid : "synrel_form_Modal",
					callback : function(data) {// 回调函数
						if(data){
							$('#'+_this.id +' #dxid').val(data.id);
							$('#'+_this.id +' #dxText').val(data.khbm+" "+data.khmc);
							$('#'+_this.id+' #form').data('bootstrapValidator').updateStatus('dxText',
									'NOT_VALIDATED', null).validateField('dxText');
						}
					}
				});
				
			}
		},
		/**保存同步关系*/
		saveUpdate: function(){
			//验证
			var $form = $('#'+this.id+' #form');
			$form.data('bootstrapValidator').validate();
		    if(!$form.data('bootstrapValidator').isValid()){
		    	AlertUtil.showModalFooterMessage('完善必填数据');
				return false;
		    }	
			//取值提交
			var formid = synrel_form_Modal.id;
			var $modal = $('#'+formid);
			var gljgdm = $modal.find('#gljgdm').val();
			var obj  = {
					id: $modal.find('#id').val(),
					gxlx: $modal.find('#gxlx').val(),
					gljgdm: gljgdm ,
					dxid: $modal.find('#dxid').val()
			};
			 
			//验证，通过提交，否则提示错误。
			AjaxUtil.ajax({
				url: basePath+"/sys/synrel/save",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				async : true,
				data:JSON.stringify(obj),
				success:function(data){
					 
					$('#synrel_form_Modal').modal('hide');
					AlertUtil.showAfterMessage('操作成功');
					synrel_main.search();
			    }
			}); 
		},
		/** 加载关联机构*/
		loadRepOrg: function(){
			var _this = this;
			//关联机构只加载一次
			if(!_this.gljgList){
				//dwj:135用户查145的机构，145的用户查135的机构
				var type = '';
				if(deptType == '135'){
					type = '145';
				}
				else if(deptType == '145'){
					type = '135';
				}
				if(type !='') {
					var obj  = {paramsMap:{deptType: type}}
					var $gljgdm = $('#'+this.id +' #gljgdm');
					AjaxUtil.ajax({
						url: basePath+"/sys/department/queryOrgs",
						type: "post",
						data:JSON.stringify(obj),
						contentType:"application/json;charset=utf-8",
						dataType:"json",
						success:function(data){
							 if(data){
								 _this.gljgList = data;
								 $gljgdm.empty();
								 $.each(data,function(idx,item){
									 $gljgdm.append("<option value='"+item.id+"'>"+item.dprtname+"</option>");
								 });
							 }
							 else {
								 AlertUtil.showErrorMessage('找不到关联机构');
							 }
					    }
					}); 
				}
				
			}
		},
		clearForm: function(){
			$('#synrel_form_Modal #gxlx:first').prop("selected", 'selected');
			$('#synrel_form_Modal #gljgdm:first').prop("selected", 'selected');
			$('#synrel_form_Modal #dxText').val('');
			$('#synrel_form_Modal #dxid').val('');
			$('#synrel_form_Modal #id').val('');
		},
		/**加载一个同步关系到form*/
		loadForm: function() {
			var param = this.param;
			AjaxUtil.ajax({
				url: basePath+"/sys/synrel/load",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data: JSON.stringify({id:param.id}), 
				success:function(data){
					 if(data){
						 $('#synrel_form_Modal #id').val(data.id);
						 $('#synrel_form_Modal #gxlx').val(data.gxlx);
						 $('#synrel_form_Modal #gljgdm').val(data.gljgdm);
						 $('#synrel_form_Modal #dxid').val(data.dxid);
						 $('#synrel_form_Modal #dxText').val( StringUtil.escapeStr(data.objText) );
					 }
					 else {
						 AlertUtil.showErrorMessage('加载同步关系失败');
					 }
			    }
			}); 
		},
		/**实例化验证器*/
		validation: function() { 
			 $('#'+this.id+' #form').bootstrapValidator({
			        message: '数据不合法',
			        feedbackIcons: {
			            //valid: 'glyphicon glyphicon-ok',
			            invalid: 'glyphicon glyphicon-remove',
			            validating: 'glyphicon glyphicon-refresh'
			        },
			        fields: {
			        	gljgdm: {
			                validators: {
			                	notEmpty: {
			                        message: '关联组织机构不能为空'
			                    }
			                }
			            },
				        gxlx: {
			                validators: {
			                	notEmpty: {
			                        message: '关系类型不能为空'
			                    }
			                }
			            } 
				        ,dxText: {
			                validators: {
			                	notEmpty: {
			                        message: '对象不能为空'
			                    }
			                }
			            }
			        }
			    });
		 },
		 /** 加载关系类型*/
		 loadGxlx : function(){
			 var _this = this;
			$('#gxlx').empty();
			if(_this.param.deptType == '135'){
				$('#gxlx').append( "<option value=\"1\" >外委供应商</option>");
			}
			else if(_this.param.deptType == '145'){
				$('#gxlx').append("<option value=\"2\" >客户</option>");
			}
		 },	
		 init : function(){
			 var _this = this;
			 var $form  = $('#'+_this.id+' #form');
			 this.clearForm();
			 this.loadGxlx();
			 this.loadRepOrg();
			 this.validation();
			 
			//隐藏Modal时验证销毁重构
			$form.on("hidden.bs.modal", function() {
				$form.data('bootstrapValidator').destroy();
				$form.data('bootstrapValidator', null);
				_this.validation();
			 });
		 },
		popAdd: function(param){
			var _this = this;
			_this.param = param;
			$('#synrel_form_Modal').modal('show');
			this.init();
		},
		popEdit: function(param){
			$('#synrel_form_Modal').modal('show');
			this.init();
			this.param = param;
			this.loadForm();
		}

	}

</script>
