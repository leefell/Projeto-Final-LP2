package br.com.projetofinal.CTR;

import br.com.projetofinal.DAO.TaskDAO;
import br.com.projetofinal.DTO.ITaskDTO;

import java.sql.Connection;
import java.util.List;

public class TaskCTR {

	private TaskDAO taskDAO = new TaskDAO();

	public TaskCTR() {

	}

	// Cria uma nova tarefa e retorna uma mensagem indicando se a criação foi
	// bem-sucedida ou não
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

	// Retorna uma lista com todas as tarefas armazenadas no banco de dados
	public List<ITaskDTO> getAllTasks() {
		return taskDAO.getAllTasks();
	}

	// Retorna uma tarefa específica com base no ID fornecido
	public ITaskDTO getTaskById(int id) {
		return taskDAO.getTaskById(id);
	}

	// Atualiza os dados de uma tarefa existente (título e tipo) e retorna uma
	// mensagem de sucesso ou erro
	public String updateTask(int id, String title, String type) {
		try {
			taskDAO.updateTask(id, title, type);
			return "Task atualizada com sucesso!";
		} catch (Exception e) {
			return handleError("Erro ao atualizar tarefa", e);
		}
	}

	// Atualiza o status de uma tarefa e retorna uma mensagem indicando o sucesso ou
	// falha da operação
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

	// Exclui uma tarefa do banco de dados e retorna uma mensagem de sucesso ou erro
	public String deleteTask(int id) {
		try {
			taskDAO.deleteTask(id);
			return "Tarefa excluída com sucesso!";
		} catch (Exception e) {
			return handleError("Erro ao excluir tarefa", e);
		}
	}

	// Método auxiliar que lida com a captura e formatação de mensagens de erro
	private String handleError(String message, Exception e) {
		System.err.println(message + ": " + e.getMessage());
		return message + ". Consulte os logs para mais detalhes.";
	}
}
