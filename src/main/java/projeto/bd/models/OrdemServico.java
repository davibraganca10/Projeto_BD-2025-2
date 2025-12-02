package projeto.bd.models;

import java.sql.Timestamp;

public class OrdemServico {
    private Integer id;
    private Integer idSolicitacao;
    private Integer idEquipe;
    private Timestamp dataAtribuicao;
    private Timestamp dataConclusao;
    private String relatorio;

    public Integer getId(){
        return id;
    }
    public void setId(Integer id)
    { this.id = id;
    }
    public Integer getIdSolicitacao(){
        return idSolicitacao;
    }
    public void setIdSolicitacao(Integer idSolicitacao)
    { this.idSolicitacao = idSolicitacao;
    }
    public Integer getIdEquipe() {
        return idEquipe;
    }
    public void setIdEquipe(Integer idEquipe){
        this.idEquipe = idEquipe;
    }
    public Timestamp getDataAtribuicao() {
        return dataAtribuicao;
    }
    public void setDataAtribuicao(Timestamp dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }
    public Timestamp getDataConclusao(){
        return dataConclusao;
    }
    public void setDataConclusao(Timestamp dataConclusao){
        this.dataConclusao = dataConclusao;
    }
    public String getRelatorio(){
        return relatorio;
    }
    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    @Override
    public String toString() {
        // concertar como o nome parece para a tela de matUtlz
        return "OS " + id + " (Solicitação: " + idSolicitacao + ")";
    }
}