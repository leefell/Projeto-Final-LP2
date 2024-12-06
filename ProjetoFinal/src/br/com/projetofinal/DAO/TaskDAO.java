package br.com.projetofinal.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.projetofinal.DTO.ITaskDTO;
import br.com.projetofinal.DTO.NormalTaskDTO;
import br.com.projetofinal.DTO.UrgentTaskDTO;

public class TaskDAO {
	private Connection con;

	public TaskDAO(Connection con) {
		this.con = con;
	}

	// Cria
	public boolean createTask(ITaskDTO task) {
		String query = "INSERT INTO tasks (title, status, type, created_at) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, task.getTitle());
			stmt.setString(2, "TODO");
			stmt.setString(3, task.getType());
			stmt.setDate(4, new Date(System.currentTimeMillis()));

			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Erro ao criar tarefa: " + e.getMessage());
			return false;
		}
	}

	// Lista
	public List<ITaskDTO> getAllTasks() {
		String query = "SELECT * FROM tasks";
		List<ITaskDTO> tasks = new ArrayList<>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				tasks.add(createTaskFromResultSet(rs));
			}
		} catch (SQLException e) {
			System.err.println("Erro ao buscar todas as tarefas: " + e.getMessage());
		}
		return tasks;
	}

	// Ler tarefa especifica
	public ITaskDTO getTaskById(int id) {
		String query = "SELECT * from tasks where id = ?";
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return createTaskFromResultSet(rs);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao buscar tarefa pelo ID: " + e.getMessage());
		}
		return null;
	}

	// Atualiza
	public void updateTask(int id, ITaskDTO task) {
		String query = "UPDATE tasks SET title = ?, status = ?, type = ?, created_at = ? WHERE id = ?";
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, task.getTitle());
			stmt.setString(2, task.getStatus());
			stmt.setString(3, task.getType());
			stmt.setTimestamp(4, new Timestamp(task.getCreatedAt().getTime()));
			stmt.setInt(5, id);

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Erro ao atualizar tarefa: " + e.getMessage());
		}
	}
	
	// Área de Edição de Status
	// Atualiza Fazer para Fazendo
		public void updateTaskTodoToDoing(int id) {
			String query = "UPDATE tasks SET status = ? WHERE id = ?";
			try {
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, "DOING");
				stmt.setInt(2, id);

				stmt.executeUpdate();
			} catch (SQLException e) {
				System.err.println("Erro ao atualizar tarefa: " + e.getMessage());
			}
		}
		
		// Atualiza Fazendo para Feita
				public void updateTaskDoingToDone(int id) {
					String query = "UPDATE tasks SET status = ? WHERE id = ?";
					try {
						PreparedStatement stmt = con.prepareStatement(query);
						stmt.setString(1, "DONE");
						stmt.setInt(2, id);

						stmt.executeUpdate();
					} catch (SQLException e) {
						System.err.println("Erro ao atualizar tarefa: " + e.getMessage());
					}
				}
				
				// Atualiza Feita para Fazendo
				public void updateTaskDoneToDoing(int id) {
					String query = "UPDATE tasks SET status = ? WHERE id = ?";
					try {
						PreparedStatement stmt = con.prepareStatement(query);
						stmt.setString(1, "DOING");
						stmt.setInt(2, id);

						stmt.executeUpdate();
					} catch (SQLException e) {
						System.err.println("Erro ao atualizar tarefa: " + e.getMessage());
					}
				}
				
				// Atualiza Feita para Fazendo
				public void updateTaskDoingToTodo(int id) {
					String query = "UPDATE tasks SET status = ? WHERE id = ?";
					try {
						PreparedStatement stmt = con.prepareStatement(query);
						stmt.setString(1, "TODO");
						stmt.setInt(2, id);

						stmt.executeUpdate();
					} catch (SQLException e) {
						System.err.println("Erro ao atualizar tarefa: " + e.getMessage());
					}
				}

	// Deleta
	public void deleteTask(int id) {
		String query = "Delete from tasks where id = ?";
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Erro ao deletar tarefa: " + e.getMessage());
		}
	}

	// método auxiliar que converte o resultset em task
	private ITaskDTO createTaskFromResultSet(ResultSet rs) {
	    ITaskDTO task = null;
	    try {
	        // pega os valores das colunas
	        String title = rs.getString("title");
	        String status = rs.getString("status");
	        String type = rs.getString("type");
	        Date createdAt = rs.getDate("created_at");

	        // checa o tipo
	        if ("normal".equalsIgnoreCase(type)) {
	            task = new NormalTaskDTO(title, status, createdAt);
	        } else if ("urgent".equalsIgnoreCase(type)) {
	            task = new UrgentTaskDTO(title, status, createdAt);
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao criar tarefa a partir do ResultSet: " + e.getMessage());
	    }
	    return task;
	}
}
