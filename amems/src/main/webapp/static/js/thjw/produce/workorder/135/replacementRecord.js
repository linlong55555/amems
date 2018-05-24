replacementRecord = {
		id:"replacementRecord",
		param: {
			data:{},
			back:null,
			fjjx:null,
			fjzch:null,
			fdjsl:2,
			isShowed:true,
			dprtcode:userJgdm,
			parent:null,
			index:0,
			defaultCzrq:'',
		},
		init:function(param){
			this.param.index=0;
			this.param.data={};
			if(param){
				$.extend(this.param, param);
			}			
			$("#"+this.param.parent+" #"+this.id+"_list").empty();			
			if(this.param.isShowed){
				$("#"+this.param.parent+" #"+this.id+"_option").show();
				$("#"+this.param.parent+" #"+this.id+"_option_view").hide();
				$("#"+this.param.parent+" #"+this.id+"_list").append("<tr id='replacementRecord_nodata'><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");			
			}else{
				$("#"+this.param.parent+" #"+this.id+"_option").hide();
				$("#"+this.param.parent+" #"+this.id+"_option_view").show();
				$("#"+this.param.parent+" #"+this.id+"_list").append("<tr id='replacementRecord_nodata'><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");	
			}
			if(this.param.back!=null){
				for (var i = 0; i < this.param.back.length; i++) {
					if(this.param.back[i].id){
						var data = this.param.back[i];
						if(data.zsj !=null && data.zsj.initDatas !=null ){
							$.each(data.zsj.initDatas, function(i, init){
								var value = init.csz;
								if(MonitorUnitUtil.isTime(init.jklbh)){
									value = value?TimeUtil.convertToHour(value, TimeUtil.Separator.COLON) : "";
								}
								init.csz = value;
							});
						}
						if(data.zsj){
							data.zsj.tsn = data.zsj.tsn?TimeUtil.convertToHour(data.zsj.tsn, TimeUtil.Separator.COLON) : "";			    
							data.zsj.tso = data.zsj.tso?TimeUtil.convertToHour(data.zsj.tso, TimeUtil.Separator.COLON) : "";
						}			
						this.appendHtml(data);
					}								
				}
			}
		},
		openReplaceWin : function(backData,id){
			var this_ = this;
			var defaultCzrq = this_.param.defaultCzrq;
			var czrq;
			if(defaultCzrq && typeof(defaultCzrq) == "function"){
				czrq = defaultCzrq();
			}else if(defaultCzrq && typeof(defaultCzrq) == "string"){
				czrq = defaultCzrq;
			}
			open_win_installationlist_replace.show({
				data:backData||{},//原值，在弹窗中默认勾选
				jx:this_.param.fjjx,
				fjzch:this_.param.fjzch,
				fdjsl:this_.param.fdjsl,
				defaultCzrq:czrq,
				dprtcode:userJgdm, //机构代码
				callback: function(data){//回调函数			
					this_.appendHtml(data,id);
					
				}
			});
		},
		getData:function(){
			var data=[];
			for(key in this.param.data){
				data.push(this.param.data[key]);
			}
			return data;
		},
		appendHtml:function(data,id){
			var htmlContent="";			
			var this_ = this;
			if(id == null || id == undefined){
				this_.param.index++;
					htmlContent +="<tr name='"+this_.param.index+"'>";
					htmlContent +=
						"<td class='text-center'>" +
							(this_.param.isShowed ? ("<button class='line6 line6-btn notView' index='"+this_.param.index+"' onclick='"+this_.id+".editReplacementRecord(this);'  type='button'><i class='icon-edit cursor-pointer color-blue cursor-pointer'></i></button>&nbsp;") : "") +
							(!this_.param.isShowed ? ("<button class='line6 line6-btn' index='"+this_.param.index+"' onclick='"+this_.id+".viewReplacementRecord(this);'  type='button'><i class='icon-eye-open cursor-pointer color-blue cursor-pointer'></i></button>&nbsp;") : "") +
							(this_.param.isShowed ? ("<button class='line6 line6-btn' index='"+this_.param.index+"' onclick='"+this_.id+".delReplacementRecord(this);'  type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button>") : "") +
						"</td>";									
				    htmlContent += "<td name='cxbjh' title='"+StringUtil.escapeStr(data.cxj ? data.cxj.bjh : "")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.cxj ? data.cxj.bjh : "")+"</td>";
				    htmlContent += "<td name='cxxlh' title='"+StringUtil.escapeStr(data.cxj ? data.cxj.xlh : "")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.cxj ? data.cxj.xlh : "")+"</a></td>";
				    htmlContent += "<td name='cxmc' title='"+StringUtil.escapeStr(data.cxj ? ((data.cxj.ywmc||"") + " " + (data.cxj.zwmc||"")) : "")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.cxj ? ((data.cxj.ywmc||"") + " " + (data.cxj.zwmc||"")) : "")+"</td>";
				    htmlContent += '<td name="cxsj" title="'+(data.cxSj||"").substr(0, 16)+'" style="text-align:center;vertical-align:middle;">'+(data.cxSj||"").substr(0, 16)+'</td>';
				    htmlContent += "<td name='zsbjh' title='"+StringUtil.escapeStr(data.zsj ? data.zsj.bjh : "")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.zsj ? data.zsj.bjh : "")+"</td>";
				    htmlContent += "<td name='zsxlh' title='"+StringUtil.escapeStr(data.zsj ? data.zsj.xlh : "")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.zsj ? data.zsj.xlh : "")+"</a></td>";
				    htmlContent += "<td name='zsmc' title='"+StringUtil.escapeStr(data.zsj ? ((data.zsj.ywmc||"") + " " + (data.zsj.zwmc||"")) : "")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.zsj ? ((data.zsj.ywmc||"") + " " + (data.zsj.zwmc||"")) : "")+"</td>";
				   
				    htmlContent += '<td name="zssj" title="'+(data.zsSj||"").substr(0, 16)+'" style="text-align:center;vertical-align:middle;">'+(data.zsSj||"").substr(0, 16)+'</td>';	   
				    htmlContent += "<td name='czwz' title='"+DicAndEnumUtil.getEnumName('installedPositionEnum', ((data.cxj==null?"":data.cxj.wz)||(data.zsj==null?"":data.zsj.wz)|| "11"))+"' style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('installedPositionEnum', ((data.cxj==null?"":data.cxj.wz)||(data.zsj==null?"":data.zsj.wz)|| "11"))+"</td>";	   
				    htmlContent += "<td name='czyy' title='"+StringUtil.escapeStr(data.cxBz || data.zsBz || "")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.cxBz || data.zsBz || "")+"</td>";	   
				    htmlContent += "<td name='init' title='"+this_.getInitValueByJklbh(data.zsj ? data.zsj.initDatas : [])+"' style='text-align:left;vertical-align:middle;'>"+this_.getInitValueByJklbh(data.zsj ? data.zsj.initDatas : [])+"</td>";	   
				   	htmlContent += "</tr>";
				$("#"+this_.param.parent+" #"+this_.id+"_list"+" #"+this_.id+"_nodata").remove();  
			    $("#"+this_.param.parent+"  #"+this_.id+"_list").append(htmlContent);
			    this_.param.data[this_.param.index]=data;
			}else{
				var $tr = $("#"+this_.param.parent+"  #"+this_.id+"_table").find("tr[name='"+id+"']");
				$tr.find("td[name='cxbjh']").text(StringUtil.escapeStr(data.cxj ? data.cxj.bjh : ""));
				$tr.find("td[name='cxxlh']").text(StringUtil.escapeStr(data.cxj ? data.cxj.xlh : ""));
				$tr.find("td[name='cxmc']").text(StringUtil.escapeStr(data.cxj ? ((data.cxj.ywmc||"") + " " + (data.cxj.zwmc||"")) : ""));
				$tr.find("td[name='zsbjh']").text(StringUtil.escapeStr(data.zsj ? data.zsj.bjh : ""));
				$tr.find("td[name='zsxlh']").text(StringUtil.escapeStr(data.zsj ? data.zsj.xlh : ""));
				$tr.find("td[name='zsmc']").text(StringUtil.escapeStr(data.zsj ? ((data.zsj.ywmc||"") + " " + (data.zsj.zwmc||"")) : ""));
				$tr.find("td[name='cxsj']").text((data.cxSj||"").substr(0, 16));
				$tr.find("td[name='zssj']").text((data.zsSj||"").substr(0, 16));
				$tr.find("td[name='czwz']").text(DicAndEnumUtil.getEnumName('installedPositionEnum', ((data.cxj?data.cxj.wz:"") || (data.zsj?data.zsj.wz:"") || "11")));
				$tr.find("td[name='czyy']").text(StringUtil.escapeStr(data.cxBz || data.zsBz || ""));
				$tr.find("td[name='init']").text(this_.getInitValueByJklbh(data.zsj ? data.zsj.initDatas : []));
				this_.param.data[id]=data;
			}
		},
		// 根据监控类编号获取初始化值
		getInitValueByJklbh : function(initDatas){
			var result = "";
			$(initDatas||[]).each(function(i, obj){
				if(obj.csz){
					if(MonitorUnitUtil.isCal(obj.jklbh)){
						result += obj.csz + " ";
					}else{
						result +=  obj.csz + MonitorUnitUtil.getMonitorUnit(obj.jklbh)+ " ";
					}
				}
			});
			return result;
		},
		editReplacementRecord:function(obj){
			var id = $(obj).attr("index");
			this.openReplaceWin(this.param.data[id],id)
		},
		viewReplacementRecord : function(obj){
			var this_ = this;
			var id = $(obj).attr("index");
			open_win_installationlist_replace.show({
				data:this_.param.data[id]||{},//原值，在弹窗中默认勾选
				jx:this_.param.fjjx,
				fjzch:this_.param.fjzch,
				fdjsl:this_.param.fdjsl,
				ope_type:3,
				dprtcode:userJgdm, //机构代码
				callback: function(data){//回调函数			
					this_.appendHtml(data,id);
					
				}
			});
		},
		delReplacementRecord:function(obj){
			var id = $(obj).attr("index");
			var this_=this;
			$(obj).parent().parent().remove();
			var table = document.getElementById(this_.id+"_table");
			var row = $("#"+this_.param.parent+"  #"+this_.id+"_table").find("tr").length;
			if(row == 1){
				$("#"+this_.param.parent+ " #"+this_.id+"_list").append("<tr id='replacementRecord_nodata'><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");
			}
			delete(this_.param.data[id]);
		},
		
		
};