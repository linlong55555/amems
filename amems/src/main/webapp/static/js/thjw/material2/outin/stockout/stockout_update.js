stockout_update_modal = {
	id : "stockout_main",
	open: function(param){
		var this_ = this;
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 

		 
		var shdhid=$("#shdhid").val();
		
		
		if(shdhid!=''){
			this_.initDate(shdhid);					
		}else{
//			Defectkeep_Open_Modal.EmptiedData();  //清空数据
		}
		
	},
	/**
	 * 加载数据
	 */
	initDate : function(param){
		var this_=this;
		var obj = {};
		obj.id = param;
		//根据单据id查询信息
		startWait();
	   	AjaxUtil.ajax({
	 		url:basePath + "/material/outin/getByStockoutId",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		success:function(data){
	 			finishWait();
	 			if(data!=null){
	 				if(data.zt==11){
	 					AlertUtil.showMessage("该数据已更新，请刷新后再进行操作!");
	 					return false;
	 				}
	 				this_.setDate(data);// 加载数据
				};
	 		}
		});
	},
	/**
	 * 加载缺陷保留数据
	 */
	setDate : function(data){
		var bs=1;
		if(data.zt==1){
			stockout_body.button(2);//保存权限
		}else{
			stockout_body.button(3);//提交权限
			bs=2;
		}
		
		$("#id").val(data.id);					
		$("#shdh").val(data.shdh);					
		$("#shlx").val(data.shlx);					
		$("#shrq").val(StringUtil.escapeStr(data.shrq).substring(0,10));					
		$("#shr").val(displayName);				
		$("#lyid").val(data.lyid);					
		$("#lybh").val(data.lybh);					
		$("#lydw").val(data.lydw);					
		$("#fjzch").val(data.fjzch);	
		if(data.zt==2){
			$("#ckids").val(data.paramsMap?data.paramsMap.ckmc:'');					
			$("#ckids").show();	
			$("#ckid").hide();		
		}
		$("#ckid").val(data.ckid);					
		$("#bz").val(data.bz);					
		$("#ztName").val(DicAndEnumUtil.getEnumName('outboundOrderStatusEnum',data.zt));					
		$("#zt").val(data.zt);					
		
		if(data.shlx == 70){ //发料
			//选择需求单
			$("#lyidDiv").show(); //显示来源框
			

			 $("#lybutton").attr("onclick","stockout_body.initDemandData()");
			 $("#lybh").attr("ondblclick","stockout_body.initDemandData()");
			 
		}else if(data.shlx == 90){
			//隐藏选择
			$("#lyidDiv").hide();
			
		}else{
			$("#lyidDiv").show(); //显示来源框
			//与出库类型匹配的选择合同
			$("#lybutton").attr("onclick","stockout_body.initContractData()");
			$("#lybh").attr("ondblclick","stockout_body.initContractData()");
		}
	
		this.setStockData(data.outboundOrderDetailslist,bs);
	},
	setStockData: function(list,bs){
		console.info(list);
		var this_=this;
		var htmlContent = '';
		$.each(list,function(index,row){
			htmlContent += "<tr>";
			if(bs!=2){
				htmlContent += '<td class="text-center required"><button class="line6 line6-btn" onclick="stockout_body.removeCol(this)" type="button">';
				htmlContent += '<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i></button></td>';
			}
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>"; 
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh)+"</td>";
			htmlContent += "<td style='text-align:right;vertical-align:middle;'  >"+formatUndefine(row.kcsl-row.djsl)+"</td>"; 
			if(bs!=2){
				htmlContent += "<td  style='text-align:left;vertical-align:middle;'><input type='text' onkeyup='stockout_body.clearNoNum(this)' placeholder='长度最大为10'   maxlength='10' value='"+formatUndefine(row.cksl)+"' name='cksl' class='form-control'></td>";//部件名称
			}else{
				htmlContent += "<td class='text-right'>"+StringUtil.escapeStr(row.cksl)+"</td>"; 
			}
			htmlContent += "<td style='text-align:left;vertical-align:middle;'  >"+formatUndefine(row.jldw)+"</td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.ckh)+"</td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.kwh)+"</td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+formatUndefine(row.paramsMap?row.paramsMap.cqbh:'')+"</td>"; //产权
		    htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gg)+"</td>"; 
		    htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xingh)+"</td>"; 
			var kccb = StringUtil.escapeStr(row.kccb);
			if(kccb == ''){
				htmlContent += "<td ></td>"; 
			}else{
				kccb = kccb.toFixed(2);
				htmlContent += "<td class='text-right' >"+kccb+" "+formatUndefine(row.biz)+"</td>"; 
			}
			var jz = formatUndefine(row.jz);
			if(jz == ''){
				htmlContent += "<td ></td>"; 
			}else{
				jz = jz.toFixed(2);
				htmlContent += "<td class='text-right' >"+jz+" "+formatUndefine(row.jzbz)+"</td>"; 
			}
		    htmlContent += "<input type='hidden' name='kcsl' value='"+formatUndefine(row.kcsl)+"'>"; 
		    htmlContent += "<input type='hidden' name='kcid' value='"+row.kcid+"'>"; 
		    htmlContent += "<input type='hidden' name='id' value='"+row.id+"'>"; 
		    htmlContent += "<input type='hidden' name='kcllid' value='"+row.kcllid+"'>"; 
		    htmlContent += "<input type='hidden' name='gljb' value='"+formatUndefine(row.gljb)+"'>"; 
		    htmlContent += "<input type='hidden' name='mxid' value='"+formatUndefine(row.paramsMap?row.paramsMap.mxid:'')+"'>"; 
			htmlContent += "</tr>";  
		    
		});
		$("#"+this_.id+"_list").empty();
		$("#"+this_.id+"_list").html(htmlContent);
		 TableUtil.addTitle("#"+this_.id+"_list tr td"); //加载td title
	}
	
};