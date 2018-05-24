
	var SubTable = {
			
			 config : {},
			 
			 setConfig : function(_config){
				 this.config = _config;
			 },
			 /**
			  * 添加子table
			  * @param rowId
			  */
			 addSubTable : function(rowId){
				var previousRow = $("#"+rowId);
				var subTitleList = [];
				var parentTableColumeLength = $("#"+this.config.tableId+" thead:first th").length - 1;
				// 动态添加行
				if(this.config.dynamicAdd){
					var width = SubTable.config.rowOperationWidth || "";
					subTitleList.push(['<th style="vertical-align:middle;width : '+width+';">',
					                   '<button type="button" id="addTable" class="btn btn-default btn-xs" onclick="SubTable.addSubRow(\''+rowId+'\')" title="新增行">',
					                   '<i class="icon-plus cursor-pointer color-default cursor-pointer"></i>',
					                   '</button>',
					                   '</th>'
					                   ].join(''));
				}
				// 拼装表头
				$.each(this.config.subColumeNames_cn, function(i ,obj){
					var width = SubTable.config.subColumeWidth ? SubTable.config.subColumeWidth[i] : "";
					subTitleList.push(['<th style="width : '+width+';"><div class="font-size-12 line-height-18" >',
					                   obj,
					                   '</div><div class="font-size-9 line-height-18" >',
					                   (SubTable.config.subColumeNames_eng)[i],
					                   '</div></th>'
					                   ].join(''));
				});
				// 2级表头
				var subTitle_secord = "";
				if(this.config.subColumeNames_secord_cn){
					subTitle_secord += "<tr>";
					if(this.config.dynamicAdd){
						subTitle_secord += '<th><div class="font-size-12 line-height-18" >行操作</div><div class="font-size-9 line-height-18" >Operation</div></th>';
					}
					$.each(this.config.subColumeNames_secord_cn, function(i ,obj){
						subTitle_secord += '<th colspan="'+SubTable.config.subColumeNames_secord_colspan[i]+'">';
						subTitle_secord += '<div class="font-size-12 line-height-18" >'+obj+'</div>';
						subTitle_secord += '<div class="font-size-9 line-height-18" >'+SubTable.config.subColumeNames_secord_eng[i]+'</div>';
						subTitle_secord += '</th>';
					});
					subTitle_secord += "</tr>";
				}
				var currentRow = ['<tr parent="'+this.config.tableId+'_'+rowId+'">',
				                  '<td style="vertical-align:middle;">',
				                  this.config.title,
				                  '</td>',
				                  '<td colspan="'+parentTableColumeLength+'">',
				                  '<table class="table table-thin table-bordered text-center table-set" id="subtable_'+this.config.tableId+'_'+rowId+'">',
				                  '<thead>',
				                  subTitle_secord,
				                  '<tr>',
				                  subTitleList.join(''),
				                  '</tr>',
				                  '<tr class="non-choice">',
				                  '<td colspan="'+(this.config.dynamicAdd?this.config.subColumeNames_cn.length+1:this.config.subColumeNames_cn.length)+'">暂无数据 No data.</td>',
				                  '</tr>',
				                  '</thead>',
				                  '</table>',
				                  '</td>',
				                  '</tr>'
				                  ].join('');
				previousRow.after(currentRow);
				
			},
			
			/**
			 * 显示子table
			 * @param rowId
			 */
			toggleSubTable : function (rowId){
				// 隐藏其他的子table
				//$("tr[parent^='"+this.config.tableId+'_'+"']").hide();
				var subRow = $("tr[parent='"+this.config.tableId+'_'+rowId+"']");
				if(subRow.length >= 1){
					subRow.toggle();
				}else {
					this.addSubTable(rowId);
				}
				
				$("#"+this.config.tableId+" .icon-chevron-up").removeClass("icon-chevron-up").addClass("icon-chevron-down");
				$("#"+rowId+" .icon-chevron-down").removeClass("icon-chevron-down").addClass("icon-chevron-up");
				
			},
			
			/**
			 * 添加子row
			 * @param rowId
			 */
			addSubRow : function (rowId){
				var subtable = $('#subtable_'+this.config.tableId+'_'+rowId);
				subtable.find(".non-choice").remove();
				var rowNum = this.getMaxSubRowNum(rowId);
				var subrow = "<tr id='"+rowId+"_"+rowNum+"'>";
				if(this.config.dynamicAdd){
					subrow += ['<td style="vertical-align:middle;">',
					           '<button type="button" class="btn btn-default btn-xs" onclick="SubTable.deleteSubRow(this)" title="删除行">',
							   '<i class="icon-minus cursor-pointer color-default cursor-pointer"></i>',
							   '</button>',
							   '</td>'
					           ].join("");
				}
				if(this.config.customColumnContent){
					subrow += this.config.customColumnContent;
				}else{
					$.each(this.config.subColumeNames_cn, function(i, obj){
						subrow += "<td></td>";
					});
				}
				subrow += "</tr>";
				subtable.append(subrow);
				// 回调方法
				if(this.config.afterAddRow){
					if ((typeof this.config.afterAddRow) == "function") {
						this.config.afterAddRow.apply(0, [rowId,rowId+"_"+rowNum]);
					}
				}
			},
			
			/**
			 * 删除子row
			 * @param btn
			 */
			deleteSubRow : function(btn){
				var tr = $(btn).parent().parent();
				var subRowId = tr.attr("id");
				tr.remove();
				// 回调方法
				if(this.config.afterDeleteRow){
					if ((typeof this.config.afterDeleteRow) == "function") {
						this.config.afterDeleteRow.apply(0, [subRowId]);
					}
				}
			},
			
			/**
			 * 获取最大行号
			 * @param rowId
			 * @returns
			 */
			getMaxSubRowNum : function(rowId){
				return $('#subtable_'+this.config.tableId+'_'+rowId + ' >tbody>tr').length;
			}
			
			
	}
	
