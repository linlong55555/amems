
WebuiPopoverUtil = {
	initWebuiPopover : function(clz, body, obj, w){
		var width = 380;
		if(w != null && w != '' && w != undefined){
			width = w;
		}
		var this_ = this;
		$('.'+clz).webuiPopover({
			placement:'auto',//bottom-right
			container: $(body),
			style:'',
			padding: 0,
			trigger: 'click',
			type:'html',
			width:width,
			height:'auto',
			template: '<div class="webui-popover webui-popover-new"><div class="webui-arrow"></div><div class="webui-popover-inner"><a href="#" class="close"></a><h3 class="webui-popover-title"></h3><div class="webui-popover-content"><i class="icon-refresh"></i> <p>&nbsp;</p></div></div></div>',
			backdrop:false/*,
			content: generateAuthorInfo2()*/// 这里可以直接写字符串，也可以 是一个函数，该函数返回一个字符串；
		});
		$('.'+clz).on("click",function(e){
			e = e || window.event;  
		    if(e.stopPropagation) { //W3C阻止冒泡方法  
		        e.stopPropagation();  
		    } else {  
		        e.cancelBubble = true; //IE阻止冒泡方法  
		    } 
		/*WebuiPopovers.updateContent(e.target,obj(e.target));*/
		    WebuiPopovers.updateContent($(this),obj($(this)));
		});
	}
}
