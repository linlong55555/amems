settingtimeprice_open = {
	id:"settingtimeprice_open_alert",
	data:[],
	wx:{},
	qj:{},
	wxIndex:0,
	qjIndex:0,
	param: {
		obj : {},
		dprtcode:userJgdm,	
		operation:true,
		view:false,
		mark:true,
		modalShow:true,
		callback:function(){}
	},
	init:function(param){
		$("#"+this.id+" input[type='text']").val("");
		$("#"+this.id+" select").empty();
		this.wx = {};
		this.qj = {};
		this.wxIndex = 0;
		this.qjIndex = 0;
		$(".tr_remove").remove();
		$("#settingtimeprice_open_alert_autoQj").html("0工时以后");
		$("#settingtimeprice_open_alert_autoWx").html("0工时以后");
		DicAndEnumUtil.registerDic('3','settingtimeprice_open_alert_biz',this.param.dprtcode);
		if(param){
			$.extend(this.param, param);
		}
		if(this.param.obj != null ){
			this.setData(this.param.obj);
		}else{
		}
		AlertUtil.hideModalFooterMessage();
		$("#"+this.id).modal("show");	
	},

	setData:function(list){
		var this_ = this;
		$.each(list,function(index,data){
			$("#settingtimeprice_open_alert_biz").val(data.biz);
			if(!data.dj && data.dj != 0){
				this_.appendHtml(data.ladderList,data.lx);
			}else{
				if(data.lx == 1){
					$("#settingtimeprice_open_alert_wxdj").val(data.dj);
				}else{
					$("#settingtimeprice_open_alert_qjdj").val(data.dj);
				}
			}
			
		})
	},
	appendHtml:function(list,lx){
		var this_ = this;
		var htmlContent ="";
		$.each(list,function(i,row){
			var sl = 0;
			var qsz = "";
			var click = "";
			var changeClick = "";
			var id = "";
			if(lx == 1){
				this_.wxIndex++;
				qsz=(i+1)+"_qssl";
				click="settingtimeprice_open.delWx(this)";
				changeClick="settingtimeprice_open.changeSl(this)";
				id = (i+1)+"_wx";
			}else{
				this_.qjIndex++;
				qsz=(i+1)+"_qjqssl";
				click="settingtimeprice_open.delQj(this)";
				changeClick="settingtimeprice_open.changeQj(this)";
				id = (i+1)+"_qj";
			}
			if(i<list.length-1){
				var j = i+1;
				var data = row.jzgs;
				if(lx == 1){
					this_.wx[j] = data;
				}else{
					this_.qj[j] = data;
				}
				htmlContent += "<tr class='tr_remove' id='"+(i+1)+"'>";
				htmlContent +="<td style='text-align:center;vertical-align:middle;'><button class='line6 line6-btn' id='"+(i+1)+"' onclick='"+click+"'  type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button></td>";	
				htmlContent += "<td  style='text-align:left;vertical-align:middle;' ><div class='input-group col-xs-12'><span id='"+qsz+"' class='input-group-addon input-addon-style padding-right-8'>";
				htmlContent += formatUndefine(row.qsgs)+"-</span><input value='"+formatUndefine(row.jzgs)+"' type='text' id='"+id+"' qsz ='"+formatUndefine(sl)+"' value='' name='gs' class='form-control' onkeyup='"+changeClick+"' maxlength='10' /><span id='' class='input-group-addon input-addon-style padding-left-8'>";
				htmlContent += "工时</span></div></td>";
				htmlContent += "<td  style='text-align:left;vertical-align:middle;' >";
				htmlContent += "<div class='input-group col-xs-12'><span id='' class='input-group-addon input-addon-style padding-right-8' >单价</span>";
				htmlContent += "<input type='text' name='jg' value='"+formatUndefine(row.gsdj)+"' onkeyup='settingtimeprice_open.changeDj(this)' class='form-control' maxlength='10' /></div></td>";
				htmlContent += "</tr>";
			}else{
				if(lx == 1){
					$("#settingtimeprice_open_alert_autoWx").html(row.qsgs+"工时以后")
					$("#settingtimeprice_open_alert_wxdj").val(row.gsdj);
				}else{
					$("#settingtimeprice_open_alert_autoQj").html(row.qsgs+"工时以后")
					$("#settingtimeprice_open_alert_qjdj").val(row.gsdj);
				}
			}
		})
		if(lx == 1){
			$("#settingtimeprice_open_alert_wxGd").before(htmlContent);
		}else{
			$("#settingtimeprice_open_alert_qjGd").before(htmlContent);
		}
	},
	valid:function(obj,str){
		var flag = true;
		if(null == obj || "" == obj ){
			AlertUtil.showModalFooterMessage("请输入单价!","settingtimeprice_open_alert");
			$("#"+str).focus();
			$("#"+str).addClass("border-color-red");
			flag =  false;
		}else{
			var reg1 = /^[0-9]{1,10}(\.[0-9]{0,2})?$/;
			if(reg1.test(obj)==false){
				AlertUtil.showModalFooterMessage("单价输入不正确!","settingtimeprice_open_alert");
				$("#"+str).focus();
				$("#"+str).addClass("border-color-red");
				flag = false;
			}
		}
		return flag;
	},
	validData:function(data,message,obj,name){
		var flag = true;
		if(null == data || "" == data ){
			AlertUtil.showModalFooterMessage("请输入"+message,"settingtimeprice_open_alert");
			obj.find("input[name='"+name+"']").focus();
			obj.find("input[name='"+name+"']").addClass("border-color-red");
			flag =  false;
		}else{
			var reg1 = /^[0-9]{1,10}(\.[0-9]{0,2})?$/;
			if(reg1.test(data)==false){
				AlertUtil.showModalFooterMessage(message+"不正确!","settingtimeprice_open_alert");
				obj.find("input[name='"+name+"']").focus();
				obj.find("input[name='"+name+"']").addClass("border-color-red");
				flag =  false;
			}
		}
		return flag;
	},
	getWxData:function(){
		var this_=this;
		var biz = $("#settingtimeprice_open_alert_biz").val();
		var temp = 0;
		var tempJg = 0;
		var table = document.getElementById("settingtimeprice_open_alert_wxTable");
		var row = table.rows.length;
		var info = {};
		var flg = true;
		if(row==1){
			var dj = $.trim($("#settingtimeprice_open_alert_wxdj").val());
			if(this_.valid(dj,"settingtimeprice_open_alert_wxdj")){
				info.biz = biz;
				info.dj = dj;
				info.lx = 1;
			}else{
				flg =  false;
			}
		}else{
			info.biz = biz;
			info.lx = 1;
			var ladderList =[];
			$("#settingtimeprice_open_alert_wxTbody",$("#settingtimeprice_open_alert")).find("tr").each(function(){
				var tr_this = this;
				var detail = {};
				var index=$(this).index(); //当前行
				var gs = $(this).find("input[name='gs']").val();
				var jg = $(this).find("input[name='jg']").val(); 
				if(index < row - 1){
					var gsFlag = this_.validData(gs,"工时",$(tr_this),"gs");
					var jgFlag = this_.validData(jg,"价格",$(tr_this),"jg");
					if(gsFlag && jgFlag && parseFloat(gs) > parseFloat(temp) ){
						if(index == 0){
							detail.qsgs = temp;
							detail.jzgs = gs;
							detail.gsdj= jg;
							detail.qdje = 0;
							tempJg = (gs - temp) * jg;
							temp = gs;
						}else{
							detail.qsgs = temp;
							detail.jzgs = gs;
							detail.gsdj= jg;
							detail.qdje = tempJg ;
							tempJg = (tempJg+(gs - temp )* jg);
							temp = gs;
						}
					}else{
						if(!(parseFloat(gs) > parseFloat(temp))){
							$(tr_this).find("input[name='gs']").focus();
							$(tr_this).find("input[name='gs']").addClass("border-color-red");
							AlertUtil.showModalFooterMessage("工时输入不正确!","settingtimeprice_open_alert");
						}
						flg = false;
					}
				}else{
					var dj = $.trim($("#settingtimeprice_open_alert_wxdj").val());
					if(this_.valid(dj,"settingtimeprice_open_alert_wxdj")){
						detail.qsgs = temp;
						detail.gsdj= dj;
						detail.qdje = tempJg ;
					}else{
						flg =  false;
					}
				}
				ladderList.push(detail);
			});
			info.ladderList = ladderList;
		}
		var param = {};
		param.flg =flg;
		param.info = info;
		return param;
	},
	getQjData:function(){
		var this_=this;
		var biz = $("#settingtimeprice_open_alert_biz").val();
		var temp = 0;
		var tempJg = 0;
		var table = document.getElementById("settingtimeprice_open_alert_qjTable");
		var row = table.rows.length;
		var info = {};
		var flg = true;
		if(row==1){
			var dj = $.trim($("#settingtimeprice_open_alert_qjdj").val());
			if(this_.valid(dj,"settingtimeprice_open_alert_qjdj")){
				info.biz = biz;
				info.dj = dj;
				info.lx = 2;
			}else{
				flg =  false;
			}
		}else{
			info.biz = biz;
			info.lx = 2;
			var ladderList =[];
			$("#settingtimeprice_open_alert_qjTable",$("#settingtimeprice_open_alert")).find("tr").each(function(){
				var tr_this = this;
				var detail = {};
				var index=$(this).index(); //当前行
				var gs = $(this).find("input[name='gs']").val();
				var jg = $(this).find("input[name='jg']").val(); 
				if(index < row - 1){
					var gsFlag = this_.validData(gs,"工时",$(tr_this),"gs");
					var jgFlag = this_.validData(jg,"价格",$(tr_this),"jg");
					if(gsFlag && jgFlag && parseFloat(gs) > parseFloat(temp) ){
						if(index == 0){
							detail.qsgs = temp;
							detail.jzgs = gs;
							detail.gsdj= jg;
							detail.qdje = 0;
							tempJg = (gs - temp) * jg;
							temp = gs;
						}else{
							detail.qsgs = temp;
							detail.jzgs = gs;
							detail.gsdj= jg;
							detail.qdje = tempJg ;
							tempJg = (tempJg+(gs - temp )* jg);
							temp = gs;
						}
					}else{
						if(!(parseFloat(gs) > parseFloat(temp))){
							$(tr_this).find("input[name='gs']").focus();
							$(tr_this).find("input[name='gs']").addClass("border-color-red");
							AlertUtil.showModalFooterMessage("工时输入不正确!","settingtimeprice_open_alert");
						}
						flg = false;
					}
				}else{
					var dj = $.trim($("#settingtimeprice_open_alert_qjdj").val());
					if(this_.valid(dj,"settingtimeprice_open_alert_qjdj")){
						detail.qsgs = temp;
						detail.gsdj= dj;
						detail.qdje = tempJg ;
					}else{
						flg =  false;
					}
				}
				ladderList.push(detail);
			});
			info.ladderList = ladderList;
		}
		var param = {};
		param.flg =flg;
		param.info = info;
		return param;
	},
	saveData:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){
				var this_ = this;
				var data = [];
				var wx = this_.getWxData();
				var qj = this_.getQjData();
				if(wx.flg && qj.flg){
					data.push(wx.info);
					data.push(qj.info);
					this_.param.callback(data);	
				}else{
					return false;
				}
				
		}
	},
	addWx:function(){
		var sl = 0;
		for (var key in this.wx) {  
          sl = this.wx[key];  
        }
		this.wxIndex++;
		var htmlContent = "<tr class='tr_remove' id='"+this.wxIndex+"'>";
		htmlContent +="<td style='text-align:center;vertical-align:middle;'><button class='line6 line6-btn' id='"+this.wxIndex+"' onclick='settingtimeprice_open.delWx(this)'  type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button></td>";	
		htmlContent += "<td  style='text-align:left;vertical-align:middle;' ><div class='input-group col-xs-12'><span id='"+this.wxIndex+"_qssl"+"' class='input-group-addon input-addon-style padding-right-8'>";
		htmlContent += formatUndefine(sl)+"-</span><input type='text' id='"+this.wxIndex+"_wx' qsz ='"+formatUndefine(sl)+"' value='' name='gs' class='form-control' onkeyup='settingtimeprice_open.changeSl(this)' maxlength='10' /><span id='' class='input-group-addon input-addon-style padding-left-8'>";
		htmlContent += "工时</span></div></td>";
		htmlContent += "<td  style='text-align:left;vertical-align:middle;' >";
		htmlContent += "<div class='input-group col-xs-12'><span id='' class='input-group-addon input-addon-style padding-right-8' >单价</span>";
		htmlContent += "<input type='text' name='jg' class='form-control' onkeyup='settingtimeprice_open.changeDj(this)' maxlength='10' /></div></td>";
		htmlContent += "</tr>";
		$("#settingtimeprice_open_alert_wxGd").before(htmlContent);
	},
	delWx:function(obj){
		var id = $(obj).attr("id").split("_")[0];
		var prevId = $(obj).parent().parent().prev().attr("id");
		var nextId = $(obj).parent().parent().next().attr("id");
		$(obj).parent().parent().remove();
		var tem = 0;
		if(prevId !=null){
			tem = this.wx[prevId];
		}
		var sl = 0;
		$("#"+nextId+"_qssl").html(tem+"-");
		delete(this.wx[id]);
		for (var key in this.wx) {  
          sl = this.wx[key];  
        }
		$("#settingtimeprice_open_alert_autoWx").html(sl+"工时以后");
	},
	changeDj : function(obj){
		this.onkeyup4Num(obj);
		var this_ = this;
		var val = $(obj).val();
		$(obj).removeClass("border-color-red");
	},
	changeSl : function(obj){
		this.onkeyup4Num(obj);
		var this_ = this;
		var val = $(obj).val();
		var id = $(obj).attr("id").split("_")[0];
		var qsz = $(obj).attr("qsz");
		$(obj).removeClass("border-color-red");
		this.wx[id]=val;	
		var nextId = $(obj).parent().parent().parent().next().attr("id");
		$("#"+nextId+"_qssl").html(val+"-");
		var sl = 0;
		for (var key in this.wx) {  
	          sl = this.wx[key];  
	        }
		$("#settingtimeprice_open_alert_autoWx").html(sl+"工时以后");
	},
	onkeyup4Num : function(obj){//只能输入数字和小数,保留两位
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
	    	 if(Number(_value) > 99999999.99){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	},
	addQj:function(){
		var sl = 0;
		for (var key in this.qj) {  
          sl = this.qj[key];  
        }
		this.qjIndex++;
		var htmlContent = "<tr class='tr_remove' id='"+this.qjIndex+"'>";
		htmlContent +="<td style='text-align:center;vertical-align:middle;'><button class='line6 line6-btn' id='"+this.qjIndex+"' onclick='settingtimeprice_open.delQj(this)'  type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button></td>";	
		htmlContent += "<td  style='text-align:left;vertical-align:middle;' ><div class='input-group col-xs-12'><span id='"+this.qjIndex+"_qjqssl"+"' class='input-group-addon input-addon-style padding-right-8'>";
		htmlContent += formatUndefine(sl)+"-</span><input type='text' id='"+this.qjIndex+"_qj' qsz ='"+formatUndefine(sl)+"' value='' name='gs' class='form-control' onkeyup='settingtimeprice_open.changeQj(this)' maxlength='10' /><span id='' class='input-group-addon input-addon-style padding-left-8'>";
		htmlContent += "工时</span></div></td>";
		htmlContent += "<td  style='text-align:left;vertical-align:middle;' >";
		htmlContent += "<div class='input-group col-xs-12'><span id='' class='input-group-addon input-addon-style padding-right-8' >单价</span>";
		htmlContent += "<input type='text' name='jg' onkeyup='settingtimeprice_open.changeDj(this)' class='form-control' maxlength='10' /></div></td>";
		htmlContent += "</tr>";
		$("#settingtimeprice_open_alert_qjGd").before(htmlContent);
	},
	delQj:function(obj){
		var id = $(obj).attr("id").split("_")[0];
		var prevId = $(obj).parent().parent().prev().attr("id");
		var nextId = $(obj).parent().parent().next().attr("id");
		$(obj).parent().parent().remove();
		var tem = 0;
		if(prevId !=null){
			tem = this.qj[prevId];
		}
		var sl = 0;
		$("#"+nextId+"_qjqssl").html(tem+"-");
		delete(this.qj[id]);
		for (var key in this.qj) {  
          sl = this.qj[key];  
        }
		$("#settingtimeprice_open_alert_autoQj").html(sl+"工时以后");
	},
	changeQj : function(obj){
		this.onkeyup4Num(obj);
		var this_ = this;
		var val = $(obj).val();
		var id = $(obj).attr("id").split("_")[0];
		var qsz = $(obj).attr("qsz");
		this.qj[id]=val;	
		$(obj).removeClass("border-color-red");
		var nextId = $(obj).parent().parent().parent().next().attr("id");
		$("#"+nextId+"_qjqssl").html(val+"-");
		var sl = 0;
		for (var key in this.qj) {  
	          sl = this.qj[key];  
	        }
		$("#settingtimeprice_open_alert_autoQj").html(sl+"工时以后");
	},
	changedj : function(obj){
		this.onkeyup4Num(obj);
		var this_ = this;
		$(obj).removeClass("border-color-red");
	},
}

