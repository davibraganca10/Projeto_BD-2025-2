-- projeto Banco de Dados

DROP TABLE IF EXISTS 
    Regiao,
    Tipo_Servico,
    Status_ocorrencia,
    Material,
    Equipe,
    Cidadao,
    Veiculo,
    Funcionario,
    Solicitacao,
    Ordem_Servico,
    Material_Utilizado
CASCADE;

CREATE TABLE Regiao (
    id_regiao SERIAL PRIMARY KEY,
    nome_regiao VARCHAR(100) NOT NULL
);

CREATE TABLE Tipo_Servico (
    id_tipo_servico SERIAL PRIMARY KEY,
    nome_servico VARCHAR(100) NOT NULL,
    descricao TEXT
);

CREATE TABLE Status_ocorrencia (
    id_status SERIAL PRIMARY KEY,
    nome_status VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Material (
    id_material SERIAL PRIMARY KEY,
    nome_material VARCHAR(150) NOT NULL,
    unidade_medida VARCHAR(20)
);

CREATE TABLE Equipe (
    id_equipe SERIAL PRIMARY KEY,
    especialidade VARCHAR(100)
);


CREATE TABLE Cidadao (
    id_cidadao SERIAL PRIMARY KEY,
    nome_cidadao VARCHAR(255) NOT NULL,
    email_cidadao VARCHAR(255) UNIQUE,
    telefone_cidadao CHAR(9),
    cpf_cidadao CHAR(11) UNIQUE NOT NULL
);

CREATE TABLE Veiculo (
    id_veiculo SERIAL PRIMARY KEY,
    id_equipe INTEGER NOT NULL,
    modelo_veiculo VARCHAR(100),
    placa CHAR(7) NOT NULL UNIQUE,
    FOREIGN KEY (id_equipe) REFERENCES Equipe(id_equipe)
);

CREATE TABLE Funcionario (
    id_funcionario SERIAL PRIMARY KEY,
    id_equipe INTEGER NOT NULL,
    nome_funcionario VARCHAR(255) NOT NULL,
    cargo VARCHAR(100),
    matricula CHAR(5) UNIQUE,
    cpf_funcionario CHAR(11) UNIQUE NOT NULL,
    FOREIGN KEY (id_equipe) REFERENCES Equipe(id_equipe)
);


CREATE TABLE Solicitacao (
    id_solicitacao SERIAL PRIMARY KEY,
    id_regiao INTEGER NOT NULL,
    id_cidadao INTEGER NOT NULL,
    id_tipo_servico INTEGER NOT NULL,
    id_status INTEGER NOT NULL,
    logradouro VARCHAR(255) NOT NULL,
    data_abertura TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    numero_solicitacao VARCHAR(50) UNIQUE,
    foto_ocorrencia BYTEA, --botar foto 
    referencia TEXT,
    FOREIGN KEY (id_regiao) REFERENCES Regiao(id_regiao),
    FOREIGN KEY (id_cidadao) REFERENCES Cidadao(id_cidadao),
    FOREIGN KEY (id_tipo_servico) REFERENCES Tipo_Servico(id_tipo_servico),
    FOREIGN KEY (id_status) REFERENCES Status_ocorrencia(id_status)
);
CREATE TABLE Ordem_Servico (
    id_ordem_servico SERIAL PRIMARY KEY,
    id_solicitacao INTEGER NOT NULL,
    id_equipe INTEGER NOT NULL,
    data_atribuicao TIMESTAMP,
    data_conclusao TIMESTAMP,
    relatorio_tecnico TEXT,
    FOREIGN KEY (id_solicitacao) REFERENCES Solicitacao(id_solicitacao),
    FOREIGN KEY (id_equipe) REFERENCES Equipe(id_equipe)
);


CREATE TABLE Material_Utilizado (
    id_material INTEGER NOT NULL,
    id_ordem_servico INTEGER NOT NULL,
    quantidade_usada INTEGER NOT NULL,
    PRIMARY KEY (id_material, id_ordem_servico),
    FOREIGN KEY (id_material) REFERENCES Material(id_material),
    FOREIGN KEY (id_ordem_servico) REFERENCES Ordem_Servico(id_ordem_servico)
);

SELECT * FROM EQUIPE;
SELECT * FROM VEICULO; 
SELECT * FROM FUNCIONARIO; 
SELECT * FROM REGIAO; 
SELECT * FROM SOLICITACAO; 
SELECT * FROM CIDADAO; 
SELECT * FROM TIPO_SERVICO; 
SELECT * FROM STATUS_OCORRENCIA; 
SELECT * FROM ORDEM_SERVICO; 
SELECT * FROM MATERIAL; 
SELECT * FROM MATERIAL_UTILIZADO; 

