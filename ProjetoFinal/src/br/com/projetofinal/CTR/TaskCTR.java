package br.com.projetofinal.CTR;

import br.com.projetofinal.DAO.TaskDAO;
import br.com.projetofinal.DTO.ITaskDTO;

import java.sql.Connection;
import java.util.List;

public class TaskCTR {

    private TaskDAO taskDAO = new TaskDAO();

    public TaskCTR() {
    	
    }

    public String createTask(ITaskDTO task) {
        try {
            if (taskDAO.createTask(task)) {
                return task.getType() + " task cadastrada com sucesso!";
            } else {
                return task.getType() + " task não cadastrada!";
            }
        } catch (Exception e) {
            return handleError("Erro ao criar tarefa", e);
        }
    }

    public List<ITaskDTO> getAllTasks() {
        return taskDAO.getAllTasks();
    }

    public ITaskDTO getTaskById(int id) {
        return taskDAO.getTaskById(id);
    }

    public String updateTask(int id, ITaskDTO updatedTask) {
        try {
            taskDAO.updateTask(id, updatedTask);
            return "Task atualizada com sucesso!";
        } catch (Exception e) {
            return handleError("Erro ao atualizar tarefa", e);
        }
    }

    public String updateTaskStatus(int id, String newStatus) {
        try {
            boolean isUpdated = taskDAO.updateTaskStatus(id, newStatus);
            if (isUpdated) {
                return "Status da task atualizado para '" + newStatus + "' com sucesso!";
            } else {
                return "Não foi possível atualizar o status da task.";
            }
        } catch (Exception e) {
            return handleError("Erro ao atualizar status da tarefa", e);
        }
    }

    public String deleteTask(int id) {
        try {
            taskDAO.deleteTask(id);
            return "Tarefa excluída com sucesso!";
        } catch (Exception e) {
            return handleError("Erro ao excluir tarefa", e);
        }
    }

    // Método auxiliar para manipular mensagens de erro
    private String handleError(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
        return message + ". Consulte os logs para mais detalhes.";
    }
}
