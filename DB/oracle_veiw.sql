DROP FUNCTION F_GETNAME_JKXM
/


CREATE OR REPLACE FUNCTION F_GETNAME_JKXM(JKLBH IN VARCHAR2)
  RETURN VARCHAR2 IS
  FUNCTIONRESULT VARCHAR2(200);
BEGIN
  SELECT CASE LOWER(JKLBH)
           WHEN LOWER('calendar') THEN
            '日历'
           WHEN LOWER('fuselage_flight_time') THEN
            '机身飞行时间'
           WHEN LOWER('search_light_time') THEN
            '搜索灯时间'
           WHEN LOWER('winch_time') THEN
            '绞车时间'
           WHEN LOWER('landing_gear_cycle') THEN
            '起落循环'
           WHEN LOWER('winch_cycle') THEN
            '绞车循环'
           WHEN LOWER('ext_suspension_loop') THEN
            '外吊挂循环'
           WHEN LOWER('search_light_cycle') THEN
            '搜索灯循环'
           WHEN LOWER('special_first') THEN
            '特殊监控1'
           WHEN LOWER('special_second') THEN
            '特殊监控2'
           WHEN LOWER('N1') THEN
            'N1'
           WHEN LOWER('N2') THEN
            'N2'
           WHEN LOWER('N3') THEN
            'N3'
           WHEN LOWER('N4') THEN
            'N4'
           WHEN LOWER('N5') THEN
            'N5'
           WHEN LOWER('N6') THEN
            'N6'
           ELSE
            JKLBH
         END
    INTO FUNCTIONRESULT
    FROM DUAL;
  RETURN(FUNCTIONRESULT);
END F_GETNAME_JKXM;
/

DROP FUNCTION F_GETREC_HD
/


CREATE OR REPLACE FUNCTION F_GETREC_HD(REC_CZSM IN VARCHAR2)
  RETURN VARCHAR2 IS
  FUNCTIONRESULT VARCHAR2(100);
BEGIN
  SELECT CASE LOWER(REC_CZSM)
           WHEN LOWER(0) THEN
            '创建'
           WHEN LOWER(1) THEN
            '提交'
           WHEN LOWER(2) THEN
            '已审核'
           WHEN LOWER(3) THEN
            '已批准'
           WHEN LOWER(4) THEN
            '中止关闭'
           WHEN LOWER(5) THEN
            '审核驳回'
           WHEN LOWER(6) THEN
            '批准驳回'
           WHEN LOWER(8) THEN
            '作废'
           WHEN LOWER(9) THEN
            '指定结束'
           WHEN LOWER(10) THEN
            '完成'
           WHEN LOWER(11) THEN
            '撤销'
           WHEN LOWER(12) THEN
            '修改'
           WHEN LOWER(13) THEN
            '改版'
           WHEN LOWER(14) THEN
            '已生效'
           WHEN LOWER(15) THEN
            '提交生产确认'
           WHEN LOWER(16) THEN
            '借出确认'
            WHEN LOWER(17) THEN
            '归还'
            WHEN LOWER(18) THEN
            '导入'
             WHEN LOWER(19) THEN
            '已评估'
           WHEN LOWER(20) THEN
            '修改-已新增了文件'
            WHEN LOWER(21) THEN
            '修改-已修改了文件'
            WHEN LOWER(22) THEN
            '修改-已删除了文件'
              WHEN LOWER(23) THEN
            '完工'
            WHEN LOWER(24) THEN
            '工单工时确认'
           ELSE
            REC_CZSM
         END
    INTO FUNCTIONRESULT
    FROM DUAL;
  RETURN(FUNCTIONRESULT);
END F_GETREC_HD;
/
DROP view V_ROLE_JX;

/*==============================================================*/
/* View: V_ROLE_JX                                              */
/*==============================================================*/
CREATE OR REPLACE VIEW V_ROLE_JX AS
SELECT B1.ID 

 USER_ID, B3.ROLE_ID, B3.DPRTCODE, B3.FJJX, B3.FJZCH, B3.XLH, B3.FDJSL
  FROM T_USER B1
  JOIN T_USER2ROLE_JX B2
    ON B1.ID 

 = B2.USER_ID
  JOIN (SELECT DISTINCT ROLE_ID, DPRTCODE, FJJX, FJZCH, XLH, FDJSL FROM
       (SELECT T1.ROLE_ID, T1.DPRTCODE, T1.FJJX, NVL(T2.FJZCH, '-') FJZCH, T2.XLH, T2.FDJSL
          FROM T_ROLE2JX T1
          LEFT JOIN D_007 T2
            ON T1.DPRTCODE = T2.DPRTCODE
           AND T1.FJZCH = T2.FJZCH
           AND T2.ZT = 1
         WHERE T1.LX = 2
        UNION
        SELECT A1.ROLE_ID, A1.DPRTCODE, A1.FJJX, NVL(A2.FJZCH, '-') FJZCH, A2.XLH, A2.FDJSL
          FROM T_ROLE2JX A1
          LEFT JOIN D_007 A2
            ON A1.DPRTCODE = A2.DPRTCODE
           AND A1.FJJX = A2.FJJX
           AND (A2.ZT = 1 OR A2.FJZCH IS NULL)
           AND A1.LX = 1)
          ) B3
    ON B2.ROLE_ID = B3.ROLE_ID
  JOIN D_004 B4
    ON B4.DPRTCODE = B3.DPRTCODE
   AND B4.ZT = 1
   AND B4.FJJX = B3.FJJX
WITH READ ONLY;


DROP view V_ROLE_WH;

/*==============================================================*/
/* View: V_ROLE_WH                                              */
/*==============================================================*/
CREATE OR REPLACE VIEW V_ROLE_WH AS
SELECT B1.ID AS USER_ID, B3.ROLE_ID, B3.CK_ID
  FROM T_USER B1
  JOIN T_USER2ROLE_CK B2
    ON B1.ID = B2.USER_ID
  JOIN T_ROLE2WH B3
    ON B2.ROLE_ID = B3.ROLE_ID
  JOIN D_009 B4 ON B3.CK_ID = B4.ID AND B4.ZT =1
WITH READ ONLY;
