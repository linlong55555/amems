/*==============================================================*/
/* Table: "B_G2_014_REC"                                        */
/*==============================================================*/
CREATE TABLE "B_G2_014_REC"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "DPRTCODE"           VARCHAR2(50)                    NOT NULL,
   "ZLBH"               VARCHAR2(50)                    NOT NULL,
   "BB"                 NUMBER(12,2)                    NOT NULL,
   "ZLMS"               VARCHAR2(4000)                  NOT NULL,
   "JX"                 VARCHAR2(50),
   "ZJH"                VARCHAR2(20),
   "SYX"                VARCHAR2(20),
   "GKBH"               VARCHAR2(50),
   "JSGS"               INTEGER                        DEFAULT 3,
   "IS_HDWZ"            INTEGER                        DEFAULT 0,
   "JGMS"               VARCHAR2(4000),
   "BZ"                 VARCHAR2(4000),
   "ZT"                 INTEGER,
   "F_BBID"             VARCHAR2(50),
   "B_BBID"             VARCHAR2(50),
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   "SXSJ"               DATE,
   "REC_XGLX"           INTEGER,
   "REC_CZRID"          VARCHAR2(50),
   "REC_CZSJ"           DATE,
   "REC_IP"             VARCHAR2(20),
   "REC_CZSM"           VARCHAR2(300),
   "REC_CZLS"           VARCHAR2(50),
   "REC_ZBID"           VARCHAR2(50)
)
TABLESPACE TS_HIS;

COMMENT ON TABLE "B_G2_014_REC" IS
'b_g2_014_rec 生产指令-修改记录';

COMMENT ON COLUMN "B_G2_014_REC"."ID" IS
'uuid';

COMMENT ON COLUMN "B_G2_014_REC"."DPRTCODE" IS
'机构代码';

COMMENT ON COLUMN "B_G2_014_REC"."ZLBH" IS
'指令编号';

COMMENT ON COLUMN "B_G2_014_REC"."BB" IS
'版本号';

COMMENT ON COLUMN "B_G2_014_REC"."ZLMS" IS
'指令描述';

COMMENT ON COLUMN "B_G2_014_REC"."JX" IS
'机型。来源于机型参考系';

COMMENT ON COLUMN "B_G2_014_REC"."ZJH" IS
'ATA章节号';

COMMENT ON COLUMN "B_G2_014_REC"."SYX" IS
'适用性：00000（表示通用）、-（表示设置有飞机注册号）、null表示无';

COMMENT ON COLUMN "B_G2_014_REC"."GKBH" IS
'工卡编号';

COMMENT ON COLUMN "B_G2_014_REC"."JSGS" IS
'计算公式：1计划与实际取小再加上周期、2实际加上周期、3计划加上周期';

COMMENT ON COLUMN "B_G2_014_REC"."IS_HDWZ" IS
'后到为准：0否、1是';

COMMENT ON COLUMN "B_G2_014_REC"."JGMS" IS
'间隔描述';

COMMENT ON COLUMN "B_G2_014_REC"."BZ" IS
'备注';

COMMENT ON COLUMN "B_G2_014_REC"."ZT" IS
'状态：1保存、2提交、3已审核、4已批准、5审核驳回、6审批驳回、7生效、9关闭';

COMMENT ON COLUMN "B_G2_014_REC"."F_BBID" IS
'前版本id：记录该版本的前一个版本id';

COMMENT ON COLUMN "B_G2_014_REC"."B_BBID" IS
'后版本id：记录该版本改版后的版本id';

COMMENT ON COLUMN "B_G2_014_REC"."WHBMID" IS
'维护部门id。关联组织机构表';

COMMENT ON COLUMN "B_G2_014_REC"."WHRID" IS
'维护人id。关联用户表';

COMMENT ON COLUMN "B_G2_014_REC"."WHSJ" IS
'维护时间。yyyymmddhh24miss';

COMMENT ON COLUMN "B_G2_014_REC"."SXSJ" IS
'生效时间。yyyymmddhh24miss';

COMMENT ON COLUMN "B_G2_014_REC"."REC_XGLX" IS
'记录-修改类型：1新增、2修改、3删除';

COMMENT ON COLUMN "B_G2_014_REC"."REC_CZRID" IS
'记录-操作人id';

COMMENT ON COLUMN "B_G2_014_REC"."REC_CZSJ" IS
'记录-操作时间。yyymmddhh24miss';

COMMENT ON COLUMN "B_G2_014_REC"."REC_IP" IS
'记录-ip';

COMMENT ON COLUMN "B_G2_014_REC"."REC_CZSM" IS
'记录-操作说明';

COMMENT ON COLUMN "B_G2_014_REC"."REC_CZLS" IS
'记录-操作流水 uuid';

COMMENT ON COLUMN "B_G2_014_REC"."REC_ZBID" IS
'记录-主表ID';


/*==============================================================*/
/* Table: "B_G2_01401_REC"                                      */
/*==============================================================*/
CREATE TABLE "B_G2_01401_REC"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "MAINID"             VARCHAR2(50)                    NOT NULL,
   "JKLBH"              VARCHAR2(20),
   "JKFLBH"             VARCHAR2(20),
   "SCZ"                VARCHAR2(20),
   "DW_SCZ"             INTEGER,
   "ZQZ"                VARCHAR2(20),
   "DW_ZQZ"             INTEGER,
   "YDZ_Q"              VARCHAR2(20),
   "YDZ_QDW"            INTEGER,
   "YDZ_H"              VARCHAR2(20),
   "YDZ_HDW"            INTEGER,
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

COMMENT ON TABLE "B_G2_01401_REC" IS
'b_g2_01401_rec 生产指令-监控项设置-修改记录';

COMMENT ON COLUMN "B_G2_01401_REC"."ID" IS
'uuid';

COMMENT ON COLUMN "B_G2_01401_REC"."MAINID" IS
'mainID';

COMMENT ON COLUMN "B_G2_01401_REC"."JKLBH" IS
'监控项目编号';

COMMENT ON COLUMN "B_G2_01401_REC"."JKFLBH" IS
'监控分类编号';

COMMENT ON COLUMN "B_G2_01401_REC"."SCZ" IS
'首次值';

COMMENT ON COLUMN "B_G2_01401_REC"."DW_SCZ" IS
'首次值单位：10(CAL)、11(DAY)、12(MON)、20(HRS)、30(CYC)';

COMMENT ON COLUMN "B_G2_01401_REC"."ZQZ" IS
'周期值';

COMMENT ON COLUMN "B_G2_01401_REC"."DW_ZQZ" IS
'周期值单位：10(CAL)、11(DAY)、12(MON)、20(HRS)、30(CYC)';

COMMENT ON COLUMN "B_G2_01401_REC"."YDZ_Q" IS
'裕度值_前';

COMMENT ON COLUMN "B_G2_01401_REC"."YDZ_QDW" IS
'裕度值_前单位：10(CAL)、11(DAY)、12(MON)、20(HRS)、30(CYC)';

COMMENT ON COLUMN "B_G2_01401_REC"."YDZ_H" IS
'裕度值_后';

COMMENT ON COLUMN "B_G2_01401_REC"."YDZ_HDW" IS
'裕度值_后单位：10(CAL)、11(DAY)、12(MON)、20(HRS)、30(CYC)';

COMMENT ON COLUMN "B_G2_01401_REC"."ZT" IS
'状态：0无效、1有效';

COMMENT ON COLUMN "B_G2_01401_REC"."WHBMID" IS
'维护部门id。关联组织机构表';

COMMENT ON COLUMN "B_G2_01401_REC"."WHRID" IS
'维护人id。关联用户表';

COMMENT ON COLUMN "B_G2_01401_REC"."WHSJ" IS
'维护时间。yyyymmddhh24miss';

COMMENT ON COLUMN "B_G2_01401_REC"."REC_XGLX" IS
'记录-修改类型：1新增、2修改、3删除';

COMMENT ON COLUMN "B_G2_01401_REC"."REC_CZRID" IS
'记录-操作人id';

COMMENT ON COLUMN "B_G2_01401_REC"."REC_CZSJ" IS
'记录-操作时间。yyymmddhh24miss';

COMMENT ON COLUMN "B_G2_01401_REC"."REC_IP" IS
'记录-ip';

COMMENT ON COLUMN "B_G2_01401_REC"."REC_CZSM" IS
'记录-操作说明';

COMMENT ON COLUMN "B_G2_01401_REC"."REC_CZLS" IS
'记录-操作流水 uuid';

COMMENT ON COLUMN "B_G2_01401_REC"."REC_ZBID" IS
'记录-主表ID';

CREATE TABLE "B_G2_01402_REC"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "MAINID"             VARCHAR2(50)                    NOT NULL,
   "XC"                 INTEGER,
   "FJZCH"              VARCHAR2(20),
   "WHDWID"             VARCHAR2(50),
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

COMMENT ON TABLE "B_G2_01402_REC" IS
'b_g2_01402_rec 生产指令-飞机关系-修改记录';

COMMENT ON COLUMN "B_G2_01402_REC"."ID" IS
'uuid';

COMMENT ON COLUMN "B_G2_01402_REC"."MAINID" IS
'mainID';

COMMENT ON COLUMN "B_G2_01402_REC"."XC" IS
'项次';

COMMENT ON COLUMN "B_G2_01402_REC"."FJZCH" IS
'飞机注册号';

COMMENT ON COLUMN "B_G2_01402_REC"."WHDWID" IS
'维护单位id。关联组织结构表';

COMMENT ON COLUMN "B_G2_01402_REC"."WHRID" IS
'维护人id。关联用户表';

COMMENT ON COLUMN "B_G2_01402_REC"."WHSJ" IS
'维护时间。yyyymmddhh24miss';

COMMENT ON COLUMN "B_G2_01402_REC"."REC_XGLX" IS
'记录-修改类型：1新增、2修改、3删除';

COMMENT ON COLUMN "B_G2_01402_REC"."REC_CZRID" IS
'记录-操作人id';

COMMENT ON COLUMN "B_G2_01402_REC"."REC_CZSJ" IS
'记录-操作时间。yyymmddhh24miss';

COMMENT ON COLUMN "B_G2_01402_REC"."REC_IP" IS
'记录-ip';

COMMENT ON COLUMN "B_G2_01402_REC"."REC_CZSM" IS
'记录-操作说明';

COMMENT ON COLUMN "B_G2_01402_REC"."REC_CZLS" IS
'记录-操作流水 uuid';

COMMENT ON COLUMN "B_G2_01402_REC"."REC_ZBID" IS
'记录-主表ID';

CREATE TABLE "B_G2_014"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "DPRTCODE"           VARCHAR2(50)                    NOT NULL,
   "ZLBH"               VARCHAR2(50)                    NOT NULL,
   "BB"                 NUMBER(12,2)                    NOT NULL,
   "ZLMS"               VARCHAR2(4000)                  NOT NULL,
   "JX"                 VARCHAR2(50),
   "ZJH"                VARCHAR2(20),
   "SYX"                VARCHAR2(20),
   "GKBH"               VARCHAR2(50),
   "JSGS"               INTEGER                        DEFAULT 3,
   "IS_HDWZ"            INTEGER                        DEFAULT 0,
   "JGMS"               VARCHAR2(4000),
   "BZ"                 VARCHAR2(4000),
   "ZT"                 INTEGER,
   "F_BBID"             VARCHAR2(50),
   "B_BBID"             VARCHAR2(50),
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   "SXSJ"               DATE,
   CONSTRAINT PK_B_G2_014 PRIMARY KEY ("ID")
         USING INDEX TABLESPACE TS_INDEX
)
TABLESPACE TS_YWHD;

COMMENT ON TABLE "B_G2_014" IS
'b_g2_014 生产指令';

COMMENT ON COLUMN "B_G2_014"."ID" IS
'uuid';

COMMENT ON COLUMN "B_G2_014"."DPRTCODE" IS
'机构代码';

COMMENT ON COLUMN "B_G2_014"."ZLBH" IS
'指令编号';

COMMENT ON COLUMN "B_G2_014"."BB" IS
'版本号';

COMMENT ON COLUMN "B_G2_014"."ZLMS" IS
'指令描述';

COMMENT ON COLUMN "B_G2_014"."JX" IS
'机型。来源于机型参考系';

COMMENT ON COLUMN "B_G2_014"."ZJH" IS
'ATA章节号';

COMMENT ON COLUMN "B_G2_014"."SYX" IS
'适用性：00000（表示通用）、-（表示设置有飞机注册号）、null表示无';

COMMENT ON COLUMN "B_G2_014"."GKBH" IS
'工卡编号';

COMMENT ON COLUMN "B_G2_014"."JSGS" IS
'计算公式：1计划与实际取小再加上周期、2实际加上周期、3计划加上周期';

COMMENT ON COLUMN "B_G2_014"."IS_HDWZ" IS
'后到为准：0否、1是';

COMMENT ON COLUMN "B_G2_014"."JGMS" IS
'间隔描述';

COMMENT ON COLUMN "B_G2_014"."BZ" IS
'备注';

COMMENT ON COLUMN "B_G2_014"."ZT" IS
'状态：1保存、2提交、3已审核、4已批准、5审核驳回、6审批驳回、7生效、9关闭';

COMMENT ON COLUMN "B_G2_014"."F_BBID" IS
'前版本id：记录该版本的前一个版本id';

COMMENT ON COLUMN "B_G2_014"."B_BBID" IS
'后版本id：记录该版本改版后的版本id';

COMMENT ON COLUMN "B_G2_014"."WHBMID" IS
'维护部门id。关联组织机构表';

COMMENT ON COLUMN "B_G2_014"."WHRID" IS
'维护人id。关联用户表';

COMMENT ON COLUMN "B_G2_014"."WHSJ" IS
'维护时间。yyyymmddhh24miss';

COMMENT ON COLUMN "B_G2_014"."SXSJ" IS
'生效时间。yyyymmddhh24miss';

CREATE TABLE "B_G2_01401"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "MAINID"             VARCHAR2(50)                    NOT NULL,
   "JKLBH"              VARCHAR2(20),
   "JKFLBH"             VARCHAR2(20),
   "SCZ"                VARCHAR2(20),
   "DW_SCZ"             INTEGER,
   "ZQZ"                VARCHAR2(20),
   "DW_ZQZ"             INTEGER,
   "YDZ_Q"              VARCHAR2(20),
   "YDZ_QDW"            INTEGER,
   "YDZ_H"              VARCHAR2(20),
   "YDZ_HDW"            INTEGER,
   "ZT"                 INTEGER,
   "WHBMID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   CONSTRAINT PK_B_G2_01401 PRIMARY KEY ("ID")
         USING INDEX TABLESPACE TS_INDEX
)
TABLESPACE TS_YWHD;

COMMENT ON TABLE "B_G2_01401" IS
'b_g2_01401 生产指令-监控项设置';

COMMENT ON COLUMN "B_G2_01401"."ID" IS
'uuid';

COMMENT ON COLUMN "B_G2_01401"."MAINID" IS
'mainID';

COMMENT ON COLUMN "B_G2_01401"."JKLBH" IS
'监控项目编号';

COMMENT ON COLUMN "B_G2_01401"."JKFLBH" IS
'监控分类编号';

COMMENT ON COLUMN "B_G2_01401"."SCZ" IS
'首次值';

COMMENT ON COLUMN "B_G2_01401"."DW_SCZ" IS
'首次值单位：10(CAL)、11(DAY)、12(MON)、20(HRS)、30(CYC)';

COMMENT ON COLUMN "B_G2_01401"."ZQZ" IS
'周期值';

COMMENT ON COLUMN "B_G2_01401"."DW_ZQZ" IS
'周期值单位：10(CAL)、11(DAY)、12(MON)、20(HRS)、30(CYC)';

COMMENT ON COLUMN "B_G2_01401"."YDZ_Q" IS
'裕度值_前';

COMMENT ON COLUMN "B_G2_01401"."YDZ_QDW" IS
'裕度值_前单位：10(CAL)、11(DAY)、12(MON)、20(HRS)、30(CYC)';

COMMENT ON COLUMN "B_G2_01401"."YDZ_H" IS
'裕度值_后';

COMMENT ON COLUMN "B_G2_01401"."YDZ_HDW" IS
'裕度值_后单位：10(CAL)、11(DAY)、12(MON)、20(HRS)、30(CYC)';

COMMENT ON COLUMN "B_G2_01401"."ZT" IS
'状态：0无效、1有效';

COMMENT ON COLUMN "B_G2_01401"."WHBMID" IS
'维护部门id。关联组织机构表';

COMMENT ON COLUMN "B_G2_01401"."WHRID" IS
'维护人id。关联用户表';

COMMENT ON COLUMN "B_G2_01401"."WHSJ" IS
'维护时间。yyyymmddhh24miss';

/*==============================================================*/
/* Index: "INDEX_35"                                            */
/*==============================================================*/
CREATE INDEX "INDEX_35" ON "B_G2_01401" (
   "MAINID" ASC
)
TABLESPACE TS_INDEX;

CREATE TABLE "B_G2_01402"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "MAINID"             VARCHAR2(50),
   "XC"                 INTEGER,
   "FJZCH"              VARCHAR2(20),
   "WHDWID"             VARCHAR2(50),
   "WHRID"              VARCHAR2(50),
   "WHSJ"               DATE,
   CONSTRAINT PK_B_G2_01402 PRIMARY KEY ("ID")
         USING INDEX TABLESPACE TS_INDEX
)
TABLESPACE TS_YWHD;

COMMENT ON TABLE "B_G2_01402" IS
'b_g2_01402 生产指令-飞机关系';

COMMENT ON COLUMN "B_G2_01402"."ID" IS
'uuid';

COMMENT ON COLUMN "B_G2_01402"."MAINID" IS
'mainid';

COMMENT ON COLUMN "B_G2_01402"."XC" IS
'项次';

COMMENT ON COLUMN "B_G2_01402"."FJZCH" IS
'飞机注册号';

COMMENT ON COLUMN "B_G2_01402"."WHDWID" IS
'维护单位id。关联组织结构表';

COMMENT ON COLUMN "B_G2_01402"."WHRID" IS
'维护人id。关联用户表';

COMMENT ON COLUMN "B_G2_01402"."WHSJ" IS
'维护时间。yyyymmddhh24miss';

/*==============================================================*/
/* Index: "INDEX_36"                                            */
/*==============================================================*/
CREATE INDEX "INDEX_36" ON "B_G2_01402" (
   "MAINID" ASC
)
TABLESPACE TS_INDEX;
