update d_001 t set xlms='物料主数据' where t.lxid = 1;
update d_001 t set xlms='维修技术人员档案' where t.lxid = 2;
update d_001 t set xlms='工卡/工单/培训计划' where t.lxid = 4;
update d_001 t set xlms='维修技术人员档案' where t.lxid = 10;
update d_001 t set xlms='维修技术人员档案' where t.lxid = 11;
update d_001 t set xlms='客户信息' where t.lxid = 9;
update d_001 t set xlms='工程指令/维修方案/工卡/工单' where t.lxid = 17;
update d_001 t set xlms='工程指令/工卡/工单' where t.lxid = 23;
update d_001 t set xlms='课程管理' where t.lxid = 31;
update d_001 t set xlms='课程管理' where t.lxid = 32;
update d_001 t set xlms='课程管理' where t.lxid = 33;
update d_001 t set xlms='培训计划' where t.lxid = 34;
update d_001 t set xlms='装机清单' where t.lxid = 51;
update d_001 t set xlms='装机清单' where t.lxid = 52;
update d_001 t set xlms='FLB' where t.lxid = 53;
update d_001 t set xlms='客户信息' where t.lxid = 61;
update d_001 t set xlms='项目信息' where t.lxid = 62;
update d_001 t set xlms='项目信息' where t.lxid = 63;
update d_001 t set xlms='工包管理' where t.lxid = 71;
update d_001 t set xlms='质量审核' where t.lxid = 72;
update d_001 t set xlms='质量审核' where t.lxid = 73;
update d_001 t set xlms='FLB' where t.lxid = 74;
update d_001 t set xlms='DD故障保留' where t.lxid = 75;
update d_001 t set xlms='DD故障保留' where t.lxid = 76;
update d_001 t set xlms='DD故障保留' where t.lxid = 77;
update d_001 t set xlms='DD故障保留' where t.lxid = 78;
update d_001 t set xlms='合同管理' where t.lxid = 81;
update d_001 t set xlms='合同管理' where t.lxid = 82;
update d_001 t set xlms='合同管理' where t.lxid = 83;
update d_001 t set xlms='需求管理' where t.lxid = 84;


insert into t_button (id, buttonCode, buttonName, menuId,path, remark) values ('040010010080','maintenance:maintenancemanuals:manage:08','上传ZIP','040010010','','文档管理');
insert into t_button (id, buttonCode, buttonName, menuId,path, remark) values ('040010010090','maintenance:maintenancemanuals:manage:09','移动','040010010','','文档管理');
insert into t_button (id, buttonCode, buttonName, menuId,path, remark) values ('040010010100','maintenance:maintenancemanuals:manage:10','下载文件夹','040010010','','文档管理');

insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('040180080','maintenance:maintenancemanuals:manage1:08','上传ZIP','040180','质量文档管理');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('040180090','maintenance:maintenancemanuals:manage1:09','移动','040180','质量文档管理');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('040180100','maintenance:maintenancemanuals:manage1:10','下载文件夹','040180','质量文档管理');

insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('015100080','maintenance:maintenancemanuals:manage:08','上传ZIP','015100','工程文件管理');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('015100090','maintenance:maintenancemanuals:manage:09','移动','015100','工程文件管理');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('015100100','maintenance:maintenancemanuals:manage:10','下载文件夹','015100','工程文件管理');

insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('025900080','produce:file:scmain:08','上传ZIP','025900','生产文件管理');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('025900090','produce:file:scmain:09','移动','025900','生产文件管理');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('025900100','produce:file:scmain:10','下载文件夹','025900','生产文件管理');

insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('030900080','training:file:zlmain:08','上传ZIP','030900','培训文件管理');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('030900090','training:file:zlmain:09','移动','030900','培训文件管理');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('030900100','training:file:zlmain:10','下载文件夹','030900','培训文件管理');

insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('055900080','material:file:hcmain:08','上传ZIP','055900','航材文件管理');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('055900090','material:file:hcmain:09','移动','055900','航材文件管理');
insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('055900100','material:file:hcmain:10','下载文件夹','055900','航材文件管理');
