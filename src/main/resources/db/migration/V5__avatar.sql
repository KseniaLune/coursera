create table if not exists t_users_avatars(
app_user_c_id uuid not null,
c_avatar varchar(255) not null,
constraint fk_t_users_avatars_t_usr foreign key (app_user_c_id) references t_usr (c_id) ON DELETE CASCADE on update cascade
);