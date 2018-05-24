/**
  * 刷新权限按钮
  */
 function refreshPermission(){
	 if(userName != 'admin'){
		 var checkItems = $('.checkPermission');
	 	  var len = checkItems.length;
	 	  for(var i=0;i<len;i++){
	 		 if(!$(checkItems[i]).attr('permissioncode')){
	 			  continue;
	 		 }
	 		 var permissioncodes = $(checkItems[i]).attr('permissioncode').toLowerCase().split(",");
	 		 var flag = false;
	 		 for ( var code in permissioncodes) {
	 			 if($.inArray(permissioncodes[code], buttonPermissions) > -1){
	 				 flag = true;
	 			 }
	 		 }
	 		 if(!flag){
	 			 $(checkItems[i]).remove();
	 		 }
	 	  } 
	 }
 	 
 }
 
 function checkBtnPermissions(btnCode){
	 if(userName != 'admin'){
		 if($.inArray(btnCode.toLowerCase(), buttonPermissions) == -1){
			 return false;
		 }
	 }
	 return true;
 }
 
 function checkMenuPermissions(menuCode){
	 if(userName != 'admin'){
		 var index = -1;
		 
		 setIndex_(userMenuListJson, menuCode);
		 
		 function setIndex_(list_, menuCode){
			 $.each(list_, function(index_, row_){
				 if(row_.menuCode == menuCode){
					 index = index_;
					 return false;
				 }
				 if(row_.children && row_.children.length > 0){
					 setIndex_(row_.children, menuCode);
				 }
			 })
		 }
		 if(index == -1){
			 return false;
		 }
	 }
	 return true;
 }