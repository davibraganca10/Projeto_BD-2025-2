package projeto.bd.tela;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaInicial extends JFrame {

    public TelaInicial() {
        super("SISTEMA DE GESTÃO URBANA");
        setSize(850, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(new Color(44, 62, 80)); // Azul Escuro
        painelTitulo.setPreferredSize(new Dimension(800, 80));
        painelTitulo.setLayout(new GridBagLayout());

        JLabel lblTitulo = new JLabel("PAINEL DE CONTROLE");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        painelTitulo.add(lblTitulo);

        add(painelTitulo, BorderLayout.NORTH);


        JPanel painelCentral = new JPanel(new GridLayout(4, 2, 20, 20));
        painelCentral.setBackground(new Color(236, 240, 241)); // Fundo Cinza Claro
        painelCentral.setBorder(new EmptyBorder(30, 50, 30, 50)); // Margens laterais

        JButton btnCidadao = criarBotao("GERENCIAR CIDADÃOS");
        JButton btnSolicitacao = criarBotao("GERENCIAR SOLICITAÇÕES");
        JButton btnEquipe = criarBotao("GERENCIAR EQUIPES");
        JButton btnFuncionario = criarBotao("GERENCIAR FUNCIONÁRIOS");
        JButton btnVeiculo = criarBotao("GERENCIAR VEÍCULOS");
        JButton btnMaterial = criarBotao("GERENCIAR MATERIAIS");
        JButton btnMatUtilizado = criarBotao("MATERIAIS UTILIZADOS");
        JButton btnOrdem = criarBotao("ORDENS DE SERVIÇO");


        painelCentral.add(btnCidadao);
        painelCentral.add(btnSolicitacao);
        painelCentral.add(btnEquipe);
        painelCentral.add(btnFuncionario);
        painelCentral.add(btnVeiculo);
        painelCentral.add(btnMaterial);
        painelCentral.add(btnMatUtilizado);
        painelCentral.add(btnOrdem);

        add(painelCentral, BorderLayout.CENTER);


        JPanel painelSul = new JPanel(new BorderLayout());
        painelSul.setBackground(new Color(236, 240, 241));


        JPanel painelSair = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelSair.setBackground(new Color(236, 240, 241));
        painelSair.setBorder(new EmptyBorder(0, 0, 20, 0)); // Espaço embaixo

        JButton btnSair = criarBotao("SAIR DO SISTEMA");
        btnSair.setBackground(new Color(192, 57, 43)); // Vermelho Base
        btnSair.setForeground(Color.WHITE);
        btnSair.setPreferredSize(new Dimension(300, 50)); // Botão maior e largo
        painelSair.add(btnSair);


        JPanel painelRodape = new JPanel();
        painelRodape.setBackground(new Color(44, 62, 80));
        JLabel lblRodape = new JLabel("Projeto Banco de Dados");
        lblRodape.setForeground(Color.LIGHT_GRAY);
        lblRodape.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblRodape.setBorder(new EmptyBorder(10, 0, 10, 0));
        painelRodape.add(lblRodape);

        painelSul.add(painelSair, BorderLayout.CENTER);
        painelSul.add(painelRodape, BorderLayout.SOUTH);

        add(painelSul, BorderLayout.SOUTH);


        btnCidadao.addActionListener(e -> new TelaCidadao().setVisible(true));
        btnSolicitacao.addActionListener(e -> new TelaSolicitacao().setVisible(true));
        btnEquipe.addActionListener(e -> new TelaEquipe().setVisible(true));
        btnFuncionario.addActionListener(e -> new TelaFuncionario().setVisible(true));
        btnVeiculo.addActionListener(e -> new TelaVeiculo().setVisible(true));
        btnMaterial.addActionListener(e -> new TelaMaterial().setVisible(true));
        btnMatUtilizado.addActionListener(e -> new TelaMaterialUtilizado().setVisible(true));
        btnOrdem.addActionListener(e -> new TelaOrdemServico().setVisible(true));
        btnSair.addActionListener(e -> System.exit(0));
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(44, 62, 80)); // Azul escuro
        // Borda suave
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));


        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                if (texto.contains("SAIR")) {
                    btn.setBackground(new Color(231, 76, 60));
                } else {
                    btn.setBackground(new Color(52, 152, 219));
                    btn.setForeground(Color.WHITE);
                }
            }
            public void mouseExited(MouseEvent evt) {
                if (texto.contains("SAIR")) {
                    btn.setBackground(new Color(192, 57, 43));
                } else {
                    btn.setBackground(Color.WHITE);
                    btn.setForeground(new Color(44, 62, 80));
                }
            }
        });

        return btn;
    }
}