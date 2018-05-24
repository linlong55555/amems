/*==============================================================*/
/* Table: "D_028"                                               */
/*==============================================================*/
CREATE TABLE "D_028"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "DPRTCODE"           VARCHAR2(50),
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   "FJZCH"              VARCHAR2(20),
   "YF"                 VARCHAR2(7),
   "HQGS"               NUMBER(12,2),
   "GZGS"               NUMBER(12,2),
   "HHGS"               NUMBER(12,2),
   "LXGSDJ"             NUMBER(12,2),
   "FLXGSDJ"            NUMBER(12,2),
   "CYHCFYBL"           NUMBER(12,2),
   CONSTRAINT PK_D_028 PRIMARY KEY ("ID")
         USING INDEX TABLESPACE TS_INDEX
)
TABLESPACE TS_BASEDATA;

COMMENT ON TABLE "D_028" IS
'd_028 维修执管月报配置';

COMMENT ON COLUMN "D_028"."ID" IS
'uuid';

COMMENT ON COLUMN "D_028"."DPRTCODE" IS
'机构代码';

COMMENT ON COLUMN "D_028"."WHBMID" IS
'维护部门id。关联组织机构表';

COMMENT ON COLUMN "D_028"."WHRID" IS
'维护人id。关联用户表';

COMMENT ON COLUMN "D_028"."WHSJ" IS
'维护时间YYYYMMDDHH24MISS';

COMMENT ON COLUMN "D_028"."FJZCH" IS
'飞机注册号';

COMMENT ON COLUMN "D_028"."YF" IS
'月份（yyyy-mm）';

COMMENT ON COLUMN "D_028"."HQGS" IS
'航前工时';

COMMENT ON COLUMN "D_028"."GZGS" IS
'过站工时';

COMMENT ON COLUMN "D_028"."HHGS" IS
'航后工时';

COMMENT ON COLUMN "D_028"."LXGSDJ" IS
'例行工时单价';

COMMENT ON COLUMN "D_028"."FLXGSDJ" IS
'非例行工时单价';

COMMENT ON COLUMN "D_028"."CYHCFYBL" IS
'常用耗材费用比例';

insert into t_button (id, buttonCode, buttonName, menuId, remark) values ('025920010','produce:maintenancemgnt:monthlyreport:01','设置工时费用','025920','维修执管月报');
insert into t_menu (id, menucode, menuname, menufname, menutype, parentid, menuorder, path, fullorder, iconpath,xtlx) values ('025920','produce:maintenancemgnt:monthlyreport','维修执管月报','Maintenance MGNT Report',2,'025',920,'produce/maintenancemgnt/monthlyreport','','icon-list-alt','AMEMS');
