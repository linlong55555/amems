var orderNumber = 1;
var jdxd=["其它","飞行队","航空公司"];
$(document).ready(function(){
	Navigation(menuCode,"其他飞行队借入申请出库","Out Stock Other Aerocade Borrow Apply");
		//数据字典
	// DicAndEnumUtil.registerDic('21','fxd');
	 var zzjg=$('#dprtCode1').val();
	 $("#fxd").val(zzjg); 
	 datepicker();
	 $("#reserveTable").append("<tr><td  colspan='11'  class='text-center'>暂无数据 No data.</td></tr>");
});

function datepicker(){
	$('.date-picker').datepicker( 'setDate' , new Date());
}
 

		
		$('.date-picker').datepicker({
			autoclose : true
		}).next().on("click", function() {
			$(this).prev().focus();
		});
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
				});
		 $('#example27').multiselect({
			buttonClass : 'btn btn-default',
			buttonWidth : 'auto',

			includeSelectAllOption : true
		}); 
		 
		 //回车事件控制
		 document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#workOrderNum").is(":focus")){
					$("#homeSearchWorkOrder").click();      
				}
			}
		};
		 
		//选择一行
		 function selectOtherStock(id){
		 	//先移除暂无数据一行
		 	var len = $("#reserveTable").length;
		 	if (len == 1) {
		 		if($("#reserveTable").find("td").length == 1){
		 			$("#reserveTable").empty();
		 		}
		 	}
		 	var flag = true;
		 	if (len > 0){
		 		$("#reserveTable").find("tr").each(function(){
		 			var index=$(this).index(); //当前行
		 			var hiddenid =$("input[name='kcid']").eq(index).val(); //当前行，隐藏id的值
		 			if(id == hiddenid){
		 				flag = false;
		 			}
		 		});
		 	}
		 	if(flag){
		 		var row = getSelectRows(id);
		 		row.orderNumber = orderNumber++;
		 		addRow(row);
		 	}
		 }
		//向table新增一行
		 function addRow(obj){
			 //$("#reserveTable").parent().parent().remove();
		 	var tr = "";
		 		tr += "<tr>";
		 		
		 		tr += "<td style='text-align:center;vertical-align:middle;'>";
		 		tr += "<button class='line6' onclick='removeCol(this)'>";
		 	    tr += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
		 	    tr += "</button>";
		 		tr += "</td>";
		 		
		 		tr += "<td style='text-align:center;vertical-align:middle;'>";
		 		tr += 	  "<span name='orderNumber'>"+obj.orderNumber+"</span>";
		 		tr += 	  "<input type='hidden' name='kcid' value='"+obj.id+"'/>";
		 		tr += 	  "<input type='hidden' name='kcsl' value='"+obj.kcsl+"'/>";
		 		tr += "</td>";
		 		
		 		tr +=  "<td style='text-align:left;vertical-align:middle;'><input type='hidden' name='bjid' value='"+obj.bjid+"'/><input type='hidden' name='bjh' value='"+obj.bjh+"'/>"+StringUtil.escapeStr(obj.bjh)+"</td>";
		 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zwms)+"'>"+StringUtil.escapeStr(obj.zwms)+"</td>";
		 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.ywms)+"'>"+StringUtil.escapeStr(obj.ywms)+"</td>";
		 		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',obj.hclx) +"</td>";
		 		tr += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(gljb[obj.gljb]) +"</td>";
		 		
		 		
		 		if(StringUtil.escapeStr(obj.sn) != ""){
		 			tr += "<td class='text-left'><input type='hidden' name='sn1' value='"+StringUtil.escapeStr(obj.sn)+"'/>"+StringUtil.escapeStr(obj.sn)+"</td>"; 
				}else{
					tr += "<td class='text-left'><input type='hidden' name='pch' value='"+StringUtil.escapeStr(obj.pch)+"'/>"+StringUtil.escapeStr(obj.pch)+"</td>"; 
				}
		 		tr = tr + "<td class='text-left'>"+formatUndefine(obj.kcsl)+"</td>";  
		 		tr += "<td><input class='form-control' name='cksl' type='text'  placeholder='请输入数字...' onkeyup='clearNoNum(this)' maxlength='10' /></td>";
		 		tr = tr + "<td class='text-left'>"+StringUtil.escapeStr(obj.jldw)+"</td>";  
		 		tr += "</tr>";
		 	
		 	$("#reserveTable").append(tr);
		 }
		 
		//移除一行
		 function removeCol(e){
		 	$(e).parent().parent().remove();
		 	resXh();
		 	orderNumber--;
		 	var len = $("#reserveTable").find("tr").length;
		 	if(len < 1){
		 		$("#reserveTable").append("<tr><td  colspan='11'  class='text-center'>暂无数据 No data.</td></tr>");
		 	}
		 }

		 function resXh(){
		 	var len = $("#reserveTable").find("tr").length;
		 	if (len == 1) {
		 		if($("#reserveTable").find("td").length ==1){
		 			return false;
		 		}
		 	}
		 	var orderNumber = 1;
		 	if (len > 0){
		 		$("#reserveTable").find("tr").each(function(){
		 			var index=$(this).index(); //当前行
		 			$("span[name='orderNumber']").eq(index).html(orderNumber++);
		 		});
		 	}
		 }

		//返回
		 function go(){
			 window.location = basePath+"/otheraerocade/borrow/main?pageParam="+pageParam;
		 }
		 
		 /**
		  * 选择申请人
		  * @returns
		  */
		 function selectUser(){
		 	userModal.show({
		 		selected:$("#userid").val(),//原值，在弹窗中默认勾选
		 		callback: function(data){//回调函数
		 			$("#userid").val(formatUndefine(data.id));
		 			$("#username").val(StringUtil.escapeStr(data.username)+" "+StringUtil.escapeStr(data.realname));
		 			$("#ckbmid").val(StringUtil.escapeStr(data.bmdm));
		 		}
		 	});
		 }	
		 
		 /**
		  * 选择出库人
		  * @returns
		  */
		 function selectUser1(){
		 	userModal.show({
		 		selected:$("#inventoryckrid").val(),//原值，在弹窗中默认勾选
		 		callback: function(data){//回调函数
		 			$("#inventoryckrid").val(formatUndefine(data.id));
		 			$("#inventoryckr").val(StringUtil.escapeStr(data.username)+" "+StringUtil.escapeStr(data.realname));
		 			$("#inventorymckmid").val(StringUtil.escapeStr(data.bmdm));
		 			
		 		}
		 	});
		 }
		 
		 /**
		  * 出库
		  * @returns
		  */
		 function putoutstorage(){
			 
					var workContentParam = [];
					var fola=true;
						$("#reserveTable").find("tr").each(function(){
							var infos ={};
							var index=$(this).index(); //当前行
							var id =$("input[name='kcid']").eq(index).val(); //当前行，隐藏id的值
							var cksl =$("input[name='cksl']").eq(index).val(); //出库数量
							var kcsl =$("input[name='kcsl']").eq(index).val(); //库存数量
							
							var bjid =$("input[name='bjid']").eq(index).val(); //部件id
							var bjh =$("input[name='bjh']").eq(index).val(); //部件号
							var sn =$("input[name='sn1']").eq(index).val(); //序列号
							if(sn!=""){
								infos.sn  = sn;
							}else{
								var pch =$("input[name='pch']").eq(index).val(); //序列号
								infos.pch  = pch;
							}
							
							var folt=true;
							if(cksl==""||cksl==null){
								AlertUtil.showErrorMessage("出库数量不能为空!");
								fola= false;
								folt=false;
								return false;
							}
							
							if(parseInt(cksl)>parseInt(kcsl)){
								AlertUtil.showErrorMessage("出库数不能大于库存数!");
								fola= false;
								folt=false;
								return false;
							}
							
							if(folt==false){
								return false;
							}
							
							infos.id  = id;
							infos.cksl  = cksl;
							infos.bjid  = bjid;
							infos.bjh  = bjh;
						
							workContentParam.push(infos);
						});
					
					
					if(fola==false){
						return false;
					}
					
					var fixedCheckItem = {
							
							otheraerocadeid :$.trim($('#otheraerocadeid').val()),//其它飞行队申请单id
							cukid :$.trim($('#inventoryckrid').val()),//出库人id
							cksj :$.trim($('#inventoryckrq').val()),//出库日期
							ckbmid :$.trim($('#inventorymckmid').val()),//出库人部门
							sqrid :$.trim($('#userid').val()),//出库人id
							sqsj :$.trim($('#sqsj').val()),//出库日期
							sqbmid :$.trim($('#ckbmid').val()),//出库人部门
							jdfzr :$.trim($('#jdfzr').val()),//借调对象负责人
							fxd :$.trim($('#fxd').val()),//飞行队
							bz :$.trim($('#bz').val()),//备注
					};
					
					if(workContentParam==null||workContentParam==""){
						AlertUtil.showErrorMessage("请选择要出库的航材!");
						return;
					}
					fixedCheckItem.materialRequisitionDetail = workContentParam;
				    AlertUtil.showConfirmMessage("你确定要出库吗？",{callback: function(){
					
					startWait();
					// 提交数据
					AjaxUtil.ajax({
						url : basePath+"/otheraerocade/borrow/stockRemoval",
						contentType:"application/json;charset=utf-8",
						type:"post",
						data:JSON.stringify(fixedCheckItem),
						dataType:"json",
						success:function(data){
								finishWait();//结束Loading
							if (data.state == "success") {
								AlertUtil.showMessage(data.message,'/otheraerocade/borrow/main?id='+data.id+'&pageParam='+pageParam);
							} else {
								AlertUtil.showErrorMessage(data.message);
							}
						}
					});
					 }});
		 }	
		 
			//只能输入数字和小数
		 function clearNoNum(obj){
		    	//先把非数字的都替换掉，除了数字
		        obj.value = obj.value.replace(/[^\d.:]/g,"");
		    	
		    	if(obj.value.indexOf(".") != -1){
		    		if(obj.value.indexOf(":") != -1){
		    			obj.value = obj.value.substring(0,obj.value.length -1);
		    		}else{
		    			clearNoNumTwoDate(obj);
		    		}
		    	}
		    	if(obj.value.indexOf(":") != -1){
		    		if(obj.value.indexOf(".") != -1){
		    			obj.value = obj.value.substring(0,obj.value.length -1);
		    		}else{
		    			clearNoNumTwoColon(obj);
		    		}
		    	}
		    	
		    	if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
		      		 if(obj.value.substring(1,2) != "." && obj.value.substring(1,2) != ":"){
		      			obj.value = 0;
		      		 }
		      	 }
		 }
		 
		//只能输入数字和小数,保留两位,小数后不能超过59
		 function clearNoNumTwoDate(obj){
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
		    	 var value = obj.value;
		    	 if(str[1].length == 2){
		    		 if(str[1] > 59){
		        		 value = str[0] +".59";
		        	 }
		    	 }
		    	 if(str[1].length > 2){
		    		 value = str[0] +"." + str[1].substring(0,2);
		    	 }
		    	 obj.value = value;
		     }
		 }
		 
		//只能输入数字和冒号,保留两位,冒号后不能超过59
		 function clearNoNumTwoColon(obj){
		 	 //先把非数字的都替换掉，除了数字和.
		     obj.value = obj.value.replace(/[^\d:]/g,"");
		     //必须保证第一个为数字而不是:
		     obj.value = obj.value.replace(/^\:/g,"");
		     //保证只有出现一个.而没有多个.
		     obj.value = obj.value.replace(/\:{2,}/g,":");
		     //保证.只出现一次，而不能出现两次以上
		     obj.value = obj.value.replace(":","$#$").replace(/\:/g,"").replace("$#$",":");
		     
		     var str = obj.value.split(":");
		     if(str.length > 1){
		    	 var value = obj.value;
		    	 if(str[1].length == 2){
		    		 if(str[1] > 59){
		        		 value = str[0] +":59";
		        	 }
		    	 }
		    	 if(str[1].length > 2){
		    		 value = str[0] +":" + str[1].substring(0,2);
		    	 }
		    	 obj.value = value;
		     }
		 }