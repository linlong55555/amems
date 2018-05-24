
    var planes;
	$(function(){
		 initPlanes();
		 changeOrganization(true);
		 //回车搜索
		 $(document).keydown(function(event){
			 if(event.keyCode==13){
					if($("#keyword_search").is(":focus")){
						refreshTree();      
					}
				}
		 });
		 
		 Navigation(menuCode);
		 
		 initNav();
		 goPage_tableView(1,"auto","desc");
		 initDatePick();
		 
		 initValidate();
		

		// 维护子部件关联模态框拖动
	    $("#component_remoteModal").draggable({   
	        cursor: 'move',   
	        refreshPositions: false  
	    });  
	    
	    
	    
	    // 初始化导入
	    initImport();
		
		/**
		 * 自动带入航次主数据的数据
		 */
		$("#jh").blur(function(){
			AjaxUtil.ajax({
				   url:basePath+"/material/material/selectByBjhAndDprt",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify({
					   bjh : $.trim($("#jh").val()),
					   dprtcode : $.trim($("#dprtcode_head").val())
				   }),
				   success:function(data){
					   if(data){
						   $("#zwmc").val(data.zwms);
						   $("#ywmc").val(data.ywms);
						   $("#cjjh").val(data.cjjh);
						   $("#zjh").val(data.zjh);
						   if(data.fixChapter){
							   $("#zjh_show").val($.trim((data.fixChapter.zjh||"")+"  "+ (data.fixChapter.zwms||"")));
						   }
					   }
			      }
		    }); 
		});
	    
	    //初始化日志功能
		logModal.init({code:'B_S_001',diffCallback:function(data){
			// 当时控件设置当前版本无数据时，复制上一版本数据到当前版本
			if(($("#B_S_00101_REC_CURRENT tbody>tr:eq(0)>td:eq(0)").html()||'&nbsp;') == '&nbsp;'){
				$("#B_S_00101_REC_CURRENT>tbody").html($("#B_S_00101_REC_LAST>tbody>tr").clone());
			}
			// 当特殊情况设置当前版本无数据时，复制上一版本数据到当前版本
			if(($("#B_S_00102_REC_CURRENT tbody>tr:eq(0)>td:eq(0)").html()||'&nbsp;') == '&nbsp;'){
				$("#B_S_00102_REC_CURRENT>tbody").html($("#B_S_00102_REC_LAST>tbody>tr").clone());
			}
			var hasData = true;
			$(data.slaves).each(function(i,slave){
				if(slave.diffSqlId = 'queryDiff4BS00103'){
					$(slave.rows).each(function(j,row){
						if(row.DATA_VERSION = 'CURRENT' && row.REC_XGLX == 3){
							hasData = false;
						}
					});
				}
			});
			if(!hasData){
				$("#B_S_00103_REC_CURRENT>tbody>tr").remove();
			}else{
				
				if(($("#B_S_00103_REC_CURRENT tbody>tr:eq(0)>td:eq(0)").html()||'&nbsp;') == '&nbsp;'){
					$("#B_S_00103_REC_CURRENT>tbody").html($("#B_S_00103_REC_LAST>tbody>tr").clone());
				}
			}
		}});
	 });
	
	function initValidate(){
		 var validatorForm =  $('#componentForm').bootstrapValidator({
			 	excluded: [':disabled'],
		        message: '数据不合法',
		        feedbackIcons: {
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	jh: {
		                validators: {
		                	regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    },
		                    notEmpty: {
		                        message: '件号不能为空'
		                    }
		                }
		            },
		            zjh: {
		                validators: {
		                    notEmpty: {
		                        message: 'ATA章节号不能为空'
		                    }
		                },
	                	callback: {
	                        message: '时控件和定检件的序列号为必填项',
	                        callback: function(value, validator) {
	                			var zjh = $("#zjh").val();
	                			if(zjh){
	                				return false;
	                			}
	                            return true;
	                        }
	                    }
		            },
		            cjjh: {
		                validators: {
		                	regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            nbsbm: {
		                validators: {
		                	regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            zjsl: {
		            	validators: {
		                	regexp: {
		                        regexp: /^([1-9]|([1-9]\d+))$/,
		                        message: '只能输入大于1的正整数'
		                    }
		                }
		            },
		            xlh: {
		                validators: {
		                	regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    },
		                	callback: {
		                        message: '时控件和定检件的序列号为必填项',
		                        callback: function(value, validator) {
		                        	var kzlx = $("#kzlx").val();
		                			var isDj = $("#isDj").val();
		                			var xlh = $("#xlh").val();
		                			if(!xlh && (kzlx == 1 || kzlx == 2 || isDj == 1)){
		                				return false;
		                			}
		                            return true;
		                        }
		                    }
		                }
		            },
		            /*ywmc: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },	*/
		            xkzh: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },	
		            shzh: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },	
		            pch: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            llkbh: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            azjldh: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            ccjldh: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		        }
		    });
	}
	 
	 /**
	  * 装机清单状态
	  */
	 var ztMap = {
			 1 : "装上",
			 2 : "卸下",
			 3 : "作废"
	 };
	 
	 function reValidateXlh(){
		 manualValidate("xlh");
	 }
	 
	 /**
	  * 手工重新验证
	  * @param column
	  */
	 function manualValidate(column){
		 $('#componentForm').data('bootstrapValidator')  
         .updateStatus(column, 'NOT_VALIDATED',null)  
         .validateField(column);
	 }
	 
	 function resetValidate(column){
		 $('#componentForm').data('bootstrapValidator')  
         .updateStatus(column, 'NOT_VALIDATED',null);  
	 }
	 
	 /**
	  * 初始化时间控件
	  */
	 function initDatePick(){
		$('.date-picker').datepicker({
			 autoclose: true,
			 clearBtn:true
		});
	 }
	 
	 /**
	  * 带条件搜索的翻页
	  */
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var searchParam = gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/productionplan/loadingList/queryNoChildList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   $("#chooseList").empty();
			   if(data.total >0){
				   appendContentHtml(data.rows, searchParam.keyword);
				   new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(pageNumber,sortType,sequence){
							AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
						}
					});
				   // 替换null或undefined
				   FormatUtil.removeNullOrUndefined();
				   // 标记关键字
				   signByKeyword($("#subcomponent_search").val(),[2,3,4,5,6,7],"#loadingList tr td");
			   } else {
				  $("#loadingList").empty();
				  $("#pagination").empty();
				  $("#loadingList").append("<tr><td colspan=\"11\">暂无数据 No data.</td></tr>");
			   }
	      }
	    }); 
		
	 }

	 /**
	  * 封装搜索条件
	  * @returns
	  */
	 function gatherSearchParam(){
		 var searchParam = {};
		 var treeObj = $.fn.zTree.getZTreeObj("loadingList_tree");
		 searchParam.keyword = $.trim($("#subcomponent_search").val());
		 searchParam.id = treeObj.getSelectedNodes()[0].id;
		 searchParam.fjzch = $("#fjzch_search").val();
		 searchParam.wz =  $.trim($("#wz_main").val());
		 searchParam.azjldh =  $.trim($("#azjldh_main").val());
		 searchParam.ccjldh =  $.trim($("#ccjldh_main").val());
		 searchParam.llklx =  $.trim($("#llklx_main").val());
		 searchParam.llkbh =  $.trim($("#llkbh_main").val());
		 searchParam.bz =  $.trim($("#bz_main").val());
		 searchParam.kzlx =  $.trim($("#kzlx_main").val());
		 searchParam.isDj =  $.trim($("#isDj_main").val());
		 searchParam.dprtcode =  $.trim($("#dprtcode_head").val());
		 var azrq = $.trim($("#azrq_main").val());
		 if(azrq != ''){
			 searchParam.beginAzrq = azrq.substring(0,10);
			 searchParam.endAzrq = azrq.substring(13,23);
		 }
		 var scrq = $.trim($("#scrq_main").val());
		 if(scrq != ''){
			 searchParam.beginScrq = scrq.substring(0,10);
			 searchParam.endScrq = scrq.substring(13,23);
		 }
		 var ccrq = $.trim($("#ccrq_main").val());
		 if(ccrq != ''){
			 searchParam.beginCcrq = ccrq.substring(0,10);
			 searchParam.endCcrq = ccrq.substring(13,23);
		 }
		 return searchParam;
	 }
	 
	 /**
	  * 拼接table数据
	  * @param list
	  */
	 function appendContentHtml(list, keyword){
		   var downList = [];
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   htmlContent += ["<tr bgcolor='"+(index % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
			                   "<td>"+(($.inArray(row.id, downList) == -1)?
			                		   "<i class='icon-arrow-down' onclick='goDown(this)' style='cursor:pointer' ></i>":"")+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr($.trim((row.zjh || "") + "  "+ (row.zjhms || "")))+"'>"+StringUtil.escapeStr($.trim((row.zjh || "") + "  "+ (row.zjhms || "")))+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.ywmc)+"'>"+StringUtil.escapeStr(row.ywmc)+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.zwmc)+"'>"+StringUtil.escapeStr(row.zwmc)+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.jh)+"'>"+StringUtil.escapeStr(row.jh)+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.xlh)+"'>"+StringUtil.escapeStr(row.xlh)+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.nbsbm)+"'>"+StringUtil.escapeStr(row.nbsbm)+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.cjjh)+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>",
			                   "<td style='text-align: right' title='"+row.zjsl+"'>"+row.zjsl+"</td>",
			                   "<td>"+DicAndEnumUtil.getEnumName('planeComponentPositionEnum',row.wz)+"</td>",
			                   "<input type='hidden' name='id' value='"+row.id+"'>",
			                   "</tr>"
			                   ].join("");
		   });
		   $("#loadingList").empty();
		   $("#loadingList").html(htmlContent);
		 
	 }
	 
	 
	 
	/**
	 * 跳转至指定页数
	 * @param pageNumber	当前页码
	 * @param sortType	排序字段
	 * @param sequence	排序规则 
	 */
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
	
	/**
	 * ajax获取子部件
	 */
	function ajaxComponent(){
		var msg = validatePlaneZt();
		if(msg){
			AlertUtil.showErrorMessage(msg);
			return false;
		}
		var treeObj = $.fn.zTree.getZTreeObj("loadingList_tree");
		var nodes = treeObj.getSelectedNodes();
		if(nodes.length==0){
			AlertUtil.showErrorMessage("请先选择一个部件");
			return;
		}else if(nodes[0].cj >= 2){
			AlertUtil.showErrorMessage("二级部件无法添加子部件");
			return;
		}
		if(nodes[0].id == "0"){
			AlertUtil.showErrorMessage("该部件无法维护子部件关联");
			return;
		}
		$("#jh_subModal").val(nodes[0].jh);
		$("#xlh_subModal").val(nodes[0].xlh);
		$("#zwmc_subModal").val(nodes[0].zwmc);
		$("#ywmc_subModal").val(nodes[0].ywmc);
		$('#component_remoteModal').modal('show');
		// 重新刷新待选择列表
		goPage(1,"auto","desc");
	}
	
	/**
	 * 初始化页面sheet功能
	 */
	function initNav(){
		$('.nav.nav-tabs > li > a').click(function(){
			var href=$(this).attr('href');
			$('#tablist a[href="'+href+'"]').tab('show');
			if(href == "#planeLoadingList"){
				refreshTree();
				goPage_tableView(1,"auto","desc");
			}else if(href == "#timeMonitor"){
				refreshTimeMonitor();
			}else if(href == "#fixedMonitor"){
				getfixedMonitorList();
			}
		}); 
	}
	
	/**
	 * 初始化树
	 * @param node 默认选择的节点id
	 * @param hasValue 右侧面板是否有值
	 */
	function initTree(id, hasValue, expandAll){
		startWait();
		AjaxUtil.ajax({
			   url:basePath+"/productionplan/loadingList/queryEditableTree", 
			   type: "post",
			   dataType:"json",
			   contentType:"application/json;charset=utf-8",
			   data:JSON.stringify({
				   fjzch:$.trim($("#fjzch_search").val()),
				   dprtcode : $.trim($("#dprtcode_head").val()),
				   zt:1,
				   keyword:$.trim($("#keyword_search").val())
			   }),
			   success:function(data){
				   finishWait();
				   var setting = {
						   view: {
							   showIcon: false,
							   addDiyDom: addIcon,
							   fontCss: setFontCss,
							   selectedMulti: false,
							   nameIsHTML: false
						   },
						   check: {enable: false,autoCheckTrigger: true}, 
						   data: {
							   key: {
									name: "displayName"
							   },
							   simpleData: {
								   enable: true,
								   idKey: "id",
								   pIdKey: "pId",
								   rootPId: null
							   }
						   },
						   edit: {
							   drag: {
								   autoExpandTrigger: true,
								   isCopy: false,
								   isMove: true,
								   prev: false,
								   next: false,
								   inner: true
							   },
						   	   enable: true,
						   	   showRemoveBtn: false,
						   	   showRenameBtn: false
						   },
						   callback:{
							   beforeClick : beforeClick,
							   onClick: loadDetail,
							   beforeDrag: checkCanBeginDrag,
							   beforeDrop: checkCanEndDrag,
							   onDrop: changeParent
						   }
				   };
				   var treeObj = $.fn.zTree.init($("#loadingList_tree"), setting, data);
				   var nodes = treeObj.transformToArray(treeObj.getNodes());
				   if(expandAll){
					   treeObj.expandAll(true); 
				   }else{
					   treeObj.expandNode(nodes[0]);
				   }
				   $.each(nodes, function(index, node){
					   node.title = StringUtil.escapeStr(node.name);  
					   treeObj.updateNode(node); 
				   });
				   // 模拟点击节点
				   if(id){
					   treeObj.expandAll(true);
					   // 找到id对应节点
					   var node = treeObj.getNodeByParam("id", id, null);
					   $("#"+node.tId+"_a").click();
				   }else{
					   var root = getRoot();
					   if(root){
						   $("#"+getRoot().tId+"_a").click();
					   }
				   }
				   if(hasValue){
					   resetDetail();
				   }
				   // 重新设定树高度
				   $("#loadingList_tree").height($("#right_div").actual("height")-110);
		      }
		    }); 
	}
	
	/**
	 * 刷新整个树
	 * @param id 默认选中的id
	 */
	function refreshTree(id, hasValue, expandAll){
		if($.fn.zTree.getZTreeObj("loadingList_tree")){
			$.fn.zTree.getZTreeObj("loadingList_tree").destroy();
		}
		initTree(id, hasValue, expandAll);
	}
	
	/**
	 * 添加定检件和时控件图标
	 * @param treeId
	 * @param treeNode
	 */
	function addIcon(treeId, treeNode){
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#diyBtn_"+treeNode.id).length>0) return;
		// 添加时控件图标
		if(treeNode.kzlx == 1 || treeNode.kzlx == 2){
			var kzlx_icon = "<span> </span>"
			+ "<i class='icon-time color-gray' title='时控件/时寿件'></i>&nbsp;";
			aObj.after(kzlx_icon);
		}
		// 添加定检件图标
		if(treeNode.isDj == 1){
			var dj_icon = "<span> </span>"
			+ "<i class='icon-calendar color-gray' title='定检件'></i>&nbsp;";
			aObj.after(dj_icon);
		}
		/*var btn = $("#diyBtn_"+treeNode.id);
		if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});*/
	}
	
	/**
	 * 设置样式
	 * @param treeId
	 * @param treeNode
	 * @returns {___anonymous11007_11024}
	 */
	function setFontCss(treeId, treeNode){
		return {"font-size":"18"}
	}
	
	/**
	 * 获取根节点
	 * @returns
	 */
    function getRoot() {  
       var treeObj = $.fn.zTree.getZTreeObj("loadingList_tree");  
        //返回一个根节点  
       return treeObj.getNodesByFilter(function (node) { return node.cj == 0 }, true);  
    }  
	
	/**
	 * 加载装机清单详情信息
	 * @param event
	 * @param treeId
	 * @param treeNode
	 */
	function loadDetail(event, treeId, treeNode){
		if(treeNode.id == "0"){
			return;
		}
		$("#current_id").val(treeNode.id);
		$("#fjzch").val(treeNode.fjzch);
		$("#fjjx").val(treeNode.fjjx);
		$("#jh").val(treeNode.jh);
		$("#xlh").val(treeNode.xlh);
		$("#zjh").val(treeNode.zjh);
		$("#zjh_show").val((treeNode.zjh||"")+"  "+(treeNode.zjhms||""));
		$("#cjjh").val(treeNode.cjjh);
		$("#zwmc").val(treeNode.zwmc);
		$("#ywmc").val(treeNode.ywmc);
		$("#nbsbm").val(treeNode.nbsbm);
		$("#bjgzjl").val(treeNode.bjgzjl);
		$("#zjsl").val(treeNode.zjsl);
		$("#wz").val(treeNode.wz);
		$("#bz").val(treeNode.bz);
		$("#azjldh").val(treeNode.azjldh);
		$("#scrq").val((treeNode.scrq||"").substr(0,10));
		$("#azrq").val((treeNode.azrq||"").substr(0,10));
		$("#ccrq").val((treeNode.ccrq||"").substr(0,10));
		$("#ccjldh").val(treeNode.ccjldh);
		$("#xkzh").val(treeNode.xkzh);
		$("#shzh").val(treeNode.shzh);
		$("#pch").val(treeNode.pch);
		$("#llkbh").val(treeNode.llkbh);
		// 更新日期控件的默认选择日期为输入框值
		$(".date-picker").datepicker("update");
		if(treeNode.cj==0){
			$("#componentForm").find("input").each(function() {
				$(this).attr("disabled","disabled");
			});
			$("#componentForm").find("textarea").each(function() {
				$(this).attr("disabled","disabled");
			});
			$("#componentForm").find("select").each(function() {
				$(this).attr("disabled","disabled");
			});
			$("#main_insertOrUpdate_btn").hide();
			$("#main_chapter_btn").attr("disabled","disabled");
		}else {
			$("#componentForm").find("input").each(function() {
				$(this).removeAttr("disabled");
			});
			$("#componentForm").find("textarea").each(function() {
				$(this).removeAttr("disabled");
			});
			$("#componentForm").find("select").each(function() {
				$(this).removeAttr("disabled");
			});
			$('#fjjx').attr("disabled","disabled");
			$('#fjzch').attr("disabled","disabled");
			$('#zjh_show').attr("disabled","disabled");
			$("#main_insertOrUpdate_btn").show();
			$("#main_chapter_btn").removeAttr("disabled");
		}
		
		if(treeNode.cj==2){
			$('#wz').attr("disabled","disabled");
		}else if(treeNode.cj==1){
			$('#wz').removeAttr("disabled");
		}
		$("#llklx").val(treeNode.llklx);
		$("#kzlx").val(treeNode.kzlx);
		$("#isDj").val(treeNode.isDj);
		if($("#componentForm").data('bootstrapValidator')){
			$("#componentForm").data('bootstrapValidator').destroy();
			$('#componentForm').data('bootstrapValidator', null);
		}
		limitCount();
		disabledJhAndXlh(treeNode);
        initValidate();
		
	}
	
	/**
	 * 新增装机清单
	 */
	function addLoadingList(){
		var msg = validatePlaneZt();
		if(msg){
			AlertUtil.showErrorMessage(msg);
			return false;
		}
		var parent = $.fn.zTree.getZTreeObj("loadingList_tree").getSelectedNodes()[0];
		if(parent.cj >= 2){
			AlertUtil.showErrorMessage("二级部件无法添加子部件");
			return;
		}
		if(parent.id == "0"){
			AlertUtil.showErrorMessage("该部件无法添加子部件");
			return;
		}
		resetDetail();
	}
	
	/**
	 * 重置装机清单详情信息
	 */
	function resetDetail(){
		var parent = $.fn.zTree.getZTreeObj("loadingList_tree").getSelectedNodes()[0];
		$("#current_id").val("");
		$("#jh").val("");
		$("#xlh").val("");
		$("#zjh").val("");
		$("#zjh_show").val("");
		$("#cjjh").val("");
		$("#zwmc").val("");
		$("#ywmc").val("");
		$("#bjgzjl").val("");
		$("#zjsl").val("1");
		$("#nbsbm").val("");
		$("#xkzh").val("");
		$("#shzh").val("");
		$("#pch").val("");
		$("#llkbh").val("");
		$('#jh').removeAttr("disabled");
		$('#xlh').removeAttr("disabled");
		$('#zjsl').removeAttr("disabled");
		if(parent.cj==0){
			$("#componentForm").find("input").each(function() {
				$(this).removeAttr("disabled");
			});
			$("#componentForm").find("textarea").each(function() {
				$(this).removeAttr("disabled");
			});
			$("#componentForm").find("select").each(function() {
				$(this).removeAttr("disabled");
			});
			$('#fjjx').attr("disabled","disabled");
			$('#fjzch').attr("disabled","disabled");
		}
		
		if(parent.cj==1){
			$("#wz").val(parent.wz);
			$('#wz').attr("disabled","disabled");
		}else{
			$("#wz").val("0");
			$('#wz').removeAttr("disabled");
		}
		
		$("#scrq").val("");
		$("#bz").val("");
		$("#azrq").val("");
		$("#azjldh").val("");
		$("#ccrq").val("");
		$("#ccjldh").val("");
		
		
		$("#llklx").val("1");
		$("#kzlx").val("1");
		$("#isDj").val("1");
		$('#zjh_show').attr("disabled","disabled");
		$("#main_insertOrUpdate_btn").show();
		$("#main_chapter_btn").removeAttr("disabled");
		// 更新日期控件的默认选择日期为输入框值
		$(".date-picker").datepicker("update");
	}
	
	/**
	 * 检查节点是否能被开始拖动
	 * @param treeId
	 * @param treeNodes
	 */
	function checkCanBeginDrag(treeId, treeNodes){
		var msg = validatePlaneZt();
		if(msg){
			AlertUtil.showErrorMessage(msg);
			return false;
		}
		var node = treeNodes[0];
		// 只能拖拽非根节点且无子节点的node
		if(node && node.cj>0 && (!node.children || node.children.length==0) 
				&& node.id != "0" && node.id != "js" && node.id != "zf"
				&& node.id != "yf" && node.id != "ssd" && node.id != "wdg"
				&& node.id != "jc"){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查节点是否能拖动到该节点
	 * @param treeId
	 * @param treeNodes
	 * @param targetNode
	 * @param moveType
	 * @returns {Boolean}
	 */
	function checkCanEndDrag(treeId, treeNodes, targetNode, moveType){
		var node = treeNodes[0];
		// 2级节点无法添加子节点，且父节点必须发生改变
		if(targetNode && targetNode.cj==2 || node.parentTId == targetNode.tId){
			return false;
		}
		// 未关联部件无法添加子节点
		if(targetNode && (targetNode.id=='0'||targetNode.bjlx == 2)){
			return false;
		}
		// 查询相同位置的一级节点
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var existNode = treeObj.getNodesByFilter(function (n){
			return (n.cj == 1 && n.wz == targetNode.wz && n.fjdid != '0' && n.id != targetNode.id);
		}, true); 
		if(existNode && targetNode.wz != 0 && targetNode.cj == 0){
			AlertUtil.showErrorMessage("位置为："+DicAndEnumUtil.getEnumName('planeComponentPositionEnum',targetNode.wz)+"的一级部件已存在！");
			return false;
		}
		return true;
	}
	
	/**
	 * 更改父节点
	 * @param event
	 * @param treeId
	 * @param treeNodes
	 * @param targetNode
	 * @param moveType
	 */
	function changeParent(event, treeId, treeNodes, targetNode, moveType){
		if(!targetNode){
			return;
		}
		var param = [];
		param.push({
			id : treeNodes[0].id,
			cj : parseInt(targetNode.cj)+1,
			jh : treeNodes[0].jh,
			xlh : treeNodes[0].xlh,
			fjdid : targetNode.level == 1 ? targetNode.fjdid : targetNode.id,
			wz : targetNode.wz,
		    fjzch : $("#fjzch_search").val()
		});
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/productionplan/loadingList/changeParent",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(data){
				refreshTree(treeNodes[0].id);
				AlertUtil.showMessage("维护子部件关系成功!");
			}
		});
	}
	
	/**
	 * 选择子部件
	 * @param btn
	 */
	function goDown(btn){
		var tds = $(btn).parent().parent().find("td");
		var id = $(btn).parent().parent().find("input[name='id']").val();
		var count = $("#chooseList").children().length;
	    $("#chooseList").append(["<tr bgcolor='"+(count % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
               "<td><i class='icon-arrow-up' onclick='goUp(this)' style='cursor:pointer' ></i></td>",
               "<td style='text-align: left' title='"+StringUtil.escapeStr($(tds[1]).text())+"'>"+StringUtil.escapeStr($(tds[1]).text())+"</td>",
               "<td style='text-align: left' title='"+StringUtil.escapeStr($(tds[2]).text())+"'>"+StringUtil.escapeStr($(tds[2]).text())+"</td>",
               "<td style='text-align: left' title='"+StringUtil.escapeStr($(tds[3]).text())+"'>"+StringUtil.escapeStr($(tds[3]).text())+"</td>",
               "<td style='text-align: left' title='"+StringUtil.escapeStr($(tds[4]).text())+"'>"+StringUtil.escapeStr($(tds[4]).text())+"</td>",
               "<td style='text-align: left' title='"+StringUtil.escapeStr($(tds[5]).text())+"'>"+StringUtil.escapeStr($(tds[5]).text())+"</td>",
               "<td style='text-align: left' title='"+StringUtil.escapeStr($(tds[6]).text())+"'>"+StringUtil.escapeStr($(tds[6]).text())+"</td>",
               "<td style='text-align: left' title='"+StringUtil.escapeStr($(tds[7]).text())+"'>"+StringUtil.escapeStr($(tds[7]).text())+"</td>",
               "<td style='text-align: left' title='"+StringUtil.escapeStr($(tds[8]).text())+"'>"+StringUtil.escapeStr($(tds[8]).text())+"</td>",
               "<td style='text-align: right' title='"+StringUtil.escapeStr($(tds[9]).text())+"'>"+StringUtil.escapeStr($(tds[9]).text())+"</td>",
               "<td title='"+$(tds[10]).text()+"'>"+$(tds[10]).text()+"</td>",
               "<input type='hidden' name='id' value='"+id+"'>",
               "</tr>"
               ].join(""));
		$(btn).remove();
	}
	
	/**
	 * 还原子部件
	 * @param btn
	 */
	function goUp(btn){
		var id = $(btn).parent().parent().find("input[name='id']").val();
		var input = $("#loadingList>tr>input[value='"+id+"']");
		if(input){
			input.parent().find("td:first").append("<i class='icon-arrow-down' onclick='goDown(this)' style='cursor:pointer' ></i>");
		}
		$(btn).parent().parent().remove();
	}
	

	/**
	 * 
	 * 维护子部件关联
	 */
	function ListChangeParent(){
		startWait($("#component_remoteModal"));
		var treeObj = $.fn.zTree.getZTreeObj("loadingList_tree");
		var node = treeObj.getSelectedNodes()[0];
		
		var params = [];
		$("#chooseList>tr>input[name='id']").each(function(){
	    	params.push({
	    		id : $(this).val(),
				cj : parseInt(node.cj)+1,
				fjdid : node.id,
				wz : node.wz,
				fjzch : $("#fjzch_search").val()
	    	});
		});  
		if(params.length == 0){
			AlertUtil.showErrorMessage("至少选择一个子部件！");
			finishWait($("#component_remoteModal"));
			return false;
		}
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/productionplan/loadingList/changeParent",
			type:"post",
			data:JSON.stringify(params),
			dataType:"json",
			modal:$("#component_remoteModal"),
			contentType: "application/json;charset=utf-8",
			success:function(data){
				$("#component_remoteModal").modal("hide");
				finishWait($("#component_remoteModal"));
				AlertUtil.showMessage("维护子部件关联成功！");
				// 刷新树
				refreshTree(node.id);
			}
		});
	}
	
	/**
	 * 新增或更新飞机部件信息
	 */
	function insertOrUpdate(){
		var msg = validatePlaneZt();
		if(msg){
			AlertUtil.showErrorMessage(msg);
			return false;
		}
		$('#componentForm').data('bootstrapValidator').validate();
		  if(!$('#componentForm').data('bootstrapValidator').isValid()){
			return false;
		  }
		var treeObj = $.fn.zTree.getZTreeObj("loadingList_tree");
		var node = treeObj.getSelectedNodes()[0];
		if($("#current_id").val() && node.cj == 0){
			AlertUtil.showErrorMessage("飞机信息不可修改!");
			return false;
		}
		var param = {
			id : $("#current_id").val(),
			cj : $("#current_id").val() ? node.cj : parseInt(node.cj)+1,
			fjdid : $("#current_id").val() ? node.fjdid : node.id,
			jh : $("#jh").val(),
			fjzch : $("#fjzch_search").val(),
			xlh : $("#xlh").val(),
			zjh : $("#zjh").val(),
			cjjh : $("#cjjh").val(),
			zwmc : $("#zwmc").val(),
			ywmc : $("#ywmc").val(),
			nbsbm : $("#nbsbm").val(),
			shzh : $("#shzh").val(),
			xkzh : $("#xkzh").val(),
			pch : $("#pch").val(),
			bjgzjl : $("#bjgzjl").val(),
			zjsl : $("#zjsl").val(),
			wz : $("#wz").val(),
			scrq : $("#scrq").val(),
			bz : $("#bz").val(),
			azrq : $("#azrq").val(),
			azjldh : $("#azjldh").val(),
			ccrq : $("#ccrq").val(),
			ccjldh : $("#ccjldh").val(),
			llkbh : $("#llkbh").val(),
			bjlx : 1,
			zt : 1,
			llklx : $("#llklx").val(),
			kzlx : $("#kzlx").val(),
			isDj : $("#isDj").val(),
			dprtcode : $("#dprtcode_head").val()
		};
		if(param.xlh != "" && $("#warn_ensure_hid").val() != param.xlh){
			var returnFlag = false;
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/productionplan/loadingList/judgeXlhExist",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				contentType: "application/json;charset=utf-8",
				success:function(data){
					if(data.isExist){
						var ll = data.loadingList;
						if(ll.id != param.id && ll.zt != 1){
							returnFlag = true;
							$("#xlhExistModalBody").html("已存在件号为："+ll.jh+"，序列号为："+ll.xlh+"，状态为"+ztMap[ll.zt]+"的部件，是否确认操作？");
							$("#xlhExistModal").modal("show");
							$("#warn_ensure_hid").val(ll.xlh);
						}
					}
				}
			});
			if(returnFlag){
				return false;
			}
		}
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/productionplan/loadingList/insertOrUpdateEditable",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(data){
				refreshTree(data);
				AlertUtil.showMessage(($("#current_id").val() ? "修改" : "新增") +"部件基本信息成功!");
				$("#warn_ensure_hid").val("");
			}
		});
	}
	
	/**
	 * 作废消息框
	 */
	function alertScrapWarnMessage(id,cj){
		var msg = validatePlaneZt();
		if(msg){
			AlertUtil.showErrorMessage(msg);
			return false;
		}
		if(!id){
			var treeObj = $.fn.zTree.getZTreeObj("loadingList_tree");
			var node = treeObj.getSelectedNodes()[0];
			id = node.id;
			cj = node.cj;
		}
		if(cj == 0){
			AlertUtil.showErrorMessage("该数据为飞机初始化数据，不可作废!");
			return false;
		}
		if(id == "0"){
			AlertUtil.showErrorMessage("该部件无法作废!");
			return false;
		}
		$("#delete_self_hid").val(id);
		$("#delete_cascade_hid").val(id);
		$("#scrapModalBody").html("是否确认作废该部件？");
		$("#scrapModal").modal("show");
	}
	
	/**
	 * 本身作废
	 */
	function delete_self(){
		startWait($("#scrapModal"));
		scrap($("#delete_self_hid").val(), basePath+"/productionplan/loadingList/scrapEditable");
	}
	
	/**
	 * 级联作废
	 */
	function delete_cascade(id){
		startWait($("#scrapModal"));
		scrap($("#delete_cascade_hid").val(), basePath+"/productionplan/loadingList/cascadeScrapEditable");
	}
	
	/**
	 * 作废
	 * @param id
	 * @param url
	 */
	function scrap(id, url){
		var param = {
				id : id,
				fjzch : $("#fjzch_search").val()
		};
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			modal:$("#scrapModal"),
			contentType: "application/json;charset=utf-8",
			success:function(data){
				$("#scrapModal").modal("hide");
				finishWait($("#scrapModal"));
				refreshTree();
				goPage_tableView(1,"auto","desc");
				AlertUtil.showMessage("部件作废成功!");
			}
		});
	}
	
	/**
	 * 切换显示
	 */
	function switchView(){
		$("#treeView").toggle();
		$("#tableView").toggle();
		if($("#treeView").is(":visible")){
			refreshTree();
		}else{
			goPage_tableView(1,"auto","desc");
		}
	}
	
	/**
	 * 更改选中的飞机
	 */
	function changeSelectedPlane(){
		var activeId = $("#tablist>li.active").attr("id");
		if(activeId == "tab-1"){
			if($("#left_div").is(":visible")){
				refreshTree();
			}else{
				goPage_tableView(1,"auto","desc");
			}
		}else if(activeId == "tab-2"){
			refreshTimeMonitor();
		}else if(activeId == "tab-3"){
			getfixedMonitorList();
		}
	}
	
	/**
	 * 验证飞机状态是否为生效
	 * @returns {Boolean}
	 */
	function validatePlaneZt(){
		if($("#fjzch_search").val() == 'NO_PLANE'){
			return "请先选择飞机!";
		}
		if($("#fjzch_search option:selected").attr("zt") != 1){
			return "只有飞机的状态为有效，才能进行操作!";
		}
		return null;
	}
	
	/**
	 * 同步生效区
	 */
	function synchronizeEffective(){
		var params = {
				fjzch : $("#fjzch_search").val(),
				dprtcode : $("#dprtcode_head").val()
		};
		var msg = validatePlaneZt();
		if(msg){
			AlertUtil.showErrorMessage(msg);
			return false;
		}
		AjaxUtil.ajax({
			url:basePath+"/productionplan/loadingList/synchronizeEffective",
			type:"post",
			data:JSON.stringify(params),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(data){
				if(data && data.length > 0){
					var msg = "初始化失败！</br>";
					for(var i = 0; i < data.length; i++){
						msg += (StringUtil.escapeStr(data[i]) + "</br>");
					}
					AlertUtil.showErrorMessage(msg);
				}else{
					refreshTree();
					AlertUtil.showMessage("初始化成功!");
				}
			}
		});
	}
	
	function moreSubcomponent() {
		if ($("#divSearchSubcomponent").css("display") == "none") {
			$("#divSearchSubcomponent").css("display", "block");
			$("#iconSubcomponent").removeClass("icon-caret-down");
			$("#iconSubcomponent").addClass("icon-caret-up");
		} else {

			$("#divSearchSubcomponent").css("display", "none");
			$("#iconSubcomponent").removeClass("icon-caret-up");
			$("#iconSubcomponent").addClass("icon-caret-down");
		}
	}
	 
	 //搜索条件重置
	function subcomponentSearchreset(){
		$("#divSearchSubcomponent").find("input").each(function() {
			$(this).attr("value", "");
		});
		
		$("divSearchSubcomponent").find("textarea").each(function(){
			$(this).val("");
		});
		 
		$("#divSearchSubcomponent").find("select").each(function(){
			$(this).val("");
		});
		$("#subcomponent_search").val("");
	 }
	
	//只能输入数字和小数
	 function clearNoNum(obj){
	      //先把非数字的都替换掉，除了数字和.
	      obj.value = obj.value.replace(/[^\d.]/g,"");
	      //必须保证第一个为数字而不是.
	      obj.value = obj.value.replace(/^\./g,"");
	      //保证只有出现一个.而没有多个.
	      obj.value = obj.value.replace(/\.{2,}/g,".");
	      //保证.只出现一次，而不能出现两次以上
	      obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	      //最多2位小数
	      if(/^\d+(\.\d{3,})$/.test(obj.value)){
	    	  obj.value = Math.floor(parseFloat(obj.value) * 100) / 100;
	      }
	      
	      var td = $(obj).parent();
	      var personNum = td.find("input:first").val();
	      var timeNum = td.find("input:last").val();
	      var result = (parseFloat(personNum||0)*parseFloat(timeNum||0)).toFixed(2);
	      td.find("span[name='totalTime']").text(result);
	 }
	 
	 //打开ATA章节号对话框
	 function openChapterWin(){
	 	var zjh = $.trim($("#zjh").val());
	 	var dprtcode = $.trim($("#dprtcode_head").val());
	 	FixChapterModal.show({
	 		selected:zjh,//原值，在弹窗中默认勾选
	 		dprtcode : dprtcode,
	 		callback: function(data){//回调函数
	 			var wczjh = '';
	 			var wczjhName = '';
	 			
	 				wczjh = StringUtil.escapeStr(data.zjh);
	 				wczjhName = StringUtil.escapeStr(data.zwms);
	 				wczjhName = wczjh + " " + wczjhName;
	 			
	 			$("#zjh").val(wczjh);
	 			$("#zjh_show").val(wczjhName);
	 			resetValidate("zjh");
	 		}
	 	})
	 }
	 
	//改变组织机构时改变飞机注册号
	 function changeOrganization(notRefresh){
	 	var dprtcode = $.trim($("#dprtcode_head").val());
	 	var planeRegOption = '';
	 	if(planes != null && planes.length >0){
	 		$.each(planes,function(i,planeData){
	 			if(dprtcode == planeData.dprtcode){
	 				planeRegOption += ("<option value='"+StringUtil.escapeStr(planeData.fjzch)+
	 					"' zt='"+planeData.zt+"' fjjx='"+StringUtil.escapeStr(planeData.fjjx)+
	 					"' isTsqk='"+planeData.isTsqk+"' issync='"+(planeData.isSync||0)+
	 					"'>"+StringUtil.escapeStr(planeData.fjzch)+"</option>");
	 			}
	 		});
	 	}
	 	if(planeRegOption == ''){
	 		planeRegOption = '<option value="NO_PLANE">暂无飞机 No plane</option>';
	 	}
	 	$("#fjzch_search").html(planeRegOption);
	 	if(!notRefresh){
	 		changeSelectedPlane();
	 	}
	 }
	 
	 /**
	  * 限制数量
	  */
	 function limitCount(){
		 var xlh = $.trim($("#xlh").val());
		 if(xlh){
			 $("#zjsl").attr("disabled","disabled");
			 $("#zjsl").val(1);
		 }else{
			 $("#zjsl").removeAttr("disabled");
		 }
	 }
	 
	 /**
	  * 失效件号序列号输入框
	  * @param node
	  */
	 function disabledJhAndXlh(node){
		 if(node.jh && node.xlh && node.isSync == 1){
			 $("#jh").attr("disabled","disabled");
			 $("#xlh").attr("disabled","disabled");
		 }
	 }
	 
	 /**
	  * 显示导入模态框
	  */
	 function showImportModal(){
		 importModal.show();
	 }
	 
	 function abbreviate(content, maxLength){
  		if(content && content.length > maxLength){
			   remark=content.substr(0,maxLength)+"..."; 
		   }else{
			   remark=content;
		   }
  		return remark;
  	 }
	 
	 function beforeClick(treeId, treeNode, clickFlag){
		 return treeNode.id != '0' 
			 	&& treeNode.id != 'js'
		 		&& treeNode.id != 'zf'
	 			&& treeNode.id != 'yf'
 				&& treeNode.id != 'ssd'
				&& treeNode.id != 'wdg'
				&& treeNode.id != 'jc'
	 }
	 
	 function initPlanes(){
		 AjaxUtil.ajax({
			   async: false,
			   url:basePath+"/productionplan/planeData/findAllWithFjjx",
			   type: "post",
			   success:function(data){
				   planes = data;
		      }
	    }); 
	 }
	 
	 /**
	  * 初始化导入
	  */
	 function initImport(){
		$("#ImportBody").prepend(['<div class="row padding-bottom-5">',
			     					'<label class="col-lg-2 text-right padding-left-0 padding-right-0">',
										'<div class="font-size-12 line-height-18">',
											'<span style="color: red"></span>飞机注册号',
										'</div>',
										'<div class="font-size-9 line-height-18">A/C REG</div>',
									'</label>',
									'<div class="col-lg-7 padding-left-8 padding-right-0" >',
										'<select class="form-control" id="fjzch_import">'+$("#fjzch_search").html()+'</select>',
									'</div>',
									'<input type="text" id="ignore" style="display:none; value="0">',
								 '</div>'].join(""));
		// 初始化导入
	    importModal.init({
		    importUrl:"/productionplan/loadingList/excelImport",
		    formData:{
		    	fjzch : function(){
		    		return $("#fjzch_import").val();
		    	}
		    },
		    downloadUrl:"/common/templetDownfile/12",
			callback: function(data){
				// 刷新装机清单数据
				changeSelectedPlane();
				$("#ignore").val(0);
			},
			beforeImport: function(){
				// 验证飞机是否已经初始化
				var issync = $("#fjzch_import option:selected").attr("issync");
				var ignore = $("#ignore").val();
				if(issync == 0 || ignore == 1){
					return true;
				}else{
					AlertUtil.showConfirmMessage("该飞机数据已初始化成功，是否需要继续导入",{callback:function(option){
						$("#ignore").val(1);
						$("#ImportBody form input").change();
					}});
					return false;
				}
			}
		})
	 }
		
		 