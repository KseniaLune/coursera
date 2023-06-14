CREATE TABLE t_role
(
    c_id uuid primary key DEFAULT gen_random_uuid(),
    c_role varchar(255) not null unique
);

create table t_user_role
(
    c_user_id UUID NOT NULL,
    c_role_id UUID NOT NULL,
    PRIMARY KEY (c_user_id, c_role_id),
    FOREIGN KEY (c_user_id) REFERENCES t_usr (c_id) ON DELETE CASCADE,
    FOREIGN KEY (c_role_id) REFERENCES t_role (c_id) ON DELETE CASCADE

);

insert into t_role (c_role) values ('ROLE_OWNER');
insert into t_role (c_role) values ('ROLE_ADMIN');
insert into t_role (c_role) values ('ROLE_PROFESSOR');
insert into t_role (c_role) values ('ROLE_STUDENT');