package br.com.projetofinal.DAO;

import java.sql.*;

public class ConnectionDAO {

    private static Connection con = null;

    private ConnectionDAO() {

    }

    public static Connection ConnectDB() {
        if (con == null) {
            try {
                String dsn = "projetoFinalLP2";
                String user = "postgres";
                String senha = "1508";
                DriverManager.registerDriver(new org.postgresql.Driver());
                String url = "jdbc:postgresql://localhost:5432/" + dsn;

                con = DriverManager.getConnection(url, user, senha);
                con.setAutoCommit(false);
                System.out.println("Conexão estabelecida com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco: " + e.getMessage());
            }
        }
        return con;
    }

    public static void closeDB() {
        if (con != null) {
            try {
                con.close();
                con = null;
                System.out.println("Conexão fechada com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
