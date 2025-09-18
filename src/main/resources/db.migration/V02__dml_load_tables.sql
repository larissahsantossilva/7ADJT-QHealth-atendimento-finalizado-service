INSERT INTO atendimento
(paciente_id,
 status,
 fila_id,
 posicao_fila,
 data_criacao,
 data_ultima_alteracao)
VALUES
(uuid_generate_v4(), 'FINALIZADO', uuid_generate_v4(), 1, CURRENT_TIMESTAMP - INTERVAL '10 days', CURRENT_TIMESTAMP - INTERVAL '9 days'),
(uuid_generate_v4(), 'CANCELADO', uuid_generate_v4(), 2, CURRENT_TIMESTAMP - INTERVAL '8 days', CURRENT_TIMESTAMP - INTERVAL '7 days'),
(uuid_generate_v4(), 'EM_ANDAMENTO', uuid_generate_v4(), 3, CURRENT_TIMESTAMP - INTERVAL '6 days', CURRENT_TIMESTAMP - INTERVAL '5 days'),
(uuid_generate_v4(), 'FINALIZADO', uuid_generate_v4(), 4, CURRENT_TIMESTAMP - INTERVAL '5 days', CURRENT_TIMESTAMP - INTERVAL '4 days'),
(uuid_generate_v4(), 'AGUARDANDO', uuid_generate_v4(), 5, CURRENT_TIMESTAMP - INTERVAL '4 days', CURRENT_TIMESTAMP - INTERVAL '2 days'),
(uuid_generate_v4(), 'FINALIZADO', uuid_generate_v4(), 6, CURRENT_TIMESTAMP - INTERVAL '3 days', CURRENT_TIMESTAMP - INTERVAL '2 days'),
(uuid_generate_v4(), 'CANCELADO', uuid_generate_v4(), 7, CURRENT_TIMESTAMP - INTERVAL '2 days', CURRENT_TIMESTAMP - INTERVAL '1 days'),
(uuid_generate_v4(), 'AGUARDANDO', uuid_generate_v4(), 8, CURRENT_TIMESTAMP - INTERVAL '1 days', CURRENT_TIMESTAMP),
(uuid_generate_v4(), 'FINALIZADO', uuid_generate_v4(), 9, CURRENT_TIMESTAMP - INTERVAL '12 hours', CURRENT_TIMESTAMP - INTERVAL '6 hours'),
(uuid_generate_v4(), 'EM_ANDAMENTO', uuid_generate_v4(), 10, CURRENT_TIMESTAMP - INTERVAL '2 hours', CURRENT_TIMESTAMP);