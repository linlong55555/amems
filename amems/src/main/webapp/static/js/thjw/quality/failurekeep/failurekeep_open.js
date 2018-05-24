FailureKeep_Open_Modal = {
	id : "FailureKeep_Open_Modal",
	deleteUploadId : "",
	uploadObjSingle :[],
	uploadObjjl : {},
	planeDatas:[],
	/**
	 * 字段验证
	 */
	validation : function(){
		validatorForm = $('#failurekeepForm').bootstrapValidator({
		       message: '数据不合法',
		       feedbackIcons: {
		          invalid:    'glyphicon glyphicon-remove',
		          validating: 'glyphicon glyphicon-refresh'
		       },
		       fields: {
		    	  fjzch: {
			            validators: {
			            	notEmpty: {message: '飞机注册号不能为空'}
			            }
			      }, 
			      hz: {
			            validators: {
			            	notEmpty: {message: '航站不能为空'}
			           }
				  }, 
				  scSqr: {
			            validators: {
			            	notEmpty: {message: '申请人不能为空'}
			           }
				  }, 
				  scSqrq: {
			            validators: {
			            	notEmpty: {message: '申请日期不能为空'}
			           }
				  }, 
				  scBlqx: {
			            validators: {
			            	notEmpty: {message: '保留期限不能为空'}
			           }
				  }, 
				  blyj: {
			            validators: {
			            	notEmpty: {message: '保留依据不能为空'}
			           }
				  }, 
				  bldh: {
					  validators: {
						  regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '保留单编号不能输入中文!' 	
		                  }
					  }
				  }
		       }
		   });
	},
	/**
	 * 清空数据
	 */
	EmptiedData : function(){
		
		$(".noteditable").attr("disabled",false);				  //标签不可编辑
		$(".required").show();   			    			      //隐藏必填标识*
		$(".colse").addClass("readonly-style");  				  //去掉文本框为白的的样式
		
		$(".bldh").attr("disabled",false);						  //保留单号不可编辑
		
		$("#bzgs").html(0); 									  //总数
		$("#djzt").val(""); 									  //清空old状态
		$("#id").val(""); 									  	  //清空id
		$("#scSqrid").val(""); 									  //清空申请人id
		$("#"+this.id+" input:text").val(""); 					  //清空所有文本框数据
		$("#"+this.id+" textarea").val("");	  					  //清空所有多行文本框数据
		
		$("#bllxsm").css("visibility","hidden");				  //其它说明隐藏
		
		$("#mbs").attr("checked",false); 						  //执行(M)程序默认不勾选
		this.onchangembs("m");
		$("#obs").attr("checked",false);						  //执行(O)程序
		this.onchangembs("o");
		$("#yxxzbs").attr("checked",false);						  //运行限制
		this.onchangembs("yxxz");
		
		$("#"+this.id+" input[name='blly']").get(2).checked=true; //来源类型默认选择n/a
		$("#"+this.id+" input[name='bllx']").get(0).checked=true; //保留类型默认选择第一个
		$("#scBlqx").attr("disabled",false);					  //可编辑
		$("#addupdatebs").val("1");   			        		  //默认新增标识为1为新增
		$("#bs145").val("0");   			        		 	  //145标识
		this.getAttachments(null,true,true);	 				  //初始化附件
		
	},
	//按钮权限
	button: function(param){
		if(param==1){//新增
			 $("#sptg").hide();
			 $("#spbh").hide();
			 $("#baocuns").show();
			 $("#tijiao").show();
		}else if(param==4){//批准
			 $("#sptg").show();
			 $("#spbh").show();
			 $("#baocuns").hide();
			 $("#tijiao").hide();
		}
	},
	//初始化器材清单
	Equipment_list_edit: function(id,colOptionhead){
		var this_=this;
		Equipment_list_edit.init({
			djid:id,//关联单据id
			parentWinId : this_.id,//父窗口id
			ywlx:31,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单、21工单、31故障保留、32项目保留、33缺陷保留
			colOptionhead : colOptionhead,//true:编辑,false:查看
			dprtcode:$("#dprtcode").val()//默认登录人当前机构代码
		});
	},
	//初始化工具设备
	Tools_list_edit: function(id,colOptionhead){
		var this_=this;
		Tools_list_edit.init({
			djid:id,//关联单据id
			parentWinId : this_.id,//父窗口id
			ywlx:31,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单、21工单、31故障保留、32项目保留、33缺陷保留
			colOptionhead : colOptionhead,//true:编辑,false:查看
			dprtcode:$("#dprtcode").val()//默认登录人当前机构代码
		});
	},
	//初始化附件
	getAttachments: function(id,fileHead,colOptionhead){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:id,
			fileHead : fileHead,
			colOptionhead : colOptionhead,
			fileType:"reservation"
		});//显示附件
	},
	//初始化附件3
	getAttachments3: function(id,fileHead,colOptionhead){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit3');
		attachmentsObj.show({
			djid:id,
			fileHead : fileHead,
			colOptionhead : colOptionhead,
			fileType:"reservation"
		});//显示附件
	},
	//初始化飞机注册号
	initFjzchOption: function(jgdm){
		var this_ = this;
		$("#"+this_.id+"_fjzch").html("");
		var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE : jgdm});
		this_.planeDatas=planeDatas;
		if(planeDatas.length > 0){
			for(var i = 0; i < planeDatas.length; i++){
				$("#"+this_.id+"_fjzch").append("<option value='"+StringUtil.escapeStr(planeDatas[i].FJZCH)+"'>"+StringUtil.escapeStr(planeDatas[i].FJZCH)+ " " + StringUtil.escapeStr(planeDatas[i].XLH)+"</option>");
			}
			$("#jx").val(planeDatas[0].FJJX);
		}else{
			$("#"+this_.id+"_fjzch").html("<option value=''>N/A</option>");
		}
	},
	//初始化机型
	onchangeFjzch: function(e){
		var this_ = this;
		var fjzch=$(e).val();
		var planeDatas=this_.planeDatas;
		for(var i = 0; i < planeDatas.length; i++){
			if(fjzch==planeDatas[i].FJZCH){
				$("#jx").val(planeDatas[i].FJJX);
			}
		}
		$("#bllyid").val('');  //清空来源类型id
		$("#blly").val('');    //清空来源编号
	},
	//初始化章节号
	initFixChapter: function(){
		var zjh = $.trim($("#zjh").val());
		var dprtcode = $("#dprtcode").val();
		FixChapterModal.show({
			selected:zjh,//原值，在弹窗中默认勾选
			dprtcode:dprtcode,
			parentWinId : "FailureKeep_Open_Modal",
			callback: function(data){//回调函数
				if(data != null){
					var wczjhName = formatUndefine(data.zjh) + " " +formatUndefine(data.ywms);
					$("#zjh").val(data.zjh);
					$("#zjhms").val(wczjhName);
				}else{
					$("#zjh").val('');
					$("#zjhms").val('');
				}
			}
		});
	},
	//选择人员
	openUser: function(obj){
		var userList = [];
		var a = $("#"+obj+"id").val();
		var b = $("#"+obj).val();
		for (var i = 0; i < a.split(",").length; i++) {
			var p = {
				id : a.split(",")[i],
				displayName : b.split(",")[i]
			};
			userList.push(p);
		}
		if (a == "") {
			userList = null;
		}
		Personel_Tree_Multi_Modal.show({
			checkUserList:userList,//原值，在弹窗中回显
			dprtcode:$("#dprtcode").val(),//
			multi:false,
			callback: function(data){//回调函数
				var bmdm = '';
				var displayName = '';
				var mjsrid = '';
				if(data != null && data != ""){
					$.each(data, function(i, row){
						displayName += formatUndefine(row.displayName) + ",";
						mjsrid += formatUndefine(row.id) + ",";
						bmdm += formatUndefine(row.bmdm) + ",";
					});
				}
				if(bmdm != ''){
					bmdm = bmdm.substring(0,bmdm.length - 1);
				}
				if(displayName != ''){
					displayName = displayName.substring(0,displayName.length - 1);
				}
				if(mjsrid != ''){
					mjsrid = mjsrid.substring(0,mjsrid.length - 1);
				}
				$("#"+obj).val(displayName);
				$("#"+obj+"id").val(mjsrid);
				$("#"+obj+"bmdm").val(bmdm);
				
				$('#failurekeepForm').data('bootstrapValidator')  
	 		      .updateStatus(""+obj+"", 'NOT_VALIDATED',null)  
	 		      .validateField(""+obj+""); 
			}
		});
	},
	/**
	 * fh输入框验证
	 */
	validateFH : function(obj){
		var this_ = this;
		var reg = /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/;
		var value = $(obj).val();
		while(!(reg.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		$(obj).val(value);
		this_.clearRedColor();
	},
	/**
	 * fh输入框验证
	 */
	validateFC : function(obj){
		var this_ = this;
		var reg = /^(0|[1-9]\d*)$/;
		var value = $(obj).val();
		while(!(reg.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		$(obj).val(value);
		this_.clearRedColor();
	},
	clearRedColor : function(){
		$("#scBlqx").removeClass("border-color-red");
		$("#blfhInput").removeClass("border-color-red");
		$("#blfcInput").removeClass("border-color-red");
		
		$("#zcBlqx").removeClass("border-color-red");
		$("#zcFhInput").removeClass("border-color-red");
		$("#zcFcInput").removeClass("border-color-red");
	},
	//回显保留期限
	CalculationNum: function(num){
		var rDate = new Date(num);  
	    var year = rDate.getFullYear();  
	    var month = rDate.getMonth() + 1;  
	    if (month < 10) month = "0" + month;  
	    var date = rDate.getDate();
	    if (date < 10) date = "0" + date;  
		$("#scBlqx").val(year + "-" + month + "-" + date);//保留期限
		$("#scBlqx").datepicker("update");
		$('#failurekeepForm').data('bootstrapValidator')  
	      .updateStatus("scBlqx", 'NOT_VALIDATED',null)  
	      .validateField("scBlqx"); 
	
	},
	//改变保留类别
	onchangebllx : function(){
		var this_=this;
		var bllx=$("input:radio[name='bllx']:checked").val();
		if(bllx==9){ //其他
			$("#scBlqx").attr("disabled",false);//可编辑
			$("#bllxsm").css("visibility","visible");//显示
			$("#scBlqx").val("");//保留期限
			$('#failurekeepForm').data('bootstrapValidator')  
		      .updateStatus("scBlqx", 'NOT_VALIDATED',null)  
		      .validateField("scBlqx"); 
		}else if(bllx==2){//b 3天 保留期限不可编辑
			var scSqrq=$("#scSqrq").val(); //申请人日期
			if(scSqrq!=''){
				var num=new Date(this_.dateAdd(scSqrq,"d",3));
				if(num!=""){
					this_.CalculationNum(num);
				}
			}
			
			$("#scBlqx").attr("disabled",true);//不可编辑
			$("#bllxsm").css("visibility","hidden");//隐藏
		}else if(bllx==3){//b 10天 保留期限不可编辑
			var scSqrq=$("#scSqrq").val(); //申请人日期
			if(scSqrq!=''){
				var num=new Date(this_.dateAdd(scSqrq,"d",10));
				if(num!=""){
					this_.CalculationNum(num);
				}
			}
			
			$("#scBlqx").attr("disabled",true);//不可编辑
			$("#bllxsm").css("visibility","hidden");//隐藏
		}else if(bllx==4){//b 120天 保留期限不可编辑
			var scSqrq=$("#scSqrq").val(); //申请人日期
			if(scSqrq!=''){
				var num=new Date(this_.dateAdd(scSqrq,"d",120));
				if(num!=""){
					this_.CalculationNum(num);
				}
			}
			
			$("#scBlqx").attr("disabled",true);//不可编辑
			$("#bllxsm").css("visibility","hidden");//隐藏
		}else{
			$("#scBlqx").attr("disabled",false);//可编辑
			$("#bllxsm").css("visibility","hidden");//隐藏
			$("#scBlqx").val("");//保留期限
			
			$('#failurekeepForm').data('bootstrapValidator')  
		      .updateStatus("scBlqx", 'NOT_VALIDATED',null)  
		      .validateField("scBlqx"); 
		}
	},
	//改变保留类别
	onchangebllxfind : function(){
		var this_=this;
		var bllx=$("input:radio[name='bllx']:checked").val();
		if(bllx==9){ //其他
			$("#scBlqx").attr("disabled",false);//可编辑
			$("#bllxsm").css("visibility","visible");//显示
			$("#scBlqx").val("");//保留期限
		}else if(bllx==2){//b 3天 保留期限不可编辑
			var scSqrq=$("#scSqrq").val(); //申请人日期
			if(scSqrq!=''){
				var num=new Date(this_.dateAdd(scSqrq,"d",3));
				if(num!=""){
					this_.CalculationNum(num);
				}
			}
			
			$("#scBlqx").attr("disabled",true);//不可编辑
			$("#bllxsm").css("visibility","hidden");//隐藏
		}else if(bllx==3){//b 10天 保留期限不可编辑
			var scSqrq=$("#scSqrq").val(); //申请人日期
			if(scSqrq!=''){
				var num=new Date(this_.dateAdd(scSqrq,"d",10));
				if(num!=""){
					this_.CalculationNum(num);
				}
			}
			
			$("#scBlqx").attr("disabled",true);//不可编辑
			$("#bllxsm").css("visibility","hidden");//隐藏
		}else if(bllx==4){//b 120天 保留期限不可编辑
			var scSqrq=$("#scSqrq").val(); //申请人日期
			if(scSqrq!=''){
				var num=new Date(this_.dateAdd(scSqrq,"d",120));
				if(num!=""){
					this_.CalculationNum(num);
				}
			}
			
			$("#scBlqx").attr("disabled",true);//不可编辑
			$("#bllxsm").css("visibility","hidden");//隐藏
		}else{
			$("#scBlqx").attr("disabled",false);//可编辑
			$("#bllxsm").css("visibility","hidden");//隐藏
			$("#scBlqx").val("");//保留期限
		}
	},
	//改变来源类型
	onchangeblly : function(){
		var blly=$("input:radio[name='blly']:checked").val();
		if(blly==9){ //来源编号
			$("#lybhdiv").css("visibility","hidden");//隐藏
		}else {
			$("#lybhdiv").css("visibility","visible");//显示
		}
		$("#bllyid").val('');  //清空来源类型id
		$("#blly").val('');    //清空来源编号
	},
	//初始化来源编号
	initSource : function(obj){
		var blly=$("input:radio[name='blly']:checked").val();
		if(blly==1){//飞行记录表
			this.initSourceData("flb");
		}else if(blly==2){//nrc
			this.initSourceData("nrc");
		}
	},
	//初始化来源编号数据
	initSourceData: function(type){
		var bllyid = $.trim($("#bllyid").val());
		var addupdatebs = $.trim($("#addupdatebs").val());//新增修改标识
		var dprtcode = $("#dprtcode").val();
		var fjzch = $("#"+this.id+"_fjzch").val(); //飞机注册号
		if(type=='flb'){  //飞行记录本
			SelectFlbModal.show({
				selected:bllyid,//原值，在弹窗中默认勾选
				dprtcode:dprtcode,
				fjzch:fjzch,
				parentWinId : "SelectFlbModal",
				callback: function(data){//回调函数
					if(data != null){
						$("#bllyid").val(data.id);
						$("#blly").val(data.fxjlbh);
					}else{
						$("#bllyid").val('');
						$("#blly").val('');
					}
				}
			});
			
		}else{ //工单
			var bs=''; //145标识
			if(addupdatebs==1){//新增
				bs=deptInfo.deptType;//取session里的deptType
			}else{
				var bs145 = $("#bs145").val(); 
				if(bs145==1){
					bs=145;//取数据库里的bs145
				}
			}
			SelectSourceorderModal.show({
				selected:bllyid,//原值，在弹窗中默认勾选
				dprtcode:dprtcode,
				fjzch:fjzch,
				zt:"7,10",
				type:bs, //145标识
				parentWinId : "SelectSourceorderModal",
				callback: function(data){//回调函数
					if(data != null){
						$("#bllyid").val(data.id);
						$("#blly").val(data.gdbh);
					}else{
						$("#bllyid").val('');
						$("#blly").val('');
					}
				}
			});
		}
		
	},
	//执行M程序标识/执行O程序标识/运行限制标识
	onchangembs : function(obj){
		 var bs=$("input:checkbox[name='"+obj+"bs']:checked").val();
		 if(bs=='on'){
			 $("#"+obj+"sm").attr("disabled",false);//可编辑
		 }else{
			 $("#"+obj+"sm").attr("disabled",true);//不可编辑
			 $("#"+obj+"sm").val("");//不可编辑
		 }
	},
	//改变人数时触发
	changeRs : function(obj){
		this.clearNoNumber(obj);
		this.sumTotal();
	},
	//改变小时数触发
	changeXss : function(obj){
		this.clearNoNumTwo(obj);
		this.sumTotal();
	},
	//计算总数
	sumTotal : function(){
		var total = 0;
		var sxgsRs = $("#sxgsRs").val();
		var sxgsXs = $("#sxgsXs").val();
		total = sxgsRs*sxgsXs;
		if(total == ''){
			total = 0;
		}
		if(String(total).indexOf(".") >= 0){
			total = total.toFixed(2);
		}
		$("#bzgs").html(total);
	},
	//只能输入数字
	clearNoNumber : function(obj){
		//先把非数字的都替换掉，除了数字
	     obj.value = obj.value.replace(/[^\d]/g,"");
	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	  		obj.value = 0;
	  	 }
	     obj.value = validateMax(obj.value);
	     function validateMax(_value){
	    	 if(Number(_value) > 9999999999){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    };
	},
	//只能输入数字和小数,保留两位
	clearNoNumTwo : function(obj){
		//先把非数字的都替换掉，除了数字和.
	     obj.value = obj.value.replace(/[^\d.]/g,"");
	     //必须保证第一个为数字而不是.
	     obj.value = obj.value.replace(/^\./g,"");
	     //保证只有出现一个.而没有多个.
	     obj.value = obj.value.replace(/\.{2,}/g,".");
	     //保证.只出现一次，而不能出现两次以上
	     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	     
	     var str = obj.value.split(".");
	     if(str.length > 1){
	    	 if(str[1].length > 2){
	    		 obj.value = str[0] +"." + str[1].substring(0,2);
	    	 }
	     }
	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	  		 if(obj.value.substring(1,2) != "."){
	  			obj.value = 0;
	  		 }
	  	 }
	     obj.value = validateMax(obj.value);
	     function validateMax(_value){
	    	 if(Number(_value) > 9999999999.99){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	},
	//参数分别为日期对象，增加的类型，增加的数量 
	dateAdd: function(date,strInterval, Number){
        var dtTmp = date;  
        switch (strInterval) {   
		    case 'second':
            case 's' :
			    return new Date(Date.parse(dtTmp) + (1000 * Number));  
			case 'minute':
            case 'n' :
			    return new Date(Date.parse(dtTmp) + (60000 * Number));  
			case 'hour':
            case 'h' :
			    return new Date(Date.parse(dtTmp) + (3600000 * Number)); 
            case 'day':							
            case 'd' :
			    return new Date(Date.parse(dtTmp) + (86400000 * Number)); 
            case 'week':							
            case 'w' :
			    return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
			case 'month':
            case 'm' :
			    return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
            case 'year':
			case 'y' :
			    return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
        }  
	},
	//跳转到评估单查询界面
	findAssessment: function(){
		var id =$("#oldbbid").val();
		window.open(basePath+"/project2/assessment/view?id="+id);
	},
	//附件下载
	downfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	}
};
$('.date-picker').datepicker({
	format:'yyyy-mm-dd',
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});
$('#scSqrq').datepicker({
	 autoclose: true,
	 clearBtn:true
}).on('hide', function(e) {
	  $('#failurekeepForm').data('bootstrapValidator')  
.updateStatus('scSqrq', 'NOT_VALIDATED',null)  
.validateField('scSqrq');  
});
$('#scBlqx').datepicker({
	 autoclose: true,
	 clearBtn:true
}).on('hide', function(e) {
	  $('#failurekeepForm').data('bootstrapValidator')  
.updateStatus('scBlqx', 'NOT_VALIDATED',null)  
.validateField('scBlqx');  
});
