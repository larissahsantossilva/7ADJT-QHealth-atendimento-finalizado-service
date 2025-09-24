CREATE SCHEMA IF NOT EXISTS atendimento_finalizado;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE atendimento_finalizado.atendimento (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    cpf VARCHAR(11) NOT NULL,
    status VARCHAR(255) NOT NULL,
    fila_id UUID NOT NULL,
    posicao_fila INTEGER NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_ultima_alteracao TIMESTAMP
);

