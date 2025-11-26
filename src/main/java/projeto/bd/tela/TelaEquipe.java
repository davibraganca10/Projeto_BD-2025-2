package projeto.bd.tela;

import projeto.bd.dao.EquipeDAO;
import projeto.bd.models.Equipe;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaEquipe extends JFrame {
    private JTextField txtEspecialidade;
    private EquipeDAO dao = new EquipeDAO();
    private DefaultTableModel modelo;
    private Integer idSelecionado = null;

    public TelaEquipe() {
        super("Gerenciar Equipes");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel pForm = new JPanel(new GridLayout(3, 2, 10, 10));
        pForm.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        txtEspecialidade = new JTextField();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnDeletar = new JButton("Excluir");

        pForm.add(new JLabel("Especialidade:"));
        pForm.add(txtEspecialidade);
        pForm.add(btnSalvar);
        pForm.add(btnLimpar);
        pForm.add(new JLabel(""));
        pForm.add(btnDeletar);

        add(pForm, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"ID", "Especialidade"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvar());
        btnLimpar.addActionListener(e -> limpar());
        btnDeletar.addActionListener(e -> deletar());

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    idSelecionado = (Integer) modelo.getValueAt(linha, 0);
                    txtEspecialidade.setText((String) modelo.getValueAt(linha, 1));
                    btnSalvar.setText("Atualizar");
                }
            }
        });

        atualizarTabela();
    }

    private void salvar() {
        try {
            Equipe e = new Equipe();
            e.setEspecialidade(txtEspecialidade.getText());

            if (idSelecionado == null) {
                dao.salvar(e);
                JOptionPane.showMessageDialog(this, "Equipe salva!");
            } else {
                e.setId(idSelecionado);
                dao.atualizar(e);
                JOptionPane.showMessageDialog(this, "Equipe atualizada!");
            }
            limpar();
            atualizarTabela();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
    }

    private void deletar() {
        if (idSelecionado != null) {
            try {
                dao.deletar(idSelecionado);
                JOptionPane.showMessageDialog(this, "Equipe removida!");
                limpar();
                atualizarTabela();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: Equipe em uso!"); }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma equipe.");
        }
    }

    private void limpar() {
        txtEspecialidade.setText("");
        idSelecionado = null;
        JButton btn = (JButton)((JPanel)getContentPane().getComponent(0)).getComponent(2);
        btn.setText("Salvar");
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        for (Equipe e : dao.listarTodas()) {
            modelo.addRow(new Object[]{e.getId(), e.getEspecialidade()});
        }
    }
}