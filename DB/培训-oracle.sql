/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2017.06.27 14:04:14                          */
/*==============================================================*/


DROP TABLE "B_P_001" CASCADE CONSTRAINTS;

DROP TABLE "B_P_001_REC" CASCADE CONSTRAINTS;

DROP TABLE "B_P_002" CASCADE CONSTRAINTS;

DROP INDEX "IDX_BP0020101_MAINID";

DROP TABLE "B_P_00201" CASCADE CONSTRAINTS;

DROP TABLE "B_P_00201_REC" CASCADE CONSTRAINTS;

DROP TABLE "B_P_002_REC" CASCADE CONSTRAINTS;

DROP TABLE "B_P_003" CASCADE CONSTRAINTS;

DROP TABLE "B_P_003_REC" CASCADE CONSTRAINTS;

DROP TABLE "B_P_004" CASCADE CONSTRAINTS;

DROP TABLE "B_P_004_REC" CASCADE CONSTRAINTS;

DROP TABLE "B_P_005" CASCADE CONSTRAINTS;

DROP TABLE "B_P_005_REC" CASCADE CONSTRAINTS;

DROP TABLE "B_P_006" CASCADE CONSTRAINTS;

DROP TABLE "B_P_006_REC" CASCADE CONSTRAINTS;

/*==============================================================*/
/* Table: "B_P_001"                                             */
/*==============================================================*/
CREATE TABLE "B_P_001"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "DPRTCODE"           VARCHAR2(50),
   "DGBH"               VARCHAR2(50),
   "DGMC"               VARCHAR2(200),
   "BZ"                 VARCHAR2(1000),
   "ZT"                 INTEGER,
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   CONSTRAINT PK_B_P_001 PRIMARY KEY ("ID")
         USING INDEX TABLESPACE TS_INDEX
)
TABLESPACE TS_YWHD;

COMMENT ON TABLE "B_P_001" IS
'b_p_001 岗位';

COMMENT ON COLUMN "B_P_001"."ID" IS
'id uuid';

COMMENT ON COLUMN "B_P_001"."DPRTCODE" IS
'机构代码';

COMMENT ON COLUMN "B_P_001"."DGBH" IS
'大纲编号';

COMMENT ON COLUMN "B_P_001"."DGMC" IS
'大纲名称';

COMMENT ON COLUMN "B_P_001"."BZ" IS
'备注';

COMMENT ON COLUMN "B_P_001"."ZT" IS
'状态：0无效、1有效';

COMMENT ON COLUMN "B_P_001"."WHBMID" IS
'维护部门id';

COMMENT ON COLUMN "B_P_001"."WHRID" IS
'维护人id';

COMMENT ON COLUMN "B_P_001"."WHSJ" IS
'维护时间';

/*==============================================================*/
/* Table: "B_P_001_REC"                                         */
/*==============================================================*/
CREATE TABLE "B_P_001_REC"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "DPRTCODE"           VARCHAR2(50),
   "DGBH"               VARCHAR2(50),
   "DGMC"               VARCHAR2(200),
   "BZ"                 VARCHAR2(1000),
   "ZT"                 INTEGER,
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   "REC_XGLX"           INTEGER,
   "REC_CZRID"          VARCHAR2(50),
   "REC_CZSJ"           DATE,
   "REC_IP"             VARCHAR2(20),
   "REC_CZSM"           VARCHAR2(300),
   "REC_CZLS"           VARCHAR2(50),
   "REC_ZBID"           VARCHAR2(50)
)
TABLESPACE TS_HIS;

COMMENT ON TABLE "B_P_001_REC" IS
'b_p_001_rec 岗位--修改记录';

COMMENT ON COLUMN "B_P_001_REC"."ID" IS
'id uuid';

COMMENT ON COLUMN "B_P_001_REC"."DPRTCODE" IS
'机构代码';

COMMENT ON COLUMN "B_P_001_REC"."DGBH" IS
'大纲编号';

COMMENT ON COLUMN "B_P_001_REC"."DGMC" IS
'大纲名称';

COMMENT ON COLUMN "B_P_001_REC"."BZ" IS
'备注';

COMMENT ON COLUMN "B_P_001_REC"."ZT" IS
'状态：0无效、1有效';

COMMENT ON COLUMN "B_P_001_REC"."WHBMID" IS
'维护部门id';

COMMENT ON COLUMN "B_P_001_REC"."WHRID" IS
'维护人id';

COMMENT ON COLUMN "B_P_001_REC"."WHSJ" IS
'维护时间';

COMMENT ON COLUMN "B_P_001_REC"."REC_XGLX" IS
'记录-修改类型：1新增、2修改、3删除';

COMMENT ON COLUMN "B_P_001_REC"."REC_CZRID" IS
'记录-操作人id';

COMMENT ON COLUMN "B_P_001_REC"."REC_CZSJ" IS
'记录-操作时间。yyymmddhh24miss';

COMMENT ON COLUMN "B_P_001_REC"."REC_IP" IS
'记录-ip';

COMMENT ON COLUMN "B_P_001_REC"."REC_CZSM" IS
'记录-操作说明';

COMMENT ON COLUMN "B_P_001_REC"."REC_CZLS" IS
'记录-操作流水 uuid';

COMMENT ON COLUMN "B_P_001_REC"."REC_ZBID" IS
'记录-主表ID';

/*==============================================================*/
/* Table: "B_P_002"                                             */
/*==============================================================*/
CREATE TABLE "B_P_002"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "DPRTCODE"           VARCHAR2(50),
   "KCID"               VARCHAR2(50),
   "KCRQ"               DATE,
   "KCSJ"               VARCHAR2(20),
   "KCDD"               VARCHAR2(50),
   "JSID"               VARCHAR2(50),
   "JSXM"               VARCHAR2(300),
   "BZ"                 VARCHAR2(1000),
   "ZT"                 INTEGER,
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   "ZDJSRID"            VARCHAR2(50),
   "ZDJSRQ"             DATE,
   "ZDJSYY"             VARCHAR2(500),
   CONSTRAINT PK_B_P_002 PRIMARY KEY ("ID")
         USING INDEX TABLESPACE TS_INDEX
)
TABLESPACE TS_YWHD;

COMMENT ON TABLE "B_P_002" IS
'b_p_002 培训计划';

COMMENT ON COLUMN "B_P_002"."ID" IS
'id uuid';

COMMENT ON COLUMN "B_P_002"."DPRTCODE" IS
'机构代码';

COMMENT ON COLUMN "B_P_002"."KCID" IS
'课程ID';

COMMENT ON COLUMN "B_P_002"."KCRQ" IS
'课程日期';

COMMENT ON COLUMN "B_P_002"."KCSJ" IS
'课程时间 hh:mm';

COMMENT ON COLUMN "B_P_002"."KCDD" IS
'课程地点';

COMMENT ON COLUMN "B_P_002"."JSID" IS
'讲师ID，对应维修人员档案中ID';

COMMENT ON COLUMN "B_P_002"."JSXM" IS
'讲师姓名';

COMMENT ON COLUMN "B_P_002"."BZ" IS
'备注';

COMMENT ON COLUMN "B_P_002"."ZT" IS
'状态：0保存、1提交、8作废、9关闭、10完成';

COMMENT ON COLUMN "B_P_002"."WHBMID" IS
'维护部门id';

COMMENT ON COLUMN "B_P_002"."WHRID" IS
'维护人id';

COMMENT ON COLUMN "B_P_002"."WHSJ" IS
'维护时间';

COMMENT ON COLUMN "B_P_002"."ZDJSRID" IS
'指定结束人id。关联用户表';

COMMENT ON COLUMN "B_P_002"."ZDJSRQ" IS
'指定结束日期。对应关闭';

COMMENT ON COLUMN "B_P_002"."ZDJSYY" IS
'指定结束原因';

/*==============================================================*/
/* Table: "B_P_00201"                                           */
/*==============================================================*/
CREATE TABLE "B_P_00201"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "MAINID"             VARCHAR2(50),
   "WXRYDAID"           VARCHAR2(50),
   "KQ"                 INTEGER,
   "CQL"                NUMBER(12,2),
   "CJ"                 VARCHAR2(50),
   "KCBM"               VARCHAR2(50),
   "KCNR"               VARCHAR2(1000),
   "PXDW"               VARCHAR2(300),
   "IS_TG"              INTEGER,
   "BZ"                 VARCHAR2(1000),
   "CYBS"               INTEGER,
   "ZT"                 INTEGER,
   "JSZT"               INTEGER,
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   CONSTRAINT PK_B_P_00201 PRIMARY KEY ("ID")
         USING INDEX TABLESPACE TS_INDEX
)
TABLESPACE TS_YWHD;

COMMENT ON TABLE "B_P_00201" IS
'b_p_00201 培训计划-培训课程人员表';

COMMENT ON COLUMN "B_P_00201"."ID" IS
'id uuid';

COMMENT ON COLUMN "B_P_00201"."MAINID" IS
'mainid。关联岗位表id';

COMMENT ON COLUMN "B_P_00201"."WXRYDAID" IS
'维修人员档案id。关联维修人员档案表id';

COMMENT ON COLUMN "B_P_00201"."KQ" IS
'考勤：0未参加、1出勤';

COMMENT ON COLUMN "B_P_00201"."CQL" IS
'出勤率（单位：百分比%）';

COMMENT ON COLUMN "B_P_00201"."CJ" IS
'成绩';

COMMENT ON COLUMN "B_P_00201"."KCBM" IS
'课程编码';

COMMENT ON COLUMN "B_P_00201"."KCNR" IS
'课程内容';

COMMENT ON COLUMN "B_P_00201"."PXDW" IS
'培训单位';

COMMENT ON COLUMN "B_P_00201"."IS_TG" IS
'是否通过：0不通过、1通过';

COMMENT ON COLUMN "B_P_00201"."BZ" IS
'备注';

COMMENT ON COLUMN "B_P_00201"."CYBS" IS
'查阅标识：0未查阅、1已查阅';

COMMENT ON COLUMN "B_P_00201"."ZT" IS
'状态：0无效、1有效';

COMMENT ON COLUMN "B_P_00201"."JSZT" IS
'接收状态：0未接收、1已接收';

COMMENT ON COLUMN "B_P_00201"."WHBMID" IS
'维护部门id';

COMMENT ON COLUMN "B_P_00201"."WHRID" IS
'维护人id';

COMMENT ON COLUMN "B_P_00201"."WHSJ" IS
'维护时间';

/*==============================================================*/
/* Index: "IDX_BP0020101_MAINID"                                */
/*==============================================================*/
CREATE INDEX "IDX_BP0020101_MAINID" ON "B_P_00201" (
   "MAINID" ASC
)
TABLESPACE TS_INDEX;

/*==============================================================*/
/* Table: "B_P_00201_REC"                                       */
/*==============================================================*/
CREATE TABLE "B_P_00201_REC"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "MAINID"             VARCHAR2(50),
   "WXRYDAID"           VARCHAR2(50),
   "KQ"                 INTEGER,
   "CJ"                 VARCHAR2(50),
   "CQL"                NUMBER(12,2),
   "KCBM"               VARCHAR2(50),
   "KCNR"               VARCHAR2(1000),
   "PXDW"               VARCHAR2(300),
   "IS_TG"              INTEGER,
   "BZ"                 VARCHAR2(1000),
   "CYBS"               INTEGER,
   "ZT"                 INTEGER,
   "JSZT"               INTEGER,
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   "REC_XGLX"           INTEGER,
   "REC_CZRID"          VARCHAR2(50),
   "REC_CZSJ"           DATE,
   "REC_IP"             VARCHAR2(20),
   "REC_CZSM"           VARCHAR2(300),
   "REC_CZLS"           VARCHAR2(50),
   "REC_ZBID"           VARCHAR2(50)
)
TABLESPACE TS_HIS;

COMMENT ON TABLE "B_P_00201_REC" IS
'b_p_00201_rec 培训计划-培训课程人员表-修改记录';

COMMENT ON COLUMN "B_P_00201_REC"."ID" IS
'id uuid';

COMMENT ON COLUMN "B_P_00201_REC"."MAINID" IS
'mainid。关联岗位表id';

COMMENT ON COLUMN "B_P_00201_REC"."WXRYDAID" IS
'维修人员档案id。关联维修人员档案表id';

COMMENT ON COLUMN "B_P_00201_REC"."KQ" IS
'考勤：0未参加、1出勤';

COMMENT ON COLUMN "B_P_00201_REC"."CJ" IS
'成绩';

COMMENT ON COLUMN "B_P_00201_REC"."CQL" IS
'出勤率（单位：百分比%）';

COMMENT ON COLUMN "B_P_00201_REC"."KCBM" IS
'课程编码';

COMMENT ON COLUMN "B_P_00201_REC"."KCNR" IS
'课程内容';

COMMENT ON COLUMN "B_P_00201_REC"."PXDW" IS
'培训单位';

COMMENT ON COLUMN "B_P_00201_REC"."IS_TG" IS
'是否通过：0不通过、1通过';

COMMENT ON COLUMN "B_P_00201_REC"."BZ" IS
'备注';

COMMENT ON COLUMN "B_P_00201_REC"."CYBS" IS
'查阅标识：0未查阅、1已查阅';

COMMENT ON COLUMN "B_P_00201_REC"."ZT" IS
'状态：0无效、1有效';

COMMENT ON COLUMN "B_P_00201_REC"."JSZT" IS
'接收状态：0未接收、1已接收';

COMMENT ON COLUMN "B_P_00201_REC"."WHBMID" IS
'维护部门id';

COMMENT ON COLUMN "B_P_00201_REC"."WHRID" IS
'维护人id';

COMMENT ON COLUMN "B_P_00201_REC"."WHSJ" IS
'维护时间';

COMMENT ON COLUMN "B_P_00201_REC"."REC_XGLX" IS
'记录-修改类型：1新增、2修改、3删除';

COMMENT ON COLUMN "B_P_00201_REC"."REC_CZRID" IS
'记录-操作人id';

COMMENT ON COLUMN "B_P_00201_REC"."REC_CZSJ" IS
'记录-操作时间。yyymmddhh24miss';

COMMENT ON COLUMN "B_P_00201_REC"."REC_IP" IS
'记录-ip';

COMMENT ON COLUMN "B_P_00201_REC"."REC_CZSM" IS
'记录-操作说明';

COMMENT ON COLUMN "B_P_00201_REC"."REC_CZLS" IS
'记录-操作流水 uuid';

COMMENT ON COLUMN "B_P_00201_REC"."REC_ZBID" IS
'记录-主表ID';

/*==============================================================*/
/* Table: "B_P_002_REC"                                         */
/*==============================================================*/
CREATE TABLE "B_P_002_REC"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "DPRTCODE"           VARCHAR2(50),
   "KCID"               VARCHAR2(50),
   "KCRQ"               DATE,
   "KCSJ"               VARCHAR2(20),
   "KCDD"               VARCHAR2(50),
   "JSID"               VARCHAR2(50),
   "JSXM"               VARCHAR2(300),
   "BZ"                 VARCHAR2(1000),
   "ZT"                 INTEGER,
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   "ZDJSRID"            VARCHAR2(50),
   "ZDJSRQ"             DATE,
   "ZDJSYY"             VARCHAR2(500),
   "REC_XGLX"           INTEGER,
   "REC_CZRID"          VARCHAR2(50),
   "REC_CZSJ"           DATE,
   "REC_IP"             VARCHAR2(20),
   "REC_CZSM"           VARCHAR2(300),
   "REC_CZLS"           VARCHAR2(50),
   "REC_ZBID"           VARCHAR2(50)
)
TABLESPACE TS_HIS;

COMMENT ON TABLE "B_P_002_REC" IS
'b_p_002_rec 培训计划-修改记录';

COMMENT ON COLUMN "B_P_002_REC"."ID" IS
'id uuid';

COMMENT ON COLUMN "B_P_002_REC"."DPRTCODE" IS
'机构代码';

COMMENT ON COLUMN "B_P_002_REC"."KCID" IS
'课程ID';

COMMENT ON COLUMN "B_P_002_REC"."KCRQ" IS
'课程日期';

COMMENT ON COLUMN "B_P_002_REC"."KCSJ" IS
'课程时间 hh:mm';

COMMENT ON COLUMN "B_P_002_REC"."KCDD" IS
'课程地点';

COMMENT ON COLUMN "B_P_002_REC"."JSID" IS
'讲师ID，对应维修人员档案中ID';

COMMENT ON COLUMN "B_P_002_REC"."JSXM" IS
'讲师姓名';

COMMENT ON COLUMN "B_P_002_REC"."BZ" IS
'备注';

COMMENT ON COLUMN "B_P_002_REC"."ZT" IS
'状态：0保存、1提交、8作废、9关闭、10完成';

COMMENT ON COLUMN "B_P_002_REC"."WHBMID" IS
'维护部门id';

COMMENT ON COLUMN "B_P_002_REC"."WHRID" IS
'维护人id';

COMMENT ON COLUMN "B_P_002_REC"."WHSJ" IS
'维护时间';

COMMENT ON COLUMN "B_P_002_REC"."ZDJSRID" IS
'指定结束人id。关联用户表';

COMMENT ON COLUMN "B_P_002_REC"."ZDJSRQ" IS
'指定结束日期。对应关闭';

COMMENT ON COLUMN "B_P_002_REC"."ZDJSYY" IS
'指定结束原因';

COMMENT ON COLUMN "B_P_002_REC"."REC_XGLX" IS
'记录-修改类型：1新增、2修改、3删除';

COMMENT ON COLUMN "B_P_002_REC"."REC_CZRID" IS
'记录-操作人id';

COMMENT ON COLUMN "B_P_002_REC"."REC_CZSJ" IS
'记录-操作时间。yyymmddhh24miss';

COMMENT ON COLUMN "B_P_002_REC"."REC_IP" IS
'记录-ip';

COMMENT ON COLUMN "B_P_002_REC"."REC_CZSM" IS
'记录-操作说明';

COMMENT ON COLUMN "B_P_002_REC"."REC_CZLS" IS
'记录-操作流水 uuid';

COMMENT ON COLUMN "B_P_002_REC"."REC_ZBID" IS
'记录-主表ID';

/*==============================================================*/
/* Table: "B_P_003"                                             */
/*==============================================================*/
CREATE TABLE "B_P_003"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "DPRTCODE"           VARCHAR2(50),
   "KCBH"               VARCHAR2(50),
   "KCMC"               VARCHAR2(300),
   "KS"                 NUMBER(12,2),
   "PXXS"               INTEGER,
   "IS_FX"              INTEGER,
   "ZQZ"                INTEGER,
   "ZQDW"               INTEGER,
   "BZ"                 VARCHAR2(1000),
   "ZT"                 INTEGER,
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   CONSTRAINT PK_B_P_003 PRIMARY KEY ("ID")
         USING INDEX TABLESPACE TS_INDEX
)
TABLESPACE TS_YWHD;

COMMENT ON TABLE "B_P_003" IS
'b_p_003 课程大纲';

COMMENT ON COLUMN "B_P_003"."ID" IS
'id uuid';

COMMENT ON COLUMN "B_P_003"."DPRTCODE" IS
'机构代码';

COMMENT ON COLUMN "B_P_003"."KCBH" IS
'课程编号';

COMMENT ON COLUMN "B_P_003"."KCMC" IS
'课程名称';

COMMENT ON COLUMN "B_P_003"."KS" IS
'课时';

COMMENT ON COLUMN "B_P_003"."PXXS" IS
'培训形式：1课堂、2自学、3OJT';

COMMENT ON COLUMN "B_P_003"."IS_FX" IS
'是否复训：0否、1是';

COMMENT ON COLUMN "B_P_003"."ZQZ" IS
'复训周期值';

COMMENT ON COLUMN "B_P_003"."ZQDW" IS
'周期单位（1天；2月；3年）';

COMMENT ON COLUMN "B_P_003"."BZ" IS
'备注';

COMMENT ON COLUMN "B_P_003"."ZT" IS
'状态：0无效、1有效';

COMMENT ON COLUMN "B_P_003"."WHBMID" IS
'维护部门id';

COMMENT ON COLUMN "B_P_003"."WHRID" IS
'维护人id';

COMMENT ON COLUMN "B_P_003"."WHSJ" IS
'维护时间';

/*==============================================================*/
/* Table: "B_P_003_REC"                                         */
/*==============================================================*/
CREATE TABLE "B_P_003_REC"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "DPRTCODE"           VARCHAR2(50),
   "KCBH"               VARCHAR2(50),
   "KCMC"               VARCHAR2(300),
   "KS"                 NUMBER(12,2),
   "PXXS"               INTEGER,
   "IS_FX"              INTEGER,
   "ZQZ"                INTEGER,
   "ZQDW"               INTEGER,
   "BZ"                 VARCHAR2(1000),
   "ZT"                 INTEGER,
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   "REC_XGLX"           INTEGER,
   "REC_CZRID"          VARCHAR2(50),
   "REC_CZSJ"           DATE,
   "REC_IP"             VARCHAR2(20),
   "REC_CZSM"           VARCHAR2(300),
   "REC_CZLS"           VARCHAR2(50),
   "REC_ZBID"           VARCHAR2(50)
)
TABLESPACE TS_HIS;

COMMENT ON TABLE "B_P_003_REC" IS
'b_p_003_rec 课程大纲-修改记录';

COMMENT ON COLUMN "B_P_003_REC"."ID" IS
'id uuid';

COMMENT ON COLUMN "B_P_003_REC"."DPRTCODE" IS
'机构代码';

COMMENT ON COLUMN "B_P_003_REC"."KCBH" IS
'课程编号';

COMMENT ON COLUMN "B_P_003_REC"."KCMC" IS
'课程名称';

COMMENT ON COLUMN "B_P_003_REC"."KS" IS
'课时';

COMMENT ON COLUMN "B_P_003_REC"."PXXS" IS
'培训形式：1课堂、2自学、3OJT';

COMMENT ON COLUMN "B_P_003_REC"."IS_FX" IS
'是否复训：0否、1是';

COMMENT ON COLUMN "B_P_003_REC"."ZQZ" IS
'复训周期值';

COMMENT ON COLUMN "B_P_003_REC"."ZQDW" IS
'周期单位（1天；2月；3年）';

COMMENT ON COLUMN "B_P_003_REC"."BZ" IS
'备注';

COMMENT ON COLUMN "B_P_003_REC"."ZT" IS
'状态：0无效、1有效';

COMMENT ON COLUMN "B_P_003_REC"."WHBMID" IS
'维护部门id';

COMMENT ON COLUMN "B_P_003_REC"."WHRID" IS
'维护人id';

COMMENT ON COLUMN "B_P_003_REC"."WHSJ" IS
'维护时间';

COMMENT ON COLUMN "B_P_003_REC"."REC_XGLX" IS
'记录-修改类型：1新增、2修改、3删除';

COMMENT ON COLUMN "B_P_003_REC"."REC_CZRID" IS
'记录-操作人id';

COMMENT ON COLUMN "B_P_003_REC"."REC_CZSJ" IS
'记录-操作时间。yyymmddhh24miss';

COMMENT ON COLUMN "B_P_003_REC"."REC_IP" IS
'记录-ip';

COMMENT ON COLUMN "B_P_003_REC"."REC_CZSM" IS
'记录-操作说明';

COMMENT ON COLUMN "B_P_003_REC"."REC_CZLS" IS
'记录-操作流水 uuid';

COMMENT ON COLUMN "B_P_003_REC"."REC_ZBID" IS
'记录-主表ID';

/*==============================================================*/
/* Table: "B_P_004"                                             */
/*==============================================================*/
CREATE TABLE "B_P_004"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "DPRTCODE"           VARCHAR2(50),
   "WXRYDAID"           VARCHAR2(50),
   "KCID"               VARCHAR2(50),
   "PXJHID"             VARCHAR2(50),
   "KCRQ"               DATE,
   "KCSJ"               VARCHAR2(20),
   "CJ"                 VARCHAR2(50),
   "CQL"                NUMBER(12,2),
   "IS_TG"              INTEGER,
   "KCBM"               VARCHAR2(50),
   "KCNR"               VARCHAR2(1000),
   "PXDW"               VARCHAR2(300),
   CONSTRAINT PK_B_P_004 PRIMARY KEY ("ID")
         USING INDEX TABLESPACE TS_INDEX
)
TABLESPACE TS_YWHD;

COMMENT ON TABLE "B_P_004" IS
'b_p_004 人员最近培训记录';

COMMENT ON COLUMN "B_P_004"."ID" IS
'id uuid';

COMMENT ON COLUMN "B_P_004"."DPRTCODE" IS
'机构代码';

COMMENT ON COLUMN "B_P_004"."WXRYDAID" IS
'维修人员档案ID';

COMMENT ON COLUMN "B_P_004"."KCID" IS
'课程ID';

COMMENT ON COLUMN "B_P_004"."PXJHID" IS
'培训计划ID';

COMMENT ON COLUMN "B_P_004"."KCRQ" IS
'课程日期';

COMMENT ON COLUMN "B_P_004"."KCSJ" IS
'课程时间 hh:mm';

COMMENT ON COLUMN "B_P_004"."CJ" IS
'成绩';

COMMENT ON COLUMN "B_P_004"."CQL" IS
'出勤率（单位：百分比%）';

COMMENT ON COLUMN "B_P_004"."IS_TG" IS
'是否通过：0不通过、1通过';

COMMENT ON COLUMN "B_P_004"."KCBM" IS
'课程编码';

COMMENT ON COLUMN "B_P_004"."KCNR" IS
'课程内容';

COMMENT ON COLUMN "B_P_004"."PXDW" IS
'培训单位';

/*==============================================================*/
/* Table: "B_P_004_REC"                                         */
/*==============================================================*/
CREATE TABLE "B_P_004_REC"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "DPRTCODE"           VARCHAR2(50),
   "WXRYDAID"           VARCHAR2(50),
   "KCID"               VARCHAR2(50),
   "PXJHID"             VARCHAR2(50),
   "KCRQ"               DATE,
   "KCSJ"               VARCHAR2(20),
   "CJ"                 VARCHAR2(50),
   "CQL"                NUMBER(12,2),
   "KCBM"               VARCHAR2(50),
   "KCNR"               VARCHAR2(1000),
   "PXDW"               VARCHAR2(300),
   "IS_TG"              INTEGER,
   "REC_XGLX"           INTEGER,
   "REC_CZRID"          VARCHAR2(50),
   "REC_CZSJ"           DATE,
   "REC_IP"             VARCHAR2(20),
   "REC_CZSM"           VARCHAR2(300),
   "REC_CZLS"           VARCHAR2(50),
   "REC_ZBID"           VARCHAR2(50)
)
TABLESPACE TS_HIS;

COMMENT ON TABLE "B_P_004_REC" IS
'b_p_004_rec 人员最近培训记录-修改记录';

COMMENT ON COLUMN "B_P_004_REC"."ID" IS
'id uuid';

COMMENT ON COLUMN "B_P_004_REC"."DPRTCODE" IS
'机构代码';

COMMENT ON COLUMN "B_P_004_REC"."WXRYDAID" IS
'维修人员档案ID';

COMMENT ON COLUMN "B_P_004_REC"."KCID" IS
'课程ID';

COMMENT ON COLUMN "B_P_004_REC"."PXJHID" IS
'培训计划ID';

COMMENT ON COLUMN "B_P_004_REC"."KCRQ" IS
'课程日期';

COMMENT ON COLUMN "B_P_004_REC"."KCSJ" IS
'课程时间 hh:mm';

COMMENT ON COLUMN "B_P_004_REC"."CJ" IS
'成绩';

COMMENT ON COLUMN "B_P_004_REC"."CQL" IS
'出勤率（单位：百分比%）';

COMMENT ON COLUMN "B_P_004_REC"."KCBM" IS
'课程编码';

COMMENT ON COLUMN "B_P_004_REC"."KCNR" IS
'课程内容';

COMMENT ON COLUMN "B_P_004_REC"."PXDW" IS
'培训单位';

COMMENT ON COLUMN "B_P_004_REC"."IS_TG" IS
'是否通过：0不通过、1通过';

COMMENT ON COLUMN "B_P_004_REC"."REC_XGLX" IS
'记录-修改类型：1新增、2修改、3删除';

COMMENT ON COLUMN "B_P_004_REC"."REC_CZRID" IS
'记录-操作人id';

COMMENT ON COLUMN "B_P_004_REC"."REC_CZSJ" IS
'记录-操作时间。yyymmddhh24miss';

COMMENT ON COLUMN "B_P_004_REC"."REC_IP" IS
'记录-ip';

COMMENT ON COLUMN "B_P_004_REC"."REC_CZSM" IS
'记录-操作说明';

COMMENT ON COLUMN "B_P_004_REC"."REC_CZLS" IS
'记录-操作流水 uuid';

COMMENT ON COLUMN "B_P_004_REC"."REC_ZBID" IS
'记录-主表ID';

/*==============================================================*/
/* Table: "B_P_005"                                             */
/*==============================================================*/
CREATE TABLE "B_P_005"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "GWID"               VARCHAR2(50),
   "KCID"               VARCHAR2(50),
   "ZT"                 INTEGER,
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   CONSTRAINT PK_B_P_005 PRIMARY KEY ("ID")
         USING INDEX TABLESPACE TS_INDEX
)
TABLESPACE TS_YWHD;

COMMENT ON TABLE "B_P_005" IS
'b_p_005 岗位-课程关系';

COMMENT ON COLUMN "B_P_005"."ID" IS
'id uuid';

COMMENT ON COLUMN "B_P_005"."GWID" IS
'岗位id';

COMMENT ON COLUMN "B_P_005"."KCID" IS
'课程ID';

COMMENT ON COLUMN "B_P_005"."ZT" IS
'状态：0无效、1有效';

COMMENT ON COLUMN "B_P_005"."WHBMID" IS
'维护部门id';

COMMENT ON COLUMN "B_P_005"."WHRID" IS
'维护人id';

COMMENT ON COLUMN "B_P_005"."WHSJ" IS
'维护时间';

/*==============================================================*/
/* Table: "B_P_005_REC"                                         */
/*==============================================================*/
CREATE TABLE "B_P_005_REC"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "GWID"               VARCHAR2(50),
   "KCID"               VARCHAR2(50),
   "ZT"                 INTEGER,
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   "REC_XGLX"           INTEGER,
   "REC_CZRID"          VARCHAR2(50),
   "REC_CZSJ"           DATE,
   "REC_IP"             VARCHAR2(20),
   "REC_CZSM"           VARCHAR2(300),
   "REC_CZLS"           VARCHAR2(50),
   "REC_ZBID"           VARCHAR2(50)
)
TABLESPACE TS_HIS;

COMMENT ON TABLE "B_P_005_REC" IS
'b_p_005_rec 岗位-课程关系-修改记录';

COMMENT ON COLUMN "B_P_005_REC"."ID" IS
'id uuid';

COMMENT ON COLUMN "B_P_005_REC"."GWID" IS
'岗位id';

COMMENT ON COLUMN "B_P_005_REC"."KCID" IS
'课程ID';

COMMENT ON COLUMN "B_P_005_REC"."ZT" IS
'状态：0无效、1有效';

COMMENT ON COLUMN "B_P_005_REC"."WHBMID" IS
'维护部门id';

COMMENT ON COLUMN "B_P_005_REC"."WHRID" IS
'维护人id';

COMMENT ON COLUMN "B_P_005_REC"."WHSJ" IS
'维护时间';

COMMENT ON COLUMN "B_P_005_REC"."REC_XGLX" IS
'记录-修改类型：1新增、2修改、3删除';

COMMENT ON COLUMN "B_P_005_REC"."REC_CZRID" IS
'记录-操作人id';

COMMENT ON COLUMN "B_P_005_REC"."REC_CZSJ" IS
'记录-操作时间。yyymmddhh24miss';

COMMENT ON COLUMN "B_P_005_REC"."REC_IP" IS
'记录-ip';

COMMENT ON COLUMN "B_P_005_REC"."REC_CZSM" IS
'记录-操作说明';

COMMENT ON COLUMN "B_P_005_REC"."REC_CZLS" IS
'记录-操作流水 uuid';

COMMENT ON COLUMN "B_P_005_REC"."REC_ZBID" IS
'记录-主表ID';

/*==============================================================*/
/* Table: "B_P_006"                                             */
/*==============================================================*/
CREATE TABLE "B_P_006"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "GWID"               VARCHAR2(50),
   "WXRYDAID"           VARCHAR2(50),
   "ZT"                 INTEGER,
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   CONSTRAINT PK_B_P_006 PRIMARY KEY ("ID")
         USING INDEX TABLESPACE TS_INDEX
)
TABLESPACE TS_YWHD;

COMMENT ON TABLE "B_P_006" IS
'b_p_006 岗位-人员关系';

COMMENT ON COLUMN "B_P_006"."ID" IS
'id uuid';

COMMENT ON COLUMN "B_P_006"."GWID" IS
'岗位id';

COMMENT ON COLUMN "B_P_006"."WXRYDAID" IS
'维修人员档案ID';

COMMENT ON COLUMN "B_P_006"."ZT" IS
'状态：0无效、1有效';

COMMENT ON COLUMN "B_P_006"."WHBMID" IS
'维护部门id';

COMMENT ON COLUMN "B_P_006"."WHRID" IS
'维护人id';

COMMENT ON COLUMN "B_P_006"."WHSJ" IS
'维护时间';

/*==============================================================*/
/* Table: "B_P_006_REC"                                         */
/*==============================================================*/
CREATE TABLE "B_P_006_REC"  (
   "ID"                 VARCHAR2(50),
   "GWID"               VARCHAR2(50),
   "WXRYDAID"           VARCHAR2(50),
   "ZT"                 INTEGER,
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   "REC_XGLX"           INTEGER,
   "REC_CZRID"          VARCHAR2(50),
   "REC_CZSJ"           DATE,
   "REC_IP"             VARCHAR2(20),
   "REC_CZSM"           VARCHAR2(300),
   "REC_CZLS"           VARCHAR2(50),
   "REC_ZBID"           VARCHAR2(50)
)
TABLESPACE TS_HIS;

COMMENT ON TABLE "B_P_006_REC" IS
'b_p_006_rec 岗位-人员关系-修改记录';

COMMENT ON COLUMN "B_P_006_REC"."ID" IS
'id uuid';

COMMENT ON COLUMN "B_P_006_REC"."GWID" IS
'岗位id';

COMMENT ON COLUMN "B_P_006_REC"."WXRYDAID" IS
'维修人员档案ID';

COMMENT ON COLUMN "B_P_006_REC"."ZT" IS
'状态：0无效、1有效';

COMMENT ON COLUMN "B_P_006_REC"."WHBMID" IS
'维护部门id';

COMMENT ON COLUMN "B_P_006_REC"."WHRID" IS
'维护人id';

COMMENT ON COLUMN "B_P_006_REC"."WHSJ" IS
'维护时间';

COMMENT ON COLUMN "B_P_006_REC"."REC_XGLX" IS
'记录-修改类型：1新增、2修改、3删除';

COMMENT ON COLUMN "B_P_006_REC"."REC_CZRID" IS
'记录-操作人id';

COMMENT ON COLUMN "B_P_006_REC"."REC_CZSJ" IS
'记录-操作时间。yyymmddhh24miss';

COMMENT ON COLUMN "B_P_006_REC"."REC_IP" IS
'记录-ip';

COMMENT ON COLUMN "B_P_006_REC"."REC_CZSM" IS
'记录-操作说明';

COMMENT ON COLUMN "B_P_006_REC"."REC_CZLS" IS
'记录-操作流水 uuid';

COMMENT ON COLUMN "B_P_006_REC"."REC_ZBID" IS
'记录-主表ID';

