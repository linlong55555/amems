Personel_Tree_Multi_Modal = {
	id:'Personel_Tree_Multi_Modal',
	viewUserId:'um_selectUser',
	dprt_json_id : 'dprt_json',
	station_json_id : 'station_json',
	isLoad:false,
	param: {
		multi:true, //是否多选 默认多选
		checkUserList:null,
		clearUser:true,
		maxSelectCount : 1,
		callback:function(){},
		dprtcode:userJgdm,//默认登录人当前机构代码
		bmdm:""
	},
	data:[],
	show : function(param){
		this.param.checkUserList = null;
		if(param){
			$.extend(true,this.param, param);
		}
		AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		//初始化时加载数据
		if(!this.isLoad){
			this.initMulti();
			this.isLoad = true;
		}
		if(param.clearUser==false){
			$("#Personel_Tree_Multi_Modal_btn_clear").hide();
		}
		this.clearData();//清空数据
		//回显数据
		if(this.param.checkUserList){
			this.returnViewData(StringUtil.escapeStr(this.param.checkUserList));
		}
		$("#user_keyword_search").val("");
		$("#"+this.id).modal("show");
		this.loadDpartment();
		
		//this.loadStation();
	},
	initMulti : function(){
		var this_= this;
		$("#"+this_.viewUserId).tagsinput({
		  itemValue: 'id'
		  ,itemText: 'displayName'
		  ,maxTags: 1000
		});
		
		if(this_.param.multi){
			$('#'+this_.viewUserId).on('itemRemoved', function(event) {
				if(event.item != null && event.item != undefined &&  event.item != 'undefined'){
					this_.romoveCheckTree(event.item.id);
				}
			});
		}
	},
	clearData : function(){
		$("#"+this.viewUserId).tagsinput('removeAll');
	},
	returnViewData : function(checkUserList){
		var this_ = this;
		$.each(checkUserList, function(i, row){
			if((row.id != null) && (row.id != '')){
				this_.addTaginput(row);
			}
		});
	},
	addTaginput : function(row){
		$("#"+this.viewUserId).tagsinput('add', row);
	},
	addCheckTree : function(id){
		var this_ =this;
		$('#'+this_.dprt_json_id).jstree("check_node",id);
		$('#'+this_.station_json_id).jstree("check_node",id);
	},
	romoveCheckTree : function(id){
		var this_ =this;
		$('#'+this_.dprt_json_id).jstree("uncheck_node",id);
		$('#'+this_.station_json_id).jstree("uncheck_node",id);
	},
	romoveTagInput : function(id){
		$("#"+this.viewUserId).tagsinput('remove', id);
	},
	getTagInputAllData : function(){
		var data = $("#"+this.viewUserId).tagsinput('items');
		return data;
	},
	loadDpartment : function(str){
		if(str== null){
			str="";
		}
		var this_ = this;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/sys/user/queryDrptTreeByDprtcode",
			type:"post",
			data:{	dprtcode:this_.param.dprtcode,
					bmdm:this_.param.bmdm,
					str:str
				 },
			dataType:"json",
			success:function(data){
				if(data != null && data.treeList.length > 0){
					$.each(data.treeList, function(){
						if(this_.param.bmdm && this.id == this_.param.bmdm){
							this.parent = "#";
						}
					});
					this_.createJsTree(this_.dprt_json_id,data.treeList);
				}
			}
		});
	},
	createJsTree : function(treeId,data){
		if(this.param.multi){
			this.createJsTreeMulti(treeId, data);
		}else{
			this.createJsTreeSinge(treeId, data);
		}
	},
	createJsTreeSinge : function(treeId,data){
		var this_ = this;
		$('#'+treeId).jstree('destroy');
		$('#'+treeId).data('jstree', false).empty();
		$('#'+treeId).jstree({
//			'plugins': ["wholerow", "checkbox", "types"],
				"types": {
					"default" : {
		            "icon" : false  // 关闭默认图标
		          },
				},
	            'core': {
//	            	 "themes" : { "stripes" : true },  // 条纹主题
	            	 'data': data,
	            	 "force_text": true
	            }
	            
		});
		$('#'+treeId).bind("select_node.jstree", function (e, data) {
			var type=data.node.li_attr.type;
        	if(type == 1){
        		$('#'+this_.dprt_json_id+" .jstree-clicked").removeClass("jstree-clicked");
        		$('#'+this_.station_json_id+" .jstree-clicked").removeClass("jstree-clicked");
        		$("#"+this_.viewUserId).tagsinput('add', data.node.li_attr);
        		var tagData = this_.getTagInputAllData();
        		if(tagData.length > this_.param.maxSelectCount){
        			this_.romoveTagInput(tagData[0].id);
        		}
	        }
	     });
		
	},
	createJsTreeMulti : function(treeId,data){
		var this_ = this;
		$('#'+treeId).jstree('destroy');
		$('#'+treeId).data('jstree', false).empty();
		$('#'+treeId).jstree({
				'plugins': ["wholerow", "checkbox", "types"],
				"types": {
					"default" : {
		            "icon" : false  // 关闭默认图标
		          },
				},
	            'core': {
//	            	 "themes" : { "stripes" : true },  // 条纹主题
	            	 'data': data,
	            	 "force_text": true
	            }
	            
		});
		//回显数据
		if(this_.param.checkUserList){
			$.each(this_.param.checkUserList, function(i, row){
				this_.addCheckTree(row.id);
			});
		}
		$('#'+treeId).bind("select_node.jstree", function (e, data) {
			var type=data.node.li_attr.type;
        	if(type == 1){
        		this_.addCheckTree(data.node.li_attr.id);
        		this_.addTaginput(data.node.li_attr);
	        }else{
	        	var nodes=$("#"+treeId).jstree("get_checked",true); //使用get_checked方法 
				$.each(nodes, function(i, n) { 
					var type=n.li_attr.type;
					if(type == 1){
		        		this_.addCheckTree(n.li_attr.id);
		        		this_.addTaginput(n.li_attr);
			        }
				}); 
	        }
	     });
		
		$('#'+treeId).bind("deselect_node.jstree", function (e, data) {
			var type=data.node.li_attr.type;
			var nodeId = data.node.li_attr.id;
        	if(type == 1){
        		this_.romoveCheckTree(nodeId);
        		this_.romoveTagInput(nodeId);
	        }else{
	        	this_.removeNodes(treeId,nodeId);
	        }
		    
		});
	},
	removeNodes : function(treeId,nodeId){
		var this_ = this;
		var nodes=$("#"+treeId).jstree("get_children_dom",nodeId); //获取选中的子类节点
    	$.each(nodes, function(i, n) { 
    		if(n.type == 1){
    			var nodee = $("#"+treeId).jstree("get_node",n.id);
    			this_.romoveCheckTree(nodee.li_attr.id);
        		this_.romoveTagInput(n);
    		}else{
    			this_.removeNodes(treeId, n.id);
    		}
		}); 
	},
	setData: function(){
		
		var data = $("#"+this.viewUserId).tagsinput('items');
		if(data == null || data == ""){
			AlertUtil.showErrorMessage("请选择人员!");
			return false;
		}
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
		$("#"+this.id).modal("hide");
	},
	clearUser:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
		$("#"+this.id).modal("hide");
	},
	searchRevision: function(){
		var str=$.trim($("#user_keyword_search").val());
		//清空数据
		this.clearData();
		this.loadDpartment(str);
	}
}