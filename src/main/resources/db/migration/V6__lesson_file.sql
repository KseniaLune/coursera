create table if not exists t_lesson_file
(
    lesson_c_id uuid ,
    c_file      varchar(255),
    constraint fk_t_lesson_file_t_lesson foreign key (lesson_c_id) references t_lesson (c_id) ON DELETE CASCADE on update cascade
);