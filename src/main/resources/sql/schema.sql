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