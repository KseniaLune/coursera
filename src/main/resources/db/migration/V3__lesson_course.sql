create table t_lesson(
    c_id uuid primary key default gen_random_uuid(),
    c_title varchar(1024),
    c_text text,
    c_course_id uuid,
    constraint fk_t_course foreign key (c_course_id) references t_course(c_id)
)
