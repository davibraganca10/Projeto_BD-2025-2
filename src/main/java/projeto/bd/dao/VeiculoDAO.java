package projeto.bd.dao;

import projeto.bd.models.Veiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {
    // CREATE
    public void salvar(Veiculo v) {
        String sql = "INSERT INTO Veiculo (id_equipe, modelo_veiculo, placa) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, v.getIdEquipe());
            stmt.setString(2, v.getModelo());
            stmt.setString(3, v.getPlaca());
            stmt.execute();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    // READ
    public List<Veiculo> listarTodos() {
        String sql = "SELECT * FROM Veiculo ORDER BY id_veiculo";
        List<Veiculo> lista = new ArrayList<>();
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Veiculo(
                        rs.getInt("id_veiculo"),
                        rs.getInt("id_equipe"),
                        rs.getString("modelo_veiculo"),
                        rs.getString("placa")
                ));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return lista;
    }

    // UPDATE
    public void atualizar(Veiculo v) {
        String sql = "UPDATE Veiculo SET id_equipe=?, modelo_veiculo=?, placa=? WHERE id_veiculo=?";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, v.getIdEquipe());
            stmt.setString(2, v.getModelo());
            stmt.setString(3, v.getPlaca());
            stmt.setInt(4, v.getId()); //id vai no WHERE , qual veiculo atualizar
            stmt.execute();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    // DELETE
    public void deletar(int id) {
        String sql = "DELETE FROM Veiculo WHERE id_veiculo=?";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}