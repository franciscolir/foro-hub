create table usuarios(
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    correo_electronico varchar(100) UNIQUE not null,
    contraseña varchar(100) not null,
    perfil_id bigint not null,
    primary key(id),
    constraint fk_usuarios_perfil_id foreign key(perfil_id) references perfiles(id)
    );