package projeto.bd.dao;

import projeto.bd.models.Regiao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegiaoDAO {

    public List<Regiao> listarTodas() {
        String sql = "SELECT * FROM Regiao ORDER BY nome_regiao";
        List<Regiao> lista = new ArrayList<>();

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Regiao r = new Regiao();
                r.setId(rs.getInt("id_regiao"));
                r.setNome(rs.getString("nome_regiao"));
                lista.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
}
