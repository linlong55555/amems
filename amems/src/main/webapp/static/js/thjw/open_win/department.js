departmentModal = {
	id:'departmentModal',
//	viewDprtment:'um_selectDprtname',
	isLoad:false,
	param: {
		dprtnameList:null,//部门名称
		dprtcodelist:null,//部门id
		checkDepartmentList:[],//存储转换后的部门对象
		parentid:null,//第一层modal
		type:true,//默认checkbox,false为radio
		clearDepartment: true,//清空按钮是显示,默认true显示
		callback:function(){},
		dprtcode:userJgdm,//默认登录人当前机构代码
		jdbs:null
	},
	data:[],
	show : function(param){
		//初始化时加载数据
		this.param.checkDepartmentList = null;
		this.param.parentid = null;
		if(!this.isLoad){
//			this.initMulti();
			this.isLoad = true;
		}
		
		if(param){
			this.param.checkDepartmentList=param;
			$.extend(true,this.param, param);
		}
		//不显示清空按钮
		if(this.param.clearDepartment==false){
			$("#departmentModal_clear").hide();
		}else{
			$("#departmentModal_clear").show();
		}
		//回显数据		
		if(this.param.dprtcodeList){
			this.converData(this.param.dprtnameList,this.param.dprtcodeList);
		}
		AlertUtil.hideModalFooterMessage();
		
		this.goPage(1,"auto","desc");
		$("#"+this.id).modal("show");
	},
	converData:function(dprtname,dprtcode){
		var checkDepartmentList=[];
		for (var i = 0; i < dprtname.split(",").length; i++) {
			var p = {
				id : dprtcode.split(",")[i],
				dprtname : dprtname.split(",")[i]
			};
			checkDepartmentList.push(p);
		}
		this.param.checkDepartmentList=checkDepartmentList;		
	},
	initMulti : function(){
		$("#"+this.viewDprtment).tagsinput({
		  itemValue: 'id',
		  itemText: 'dprtname'
		});
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var this_ = this;
		var searchParam = this_.gatherSearchParam();
		AjaxUtil.ajax({
			   url:basePath+"/sys/department/queryDepartTree", 
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   async:false,
			   data : JSON.stringify(searchParam),
			   success:function(data){
				   var map={};
				   if(this_.param.type==false){
					   var setting = {view: {showIcon: true, nameIsHTML: false,selectedMulti: false,showLine: true}, check: {enable: true,chkStyle:"radio",radioType: "all",checkTypeFlag:false, autoCheckTrigger: true}, data: {
						   simpleData: {
							   enable: true,
							   idKey: "id",
							   pIdKey: "pid",
							   rootPId: null
						   }
					   },
					   callback:{
						   beforeClick : this_.beforeClick,
						   onCheck: this_.loadRadioDetail,
						   onClick: this_.clickRadioDetail,
					   }};
				   }else{
					   var setting = {view: {showIcon: true, nameIsHTML: false,selectedMulti: false,showLine: true}, check: {enable: true,chkStyle:"checkbox",checkTypeFlag:false,chkboxType : { "Y": "s", "N": "s"},  autoCheckTrigger: true}, data: {
						   simpleData: {
							   enable: true,
							   idKey: "id",
							   pIdKey: "pid",
							   rootPId: null
						   }
					   },
					   callback:{
						   beforeClick : this_.beforeClick,
						   onCheck: this_.loadDetail,
						   onClick: this_.clickDetail,				   
					   }}; 
				   }
				   var treeObj =   $.fn.zTree.init($("#treeDemo"), setting, data);				   				   
				   treeObj.expandAll(true);				   
				   var nodes = treeObj.transformToArray(treeObj.getNodes());
				   $.each(nodes, function(index, node){
					   name=StringUtil.escapeStr(node.name);				   
					   node.title = name;
					   treeObj.updateNode(node);					  
					  if(this_.param.dprtcodeList!="" &&this_.param.dprtcodeList != undefined){
						  if(this_.param.dprtcodeList.indexOf(node.id)>-1){
							  treeObj.checkNode(node, true, false);
						  }
					  }
				   });
		      }
		    });
	},

	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.jdbs=this.param.jdbs;
		 searchParam.id=this.param.dprtcode;
		 return searchParam;
	},
	setData: function(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		var data ={};
		var paramMap={};				
		var dprtname='';
		var dprtbm='';
		var dprtcode='';
		var departmentList=[];
	
		
		$.each(nodes,function(index,treeNode){
			var department={
					id:treeNode.id,
					dprtname:treeNode.name
			}
			if(treeNode.level!=0&&treeNode.checked==true){				
				data[department.id]=department;
				departmentList.push(department);
				dprtcode += treeNode.id + ",";
				var name=StringUtil.escapeStr(treeNode.name);
				name=name.substring(name.indexOf("/")+1,name.length);
				dprtname += name + ",";	
				
				var namecode=StringUtil.escapeStr(treeNode.name);
				namecode=namecode.substring(0,namecode.indexOf("/"));
				dprtbm += namecode + ",";	
			}	
		});
		if(dprtname!=""){
			dprtname=dprtname.substring(0,dprtname.length-1);
			dprtbm=dprtbm.substring(0,dprtbm.length-1);
		}else{
			AlertUtil.showModalFooterMessage("请选择部门!",this.id);
			return false;
		}
		if(dprtcode!=""){
			dprtcode=dprtcode.substring(0,dprtcode.length-1);
		}
		
		paramMap={"dprtcode":dprtcode,
				"dprtname":dprtname,
				"dprtbm":dprtbm,
				"departmentList":departmentList
		}
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(paramMap);
		}
		$("#"+this.id).modal("hide");
	},
	closemodal:function (){
		$("#"+this.id).modal("hide");
	},
	clearData:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
			var	paramMap={"dprtcode":"",
					"dprtname":"",
					"dprtbm":"",
					"departmentList":"",
			}
		this.param.callback(paramMap);
		}
		$("#"+this.id).modal("hide");
	},
	beforeClick:function(treeId, treeNode, clickFlag){
		 return treeNode.id != '0'
	},
	clickDetail:function(event, treeId, treeNode){
		
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.transformToArray(treeNode);
		if(treeNode.checked){
			$.each(nodes, function(index, node){				 	
				   treeObj.checkNode(nodes[0], false, true);			  		
			   });
		}else{
			$.each(nodes, function(index, node){				 	
				   treeObj.checkNode(node, true, true);			  		
			   });
		}		 
	},
	clickRadioDetail:function(event, treeId, treeNode){		
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.transformToArray(treeNode);	
		treeObj.checkNode(nodes[0], true, false);		  				  
	},
	loadDetail:function(event, treeId, treeNode){		
	},
	loadRadioDetail:function(event, treeId, treeNode){	
	},
	getRoot: function() {  
	   var treeObj = $.fn.zTree.getZTreeObj("treeDemo");  
	    //返回一个根节点  
	   return treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true);  
	} 
}

