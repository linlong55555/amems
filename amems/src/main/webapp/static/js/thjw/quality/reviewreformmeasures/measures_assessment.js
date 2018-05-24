
	var measures_assessment = {
		
		// 页面初始化
		init : function(){
			
			var this_ = this;
			//初始化radio
			this_.initRadio();
			// 加载数据
			this_.loadData();
		},
		
		param : {
			data:{},
			option:true,
			modal:null,
			callback:function(){}//回调函数
		},
		
		// 显示弹窗
		show : function(param, isReload){
			if(param){
				$.extend(this.param, param);
			}
			this.init();
		},
		initRadio : function(){
			if(this.param.option){
				$("#"+this.param.modal+" input[type='radio']").attr("disabled",false);
				$("#"+this.param.modal+" #pgrdiv").hide();
				$("#"+this.param.modal+" #pgsjdiv").hide();
			}else{
				$("#"+this.param.modal+" input[type='radio']").attr("disabled",true);
				$("#"+this.param.modal+" #pgrdiv").show();
				$("#"+this.param.modal+" #pgsjdiv").show();
			}
			this.chooseRadio("isyx",1);
			this.chooseRadio("iscz",1);
			this.chooseRadio("iskx",1);
		},
		chooseRadio : function(obj,value){
			$("#"+this.param.modal+" input[name='"+obj+"'][value='"+value+"']").attr('checked',true);
		},
		// 加载数据
		loadData : function(){
			var this_ = this;
			var data = this_.param.data;			
			var pgjg = data.pgjg;
			if(pgjg){
				this_.chooseRadio("isyx",pgjg.substring(0,1));
				this_.chooseRadio("iscz",pgjg.substring(1,2));
				this_.chooseRadio("iskx",pgjg.substring(2,3));
				$("#"+this.param.modal+" #pgr").val(data.pgr?(data.pgr.username+" "+data.pgr.realname):"");
				$("#"+this.param.modal+" #pgsj").val(data.pgsj);
			}		
		},		
		// 获取数据
		getData : function(){
			var str = '';
			str += $.trim($("#"+this.param.modal+ " [name=isyx]:radio:checked").val());
			str += $.trim($("#"+this.param.modal+ " [name=iscz]:radio:checked").val());
			str += $.trim($("#"+this.param.modal+ " [name=iskx]:radio:checked").val());
			return str;
		},
	};
	
	