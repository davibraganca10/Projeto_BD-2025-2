package projeto.bd.tela;

import projeto.bd.dao.EquipeDAO;
import projeto.bd.dao.FuncionarioDAO;
import projeto.bd.models.Equipe;
import projeto.bd.models.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaFuncionario extends JFrame {

    private JComboBox<Equipe> comboEquipe;
    private JTextField txtNome, txtCargo, txtMatricula, txtCpf;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private FuncionarioDAO dao;
    private EquipeDAO equipeDao;
    private Integer idSelecionado = null;

    public TelaFuncionario() {
        super("Gerenciamento de Funcionários");
        this.dao = new FuncionarioDAO();
        this.equipeDao = new EquipeDAO();

        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha só a janela, não o app
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Painel do Formulário ( linhas pra caber tudo)
        JPanel painelForm = new JPanel(new GridLayout(6, 2, 10, 10));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        painelForm.add(new JLabel("Equipe:"));
        comboEquipe = new JComboBox<>();
        carregarEquipes();
        painelForm.add(comboEquipe);

        painelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelForm.add(txtNome);

        painelForm.add(new JLabel("Cargo:"));
        txtCargo = new JTextField();
        painelForm.add(txtCargo);

        painelForm.add(new JLabel("Matrícula:"));
        txtMatricula = new JTextField();
        painelForm.add(txtMatricula);

        painelForm.add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        painelForm.add(txtCpf);

        // Botoes
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


        String[] colunas = {"ID", "Equipe", "Nome", "Cargo", "Matrícula", "CPF"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);


        btnSalvar.addActionListener(e -> salvarFuncionario());
        btnDeletar.addActionListener(e -> deletarFuncionario());
        btnLimpar.addActionListener(e -> limparCampos());

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    idSelecionado = (Integer) modeloTabela.getValueAt(linha, 0);


                    String nomeEquipe = (String) modeloTabela.getValueAt(linha, 1);
                    txtNome.setText((String) modeloTabela.getValueAt(linha, 2));
                    txtCargo.setText((String) modeloTabela.getValueAt(linha, 3));
                    txtMatricula.setText((String) modeloTabela.getValueAt(linha, 4));
                    txtCpf.setText((String) modeloTabela.getValueAt(linha, 5));

                    selecionarEquipeNoCombo(nomeEquipe);

                    JButton btn = (JButton) ((JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(11)).getComponent(0);
                    btn.setText("Atualizar");
                }
            }
        });

        atualizarTabela();
    }

    private void carregarEquipes() {
        comboEquipe.removeAllItems();
        equipeDao.listarTodas().forEach(e -> comboEquipe.addItem(e));
    }

    private void salvarFuncionario() {
        try {
            Funcionario f = new Funcionario();
            Equipe equipeSelecionada = (Equipe) comboEquipe.getSelectedItem();
            if (equipeSelecionada != null) f.setIdEquipe(equipeSelecionada.getId());

            f.setNome(txtNome.getText());
            f.setCargo(txtCargo.getText());
            f.setMatricula(txtMatricula.getText());
            f.setCpf(txtCpf.getText());

            if (idSelecionado == null) {
                dao.salvar(f);
                JOptionPane.showMessageDialog(this, "Funcionário salvo!");
            } else {
                f.setId(idSelecionado);
                dao.atualizar(f);
                JOptionPane.showMessageDialog(this, "Funcionário atualizado!");
            }
            limparCampos();
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void deletarFuncionario() {
        if (idSelecionado != null) {
            if (JOptionPane.showConfirmDialog(this, "Tem certeza?", "Excluir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                dao.deletar(idSelecionado);
                limparCampos();
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Excluído.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione alguém para excluir.");
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Funcionario f : dao.listarTodos()) {
            String nomeEquipe = getNomeEquipe(f.getIdEquipe());
            modeloTabela.addRow(new Object[]{
                    f.getId(), nomeEquipe, f.getNome(), f.getCargo(), f.getMatricula(), f.getCpf()
            });
        }
    }

    private String getNomeEquipe(int id) {
        for (int i = 0; i < comboEquipe.getItemCount(); i++) {
            Equipe e = comboEquipe.getItemAt(i);
            if (e.getId() == id) return e.getEspecialidade();
        }
        return "ID: " + id;
    }

    private void selecionarEquipeNoCombo(String nomeExibido) {
        for (int i = 0; i < comboEquipe.getItemCount(); i++) {
            Equipe e = comboEquipe.getItemAt(i);
            if (e.getEspecialidade().equals(nomeExibido)) {
                comboEquipe.setSelectedIndex(i);
                break;
            }
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCargo.setText("");
        txtMatricula.setText("");
        txtCpf.setText("");
        if(comboEquipe.getItemCount() > 0) comboEquipe.setSelectedIndex(0);
        idSelecionado = null;
        tabela.clearSelection();
        JButton btn = (JButton) ((JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(11)).getComponent(0);
        btn.setText("Salvar");
    }
}