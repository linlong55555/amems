
	$(document).ready(function(){
		  Navigation(menuCode);
	      $('#submit').on('click',function(){
	    	  
	    	  //参数组装
			  var obj = {};
			  obj.id = $.trim($("#id").val());				//主键id
		      obj.zdjsrid = $.trim($("#userId").val());		//指定结束人id
		      obj.zdjsyy =  $.trim($("#zdjsyy").val());								//指定结束原因
		      
	    	  //判断指定结束原因
	    	  var zdjsyy = $.trim($("#zdjsyy").val());
	    	  if (zdjsyy == "") {
	    		  $('#alertErrorModalBody').html("指定结束原因不能为空");
			      $('#alertErrorModal').modal('show');
	    		  return false;
	    	  }
	    	  
	    	  AjaxUtil.ajax({
					  url:'endRevisionNoticeBook',						//发送请求的地址（默认为当前页地址）
					  type:'post',										//请求方式（post或get）默认为get
					  data:JSON.stringify(obj),							//发送到服务器的数据
					  contentType:"application/json;charset=utf-8", 	//发送到服务器的数据内容编码类型
					  dataType:'json',									//预期服务器返回的数据类型
					  cache:false,										//缓存（true有缓存，false无缓存）
					  async: false,
					  success:function(data) {							//请求成功后调用的回调函数
						  $('#alertModalBody').html("成功");
					      $('#alertModal').modal('show');
						 
					  }
			  }); 
	      })
		      
		$('#alertModal').on('hide.bs.modal', function () {
			window.location.href =  basePath+"/project/revisionNoticeBook/main";
		})
			
		$('#alertModal').on('hidden.bs.modal', function () {
			window.location.href =  basePath+"/project/revisionNoticeBook/main";
		})
		  
	});
	