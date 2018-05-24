.
insert into T_MENU (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('040020030', 'demo:auth:station', '岗位授权', 'Station Authorization', 2, '040020', 30, 'demo/auth/station', null, null, null, 'AMEMS');
insert into T_MENU (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('040020040', 'demo:auth:assessment', '授权评估', 'Authorization Assessment', 2, '040020', 40, 'demo/auth/assessment', null, null, null, 'AMEMS');
insert into T_MENU (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('040020050', 'demo:auth:audit', '资质审核', 'Qualifications Check', 2, '040020', 50, 'demo/auth/audit', null, null, null, 'AMEMS');
insert into T_MENU (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('c013d584-0649-42f4-b791-5d17c9c001df', 'demo:security:item', '质量安全审核', 'Quality and safety audit', 2, '040120', 10, 'demo/security/item', null, null, null, 'AMEMS');
insert into T_MENU (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('6820c997-d5e5-4139-b4a6-3e7d3dd3e115', 'demo:security:audit', '审核项目单', 'Audit item list', 2, '040120', 11, 'demo/security/audit', null, null, null, 'AMEMS');
insert into T_MENU (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('ccf97095-8520-4032-8338-8edadf188ce6', 'demo:security:summary', '质量安全审核发现问题汇总单', 'Quality and safety audit summary', 2, '040120', 20, 'demo/security/summary', null, null, null, 'AMEMS');
insert into T_MENU (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('775bd71c-faf8-4380-9b69-5eb0fcba4d02', 'demo:security:report', '审核报告', 'Audit report', 2, '040120', 40, 'demo/security/report', null, null, null, 'AMEMS');
insert into T_MENU (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('040120', 'quality:110', 'QA012 质量审核', 'Audit', 1, '040', 120, null, null, null, 'icon-bolt', 'AMEMS');
insert into T_MENU (ID, MENUCODE, MENUNAME, MENUFNAME, MENUTYPE, PARENTID, MENUORDER, PATH, REMARK, FULLORDER, ICONPATH, XTLX)
values ('040020', 'quality:020', 'QA002 人员授权管理', 'Authorization', 1, '040', 20, null, null, null, 'icon-male', 'AMEMS');
