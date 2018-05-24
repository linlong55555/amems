
var xh = 1;
var flag = true;
var oldStorageIdArr = [];// 已拥有的库位id数组
$(function(){

	
});

var storefoModal = {
		id : "storefoModal",
		planeRegOption : '',
		attachmentSingle : {},
		isLoad:false,//是否加载
		param: {
			id:'',
			modalHeadChina : '飞机基础信息',
			modalHeadEnglish : 'A/C Type Message',
			type:1, 
			blOption:true,
			callback:function(){},
			dprtcode:userJgdm//默认登录人当前机构代码
		},
		show : function(param){
			if(param){
				$.extend(this.param, param);
			}
			//初始化信息
			this.initInfo();
			$("#" + this.id).modal("show");
		},
		//初始化信息
		initInfo : function(){
			var this_ = this;
			//初始化表单验证
			if(!this_.isLoad){
				this_.initFormValidator();
				this_.isLoad = true;
			}
			this_.initModelHead();
			this_.modalEmpty();
			if(this_.param.type == 2){
				this_.returnViewData(this_.param.viewObj);
			}else{
				xh = 1;
				this_.loodingBase("");
			}
		},
		//标题
		initModelHead : function(){
			//隐藏出现异常的感叹号
			AlertUtil.hideModalFooterMessage();
			$("#modalHeadChina", $("#"+this.id)).html(this.param.modalHeadChina);
			$("#modalHeadEnglish", $("#"+this.id)).html(this.param.modalHeadEnglish);
		},
		initFormValidator : function(){//初始化验证
			var this_ = this;
			validation();
			//隐藏Modal时验证销毁重构
			$("#"+this_.id).on("hidden.bs.modal", function() {
				 $("#form").data('bootstrapValidator').destroy();
			     $('#form').data('bootstrapValidator', null);
			     validation();
			});
		},
		//清空
		modalEmpty : function(){
			validation();
			$("#ckh").attr('disabled',false);	
			$("#ckmc").attr('disabled',false);	
			$("#xlh").attr('disabled',false);	
			$("#cklb").attr('disabled',false);	
			$("#kgyn").attr('disabled',false);	
			$("#kgyid").attr('disabled',false);	
			$("#ckdz").attr('disabled',false);	
			$("#jd").attr('disabled',false);	
			$("#bz").attr('disabled',false);		
			$("#ckh").val("");	
			$("#ckmc").val("");	
			$("#xlh").val("");		
			if($("#isStoreData").val()=='true'){
				$("#cklb").val(14);	
			}else if($("#isStoreData").val()=='false'){
				$("#cklb").val(6);	
			}	
			$("#kgyn").val("");	
			$("#kgyid").val("");	
			$("#ckdz").val("");	
			$("#jd").val("");	
			$("#bz").val("");	
			xh = 1;
			oldStorageIdArr = [];
			this.ckmodalEmpty();
		},
		//清空仓库库位
		ckmodalEmpty : function(){
			$("#rotatable").empty();	
			$("#rotatable").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
		},
		//获取库位参数
		getStorageParam:function(){
			
			flag = true;
			var storageParam = [];
			var message = '';
			
			var len = $("#rotatable").find("tr").length;
			if (len == 1) {
				if($("#rotatable").find("td").length ==1){
					return storageParam;
				}
			}
			
			if (len > 0){
				var kwhArr = [];
				$("#rotatable").find("tr").each(function(){
					var infos ={};
					var index=$(this).index(); //当前行
					var hiddenid =$.trim($("input[name='hiddenid']").eq(index).val());
					var kwh =$.trim($("input[name='kwh']").eq(index).val());
					var bz =$.trim($("input[name='bz']").eq(index).val());
					if(null == kwh || "" == kwh){
						flag = false;
						message = '请输入库位号!';
					}
					var reg = /^[^\u4e00-\u9fa5]{0,50}$/;
					if(flag && !reg.test(kwh)){    
						flag = false;
						message = '对不起，库位号不能输入中文且长度最多不超过50个字符!';
			        }
					if(flag && kwhArr.indexOf(kwh) != -1){
						flag = false;
						message = '对不起，库位号不能重复!';
					}
					if(!flag){
						AlertUtil.showMessageCallBack({
							message : message,
							callback : function(){
								$("input[name='kwh']").eq(index).focus();
							}
						});
						return false;
					}
					kwhArr.push(kwh);
					infos.id = hiddenid;
					infos.kwh = kwh;
					infos.bz = bz;
					storageParam.push(infos);
				});
			}
			
			return storageParam;
		},
		loodingBase:function(jd){
			var dprtcode = userJgdm;
			AjaxUtil.ajax({
				   url:basePath+"/sys/department/queryDepartmentByJd", 
				   type: "post",
				   dataType:"json",
				   data:{
					   'id':dprtcode
				   },
				   success:function(data){
					   if(data != null && data.length > 0){
						   var htmlappend = '<option value="">请选择</option>';
						   $.each(data,function(index,row){
							   if(row.id == jd){
								   htmlappend += "<option selected value='"+row.id+"'>"+row.dprtname+"</option>";
							   }else{
								   htmlappend += "<option value='"+row.id+"'>"+row.dprtname+"</option>";
							   }
						   });
						   
						   $("#jd").html(htmlappend);
					   }
				   }
			 });
		},
		/**
		 * 保存数据
		 */
		setData: function(){
			var this_ = this;
			//表单验证
			$('#form').data('bootstrapValidator').validate();
			if(!$('#form').data('bootstrapValidator').isValid()){
				AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
				return false;
			}
			var ckh = $.trim($("#ckh").val());
			var ckmc = $.trim($("#ckmc").val());
			var cklb = $.trim($("#cklb").val());
			var kgyid = $.trim($("#kgyid").val());
			var ckdz = $.trim($("#ckdz").val());
			var jd = $.trim($("#jd").val());
			var bz = $.trim($("#bz").val());
			var store = {
					ckh : ckh,
					ckmc : ckmc,
					cklb : cklb,
					kgyid : kgyid,
					ckdz : ckdz,
					jd : jd,
					bz : bz,
					dprtcode : userJgdm
				};
			if(this_.param.type == 2){
				store.storageIdList = oldStorageIdArr;
				store.id = $.trim($("#storeId").val());
			}
			store.storageList = this_.getStorageParam();
			
			if(!flag){
				return false;
			}
			//回调
			this_.param.callback(store);
		},
		returnViewData:function(data){
			var obj = data.row;
			var this_ = this;
			this_.loodingBase(obj.jd);
			$("#ckh").val(obj.ckh);	
			$("#ckmc").val(obj.ckmc);	
			$("#xlh").val(obj.xlh);	
			$("#cklb").val(obj.cklb);	
			$("#kgyn").val(obj.displayName);	
			$("#kgyid").val(obj.kgyid);	
			$("#ckdz").val(obj.ckdz);	
			$("#jd").val(obj.jd);	
			$("#bz").val(obj.bz);
			$("#storeId").val(obj.id)
			if(data.rows && data.rows.length>0){
				this_.loingCkkw(data.rows);
			}
		},
		//加载仓库库位
		loingCkkw: function(data){
			// 先移除暂无数据一行
			$("#rotatable").empty();
			if (JSON.stringify(data) == '[]') {
				$("#rotatable").append("<tr><td  colspan='4' class='text-center'>暂无数据 No data.</td></tr>");
				return false;
			}
			$.each(data, function(i, obj) {
				obj.xh = xh++;
				oldStorageIdArr.push(obj.id);
				addRow(obj);
			});
		}
}

//打开调整列表对话框
function openUserList() {
	userModal.show({
		selected:$("#kgyid").val(),//原值，在弹窗中默认勾选
		callback: function(data){//回调函数
			if(data != null){
				var str = StringUtil.escapeStr(data.username) + " " + StringUtil.escapeStr(data.realname);
				$("#kgyid").val(formatUndefine(data.id));
				$("#kgyn").val(str);
			}
		}
	})
}


//向table新增一行
function addRotatableCol(){
	
	//先移除暂无数据一行
	var len = $("#rotatable").length;
	if (len == 1) {
		if($("#rotatable").find("td").length == 1){
			$("#rotatable").empty();
		}
	}
	
	var obj = {};
	obj.id = "";
	obj.xh = xh++;
	obj.kwh = "";
	obj.bz = "";
	
	addRow(obj);
}

//向table新增一行
function addRow(obj){
	$("#rotatable").append(
	
		"<tr>"	
			
		+"<td style='text-align:center;vertical-align:middle;'>" 
		+	"<button class='line6 line6-btn' onclick='removeCol(this)' type='button'>"
		+	"<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>"
		+	"</button>"
		+"</td>" 
			
		+"<td style='text-align:center;vertical-align:middle;'>"
		+	"<span name='xh'>"+obj.xh+"</span>"
		+	"<input type='hidden' class='form-control' name='hiddenid' value='"+obj.id+"'/>" 
		+"</td>"
		
		+"<td>"
		+	"<input name='kwh' type='text' title='不能输入中文且长度最多不超过50个字符' value='"+StringUtil.escapeStr(obj.kwh)+"' class='form-control' maxlength='50'/>" 
		+"</td>"
		
		+"<td>"
		+	"<input name='bz' type='text' title='长度最多不超过300个字符' value='"+StringUtil.escapeStr(obj.bz)+"' class='form-control' maxlength='300' />" 
		+"</td>"
		
		+"</tr>"
	);
}

//移除一行
function removeCol(e){
	$(e).parent().parent().remove();
	resXh();
	xh--;
	var len = $("#rotatable").find("tr").length;
	if(len < 1){
		$("#rotatable").append("<tr><td  colspan='4'  class='text-center'>暂无数据 No data.</td></tr>");
	}
}

function resXh(){
	
	var len = $("#rotatable").find("tr").length;
	if (len == 1) {
		if($("#rotatable").find("td").length ==1){
			return false;
		}
	}
	var xh = 1;
	if (len > 0){
		$("#rotatable").find("tr").each(function(){
			var index=$(this).index(); //当前行
			$("span[name='xh']").eq(index).html(xh++);
		});
	}
}