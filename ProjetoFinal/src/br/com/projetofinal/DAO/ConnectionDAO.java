package br.com.projetofinal.DAO;

import java.sql.*;

public class ConnectionDAO {

    public static Connection con = null;

    public ConnectionDAO() {

    }

    public static void ConnectDB() {
        try {
            String dsn = "projetoFinalLP2";
            String user = "postgres";
            String senha = "1508";

            DriverManager.registerDriver(new org.postgresql.Driver());

            String url = "jdbc:postgresql://localhost:5432/" + dsn;

            con = DriverManager.getConnection(url, user, senha);
            con.setAutoCommit(false);
            if (con != null) {
                System.out.println("Conexão Estabelecida com o banco de dados");
            }

            if (con == null) {
                System.out.println("Erro ao abrir o banco");
            }
        } catch (Exception e) {
            System.out.println("Problema ao abrir a base de dados! " + e.getMessage());
        }

    }

    public static void CloseDB() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.println("Problema ao fechar a base de dados! "
                    + e.getMessage());
        }
    }
}
