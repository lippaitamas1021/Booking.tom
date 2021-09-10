create table guests (
    id bigint not null auto_increment,
    name varchar(255),
    room_id bigint,
    primary key (id));

create table rooms (
    id bigint not null auto_increment,
    room_number varchar(255),
    primary key (id));

alter table guests add constraint FK_GR foreign key (room_id) references rooms (id);
