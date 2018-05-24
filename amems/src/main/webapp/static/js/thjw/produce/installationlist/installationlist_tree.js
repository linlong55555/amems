
	
	var installationlist_tree = {
			
		treeId : "installation_tree",
			
		// 获取关键字
		getKeyword : function(){
			return $.trim($("#installation_tree_search").val());
		},
		
		// 初始化
		init : function(id){
			if($.fn.zTree.getZTreeObj(this.treeId)){
				$.fn.zTree.getZTreeObj(this.treeId).destroy();
			}
			this.initTree(id);
			
			if(checkBtnPermissions('aircraftinfo:installationlist:main:02')){
				$("#installation_tree_save_btn").hide();
			}
			if(checkBtnPermissions('aircraftinfo:installationlist:main:03')){
				$("#installation_tree_del_btn").hide();
			}
		},
		
		// 初始化装机清单树
		initTree : function(id){
			var this_ = this;
			startWait();
			var param = installationlist_table.gatherSearchParam();
			AjaxUtil.ajax({
				   url:basePath+"/aircraftinfo/installationlist/list", 
				   type: "post",
				   dataType:"json",
				   contentType:"application/json;charset=utf-8",
				   data:JSON.stringify(param),
				   success:function(data){
					   finishWait();
					   var setting = {
							   view: {
								   showIcon: false,
								   addDiyDom: this_.addIcon,
								   fontCss: this_.setFontCss,
								   selectedMulti: false,
								   nameIsHTML: false
							   },
							   check: {enable: false,autoCheckTrigger: true}, 
							   data: {
								   key: {
										name: "detailName"
								   },
								   simpleData: {
									   enable: true,
									   idKey: "id",
									   pIdKey: "fjdid",
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
								   beforeClick : this_.beforeClick,
								   onClick: this_.onClick,
//								   beforeDrag: checkCanBeginDrag,
//								   beforeDrop: checkCanEndDrag,
//								   onDrop: changeParent
							   }
					   };
					   
					   this_.createVirtualNode(data);
					   var treeObj = $.fn.zTree.init($("#"+this_.treeId), setting, data);
					   var nodes = treeObj.transformToArray(treeObj.getNodes());
					   $.each(nodes, function(index, node){
						   node.title = StringUtil.escapeStr(node.name);  
						   treeObj.updateNode(node); 
					   });
					   treeObj.expandAll(true); 
					   if(id){
						   var node = treeObj.getNodeByParam("id", id, null);
						   $("#"+node.tId+"_a").click();
					   }else{
						   var root = this_.getRoot();
						   if(root){
							   $("#"+root.tId+"_a").click();
						   }
					   }
					   // 重新设定树高度
					   $("#"+this_.treeId).height();
			      }
			    }); 
		},
		
		// 获取根节点
	    getRoot : function() {  
	       var treeObj = $.fn.zTree.getZTreeObj(this.treeId);  
	       return treeObj.getNodesByFilter(function (node) { return node.id == "0" }, true);  
	    },
		
		createVirtualNode : function(data){
			
			var hasNoParentPart = false;
			$.each(data, function(i, obj){
				if(obj.fjdid == "0"){
					if(obj.wz == "11"){
						obj.fjdid = "011";
					}else if(obj.wz == "21"){
						obj.detailName = "1#发" + " " + obj.detailName;
					}else if(obj.wz == "22"){
						obj.detailName = "2#发" + " " + obj.detailName;
					}else if(obj.wz == "23"){
						obj.detailName = "3#发" + " " + obj.detailName;
					}else if(obj.wz == "24"){
						obj.detailName = "4#发" + " " + obj.detailName;
					}else if(obj.wz == "31"){
						obj.detailName = "APU" + " " + obj.detailName;
					}
				}else{
					//上级部件不在编辑区
					if(!obj.parent || !obj.parent.id){
						obj.fjdid='99';
						hasNoParentPart = true;
					}
				}
			});
			
			// 根节点-飞机
			data.push({
			   id : "0",
			   cj : "0",
			   bjh : "N/A",
			   detailName : $("#fjzch option:selected").text()
		    });
			
			// 机身
			data.unshift({
			   id : "011",
			   fjdid : "0",
			   cj : "0",
			   wz : "11",
			   bjh : "N/A",
			   detailName : "机身"
		    });
			
			if(hasNoParentPart){
				data.push({
				   id : "99",
				   fjdid : "0",
				   cj : "0",				  
				   bjh : "N/A",
				   detailName : "无上级部件"
			    });
			}
			
		},
		
		// 添加图标
		addIcon : function(treeId, treeNode){
			var aObj = $("#" + treeNode.tId + "_a");
			
			// 修改待同步
			if(treeNode.tbbs == 1){
				var dj_icon = "<span> </span>"
				+ "<i class='icon-pencil color-blue' title='修改待同步'></i>&nbsp;";
				aObj.after(dj_icon);
			}
			
			// 已拆下
			if(treeNode.zjzt == 2){
				var dj_icon = "<span> </span>"
				+ "<i class='icon-ban-circle color-blue' title='已拆下'></i>&nbsp;";
				aObj.after(dj_icon);
			}
			
			// 初始化数据是否维护
			if(treeNode.initDatas && treeNode.initDatas.length > 0){
				var initFlag = false;
				$(treeNode.initDatas).each(function(i, obj){
					if(obj.csz){
						initFlag = true;
					}
				});
				if(initFlag){
					var kzlx_icon = "<span> </span>"
						+ "<i class='icon-ok color-blue' title='初始化信息已维护'></i>&nbsp;";
						aObj.after(kzlx_icon);
				}else{
					var dj_icon = "<span> </span>"
						+ "<i class='icon-exclamation color-red' title='初始化信息未维护'></i>&nbsp;";
						aObj.after(dj_icon);
				}
			}
			
		},
		
		// 设置字体样式
		setFontCss : function(treeId, treeNode){
//			if(treeNode.zjzt == 2){
//				return {'background':'url("'+basePath+'/static/assets/img/delete-lineorange.png") center repeat-x' }
//			}else{
//				return {};
//			}
			return {};
		},
		
		// 点击事件
		onClick : function(event, treeId, treeNode){
			
			// 验证销毁
			$("#installation_tree_view_detail form").data('bootstrapValidator').resetForm(false);
			
			var common = installationlist.common;
			common.jQueryName("common_wz").removeAttr("disabled");
			common.jQueryName("common_fjd_btn").removeAttr("disabled");
			common.jQueryName("common_fjd_display").removeAttr("disabled");
			
			if(treeNode.id != "0"){
				installationlist_tree.enableAll(common);
			}
			// 加载数据
			installationlist.common.loadData(treeNode.id);
			
			if(treeNode.id == "0"){
				installationlist_tree.disableAll(common);
			}
			// 显示保存按钮
			if(treeNode.cj == 0){
				$("#installation_tree_save_btn").hide();
			}else{
				$("#installation_tree_save_btn").show();
			}
			
			// 显示删除按钮
			if(treeNode.effective || treeNode.tbbs != 1 ||
					treeNode.cj == 0){
				$("#installation_tree_del_btn").hide();
			}else{
				$("#installation_tree_del_btn").show();
			}
		},
		
		disableAll : function(common){
			$("#installation_tree_view_detail").find("input,select,textarea").attr("disabled", "disabled");
			common.jQueryName("common_zjh_display").removeClass("readonly-style");
			common.jQueryName("common_fjd_display").removeClass("readonly-style");
//			common.jQueryName("common_fjzw").removeClass("readonly-style").css("background-color", "#f3f3f3");
			common.jQueryName("common_certificate_addBtn").hide();
		},
		
		
		enableAll : function(common){
			$("#installation_tree_view_detail").find("input,select,textarea").removeAttr("disabled");
			common.jQueryName("common_zjh_display").addClass("readonly-style");
			common.jQueryName("common_fjd_display").addClass("readonly-style");
//			common.jQueryName("common_fjzw").addClass("readonly-style").css("background-color", "#fff");
			common.jQueryName("common_certificate_addBtn").show();
		},
		
		// 点击之前事件
		beforeClick : function(treeId, treeNode, clickFlag){
			if(treeNode.id=='011' || treeNode.id=='99'){
				return false;
			}
			return true;
		},
		
		// 点击新增按钮
		modify : function(){
			
			var common = installationlist.common;
			var parent = $.fn.zTree.getZTreeObj(this.treeId).getSelectedNodes()[0];
			$("#installation_tree_save_btn").show();
			$("#installation_tree_del_btn").hide();
			
			if(parent.id == "0"){
				this.enableAll(common);
			}
			
			common.loadData();
			
			// 禁用位置和父节点
			if(parent.id != "0"){
				common.jQueryName("common_wz").attr("disabled","disabled");
			}
			common.jQueryName("common_fjd_btn").addClass("hidden");
			common.jQueryName("common_fjd_display").attr("disabled","disabled").removeClass("readonly-style");
			
			if(parent.id == "011"){
				parent.id = "0";
			}
			common.setParent(parent);
			common.buildWz(parent.wz);
		},
		
		// 保存
		save : function(){
			var this_ = this;
			var obj = installationlist.common.getInstallation();
			installationlist.save(obj, function(data){
				finishWait();
				AlertUtil.showMessage("保存成功！");
				this_.init(data);
			});
		},
		
		// 删除
		del : function(){
			var common = installationlist.common;
			installationlist.del(common.jQueryName("common_id").val());
		},
	};