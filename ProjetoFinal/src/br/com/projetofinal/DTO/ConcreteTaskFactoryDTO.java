package br.com.projetofinal.DTO;

import java.util.Date;

public class ConcreteTaskFactoryDTO extends TaskFactoryDTO {
    @Override
    public ITaskDTO createTask(String title, String status, Date created_at){
        if(status.equalsIgnoreCase("urgent")){
            return new UrgentTaskDTO(title, status, created_at);
        }else{
            return new NormalTaskDTO(title, status, created_at);
        }
    }
}
