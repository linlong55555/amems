$(document).ready(function(){

	//加载岗位信息
	trainingTesting_main.init();
});
//左边数据对象
 trainingTesting_main = {
		 id:'left_div',
		 tableId : 'trainingTesting_main_table',
		 tbodyId : 'trainingTesting_main_tbody',
		 data:[],
		 gwids:[],
		 param: {
				callback:function(){},
				dprtcode:userJgdm//默认登录人当前机构代码
			},
		init: function(){
			var this_ = this;
			this_.goPage();
			trainingTesting_list.appendNoData();
			this_.gwids = [];
			this_.appendNoData();
		},
		gwChange : function(){
			var this_ = this;
			if(this_.data != null && this_.data.length > 0){
				this_.refreshAutoComplete(this_.data);
			}
		},
		/**
		 * 初始化输入框自动完成控件
		 */
		 refreshAutoComplete : function(data){
			 var this_ = this;
			$('#gwText_search').typeahead({
				displayText : function(item){
					return item.dgbh;
				},
			    source: function (query, process) {
			    	process(data);
			    },

			    highlighter: function (item) {
		          return "&nbsp;&nbsp;" + item + "&nbsp;&nbsp;";
			    },
			    updater: function (item) {
			    	var ids = this_.gwids;
			    	var fl = true;
			    	if(ids != null && ids.length > 0){
			    		$.each(ids,function(index,gwid){
			    			if(gwid == item.id){
			    				fl = false;
			    			}
			    		});
			    	}
			    	if(fl){
			    		this_.appendData(item);
			    		this_.gwids.push(item.id);
			    		$("#gwText_search").val('');
			    		//加载检测结果
						trainingTesting_list.init({
							gwids:this_.gwids,
							dprtcode:userJgdm
						});
			    	}
			    	//return item;
		      	}
			});
		},
		appendNoData : function(){
			var this_ = this;
			$("#"+this_.tbodyId).empty();
	    	$("#"+this_.tbodyId).append("<tr class='text-center'><td colspan=\"3\">暂无数据 No data.</td></tr>");
		},
		remove : function(rowid){
			var this_ = this;
			for (var i = 0; i < this_.gwids.length; i++) {
	            if (this_.gwids[i] == rowid) {
	            	this_.gwids.splice(i, 1);
	            }
	        }
		}, 
		invalid : function(id){
			var this_ = this;
			this_.remove(id);
			/*this_.gwids = $.grep(this_.gwids, function(value) {
				 return value != id;
				});*/
			$("#"+id).remove();
			if($("#"+this_.tbodyId).find("tr").eq(0).find("td").length == 0){
				this_.appendNoData();
				trainingTesting_list.appendNoData();
				trainingTesting_list.data=[];
			}else{
				//加载检测结果
				trainingTesting_list.init({
					gwids:this_.gwids,
					dprtcode:userJgdm
				});
			}
		},
		appendData : function(row){
			var this_ = this;
			var htmlContent = '';
				htmlContent += "<tr id='"+row.id+"'>";
				htmlContent += "<td class='text-center'>";
				htmlContent += "<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer ' onClick=\"trainingTesting_main.invalid('"
		    		+ row.id + "')\" title='删除 Delete '></i>&nbsp;&nbsp;";
				htmlContent += "</td>";
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.dgbh)+"'>"+StringUtil.escapeStr(row.dgbh)+"</td>";
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.dgmc)+"'>"+StringUtil.escapeStr(row.dgmc)+"</td>";
				htmlContent += "</tr>"; 
				if($("#"+this_.tbodyId).find("tr").eq(0).find("td").length <= 1){
					$("#"+this_.tbodyId).empty();
				}
		    	$("#"+this_.tbodyId).append(htmlContent);
		},
		goPage : function(){
			var this_ = this;
			startWait();
			var obj ={};
			obj.dprtcode  = this_.param.dprtcode;
			AjaxUtil.ajax({
				   url:basePath+"/training/testing/main",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(obj),
				   success:function(data){
				    finishWait();
				    if(data.rows.length>0){
				    	//this_.appendHtml(data.rows);
				    	this_.data = data.rows;
				    }else{
				    	this_.appendNoData();
				    }
			     }
			   });
		},
		appendHtml : function(list){
			var this_ = this;
			var htmlContent = '';
				$.each(list,function(index,row){
					htmlContent += "<tr onclick=SelectUtil.clickRow("+index+",'trainingTesting_main_tbody','trainingTesting_row')>";
					htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;'><input value='"+row.id+"' type='checkbox' name='trainingTesting_row' index="+index+"  onclick=selectworkcard(event,"+index+",'trainingTesting_table_list','trainingTesting_row',this) /></td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.dgbh)+"'>"+StringUtil.escapeStr(row.dgbh)+"</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.dgmc)+"'>"+StringUtil.escapeStr(row.dgmc)+"</td>";
					htmlContent += "</tr>"; 
			 });
				$("#"+this_.tbodyId).empty();
		    	$("#"+this_.tbodyId).append(htmlContent);
			
		},
		//打开人员列表对话框
		 openPersonelWin : function() {
			var this_ = this;
			Personel_Tree_Multi_Modal.show({
				checkUserList:null,//原值，在弹窗中回显
				multi:false,
				dprtcode:userJgdm,//
				callback: function(data){//回调函数
					if(data != null && data != '' && data.length > 0 ){
						$("#ryText_search").val(data[0].displayName);
						$("#ryid_search").val(data[0].id);
						if(this_.gwids != null && this_.gwids.length > 0){
							trainingTesting_list.init({
								gwids:this_.gwids,
								dprtcode:userJgdm
							});
						}
					}else{
						$("#ryText_search").val('');
						$("#ryid_search").val('');
					}
				}
			});
		},
		//打开岗位列表对话框
		 openBusiness : function(){
			 var this_ = this;
			 business_main.show({
				ids:this_.gwids,
				dprtcode:this_.param.dprtcode,
				callback: function(data){//回调函数
					if(data.length > 0){
						$.each(data,function(index,row){
							this_.gwids.push(row.id);
							this_.appendData(row);
						});
						//加载检测结果
						trainingTesting_list.init({
							gwids:this_.gwids,
							dprtcode:userJgdm
						});
					}
					
				}
			});
		},
		search : function(){
			var this_ = this;
			if(this_.gwids != null && this_.gwids.length > 0){
				trainingTesting_list.init({
					gwids:this_.gwids,
					dprtcode:userJgdm
				});
			}else{
				AlertUtil.showErrorMessage("请至少选择一条岗位数据,再进行搜索操作");
			}
		}
 }
//右边数据对象
 trainingTesting_list = {
		 id:'right_div',
		 tableId : 'trainingTesting_list_table',
		 tbodyId : 'trainingTesting_list_tbody',
		 data:[],
		 param: {
				callback:function(){},
				dprtcode:userJgdm,//默认登录人当前机构代码
				gwids:[],
				wxdaid:''
			},
		init: function(param){
			if(param){
				$.extend(true,this.param, param);
			}
			var this_ = this;
			//默认不勾选
			//$("#isAll").attr("checked","checked");
			this_.goPage();
		},
		goPage : function(){
			var this_ = this;
			startWait();
			var obj ={};
			var paramsMap ={};
			paramsMap.wxrydaid  =$("#ryid_search").val();
			paramsMap.ids  = this_.param.gwids;
			obj.paramsMap = paramsMap;
			AjaxUtil.ajax({
				   url:basePath+"/training/testing/list",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(obj),
				   success:function(data){
				    finishWait();
				    if(data.rows.length>0){
				    	this_.data = data.rows;
				    	if($("#isAll").attr("checked")){
				    		this_.appendHtmlHide(data.rows);
				    	}else{
				    		this_.appendHtmlShow(data.rows);
				    	}
				    }else{
				    	this_.appendNoData();
				    }
			     }
			   });
		},
		isAllChange : function(){
			var this_ = this;
			if(this_.data && this_.data.length> 0){
				if($("#isAll").attr("checked")){
					this_.appendHtmlHide(this_.data);
				}else{
					this_.appendHtmlShow(this_.data);
				}
			}else{
				this_.appendNoData();
			}
			if($("#"+this_.tbodyId).find("tr").eq(0).find("td").length == 0){
				this_.appendNoData();
			}
		},
		appendNoData : function(){
			var this_ = this;
			$("#"+this_.tbodyId).empty();
	    	$("#"+this_.tbodyId).append("<tr class='text-center'><td colspan=\"4\">暂无数据 No data.</td></tr>");
		},
		appendHtmlShow : function(list){
			var this_ = this;
			var htmlContent = '';
				$.each(list,function(index,row){
					htmlContent += "<tr>";
					htmlContent += "<td class='text-left' title-'"+StringUtil.escapeStr(row.dgbh)+" "+StringUtil.escapeStr(row.dgmc)+"'>"+StringUtil.escapeStr(row.dgbh)+" "+StringUtil.escapeStr(row.dgmc)+"</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.kcbh:'')+"'>"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.kcbh:'')+"</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.kcmc:'')+"'>"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.kcmc:'')+"</td>";
					if(row.paramsMap && row.paramsMap.pxqk ==1){
						htmlContent += "<td class='text-center' ><i class='icon-ok-sign color-blue' title='查看 View'  onClick=\"trainingTesting_list.viewPxqk('"+row.paramsMap.kcbh+"')\" /></td>";
					}else{
						htmlContent += "<td class='text-center' ><i class=' icon-remove-sign' /></td>";
					}
					htmlContent += "</tr>"; 
			 });
				$("#"+this_.tbodyId).empty();
		    	$("#"+this_.tbodyId).append(htmlContent);
			
		},
		viewPxqk : function(kcbh){
			 var this_ = this;
			 trainingRecords_main.show({
				wxrydaid:$("#ryid_search").val(),
				wxry:$("#ryText_search").val(),
				kcbm:kcbh,
				dprtcode:this_.param.dprtcode
			});
		},
		appendHtmlHide : function(list){
			var this_ = this;
			var htmlContent = '';
				$.each(list,function(index,row){
					if(row.paramsMap && row.paramsMap.pxqk !=1){
						htmlContent += "<tr>";
						htmlContent += "<td class='text-left' title-'"+StringUtil.escapeStr(row.dgbh)+" "+StringUtil.escapeStr(row.dgmc)+"'>"+StringUtil.escapeStr(row.dgbh)+" "+StringUtil.escapeStr(row.dgmc)+"</td>";
						htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.kcbh:'')+"'>"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.kcbh:'')+"</td>";
						htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.kcmc:'')+"'>"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.kcmc:'')+"</td>";
						htmlContent += "<td class='text-center' ><i class=' icon-remove-sign' /></td>";
						htmlContent += "</tr>"; 
					}
			 });
				$("#"+this_.tbodyId).empty();
		    	$("#"+this_.tbodyId).append(htmlContent);
		    	
		    	if($("#"+this_.tbodyId).find("tr").eq(0).find("td").length == 0){
					this_.appendNoData();
				}
		    	
			
		}
 }
 
 function customResizeHeight(bodyHeight, tableHeight){
	 // 关键字高度
	 
	var leftHeight=$("#left_div").outerHeight();
	var leftHeader=$("#left_div .panel-heading").outerHeight();
	var leftRowHeight=$("#left_div .row-height").outerHeight();
	var leftTable=leftHeight-leftRowHeight-leftHeader-35;
	$("#left_div").find("table").parent().css({"height":(leftTable)+"px","overflow":"auto"})
	var panelHeader=$("#right_div .panel-heading").outerHeight();
	 if($("#left_div .panel-heading").outerHeight()<35){
		 leftHeight=leftHeight-35;
	 }
	var tableHeight=leftHeight-43-panelHeader;
	$("#right_div").find("table").parent().css({"height":tableHeight+"px","overflow":"auto"});
 }
//左右收缩的小图标
	function toggleIcon(obj){
		var i = $(obj);
		if(i.hasClass("icon-caret-left")){
			i.removeClass("icon-caret-left").addClass("icon-caret-right");
			$("#left_div").hide();
			$("#right_div").removeClass("col-sm-8 col-xs-12").addClass("col-sm-12 col-xs-12");
		}else{
			i.removeClass("icon-caret-right").addClass("icon-caret-left");
			$("#left_div").show();
			$("#right_div").removeClass("col-sm-12 col-xs-12").addClass("col-sm-8 col-xs-12");
		}
		App.resizeHeight();
	}

	function selectworkcard(e,index,id,name,obj){
		 e = e || window.event;  
		    if(e.stopPropagation) { //W3C阻止冒泡方法  
		        e.stopPropagation();  
		    } else {  
		        e.cancelBubble = true; //IE阻止冒泡方法  
		    }
		    var $checkbox1 = $("#" + id + " :checkbox[name='" + name + "']:eq("
	 				+ index + ")");
	 		var $checkbox2 = $(".sticky-col :checkbox[name='" + name + "']:eq("
	 				+ index + ")");
		    if(!$(obj).parents("table").hasClass("sticky-col")){
		    	var checked = $checkbox1.is(":checked");
		 		$checkbox1.attr("checked", checked);
		 		$checkbox2.attr("checked", checked);	
		    }else{
		    	var checked = $checkbox2.is(":checked");
		 		$checkbox1.attr("checked", checked);
		 		$checkbox2.attr("checked", checked);
		    }
		   
	}