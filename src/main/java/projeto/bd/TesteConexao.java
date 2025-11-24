package projeto.bd; // Note que não tem o ".dao" aqui

import projeto.bd.dao.ConexaoFactory;
import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        System.out.println("Iniciando o Sistema de Ocorrências...");
        System.out.println("Tentando conectar ao banco de dados...");

        try (Connection con = ConexaoFactory.getConexao()) {

            if (con != null) {
                System.out.println("Conexão estabelecida com sucesso!");
            } else {
                System.out.println("A conexão veio nula.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao tentar conectar:");
            e.printStackTrace();
        }
    }
}