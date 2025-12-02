package projeto.bd.dao;

import projeto.bd.models.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
//CREATE
    public void salvar(Funcionario f) {
        String sql = "INSERT INTO Funcionario (id_equipe, nome_funcionario, cargo, matricula, cpf_funcionario) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, f.getIdEquipe());
            stmt.setString(2, f.getNome());
            stmt.setString(3, f.getCargo());
            stmt.setString(4, f.getMatricula());
            stmt.setString(5, f.getCpf());
            stmt.execute();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
    //READ
    public List<Funcionario> listarTodos() {
        String sql = "SELECT * FROM Funcionario ORDER BY nome_funcionario";
        List<Funcionario> lista = new ArrayList<>();
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Funcionario(
                        rs.getInt("id_funcionario"),
                        rs.getInt("id_equipe"),
                        rs.getString("nome_funcionario"),
                        rs.getString("cargo"),
                        rs.getString("matricula"),
                        rs.getString("cpf_funcionario")
                ));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return lista;
    }
    // UOADATE
    public void atualizar(Funcionario f) {
        String sql = "UPDATE Funcionario SET id_equipe=?, nome_funcionario=?, cargo=?, matricula=?, cpf_funcionario=? WHERE id_funcionario=?";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, f.getIdEquipe());
            stmt.setString(2, f.getNome());
            stmt.setString(3, f.getCargo());
            stmt.setString(4, f.getMatricula());
            stmt.setString(5, f.getCpf());
            stmt.setInt(6, f.getId()); // O ID vai no WHERE
            stmt.execute();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
    //DELETE
    public void deletar(int id) {
        String sql = "DELETE FROM Funcionario WHERE id_funcionario=?";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}