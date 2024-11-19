package br.com.projetofinal.DTO;

import java.util.Date;

public  abstract class TaskFactoryDTO {
       public abstract ITaskDTO createTask(String title, String status, Date created_at);
       
       
}
