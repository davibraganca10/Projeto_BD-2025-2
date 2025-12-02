package projeto.bd.tela;

import projeto.bd.dao.CidadaoDAO;
import projeto.bd.models.Cidadao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaCidadao extends JFrame {

    private JTextField txtNome, txtEmail, txtTelefone, txtCpf;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private CidadaoDAO dao;
    private Integer idSelecionado = null;

    public TelaCidadao() {
        super("Gerenciamento de Cidadãos");
        this.dao = new CidadaoDAO();

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel painelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelForm.add(txtNome);

        painelForm.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        painelForm.add(txtEmail);

        painelForm.add(new JLabel("Telefone (apenas números):"));
        txtTelefone = new JTextField();
        painelForm.add(txtTelefone);

        painelForm.add(new JLabel("CPF (11 dígitos):"));
        txtCpf = new JTextField();
        painelForm.add(txtCpf);

        JPanel painelBotoes = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnDeletar = new JButton("Deletar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnDeletar);

        painelForm.add(new JLabel("Ações:"));
        painelForm.add(painelBotoes);

        add(painelForm, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "Email", "Telefone", "CPF"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarCidadao();
            }
        });

        btnDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarCidadao();
            }
        });

        btnLimpar.addActionListener(e -> limparCampos());

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    idSelecionado = (Integer) modeloTabela.getValueAt(linha, 0);
                    txtNome.setText((String) modeloTabela.getValueAt(linha, 1));
                    txtEmail.setText((String) modeloTabela.getValueAt(linha, 2));
                    txtTelefone.setText((String) modeloTabela.getValueAt(linha, 3));
                    txtCpf.setText((String) modeloTabela.getValueAt(linha, 4));

                    btnSalvar.setText("Atualizar");
                }
            }
        });

        atualizarTabela();
    }

    private void salvarCidadao() {
        try {
            Cidadao c = new Cidadao();
            c.setNome(txtNome.getText());
            c.setEmail(txtEmail.getText());
            c.setTelefone(txtTelefone.getText());
            c.setCpf(txtCpf.getText());

            if (idSelecionado == null) {
                dao.salvar(c);
                JOptionPane.showMessageDialog(this, "Cidadão cadastrado com sucesso!");
            } else {
                c.setId(idSelecionado);
                dao.atualizar(c);
                JOptionPane.showMessageDialog(this, "Cidadão atualizado com sucesso!");
            }

            limparCampos();
            atualizarTabela();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void deletarCidadao() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cidadão na tabela para deletar.");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Excluir", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                dao.deletar(idSelecionado);
                limparCampos();
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Cidadão excluído.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir (Pode ter solicitações vinculadas): " + ex.getMessage());
            }
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);

        List<Cidadao> lista = dao.listarTodos();

        for (Cidadao c : lista) {
            modeloTabela.addRow(new Object[]{
                    c.getId(), c.getNome(), c.getEmail(), c.getTelefone(), c.getCpf()
            });
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtCpf.setText("");
        idSelecionado = null;
        tabela.clearSelection();
        JButton btnSalvar = (JButton) ((JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(9)).getComponent(0);
        btnSalvar.setText("Salvar");
    }
}
