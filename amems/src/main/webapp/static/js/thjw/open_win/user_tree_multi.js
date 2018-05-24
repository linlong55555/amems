User_Tree_Multi_Modal = {
	id:'User_Tree_Multi_Modal',
	viewUserId:'um_selectUser',
	dprt_json_id : 'dprt_json',
	station_json_id : 'station_json',
	isLoad:false,
	param: {
		multi:true, //是否多选 默认单选
		checkUserList:null,
		parentWinId : '',
		clearUser:true,
		maxSelectCount : 1,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	data:[],
	userList:[],
	newData:[],
	show : function(param){
		this.param.checkUserList = null;
		if(param){
			$.extend(true,this.param, param);
		}
		//初始化时加载数据
		if(!this.isLoad){
			this.initMulti();
			this.isLoad = true;
		}
		if(param.clearUser==false){
			$("#User_Tree_Multi_Modal_btn_clear").hide();
		}
		this.clearData();//清空数据
		//回显数据
		if(this.param.checkUserList){
			this.returnViewData(this.param.checkUserList);
		}
		$("#"+this.id).modal("show");
		this.loadDpartment();
		
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
			this_.addTaginput(row);
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
		$('#'+this.dprt_json_id).jstree("uncheck_node",id);
	},
	romoveTagInput : function(id){
		$("#"+this.viewUserId).tagsinput('remove', id);
	},
	getTagInputAllData : function(){
		var data = $("#"+this.viewUserId).tagsinput('items');
		return data;
	},
	loadDpartment : function(){
		var this_ = this;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/sys/user/queryDrptTreeByDprtcode",
			type:"post",
			data:{dprtcode:this.param.dprtcode},
			dataType:"json",
			success:function(data){
				if(data != null && data.treeList.length > 0){
					this_.newData=data.treeList;
					this_.userList=data.userList;
					$("#user_keyword_search").val("");
					this_.createJsTree(this_.dprt_json_id,data.treeList);
				}
			}
		});
	},
	createJsTree : function(treeId,data){
			this.createJsTreeMulti(treeId, data);
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
				$('#'+treeId).jstree("check_node",row.id);
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
					this_.addCheckTree(n.li_attr.id);
					if(n.li_attr.type == 1){
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
    			this_.romoveCheckTree(n.id);
        		this_.romoveTagInput(n);
    		}else{
    			this_.removeNodes(treeId, n.id);
    		}
		}); 
	},
	setData: function(){
		var data = $("#"+this.viewUserId).tagsinput('items');
		if(data.length < 1){
			AlertUtil.showErrorMessage("请选择一个人员！");
			return false;
		}
		if(data.length >= 2 ){
			AlertUtil.showErrorMessage("只能选择一个人员！");
			return false;
		}
		
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
		$("#"+this.id).modal("hide");
	},
	clear:function(){
		//清空
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback('clear');
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
		var this_=this;
		var str=$("#user_keyword_search").val();
		var data=this_.newData;
		var userList=this_.userList;
		var checkUserList=[];
		//清空数据
		this.clearData();
		if(str==""){
			str='&sun1';
		}
		//遍历用户
		$.each(userList, function(index, row) { 
    		if(row.displayName.indexOf(str) >= 0){
    			checkUserList.push(row);
    			this_.addTaginput(row);
    		}
			
		});
		//回显input中
		this_.param.checkUserList=checkUserList;
		//回显选中
		this_.createJsTree(this_.dprt_json_id,data);
	}
}