insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100010', 'osms:home', '首页', 'Home', 2, '0', null, '/main/revision/revisionlist', '', '10', 'fa fa-home', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100020', 'osms:revision', '工包管理', 'Revision Management', 1, '0', null, '', '', '20', 'fa fa-archive', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100020010', 'osms:revision:control', '工包控制', 'Revision Control', 2, '100020', 10, '/main/revision/revisioncontrol', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100020020', 'osms:revision:stage:config', '配置阶段', 'Stage Configuration', 2, '100020', 20, '/main/stage/${rid}/configstage', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100020030', 'osms:revision:earn:config', '收费工时', 'Earn Hr', 2, '100020', 30, '/main/earnconf/${rid}/goEarnconf', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100020040', 'osms:revision:nrcworkhour:config', '非例行工时配置', 'NRC Workhour Configuration', 2, '100020', 40, '/main/nrcworkhourcfg/${rid}/goToConfigPage', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100030', 'osms:workorder', '工单管理', 'Work Order Management', 1, '0', null, '', '', '30', 'fa fa-list-ol', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100030010', 'osms:workorder:list', '工单列表', 'Work Order List', 2, '100030', 10, '/main/work/${rid}/listpage', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100030020', 'osms:workorder:listbyday', '工单按天汇总', 'Work Order List By Day', 2, '100030', 20, '/main/work/${rid}/workorderbyday', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100040', 'osms:spenthour', '实际工时', 'Spent Hr', 1, '0', null, '', '', '40', 'iconbg-spent', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100040010', 'osms:spenthour:dailycard', '派卡与报钟', ' Assign And Booking', 2, '100040', 10, '/main/dailycard/gotoDailycardPage', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100040020', 'osms:spenthour:batchbookonoff', '批量报钟退钟', 'Batch  Booking', 2, '100040', 20, '/main/batchBookOnOff/gotoLoginBatchBookOnOffPage', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100120', 'remotesystem:amems', '维修工程管理系统', 'AMEMS', 2, '0', null, '/remotesystem/amems', '', '110', 'fa fa-envira', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100040030', 'osms:spenthour:personaltiming', '个人报钟退钟', 'Personal  Booking', 2, '100040', 30, '/main/personalTiming/gotoPersonalTimingPage', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100040040', 'osms:spenthour:cardoperation', '已派卡操作', 'Card Operation', 2, '100040', 40, '/main/dailycard/gotoCardOperationPage', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100040050', 'osms:spenthour:overhead', '培训管理', 'Overhead', 2, '100040', 50, '/main/batchBookOnOff/gotoLoginBatchOverheadPage', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100040060', 'osms:spenthour:remainhours', '剩余工时', 'Remaining Hr', 2, '100040', 60, '/main/remainhours/page', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100040070', 'osms:spenthour:assignInformation', '派卡统计', 'Assign Information', 2, '100040', 70, '/main/dailycard/gotoAssignInformation', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100040080', 'osms:spenthour:bookondifferencereport', '派卡差异报表', 'BookOn Difference Report', 2, '100040', 80, '/main/dailycard/gotoBookOnDifferenceReport', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100040090', 'osms:spenthour:spentplanning', '实际/ 计划工时', 'Spent hrs And Planning hr', 2, '100040', 90, '/main/spentHrsPlanningHrs/gotoSpentPlanning', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100040100', 'osms:spenthour:quickbookdisplayorhide', '快速报钟显示或隐藏', 'Quick  Booking Display Or Hide', 2, '100040', 100, '/main/quickBookDisplayOrHide/gotoQuickBookDisplayOrHide', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100040110', 'osms:spenthour:spentnoisnout', '有报钟无考勤统计', ' Booking Without Attendance ', 2, '100040', 110, '/main/empAvailable/goSpentNoInOut', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100040120', 'osms:spenthour:inoutnospent', '有考勤无报钟统计', 'Attendance Without  Booking', 2, '100040', 120, '/main/empAvailable/goInOutNoSpent', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100050', 'osms:report', '报表', 'Reports', 1, '0', null, '', '', '50', 'fa fa-table', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100050010', 'osms:report:showreport', '统计报表(计划部)', 'Statistical Reports(Plan Dept)', 2, '100050', 10, '/main/report/showreport', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100050020', 'osms:report:report2', '系统报表(生产部)', 'System Reports(Plan Dept)', 2, '100050', 20, '/main/report2/view', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100060', 'osms:batchprocess', '批量处理', 'Batch Processing', 2, '0', null, '/main/groupchange/${rid}/home', '', '60', 'fa fa-puzzle-piece', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100070', 'osms:config', '配置管理', 'CM', 1, '0', null, '', '', '70', 'fa fa-gear', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100070010', 'osms:config:emptradecfg', '用户关联工作组', 'User Related Trade', 2, '100070', 10, '/main/emptradecfg/goToConfigPage', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100070020', 'osms:config:depttradecfg', '部门关联工作组', 'Dept Related Trade', 2, '100070', 20, '/main/depttradecfg/goToConfigPage', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100070030', 'osms:config:dinnerconfig', '用餐时间配置', 'Dinner Time Configuration', 2, '100070', 30, '/main/dinnerconfig', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100070040', 'osms:config:miconfig', '机器/检查工时配置', 'M/I Configuration', 2, '100070', 40, '/main/miconfig/miConfigList', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100070050', 'osms:config:hangar', '机库维护', 'Hangar Maintenance', 2, '100070', 50, '/sys/hangar/addhangarview', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100020050', 'osms:config:revisionsettop', '工包标记', 'Revision Tagging', 2, '100020', 50, '/main/revision/revisionsettop', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100080', 'osms:database', '数据库管理', 'Database Management', 1, '0', null, '', '', '80', 'fa fa-database', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100080010', 'osms:database:taskdb', '工卡维护', 'Job card maintenance', 2, '100080', 10, '/taskdb', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100080020', 'osms:database:accessmhdb', '盖板工时', 'Accessl Manhour', 2, '100080', 20, '/accessmhdb', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100090', 'osms:avaliable', '可用工时管理', 'Avaliable Hrs Management', 1, '0', null, '', '', '90', 'iconbg-available', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100090010', 'osms:avaliable:shiftconfig', '班次配置', 'Shift Config', 2, '100090', 10, '/main/shiftconfig', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100090030', 'osms:avaliable:overtimeconfig', '加班起算时间配置', 'Overtime starting time configuration', 2, '100090', 30, '/main/overtimeconfig', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100090040', 'osms:avaliable:shiftinout', '可用工时', 'Avaliable Hrs', 2, '100090', 40, '/main/shiftInOut/goShiftInOut', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100090050', 'osms:avaliable:limitinoutmodifytimeconfig', '限制考勤修改时间配置', 'Limit IN/OUT Operation Time Config', 2, '100090', 50, '/main/availableHrsConfig/goLimitInOutModifyTimeConfig', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100100', 'osms:import', '导入管理', 'Import Management', 1, '0', null, '', '', '100', 'fa fa-bookmark-o', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100100010', 'osms:import:files', '导入文件列表', 'Import Files', 2, '100100', 10, '/importfiles', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100100020', 'osms:import:faileddata', '错误处理', 'Error Handling', 2, '100100', 20, '/faileddata', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100110', 'osms:email', '邮件管理', 'Email Management', 1, '0', null, '', '', '110', 'fa fa-envelope-o', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100110010', 'osms:email:config', '邮件配置', 'Mail Configuration', 2, '100110', 10, '/main/paramconfig/goparamconfigpage', '', '', '', 'OSMS');

insert into t_menu (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('100110020', 'osms:email:send', '邮件发送', 'Mail Send', 2, '100110', 20, '/main/mailsend/gomailsendpage', '', '', '', 'OSMS');

