INSERT INTO roles (name_role)
VALUES
    ('ROLE_USER'),
    ('ROLE_BREEDER'),
    ('ROLE_ADMIN');

insert into users (first_name, last_name, username, password, email)
values
    ('admin','admin','admin', '100', 'breedersbook@yandewx.ru');

insert into users_roles (user_id, role_id)
values
    (1, 3);