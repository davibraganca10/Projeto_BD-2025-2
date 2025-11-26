package projeto.bd.dao;

import projeto.bd.models.Equipe;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeDAO {
    public void salvar(Equipe e) {
        String sql = "INSERT INTO Equipe (especialidade) VALUES (?)";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getEspecialidade());
            stmt.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex); }
    }

    public List<Equipe> listarTodas() {
        String sql = "SELECT * FROM Equipe";
        List<Equipe> lista = new ArrayList<>();
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Equipe(rs.getInt("id_equipe"), rs.getString("especialidade")));
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex); }
        return lista;
    }

    public void atualizar(Equipe e) {
        String sql = "UPDATE Equipe SET especialidade = ? WHERE id_equipe = ?";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getEspecialidade());
            stmt.setInt(2, e.getId());
            stmt.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex); }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM Equipe WHERE id_equipe = ?";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex){
            throw new RuntimeException(ex); }
    }
}