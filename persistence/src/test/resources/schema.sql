drop schema if exists gift_service;
create schema if not exists gift_service;

create table certificate
(
    id               int  unsigned auto_increment,
    name             varchar(45)  not null,
    description      varchar(255) null,
    price            double       not null,
    duration         int          not null,
    create_date      datetime     not null,
    last_update_date datetime     not null
);

alter table certificate
    add primary key (id);

create table tag
(
    id   int unsigned auto_increment,
    name varchar(45) not null,
    constraint id_UNIQUE
        unique (id)
);

create table certificate_tag
(
    id             int unsigned auto_increment
        primary key,
    tag_id         int unsigned not null,
    certificate_id int unsigned not null,
    constraint certificate_id
        foreign key (certificate_id) references certificate (id)
            on update cascade on delete cascade,
    constraint tag_id
        foreign key (tag_id) references tag (id)
            on update cascade on delete cascade
);

alter table tag
    add primary key (id);


insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('Flying on a motor hang-glider' ,
        'Flying on a motor hang-glider gives you a crystal clear rush of adrenaline and an incomparable experience. ',
        55,
        31,
        '2021-06-12T10:34:09',
        '2021-06-12T10:34:09');
insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('Diving with dolphins' ,
        'The Dolphin Diving Certificate is a unique opportunity to interact with amazing marine life in their natural environment.',
        220,
        31,
        '2021-06-12T10:34:09',
        '2021-06-12T10:34:09');
insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('Men''s haircut ' ,
        'Stylish, well-groomed, impeccable ... These are all the answers to the question of what a men''s haircut looks like in a barbershop as a gift.',
        35,
        31,
        '2021-06-12T10:34:09',
        '2021-06-12T10:34:09');
insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('Relax full body massage ' ,
        'Relax massage is a classic type of massage aimed at relieving physical and nervous tension. ',
        35,
        31,
        '2021-06-12T10:34:09',
        '2021-06-12T10:34:09');
insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('Basic course "Drawing with a pencil"' ,
        'Especially for you, the drawing school "All Malevichi" has created a course that allows you to include your so-called "artistic vision" in just 6 lessons, learn the basic rules of drawing and learn how to draw complex pencil drawings from scratch. ',
        102,
        31,
        '2021-06-12T10:34:09',
        '2021-06-12T10:34:09');
insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('Horse ride' ,
        'It''s time for your dreams to come true! Horseback riding in a fairytale forest or horseback riding training gives you the opportunity to touch the world of these beautiful animals, communicate with them at home, relieve stress and accumulated fatigue.',
        150,
        31,
        '2021-06-12T10:34:09',
        '2021-06-12T10:34:09');
insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('Jeep trip' ,
        'Awesome "pokatushki" on impassable forest roads on a powerful SUV will delight anyone. Impenetrable mud, huge puddles, frequent descents, ascents and even fallen trees - steep all-terrain vehicles can handle everything.',
        129,
        31,
        '2021-06-12T10:34:09',
        '2021-06-12T10:34:09');
insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('Vocal Mastery Course' ,
        'Want to get 100 karaoke points, perform at events, or sing beautifully in the shower? In one month, you will master the basic theoretical knowledge and acquire professional performance skills that will help you develop in the future.',
        94,
        31,
        '2021-06-12T10:34:09',
        '2021-06-12T10:34:09');
insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('Hang learning ',
        'Touch the many years of experience of sages and healers, combined in one of the youngest musical instruments. There are no notes and ready-made compositions here. Hang sound is unique due to the intuitiveness of playing music. ',
        65,
        31,
        '2021-06-12T10:34:09',
        '2021-06-12T10:34:09');
insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('ATV racing ',
        'Does the soul require something extreme? Buy a certificate for riding ATVs in Minsk, gather a company of gambling friends and get ready for a burst of emotions and adrenaline! ',
        55,
        31,
        '2021-06-12T10:34:09',
        '2021-06-12T10:34:09');

insert into tag (name) values ('flight');
insert into tag (name) values ('sport');
insert into tag (name) values ('training');
insert into tag (name) values ('beauty salon');
insert into tag (name) values ('entertainment');
insert into tag (name) values ('for one person');
insert into tag (name) values ('for two persons');

insert into certificate_tag (tag_id, certificate_id) values (1, 1);
insert into certificate_tag (tag_id, certificate_id) values (6, 1);
insert into certificate_tag (tag_id, certificate_id) values (5, 2);
insert into certificate_tag (tag_id, certificate_id) values (4, 3);
insert into certificate_tag (tag_id, certificate_id) values (4, 4);
insert into certificate_tag (tag_id, certificate_id) values (3, 5);
insert into certificate_tag (tag_id, certificate_id) values (5, 6);
insert into certificate_tag (tag_id, certificate_id) values (5, 7);
insert into certificate_tag (tag_id, certificate_id) values (3, 8);
insert into certificate_tag (tag_id, certificate_id) values (6, 8);
insert into certificate_tag (tag_id, certificate_id) values (6, 3);

