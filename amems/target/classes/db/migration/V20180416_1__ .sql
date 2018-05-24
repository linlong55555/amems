/*==============================================================*/
/* Table: "T_BATCHTASK"                                         */
/*==============================================================*/
CREATE TABLE "T_BATCHTASK"  (
   "ID"                 VARCHAR2(50)                    NOT NULL,
   "DPRTCODE"           VARCHAR2(50),
   "ZT"                 INTEGER                         NOT NULL,
   "RWLX"               INTEGER,
   "RWBM"               VARCHAR2(50),
   "RWMS"               VARCHAR2(4000),
   "SQRID"              VARCHAR2(50),
   "SQSJ"               DATE,
   "FKSM"               VARCHAR2(4000),
   "FKSJ"               DATE,
   "NBSBM"              VARCHAR2(50),
   "WJDZ"               VARCHAR2(4000),
   "RWDXID"             VARCHAR2(50),
   CONSTRAINT PK_T_BATCHTASK PRIMARY KEY ("ID")
         USING INDEX TABLESPACE TS_INDEX
)
TABLESPACE TS_SYS;

COMMENT ON TABLE "T_BATCHTASK" IS
't_batchtask 后台任务';

COMMENT ON COLUMN "T_BATCHTASK"."ID" IS
'uuid';

COMMENT ON COLUMN "T_BATCHTASK"."DPRTCODE" IS
'机构代码';

COMMENT ON COLUMN "T_BATCHTASK"."ZT" IS
'状态：1待处理、2处理中、9失败、10完成';

COMMENT ON COLUMN "T_BATCHTASK"."RWLX" IS
'任务类型：1导出文档';

COMMENT ON COLUMN "T_BATCHTASK"."RWBM" IS
'任务编码';

COMMENT ON COLUMN "T_BATCHTASK"."RWMS" IS
'任务描述';

COMMENT ON COLUMN "T_BATCHTASK"."SQRID" IS
'申请人id';

COMMENT ON COLUMN "T_BATCHTASK"."SQSJ" IS
'申请时间';

COMMENT ON COLUMN "T_BATCHTASK"."FKSM" IS
'反馈说明';

COMMENT ON COLUMN "T_BATCHTASK"."FKSJ" IS
'反馈时间：YYYY-MM-DD HH24:MI:SS';

COMMENT ON COLUMN "T_BATCHTASK"."NBSBM" IS
'内部识别码';

COMMENT ON COLUMN "T_BATCHTASK"."WJDZ" IS
'文件地址';

COMMENT ON COLUMN "T_BATCHTASK"."RWDXID" IS
'任务对象id';
