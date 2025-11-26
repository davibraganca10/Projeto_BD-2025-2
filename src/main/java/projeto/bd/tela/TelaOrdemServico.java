package projeto.bd.tela;

import projeto.bd.dao.*;
import projeto.bd.models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;

public class TelaOrdemServico extends JFrame {
    private JComboBox<Solicitacao> comboSolicitacao;
    private JComboBox<Equipe> comboEquipe;
    private JTextArea txtRelatorio;
    private JCheckBox chkConcluido;
    private OrdemServicoDAO dao = new OrdemServicoDAO();
    private DefaultTableModel modelo;
    private Integer idSelecionado = null;

    public TelaOrdemServico() {
        super("Ordem de Serviço");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        comboSolicitacao = new JComboBox<>();
        new SolicitacaoDAO().listarTodas().forEach(s -> comboSolicitacao.addItem(s));

        comboEquipe = new JComboBox<>();
        new EquipeDAO().listarTodas().forEach(e -> comboEquipe.addItem(e));

        txtRelatorio = new JTextArea(3, 20);
        chkConcluido = new JCheckBox("Marcar como Concluído?");

        JButton btnSalvar = new JButton("Salvar OS");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnDeletar = new JButton("Excluir");

        form.add(new JLabel("Solicitação:")); form.add(comboSolicitacao);
        form.add(new JLabel("Equipe:")); form.add(comboEquipe);
        form.add(new JLabel("Relatório:")); form.add(new JScrollPane(txtRelatorio));
        form.add(new JLabel("Status:")); form.add(chkConcluido);

        JPanel pBtns = new JPanel();
        pBtns.add(btnSalvar); pBtns.add(btnLimpar); pBtns.add(btnDeletar);
        form.add(new JLabel("Ações:")); form.add(pBtns);

        add(form, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"ID", "Solicitação", "Equipe", "Data Início", "Data Fim"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvar(btnSalvar));
        btnLimpar.addActionListener(e -> limpar(btnSalvar));
        btnDeletar.addActionListener(e -> deletar(btnSalvar));

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    idSelecionado = (Integer) modelo.getValueAt(linha, 0);
                    txtRelatorio.setText("Edição selecionada...");
                    btnSalvar.setText("Atualizar");
                    comboSolicitacao.setEnabled(false);
                }
            }
        });

        atualizarTabela();
    }

    private void salvar(JButton btn) {
        try {
            OrdemServico os = new OrdemServico();
            os.setIdSolicitacao(((Solicitacao) comboSolicitacao.getSelectedItem()).getId());
            os.setIdEquipe(((Equipe) comboEquipe.getSelectedItem()).getId());
            os.setRelatorio(txtRelatorio.getText());

            if (idSelecionado == null) {
                os.setDataAtribuicao(new Timestamp(System.currentTimeMillis()));
                dao.salvar(os);
                JOptionPane.showMessageDialog(this, "OS Criada!");
            } else {
                os.setId(idSelecionado);
                if (chkConcluido.isSelected()) {
                    os.setDataConclusao(new Timestamp(System.currentTimeMillis()));
                }
                dao.atualizar(os);
                JOptionPane.showMessageDialog(this, "OS Atualizada!");
            }
            limpar(btn);
            atualizarTabela();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
    }

    private void deletar(JButton btn) {
        if (idSelecionado != null) {
            dao.deletar(idSelecionado);
            JOptionPane.showMessageDialog(this, "OS Removida!");
            limpar(btn);
            atualizarTabela();
        }
    }

    private void limpar(JButton btn) {
        idSelecionado = null;
        txtRelatorio.setText("");
        chkConcluido.setSelected(false);
        comboSolicitacao.setEnabled(true);
        btn.setText("Salvar OS");
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        for (OrdemServico os : dao.listarTodas()) {
            modelo.addRow(new Object[]{os.getId(), os.getIdSolicitacao(), os.getIdEquipe(), os.getDataAtribuicao(), os.getDataConclusao()});
        }
    }
}