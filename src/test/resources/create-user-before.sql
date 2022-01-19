delete from user_role;
delete from users;

insert into users(id, username, password, is_active) values
(1, 'admin', '$2a$08$ETGhKhwPKvK60cQyl5hYQOwmRPUPYJzaV6KTpgPn0HDk/Ybrn6A2u', true),
(2, 'user', '$2a$08$511YNt2qaEJFOiKTiAGwNeYvsej29VQqL1Rx6G/wfUkJhA607x9jG', true);

insert into user_role(user_id, roles) values
(1, 'ADMIN'), (1, 'USER'),
(2, 'USER');