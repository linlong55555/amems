$(document).ready(function(){
	Navigation(menuCode,"报废出库","Scrap Outstock");
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				//调用主列表页查询
			}
		}
	});
	
	 datepicker();
	 
});

/**
 * 选择申请人
 * @returns
 */
function selectUser(){
	userModal.show({
		selected:$("#inventorysqrid").val(),//原值，在弹窗中默认勾选
		dprtcode:$("#dprtcodeNum").val(),//原值，在弹窗中默认勾选
		callback: function(data){//回调函数
			$("#inventorysqrid").val(formatUndefine(data.id));
			$("#inventorysqr").val(StringUtil.escapeStr(data.username)+" "+StringUtil.escapeStr(data.realname));
			$("#inventorysqbmid").val(StringUtil.escapeStr(data.bmdm));
			
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
		dprtcode:$("#dprtcodeNum").val(),//原值，在弹窗中默认勾选
		callback: function(data){//回调函数
			$("#inventoryckrid").val(formatUndefine(data.id));
			$("#inventoryckr").val(StringUtil.escapeStr(data.username)+" "+StringUtil.escapeStr(data.realname));
			$("#inventorymckmid").val(StringUtil.escapeStr(data.bmdm));
			
			}
		});
	}

	function datepicker(){
		$('.date-picker').datepicker( 'setDate' , new Date());
	}
	 
	//信息检索
	function change(bjh,id){
			$("#alertModalMaterialWinAdd").modal("show");
			AjaxGetDatasWithSearch2(bjh,id);
	}
	  
	//字段排序
	function orderBy(obj) {
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass().addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		if (orderStyle == "sorting_asc") {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		goPage(currentPage,obj,orderStyle.split("_")[1]);
	}

	 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
	
	//信息检索
	function searchRevision(){
		goPage(1,"auto","desc");
	}
		
	//搜索条件重置
	function searchreset(){
		$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function(){
		$(this).val("");
	});
}
 
	//搜索条件显示与隐藏 
	function search() {
		if ($("#divSearch").css("display") == "none") {
			$("#divSearch").css("display", "block");
			$("#icon").removeClass("fa-chevron-down");
			$("#icon").addClass("fa-chevron-up");
		} else {

			$("#divSearch").css("display", "none");
			$("#icon").removeClass("fa-chevron-up");
			$("#icon").addClass("fa-chevron-down");
		}
	}

		
	$('#ckrq').datepicker({
		 autoclose: true,
		 clearBtn:true
	}).on('hide', function(e) {
	});
	
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
		 
	
	 
	//只能输入数字和小数
	 function go(){
		 window.location = basePath+"/aerialmaterial/outstock/main?t=" + new Date().getTime()+"#inventory";
	 }
	 
	//删除
	 function deleteinventory(id){
		 $('#'+id).remove();
	 }

	 /**
	  * 出库
	  * @returns
	  */
	 function putoutstorage(){
		 
	
				var workContentParam = [];
				var fola=true;
				
				var len = $("#detailTBody").find("tr").length;
				if (len > 0){
				
					$("#detailTBody").find("tr").each(function(){
						var infos ={};
						var index=$(this).index(); //当前行
						
						var id =$("input[name='id']").eq(index).val(); //当前行，隐藏id的值
						var bjh =$("input[name='bjh']").eq(index).val(); //当前行，隐藏件号的值
						var zwms =$("input[name='zwms']").eq(index).val(); //中文名称
						var ywms =$("input[name='ywms']").eq(index).val(); //英文名称
						var cjjh =$("input[name='cjjh']").eq(index).val(); //厂家件号
						var hclx =$("input[name='hclx']").eq(index).val(); //航材类型
						
						var sn =$("input[name='sn']").eq(index).val(); //序列号
						var pch ="";
						if(sn==""){
							 pch =$("input[name='pch']").eq(index).val(); //批次号
						}	
						
						var kcid =$("input[name='kcid']").eq(index).val(); //库存id
						var shzh =$("input[name='shzh']").eq(index).val(); //适航证号
						var kwh =$("input[name='kwh']").eq(index).val(); //库位号
						var kcsl =$("input[name='kcsl']").eq(index).val(); //库存数量
						var cksl =$("input[name='cksl']").eq(index).val(); //出库数量
						var folt=true;
						if(cksl==""||cksl==null||cksl==0){
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
						
						infos.kcid  = kcid;
						infos.id  = id;
						infos.bjh  = bjh;
						infos.zwms  = zwms;
						infos.ywms  = ywms;
						infos.cjjh  = cjjh;
						infos.hclx  = hclx;
						infos.sn  = sn;
						if(sn==""){
							infos.pch  = pch;
						}
						infos.shzh  = shzh;
						infos.kwh  = kwh;
						infos.kcsl  = kcsl;
						infos.cksl  = cksl;
						workContentParam.push(infos);
					});
				}else{
					AlertUtil.showErrorMessage("请选择需要出库的的出库清单");
					return false;
				}
				
				if(fola==false){
					return false;
				}
				
				var fixedCheckItem = {
						
						sqrid :$.trim($('#inventorysqrid').val()),//申请人id
						sqsj :$.trim($('#inventorysqrq').val()),//申请日期
						sqbmid :$.trim($('#inventorysqbmid').val()),//申请人部门
						cukid :$.trim($('#inventoryckrid').val()),//出库人id
						cksj :$.trim($('#inventoryckrq').val()),//出库日期
						ckbmid :$.trim($('#inventorymckmid').val()),//出库人部门
						bz :$.trim($('#inventorybz').val()),//备注
					};
				
				fixedCheckItem.materialRequisitionDetail = workContentParam;
			
		AlertUtil.showConfirmMessage("你确定要出库吗？",{callback: function(){
				startWait();
				// 提交数据
				AjaxUtil.ajax({
					url : basePath+"/aerialmaterial/outstock/manualstockdumping",
					contentType:"application/json;charset=utf-8",
					type:"post",
					data:JSON.stringify(fixedCheckItem),
					dataType:"json",
					success:function(data){
							finishWait();//结束Loading
						if (data.state == "success") {
							$("#alertModal").modal("show");
								$('#alertModal').on('hidden.bs.modal', function (e) {
									window.location = basePath+"/aerialmaterial/outstock/main?t=" + new Date().getTime()+"#inventory";
							});
							$("#alertModalBody").html("出库成功!");
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
	 
	 
		var ids=[];
	 /**
	  * 更换库存
	  * @returns
	  */
	 function changerepertory(){
		 
			$("#detailTBody").find("tr").each(function(){
				var index=$(this).index(); //当前行
				var id =$("input[name='id']").eq(index).val(); //当前行，隐藏id的值
				 ids.push(id);
			});
		 
			 window.location =basePath+"/aerialmaterial/outstock/changerepertory?id="+$.trim(ids)+"&parameter=inventory";
	 }
	 