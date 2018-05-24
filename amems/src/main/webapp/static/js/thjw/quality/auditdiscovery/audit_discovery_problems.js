
	var auditDiscoveryAddProblems = {
		wtdj:"",
		wtfl:"",
		idList:[],
		// 页面初始化
		init : function(param){
			if(param){
				$.extend(this.param, param);
			}
			$("#finding_list").empty();
			if(this.param.ope_type==3){
				$("#basic_education_th").hide();
			}else{
				$("#basic_education_th").show();
			}
			var this_ = this;
			// 加载数据
			this_.loadData();
		},
		isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
		codeValidator : {
			reg : new RegExp("^[a-zA-Z 0-9-_\x21-\x7e]{1,50}$"),
			message: "只能输入长度不超过50个字符的英文、数字、英文特殊字符!"
		},
		param : {
			data:[],
			ope_type:1,//操作类型
			dprtcode:userJgdm,//默认登录人当前机构代码
			callback:function(){}//回调函数
		},
		
		// 初始化数据字典和枚举
		initDicAndEnum : function(enumType,value){
			
			var enumSelect = DicAndEnumUtil.getDict(enumType,this.param.dprtcode);//问题等级		
			if(enumSelect){
				if("72" == enumType){
					this.wtdj = this.converDic(enumSelect,'wtdj',value);
				}else{
					this.wtfl = this.converDic(enumSelect,'wtfl',value);
				}			
			}
		},
		converDic : function(obj,str,val){
			var html ="";
			if(this.param.ope_type==3){
				html = "<select name='"+str+"' disabled='disabled' class='form-control'>";
			}else{
				var html = "<select name='"+str+"' class='form-control'>";
			}	
			$.each(obj,function(i,data){
				if(val == data.id){
					html +="<option selected='selected' value='"+data.id+"' >"+data.name+"</option>";
				}else{
					html +="<option value='"+data.id+"' >"+data.name+"</option>";
				}			
			})
			return html+"</select>";
		},
	    addFinding : function(){
	    	var findingInfo="";
	    	    findingInfo +="<tr ><td style='text-align: center;vertical-align:middle;'>"
	    	    findingInfo +="<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick=auditDiscoveryAddProblems.removeFinding(this)>"
	    	    findingInfo +="<i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i>"
	    	    findingInfo +="</button>"
	    	    findingInfo +="<input type='hidden'  name='id'  class='form-control'/></td>"
	    	    findingInfo +="<td><input type='text' class='form-control' maxlength='1300' name='wtms' onkeyup='auditDiscoveryAddProblems.changeWtms(this)' /></td>"
		  		findingInfo +="<td>"+this.wtdj+"</td>"
				findingInfo +="<td>"+this.wtfl+"</td>"
				findingInfo +="<td><input type='text' name='shwtbh' placeholder='不填写则自动生成'  class='form-control' onkeyup='auditDiscoveryAddProblems.changeQcdh(this)'   /></td>"
				findingInfo +="</tr>"
	    	$("#finding_list").append(findingInfo);
	    },
	    editFinding : function(data){
	    	var findingInfo="";
	    	    findingInfo +="<tr ><td style='text-align: center;vertical-align:middle;'>"
	    	    findingInfo +="<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick=auditDiscoveryAddProblems.removeFinding(this)>"
	    	    findingInfo +="<i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i>"
	    	    findingInfo +="</button>"
	    	    findingInfo +="<input type='hidden' value='"+StringUtil.escapeStr(data.id)+"' name='id'  class='form-control'/></td>"
	    	    findingInfo +="<td><input type='text' value='"+StringUtil.escapeStr(data.wtms)+"' class='form-control' maxlength='1300' name='wtms' onkeyup='auditDiscoveryAddProblems.changeWtms(this)' /></td>"
		  		findingInfo +="<td>"+this.wtdj+"</td>"
				findingInfo +="<td>"+this.wtfl+"</td>"
				findingInfo +="<td><input type='text' name='shwtbh' disabled='disabled' value='"+StringUtil.escapeStr(data.shwtbh)+"' class='form-control' onkeyup='auditDiscoveryAddProblems.changeQcdh(this)'   /></td>"
				findingInfo +="</tr>"
	    	$("#finding_list").append(findingInfo);
	    },     
	    removeFinding :function(obj){
	    	$(obj).parent("td").parent("tr").remove();
	    	this.idList.push($(obj).next().val());
	    },

		// 加载数据
		loadData : function(){
			var this_ = this;
			if(this_.param.ope_type==1){//新增
				// 初始化数据字典和枚举
				this_.initDicAndEnum('72');
				this_.initDicAndEnum('73');
				this_.addFinding();
			}else if(this_.param.ope_type==2){//修改
				if(null != this.param.data && this.param.data.length>0){
					var list = this.param.data;
					$.each(list,function(i,data){
						// 初始化数据字典和枚举
						this_.initDicAndEnum('72',data.wtdj);
						this_.initDicAndEnum('73',data.wtfl);
						this_.editFinding(data);
					})				
					this_.initDicAndEnum('72');
					this_.initDicAndEnum('73');
				}			
			}else if(this_.param.ope_type==3){//查看
				if(null != this.param.data && this.param.data.length>0){
					var list = this.param.data;
					$.each(list,function(i,data){
						// 初始化数据字典和枚举
						this_.initDicAndEnum('72',data.wtdj);
						this_.initDicAndEnum('73',data.wtfl);
						this_.viewFinding(data);
					})				
				}
			}
		},
		
		// 获取数据
		getData : function(){
			var this_ = this;
			this_.isValid = true;
			var obj = [];
			var table = document.getElementById("basic_education_table");
			var row = table.rows.length;
			if (row > 1){
				var wtbhArr = [];
				$("#finding_list").find("tr").each(function(){
					var tr_this = this;
					var infos ={};
					var index=$(this).index(); //当前行
					var wtms = $.trim($(this).find("input[name='wtms']").val());
					var wtdj =$(tr_this).find("select[name='wtdj']").val();
					var wtfl =$(tr_this).find("select[name='wtfl']").val();
					var shwtbh = $.trim($(this).find("input[name='shwtbh']").val());
					var id = $(this).find("input[name='id']").val();
					
					if("" == wtms || null == wtms){					
						AlertUtil.showModalFooterMessage("问题描述不能为空!");
						$(tr_this).find("input[name='wtms']").focus();
						$(tr_this).find("input[name='wtms']").addClass("border-color-red");
						this_.isValid = false; 
						return false;
					}
					if(shwtbh){
						if($.inArray(shwtbh, wtbhArr) != -1 ){
							AlertUtil.showModalFooterMessage("问题编号重复");
							$(tr_this).find("input[name='shwtbh']").focus();
							$(tr_this).find("input[name='shwtbh']").addClass("border-color-red");
							this_.isValid = false; 
							return false;
						}
						wtbhArr.push(shwtbh);
						if(!this_.codeValidator.reg.test(shwtbh)){  
							AlertUtil.showModalFooterMessage("问题编号"+this_.codeValidator.message);
							$(tr_this).find("input[name='shwtbh']").focus();
							$(tr_this).find("input[name='shwtbh']").addClass("border-color-red");
							this_.isValid = false; 
							return false;
						}
					}
					infos.wtms = wtms;
					infos.wtdj = wtdj;
					infos.wtfl = wtfl;
					infos.shwtbh = shwtbh;
					infos.id = id;
					obj.push(infos);
				});
			}else{
				AlertUtil.showModalFooterMessage("审核发现问题必须有一条!");
				this_.isValid = false; 
				return false;
			}
			if(this_.idList.length>0){
				var infos ={};
				var paramsMap ={};
				paramsMap.idList = this_.idList;
				infos.paramsMap = paramsMap;
				obj.push(infos);
			}
			return obj;
		},
		changeQcdh : function(obj){
			this.onkeyup4Code(obj);
			$(obj).removeClass("border-color-red");
		},
		changeWtms : function(obj){
			$(obj).removeClass("border-color-red");
		},
		onkeyup4Code : function(obj){
			obj.value = obj.value.replaceAll("−","-");
			var reg = new RegExp("^[a-zA-Z 0-9-_\x21-\x7e]{1,50}$");
			
		     obj.value = validate(obj.value);
		     function validate(_value){
		    	 if(_value.length == 0){
		    		 return "";
		    	 }
		    	 if(!reg.test(_value)){
		    		return validate(_value.substr(0, _value.length-1));
		    	 }
		    	 return _value;
		    }
		},
		viewFinding : function(data){
	    	var findingInfo="";
	    	    findingInfo +="<tr >"
	    	    findingInfo +="<td><input type='text' value='"+StringUtil.escapeStr(data.wtms)+"' disabled='disabled' class='form-control' maxlength='1300' name='wtms' onkeyup='auditDiscoveryAddProblems.changeWtms(this)' /></td>"
		  		findingInfo +="<td>"+this.wtdj+"</td>"
				findingInfo +="<td>"+this.wtfl+"</td>"
				findingInfo +="<td><input type='text' name='shwtbh' disabled='disabled' value='"+StringUtil.escapeStr(data.shwtbh)+"' class='form-control' onkeyup='auditDiscoveryAddProblems.changeQcdh(this)'   /></td>"
				findingInfo +="</tr>"
	    	$("#finding_list").append(findingInfo);
	    },  
	};
	
	