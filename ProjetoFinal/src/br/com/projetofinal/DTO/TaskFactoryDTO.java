package br.com.projetofinal.DTO;

public abstract class TaskFactoryDTO {
	public abstract ITaskDTO createTask(String title, String status);
}
