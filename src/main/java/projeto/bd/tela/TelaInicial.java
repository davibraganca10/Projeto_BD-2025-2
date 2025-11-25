package projeto.bd.tela;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JFrame {
    public TelaInicial() {
        super("SISTEMA DE OCORRÊNCIAS URBANAS");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 20, 20));

        JButton btn1 = new JButton("GERENCIAR CIDADÃOS");
        JButton btn2 = new JButton("GERENCIAR SOLICITAÇÕES");
        JButton btn3 = new JButton("SAIR");

        btn1.addActionListener(e -> new TelaCidadao().setVisible(true));
        btn2.addActionListener(e -> new TelaSolicitacao().setVisible(true));
        btn3.addActionListener(e -> System.exit(0));

        JPanel p = new JPanel(new GridLayout(3, 1, 10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        p.add(btn1); p.add(btn2); p.add(btn3);
        add(p);
    }
}