package projeto.bd.models;

public class Cidadao {
        private Integer id;
        private String nome;
        private String email;
        private String telefone;
        private String cpf;

        public Cidadao() {}

        public Cidadao(String nome, String email, String telefone, String cpf) {
            this.nome = nome;
            this.email = email;
            this.telefone = telefone;
            this.cpf = cpf;
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

        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelefone() {
            return telefone;
        }
        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        public String getCpf() {
            return cpf;
        }
        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        @Override
        public String toString() {
            return "ID: " + id + " Nome: " + nome + " CPF: " + cpf;
        }
}

