<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
	
	<div id="edit_attach_div" class="input-group col-xs-12">
	    <span class="input-group-addon inputgroupbordernone" style=''>
	    	<label class='margin-left-0 label-input' >
	    		<input name="attachBox" type='checkbox' onclick="attachmentsSingleUtil.showOrHideAttach(this)" />
	    		&nbsp;
	    	</label>
	    </span>
		<span id="fileuploaderSingle" class='singlefile input-group-btn'>
		</span>
	    <div class="wbwjmSingle padding-left-0 padding-right-0 font-size-12" ></div>
    </div>
    <div id="view_attach_div" class="tag-set" style="margin-top: 5px;">
    	
    </div>
	
<script type="text/javascript" src="${ctx}/static/js/thjw/common/attachments/attachments_single_edit.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
