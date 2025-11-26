package projeto.bd.dao;

import projeto.bd.models.Cidadao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CidadaoDAO {
    //crete
    public void salvar(Cidadao cidadao) {
        String sql = "INSERT INTO Cidadao (nome_cidadao, email_cidadao, telefone_cidadao, cpf_cidadao) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cidadao.getNome());
            stmt.setString(2, cidadao.getEmail());
            stmt.setString(3, cidadao.getTelefone());
            stmt.setString(4, cidadao.getCpf());
            stmt.execute();
            System.out.println("Cidadão criado");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar: " + e.getMessage(), e);
        }
    }

    //read
    public List<Cidadao> listarTodos() {
        String sql = "SELECT * FROM Cidadao";
        List<Cidadao> cidadaos = new ArrayList<>();

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cidadao c = new Cidadao();
                c.setId(rs.getInt("id_cidadao"));
                c.setNome(rs.getString("nome_cidadao"));
                c.setEmail(rs.getString("email_cidadao"));
                c.setTelefone(rs.getString("telefone_cidadao"));
                c.setCpf(rs.getString("cpf_cidadao"));

                cidadaos.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cidadãos", e);
        }
        return cidadaos;
    }

    //update
    public void atualizar(Cidadao cidadao) {
        String sql = "UPDATE Cidadao SET nome_cidadao=?, email_cidadao=?, telefone_cidadao=?, cpf_cidadao=? WHERE id_cidadao=?";

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cidadao.getNome());
            stmt.setString(2, cidadao.getEmail());
            stmt.setString(3, cidadao.getTelefone());
            stmt.setString(4, cidadao.getCpf());
            stmt.setInt(5, cidadao.getId());

            stmt.execute();
            System.out.println("Cidadão atualizado (ID: " + cidadao.getId() + ")");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cidadão", e);
        }
    }

    //delete
    public void deletar(int id) {
        String sql = "DELETE FROM Cidadao WHERE id_cidadao = ?";

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Cidadão removido!");
            } else {
                System.out.println("Nenhum cidadão com o ID: " + id);
            }

        } catch (SQLException e) {
            //se tentar apagar um cidadão que tem solicitações, vai dar erro de FK aqui
            throw new RuntimeException("Erro ao deletar cidadão. Verifique se ele possui solicitações.", e);
        }
    }
}