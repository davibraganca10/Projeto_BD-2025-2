
--TABELA REGIAO
INSERT INTO Regiao (nome_regiao) VALUES ('Asa Sul');
INSERT INTO Regiao (nome_regiao) VALUES ('Asa Norte');
INSERT INTO Regiao (nome_regiao) VALUES ('Taguatinga');
INSERT INTO Regiao (nome_regiao) VALUES ('Ceilândia');
INSERT INTO Regiao (nome_regiao) VALUES ('Lago Norte');

--TABELA TIPO_SERVICO
INSERT INTO Tipo_Servico (nome_servico, descricao) VALUES ('Iluminação', 'Reparo de postes e troca de lâmpadas');
INSERT INTO Tipo_Servico (nome_servico, descricao) VALUES ('Pavimentação', 'Operação tapa-buraco em via pública');
INSERT INTO Tipo_Servico (nome_servico, descricao) VALUES ('Limpeza', 'Remoção de entulho e lixo acumulado');
INSERT INTO Tipo_Servico (nome_servico, descricao) VALUES ('Jardinagem', 'Poda de árvores e corte de grama');
INSERT INTO Tipo_Servico (nome_servico, descricao) VALUES ('Hidráulica', 'Reparo de vazamentos e bueiros');

--TABELA STATUS_OCORRENCIA
INSERT INTO Status_ocorrencia (nome_status) VALUES ('Aberto');
INSERT INTO Status_ocorrencia (nome_status) VALUES ('Em Análise');
INSERT INTO Status_ocorrencia (nome_status) VALUES ('Em Andamento');
INSERT INTO Status_ocorrencia (nome_status) VALUES ('Concluído');
INSERT INTO Status_ocorrencia (nome_status) VALUES ('Cancelado');

--TABELA MATERIAL
INSERT INTO Material (nome_material, unidade_medida) VALUES ('Lâmpada LED', 'Unidade');
INSERT INTO Material (nome_material, unidade_medida) VALUES ('Cimento', 'Saco 50kg');
INSERT INTO Material (nome_material, unidade_medida) VALUES ('Areia', 'Metro Cubico');
INSERT INTO Material (nome_material, unidade_medida) VALUES ('Cabo 10mm', 'Metros');
INSERT INTO Material (nome_material, unidade_medida) VALUES ('Tinta', 'Lata 18L');

--TABELA EQUIPE
INSERT INTO Equipe (especialidade) VALUES ('Elétrica');
INSERT INTO Equipe (especialidade) VALUES ('Obras');
INSERT INTO Equipe (especialidade) VALUES ('Saneamento');
INSERT INTO Equipe (especialidade) VALUES ('Paisagismo');
INSERT INTO Equipe (especialidade) VALUES ('Limpeza Urbana');

--TABELA CIDADAO
INSERT INTO Cidadao (nome_cidadao, email_cidadao, telefone_cidadao, cpf_cidadao) VALUES ('João Silva', 'joao@email.com', '999911111', '11111111111');
INSERT INTO Cidadao (nome_cidadao, email_cidadao, telefone_cidadao, cpf_cidadao) VALUES ('Maria Souza', 'maria@email.com', '999922222', '22222222222');
INSERT INTO Cidadao (nome_cidadao, email_cidadao, telefone_cidadao, cpf_cidadao) VALUES ('Pedro Santos', 'pedro@email.com', '999933333', '33323333333');
INSERT INTO Cidadao (nome_cidadao, email_cidadao, telefone_cidadao, cpf_cidadao) VALUES ('Ana Oliveira', 'ana@email.com', '999944444', '44444444444');
INSERT INTO Cidadao (nome_cidadao, email_cidadao, telefone_cidadao, cpf_cidadao) VALUES ('Lucas Lima', 'lucas@email.com', '999955555', '55555555555');

--TABELA VEICULO
INSERT INTO Veiculo (id_equipe, modelo_veiculo, placa) VALUES (1, 'Carro', 'ABC1234');
INSERT INTO Veiculo (id_equipe, modelo_veiculo, placa) VALUES (2, 'Rolo Compressor', 'DEF5678');
INSERT INTO Veiculo (id_equipe, modelo_veiculo, placa) VALUES (3, 'Caminhão Pipa', 'GHI9012');
INSERT INTO Veiculo (id_equipe, modelo_veiculo, placa) VALUES (4, 'Caminhonete', 'JKL3456');
INSERT INTO Veiculo (id_equipe, modelo_veiculo, placa) VALUES (5, 'Caminhão Lixo', 'MNO7890');

--TABELA FUNCIONARIO
INSERT INTO Funcionario (id_equipe, nome_funcionario, cargo, matricula, cpf_funcionario) VALUES (1, 'Carlos Eletricista', 'Técnico', 'M0001', '66666666666');
INSERT INTO Funcionario (id_equipe, nome_funcionario, cargo, matricula, cpf_funcionario) VALUES (2, 'Roberto Pedreiro', 'Operário', 'M0002', '77777777777');
INSERT INTO Funcionario (id_equipe, nome_funcionario, cargo, matricula, cpf_funcionario) VALUES (3, 'Fernanda Encanadora', 'Técnica', 'M0003', '88888888888');
INSERT INTO Funcionario (id_equipe, nome_funcionario, cargo, matricula, cpf_funcionario) VALUES (4, 'Julia Jardineira', 'Assistente', 'M0004', '99999999999');
INSERT INTO Funcionario (id_equipe, nome_funcionario, cargo, matricula, cpf_funcionario) VALUES (5, 'Marcos Gari', 'Operário', 'M0005', '00000000000');

--TABELA SOLICITACAO
INSERT INTO Solicitacao (id_regiao, id_cidadao, id_tipo_servico, id_status, logradouro, numero_solicitacao, referencia) VALUES (1, 1, 1, 1, 'SQS 102 Bloco A', 'SOL001', 'Poste apagado na esquina');
INSERT INTO Solicitacao (id_regiao, id_cidadao, id_tipo_servico, id_status, logradouro, numero_solicitacao, referencia) VALUES (2, 2, 2, 2, 'CLN 205 Bloco C', 'SOL002', 'Buraco na faixa da direita');
INSERT INTO Solicitacao (id_regiao, id_cidadao, id_tipo_servico, id_status, logradouro, numero_solicitacao, referencia) VALUES (3, 3, 3, 3, 'QNA 10 Casa 5', 'SOL003', 'Lixo acumulado na calçada');
INSERT INTO Solicitacao (id_regiao, id_cidadao, id_tipo_servico, id_status, logradouro, numero_solicitacao, referencia) VALUES (4, 4, 4, 4, 'QNP 15 Conjunto B', 'SOL004', 'Árvore caindo sobre muro');
INSERT INTO Solicitacao (id_regiao, id_cidadao, id_tipo_servico, id_status, logradouro, numero_solicitacao, referencia) VALUES (5, 5, 5, 1, 'SHIN QI 5 Conjunto 9', 'SOL005', 'Vazamento de água na rua');

--TABELA ORDEM_SERVICO
INSERT INTO Ordem_Servico (id_solicitacao, id_equipe, data_atribuicao, relatorio_tecnico) VALUES (3, 5, '2025-10-20 08:00:00', 'Equipe deslocada para limpeza');
INSERT INTO Ordem_Servico (id_solicitacao, id_equipe, data_atribuicao, relatorio_tecnico) VALUES (4, 4, '2025-10-21 09:00:00', 'Poda realizada com sucesso');
INSERT INTO Ordem_Servico (id_solicitacao, id_equipe, data_atribuicao, relatorio_tecnico) VALUES (2, 2, '2025-10-22 10:30:00', 'Tapa-buraco agendado');
INSERT INTO Ordem_Servico (id_solicitacao, id_equipe, data_atribuicao, relatorio_tecnico) VALUES (1, 1, '2025-10-23 14:00:00', NULL);
INSERT INTO Ordem_Servico (id_solicitacao, id_equipe, data_atribuicao, relatorio_tecnico) VALUES (5, 3, NULL, NULL);

--TABELA MATERIAL_UTILIZADO
INSERT INTO Material_Utilizado (id_material, id_ordem_servico, quantidade_usada) VALUES (4, 1, 10);
INSERT INTO Material_Utilizado (id_material, id_ordem_servico, quantidade_usada) VALUES (2, 3, 5);
INSERT INTO Material_Utilizado (id_material, id_ordem_servico, quantidade_usada) VALUES (1, 4, 1);
INSERT INTO Material_Utilizado (id_material, id_ordem_servico, quantidade_usada) VALUES (5, 2, 2);
INSERT INTO Material_Utilizado (id_material, id_ordem_servico, quantidade_usada) VALUES (3, 3, 1);