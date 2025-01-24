create database if not exists granturismo4
default character set utf8 collate utf8_general_ci;

use granturismo4;

create user if not exists 'gt4'@'localhost' identified by 'spooneg';
grant insert,select, create, alter, update on granturismo4.* to'gt4'@'localhost';

create table if not exists marcas(
	id int auto_increment primary key,
    nombre varchar(150) not null,
    pais varchar(150) not null
);

create table if not exists coches(
	id int auto_increment primary key,
    marca integer not null,
	nombre varchar(150),
    anyo integer not null,
constraint fk1_coches_marca foreign key (marca) references marcas(id) on update cascade on delete no action
);

create table if not exists usuarios(
	id int auto_increment primary key,
    nombre varchar(30) not null,
    apellidos varchar(60) not null,
    nombreUsuario varchar(60) unique not null,
    pass varchar(30) not null,
    tipo char(1) default 'u' 
);

create table if not exists garaje(
	usuario int,
    coche int,
	primary key (usuario, coche),
constraint fk2_garaje_usuarios foreign key (usuario) references usuarios(id) on update cascade on delete no action,
constraint fk3_garaje_coche foreign key (coche) references coches(id) on update cascade on delete no action
);
