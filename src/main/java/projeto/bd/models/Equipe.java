package projeto.bd.models;

public class Equipe {
    private Integer id;
    private String especialidade;

    public Equipe() {}
    public Equipe(Integer id, String especialidade) {
        this.id = id;
        this.especialidade = especialidade;
    }

    public Integer getId(){
        return id;
    }
    public void setId(Integer id)
    { this.id = id;
    }
    public String getEspecialidade(){
        return especialidade;
    }
    public void setEspecialidade(String especialidade){
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return especialidade;
    }
}