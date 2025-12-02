package projeto.bd.models;

public class MaterialUtilizado {
    private Integer idMaterial;
    private Integer idOrdemServico;
    private Integer quantidade;

    public MaterialUtilizado() {}

    public Integer getIdMaterial() { return idMaterial; }
    public void setIdMaterial(Integer idMaterial) { this.idMaterial = idMaterial; }

    public Integer getIdOrdemServico() { return idOrdemServico; }
    public void setIdOrdemServico(Integer idOrdemServico) { this.idOrdemServico = idOrdemServico; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}