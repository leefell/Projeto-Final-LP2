package br.com.projetofinal.CTR;

import br.com.projetofinal.DAO.TaskDAO;
import br.com.projetofinal.DTO.ITaskDTO;

import java.sql.Connection;
import java.util.List;

public class TaskCTR {
    private TaskDAO taskDAO;

    public TaskCTR(Connection con) {
        this.taskDAO = new TaskDAO(con);
    }

    public String createTask(ITaskDTO task) {
        try {
            if (taskDAO.createTask(task)) {
                return task.getType() + " task cadastrada com sucesso!";
            } else {
                return task.getType() + " task não cadastrada!";
            }
        } catch (Exception e) {
            System.err.println("Erro ao criar tarefa: " + e.getMessage());
            return task.getType() + " task não cadastrada.";
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
            System.err.println("Erro ao atualizar tarefa: " + e.getMessage());
            return "Erro ao atualizar a task.";
        }
    }
    
    public String deleteTask(int id) {
    	try {
    		taskDAO.deleteTask(id);
    		return "Tarefa excluída com sucesso!";
    	}catch(Exception e) {
    		System.err.println("Erro ao excluir tarefa: " + e.getMessage());
            return "Erro ao excluir a task.";
    	}
    }
}
