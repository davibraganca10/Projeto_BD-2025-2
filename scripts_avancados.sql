--VIEW
CREATE VIEW View_Contatos_Por_Regiao AS
SELECT DISTINCT
    c.nome_cidadao AS Nome,
    c.telefone_cidadao AS Telefone,
    c.email_cidadao AS Email,
    r.nome_regiao AS Regiao_Solicitada
FROM Cidadao c
         JOIN Solicitacao s ON c.id_cidadao = s.id_cidadao
         JOIN Regiao r ON s.id_regiao = r.id_regiao;

SELECT * FROM View_Contatos_Por_Regiao;


--PROCEDURE
CREATE PROCEDURE Finalizar_Servico(
    meu_id INTEGER,
    texto_relatorio TEXT
)
    LANGUAGE SQL
AS '
    UPDATE Ordem_Servico
    SET relatorio_tecnico = texto_relatorio,
        data_conclusao = CURRENT_TIMESTAMP
    WHERE id_ordem_servico = meu_id;
';

CALL Finalizar_Servico(1, 'Serviço concluído com sucesso.');
SELECT * FROM Ordem_Servico WHERE id_ordem_servico = 1;

-- TRIGGER PARA ATUALIZAR STATUS

CREATE OR REPLACE FUNCTION func_atualizar_status_em_andamento()
RETURNS TRIGGER AS $$
BEGIN
UPDATE Solicitacao
SET id_status = 3 -- status 3 = em andamento
WHERE id_solicitacao = NEW.id_solicitacao;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;
-- sempre que INSERT uma OS, dispara a func_atualizar
CREATE TRIGGER tg_iniciar_atendimento
    AFTER
    INSERT
ON Ordem_Servico
    FOR EACH ROW
EXECUTE FUNCTION
    func_atualizar_status_em_andamento();