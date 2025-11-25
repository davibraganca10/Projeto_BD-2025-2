package projeto.bd.models;

public class TipoServico {
    private Integer id;
    private String nome;
    private String descricao;

    public TipoServico() {}

    public TipoServico(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Nome:" + nome + "  Descrição: " + descricao;
    }
}