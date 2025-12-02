package projeto.bd.models;

public class Funcionario {
    private Integer id;
    private Integer idEquipe;
    private String nome;
    private String cargo;
    private String matricula;
    private String cpf;

    public Funcionario() {}

    public Funcionario(Integer id, Integer idEquipe, String nome, String cargo, String matricula, String cpf) {
        this.id = id;
        this.idEquipe = idEquipe;
        this.nome = nome;
        this.cargo = cargo;
        this.matricula = matricula;
        this.cpf = cpf;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdEquipe() { return idEquipe; }
    public void setIdEquipe(Integer idEquipe) { this.idEquipe = idEquipe; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    @Override
    public String toString() {
        return nome + " (" + cargo + ")";
    }
}