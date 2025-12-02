package projeto.bd.models;

public class Veiculo {
    private Integer id;
    private Integer idEquipe; // FK
    private String modelo;
    private String placa;

    public Veiculo() {}

    public Veiculo(Integer id, Integer idEquipe, String modelo, String placa) {
        this.id = id;
        this.idEquipe = idEquipe;
        this.modelo = modelo;
        this.placa = placa;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIdEquipe() { return idEquipe; }
    public void setIdEquipe(Integer idEquipe) { this.idEquipe = idEquipe; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    @Override
    public String toString() { return modelo + " (" + placa + ")"; }
}
