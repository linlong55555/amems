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
				  /*scBlqx: {
			            validators: {
			            	notEmpty: {message: '保留期限不能为空'}
			           }
				  }, */
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
				  },
				  gzblBjh: {
					  validators: {
						  regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '部件号不能输入中文!' 	
		                  }
					  }
				  },
				  gzblXlh: {
					  validators: {
						  regexp: {
							  regexp: /^[\x00-\xff]*$/,
							  message: '序列号不能输入中文!' 	
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
		$("#"+this.id+" input[type='text']").val(""); 					  //清空所有文本框数据
		$("#"+this.id+" textarea").val("");	  					  //清空所有多行文本框数据
		$("#"+this.id+" input[type='hidden']").val(""); 	
		
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
		$("#bs145").val("");   			        		 	  //145标识
		this.getAttachments(null,true,true);	 				  //初始化附件
		
		this.initDicData(); //初始化,影响服务程度、涉及部门、运行限制、保留原因代码
		
		this.getDefaultFHFC(); //获取飞行时间(2T)、飞行循环默认值(3C)： 组织编码+飞机注册号+位置11+分类(2T、3C)
		
		this.initAutoComplete();
		
	},
	//ATA章节号自动补全
	initAutoComplete : function() {
		 $("#failurekeepForm input[name='zjhms']").typeahead({
				displayText : function(item){
					return StringUtil.escapeStr(item.zjh)+" "+StringUtil.escapeStr(item.ywms);
				},
			    source: function (query, process) {
			    	AjaxUtil.ajax({
						url: basePath+"/project/fixchapter/limitTen",
						type: "post",
						contentType:"application/json;charset=utf-8",
						dataType:"json",
						data:JSON.stringify({
							zjh : query.toUpperCase(),
							dprtcode : $("#dprtcode").val()
						}),
						success:function(data){
							if(data.length>0){
							}else{
								$("#zjh").val("");
							}
							process(data);
					    }
					}); 
			    },

		       updater: function (item) {
		    	  $("#zjh").val(StringUtil.escapeStr(item.zjh));
		    	  return item;
		       }
		   });
	},
	getDefaultFHFC : function(){
		var this_ = this;
		var obj = {};
		obj.dprtcode = $("#dprtcode").val(); 
		obj.fjzch =  $("#"+this_.id+"_fjzch").val();
		AjaxUtil.ajax({
			url:basePath+"/produce/reservation/fault/getDefaultFHFC",
			contentType:"application/json;charset=utf-8",
			type:"post",
			async: false,
			data:JSON.stringify(obj),
			dataType:"json",
			success:function(data){
				$("#fhInput").val("");
				$("#fcInput").val("");
				if(null != data && data.length > 0){
					for ( var index = 0; index < data.length; index++) {
						if(data[index].jkflbh == '2T'){
							$("#fhInput").val(TimeUtil.convertToHour(data[index].csz, TimeUtil.Separator.COLON));
						}else if(data[index].jkflbh == '3C'){
							$("#fcInput").val(data[index].csz);
						}
					}
				}
			}
		});
	},
	initDicData : function(){
		var this_ = this;
		var dprtcode_ = $("#dprtcode").val(); //组织结构
		var lables = "";
		
		//影响服务程度
		var serviceDegrees = DicAndEnumUtil.getDicItems("75",dprtcode_);
		if(serviceDegrees != null && serviceDegrees.length >0){
			$.each(serviceDegrees,function(i,item){
				lables += "<span class='label-input'>" +
						  "		<input name='yxfwdm' type='checkbox' class='noteditable' value='"+item.name+"' id='service"+i+"' />"+
						  "     <label for='service"+i+"' style = 'font-weight: normal;' >"+item.name+"</lable>" +
						  "</span> &nbsp;&nbsp;&nbsp;";
			});
		}
		$("#serviceDegreedic", $("#"+this_.id)).html(lables); 
		
		
		//涉及部门
		lables = "";
		var involvedDepts = DicAndEnumUtil.getDicItems("76",dprtcode_);
		if(involvedDepts != null && involvedDepts.length >0){
			$.each(involvedDepts,function(i,item){
				lables += "<span class='label-input'>" +
						  "		<input name='sjbmdm' type='checkbox' class='noteditable' value='"+item.name+"' id='dept"+i+"' />" +
						  "     <label for='dept"+i+"' style = 'font-weight: normal;' >"+item.name+"</lable>" +
						  "</span> &nbsp;&nbsp;&nbsp;";
			});
		}
		$("#involvedDeptdic", $("#"+this_.id)).html(lables); 
		
		
		//保留原因
		lables = "";
		var blyyReasons = DicAndEnumUtil.getDicItems("78",dprtcode_);
		if(blyyReasons != null && blyyReasons.length >0){
			$.each(blyyReasons,function(i,item){
				lables += "<span class='label-input'>" +
						  "		<input onclick='FailureKeep_Open_Modal.onchangedm(this,1)' name='blyydm' type='checkbox' class='noteditable' value='"+item.name+"' id='blyy"+i+"' />"+
						  "     <label for='blyy"+i+"' style = 'font-weight: normal;'>"+item.name+"</lable>" +
						  "</span> &nbsp;&nbsp;&nbsp;";
			});
		}
		$("#blyyReasondic", $("#"+this_.id)).html(lables); 
		
		//运行限制
		lables = "";
		var fltLimiteds = DicAndEnumUtil.getDicItems("77",dprtcode_);
		if(fltLimiteds != null && fltLimiteds.length >0){
			$.each(fltLimiteds,function(i,item){
				lables += "<span class='label-input'>" +
						  "   <input onclick='FailureKeep_Open_Modal.onchangedm(this,2)' name='yxxzdm' disabled='disabled' type='checkbox' class='noteditable' value='"+item.name+"' id='limit"+i+"' />"+
						  "     <label for='limit"+i+"' style = 'font-weight: normal;'>"+item.name+"</lable>" +
						  "</span> &nbsp;&nbsp;&nbsp;";
			});
		}
		$("#fltLimitedDic", $("#"+this_.id)).html(lables); 
	},
	/**
	 * 运行限制：2
	 * 保留原因：1
	 * 代码勾选时，回显到文本框
	 * @param code 代码
	 */
	onchangedm : function(obj,code){
		var value = ""; // $(obj).val();
		if (code == 1) {
			$("input[name='blyydm']:checked").each(function() {  
				value += ","+$(this).val();  
			});
			if (value.substr(0,1)==',') {
				value = value.substr(1);
			}
			$("#blyy").val(value);
		} else if (code == 2) {
			$("input[name='yxxzdm']:checked").each(function() {  
				value += ","+$(this).val();  
			});  
			if (value.substr(0,1)==',') {
				value = value.substr(1);
			}
			$("#yxxzsm").val(value);
		}
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
		var planeDatas = acAndTypeUtil.getACReg135145List({DPRTCODE : jgdm});
		this_.planeDatas=planeDatas;
		if(planeDatas.length > 0){
			for(var i = 0; i < planeDatas.length; i++){
				$("#"+this_.id+"_fjzch").append("<option value='"+StringUtil.escapeStr(planeDatas[i].FJZCH)+"'>"+StringUtil.escapeStr(planeDatas[i].FJZCH)+ " " + StringUtil.escapeStr(planeDatas[i].XLH)+"</option>");
			}
			$("#jx").val(planeDatas[0].FJJX);
		}else{
			$("#"+this_.id+"_fjzch").html("<option value=''>N/A</option>");
		}
		this_.getDefaultFHFC();
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
		this_.getDefaultFHFC();
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
	
	//回显保留期限
	CalculationNum: function(num){
		var rDate = new Date(num);  
	    var year = rDate.getFullYear();  
	    var month = rDate.getMonth() + 1;  
	    if (month < 10) month = "0" + month;  
	    var date = rDate.getDate();
	    if (date < 10) date = "0" + date;  
		$("#scBlqx").val(year + "-" + month + "-" + date);//保留期限
		/*$("#scBlqx").datepicker("update");*/
		/*$('#failurekeepForm').data('bootstrapValidator')  
	      .updateStatus("scBlqx", 'NOT_VALIDATED',null)  
	      .validateField("scBlqx"); */
	
	},
	//改变保留类别
	onchangebllx : function(typeFlag){
		
		var this_=this;
		var bllx=$("input[name='bllx']:checked").val();
		
		/**清空*/
		if(typeFlag==1){
			$("#scBlqx").val("");//保留期限
			$("#blfhInput").val("");//飞行时间
			$("#blfcInput").val("");//飞行循环
		}
		
		/**文本框显示和可编辑*/
		if (bllx==2 || bllx==3 || bllx==4) {
			$("#scBlqx").val("");//保留期限
			
			$("#scBlqx").attr("disabled",true);//保留期限不可编辑
			$("#blfhDiv").css("visibility","hidden");//飞行时间隐藏
			$("#blfcDiv").css("visibility","hidden");//飞行循环隐藏
		}else{
			$("#scBlqx").attr("disabled",false);//保留期限可编辑
			$("#blfhDiv").css("visibility","visible");//飞行时间显示
			$("#blfcDiv").css("visibility","visible");//飞行循环显示
		}

		/**保留期限赋值*/
		if (bllx==2 || bllx==3 || bllx==4) {
			var addDay = 0;
			if (bllx==2) {
				addDay = 3;
			} else if (bllx==3) {
				addDay = 10;
			} else if (bllx==4) {
				addDay = 120;
			}
			var scSqrq=$("#scSqrq").val(); //申请人日期
			if(scSqrq !=''){
				var num=new Date(this_.dateAdd(scSqrq,"d",addDay));
				if(num!=""){
					this_.CalculationNum(num);
				}
			}
		}
		
	},
	//改变保留类别
	onchangebllxfind : function(){
		var this_=this;
		var bllx=$("input[name='bllx']:checked").val();
		if(bllx==9){ //其他
			$("#scBlqx").attr("disabled",false);//可编辑
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
		}else if(bllx==3){//b 10天 保留期限不可编辑
			var scSqrq=$("#scSqrq").val(); //申请人日期
			if(scSqrq!=''){
				var num=new Date(this_.dateAdd(scSqrq,"d",10));
				if(num!=""){
					this_.CalculationNum(num);
				}
			}
			
			$("#scBlqx").attr("disabled",true);//不可编辑
		}else if(bllx==4){//b 120天 保留期限不可编辑
			var scSqrq=$("#scSqrq").val(); //申请人日期
			if(scSqrq!=''){
				var num=new Date(this_.dateAdd(scSqrq,"d",120));
				if(num!=""){
					this_.CalculationNum(num);
				}
			}
			
			$("#scBlqx").attr("disabled",true);//不可编辑
		}else{
			$("#scBlqx").attr("disabled",false);//可编辑
			$("#scBlqx").val("");//保留期限
		}
	},
	//改变来源类型
	onchangeblly : function(){
		var blly=$("input[name='blly']:checked").val();
		if(blly==9){ //来源编号
			$("#lybhdiv").css("visibility","hidden");//隐藏
		}else if(blly==1){
			$("#lybhdiv").css("visibility","visible");//显示
			$("#wxrybtn1").show();//显示
			$("#wxrybtn2").hide();//显示
		}else{
			$("#wxrybtn1").show();//显示
			$("#wxrybtn2").show();//显示
			$("#lybhdiv").css("visibility","visible");//显示
		}
		$("#bllyid").val('');  //清空来源类型id
		$("#blly").val('');    //清空来源编号
	},
	//初始化来源编号
	initSource : function(obj){
		var blly=$("input[name='blly']:checked").val();
		if(blly==1){//飞行记录表
			this.initSourceData("flb",obj);
		}else if(blly==2){//nrc
			this.initSourceData("nrc",obj);
		}
	},
	//初始化来源编号数据
	initSourceData: function(type,obj){
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
//			var obj="";
//			var code135="produce:reservation:fault:main:062";
//			var code145="produce:reservation:fault:main:063";
//			if($.inArray(code135.toLowerCase(), buttonPermissions) == -1){
//			}else{
//				obj="135";
//			}
//			if($.inArray(code145.toLowerCase(), buttonPermissions) == -1){
//			}else{
//				obj="145";
//			}
			var bs=''; //145标识
			if(addupdatebs==1){//新增
				bs=obj;
			}else{
				var bs145 = $("#bs145").val(); 
				if(bs145 == ''){
					bs=obj;
				}else{
					if((bs145==1 && obj==135) ){
						AlertUtil.showModalFooterMessage("您没有145工单选择权限!");
						return false;
					}
					if( (bs145==0 && obj==145)){
						AlertUtil.showModalFooterMessage("您没有135工单选择权限!");
						return false;
					}
					bs=obj;
				}
			}
			SelectSourceorderModal.show({
				selected:bllyid,//原值，在弹窗中默认勾选
				dprtcode:dprtcode,
				fjzch:fjzch,
				zt:"2,7,10",
				type:bs, //145标识
				parentWinId : "SelectSourceorderModal",
				callback: function(data){//回调函数
					if(data != null){
						$("#bllyid").val(data.id);
						$("#blly").val(data.gdbh);
						if(data.bs==145){
							$("#bs145").val(1); 
						}else{
							$("#bs145").val(0); 
						}
					}else{
						$("#bllyid").val('');
						$("#blly").val('');
						$("#bs145").val('');
					}
				}
			});
		}
		
	},
	//执行M程序标识/执行O程序标识/运行限制标识
	onchangembs : function(obj){
		var bs= $("input[type='checkbox'][name='"+obj+"bs']:checked").val();
		 if(bs=='on'){
			 $("#"+obj+"sm").attr("disabled",false);//可编辑
		 }else{
			 $("#"+obj+"sm").attr("disabled",true);//不可编辑
			 $("#"+obj+"sm").val("");//不可编辑
		 }
		 
		 //如果是运行限制
		 if(obj=="yxxz"){
			 if (bs=='on') {
				 $("input[type='checkbox'][name='yxxzdm']").attr("disabled",false);//可编辑
			 } else {
				 $("input[type='checkbox'][name='yxxzdm']").attr("disabled",true);//不可编辑
				 $("input[type='checkbox'][name='yxxzdm']").attr("checked",false);//取消勾选
			 }
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
/*$('#scBlqx').datepicker({
	 autoclose: true,
	 clearBtn:true
}).on('hide', function(e) {
	  $('#failurekeepForm').data('bootstrapValidator')  
.updateStatus('scBlqx', 'NOT_VALIDATED',null)  
.validateField('scBlqx');  
});*/
