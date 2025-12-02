package projeto.bd.tela;

import projeto.bd.dao.MaterialDAO;
import projeto.bd.models.Material;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaMaterial extends JFrame {
    private JTextField txtNome, txtUnidade;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private MaterialDAO dao = new MaterialDAO();
    private Integer idSelecionado = null;

    public TelaMaterial() {
        super("Gerenciamento de Materiais");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel pForm = new JPanel(new GridLayout(3, 2, 10, 10));
        pForm.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        txtNome = new JTextField();
        txtUnidade = new JTextField();
        pForm.add(new JLabel("Nome do Material:")); pForm.add(txtNome);
        pForm.add(new JLabel("Unidade (ex: kg, m, un):")); pForm.add(txtUnidade);

        JPanel pBtns = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnDeletar = new JButton("Deletar");
        pBtns.add(btnSalvar); pBtns.add(btnLimpar); pBtns.add(btnDeletar);

        pForm.add(new JLabel("Ações:")); pForm.add(pBtns);
        add(pForm, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(new String[]{"ID", "Material", "Unidade"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvar());
        btnDeletar.addActionListener(e -> deletar());
        btnLimpar.addActionListener(e -> limpar());

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    idSelecionado = (Integer) modeloTabela.getValueAt(linha, 0);
                    txtNome.setText((String) modeloTabela.getValueAt(linha, 1));
                    txtUnidade.setText((String) modeloTabela.getValueAt(linha, 2));
                    btnSalvar.setText("Atualizar");
                }
            }
        });
        atualizarTabela();
    }

    private void salvar() {
        try {
            Material m = new Material();
            m.setNome(txtNome.getText());
            m.setUnidadeMedida(txtUnidade.getText());
            if (idSelecionado == null) dao.salvar(m);
            else { m.setId(idSelecionado); dao.atualizar(m); }
            limpar(); atualizarTabela();
            JOptionPane.showMessageDialog(this, "Sucesso!");
        } catch(Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
    }

    private void deletar() {
        if(idSelecionado != null) { dao.deletar(idSelecionado); limpar(); atualizarTabela(); }
    }

    private void limpar() {
        txtNome.setText(""); txtUnidade.setText(""); idSelecionado = null;
        ((JButton)((JPanel)((JPanel)getContentPane().getComponent(0)).getComponent(5)).getComponent(0)).setText("Salvar");
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for(Material m : dao.listarTodos()) modeloTabela.addRow(new Object[]{m.getId(), m.getNome(), m.getUnidadeMedida()});
    }
}