create table t_user_course(
    c_id uuid primary key DEFAULT gen_random_uuid(),
    c_usr_id uuid references t_usr(c_id),
    c_course_id uuid references  t_course(c_id)
)