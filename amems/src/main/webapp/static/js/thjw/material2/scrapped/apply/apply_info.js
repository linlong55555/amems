apply_info = {
	id:"apply_open",
	data:[],
	existsIdList:[],
	isValid : true,
	param: {
		id : null,
		operation:true,
		modalShow:true,
		callback:function(){}
	},
	init:function(param){
		this.existsIdList = [];
		this.isValid = true;
		$("#"+this.id+"_detail").empty();
		if(param){
			$.extend(this.param, param);
		}
		if(this.param.id){
			this.load();	
		}else{
			$("#"+this.id+"_detail").append("<tr id='apply_open_tr' class='text-center'><td colspan=\"10\">暂无数据 No data.</td></tr>");		}
	},
	load : function(){
		var this_= this;
		var obj = {};
		obj.id = this_.param.id;
		AjaxUtil.ajax({
			url : basePath + "/material/scrapped/apply/getScrappedDetailList",
			type : "post",
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : JSON.stringify(obj),
			success : function(data) {
				finishWait();
				if (data.total > 0) {
					this_.editStock(data.rows);
				} else {
					$("#"+this_.id+"_detail").append("<tr class='text-center'><td colspan=\"10\">暂无数据 No data.</td></tr>");
				}
			}
		});
	
	},
	openStock:function(){
		var this_ = this;
		stock_open_alert.show({
			existsIdList :this_.existsIdList,
			dprtcode:userJgdm,//组织机构
			callback:function(data){
				this_.appendStock(data);
			}
		})
	},
	getData:function(){
		var this_  = this;
		this_.isValid = true;
		var param = [];
		$("#apply_open_detail",$("#apply_open_modal")).find("tr").each(function(){
			var tr_this = this;
			var infos ={};
			var index=$(this).index(); //当前行
			var kcid = $(this).find("input[name='hiddenid']").val(); //当前行，隐藏id的值
			var bfsl = $(this).find("input[name='bfsl']").val();

			if(null == bfsl || "" == bfsl ){
				AlertUtil.showModalFooterMessage("请选择库存或请输入报废数量!");
				$(tr_this).find("input[name='bfsl']").focus();
				$(tr_this).find("input[name='bfsl']").addClass("border-color-red");
				this_.isValid = false; 
				return false;
			}else{    
				var reg1 = /(^[1-9]{1}[0-9]*$)|(^[0-9]*\.[0-9]{0,2}$)/;
				if(reg1.test(bfsl)==false){
					AlertUtil.showModalFooterMessage("报废数量输入不正确!");
					$(tr_this).find("input[name='bfsl']").focus();
					$(tr_this).find("input[name='bfsl']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
			}
			
			infos.kcid = kcid;
			infos.kcsl = bfsl;
			infos.zt =1;
			infos.sjly =1;
			param.push(infos);
		});
		if(param.length == 0 && this_.isValid == true){
			AlertUtil.showModalFooterMessage("请选择库存!");
			this_.isValid = false; 
			return false;
		}
	return param;
	},
	appendStock: function(list){
		var this_=this;
		var htmlContent = '';
		$.each(list,function(index,row){
			var kcsl = row.kcsl?row.kcsl:0;
			var djsl = 0;
			if(row.djsl != undefined && row.djsl !="" && row.djsl !=null){
				djsl = row.djsl 
			}
			var sy = kcsl - djsl;
			var kccb = 0;
			var cb = 0;
			if(row.kccb != undefined && row.kccb !="" && row.kccb !=null){
				kccb = row.kccb.toFixed(2);
				cb = (sy * kccb).toFixed(2);
			}else{
				kccb = "";
				cb = "";
			}
			var jz = 0;
			var zjz = 0;
			if(row.jz != undefined && row.jz !="" && row.jz !=null){
				jz = row.jz.toFixed(2);
				zjz = (sy * jz).toFixed(2);
			}else{
				jz = "";
				zjz = "";
			}
			this_.existsIdList.push(row.id);
			htmlContent += "<tr name='"+row.id+"' id='"+row.id+"'>";
			htmlContent +="<td style='text-align:center;vertical-align:middle;'><input type='hidden' name='hiddenid' value='"+row.id+"'/><button class='line6 line6-btn' onclick='apply_info.deleteRow(\""+ row.id+ "\");'  type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button></td>";	
			htmlContent += "<td  style='text-align:left;vertical-align:middle;' titile='"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"' >"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.bjh)+"\n"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.bjh)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"</p></td>";//部件名称
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.pch)+"\n"+StringUtil.escapeStr(row.sn)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.pch)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.sn)+"</p></td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.gg)+"\n"+StringUtil.escapeStr(row.xh)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.gg)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.xh)+"</p></td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(formatUndefine(row.ckh))+" "+StringUtil.escapeStr(formatUndefine(row.ckmc))+"\n"+StringUtil.escapeStr(formatUndefine(row.kwh))+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.ckh)+" "+formatUndefine(row.ckmc)+"</p><p  class='margin-bottom-0 '>"+formatUndefine(row.kwh)+"</p></td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+sy+row.jldw+"' ><div class='input-group col-xs-12'><input maxlength='10' value='"+(sy)+"' id='"+row.id+"' kcbz='"+formatUndefine(row.biz)+"' jzbz='"+formatUndefine(row.jzbz)+"'  kccb='"+formatUndefine(kccb)+"' jz ='"+formatUndefine(jz)+"' name='bfsl' class='form-control' type='text'  onkeyup='apply_info.changeSl(this)'><span name='dw' class='input-group-addon' style='min-width: 35px;'>"+StringUtil.escapeStr(row.jldw)+"</span></div></td>"; 	
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(row.paramsMap?row.paramsMap.cqbh:'')+"' >"+formatUndefine(row.paramsMap?row.paramsMap.cqbh:'')+"</td>";//产权 		
		    htmlContent += "<td style='text-align:left;vertical-align:middle;' name='kcjz' title='"+formatUndefine(cb)+" "+formatUndefine(row.biz)+"' ><p  class='margin-bottom-0 '>"+formatUndefine(cb)+" "+formatUndefine(row.biz)+"</p></td>"; 
		    if(formatUndefine(row.hjsm) =='' && formatUndefine(row.syts)==''){
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(row.rksj?row.rksj.substring(0,10):"")+"'>"+formatUndefine(row.rksj?row.rksj.substring(0,10):"")+"</td>";
			}else{
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(row.rksj?row.rksj.substring(0,10):"")+"\n"+formatUndefine(row.hjsm).substring(0,10)+"/"+formatUndefine(row.syts)+"' ><p  class='margin-bottom-0 '>"+formatUndefine(row.rksj?row.rksj.substring(0,10):"")+"</p><p  class='margin-bottom-0 '>"+formatUndefine(row.hjsm).substring(0,10)+"/"+formatUndefine(row.syts)+"</p></td>";
			}
			htmlContent += "</tr>";  
		    
		});
		$("#apply_open_tr").remove();
		$("#"+this_.id+"_detail").append(htmlContent);
		TableUtil.addTitle("#"+this_.id+"_detail tr td"); //加载td title
	},
	deleteRow:function(id){
		$("#apply_open_table #"+id).remove();
		var table = document.getElementById(this.id+"_table");
		var row = table.rows.length;
		if(row == 1){
			$("#"+this.id+"_detail").append("<tr id='apply_open_tr'><td class='text-center' colspan=\"10\">暂无数据 No data.</td></tr>");
		}
		var existcode=this.existsIdList;
		if(existcode.length>0){
			var index = existcode.indexOf(id);
			if (index > -1) {
				existcode.splice(index, 1);
			}
		}
		this.existsIdList=existcode;
		console.info(this.existsIdList);
	},
	editStock: function(list){
		var this_=this;
		var htmlContent = '';
		$.each(list,function(index,row){
			this_.existsIdList.push(row.KCID);
			htmlContent += "<tr name='"+row.KCID+"' id='"+row.KCID+"'>";
			htmlContent +="<td style='text-align:center;vertical-align:middle;'> <input type='hidden' name='hiddenid' value='"+row.KCID+"'/><button class='line6 line6-btn' onclick='apply_info.deleteRow(\""+ row.KCID+ "\");'  type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button></td>";	
			htmlContent += "<td  style='text-align:left;vertical-align:middle;' title='"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.HCLX)+"' >"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.HCLX)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.BJH)+"\n"+StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.BJH)+"</p><p  class='margin-bottom-0 '> "+StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS)+"</p></td>";//部件名称
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.PCH)+"\n"+StringUtil.escapeStr(row.SN)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.PCH)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.SN)+"</p></td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.GG)+"\n"+StringUtil.escapeStr(row.XH)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.GG)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.XH)+"</p></td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(formatUndefine(row.CKH))+" "+StringUtil.escapeStr(formatUndefine(row.CKMC))+"\n"+StringUtil.escapeStr(formatUndefine(row.KWH))+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(formatUndefine(row.CKH))+" "+StringUtil.escapeStr(formatUndefine(row.CKMC))+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(formatUndefine(row.KWH))+"</p></td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+row.BFSL+row.JLDW+"' ><div class='input-group col-xs-12'><input name='bfsl' maxlength='10' value='"+row.BFSL+"' id='"+row.KCID+"' kcbz='"+formatUndefine(row.BIZ)+"' jzbz='"+formatUndefine(row.JZBZ)+"'  kccb='"+formatUndefine(row.KCCB)+"' jz ='"+formatUndefine(row.JZ)+"' onkeyup='apply_info.changeSl(this)' class='form-control' type='text'><span name='dw' class='input-group-addon' style='min-width: 35px;'>"+StringUtil.escapeStr(row.JLDW)+"</span></div></td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(row.CQBH)+"'>"+formatUndefine(row.CQBH)+"</td>";//产权 	
			var zcb = '';
			var zjz = '';
			if(formatUndefine(row.ZCB) != ''){
				zcb = formatUndefine(row.ZCB).toFixed(2)+" "+formatUndefine(row.BIZ);
			}
			if(formatUndefine(row.ZJZ) != ''){
				zjz = formatUndefine(row.ZJZ).toFixed(2)+" "+formatUndefine(row.JZBZ);
			}
		 
		    htmlContent += "<td style='text-align:left;vertical-align:middle;' name='kcjz' title='"+formatUndefine(zcb)+"' ><p  class='margin-bottom-0 '>"+formatUndefine(zcb)+"</p></td>"; 
		    if(formatUndefine(row.HJSM) =='' && formatUndefine(row.SYTS)==''){
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"' >"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"</td>";
			}else{
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"\n"+formatUndefine(row.HJSM).substring(0,10)+"/"+formatUndefine(row.SYTS)+"' ><p  class='margin-bottom-0 '>"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"</p><p  class='margin-bottom-0 '>"+formatUndefine(row.HJSM).substring(0,10)+"/"+formatUndefine(row.SYTS)+"</p></td>";
			}
			htmlContent += "</tr>";  
		    
		});
		$("#apply_open_tr").remove();
		$("#"+this_.id+"_detail").append(htmlContent);
	},
	changeSl : function(obj){
		this.onkeyup4Num(obj);
		var this_ = this;
		var jz = $(obj).attr("jz");
		var cb = $(obj).attr("kccb");
		var id = $(obj).attr("id");
		var sl = $(obj).val();
		var kcbz = $(obj).attr("kcbz");
		var jzbz = $(obj).attr("jzbz");
		var $tr = $("#apply_open_table").find("tr[name='"+id+"']");
		var zjz ="";
		if(jz !=""){
			zjz = (sl * jz).toFixed(2);
		}
		var zcb ="";
		if(cb !=""){
			zcb = (sl * cb).toFixed(2);
		}
		$tr.find("td[name='kcjz']").html("<p  class='margin-bottom-0 '>"+zcb+kcbz+"</p>");
		$tr.find("td[name='kcjz']").attr("title",zcb+kcbz);
		$(obj).removeClass("border-color-red");
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
	}
}

