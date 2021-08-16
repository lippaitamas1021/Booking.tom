create table guests (
    id bigint(10) not null auto_increment,
    name varchar(255),
    room_id bigint(10),
    primary key (id));