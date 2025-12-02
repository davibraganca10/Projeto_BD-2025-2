package projeto.bd.models;

public class Material {
    private Integer id;
    private String nome;
    private String unidadeMedida;

    public Material() {}
    public Material(Integer id, String nome, String unidadeMedida) {
        this.id = id; this.nome = nome; this.unidadeMedida = unidadeMedida;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    @Override
    public String toString() { return nome + " (" + unidadeMedida + ")"; }
}