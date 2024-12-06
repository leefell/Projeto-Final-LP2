package br.com.projetofinal.DTO;

import java.util.Date;

public class UrgentTaskDTO implements ITaskDTO {

    private String title;
    private String status;
    private String type;
    private Date created_At;

    public UrgentTaskDTO(String title, String status, Date created_At) {
    	this.title = title;
        this.status = status;
        this.type = "urgent";
        this.created_At = created_At;
    }
    
    public UrgentTaskDTO(String title, String status) {
        this.title = title;
        this.status = status;
        this.type = "urgent";       
    }
    
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Date getCreatedAt() {
        return created_At;
    }

}
