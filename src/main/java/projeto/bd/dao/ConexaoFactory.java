package projeto.bd.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoFactory {

    public static Connection getConexao() {
        try {
            Properties props = loadProperties();
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            return DriverManager.getConnection(url, user, pass);

        } catch (SQLException | IOException e) {
            throw new RuntimeException("Erro ao conectar no banco de dados", e);
        }
    }

    private static Properties loadProperties() throws IOException {
        Properties props = new Properties();
        try (InputStream input = ConexaoFactory.class.getResourceAsStream("/db.properties")) {
            if (input == null) {
                throw new RuntimeException("Arquivo não encontrado");
            }
            props.load(input);
        }
        return props;
    }
}