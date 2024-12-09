package br.com.projetofinal.DAO;

import java.sql.*;

public class ConnectionDAO {

	private static Connection con = null;

	private ConnectionDAO() {

	}

	// Estabelece uma conexão com o banco de dados
	// Se a conexão ainda não foi estabelecida, tenta criar a conexão e retornar o
	// objeto Connection.
	public static Connection ConnectDB() {
		if (con == null) {
			try {
				String dsn = "projetoFinalLP2";
				String user = "postgres";
				String senha = "1508";
				DriverManager.registerDriver(new org.postgresql.Driver());
				String url = "jdbc:postgresql://localhost:5432/" + dsn;

				// Cria a conexão com o banco de dados
				con = DriverManager.getConnection(url, user, senha);
				con.setAutoCommit(false);
				System.out.println("Conexão estabelecida com sucesso!");
			} catch (SQLException e) {
				System.out.println("Erro ao conectar ao banco: " + e.getMessage());
			}
		}
		return con;
	}

	// Fecha a conexão com o banco de dados, se estiver aberta.
	// Se a conexão for fechada com sucesso, o objeto de conexão é setado como null.
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
