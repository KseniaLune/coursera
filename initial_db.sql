create table t_usr
(
    c_id        uuid primary key,
    c_nickname  varchar(1024),
    c_password  varchar(1024),
    c_full_name varchar(1024),
    c_e_mail    varchar(1024),
    c_phone     bigint
)