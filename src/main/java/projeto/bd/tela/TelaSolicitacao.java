package projeto.bd.tela;

import projeto.bd.dao.*;
import projeto.bd.models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaSolicitacao extends JFrame {
    private JComboBox<Regiao> comboRegiao;
    private JComboBox<Cidadao> comboCidadao;
    private JComboBox<TipoServico> comboTipo;
    private JComboBox<Status> comboStatus;
    private JTextField txtLogradouro, txtReferencia;
    private JLabel lblFoto;
    private byte[] fotoBytes = null;
    private SolicitacaoDAO dao = new SolicitacaoDAO();
    private DefaultTableModel modeloTabela;
    private Integer idSelecionado = null;

    public TelaSolicitacao() {
        super("Gerenciamento de Solicitações");
        setSize(1000, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(8, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        comboRegiao = new JComboBox<>();
        comboCidadao = new JComboBox<>();
        comboTipo = new JComboBox<>();
        comboStatus = new JComboBox<>();

        new RegiaoDAO().listarTodas().forEach(r -> comboRegiao.addItem(r));
        new CidadaoDAO().listarTodos().forEach(c -> comboCidadao.addItem(c));
        new TipoServicoDAO().listarTodos().forEach(t -> comboTipo.addItem(t));
        new StatusDAO().listarTodos().forEach(s -> comboStatus.addItem(s));

        txtLogradouro = new JTextField();
        txtReferencia = new JTextField();
        JButton btnFoto = new JButton("Selecionar Foto");
        lblFoto = new JLabel("Sem foto");

        form.add(new JLabel("Cidadão:")); form.add(comboCidadao);
        form.add(new JLabel("Região:")); form.add(comboRegiao);
        form.add(new JLabel("Tipo Serviço:")); form.add(comboTipo);
        form.add(new JLabel("Status:")); form.add(comboStatus);
        form.add(new JLabel("Logradouro:")); form.add(txtLogradouro);
        form.add(new JLabel("Referência:")); form.add(txtReferencia);
        form.add(new JLabel("Foto:"));
        JPanel pFoto = new JPanel(); pFoto.add(btnFoto); pFoto.add(lblFoto);
        form.add(pFoto);

        JPanel pBotoes = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnDeletar = new JButton("Excluir");
        pBotoes.add(btnSalvar); pBotoes.add(btnLimpar); pBotoes.add(btnDeletar);

        form.add(new JLabel("Ações:")); form.add(pBotoes);
        add(form, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(new String[]{
                "ID", "Protocolo", "Data", "Cidadão", "Serviço", "Status"
        }, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnFoto.addActionListener(e -> selecionarFoto());
        btnSalvar.addActionListener(e -> salvar(btnSalvar));
        btnLimpar.addActionListener(e -> limpar(btnSalvar));
        btnDeletar.addActionListener(e -> deletar(btnSalvar));

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    carregarDadosDaLinha(linha);
                    btnSalvar.setText("Atualizar");
                    comboCidadao.setEnabled(false);
                }
            }
        });

        atualizarTabela();
    }

    private void carregarDadosDaLinha(int linha) {
        int id = (Integer) modeloTabela.getValueAt(linha, 0);
        idSelecionado = id;

        List<Solicitacao> lista = dao.listarTodas();
        for (Solicitacao s : lista) {
            if (s.getId().equals(id)) {
                selecionarComboItem(comboRegiao, s.getIdRegiao());
                selecionarComboItem(comboCidadao, s.getIdCidadao());
                selecionarComboItem(comboTipo, s.getIdTipoServico());
                selecionarComboItem(comboStatus, s.getIdStatus());
                txtLogradouro.setText(s.getLogradouro());
                txtReferencia.setText(s.getReferencia());
                if(s.getFoto() != null) lblFoto.setText("Foto existente");
                break;
            }
        }
    }

    private void selecionarComboItem(JComboBox box, int id) {
        for (int i = 0; i < box.getItemCount(); i++) {
            try {
                Object item = box.getItemAt(i);
                int itemId = -1;
                if (item instanceof Regiao) itemId = ((Regiao) item).getId();
                else if (item instanceof Cidadao) itemId = ((Cidadao) item).getId();
                else if (item instanceof TipoServico) itemId = ((TipoServico) item).getId();
                else if (item instanceof Status) itemId = ((Status) item).getId();

                if (itemId == id) { box.setSelectedIndex(i); break; }
            } catch (Exception e) {}
        }
    }

    private void selecionarFoto() {
        JFileChooser ch = new JFileChooser();
        if (ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileInputStream fis = new FileInputStream(ch.getSelectedFile())) {
                fotoBytes = fis.readAllBytes();
                lblFoto.setText("Nova Foto OK");
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    private void salvar(JButton btn) {
        try {
            Solicitacao s = new Solicitacao();
            s.setIdCidadao(((Cidadao) comboCidadao.getSelectedItem()).getId());
            s.setIdRegiao(((Regiao) comboRegiao.getSelectedItem()).getId());
            s.setIdTipoServico(((TipoServico) comboTipo.getSelectedItem()).getId());
            s.setIdStatus(((Status) comboStatus.getSelectedItem()).getId());
            s.setLogradouro(txtLogradouro.getText());
            s.setReferencia(txtReferencia.getText());
            s.setFoto(fotoBytes);

            if (idSelecionado == null) {
                s.setNumeroSolicitacao("SOL" + System.currentTimeMillis());
                dao.salvar(s);
                JOptionPane.showMessageDialog(this, "Salvo!");
            } else {
                s.setId(idSelecionado);
                dao.atualizar(s);
                JOptionPane.showMessageDialog(this, "Atualizado!");
            }
            limpar(btn);
            atualizarTabela();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
    }

    private void deletar(JButton btn) {
        if (idSelecionado != null) {
            dao.deletar(idSelecionado);
            JOptionPane.showMessageDialog(this, "Removido!");
            limpar(btn);
            atualizarTabela();
        } else JOptionPane.showMessageDialog(this, "Selecione para excluir.");
    }

    private void limpar(JButton btn) {
        idSelecionado = null;
        txtLogradouro.setText("");
        txtReferencia.setText("");
        fotoBytes = null;
        lblFoto.setText("Sem foto");
        comboCidadao.setEnabled(true);
        btn.setText("Salvar");
    }

    // Método modificado para mostrar nomes em vez de IDs
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (Solicitacao s : dao.listarTodas()) {
            // Aqui fazemos a mágica: convertemos os IDs para Nomes
            String nomeStatus = getNomeStatus(s.getIdStatus());
            String nomeServico = getNomeServico(s.getIdTipoServico());
            String nomeCidadao = getNomeCidadao(s.getIdCidadao());

            modeloTabela.addRow(new Object[]{
                    s.getId(),                  // ID (Oculto ou visível)
                    s.getNumeroSolicitacao(),   // Protocolo
                    s.getDataAbertura() != null ? sdf.format(s.getDataAbertura()) : "",
                    nomeCidadao,                // Mostra "João" em vez de "1"
                    nomeServico,                // Mostra "Iluminação" em vez de "2"
                    nomeStatus                  // Mostra "Aberto" em vez de "1"
            });
        }
    }

    private String getNomeStatus(int id) {
        for (int i = 0; i < comboStatus.getItemCount(); i++) {
            Status s = comboStatus.getItemAt(i);
            if (s.getId() == id) return s.getNome();
        }
        return "ID: " + id;
    }

    private String getNomeServico(int id) {
        for (int i = 0; i < comboTipo.getItemCount(); i++) {
            TipoServico t = comboTipo.getItemAt(i);
            if (t.getId() == id) return t.getNome();
        }
        return "ID: " + id;
    }

    private String getNomeCidadao(int id) {
        for (int i = 0; i < comboCidadao.getItemCount(); i++) {
            Cidadao c = comboCidadao.getItemAt(i);
            if (c.getId() == id) return c.getNome();
        }
        return "ID: " + id;
    }
}