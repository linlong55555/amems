<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

<style>

/* AMEMS 更新日志的样式 */

.log-container{
 	position: relative;
    padding-top: 4px;
    margin-bottom: 32px;
}
.log-container:before {
    content: "";
    display: block;
    position: absolute;
    left: 96px;
    top: 20px;
    bottom: 0;
    background-color: #AFDCF8;
    width: 5px;
    border-width: 0 5px
}
.log-header{
 	display: block;
    clear: both;
    margin: 0 0 18px;
    margin-left: 86px;
}
.log-header-icon{
	width: 28px;
 	height: 28px;
	font-family: 'ecoico';
	speak: none;
	font-style: normal;
	font-weight: normal;
	font-variant: normal;
	text-transform: none;
	font-size: 1.4em;
	line-height: 28px;
	-webkit-font-smoothing: antialiased;
	position: absolute;
	color: #fff;
	background: #46a4da;
	border-radius: 50%;
	box-shadow: 0 0 0 3px #afdcf8;
	text-align: center;
	left: 85px;
	top: 4px;
}
.log-header i{
	font-size:18px;
}

.log-header-info{
  left: 105px;
	font-size:24px;
	display:block;
	margin-left:38px;
	color:#05A1F6;
	font-weight:bolder;
}
/* 年份 */
.log-year{
 margin-top:15px;
 margin-bottom:15px;
}

.log-year-info{
	float: left;
	width: 116px;
	padding-left:5px;
	position: relative;
	cursor:pointer;
}
.log-year-date{
 	display: inline-block;
    width: 55px;
    text-align: right;
    color: #EC6A13;
    font-size:20px;
    font-weight:bold;
    margin-right:5px;
}
.log-year-info i{
	font-size:16px;
	margin-left:6px;
	color: #EC6A13;
}
.log-year-box{
	margin-left:86px;
	padding-top:4px;
    font-size:16px;
	display: block;
	color:#EC6A13;
	 
}
.log-year-box span{
	cursor:pointer;
}
.log-body-item{
  margin-top:20px;
}

.log-body{
height:0px;
overflow:hidden;
-webkit-transform: translateX(100%);
-ms-transform: scale(0.5);
-o-transform: scale(0.5);
transform: scale(0.5);
opacity: 0;
-webkit-transition: all 500ms;
-o-transition: 500ms;
transition: 500ms; 
}

.log-items.active .log-body{
 height:auto;
-webkit-transform: translateX(0);
-ms-transform: scale(1);
-o-transform: scale(1);
transform: scale(1);
opacity: 1; 
}
  
.log-body-info{
	float: left;
	width: 100px;
	padding-left:5px;
	text-align:right;
	padding-right:20px;
	font-size:16px;
	
	position: relative;
}
.log-body-date{
 	display: inline-block;
    width: 55px;
    text-align: right;
    font-size:16px;
    color: #58A6FB;
    margin-top:8px;
}
.log-body-icon{
	width: 14px;
	height: 14px;
	font-family: 'ecoico';
	speak: none;
	font-style: normal;
	font-weight: normal;
	font-variant: normal;
	text-transform: none;
	font-size: 1.4em;
	line-height: 40px;
	-webkit-font-smoothing: antialiased;
	position: absolute;
	color: #fff;
	background: #46a4da;
	border-radius: 50%;
	box-shadow: 0 0 0 4px #afdcf8;
	text-align: center;
	left: 90px;
	top: 12px;
	
}
.log-body-icon i{
	font-size:22px;
	font-weight:bolder;
}
.log-body-box{
	margin-left:126px;
	background: #6CBFEE;
	color: #fff;
	padding: 15px;;
	font-size: 1.2em;
	font-weight: 300;
	line-height: 1.4;
	position: relative;
}
.log-body-box h2{
    margin-top: 0px;
    font-size:20px;
	padding: 0 0 10px 0;
	border-bottom: 1px solid rgba(255,255,255,0.4);
}
.log-body-box:after{
  	right: 100%;
	border: solid transparent;
	content: " ";
	height: 0;
	width: 0;
	position: absolute;
	pointer-events: none;
	border-right-color: #6CBFEE;
	border-width: 10px;
	top: 10px;
}
.overflowX-hidden{
overflow-x:hidden !important;
}

</style>
<div class="modal fade in modal-new" id="update_log_alert_Modal" tabindex="-1" role="dialog"  aria-labelledby="update_log_alert_Modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:75%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">更新日志</div>
							<div class="font-size-12" id="modalHeadENG">Update Log</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body overflowX-hidden" >
            <div id='updateLog' style="margin-top:15px;">
		
		    </div>
            </div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfooterbtn">
	                    	 <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
						    </button>
	                    </span>
	               	</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/static/js/thjw/sys/updatelog/update_log.js"></script>