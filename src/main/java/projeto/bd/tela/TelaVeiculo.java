package projeto.bd.tela;

import projeto.bd.dao.EquipeDAO;
import projeto.bd.dao.VeiculoDAO;
import projeto.bd.models.Equipe;
import projeto.bd.models.Veiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaVeiculo extends JFrame {

    private JComboBox<Equipe> comboEquipe;
    private JTextField txtModelo, txtPlaca;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private VeiculoDAO dao;
    private EquipeDAO equipeDao; // Necessário para carregar a lista de equipes
    private Integer idSelecionado = null;

    public TelaVeiculo() {
        super("Gerenciamento de Veículos");
        this.dao = new VeiculoDAO();
        this.equipeDao = new EquipeDAO();

        setSize(800, 600);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);


        JPanel painelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        painelForm.add(new JLabel("Equipe Responsável:"));
        comboEquipe = new JComboBox<>();
        carregarEquipes(); // Preenche a lista
        painelForm.add(comboEquipe);


        painelForm.add(new JLabel("Modelo do Veículo:"));
        txtModelo = new JTextField();
        painelForm.add(txtModelo);


        painelForm.add(new JLabel("Placa:"));
        txtPlaca = new JTextField();
        painelForm.add(txtPlaca);


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


        String[] colunas = {"ID", "Equipe", "Modelo", "Placa"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvarVeiculo());
        btnDeletar.addActionListener(e -> deletarVeiculo());
        btnLimpar.addActionListener(e -> limparCampos());


        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    idSelecionado = (Integer) modeloTabela.getValueAt(linha, 0);


                    String nomeEquipe = (String) modeloTabela.getValueAt(linha, 1);
                    String modelo = (String) modeloTabela.getValueAt(linha, 2);
                    String placa = (String) modeloTabela.getValueAt(linha, 3);


                    txtModelo.setText(modelo);
                    txtPlaca.setText(placa);
                    selecionarEquipeNoCombo(nomeEquipe); // Seleciona a equipe correta na lista

                    JButton btn = (JButton) ((JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(7)).getComponent(0);
                    btn.setText("Atualizar");
                }
            }
        });

        atualizarTabela();
    }

    private void carregarEquipes() {
        comboEquipe.removeAllItems();
        List<Equipe> equipes = equipeDao.listarTodas();
        for (Equipe e : equipes) {
            comboEquipe.addItem(e);
        }
    }

    private void salvarVeiculo() {
        try {
            Veiculo v = new Veiculo();

            Equipe equipeSelecionada = (Equipe) comboEquipe.getSelectedItem();
            if (equipeSelecionada != null) {
                v.setIdEquipe(equipeSelecionada.getId());
            }

            v.setModelo(txtModelo.getText());
            v.setPlaca(txtPlaca.getText());

            if (idSelecionado == null) {
                dao.salvar(v);
                JOptionPane.showMessageDialog(this, "Veículo cadastrado!");
            } else {
                v.setId(idSelecionado);
                dao.atualizar(v);
                JOptionPane.showMessageDialog(this, "Veículo atualizado!");
            }

            limparCampos();
            atualizarTabela();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void deletarVeiculo() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para deletar.");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza?", "Excluir", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                dao.deletar(idSelecionado);
                limparCampos();
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Veículo excluído.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
            }
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Veiculo> lista = dao.listarTodos();

        for (Veiculo v : lista) {
            String nomeEquipe = getNomeEquipe(v.getIdEquipe());

            modeloTabela.addRow(new Object[]{
                    v.getId(), nomeEquipe, v.getModelo(), v.getPlaca()
            });
        }
    }

    private String getNomeEquipe(int id) {
        for (int i = 0; i < comboEquipe.getItemCount(); i++) {
            Equipe e = comboEquipe.getItemAt(i);
            if (e.getId() == id) {
                return e.getEspecialidade();
            }
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
        txtModelo.setText("");
        txtPlaca.setText("");
        if(comboEquipe.getItemCount() > 0) comboEquipe.setSelectedIndex(0);
        idSelecionado = null;
        tabela.clearSelection();

        JButton btn = (JButton) ((JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(7)).getComponent(0);
        btn.setText("Salvar");
    }
}