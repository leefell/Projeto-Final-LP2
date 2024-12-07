package br.com.projetofinal.DTO;

import java.util.Date;

public interface ITaskDTO {
	int getId();
    String getTitle();
    String getStatus();
    String getType();
    Date getCreatedAt();
}
