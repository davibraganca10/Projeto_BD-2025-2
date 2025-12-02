package projeto.bd.dao;

import projeto.bd.models.MaterialUtilizado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialUtilizadoDAO {

    public void salvar(MaterialUtilizado mu) {
        String sql = "INSERT INTO Material_Utilizado (id_material, id_ordem_servico, quantidade_usada) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mu.getIdMaterial());
            stmt.setInt(2, mu.getIdOrdemServico());
            stmt.setInt(3, mu.getQuantidade());
            stmt.execute();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<MaterialUtilizado> listarTodos() {
        String sql = "SELECT * FROM Material_Utilizado";
        List<MaterialUtilizado> lista = new ArrayList<>();
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MaterialUtilizado mu = new MaterialUtilizado();
                mu.setIdMaterial(rs.getInt("id_material"));
                mu.setIdOrdemServico(rs.getInt("id_ordem_servico"));
                mu.setQuantidade(rs.getInt("quantidade_usada"));
                lista.add(mu);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return lista;
    }

    // Atualiza (soh a qtd, ) (nn muda os IDs PK aqui pra simplificar) se precisar mudar o ID ,deletar e criar um novo
    public void atualizar(MaterialUtilizado mu) {
        String sql = "UPDATE Material_Utilizado SET quantidade_usada=? WHERE id_material=? AND id_ordem_servico=?";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mu.getQuantidade());
            stmt.setInt(2, mu.getIdMaterial());
            stmt.setInt(3, mu.getIdOrdemServico());
            stmt.execute();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void deletar(int idMaterial, int idOS) {
        String sql = "DELETE FROM Material_Utilizado WHERE id_material=? AND id_ordem_servico=?";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMaterial);
            stmt.setInt(2, idOS);
            stmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}