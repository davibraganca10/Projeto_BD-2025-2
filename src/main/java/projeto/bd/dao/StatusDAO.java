package projeto.bd.dao;

import projeto.bd.models.Status;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusDAO {

    public List<Status> listarTodos() {
        String sql = "SELECT * FROM Status_ocorrencia ORDER BY id_status";
        List<Status> lista = new ArrayList<>();

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Status s = new Status();
                s.setId(rs.getInt("id_status"));
                s.setNome(rs.getString("nome_status"));
                lista.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
}