create table transacoes (
                            id bigint not null auto_increment,
                            descricao varchar(255) not null,
                            valor decimal(10,2) not null,
                            data date not null,
                            tipo varchar(20) not null,
                            usuario_id bigint not null,

                            primary key (id),
                            constraint fk_transacoes_usuario foreign key (usuario_id) references usuarios(id)
);