create table shop.item
(
    id bigint not null auto_increment,
    detail longtext not null,
    name varchar(50) not null,
    sell_status varchar(255) not null ,
    price int not null,
    quantity int not null,
    date_created datetime(6) not null,
    date_updated datetime(6) not null,
    primary key (id)
) engine=InnoDB
DEFAULT CHARSET = utf8mb4
comment '상품 정책';


create table shop.member
(
    id bigint not null auto_increment,
    name varchar(50) not null,
    email varchar(200) not null,
    password varchar(500) not null,
    address varchar(500) not null,
    role varchar(50) not null,
    date_created datetime(6) not null,
    date_updated datetime(6) not null,
    primary key (id)
) engine=InnoDB
DEFAULT CHARSET = utf8mb4
comment '회원 정보';