package projeto.bd.dao;

import projeto.bd.models.Solicitacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoDAO {

    public void salvar(Solicitacao s) {
        String sql = "INSERT INTO Solicitacao (id_regiao, id_cidadao, id_tipo_servico, id_status, logradouro, numero_solicitacao, referencia, foto_ocorrencia) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, s.getIdRegiao());
            stmt.setInt(2, s.getIdCidadao());
            stmt.setInt(3, s.getIdTipoServico());
            stmt.setInt(4, s.getIdStatus());
            stmt.setString(5, s.getLogradouro());
            stmt.setString(6, s.getNumeroSolicitacao());
            stmt.setString(7, s.getReferencia());
            stmt.setBytes(8, s.getFoto());
            stmt.execute();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Solicitacao> listarTodas() {
        String sql = "SELECT * FROM Solicitacao ORDER BY data_abertura DESC";
        List<Solicitacao> lista = new ArrayList<>();
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){
            while (rs.next()){
                Solicitacao s = new Solicitacao();
                s.setId(rs.getInt("id_solicitacao"));
                s.setIdRegiao(rs.getInt("id_regiao"));
                s.setIdCidadao(rs.getInt("id_cidadao"));
                s.setIdTipoServico(rs.getInt("id_tipo_servico"));
                s.setIdStatus(rs.getInt("id_status"));
                s.setLogradouro(rs.getString("logradouro"));
                s.setNumeroSolicitacao(rs.getString("numero_solicitacao"));
                s.setReferencia(rs.getString("referencia"));
                s.setDataAbertura(rs.getTimestamp("data_abertura"));
                lista.add(s);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);}
        return lista;
    }
}