/**
 *初始化当前js
 */
$(function(){
	stockout_main.load();
});

stockout_main = {
	id : "stockout_main",
	load: function(){
		Navigation(menuCode, '', '', 'SC12-1 ', '林龙', '2017-09-27', '林龙', '2017-10-10');//加载导航栏
		stockout_body.initFjzchOption(userJgdm); //初始化飞机注册号	
		this.initdata();//初始化数据
		AlertUtil.hideModalFooterMessage();    	//初始化错误信息 
		stockout_body.validation(); 			//初始化验证
	},
	//初始化数据
	initdata: function(){
		$("#shr").val(displayName);
		TimeUtil.getCurrentDate("#shrq"); //颁发日期,默认当前日期
		stockout_body.onchangeshlx();
		stockout_body.initStoreSelect();//初始化仓库
		stockout_body.button(1);//技术评估单新增权限
		$("#"+this.id+"_list").empty();
		$("#"+this.id+"_list").append("<tr><td colspan=\"15\"  class='text-center'>暂无数据 No data.</td></tr>");
	},
	appendContentHtmlStock: function(list){
		var this_=this;
		var htmlContent = '';
		$.each(list,function(index,row){
				htmlContent += "<tr>";
				htmlContent += "<td class=\"text-center\"><button class=\"line6 line6-btn\" onclick=\"stockout_body.removeCol(this,'"+row.id+"')\" type=\"button\">";
				htmlContent += '<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i></button></td>';
				htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>"; 
				htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh)+"</td>";
				htmlContent += "<td style='text-align:right;vertical-align:middle;'  >"+formatUndefine(row.kcsl-row.djsl)+"</td>"; 
				htmlContent += "<td  style='text-align:left;vertical-align:middle;'><input type='text' name='cksl' onkeyup='stockout_body.clearNoNum(this)' placeholder='长度最大为10'   maxlength='10' class='form-control'></td>";//部件名称
				htmlContent += "<td style='text-align:left;vertical-align:middle;'  >"+formatUndefine(row.jldw)+"</td>"; 
				htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.ckh)+"</td>"; 
				htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.kwh)+"</td>"; 
				htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
				htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
				htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+formatUndefine(row.paramsMap?row.paramsMap.cqbh:'')+"</td>"; //产权
			    htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gg)+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xh)+"</td>"; 
				var kccb = StringUtil.escapeStr(row.kccb);
				if(kccb == ''){
					htmlContent += "<td ></td>"; 
				}else{
					kccb = kccb.toFixed(2);
					htmlContent += "<td class='text-right' >"+kccb+" "+formatUndefine(row.biz)+"</td>"; 
				}
//				var jz = formatUndefine(row.jz);
//				if(jz == ''){
//					htmlContent += "<td ></td>"; 
//				}else{
//					jz = jz.toFixed(2);
//					htmlContent += "<td class='text-right' >"+jz+" "+formatUndefine(row.jzbz)+"</td>"; 
//				}
			    htmlContent += "<input type='hidden' name='kcsl' value='"+formatUndefine(row.kcsl)+"'>"; 
			    htmlContent += "<input type='hidden' name='kcid' value='"+row.id+"'>"; 
			    htmlContent += "<input type='hidden' name='id' value=''>"; 
			    htmlContent += "<input type='hidden' name='kcllid' value=''>"; 
			    htmlContent += "<input type='hidden' name='gljb' value='"+formatUndefine(row.gljb)+"'>"; 
			    htmlContent += "<input type='hidden' name='mxid' value='"+formatUndefine(row.paramsMap?row.paramsMap.mxid:'')+"'>"; 
				htmlContent += "</tr>";  
		});
		$("#"+this_.id+"_list").empty();
		$("#"+this_.id+"_list").html(htmlContent);
		 TableUtil.addTitle("#"+this_.id+"_list tr td"); //加载td title
	},
	
	/**
	 * 获取数据
	 */
	getData : function(){
		var this_=this;
		var obj = {};
		$('#from1').data('bootstrapValidator').validate();
		if (!$('#from1').data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		}
		
		obj.shlx = $("#shlx").val(); //出库类型
		obj.shdh = $("#shdh").val(); //编号
		obj.shrq = $("#shrq").val(); //出库日期
		obj.lydw = $("#lydw").val(); //接收单位
		obj.fjzch = $("#fjzch").val(); //飞机注册号
		obj.bz = $("#bz").val(); //备注
		obj.lyid = $("#lyid").val(); //来源id
		obj.lybh = $("#lybh").val(); //来源编号
		obj.ckid = $("#ckid").val(); //仓库id
		
		var outboundOrderDetailslist =[];
		var len = $("#stockout_main_list").find("tr").length;
		if (len == 1) {
			if($("#stockout_main_list").find("td").length ==1){
				flag = false;
				AlertUtil.showModalFooterMessage("请选择需要出库明细单!");
				return false;
			}
		}
		var fola=true;
		if (len > 0){
		$("#stockout_main_list>tr").each(function(){
				var infos2 ={};
				var paramsMap = {};
				var index=$(this).index(); //当前行
				var kcid =$(this).find("input[name='kcid']").val(); 
				var cksl =$(this).find("input[name='cksl']").val(); 
				var id =$(this).find("input[name='id']").val(); 
				var kcllid =$(this).find("input[name='kcllid']").val(); 
				var kcsl =$(this).find("input[name='kcsl']").val(); 
				var gljb =$(this).find("input[name='gljb']").val(); 
				var mxid =$(this).find("input[name='mxid']").val(); 
				var folt=true;
				
				if(cksl == "" || cksl == null){
					AlertUtil.showModalFooterMessage("出库数量不能为空!");
					fola= false;
					folt=false;
					return false;
				}
				
				if(parseInt(gljb) == 3 && parseInt(cksl) != 1){
					AlertUtil.showModalFooterMessage("管理级别为序列号时出库数量只能为1!");
					fola= false;
					folt=false;
					return false;
				}
				
				if(parseInt(cksl)>parseInt(kcsl)){
					AlertUtil.showModalFooterMessage("出库数不能大于可用数!");
					fola= false;
					folt=false;
					return false;
				}
				
				if(folt==false){
					return false;
				}
				
				
				paramsMap.mxid = mxid; //来源明细id
				infos2.paramsMap = paramsMap;
				
				infos2.id = id;
				infos2.kcid = kcid;
				infos2.cksl = cksl;
				infos2.kcsl = kcsl;
				infos2.kcllid = kcllid;
				
				outboundOrderDetailslist.push(infos2);
	 	});
		}
		if(fola==false){
			return false;
		}
		obj.outboundOrderDetailslist=outboundOrderDetailslist;  //库存数据
		
		
		
	return obj;
	},
	//保存
	save: function(){
		var obj=this.getData();
		if(obj==false){
			return false;
		}
		var idnew=$("#id").val();
		var url="";
		if(idnew==""){
			url="/material/outin/saveOutboundOrder";//新增
		}else if(idnew!=""){
			url="/material/outin/updateOutboundOrder";//修改
			obj.id=idnew;
		}
		 startWait();
	   	 AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		success:function(data){
	 			finishWait();
	 			AlertUtil.showMessage('保存成功!');
	 			stockout_body.EmptiedData(); //清空数据
	 			$("#shdhid").val(data);
	 			$('#from1').data('bootstrapValidator').resetForm(false);
				 stockout_body.validation(); 			//初始化验证
				 stockout_update_modal.open();//加载数据
	 		}
	   	  });
	},
	/**
	 * 提交
	 */
	submit : function(){
		var this_=this;
		
		var obj=this_.getData();
		if(obj==false){
			return false;
		}
		var idnew=$("#id").val();
		var url="";
		
		if(idnew==""){
			url="/material/outin/saveSubmit";//新增提交
		}else if(idnew!=""){
			url="/material/outin/updateSubmit";//修改提交
			obj.id=idnew;
		}
		
		AlertUtil.showConfirmMessage("确定要提交吗？", {callback: function(){
		 startWait($("#Assessment_Open_Modal"));
	   	 AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Assessment_Open_Modal"),
	 		success:function(data){
	 			finishWait();
	 			AlertUtil.showMessage('提交成功!');
	 			stockout_body.EmptiedData(); //清空数据
	 			$("#shdhid").val(data);
	 			$('#from1').data('bootstrapValidator').resetForm(false);
				 stockout_body.validation(); 			//初始化验证
				 stockout_update_modal.open();//加载数据
				
	 		}
	   	  });
	  }});
	},
	
	/**
	 * 获取数据
	 */
	getDatas : function(){
		var obj = {};
		
		obj.shlx = $("#shlx").val(); //出库类型
		obj.shdh = $("#shdh").val(); //编号
		obj.shrq = $("#shrq").val(); //出库日期
		obj.lydw = $("#lydw").val(); //接收单位
		obj.fjzch = $("#fjzch").val(); //飞机注册号
		obj.bz = $("#bz").val(); //备注
		obj.lyid = $("#lyid").val(); //来源id
		obj.lybh = $("#lybh").val(); //来源编号
		obj.ckid = $("#ckid").val(); //仓库id
		
		var outboundOrderDetailslist =[];

		$("#stockout_main_list>tr").each(function(){
				var infos2 ={};
				var paramsMap = {};
				var index=$(this).index(); //当前行
				var kcid =$(this).find("input[name='kcid']").val(); 
				var cksl =$(this).find("input[name='cksl']").val(); 
				var id =$(this).find("input[name='id']").val(); 
				var kcllid =$(this).find("input[name='kcllid']").val(); 
				var xmid =$(this).find("input[name='xmid']").val(); 
				var kcsl =$(this).find("input[name='kcsl']").val(); 
				infos2.id = id;
				infos2.kcid = kcid;
				infos2.cksl = cksl;
				infos2.kcsl = kcsl;
				infos2.kcllid = kcllid;
				
				
				paramsMap.mxid = xmid; //来源明细id
				infos2.paramsMap = paramsMap;
				
				outboundOrderDetailslist.push(infos2);
	 	});
		
		obj.outboundOrderDetailslist=outboundOrderDetailslist;  //库存数据
		
		
		
	return obj;
	},
	//删除
	deleteoutin: function(){
		var this_=this;
		var obj={};
		
		obj.id=$("#id").val();
			AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
				 startWait();
			   	 AjaxUtil.ajax({
			   		url:basePath + "/material/outin/deleteOut",
			 		contentType:"application/json;charset=utf-8",
			 		type:"post",
			 		async: false,
			 		data:JSON.stringify(obj),
			 		dataType:"json",
			 		success:function(data){
			 			finishWait();
			 			AlertUtil.showMessage("删除成功!");
			 			
			 			$('#from1').data('bootstrapValidator').resetForm(false);
						 stockout_body.validation(); 			//初始化验证
						 stockout_body.EmptiedData(); //清空数据
						 TimeUtil.getCurrentDate("#shrq"); //颁发日期,默认当前日期
			 		}
			   	  });
			   	 
			 }});
	},
	//撤销
	revoke: function(){
		var this_=this;
		var obj=this_.getDatas();
		
		obj.id=$("#id").val();
		
			AlertUtil.showConfirmMessage("确定要撤销吗？", {callback: function(){
				 startWait();
			   	 AjaxUtil.ajax({
			   		url:basePath + "/material/outin/revokeOut",
			 		contentType:"application/json;charset=utf-8",
			 		type:"post",
			 		async: false,
			 		data:JSON.stringify(obj),
			 		dataType:"json",
			 		success:function(data){
			 			finishWait();
			 			AlertUtil.showMessage("撤销成功!");
			 			$('#from1').data('bootstrapValidator').resetForm(false);
						 stockout_body.validation(); 			//初始化验证
						 stockout_body.EmptiedData(); 			//清空数据
						 
						 TimeUtil.getCurrentDate("#shrq"); //颁发日期,默认当前日期
			 		}
			   	  });
			   	 
			 }});
	}
	
}



/**
 * 弹出窗验证销毁
 */
$("#Assessment_Open_Modal").on("hidden.bs.modal", function() {

	$("#from1").data('bootstrapValidator').destroy();
	$('#from1').data('bootstrapValidator', null);
	//Assessment_Open_Modal.validation();
});