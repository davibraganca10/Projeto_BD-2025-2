package projeto.bd.tela;

import projeto.bd.dao.*;
import projeto.bd.models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;

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

    public TelaSolicitacao() {
        super("Cadastro de Solicitação");
        setSize(900, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));
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

        add(form, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(new String[]{"Protocolo", "Data", "Cidadão ID", "Serviço ID"}, 0);
        add(new JScrollPane(new JTable(modeloTabela)), BorderLayout.CENTER);

        JButton btnSalvar = new JButton("SALVAR SOLICITAÇÃO");
        add(btnSalvar, BorderLayout.SOUTH);

        btnFoto.addActionListener(e -> selecionarFoto());
        btnSalvar.addActionListener(e -> salvar());

        atualizarTabela();
    }

    private void selecionarFoto() {
        JFileChooser ch = new JFileChooser();
        if (ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileInputStream fis = new FileInputStream(ch.getSelectedFile())) {
                fotoBytes = fis.readAllBytes();
                lblFoto.setText("Foto OK");
                lblFoto.setForeground(Color.BLUE);
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    private void salvar() {
        try {
            Solicitacao s = new Solicitacao();
            s.setIdCidadao(((Cidadao) comboCidadao.getSelectedItem()).getId());
            s.setIdRegiao(((Regiao) comboRegiao.getSelectedItem()).getId());
            s.setIdTipoServico(((TipoServico) comboTipo.getSelectedItem()).getId());
            s.setIdStatus(((Status) comboStatus.getSelectedItem()).getId());
            s.setLogradouro(txtLogradouro.getText());
            s.setReferencia(txtReferencia.getText());
            s.setNumeroSolicitacao("SOL" + System.currentTimeMillis());
            s.setFoto(fotoBytes);

            dao.salvar(s);
            JOptionPane.showMessageDialog(this, "Salvo!");
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (Solicitacao s : dao.listarTodas()) {
            modeloTabela.addRow(new Object[]{
                    s.getNumeroSolicitacao(),
                    s.getDataAbertura() != null ? sdf.format(s.getDataAbertura()) : "",
                    s.getIdCidadao(),
                    s.getIdTipoServico()
            });
        }
    }
}