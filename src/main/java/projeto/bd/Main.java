package projeto.bd;

import projeto.bd.tela.TelaCidadao;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        //abridno a tela
        SwingUtilities.invokeLater(() -> {

            TelaCidadao tela = new TelaCidadao();
            tela.setVisible(true);

        });
    }
}