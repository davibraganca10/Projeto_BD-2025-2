package projeto.bd.dao;

import projeto.bd.models.OrdemServico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoDAO {
    public void salvar(OrdemServico os) {
        String sql = "INSERT INTO Ordem_Servico (id_solicitacao, id_equipe, data_atribuicao, data_conclusao, relatorio_tecnico) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, os.getIdSolicitacao());
            stmt.setInt(2, os.getIdEquipe());
            stmt.setTimestamp(3, os.getDataAtribuicao());
            stmt.setTimestamp(4, os.getDataConclusao());
            stmt.setString(5, os.getRelatorio());
            stmt.execute();
        } catch (SQLException ex){
            throw new RuntimeException(ex); }
    }

    public List<OrdemServico> listarTodas() {
        String sql = "SELECT * FROM Ordem_Servico ORDER BY id_ordem_servico";
        List<OrdemServico> lista = new ArrayList<>();
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OrdemServico os = new OrdemServico();
                os.setId(rs.getInt("id_ordem_servico"));
                os.setIdSolicitacao(rs.getInt("id_solicitacao"));
                os.setIdEquipe(rs.getInt("id_equipe"));
                os.setDataAtribuicao(rs.getTimestamp("data_atribuicao"));
                os.setDataConclusao(rs.getTimestamp("data_conclusao"));
                os.setRelatorio(rs.getString("relatorio_tecnico"));
                lista.add(os);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex); }
        return lista;
    }
    public void atualizar(OrdemServico os) {
        String sql = "UPDATE Ordem_Servico SET id_equipe=?, relatorio_tecnico=?, data_conclusao=? WHERE id_ordem_servico=?";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, os.getIdEquipe());
            stmt.setString(2, os.getRelatorio());
            stmt.setTimestamp(3, os.getDataConclusao());
            stmt.setInt(4, os.getId());
            stmt.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex); }
    }
    public void deletar(int id) {
        String sql = "DELETE FROM Ordem_Servico WHERE id_ordem_servico=?";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {throw new RuntimeException(ex); }
    }
}