package projeto.bd.tela;

import projeto.bd.dao.*;
import projeto.bd.models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaMaterialUtilizado extends JFrame {
    private JComboBox<OrdemServico> comboOS;
    private JComboBox<Material> comboMaterial;
    private JTextField txtQuantidade;
    private JTable tabela;
    private DefaultTableModel modelo;
    private MaterialUtilizadoDAO dao = new MaterialUtilizadoDAO();

    private Integer idMaterialSel = null;
    private Integer idOSSel = null;

    public TelaMaterialUtilizado() {
        super("Materiais usados nas OS");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ComboBoxes agora vão usar o toString() que criamos
        comboOS = new JComboBox<>();
        comboMaterial = new JComboBox<>();

        new OrdemServicoDAO().listarTodas().forEach(os -> comboOS.addItem(os));
        new MaterialDAO().listarTodos().forEach(m -> comboMaterial.addItem(m));

        txtQuantidade = new JTextField();

        form.add(new JLabel("Ordem de Serviço:"));
        form.add(comboOS);
        form.add(new JLabel("Material:"));
        form.add(comboMaterial);
        form.add(new JLabel("Quantidade Usada:"));
        form.add(txtQuantidade);

        JPanel pBtns = new JPanel();
        JButton btnSalvar = new JButton("Adicionar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnDeletar = new JButton("Remover");
        pBtns.add(btnSalvar);
        pBtns.add(btnLimpar);
        pBtns.add(btnDeletar);

        form.add(new JLabel("Ações:"));
        form.add(pBtns);
        add(form, BorderLayout.NORTH);

        // Mudei o nome das colunas para ficar mais claro
        modelo = new DefaultTableModel(new String[]{"ID OS", "Descrição OS", "Material", "Qtd"}, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tabela = new JTable(modelo);
        // Escondendo a primeira coluna (ID OS) se quiser, mas deixei visível para conferência
        tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(50);

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvar());
        btnDeletar.addActionListener(e -> deletar());
        btnLimpar.addActionListener(e -> limpar());

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    // CUIDADO: Agora a tabela tem mais colunas, os índices mudaram!
                    idOSSel = (Integer) modelo.getValueAt(linha, 0);
                    // O nome do material está na coluna 2, mas precisamos achar o ID dele para selecionar no combo
                    String nomeMaterial = (String) modelo.getValueAt(linha, 2);
                    int qtd = (Integer) modelo.getValueAt(linha, 3);

                    txtQuantidade.setText(String.valueOf(qtd));

                    selecionarComboOS(idOSSel);
                    selecionarComboMaterialPorNome(nomeMaterial);

                    // Guarda o ID do material selecionado para poder deletar depois
                    idMaterialSel = ((Material) comboMaterial.getSelectedItem()).getId();

                    comboOS.setEnabled(false);
                    comboMaterial.setEnabled(false);
                    JButton btn = (JButton) ((JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(7)).getComponent(0);
                    btn.setText("Atualizar Qtd");
                }
            }
        });
        atualizarTabela();
    }

    private void selecionarComboOS(int id) {
        for (int i = 0; i < comboOS.getItemCount(); i++)
            if (comboOS.getItemAt(i).getId() == id) comboOS.setSelectedIndex(i);
    }

    private void selecionarComboMaterialPorNome(String nome) {
        for (int i = 0; i < comboMaterial.getItemCount(); i++)
            if (comboMaterial.getItemAt(i).getNome().equals(nome)) comboMaterial.setSelectedIndex(i);
    }

    // Tradutor: Pega ID -> Retorna Texto Bonito
    private String getDescricaoOS(int id) {
        for (int i = 0; i < comboOS.getItemCount(); i++) {
            OrdemServico os = comboOS.getItemAt(i);
            if (os.getId() == id) return os.toString(); // Usa aql toString novo
        }
        return "OS " + id;
    }

    private String getNomeMaterial(int id) {
        for (int i = 0; i < comboMaterial.getItemCount(); i++) {
            Material m = comboMaterial.getItemAt(i);
            if (m.getId() == id) return m.getNome();
        }
        return "ID " + id;
    }

    // CRUD

    private void salvar() {
        try {
            MaterialUtilizado mu = new MaterialUtilizado();
            mu.setIdOrdemServico(((OrdemServico) comboOS.getSelectedItem()).getId());
            mu.setIdMaterial(((Material) comboMaterial.getSelectedItem()).getId());

            String qtdTexto = txtQuantidade.getText();
            if (qtdTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite a quantidade!");
                return;
            }
            mu.setQuantidade(Integer.parseInt(qtdTexto));

            if (idOSSel == null) {
                dao.salvar(mu);
            } else {

                mu.setIdOrdemServico(idOSSel); // Garante que usa o ID antigo travado
                mu.setIdMaterial(idMaterialSel); // Garante que usa o ID antigo travado
                dao.atualizar(mu);
            }
            limpar();
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Salvo!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void deletar() {
        if (idOSSel != null && idMaterialSel != null) {
            dao.deletar(idMaterialSel, idOSSel);
            limpar();
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Removido!");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um item na tabela.");
        }
    }

    private void limpar() {
        txtQuantidade.setText("");
        idOSSel = null;
        idMaterialSel = null;
        comboOS.setEnabled(true);
        comboMaterial.setEnabled(true);
        tabela.clearSelection();
        ((JButton) ((JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(7)).getComponent(0)).setText("Adicionar");
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);

        // VERIFIQUE SE ESTA CHAVE { ESTÁ AQUI
        for (MaterialUtilizado mu : dao.listarTodos()) {

            // Agora sim pode declarar variáveis aqui dentro
            String nomeMat = getNomeMaterial(mu.getIdMaterial());
            String descOS = getDescricaoOS(mu.getIdOrdemServico());

            modelo.addRow(new Object[]{
                    mu.getIdOrdemServico(),
                    descOS,
                    nomeMat,
                    mu.getQuantidade()
            });

        } // E VERIFIQUE SE FECHOU AQUI
    }
}