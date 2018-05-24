insert into t_menu (id, menucode, menuname, menufname, menutype, parentid, menuorder, path, fullorder, iconpath,XTLX) values('015031','project2:production:main','生产指令','Production Order',2,'015',31,'project2/production/main','','icon-hand-right','AMEMS');

insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('015031010','project2:production:main:01','新增','015031','生产指令');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('015031020','project2:production:main:02','编辑','015031','生产指令');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('015031030','project2:production:main:03','删除','015031','生产指令');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('015031040','project2:production:main:04','改版','015031','生产指令');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('015031050','project2:production:main:05','关闭','015031','生产指令');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('015031060','project2:production:main:06','审核','015031','生产指令');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('015031070','project2:production:main:07','审批','015031','生产指令');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('015031080','project2:production:main:08','启用','015031','生产指令');