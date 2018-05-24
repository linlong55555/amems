select t1.wxrydaid, --人员档案id
       t1.dprtcode,
       t1.rybh,
       t1.xm,
       t1.szdw,
       t1.kcid,
       t1.kcbh,
       t1.kcmc,
       t1.sjgw, --涉及岗位
       t1.zqz, --复训周期
       t1.zqdw, --周期单位
       t1.fxqx, --复训期限(+)
       t1.sj_ksrq, --上次培训日期
       t1.scpxjhid, --上次培训计划id
       t1.xcpxrq, --下次培训日期     
       (case
         when nvl(t1.xcpxrq, to_date('9999-12-31', 'YYYY-MM-DD')) < t1.fxqx then
          t1.xcpxrq - sysdate
         else
          t1.fxqx - sysdate
       end) sy, --剩余
       t1.jh_ksrq, --培训计划
       t1.pxjhid, --培训计划id
       t1.pxlb,
       t1.sjks,
       t1.pxxs,
       t1.ksxs,
       t1.jsxm,
       t1.pxgh,
       t1.cql,
       t1.cj,
       t1.zs,
       t1.khjg,
       COALESCE(t1.fjjx, 'N/A') fjjx
  from (select temp_.wxrydaid,
               bz001.dprtcode,
               bz001.rybh,
               bz001.xm,
               bz001.szdw,
               temp_.kcid,
               bp003.kcbh,
               bp003.kcmc,
               bp001_sjgw.sjgw, --涉及岗位
               bp003.zqz,
               bp003.zqdw,
               (case
                 when bp003.zqdw = 1 then
                  (case
                    when bp00201.sj_ksrq is null then
                     sysdate
                    else
                     bp00201.sj_ksrq + bp003.zqz
                  end)
                 when bp003.zqdw = 2 then
                  (case
                    when bp00201.sj_ksrq is null then
                     sysdate
                    else
                     add_months(bp00201.sj_ksrq, bp003.zqz)
                  end)
                 when bp003.zqdw = 3 then
                  (case
                    when bp00201.sj_ksrq is null then
                     sysdate
                    else
                     add_months(bp00201.sj_ksrq, (bp003.zqz * 12))
                  end)
               end) as fxqx, --复训期限
               bp00201.xcpxrq, --下次培训日期   
               bp002.jhsj jh_ksrq, --培训计划
               bp002.pxjhid, --培训计划id
               bp00201.sj_ksrq, --上次培训日期
               bp00201.pxjhid scpxjhid, --上次培训计划id
               bp00201.pxlb,
               bp00201.sjks,
               bp00201.pxxs,
               bp00201.ksxs,
               bp00201.jsxm,
               bp00201.pxgh,
               bp00201.cql,
               bp00201.cj,
               bp00201.zs,
               bp00201.khjg,
               bp003.fjjx
          from (select distinct wxrydaid, kcid
                  from (select bp004.wxrydaid, bp004.kcid
                          from b_p_004 bp004
                        union all
                        select bp006.wxrydaid, bp003.id kcid
                          from b_p_006 bp006
                          join b_p_005 bp005
                            on bp005.zt = 1
                           and bp006.zt = 1
                           and bp006.gwid = bp005.gwid
                          join b_p_001 bp001
                            on bp001.id = bp006.gwid
                          join b_p_003 bp003
                            on bp003.zt = 1
                           and bp001.dprtcode = bp003.dprtcode
                           and bp003.kcbh = bp005.kcid
                           and COALESCE(bp003.fjjx, '-') =
                               COALESCE(bp006.fjjx, '-'))) temp_
          join b_p_003 bp003
            on bp003.zt = 1
           and bp003.is_fx = 1
           and temp_.kcid = bp003.id
          left join b_z_001 bz001
            on temp_.wxrydaid = bz001.id
        --关联上一次的培训记录
          left join (select tmp_.sj_ksrq,
                           tmp_.xcpxrq,
                           tmp_.pxlb,
                           tmp_.sjks,
                           tmp_.pxxs,
                           tmp_.ksxs,
                           tmp_.jsxm,
                           tmp_.pxgh,
                           tmp_.cql,
                           tmp_.cj,
                           tmp_.zs,
                           tmp_.khjg,
                           tmp_.pxjhid,
                           bp004.kcid,
                           bp004.wxrydaid
                      from b_p_00201 tmp_
                      join b_p_004 bp004
                        on bp004.pxjlid = tmp_.id) bp00201
            on bp00201.wxrydaid = temp_.wxrydaid
           and bp00201.kcid = temp_.kcid
        
        --关联已下发的培训计划        
          left join (SELECT t1.kcid, t1.wxrydaid, t1.jhsj, t1.pxjhid
                      FROM (SELECT ROW_NUMBER() OVER(PARTITION BY b_p_00201.kcid, b_p_00201.wxrydaid ORDER BY b_p_002.jh_ksrq desc, b_p_002.jh_kssj desc) rn,
                                   b_p_00201.kcid,
                                   b_p_00201.wxrydaid,
                                   b_p_00201.pxjhid,
                                   to_char(b_p_002.jh_ksrq, 'yyyy-mm-dd') ||
                                   (case
                                      when b_p_002.jh_kssj is null then
                                       ''
                                      else
                                       ' ' || b_p_002.jh_kssj
                                    end) jhsj
                              FROM b_p_00201
                              join b_p_002
                                on b_p_00201.zt = 1
                               and b_p_002.zt = 1
                               and b_p_00201.is_yc = 1
                               and b_p_00201.pxjhid = b_p_002.id) t1
                     WHERE rn = 1) bp002
            on temp_.kcid = bp002.kcid
           and temp_.wxrydaid = bp002.wxrydaid
        
        --关联人员岗位
          left join (select bp006.wxrydaid,
                           bp003.id kcid,
                           wm_concat(bp001.id || '#_#' || bp001.dgmc) as sjgw
                      from b_p_001 bp001 -- postgres  wm_concat(bp001.dgmc) -> string_agg(bp001.dgmc, ',')
                      join b_p_006 bp006
                        on bp001.zt = 1
                       and bp006.zt = 1
                       and bp001.id = bp006.gwid
                      join b_p_005 bp005
                        on bp001.zt = 1
                       and bp005.zt = 1
                       and bp005.gwid = bp001.id
                      join b_p_003 bp003
                        on bp003.dprtcode = bp001.dprtcode
                       and bp003.zt = 1
                       and bp003.kcbh = bp005.kcid
                       and coalesce(bp003.fjjx, '-') =
                           coalesce(bp006.fjjx, '-')
                     group by bp006.wxrydaid, bp003.id) bp001_sjgw
            on bp001_sjgw.wxrydaid = temp_.wxrydaid
           and bp001_sjgw.kcid = temp_.kcid) t1
 where 1 = 1;
