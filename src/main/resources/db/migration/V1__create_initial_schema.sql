create sequence hibernate_sequence start 1 increment 1;
create table midis_results (id int8 not null, created_at timestamp, score float8, midi_id varchar(255), midi_user_id varchar(255), primary key (id));
create table midis (id varchar(255) not null, user_id varchar(255) not null, data bytea, primary key (id, user_id));
alter table midis_results add constraint FKao02blfvxk3a82r32fwfmeuyc foreign key (midi_id, midi_user_id) references midis;
