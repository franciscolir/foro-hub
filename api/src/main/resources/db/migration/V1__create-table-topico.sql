create table topicos(
    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(500) not null,
    fecha_creacion datetime not null,
    status varchar(50) not null,
    usuario_id bigint not null,
    curso_id bigint not null,
    respuestas bigint DEFAULT 0,


    primary key(id)

       constraint fk_topicos_usuario_id foreign key(usuario_id) references usuarios(id),
       constraint fk_topicos_curso_id foreign key(curso_id) references cursos(id)
    );