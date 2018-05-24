/**
 * 拆下件弹窗
 */
materialAndOutfield = {
	id:'open_win_materialAndOutfield',
	loaded: false,//是否已加载 
	data:[],
	param: {
		multi:false, //是否多选 默认单选
		clearProject: false,
		selected:null, //已经选择的
		fjzch:'', //飞机注册号
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){}//回调函数
	}, 
	show : function(param, isReload){
		if(param){
			$.extend(this.param, param);
		}
		$("#"+this.id).modal("show");
		this.initParam();
		this.load();
		if(param.clearProject == false){
			$("#project_btn_clear").hide();
		}
		if(!this.loaded || isReload === true){
		}
		var this_ = this;
		$("#"+this.id).on('shown.bs.modal',function(){
			materialAndOutfield.computeTable();
		});
		$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function () {
			materialAndOutfield.computeTable();
		})
	},
	//搜索按钮
	search :function(){
		var fl = $("#materialAndOutfield_keyword_search").attr("fl");
		if(fl == "true"){
			outfield_main.load();
		}else{
			material_main.load();
		}
	},
	//页签改变事件
	yqChange : function(fl){
		var this_ = this;
		if(fl){
			$("#materialAndOutfield_keyword_search").attr("placeholder","件号/名称/序列号/型号/厂家件号");
		}else{
			$("#materialAndOutfield_keyword_search").attr("placeholder","件号/厂家件号/中英文/ATA章节号/备注/维护人");
		}
		$("#materialAndOutfield_keyword_search").attr("fl",fl);
		
		
	},
	//计算表格高度
	computeTable:function(){
		var modalMaxHeight = parseInt($("#open_win_materialAndOutfield .modal-body").css("max-height"));
		var modalUl = $("#open_win_materialAndOutfield .modal-body ul#myTab").outerHeight()||0;
		var modalSearch=$("#open_win_materialAndOutfield .modal-body .modalSearch").outerHeight()||0;
		var modalPageHeight=$("#open_win_materialAndOutfield .modal-body .tab-pane:visible .page-height").outerHeight()||0;
		var modalTable=modalMaxHeight-modalUl-modalSearch-modalPageHeight-40;
		$("#outfield_main_top_div").css({"max-height":modalTable+"px"});
		$("#material_main_top_div").css({"max-height":modalTable+"px"});
	},
	//加载数据
	load : function(){
		var this_ = this;
		outfield_main.show({
			selected:this_.param.selected,//原值，在弹窗中默认勾选
			dprtcode:this_.param.dprtcode, //机构代码
			fjzch:this_.param.fjzch,//飞机注册号
		});
		material_main.show({
			selected:this_.param.selected,//原值，在弹窗中默认勾选
			dprtcode:this_.param.dprtcode, //机构代码
			fjzch:this_.param.fjzch,//飞机注册号
		});
	},
	//初始化参数
	initParam: function(){
		var this_ = this;
		$("#materialAndOutfield_qczt_search").empty();
		$("#materialAndOutfield_qczt_search").html("<option value=''>显示全部 All</option>");
		DicAndEnumUtil.registerDic('86',"materialAndOutfield_qczt_search",this_.param.dprtcode);
	},
	save: function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = [];
			var fl;
			var index;
			if($("#materialAndOutfield_keyword_search").attr("fl") == "true"){
				fl = true;
				index = $("#outfield_main_list").find("input:checked").attr("value");
				data =  outfield_main.data;
			}else{
				fl = false;
				index = $("#material_main_list").find("input:checked").attr("value");
				data =  material_main.data;
			}
			if(data.length > 0 && index != undefined){	
				var row = data[index];
				row.fl = fl;
				this.param.callback(row);
			}else{
				
			}
		}
	},
	clearProject:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
	}
}