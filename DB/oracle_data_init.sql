--系统信息表
insert into t_sys_info (syscode, ms, zt) values ('AMEMS', '航空维修工程管理系统', 1);
insert into t_sys_info (syscode, ms, zt) values ('OSMS', '现场执行管理系统', 1);
insert into t_sys_info (syscode, ms, zt) values ('ISCHEDULE', '任务管理系统', 1);

--系统配置表
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('AMEMS', 'LOGIN_OUT', '退出系统转向URL', '', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('AMEMS', 'SESSION_LOSE', 'session失效转向URL', '', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('AMEMS', 'LOGIN_ERROR', '登录失败转向URL', '', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('AMEMS', 'ATT_ROOT_PATH', '文件根目录', '/amems', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('AMEMS', 'DEFAULT_PASSWORD', '默认密码', '123456', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('AMEMS', 'MAC_LIMIT', 'mac限制', 'false', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('AMEMS', 'SUPER_AGENCY', '超级机构', 'SuperDept', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('AMEMS', 'PAGING_NUM', '分页数', '20', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('AMEMS', 'AGENCY_TYPE', '机构类型', '平台', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('AMEMS', 'AGENCY_TYPE', '机构类型', '135', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('AMEMS', 'AGENCY_TYPE', '机构类型', '145', '', '', 1);

insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('OSMS', 'LOGIN_OUT', '退出系统转向URL', '', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('OSMS', 'SESSION_LOSE', 'session失效转向URL', '', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('OSMS', 'LOGIN_ERROR', '登录失败转向URL', '', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('OSMS', 'ATT_ROOT_PATH', '文件根目录', '/osms', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('OSMS', 'DEFAULT_PASSWORD', '默认密码', '123456', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('OSMS', 'MAC_LIMIT', 'mac限制', 'false', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('OSMS', 'SUPER_AGENCY', '超级机构', 'SuperDept', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('OSMS', 'PAGING_NUM', '分页数', '20', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('OSMS', 'AGENCY_TYPE', '机构类型', '平台', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('OSMS', 'AGENCY_TYPE', '机构类型', '135', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('OSMS', 'AGENCY_TYPE', '机构类型', '145', '', '', 1);

insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('ISCHEDULE', 'LOGIN_OUT', '退出系统转向URL', '', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('ISCHEDULE', 'SESSION_LOSE', 'session失效转向URL', '', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('ISCHEDULE', 'LOGIN_ERROR', '登录失败转向URL', '', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('ISCHEDULE', 'ATT_ROOT_PATH', '文件根目录', '/ischedule', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('ISCHEDULE', 'DEFAULT_PASSWORD', '默认密码', '123456', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('ISCHEDULE', 'MAC_LIMIT', 'mac限制', 'false', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('ISCHEDULE', 'SUPER_AGENCY', '超级机构', 'SuperDept', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('ISCHEDULE', 'PAGING_NUM', '分页数', '20', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('ISCHEDULE', 'AGENCY_TYPE', '机构类型', '平台', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('ISCHEDULE', 'AGENCY_TYPE', '机构类型', '135', '', '', 1);
insert into t_sys_config (syscode, pzbm, ms, value_1, value_2, value_3, zt)
values ('ISCHEDULE', 'AGENCY_TYPE', '机构类型', '145', '', '', 1);

--系统机构关系
insert into t_sys_dprt (syscode, dprtCode) values ('AMEMS', 'SuperDept');
insert into t_sys_dprt (syscode, dprtCode) values ('OSMS', 'SuperDept');
insert into t_sys_dprt (syscode, dprtCode) values ('ISCHEDULE', 'SuperDept');

--t_department 组织机构   --上线前需要修改
insert into t_department (id, dprtCode, dprtName, parentId, dprtType, remark)
values ('SuperDept', 'SuperDept', '平台管理机构', '0', '1', '组织机构请勿删除');
insert into t_deptInfo (id, deptType, remark, zt)
values ('SuperDept', 'SuperDept', '平台管理机构', 1);

--t_user 用户表
insert into t_user (id, userName, password, realName, cellphone, phone, sex, state, lastVisit, lastIp, bmdm, jgdm, drzhid)
values ('admin', 'admin', '', '超级管理员', '', '', 1, 1, null, null, '', 'SuperDept', 'admin');
insert into t_login (id, userName, password, state, jgdm)
values ('admin', 'admin', '87be1aeaa0b46573a862120df983577d',  1, 'SuperDept');

--t_menu 菜单表


--t_button 按键管理表


--t_threshold 系统阀值设置
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('JSWJ', 15, 30, null, '-1');
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('TGZL', 15, 30, null, '-1');
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('HCSM', 10, 20, null, '-1');
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('PXTX', 1, 5, 11, '-1');
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('GZBLD', 5, 10, null, '-1');
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('SQZZ', 5, 10, null, '-1');
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('DTZZ', 5, 10, null, '-1');
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('TDYQ', 5, 10, null, '-1');
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('GJJK', 5, 10, null, '-1');
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('GYSYXQ', 15, 30, null, '-1');
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('ZWZLSCQX', 1, 5, null, '-1');
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('RYKCPX', 10, 30, null, '-1');
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('GWDQJK', 10, 30, null, '-1');
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('JXDQJK', 5, 10, null, '-1');
insert into t_threshold (key, yjts_jb1, yjts_jb2, yjts_jb3, dprtCode) values ('ZLWTFK', 5, 10, null, '-1');


--d_001 数据字典类型表    d_00101 数据字典明细表
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (1, '计量单位', '物料主数据', 1, 1, 1, 0, 1, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 1, 'UN', '个', 1,null, null, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (2, '学历', '维修技术人员档案', 1, 1, 1, 0, 1, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 2, 'BK', '本科',1, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 2, 'SS', '硕士',2, null, null, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (3, '币种', '', 1, 1, 1, 1, 1, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (4, '专业', '工卡/工单/培训计划', 1, 1, 1, 0, 1, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 4, 'AV', 'AV电子', 1, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 4, 'ME', 'ME机械', 2, null, null, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (9, '国家', '客户信息', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (10, '政治面貌', '维修技术人员档案', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (11, '语言水平', '维修技术人员档案', 1, 1, 1, 1, 1, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (8, '适航性资料来源', '', 1, 1, 1, 0, 1, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 8, 'CAAC', 'CAAC',1, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 8, 'FAA', 'FAA',2, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 8, 'EASA', 'EASA',3, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 8, 'AH', 'A'||chr(46)||'H',4, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 8, 'TM', 'T'||chr(46)||'M',5, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 8, 'SIKORSKY', 'SIKORSKY',6, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 8, 'PW', 'P'||chr(38)||'W',7, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 8, '其他', '其他',8, null, null, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (16, '适航性资料类型', '', 1, 1, 1, 0, 1, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 16, 'CAD', 'CAD',1, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 16, 'EAD', 'EAD',2, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 16, 'FAA', 'FAA',3, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 16, 'ASB', 'ASB',4, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 16, 'SB', 'SB',5, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 16, 'SL', 'SL',6, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 16, 'OTHER', '其他',7, null, null, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (17, '工作类别', '工程指令/维修方案/工卡/工单', 1, 1, 1, 0, 1, '-1');


insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (18, '维修方案-项目类别', '', 1, 1, 1, 0, 1, '-1');


insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (21, '技术评估完成类型', '', 1, 1, 1, 0, 1, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (22, '工卡类型', '', 1, 1, 1, 0, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (23, '器材工具必需性', '工程指令/工卡/工单', 1, 1, 1, 0, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (24, '工卡适用单位', '', 1, 1, 1, 1, 1, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (25, 'EO类别', '', 1, 1, 1, 0, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (26, 'EO分级', '', 1, 1, 1, 0, 1, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (31, '课程类别', '课程管理', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (32, '培训形式', '课程管理', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (33, '考试形式', '课程管理', 1, 1, 1, 1, 1, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (34, '培训类别', '培训计划', 1, 1, 1, 1, 1, '-1');


insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (41, 'MEL修改依据', '', 1, 1, 1, 1, 1, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 41, 'MMEL', 'MMEL',1, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 41, 'DDG', 'DDG',2, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 41, 'Supplemental DDG', 'Supplemental DDG',3, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 41, 'STC', 'STC',4, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 41, 'CCAR的相关规章', 'CCAR的相关规章',5, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 41, 'AFM', 'AFM',6, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 41, 'AMM', 'AMM',7, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 41, 'OTHER', 'OTHER',8, null, null, '-1');


insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (51, '物料证书类型', '装机清单', 1, 1, 1, 1, 1, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 51, 'CAAC', '',1, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 51, 'JAA', '',2, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 51, 'EASA', '',3, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 51, 'FAA', '',4, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 51, '许可证', '',5, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 51, '合格证', '',6, null, null, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (52, '装机件类型', '装机清单', 1, 1, 1, 1, 1, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 52, '电子件', '',1, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 52, '电子设备', '',2, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 52, '应急设备', '',3, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 52, '松散设备', '',4, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 52, '其他', '',5, null, null, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (53, '飞行任务类型', 'FLB', 1, 1, 1, 1, 1, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 53, '训练', '',1, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 53, '调机', '',2, null, null, '-1');
insert into d_00101 (id, lxid, sz, mc,xc, whrid, whsj, dprtcode) values (sys_guid(), 53, '载客', '',3, null, null, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (61, '客户分类', '客户信息', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (62, '飞机状态', '项目信息', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (63, '项目种类', '项目信息', 1, 1, 1, 1, 1, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (71, '工包-维修类型', '工包', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (72, '问题等级', '质量审核', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (73, '问题分类', '质量审核', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (74, '燃油单位', 'FLB', 1, 1, 1, 1, 1, '-1');

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (75, 'DD影响服务程度', 'DD故障保留', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (76, 'DD涉及部门', 'DD故障保留', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (77, 'DD运行限制', 'DD故障保留', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (78, 'DD原因代码', 'DD故障保留', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (79, '技术评估单类型', '', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (80, '工具/设备校验-计量方式', '', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (81, '合同交付方式', '合同管理', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (82, '合同器材状态', '合同管理', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (83, '合同支付方式', '合同管理', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (84, '需求提报类别', '需求管理', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (85, '航材来源', '航材管理', 1, 1, 1, 1, 1, '-1');
insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (86, '器材状态', '器材状态', 1, 1, 1, 1, 1, '-1');


--saibong_gz 采番规则表
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('CGRK','入库单号', 'RK', 1, 'YYYYMM', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('DJRW','定检任务单', 'DJ', 1, 'YYYY', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('DJXM','定检项目', null, 1, null, 2, 3, 1, 3, null, '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('FLX','非例行工单', 'NRC', 1, 'YYYY', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('FXJL','飞行记录本', 'FLB', 1, null, 2, 5, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('GCLZ','工程指令', 'EO', 1, 'YYYY', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('HCSX','航材送修单', 'SX', 1, 'YYYY', 2, 5, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('HCTD','航材提订单', 'TD', 1, 'YYYY', 2, 5, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('HCYK','移库单', 'YK', 1, 'YYYYMM', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('HTCG','航材采购合同', 'CHT', 1, 'YYYY', 2, 5, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('JSPG','技术文件评估单', 'PG', 1, 'YYYY', 2, 4, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('JSTB','技术通告', 'TB', 1, 'YYYY', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('JSZL','技术指令', 'TO', 1, 'YYYY', 2, 4, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('LXGD','例行工单', 'RC', 1, 'YYYY', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('LYCK','出库单', 'CK', 1, 'YYYYMM', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('LYSQ','领用申请单', 'LY', 1, 'YYYY', 2, 5, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('SXHT','送修合同', 'XHT', 1, 'YYYY', 2, 5, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('TSFX','特殊飞行情况配置', null, 1, null, 2, 2, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('WXFA','维修方案', 'WX', 1, 'YYYY', 2, 2, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('EOGD','EO工单', null, 1, null, 2, 2, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('XDTZ','修订维修方案', 'XD', 1, 'YYYY', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('XDMEL','修订MEL', 'XD', 1, 'YYYY', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('XDGK','修订工卡', 'XD', 1, 'YYYY', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('YPKD','盘点单', 'PD', 1, 'YYYYMM', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('SHZL','适航指令', '', 1, 'YYYY', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('CJJL','拆解记录单', 'DR', 1, null, 2, 8, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('HCXJ','询价单', 'XJ', 1, 'YYYY', 2, 8, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('PXJL','培训记录', 'PX', 1, 'YYYY', 2, 5, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('JRSQ','借入申请单', 'JR', 1, 'YYYYMM', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('JCBZWX','机场保障部维修记录单', 'JCWX', 1, 'YYYY', 2, 5, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('JCBZXJ','巡检记录单', 'JCXJ', 1, 'YYYY', 2, 5, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('JCBZJY','机场加油单', 'JCJY', 1, 'YYYY', 2, 5, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('HCJY','检验单', 'JY', 1, 'YYYY', 2, 5, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('GJSY','工具使用记录单', 'GJSY', 1, 'YYYYMM', 2, 5, 1, 3, '-', '-1');

insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('SHD','收货单', 'SH', 1, 'YYYYMM', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('XHD','销毁单', 'XH', 1, 'YYYYMM', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('ZWZLSC','自我质量审查单', 'ZL', 1, 'YYYYMM', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('THD','退货单', 'TH', 1, 'YYYYMM', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('BFD','报废单', 'BF', 1, 'YYYYMM', 2, 3, 1, 3, '-', '-1');

insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('DDBLD','DD保留单', 'DD', 1, 'YYYYMM', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('XMBLD','项目保留单', 'BL', 1, 'YYYYMM', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('QXBLD','缺陷保留单', 'BL', 1, 'YYYYMM', 2, 3, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('ZBGD','组包生成工单', null, 1, null, 2, 4, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('SQSQD','授权申请单', null, 1, null, 2, 4, 1, 3, '-', '-1');

insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('43_XMBH','项目编号', null, 1, null, 2, 2, 1, 3, '', '-1');

insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('44_145GDNRC','145工单-NRC', null, 1, null, 2, 3, 1, 3, '', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('45_145GDRTN','145工单-RTN', null, 1, null, 2, 4, 1, 3, '-', '-1');

insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('46_JCDBH','检查单编号', 'CH', 1, 'YYYYMM', 2, 4, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('47_SHXMDBH','审核项目单编号', 'AP', 1, 'YYYYMM', 2, 4, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('48_WTDBH','问题单编号', 'PL', 1, 'YYYYMM', 2, 4, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('49_WTBH','问题编号', 'PR', 1, 'YYYYMM', 2, 4, 1, 3, '-', '-1');
insert into saibong_gz (CFKEY, cfsm, G_GDWZ, G_CFZCSX, T_RQGS, T_CFZCSX, L_LSHCD, L_TBGZ, L_CFZCSX, FGF, DPRTCODE) values ('50_ZLSHBGBH','质量审核报告编号', 'AR', 1, 'YYYYMM', 2, 4, 1, 3, '-', '-1');


--d_006 监控大类
insert into d_006 (JKFLBH, MS, PXH) values ('1D', '日历', 1);
insert into d_006 (JKFLBH, MS, PXH) values ('2T', '时间', 2);
insert into d_006 (JKFLBH, MS, PXH) values ('3C', '循环', 3);

--d_00601 监控设置项
insert into d_00601 (JKLBH, MS, PXH, JKFLBH) values ('1_10', '日历', 1, '1D');
insert into d_00601 (JKLBH, MS, PXH, JKFLBH) values ('2_10_FH', '飞行时间', 2, '2T');
insert into d_00601 (JKLBH, MS, PXH, JKFLBH) values ('2_20_AH', 'APU时间', 4, '2T');
insert into d_00601 (JKLBH, MS, PXH, JKFLBH) values ('2_30_EH', '发动机时间', 6, '2T');
insert into d_00601 (JKLBH, MS, PXH, JKFLBH) values ('3_10_FC', '飞行循环', 3, '3C');
insert into d_00601 (JKLBH, MS, PXH, JKFLBH) values ('3_20_AC', 'APU循环', 5, '3C');
insert into d_00601 (JKLBH, MS, PXH, JKFLBH) values ('3_30_EC', '发动机循环', 7, '3C');


--s_001 任务定义表
insert into s_001 (RWBH, RWMC, RWLX, RWZT, ZXPC, ZXSJ, YXST, IS_KFP) values ('9002', 'AEMES配置数据同步到PBS', 2, 1, '0 0 5,12 * * ?', to_date('12-12-2017 09:55:16', 'dd-mm-yyyy hh24:mi:ss'), 'com.eray.schedule.job.SynConfigDataJob', null);
insert into s_001 (RWBH, RWMC, RWLX, RWZT, ZXPC, ZXSJ, YXST, IS_KFP) values ('5002', '基地接收', 2, 0, '0 0 1 * * ?', null, 'com.eray.schedule.job.BaseSynchronizationJob', null);
insert into s_001 (RWBH, RWMC, RWLX, RWZT, ZXPC, ZXSJ, YXST, IS_KFP) values ('5003', '飞行队接收', 2, 0, '0 5 1 * * ?', null, 'com.eray.schedule.job.FlightTeamSynchronizationJob', null);
insert into s_001 (RWBH, RWMC, RWLX, RWZT, ZXPC, ZXSJ, YXST, IS_KFP) values ('4001', '修改飞机颜色', 2, 0, '0 0/30 * ? * *', null, 'com.eray.schedule.job.CheckHangarStatusJob', null);
insert into s_001 (RWBH, RWMC, RWLX, RWZT, ZXPC, ZXSJ, YXST, IS_KFP) values ('5001', '航务消息接收', 2, 0, '0 0/30 * * * ? *', null, 'com.eray.schedule.job.MessageReleaseJob', null);
insert into s_001 (RWBH, RWMC, RWLX, RWZT, ZXPC, ZXSJ, YXST, IS_KFP) values ('9004', 'SpentHours工时每天定时拆分', 2, 1, '0 59 23 ? * *', to_date('03-11-2017 00:00:16', 'dd-mm-yyyy hh24:mi:ss'), 'com.eray.schedule.job.SpentHoursJob', null);
insert into s_001 (RWBH, RWMC, RWLX, RWZT, ZXPC, ZXSJ, YXST, IS_KFP) values ('9005', 'Overhead拆分', 2, 1, '0 59 23 ? * *', to_date('04-11-2017 23:58:46', 'dd-mm-yyyy hh24:mi:ss'), 'com.eray.schedule.job.OverheadJob', null);
insert into s_001 (RWBH, RWMC, RWLX, RWZT, ZXPC, ZXSJ, YXST, IS_KFP) values ('9001', '常用隔夜生效配置', 2, 0, '59 59 23 ? * *', to_date('02-11-2017 00:02:10', 'dd-mm-yyyy hh24:mi:ss'), 'com.eray.schedule.job.GeneralConfigJob', null);
insert into s_001 (RWBH, RWMC, RWLX, RWZT, ZXPC, ZXSJ, YXST, IS_KFP) values ('9003', 'AvailableResult', 2, 0, '0 0/15 * * * ?', to_date('02-11-2017 00:16:23', 'dd-mm-yyyy hh24:mi:ss'), 'com.eray.schedule.job.AvailableResultJob', null);
insert into s_001 (RWBH, RWMC, RWLX, RWZT, ZXPC, ZXSJ, YXST, IS_KFP) values ('1001', '数据清理进程', 2, 0, '0 0/2 * * * ?', null, 'com.eray.schedule.job.DataCleanJob', null);
insert into s_001 (RWBH, RWMC, RWLX, RWZT, ZXPC, ZXSJ, YXST, IS_KFP) values ('1002', '写入工单信息', 2, 1, '0 0/2 * * * ?', to_date('12-12-2017 09:55:16', 'dd-mm-yyyy hh24:mi:ss'), 'com.eray.schedule.job.SyncWorkSendJob', null);
insert into s_001 (RWBH, RWMC, RWLX, RWZT, ZXPC, ZXSJ, YXST, IS_KFP) values ('1003', '提取工单信息', 2, 1, '0 0/3 * * * ?', to_date('12-12-2017 09:55:16', 'dd-mm-yyyy hh24:mi:ss'), 'com.eray.schedule.job.SyncWorkReceiveJob', null);

--saibong_rule（新采番规则）
--故障保留单
insert into saibong_rule (cfkey,dprtcode,gznr) values ('DDBLD','-1','{"sn:5":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":3},"type":"$N"},"sn:3":{"join":"Y","cont":"YYYYMM","type":"$T"},"sn:4":{"join":"Y","cont":"-","type":"$S"},"sn:1":{"join":"Y","cont":"DD","type":"$S"},"sn:2":{"join":"Y","cont":"-","type":"$S"}}');
--项目保留单
insert into saibong_rule (cfkey,dprtcode,gznr) values ('XMBLD','-1','{"sn:5":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":3},"type":"$N"},"sn:3":{"join":"Y","cont":"YYYYMM","type":"$T"},"sn:4":{"join":"Y","cont":"-","type":"$S"},"sn:1":{"join":"Y","cont":"IR","type":"$S"},"sn:2":{"join":"Y","cont":"-","type":"$S"}}');
--缺陷保留单
insert into saibong_rule (cfkey,dprtcode,gznr) values ('QXBLD','-1','{"sn:5":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":3},"type":"$N"},"sn:3":{"join":"Y","cont":"YYYYMM","type":"$T"},"sn:4":{"join":"Y","cont":"-","type":"$S"},"sn:1":{"join":"Y","cont":"DR","type":"$S"},"sn:2":{"join":"Y","cont":"-","type":"$S"}}');
--135工包
insert into saibong_rule (cfkey,dprtcode,gznr) values ('GB135','-1','{"sn:1":{"join":"N","cont":"E","type":"$S"},"sn:2":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":3},"type":"$N"}}');
--135工单
insert into saibong_rule (cfkey,dprtcode,gznr) values ('GD135','-1','{"sn:1":{"join":"Y","cont":{"field":"gdlx","pattern":{"1":"0","2":"0","3":"1","4":"0","5":"0","9":"2"},"pattern-default":"1"},"type":"$D"},"sn:2":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":4},"type":"$N"}}');
--145工包
insert into saibong_rule (cfkey,dprtcode,gznr) values ('GB145','-1','{"sn:1":{"join":"N","cont":"M","type":"$S"},"sn:2":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":3},"type":"$N"}}');
--145工单
insert into saibong_rule (cfkey,dprtcode,gznr) values ('GD145','-1','{"sn:3":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":4},"type":"$N"},"sn:1":{"join":"Y","cont":{"field":"gbbh","start":0,"end":0},"type":"$D"},"sn:2":{"join":"Y","cont":{"field":"gdlx","pattern":{"1":"0","2":"0","3":"1","4":"0","5":"0","9":"2"},"pattern-default":"1"},"type":"$D"}}');
--航材需求单
insert into saibong_rule (cfkey,dprtcode,gznr) values ('1_HC_XQD','-1','{"sn:5":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":4},"type":"$N"},"sn:3":{"join":"Y","cont":"YYYYMM","type":"$T"},"sn:4":{"join":"Y","cont":"-","type":"$S"},"sn:1":{"join":"Y","cont":"XQ","type":"$S"},"sn:2":{"join":"Y","cont":"-","type":"$S"}}');
--航材收货单
insert into saibong_rule (cfkey,dprtcode,gznr) values ('2_HC_SHD','-1','{"sn:5":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":4},"type":"$N"},"sn:3":{"join":"Y","cont":"YYYYMM","type":"$T"},"sn:4":{"join":"Y","cont":"-","type":"$S"},"sn:1":{"join":"Y","cont":"SI","type":"$S"},"sn:2":{"join":"Y","cont":"-","type":"$S"}}');
--批次号
insert into saibong_rule (cfkey,dprtcode,gznr) values ('3_HC_PCH','-1','{"sn:2":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":3},"type":"$N"},"sn:1":{"join":"Y","cont":"YYMMdd","type":"$T"}}');
--航材出库单
insert into saibong_rule (cfkey,dprtcode,gznr) values ('4_HC_CKD','-1','{"sn:5":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":4},"type":"$N"},"sn:3":{"join":"Y","cont":"YYYYMM","type":"$T"},"sn:4":{"join":"Y","cont":"-","type":"$S"},"sn:1":{"join":"Y","cont":"SO","type":"$S"},"sn:2":{"join":"Y","cont":"-","type":"$S"}}');
--采购合同
insert into saibong_rule (cfkey,dprtcode,gznr) values ('5_HT1_CG','-1','{"sn:2":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":6},"type":"$N"},"sn:1":{"join":"Y","cont":"PO","type":"$S"}}');
--出售合同
insert into saibong_rule (cfkey,dprtcode,gznr) values ('5_HT2_CS','-1','{"sn:2":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":6},"type":"$N"},"sn:1":{"join":"Y","cont":"SO","type":"$S"}}');
--修理合同
insert into saibong_rule (cfkey,dprtcode,gznr) values ('5_HT3_XL','-1','{"sn:2":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":6},"type":"$N"},"sn:1":{"join":"Y","cont":"ER","type":"$S"}}');
--交换合同
insert into saibong_rule (cfkey,dprtcode,gznr) values ('5_HT4_JH','-1','{"sn:2":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":6},"type":"$N"},"sn:1":{"join":"Y","cont":"EX","type":"$S"}}');
--租进合同
insert into saibong_rule (cfkey,dprtcode,gznr) values ('5_HT5_ZJ','-1','{"sn:2":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":6},"type":"$N"},"sn:1":{"join":"Y","cont":"LI","type":"$S"}}');
--租出合同
insert into saibong_rule (cfkey,dprtcode,gznr) values ('5_HT6_ZC','-1','{"sn:2":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":6},"type":"$N"},"sn:1":{"join":"Y","cont":"LO","type":"$S"}}');
--报废单
insert into saibong_rule (cfkey,dprtcode,gznr) values ('6_HC_BFD','-1','{"sn:5":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":3},"type":"$N"},"sn:3":{"join":"Y","cont":"YYYYMM","type":"$T"},"sn:4":{"join":"Y","cont":"-","type":"$S"},"sn:1":{"join":"Y","cont":"BF","type":"$S"},"sn:2":{"join":"Y","cont":"-","type":"$S"}}');
--质检单
insert into saibong_rule (cfkey,dprtcode,gznr) values ('7_HC_ZJD','-1','{"sn:5":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":5},"type":"$N"},"sn:3":{"join":"Y","cont":"YYYY","type":"$T"},"sn:4":{"join":"Y","cont":"-","type":"$S"},"sn:1":{"join":"Y","cont":"JY","type":"$S"},"sn:2":{"join":"Y","cont":"-","type":"$S"}}');
--GRN
insert into saibong_rule (cfkey,dprtcode,gznr) values ('8_HC_GRN','-1','{"sn:2":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":3},"type":"$N"},"sn:1":{"join":"Y","cont":"YYMMdd","type":"$T"}}');
--后台任务编码
insert into saibong_rule (cfkey,dprtcode,gznr) values ('9_SYS_TASK','-1','{"sn:2":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":3},"type":"$N"},"sn:1":{"join":"Y","cont":"YYYYMMdd","type":"$T"}}');
--生产指令编号
insert into saibong_rule (cfkey,dprtcode,gznr) values ('10_GC_SCZL','-1','{"sn:2":{"join":"N","cont":{"hex":"D","posi":"B","fill":"0","len":3},"type":"$N"},"sn:1":{"join":"Y","cont":"YYYYMMdd","type":"$T"}}');