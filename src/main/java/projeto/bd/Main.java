package projeto.bd;

import projeto.bd.tela.TelaInicial;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaInicial().setVisible(true);
        });
    }
}