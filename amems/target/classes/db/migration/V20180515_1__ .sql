alter table b_h_001 add qczt varchar2(50);
alter table b_h_001_rec add qczt varchar2(50);
alter table b_h_003 add qczt varchar2(50);
alter table b_h_003_rec add qczt varchar2(50);
alter table b_h_017 add qczt varchar2(50);

COMMENT ON COLUMN b_h_001.qczt IS '器材状态,来源于数据字典';
COMMENT ON COLUMN b_h_001_rec.qczt IS '器材状态,来源于数据字典';
COMMENT ON COLUMN b_h_003.qczt IS '器材状态,来源于数据字典';
COMMENT ON COLUMN b_h_003_rec.qczt IS '器材状态,来源于数据字典';
COMMENT ON COLUMN b_h_017.qczt IS '器材状态,来源于数据字典';

insert into d_001 (lxid, lxmc, xlms, is_bj, is_xz, is_sc, is_mc, is_sz, dprtCode) values (86, '器材状态', '', 1, 1, 1, 1, 1, '-1');
