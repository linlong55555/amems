partModal = {
	param: {
		selected:null,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		$("#partModal").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.clearData();
		this.goPage(1,"auto","desc");
	},
	data:[],
	clearData : function(){
		$("#partsearch").val('');
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var searchParam = this.gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		var this_ = this;
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/project/workorder/getLoadingList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.data = data.rows;
				   partModal.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : 'part_pagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				// 标记关键字
				signByKeyword($("#partsearch").val(),[2,3,4,5,6,7,8],"#partlist tr td");
			   } else {
				  $("#partlist").empty();
				  $("#part_pagination").empty();
				  $("#partlist").append("<tr><td colspan=\"9\">暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.fjzch = $.trim($("#fjzch").val());
		 searchParam.dprtcode = $("#dprtcode").val();
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
		  /* if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='partModal.checkUser(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='partModal.checkUser(this)'>";
		   }
		   if(partModal.param.selected && partModal.param.selected == row.rwid){
			   checked = "checked";
		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jh)+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jh)+"</td>";  
		   htmlContent += + "</tr>"; */
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\"  bgcolor=\"#f9f9f9\" onclick='partModal.checkUser(this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='partModal.checkUser(this)'>";
		   }
/*		   if(sn==row.xlh){
			   htmlContent = htmlContent + "<td class='text-center'><input type=\"radio\" name='pgd' checked='checked' onclick='chooesRow5(this)' ><input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.jh))+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.xlh))+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.zwmc))+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.ywmc))+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.id)+"'></td>";
		   }else{*/
			   htmlContent = htmlContent + "<td class='text-center'><input type=\"radio\" index='"+index+"'  name='pgd' ><input type='hidden' value="+StringUtil.escapeStr(formatUndefine(row.jh))+">" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.xlh))+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.zwmc))+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.ywmc))+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.id)+"'></td>";
		  /* }*/
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zjhms)+"'>"+StringUtil.escapeStr(row.zjhms)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.jh)+"'>"+StringUtil.escapeStr(row.jh)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.xlh)+"'>"+StringUtil.escapeStr(row.xlh)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zwmc)+"'>"+StringUtil.escapeStr(row.zwmc)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ywmc)+"'>"+StringUtil.escapeStr(row.ywmc)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.cjjh)+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
			htmlContent = htmlContent + "<td class='text-left' title='"+DicAndEnumUtil.getEnumName('partsPositionEnum',row.wz)+"'>"+DicAndEnumUtil.getEnumName('partsPositionEnum',row.wz)+"</td>";
			htmlContent = htmlContent + "</tr>"; 
		   
		    
	   });
	   $("#partlist", $("#partModal")).empty();
	   $("#partlist", $("#partModal")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkUser: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	setUser: function(){
		var $checkedRadio = $("#partlist", $("#partModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0){
				this.param.callback(this.data[index]);
			}else{
				this.param.callback({});
			}
		}
		$("#partModal").modal("hide");
	}
}