alter table t_user add email varchar2(300);
COMMENT ON COLUMN t_user.email IS '邮箱';