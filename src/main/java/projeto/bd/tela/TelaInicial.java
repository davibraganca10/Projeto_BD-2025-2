package projeto.bd.tela;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaInicial extends JFrame {

    public TelaInicial() {
        super("SISTEMA DE OCORRÊNCIAS URBANAS");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(new Color(50, 50, 50));
        JLabel lblTitulo = new JLabel("MENU PRINCIPAL");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        painelTitulo.add(lblTitulo);

        add(painelTitulo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(5, 1, 15, 15));

        painelBotoes.setBorder(new EmptyBorder(30, 100, 30, 100));

        JButton btn1 = criarBotao("GERENCIAR CIDADÃOS");
        JButton btn2 = criarBotao("GERENCIAR SOLICITAÇÕES");
        JButton btn3 = criarBotao("GERENCIAR EQUIPES");
        JButton btn4 = criarBotao("GERAR ORDEM DE SERVIÇO");
        JButton btn5 = criarBotao("SAIR");

        btn1.addActionListener(e -> new TelaCidadao().setVisible(true));
        btn2.addActionListener(e -> new TelaSolicitacao().setVisible(true));
        btn3.addActionListener(e -> new TelaEquipe().setVisible(true));
        btn4.addActionListener(e -> new TelaOrdemServico().setVisible(true));
        btn5.addActionListener(e -> System.exit(0));

        painelBotoes.add(btn1);
        painelBotoes.add(btn2);
        painelBotoes.add(btn3);
        painelBotoes.add(btn4);
        painelBotoes.add(btn5);

        add(painelBotoes, BorderLayout.CENTER);

        JLabel rodape = new JLabel("Projeto Banco de Dados 2025/2", SwingConstants.CENTER);
        rodape.setBorder(new EmptyBorder(10,0,10,0));
        add(rodape, BorderLayout.SOUTH);
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(230, 230, 230));
        return btn;
    }
}