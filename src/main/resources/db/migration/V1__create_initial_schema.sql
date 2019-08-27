create sequence hibernate_sequence start 1 increment 1;
create table customers (id int8 not null, firstname varchar(255), lastname varchar(255), primary key (id));
