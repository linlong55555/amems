
WorkContentModal = {
	isLoad:false,	
	planeRegOption : '',
	param: {
		fjjx:'',
		viewObj:null,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		//初始化下拉框数据
		if(!this.isLoad){
			this.initDic();
			this.isLoad = true;
		}
		//初始化飞机注册号数据
		if(this.planeRegOption == ''){
			this.initPlaneData();
		}
		//初始化多选下拉框数据
		this.multiselect();
		this.clearData();//清空数据
		//回显数据
		if(this.param.viewObj){
			this.returnViewData(this.param.viewObj);
		}else{
			$('#wcfjzch').multiselect('select', '00000');
		}
		$("#WorkContentModal").modal("show");
	},
	initPlaneData : function(){
		var this_ = this;
		var data= acAndTypeUtil.getACRegList({DPRTCODE:this.param.dprtcode,FJJX:this.param.fjjx});
		var planeRegOption = '';
			planeRegOption += "<option value='00000' >通用Currency</option>";
			if(data.length >0){
				$.each(data,function(i,planeData){
					var tempOption = "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
					planeRegOption += tempOption;
				});
		  	 }
		this_.planeRegOption = planeRegOption;
	},
	initDic : function(){
		DicAndEnumUtil.registerDic('18','wcjclx',this.param.dprtcode);
		DicAndEnumUtil.registerDic('19','wcgzzw',this.param.dprtcode);
	},
	multiselect : function(){
		//生成多选下拉框(飞机注册号)
		$('#wcfjzchdiv').empty();
		$('#wcfjzchdiv').html("<select multiple='multiple' id='wcfjzch' ></select>");
		$("#wcfjzch").append(this.planeRegOption);
		$('#wcfjzch').multiselect({
		    buttonClass: 'btn btn-default',
		    buttonWidth: 'auto',
		    numberDisplayed:8,
		    includeSelectAllOption: true,
		    onChange:function(element,checked){
	  	    }
	    });
		
		//生成多选下拉框工作类型
		$('#wcgzlxdiv').empty();
		$('#wcgzlxdiv').html("<select multiple='multiple' id='wcgzlx' ></select>");
		DicAndEnumUtil.registerDic('17','wcgzlx',this.param.dprtcode);
	   $('#wcgzlx').multiselect({
		    buttonClass: 'btn btn-default',
		    buttonWidth: 'auto',
		    numberDisplayed:15,
		    includeSelectAllOption: true,
		    onChange:function(element,checked){
	  	    }
	   });
	},
	clearData : function(){
		$("#wcxmly").val('');
		$("#wczjh").val('');
		$("#wczjhName").val('');
		$("#wcscmsZw").val('');
		$("#wcscmsYw").val('');
		$("#wccksc").val('');
		$("#wcckgk").val('');
		$("#wwz").val('');
		$("#wcjclx").val('');
		$("#wcgzzw").val('-');
		$("#wczt").val(1);
		$("input:radio[name='wcisBj'][value=0]").attr("checked",true); 
		$("#wcisMi").removeAttr("checked");
		$("#wcbz").val('');
	},
	returnViewData : function(obj){
		$("#wcxmly").val(obj.xmly);
		$("#wczjh").val(obj.zjh);
		$("#wczjhName").val(obj.zjhStr);
		$("#wcscmsZw").val(obj.scmsZw);
		$("#wcscmsYw").val(obj.scmsYw);
		$("#wccksc").val(obj.cksc);
		$("#wcckgk").val(obj.ckgk);
		$("#wwz").val(obj.wz);
		$("#wcjclx").val(obj.jclx);
		$("#wcgzzw").val(obj.gzzw);
		$("#wczt").val(obj.zt);
		$("input:radio[name='wcisBj'][value='"+obj.isBj+"']").attr("checked",true); 
		if(obj.isMi == 1){
			$("#wcisMi").attr("checked",'true');//选中
		}
		if(obj.gzlx != null){
			$('#wcgzlx').multiselect('select', obj.gzlx.split(","));
		}
		if(obj.fjzch != null){
			$('#wcfjzch').multiselect('select', obj.fjzch.split(","));
		}
		$("#wcbz").val(obj.bz);
	},
	openChapterWin : function(){
		var zjh = $.trim($("#wczjh").val());
		var dprtcode = this.param.dprtcode;
		FixChapterModal.show({
			selected:zjh,//原值，在弹窗中默认勾选
			dprtcode:dprtcode, //机构代码
			callback: function(data){//回调函数
				if(data != null){
					var wczjhName = formatUndefine(data.zjh) + " " +formatUndefine(data.zwms);
					$("#wczjh").val(data.zjh);
					$("#wczjhName").val(wczjhName);
				}else{
					$("#wczjh").val('');
					$("#wczjhName").val('');
				}
			}
		});
	},
	vilidateData : function(){
		var zjh = $.trim($("#wczjh").val());
		var fjzch = $.trim($("#wcfjzch").val());
		var wcscmsYw = $.trim($("#wcscmsYw").val());
		if(null != wcscmsYw && "" != wcscmsYw){
			var reg = /^[^\u4e00-\u9fa5]+$/;
			if(!reg.test(wcscmsYw)){
				AlertUtil.showMessageCallBack({
					message : '英文描述不能输入中文!',
					option : 'wcscmsYw',
					callback : function(option){
						$("#"+option).focus();
					}
				});
				return false;
			}
		}
		if(null == zjh || "" == zjh){
			AlertUtil.showErrorMessage("请选择ATA章节号!");
			return false;
		}
		if(null == fjzch || "" == fjzch){
			AlertUtil.showErrorMessage("请选择适用性!");
			return false;
		}else{
			if('multiselect-all' == fjzch){
				AlertUtil.showErrorMessage("请选择适用性!");
				return false;
			}
		}
		return true;
	},
	setData: function(){
		
		if(!this.vilidateData()){
			return false;
		}
		
		var data = {};
		data.xmly = $.trim($("#wcxmly").val());
		data.zjh = $.trim($("#wczjh").val());
		data.zjhStr = $.trim($("#wczjhName").val());
		data.scmsZw = $.trim($("#wcscmsZw").val());
		data.scmsYw = $.trim($("#wcscmsYw").val());
		data.cksc = $.trim($("#wccksc").val());
		data.ckgk = $.trim($("#wcckgk").val());
		data.wz = $.trim($("#wwz").val());
		data.jclx = $.trim($("#wcjclx").val());
		data.gzzw = $.trim($("#wcgzzw").val());
		data.isBj = $("input:radio[name='wcisBj']:checked").val(); 
		data.isMi = $("#wcisMi").is(":checked")?1:0;
		data.bz = $.trim($("#wcbz").val());
		data.zt = $.trim($("#wczt").val());
		var gzlx = $.trim($("#wcgzlx").val());
		var fjzch = $.trim($("#wcfjzch").val());
		var fjzchStr = '';
		
		var gzlxStr = '';
		if(gzlx != null && gzlx.length > 0){
			var gzlxArr = gzlx.split(",");
			for(var i = 0 ; i < gzlxArr.length ; i++){
				if('multiselect-all' != gzlxArr[i]){
					gzlxStr += gzlxArr[i]+",";
				}
			}
			if('multiselect-all' == gzlxArr[0]){
				gzlx = gzlx.replace('multiselect-all',"").substring(1,gzlx.length); 
			}
		}
		if(gzlxStr != ''){
			gzlxStr = gzlxStr.substring(0,gzlxStr.length-1);
		}
		data.gzlx = gzlx;
		data.gzlxStr = gzlxStr;
		
		var fjzchValue = '';
		if(fjzch != null && fjzch.length > 0){
			var fjzchArr = fjzch.split(",");
			for(var i = 0 ; i < fjzchArr.length ; i++){
				if("00000" == fjzchArr[i]){
					fjzchValue = '00000';
					fjzchStr = '通用Currency';
					break;
				}
				if('multiselect-all' != fjzchArr[i]){
					fjzchValue += fjzchArr[i]+",";
				}
			}
		}
		if(fjzchValue != '' && fjzchValue != '00000'){
			fjzchValue = fjzchValue.substring(0,fjzchValue.length-1);
			fjzchStr = fjzchValue;
		}
		data.fjzch = fjzchValue;
		data.fjzchStr = fjzchStr;
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
		$("#WorkContentModal").modal("hide");
	}
	
}