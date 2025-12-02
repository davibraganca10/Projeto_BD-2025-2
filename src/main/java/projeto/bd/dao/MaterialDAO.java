package projeto.bd.dao;

import projeto.bd.models.Material;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {
    public void salvar(Material m) {
        String sql = "INSERT INTO Material (nome_material, unidade_medida) VALUES (?, ?)";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getUnidadeMedida());
            stmt.execute();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<Material> listarTodos() {
        String sql = "SELECT * FROM Material ORDER BY nome_material";
        List<Material> lista = new ArrayList<>();
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Material(
                        rs.getInt("id_material"),
                        rs.getString("nome_material"),
                        rs.getString("unidade_medida")
                ));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return lista;
    }

    public void atualizar(Material m) {
        String sql = "UPDATE Material SET nome_material=?, unidade_medida=? WHERE id_material=?";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getUnidadeMedida());
            stmt.setInt(3, m.getId());
            stmt.execute();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM Material WHERE id_material=?";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}