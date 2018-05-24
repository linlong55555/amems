SELECT B_P_001.ID BID,
       B_P_001.DGBH,
       B_P_001.DGMC,
       B_P_006.ZT,
       B_P_003.ID KCID,
       B_P_003.KCBH,
       B_P_003.KCMC,
       B_P_003.IS_FX,
       B_P_003.ZQZ,
       B_P_003.ZQDW,
       COALESCE(B_P_003.FJJX, 'N/A') FJJX,
       BP00201.PXJHID SCPXJHID,
       BP00201.XCPXRQ,
       BP00201.SJ_KSRQ,
       BP00201.PXGH,
       BP00201.KCDD,
       BP00201.JSXM,
       BP00201.CQL,
       BP00201.CJ,
       BP00201.ZS,
       BP00201.KHJG,
       BP002_B.PXJHID PXJHID,
       BP002_B.JHSJ
  FROM B_P_006
  JOIN B_P_001
    ON B_P_006.GWID = B_P_001.ID
   AND B_P_001.ZT = 1
   AND B_P_006.ZT = 1
   AND B_P_006.WXRYDAID = 'db6f39d9-ebea-49a3-8274-66476ff85f64'
  JOIN B_P_005
    ON B_P_001.ID = B_P_005.GWID
   AND B_P_005.ZT = 1
  JOIN B_P_003
    ON B_P_003.DPRTCODE = B_P_001.DPRTCODE
   AND B_P_003.ZT = 1
   AND COALESCE(B_P_003.FJJX, '-') = COALESCE(B_P_006.FJJX, '-')
   AND (B_P_003.KCBH = B_P_005.KCID OR B_P_003.ID = B_P_005.KCID) --需要删掉OR后的条件

  LEFT JOIN (SELECT B_P_00201.PXJHID,
                    B_P_00201.XCPXRQ,
                    B_P_00201.SJ_KSRQ,
                    B_P_00201.PXGH,
                    B_P_00201.KCDD,
                    B_P_00201.JSXM,
                    B_P_00201.CQL,
                    B_P_00201.CJ,
                    B_P_00201.ZS,
                    B_P_00201.KHJG,
                    B_P_00201.KCID
               FROM B_P_00201
               JOIN B_P_004
                 ON B_P_00201.ZT = 1
                AND B_P_00201.ID = B_P_004.PXJLID
                AND B_P_004.WXRYDAID =
                    'db6f39d9-ebea-49a3-8274-66476ff85f64') BP00201
    ON BP00201.KCID = B_P_003.ID

  LEFT JOIN (SELECT T1.KCID, T1.WXRYDAID, T1.JHSJ, T1.PXJHID
               FROM (SELECT ROW_NUMBER() OVER(PARTITION BY B_P_00201.KCID, B_P_00201.WXRYDAID ORDER BY B_P_002.JH_KSRQ DESC, B_P_002.JH_KSSJ DESC) RN,
                            B_P_00201.KCID,
                            B_P_00201.WXRYDAID,
                            B_P_00201.PXJHID,
                            TO_CHAR(B_P_002.JH_KSRQ, 'yyyy-mm-dd') ||
                            (CASE
                               WHEN B_P_002.JH_KSSJ IS NULL THEN
                                ''
                               ELSE
                                ' ' || B_P_002.JH_KSSJ
                             END) JHSJ
                       FROM B_P_00201
                       JOIN B_P_002
                         ON B_P_00201.ZT = 1
                        AND B_P_002.ZT = 1
                        AND B_P_00201.IS_YC = 1
                        AND B_P_00201.PXJHID = B_P_002.ID) T1
              WHERE RN = 1) BP002_B
    ON B_P_003.ID = BP002_B.KCID
   AND B_P_006.WXRYDAID = BP002_B.WXRYDAID
 ORDER BY B_P_001.DGBH
