package projeto.bd.models;

import java.sql.Timestamp;

public class Solicitacao {
    private Integer id;
    private Integer idRegiao;
    private Integer idCidadao;
    private Integer idTipoServico;
    private Integer idStatus;
    private String logradouro;
    private Timestamp dataAbertura;
    private String numeroSolicitacao;
    private byte[] foto;
    private String referencia;

    public Solicitacao() {}

    public Integer getId(){
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getIdRegiao(){
        return idRegiao;
    }
    public void setIdRegiao(Integer idRegiao) {
        this.idRegiao = idRegiao;
    }
    public Integer getIdCidadao(){
        return idCidadao;
    }
    public void setIdCidadao(Integer idCidadao) {
        this.idCidadao = idCidadao;
    }
    public Integer getIdTipoServico() {
        return idTipoServico;
    }
    public void setIdTipoServico(Integer idTipoServico) {
        this.idTipoServico = idTipoServico;
    }
    public Integer getIdStatus(){
        return idStatus;
    }
    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }
    public String getLogradouro(){
        return logradouro;
    }
    public void setLogradouro(String logradouro){
        this.logradouro = logradouro;
    }
    public Timestamp getDataAbertura(){
        return dataAbertura;
    }
    public void setDataAbertura(Timestamp dataAbertura){
        this.dataAbertura = dataAbertura;
    }
    public String getNumeroSolicitacao(){
        return numeroSolicitacao;
    }
    public void setNumeroSolicitacao(String numeroSolicitacao) {
        this.numeroSolicitacao = numeroSolicitacao;
    }
    public byte[] getFoto() {
        return foto;
    }
    public void setFoto(byte[] foto){
        this.foto = foto;
    }
    public String getReferencia() {
        return referencia;
    }
    public void setReferencia(String referencia){
        this.referencia = referencia;
    }

    @Override
    public String toString() { return numeroSolicitacao + " - " + logradouro; }
}