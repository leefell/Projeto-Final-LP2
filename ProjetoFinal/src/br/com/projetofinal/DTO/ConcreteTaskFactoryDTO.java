package br.com.projetofinal.DTO;

import java.util.Date;

public class ConcreteTaskFactoryDTO extends TaskFactoryDTO {
    @Override
    public ITaskDTO createTask(String title, String status){
        if(status.equalsIgnoreCase("urgent")){
            return new UrgentTaskDTO(title, status);
        }else{
            return new NormalTaskDTO(title, status);
        }
    }
}
