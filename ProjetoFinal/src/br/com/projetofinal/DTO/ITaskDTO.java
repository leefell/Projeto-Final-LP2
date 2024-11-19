package br.com.projetofinal.DTO;

import java.util.Date;

public interface ITaskDTO {
    String getTitle();
    String getStatus();
    String getType();
    Date getCreatedAt();
}
