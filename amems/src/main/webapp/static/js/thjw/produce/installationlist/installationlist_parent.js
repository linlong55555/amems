	

	var installationlist_parent = {
		
		id : "installation_parent_modal",
		
		treeId : "installation_parent_modal_tree",
		
		param: {
			selected:null,
			fjzch:"",
			wz:"",
			id:"",
			effect:true,
			callback:function(){},
			dprtcode:userJgdm//默认登录人当前机构代码
		},
		
		show : function(param){
			$("#"+this.id).modal("show");
			if(param){
				$.extend(this.param, param);
			}
			this.refreshTree();
		},
		
		// 刷新装机清单树
		refreshTree : function(){
			if($.fn.zTree.getZTreeObj(this.treeId)){
				$.fn.zTree.getZTreeObj(this.treeId).destroy();
			}
			this.initTree();
		},
		
		// 初始化装机清单树
		initTree : function(){
			var this_ = this;
			startWait();
			var url = this_.param.effect ? (basePath+"/aircraftinfo/installationlist/effectlist") 
					: (basePath+"/aircraftinfo/installationlist/list");
			AjaxUtil.ajax({
				   url: url, 
				   type: "post",
				   dataType:"json",
				   contentType:"application/json;charset=utf-8",
				   data:JSON.stringify({
					   fjzch : this_.param.fjzch,
					   dprtcode : this_.param.dprtcode,
					   wz : this_.param.wz,
					   jssj : this_.param.jssj,
					   zjzt : 1,
					   paramsMap : {
						   fjdKeyword : $.trim($("#installation_parent_modal_keyword").val()),
						   notParentId : this_.param.id,
					   }
				   }),
				   success:function(data){
					   finishWait();
					   var setting = {
							   view: {
								   showIcon: false,
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
					   };
					   
					   // 父节点增加N/A选项。选择N/A时表示此件为机身的一级件。
					   var na = {
						   id : "0",
						   bjh : "N/A",
						   detailName : "N/A",
						   cj : "0",
					   };
					   data.unshift(na);
					   var treeObj = $.fn.zTree.init($("#"+this_.treeId), setting, data);
					   var nodes = treeObj.transformToArray(treeObj.getNodes());
					   $.each(nodes, function(index, node){
						   node.title = StringUtil.escapeStr(node.name);  
						   treeObj.updateNode(node); 
					   });
					   treeObj.expandAll(true); 
					   
					   if(this_.param.selected){
						   var selected = treeObj.getNodeByParam("id", this_.param.selected, null);
						   treeObj.selectNode(selected);
					   }
			      }
			    }); 
		},
		
		// 确定
		save: function(){
			
			if(this.param.callback && typeof(this.param.callback) == "function"){
				var treeObj = $.fn.zTree.getZTreeObj(this.treeId);
				var nodes = treeObj.getSelectedNodes();
				if(nodes.length == 0){
					AlertUtil.showErrorMessage("请先选择一个部件");
					return;
				}
				this.param.callback(nodes[0]);
				$("#"+this.id).modal("hide");
			}
		},
		
		// 清空
		clear : function(){
			if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
			}
			$("#"+this.id).modal("hide");
		},
		
	};
	
	