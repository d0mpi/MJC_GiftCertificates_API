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

