package br.com.projetofinal.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.projetofinal.DTO.ITaskDTO;
import br.com.projetofinal.DTO.NormalTaskDTO;
import br.com.projetofinal.DTO.UrgentTaskDTO;

public class TaskDAO {

    // Criação de tarefa
    public boolean createTask(ITaskDTO task) {
        String query = "INSERT INTO tasks (title, status, type, created_at) VALUES (?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = ConnectionDAO.ConnectDB();
            stmt = con.prepareStatement(query);
            stmt.setString(1, task.getTitle());
            stmt.setString(2, "TODO");
            stmt.setString(3, task.getType());
            stmt.setDate(4, new Date(System.currentTimeMillis()));

            stmt.executeUpdate();
            con.commit();
            System.out.println("Tarefa Inserida no banco!");
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao criar tarefa: " + e.getMessage());
            return false;
        } finally {
            ConnectionDAO.closeDB();
        }
    }

    // Busca todas as tarefas
    public List<ITaskDTO> getAllTasks() {
        String query = "SELECT * FROM tasks";
        List<ITaskDTO> tasks = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionDAO.ConnectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                tasks.add(createTaskFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as tarefas: " + e.getMessage());
        } finally {
            ConnectionDAO.closeDB();
        }
        return tasks;
    }

    // Busca tarefa pelo ID
    public ITaskDTO getTaskById(int id) {
        String query = "SELECT * FROM tasks WHERE id = ?";
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionDAO.ConnectDB();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return createTaskFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar tarefa pelo ID: " + e.getMessage());
        } finally {
            ConnectionDAO.closeDB();
        }
        return null;
    }

    // Atualização de tarefa
    public boolean updateTask(int id, ITaskDTO task) {
        String query = "UPDATE tasks SET title = ?, status = ?, type = ?, created_at = ? WHERE id = ?";
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = ConnectionDAO.ConnectDB();
            stmt = con.prepareStatement(query);
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getStatus());
            stmt.setString(3, task.getType());
            stmt.setTimestamp(4, new Timestamp(task.getCreatedAt().getTime()));
            stmt.setInt(5, id);

            stmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar tarefa: " + e.getMessage());
            return false;
        } finally {
            ConnectionDAO.closeDB();
        }
    }

    // Atualização de status (método genérico)
    public boolean updateTaskStatus(int id, String newStatus) {
        String query = "UPDATE tasks SET status = ? WHERE id = ?";
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = ConnectionDAO.ConnectDB();
            stmt = con.prepareStatement(query);
            stmt.setString(1, newStatus);
            stmt.setInt(2, id);

            stmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status da tarefa: " + e.getMessage());
            return false;
        } finally {
            ConnectionDAO.closeDB();
        }
    }

    // Exclusão de tarefa
    public boolean deleteTask(int id) {
        String query = "DELETE FROM tasks WHERE id = ?";
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = ConnectionDAO.ConnectDB();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar tarefa: " + e.getMessage());
            return false;
        } finally {
            ConnectionDAO.closeDB();
        }
    }

    // Criação de tarefa a partir do ResultSet (método auxiliar)
    private ITaskDTO createTaskFromResultSet(ResultSet rs) {
        try {
            String title = rs.getString("title");
            String status = rs.getString("status");
            String type = rs.getString("type");
            Date createdAt = rs.getDate("created_at");

            if ("normal".equalsIgnoreCase(type)) {
                return new NormalTaskDTO(title, status, createdAt);
            } else if ("urgent".equalsIgnoreCase(type)) {
                return new UrgentTaskDTO(title, status, createdAt);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar tarefa a partir do ResultSet: " + e.getMessage());
        }
        return null;
    }
}
