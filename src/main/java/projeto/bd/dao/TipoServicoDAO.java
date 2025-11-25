package projeto.bd.dao;

import projeto.bd.models.TipoServico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoServicoDAO {

    public List<TipoServico> listarTodos() {
        String sql = "SELECT * FROM Tipo_Servico ORDER BY nome_servico";
        List<TipoServico> lista = new ArrayList<>();

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TipoServico t = new TipoServico();
                t.setId(rs.getInt("id_tipo_servico"));
                t.setNome(rs.getString("nome_servico"));
                t.setDescricao(rs.getString("descricao"));
                lista.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
}