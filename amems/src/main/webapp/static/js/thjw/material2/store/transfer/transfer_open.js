var nonchineseReg = /^[\x00-\xff]*$/;
//var cycleReg = /^(0|[1-9]\d*)$/;
var cycleReg = /^-?[0-9]\d*$/;
var timeReg = /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/;
var transfer_open={
		id:'transfer_open_alert',
		tableBody:'transfer_tbody',
		param: {
			storeIds:[],
			callback:function(){},
			dprtcode:userJgdm,//默认登录人当前机构代码
			parentid:null,//第一层modal
		},
		/**
		 * 初始化modal
		 */
		init : function(param){
			//隐藏出现异常的感叹号
			AlertUtil.hideModalFooterMessage();
			if(param){
				$.extend(this.param, param);
			}
			var this_ = this;
			//清空modal
			this_.closeEmpty();
			//初始化仓库
			var storeHtml = this_.initCk();
			$("#ck",$("#"+this_.id)).empty();
			$("#ck",$("#"+this_.id)).append(storeHtml);
			//初始化库位
			var kwHtml = this_.initKw();
			$("#kw",$("#"+this_.id)).empty();
			$("#kw",$("#"+this_.id)).append(kwHtml);
			//加载列表
			if(this_.param.storeIds != null && this_.param.storeIds.length > 0){
				this_.goPage();
			}
			
			$("#transfer_open_alert").modal("show");
		},
		goPage : function(){
			var this_ = this;
			var storeIds = this_.param.storeIds;
			AjaxUtil.ajax({
				   url:basePath+"/aerialmaterial/stock/selectByIds",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(storeIds),
				   success:function(data){
					   finishWait();
					   if(data!= null && data.length > 0 ){
						   this_.data = data;
						   this_.appendContentHtml(data);
					   } else {
						  $("#"+this_.tableBody,$("#"+this_.id)).empty();
						  $("#"+this_.tableBody,$("#"+this_.id)).append("<tr><td colspan=\"9\"  class='text-center'>暂无数据 No data.</td></tr>");
					   }
			     }
			   });
			
			
		},
		appendContentHtml : function(list){
			var this_ =this;
			var htmlContent = '';
			$("#"+this_.tableBody,$("#"+this_.id)).empty();
			$.each(list,function(index,row){
					htmlContent = '';
				    var kysl = parseInt(row.kcsl)-parseInt(row.djsl);
					htmlContent += "<tr id='"+row.id+"' sl='"+row.kcsl+"'>";  
					htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
					htmlContent += "<button class='line6 line6-btn' type='button' onclick='transfer_open.deletRow(this)'>"; 
					htmlContent += "<i class='icon-minus cursor-pointer color-blue cursor-pointer'></i>";
					htmlContent += "</button>";
					htmlContent += "</td>";
					
					htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
					htmlContent += "<p class='margin-bottom-0'>"+StringUtil.escapeStr(row.bjh)+"</p>";
					htmlContent += "<p class='margin-bottom-0'>"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"</p>";
					htmlContent += "</td>";
					
					htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
					htmlContent += "<p class='margin-bottom-0'>"+StringUtil.escapeStr(row.pch)+"</p>";
					htmlContent += "<p class='margin-bottom-0'>"+StringUtil.escapeStr(row.sn)+"</p>";
					htmlContent += "</td>";
					
					htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
					htmlContent += "<p class='margin-bottom-0'>"+kysl+"/"+StringUtil.escapeStr(row.kcsl)+" "+StringUtil.escapeStr(row.jldw)+"</p>";
					htmlContent += "</td>";
					
					htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
					//if(row.djsl && row.djsl > 0){
						htmlContent +="<input type='checkbox' onchange='transfer_open.checkChange(this,"+row.djsl+","+kysl+")' />";
					//}else{
					//	htmlContent +="<input type='checkbox'  disabled='disabled' />";
					//}
					htmlContent += "</td>";
					
					htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
					htmlContent += "<div class='input-group col-xs-12'>";
					htmlContent +="<input name='ycsl' value="+kysl+" onkeyup='transfer_open.slChange(this,"+kysl+")' type='text' class='form-control'/>";
					htmlContent +='<span id="" class="input-group-addon" style="border:0px;background:none;padding-left:8px;">';
					htmlContent +=StringUtil.escapeStr(row.jldw);
					htmlContent +='</span>';
					htmlContent +='</div>';
					htmlContent += "</td>";
					htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
					htmlContent +="<select onchange='transfer_open.td_ckChange(this)' name='td_ck' type='text' class='form-control'>";
					htmlContent += this_.initCk(row.ckid);
					htmlContent +="</select>";
					htmlContent += "</td>";
					
					htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
					htmlContent +="<select name='td_kw' type='text' class='form-control selectpicker' data-live-search='true'  data-size='10' data-container='#transfer_open_alert'>";
					
					htmlContent +="</select>";
					htmlContent += "</td>";
					
					htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
					htmlContent += StringUtil.escapeStr(row.ckmc) +" "+StringUtil.escapeStr(row.kwh);
					htmlContent += "</td>";
				    htmlContent += "</tr>";
				    $("#"+this_.tableBody,$("#"+this_.id)).append(htmlContent);
				    this_.appendKw(row.ckid,row.kwh,index);
			    
			});
			
		},
		appendKw : function(ckid,kwh,index){
			var this_ = this;
			var option = this_.initKw(ckid,kwh);
			$("#"+this_.tableBody,$("#"+this_.id)).find("tr").eq(index).find("select[name='td_kw']").empty();
			$("#"+this_.tableBody,$("#"+this_.id)).find("tr").eq(index).find("select[name='td_kw']").append(option);
			$("#"+this_.tableBody,$("#"+this_.id)).find("tr").eq(index).find("select[name='td_kw']").selectpicker("refresh");
		},
		/**
		 * 整数验证
		 */
		 backspace : function(obj, reg, replace){
			var value = obj.val();
			var count = this.getCount("", value);
			if (replace) {
				value = value.replace(/：/g, ":");
			}
			if (count != 1) {
				while (!(reg.test(value)) && value.length > 0) {
					value = value.substr(0, value.length - 1);
				}

			}

			
			obj.val(value);
		},
		 getCount : function(separtor,str){
			var count=0;
			for(var i=0;i<str.length;i++){
				if(separtor==str[i])count++;
				
			}
			return count;
		},
		td_ckChange : function(obj){
			var this_ = this;
			var ckid = $(obj).val();
			var kwh = $(obj).attr("kwh");
			var kwHtml = this_.initKw(ckid,kwh);
			$(obj).parent().next().find("select[name='td_kw']").empty();
			$(obj).parent().next().find("select[name='td_kw']").append(kwHtml);
			$(obj).parent().next().find("select[name='td_kw']").selectpicker("refresh");
		},
		/**
		 * 批量修改仓库
		 */
		selectCkChange : function(obj){
			var this_ = this;
			var ck = $(obj).val();
			//拼接库位
			this_.initKw();
			var kwHtml = this_.initKw();
			$("#kw",$("#"+this_.id)).empty();
			$("#kw",$("#"+this_.id)).append(kwHtml);
			$("#kw").selectpicker("refresh");
			kwHtml.replace("<option value=''>选择库位</option>", "");
			if(ck){
				//如果不为空，给所有数据赋值
				$("#"+this_.tableBody,$("#"+this_.id)).find("select[name='td_ck']").val(ck);
				$("#"+this_.tableBody,$("#"+this_.id)).find("select[name='td_kw']").empty();
				$("#"+this_.tableBody,$("#"+this_.id)).find("select[name='td_kw']").append(kwHtml);
				$("#"+this_.tableBody,$("#"+this_.id)).find("select[name='td_kw']").selectpicker("refresh");
				
			}
			
		},
		/**
		 * 批量修改库位
		 */
		selectKwChange : function(obj){
			var this_ = this;
			var kw = $(obj).find("option:selected").val();
			var kwhtml =$(obj).html();
			//this_.initKw($("#ck",$("#"+this_.id)).val(),kw);
			//alert(kwhtml);
			if(kw){
				$("#"+this_.tableBody,$("#"+this_.id)).find("select[name='td_kw']").empty();
				$("#"+this_.tableBody,$("#"+this_.id)).find("select[name='td_kw']").append(kwhtml);
				//如果不为空，给所有数据赋值
				$("#"+this_.tableBody,$("#"+this_.id)).find("select[name='td_kw']").val(kw);
				$("#"+this_.tableBody,$("#"+this_.id)).find("select[name='td_ck']").val($("#ck",$("#"+this_.id)).val());
				$("#"+this_.tableBody,$("#"+this_.id)).find("select[name='td_kw']").selectpicker("refresh");
			}
			
		},
		/**
		 * 冻结数量
		 */
		checkChange : function(obj,djsl,kcsl){
			if($(obj).is(":checked")){
				$(obj).parent().next().find("input").val(djsl);
				$(obj).parent().next().find("input").attr("disabled","disabled");
			}else{
				$(obj).parent().next().find("input").val(kcsl);
				$(obj).parent().next().find("input").attr("disabled",false);
			}
		},
		/**
		 * 库存数量
		 */
		slChange:function(obj,kcsl){
			var sl = $(obj).val();
			this.backspace($(obj), cycleReg);
			kcsl = kcsl?kcsl:0;
			if(sl > kcsl || sl <= 0){
				$(obj).val(kcsl);
			}
		},
		/**
		 * 删除行
		 */
		deletRow : function(obj){
			var this_ = this;
			var index = $("#"+this_.tableBody,$("#"+this_.id)).find("tr").length;
			if(index <= 1){
				AlertUtil.showErrorMessage("请至少保留一条数据");
				return false;
			}
			$(obj).parent().parent("tr").remove();
		},
		/**
		 * 清空modal
		 * 
		 */
		closeEmpty : function(){
			var this_ = this;
			$("#yksm").val("");
			$("#"+this_.tableBody,$("#"+this_.id)).empty();
			$("#"+this_.tableBody,$("#"+this_.id)).html("<tr><td class='text-center' colspan=\"9\">暂无数据 No data.</td></tr>");
		},
		/**
		 * 初始化仓库
		 */
		initCk : function (ckid){
			var this_ = this;
			var dprtcode = this_.param.dprtcode;
			var storeHtml = "<option value=''>选择仓库</option>";
			if(ckid){
				storeHtml = "";
			}
			$.each(userStoreList, function(index, row){
				if(dprtcode == row.dprtcode){
					if(ckid){
						if(ckid == row.id){
							storeHtml += "<option  selected = 'selected' value=\""+row.id +"\" ckh=\""+StringUtil.escapeStr(row.ckh)+"\" ckmc=\""+StringUtil.escapeStr(row.ckmc)+"\" cklb=\""+StringUtil.escapeStr(row.cklb)+"\">"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+"</option>";
						}else{
							storeHtml += "<option value=\""+row.id +"\" ckh=\""+StringUtil.escapeStr(row.ckh)+"\" ckmc=\""+StringUtil.escapeStr(row.ckmc)+"\" cklb=\""+StringUtil.escapeStr(row.cklb)+"\">"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+"</option>";
						}
					}else{
						storeHtml += "<option value=\""+row.id +"\" ckh=\""+StringUtil.escapeStr(row.ckh)+"\" ckmc=\""+StringUtil.escapeStr(row.ckmc)+"\" cklb=\""+StringUtil.escapeStr(row.cklb)+"\">"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+"</option>";
					}
				}
			});
			return storeHtml;
		},
		/**
		 * 初始化库位
		 */
		initKw : function(ckid,kwh){
			var this_ = this;
			var option = "";
			if(!ckid){
				ckid = $("#ck",$("#"+this_.id)).val();
			}
			if(!ckid){
				option = "<option value=''>请选择仓库</option>";
				return option;
			}
			$.each(userStoreList, function(index, row){
				if(row.id == ckid){
					for(var i = 0 ; i < row.storageList.length ; i++){
				 		var storage = row.storageList[i];
				 		if(kwh == storage.kwh){
				 			option += '<option selected = "selected" value="'+storage.id +'" kwh="'+StringUtil.escapeStr(storage.kwh)+'">'+StringUtil.escapeStr(storage.kwh)+'</option>';
				 		}else{
				 			option += '<option value="'+storage.id +'" kwh="'+StringUtil.escapeStr(storage.kwh)+'">'+StringUtil.escapeStr(storage.kwh)+'</option>';
				 		}
				 	}
				}
			});
			return option;
		},
		/**
		 * 添加
		 */
		save : function(){
			var this_ = this;
			var obj = {};
			var list = [];
			var row = {};
			var fl = false;
			var message = '';
			$("#"+this_.tableBody,$("#"+this_.id)).find("tr").each(function(){
				row = {};
				row.id = $(this).attr("id");
				row.zt = $(this).find("input[type='checkbox']").is(":checked")?1:2;
				row.kcsl = $(this).find("input[name='ycsl']").val();
				row.ckid = $(this).find("select[name='td_ck']").val();
				row.ckh = $(this).find("select[name='td_ck'] option:checked").attr("ckh");
				row.ckmc = $(this).find("select[name='td_ck'] option:checked").attr("ckmc");
				row.kwid = $(this).find("select[name='td_kw']").val();
				row.kwh = $(this).find("select[name='td_kw'] option:checked").attr("kwh");
				var sl = $(this).attr("sl");
				
				if(!cycleReg.test(row.kcsl)){
					message = "数量只能输入正整数";
					fl = true;
					return false;
				};
				if(parseInt(sl) < parseInt(row.kcsl)){
					message = "移出数量不能大于库存数量";
					fl = true;
					return false;
				}
				if(parseInt(row.kcsl) <= 0){
					message = "移出数量不能小于等于0";
					fl = true;
					return false;
				}
				list.push(row);
			});
			if(fl){
				AlertUtil.showModalFooterMessage(message);
				return false;
			}
			obj.czsm = $("#yksm",$("#"+this_.id)).val();
			obj.stockList = list;
			AjaxUtil.ajax({
				   url:basePath+"/material/store/saveYk",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(obj),
				   success:function(data){
					   finishWait();
					   $("#transfer_open_alert").modal("hide");
					   AlertUtil.showErrorMessage("操作成功");
					   transfer_main.init();
			     }
			   });
			
		}

}

